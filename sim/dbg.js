const { optimize, summary, OBJ, ctx } = require('./optimize');
const { D, ENG, IDX } = ctx;
const spec = JSON.parse(require('fs').readFileSync('site/data/builds.js','utf8').replace('window.BUILDS=','').replace(/;$/,''))[1];
const R = ENG.compute(spec.state);
const id='irons_spellbooks:ender_spell_power';
console.log(R.at[id]);
for (const m of R.mods.filter(m=>m.attr===id)) console.log(m.src, m.op, m.val, m.label);
