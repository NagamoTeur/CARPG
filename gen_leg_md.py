# -*- coding: utf-8 -*-
"""Écrit les guides des builds d'armes légendaires dans wiki_src/builds/leg-*.md. Les chiffres (stats, arbre, progression)
sont ajoutés par build_wiki.py. Relancer après modification : python3 gen_leg_md.py"""
import os

OUT = 'wiki_src/builds'

TPL = """---
title: {title}
desc: Build dédié à l'arme légendaire {weapon}
---

> **En une phrase :** {pitch}

## L'arme en bref

| | |
|---|---|
| **Arme** | {weapon} ({kind}) |
| **Comment l'obtenir** | {obtain} |
| **Dégâts de base** | {stats} |

{abil}

## Pour qui ?
{who}

## Style de jeu
{style}

## Pourquoi ce trio origine / classe / bénédiction ?
{trio}

## Équipement : quoi chercher
{gear}

## Talents
{talents}

## Face aux boss
{boss}

## Forces et faiblesses

| 👍 Forces | 👎 Faiblesses |
|---|---|
{sw}

## Les erreurs classiques
{err}

## Variantes
{var}

Voir aussi : [Adapter un build à son loot](adapter.html) · [Toutes les armes légendaires](index.html#leg-cisco).
{note}"""

NOTE_COLD = "\n!!! warning \"Chiffres très élevés\"\n    Les dégâts de ce build paraissent bien plus hauts que les autres : le planificateur cumule les **gemmes de froid** de façon multiplicative. Considère-les comme un **maximum théorique** et vérifie dans l'info-bulle de tes objets.\n"
NOTE_ACTIVE = "\n!!! tip \"Capacité active\"\n    La fiche chiffrée plus bas est calculée **sans** la capacité du clic droit. Dans le planificateur, coche **« Capacité active de l'arme »** (Personnage → Hypothèses de combat) pour voir l'attaque pendant les 10 secondes de la capacité.\n"


def abil(items):
    return '\n'.join(f'- **{a}** : {b}' for a, b in items)


W = []


def add(**k):
    k.setdefault('note', '')
    W.append(k)


# ---------------------------------------------------------------- Cisco
add(id='equillibrium', title="Gardien de l'Équilibre (Equillibrium éveillée)", weapon='Awakened Equillibrium', kind='épée', stats='31 (vitesse 1,0)',
    obtain="Forger **Slumbering Equillibrium** (épée dormante), puis l'**éveiller** avec un **Rubis divin** (Divine Core).",
    pitch="l'épée de l'équilibre : chaque coup retire 4 % des PV maximum de la cible en dégâts réels, qui ignorent l'armure.",
    abil=[('Effet unique – Equillibrium', "Chaque coup inflige **4 % des PV max de la cible** en dégâts réels (qui ignorent l'armure), tant que la cible a plus de 10 % de PV."), ('Clic droit', "Donne un **bouclier d'absorption** (≈ 16 PV) pendant 10 secondes. Recharge : 23 s.")],
    who="Joueurs qui veulent **faire tomber les boss à très gros PV** sans se préoccuper de leur armure.",
    style="Frappe lente mais lourde (vitesse 1,0). Tu tiens le boss en mêlée, tu poses ton bouclier d'absorption au bon moment et tu laisses les 4 % de PV max faire le travail : 25 coups suffisent pour tuer n'importe quel boss, quels que soient ses PV.",
    trio="**Demi-Dieu Lux** : dégâts de lumière et bonne vie ; **Guerrier** : bonus de mêlée ; **Arès** : critiques. L'arme ne dépend pas des critiques pour les 4 %, mais ils amplifient le reste.",
    gear="**Dégâts de l'arme** et **critiques** d'abord (ils s'ajoutent aux 4 %), **PV**, puis **vol de vie**. Un bouclier n'est pas possible à deux mains ; l'épée est à une main.",
    talents="Voie du **Forgeron** : dégâts de mêlée et résistance.",
    boss="- **Boss à gros PV** : meilleur choix : les 4 % deviennent énormes.\n- **Boss rapides** : utilise ton clic droit avant les gros coups.\n- **Boss très blindés** : parfait, l'armure ne compte pas.",
    sw="| Dégâts réels indépendants de l'armure | Vitesse lente |\n| Excellent contre les gros PV | Moins efficace contre les petits adversaires |",
    err="- **Négliger l'absorption** : elle sauve la vie.\n- **Frapper sans critiques** : ils amplifient les dégâts normaux.",
    var="Plus rapide : [Lame d'équilibre agile](leg-refined-equillibrium.html). Plus puissante : [Équilibre absolu](leg-absolute-equillibrium.html).")

