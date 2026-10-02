const fs = require('fs'), vm = require('vm');
const ctx = require('./harness');
vm.runInContext(fs.readFileSync(__dirname + '/../site/data/builds.js', 'utf8'), ctx);
const B = ctx.window.BUILDS, { ENG, D } = ctx;
let bad = 0;
for (const b of B) {
  const R = ENG.compute(b.state);
  const nums = ['hp', 'armor', 'hit', 'dps', 'mana', 'spellPower', 'critE'];
  for (const k of nums) if (!isFinite(R[k])) { console.log('NaN', b.id, k, R[k]); bad++; }
  for (const bs of D.bosses) { const r = ENG.bossSim(R, b.state, bs, 2000, 0, 'avg'); if (isNaN(r.ttk) || isNaN(r.hitsToDie)) { console.log('NaN boss', b.id, bs.id, r.ttk, r.hitsToDie); bad++; } }
  for (const sp of b.state.spells) { const d = D.spells.spells.find(x => x.id === sp.id); const c = ENG.spellCalc(d, sp.level, R.at); if (!isFinite(c.sp)) { console.log('NaN spell', b.id, sp.id); bad++; } }
}
console.log(B.length, 'builds,', bad, 'problèmes');
