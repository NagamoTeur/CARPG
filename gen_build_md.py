# -*- coding: utf-8 -*-
"""Écrit les guides détaillés des builds de la vague 3 dans wiki_src/builds/*.md (relancer après modification).
Les sections chiffrées (3 choix en détail, progression, arbre de talents, fiche par niveau) sont ajoutées par build_wiki.py."""
import os

OUT = 'wiki_src/builds'

TPL = """---
title: {title}
desc: {desc}
---

> **En une phrase :** {pitch}

## Pour qui ?
{who}

## Style de jeu
{style}

## Les 3 choix du début

| Choix | Pick | Pourquoi |
|---|---|---|
{choices}

{warn}

## Équipement : quoi chercher
{gear}

## Talents
{talents}
{spells}
## Face aux boss
{boss}

## Forces et faiblesses

| 👍 Forces | 👎 Faiblesses |
|---|---|
{sw}

## Les erreurs classiques
{errors}

## Variantes
{variants}

Voir aussi : [Adapter un build à son loot](adapter.html) · [Débutant → Optimisé](niveaux.html).
"""

B = {}

B['pretre-soigneur'] = dict(
    title='Prêtre soigneur (Soutien pur)', desc="Guide du build soigneur pur avec l'origine Priest",
    pitch="le soigneur « pur » : toute la panoplie de soins du pack. Il ne tue presque rien, mais personne ne meurt à côté de lui.",
    who="Le joueur qui aime **garder les autres en vie** et lire la barre de vie du groupe plutôt que celle du boss. Indispensable pour les boss à gros dégâts en pourcentage de PV.",
    style="Tu restes **derrière** le tank, tu entretiens une zone de soin (*Cercle de soin*) et tu gardes un soin direct (*Soin supérieur*) pour les urgences. Entre deux soins, *Fortification* absorbe les gros coups à l'avance. Ta réserve de mana est ta vraie « barre de vie » : surveille-la.",
    choices="| Origine | **Priest** | Bouclier de protection de 10 s, plus rapide, aucune chute ; **végétarien** (aucune viande) ; les morts-vivants t'affaiblissent |\n| Classe | **Cleric** | Meilleurs enchantements et potions |\n| Bénédiction | **Chiron** | Triple XP et soins accrus (voir le détail plus bas) |",
    warn='!!! warning "Attention"\n    Les **morts-vivants** t\'affaiblissent : évite d\'être en première ligne dans les donjons de squelettes et de zombies.',
    gear="**Puissance Sacré** et **mana** avant tout, puis **réduction de recharge** (pour relancer tes soins), puis **PV**. Un livre de sorts et une pierre de sort Sacré sont indispensables. Armure légère à moyenne : tu ne dois pas être frappé, pas tenir les coups.",
    talents="Voie de l'**Enchanteur** : mana, régénération de mana, puissance de sorts. Au niveau Débutant, ne prends que les premiers nœuds ; ajoute la voie des gemmes dès l'Intermédiaire.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Cercle de soin** en début de combat, posé là où se tient le tank.\n2. **Fortification** sur celui qui va prendre un gros coup.\n3. **Bénédiction de vie** quand plusieurs joueurs sont bas.\n4. **Soin supérieur** (rare, cher) pour sauver quelqu'un sur le point de mourir.\n5. **Nuage de régénération** pour maintenir un groupe entier entre deux vagues.\n",
    boss="- **Boss à gros coups** : prépare *Fortification* avant l'attaque ; reste hors de portée.\n- **Boss rapides** : entretiens le *Cercle de soin* en continu.\n- **Boss en zone** : éloigne-toi du tank et soigne à distance.",
    sw="| Soigne tout le groupe, même à distance | Presque aucun dégât |\n| Fortification absorbe les gros coups | Dépend du mana : sans mana, plus de soins |\n| Fait vite monter le niveau du groupe | Faible aux morts-vivants et en mêlée |",
    errors="- **Se mettre devant** : tu es la dernière personne qu'on peut perdre.\n- **Soigner tout le monde en même temps** : le mana s'épuise ; soigne celui qui en a besoin.\n- **Oublier les repas** : sans viande, tes plats doivent être végétariens.",
    variants="Pour tuer aussi : [Justicier sacré](justicier.html) (Trait guidant). Pour tenir plus : [Gardien sacré](mage-tank.html).")