add(id='refined-equillibrium', title="Lame d'équilibre agile (Equillibrium raffinée)", weapon='Refined Equillibrium', kind='épée', stats='24 (vitesse 1,6)',
    obtain="Modifier **Equillibrium** chez le forgeron Arthas (succès « Size Matters »).",
    pitch="la version légère de l'épée de l'équilibre : 2 % des PV max en dégâts réels par coup, mais frappe bien plus vite.",
    abil=[('Effet unique – Expedient Equillibrium', "**2 % des PV max de la cible** en dégâts réels à chaque coup (tant qu'elle a plus de 12,5 % de PV)."), ('Clic droit', "Absorption (≈ 8 PV) et **Hâte II** pendant 9 secondes. Recharge : 23 s.")],
    who="Joueurs qui veulent une arme légendaire **tôt** dans la partie.",
    style="Frappe rapide (1,6) donc plus de coups, donc plus de dégâts réels. Plus sûr que la version éveillée : tu frappes et tu recules.",
    trio="**Félin** : rapide et léger ; **Voleur** : esquive et critiques ; **Susanoo** : vitesse de déplacement et d'attaque.",
    gear="**Vitesse d'attaque**, **critiques**, **esquive**, **PV**.", talents="Voie du **Chasseur** : vitesse et esquive.",
    boss="- **Boss à gros PV** : 2 % par coup à vitesse 1,6 = très efficace.\n- **Boss rapides** : esquive.\n- **Boss de zone** : reste mobile.",
    sw="| Légendaire accessible tôt | Moins puissante que la version éveillée |\n| Rapide, pratique | Dégâts réels plus faibles |",
    err="- **Ignorer la Hâte du clic droit.**", var="Plus puissante : [Equillibrium éveillée](leg-equillibrium.html).")

add(id='absolute-equillibrium', title="Équilibre absolu (Absolute Equillibrium)", weapon='Absolute Equillibrium', kind='épée', stats='44 (vitesse 1,5)',
    obtain="Forger avec **Equillibrium**, **Nightfall**, une **Tablette d'ascension** et un **bloc de brightsteel arcanique**.",
    pitch="le sommet de la lignée de l'équilibre : 44 de dégâts de base et un clic droit qui donne un énorme bonus de PV et de vitesse.",
    abil=[('Clic droit', "Absorption (≈ 24 PV) et **Hâte III** pendant 9 secondes. Recharge : 29 s.")],
    who="Joueurs de **fin de jeu** qui veulent la meilleure épée polyvalente.",
    style="Rapide et lourde à la fois (44 à 1,5). Tu enchaînes les coups, tu poses ton absorption quand tu es touché et tu finis le boss.",
    trio="**Demi-Dieu Lux** : lumière et vie ; **Guerrier** ; **Thor** : +9 d'attaque puis +15 % de dégâts.",
    gear="**Dégâts**, **critiques**, **PV**, **vol de vie**.", talents="Voie du **Forgeron**.",
    boss="- **Tous les boss** : arme universelle.\n- **Boss rapides** : absorption avant la rafale.", sw="| Dégâts de base les plus hauts | Coûteuse à fabriquer |\n| Hâte + absorption | Pas d'effet spécial de dégâts réels |",
    err="- **Négliger la défense.**", var="Version à effet : [Equillibrium éveillée](leg-equillibrium.html).")

add(id='nightfall', title="Chevalier déchu (Nightfall)", weapon='Nightfall', kind='épée', stats='29 (vitesse 1,6)',
    obtain="Forger avec **Equillibrium**, un **Noyau sombre** et un **lingot de netherite**.",
    pitch="la lame d'un héros déchu : gros dégâts, exécution sous 25 % de vie, clic droit qui donne force et vitesse au prix du Wither sur toi.",
    abil=[('Effet unique – dégâts réels', "Tant que la cible a plus de 25 % de PV : **2 % de ses PV max** en dégâts réels à chaque coup."), ('Effet unique – exécution', "Quand la cible est sous **25 % de ses PV max**, le coup inflige des dégâts égaux à ≈ 83 % de ses PV restants (exécution)."), ('Clic droit', "**Force III** et **Hâte III** pendant 12 secondes, mais **Wither III** pendant 8 secondes sur toi. Recharge : 15 s.")],
    who="Joueurs qui aiment **prendre des risques** et **finir** les ennemis.",
    style="Tu ouvres avec le clic droit, tu frappes à pleine puissance et tu achèves les cibles à 25 %. Le Wither te blesse : active-le quand tu as des PV.",
    trio="**Demi-Dieu Umbra** : dégâts sombres et vitalité ; **Guerrier** ; **Arès** : critiques.",
    gear="**Dégâts**, **critiques**, **PV**, **vol de vie** (pour compenser le Wither).", talents="Voie du **Forgeron**.",
    boss="- **Boss à gros PV** : tu finis vite grâce à l'exécution.\n- **Boss à phases** : place le clic droit juste avant les derniers 25 %.\n- **Morts-vivants** : immunisés au Wither, donc sans risque.",
    sw="| Exécution sous 25 % | Le Wither te blesse |\n| Gros dégâts, force III | Moins sûre que les autres légendaires |",
    err="- **Activer le clic droit avec peu de PV** : le Wither peut te tuer.", var="Plus sûr : [Equillibrium éveillée](leg-equillibrium.html).")

