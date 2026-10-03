---
title: Utiliser le planificateur de build
desc: Mode d'emploi pas à pas de l'outil de théorycraft
---

Le **planificateur** (page « Planificateur de build » en haut à droite) fonctionne comme *Path of Building* : tu construis un personnage, et les statistiques à droite se mettent à jour **en direct**. Rien n'est envoyé sur Internet : tout est calculé dans ton navigateur et sauvegardé automatiquement dans celui-ci.

## 1. Personnage
Choisis ton **origine**, ta **classe** et ta **bénédiction divine**. Coche **« Dragon vaincu »** si tu as tué l'Ender Dragon (pour activer les grands bienfaits). Dans **Hypothèses de combat**, indique la situation que tu veux simuler : cible en feu, PV bas, distance de tir…

## 2. Arbre de talents
- **Clic** sur un nœud : le prend, ou prend **tout le chemin** jusqu'à lui (la barre jaune montre le coût).
- **Clic droit** (ou clic sur un nœud pris) : le retire.
- **Molette** : zoom ; **glisser** : déplacer. La barre de recherche surligne un effet (« critique », « gemme »…).
- Indique le **nombre de points disponibles** : l'outil affiche aussi le **coût en XP** des points au-delà des parchemins de sagesse.
- Les talents qui dépendent d'une situation s'affichent **inactifs** dans « Statistiques détaillées » avec la raison.

## 3. Équipement
Chaque emplacement (arme, armure, accessoires, livre de sorts…) a :
- un **type** et une **rareté** ;
- une **base** (dégâts et vitesse de l'arme ; armure et robustesse). Une liste de **bases prédéfinies** propose les armures et armes de Cisco et de KubeJS ; pour les autres objets, saisis la valeur de l'info-bulle ;
- des **affixes** (stat / capacité), dont tu règles la **plage** avec le curseur (valeur tirée au hasard dans le jeu) ;
- des **sockets** avec leurs **gemmes** et leur qualité. Le talent *Greed*/*Cullinan* ajoute des sockets, *Cullinan*/*Estrela de Fura* augmentent l'efficacité des gemmes.
- **Stats supplémentaires** : pour tout effet que l'outil ne connaît pas (enchantements, effet propre à un objet).

## 4. Sorts
Ajoute des sorts d'Iron's Spells et règle leur **niveau** : l'outil calcule la **puissance**, le **coût en mana**, le **temps d'incantation**, la **recharge** et les **dégâts** avec **tes** statistiques et le **réglage du pack**.

## 5. Simulateur de boss
Choisis un boss, la **distance** du spawn et le **niveau du monde** : tu vois son **niveau, ses PV, ses dégâts, son plafond de dégâts**, le **temps pour le tuer** avec ton build, et le **nombre de coups qu'il te faut pour mourir**. Le tableau du bas compare **tous les boss** d'un coup d'œil.

## 6. Statistiques détaillées
Toutes les statistiques, avec la **source** de chaque bonus. Les effets non chiffrés et conditionnels sont listés en dessous.

## 7. Builds prêts, partager, exporter
- **Builds prêts** : 24 builds (12 archétypes × milieu/fin de jeu). Charge-en un et modifie-le.
- **Partager** : copie un **lien** qui contient tout le build (à envoyer à tes amis).
- **Exporter / Importer** : fichier `.json` pour sauvegarder tes versions.

!!! warning "Précautions"
    Les valeurs de base des armes/armures **hors Cisco/KubeJS** sont à saisir à la main. Les sorts d'**Ars Nouveau** ne sont pas calculés. Les résultats sont une **estimation** ; compare toujours avec ce que le jeu affiche.

## Est-ce que les chiffres sont justes ?

Le planificateur a été **comparé au jeu** (octobre 2026, sur un vrai personnage) : armure, robustesse, vitesse, mana, régénération de mana, réduction de recharge, coût en mana et dégâts de base des sorts, plages de stats des objets : **tout correspond exactement**. Deux points sont encore en cours de vérification : la **vie max** (la nourriture et les talents jouent) et les **dégâts réels sur un mannequin** (environ 5 % d'écart observé avec un bâton et un livre de sorts).

!!! tip "Les parchemins mentent (un peu)"
    L'info-bulle d'un parchemin de sort affiche les valeurs de **base** : puissance 1 et aucune réduction de recharge. Avec ton équipement, les dégâts sont plus élevés et la recharge plus courte. Le planificateur calcule les valeurs avec **tes** statistiques.
