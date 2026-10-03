// Variante « loot réaliste » de chaque build : affixes à 50 % de leur plage au plus (médiane) et gemmes d'une rareté en dessous.
// Ajoute `real` (résumé chiffré) à chaque build de site/data/builds.js et de out/builds_report.json. Pas de réoptimisation : on recalcule seulement.
const fs = require('fs'), path = require('path');
const { summary, ctx } = require('./optimize');
const ORDER = ctx.D.rarityOrder;
const bp = path.join(__dirname, '..', 'site/data/builds.js'), rp = path.join(__dirname, '..', 'out/builds_report.json');
const B = JSON.parse(fs.readFileSync(bp, 'utf8').replace('window.BUILDS=', '').replace(/;$/, ''));
const Rp = JSON.parse(fs.readFileSync(rp, 'utf8'));
const down = r => ORDER[Math.max(0, ORDER.indexOf(r) - 1)] || r;
function realistic(S) {
  const T = JSON.parse(JSON.stringify(S));
  for (const it of Object.values(T.gear || {})) {
    (it.affixes || []).forEach(a => { a.roll = Math.min(a.roll ?? 0.8, 0.5); });
    (it.gems || []).forEach(g => { if (g.id) g.rar = down(g.rar); });
  }
  return T;
}
const pick = s => ({ hp: s.hp, armor: s.armor, dodge: s.dodge, hit: s.hit, dps: s.dps, arrow: s.arrow, mana: s.mana, critC: s.critC, critD: s.critD });
let n = 0;
for (const b of B) {
  const real = pick(summary(realistic(b.state))); b.real = real; n++;
  const r = Rp.find(x => x.id === (b.arch || b.id) && x.stage === b.level); if (r) r.real = real;
}
fs.writeFileSync(bp, 'window.BUILDS=' + JSON.stringify(B) + ';');
fs.writeFileSync(rp, JSON.stringify(Rp, null, 1));
console.log(n, 'builds : variante réaliste calculée');