add(id='supreme-nightfall', title="Canon de verre (Supreme Nightfall)", weapon='Supreme Nightfall', kind='épée', stats='42 (vitesse 2,0)',
    obtain="Arme légendaire de fin de jeu : l'épée de l'équilibre corrompue (succès « Balance Unbound »).",
    pitch="42 de dégâts à vitesse 2,0 et un clic droit qui met tes PV à 50 % mais donne une force et une vitesse énormes.",
    abil=[('Effet unique – dégâts réels', "Tant que la cible a plus de 10 % de PV : **≈ 6,7 % de ses PV max** (1/15) en dégâts réels à chaque coup."), ('Clic droit', "Passe tes PV à **50 %**, puis **Hâte VI**, **Vitesse VI** et **Force XXVI** pendant 9 secondes. Recharge : 29 s.")],
    who="Joueurs **expérimentés** qui aiment le **burst** et le risque.",
    style="Tu te places, tu déclenches le clic droit (tu perds la moitié de ta vie), et pendant 9 secondes tu fais des dégâts colossaux. Tu dois tuer vite ou fuir.",
    trio="**Demi-Dieu Umbra** : dégâts et vie ; **Guerrier** ; **Baba Yaga** : affaiblit tout ce que tu touches.",
    gear="**PV** (tu les perds), **vol de vie**, **dégâts**, **critiques**.", talents="Voie du **Forgeron**.",
    boss="- **Boss à gros PV** : burst 9 secondes.\n- **Boss à zone** : n'active pas sans issue de secours.",
    sw="| Burst le plus puissant du pack | Perds 50 % de tes PV |\n| Très rapide | À gérer avec précision |",
    err="- **Activer à faible PV** : tu pourrais mourir.", var="Plus sûr : [Équilibre absolu](leg-absolute-equillibrium.html).")

add(id='azure-thunder', title="Juge de la foudre (Azure Thunder)", weapon='Azure Thunder', kind='épée', stats='19 (vitesse 1,8)',
    obtain="Forger **Azure Thunder** (succès « Azure Judgment »).",
    pitch="une lame qui appelle la foudre : les coups déclenchent des éclairs, le clic droit frappe tous les ennemis proches.",
    abil=[('Effet unique – Azure Judgment', "Les coups font **périodiquement tomber la foudre** sur la cible."), ('Clic droit', "**9 dégâts de foudre** à toutes les entités proches et **Vitesse III** pendant 7 secondes. Recharge : 13 s.")],
    who="Joueurs qui aiment la **zone** et la mobilité.", style="Tu cours, tu frappes, tu déclenches l'éclair de zone quand tu es entouré. Très rapide.",
    trio="**Humain** : aucune contrainte ; **Voleur** : esquive ; **Thor** : dégâts de foudre.",
    gear="**Vitesse**, **critiques**, **esquive**, **dégâts**.", talents="Voie du **Chasseur**.",
    boss="- **Groupes** : l'éclair de zone fait le ménage.\n- **Boss isolé** : la foudre s'ajoute aux coups.",
    sw="| Zone + mobilité | Dégâts de base plus faibles |\n| Rapide | Foudre au hasard |", err="- **Attendre que la foudre fasse tout** : frappe.", var="Plus de puissance : [Gardien de l'Équilibre](leg-equillibrium.html).")

add(id='adjudicator', title="Faucheur sacré (Adjudicator)", weapon='Adjudicator', kind='faux', stats='20 (vitesse 1,6)',
    obtain="Forger avec un **Noyau de lumière**, un **Rubis divin**, un **bloc de brightsteel arcanique** et un **lingot de brightsteel** (succès « Lawbringer »).",
    pitch="la faux sacrée : elle achève les cibles sous 20 % de PV et son clic droit purifie les effets négatifs et soigne.",
    abil=[('Effet unique – Divine Justice', "Sous **20 % de PV**, le coup inflige ≈ 83 % des PV restants de la cible."), ('Clic droit', "**Retire** Poison, Wither, Cécité, Ténèbres, Fatigue minière, Faiblesse et d'autres effets négatifs, et **soigne**. Recharge : 24 s.")],
    who="Joueurs qui veulent un build **fiable** avec exécution et soins.", style="Tu frappes à grande portée (faux), tu achèves ce qui est sous 20 %, et tu te purifies quand les effets s'accumulent.",
    trio="**Justicière de Lumeria** : dégâts sacrés et soins ; **Guerrier ou Clerc** ; **Chiron** : XP ×3 et soins.",
    gear="**PV**, **dégâts**, **soins reçus**, **critiques**.", talents="Voie du **Forgeron** ou de l'**Enchanteur**.",
    boss="- **Boss à gros PV** : exécution sous 20 %.\n- **Boss à effets négatifs** : le clic droit purifie.",
    sw="| Exécution + soin | Dégâts moyens |\n| Purifie les effets | Moins explosive |", err="- **Oublier le clic droit** contre les poisons.", var="Version démoniaque : [Hellbrand](leg-hellbrand.html).")

add(id='skysplitter', title="Lancier des vents (Skysplitter)", weapon='Skysplitter', kind='lance', stats='21 (vitesse 1,9)',
    obtain="Forger avec un **Noyau de vent**, un **Rubis divin**, un **bloc de brightsteel arcanique**, un **diamant brillant** et une **verge de blaze** (succès « Wind Favoured »).",
    pitch="la lance du vent : aucun dégât de chute, saut amélioré en main, et un clic droit qui te propulse dans les airs.",
    abil=[('Effet unique – Windblessed', "En main : **annule les chutes** et donne **Saut amélioré**."), ('Clic droit', "Une rafale te **propulse** (×20 horizontalement). Recharge : 8 s.")],
    who="Joueurs qui aiment **la mobilité** et l'exploration.", style="Tu bondis, tu plonges, tu repars. Ton ennemi ne te touche presque jamais.",
    trio="**Avien** : plane ; **Explorateur** ; **Zéphyr** : vol après le dragon.", gear="**Vitesse**, **critiques**, **esquive**, **PV**.", talents="Voie du **Chasseur**.",
    boss="- **Boss de mêlée** : joue au chat et à la souris.\n- **Boss volants** : tu les atteins.", sw="| Mobilité extrême | Dégâts moyens |\n| Pas de dégâts de chute | Faible en défense |", err="- **Se propulser dans un mur** : attention.", var="Tout au sol : [Lame d'équilibre agile](leg-refined-equillibrium.html).")

