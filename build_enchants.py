# -*- coding: utf-8 -*-
import json, re
L = json.load(open('out/lang_en.json'))
cfg = json.load(open('out/ench_cfg.json'))
FR = dict(l.rstrip('\n').split('|', 1) for l in open('translations/ench_fr.txt', encoding='utf8') if '|' in l)
VAN = {'protection': ('Protection', 'Armure'), 'fire_protection': ('Fire Protection', 'Armure'), 'feather_falling': ('Feather Falling', 'Armure'), 'blast_protection': ('Blast Protection', 'Armure'),
 'projectile_protection': ('Projectile Protection', 'Armure'), 'respiration': ('Respiration', 'Armure'), 'aqua_affinity': ('Aqua Affinity', 'Armure'), 'thorns': ('Thorns', 'Armure'),
 'depth_strider': ('Depth Strider', 'Armure'), 'frost_walker': ('Frost Walker', 'Armure'), 'binding_curse': ('Curse of Binding', 'Malédiction'), 'soul_speed': ('Soul Speed', 'Armure'),
 'swift_sneak': ('Swift Sneak', 'Armure'), 'sharpness': ('Sharpness', 'Arme'), 'smite': ('Smite', 'Arme'), 'bane_of_arthropods': ('Bane of Arthropods', 'Arme'), 'knockback': ('Knockback', 'Arme'),
 'fire_aspect': ('Fire Aspect', 'Arme'), 'looting': ('Looting', 'Arme'), 'sweeping': ('Sweeping Edge', 'Arme'), 'efficiency': ('Efficiency', 'Outil'), 'silk_touch': ('Silk Touch', 'Outil'),
 'unbreaking': ('Unbreaking', 'Tous'), 'fortune': ('Fortune', 'Outil'), 'power': ('Power', 'Arc'), 'punch': ('Punch', 'Arc'), 'flame': ('Flame', 'Arc'), 'infinity': ('Infinity', 'Arc'),
 'luck_of_the_sea': ('Luck of the Sea', 'Canne à pêche'), 'lure': ('Lure', 'Canne à pêche'), 'loyalty': ('Loyalty', 'Trident'), 'impaling': ('Impaling', 'Trident'), 'riptide': ('Riptide', 'Trident'),
 'channeling': ('Channeling', 'Trident'), 'multishot': ('Multishot', 'Arbalète'), 'quick_charge': ('Quick Charge', 'Arbalète'), 'piercing': ('Piercing', 'Arbalète'), 'mending': ('Mending', 'Tous'),
 'vanishing_curse': ('Curse of Vanishing', 'Malédiction')}
MOD = {'apotheosis': 'Apotheosis', 'majruszsenchantments': "Majrusz's Enchantments", 'dragonenchants': 'Dragon Enchants', 'enigmaticlegacy': 'Enigmatic Legacy', 'enigmaticaddons': 'Enigmatic Addons'}
out = []
for k in sorted({'.'.join(x.split('.')[1:3]) for x in L if x.startswith('enchantment.') and x.count('.') == 2 and x.split('.')[1] != 'level'}) + ['minecraft.' + v for v in VAN]:
    ns, p = k.split('.'); iid = f'{ns}:{p}'
    c = cfg.get(iid, {})
    if ns == 'minecraft': name, cat = VAN[p]
    else: name = L.get(f'enchantment.{ns}.{p}') or p; cat = 'Malédiction' if 'curse' in p else 'Autre'
    out.append({'id': iid, 'name': name, 'mod': MOD.get(ns, 'Vanilla' if ns == 'minecraft' else ns), 'cat': cat, 'desc': FR.get(iid) or L.get(f'enchantment.{ns}.{p}.desc', ''), 'max': c.get('max') or None, 'loot': c.get('loot') or None,
                'rarity': c.get('rarity'), 'treasure': c.get('treasure', False)})
json.dump(out, open('site/data/enchants.json', 'w'), ensure_ascii=False)
print(len(out), sum(1 for o in out if not o['desc']), sum(1 for o in out if o['max'] is None))
