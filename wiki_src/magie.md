---
title: Comprendre la magie
desc: Iron's Spells et Ars Nouveau : deux magies, une seule logique de puissance
---

Le pack contient **deux systèmes de magie** indépendants. La quête *The path of the mage* te demande de choisir :

| | **Iron's Spells 'n Spellbooks** | **Ars Nouveau** (+ Elemental, Additions, Too Many Glyphs) |
|---|---|---|
| Ressource | **Mana** (une jauge qui se régénère toute seule) | **Source** (une énergie à produire/stocker : gemmes de source, jarres, starbuncles) |
| Sorts | Une liste de **sorts nommés** (Boule de feu, Trait guidant…) trouvés en **parchemins** ou appris | Tu **construis** tes sorts en combinant des **glyphes** (Projectile, Dégâts, Flamme, Amplifier…) |
| Matériel | **Livre de sorts** (emplacement Curios) + **encre** + **table d'inscription** | **Livre de sorts** (Novice → Mage → Archimage) + **table du scribe** |
| Calcul de puissance | Détaillé par le planificateur de build | Non modélisé par le planificateur (dépend des glyphes) |
| Avantage | Très **simple** à lancer, énorme **mise à l'échelle** avec l'équipement | Très **flexible** et créatif |

!!! tip "Quelle magie choisir ?"
    Pour débuter et tenir un rôle précis (mage de feu, soigneur…), **Iron's Spells** est plus direct et mieux intégré au système d'équipement du pack (gemmes, affixes, origines). Ars Nouveau est génial pour automatiser (starbuncles, golems d'améthyste) et personnaliser.

## Iron's Spells en pratique

1. **Mana** : ton maximum est l'attribut *Mana max* (100 de base). Tu **régénères 1 % de ton max × ta régénération toutes les 0,5 seconde**. Les origines Anthraxi / Lumerian **triplent** le mana max.
2. **Livre de sorts** : sans lui, tu ne lances rien. Il définit **combien de sorts** tu peux préparer (emplacements) et leur **rareté maximale**. Il se porte dans un emplacement Curios. Les spellbooks se trouvent en donjon ou se fabriquent avec des matériaux rares ; le pack donne des quêtes pour passer aux rangs supérieurs (*Rank up your magic* : Mage's Spell Book → *The Greatest Magician* : Archmage → *The Codex*).
3. **Sorts** : trouve des **parchemins** (donjons, tours de mages, ennemis), puis **inscris-les** dans ton livre avec de l'**encre** (*Common / Uncommon / Epic Ink…*) à la **table d'inscription**. L'**enclume arcanique** fusionne deux parchemins identiques en une version plus haute.
4. **Lancer** : on sélectionne un sort puis on utilise l'objet « focus » (arme, bâton, livre) ; certains sorts sont **instantanés**, d'autres demandent une **incantation** ou se **canalisent** (tu maintiens).
5. **Écoles de magie** : Feu, Glace, Foudre, Sacré, Ender, Sang, Évocation, Nature. Chaque école a **sa propre puissance** (attribut) qui se **multiplie** avec la puissance globale.

### Puissance des sorts (la partie importante)

> puissance du sort = (base du sort + gain par niveau) × **puissance globale** × **puissance de l'école** × **multiplicateur du pack**

Les gemmes d'école (*Inferno, Frozen, Storm, Holy, Ender, Blood, Illager's, Natural*), les affixes d'accessoires (« Power Ender »…) et les origines (Ender Affinity : +350 %, Lumera's Might : +350 %, Fire Magic Adept : +200 %) alimentent ces puissances. Les bonus « total » **se multiplient entre eux** : c'est ce qui rend les builds de mage aussi explosifs.

!!! warning "Spécificités de ce pack"
    - Le pack **modifie le multiplicateur de puissance de presque chaque sort** (×0,2 à ×0,3 pour la plupart, ×1 pour une poignée, ×0,008–0,01 pour les sorts de mobilité). Les sorts à **×1** (Missile magique ×0,9, Trait guidant ×0,9, Éclair en boule, Onde de choc, Tempête, Flaming Barrage, Heat Surge, Scorch, Gluttony, Stomp…) sont **ceux qui servent d'attaques principales**.
    - **Boule de feu** est volontairement quasi inutile (×0,008, niveau max 3).
    - Le sort **Flaming Strike** est passé de l'école Feu à l'école **Sacré**.
    - **Echoing Strikes, Firecracker, Root** sont **désactivés**.
    - Les **épées magiques** consomment du mana et ont une recharge réduite de moitié.
    - Le **griefing** des sorts (casser des blocs) est désactivé.

### Formules utiles

- **Mana** : coût = base + (niveau − 1) × coût par niveau.
- **Recharge** = recharge de base × (2 − réduction de recharge, lissée). Au-delà de ×1,75 de réduction, les gains diminuent.
- **Temps d'incantation** = temps de base × (2 − vitesse d'incantation, lissée) ; pour les sorts **canalisés** c'est directement multiplié par ta vitesse.

## Ars Nouveau en pratique

1. **Source** : fabrique une **table du scribe** (*Scribe's Table*, que la quête de départ appelle *Mage's Workstation*) : c'est là que tu apprends les glyphes ; chaque nouveau glyphe **augmente un peu ton mana max**.
2. **Starbuncles** (petites créatures) transportent la Source ; capture-en avec des pépites d'or (quête *Tiny Workhorse*). Les **golems d'améthyste** produisent l'améthyste de façon automatique.
3. **Construire un sort** : une **forme** (Projectile, Toucher, Soi…) + des **effets** (Dégâts, Flamme, Gel…) + des **augmentations** (Amplifier, Accélérer, AoE…). Le glyphe **Amplify** est le premier à apprendre (*Amplified Magics*).
4. **Grimoires** : un livre plus haut permet plus de glyphes par sort. Le pack ajoute **Ars Elemental** (écoles élémentaires), **Ars Additions** et **Too Many Glyphs** (plus de glyphes).
5. Les gemmes **Source Jewel** (*Jewel of the Archmage*) donnent du mana Ars et de la puissance.

## Pierre de sort, charmes et divers

- **Spellstone** (Curios) : une pierre qui donne une capacité active (touche `K`).
- **Iron's + Ars** coexistent : tu peux avoir un livre de sorts Iron's **et** un grimoire Ars ; les deux jauges (mana / Source) sont indépendantes.

## Voir aussi

- [Catalogue des sorts](sorts.html) (réglages exacts du pack)
- [Catalogue des gemmes](gemmes.html) (gemmes de magie)
- [Builds de magie](builds/index.html)
