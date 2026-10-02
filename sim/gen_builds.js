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

  // ---------------- variantes et builds supplémentaires ----------------
  { isNew: 1, id: 'mage-glace', title: 'Mage de glace (Boréal)', obj: OBJ.magic('ice'), origin: 'origins:human', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:boreas_blessing', tree: 'enchanter', weapon: STAFF, extra: ['book'], armor: ['brightsteel', 'ascended_hero_violet'], spells: [['icicle', 10], ['cone_of_cold', 10], ['frostwave', 8], ['ray_of_frost', 5], ['ice_block', 6]], tags: ['Magie', 'Glace', 'Contrôle'], wiki: 'builds/mage-glace.html',
    pitch: 'Un mage de contrôle : tes sorts ralentissent et gèlent. Chaque sort de glace a un multiplicateur de ×0,3 dans le pack : il faut un équipement très axé glace pour décoller.' },
  { isNew: 1, id: 'mage-sang', title: 'Mage de sang (Demi-dieu Umbra)', obj: OBJ.magic('blood'), origin: 'cisco_rpg_origins:demi_god_umbra', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:babayaga_blessing', tree: 'alchemist', weapon: STAFF, extra: ['book'], armor: ['darksteel_armor', 'ascended_hero_violet'], spells: [['blood_slash', 5], ['wither_skull', 10], ['ray_of_siphoning', 10], ['acupuncture', 10], ['devour', 10]], tags: ['Magie', 'Sang', 'Vol de vie'], wiki: 'builds/mage-sang.html',
    pitch: 'La magie de sang sans invocations : tu te soignes en frappant. +10 PV et +40 % de dégâts du Demi-dieu Umbra (mais +20 % de dégâts subis).' },
  { isNew: 1, id: 'archer-elfe', title: 'Archer elfe des bois', obj: OBJ.ranged, origin: 'arkwys:woodelf', cls: 'origins-classes:archer', blessing: 'cisco_rpg_origins:skadis_blessing', tree: 'hunter', weapon: BOW, extra: [], armor: ['gilded_eagle', 'ascended_hero_rouge'], spells: [], tags: ['Distance', 'Souple', 'Débutant-friendly'], wiki: 'builds/archer-elfe.html',
    pitch: 'Alternative plus simple à l\'archer Venthari : l\'elfe des bois tire mieux avec tout projectile (mais il est faible en mêlée) et ne subit pas −30 % de PV.' },
  { isNew: 1, id: 'arbalete', title: 'Arbalétrier critique', obj: OBJ.ranged, origin: 'cisco_rpg_origins:venthari_sharpshooter', cls: 'origins-classes:archer', blessing: 'cisco_rpg_origins:ares_blessing', tree: 'hunter', weapon: { type: 'crossbow', name: 'Arbalète (dégâts de flèche de base)', base: { dmg: 11, spd: 1 } }, extra: [], armor: ['gilded_eagle', 'ascended_hero_rouge'], spells: [], tags: ['Distance', 'Critique', 'Burst'], wiki: 'builds/arbalete.html',
    pitch: 'Un coup puissant à chaque rechargement. Arès (+10 % de critique, puis +20 % et +40 % de dégâts critiques) pousse la logique des critiques à fond.' },
  { isNew: 1, id: 'demi-umbra', title: 'Demi-dieu Umbra (Bruiser)', obj: OBJ.melee, origin: 'cisco_rpg_origins:demi_god_umbra', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:thors_blessing', tree: 'blacksmith', weapon: 'equi', extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [], tags: ['Mêlée', 'Dégâts', 'Simple'], wiki: 'builds/demi-umbra.html',
    pitch: '+40 % de dégâts et +10 PV dès le début : un guerrier simple et efficace. En échange tu subis +20 % de dégâts.' },
  { isNew: 1, id: 'demi-lux', title: 'Demi-dieu Lux (Lumière)', obj: OBJ.melee, origin: 'cisco_rpg_origins:demi_god_lux', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:ares_blessing', tree: 'blacksmith', weapon: 'equi', extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [], tags: ['Mêlée', 'Dégâts', 'Anti-Wither'], wiki: 'builds/demi-lux.html',
    pitch: 'Comme l\'Umbra mais immunisé aux ténèbres et au Wither, avec −10 % de dégâts sous le ciel ; Arès apporte les critiques.' },
  { isNew: 1, id: 'duelliste', title: 'Duelliste glacé (Frijani)', obj: R => Math.pow(R.dps + 1, 0.55) * Math.pow(ehp(R), 0.45), origin: 'cisco_rpg_origins:frijani_drengr', cls: 'origins-classes:rogue', blessing: 'cisco_rpg_origins:ares_blessing', tree: 'hunter', weapon: 'rogue', extra: [], armor: ['gilded_eagle', 'gilded_eagle'], spells: [], tags: ['Mêlée', 'Rapide', 'Vol de vie'], wiki: 'builds/duelliste.html',
    pitch: 'Le Frijani à l\'épée rapide : vol de vie, critiques et esquive du set Gilded Eagle. Moins lourd que le berserker à deux mains.' },
  { isNew: 1, id: 'tank-lux', title: 'Rempart de lumière (Tank Lux)', obj: OBJ.tank, origin: 'cisco_rpg_origins:demi_god_lux', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:amaterasus_blessing', tree: 'blacksmith', weapon: 'equi', shield: true, extra: [], armor: ['darksteel_armor', 'sovereign_ascendant'], spells: [], tags: ['Tank', 'Bouclier', 'Vie'], wiki: 'builds/tank-lux.html',
    pitch: 'Un tank qui fait aussi mal : +10 PV de l\'origine, +20 % de vie après le dragon (Amaterasu), −10 % de dégâts sous le ciel.' },
  { isNew: 1, id: 'mage-tank', title: 'Gardien sacré (Mage tank)', obj: R => Math.pow(school(R, 'holy'), 0.4) * Math.pow(ehp(R), 0.6), origin: 'cisco_rpg_origins:rastrayian_justicar', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:vishnus_blessing', tree: 'blacksmith', weapon: STAFF, shield: true, extra: ['book'], armor: ['darksteel_armor', 'sovereign_ascendant'], spells: [['guiding_bolt', 10], ['heal', 10], ['divine_smite', 5], ['healing_circle', 10]], tags: ['Soutien', 'Tank', 'Hybride'], wiki: 'builds/mage-tank.html',
    pitch: 'Le soigneur qu\'on ne peut pas tuer : armure lourde, régénération de Vishnu (−15 % de dégâts reçus après le dragon), et des soins sacrés.' },
  { isNew: 1, id: 'phenix-bouclier', title: 'Rempart du Phénix', obj: R => Math.pow(R.dps + 1, 0.25) * Math.pow(ehp(R), 0.6) * Math.pow(R.at['irons_spellbooks:fire_spell_power'], 0.15), origin: 'cisco_rpg_origins:pyrios_pheonix_knight', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:hephaestus_blessing', tree: 'blacksmith', weapon: 'fire', shield: true, extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [['burning_dash', 10], ['heat_surge', 8]], tags: ['Tank', 'Feu', 'Hybride'], wiki: 'builds/phenix-bouclier.html',
    pitch: 'Un tank offensif : bouclier, épée de feu et Héphaïstos pour entamer les boss. Fragile dans le froid.' },
  { isNew: 1, id: 'berserker-critique', title: 'Berserker critique (Arès)', obj: OBJ.melee, origin: 'cisco_rpg_origins:frijani_drengr', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:ares_blessing', tree: 'blacksmith', weapon: 'heavy', extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [], tags: ['Mêlée', 'Critique', 'Burst'], wiki: 'builds/berserker-critique.html',
    pitch: 'Variante du berserker : Arès à la place d\'Héphaïstos pour miser sur les critiques plutôt que sur les % de PV.' },
  { isNew: 1, id: 'templier-sacre', title: 'Templier sacré (Paladin)', obj: R => Math.pow(R.dps + 1, 0.4) * Math.pow(ehp(R), 0.35) * Math.pow(school(R, 'holy'), 0.25), origin: 'strictly:paladin', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:chiron_blessing', tree: 'blacksmith', weapon: 'equi', shield: true, extra: ['book'], armor: ['darksteel_armor', 'ascended_hero_rouge'], spells: [['divine_smite', 5], ['heal', 10], ['guiding_bolt', 10]], tags: ['Mêlée', 'Soutien', 'Hybride'], wiki: 'builds/templier-sacre.html',
    pitch: 'Épée, bouclier et soins : le polyvalent du groupe. Chiron triple ton XP et renforce les soins.' },
  { isNew: 1, id: 'assassin-voleur', title: 'Voleur de l\'ombre', obj: R => Math.pow(R.dps + 1, 0.5) * Math.pow(ehp(R), 0.5), origin: 'strictly:thief', cls: 'origins-classes:rogue', blessing: 'cisco_rpg_origins:lokis_blessing', tree: 'hunter', weapon: 'rogue', extra: [], armor: ['gilded_eagle', 'gilded_eagle'], spells: [], tags: ['Mêlée', 'Furtivité', 'Loki'], wiki: 'builds/assassin-voleur.html',
    pitch: 'Invisible accroupi (équipement compris), +9 emplacements d\'inventaire, et Loki : poison, portée, invisibilité. Frappe dans le dos.' },
  { isNew: 1, id: 'nain-tank', title: 'Tank Shulk (peau de pierre)', obj: OBJ.tank, origin: 'origins:shulk', cls: 'origins-classes:blacksmith', blessing: 'cisco_rpg_origins:athenas_blessing', tree: 'blacksmith', weapon: 'equi', shield: true, extra: [], armor: ['darksteel_armor', 'sovereign_ascendant'], spells: [], tags: ['Tank', 'Armure', 'Simple'], wiki: 'builds/nain-tank.html',
    pitch: 'Armure naturelle de la carapace du Shulk + 9 emplacements d\'inventaire en plus ; Forgeron pour de petits bonus d\'équipement. Un tank facile à vivre.' },
];

