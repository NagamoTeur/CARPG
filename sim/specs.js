// Définition des builds (archétypes). L'optimiseur remplit talents / affixes / gemmes.
const { ctx } = require('./optimize');
const { D } = ctx;
const P = (type, name) => { const p = (D.presets[type] || []).find(x => x.note === name || x.name.startsWith(name)); if (!p) throw new Error('preset ' + name); return { type, name: p.name.replace(/ \[.*\]/, ''), base: p.base }; };
const armorSet = (key) => ({
  head: P('helmet', 'cisco_mod:' + key + '_helmet'), chest: P('chestplate', 'cisco_mod:' + key + '_chestplate'),
  legs: P('leggings', 'cisco_mod:' + key + '_leggings'), boots: P('boots', 'cisco_mod:' + key + '_boots'),
});
module.exports = { P, armorSet };
