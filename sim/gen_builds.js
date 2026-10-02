// Génère les builds (milieu de jeu / fin de jeu) -> site/data/builds.js + out/builds_report.json
const fs = require('fs'), path = require('path');
const { optimize, summary, OBJ, ctx } = require('./optimize');
const { D, ENG, IDX } = ctx;
const { P, armorSet } = require('./specs');

const CURIOS = ['ring1', 'ring2', 'neck', 'belt', 'back', 'body', 'hands', 'wrist', 'talis', 'charm'];
const CTYPE = { ring1: 'curios:ring', ring2: 'curios:ring', neck: 'curios:necklace', belt: 'curios:belt', back: 'curios:back', body: 'curios:body', hands: 'curios:hands', wrist: 'curios:bracelet', talis: 'curios:talisman', charm: 'curios:charm', book: 'curios:spellbook', stone: 'curios:spellstone' };
const NAME = { ring1: 'Anneau', ring2: 'Anneau', neck: 'Collier', belt: 'Ceinture', back: 'Cape/Dos', body: 'Corps', hands: 'Gants', wrist: 'Bracelet', talis: 'Talisman', charm: 'Charme', book: 'Livre de sorts', stone: 'Pierre de sort' };
const cur = (extra = []) => { const g = {}; [...CURIOS, ...extra].forEach(s => g[s] = { type: CTYPE[s], name: NAME[s] }); return g; };

const sword = (note, type = 'sword') => P(type, note);
const WEAPON = { // [milieu, fin]
  equi: [P('sword', 'cisco_mod:refined_equillibrium'), P('sword', 'cisco_mod:absolute_equillibrium')],
  fire: [P('sword', 'simplyswords:hearthflame'), P('sword', 'simplyswords:molten_edge')],
  heavy: [P('heavy_weapon', 'simplyswords:brimstone_claymore'), P('heavy_weapon', 'simplyswords:sunfire')],
  rogue: [P('sword', 'cisco_mod:azure_thunder'), P('sword', 'cisco_mod:supreme_nightfall')],
};
const STAFF = { type: 'sword', name: 'Bâton / baguette (arme de mage)', base: { dmg: 4, spd: 1.4 } };
const BOW = { type: 'bow', name: 'Arc (dégâts de flèche de base)', base: { dmg: 9, spd: 1 } };
const SHIELD = { type: 'shield', name: 'Bouclier' };

