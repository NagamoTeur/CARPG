const ctx = require('./harness');
const S = { origin: 'origins:human', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:ares_blessing', flags: { dragon: true }, nodes: [], gear: {}, spells: [] };
const R = ctx.ENG.compute(S);
console.log(R.hp, R.critC, R.critD, R.mods.length);
