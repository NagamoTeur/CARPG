# -*- coding: utf-8 -*-
"""Recettes -> site/data/recipes.json (chargé à la demande par la page « Recettes »).
Sources : datapacks des jars (extract/), datapacks Paxi, script CraftTweaker scripts/Cisco.zs (recettes ajoutées + suppressions par mod).
Seuls les mods d'équipement / de progression sont retenus (pas les blocs de décoration)."""
import json, glob, os, re, collections
import jl

ROOT = '/home/nagamo/Documents/Dev/Minecraft/minecraft'
MODS = ['cisco_mod', 'ciscounbound', 'dreadsteel', 'skilltree', 'simplyswords', 'celestisynth', 'cataclysm', 'upgradednetherite', 'upgradednetherite_items', 'upgradednetherite_ultimate',
        'irons_spellbooks', 'apotheosis', 'relics', 'enigmaticlegacy', 'enigmaticaddons', 'iter_rpg', 'majruszsaccessories', 'majruszsenchantments', 'knightquest', 'twilightforest', 'aether',
        'deep_aether', 'blue_skies', 'forbidden_arcanus', 'stalwart_dungeons', 'sons_of_sins', 'immersive_armors', 'mcsa', 'ars_elemental', 'ars_additions', 'ars_nouveau', 'endrem', 'minecolonies', 'alexsmobs', 'sophisticatedbackpacks']
EN = json.load(open('out/lang_en.json')); FR = json.load(open('out/lang_fr.json'))
SKIP_TYPES = ('crafting_special', 'crafting_decorated_pot', 'crafting_dye', 'armor_dye')


def nm(i):
    ns, _, p = i.partition(':')
    for kind in ('item', 'block', 'entity'):
        k = f'{kind}.{ns}.{p.replace("/", ".")}'
        if k in EN or k in FR: return EN.get(k) or FR.get(k), FR.get(k)
    return p.replace('_', ' ').replace('/', ' ').title(), None


def ing(x):
    """-> ('item'|'tag', id) liste d'alternatives"""
    if isinstance(x, list): return [a for y in x for a in ing(y)]
    if isinstance(x, str): return [('item', x)]
    if 'item' in x: return [('item', x['item'])]
    if 'tag' in x: return [('tag', x['tag'])]
    return []


def label(alts):
    if not alts: return '?'
    k, i = alts[0]
    n = nm(i)[0] if k == 'item' else '#' + i.split(':')[-1].replace('/', ' ')
    return n + (f' (ou {len(alts)-1} autre{"s" if len(alts) > 2 else ""})' if len(alts) > 1 else '')


def out_of(r):
    o = r.get('result') or r.get('output')
    if isinstance(o, str): return o, 1
    if isinstance(o, dict): return o.get('item') or o.get('id'), o.get('count', 1)
    return None, 1


def parse(path, mod, src):
    try: r = jl.load(path)
    except Exception: return None
    t = r.get('type', '').split(':')[-1]
    if t in SKIP_TYPES or not isinstance(r, dict): return None
    for c in r.get('conditions', []) or []:
        if c.get('type') in ('forge:false',): return None
    o, cnt = out_of(r)
    if not o: return None
    rec = {'id': os.path.basename(path)[:-5], 'mod': mod, 'type': t, 'out': o, 'n': cnt, 'src': src}
    if 'pattern' in r and 'key' in r:
        keys = {k: label(ing(v)) for k, v in r['key'].items()}
        rec['grid'] = [[keys.get(ch, '') for ch in row.ljust(3)] for row in r['pattern']]
        tally = collections.Counter(keys.get(ch, '?') for row in r['pattern'] for ch in row if ch != ' ')
        rec['ing'] = [[k, n] for k, n in tally.items()]
    elif 'ingredients' in r:
        c = collections.Counter(label(ing(x)) for x in r['ingredients']); rec['ing'] = [[k, n] for k, n in c.items()]
    elif 'ingredient' in r:
        rec['ing'] = [[label(ing(r['ingredient'])), 1]]
    elif 'base' in r and 'addition' in r:
        rec['ing'] = [[label(ing(r['base'])), 1], [label(ing(r['addition'])), 1]]
        if 'template' in r: rec['ing'].insert(0, [label(ing(r['template'])), 1])
    elif 'input' in r:
        rec['ing'] = [[label(ing(r['input'])), 1]]
    else: return None
    return rec


R = []
for mod in MODS:
    for f in glob.glob(f'extract/{mod}/data/*/recipes/**/*.json', recursive=True):
        x = parse(f, mod, 'mod')
        if x: R.append(x)
# datapacks Paxi
for f in glob.glob(ROOT + '/config/paxi/datapacks/*/data/*/recipes/**/*.json', recursive=True):
    x = parse(f, f.split('/data/')[1].split('/')[0], 'datapack')
    if x: R.append(x)

# CraftTweaker : recettes ajoutées et suppressions par mod
zs = open(ROOT + '/scripts/Cisco.zs', encoding='utf8').read()
removed = set(re.findall(r'removeByModid\("([a-z0-9_]+)"\)', zs))
item = lambda s: re.findall(r'<item:([a-z0-9_]+:[a-z0-9_/]+)>', s)
for m in re.finditer(r'craftingTable\.addShapeless\("([^"]+)",\s*<item:([^>]+)>,\s*\[(.*?)\]\)\s*;', zs):
    c = collections.Counter(item(m.group(3)))
    R.append({'id': m.group(1), 'mod': m.group(2).split(':')[0], 'type': 'crafting_shapeless', 'out': m.group(2), 'n': 1, 'src': 'CraftTweaker', 'ing': [[nm(k)[0], n] for k, n in c.items()]})
for m in re.finditer(r'smithing\.addRecipe\("([^"]+)",\s*<item:([^>]+)>,\s*<item:([^>]+)>,\s*<item:([^>]+)>\)', zs):
    R.append({'id': m.group(1), 'mod': m.group(2).split(':')[0], 'type': 'smithing', 'out': m.group(2), 'n': 1, 'src': 'CraftTweaker', 'ing': [[nm(m.group(3))[0], 1], [nm(m.group(4))[0], 1]]})
# retire les recettes d'origine des mods supprimés par le script
R = [r for r in R if not (r['src'] == 'mod' and r['mod'] in removed)]
# les recettes de datapack/CraftTweaker remplacent celles du même identifiant
seen = {}
for r in R: seen[(r['out'], r['id'], r['src'])] = r
R = list(seen.values())

for r in R:
    r['name'], r['fr'] = nm(r['out'])
R.sort(key=lambda r: (r['name'].lower(), r['id']))
json.dump({'removed_mods': sorted(removed), 'recipes': R}, open('site/data/recipes.json', 'w'), ensure_ascii=False, separators=(',', ':'))
print(len(R), 'recettes ;', collections.Counter(r['src'] for r in R), '; supprimées par script :', removed)