B['mage-evocateur'] = dict(
    title='Évocateur des vents (Foudre mobile)', desc="Guide du mage de foudre mobile avec l'origine Elytrian",
    pitch="un mage de foudre qui ne reste jamais en place : élytres, pas de tonnerre et bourrasques pour frapper puis disparaître.",
    who="Joueurs **dynamiques** qui détestent rester immobiles. Excellent contre les boss qui frappent lentement mais fort.",
    style="Tu **tournes autour** du boss, tu tires depuis les airs (**+dégâts en vol**), tu te repositionnes avec *Pas du tonnerre* et tu retombes dans un coin sûr. Le sol n'est qu'un endroit où tu te poses pour recharger ton mana.",
    choices="| Origine | **Elytrian** | Ailes d'élytres sans les équiper ; élan de 20 blocs toutes les 30 s ; dégâts en vol ; **armure légère uniquement** ; claustrophobe |\n| Classe | **Explorer** | Mobilité, exploration |\n| Bénédiction | **Zéphyr** | Pas de dégâts de chute ; **vol ailé** après le dragon (touche `H`) |",
    warn='!!! warning "Armure limitée à la cotte de mailles"\n    L\'Élytrien ne peut porter aucune pièce dont la valeur d\'armure dépasse **2 / 5 / 4 / 1** (casque / plastron / jambières / bottes). Les armures de Cisco sont toutes trop fortes : ce build utilise de la **cotte de mailles vanilla** avec de bons affixes. Le planificateur te prévient si ton équipement dépasse la limite.',
    gear="**Puissance Foudre**, **mana**, **vitesse d'incantation** : tu dois pouvoir lancer en vol. Mets des PV : tu as peu d'armure et des os fragiles (dégâts de chute et de collision en vol).",
    talents="Voie de l'**Enchanteur**, avec les gemmes qui donnent de la vitesse et du mana.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Éclair** en ouverture depuis les airs (gros dégâts).\n2. **Électrocution** en continu tant que le boss est à portée.\n3. **Pas du tonnerre** pour changer de position instantanément.\n4. **Charge** pour fondre sur un ennemi isolé.\n5. **Bourrasque** pour repousser ce qui s'approche trop.\n",
    boss="- **Boss de mêlée** : reste en l'air et tire ; il ne peut pas t'atteindre.\n- **Boss à projectiles** : ne vole pas en ligne droite, change de direction.\n- **Boss dans un couloir** (claustrophobie) : combats-les à l'extérieur.",
    sw="| Mobilité énorme, difficile à toucher | Fragile aux chutes et collisions |\n| Dégâts en vol | Handicapé en intérieur (claustrophobie) |\n| Pas de dégâts de chute (Zéphyr) | Armure légère uniquement |",
    errors="- **Se battre en intérieur** : sans ciel, tu perds ton avantage.\n- **Oublier l'élan** : il est disponible toutes les 30 s, garde-le pour fuir.\n- **Voler trop près du boss** : reste à portée de sort mais hors de portée de coup.",
    variants="Pour plus de défense : [Mage de la tempête](mage-foudre.html). Au sol : [Frappeur de foudre](frappeur-foudre.html).")

