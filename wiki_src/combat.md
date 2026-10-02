---
title: Combat & statistiques
desc: Comment lire ses statistiques : armure, esquive, critiques, puissance de sorts
---

Tout ce que ton équipement, ton arbre de talents, ton origine et tes gemmes te donnent se résume à des **statistiques** (« attributs »). Voici ce qu'elles font **vraiment**, d'après le code des mods.

## Comment les bonus s'additionnent

Chaque bonus a un type :

- **+X** (ajout) : s'additionne à la base. 
- **+X %** « de base » : s'additionne avec les autres « % de base » puis multiplie la base.
- **×(1+X)** « total » : multiplie le résultat, **séparément pour chaque source** — donc plusieurs bonus « total » **se multiplient entre eux**.

Formule : **(base + Σ ajouts) × (1 + Σ % de base) × Π(1 + % total)**. C'est pour ça que les gemmes de puissance de sorts (souvent « total ») explosent quand on en empile.

## Défense

### Vie et armure

- **Vie max** de base : 20 (10 cœurs). Le pack permet des valeurs bien plus hautes (plafond à 90 millions).
- **Armure** : réduit les dégâts physiques. Formule vanilla : réduction = min(20 ; max(armure/5 ; armure − dégâts/(2 + robustesse/4))) / 25. **La réduction maximale est de 80 %** (≈ armure 100 contre un coup moyen).
- **Robustesse** : aide l'armure contre les **gros coups**. Au-delà de 80 % de réduction, ajouter de l'armure ne sert à rien : mieux vaut de la vie, de l'esquive ou du blocage.

| Armure | contre un coup de 50 | contre un coup de 250 |
|---|---|---|
| 20 | 16 % | 16 % |
| 60 (robustesse 15) | 80 % | 66 % |
| 100 (robustesse 28) | 80 % | 80 % |

### Esquive et blocage (arbre de talents)

Chaque point d'**Esquive** (ou de **Blocage**, qui demande un bouclier) donne une chance d'éviter une attaque :

> chance = 0,8 × (0,05 × points) / (1 + 0,05 × points)

| Points | 5 | 10 | 20 | 32 | 60 | 90 (max) |
|---|---|---|---|---|---|---|
| Chance | 16 % | 27 % | 40 % | 49 % | 60 % | 65 % |

L'attribut **Chance d'esquive** d'Apotheosis (en %) est **séparé** : les deux se combinent (1 − (1−a)(1−b)).

### Dégâts reçus

Certaines origines **augmentent** (Hubris +20 %, Berserkergang +30 %) ou **réduisent** (Athena −25 % après le dragon, Vishnu −15 %) les dégâts que tu subis.

!!! warning "Attaques « % de PV max »"
    Plusieurs boss Cataclysm (Ignis, Leviathan, Maledictus, Ender Guardian…) infligent un **pourcentage de tes PV max** (jusqu'à 15 %). Armure et vie n'y changent rien : il faut **esquiver**, bloquer ou rester hors de portée.

## Attaque

### Dégâts d'une arme

Dégâts d'un coup = (dégâts de l'arme + bonus d'attaque + dégâts de feu + dégâts de froid) × (1 + bonus de dégâts %) × (effet du critique).
La **vitesse d'attaque** détermine le nombre de coups par seconde. Les **dégâts de feu et de froid** sont des dégâts **fixes ajoutés à chaque coup** : très bons avec les armes rapides.

### Critiques (Apotheosis)

- Base : **5 % de chance**, **×1,5** de dégâts.
- La chance peut **dépasser 100 %** : un critique garanti, puis un second critique avec la chance restante, avec un multiplicateur réduit de 15 % à chaque fois (critique 3 → ×1,5 × 1,28 × 1,09…).
- Chance de critique 200 % + dégâts critiques ×4 ≈ plusieurs critiques par coup. C'est ce qui donne les chiffres énormes des builds de critiques.

### Perforation / déchirement

| Attribut | Effet |
|---|---|
| **Perforation d'armure** | Ignore une quantité fixe d'armure de la cible |
| **Déchirement d'armure** | Réduit l'armure de la cible en % |
| **Perforation / déchirement de protection** | Pareil pour les enchantements de protection |
| **Dégâts en % des PV actuels** | Inflige un % des PV **actuels** de la cible (idéal contre les boss à des dizaines de milliers de PV) |
| **Vol de vie** | Te soigne d'un % des dégâts infligés |

### Armes à distance

Dégâts de flèche = dégâts de l'arc × attribut **Dégâts des flèches** × (1 + bonus de dégâts). La **vitesse de tension** de l'arc donne la cadence.

## Magie

Voir aussi [Comprendre la magie](magie.html). Les points clés :

- **Puissance du sort** = (base du sort + gain par niveau) × **puissance de sorts globale** × **puissance de l'école** × multiplicateur du pack.
- **Mana** : le maximum est ton attribut *Mana max* ; tu **régénères 1 % du max × ta régénération toutes les 0,5 s** (donc 2 %/s de base).
- **Recharge** et **temps d'incantation** : l'effet est « lissé » : au-delà de ×1,75 de réduction, les gains diminuent fortement (formule de plafond doux). Une origine donnant +200 % de vitesse d'incantation rend les sorts **presque instantanés**.

## Vitesse, roulade, portée

- **Roulade** (`R`) : esquive active avec brève invulnérabilité (mod Combat Roll). Nombre, recharge et distance sont des attributs.
- **Better Combat** : les armes ont des **enchaînements** de coups et une **portée** propres ; certaines armes (claymores, hallebardes) frappent en zone.
- **Vitesse de déplacement** : 100 % = vitesse normale ; les bonus de bottes/origines s'additionnent.
