# Audit du site CARPG et plan d'amélioration

Audit du 2026-10-03, pack **Cisco's Adventure RPG Ultimate V8E** (Forge 1.19.2, 288 mods), comparé au contenu réel du dossier `minecraft/` (mods, `config/`, `defaultconfigs/`, `config/paxi/datapacks/`, `kubejs/`, `scripts/Cisco.zs`).

Ce qui a été vérifié : inventaire des mods, couverture du wiki et des données, lecture des configs, pages testées dans le navigateur (bureau et mobile), cohérence de quelques affirmations du wiki avec la config. Ce qui n'a **pas** pu l'être : aucune valeur lue en jeu (voir « Calibrage »).

---

## 1. Les 8 constats qui comptent

| # | Constat | Gravité |
|---|---|---|
| 1 | **Aucune valeur du planificateur n'a été confrontée au jeu.** Les formules viennent du code décompilé et des configs, mais jamais de mesures réelles (la question « calibrage » du `QUESTIONS.md` est restée sans réponse). | Haute |
| 2 | **Les builds « Optimisé » ne sont pas réalistes** : affixes à 90 % de leur plage, 3-4 affixes partout, gemmes parfaites, aucune notion de chance de loot. Les dégâts atteignent des valeurs absurdes (jusqu'à ≈ 5×10⁷ DPS ; les builds de froid butent sur le plafond Apotheosis de 1000). | Haute |
| 3 | **Une affirmation du wiki est contredite par la config** : `monde.md` annonce la *lune de sang* ; dans `majruszsdifficulty.json`, `blood_moon`, `undead_army` et `bleeding` sont **désactivés** (`is_enabled: false`). | Haute (exactitude) |
| 4 | **Seuls 64 des 154 mods de gameplay sont expliqués dans les pages écrites à la main.** 90 n'apparaissent que dans la liste des mods. Structures : 2 sur 30. Dimensions : 4 sur 16. Boss et monstres : 3 sur 13. | Moyenne |
| 5 | **Le catalogue d'équipement est très incomplet** : armures MCSA (89), Iron's Spells (45), Twilight Forest (28), Aether (25), Blue Skies (24), Iter RPG (24, avec bonus de set)… absentes. Armes : Simply Swords 27/69, Blue Skies 57, Upgraded Netherite 50, Iter RPG 28, Twilight Forest 22, Aether 22 absentes. | Moyenne |
| 6 | **Le simulateur de boss ne couvre que 32 boss** et ignore les paliers de difficulté (Progressive Bosses, Majrusz). Manquent notamment les boss de Twilight Forest (Lich, Minoshroom, Hydre, Knight Phantom, Ur-Ghast, Yéti alpha, Reine des neiges…), de l'Aether (Slider, Reine des Valkyries, Sun Spirit), de Blue Skies (Summoner, Alchemist) et Sons of Sins. Pourtant, le livre de quêtes les demande. | Moyenne |
| 7 | **Pour un débutant, le site est un mur** : 73 builds sans aide au choix, un planificateur à 7 onglets sans mode simple, du jargon (affixes, sockets, EPF) sans infobulle, des noms d'objets/sorts en anglais, aucune feuille de route de la partie. Chiffres périmés sur la page d'accueil (« 26 builds ») et le README (« 12 builds », « 104 builds »). | Haute (objectif utilisateur) |
| 8 | **Le pipeline ne se met pas à jour tout seul** : plusieurs constantes sont recopiées à la main depuis les configs (plafonds d'attributs, AutoLeveling, plafonds Cataclysm). Aucun test ne compare le wiki aux configs. | Moyenne |

---

## 2. Comparatif mods ↔ site

### 2.1 Inventaire

288 mods : **154 gameplay**, 38 bibliothèques, 37 performance, 26 admin/scripts, 20 outils/interface, 13 décoration.

### 2.2 Couverture des 154 mods de gameplay

*« Expliqué » = cité dans les pages écrites à la main (`wiki_src/*.md`), hors liste générée des mods.*

| Catégorie | Mods | Expliqués | Jamais cités hors liste | Ce qui est réellement modélisé dans le planificateur |
|---|---|---|---|---|
| Cœur RPG | 11 | 3 | 8 | Apotheosis (rareté, affixes, gemmes, sockets), Passive Skill Tree, AutoLeveling (boss) |
| Magie | 6 | 5 | 1 | Iron's Spells (formules + config du pack) ; Ars Nouveau en catalogue seulement |
| Classes & origines | 5 | 2 | 3 | 33 origines, 13 classes, 17 bénédictions (pouvoirs chiffrés) |
| Combat & armes | 16 | 6 | 10 | Cisco, Cataclysm, Simply Swords (partiel), Celestisynth (4), Dreadsteel, Immersive Armors, Upgraded Netherite (armures), Knight Quest (armures) |
| Accessoires & reliques | 8 | 8 | 0 | Anneau des Sept Malédictions ; **aucun** relique (27 fichiers `config/relics/*.json`), Majrusz's Accessories, Enigmatic hors anneau |
| Boss & monstres | 13 | 3 | 10 | 32 boss (hors liste ci-dessus) |
| Progression & difficulté | 8 | 5 | 3 | Rien (texte seulement) |
| Dimensions & biomes | 16 | 4 | 12 | Rien (niveaux de départ dans `monde.md`) |
| Structures & donjons | 30 | 2 | 28 | Rien |
| Vie pratique | 19 | 10 | 9 | Rien (texte) |
| Cuisine, ferme, pêche | 11 | 10 | 1 | Rien (texte) |
| Colonie & PNJ | 11 | 6 | 5 | Rien (texte) |
| **Total** | **154** | **64** | **90** | ≈ 20 mods modélisés |

### 2.3 Écarts d'équipement (nombre de pièces dans les fichiers de langue du pack vs dans le site)

| Mod | Armures (jeu → site) | Armes (jeu → site) |
|---|---|---|
| Knight Quest | 163 → 154 | 9 → 0 |
| MC Story Mode Armors | 89 → 0 | — |
| Iron's Spells | 45 → 0 | 8 → 0 |
| Upgraded Netherite (+ Ultimerite) | 40 → 40 | 55 → 0 |
| Immersive Armors | 40 → 40 | — |
| Twilight Forest | 28 → 0 | 22 → 0 |
| Aether / Deep Aether | 33 → 0 | 28 → 0 |
| Blue Skies | 24 → 0 | 57 → 0 |
| Iter RPG | 24 → 0 | 28 → 0 |
| Forbidden & Arcanus | 16 → 0 | 12 → 0 |
| Simply Swords | — | 69 → 27 |
| Cataclysm | 11 → 25 | 6 → 13 |
| Stalwart Dungeons | 12 → 0 | 15 → 0 |

### 2.4 Configs et scripts : lus, recopiés ou ignorés

**Lus par le pipeline** : `skilltree-common.toml`, `irons_spellbooks-server.toml`, `ars_nouveau-server.toml`, `dreadsteel-common.toml`, `config/ftbquests/`, les datapacks Paxi (`config/paxi/datapacks/`), `kubejs/startup_scripts/carpgitemmod.js`, les fichiers de langue, le code décompilé.

**Recopiés à la main** (à automatiser) : plafonds d'attributs (`attributefix.json`, 26 attributs actifs), constantes AutoLeveling (`autoleveling-common.toml`), plafonds de dégâts Cataclysm, règles de butin Apotheosis (`apotheosis/adventure.cfg`, documentées dans `equipement.md`).

**Ignorés alors qu'ils influent sur le jeu** :

| Fichier | Contenu utile | Impact sur le site |
|---|---|---|
| `scripts/Cisco.zs` (CraftTweaker) | Recettes Dreadsteel, **parchemin d'amnésie** (réinitialise l'arbre), **lingots de démonium et de frigidium** (nécessaires pour Hellbrand et Frostfang), Souffle de dragon véritable, Œil voyou, Âme morte-vivante | Aucune page « recettes » ; l'amnésie et le démonium ne sont mentionnés nulle part. |
| `progressivebosses-common.toml` | Wither, Dragon, Elder Guardian : paliers de difficulté, bonus de vie, sbires, dégâts | Le simulateur utilise 300 PV pour le Wither, sans paliers. |
| `majruszsdifficulty.json` | Paliers normal / expert / maître, 19 fonctionnalités actives (creepers qui se divisent, double butin, mobs plus forts…), lune de sang, armée des morts et saignement **désactivés** | `monde.md` annonce la lune de sang à tort ; fonctionnalités actives non listées. |
| `config/relics/*.json` (27 reliques) | Valeurs des reliques | Absent du planificateur. |
| `infernalmobs.cfg`, `dragonfight.json`, `gamestages/` (vide), `minecolonies-*.toml`, `waystones-common.toml`, `corpse-server.toml` | Règles de monstres, du dragon, du vide-poche, des téléports, des cadavres | Texte partiel, non vérifié ligne à ligne. |
| `simplyswords/`, `celestisynth/`, `iter_rpg/iterpg.json`, `bettercombat/` | Stats et portées d'armes, bonus de set | Portées et vitesses d'attaque non utilisées ; bonus de set Iter RPG absents. |
| `config/paxi/datapacks/CiscoRpgClassDisable` | Classes désactivées (ex. `merchant`) | À recouper avec la liste des 13 classes affichée. |

**Rien d'exploitable dans** : `item_obliterator` (config d'exemple seulement), `kubejs/server_scripts` et `client_scripts` (exemples vides), `kubejs/data` (vide).