B['archimage-elfe'] = dict(
    title='Archimage haut elfe (Ender)', desc="Guide du mage Ender avec l'origine High Elf",
    pitch="le moteur Ender du Mage sombre avec une origine plus classique : trou noir pour regrouper, étoiles filantes pour finir, contresort pour couper les incantations.",
    who="Joueurs qui veulent un mage **polyvalent** et un peu plus **technique** que le Mage sombre, avec du contrôle de foule.",
    style="Tu **ouvres** avec *Trou noir* pour regrouper les monstres, puis *Étoiles filantes* pour tout nettoyer, *Flèche magique* pour finir les rescapés, et *Contresort* si un boss lance une grosse attaque magique.",
    choices="| Origine | **High Elf** | Trait de feu en touche principale ; perception aiguisée ; grand saut ; **mailles maximum** ; **végétarien** |\n| Classe | **Cleric** | Potions et enchantements |\n| Bénédiction | **Râ** | +11 dégâts de feu au soleil ; +10 après le dragon |",
    warn='!!! warning "Armure limitée à la cotte de mailles"\n    Le Haut-Elfe ne peut porter aucune pièce dont la valeur d\'armure dépasse **2 / 5 / 4 / 1**. Ce build utilise donc de la **cotte de mailles vanilla** avec de bons affixes, et ne mange pas de viande : prévois des repas végétariens.',
    gear="**Puissance Ender**, **mana**, **recharge**. Armure légère. Ajoute des PV : l'origine est fragile.",
    talents="Voie de l'**Enchanteur** puis les gemmes. La voie du Mineur (gemmes) est un bon complément.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Trou noir** : attire les ennemis au même endroit.\n2. **Étoiles filantes** : zone de dégâts massifs.\n3. **Flèche magique** : dégâts à volonté pour finir.\n4. **Contresort** : coupe un sort ennemi (boss magiciens).\n5. **Téléportation** : fuir ou changer de zone.\n",
    boss="- **Boss de groupe** : *Trou noir* + *Étoiles filantes*.\n- **Boss magicien** : *Contresort* sur l'incantation.\n- **Boss rapide** : *Flèche magique* à volonté, reste mobile.",
    sw="| Contrôle de foule + zone + burst | Fragile et peu de PV |\n| Contresort rare et utile | Mana vite épuisé par Étoiles filantes |\n| Origine simple à jouer | Régime végétarien |",
    errors="- **Lancer Étoiles filantes trop tôt** : attends que les ennemis soient regroupés.\n- **Oublier le Contresort** : il n'est utile qu'au bon moment.\n- **Armure trop lourde** : tu n'en bénéficies pas.",
    variants="Plus simple : [Mage sombre](mage-ender.html). Plus de défense : [Gardien sacré](mage-tank.html).")

B['pyromancien'] = dict(
    title='Pyromancien Blazeborn (Feu pur)', desc="Guide du mage de feu avec l'origine Blazeborn",
    pitch="le Nether est ta maison : immunisé au feu, tu lances des sorts de zone en restant au milieu des flammes.",
    who="Joueurs qui veulent **brûler tout ce qui bouge**, à l'aise dans le Nether. Redoutable en groupe.",
    style="Tu poses un **mur de feu**, tu restes dedans (immunisé), tu bombardes (*Boule de feu*, *Bombe de magma*) et tu finis avec *Tempête de braise*. Tu **brûles** les ennemis qui s'approchent ; ton propre feu ne te touche pas.",
    choices="| Origine | **Blazeborn** | Immunisé au feu, au poison et à la faim ; plus de dégâts en feu ; **hydrophobe** (l'eau te blesse) |\n| Classe | **Explorer** | Mobilité, exploration |\n| Bénédiction | **Râ** | +11 dégâts de feu au soleil ; +10 après le dragon |",
    warn='!!! warning "Hydrophobie"\n    L\'eau te fait des dégâts : évite la pluie, les rivières et les boss aquatiques.',
    gear="**Puissance Feu**, **dégâts de feu fixes**, **mana**, **recharge**. Armure légère à moyenne, mais prends des PV.",
    talents="Voie de l'**Enchanteur** puis les gemmes de feu.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Mur de feu** pour bloquer et brûler.\n2. **Bombe de magma** sur les groupes.\n3. **Boule de feu** pour les dégâts de zone moyens.\n4. **Souffle de feu** à courte portée.\n5. **Tempête de braise** pour finir.\n",
    boss="- **Boss de feu** : inutile, change de build.\n- **Boss de glace / morts-vivants** : excellent.\n- **Boss aquatique** : à éviter.",
    sw="| Dégâts de zone énormes | Aucun dégât contre les boss de feu |\n| Immunisé au feu et au poison | L'eau te fait mal |\n| Immunité à la faim | Peu de mobilité hors Nether |",
    errors="- **Se battre dans l'eau.**\n- **Utiliser le feu contre un boss de feu.**\n- **Oublier le soleil** : le bonus de Râ n'agit qu'au soleil.",
    variants="Hors Nether : [Chevalier-mage du Phénix](mage-feu.html). Avec bouclier : [Forteresse ardente](forteresse-ardente.html).")