const derive = (base, over) => Object.assign({}, ARCH.find(x => x.id === base), { isNew: 1 }, over);
// ---- Builds supplémentaires (vague 3) : nouvelles origines, nouvelles écoles, nouveaux rôles ----
const heal = R => Math.pow(school(R, 'holy'), 0.5) * Math.pow(1 / (2 - ENG.softCap(R.at['irons_spellbooks:cooldown_reduction'])), 0.2) * Math.pow(R.mana * R.manaRegen, 0.15) * Math.pow(ehp(R), 0.35);
ARCH.push(
  derive('justicier', { id: 'pretre-soigneur', title: 'Prêtre soigneur (Soutien pur)', obj: heal, origin: 'strictly:priest', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:chiron_blessing',
    spells: [['heal', 10], ['greater_heal', 1], ['healing_circle', 10], ['blessing_of_life', 8], ['fortify', 8], ['cloud_of_regeneration', 8]], tags: ['Soutien', 'Soigneur', 'Groupe'], wiki: 'builds/pretre-soigneur.html',
    pitch: 'Le soigneur « pur » : tous les soins du pack, de la bénédiction de vie à la zone de régénération. Il ne tue presque rien, mais personne ne meurt à côté de lui.' }),
  derive('mage-foudre', { id: 'mage-evocateur', title: 'Évocateur des vents (Foudre mobile)', origin: 'origins:elytrian', cls: 'origins-classes:explorer', blessing: 'cisco_rpg_origins:zephyrus_blessing',
    spells: [['lightning_bolt', 10], ['thunder_step', 5], ['electrocute', 10], ['charge', 8], ['gust', 5]], tags: ['Magie', 'Foudre', 'Mobilité'], wiki: 'builds/mage-evocateur.html',
    pitch: 'Un mage de foudre qui ne reste jamais en place : élytres, pas de tonnerre et bourrasques pour frapper puis disparaître. Zéphyr accélère encore le tout.' }),
  derive('mage-ender', { id: 'archimage-elfe', title: 'Archimage haut elfe (Ender)', origin: 'strictly:high_elf', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:ras_blessing',
    spells: [['magic_arrow', 10], ['starfall', 10], ['black_hole', 5], ['counterspell', 3], ['teleport', 5]], tags: ['Magie', 'Ender', 'Contrôle'], wiki: 'builds/archimage-elfe.html',
    pitch: 'Le même moteur Ender que le Mage sombre, mais avec une origine plus « classique » : trou noir pour regrouper, étoiles filantes pour finir, contresort pour couper les incantations.' }),
  derive('mage-feu', { id: 'pyromancien', title: 'Pyromancien Blazeborn (Feu pur)', origin: 'origins:blazeborn', cls: 'origins-classes:explorer', blessing: 'cisco_rpg_origins:ras_blessing',
    spells: [['fireball', 10], ['wall_of_fire', 8], ['blaze_storm', 8], ['magma_bomb', 8], ['fire_breath', 10]], tags: ['Magie', 'Feu', 'Zone'], wiki: 'builds/pyromancien.html',
    pitch: 'Le Nether est ta maison : immunisé au feu, tu peux lancer des sorts de zone en restant au milieu des flammes. Idéal contre les groupes et les boss sensibles au feu.' }),
  derive('druide', { id: 'empoisonneur', title: 'Empoisonneur des ombres (Nature)', obj: OBJ.magic('nature'), origin: 'origins:arachnid', cls: 'origins-classes:cleric', blessing: 'cisco_rpg_origins:lokis_blessing', tree: 'alchemist',
    spells: [['poison_arrow', 10], ['acid_orb', 8], ['blight', 8], ['poison_splash', 8], ['root', 5]], tags: ['Magie', 'Poison', 'Dégâts sur la durée'], wiki: 'builds/empoisonneur.html',
    pitch: 'Dégâts sur la durée : tu empoisonnes, tu immobilises (Racines) et tu laisses le temps travailler. Loki donne ensuite +40 % de dégâts aux empoisonnés.' }),
  derive('archer-elfe', { id: 'archer-braise', title: 'Archer de braise (Feu)', origin: 'origins:blazeborn', cls: 'origins-classes:archer', blessing: 'cisco_rpg_origins:ras_blessing',
    tags: ['Distance', 'Feu', 'Zone'], wiki: 'builds/archer-braise.html',
    pitch: 'Des flèches enflammées : les dégâts de feu fixes s\'ajoutent à chaque tir et brûlent les cibles. Ra donne la puissance du feu, le Blazeborn ne craint pas ses propres flammes.' }),
  derive('archer-elfe', { id: 'archer-fantome', title: 'Archer fantôme (Mobilité)', origin: 'origins:phantom', cls: 'origins-classes:archer', blessing: 'cisco_rpg_origins:zephyrus_blessing',
    tags: ['Distance', 'Mobilité', 'Esquive'], wiki: 'builds/archer-fantome.html',
    pitch: 'Un tireur qui passe en forme fantôme pour traverser les murs et tirer là où personne ne l\'attend. Zéphyr ajoute de la vitesse pour garder ses distances.' }),
  derive('chasseur-tresors', { id: 'maitre-betes', title: 'Maître des bêtes (Compagnons)', obj: R => Math.pow(R.dps + 1, 0.45) * Math.pow(ehp(R), 0.45) * Math.pow(R.at['irons_spellbooks:summon_damage'] || 1, 0.1), origin: 'strictly:wildcat', cls: 'origins-classes:beastmaster', blessing: 'cisco_rpg_origins:skadis_blessing', tree: 'hunter',
    spells: [['fang_strike', 10], ['fang_ward', 8], ['summon_polar_bear', 8], ['summon_horse', 3]], tags: ['Mêlée', 'Invocation', 'Compagnons'], wiki: 'builds/maitre-betes.html',
    pitch: 'Un chasseur qui combat avec ses bêtes : ours polaire, crocs, monture. Tu tapes à côté d\'eux et ils absorbent une partie de l\'attention du boss.' }),
  derive('berserker', { id: 'brute-bastion', title: 'Brute des bastions (Piglin)', origin: 'arkwys:piglinbrute', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:hephaestus_blessing',
    tags: ['Mêlée', 'Force brute', 'Simple'], wiki: 'builds/brute-bastion.html',
    pitch: 'La force brute à l\'état pur : grosse hache, gros dégâts, aucune subtilité. Héphaïstos transforme chaque coup en pourcentage des PV du boss.' }),
  derive('assassin', { id: 'rodeur-ender', title: 'Rôdeur d\'Ender (Téléportation)', origin: 'origins:enderian', cls: 'origins-classes:rogue', blessing: 'cisco_rpg_origins:lokis_blessing',
    spells: [['teleport', 5], ['evasion', 5], ['echoing_strikes', 8]], tags: ['Mêlée', 'Téléportation', 'Burst'], wiki: 'builds/rodeur-ender.html',
    pitch: 'Frappe, téléporte-toi, recommence : l\'Enderian se téléporte naturellement, les sorts Ender (Téléportation, Évasion, Frappes en écho) font le reste. Craint l\'eau.' }),
  derive('templier-sacre', { id: 'frappeur-foudre', title: 'Frappeur de foudre (Mêlée + Foudre)', obj: R => Math.pow(R.dps + 1, 0.45) * Math.pow(ehp(R), 0.3) * Math.pow(R.at['irons_spellbooks:lightning_spell_power'], 0.25), origin: 'origins:human', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:thors_blessing',
    spells: [['charge', 8], ['thunder_step', 5], ['electrocute', 10], ['lightning_bolt', 10]], tags: ['Mêlée', 'Foudre', 'Hybride'], wiki: 'builds/frappeur-foudre.html',
    pitch: 'Épée au poing et foudre dans les veines : Thor renforce les dégâts de foudre, la Charge engage, le Pas du tonnerre repositionne. Un guerrier-mage plus sûr qu\'un mage pur.' }),
  derive('chevalier', { id: 'forteresse-ardente', title: 'Forteresse ardente (Tank Blazeborn)', origin: 'origins:blazeborn', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:hephaestus_blessing', weapon: 'fire',
    spells: [['burning_dash', 10], ['wall_of_fire', 6]], tags: ['Tank', 'Feu', 'Brûlure'], wiki: 'builds/forteresse-ardente.html',
    pitch: 'Un tank qui fait mal : immunisé au feu, il avance dans le Nether sans broncher, brûle ses ennemis et protège le groupe derrière un mur de flammes.' }),
  derive('chevalier', { id: 'garde-royal', title: 'Garde royal (Tank chevalier)', origin: 'strictly:knight', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:arthurs_blessing',
    tags: ['Tank', 'Bouclier', 'Anti-mort-vivant'], wiki: 'builds/garde-royal.html',
    pitch: 'Le chevalier de la légende : armure lourde, bouclier et l\'Excalibur d\'Arthur (+60 % contre les morts-vivants). Le tank idéal pour les donjons de squelettes et de Wither.' }),
  derive('chasseur-tresors', { id: 'mineur-blinde', title: 'Mineur blindé (Farm de ressources)', obj: R => (1 + R.at['minecraft:generic.luck']) * Math.pow(ehp(R), 0.4) * Math.pow(R.dps + 1, 0.2), origin: 'origins:shulk', cls: 'origins-classes:miner', blessing: 'cisco_rpg_origins:athenas_blessing', tree: 'miner', weapon: 'equi',
    tags: ['Utilitaire', 'Mineur', 'Gemmes'], wiki: 'builds/mineur-blinde.html',
    pitch: 'Une carapace de Shulk, la voie du Mineur (gemmes Cullinan et Estrela de Fura) et une chance maximale : celui qui descend chercher les ressources pour tout le monde.' }),
  derive('druide', { id: 'chef-de-guerre', title: 'Chef de guerre (Cuisinier-combattant)', obj: R => Math.pow(R.dps + 1, 0.35) * Math.pow(ehp(R), 0.55) * Math.pow(1 + (R.regen || 0), 0.1), origin: 'origins:human', cls: 'origins-classes:cook', blessing: 'cisco_rpg_origins:vishnus_blessing', tree: 'cook', weapon: 'equi', shield: true, extra: [], armor: ['darksteel_armor', 'ascended_hero_rouge'],
    spells: [], tags: ['Hybride', 'Nourriture', 'Régénération'], wiki: 'builds/chef-de-guerre.html',
    pitch: 'Un guerrier qui se bat avec ce qu\'il a cuisiné : voie du Cuisinier, régénération de Vishnu, repas buffs. Il récupère entre deux combats sans consommer de potions.' }),
);

