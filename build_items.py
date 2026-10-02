# -*- coding: utf-8 -*-
"""Objets de base (armures / armes) : KubeJS (carpgitemmod.js) + code décompilé de cisco_mod.
-> site/data/items.json  +  out/presets_extra.json (présets pour le PoB)"""
import re, json, os, glob

LANG = json.load(open('out/lang_en.json'))
KJS = open('../minecraft/kubejs/startup_scripts/carpgitemmod.js', encoding='utf8').read()
SLOTS = {'helmet': 'helmet', 'chestplate': 'chestplate', 'leggings': 'leggings', 'boots': 'boots'}
SLOT_IDX = {0: 'boots', 1: 'leggings', 2: 'chestplate', 3: 'helmet'}


def nm(i):
    ns, p = i.split(':', 1)
    return LANG.get(f'item.{ns}.{p}') or LANG.get(f'block.{ns}.{p}') or p.replace('_', ' ').title()


def clean(t): return re.sub(r'§.', '', t).strip()


# ---- KubeJS
kjs = {}
for m in re.finditer(r"event\.modify\('([^']+)',\s*item\s*=>\s*\{(.*?)\}\)", KJS, re.S):
    iid, body = m.group(1), m.group(2)
    d = {}
    for k in ('armorProtection', 'attackDamage'):
        mm = re.search(k + r'\s*=\s*([\d.]+)', body)
        if mm: d[k] = float(mm.group(1))
    kjs.setdefault(iid, {}).update(d)

# ---- cisco_mod décompilé
ROOT = 'decomp/cisco_src/net/cisco/'
reg = {}
for m in re.finditer(r'REGISTRY\.register\("([a-z0-9_]+)",\s*\(\)\s*->\s*new ([A-Za-z0-9_.]+)\(', open(ROOT + 'init/CiscoModModItems.java', encoding='utf8').read()):
    reg[m.group(1)] = m.group(2)
# Items enregistrés via une fonction (variantes dont le nom est sur une autre ligne)
for m in re.finditer(r'REGISTRY\.register\(\s*"([a-z0-9_]+)",\s*\(\)\s*->\s*new ([A-Za-z0-9_.]+)\(', open(ROOT + 'init/CiscoModModItems.java', encoding='utf8').read()):
    reg.setdefault(m.group(1), m.group(2))


def parse_class(cname):
    f = ROOT + 'item/' + cname.split('.')[0] + '.java'
    if not os.path.exists(f): return None
    s = open(f, encoding='utf8').read()
    sub = cname.split('.')[1] if '.' in cname else None
    d = {}
    # armure
    mm = re.search(r'new int\[\]\{(\d+),\s*(\d+),\s*(\d+),\s*(\d+)\}\[slot\.m_20749_\(\)\]\s*;', s[s.find('m_7365_'):] if 'm_7365_' in s else '')
    if mm: d['defense'] = [int(x) for x in mm.groups()]  # [boots, legs, chest, helm]
    mm = re.search(r'public float m_6651_\(\)\s*\{\s*return ([\d.]+)F?;', s)
    if mm: d['toughness'] = float(mm.group(1))
    mm = re.search(r'public float m_6649_\(\)\s*\{\s*return ([\d.]+)F?;', s)
    if mm: d['kb'] = float(mm.group(1))
    # arme (Tier)
    if 'new Tier()' in s:
        mm = re.search(r'public float m_6631_\(\)\s*\{\s*return ([\d.]+)F?;', s)
        if mm: d['tierDmg'] = float(mm.group(1))
        mm = re.search(r'\}\s*,\s*(\d+)\s*,\s*(-?[\d.]+)F?\s*,\s*new Properties', s)
        if mm: d['swordDmg'] = int(mm.group(1)); d['swordSpd'] = float(mm.group(2))
    # info-bulles
    body = s
    if sub:
        a = s.find('class ' + sub + ' ')
        if a >= 0:
            b = s.find('\n   public static class', a + 10)
            body = s[a: b if b > 0 else len(s)]
    tips = [clean(x) for x in re.findall(r'list\.add\(Component\.m_237113_\("((?:[^"\\]|\\.)*)"\)\)', body)]
    tips = [t for t in tips if t and t != '-']
    d['tooltip'] = tips
    return d


items = {}
for rid, cname in reg.items():
    d = parse_class(cname)
    if d is None: continue
    iid = 'cisco_mod:' + rid
    slot = next((v for k, v in SLOTS.items() if rid.endswith('_' + k)), None)
    e = {'id': iid, 'name': nm(iid), 'slot': slot, 'tooltip': d.get('tooltip', [])}
    if slot and d.get('defense'):
        idx = {'boots': 0, 'leggings': 1, 'chestplate': 2, 'helmet': 3}[slot]
        e['armor'] = d['defense'][idx]; e['toughness'] = d.get('toughness', 0); e['kb'] = d.get('kb', 0)
    if d.get('tierDmg') is not None:
        e['dmg'] = 1 + d.get('tierDmg', 0) + d.get('swordDmg', 0); e['spd'] = 4 + d.get('swordSpd', -2.4)
    items[iid] = e

# ---- autres mods (enums de matériaux décompilés)
for e in json.load(open('out/items_extra.json')) if os.path.exists('out/items_extra.json') else []:
    items.setdefault(e['id'], {'tooltip': []}).update({k: v for k, v in e.items() if v is not None or k == 'slot'})

# ---- surcharges KubeJS
for iid, v in kjs.items():
    e = items.setdefault(iid, {'id': iid, 'name': nm(iid), 'slot': next((s for s in SLOTS if iid.endswith('_' + s)), None), 'tooltip': []})
    if 'armorProtection' in v: e['armor'] = v['armorProtection']; e['kjs'] = True
    if 'attackDamage' in v: e['dmg'] = v['attackDamage'] + 1; e['kjs'] = True

json.dump(list(items.values()), open('site/data/items.json', 'w'), ensure_ascii=False, separators=(',', ':'))
print(len(items), 'objets;', sum(1 for e in items.values() if e.get('armor') is not None), 'armures;', sum(1 for e in items.values() if e.get('dmg') is not None), 'armes')

# ---- présets pour le PoB
HEAVY = ('greataxe', 'claymore', 'greathammer', 'halberd', 'hammer', 'scythe', 'warglaive', 'mace', 'maul', 'greatsword', 'sunfire', 'shadowmourne', 'colossus')
P = {'sword': [], 'heavy_weapon': [], 'helmet': [], 'chestplate': [], 'leggings': [], 'boots': []}
for e in items.values():
    if e.get('dmg') is not None and e.get('slot') is None and 'bow' not in e['id'] and 'pickaxe' not in e['id'] and 'multitool' not in e['id']:
        heavy = any(h in e['id'] for h in HEAVY)
        spd = round(e.get('spd') or (1.0 if heavy else 1.6), 2)
        P['heavy_weapon' if heavy else 'sword'].append({'name': e['name'] + ' [' + e['id'].split(':')[0] + ']', 'base': {'dmg': round(e['dmg'], 1), 'spd': spd}, 'note': e['id']})
    elif e.get('armor') is not None and e.get('slot'):
        P[e['slot']].append({'name': e['name'] + ' [' + e['id'].split(':')[0] + ']', 'base': {'armor': e['armor'], 'tough': e.get('toughness', 0), 'kb': e.get('kb', 0)}, 'note': e['id']})
for k in P: P[k].sort(key=lambda x: -(x['base'].get('dmg') or x['base'].get('armor') or 0))
json.dump(P, open('out/presets_extra.json', 'w'), ensure_ascii=False)
print({k: len(v) for k, v in P.items()})