B['empoisonneur'] = dict(
    title='Empoisonneur des ombres (Nature)', desc="Guide du mage nature/poison avec l'origine Arachnid",
    pitch="dégâts sur la durée : tu empoisonnes, tu immobilises, tu laisses le temps travailler.",
    who="Joueurs **patients** qui aiment la stratégie. Parfait contre les boss à beaucoup de PV.",
    style="Tu **empoisonnes** d'abord (*Flèche empoisonnée*), tu **entraves** (*Racines*), tu empiles les poisons (*Jet d'acide*, *Flétrissure*, *Éclaboussure*) et tu t'éloignes pendant qu'ils font effet. Tu peux **grimper aux murs** pour rester hors d'atteinte.",
    choices="| Origine | **Arachnid** | Grimpe sur les murs ; les ennemis frappés restent coincés dans une toile ; **−3 cœurs** ; carnivore |\n| Classe | **Cleric** | Potions affinées |\n| Bénédiction | **Loki** | Invisibilité accroupi, poison ; +40 % de dégâts aux empoisonnés après le dragon |",
    warn='!!! warning "Fragile"\n    −3 cœurs : prévois des PV supplémentaires dès l\'Intermédiaire.',
    gear="**Puissance Nature**, **dégâts de poison**, **mana**, **PV**.",
    talents="Voie de l'**Alchimiste** : durée et puissance des potions, utile pour les poisons.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Flèche empoisonnée** à distance pour commencer.\n2. **Racines** pour immobiliser.\n3. **Jet d'acide** et **Flétrissure** pour empiler les dégâts.\n4. **Éclaboussure** sur les groupes.\n",
    boss="- **Boss à gros PV** : les poisons font plus de dégâts avec le temps.\n- **Boss immunisés au poison** (morts-vivants, golems) : change de sort.\n- **Boss rapides** : *Racines* d'abord.",
    sw="| Dégâts continus énormes | Lent à démarrer |\n| Grimpe aux murs | Inutile contre les immunisés au poison |\n| Contrôle (racines) | Très fragile |",
    errors="- **Attaquer un mort-vivant** : immunisé.\n- **Rester au sol** : monte sur un mur.\n- **Négliger les PV.**",
    variants="Plus sûr : [Druide du marais](druide.html). Plus de dégâts directs : [Mage de sang](mage-sang.html).")

B['archer-braise'] = dict(
    title='Archer de braise (Feu)', desc="Guide de l'archer de feu avec l'origine Blazeborn",
    pitch="des flèches enflammées : les dégâts de feu fixes s'ajoutent à chaque tir et brûlent les cibles.",
    who="Joueurs qui veulent un archer **simple** avec un bonus de dégâts de zone grâce au feu.",
    style="Tu tires à distance, chaque flèche **brûle** la cible. Les dégâts de feu fixes ne dépendent pas de la force de l'arc : ils restent constants même avec un arc faible.",
    choices="| Origine | **Blazeborn** | Immunisé au feu, au poison et à la faim ; **hydrophobe** |\n| Classe | **Archer** | Tir plus précis |\n| Bénédiction | **Râ** | +11 dégâts de feu au soleil ; +10 après le dragon |",
    warn='!!! warning "Eau interdite"\n    L\'eau te blesse : évite la pluie et les rivières.',
    gear="**Dégâts de flèche**, **dégâts de feu fixes**, **cadence de tir**, **critiques**. Un arc avec « Flamme » est un bonus.",
    talents="Voie du **Chasseur** : flèches, critiques, esquive.",
    spells="",
    boss="- **Boss de glace / morts-vivants** : très efficace.\n- **Boss de feu** : inutile.\n- **Boss volants** : reste à distance.",
    sw="| Dégâts de feu constants | Rien contre les boss de feu |\n| Immunisé au feu et au poison | L'eau te blesse |",
    errors="- **Tirer sous la pluie.**\n- **Viser un boss de feu.**",
    variants="Neutre : [Archer elfe](archer-elfe.html). Tout feu : [Pyromancien Blazeborn](pyromancien.html).")

