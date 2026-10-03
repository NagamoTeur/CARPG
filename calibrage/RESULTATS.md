# Calibrage en jeu : résultats (3 octobre 2026)

Personnage : **NagamoTeur**, Anthraxi Dark Mage, Clerc, bénédiction de Loki. Armure Adventurer de rareté Rare, anneau, livre de sorts et bâton, sans arme en main pour les attributs. Sources : 19 captures (ce dossier) et les résultats de `/attribute @s … get`. Test automatique : `node tests/calibration.js` (39 vérifications).

## Ce qui correspond exactement

| Mesure | Jeu | Moteur |
|---|---|---|
| Armure (5 + 7 + 6 + 5) | 23 | 23 |
| Robustesse (4 + 4 + 4 + 5) | 17 | 17 |
| Vitesse de déplacement (bottes +20 %) | 0,12 | 0,12 |
| Mana max (100 × 3, origine Anthraxi) | 300 | 300 |
| Régénération de mana | 1,0 | 1,0 |
| Réduction de recharge (casque +15 %) | 1,15 | 1,15 |
| Puissance de sorts globale | 1,0 | 1,0 |
| Critique de base | 5 % × 1,5 | 5 % × 1,5 |
| Évasion → coups esquivés (Évasion niv. 5) | 1 | 1 (après correction, voir ci-dessous) |
| Mana : Évasion 5 / Téléportation 5 / Chute d'étoiles 10 / Souffle du dragon 10 | 120 / 28 / 14 / 14 | 120 / 28 / 14 / 14 |
| Recharge de base des mêmes sorts | 3 min / 3 s / 16 s / 12 s | 180 / 3 / 16 / 12 s |
| Dégâts à puissance 1 : Chute d'étoiles / Souffle du dragon | 2,55 / 4 | 2,55 / 4 |
| Portée de la Téléportation niv. 5 à puissance 1 | 0,4 bloc | 0,4 bloc |
| Plages d'affixes (Rare) lues sur 11 lignes de stats | dans les plages | dans les plages |
| Armure de base de l'Adventurer (5 / 7 / 6 / 5) | 5 / 7 / 6 / 5 | 5 / 7 / 6 / 5 |

## Ce qui a été corrigé grâce aux mesures

- **Évasion** : le nombre de coups esquivés est tronqué à l'entier dans le code du mod (`(int)`). Le moteur affichait 2,8 au lieu de 2 ; c'est corrigé.
- **Les parchemins affichent les valeurs « de base »** (puissance 1, sans réduction de recharge). Le wiki l'indique : ce n'est pas ce que tu fais en jeu.

## Ce qui reste ouvert

1. **Vie max : 41,4 mesurés, 25,2 calculés.** Le moteur donne (20 + 22 de pièces) × 0,6 (« Profane Sacrifice » −40 %). Pour arriver à 41,4 il faut 27 points de vie de plus avant le malus : nourriture (diversité alimentaire) et/ou talents, que je ne connais pas. **Pour fermer ce point** : une capture de ton arbre de talents (nœuds pris) et le nombre d'aliments différents de ton livre de nourriture.
2. **Souffle du dragon sur le mannequin : ≈ 19,25 par tic mesurés, 20,25 calculés** (bâton et livre équipés), soit environ 5 % d'écart. Cause probable : un bonus ou un malus de puissance que je ne vois pas (talent, gemme, résistance du mannequin). À confirmer avec les talents.
3. **DPS affichés par le mannequin** (34,31 puis 19,3) : la durée de la fenêtre de mesure et le sort lancé ne sont pas connus ; non utilisables en l'état.
4. **Esquive : 2,0 mesurés** (anneau +1 + une source inconnue) : à confirmer avec les talents.