const ARCH = [
  { id: 'mage-ender', title: 'Mage sombre (Ender)', obj: OBJ.magic('ender'), origin: 'cisco_rpg_origins:hobrosi_dark_mage', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:lokis_blessing', tree: 'enchanter', weapon: STAFF, extra: ['book'], armor: ['brightsteel', 'ascended_hero_violet'], spells: [['magic_missile', 10], ['evasion', 5], ['teleport', 5], ['starfall', 10], ['dragon_breath', 10]], tags: ['Magie', 'Distance', 'Burst continu'], wiki: 'builds/mage-ender.html',
    pitch: 'Le mage le plus « simple » du pack : l\'origine Anthraxi donne +350 % de puissance Ender et un lancer de sort quasi instantané. Missile magique à volonté, téléportation pour esquiver.' },
  { id: 'justicier', title: 'Justicier sacré (Holy)', obj: OBJ.magic('holy'), origin: 'cisco_rpg_origins:rastrayian_justicar', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:chiron_blessing', tree: 'enchanter', weapon: STAFF, extra: ['book'], armor: ['brightsteel', 'ascended_hero_violet'], spells: [['guiding_bolt', 10], ['heal', 10], ['greater_heal', 1], ['healing_circle', 10], ['divine_smite', 5]], tags: ['Magie', 'Soutien', 'Soigneur'], wiki: 'builds/justicier.html',
    pitch: 'Le lanceur de lumière : gros dégâts sacrés (Trait guidant) ET le meilleur soigneur du groupe. Bénédiction de Chiron : triple XP et soins accrus.' },
  { id: 'mage-feu', title: 'Chevalier-mage du Phénix (Feu)', obj: OBJ.magic('fire'), origin: 'cisco_rpg_origins:pyrios_pheonix_knight', cls: 'origins-classes:explorer', blessing: 'cisco_rpg_origins:ras_blessing', tree: 'enchanter', weapon: STAFF, extra: ['book'], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [['flaming_barrage', 5], ['scorch', 10], ['heat_surge', 8], ['firebolt', 10], ['fire_breath', 10]], tags: ['Magie', 'Feu', 'Hybride'], wiki: 'builds/mage-feu.html',
    pitch: 'L\'origine Phénix donne +200 % de puissance du feu et des dégâts de feu au corps à corps, mais fragilise dans les biomes très froids.' },
  { id: 'mage-foudre', title: 'Mage de la tempête (Foudre)', obj: OBJ.magic('lightning'), origin: 'origins:human', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:thors_blessing', tree: 'enchanter', weapon: STAFF, extra: ['book'], armor: ['brightsteel', 'ascended_hero_rouge'], spells: [['ball_lightning', 10], ['shockwave', 8], ['thunderstorm', 8], ['chain_lightning', 10]], tags: ['Magie', 'Foudre', 'Zone'], wiki: 'builds/mage-foudre.html',
    pitch: 'Pas de bonus d\'origine, donc tout vient de l\'équipement et des gemmes ; en échange, les sorts de foudre les mieux dotés du pack (Éclair en boule, Onde de choc, Tempête).' },
  { id: 'necro', title: 'Chaman nécromant (Sang & invocations)', obj: OBJ.summon, origin: 'strictly:orcs_shaman', cls: 'origins-classes:beastmaster', blessing: 'cisco_rpg_origins:babayaga_blessing', tree: 'alchemist', weapon: STAFF, extra: ['book'], armor: ['darksteel_armor', 'ascended_hero_violet'], spells: [['raise_dead', 6], ['wither_skull', 10], ['devour', 10], ['ray_of_siphoning', 10], ['heartstop', 10]], tags: ['Magie', 'Sang', 'Invocations'], wiki: 'builds/necro.html',
    pitch: 'Tu laisses tes morts-vivants se battre. Baba Yaga affaiblit tout ce que tu touches ; la magie de sang te soigne.' },
  { id: 'archer', title: 'Tireur d\'élite (Archer)', obj: OBJ.ranged, origin: 'cisco_rpg_origins:venthari_sharpshooter', cls: 'origins-classes:archer', blessing: 'cisco_rpg_origins:skadis_blessing', tree: 'hunter', weapon: BOW, extra: [], armor: ['gilded_eagle', 'ascended_hero_rouge'], spells: [], tags: ['Distance', 'Précision', 'Critique'], wiki: 'builds/archer.html',
    pitch: '+30 % de dégâts de projectile dès le début, arbre du Chasseur (Sniper : plus tu es loin, plus tu fais mal), et un set Gilded Eagle qui permet d\'esquiver 3 secondes.' },
  { id: 'berserker', title: 'Berserker du Nord (2 mains)', obj: OBJ.melee, origin: 'cisco_rpg_origins:frijani_drengr', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:hephaestus_blessing', tree: 'blacksmith', weapon: 'heavy', extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [], tags: ['Mêlée', 'Vol de vie', 'Critique'], wiki: 'builds/berserker.html',
    pitch: 'Gros critiques, 20 % de vol de vie, mais tu encaisses 30 % de dégâts en plus. Héphaïstos : dégâts en % des PV actuels, idéal contre les boss à des dizaines de milliers de PV.' },
  { id: 'chevalier', title: 'Chevalier gardien (Tank)', obj: OBJ.tank, origin: 'strictly:paladin', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:athenas_blessing', tree: 'blacksmith', weapon: 'equi', shield: true, extra: [], armor: ['darksteel_armor', 'sovereign_ascendant'], spells: [], tags: ['Mêlée', 'Tank', 'Bouclier'], wiki: 'builds/chevalier.html',
    pitch: 'Épée + bouclier, armure maximale et −25 % de dégâts d\'Athéna après le dragon. Celui qui tient la ligne pendant que les autres tapent.' },
  { id: 'assassin', title: 'Lame fantôme (Assassin)', obj: R => Math.pow(R.dps + 1, 0.5) * Math.pow(ehp(R), 0.5), origin: 'origins:feline', cls: 'origins-classes:rogue', blessing: 'cisco_rpg_origins:susanoos_blessing', tree: 'hunter', weapon: 'rogue', extra: [], armor: ['gilded_eagle', 'gilded_eagle'], spells: [], tags: ['Mêlée', 'Esquive', 'Vitesse'], wiki: 'builds/assassin.html',
    pitch: 'Vitesse, esquive et critiques. Susanoo : +15 % de vitesse de déplacement, puis +25 % de vitesse d\'attaque après le dragon.' },
  { id: 'paladin-feu', title: 'Templier du Phénix (Mêlée + Feu)', obj: R => Math.pow(R.dps + 1, 0.45) * Math.pow(ehp(R), 0.3) * Math.pow(R.at['irons_spellbooks:fire_spell_power'], 0.25), origin: 'cisco_rpg_origins:pyrios_pheonix_knight', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:arthurs_blessing', tree: 'blacksmith', weapon: 'fire', extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [['burning_dash', 10], ['firebolt', 10], ['heat_surge', 8]], tags: ['Mêlée', 'Feu', 'Hybride'], wiki: 'builds/paladin-feu.html',
    pitch: 'Épée enflammée : dégâts de feu fixes, bonus de 60 % contre les cibles en feu, et quelques sorts de feu pour engager. Arthur : +60 % contre les morts-vivants.' },
  { id: 'druide', title: 'Druide du marais (Nature)', obj: OBJ.magic('nature'), origin: 'strictly:plantaemancer', cls: 'origins-classes:farmer', blessing: 'cisco_rpg_origins:vishnus_blessing', tree: 'cook', weapon: STAFF, extra: ['book'], armor: ['brightsteel', 'ascended_hero_violet'], spells: [['stomp', 5], ['gluttony', 5], ['oakskin', 8], ['spider_aspect', 8], ['poison_breath', 10]], tags: ['Magie', 'Nature', 'Régénération'], wiki: 'builds/druide.html',
    pitch: 'Un build lent mais très résistant : régénération de Vishnu, Peau de chêne, poison. Idéal pour apprendre la magie sans trop de pression.' },
  { id: 'chasseur-tresors', title: 'Chasseur de trésors (Farm & butin)', obj: R => (1 + R.at['minecraft:generic.luck']) * Math.pow(ehp(R), 0.3) * Math.pow(R.dps + 1, 0.3) * Math.pow(R.at['apotheosis:experience_gained'], 0.5), origin: 'medievalorigins:goblin', cls: 'origins-classes:explorer', blessing: 'cisco_rpg_origins:chiron_blessing', tree: 'hunter', weapon: 'rogue', extra: [], armor: ['gilded_eagle', 'gilded_eagle'], spells: [], tags: ['Utilitaire', 'Farm', 'Butin'], wiki: 'builds/chasseur-tresors.html',
    pitch: 'Pas le plus fort en combat, mais le plus riche : Treasure Hunter (×3 de butin sur 15 % des kills), chance maximale et XP ×3 de Chiron.' },
];