B['archer-fantome'] = dict(
    title='Archer fantôme (Mobilité)', desc="Guide de l'archer mobile avec l'origine Phantom",
    pitch="un tireur qui traverse les murs en forme fantôme et tire là où personne ne l'attend.",
    who="Joueurs **agiles** qui aiment les parties furtives et inattendues.",
    style="En **forme fantôme** tu es invisible et tu traverses les blocs : tu repères un boss, tu tires depuis un mur, tu reprends forme pour récupérer. Zéphyr ajoute de la vitesse pour garder tes distances.",
    choices="| Origine | **Phantom** | Forme fantôme (invisible, traverse les blocs sauf obsidienne) ; **brûle au soleil** hors forme fantôme ; **−3 cœurs** ; faim rapide |\n| Classe | **Archer** | Tir plus précis |\n| Bénédiction | **Zéphyr** | Pas de dégâts de chute, **vol** après le dragon |",
    warn='!!! warning "Fragile et nocturne"\n    Tu brûles au soleil sans invisibilité. Prévois de la nourriture : la forme fantôme te donne faim.',
    gear="**Dégâts de flèche**, **cadence**, **esquive**, **vitesse**, **PV** (−3 cœurs).",
    talents="Voie du **Chasseur** : vitesse, esquive, critiques.",
    spells="",
    boss="- **Boss de mêlée** : reste dans le mur.\n- **Boss à projectiles** : change d'angle sans cesse.\n- **Boss en plein soleil** : attention aux brûlures.",
    sw="| Mobilité et furtivité | Brûle au soleil |\n| Esquive | Très peu de PV |",
    errors="- **Rester en forme humaine au soleil.**\n- **Oublier de manger.**",
    variants="Plus sûr : [Archer elfe](archer-elfe.html). Plus de burst : [Arbalétrier critique](arbalete.html).")

B['maitre-betes'] = dict(
    title='Maître des bêtes (Compagnons)', desc="Guide du chasseur qui combat avec ses compagnons",
    pitch="un chasseur qui combat avec ses bêtes : ours polaire, crocs, monture.",
    who="Joueurs qui aiment **jouer en équipe avec leurs compagnons** plutôt que seuls.",
    style="Tu apprivoises, tu **invoques** ton ours et ta monture, et tu tapes à côté d'eux. Les bêtes absorbent une partie de l'attention du boss ; *Crocs* et *Garde de crocs* ajoutent des dégâts et de la protection.",
    choices="| Origine | **Wild Cat** | Retombe sur les pattes ; silencieux ; grimpe aux murs ; vision de nuit ; carnivore ; fatigue dans l'eau |\n| Classe | **Beastmaster** | Animaux apprivoisés : bonus permanent de vie et de force ; potions partagées |\n| Bénédiction | **Skadi** | Bonus de projectiles et de froid |",
    warn="",
    gear="**PV**, **dégâts de mêlée**, **puissance d'invocation**. Une arme rapide.",
    talents="Voie du **Chasseur** : critiques et esquive.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Invocation de l'ours polaire** avant le combat.\n2. **Garde de crocs** pour te protéger.\n3. **Frappe de crocs** pour les dégâts.\n4. **Invocation de monture** pour te déplacer.\n",
    boss="- **Boss à gros PV** : tes compagnons font le dégât en continu.\n- **Boss en zone** : reste à distance de la zone.\n- **Boss rapides** : tes bêtes absorbent les coups.",
    sw="| Dégâts constants et protection | Dépend de tes compagnons |\n| Bêtes absorbent l'aggro | Moins de dégâts personnels |",
    errors="- **Laisser les bêtes mourir** : relance les sorts.\n- **Oublier de les soigner.**",
    variants="Invocateur pur : [Chaman nécromant](necro.html). Plus de mêlée : [Duelliste glacé](duelliste.html).")