add(id='glacies', title="Tribu du givre (Glacies)", weapon='Glacies', kind='hache double', stats='14 (vitesse 1,6)',
    obtain="Forger avec une **hache en diamant**, un **Noyau glacial**, un **Rubis divin**, un **diamant brillant** et un **lingot de brightsteel** (succès « Winter Harbinger »).",
    pitch="la hache double du chef de la tribu du givre : ralentit les ennemis et donne un énorme bonus d'armure en clic droit.",
    abil=[('Effet unique – Glacies\' Bite', "Chaque coup **ralentit** l'ennemi."), ('Clic droit – Entomb', "Enferme ton armure dans la glace pour un **bonus d'armure** considérable, au prix d'un **ralentissement**. Recharge : 19 s.")],
    who="Joueurs qui veulent un **tank offensif** avec contrôle.", style="Tu ralentis tout ce qui t'approche, tu actives Entomb quand ça devient chaud, et tu frappes en zone.",
    trio="**Frijani Drengr** : vol de vie et critiques ; **Guerrier** ; **Borée** : glace.", gear="**Armure**, **PV**, **dégâts**, **vol de vie**.", talents="Voie du **Forgeron**.",
    boss="- **Boss de mêlée** : ralenti, il perd son avantage.\n- **Boss à gros coups** : Entomb avant la rafale.", sw="| Tank + contrôle | Dégâts de base faibles |\n| Armure énorme | Ralentissement quand tu actives |", err="- **Activer Entomb sans espace pour reculer.**", var="Hache plus offensive : [Fell Ragnarok](leg-fell-ragnarok.html).", note=NOTE_COLD)

add(id='castor-pollux', title="Jumeaux célestes (Castor & Pollux)", weapon='Castor & Pollux', kind='dagues jumelles', stats='12–13 (vitesse 1,9–2,0)',
    obtain="Castor : **Noyau sombre**, **Rubis divin**, **améthyste nocturne**, **épée en diamant**. Pollux : **Noyau de lumière**, **Rubis divin**, **diamant brillant**, **bloc de brightsteel arcanique**, **épée en or** (succès « Celestial Duo »).",
    pitch="deux dagues légendaires, très rapides : Castor inflige la Brûlure gémellaire, Pollux ralentit.",
    abil=[('Castor', "Chaque coup inflige l'effet **Gemini Blight** (affaiblissement gémellaire)."), ('Pollux', "En main, **ralentit** les ennemis touchés.")],
    who="Joueurs **agiles** qui aiment les combats rapides.", style="Tu danses autour de l'ennemi avec les deux lames : vitesse maximale, effets cumulés.",
    trio="**Voleur** : furtivité ; **Duelliste** : critiques ; **Loki** : poison et invisibilité.", gear="**Vitesse**, **critiques**, **esquive**, **vol de vie**.", talents="Voie du **Chasseur**.",
    boss="- **Boss rapides** : la vitesse des dagues suit.\n- **Boss lents** : ralentissement de Pollux.", sw="| Vitesse et effets cumulés | Dégâts par coup faibles |\n| Très mobile | Fragile |", err="- **Se fier uniquement aux effets** : les dégâts comptent.", var="Une seule arme : [Lame d'équilibre agile](leg-refined-equillibrium.html).")

add(id='fell-ragnarok', title="Roi déchu (Fell Ragnarok)", weapon='Fell Ragnarok', kind='marteau', stats='21 (vitesse 1,6)',
    obtain="Forger avec **Glacies**, un **lingot de darksteel**, un **Rubis divin** et une **améthyste nocturne** (succès « Herald of Ragnarok »).",
    pitch="le marteau du Roi déchu : 25 dégâts réels en plus à chaque coup et la faiblesse infligée à chaque touche.",
    abil=[('Effet unique – Ragnarok', "**+25 dégâts réels** par coup (ignorent l'armure)."), ('Effet unique – Fensalir', "Inflige **Faiblesse** à chaque coup.")],
    who="Joueurs qui veulent **percer les armures**.", style="Coups lourds et réguliers, 25 dégâts fixes en plus : excellent contre les boss blindés.",
    trio="**Piglin Brute** : force brute ; **Guerrier** ; **Héphaïstos** : dégâts en % des PV actuels.", gear="**Dégâts**, **critiques**, **PV**, **vol de vie**.", talents="Voie du **Forgeron**.",
    boss="- **Boss très blindés** : parfait.\n- **Boss à gros PV** : régulier.", sw="| Dégâts réels fixes | Constant, pas de pic |\n| Faiblesse infligée | Pas de bouclier (Brute) |", err="- **Chercher un burst** : c'est une arme régulière.", var="Hache de glace : [Glacies](leg-glacies.html).")

