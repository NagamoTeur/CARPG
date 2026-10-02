import json,collections
idx=json.load(open('out/mods_index.json'))
C={}
def add(cat,sub,ids):
    for i in ids.split(): C[i]=(cat,sub)
G='🎮 Gameplay'
add(G,'Cœur RPG (talents, loot, gemmes, affixes)','skilltree apotheosis apotheotic_additions apothiccurios apothsb autoleveling attributefix maxhealthfix armordamagelimit infernalmobs xpbook')
add(G,'Classes & origines','origins origins_classes strictly_origins collectionofsingiro oriacs')
add(G,'Magie','irons_spellbooks ars_nouveau ars_elemental ars_additions toomanyglyphs forbidden_arcanus')
add(G,'Combat & armes','bettercombat combatroll simplyswords celestisynth iter_rpg cisco_mod ciscounbound dreadsteel upgradednetherite upgradednetherite_items upgradednetherite_ultimate immersive_armors mcsa dragonenchants majruszsenchantments pehkui')
add(G,'Accessoires & reliques','curios relics enigmaticlegacy enigmaticaddons majruszsaccessories ringsofascension elytraslot transmog')
add(G,'Progression & difficulté','gamestages itemstages progressivebosses majruszsdifficulty gateways raided brutalbosses ftbquests')
add(G,'Boss & monstres','cataclysm mowziesmobs meetyourfight bosses_of_mass_destruction mutantmonsters alexsmobs sons_of_sins enemyexpansion undead_unleashed royalvariations savage_and_ravage whisperwoods dummmmmmy')
add(G,'Dimensions & biomes','twilightforest aether deep_aether blue_skies deeperdarker byg galosphere atmospheric incendium bygonenether phantasm unusualend endrem darkerdepths endlessbiomes aquamirae')
add(G,'Structures & donjons','dungeons_arise dungeons_arise_seven_seas dungeoncrawl dungeons_enhanced dungeons_plus dungeons_andtaverns_mr stalwart_dungeons knightquest tlc skyvillages totw_modded wabi_sabi_structures explorify structory structorytowers t_and_t ctov takesapillage hoporp repurposed_structures betterdeserttemples betterdungeons betterendisland betterjungletemples bettermineshafts betterfortresses betterstrongholds betteroceanmonuments awesomedungeonend awesomedungeonnether')
add(G,'Cuisine, ferme & pêche','farmersdelight farmersrespite brewinandchewin delightful nethersdelight alexsdelight bakery vinery aquaculture solapplepie rightclickharvest')
add(G,'Colonie & PNJ','minecolonies stylecolonies betterwithminecolonies towntalk humancompanions goblintraders morevillagers easy_villagers structurize domum_ornamentum blockui')
add(G,'Vie pratique (stockage, déplacement)','sophisticatedbackpacks sophisticatedcore storagedrawers toms_storage ironchest ironfurnaces waystones carryon ftbultimine ftbchunks constructionwand elevatorid comforts corpse corpsecurioscompat lootr smallships mythicmounts clumps')
add('🧭 Outils & interface','Aide en jeu','jei jeresources jade appleskin patchouli eccentrictome explorerscompass naturescompass xaerominimap xaeroworldmap enchdesc legendarytooltips advancementplaques betteradvancements travelerstitles betterthirdperson highlighter mousetweaks controlling overflowingbars')
add('🏗️ Décoration & construction','Blocs & mobilier','decorative_blocks dramaticdoors fantasyfurniture twigs woodworks clayworks supplementaries ecologics fastpaintings immersive_paintings medieval_paintings multipiston quark')
add('🛠️ Admin / modpack','Scripts & config','kubejs crafttweaker rhino paxi itemfilters item_obliterator defaultoptions badmobs letmedespawn lootintegrations getittogetherdrops itemproductionlib recipeessentials polymorph nerb toofast fancymenu konkrete melody biomemusic medievalmusic simplerpc bhmenu crash_assistant myserveriscompatible yeetusexperimentus')
add('⚙️ Performance','Optimisation','modernfix canary ferritecore starlight saturn rubidium rubidium_extras reeses_sodium_options oculus lazydfu smoothboot noisium immediatelyfast entityculling culllessleaves leavesbegone betterfpsdist doespotatotick badoptimizations aiimprovements entitycollisionfpsfix fastasyncworldsave gpumemleakfix memoryleakfix memorysettings servercore chunksending connectivity pluto packetfixer longnbtkiller nightconfigfixes redirector mixample structureessentials mixample celestweaks bocchium')
add('📚 Bibliothèques','API','architectury autoreglib balm blueprint bookshelf caelus citadel cloth_config corgilib craterlib cupboard azurelib geckolib3 iceberg insanelib knightlib libraryferret lionfishapi majruszlibrary moonlight necronomicon obscure_api octolib placebo playeranimator prism puzzleslib shadowizardlib structure_gel terrablender aeroblender valhelsia_core upgradedcore yungsapi ftblibrary ftbteams ftbxmodcompat doapi')
by=collections.defaultdict(list)
miss=[]
for o in idx:
    c=C.get(o['modid'])
    if not c: miss.append(o['modid']); c=('❓ Non classé','')
    by[c].append(o)
print('missing',miss)
json.dump({'%s / %s'%k:[(o['modid'],o['name'],o['version'],o['file']) for o in v] for k,v in by.items()},open('out/mods_classified.json','w'),ensure_ascii=False,indent=1)
from mods_fr import FR
L=['# Liste des mods — Cisco\'s Adventure RPG Ultimate (V8E)\n',f'{len(idx)} mods · Forge 1.19.2\n']
for k in sorted(by,key=lambda k:(k[0]!=G,k)):
    pass
order=[G]+[c for c in sorted({k[0] for k in by}) if c!=G]
for cat in order:
    L.append(f'\n## {cat}\n')
    for (c,s),v in sorted(by.items()):
        if c!=cat: continue
        L.append(f'\n### {s or "—"} ({len(v)})\n')
        for o in sorted(v,key=lambda o:o['name'] or ''):
            ver=o['version'] if o['version'] and '$' not in o['version'] else ''
            d=FR.get(o['modid'],'')
            L.append(f"- **{o['name']}** `{o['modid']}` {ver}" + (f" — {d}" if d else ''))
open('out/MODS.md','w').write('\n'.join(L))
print({c:sum(len(v) for (cc,s),v in by.items() if cc==c) for c in order})