// ---- Builds « Anneau des Sept Malédictions » : mêmes bases que des builds existants, avec l'anneau équipé (assume.cursed) ----
const deriveC = (base, over) => derive(base, Object.assign({ cursed: true }, over));
ARCH.push(
  deriveC('nain-tank', { id: 'maudit-tank', title: 'Le Maudit (Tank)', tags: ['Anneau maudit', 'Tank', 'Armure'], wiki: 'builds/maudit-tank.html',
    pitch: 'Le moyen le plus sûr de porter l\'Anneau des Sept Malédictions : énormes PV et armure de Shulk pour absorber les dégâts doublés, bouclier pour parer ce qui reste.' }),
  deriveC('duelliste', { id: 'maudit-esquive', title: 'Spectre maudit (Esquive)', tags: ['Anneau maudit', 'Esquive', 'Vol de vie'], wiki: 'builds/maudit-esquive.html',
    pitch: 'Ne pas se faire toucher du tout : esquive et vol de vie pour ignorer la malédiction de douleur, épée rapide pour compenser les dégâts réduits de moitié.' }),
  deriveC('mage-sang', { id: 'maudit-mage', title: 'Mage maudit (Sang)', tags: ['Anneau maudit', 'Magie', 'Vol de vie'], wiki: 'builds/maudit-mage.html',
    pitch: 'Sorts de sang et siphon de vie : la magie ignore l\'armure réduite, et chaque sort soigne. Le plein de loot et d\'XP du Ring, sans prendre de coups.' }),
  deriveC('archer-elfe', { id: 'maudit-archer', title: 'Archer maudit (Distance)', tags: ['Anneau maudit', 'Distance', 'Débutant-friendly'], wiki: 'builds/maudit-archer.html',
    pitch: 'Tout ce qui est neutre vous attaque quand vous portez l\'Anneau : tirer de loin est la meilleure façon d\'en profiter sans en subir les inconvénients.' }),
  deriveC('berserker-critique', { id: 'maudit-berserker', title: 'Berserker maudit (Critique)', tags: ['Anneau maudit', 'Mêlée', 'Critique'], wiki: 'builds/maudit-berserker.html',
    pitch: 'Le pari fou : gros critiques à deux mains pour faire oublier les −50 % de dégâts, et un Emblème du Berserker qui récompense les PV bas.' }),
);

