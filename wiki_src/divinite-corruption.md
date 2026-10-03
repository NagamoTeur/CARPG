---
title: Divinité et Corruption
desc: Les deux types de dégâts bonus de Cisco : comment ils marchent, comment les obtenir, et les builds dédiés
---

> **En une phrase :** la **Divinité** et la **Corruption** ajoutent, à **chaque coup que tu portes** (épée, flèche ou sort), un second coup séparé qui **ignore l'armure**. La Divinité peut faire un **critique divin** énorme ; la Corruption peut **retirer un pourcentage des PV max** de la cible.

Ces deux statistiques viennent du mod de Cisco (*Cisco's Content Unbound*). Tout ce qui suit est lu dans son code.

## Comment ça marche

À chaque fois que tu infliges des dégâts (coup d'épée, flèche, sort, tic d'un sort continu), le jeu lance juste après un **coup supplémentaire** :

- il **ignore l'armure** de la cible ;
- il est **séparé** de ton coup : il ne compte pas tes critiques, mais il ne peut pas non plus être « bloqué » par l'invulnérabilité de la cible ;
- face aux boss de Cataclysm, le **plafond de dégâts par coup s'applique à chaque coup séparément**.

!!! tip "Plus tu touches souvent, plus ça rapporte"
    Une arme rapide, un arc rapide ou un sort qui touche plusieurs fois (rayon, zone, souffle) déclenche la Divinité ou la Corruption **à chaque touche**.

### Divinité : régulière, avec des critiques divins

Le coup divin vaut ta **valeur de Divinité**. Il a une chance de **critique divin**, qui grandit avec ta valeur :

| Ta Divinité | Chance de critique divin | Multiplicateur | Moyenne par coup |
|---|---|---|---|
| 20 | 15 % | ×4 | 29 |
| 60 | 20 % | ×5 | 108 |
| 120 | 20 % | ×6 | 240 |
| 240 | 25 % | ×7 | 600 |
| 370 | 25 % | ×8 | 1 018 |
| 600 | 30 % | ×9 | 2 040 |
| 800 | 30 % | ×10 | 2 960 |

Paliers exacts : au-dessus de 50, 100, 200, 300, 500 et 700, le multiplicateur gagne +1 ; au-dessus de 50, 200 et 500, la chance gagne +5 %.

### Corruption : la « décomposition » anti-boss

Le coup corrompu vaut ta **valeur de Corruption**. Il a une chance de **décomposition**, qui ajoute un **pourcentage des PV max de la cible** :

| Ta Corruption | Chance de décomposition | PV max retirés | Moyenne par coup contre un boss à 10 000 PV |
|---|---|---|---|
| moins de 50 | 5 % | 6 % | ≈ valeur + 30 |
| 50 | 10 % | 7 % | ≈ 130 (à 60) |
| 100 | 15 % | 8 % | ≈ 240 (à 120) |
| 250 | 20 % | 9 % | ≈ 550 (à 370) |
| 400 | 25 % | 10 % | ≈ 650 (à 400) |
| 550 | 30 % | 11 % | ≈ 930 (à 600) |
| 800 | 35 % | 12 % | ≈ 1 220 (à 800) |

**Divinité ou Corruption ?** La Divinité est meilleure contre **tout le monde** (elle ignore l'armure et ses critiques sont énormes). La Corruption est meilleure contre les **très gros PV** (boss, monstres de haut niveau), parce qu'elle s'en prend à leur maximum de vie.

## Comment en obtenir

| Source | Divinité | Corruption |
|---|---|---|
| **Gemme de Divinité** (casque, plastron, jambières, bottes) | +5 / +10 / +15 / +20 / +30 / +40 selon la rareté (commune → ancienne) | — |
| **Gemme de Corruption** (mêmes pièces) | — | +5 / +10 / +15 / +20 / +30 / +40 |
| **Absolute Equillibrium** | +50 | — |
| **Adjudicator** (la faux sacrée) | +20 | — |
| **Pollux** | +10 | — |
| **Supreme Nightfall** | — | +50 |
| **Fell Ragnarok** | — | +50 |
| **Castor** | — | +10 |

Le bonus d'arme ne compte que si l'arme est **dans ta main principale**. Avec 4 pièces d'armure à 2 sockets et des gemmes mythiques, tu atteins déjà **240** : 25 % de critiques divins ×7, ou 15 % de décomposition (8 % des PV max).

## Les deux Ascensions (fin de jeu)

| | Ascension divine | Ascension déchue |
|---|---|---|
| **Comment** | Vaincre le **Roi déchu** (succès *Regicide*) | Vaincre **Cisco descendu** (succès *Ashes of Allegiance*) |
| **Bonus** | Divinité **+50 % de tes dégâts d'attaque** ; chaque coup divin te **soigne de 1 %** de ses dégâts | Corruption **+3 % de tes PV max** ; chaque décomposition te **soigne de 15 % de tes PV max** |
| **Malus** | Corruption **×0,25** | Divinité **×0,25** |

!!! warning "Une seule à la fois"
    Les deux ascensions s'excluent : celle que tu obtiens en premier reste active. Choisis ta voie avant d'aller tuer le deuxième boss.

## Les builds dédiés

| Build | Type | Pour qui |
|---|---|---|
| [Champion divin](builds/div-champion.html) | Divinité, mêlée | Le plus simple : épée de l'équilibre et gemmes de Divinité |
| [Danseur céleste](builds/div-jumeaux.html) | Divinité, vitesse | Beaucoup de coups, beaucoup de critiques divins |
| [Archer de lumière](builds/div-archer.html) | Divinité, distance | Ignorer l'armure de loin |
| [Prêtre de la lumière](builds/div-mage.html) | Divinité, magie | Soigneur qui fait aussi mal |
| [Seigneur corrompu](builds/fell-seigneur.html) | Corruption, mêlée | Anti-boss avec Supreme Nightfall |
| [Marteau du Roi déchu](builds/fell-roi.html) | Corruption, marteau | Fell Ragnarok : dégâts réels + Corruption |
| [Archer de la décomposition](builds/fell-archer.html) | Corruption, distance | Ronger les boss de loin |
| [Nécromancien corrompu](builds/fell-mage.html) | Corruption, magie | Sorts de sang qui touchent souvent |
| [Lame de l'ombre corrompue](builds/fell-dague.html) | Corruption, vitesse | Castor, coups rapides |

Dans le [planificateur](../pob/index.html), les valeurs de Divinité et de Corruption s'affichent dans la fiche ; l'**Ascension** se choisit dans *Personnage → Hypothèses de combat*, et le **simulateur de boss** compte la décomposition en pourcentage des PV du boss.

*Calcul du planificateur : pour les sorts, il compte un seul déclenchement par lancer (prudent) ; un sort qui touche plusieurs fois rapporte plus en jeu.*
