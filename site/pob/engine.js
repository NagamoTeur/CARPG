/* Moteur de calcul — reproduit les formules lues dans le code des mods (Minecraft, Apotheosis, Passive Skill Tree, Iron's Spells). */
(function () {
  const A = D.attrs;
  const norm = id => (id.startsWith('generic.') ? 'minecraft:' + id : id);
  // plafonds (attributefix.json du pack + valeurs des mods)
  const CAP = {
    'minecraft:generic.max_health': [1, 90000000], 'minecraft:generic.armor': [0, 1000000], 'minecraft:generic.armor_toughness': [0, 1000000],
    'minecraft:generic.attack_damage': [0, 1000000], 'minecraft:generic.attack_speed': [0, 1024], 'minecraft:generic.movement_speed': [0, 1024],
    'minecraft:generic.knockback_resistance': [0, 1], 'apotheosis:crit_chance': [0, 10], 'apotheosis:crit_damage': [1, 100],
    'apotheosis:dodge_chance': [0, 1], 'irons_spellbooks:spell_power': [0, 1000], 'irons_spellbooks:summon_damage': [0, 2000],
    'skilltree:evasion': [0, 90], 'skilltree:blocking': [0, 90], 'skilltree:stealth': [0, 90],
    'apotheosis:draw_speed': [0, 4], 'apotheosis:arrow_damage': [0, 10], 'apotheosis:arrow_velocity': [0, 10], 'apotheosis:experience_gained': [0, 10],
    'apotheosis:healing_received': [0, 10], 'apotheosis:mining_speed': [0, 10], 'apotheosis:life_steal': [0, 10], 'apotheosis:current_hp_damage': [0, 1],
    'apotheosis:overheal': [0, 10], 'apotheosis:armor_shred': [0, 2], 'apotheosis:prot_pierce': [0, 34], 'apotheosis:prot_shred': [0, 1],
    'apotheosis:cold_damage': [0, 1000], 'apotheosis:fire_damage': [0, 1000], 'apotheosis:armor_pierce': [0, 1000],
  };
  ['fire', 'ice', 'lightning', 'holy', 'ender', 'blood', 'evocation', 'nature', 'eldritch'].forEach(s => CAP['irons_spellbooks:' + s + '_spell_power'] = [0, 5000]);

  const SLOT_DEFS = [
    { id: 'main', name: 'Arme principale', types: ['sword', 'heavy_weapon', 'trident', 'bow', 'crossbow', 'pickaxe', 'shovel'], weapon: true },
    { id: 'off', name: 'Main secondaire', types: ['shield', 'sword', 'heavy_weapon', 'trident'] },
    { id: 'head', name: 'Casque', types: ['helmet'], armor: true },
    { id: 'chest', name: 'Plastron', types: ['chestplate'], armor: true },
    { id: 'legs', name: 'Jambières', types: ['leggings'], armor: true },
    { id: 'boots', name: 'Bottes', types: ['boots'], armor: true },
    { id: 'ring1', name: 'Anneau 1', types: ['curios:ring'] }, { id: 'ring2', name: 'Anneau 2', types: ['curios:ring'] }, { id: 'ring3', name: 'Anneau 3 (talent Aristocrat)', types: ['curios:ring'], needs: 'ring' },
    { id: 'neck', name: 'Collier', types: ['curios:necklace'] },
    { id: 'belt', name: 'Ceinture', types: ['curios:belt'] },
    { id: 'charm', name: 'Charme', types: ['curios:charm'] },
    { id: 'back', name: 'Dos', types: ['curios:back'] },
    { id: 'body', name: 'Corps (curio)', types: ['curios:body'] },
    { id: 'hands', name: 'Mains (curio)', types: ['curios:hands'] },
    { id: 'wrist', name: 'Bracelet', types: ['curios:bracelet', 'curios:bangle'] },
    { id: 'talis', name: 'Talisman', types: ['curios:talisman'] },
    { id: 'book', name: 'Livre de sorts', types: ['curios:spellbook'] },
    { id: 'stone', name: 'Pierre de sort', types: ['curios:spellstone'] },
    { id: 'focus', name: 'Focus Ars', types: ['curios:an_focus'] },
    { id: 'quiver', name: 'Carquois', types: ['curios:quiver'] },
  ];


  /* ---- correspondance équipement / conditions de l'arbre ---- */
  const WEAPON_T = ['sword', 'heavy_weapon', 'trident', 'bow', 'crossbow', 'pickaxe', 'shovel', 'axe'];
  function eqMatch(eq, sd, type) {
    if (!eq || eq === 'any') return true;
    const id = sd.id;
    const isArmor = ['head', 'chest', 'legs', 'boots'].includes(id);
    switch (eq) {
      case 'weapon': return id === 'main' && WEAPON_T.includes(type);
      case 'melee_weapon': return id === 'main' && ['sword', 'heavy_weapon', 'trident', 'axe'].includes(type);
      case 'ranged_weapon': return id === 'main' && ['bow', 'crossbow'].includes(type);
      case 'armor': return isArmor;
      case 'helmet': return id === 'head'; case 'chestplate': return id === 'chest'; case 'leggings': return id === 'legs'; case 'boots': return id === 'boots';
      case 'shield': return id === 'off' && type === 'shield';
      case 'ring': return /^ring/.test(id); case 'necklace': return id === 'neck';
      case 'jewelry': case 'tag:forge:curios/jewelry': return /^ring/.test(id) || id === 'neck';
      case 'tag:curios:ring': return /^ring/.test(id); case 'tag:curios:necklace': return id === 'neck';
      case 'sword': case 'bow': case 'crossbow': case 'trident': case 'heavy_weapon': case 'pickaxe': case 'shovel': return id === 'main' && type === eq;
      case 'axe': return id === 'main' && type === 'heavy_weapon';
      case 'tool': return id === 'main' && ['pickaxe', 'shovel', 'axe'].includes(type);
      default: return false;
    }
  }
  function itemsMatching(S, eq) {
    const out = [];
    for (const sd of SLOT_DEFS) { const it = (S.gear || {})[sd.id]; if (it && eqMatch(eq, sd, itemTypeOf(sd, it))) out.push({ sd, it }); }
    return out;
  }
  function assume(S) { const a = S.assume || {}; const w = (S.gear || {}).main; const ranged = w && ['bow', 'crossbow'].includes(w.type); return { dist: a.dist ?? (ranged ? 20 : 3), lowHp: !!a.lowHp, burning: !!a.burning, potions: a.potions || 0, enchants: a.enchants || 0, hunger: 20, targetEffect: !!a.targetEffect }; }
  /* évalue une stat de l'arbre -> {val} ou {off:raison} */
  function evalStat(st, S, A1) {
    const c = st.c || {}, as = assume(S), w = (S.gear || {}).main, wt = w && w.type;
    const ranged = ['bow', 'crossbow'].includes(wt);
    if (c.unknown) return { off: 'condition inconnue' };
    if (c.melee && (ranged || !w)) return { off: 'dégâts de mêlée' };
    if (c.proj && !ranged) return { off: 'dégâts de projectile' };
    if (c.hand) { const ok = c.hand === 'shield' ? (S.gear || {}).off && (S.gear.off.type === 'shield') : eqMatch(c.hand, { id: 'main' }, wt); if (!ok) return { off: 'objet en main : ' + c.hand }; }
    if (c.equipped && !itemsMatching(S, c.equipped).length) return { off: 'objet équipé : ' + c.equipped };
    if (c.gems) { const n = itemsMatching(S, c.gems[0]).reduce((a, x) => a + (x.it.gems || []).filter(g => g && g.id).length, 0); if (n < c.gems[1]) return { off: 'gemmes requises' }; }
    if (c.hp) { if (!as.lowHp) return { off: 'PV bas (option « PV ≤ 50 % »)' }; }
    if (c.food && as.hunger < c.food) return { off: 'faim insuffisante' };
    if (c.attr) { const v = A1 ? (A1[ENG_NORM(c.attr[0])] ?? 0) : 0; if (v < c.attr[1]) return { off: 'seuil de ' + (D.attrs[ENG_NORM(c.attr[0])] || {}).name + ' non atteint' }; }
    if (c.burning && !as.burning) return { off: 'cible en feu (option)' };
    if (c.targetEffect && !as.targetEffect) return { off: 'cible sous effet (option)' };
    if (c.potions && !as.potions) return { off: 'effets de potion actifs (option)' };
    let u = 1; const m = st.m;
    if (m) {
      if (m.k === 'gems') u = itemsMatching(S, m.item).reduce((a, x) => a + (x.it.gems || []).filter(g => g && g.id).length, 0);
      else if (m.k === 'dist') u = as.dist;
      else if (m.k === 'attr') u = Math.floor((A1 ? (A1[ENG_NORM(m.attr)] ?? 0) : 0) / (m.div || 1));
      else if (m.k === 'food') u = as.hunger;
      else if (m.k === 'potions') u = as.potions;
      else if (m.k === 'enchlevels') u = as.enchants;
      else if (m.k === 'enchants') u = as.enchants ? Math.max(1, Math.round(as.enchants / 4)) : 0;
      else u = 0;
      if (u === 0) return { val: 0 };
    }
    return { val: st.val * u };
  }
  const ENG_NORM = id => id.startsWith('generic.') ? 'minecraft:' + id : id;
  /* bonus de l'arbre sur les gemmes / sockets */
  function treeSocketInfo(S) {
    const gp = [], so = []; let rings = 0;
    for (const id of S.nodes || []) { const n = IDX.TREE.byId[id]; if (!n) continue;
      for (const st of n.stats) { if (st.attr === 'pob:gem_power') gp.push(st); else if (st.attr === 'pob:sockets') so.push(st); else if (st.attr === 'pob:ring_slots') rings += st.val; } }
    return { gp, so, rings };
  }
  function gemPowerMult(S, sd, type, info) { info = info || treeSocketInfo(S); let m = 0; for (const st of info.gp) if (eqMatch(st.item, sd, type)) m += st.val; return 1 + m; }
  function extraSockets(S, sd, it) { const info = treeSocketInfo(S); const type = itemTypeOf(sd, it); let n = 0; for (const st of info.so) if (eqMatch(st.item, sd, type)) n += st.val; return n; }

  function rollVal(v, roll) {
    if (v && typeof v === 'object' && 'max' in v) return v.min + (v.max - v.min) * roll;
    return v;
  }
  function itemTypeOf(slot, it) { return (it && it.type) || slot.types[0]; }

  /* ---- collecte de tous les modificateurs ---- */
  function collect(S, A1) {
    const mods = [], texts = [];
    const add = (src, attr, op, val, label) => mods.push({ src, attr: norm(attr), op, val, label });
    const flags = S.flags || {};

    // origines / classe / bénédiction
    const layers = D.origins;
    for (const [lid, pick] of [['origins:origin', S.origin], ['origins-classes:class', S.cls], ['cisco_rpg_origins:divineblessings', S.blessing]]) {
      const o = layers[lid] && layers[lid].origins.find(x => x.id === pick);
      if (!o) continue;
      const seen = new Set();
      for (const p of o.powers) {
        for (const st of p.stats) {
          const k = p.id + st.attr + st.val + st.op;
          if (seen.has(k)) continue; seen.add(k);
          if (!st.cond) { if (st.attr.startsWith('pob:')) mods.push({ src: o.name, attr: st.attr, op: 0, val: st.val, label: p.name }); else add(o.name_fr || o.name, st.attr, st.op, st.val, p.name); }
          else if (st.cond === 'origins:advancement' && flags.dragon) { if (st.attr.startsWith('pob:')) mods.push({ src: o.name, attr: st.attr, op: 0, val: st.val, label: p.name }); else add(o.name_fr || o.name, st.attr, st.op, st.val, p.name + ' (dragon vaincu)'); }
          else if (st.cond === 'origins:on_fire' && (S.assume || {}).burning) { if (st.attr.startsWith('pob:')) mods.push({ src: o.name, attr: st.attr, op: 0, val: st.val, label: p.name + ' (cible en feu)' }); else add(o.name, st.attr, st.op, st.val, p.name); }
          else if ((st.cond === 'origins:exposed_to_sun' || st.cond === 'origins:exposed_to_sky') && (S.assume || {}).sun) { if (st.attr.startsWith('pob:')) mods.push({ src: o.name, attr: st.attr, op: 0, val: st.val, label: p.name + ' (au soleil)' }); else add(o.name, st.attr, st.op, st.val, p.name); }
          else texts.push({ src: o.name, text: p.name + ' — bonus conditionnel : ' + (st.cond || '').split(':')[1] });
        }
      }
    }

    // arbre de talents
    const byId = TREE.byId;
    for (const id of S.nodes || []) {
      const n = byId[id]; if (!n) continue;
      for (const st of n.stats) {
        if (st.attr === 'pob:gem_power' || st.attr === 'pob:sockets' || st.attr === 'pob:ring_slots') continue; // traités à part
        const r = evalStat(st, S, A1);
        if (r.off) { texts.push({ src: 'Arbre : ' + n.name, text: n.effects.join(' ; ') + '  [inactif : ' + r.off + ']' }); continue; }
        if (!r.val) continue;
        mods.push({ src: 'Arbre', attr: st.attr, op: st.op, val: r.val, label: n.name });
      }
      n.effects.forEach((e, i) => { if (!n.calc[i]) texts.push({ src: 'Arbre : ' + n.name, text: e }); });
    }

    // équipement
    const tinfo = treeSocketInfo(S);
    for (const sd of SLOT_DEFS) {
      const it = (S.gear || {})[sd.id]; if (!it) continue;
      if (sd.needs === 'ring' && tinfo.rings < 1) continue;
      const type = itemTypeOf(sd, it);
      const label = it.name || sd.name;
      const b = it.base || {};
      if (sd.weapon && type !== 'bow' && type !== 'crossbow') {
        if (b.dmg != null && b.dmg !== '') add(label, 'minecraft:generic.attack_damage', 0, (+b.dmg) - 1, 'base');
        if (b.spd != null && b.spd !== '') add(label, 'minecraft:generic.attack_speed', 0, (+b.spd) - 4, 'base');
      }
      if (b.armor) add(label, 'minecraft:generic.armor', 0, +b.armor, 'base');
      if (b.tough) add(label, 'minecraft:generic.armor_toughness', 0, +b.tough, 'base');
      if (b.kb) add(label, 'minecraft:generic.knockback_resistance', 0, +b.kb, 'base');
      if (b.fire) add(label, 'apotheosis:fire_damage', 0, +b.fire, 'base');
      for (const m of it.extra || []) add(label, m.attr, m.op, +m.val, 'manuel');
      const rar = it.rarity || 'common';
      for (const af of it.affixes || []) {
        const a = AFFIX[af.id]; if (!a) continue;
        if (a.kind === 'attribute') {
          const v = a.values[rar]; if (v == null) continue;
          add(label, a.attr, a.op, rollVal(v, af.roll ?? 1), a.name);
        } else texts.push({ src: label, text: a.name + ' : ' + ((a.text && a.text[rar]) || '') });
      }
      const gpm = gemPowerMult(S, sd, type, tinfo);
      for (const g of it.gems || []) {
        if (!g || !g.id) continue;
        const gem = GEM[g.id]; if (!gem) continue;
        const gr = g.rar || 'common';
        if (gem.skilltree) {
          for (const bo of gem.bonuses) if (bo.types.includes(type) || bo.types.some(t => t === type)) {
            for (const st of bo.stats || []) add(label + ' ◆' + gem.name, st.attr, st.op, st.val * (st.op === 0 && !/^apotheosis:crit|^skilltree/.test(st.attr) ? gpm : gpm), 'gemme');
            if (!(bo.stats || []).length) texts.push({ src: label + ' ◆' + gem.name, text: bo.text });
          }
          continue;
        }
        for (const bo of gem.bonuses) {
          if (!bo.types.includes(type)) continue;
          if (bo.kind === 'attr' && bo.values[gr] != null) add(label + ' ◆' + gem.name, bo.attr, bo.op, rollVal(bo.values[gr], 1) * gpm, 'gemme');
          else if (bo.kind === 'multi') { for (const m of bo.mods) if (m.values[gr] != null) add(label + ' ◆' + gem.name, m.attr, m.op, rollVal(m.values[gr], 1) * gpm, 'gemme'); }
          else if (bo.text && bo.text[gr]) texts.push({ src: label + ' ◆' + gem.name, text: bo.text[gr] });
        }
      }
    }
    // bonus manuels globaux
    for (const m of S.manual || []) add('Manuel', m.attr, m.op, +m.val, m.note || '');
    return { mods, texts };
  }

  /* ---- évaluation des attributs (formule Minecraft : (base+Σadd)·(1+Σmult_base)·Π(1+mult_total)) ---- */
  function evalAttrs(mods) {
    const groups = {};
    for (const m of mods) { (groups[m.attr] = groups[m.attr] || { a: 0, b: 0, t: 1 }); const g = groups[m.attr];
      if (m.op === 0) g.a += m.val; else if (m.op === 1) g.b += m.val; else g.t *= (1 + m.val); }
    const out = {};
    const ids = new Set([...Object.keys(groups), ...Object.keys(A).map(norm)]);
    for (const id of ids) {
      const info = A[id] || A[id.replace('minecraft:', '')] || { base: 0 };
      const g = groups[id] || { a: 0, b: 0, t: 1 };
      let v = (info.base + g.a) * (1 + g.b) * g.t;
      const c = CAP[id]; if (c) v = Math.min(c[1], Math.max(c[0], v));
      out[id] = v;
    }
    return out;
  }

  const softCap = x => x <= 1.75 ? x : 1 / (-16 * (x - 1.5)) + 2;
  function critExpect(c, d) { // Apotheosis AttributeEvents.apothCriticalStrike
    if (d <= 1 || c <= 0) return 1;
    if (c >= 1) return d * critExpect(c - 1, d * 0.85);
    return c * d + (1 - c);
  }
  const evaChance = e => (e * 0.05) / (1 + e * 0.05) * 0.8; // skilltree AttributeEvents.applyEvasionBonus

  function spellCalc(sp, level, at) {
    const cfg = sp.cfg, school = cfg.school;
    const sAttr = at['irons_spellbooks:spell_power'], scAttr = at['irons_spellbooks:' + school + '_spell_power'] ?? 1;
    const epm = sAttr * scAttr;
    const lvl = Math.min(level, cfg.maxLevel);
    const sp0 = (sp.baseSpellPower + sp.spellPowerPerLevel * (lvl - 1)) * epm * cfg.powerMult;
    const mana = Math.floor((sp.baseMana + sp.manaPerLevel * (lvl - 1)) * cfg.manaMult);
    const cdr = softCap(at['irons_spellbooks:cooldown_reduction']);
    const ctr = at['irons_spellbooks:cast_time_reduction'];
    const cd = Math.floor(cfg.cooldown * 20 * (2 - cdr)) / 20;
    const cast = sp.castType === 'INSTANT' ? 0 : (sp.castType === 'CONTINUOUS' ? sp.castTime * ctr : Math.round(sp.castTime * (2 - softCap(ctr)))) / 20;
    const info = sp.info.map(i => {
      let val = null;
      if (i.expr && !/this\.|Utils|\(0\)/.test(i.expr)) {
        try { val = new Function('sp', 'lvl', 'epm', 'BASE', 'PERLVL', 'min', 'max', 'floor', 'ceil', 'round', 'sqrt', 'pow', 'abs', 'return ' + i.expr)(sp0, lvl, epm * cfg.powerMult, sp.baseSpellPower, sp.spellPowerPerLevel, Math.min, Math.max, Math.floor, Math.ceil, Math.round, Math.sqrt, Math.pow, Math.abs); } catch (e) { val = null; }
      }
      return { label: i.label, unit: i.unit, val };
    });
    return { lvl, sp: sp0, mana, cd, cast, info, powerRaw: epm };
  }

  function compute(S) {
    const pass1 = collect(S, null); const A1 = evalAttrs(pass1.mods);
    const { mods, texts } = collect(S, A1);
    const at = evalAttrs(mods);
    const g = id => at[id] ?? 0;
    const sumPob = k => mods.filter(m => m.attr === k).reduce((a, m) => a + m.val, 0);
    const dmgPct = sumPob('pob:damage_pct') + sumPob('pob:dmg_dealt');
    const projPct = sumPob('pob:proj_dmg');
    const taken = mods.filter(m => m.attr === 'pob:dmg_taken').reduce((a, m) => a * (1 + m.val), 1);
    const R = { at, mods, texts, dmgPct, projPct, taken };
    // défense
    R.hp = g('minecraft:generic.max_health'); R.armor = g('minecraft:generic.armor'); R.tough = g('minecraft:generic.armor_toughness');
    R.armorReduction = (hit) => { const x = Math.min(20, Math.max(R.armor / 5, R.armor - hit / (2 + R.tough / 4))); return x / 25; }; // formule vanilla CombatRules.getDamageAfterAbsorb
    R.evasion = g('skilltree:evasion'); R.evasionChance = evaChance(R.evasion);
    R.blocking = g('skilltree:blocking'); R.blockChance = evaChance(R.blocking);
    R.dodgeAttr = g('apotheosis:dodge_chance');
    R.dodgeTotal = 1 - (1 - R.evasionChance) * (1 - Math.min(1, R.dodgeAttr));
    R.regen = g('skilltree:regeneration');
    // attaque mêlée
    const w = (S.gear || {}).main;
    R.hasWeapon = !!w;
    R.atk = g('minecraft:generic.attack_damage'); R.spd = g('minecraft:generic.attack_speed');
    R.critC = g('apotheosis:crit_chance'); R.critD = g('apotheosis:crit_damage');
    R.fire = g('apotheosis:fire_damage'); R.cold = g('apotheosis:cold_damage');
    R.critE = critExpect(R.critC, R.critD);
    R.hit = (R.atk + R.fire + R.cold) * (1 + dmgPct);
    R.dps = R.hit * R.critE * R.spd * 0.9; // 0.9 ≈ rendement moyen d'attaque rechargée (placeholder)
    R.lifesteal = g('apotheosis:life_steal');
    // armes à distance : dégâts de flèche = base de l'arc × attribut « dégâts des flèches » (Apotheosis)
    const mt = w && (w.type || 'sword');
    R.ranged = mt === 'bow' || mt === 'crossbow';
    R.arrowBase = R.ranged ? (+((w.base || {}).dmg) || 9) : 0;
    R.arrowHit = (R.arrowBase + R.fire + R.cold) * g('apotheosis:arrow_damage') * (1 + dmgPct) * (1 + projPct);
    R.drawSpeed = g('apotheosis:draw_speed');
    R.arrowDps = R.arrowHit * R.critE * R.drawSpeed * (mt === 'crossbow' ? 0.7 : 1);
    if (R.ranged) R.dps = R.arrowDps;
    // magie
    R.mana = g('irons_spellbooks:max_mana'); R.manaRegen = g('irons_spellbooks:mana_regen');
    R.manaPerSec = R.mana * 0.01 * R.manaRegen * 2; // 1 % du max toutes les 10 ticks
    R.spellPower = g('irons_spellbooks:spell_power');
    return R;
  }


  /* ---- simulateur de boss : niveau (AutoLeveling), stats, plafond de dégâts, temps de kill, survie ---- */
  const AL = { hp: 0.09, dmg: 0.14, armor: 0.08 }; // autoleveling-common.toml
  function bossStats(b, dist, wl, which) {
    const lv = b.level || { start: 1, lpd: 0.008, rand: 0 };
    const base = lv.start - 1 + Math.floor(lv.lpd * dist) + (wl || 0);
    const L = base + (which === 'max' ? lv.rand : which === 'min' ? 0 : lv.rand / 2);
    return { L: Math.round(L), hp: b.hp * b.hpm * (1 + AL.hp * L), dmg: b.dmg * b.dmgm * (1 + AL.dmg * L), armor: b.armor * (1 + AL.armor * L), tough: b.tough || 0, cap: b.cap || 0 };
  }
  const armorRed = (armor, tough, hit) => Math.min(20, Math.max(armor / 5, armor - hit / (2 + tough / 4))) / 25;
  function bossSim(R, S, b, dist, wl, which) {
    const st = bossStats(b, dist, wl, which);
    const g = id => R.at[id] ?? 0;
    const eff = Math.max(0, st.armor * (1 - Math.min(1, g('apotheosis:armor_shred'))) - g('apotheosis:armor_pierce'));
    const raw = R.ranged ? R.arrowHit : R.hit;
    const red = armorRed(eff, st.tough, raw);
    let perHit = raw * (1 - red) * R.critE, capped = false;
    if (st.cap && perHit > st.cap) { perHit = st.cap; capped = true; }
    const hps = R.ranged ? R.drawSpeed * (((S.gear || {}).main || {}).type === 'crossbow' ? 0.7 : 1) : R.spd * 0.9;
    const dps = raw > 1 ? perHit * hps : 0;
    // sorts choisis (dégâts magiques : ignorent l'armure)
    let spellBest = null;
    for (const sp of S.spells || []) {
      const d = D.spells.spells.find(x => x.id === sp.id); if (!d) continue;
      const c = spellCalc(d, sp.level, R.at);
      const inf = c.info.find(i => /Dégâts|Dégâts de base|Dégâts d'impact|Dégâts de zone/.test(i.label) && typeof i.val === 'number');
      if (!inf) continue;
      let hit = inf.val; if (st.cap && hit > st.cap) hit = st.cap;
      const t = Math.max(c.cast + 0.05, c.cd, 0.5);
      const dps2 = hit / t;
      if (!spellBest || dps2 > spellBest.dps) spellBest = { name: d.name, dps: dps2, hit, uncapped: inf.val, mana: c.mana, t };
    }
    const best = Math.max(dps, spellBest ? spellBest.dps : 0);
    const ttk = best > 0 ? st.hp / best : Infinity;
    // survie
    const bossHit = st.dmg; const redP = R.armorReduction(bossHit);
    const takes = bossHit * (1 - redP) * R.taken;
    const hitsToDie = takes > 0 ? R.hp / takes : Infinity;
    return Object.assign(st, { raw, red, perHit, capped, dps, spellBest, best, ttk, bossHit, redP, takes, hitsToDie, dodge: R.dodgeTotal, eff });
  }

  window.ENG = { compute, collect, evalAttrs, spellCalc, SLOT_DEFS, extraSockets, treeSocketInfo, gemPowerMult, eqMatch, assume, critExpect, evaChance, softCap, rollVal, norm, CAP, bossSim, bossStats };

  /* index */
  const TREE = { byId: {} }; D.skilltree.nodes.forEach(n => TREE.byId[n.id] = n);
  const AFFIX = {}; D.affixes.forEach(a => AFFIX[a.id] = a);
  const GEM = {}; D.gems.apotheosis.forEach(g => GEM[g.id] = g); D.gems.skilltree.forEach(g => { g.skilltree = true; GEM[g.id] = g; });
  window.IDX = { TREE, AFFIX, GEM };
})();