const ehp = R => { const red = R.armorReduction(250); return R.hp / Math.max(0.02, 1 - red) / Math.max(0.1, 1 - Math.min(0.9, R.dodgeTotal)) / R.taken; };

const STAGES = [
  { key: 'milieu', label: 'Milieu de jeu', rarity: 'epic', points: 45, flags: { dragon: false }, wi: 0, ai: 0, affixLimit: 2, socketLimit: 1, roll: 0.4, curios: ['ring1', 'ring2', 'neck'] },
  { key: 'fin', label: 'Fin de jeu', rarity: 'mythic', points: 100, flags: { dragon: true }, wi: 1, ai: 1, affixLimit: 3, socketLimit: 2, roll: 0.6, curios: ['ring1', 'ring2', 'ring3', 'neck', 'belt', 'back', 'charm'] },
];

const out = [], report = [];
for (const a of ARCH) {
  for (const st of STAGES) {
    const w = typeof a.weapon === 'string' ? WEAPON[a.weapon][st.wi] : a.weapon;
    const gear = { main: Object.assign({ rarity: st.rarity }, w) };
    if (a.shield) gear.off = Object.assign({ rarity: st.rarity }, SHIELD);
    const arm = armorSet(a.armor[st.ai]);
    for (const k of Object.keys(arm)) gear[k] = Object.assign({ rarity: st.rarity }, arm[k]);
    const cg = {}; [...st.curios, ...a.extra].forEach(sl => cg[sl] = { type: CTYPE[sl], name: NAME[sl] });
    for (const k of Object.keys(cg)) gear[k] = Object.assign({ rarity: st.rarity, gemRarity: st.rarity }, cg[k]);
    for (const k of Object.keys(gear)) gear[k].gemRarity = st.rarity;
    const spec = { affixLimit: st.affixLimit, socketLimit: st.socketLimit, roll: st.roll, hpFloor: st.key === 'fin' ? 350 : 180, title: a.title + ' — ' + st.label, origin: a.origin, cls: a.cls, blessing: a.blessing, flags: st.flags, points: st.points, objective: a.obj, treeClass: process.env.TREE || a.tree, gear, spells: a.spells.map(([id, l]) => ({ id, level: l })) };
    let S;
    try { S = optimize(spec); } catch (e) { console.error('ERREUR', a.id, st.key, e.message); continue; }
    const R = ENG.compute(S);
    const sm = summary(S);
    // sorts calculés
    const sp = S.spells.map(s => { const d = D.spells.spells.find(x => x.id === s.id); const c = ENG.spellCalc(d, s.level, R.at); return { id: s.id, name: d.name, level: c.lvl, mana: c.mana, cast: +c.cast.toFixed(2), cd: +c.cd.toFixed(1), info: c.info.map(i => [i.label, (typeof i.val === 'number' ? +i.val.toFixed(1) : null), i.unit]) }; });
    // résumé équipement
    const gearRep = Object.entries(S.gear).map(([slot, it]) => ({ slot, name: it.name, type: it.type, rarity: it.rarity, base: it.base,
      affixes: it.affixes.map(x => { const af = IDX.AFFIX[x.id]; return { id: x.id, name: af.name, text: af.text[it.rarity] }; }),
      gems: it.gems.map(g => ({ id: g.id, name: IDX.GEM[g.id].name, rar: g.rar })) }));
    const keystones = S.nodes.map(id => IDX.TREE.byId[id]).filter(n => n.tier === 'keystone' || n.tier === 'notable').map(n => ({ name: n.name, tier: n.tier, effects: n.effects }));
    const entry = { id: a.id + '-' + st.key, title: a.title + ' — ' + st.label, summary: a.pitch, tags: [...a.tags, st.label], wiki: a.wiki, state: S };
    S.name = entry.title;
    out.push(entry);
    report.push({ id: a.id, stage: st.key, title: a.title, label: st.label, summary: sm, spells: sp, gear: gearRep, keystones, nodes: S.nodes, origin: a.origin, cls: a.cls, blessing: a.blessing, tree: a.tree });
    console.log(a.id, st.key, JSON.stringify(sm));
  }
}
fs.writeFileSync(path.join(__dirname, '..', 'site/data/builds.js'), 'window.BUILDS=' + JSON.stringify(out) + ';');
fs.writeFileSync(path.join(__dirname, '..', 'out/builds_report.json'), JSON.stringify(report, null, 1));
console.log('OK', out.length);