---

## 3. Réalisme du planificateur

### 3.1 Écarts connus entre le calcul et le jeu

| Sujet | État actuel | Conséquence |
|---|---|---|
| Calibrage | Aucune mesure en jeu | On ne sait pas si l'erreur est de 2 % ou de 30 %. |
| DPS | `coup × critique × vitesse × 0,9` (le 0,9 est un « placeholder ») | Ne tient pas compte du cooldown d'attaque réel ni de Better Combat. |
| Équipement « Optimisé » | Plages à 90 %, 4 affixes, 3 gemmes parfaites, tous les accessoires | Cible inatteignable pour la plupart des joueurs ; pas de loi de distribution (médiane). |
| Chance de loot | Absente | Aucune idée de ce qu'un joueur a vraiment à tel moment. |
| Boss | Dégâts d'un coup moyen, pas de patterns, pas de paliers de difficulté | Temps de combat et survie très optimistes. |
| Restrictions d'origine | Non appliquées | Un build peut combiner Elfe + plastron lourd, Piglin Brute + bouclier, Élytrien + armure lourde. Seuls des encadrés de guide l'indiquent. |
| Effets d'armes | Dégâts réels et plafond de coup modélisés ; soins, étendards, compétences non chiffrés | Armes à effets (Sunfire, Aquaflora…) sous-estimées. |
| Consommables | Nourriture, potions, buffs ignorés (hors effet de l'origine) | Sous-estime la puissance réelle, sauf bonus de diversité alimentaire. |
| Sets d'armure | Bonus de set non modélisés (Cisco, Iter RPG) | Idem. |
| Curios | Seulement des affixes génériques sur « Anneau / Collier / Ceinture » | Aucun accessoire propre (reliques, Enigmatic, Majrusz). |
| Noms | En anglais (noms du jeu) | Contredit « en français uniquement » et gêne les débutants. |

### 3.2 Erreur d'exactitude confirmée

- **Lune de sang** : annoncée dans `monde.md` (lignes 67 et 86), désactivée par la config (`blood_moon.is_enabled: false`, idem `undead_army` et `bleeding`). À corriger, et à lister à la place les 19 fonctionnalités vraiment actives.

Le reste du wiki n'a pas été vérifié affirmation par affirmation : voir l'action « tests wiki ↔ config » en phase 4.

---

## 4. Débutant : ce qui bloque aujourd'hui

1. **Aucune porte d'entrée « je débute »** : la page d'accueil propose planificateur, wiki, builds, mods. Un nouveau joueur ne sait pas par où commencer.
2. **Trop de choix, sans aide** : 73 builds. Pas de questionnaire, pas de « 3 builds pour commencer », pas de recommandation selon le style (soigneur, tank, distance…).
3. **Le planificateur est un outil d'expert** : 7 onglets, tableaux d'attributs, arbre de 588 nœuds. Aucun mode simple (« je choisis un build et je vois quoi améliorer »).
4. **Jargon** : affixe, socket, gemme, EPF, rolls, ehp, plafond… Le glossaire existe mais n'est pas relié au texte (pas d'infobulle au survol).
5. **Pas de feuille de route** : rien qui relie niveaux de builds, boss et chapitres du livre de quêtes (« Fais ça avant le Nether »).
6. **Pas d'images** : aucune icône d'objet, de boss ou de sort dans le wiki (seules les icônes de l'arbre sont embarquées). Pour un débutant, c'est essentiel.
7. **Pages longues sans résumé** : talents, atelier, magie dépassent 700 à 1 100 mots ; les guides de builds ont ≈ 15 sections.
8. **Chiffres périmés** sur l'accueil et le README.
9. **Mobile** : le site s'affiche, mais l'en-tête du planificateur occupe la moitié de l'écran et la fiche de stats passe sous les onglets.
10. **Poids** : `builds.js` (1,7 Mo) + `data.js` (1,1 Mo) chargés à l'ouverture du planificateur, donc lent sur téléphone.

---

## 5. Plan d'action

Estimations en journées de travail de Claude, sans compter les mesures que tu devras faire en jeu.

### Phase 0 : corrections immédiates (≈ 0,5 j)

- Corriger `monde.md` (lune de sang, armée des morts, saignement désactivés) et ajouter la liste des 19 fonctionnalités Majrusz actives.
- Rendre les chiffres de l'accueil, du README et de l'index des builds **générés** (nombre de builds, de pages, d'origines) pour qu'ils ne périment plus.
- Ajouter « Pack V8E · mis à jour le … » dans le pied de page.
- Mentionner le **parchemin d'amnésie**, les **recettes de démonium/frigidium/Dreadsteel** (depuis `Cisco.zs`) dans les guides concernés.
- Étiqueter dans les builds les chiffres au-delà de 100 000 DPS comme « maximum théorique ».

### Phase 1 : parcours débutant (≈ 3 à 4 j)

1. **Page « Je débute »** (lien en grand sur l'accueil) : parcours en 6 étapes (heure 0-2, premier jour, premier boss, Nether, dragon, après le dragon), chacune reliée au chapitre du livre de quêtes, au niveau de build conseillé et au boss à viser.
2. **Quiz « Quel build pour moi ? »** (5 questions : solo/groupe, distance/mêlée/magie, risque, patience, rôle) qui propose 3 builds ; branché sur les 73 builds existants.
3. **Mode simple du planificateur** : choisir un build, voir 8 statistiques clés, une liste « prochaine amélioration » (ex. « Cherche +PV sur tes bottes ») et un bouton « mode avancé ».
4. **Infobulles de glossaire** au survol dans tout le wiki.
5. **Résumé « 30 secondes »** en tête de chaque page longue et de chaque guide de build.
6. **Noms français** : table de traduction (origines/pouvoirs déjà faits ; sorts, objets, boss à faire) avec affichage « Nom FR (nom EN en jeu) ».
7. **Icônes** : extraire les textures des jars (objets, boss, sorts) en WebP léger, pour usage privé entre amis.
8. **Mobile** : en-tête compact, fiche de stats repliable.

Critère de réussite : un nouveau joueur trouve son premier build et sa première étape en moins d'une minute.

### Phase 2 : réalisme du moteur (≈ 5 à 7 j, dépend du calibrage)

1. **Calibrage** (voir §6) : 10 mesures en jeu, puis comparaison automatique avec le moteur. Seuil visé : moins de 5 % d'écart sur PV, armure, dégâts de coup, mana, puissance et coût de sorts.
2. **DPS** : remplacer le 0,9 par un vrai modèle (cooldown d'attaque, Better Combat, vitesse d'attaque, combos).
3. **Mode « Réaliste »** à côté de « Optimisé » : affixes à la médiane de leur plage, nombre d'affixes selon la rareté réellement obtenable, gemmes de rareté courante, raretés selon la dimension (`Affix Convert Rarities`, `Gem Dimensional Rarities` d'`adventure.cfg`).
4. **Indice de plausibilité** par build (plafonds atteints, rareté requise, nombre de boss à battre pour l'obtenir).
5. **Restrictions d'origine appliquées** : poids d'armure maximal, bouclier interdit, types d'arme autorisés, handicaps conditionnels.
6. **Boss** : paliers Progressive Bosses (Wither, Dragon, Elder Guardian), niveaux Majrusz, estimation des dégâts selon les attaques en pourcentage de PV, survie par phase.
7. **Bonus de set**, **nourriture/potions** et **reliques** (27 fichiers) intégrés au calcul.

Critère de réussite : pour 3 builds réels (le tien, un ami, un build du site), l'écart entre l'outil et le jeu est inférieur à 5 %.

### Phase 3 : couverture (≈ 5 à 6 j)

1. **Catalogue d'équipement** : ajouter les mods manquants du tableau 2.3, avec stats, bonus de set et raretés.
2. **Boss** : ajouter ≈ 20 boss (Twilight Forest, Aether, Blue Skies, Sons of Sins, mutants, Whisperwoods) et relier chaque boss au chapitre de quêtes.
3. **Guides générés** pour les **structures** (30 mods) et les **dimensions** (16) à partir des datapacks et fichiers de langue : où les trouver, niveau d'ennemis, butin type.
4. **Recettes** : navigateur de recettes (datapacks + `Cisco.zs`), au moins pour l'équipement de campagne et les armes légendaires.
5. **Reliques et accessoires** (Relics, Enigmatic, Majrusz) : page catalogue + champ dédié dans le planificateur.
6. **Armes manquantes** (Mjolnir, Brimstone Claymore, etc.) quand leurs effets sont documentés.

### Phase 4 : qualité et maintenance (≈ 3 à 4 j)

1. **Configs → données** : script qui lit directement `attributefix.json`, `autoleveling-common.toml`, `cataclysm.toml`, `progressivebosses-common.toml`, `majruszsdifficulty.json` au lieu de constantes recopiées.
2. **Tests « wiki ↔ config »** : chaque nombre-clé du wiki (niveaux, plafonds, taux de butin, coûts) est une assertion testée à la reconstruction ; la reconstruction échoue si la config a changé.
3. **Intégration continue** (GitHub Actions) : régénération, `smoke.js`, `check_links.py`, vérification de la taille des bundles.
4. **Bundles** : séparer `builds.js` (chargé à la demande) et `data.js` (découpé par onglet).
5. **Journal des changements** visible sur le site, avec la version du pack.
6. **Accessibilité** : contrastes, navigation clavier, étiquettes de formulaires.

---

## 6. Calibrage : les 10 mesures à relever en jeu

Pour chacune, note la valeur affichée (info-bulle, F3 ou Jade) et ton build (origine, classe, bénédiction, équipement, talents). Je comparerai ensuite avec le moteur.

1. PV max, armure et robustesse d'un personnage sans équipement.
2. Les mêmes valeurs avec une armure complète de Cisco (ex. Brightsteel).
3. Dégâts d'un coup d'épée sur un mannequin (Dummmmmmy est dans le pack) : sans critique, puis avec.
4. Vitesse d'attaque affichée et dégâts par seconde sur le mannequin pendant 10 secondes.
5. Mana max et régénération de mana avec un livre de sorts.
6. Dégâts et coût en mana d'un même sort au niveau 1 puis au niveau max.
7. Niveau, PV et dégâts d'un monstre à 1 000 et à 3 000 blocs du spawn (Jade).
8. Dégâts reçus d'un même coup avec et sans armure, puis avec l'anneau des Sept Malédictions.
9. Effet exact d'un affixe de chaque type sur un objet (valeur affichée vs plage du wiki).
10. Une gemme dans une pièce avec Cullinan, puis sans (pour vérifier le bonus de puissance de gemmes).

---

## 7. Décisions que je prendrais par défaut

Sauf avis contraire, je procéderais ainsi :

1. **Ordre** : phase 0, puis phase 1 (le plus visible pour tes amis), puis calibrage et phase 2.
2. **Noms** : afficher « français (anglais) » partout, sans renommer les données.
3. **Icônes** : extraire les textures des jars pour un usage strictement privé (site entre amis, non indexé).
4. **Builds « Optimisé »** : conservés, mais présentés comme des maximums théoriques, avec le mode « Réaliste » comme référence par défaut dès qu'il existera.
5. **Couverture** : générer automatiquement structures, dimensions et recettes depuis les datapacks plutôt que de les écrire à la main.