add(id='hellbrand', title="Faux des enfers (Hellbrand)", weapon='Hellbrand', kind='faux', stats='31 (vitesse 1,6)',
    obtain="Forger avec **Adjudicator**, un **lingot de démonium**, un **Rubis divin**, un **Noyau de braise** et une **améthyste nocturne**.",
    pitch="la faux du 2ᵉ Roi déchu : chaque coup marque la cible et la marque triple les dégâts de la faux.",
    abil=[('Effet unique – Hellbrand', "Chaque coup inflige l'effet **Hellbrand** pendant 5 s, qui **triple** les dégâts de l'arme."), ('Clic droit', "Si tes PV sont **au-dessus de 50 %** : ton attaque est multipliée par **(1 + 0,6 × ton armure)** pendant 10 s. Sinon : **invulnérable** 5 s. Recharge : 18 s.")],
    who="Joueurs de **fin de jeu** qui veulent le burst le plus fou.", style="Tu marques, tu actives le clic droit, et pendant 10 secondes ta faux frappe à des niveaux absurdes. Sous 50 % de PV, le même clic droit te protège 5 secondes.",
    trio="**Chevalier Phénix** : dégâts de feu ; **Guerrier** ; **Râ** : dégâts de feu.", gear="**Armure** (elle multiplie ton attaque !), **dégâts**, **critiques**, **PV**.", talents="Voie du **Forgeron** : armure et dégâts.",
    boss="- **Boss à gros PV** : le pic de 10 s fait fondre n'importe quoi (plafond Cataclysm à surveiller).\n- **Boss rapides** : le clic droit sous 50 % te protège.",
    sw="| Burst extrême | Dépend de l'armure |\n| Invulnérabilité de secours | Recharge de 18 s |", err="- **Utiliser le clic droit à 51 %** alors que tu risquais de mourir.", var="Version sacrée : [Adjudicator](leg-adjudicator.html).", note=NOTE_ACTIVE)

add(id='frostfang', title="Louve d'argent (Frostfang)", weapon='Frostfang', kind='épée', stats='28 (vitesse 2,5)',
    obtain="Forger avec **Glacies**, un **lingot de frigidium**, un **Noyau glacial**, un **Rubis divin** et un **diamant brillant**.",
    pitch="vitesse d'attaque 2,5 et un bouclier passif : aucun coup ne peut te retirer plus de 40 % de tes PV max.",
    abil=[('Effet unique – Silver Wolf\'s Splendor', "En main, **aucune attaque ennemie ne dépasse 40 % de tes PV max**."), ('Clic droit', "Si tes PV sont **au-dessus de 50 %** : ton attaque est multipliée par **(1 + 0,15 × tes PV max)** pendant 10 s. Sinon : **tes PV remontent à 70 %** et tu gagnes de la vitesse. Recharge : 15 s.")],
    who="Joueurs qui veulent **ne jamais se faire one-shot**.", style="Tu es presque intouchable : un coup ne peut pas te prendre plus de 40 %. Active le clic droit pour des dégâts massifs, ou pour te soigner.",
    trio="**Chat sauvage** : furtif et agile ; **Voleur** ; **Skadi** : projectiles et froid.", gear="**PV max** (ils multiplient ton attaque !), **vitesse**, **critiques**.", talents="Voie du **Chasseur**.",
    boss="- **Boss à gros coups** : plafonnés à 40 %.\n- **Boss rapides** : tu rends coup pour coup.", sw="| Jamais one-shot | Ne protège pas des dégâts continus |\n| Vitesse 2,5 | Dégâts de base moyens |", err="- **Se croire invincible** : les coups à répétition passent.", var="Plus de dégâts : [Hellbrand](leg-hellbrand.html).", note=NOTE_ACTIVE)

# ---------------------------------------------------------------- Celestisynth
add(id='solaris', title="Épée du soleil (Solaris)", weapon='Solaris', kind='épée', stats='18 (vitesse 1,6)', obtain="Arme **Celestisynth** (succès « Obtain a Solaris »).",
    pitch="immunité au feu en main et chaque coup enflamme la cible.",
    abil=[('Passif – Ember\'s Blessing', "Immunité au **feu** en main ; les coups **enflamment** la cible quelques secondes."), ('Compétence – Spinning Flames', "Tourbillon de flammes autour de toi ; charge enflammée vers l'avant.")],
    who="Joueurs qui aiment le **feu** et la mobilité.", style="Tu t'élances dans les flammes sans crainte, tu frappes en tourbillon et tu laisses brûler.",
    trio="**Humain** ; **Guerrier** ; **Râ** : dégâts de feu.", gear="**Dégâts de feu**, **critiques**, **PV**.", talents="Voie du **Forgeron**.",
    boss="- **Boss de glace / morts-vivants** : idéal.\n- **Boss de feu** : à éviter.", sw="| Feu + mobilité | Rien contre le feu |", err="- **Attaquer un boss de feu.**", var="Version lave : [Molten Edge](leg-molten-edge.html).")

