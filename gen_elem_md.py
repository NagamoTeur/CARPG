# -*- coding: utf-8 -*-
"""Guides des builds Divinité / Corruption -> wiki_src/builds/(div|fell)-*.md. Mécaniques : wiki_src/divinite-corruption.md.
Les chiffres (stats, arbre, progression, effets) sont ajoutés par build_wiki.py. Relancer : python3 gen_elem_md.py"""

TPL = """---
title: {title}
desc: Build {kind} du pack : {short}
---

> **En une phrase :** {pitch}

!!! tip "{kind} : rappel"
    {rule} Tout le détail (paliers, sources, ascensions) est sur la page [Divinité et Corruption](../divinite-corruption.html).

## Pour qui ?
{who}

## Style de jeu
{style}

## Les 3 choix du début

| Choix | Pick | Pourquoi |
|---|---|---|
{choices}

## Équipement : quoi chercher
{gear}

**Gemmes :** mets des **gemmes de {gem}** dans **tous les sockets de ton armure** (casque, plastron, jambières, bottes) : +5 à +40 chacune selon la rareté. C'est le cœur du build. Sur l'arme et les accessoires, prends ce qui monte ton moteur (voir le tableau plus bas).

**Arme :** {weapon}

## Progression
{prog}

## Face aux boss
{boss}

## Forces et faiblesses

| 👍 Forces | 👎 Faiblesses |
|---|---|
{sw}

## Variantes
{var} Voir aussi [Adapter un build à son loot](adapter.html).
"""

DIV_RULE = "À chaque coup que tu portes, un coup **divin** séparé part et **ignore l'armure**. Il peut faire un **critique divin** (×4 à ×10) dont la chance monte avec ta Divinité."
FELL_RULE = "À chaque coup que tu portes, un coup **corrompu** séparé part et **ignore l'armure**. Il peut déclencher une **décomposition** qui retire **6 à 12 % des PV max** de la cible."