B['brute-bastion'] = dict(
    title='Brute des bastions (Piglin)', desc="Guide du guerrier brute avec l'origine Piglin Brute",
    pitch="la force brute à l'état pur : grosse hache, gros dégâts, aucune subtilité.",
    who="Joueurs qui veulent **frapper fort et simplement**, sans bouclier ni sorts.",
    style="Une **hache** (or ou netherite) à deux mains, des coups lourds, un peu de vol de vie. Héphaïstos transforme chaque coup en pourcentage des PV du boss : ton arme tape plus fort sur les gros adversaires.",
    choices="| Origine | **Piglin Brute** | Casse les pierres naturelles sans pioche ; **bonus des haches** (or et netherite) ; **pas de bouclier** ; hydrophobe ; carnivore |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Héphaïstos** | Dégâts en % des PV actuels |",
    warn='!!! warning "Pas de bouclier"\n    L\'origine ne permet pas de tenir un bouclier. Ta défense vient des PV, de l\'armure et du vol de vie.',
    gear="Hache en **or ou netherite** (bonus d'origine), **critiques**, **vol de vie**, **PV**. L'équipement doré dure plus longtemps.",
    talents="Voie du **Forgeron** : dégâts et résistance.",
    spells="",
    boss="- **Boss à gros PV** : excellent (Héphaïstos).\n- **Boss rapides** : le vol de vie t'aide à encaisser.\n- **Boss aquatiques** : à éviter.",
    sw="| Gros dégâts simples | Pas de bouclier |\n| Haches très efficaces | Hydrophobe |",
    errors="- **Utiliser autre chose qu'une hache.**\n- **Rester dans l'eau.**",
    variants="Avec bouclier : [Chevalier gardien](chevalier.html). Critiques : [Berserker critique](berserker-critique.html).")

B['rodeur-ender'] = dict(
    title="Rôdeur d'Ender (Téléportation)", desc="Guide de l'assassin qui se téléporte avec l'origine Enderian",
    pitch="frappe, téléporte-toi, recommence : l'Enderian se téléporte naturellement et les sorts Ender font le reste.",
    who="Joueurs qui aiment les **combats rapides et imprévisibles**.",
    style="Tu **apparais**, tu frappes (*Frappes en écho* dédoublent les coups), tu te **téléportes** hors de portée avant la riposte et tu recommences. *Évasion* te protège pendant les enchaînements.",
    choices="| Origine | **Enderian** | Téléportation libre (perle sans dégâts) ; portée augmentée ; **hydrophobe** ; peur des citrouilles |\n| Classe | **Rogue** | Furtivité et dégâts dans le dos |\n| Bénédiction | **Loki** | Invisibilité accroupi ; téléportation vers un projectile après le dragon |",
    warn='!!! warning "L\'eau te blesse"\n    Les combats sous la pluie ou près de l\'eau sont à éviter.',
    gear="**Dégâts**, **critiques**, **esquive**, **vitesse**, **PV**.",
    talents="Voie du **Chasseur** : esquive et critiques.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Téléportation** : entrer et sortir.\n2. **Frappes en écho** : doubler tes coups.\n3. **Évasion** : éviter une série de coups.\n",
    boss="- **Boss rapides** : téléporte-toi à chaque gros coup.\n- **Boss à zone** : reste hors de portée entre deux frappes.\n- **Boss volants** : tire parti de ta portée.",
    sw="| Mobilité et burst | Fragile sans esquive |\n| Se téléporte librement | Hydrophobe |",
    errors="- **Rester au corps à corps trop longtemps.**\n- **Oublier l'eau.**",
    variants="Sans mana : [Lame fantôme](assassin.html). Avec vol de vie : [Duelliste glacé](duelliste.html).")

