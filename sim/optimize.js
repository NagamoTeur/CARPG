// Optimiseur de builds : choisit talents / affixes / gemmes qui maximisent un objectif, avec le même moteur que le PoB.
const ctx = require('./harness');
const { ENG, D, IDX } = ctx;
const RARITY = 'mythic';

const clone = o => JSON.parse(JSON.stringify(o));
const softCap = ENG.softCap;

// ------- objectifs (échelle log pour combiner proprement) -------
const school = (R, s) => R.at['irons_spellbooks:' + s + '_spell_power'] * R.spellPower;
const ehp = (R, hit = 250) => {
  const red = R.armorReduction(hit);
  return R.hp / Math.max(0.02, 1 - red) / Math.max(0.1, 1 - Math.min(0.9, R.dodgeTotal)) / R.taken;
};
const OBJ = {
  melee: R => Math.pow(R.dps + 1, 0.6) * Math.pow(ehp(R), 0.4),
  ranged: R => Math.pow(R.dps + 1, 0.6) * Math.pow(ehp(R), 0.4),
  tank: R => Math.pow(R.dps + 1, 0.3) * Math.pow(ehp(R), 0.7),
  magic: s => R => Math.pow(school(R, s), 0.55) * Math.pow(1 / (2 - softCap(R.at['irons_spellbooks:cooldown_reduction'])), 0.15) * Math.pow(R.mana * R.manaRegen, 0.1) * Math.pow(ehp(R), 0.3),
  summon: R => Math.pow(R.at['irons_spellbooks:summon_damage'] * school(R, 'blood'), 0.5) * Math.pow(R.mana * R.manaRegen, 0.1) * Math.pow(ehp(R), 0.4),
};

function candidates(type, slotKind, rar) {
  return D.affixes.filter(a => a.kind === 'attribute' && a.slot === slotKind && a.types.includes(type) && a.rarities.includes(rar));
}
function gemCandidates(type) {
  return D.gems.apotheosis.filter(g => g.bonuses.some(b => b.types.includes(type) && (b.kind === 'attr' || b.kind === 'multi') && ((b.values && b.values[RARITY] != null) || (b.mods && b.mods.some(m => m.values[RARITY] != null)))));
}

