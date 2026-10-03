# Journal des décisions (exécution du plan d'amélioration)

Décisions prises sans demander, suite à « tu prends les décisions seul ». Chaque ligne : décision, raison, conséquence.

| # | Décision | Raison |
|---|---|---|
| 1 | Le calibrage en jeu n'est pas fait (aucune mesure disponible). Le moteur est validé contre les configs et le code, et le site le dit. Le protocole en 10 mesures reste dans `PLAN_AMELIORATION.md` §6. | Impossible sans le jeu ; ne pas inventer de fausses mesures. |
| 2 | La lune de sang, l'armée des morts et le saignement sont présentés comme **désactivés** (config), même si le livre de quêtes en parle. | La config du serveur fait foi. |
| 3 | Les chiffres de l'accueil, de l'index des builds et du pied de page sont **générés** à chaque reconstruction (nombre de builds, pages, mods, date, version du pack). | Éviter les chiffres périmés. |
| 4 | Noms affichés « Français (anglais) » : le français vient de `fr_fr.json` des mods quand il existe, sinon d'une table de traduction à la main (sorts, boss, pièces d'armure). Rien n'est renommé dans les données. | Reste cherchable avec les noms du jeu (le client est en anglais). |
| 5 | Icônes : textures extraites des jars du pack, redimensionnées à 32 px, usage privé entre amis, site non indexé (`robots.txt` + `noindex`). | Utile aux débutants ; droits des mods respectés en limitant à un usage privé. |
| 6 | Les builds « Optimisé » sont conservés mais étiquetés « maximum théorique » ; une colonne « loot réaliste » (médiane, une rareté de gemme en moins) est calculée à côté. | Évite de relancer 70 optimisations et reste lisible. |
| 7 | Restrictions d'origine : **avertissements** dans le planificateur plutôt que blocage dur. | Les joueurs peuvent vouloir tester ; l'avertissement suffit. |
| 8 | Le simulateur de boss reçoit un **palier Majrusz** (normal / expert / maître : +15 %/+30 % de vie, +10 %/+20 % de dégâts) et les **paliers Progressive Bosses** lus dans la config. | Valeurs tirées de `majruszsdifficulty.json` et `progressivebosses-common.toml`. |
| 9 | Le facteur DPS « 0,9 » devient une hypothèse visible « efficacité d'attaque » (curseur, 85 % par défaut). | Pas de mesure pour trancher ; l'hypothèse est exposée plutôt que cachée. |
| 10 | Structures, dimensions et recettes sont **générées** à partir des datapacks des jars et des configs, avec une mention « généré automatiquement, à vérifier en jeu ». | Les écrire à la main pour 46 mods n'est pas tenable. |
| 11 | Nouvelles armes/armures : extraites des jars décompilés quand le format est lisible ; sinon non ajoutées (rien d'estimé). | Pas de stats inventées. |
