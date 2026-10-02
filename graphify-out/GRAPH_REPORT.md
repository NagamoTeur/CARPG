# Graph Report - work  (2026-10-02)

## Corpus Check
- 103 files · ~89,386 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 8 file(s) not represented in the graph (top: (none) 4, .css 2, .zip 1)

## Summary
- 442 nodes · 787 edges · 15 communities (12 shown, 3 thin omitted)
- Extraction: 91% EXTRACTED · 9% INFERRED · 0% AMBIGUOUS · INFERRED: 73 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- Pipeline données (build_*.py)
- Bénédictions divines
- Générateur de builds (sim)
- Mécaniques de jeu (wiki)
- Extraction gear/attributs
- Projet & déploiement
- Générateur du wiki
- Interface du planner (ui.js)
- Harnais de test Node
- Équipement & accessoires
- Moteur de calcul (engine.js)
- Arbre de talents (tree.js)
- Parseur SNBT
- run_all.sh
- serve.sh

## God Nodes (most connected - your core abstractions)
1. `Tous les builds` - 19 edges
2. `fnum()` - 18 edges
3. `main()` - 16 edges
4. `TreeView` - 16 edges
5. `esc()` - 14 edges
6. `render()` - 14 edges
7. `describe()` - 13 edges
8. `main()` - 12 edges
9. `recalc()` - 11 edges
10. `renderSlotEditor()` - 11 edges

## Surprising Connections (you probably didn't know these)
- `Glyphes d'Ars Nouveau` --references--> `Non-technical wiki (37 pages)`  [INFERRED]
  wiki_src/ars.md → README.md
- `AutoLeveling (+9% PV, +14% dégâts, +8% armure/niveau)` --semantically_similar_to--> `Attribute / damage formulas from decompiled mods`  [INFERRED] [semantically similar]
  wiki_src/boss.md → README.md
- `Curios slots (accessoires)` --semantically_similar_to--> `Curios API`  [INFERRED] [semantically similar]
  wiki_src/accessoires.md → site/MODS.md
- `Guide des boss` --references--> `Non-technical wiki (37 pages)`  [INFERRED]
  wiki_src/boss.md → README.md
- `Traductions batch1 (origines/classes)` --references--> `Data extraction pipeline (Python/Node scripts)`  [INFERRED]
  translations/batch1.txt → README.md

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Builds Phénix (feu)** — wiki_src_builds_paladin_feu, wiki_src_builds_phenix_bouclier, concept_pyriosi_phoenix_knight [EXTRACTED 0.90]
- **Les 3 choix du début : origine, classe, bénédiction** — concept_origine, concept_classe, concept_benediction_divine [EXTRACTED 0.95]
- **Mage builds using Cleric class** — wiki_src_builds_mage_ender, wiki_src_builds_mage_foudre, wiki_src_builds_mage_glace, wiki_src_builds_mage_sang [INFERRED 0.75]
- **Archer-type build guides** — wiki_src_builds_arbalete, wiki_src_builds_archer_elfe, wiki_src_builds_archer [INFERRED 0.85]

## Communities (15 total, 3 thin omitted)

### Community 0 - "Pipeline données (build_*.py)"
Cohesion: 0.07
Nodes (33): Base de données des boss -> site/data/bosses.json Stats de base : lues dans le…, Armures d'autres mods (matériaux en enum) -> out/items_extra.json ; fusionné…, clean(), parse_class(), Objets de base (armures / armes) : KubeJS (carpgitemmod.js) + code décompilé de…, build_origin(), collect_layers(), main() (+25 more)

### Community 1 - "Bénédictions divines"
Cohesion: 0.06
Nodes (52): Arès (Bénédiction), Athéna (Bénédiction), Baba Yaga (Bénédiction), Borée (Bénédiction), Chiron (Bénédiction), Héphaïstos (Bénédiction), Loki (Bénédiction), Râ (Bénédiction) (+44 more)

### Community 2 - "Générateur de builds (sim)"
Cohesion: 0.06
Nodes (33): { optimize, summary, OBJ, ctx }, R, ALLC, ARCH, BOW, CTYPE, CURIOS, EARLY (+25 more)