add(id='crescentia', title="Lune de guerre (Crescentia)", weapon='Crescentia', kind='épée', stats='18 (vitesse 1,6)', obtain="Arme **Celestisynth** (succès « Obtain a Crescentia »).",
    pitch="un niveau de résistance en main et chaque coup ralentit la cible.",
    abil=[('Passif – Heavy-Hitter', "**Résistance** aux dégâts en main ; les coups **ralentissent**."), ('Compétences', "Barrage lunaire (rafale de coups) ; ondes de croissants à longue portée.")],
    who="Joueurs qui veulent une arme **robuste** et **polyvalente**.", style="Tu tiens, tu ralentis, tu libères le barrage quand ils sont groupés.",
    trio="**Humain** ; **Guerrier** ; **Athéna** : armure.", gear="**PV**, **armure**, **dégâts**, **critiques**.", talents="Voie du **Forgeron**.",
    boss="- **Boss de mêlée** : ralenti et résistance.\n- **Boss à distance** : ondes de croissants.", sw="| Résistance + contrôle | Dégâts moyens |", err="- **Rester immobile** : mouvement.", var="Plus agressive : [Gardien de l'Équilibre](leg-equillibrium.html).")

add(id='breezebreaker', title="Danseur du vent (Breezebreaker)", weapon='Breezebreaker', kind='épée', stats='15 (vitesse 1,6)', obtain="Arme **Celestisynth** (succès « Obtain a Breezebreaker »).",
    pitch="saut énorme et aucune chute, mais 2,3 fois plus de dégâts subis. Le vent comme arme.",
    abil=[('Passif – Agile', "**Saut énorme** et immunité à la chute, mais **×2,3 de dégâts subis** des autres sources."), ('Passif – Benevolent Soul', "Toutes les 15 compétences : vitesse et **recharge divisée par deux** pendant 8 s."), ('Compétences', "Galestorm, Galestorm double, tornade, ruée du vent et roue de vent verticale.")],
    who="Joueurs **experts** qui ne se font jamais toucher.", style="Tu sautes, tu lances des lames de vent, tu te déplaces sans cesse. Un seul coup mal esquivé fait très mal.",
    trio="**Élytrien** : ailes ; **Voleur** ; **Zéphyr** : vol.", gear="**Esquive**, **PV**, **critiques**, **vitesse**. **Armure limitée** : l'Élytrien ne peut porter aucune pièce au-dessus de 2 / 5 / 4 / 1 d'armure (cotte de mailles) ; ce build utilise donc de la cotte de mailles vanilla avec de bons affixes.", talents="Voie du **Chasseur**.",
    boss="- **Boss de mêlée** : reste en l'air.\n- **Boss à zone** : ne reste jamais.", sw="| Mobilité et compétences multiples | ×2,3 dégâts subis |", err="- **Rester au sol.**", var="Moins risqué : [Lancier des vents](leg-skysplitter.html).")

add(id='aquaflora', title="Fleur des eaux (Aquaflora)", weapon='Aquaflora', kind='épée', stats='13 (vitesse 1,6)', obtain="Arme **Celestisynth** (succès « Obtain an Aquaflora »).",
    pitch="des pétales qui traversent une rangée d'ennemis, un état floraison et un déchaînement de jusqu'à 20 coups pendant lequel tu es immunisé.",
    abil=[('Passif – Swipethrough', "Chaque compétence donne **3 secondes de vitesse**."), ('Compétences', "Pétales perçants ; coup de tête qui active la **floraison** ; déchaînement de 20 coups pendant lequel tu es **immunisé** (40+ avec deux armes identiques) ; recul floral.")],
    who="Joueurs qui aiment les **combos**.", style="Tu enchaînes pétales, floraison puis déchaînement : un cycle de dégâts très gratifiant.",
    trio="**Merling** : eau ; **Duelliste** ; **Neptune**.", gear="**Vitesse**, **critiques**, **dégâts**, **PV**.", talents="Voie du **Chasseur**.",
    boss="- **Boss à gros coups** : déchaînement = immunité.\n- **Groupes** : pétales.", sw="| Combos + immunité | Dégâts de base faibles |", err="- **Lancer le déchaînement sans cible.**", var="Version aérienne : [Breezebreaker](leg-breezebreaker.html).")

# ---------------------------------------------------------------- Autres
add(id='dreadsteel-scythe', title="Faux d'acier funeste (Dreadsteel Scythe)", weapon='Dreadsteel Scythe', kind='faux', stats='25 (vitesse 1,6)', obtain="Mod **Dreadsteel** (équipement d'acier funeste).",
    pitch="en attaquant, une lame magique en rotation traverse tous les ennemis et leur armure.",
    abil=[('Passif', "En attaquant, **invoque une lame magique en rotation** qui perce tous les ennemis et leur armure.")],
    who="Joueurs qui veulent une **faux** polyvalente.", style="Grande portée, lame fantôme qui ignore l'armure : excellente contre les groupes.",
    trio="**Humain** ; **Guerrier** ; **Baba Yaga** : affaiblit.", gear="**Dégâts**, **critiques**, **PV**.", talents="Voie du **Forgeron**.",
    boss="- **Groupes** : la lame touche tout.\n- **Boss blindés** : perce l'armure.", sw="| Faux simple | Pas d'effet spécial majeur |", err="- **Rester au corps à corps** : utilise la portée.", var="Faux sacrée : [Adjudicator](leg-adjudicator.html).")