B = [
 dict(id='div-champion', title='Champion divin (Divinité, mêlée)', kind='Divinité', short='épée de l\'équilibre et gemmes de Divinité', gem='Divinité', rule=DIV_RULE,
      pitch="chaque coup déclenche un éclat de Divinité qui ignore l'armure, avec des critiques divins énormes ; épée de l'équilibre puis Ascension divine après le Roi déchu.",
      who="Joueurs de mêlée qui veulent un build **simple et très fort** en fin de jeu.",
      style="Tu frappes normalement : la Divinité fait le reste, à chaque coup. Plus ta Divinité monte, plus les critiques divins sont fréquents et puissants (25 % ×7 dès 240).",
      choices="| Origine | **Demi-God Lux** | Dégâts de lumière et bonne vie |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Arès** | Critiques (pour tes coups normaux) |",
      gear="**Vie max**, **armure** et **dégâts d'attaque** sur l'armure ; **dégâts d'attaque** et **critiques** sur l'arme. Avec l'Ascension divine, chaque point de **dégâts d'attaque** ajoute 0,5 Divinité.",
      weapon="**Adjudicator** (+20 Divinité) au niveau Intermédiaire, puis **Absolute Equillibrium** (+50 Divinité, 44 de dégâts). Recettes : [Recettes](../recettes.html#Absolute).",
      prog="1. Intermédiaire : Adjudicator + premières gemmes de Divinité.\n2. Avancé : Absolute Equillibrium, gemmes mythiques (objectif : 240 de Divinité).\n3. Optimisé : vaincre le **Roi déchu** pour l'**Ascension divine** (+50 % des dégâts d'attaque en Divinité, soin sur les coups divins).",
      boss="- **Boss blindés** : la Divinité ignore l'armure.\n- **Boss de Cataclysm** : chaque coup divin est plafonné séparément, mais il s'ajoute à ton coup.\n- **Boss rapides** : l'Ascension divine te soigne à chaque coup divin.",
      sw="| Dégâts qui ignorent l'armure | Demande beaucoup de gemmes rares |\n| Critiques divins énormes | Peu de défense spécifique |", var="Plus rapide : [Danseur céleste](div-jumeaux.html). Version corrompue : [Seigneur corrompu](fell-seigneur.html)."),
 dict(id='div-jumeaux', title='Danseur céleste (Pollux, Divinité)', kind='Divinité', short='Pollux et vitesse d\'attaque', gem='Divinité', rule=DIV_RULE,
      pitch="la Divinité se déclenche à chaque coup : plus tu frappes vite, plus elle tombe souvent ; Pollux (+10 Divinité) frappe à vitesse 2,0.",
      who="Joueurs **rapides** qui aiment enchaîner les coups.", style="Tu frappes très vite, chaque coup déclenche un coup divin. La vitesse d'attaque compte presque autant que la Divinité.",
      choices="| Origine | **Feline** | Agile, retombe sur ses pattes |\n| Classe | **Rogue** | Esquive et critiques |\n| Bénédiction | **Susanoo** | Vitesse de déplacement puis d'attaque |",
      gear="**Vitesse d'attaque**, **esquive** et **vie max**. Les critiques normaux comptent moins : c'est la Divinité qui fait le gros des dégâts.",
      weapon="**Pollux** (+10 Divinité, vitesse 2,0), à tous les niveaux.", prog="1. Intermédiaire : Pollux + gemmes de Divinité.\n2. Avancé : gemmes mythiques, vitesse d'attaque.\n3. Optimisé : Ascension divine.",
      boss="- **Boss à gros PV** : beaucoup de coups = beaucoup de critiques divins.\n- **Boss à zone** : reste mobile, l'esquive te protège.",
      sw="| Beaucoup de déclenchements | Fragile |\n| Ignore l'armure | Demande de la vitesse d'attaque |", var="Plus de défense : [Champion divin](div-champion.html)."),
 dict(id='div-archer', title='Archer de lumière (Divinité)', kind='Divinité', short='arc et gemmes de Divinité', gem='Divinité', rule=DIV_RULE,
      pitch="chaque flèche qui touche déclenche la Divinité : des dégâts qui ignorent l'armure, avec des critiques divins, de loin.",
      who="Archers qui veulent **ignorer l'armure** des ennemis blindés.", style="Tu tires vite et souvent : chaque flèche déclenche un coup divin. La cadence de tir compte autant que la puissance.",
      choices="| Origine | **Venthari Sharpshooter** | +30 % de dégâts de projectile |\n| Classe | **Archer** | Tir plus précis |\n| Bénédiction | **Skadi** | Bonus de projectiles |",
      gear="**Cadence de tir** (vitesse de tension de l'arc), **dégâts de flèche**, **vie max**.", weapon="Un **arc** avec de bons affixes (les armes à Divinité sont des épées : à l'arc, tout vient des gemmes).",
      prog="1. Intermédiaire : premières gemmes de Divinité.\n2. Avancé : gemmes mythiques (240 de Divinité).\n3. Optimisé : plus de sockets (talents Greed/Cullinan).",
      boss="- **Boss blindés** : la Divinité ignore l'armure.\n- **Boss volants** : tu les touches de loin.", sw="| Distance + ignore l'armure | Pas d'arme qui donne de la Divinité |\n| Cadence élevée | Dépend des gemmes |", var="Version corrompue : [Archer de la décomposition](fell-archer.html)."),
 dict(id='div-mage', title='Prêtre de la lumière (Divinité + sorts)', kind='Divinité', short='sorts sacrés et gemmes de Divinité', gem='Divinité', rule=DIV_RULE,
      pitch="chaque fois qu'un de tes sorts touche, la Divinité se déclenche aussi : les sorts qui touchent plusieurs fois en profitent le plus.",
      who="Soigneurs qui veulent **aussi faire mal**.", style="Tu soignes l'équipe et tu lances des sorts sacrés qui touchent souvent (Trait guidant, Rayon de soleil). Chaque touche déclenche un coup divin.",
      choices="| Origine | **Lumerian Justicar** | Dégâts sacrés et soins |\n| Classe | **Cleric** | Potions et enchantements |\n| Bénédiction | **Chiron** | XP ×3 et soins |",
      gear="**Puissance Sacré**, **mana**, **réduction de recharge** et **vie max**.", weapon="Un **bâton** de mage ; tes gemmes de Divinité vont sur l'armure.",
      prog="1. Intermédiaire : gemmes de Divinité + sorts sacrés.\n2. Avancé : gemmes mythiques.\n3. Optimisé : plus de sockets.",
      boss="- **Boss blindés** : sorts + Divinité, rien n'est réduit par l'armure.\n- **Groupe** : tu soignes et tu fais des dégâts.", sw="| Soins + dégâts | Le planificateur sous-estime (1 déclenchement compté par sort) |\n| Ignore l'armure | Demande des gemmes |", var="Soigneur pur : [Prêtre soigneur](pretre-soigneur.html)."),
 dict(id='fell-seigneur', title='Seigneur corrompu (Supreme Nightfall, Corruption)', kind='Corruption', short='Supreme Nightfall et gemmes de Corruption', gem='Corruption', rule=FELL_RULE,
      pitch="la Corruption frappe à chaque coup et peut retirer 6 à 12 % des PV max de la cible : l'arme anti-boss par excellence, avec l'Ascension déchue après Cisco descendu.",
      who="Joueurs qui veulent **faire fondre les boss**.", style="Tu frappes fort avec Supreme Nightfall (42 de dégâts, +50 Corruption) ; chaque coup peut déclencher une décomposition qui s'en prend aux PV max du boss.",
      choices="| Origine | **Demi-God Umbra** | Dégâts sombres et vie |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Baba Yaga** | Affaiblit ce que tu touches |",
      gear="**Vie max**, **dégâts d'attaque**, **vitesse d'attaque**. Avec l'Ascension déchue, chaque point de **vie max** ajoute 0,03 Corruption et chaque décomposition te soigne de 15 % de ta vie max.",
      weapon="**Castor** (+10 Corruption) au niveau Intermédiaire, puis **Supreme Nightfall** (+50 Corruption).",
      prog="1. Intermédiaire : Castor + gemmes de Corruption.\n2. Avancé : Supreme Nightfall, gemmes mythiques (objectif : 250 de Corruption pour 20 % de décomposition).\n3. Optimisé : vaincre **Cisco descendu** pour l'**Ascension déchue**.",
      boss="- **Boss à très gros PV** : c'est là que la Corruption brille.\n- **Boss de Cataclysm** : la décomposition est plafonnée par coup, garde ta cadence.", sw="| Anti-boss (en % des PV max) | Moins efficace sur les petits monstres |\n| Soin avec l'Ascension déchue | Demande des gemmes rares |", var="Version divine : [Champion divin](div-champion.html). Marteau : [Marteau du Roi déchu](fell-roi.html)."),
 dict(id='fell-roi', title='Marteau du Roi déchu (Fell Ragnarok, Corruption)', kind='Corruption', short='Fell Ragnarok et gemmes de Corruption', gem='Corruption', rule=FELL_RULE,
      pitch="Fell Ragnarok ajoute 25 dégâts réels ET 50 de Corruption à chaque coup : contre un gros boss, chaque décomposition retire des centaines de PV.",
      who="Joueurs de **force brute** de fin de jeu.", style="Coups lourds, dégâts réels et Corruption : rien n'est réduit par l'armure.",
      choices="| Origine | **Piglin Brute** | Haches et force brute ; pas de bouclier |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Héphaïstos** | Dégâts en % des PV actuels |",
      gear="**Vie max**, **dégâts d'attaque**, **vol de vie** (pas de bouclier pour la Brute).", weapon="**Fell Ragnarok** (+25 dégâts réels, +50 Corruption).",
      prog="1. Avancé : Fell Ragnarok + gemmes de Corruption.\n2. Optimisé : Ascension déchue.", boss="- **Boss blindés et à gros PV** : idéal.\n- **Petits monstres** : la Corruption sert moins.",
      sw="| Dégâts réels + Corruption | Pas de bouclier |\n| Anti-boss | Fin de jeu seulement |", var="Plus rapide : [Lame de l'ombre corrompue](fell-dague.html)."),
 dict(id='fell-archer', title='Archer de la décomposition (Corruption)', kind='Corruption', short='arc et gemmes de Corruption', gem='Corruption', rule=FELL_RULE,
      pitch="chaque flèche déclenche la Corruption : à distance, tu grignotes un pourcentage des PV max des boss sans jamais t'approcher.",
      who="Archers qui veulent **tuer les boss** de loin.", style="Cadence de tir maximale : chaque flèche a sa chance de décomposition.",
      choices="| Origine | **Venthari Sharpshooter** | +30 % de dégâts de projectile |\n| Classe | **Archer** | Tir plus précis |\n| Bénédiction | **Loki** | Invisibilité, poison |",
      gear="**Cadence de tir**, **dégâts de flèche**, **vie max**.", weapon="Un **arc** avec de bons affixes ; la Corruption vient des gemmes.",
      prog="1. Intermédiaire : gemmes de Corruption.\n2. Avancé : gemmes mythiques.\n3. Optimisé : plus de sockets.", boss="- **Boss à gros PV** : très efficace.\n- **Boss volants** : idéal à distance.",
      sw="| Anti-boss à distance | Moins fort contre les petits monstres |\n| Sûr | Dépend des gemmes |", var="Version divine : [Archer de lumière](div-archer.html)."),
 dict(id='fell-mage', title='Nécromancien corrompu (Sang + Corruption)', kind='Corruption', short='sorts de sang et gemmes de Corruption', gem='Corruption', rule=FELL_RULE,
      pitch="les sorts de sang touchent souvent : chaque touche déclenche la Corruption et sa décomposition en pourcentage de PV.",
      who="Mages qui veulent un **anti-boss magique**.", style="Rayon de siphonnage, Aiguilles de sang : beaucoup de touches, beaucoup de décompositions.",
      choices="| Origine | **Demi-God Umbra** | +40 % de dégâts, vie |\n| Classe | **Cleric** | Potions et enchantements |\n| Bénédiction | **Baba Yaga** | Affaiblit et dessèche |",
      gear="**Puissance Sang**, **mana**, **vie max**.", weapon="Un **bâton** de mage ; les gemmes de Corruption vont sur l'armure.",
      prog="1. Intermédiaire : gemmes de Corruption.\n2. Avancé : gemmes mythiques.\n3. Optimisé : plus de sockets.", boss="- **Boss à gros PV** : sorts multi-touches + décomposition.\n- **Groupes** : zone.",
      sw="| Anti-boss magique | Planificateur prudent (1 déclenchement par sort) |\n| Vol de vie des sorts de sang | Fragile |", var="Sans Corruption : [Mage de sang](mage-sang.html)."),
 dict(id='fell-dague', title='Lame de l\'ombre corrompue (Castor, Corruption)', kind='Corruption', short='Castor et vitesse d\'attaque', gem='Corruption', rule=FELL_RULE,
      pitch="Castor (+10 Corruption) frappe vite : beaucoup de coups, donc beaucoup de chances de décomposition. Un build rapide qui ronge les gros ennemis.",
      who="Joueurs **rapides** et **furtifs**.", style="Tu enchaînes les coups ; chaque coup a sa chance de décomposition.",
      choices="| Origine | **Thief** | Furtivité |\n| Classe | **Rogue** | Esquive et critiques |\n| Bénédiction | **Loki** | Invisibilité, poison |",
      gear="**Vitesse d'attaque**, **esquive**, **vie max**.", weapon="**Castor** (+10 Corruption, vitesse 1,9), à tous les niveaux.",
      prog="1. Intermédiaire : Castor + gemmes de Corruption.\n2. Avancé : gemmes mythiques.\n3. Optimisé : Ascension déchue.", boss="- **Boss à gros PV** : beaucoup de décompositions.\n- **Boss rapides** : esquive.",
      sw="| Beaucoup de déclenchements | Fragile |\n| Anti-boss | Dégâts par coup faibles |", var="Version divine : [Danseur céleste](div-jumeaux.html)."),
]
for d in B:
    open(f"wiki_src/builds/{d['id']}.md", 'w', encoding='utf8').write(TPL.format(**d))
print(len(B), 'guides écrits')
