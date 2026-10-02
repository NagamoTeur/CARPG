const { ENG, D } = require('./harness');
const ok = (n, a, b, eps = 1e-6) => { const good = Math.abs(a - b) < eps; console.log((good ? 'OK  ' : 'FAIL') + ' ' + n, a, b); if (!good) process.exitCode = 1; };
ok('evasion 10 pts', ENG.evaChance(10), 0.8 * 0.5 / 1.5);
ok('crit 35%/1.9', ENG.critExpect(0.35, 1.9), 0.35 * 1.9 + 0.65);
ok('crit 150%/2', ENG.critExpect(1.5, 2), 2 * (0.5 * 1.7 + 0.5));
const mods = [{ attr: 'minecraft:generic.max_health', op: 0, val: 10 }, { attr: 'minecraft:generic.max_health', op: 1, val: 0.5 }, { attr: 'minecraft:generic.max_health', op: 2, val: 0.1 }, { attr: 'minecraft:generic.max_health', op: 2, val: 0.2 }];
ok('attr formula', ENG.evalAttrs(mods)['minecraft:generic.max_health'], (20 + 10) * 1.5 * 1.1 * 1.2);
const S = { origin: 'origins:human', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:no_blessing', flags: {}, nodes: [], gear: { chest: { type: 'chestplate', rarity: 'epic', base: { armor: 100, tough: 28 }, affixes: [], gems: [] } }, spells: [] };
const R = ENG.compute(S);
ok('armor 100 vs 250', R.armorReduction(250), 20 / 25);
ok('armor 20 vs 50', (() => { const x = ENG.compute({ ...S, gear: { chest: { type: 'chestplate', rarity: 'epic', base: { armor: 20 }, affixes: [], gems: [] } } }); return x.armorReduction(50); })(), 4 / 25);
const sp = D.spells.spells.find(s => s.id === 'magic_missile'); const c = ENG.spellCalc(sp, 10, { 'irons_spellbooks:spell_power': 2, 'irons_spellbooks:ender_spell_power': 3, 'irons_spellbooks:cooldown_reduction': 1, 'irons_spellbooks:cast_time_reduction': 1 });
ok('spell power', c.sp, (sp.baseSpellPower + sp.spellPowerPerLevel * 9) * 6 * sp.cfg.powerMult);
const b = D.bosses.find(x => x.id === 'cataclysm:ignis'); const bs = ENG.bossStats(b, 1000, 0, 'min');
ok('boss level @1000 blocks (min)', bs.L, 30 - 1 + 20);
ok('boss hp', bs.hp, 450 * 1.5 * (1 + 0.09 * 49));
// arbre : allocation d'un chemin et totaux
const T = { ...S, nodes: ['hunter_class', 'hunter_defensive_1', 'hunter_defensive_2'], gear: {} };
const RT = ENG.compute(T); console.log('tree evasion', RT.evasion, 'armor', RT.armor);