add(id='soul-render', title="Tourbillon d'âmes (Soul Render)", weapon='Soul Render', kind='épée', stats='15 (vitesse 1,1)', obtain="Arme du **Harbinger** (Cataclysm).",
    pitch="fonce dans les ennemis avec la Rush of Render et, en s'accroupissant, fait pleuvoir des hallebardes fantômes en spirale.",
    abil=[('Compétence', "**Rush of Render** : ruée vers l'avant ; en s'accroupissant : **hallebardes fantômes** en spirale.")],
    who="Joueurs qui aiment la **zone**.", style="Ruée, spirale, ruée : tu nettoies les vagues.",
    trio="**Demi-Dieu Umbra** ; **Guerrier** ; **Arès**.", gear="**Dégâts**, **critiques**, **PV**.", talents="Voie du **Forgeron**.",
    boss="- **Vagues** : spirale.\n- **Boss** : ruée sur le boss.", sw="| Zone | Lente |", err="- **Spammer la spirale sans cible.**", var="Épée de zone : [Thunderbrand](leg-thunderbrand.html).")

add(id='sunfire', title="Étendard solaire (Sunfire)", weapon='Sunfire', kind='arme lourde', stats='26 (vitesse 1,0)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="chance de te soigner à chaque coup et pose un étendard qui enflamme, blesse et ralentit les ennemis tout en donnant force et vie à tes alliés.",
    abil=[('Effet unique – Righteous Standard', "Chance de **régénération** à chaque coup ; **pose un étendard** qui enflamme, blesse et ralentit les ennemis et donne **force et vie** aux alliés proches.")],
    who="Joueurs qui jouent **en équipe**.", style="Tu poses l'étendard au milieu du combat, tu frappes en dessous : tout le groupe profite.",
    trio="**Phénix** : feu ; **Guerrier** ; **Arthur** : feu et morts-vivants.", gear="**PV**, **armure**, **dégâts**.", talents="Voie du **Forgeron**.",
    boss="- **Groupe** : étendard = buff pour tous.\n- **Boss de glace** : le feu aide.", sw="| Soutien d'équipe | Lent (vitesse 1,0) |", err="- **Poser l'étendard au mauvais endroit.**", var="Version obscure : [Harbinger](leg-harbinger.html).")

add(id='harbinger', title="Étendard abyssal (Harbinger)", weapon='Harbinger', kind='épée', stats='26 (vitesse ≈ 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="chance d'infliger la faiblesse ; l'étendard abyssal attire, blesse et ralentit les ennemis et donne de la vitesse à tes alliés.",
    abil=[('Effet unique – Abyssal Standard', "Chance d'infliger **Faiblesse** ; **étendard abyssal** : attire, blesse et ralentit, et donne **Hâte** aux alliés.")],
    who="Joueurs qui aiment **contrôler** le combat.", style="Tu regroupes les ennemis autour de l'étendard et tu les frappes de près.",
    trio="**Demi-Dieu Umbra** ; **Guerrier** ; **Loki**.", gear="**Dégâts**, **critiques**, **PV**.", talents="Voie du **Forgeron**.",
    boss="- **Groupes** : regroupe.\n- **Boss** : faiblesse.", sw="| Contrôle d'équipe | Dégâts moyens |", err="- **Oublier l'étendard.**", var="Version solaire : [Sunfire](leg-sunfire.html).")

add(id='molten-edge', title="Rugissement de lave (Molten Edge)", weapon='Molten Edge', kind='épée', stats='17 (vitesse 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="plus tu perds de vie, plus tu gagnes de force et de vitesse ; le rugissement enflamme tout autour de toi.",
    abil=[('Effet unique – Molten Roar', "Chance d'**enflammer** l'ennemi (ou toi) ; à PV bas : **régénération** ; **force et vitesse** proportionnelles à tes PV manquants ; le rugissement repousse et enflamme les ennemis proches et donne **résistance et ruée**.")],
    who="Joueurs qui aiment **jouer à PV bas**.", style="Plus tu es blessé, plus tu es fort : tu rugis quand tu es entouré.",
    trio="**Chevalier Phénix** ; **Guerrier** ; **Héphaïstos**.", gear="**PV**, **dégâts de feu**, **critiques**.", talents="Voie du **Forgeron**.",
    boss="- **Boss de glace** : excellent.\n- **Boss de feu** : à éviter.", sw="| Plus fort à PV bas | Risque de mourir |", err="- **Descendre trop bas.**", var="Épée solaire : [Solaris](leg-solaris.html).")

add(id='stormbringer', title="Parade-éclair (Stormbringer)", weapon='Stormbringer', kind='épée', stats='14 (vitesse ≈ 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="concentre l'énergie dans la lame pour bloquer, et si tu pares au bon moment tu renvoies l'ennemi en l'air et réduis ta recharge.",
    abil=[('Effet unique – Shock Deflect', "Bloque brièvement les attaques ; si tu **pares au bon moment**, dégâts, projection de l'ennemi et recharge réduite. **Les parades successives** renforcent les dégâts et la recharge.")],
    who="Joueurs **techniques** qui aiment le timing.", style="Tu attends le coup, tu pares, tu renvoies. Un build de skill.",
    trio="**Humain** ; **Guerrier** ; **Thor**.", gear="**PV**, **armure**, **dégâts**.", talents="Voie du **Forgeron**.",
    boss="- **Boss à gros coups** : parade parfaite.\n- **Boss rapides** : plus difficile.", sw="| Parades très gratifiantes | Demande du timing |", err="- **Parer trop tôt.**", var="Plus simple : [Chevalier gardien](chevalier.html).")

