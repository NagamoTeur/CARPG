# -*- coding: utf-8 -*-
"""Base de données des boss -> site/data/bosses.json
Stats de base : lues dans le code décompilé (attributs createAttributes) ; multiplicateurs, plafonds de dégâts et niveaux : config du pack.
Les conseils sont rédigés à partir du livre de quêtes + connaissances générales des mods (marqués 'conseil')."""
import json

LV = json.load(open('out/leveling.json'))


def lv(i):
    d = LV.get(i)
    if not d: return None
    return {'start': d['starting_level'], 'lpd': d['levels_per_distance'], 'rand': d['random_level_bonus']}


# hp/dmg/armor/tough = base (code) ; hpm/dmgm = multiplicateurs config ; cap = plafond de dégâts par coup (0 = aucun)
B = [
 # ---------- Palier 1 ----------
 dict(id='meetyourfight:swampjaw', name='Swampjaw', mod='Meet Your Fight', tier='Palier 1', dim='Marais / eau', hp=100, dmg=12, armor=0,
      how="Se invoque avec l'appât fossile (fossil bait), près de l'eau.", notes=["Premier boss du pack : parfait pour tester son build.", "Mêlée et projectiles aquatiques : reste mobile, évite de te battre dans l'eau."]),
 dict(id='meetyourfight:bellringer', name='Bell Ringer', mod='Meet Your Fight', tier='Palier 1', dim='Overworld', hp=200, dmg=0, armor=0,
      how="Cloche hantée (haunted bell).", notes=["Boss à cloches : attaques en zone au sol, garde de la distance."]),
 dict(id='meetyourfight:dame_fortuna', name='Dame Fortuna', mod='Meet Your Fight', tier='Palier 1', dim='Overworld', hp=300, dmg=0, armor=5,
      how="Invoquée avec le « devil's ante ».", notes=["Boss à cartes : projectiles et phases ; reste en mouvement."]),
 dict(id='meetyourfight:rosalyne', name='Rosalyne', mod='Meet Your Fight', tier='Palier 1', dim='Overworld', hp=500, dmg=24, armor=8, tough=4,
      how="Clé du crépuscule (dusk key).", notes=["Le plus costaud des boss de palier 1 : 500 PV, 24 de dégâts de base, armure 8."]),
 dict(id='iter_rpg:insatiable', name='The Insatiable', mod='Iter RPG', tier='Palier 1', dim='Rivières / grands plans d\'eau', hp=200, dmg=16, armor=8,
      how="Se déclenche en tuant des animaux près de l'eau, ou œuf d'invocation à la boutique de Cisco.", notes=[]),
 # ---------- Gardiens (gatekeepers) du début ----------
 dict(id='cataclysm:ignis', name='Ignis (le Roi des flammes)', mod='Cataclysm', tier='Gardien', dim='Nether (arène brûlante)', hp=450, hpm=1.5, dmg=14, dmgm=1.5, armor=10, cap=50,
      how="Œil de flamme pour trouver l'arène ; cendres brûlantes (récoltées sur les revenants enflammés) sur l'autel central.", notes=["Plafond de 50 dégâts par coup : inutile de chercher le gros coup unique, privilégie plusieurs petits coups rapides.", "Il se protège (bouclier/armure) et réduit les dégâts de moitié pendant ses transitions de phase."]),
 dict(id='cataclysm:netherite_monstrosity', name='Netherite Monstrosity', mod='Cataclysm', tier='Gardien', dim='Nether', hp=600, hpm=2.0, dmg=25, dmgm=1.5, armor=12, tough=5, cap=35,
      how="Œil de monstruosité pour localiser son repaire dans le Nether.", notes=["Plafond de 35 par coup, 2× la vie de base : combat long. Ses attaques retirent un % de tes PV max (8 % pour sa frappe principale)."]),
 dict(id='minecraft:wither', name='Wither', mod='Minecraft (Progressive Bosses)', tier='Gardien', dim='Partout', hp=300, dmg=0, armor=4,
      how="4 sables des âmes + 3 crânes de Wither squelette.", notes=["Progressive Bosses : chaque Wither invoqué plus tard est plus dur (jusqu'à difficulté 8, +720 PV par palier, sbires squelettes). Combats-le en premier pour qu'il reste faible."]),
 dict(id='mowziesmobs:ferrous_wroughtnaut', name='Ferrous Wroughtnaut', mod="Mowzie's Mobs", tier='Gardien', dim='Souterrain', hp=400, hpm=4.0, dmg=30, dmgm=2.0, armor=0,
      how="Structure souterraine ; il s'éveille quand on s'approche de son arène.", notes=["Sa vie est ×4 et ses dégâts ×2 dans ce pack. Esquive ses grands balayages puis frappe pendant sa récupération."]),
 dict(id='knightquest:netherman', name='Netherman (l\'Architecte du Chaos)', mod='Knight Quest', tier='Gardien', dim='Nether', hp=450, dmg=16, armor=0,
      how="Grand calice rempli de Grande essence, puis ajoute de l'Essence radieuse.", notes=["Très rapide (vitesse 0,8) et résiste au recul : prévois de l'esquive."]),
 dict(id='minecraft:elder_guardian', name='Elder Guardian', mod='Minecraft (Progressive Bosses)', tier='Gardien', dim='Monument océanique', hp=80, hpm=1.5, dmg=8, armor=0,
      how="Se trouve dans le monument océanique.", notes=["Donne l'œil de gardien nécessaire pour ouvrir le portail de l'End. Progressive Bosses : +50 % de vie, 40 d'absorption, et il se renforce à chaque Elder Guardian tué."]),
 dict(id='cataclysm:ender_guardian', name='Ender Guardian', mod='Cataclysm', tier='Gardien', dim='End (citadelle en ruine)', hp=333, hpm=1.5, dmg=16, dmgm=1.5, armor=20, cap=50,
      how="Œil du Vide pour trouver la citadelle en ruine.", notes=["Armure de base 20 et plafond de 50 : privilégie les dégâts qui ignorent l'armure (perforation, magie). Ses attaques font jusqu'à 10 % de tes PV max."]),
 dict(id='minecraft:ender_dragon', name='Ender Dragon', mod='Minecraft (Progressive Bosses)', tier='Gardien', dim='End', hp=200, dmg=10, armor=0,
      how="Cristaux d'Ender sur le portail d'invocation (comme en vanilla).", notes=["Progressive Bosses : plus de cristaux et de larves à chaque difficulté, dégâts directs ×2,25.", "Niveau 100–150 : c'est le premier vrai mur de difficulté. À partir de sa mort, de nombreuses bénédictions divines se débloquent."]),
 dict(id='unusualend:endstone_golem', name='Endstone Golem', mod='Unusual End', tier='Gardien', dim='End (cité)', hp=0, dmg=0, armor=0,
      how="Au sommet d'une tour de cité de l'End : souffle de dragon sur son autel.", notes=[]),
 dict(id='minecraft:warden', name='Warden', mod='Minecraft', tier='Gardien', dim='Deep Dark', hp=500, dmg=30, armor=0,
      how="Se trouve dans les Abysses (Deep Dark).", notes=["Aveugle : ne bouge pas, accroupis-toi, et utilise les bruits pour l'attirer."]),
 # ---------- Palier 2 ----------
 dict(id='aquamirae:captain_cornelia', name='Capitaine Cornelia', mod='Aquamirae', tier='Palier 2', dim='Biome Ice Maze', hp=200, dmg=1, armor=16,
      how="Corne de coquillage près de l'eau dans le Ice Maze.", notes=[]),
 dict(id='irons_spellbooks:dead_king', name='Dead King', mod="Iron's Spells 'n Spellbooks", tier='Palier 2', dim='Overworld (repaire)', hp=400, dmg=10, armor=15,
      how="Boussole « wayward compass » à fabriquer pour trouver son repaire.", notes=["C'est un lanceur de sorts (puissance 1,15) : sa magie t'atteint si ta résistance aux sorts est faible."]),
 dict(id='bosses_of_mass_destruction:void_blossom', name='Void Blossom', mod='Bosses of Mass Destruction', tier='Palier 2', dim='Grottes tout en bas du monde', hp=3500, dmg=15, armor=4,
      how="Cavernes profondes ; suis les lis du Vide (void lilies).", notes=["3 500 PV de base (config du pack) : un vrai sac à PV dès le palier 2."]),
 dict(id='mowziesmobs:umvuthi', name='Umvuthi', mod="Mowzie's Mobs", tier='Palier 2', dim='Savane', hp=150, hpm=4.0, dmg=2, dmgm=2.0, armor=0,
      how="Hutte dans la savane ; offrande de 7 blocs d'or pour le défier.", notes=[]),
 dict(id='mowziesmobs:frostmaw', name='Frostmaw', mod="Mowzie's Mobs", tier='Palier 2', dim='Biomes enneigés', hp=250, hpm=3.0, dmg=10, dmgm=2.0, armor=0,
      how="Rarement dans les zones enneigées.", notes=["La couronne de glace peut être volée (ice crystal)."]),
 # ---------- Palier 3 ----------
 dict(id='alexsmobs:warped_mosco', name='Warped Mosco', mod="Alex's Mobs", tier='Palier 3', dim='Overworld', hp=100, dmg=10, armor=10, tough=2,
      how="Un moustique cramoisi qui boit le sang d'un mungus couvert de champignon biscornu, dans l'Overworld.", notes=[]),
 dict(id='bosses_of_mass_destruction:lich', name='Night Lich', mod='Bosses of Mass Destruction', tier='Palier 3', dim='Tours rares en biome froid', hp=6500, dmg=0, armor=0,
      how="Suis les étoiles d'âmes vers des tours rares en biomes froids.", notes=["6 500 PV de base. Il invoque des squelettes ; ses missiles font 20 de dégâts + lenteur 3."]),
 dict(id='bosses_of_mass_destruction:obsidilith', name='Obsidilith', mod='Bosses of Mass Destruction', tier='Palier 3', dim='Îles de l\'End', hp=6500, dmg=24, armor=14,
      how="Structures rares sur les îles extérieures de l'End.", notes=["6 500 PV, armure 14, 24 de dégâts."]),
 dict(id='bosses_of_mass_destruction:gauntlet', name='Nether Gauntlet', mod='Bosses of Mass Destruction', tier='Palier 3', dim='Nether', hp=4500, dmg=9, armor=8,
      how="Structures rares du Nether.", notes=[]),
 dict(id='iter_rpg:sorrowsealed', name='Sorrow Sealed', mod='Iter RPG', tier='Palier 3', dim='Nether (Sorrowspire)', hp=512, dmg=3, armor=12,
      how="Dans la Sorrowspire, quelque part dans le Nether.", notes=["Immobile (vitesse 0) : combat de positionnement et de patience."]),
 # ---------- Gardiens de palier 3 ----------
 dict(id='cataclysm:the_harbinger', name='The Harbinger', mod='Cataclysm', tier='Gardien', dim='Overworld (structure mécanique)', hp=390, hpm=1.5, dmg=9, dmgm=2.0, armor=12, cap=50,
      how="Œil de la mécanique pour trouver sa structure, puis étoile du Nether pour l'éveiller.", notes=["Très rapide (vitesse 0,6) et attaques à distance jusqu'à 35 blocs : un archer ou un mage prend de l'avantage."]),
 dict(id='cataclysm:ancient_remnant', name='Ancient Remnant', mod='Cataclysm', tier='Gardien', dim='Désert (pyramide)', hp=450, hpm=1.0, dmg=25, dmgm=1.0, armor=12, tough=4, cap=21,
      how="Pyramide du désert ; œuf et matériau d'éveil aussi vendus à la boutique de Cisco.", notes=["Plafond très bas : 21 par coup. Il faut taper vite et souvent."]),
 dict(id='cataclysm:the_leviathan', name='The Leviathan', mod='Cataclysm', tier='Gardien', dim='Cité engloutie', hp=400, hpm=3.0, dmg=15, dmgm=3.0, armor=10, cap=35,
      how="Œil de l'abysse pour la cité engloutie ; sacrifice abyssal sur son autel.", notes=["Vie ×3, dégâts ×3, plafond 35 : l'un des plus longs combats. Sa morsure retire 10 % de tes PV max."]),
 dict(id='cataclysm:maledictus', name='Maledictus', mod='Cataclysm', tier='Gardien', dim='Prison gelée (souterrain)', hp=420, hpm=1.0, dmg=13, dmgm=1.0, armor=10, cap=20,
      how="Œil de la malédiction, ou prison gelée souterraine.", notes=["Plafond de 20 par coup. Son attaque de zone retire 15 % de tes PV max : ne reste pas dedans."]),
 dict(id='alexsmobs:void_worm', name='Void Worm', mod="Alex's Mobs", tier='Gardien', dim='End extérieur', hp=0, dmg=5, armor=4,
      how="Lancer un ver mystérieux dans le vide, dans l'End extérieur.", notes=["Corps en segments : chaque segment est une cible."]),
 # ---------- Histoire ----------
 dict(id='cisco_mod:cisco', name='Cisco', mod="Cisco's Content", tier='Histoire', dim='End', hp=1000, dmg=20, armor=60,
      how="Talisman du défi (talisman of challenge) : « Cisco's Trial ».", notes=["Armure 60 : les dégâts physiques sont très réduits ; privilégie perforation d'armure, magie ou dégâts fixes (feu/froid).", "Récompense : tablette d'ascension (Divine) ou, si tu le trahis, tablette de descente (Dark)."]),
 dict(id='cisco_mod:fellkingboss', name='Le Fell King', mod="Cisco's Content", tier='Histoire', dim='Royaume déchu (podium du purgatoire)', hp=1000, dmg=21, armor=70,
      how="Talisman corrompu (tainted talisman) sur le podium du purgatoire.", notes=["Armure 70 de base, recul 2 : la fin de campagne. Récompense : Tablet of Ascension ×2 et 20 pièces de champion."]),
]

