window.BUILD_CATS={
"cats": [
{
"id": "mages",
"title": "Mages offensifs",
"icon": "🔮",
"desc": "Tu fais tes dégâts avec des sorts, un par école de magie. Faible défense, forte puissance : à jouer derrière quelqu'un qui tient la ligne.",
"adapt": [
"**Change d'école, garde la structure** : tous ces builds partagent la même base (classe de talents Enchanteur, bâton + livre de sorts, armure légère). Pour passer de feu à glace, change l'origine/bénédiction et les 5 sorts, le reste de l'équipement reste valable.",
"**Priorité d'équipement** : puissance de l'école > mana max > régénération de mana > réduction de recharge > PV. Si ton loot n'a pas la bonne école, une pièce « puissance de sorts » générale reste toujours utile.",
"**Trop fragile ?** Remplace un sort d'attaque par un sort de défense (téléportation, bouclier, soins) et ajoute des PV avant de monter la puissance.",
"**Manque de mana ?** Ajoute un affixe de régénération de mana avant un affixe de puissance : un sort puissant que tu ne peux pas lancer ne sert à rien."
],
"builds": [
"mage-ender",
"mage-feu",
"mage-foudre",
"mage-glace",
"mage-sang",
"mage-evocateur",
"archimage-elfe",
"pyromancien",
"empoisonneur"
]
},
{
"id": "soutien",
"title": "Soutien & invocation",
"icon": "✨",
"desc": "Soigner, invoquer, résister : ces builds font tenir le groupe plutôt que de tout tuer eux-mêmes.",
"adapt": [
"**Rôle avant puissance** : un soigneur doit survivre et lancer ses soins en continu. Priorité : mana, réduction de recharge, puissance de soins/de l'école, puis PV.",
"**Pas de soigneur dans le groupe ?** Prends le Justicier ou le Gardien sacré en priorité : un seul suffit pour la plupart des boss.",
"**Invocations** : la puissance d'invocation compte plus que la puissance de sort. Garde tes serviteurs devant, reste derrière.",
"**Variante solo** : remplace un sort de soutien par un sort d'attaque de l'école principale."
],
"builds": [
"justicier",
"necro",
"druide",
"mage-tank",
"pretre-soigneur"
]
},
{
"id": "distance",
"title": "Distance (arcs et arbalètes)",
"icon": "🏹",
"desc": "Dégâts physiques à distance. Pas de mana à gérer, mais il faut rester mobile et garder des munitions.",
"adapt": [
"**Arc ou arbalète ?** L'arc tire plus vite, l'arbalète frappe plus fort par tir. Le même loot (dégâts de flèches, critiques) sert aux deux.",
"**Priorité d'équipement** : dégâts de flèche > cadence de tir > critiques > esquive > PV. L'armure compte peu.",
"**Pas assez de dégâts ?** Monte la chance et les dégâts critiques avant la cadence : les critiques multiplient tout.",
"**Trop touché ?** Reste à distance maximale ; une pièce d'esquive vaut plus qu'une pièce d'armure pour un archer."
],
"builds": [
"archer",
"archer-elfe",
"arbalete",
"archer-braise",
"archer-fantome"
]
},
{
"id": "melee",
"title": "Mêlée : dégâts",
"icon": "⚔",
"desc": "Corps à corps pour faire de gros dégâts. Plus de risque, plus de récompense : à jouer avec un soigneur ou de bons soins.",
"adapt": [
"**Une ou deux mains ?** Une main + bouclier est plus sûr ; deux mains tape plus fort mais bloque moins. Les bonus d'affixes (critique, vitesse) sont les mêmes.",
"**Priorité d'équipement** : dégâts de l'arme > critiques > vitesse d'attaque > vol de vie > PV.",
"**Tu meurs trop vite ?** Passe sur un build de la catégorie « Tanks & hybrides » ou ajoute du vol de vie avant d'ajouter de la vitesse.",
"**Boss à gros PV ?** Les builds en pourcentage de PV (Berserker du Nord) battent les builds de critique."
],
"builds": [
"berserker",
"berserker-critique",
"demi-umbra",
"demi-lux",
"duelliste",
"assassin",
"assassin-voleur",
"maitre-betes",
"brute-bastion",
"rodeur-ender",
"frappeur-foudre"
]
},
{
"id": "tanks",
"title": "Tanks & hybrides",
"icon": "🛡",
"desc": "Tenir devant, encaisser, protéger les autres. Plusieurs hybrides ajoutent des soins ou du feu.",
"adapt": [
"**Priorité d'équipement** : PV > armure/robustesse > blocage > soins > dégâts. Un bouclier est presque obligatoire.",
"**Armure ou esquive ?** Armure pour les coups réguliers, esquive pour les grosses attaques isolées. Les boss du pack ont des attaques en pourcentage de tes PV : les PV d'abord.",
"**Pas de dégâts ?** Ajoute une arme à effet (feu, poison) plutôt qu'un affixe de dégâts : tu fais des dégâts passifs en tenant.",
"**Dans un groupe de 7** : un seul tank suffit ; un deuxième vaut mieux en soutien (Templier sacré)."
],
"builds": [
"chevalier",
"tank-lux",
"nain-tank",
"phenix-bouclier",
"paladin-feu",
"templier-sacre",
"forteresse-ardente",
"garde-royal"
]
},
{
"id": "utilitaire",
"title": "Farm & utilité",
"icon": "💰",
"desc": "Moins de combat, plus de butin, d'XP et d'argent : à jouer pour préparer l'équipement du reste du groupe.",
"adapt": [
"**À quoi ça sert ?** Récolter les ressources et le butin rare pour les autres builds. Change de build quand tu attaques un boss.",
"**Priorité d'équipement** : chance, butin, fortune, vitesse de déplacement, puis PV."
],
"builds": [
"chasseur-tresors",
"mineur-blinde",
"chef-de-guerre"
]
},
{
"id": "maudit",
"title": "Anneau des Sept Malédictions",
"icon": "💀",
"desc": "Builds pensés pour le porteur de l'Anneau : dégâts subis ×2, armure −30 %, dégâts infligés −50 %, mais XP ×5 et butin supplémentaire. Chiffres déjà calculés avec l'anneau.",
"adapt": [
"**Choisis ta stratégie de survie** : encaisser (Maudit tank), esquiver (Spectre maudit), garder la distance (Archer maudit, Mage maudit) ou tout miser sur les critiques (Berserker maudit).",
"**Priorité d'équipement** : PV et esquive avant dégâts. Chaque point de PV compte double.",
"**Retire l'anneau du calcul** : décoche « Anneau des Sept Malédictions » dans le planificateur (Personnage → Hypothèses de combat) pour voir le même build sans l'anneau.",
"**Pas encore prêt ?** Joue d'abord la version sans anneau du build correspondant (Tank Shulk, Duelliste, Mage de sang, Archer elfe, Berserker critique) et mets l'anneau plus tard."
],
"builds": [
"maudit-tank",
"maudit-esquive",
"maudit-mage",
"maudit-archer",
"maudit-berserker"
]
},
{
"id": "elem",
"title": "Divinité & Corruption",
"icon": "☯",
"desc": "Les dégâts bonus de Cisco : à chaque coup, un second coup qui ignore l'armure. Divinité = critiques divins ; Corruption = pourcentage des PV max de la cible (anti-boss).",
"adapt": [
"**Les gemmes font tout** : Divinité ou Corruption se trouvent sur des gemmes qui ne vont que sur l'armure (casque, plastron, jambières, bottes). Remplis d'abord les sockets de ton armure, puis cherche des pièces avec plus de sockets.",
"**Ne mélange pas** : la Divinité et la Corruption ont chacune des paliers (50, 100, 200…) ; mieux vaut en monter une seule.",
"**Touche souvent** : chaque coup, flèche ou tic de sort déclenche le bonus. Une arme rapide ou un sort à plusieurs coups le multiplie.",
"**L'ascension** se choisit après le Roi déchu (divine) ou Cisco descendu (déchue) : on ne peut en avoir qu'une. Coche-la dans le planificateur pour voir son effet."
],
"builds": [
"div-champion",
"div-jumeaux",
"div-archer",
"div-mage",
"fell-seigneur",
"fell-roi",
"fell-archer",
"fell-mage",
"fell-dague"
]
},
{
"id": "leg-cisco",
"title": "Armes légendaires de Cisco",
"icon": "👑",
"desc": "Une arme légendaire de la campagne de Cisco, un build : Equillibrium et ses variantes, Nightfall, Hellbrand (la faux), Frostfang, Glacies… avec leur effet unique chiffré quand c'est possible.",
"adapt": [
"**L'arme d'abord** : ces builds sont construits autour d'une seule arme et de son effet unique. Si tu n'as pas encore l'arme, joue le build de la catégorie correspondante (mêlée, tank…) et passe à celui-ci quand tu la forges.",
"**Lis la chaîne de fabrication** : la plupart de ces armes se fabriquent à partir d'une autre (Hellbrand ← Adjudicator, Frostfang et Fell Ragnarok ← Glacies, Nightfall et Absolute ← Equillibrium). Ne jette pas la précédente.",
"**Effets uniques** : le planificateur prend en compte les dégâts réels, le plafond de dégâts subis et les capacités actives (case « Capacité active » dans Personnage → Hypothèses de combat). Les autres effets (soins, étendards…) sont décrits dans le guide mais pas chiffrés.",
"**Changer d'arme sans changer de build** : garde l'origine, la classe et les talents, et remplace l'arme par une autre de la même famille (épée → épée). Seul l'effet unique change."
],
"builds": [
"leg-equillibrium",
"leg-refined-equillibrium",
"leg-absolute-equillibrium",
"leg-nightfall",
"leg-supreme-nightfall",
"leg-azure-thunder",
"leg-adjudicator",
"leg-skysplitter",
"leg-glacies",
"leg-castor-pollux",
"leg-fell-ragnarok",
"leg-hellbrand",
"leg-frostfang"
]
},
{
"id": "leg-autres",
"title": "Armes uniques (autres mods)",
"icon": "✦",
"desc": "Les armes à effet unique des autres mods du pack : Celestisynth, Simply Swords, Cataclysm, Dreadsteel.",
"adapt": [
"**Armes uniques de mods** : leurs effets sont décrits dans le guide ; le planificateur ne chiffre que les dégâts de base, pas les capacités spéciales (compétences, étendards).",
"**Choisis selon ton rôle** : soutien d'équipe (Sunfire, Harbinger), contrôle (Icewhisper, Livyatan), mobilité (Breezebreaker, Thunderbrand), zone (Soul Render, Solaris), autonomie (The Watcher).",
"**Les mêmes talents** s'appliquent : voie du Forgeron pour la défense, du Chasseur pour la vitesse et l'esquive."
],
"builds": [
"leg-solaris",
"leg-crescentia",
"leg-breezebreaker",
"leg-aquaflora",
"leg-dreadsteel-scythe",
"leg-soul-render",
"leg-sunfire",
"leg-harbinger",
"leg-molten-edge",
"leg-stormbringer",
"leg-thunderbrand",
"leg-icewhisper",
"leg-livyatan",
"leg-watcher"
]
}
]
};