add(id='thunderbrand', title="Charge foudroyante (Thunderbrand)", weapon='Thunderbrand', kind='épée', stats='19 (vitesse ≈ 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="charge ton arme puis fonce en avant : énormes dégâts à tous ceux sur ton chemin.",
    abil=[('Effet unique – Thunder Blitz', "Chance de **rafraîchir la capacité** à chaque coup ; tu te ralentis et charges l'arme (dégâts de zone), puis **fonce** avec **Hâte** et inflige d'**énormes dégâts** à tous sur ton chemin.")],
    who="Joueurs qui aiment les **charges**.", style="Charge, ruée, recommence : un build explosif.",
    trio="**Humain** ; **Voleur** ; **Zéphyr**.", gear="**Dégâts**, **critiques**, **vitesse**.", talents="Voie du **Chasseur**.",
    boss="- **Groupes** : traverse tout.\n- **Boss** : ruée au bon moment.", sw="| Burst en ligne | Immobile pendant la charge |", err="- **Charger sans cible.**", var="Épée de zone : [Soul Render](leg-soul-render.html).")

add(id='icewhisper', title="Aura de givre (Icewhisper)", weapon='Icewhisper', kind='épée', stats='19 (vitesse ≈ 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="une aura de givre blesse et ralentit tout ce qui t'approche ; au prix de ta faim tu déclenches un blizzard.",
    abil=[('Effet unique – Permafrost', "En main : **aura de givre** qui blesse et ralentit dans un rayon ; en vidant ta **faim** : **blizzard** qui blesse davantage.")],
    who="Joueurs qui veulent **contrôler une zone**.", style="Tu restes au centre, l'aura travaille pour toi, le blizzard finit.",
    trio="**Frijani Drengr** ; **Guerrier** ; **Borée**.", gear="**PV**, **armure**, **dégâts**, **nourriture**.", talents="Voie du **Forgeron**.",
    boss="- **Groupes** : aura.\n- **Boss de feu** : excellent.", sw="| Zone passive | Consomme la faim |", err="- **Utiliser le blizzard à jeun.**", var="Brise-glace : [Livyatan](leg-livyatan.html).", note=NOTE_COLD)

add(id='livyatan', title="Brise-glace (Livyatan)", weapon='Livyatan', kind='épée', stats='18 (vitesse ≈ 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="emprisonne les ennemis dans la glace (ils ne subissent plus de dégâts) puis la brise pour faire des dégâts d'autant plus forts que tu la brises tôt.",
    abil=[('Effet unique – Frost Shatter', "Chance d'**emprisonner** les ennemis dans la glace ; tu peux la **briser plus tôt** pour plus de dégâts.")],
    who="Joueurs qui aiment le **timing**.", style="Tu piéges, tu laisses le temps passer, tu brises : la glace amplifie tout.",
    trio="**Merling** ; **Duelliste** ; **Neptune**.", gear="**Dégâts**, **critiques**, **PV**.", talents="Voie du **Chasseur**.",
    boss="- **Boss de mêlée** : piège.\n- **Boss de feu** : ok.", sw="| Contrôle + burst | Il faut briser au bon moment |", err="- **Briser trop tard.**", var="Aura de givre : [Icewhisper](leg-icewhisper.html).")

add(id='watcher', title="L'Observateur (The Watcher)", weapon='The Watcher', kind='claymore', stats='19 (vitesse ≈ 1,6)', obtain="Mod **Simply Swords** (arme unique).",
    pitch="chance d'arracher la vie d'une cible blessée pour te donner de l'absorption, et de siphonner la santé des ennemis proches.",
    abil=[('Effet unique – Omen', "Chance d'**arracher la vie** d'une cible sous un certain seuil de PV et de t'en donner une partie en **absorption**."), ('Effet unique – Watcher', "Chance de **siphonner la santé** des ennemis proches pour te **soigner**.")],
    who="Joueurs qui aiment le **vol de vie**.", style="Tu frappes, tu absorbes, tu repars : tu ne t'arrêtes jamais.",
    trio="**Demi-Dieu Lux** ; **Voleur** ; **Loki**.", gear="**Vol de vie**, **PV**, **dégâts**.", talents="Voie du **Chasseur**.",
    boss="- **Boss à gros PV** : vol de vie.\n- **Groupes** : siphon.", sw="| Autonomie | Dégâts moyens |", err="- **Se reposer sur l'absorption.**", var="Autre vol de vie : [Duelliste glacé](duelliste.html).")

for d in W:
    d['abil'] = '## Capacités\n' + abil(d['abil'])
    d['sw'] = d['sw'].strip()
    open(f"{OUT}/leg-{d['id']}.md", 'w', encoding='utf8').write(TPL.format(**d))
print(len(W), 'guides écrits')
