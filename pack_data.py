# -*- coding: utf-8 -*-
"""Assemble tous les JSON en site/data/data.js (window.D) -> la page marche en ouvrant simplement index.html"""
import json, sys, collections
sys.path.insert(0, '.')
import jl
from fr import *
D = {}
for n in ['skilltree', 'gems', 'affixes', 'origins', 'spells', 'attrs', 'quests', 'bosses', 'items', 'enchants']:
    D[n] = json.load(open(f'site/data/{n}.json'))
# raretés
rar = {}
for r in RARITY_ORDER:
    d = jl.load(f'merged/data/apotheosis/rarities/{r}.json')
    c = collections.Counter(); sock = []
    for x in d['rules']:
        t = x['type']
        if t == 'socket': sock.append(x['chance'])
        elif t in ('stat', 'ability'): c[t] += 1
    rar[r] = {'name': RARITY_FR[r], 'color': RARITY_COLOR[r], 'stat': c['stat'], 'ability': c['ability'], 'sockets': len(sock), 'socketChances': sock,
              'quality': d.get('quality', 0), 'weight': d.get('weight', 0)}
D['rarities'] = rar
D['originRules'] = json.load(open('site/data/origin_rules.json'))
D['caps'] = json.load(open('site/data/caps.json'))
D['typeFr'] = TYPE_FR
D['rarityOrder'] = RARITY_ORDER
for a in D['affixes']:
    a['slot'] = 'stat' if a['kind'] == 'attribute' else 'ability'
for fn in ('flags',):
    pass
open('site/data/data.js', 'w').write('window.D=' + json.dumps(D, ensure_ascii=False, separators=(',', ':')) + ';')
import os
print(os.path.getsize('site/data/data.js') // 1024, 'Ko')