### Community 3 - "Mécaniques de jeu (wiki)"
Cohesion: 0.07
Nodes (39): Ars Nouveau (Source, glyphes), Bénédiction divine, Classe, Demi-God Lux (origine), Ender Dragon, Formule d'addition des bonus, FTB Chunks, Gemme et sockets (+31 more)

### Community 4 - "Extraction gear/attributs"
Cohesion: 0.11
Nodes (32): attr_text(), conditions_ok(), describe_special(), effname(), gem_bonus(), gem_name(), main(), mid() (+24 more)

### Community 5 - "Projet & déploiement"
Cohesion: 0.08
Nodes (35): Cisco's Adventure RPG Ultimate V8E modpack, CARPG Wiki & Build Planner (static FR site), Glyphes Ars Nouveau (forme+effets+augmentations, mana/Source), AutoLeveling (+9% PV, +14% dégâts, +8% armure/niveau), Règles des boss (niveau, plafond de dégâts Cataclysm, attaques % PV), docker-compose carpg-site service, Cataclysm bosses (Ignis, Harbinger, Leviathan...), Origines / classes (build_origins.py) (+27 more)

### Community 6 - "Générateur du wiki"
Cohesion: 0.13
Nodes (30): build_page(), esc(), layout(), load_md(), main(), md(), origin_cards(), page_affixes() (+22 more)

### Community 7 - "Interface du planner (ui.js)"
Cohesion: 0.14
Nodes (29): affixLabel(), clampItem(), ENG_TEXT(), ensureItem(), fmtT(), gemBonusTexts(), gemOptionsFor(), init() (+21 more)

### Community 8 - "Harnais de test Node"
Cohesion: 0.07
Nodes (26): ref_fs, ref_path, ref_vm, ctx, sim_harness_d, sim_harness_eng, fs, path (+18 more)

### Community 9 - "Équipement & accessoires"
Cohesion: 0.13
Nodes (26): 4 ateliers Apotheosis (recyclage, taille de gemmes, reforge, reforge simple), Apothic Curios (affixes sur accessoires), Bénédictions: mineur / majeur après Ender Dragon, Accessoires de Cisco (Amulet of Vitality, Band of Brilliance, Keystone...), Curios slots (accessoires), Gemmes et qualité, Venthari Sharpshooter (+30% projectiles, -30% PV), Wood Elf (Arkwys) (+18 more)

### Community 10 - "Moteur de calcul (engine.js)"
Cohesion: 0.19
Nodes (18): assume(), bossSim(), bossStats(), collect(), compute(), R, critExpect(), eqMatch() (+10 more)

### Community 12 - "Parseur SNBT"
Cohesion: 0.22
Nodes (3): loads(), P, parse_file()

## Knowledge Gaps
- **78 isolated node(s):** `run_all.sh script`, `serve.sh script`, `{ optimize, summary, OBJ, ctx }`, `R`, `fs` (+73 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 139 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **3 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Non-technical wiki (37 pages)` connect `Équipement & accessoires` to `Projet & déploiement`, `Générateur du wiki`?**
  _High betweenness centrality (0.097) - this node is a cross-community bridge._
- **Why does `README CARPG` connect `Projet & déploiement` to `Équipement & accessoires`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **Why does `renderSheet()` connect `Interface du planner (ui.js)` to `Générateur de builds (sim)`?**
  _High betweenness centrality (0.032) - this node is a cross-community bridge._
- **Are the 10 inferred relationships involving `main()` (e.g. with `page_affixes()` and `page_ars()`) actually correct?**
  _`main()` has 10 INFERRED edges - model-reasoned connections that need verification._
- **What connects `run_all.sh script`, `serve.sh script`, `{ optimize, summary, OBJ, ctx }` to the rest of the system?**
  _78 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Pipeline données (build_*.py)` be split into smaller, more focused modules?**
  _Cohesion score 0.06610169491525424 - nodes in this community are weakly interconnected._
- **Should `Bénédictions divines` be split into smaller, more focused modules?**
  _Cohesion score 0.06108597285067873 - nodes in this community are weakly interconnected._