# CARPG — Wiki & planificateur de build (Cisco's Adventure RPG Ultimate V8E)

Tout est dans le dossier **`site/`** : un site **statique, en français, qui marche hors ligne**.
Ouvre `site/index.html` dans un navigateur, ou lance `./serve.sh` (http://localhost:8080) — ou héberge le dossier tel quel sur n'importe quel serveur web statique : GitHub Pages, nginx, Caddy…).

```
site/
  index.html          page d'accueil
  pob/                planificateur de build (style Path of Building)
  wiki/               wiki non technique + guides de builds + recettes, structures, reliques, recherche (pages générées : voir la page d'accueil)
  data/               données normalisées lues dans le modpack (JSON + data.js)
  assets/             icônes de l'arbre de talents et d'objets/sorts (extraites des jars du pack, usage privé)
  css/
```

## Docker

```bash
docker compose up -d --build   # http://localhost:8088
```

## Contenu

| Livrable | Où |
|---|---|
| Liste des mods (gameplay / autres) | `site/wiki/mods.html` (+ `out/MODS.md`, `out/mods_classified.json`) |
| Planificateur de build | `site/pob/index.html` |
| Wiki non technique | `site/wiki/index.html` |
| Guides de builds rangés par catégorie, 4 niveaux chacun | `site/wiki/builds/` + « Mode simple » et « Builds prêts » du planificateur |
| Parcours débutant, quiz de build | `site/wiki/debuter.html`, `site/wiki/quiz.html` |
| Recettes, structures, reliques | `site/wiki/recettes.html`, `structures.html`, `reliques.html` |
| Guide des boss + simulateur | `site/wiki/boss.html` + onglet « Simulateur de boss » |

## Régénérer à partir du dossier du modpack

Le pipeline lit `../minecraft/` (mods, config, kubejs, datapacks Paxi) et recrée tout `site/`.

```bash
./run_all.sh                         # extraction -> données -> wiki -> vérif des liens
TREE=auto node sim/gen_builds.js     # (long) recalcule les builds ; options : STAGES=debutant,optimise ARCHS=new,leg,archer
node sim/realistic.js && python3 split_builds.py   # variante « loot médian » + découpe de l'index léger du planificateur
python3 build_wiki.py                # régénère le wiki avec les nouveaux builds
python3 tests/check_claims.py        # vérifie que les chiffres du wiki correspondent aux configs du serveur
```

Prérequis : Python 3.11+ (`markdown`), Node.js, et un Java 17 (déjà fourni par Prism Launcher) pour décompiler avec `tools/vineflower.jar`.

| Script | Rôle |
|---|---|
| `index_mods.py`, `classify.py` | Inventaire des mods et classement |
| `extract.py`, `merge.py` | Extraction des données JSON des jars, fusion avec les datapacks Paxi (les datapacks du pack surchargent les mods) |
| `build_skilltree.py` | Arbre de talents (588 nœuds), bonus structurés (conditions, sockets, gemmes…) |
| `build_gear.py` | Gemmes + affixes (Apotheosis, Apotheotic Additions, Apothic Curios, Iron's, Passive Skill Tree) |
| `build_origins.py` | Origines / classes / bénédictions (+ `translations/` : descriptions en français) |
| `build_spells.py` | Sorts d'Iron's Spells : formules lues dans le code décompilé + réglages du pack |
| `build_quests.py` | Livre de quêtes FTB |
| `build_ars.py` | Glyphes d'Ars Nouveau (coûts de la config) |
| `build_items2.py` | Armures/armes d'autres mods (Cataclysm, Upgraded Netherite, Dreadsteel, Immersive Armors) |
| `build_items.py` | Armures / armes (KubeJS + code de `cisco_mod`) |
| `build_bosses.py` | Boss : stats de base (code), multiplicateurs/plafonds (config), niveaux (AutoLeveling, liste noire comprise) |
| `build_origin_rules.py` | Restrictions d'équipement des origines (bouclier, armure, arc) lues dans les pouvoirs des datapacks |
| `build_caps.py` | Plafonds d'attributs lus dans le code des mods et `attributefix.json` |
| `build_relics.py`, `build_recipes.py`, `build_structures.py`, `build_icons.py` | Reliques (config + code), recettes (jars, datapacks, `Cisco.zs`), structures (datapacks), icônes (jars) |
| `tests/check_claims.py`, `sim/check_rules.js` | Tests : le wiki et les builds respectent configs et restrictions |
| `sim/` | Optimiseur de builds (même moteur que le planificateur) |
| `build_wiki.py` + `wiki_src/*.md` | Génération du wiki ; **les textes sont dans `wiki_src/`** |

## Comment les chiffres sont calculés

Les formules viennent du **code décompilé** des mods (Vineflower) et des **configs du pack** :

- Attributs : `(base + Σ ajouts) × (1 + Σ % de base) × Π(1 + % total)` ; plafonds d'AttributeFix.
- Critiques d'Apotheosis (base 5 % × 1,5 ; chances > 100 % = critiques multiples, ×0,85 à chaque fois).
- Esquive / blocage du Passive Skill Tree : `0,8 × 0,05p / (1 + 0,05p)`.
- Iron's Spells : puissance = (base + gain/niveau) × puissance globale × puissance d'école × multiplicateur du pack ; recharge et incantation avec plafond doux.
- Monstres : AutoLeveling (niveau = départ + 0,008 × distance + aléatoire ; +9 % PV, +14 % dégâts, +8 % armure par niveau).
- Boss Cataclysm : plafond de dégâts par coup (config).

## Limites connues (à vérifier en jeu)

- **Valeurs de base des armes/armures** : fiables pour les objets de Cisco, de KubeJS et de quelques mods ; pour les autres (Twilight Forest, Aether, Blue Skies, MCSA, Iron's…), saisis la valeur de l'info-bulle.
- **Aucun calibrage en jeu n'a été fait** : voir `PLAN_AMELIORATION.md` §6 pour les 10 mesures à relever.
- Ars Nouveau (glyphes), la rotation réelle des sorts, les phases des boss, l'infernal, les dégâts des invocations ne sont **pas modélisés**.
- Les durées/valeurs de certains sorts utilisent des formules multi-lignes non extraites (affichées « voir en jeu »).
- Les **builds** sont des cibles théoriques (optimiseur). Les chiffres servent à comparer, pas à promettre.
- Les descriptions sont traduites ; les **noms** s'affichent « Français (anglais) » quand une traduction existe (sorts, objets avec fr_fr, reliques).

## Outils installés pour l'analyse

- `tools/vineflower.jar` (décompilateur) lancé avec le Java 17 de Prism Launcher.
- `decomp/` : sources décompilées (Apotheosis, Passive Skill Tree, Iron's, AutoLeveling, Cataclysm, Cisco…). Non redistribuables : ne pas publier.

Les icônes de `site/assets/` proviennent des jars du pack (droits de leurs auteurs) ; le site est marqué `noindex` et réservé à un usage privé entre amis : ne le publie pas tel quel.
# CARPG
