# -*- coding: utf-8 -*-
"""Structures -> site/data/structures.json : une entrée par structure de worldgen (datapacks des jars).
Dimension déduite des biomes autorisés ; fréquence déduite du structure_set (espacement moyen) ; prérequis (advancements) quand il y en a."""
import json, glob, os, re, collections
import jl

EN = json.load(open('out/lang_en.json'))
MODNAME = {m['modid']: m.get('name', m['modid']) for m in json.load(open('out/mods_index.json')) if m.get('modid')}
DIMKW = [('Nether', r'nether|basalt|crimson|warped|soul_sand|bygone|incendium|inferno|dripstone_nether|fungus'), ('End', r'(^|[:_/])end([:_/]|$)|the_end|unusualend|phantasm|endlessbiomes|end_remastered|chorus|end_'),
         ('Aether', r'aether'), ('Twilight Forest', r'twilightforest|twilight'), ('Blue Skies', r'blue_skies|everbright|everdawn'), ('Otherside', r'otherside|deeperdarker'),
         ('Océan', r'ocean|deep_ocean|river|seven_seas|aquamirae|beach')]


def tagvals(tag, depth=0):
    ns, _, p = tag.lstrip('#').partition(':')
    f = f'extract/{ns}/data/{ns}/tags/worldgen/biome/{p}.json'
    if not os.path.exists(f) or depth > 3: return []
    out = []
    for v in jl.load(f).get('values', []):
        v = v if isinstance(v, str) else v.get('id', '')
        out += tagvals(v, depth + 1) if v.startswith('#') else [v]
    return out


def dim_of(biomes):
    if isinstance(biomes, str): vals = tagvals(biomes) or [biomes]
    else: vals = biomes or []
    txt = ' '.join(vals).lower()
    if not txt.strip(): return 'Overworld'
    for dim, rx in DIMKW:
        if re.search(rx, txt): return dim
    return 'Overworld'


def pretty(p): return p.split('/')[-1].replace('_', ' ').title()


sets = collections.defaultdict(list)  # structure id -> [placement]
for f in glob.glob('extract/*/data/*/worldgen/structure_set/**/*.json', recursive=True):
    try: s = jl.load(f)
    except Exception: continue
    pl = s.get('placement', {})
    for st in s.get('structures', []):
        sets[st['structure']].append(pl)

R = []
for f in glob.glob('extract/*/data/*/worldgen/structure/**/*.json', recursive=True):
    mod = f.split('/')[1]; ns = f.split('/data/')[1].split('/')[0]
    p = f.split('/structure/')[1][:-5]
    sid = f'{ns}:{p}'
    try: j = jl.load(f)
    except Exception: continue
    pls = sets.get(sid, [])
    freq = None; kind = ''
    for pl in pls:
        t = pl.get('type', '').split(':')[-1]
        if t == 'random_spread' and 'spacing' in pl: freq = pl['spacing'] * 16; kind = 'spread'; break
        if 'landmark' in t: kind = 'landmark'
        if t == 'concentric_rings': kind = 'rings'
    adv = [a.split(':')[-1] for a in j.get('advancements_required', []) or []]
    R.append({'id': sid, 'mod': MODNAME.get(mod, mod), 'name': EN.get('structure.' + ns + '.' + p.replace('/', '.')) or pretty(p), 'dim': dim_of(j.get('biomes')), 'dist': freq, 'kind': kind, 'adv': adv, 'type': j.get('type', '').split(':')[-1]})
seen = {}
for r in R: seen[r['id']] = r
R = list(seen.values())
R.sort(key=lambda r: (r['dim'], r['mod'], r['name']))
json.dump(R, open('site/data/structures.json', 'w'), ensure_ascii=False, separators=(',', ':'))
print(len(R), 'structures', collections.Counter(r['dim'] for r in R), 'avec fréquence:', sum(1 for r in R if r['dist']))
