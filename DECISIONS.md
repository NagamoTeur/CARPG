# Journal des décisions (exécution du plan d'amélioration)

Décisions prises sans demander, suite à « tu prends les décisions seul ». Chaque ligne : décision, raison, conséquence.

| # | Décision | Raison |
|---|---|---|
| 1 | Calibrage en jeu **partiel** : 19 captures reçues le 3 octobre 2026, résultats dans `calibrage/RESULTATS.md`, test `tests/calibration.js`. Reste à fermer : vie max (41,4 contre 25,2 calculé, il manque l'arbre de talents et le compte d'aliments) et dégâts sur mannequin. | Ne pas inventer de mesures ; les écarts sont listés. |
| 2 | La lune de sang, l'armée des morts et le saignement sont présentés comme **désactivés** (config), même si le livre de quêtes en parle. | La config du serveur fait foi. |
| 3 | Les chiffres de l'accueil, de l'index des builds et du pied de page sont **générés** à chaque reconstruction (nombre de builds, pages, mods, date, version du pack). | Éviter les chiffres périmés. |
| 4 | Noms affichés « Français (anglais) » : le français vient de `fr_fr.json` des mods quand il existe, sinon d'une table de traduction à la main (sorts, reliques). Les noms propres (boss, armes et armures de Cisco) restent tels qu'en jeu. Rien n'est renommé dans les données. | Reste cherchable avec les noms du jeu (le client est en anglais). |
| 5 | Icônes : textures extraites des jars du pack, redimensionnées à 32 px, usage privé entre amis, site non indexé (`robots.txt` + `noindex`). | Utile aux débutants ; droits des mods respectés en limitant à un usage privé. |
| 6 | Les builds « Optimisé » sont conservés mais étiquetés « maximum théorique » ; une colonne « loot réaliste » (médiane, une rareté de gemme en moins) est calculée à côté. | Évite de relancer 70 optimisations et reste lisible. |
| 7 | Restrictions d'origine : **avertissements** dans le planificateur plutôt que blocage dur. | Les joueurs peuvent vouloir tester ; l'avertissement suffit. |
| 8 | Le simulateur de boss reçoit un **palier Majrusz** (normal / expert / maître : +15 %/+30 % de vie, +10 %/+20 % de dégâts) et les **paliers Progressive Bosses** lus dans la config. | Valeurs tirées de `majruszsdifficulty.json` et `progressivebosses-common.toml`. |
| 9 | Le facteur DPS « 0,9 » devient une hypothèse visible « efficacité d'attaque » (champ réglable, **90 % par défaut** pour ne pas décaler les builds déjà calculés). | Pas de mesure pour trancher ; l'hypothèse est exposée plutôt que cachée. |
| 10 | Structures, dimensions et recettes sont **générées** à partir des datapacks des jars et des configs, avec une mention « généré automatiquement, à vérifier en jeu ». | Les écrire à la main pour 46 mods n'est pas tenable. |
| 11 | Nouvelles armes/armures : extraites des jars décompilés quand le format est lisible ; sinon non ajoutées (rien d'estimé). | Pas de stats inventées. |
| 12 | **Découverte : des builds étaient invalides.** Les restrictions d'origine (pas de bouclier pour Shulk, Piglin Brute, Frijani, Loutre ; armure plafonnée pour Élytrien, Haut-Elfe, Elfe des bois ; pas d'arc pour le Chevalier) ne figuraient nulle part. 26 builds les violaient. Elles sont maintenant lues dans les datapacks (`origin_rules.json`), vérifiées par le planificateur et par `sim/check_rules.js`, et les builds concernés ont été recalculés (armure vanilla en cotte de mailles ou netherite + affixes, pas de bouclier). | Un build « parfait » qu'on ne peut pas équiper n'a aucune valeur. |
| 13 | Les caps d'attributs sont **lus à la source** (`build_caps.py`) ; la table du moteur a été comparée : aucun écart. | Fin des constantes recopiées. |
| 14 | Le planificateur charge un **index léger** des builds (118 Ko) et télécharge l'état d'un build à la demande, au lieu d'1,7 Mo au démarrage. | Chargement mobile. |
| 15 | Les armures/armes manquantes d'autres mods (Twilight Forest, Aether, Blue Skies, MCSA, Iron's, Iter RPG, Simply Swords standard) **ne sont pas ajoutées** : leurs valeurs ne sont pas lisibles sans estimation. | Pas de stats inventées ; le site demande la valeur de l'info-bulle. |
| 16 | Les boss de Twilight Forest, Aether, Blue Skies et des Sept péchés (22 boss) sont ajoutés avec leurs PV/dégâts lus dans le code ; les mobs de la liste noire d'AutoLeveling (Sun Spirit, Summoner, Alchemist, Arachnarch, Starlit Crusher) sont marqués « stats de base ». | Le livre de quêtes les demande. |
| 17 | Les reliques ne sont **pas calculées** dans le planificateur (effets actifs et conditionnels) ; elles ont une page catalogue avec les valeurs réelles de la config. | Éviter de chiffrer faux. |
| 18 | Les bonus de set et les consommables (nourriture, potions) ne sont **pas modélisés** : décrits seulement. | Pas de source structurée fiable. |
