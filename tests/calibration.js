// Calibrage : valeurs relevées EN JEU (captures du 3 octobre 2026, dossier calibrage/) comparées au moteur.
// Personnage : Anthraxi Dark Mage / Clerc / Loki, armure Adventurer rare (casque, plastron, jambières, bottes), anneau, sans arme en main.
const { ctx } = require('../sim/optimize'); const { ENG, D } = ctx;
let ok = 0; const fail = [];
const near = (name, got, want, tol = 1e-6) => { if (Math.abs(got - want) <= tol * Math.max(1, Math.abs(want))) ok++; else fail.push(`${name} : moteur ${got} / jeu ${want}`); };
const A = (attr, val, op = 0) => ({ attr, op, val });
// 1) attributs finaux du personnage (commande /attribute ... get)
const S = { v: 1, origin: 'cisco_rpg_origins:hobrosi_dark_mage', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:lokis_blessing', flags: { dragon: false }, points: 0, nodes: [], gear: {}, spells: [], assume: {},
  manual: [A('minecraft:generic.armor', 5 + 7 + 6 + 5), A('minecraft:generic.armor_toughness', 5 + 5 + 5 + 5 - 3), A('minecraft:generic.movement_speed', 0.2, 1), A('irons_spellbooks:cooldown_reduction', 0.15, 1), A('minecraft:generic.max_health', 5 + 3 + 5 + 4 + 5)] };
S.manual[1] = A('minecraft:generic.armor_toughness', 4 + 4 + 4 + 5); // 4 par pièce d'Adventurer, 5 aux bottes (4 de base + 1 d'affixe)
const R = ENG.compute(S);
near('armure', R.armor, 23); near('robustesse', R.tough, 17); near('vitesse de déplacement', R.at['minecraft:generic.movement_speed'], 0.12, 1e-6);
near('mana max (×3 Anthraxi)', R.mana, 300); near('régénération de mana', R.at['irons_spellbooks:mana_regen'], 1); near('réduction de recharge', R.at['irons_spellbooks:cooldown_reduction'], 1.15);
near('puissance de sorts globale', R.spellPower, 1); near('chance de critique de base', R.critC, 0.05); near('dégâts critiques de base', R.critD, 1.5);
// vie : (20 + 22 de pièces + X) × 0,6 (Profane Sacrifice) = 41,4 => X = 27 (nourriture / talents non fournis) : on vérifie la formule
const hpNoOther = R.hp; near('vie : (20 + 22) × 0,6', hpNoOther, 25.2);
S.manual.push(A('minecraft:generic.max_health', 27)); near('vie avec +27 (nourriture/talents)', ENG.compute(S).hp, 41.4, 1e-9);
// 2) sorts : valeurs des parchemins (puissance 1, sans réduction de recharge)
const base = ENG.compute({ v: 1, origin: 'origins:human', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:no_blessing', flags: {}, points: 0, nodes: [], gear: {}, spells: [], manual: [], assume: {} });
const spell = (id, lvl) => ENG.spellCalc(D.spells.spells.find(s => s.id === id), lvl, base.at);
const ev = spell('evasion', 5); near('Évasion 5 : mana', ev.mana, 120); near('Évasion 5 : recharge (s)', ev.cd, 180); near('Évasion 5 : coups esquivés', ev.info[0].val, 1);
const tp = spell('teleport', 5); near('Téléportation 5 : mana', tp.mana, 28); near('Téléportation 5 : recharge', tp.cd, 3); near('Téléportation 5 : portée', tp.info[0].val, 0.4, 1e-9);
const sf = spell('starfall', 10); near('Chute d\'étoiles 10 : mana', sf.mana, 14); near('Chute d\'étoiles 10 : recharge', sf.cd, 16); near('Chute d\'étoiles 10 : dégâts', sf.info[0].val, 2.55, 1e-3); near('Chute d\'étoiles 10 : rayon', sf.info[1].val, 6);
const db = spell('dragon_breath', 10); near('Souffle du dragon 10 : mana', db.mana, 14); near('Souffle du dragon 10 : recharge', db.cd, 12); near('Souffle du dragon 10 : dégâts', db.info[0].val, 4, 1e-9);
// 3) les plages d'affixes lues en jeu entrent dans les données (rareté Rare) : [stat, valeur lue]
const AFF = Object.fromEntries(D.affixes.filter(a => a.kind === 'attribute').map(a => [a.id, a]));
const inRange = (attr, type, v, rar = 'rare') => { const L = D.affixes.filter(x => x.kind === 'attribute' && x.attr === attr && x.types.includes(type) && x.values[rar]); if (L.some(a => v >= a.values[rar].min - 1e-9 && v <= a.values[rar].max + 1e-9)) ok++; else fail.push(`plage ${attr}/${type}/${rar} : ${v} hors de ${JSON.stringify(L.map(a => a.values[rar]))}`); };
[['irons_spellbooks:cooldown_reduction', 'helmet', 0.15], ['minecraft:generic.max_health', 'helmet', 5], ['minecraft:generic.luck', 'helmet', 1], ['apotheosis:healing_received', 'chestplate', 0.2], ['minecraft:generic.max_health', 'chestplate', 3],
 ['ars_nouveau:ars_nouveau.perk.warding', 'leggings', 0.045], ['apotheosis:healing_received', 'leggings', 0.15], ['minecraft:generic.movement_speed', 'boots', 0.2], ['minecraft:generic.armor_toughness', 'boots', 1],
 ['apotheosis:draw_speed', 'curios:ring', 0.38], ['ars_nouveau:ars_nouveau.perk.flat_max_mana', 'curios:ring', 45]].forEach(x => inRange(...x));
// bases de l'armure d'aventurier
const adv = n => D.items.find(i => i.id === 'cisco_mod:adventurer_' + n);
[['helmet', 5], ['chestplate', 7], ['leggings', 6], ['boots', 5]].forEach(([n, v]) => near('Adventurer ' + n + ' : armure de base', adv(n).armor, v));
console.log(`${ok} vérifications réussies, ${fail.length} écart(s)`); fail.forEach(f => console.log('  ✘', f)); process.exit(fail.length ? 1 : 0);
