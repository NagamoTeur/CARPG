# -*- coding: utf-8 -*-
"""Icônes -> site/assets/icons/ + site/data/icons.json
Textures lues dans les jars du pack (et le client vanilla 1.19.2). Usage privé entre amis (site non indexé)."""
import json, os, re, zipfile, glob, collections, shutil, io
try:
    from PIL import Image
except ImportError:
    Image = None


def norm(d):
    """Première image (textures animées), réduite à 32 px au plus (voisin le plus proche), PNG optimisé."""
    if not Image: return d
    try:
        im = Image.open(io.BytesIO(d)).convert('RGBA'); w, h = im.size
        if h > w: im = im.crop((0, 0, w, w)); h = w
        if w > 32: im = im.resize((32, 32), Image.NEAREST)
        b = io.BytesIO(); im.save(b, 'PNG', optimize=True); return b.getvalue()
    except Exception: return d


MODS = '/home/nagamo/Documents/Dev/Minecraft/minecraft/mods'
VANILLA = os.path.expanduser('~/.local/share/PrismLauncher/libraries/com/mojang/minecraft/1.19.2/minecraft-1.19.2-client.jar')
OUT = 'site/assets/icons'
idx = json.load(open('out/mods_index.json'))

# espace de noms -> archives
Z = collections.defaultdict(list)
for m in idx:
    try: z = zipfile.ZipFile(os.path.join(MODS, m['file']))
    except Exception: continue
    names = None
    for ns in set(m.get('namespaces') or []) | ({m['modid']} if m.get('modid') else set()):
        Z[ns].append(z)
vz = zipfile.ZipFile(VANILLA); Z['minecraft'].append(vz)


def read(ns, path):
    for z in Z.get(ns, []):
        try: return z.read(f'assets/{ns}/{path}')
        except KeyError: pass
    return None


def tex_of_model(ns, p, depth=0):
    d = read(ns, f'models/item/{p}.json')
    if not d or depth > 3: return None
    try: j = json.loads(d.decode('utf8', 'replace'))
    except Exception: return None
    t = j.get('textures') or {}
    for k in ('layer0', 'particle', 'all', 'texture', '0'):
        if k in t and not str(t[k]).startswith('#'): return t[k]
    par = j.get('parent')
    if par and not par.startswith('minecraft:builtin') and par not in ('item/generated', 'minecraft:item/generated', 'minecraft:item/handheld', 'item/handheld'):
        pn, _, pp = par.partition(':') if ':' in par else ('minecraft', '', par)
        if pp.startswith('item/'): return tex_of_model(pn, pp[5:], depth + 1)
    return None


def icon_for(i):
    ns, _, p = i.partition(':')
    cands = []
    t = tex_of_model(ns, p)
    if t:
        tn, _, tp = t.partition(':') if ':' in t else (ns, '', t)
        cands.append((tn, f'textures/{tp}.png'))
    cands += [(ns, f'textures/item/{p}.png'), (ns, f'textures/items/{p}.png'), (ns, f'textures/item/{p.split("/")[-1]}.png')]
    for tn, path in cands:
        d = read(tn, path)
        if d: return d
    return None


ids = set(); names = {}
for x in json.load(open('site/data/items.json')): ids.add(x['id']); names[x['name']] = x['id']
pre = json.load(open('site/data/presets.json'))
for t in ('sword', 'heavy_weapon', 'helmet', 'chestplate', 'leggings', 'boots', 'shield'):
    for x in pre.get(t, []):
        if x.get('note') and ':' in x['note']:
            ids.add(x['note']); names[re.sub(r' \[.*\]', '', x['name']).strip()] = x['note']
for r in json.load(open('site/data/recipes.json'))['recipes']: ids.add(r['out'])
for r in json.load(open('site/data/relics.json')): ids.add('relics:' + r['id']); names[r['name']] = 'relics:' + r['id']

shutil.rmtree(OUT, ignore_errors=True); os.makedirs(OUT, exist_ok=True)
by = {}; miss = 0
for i in sorted(ids):
    d = icon_for(i)
    if not d: miss += 1; continue
    rel = i.replace(':', '__').replace('/', '--') + '.png'
    open(f'{OUT}/{rel}', 'wb').write(norm(d)); by[i] = rel
# sorts
sp = {}
for s in json.load(open('site/data/spells.json'))['spells']:
    d = read('irons_spellbooks', f'textures/gui/spell_icons/{s["id"]}.png')
    if d:
        rel = f'spell__{s["id"]}.png'; open(f'{OUT}/{rel}', 'wb').write(norm(d)); sp[s['id']] = rel; names[s['name']] = 'spell:' + s['id']; by['spell:' + s['id']] = rel
json.dump({'byId': by, 'byName': {n: i for n, i in names.items() if i in by}}, open('site/data/icons.json', 'w'), ensure_ascii=False, separators=(',', ':'))
tot = sum(os.path.getsize(f'{OUT}/{f}') for f in os.listdir(OUT))
print(len(by), 'icônes,', miss, 'introuvables,', tot // 1024, 'Ko')
