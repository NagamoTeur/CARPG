# -*- coding: utf-8 -*-
"""Restrictions d'équipement des origines -> site/data/origin_rules.json
Lues dans les pouvoirs des datapacks (origins:restrict_armor, origins:prevent_item_use) des jars et des datapacks Paxi.
Règles produites par origine : noShield, armorMax {head, chest, legs, feet} (pièce interdite si sa valeur d'armure est STRICTEMENT supérieure), noBow."""
import json, glob, os
import jl

ROOT = '/home/nagamo/Documents/Dev/Minecraft/minecraft'
SRC_DIRS = ['extract/*/data', 'extract_paxi/*/data', ROOT + '/config/paxi/datapacks/*/data']


def find(kind, ns, path):
    """chemin d'un fichier de datapack ; les datapacks Paxi (listés en dernier) l'emportent"""
    hit = None
    for pat in SRC_DIRS:
        for d in glob.glob(pat):
            f = f'{d}/{ns}/{kind}/{path}.json'
            if os.path.exists(f): hit = f
    return hit


def powers_of(pid):
    ns, _, p = pid.partition(':')
    f = find('powers', ns, p)
    if not f: return []
    try: j = jl.load(f)
    except Exception: return []
    if j.get('type', '').endswith('multiple'):
        return [v for k, v in j.items() if isinstance(v, dict) and 'type' in v]
    return [j]


def tags_shield(c):
    s = json.dumps(c)
    return 'origins:shields' in s or '"minecraft:shield"' in s


R = {}
origins = set()
for pat in SRC_DIRS:
    for f in glob.glob(f'{pat}/*/origins/*.json'):
        ns = f.split('/data/')[1].split('/')[0]; origins.add(f'{ns}:{os.path.basename(f)[:-5]}')
for oid in sorted(origins):
    ns, _, p = oid.partition(':')
    f = find('origins', ns, p)
    try: o = jl.load(f)
    except Exception: continue
    rule = {}
    for pw in o.get('powers', []):
        for j in powers_of(pw):
            if j.get('hidden') in (True, 'true') and 'restrict_armor' not in j.get('type', ''): pass
            t = j.get('type', '').split(':')[-1]
            if t == 'prevent_item_use':
                ic = j.get('item_condition')
                if ic and tags_shield(ic): rule['noShield'] = True
                cond = json.dumps(j.get('condition', {}))
                if 'minecraft:bow' in cond or 'buzz:bows' in json.dumps(ic or {}): rule['noBow'] = True
            if t == 'restrict_armor':
                am = {}
                for slot, key in (('head', 'head'), ('chest', 'chest'), ('legs', 'legs'), ('feet', 'feet')):
                    c = j.get(slot)
                    if isinstance(c, dict) and c.get('type', '').endswith('armor_value') and c.get('comparison') == '>': am[key] = c['compare_to']
                if am and len(am) == 4: rule['armorMax'] = am
    if rule: R[oid] = rule
json.dump(R, open('site/data/origin_rules.json', 'w'), ensure_ascii=False, indent=0)
print(len(R), 'origines avec restrictions')
for k, v in R.items(): print(' ', k, v)