B['frappeur-foudre'] = dict(
    title='Frappeur de foudre (Mêlée + Foudre)', desc="Guide du guerrier-mage qui combine mêlée et foudre",
    pitch="épée au poing et foudre dans les veines : un guerrier-mage plus sûr qu'un mage pur.",
    who="Joueurs qui veulent **mêler sorts et mêlée** sans jongler avec trop de touches.",
    style="Tu **charges** (*Charge*), tu frappes à l'épée, tu te repositionnes avec *Pas du tonnerre* et tu finis avec *Éclair*. Thor renforce tes coups d'épée et la puissance de foudre.",
    choices="| Origine | **Human** | Aucune particularité : ni bonus ni malus |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Thor** | +9 d'attaque ; +15 % de dégâts après le dragon |",
    warn="",
    gear="**Dégâts de l'arme**, **puissance Foudre**, **critiques**, **PV**. Un bouclier est optionnel.",
    talents="Voie du **Forgeron** + quelques gemmes de foudre.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Charge** pour engager.\n2. **Pas du tonnerre** pour changer de cible.\n3. **Électrocution** en cours de combat.\n4. **Éclair** pour finir.\n",
    boss="- **Boss en mêlée** : tiens-le à l'épée.\n- **Boss à distance** : *Charge* pour fermer l'écart.\n- **Boss mobile** : *Pas du tonnerre*.",
    sw="| Polyvalent, bonne défense | Moins de dégâts qu'un spécialiste |\n| Mobilité décente | Demande de gérer sorts et épée |",
    errors="- **Négliger la mêlée** : ton arme est ta base.\n- **Épuiser le mana.**",
    variants="Plus de soins : [Templier sacré](templier-sacre.html). Plus de feu : [Templier du Phénix](paladin-feu.html).")

B['forteresse-ardente'] = dict(
    title='Forteresse ardente (Tank Blazeborn)', desc="Guide du tank de feu avec l'origine Blazeborn",
    pitch="un tank qui fait mal : immunisé au feu, il avance dans le Nether sans broncher, brûle ses ennemis et protège le groupe derrière un mur de flammes.",
    who="Joueurs qui veulent un **tank** avec une identité de feu.",
    style="Tu tiens la ligne avec ton **bouclier**, tu **brûles** les attaquants et tu poses un **mur de feu** pour couvrir l'équipe. *Élan ardent* sert à engager.",
    choices="| Origine | **Blazeborn** | Immunisé au feu, au poison, à la faim ; hydrophobe |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Héphaïstos** | Dégâts en % des PV actuels |",
    warn='!!! warning "Eau"\n    L\'eau te blesse : évite les rivières et la pluie.',
    gear="**PV**, **armure**, **blocage**, **dégâts de feu**. Un bouclier est obligatoire.",
    talents="Voie du **Forgeron** : défense et résistance.",
    spells="\n## Tes sorts et comment les enchaîner\n1. **Élan ardent** pour engager.\n2. **Mur de feu** pour protéger l'équipe.\n",
    boss="- **Boss de feu** : inutile.\n- **Boss de glace / morts-vivants** : excellent.\n- **Boss à gros dégâts** : le bouclier encaisse.",
    sw="| Tank avec dégâts de brûlure | Hydrophobe |\n| Immunisé au feu et au poison | Faible contre les boss de feu |",
    errors="- **Se battre dans l'eau.**\n- **Oublier le bouclier.**",
    variants="Sans handicap d'eau : [Chevalier gardien](chevalier.html). Plus de dégâts : [Rempart du Phénix](phenix-bouclier.html).")

B['garde-royal'] = dict(
    title='Garde royal (Tank chevalier)', desc="Guide du tank chevalier avec l'origine Knight",
    pitch="le chevalier de la légende : armure lourde, bouclier et l'Excalibur d'Arthur.",
    who="Joueurs qui aiment **tenir la ligne** et jouer un rôle de protecteur classique.",
    style="Tu portes **Skofnung** (épée de départ), tu bloques avec le bouclier et tu utilises *Culte du croissant* (soin + vitesse d'attaque pendant 30 s) quand le combat devient dur.",
    choices="| Origine | **Knight** | Skofnung (+1,5 dégâts) ; bonus d'attaque ; **vitesse réduite** ; **épées uniquement** (pas d'arc) ; réparation d'épée |\n| Classe | **Warrior** | Bonus de mêlée |\n| Bénédiction | **Arthur** | +60 % de dégâts aux morts-vivants ; dégâts de feu et vitesse d'attaque après le dragon |",
    warn='!!! warning "Épées seulement"\n    Aucun arc ni arbalète. Reste en mêlée ou en soutien.',
    gear="**PV**, **armure**, **blocage**, **dégâts de l'arme**. Un bouclier et une épée sont indispensables.",
    talents="Voie du **Forgeron**.",
    spells="",
    boss="- **Boss morts-vivants** (Wither, squelettes) : excellent grâce à Arthur.\n- **Boss rapides** : bloque et recule.\n- **Boss à distance** : demande un partenaire à distance.",
    sw="| Excellent contre les morts-vivants | Lent |\n| Tank solide, bon soin ponctuel | Pas d'arc ni de sorts |",
    errors="- **Utiliser un arc** (interdit).\n- **Négliger la vitesse** (réduite).",
    variants="Sans restrictions : [Chevalier gardien](chevalier.html). Avec feu : [Forteresse ardente](forteresse-ardente.html).")

