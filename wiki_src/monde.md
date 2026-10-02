---
title: Le monde & le danger
desc: Niveaux des monstres, distance, infernaux, épreuves sombres
---

## La règle d'or : plus tu t'éloignes, plus c'est dur

Chaque monstre a un **niveau**. Ce niveau est calculé quand il apparaît :

> niveau = niveau de départ de la zone + (0,008 × distance au point d'apparition du monde) + un petit bonus de profondeur sous le sol + un bonus aléatoire (+ le « niveau du monde » si tu as relevé la difficulté)

Chaque niveau ajoute (réglages du pack) : **+9 % de PV**, **+14 % de dégâts**, **+8 % d'armure**, et un peu de vitesse. Les projectiles et les explosions des monstres gagnent aussi des bonus (+7 % et +10 % par niveau).

| Distance du spawn | Niveau | PV des monstres | Dégâts | Armure |
|---|---|---|---|---|
| 0 | 1 | ×1,1 | ×1,1 | ×1,1 |
| 500 blocs | 5 | ×1,5 | ×1,7 | ×1,4 |
| 1 000 blocs | 9 | ×1,8 | ×2,3 | ×1,7 |
| 2 000 blocs | 17 | ×2,5 | ×3,4 | ×2,4 |
| 3 000 blocs | 25 | ×3,3 | ×4,5 | ×3,0 |
| 5 000 blocs | 41 | ×4,7 | ×6,7 | ×4,3 |
| 10 000 blocs | 81 | ×8,3 | ×12,3 | ×7,5 |

### Calculateur

<div id="dangercalc" class="card calc" markdown="0">
<div class="row"><label>Distance du spawn</label><input id="dc-dist" type="number" value="2000" step="250" min="0"> blocs</div>
<div class="row"><label>Altitude (Y)</label><input id="dc-y" type="number" value="64"></div>
<div class="row"><label>Niveau du monde</label><select id="dc-wl"><option value="0">Normal</option><option value="150">Ascendant +150</option><option value="300">Divin +300</option><option value="500">Hellheim +500</option></select></div>
<div id="dc-out" class="note"></div>
</div>

## Chaque dimension a son niveau de base

| Dimension | Niveau de départ | Niveaux par bloc |
|---|---|---|
| Overworld | 1 | 0,008 (+0,01 par bloc sous Y=64) |
| Nether | **35** | 0,02 |
| Aether | 35 | 0,022 |
| End | **150** | 0,03 |

Autrement dit : **le Nether est déjà du niveau 35+ dès l'entrée**, et l'End du niveau 150+. C'est pour ça qu'il faut être prêt avant d'y aller.

## Les boss ont leur propre niveau

Chaque boss a un niveau de départ propre (voir [Guide des boss](boss.html)) :

- **Palier 1** : à partir du niveau 25 (+1 à 10 au hasard), pas de hausse avec la distance.
- **Palier 2** : niveau 50 mini (+1 à 15), petite hausse avec la distance.
- **Palier 3** : niveau 85 mini (+1 à 20), hausse modérée.
- **Gardiens** : toujours infernaux, niveau un peu plus haut que le palier qu'ils protègent. **Il faut les vaincre pour passer au palier suivant.**
- **Ender Dragon** : niveau 100 à 150. L'End entier est niveau 150+.

## Monstres spéciaux à repérer

| Monstre | Comment le reconnaître | Pourquoi c'est dangereux |
|---|---|---|
| **Infernal** | Particules violettes | Stats et pouvoirs aléatoires en plus ; environ 1 monstre hostile sur 15 est « élite », puis plus rare pour « ultra » et « infernal » ; ils lâchent de beaux matériaux (Sable arcanique, sceau de socket, poussière de gemme…) |
| **Maître de donjon** | Garde un coffre de donjon | Il faut le tuer pour ouvrir le coffre |
| **Boss apothique** | Son + message quand il apparaît près de toi | Gros loot arcanique ; il a son équipement et ses affixes |
| **Mini-boss royaux** | Variantes « royales » (squelette, creeper…) | Pouvoirs spéciaux, drops uniques (livre de quêtes « Royal Variants ») |

## Les Épreuves sombres (difficulté du monde)

Définies par le chapitre *Tutorial* du livre de quêtes :

1. **Épreuve 1** (dès le début) : ennemis plus cohérents et plus létaux, **possibilité de lune de sang**.
2. **Épreuve 2** (en entrant dans une autre dimension) : ennemis encore plus forts, mais récompenses accrues.
3. **Épreuve 3** (après la mort de l'Ender Dragon) : ennemis impitoyables, **ennemis élites** possibles.

## Après la campagne : les difficultés « New Game+ »

Le chapitre *Finis Dierum* permet de relever la difficulté du monde :

- **Ascendant** : +150 niveaux sur le monde (« Azure » ; attention, ça se cumule en multijoueur) ou remise à zéro du chapitre des boss (« Violet »).
- **Divin** : +300 niveaux, réinitialise le chapitre Deus pour refaire tous les boss.
- **Hellheim** : +500 niveaux. Au niveau 400–750 les ennemis lâchent des matériaux rares (poussière de gemme, sceaux de socket) ; au-dessus de 750, des objets très précieux (cendres brûlantes, pièces de champion).

!!! warning "À plusieurs, c'est global"
    Le niveau du monde est un bonus **global** au serveur : si deux joueurs choisissent « Azure », les +150 se **cumulent**. Choisissez-le ensemble.

## Aussi présents

- **Majrusz's Progressive Difficulty** : le monde passe par des paliers « normal → expert → maître » quand on entre dans une dimension, avec de nouveaux pouvoirs pour les monstres.
- **Raided** : raids de pillards plus poussés. **Savage & Ravage / It Takes a Pillage** : illagers et structures supplémentaires.
- **Lune de sang** : événement nocturne dangereux lié à l'Épreuve 1.