function optimize(spec) {
  const S = {
    v: 1, name: spec.title, origin: spec.origin, cls: spec.cls, blessing: spec.blessing, flags: spec.flags || { dragon: true },
    points: spec.points || 60, nodes: [], gear: {}, spells: spec.spells || [], manual: spec.manual || [], notes: spec.notes || '', hit: spec.hit || 100,
  };
  const obj = typeof spec.objective === 'function' ? spec.objective : OBJ[spec.objective];
  const floor = spec.hpFloor || 250;
  const score = st => { const R = ENG.compute(st); return obj(R) * Math.pow(Math.min(1, R.hp / floor), 2); };
  // équipement de base
  for (const [slot, g] of Object.entries(spec.gear)) {
    const rar = g.rarity || RARITY;
    S.gear[slot] = { type: g.type, rarity: rar, name: g.name || '', affixes: [], gems: [], base: Object.assign({}, g.base || {}), extra: g.extra || [] };
    const ra = D.rarities[rar];
    S.gear[slot].gems = Array.from({ length: Math.min(g.sockets ?? ra.sockets, ra.sockets, spec.socketLimit || 99) }, () => ({ id: '', rar: g.gemRarity || RARITY }));
  }
  const gearPass = () => {
  for (let pass = 0; pass < 3; pass++) {
    for (const slot of Object.keys(S.gear)) {
      const it = S.gear[slot], g = spec.gear[slot];
      if (g.fixedAffixes) { if (!it.affixes.length) it.affixes = g.fixedAffixes.map(id => ({ id, roll: spec.roll ?? 0.8 })); continue; }
      const ra = D.rarities[it.rarity];
      const want = Math.min(ra.sockets, spec.socketLimit || 99) + ENG.extraSockets(S, ENG.SLOT_DEFS.find(x => x.id === slot), it);
      while (it.gems.length < want) it.gems.push({ id: '', rar: g.gemRarity || RARITY });
      while (it.gems.length > want) it.gems.pop();
      // affixes de stat : on reconstruit à chaque passe
      it.affixes = it.affixes.filter(a => (g.keep || []).includes(a.id));
      while (it.affixes.length < Math.min(ra.stat, spec.affixLimit || 99)) {
        let best = null, bs = score(S);
        for (const a of candidates(it.type, 'stat', it.rarity)) {
          if (it.affixes.find(x => x.id === a.id)) continue;
          if ((g.ban || []).includes(a.id)) continue;
          it.affixes.push({ id: a.id, roll: spec.roll ?? 0.8 });
          const sc = score(S); it.affixes.pop();
          if (sc > bs * 1.00001) { bs = sc; best = a; }
        }
        if (!best) break; it.affixes.push({ id: best.id, roll: spec.roll ?? 0.8 });
      }
      // gemmes
      for (const gslot of it.gems) {
        let best = null, bs = -1; gslot.id = '';
        const base = score(S); bs = base;
        for (const gem of gemCandidates(it.type)) {
          if (gem.unique && it.gems.some(y => y.id === gem.id && y !== gslot)) continue;
          if (gem.unique === undefined && false) continue;
          gslot.id = gem.id; gslot.rar = RARITY; const sc = score(S);
          if (sc > bs * 1.00001) { bs = sc; best = gem.id; }
        }
        gslot.id = best || '';
      }
      it.gems = it.gems.filter(x => x.id);
    }
  }
  };
  const treePass = (cls) => {
    const start = cls + '_class';
  // talents : choix glouton chemin/gain
  const byId = IDX.TREE.byId, nodes = D.skilltree.nodes;
  S.nodes = [start];
  let base = score(S);
  const MAXP = S.points;
  while (S.nodes.length < MAXP) {
    const A = new Set(S.nodes); let bestPath = null, bestRatio = 0;
    // BFS depuis le réseau
    const prev = {}, dist = {}, q = [];
    S.nodes.forEach(n => { prev[n] = null; dist[n] = 0; q.push(n); });
    while (q.length) { const c = q.shift(); for (const nb of byId[c].adj) if (!(nb in prev)) { prev[nb] = c; dist[nb] = dist[c] + 1; q.push(nb); } }
    for (const n of nodes) {
      if (A.has(n.id) || !(n.id in dist) || dist[n.id] > 30 || S.nodes.length + dist[n.id] > MAXP) continue;
      if (n.tier === 'class' && n.id !== start) continue; // ne pas traverser d'autres départs
      const path = []; for (let c = n.id; !A.has(c); c = prev[c]) path.push(c);
      if (path.some(p => byId[p].start)) continue;
      S.nodes.push(...path); const sc = score(S); S.nodes.length -= path.length;
      const ratio = (sc - base) / path.length;
      if (ratio > bestRatio) { bestRatio = ratio; bestPath = path; }
    }
    if (!bestPath) break;
    S.nodes.push(...bestPath.reverse()); base = score(S);
  }
  };
  const CLASSES = spec.treeClass === 'auto' ? ['hunter', 'blacksmith', 'alchemist', 'miner'] : [spec.treeClass];
  const bestTree = () => {
    let best = null, bs = -1;
    for (const c of CLASSES) { treePass(c); const sc = score(S); if (sc > bs) { bs = sc; best = { nodes: S.nodes.slice(), c }; } }
    S.nodes = best.nodes; S._treeClass = best.c;
  };
  for (let round = 0; round < 2; round++) { gearPass(); bestTree(); }
  gearPass();
  return S;
}

function summary(S) {
  const R = ENG.compute(S);
  return { hp: Math.round(R.hp), armor: Math.round(R.armor), tough: R.tough, red250: +(R.armorReduction(250)).toFixed(3), eva: +R.evasionChance.toFixed(3), dodge: +R.dodgeTotal.toFixed(3),
    hit: +R.hit.toFixed(1), spd: +R.spd.toFixed(2), critC: +R.critC.toFixed(2), critD: +R.critD.toFixed(2), dps: +R.dps.toFixed(1), arrow: +R.arrowHit.toFixed(1), mana: Math.round(R.mana), regen: +R.manaPerSec.toFixed(1), sp: +R.spellPower.toFixed(2),
    fire: +R.at['irons_spellbooks:fire_spell_power'].toFixed(2), ice: +R.at['irons_spellbooks:ice_spell_power'].toFixed(2), holy: +R.at['irons_spellbooks:holy_spell_power'].toFixed(2), lightning: +R.at['irons_spellbooks:lightning_spell_power'].toFixed(2), blood: +R.at['irons_spellbooks:blood_spell_power'].toFixed(2), ender: +R.at['irons_spellbooks:ender_spell_power'].toFixed(2), nature: +R.at['irons_spellbooks:nature_spell_power'].toFixed(2), evoc: +R.at['irons_spellbooks:evocation_spell_power'].toFixed(2), eld: +R.at['irons_spellbooks:eldritch_spell_power'].toFixed(2),
    summon: +R.at['irons_spellbooks:summon_damage'].toFixed(2), cdr: +R.at['irons_spellbooks:cooldown_reduction'].toFixed(2), nodes: S.nodes.length };
}
module.exports = { optimize, summary, OBJ, ctx };