const school = (R, s) => R.at['irons_spellbooks:' + s + '_spell_power'] * R.spellPower;
const ehp = R => { const red = R.armorReduction(250); return R.hp / Math.max(0.02, 1 - red) / Math.max(0.1, 1 - Math.min(0.9, R.dodgeTotal)) / R.taken; };

const ALLC = ['ring1', 'ring2', 'ring3', 'neck', 'belt', 'back', 'body', 'hands', 'wrist', 'talis', 'charm'];
const STAGES = [
  { key: 'debutant', label: 'Débutant', rarity: 'rare', points: 15, flags: { dragon: false }, wi: -1, ai: -1, affixLimit: 1, socketLimit: 1, roll: 0.3, curios: ['ring1', 'ring2'], fixedTree: true, hp: 120 },
  { key: 'milieu', label: 'Intermédiaire', rarity: 'epic', points: 45, flags: { dragon: false }, wi: 0, ai: 0, affixLimit: 2, socketLimit: 1, roll: 0.4, curios: ['ring1', 'ring2', 'neck'], hp: 180 },
  { key: 'fin', label: 'Avancé', rarity: 'mythic', points: 100, flags: { dragon: true }, wi: 1, ai: 1, affixLimit: 3, socketLimit: 2, roll: 0.6, curios: ['ring1', 'ring2', 'ring3', 'neck', 'belt', 'back', 'charm'], hp: 350 },
  { key: 'optimise', label: 'Optimisé (BiS)', rarity: 'mythic', points: 115, flags: { dragon: true }, wi: 1, ai: 1, affixLimit: 4, socketLimit: 3, roll: 0.9, curios: ALLC, hp: 400 },
];
const EARLY = { sword: P('sword', 'Épée en fer'), heavy_weapon: P('heavy_weapon', 'Hache en fer') };
const ONLY_STAGES = process.env.STAGES ? process.env.STAGES.split(',') : null, ONLY_ARCH = process.env.ARCHS ? process.env.ARCHS.split(',') : null;
const prevB = fs.existsSync(__dirname + '/../site/data/builds.js') ? JSON.parse(fs.readFileSync(__dirname + '/../site/data/builds.js', 'utf8').replace('window.BUILDS=', '').replace(/;$/, '')) : [];
const prevR = fs.existsSync(__dirname + '/../out/builds_report.json') ? JSON.parse(fs.readFileSync(__dirname + '/../out/builds_report.json', 'utf8')) : [];
const out = [], report = [];
for (const a of ARCH) {
  if (ONLY_ARCH && !ONLY_ARCH.includes(a.id) && !(ONLY_ARCH.includes('new') && a.isNew)) continue;
  for (const st of STAGES) {
    if (ONLY_STAGES && !ONLY_STAGES.includes(st.key)) continue;
    let w = typeof a.weapon === 'string' ? (st.wi < 0 ? (a.weapon === 'heavy' ? EARLY.heavy_weapon : EARLY.sword) : WEAPON[a.weapon][st.wi]) : a.weapon;
    const gear = { main: Object.assign({ rarity: st.rarity }, w) };
    if (a.shield) gear.off = Object.assign({ rarity: st.rarity }, SHIELD);
    const arm = armorSet(st.ai < 0 ? 'adventurer' : a.armor[st.ai]);
    for (const k of Object.keys(arm)) gear[k] = Object.assign({ rarity: st.rarity }, arm[k]);
    const cg = {}; [...st.curios, ...a.extra].forEach(sl => cg[sl] = { type: CTYPE[sl], name: NAME[sl] });
    for (const k of Object.keys(cg)) gear[k] = Object.assign({ rarity: st.rarity, gemRarity: st.rarity }, cg[k]);
    for (const k of Object.keys(gear)) gear[k].gemRarity = st.rarity;
    const spec = { affixLimit: st.affixLimit, socketLimit: st.socketLimit, roll: st.roll, hpFloor: st.hp, title: a.title + ' — ' + st.label, origin: a.origin, cls: a.cls, blessing: a.blessing, flags: st.flags, points: st.points, objective: a.obj, treeClass: st.fixedTree ? a.tree : (process.env.TREE || a.tree), gear, spells: a.spells.map(([id, l]) => ({ id, level: l })) };
    if (a.cursed) { spec.assume = { cursed: true }; spec.gear.ring1 = { type: 'curios:ring', name: 'Anneau des Sept Malédictions', rarity: 'epic', fixedAffixes: [], sockets: 0 }; }
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
    const entry = { id: a.id + '-' + st.key, title: a.title + ' — ' + st.label, summary: a.pitch, tags: [...a.tags, st.label], wiki: a.wiki, arch: a.id, level: st.key, state: S };
    S.name = entry.title;
    out.push(entry);
    report.push({ id: a.id, stage: st.key, title: a.title, label: st.label, summary: sm, spells: sp, gear: gearRep, keystones, nodes: S.nodes, origin: a.origin, cls: a.cls, blessing: a.blessing, tree: a.tree });
    console.log(a.id, st.key, JSON.stringify(sm));
  }
}
const key = x => (x.arch || x.id.replace(/-(milieu|fin|debutant|optimise)$/, '')) + '|' + (x.level || x.stage || x.id.split('-').pop());
const fixOld = x => { if (!x.arch) { x.arch = x.id.replace(/-(milieu|fin)$/, ''); x.level = x.id.endsWith('-fin') ? 'fin' : 'milieu'; x.title = x.title.replace('Milieu de jeu', 'Intermédiaire').replace('Fin de jeu', 'Avancé'); x.tags = x.tags.map(t => t === 'Milieu de jeu' ? 'Intermédiaire' : t === 'Fin de jeu' ? 'Avancé' : t); } return x; };
const mergedB = [...prevB.map(fixOld).filter(x => !out.find(o => key(o) === key(x))), ...out];
const mergedR = [...prevR.map(x => (x.label === 'Milieu de jeu' ? Object.assign(x, { label: 'Intermédiaire' }) : x.label === 'Fin de jeu' ? Object.assign(x, { label: 'Avancé' }) : x)).filter(x => !report.find(o => o.id === x.id && o.stage === x.stage)), ...report];
fs.writeFileSync(path.join(__dirname, '..', 'site/data/builds.js'), 'window.BUILDS=' + JSON.stringify(mergedB) + ';');
fs.writeFileSync(path.join(__dirname, '..', 'out/builds_report.json'), JSON.stringify(mergedR, null, 1));
console.log('OK', out.length, 'nouveaux ;', mergedB.length, 'total');