B['mineur-blinde'] = dict(
    title='Mineur blindé (Farm de ressources)', desc="Guide du mineur robuste pour récolter minerais et gemmes",
    pitch="une carapace de Shulk, la voie du Mineur et une chance maximale : celui qui descend chercher les ressources pour tout le monde.",
    who="Joueurs qui aiment **creuser, miner et explorer**, et qui veulent aussi pouvoir se défendre.",
    style="Tu descends, tu mines vite et sans fatigue, tu repères les minerais rares grâce à la chance et tu remontes avec de quoi équiper tout le groupe. Ton armure naturelle te protège des mauvaises rencontres.",
    choices="| Origine | **Shulk** | Armure naturelle, 9 emplacements d'inventaire conservés à la mort ; se fatigue vite |\n| Classe | **Miner** | Mines la pierre plus vite ; casser des blocs ne te fatigue pas |\n| Bénédiction | **Athéna** | +12 armure ; −25 % de dégâts après le dragon |",
    warn="",
    gear="**Chance**, **butin**, **vitesse de minage**, **PV**, **armure**. Une pioche enchantée (Fortune).",
    talents="Voie du **Mineur** : *Cullinan*, *Estrela de Fura*, *Aristocrat*, *Greed*.",
    spells="",
    boss="Ce n'est pas un build de boss : pour un boss, passe sur un build de combat. Il est là pour **préparer l'équipement**.",
    sw="| Chance et butin maximaux | Peu de dégâts |\n| Armure naturelle | Fatigue rapide |",
    errors="- **Descendre sans nourriture.**\n- **Négliger la défense** : les cavernes sont dangereuses.",
    variants="Plus de butin : [Chasseur de trésors](chasseur-tresors.html). Plus de défense : [Tank Shulk](nain-tank.html).")

B['chef-de-guerre'] = dict(
    title='Chef de guerre (Cuisinier-combattant)', desc="Guide du guerrier cuisinier qui récupère entre deux combats",
    pitch="un guerrier qui se bat avec ce qu'il a cuisiné : voie du Cuisinier, régénération de Vishnu, repas buffs.",
    who="Joueurs qui aiment **cuisiner et jouer en autonomie** : peu de potions, beaucoup de nourriture.",
    style="Tu **cuisines** avant de partir (les plats sont plus rassasiants), tu tiens avec bouclier et épée, et tu récupères entre les combats grâce à la régénération passive. Moins de dépendance aux potions.",
    choices="| Origine | **Human** | Aucune particularité |\n| Classe | **Cook** | Nourriture plus rassasiante ; plus d'expérience en cuisine |\n| Bénédiction | **Vishnu** | Régénération |",
    warn="",
    gear="**PV**, **régénération**, **armure**, **dégâts de l'arme**. Bouclier conseillé.",
    talents="Voie du **Cuisinier** : nourriture et régénération, puis les nœuds de défense.",
    spells="",
    boss="- **Boss longs** : la régénération tient la distance.\n- **Boss à burst** : garde de la nourriture à portée.\n- **Groupes** : tu es un bon second tank.",
    sw="| Autonomie, peu de potions | Dégâts modestes |\n| Régénération | Dépend de la nourriture |",
    errors="- **Partir sans repas.**\n- **Négliger l'armure.**",
    variants="Plus de dégâts : [Berserker du Nord](berserker.html). Plus de défense : [Chevalier gardien](chevalier.html).")

for k, d in B.items():
    d.setdefault('spells', '')
    d.setdefault('warn', '')
    open(f'{OUT}/{k}.md', 'w', encoding='utf8').write(TPL.format(**d))
print(len(B), 'guides écrits')
