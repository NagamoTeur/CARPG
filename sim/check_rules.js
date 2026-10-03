// Vérifie que tous les builds respectent les restrictions d'équipement de leur origine
const fs = require('fs'), path = require('path'), { ENG } = require('./optimize').ctx;
const B = JSON.parse(fs.readFileSync(path.join(__dirname, '..', 'site/data/builds.js'), 'utf8').replace('window.BUILDS=', '').replace(/;$/, ''));
let bad = 0;
for (const b of B) { const w = ENG.restrictions(b.state); if (w.length) { bad++; console.log(b.id, w.join(' | ')); } }
console.log(B.length, 'builds,', bad, 'violations');
process.exit(bad ? 1 : 0);