# ---------- Boss ajoutés (vague 3) : stats lues dans le code décompilé (createAttributes) ; invocations d'après le livre de quêtes ----------
B += [
 dict(id='twilightforest:naga', name='Naga', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (cour du Naga)', hp=120, dmg=5, armor=0, how="Se trouve dans sa cour, au premier palier de la forêt.", notes=["Premier boss de la Twilight Forest : il charge en serpentant, ne reste pas dans sa trajectoire.", "Pas de niveau propre : règle par défaut (niveau 1 + 0,008 par bloc)."]),
 dict(id='twilightforest:lich', name='Lich (Twilight)', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (tour du Lich)', hp=100, dmg=3, armor=0, how="Tour du Lich, après le Naga.", notes=["Il se protège d'un bouclier et lance des sorts (clones, éclairs) : tue d'abord ses clones.", "Ne pas confondre avec le Lich de Bosses of Mass Destruction (fiche séparée)."]),
 dict(id='twilightforest:minoshroom', name='Minoshroom', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (fond du Labyrinthe)', hp=120, dmg=0, armor=0, how="Au fond du Labyrinthe.", notes=["Un minotaure champignon : charge puis coup de hache, reste mobile."]),
 dict(id='twilightforest:hydra', name='Hydre', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (marais de feu)', hp=360, dmg=0, armor=0, how="Dans son antre, dans un marais de feu.", notes=["Plusieurs têtes : chaque tête a ses PV ; couper une tête la fait repousser.", "Son armure est multipliée par 8 tant que ses têtes sont intactes (code du mod)."]),
 dict(id='twilightforest:knight_phantom', name='Knight Phantom', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (forteresse des chevaliers gobelins)', hp=35, dmg=1, armor=0, how="Grande salle de la forteresse des chevaliers gobelins.", notes=["Plusieurs chevaliers à affronter ; chacun a peu de PV mais une armure ×5 quand il ne charge pas."]),
 dict(id='twilightforest:ur_ghast', name='Ur-Ghast', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (sommet de la Dark Tower)', hp=250, dmg=0, armor=0, how="Au sommet de la Dark Tower.", notes=["Un ghast géant qui lance des boules de feu et des explosions ; garde ta distance et reste à l'abri des projectiles."]),
 dict(id='twilightforest:alpha_yeti', name='Yéti alpha', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (forêt enneigée)', hp=200, dmg=1, armor=0, how="Dans son antre, dans la forêt enneigée.", notes=["Charge et lance de la glace ; frappe-le de côté pendant qu'il se repose."]),
 dict(id='twilightforest:snow_queen', name='Reine des neiges', mod='Twilight Forest', tier='Twilight Forest', dim='Twilight Forest (Palais de l\'Aurore)', hp=200, dmg=7, armor=0, how="Au sommet du Palais de l'Aurore.", notes=["Elle invoque des cristaux de glace et se téléporte ; la tuer donne sa tête (quête)."]),
 dict(id='aether:slider', name='Slider', mod='The Aether', tier='Aether', dim='Aether (donjon de bronze)', hp=400, dmg=0, armor=0, how="Donjon de bronze de l'Aether.", notes=["Doit être vaincu avec des pioches ou des pelles (livre de quêtes).", "La dimension de l'Aether a son propre niveau de départ (35)."]),
 dict(id='aether:valkyrie_queen', name='Reine des Valkyries', mod='The Aether', tier='Aether', dim='Aether (donjon d\'argent)', hp=500, dmg=13.5, armor=0, how="Après avoir obtenu 10 médailles de victoire (donjon d'argent).", notes=["Bonne vitesse d'attaque : bloque ou esquive, puis réplique."]),
 dict(id='aether:sun_spirit', name='Sun Spirit', mod='The Aether', tier='Aether', dim='Aether (donjon d\'or)', hp=50, dmg=0, armor=0, how="Donjon d'or de l'Aether.", notes=["Une fois tué, l'Aether n'est plus plongé dans un jour perpétuel."]),
 dict(id='blue_skies:starlit_crusher', name='Starlit Crusher', mod='Blue Skies', tier='Blue Skies', dim='Everbright / Everdawn (donjon)', hp=500, dmg=10, armor=0, how="Boss de donjon de Blue Skies (clés de donjon).", notes=["Gros bras lent : esquive ses coups lourds et frappe dans son dos."]),
 dict(id='blue_skies:arachnarch', name='Arachnarch', mod='Blue Skies', tier='Blue Skies', dim='Everbright / Everdawn (donjon)', hp=500, dmg=14, armor=6, how="Boss de donjon de Blue Skies (clés de donjon).", notes=["Une araignée géante : se déplace sur les murs et le plafond, prévois des attaques à distance."]),
 dict(id='blue_skies:summoner', name='Summoner', mod='Blue Skies', tier='Blue Skies', dim='Everbright', hp=350, dmg=0, armor=0, how="Présidé par Everbright : il faut 4 clés de donjon.", notes=["Fait partie des mobs exclus de l'AutoLeveling (niveau fixe).", "Il invoque des serviteurs : tue-les ou garde ta distance."]),
 dict(id='blue_skies:alchemist', name='Alchemist', mod='Blue Skies', tier='Blue Skies', dim='Everdawn', hp=500, dmg=0, armor=0, how="Présidé par Everdawn : il faut 4 clés de donjon.", notes=["Exclu de l'AutoLeveling (niveau fixe).", "Lance des potions : de la mobilité et un antidote sont utiles."]),
 dict(id='sons_of_sins:wistiver', name='Wistiver (péché de la gourmandise)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=750, dmg=40, armor=0, how="Chapitre « Sept péchés capitaux » du livre de quêtes.", notes=["Ses PV et son attaque augmentent à chaque mort qu'il cause : ne le laisse pas tuer."]),
 dict(id='sons_of_sins:walking_bed', name='Walking Bed (péché de la paresse)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=840, dmg=35, armor=0, how="Chapitre « Sept péchés capitaux ».", notes=["Lent mais très résistant et frappe fort."]),
 dict(id='sons_of_sins:prowler', name='Prowler (péché de l\'orgueil)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=100, dmg=30, armor=0, how="Chapitre « Sept péchés capitaux ».", notes=["Rapide et mortel : peu de PV, beaucoup de dégâts."]),
 dict(id='sons_of_sins:kelvin', name='Kelvin (péché de l\'envie)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=560, dmg=25, armor=0, how="Chapitre « Sept péchés capitaux ».", notes=["Plus lent que ses frères mais tout aussi vicieux."]),
 dict(id='sons_of_sins:curse', name='Curse (péché de la luxure)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=350, dmg=4, armor=0, how="Chapitre « Sept péchés capitaux ».", notes=["Il préfère voler : prévois des attaques à distance."]),
 dict(id='sons_of_sins:blud', name='Blüd (péché de l\'avarice)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=540, dmg=30, armor=0, how="Chapitre « Sept péchés capitaux ».", notes=["Moins mobile mais très robuste, avec des attaques à distance."]),
 dict(id='sons_of_sins:butcher', name='Butcher (péché de la colère)', mod='Sons of Sins', tier='Sept péchés', dim='Overworld', hp=650, dmg=50, armor=0, how="Chapitre « Sept péchés capitaux ».", notes=["Polyvalent et vicieux : le plus gros dégât du chapitre."]),
]

import tomllib
BLACKLIST = set(tomllib.load(open('../minecraft/config/autoleveling-common.toml', 'rb'))['mobs']['blacklist'])  # mobs exclus de l'AutoLeveling
DEFAULT_LV = {'start': 1, 'lpd': 0.008, 'rand': 0}  # autoleveling-common.toml : default_leveling_settings (boss sans réglage propre)
AETHER = {'start': 35, 'lpd': 0.022, 'rand': 0}
out = []
for b in B:
    b.setdefault('hpm', 1.0); b.setdefault('dmgm', 1.0); b.setdefault('tough', 0); b.setdefault('cap', 0)
    b['level'] = lv(b['id']) or (AETHER if b['id'].startswith('aether:') else DEFAULT_LV)
    if b['id'] in BLACKLIST: b['fixed'] = True; b['level'] = None
    out.append(b)
json.dump(out, open('site/data/bosses.json', 'w'), ensure_ascii=False, separators=(',', ':'))
print(len(out), 'boss;', sum(1 for b in out if not b['level']), 'sans niveau')
print([b['id'] for b in out if not b['level']])
