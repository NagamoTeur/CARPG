// Charge data.js + engine.js dans Node pour simuler/optimiser des builds hors navigateur
const fs = require('fs'), vm = require('vm'), path = require('path');
const root = path.join(__dirname, '..', 'site');
const ctx = { console, Math, JSON, Object, Array, Set, Map, Number, String, isFinite, parseFloat, Function };
ctx.window = ctx;
vm.createContext(ctx);
for (const f of ['data/data.js', 'data/presets.js']) vm.runInContext(fs.readFileSync(path.join(root, f), 'utf8'), ctx);
vm.runInContext('var D = window.D;', ctx);
vm.runInContext(fs.readFileSync(path.join(root, 'pob/engine.js'), 'utf8'), ctx);
module.exports = ctx;
