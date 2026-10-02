# -*- coding: utf-8 -*-
import json
P = {'sword': [], 'heavy_weapon': [], 'helmet': [], 'chestplate': [], 'leggings': [], 'boots': [], 'shield': []}
for n, d in [('Épée en bois', 4), ('Épée en pierre', 5), ('Épée en fer', 6), ('Épée en or', 4), ('Épée en diamant', 7), ('Épée en netherite', 8)]:
    P['sword'].append({'name': n + ' (vanilla)', 'base': {'dmg': d, 'spd': 1.6}})
for n, d in [('Hache en fer', 9), ('Hache en diamant', 9), ('Hache en netherite', 10)]:
    P['heavy_weapon'].append({'name': n + ' (vanilla)', 'base': {'dmg': d, 'spd': 1.0}})
ARM = [('cuir', (1, 3, 2, 1), 0, 0), ('mailles', (2, 5, 4, 1), 0, 0), ('fer', (2, 6, 5, 2), 0, 0), ('or', (2, 5, 3, 1), 0, 0),
       ('diamant', (3, 8, 6, 3), 2, 0), ('netherite', (3, 8, 6, 3), 3, 0.1)]
for n, (h, c, l, b), t, kb in ARM:
    for slot, v, nm in (('helmet', h, 'Casque'), ('chestplate', c, 'Plastron'), ('leggings', l, 'Jambières'), ('boots', b, 'Bottes')):
        P[slot].append({'name': f'{nm} en {n} (vanilla)', 'base': {'armor': v, 'tough': t, 'kb': kb}})
extra = json.load(open('out/presets_extra.json')) if __import__('os').path.exists('out/presets_extra.json') else {}
for k, v in extra.items(): P.setdefault(k, []).extend(v)
json.dump(P, open('site/data/presets.json', 'w'), ensure_ascii=False)
open('site/data/presets.js', 'w').write('window.D.presets=' + json.dumps(P, ensure_ascii=False) + ';')
open('site/data/builds.js', 'a').close()
print({k: len(v) for k, v in P.items()})
