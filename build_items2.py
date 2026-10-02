# -*- coding: utf-8 -*-
"""Armures d'autres mods (matériaux en enum) -> out/items_extra.json ; fusionné dans items.json par build_items.py"""
import re, glob, json, os
SLOT = {'FEET': 'boots', 'LEGS': 'leggings', 'CHEST': 'chestplate', 'HEAD': 'helmet'}
IDX = {'boots': 0, 'leggings': 1, 'chestplate': 2, 'helmet': 3}
L = json.load(open('out/lang_en.json'))
MODS = {'cataclysm_src': 'cataclysm', 'unetherite_src': 'upgradednetherite', 'unetherite_items_src': 'upgradednetherite_items', 'dreadsteel_src': 'dreadsteel', 'immersive_src': 'immersive_armors'}
out = []
for d, ns in MODS.items():
    src = ''.join(open(f, encoding='utf8').read() + '\n' for f in glob.glob(f'decomp/{d}/**/*.java', recursive=True))
    mats = {}
    for m in re.finditer(r'\b([A-Z][A-Z0-9_]+)\(\s*(?:"[^"]*",\s*)?(?:\d+,\s*)?new int\[\]\{(\d+),\s*(\d+),\s*(\d+),\s*(\d+)\},\s*([\d.]+)F?', src):
        mats[m.group(1)] = ([int(m.group(i)) for i in range(2, 6)], float(m.group(6)))
    n = 0
    for m in re.finditer(r'register\(\s*"([a-z0-9_]+)",\s*\(\)\s*->\s*new [\w.]+\(\s*(?:[\w.]+\.)?([A-Z][A-Z0-9_]+)\s*,\s*EquipmentSlot\.(\w+)', src):
        rid, mat, sl = m.groups()
        if mat in mats and sl in SLOT:
            slot = SLOT[sl]; arr, t = mats[mat]
            iid = f'{ns}:{rid}'
            out.append({'id': iid, 'name': L.get(f'item.{ns}.{rid}') or rid.replace('_', ' ').title(), 'slot': slot, 'armor': arr[IDX[slot]], 'toughness': t})
            n += 1
    print(d, 'matériaux', len(mats), 'objets', n)
# Dreadsteel : valeurs de la config du pack
cfg = open('../minecraft/config/dreadsteel-common.toml').read()
g = lambda k: float(re.search(r'"%s" = ([\d.]+)' % k, cfg).group(1))
for slot, key in [('helmet', 'Helmet armor'), ('chestplate', 'Chestplate armor'), ('leggings', 'Leggings armor'), ('boots', 'Boots armor')]:
    out = [x for x in out if x['id'] != f'dreadsteel:dreadsteel_{slot}']
    out.append({'id': f'dreadsteel:dreadsteel_{slot}', 'name': 'Dreadsteel ' + slot.title(), 'slot': slot, 'armor': g(key), 'toughness': g('Armor toughness'), 'kb': g('Armor knockback resistance')})
out.append({'id': 'dreadsteel:dreadsteel_scythe', 'name': 'Dreadsteel Scythe', 'slot': None, 'dmg': g('Scythe attack damage') + 1, 'spd': g('Scythe attack speed')})
# Immersive Armors
src = open('decomp/immersive_src/immersive_armors/Items.java', encoding='utf8').read()
for m in re.finditer(r'new ExtendedArmorMaterial\("(\w+)"\)(.*?)\n   \);', src, re.S):
    nm, body = m.groups()
    p = re.search(r'protectionAmount\((\d+),\s*(\d+),\s*(\d+),\s*(\d+)\)', body)
    if not p: continue
    t = re.search(r'\.toughness\(([\d.]+)F?\)', body); k = re.search(r'knockbackReduction\(([\d.]+)F?\)', body)
    a = [int(x) for x in p.groups()]
    for slot, i in IDX.items():
        iid = f'immersive_armors:{nm}_{slot}'
        out.append({'id': iid, 'name': L.get(f'item.immersive_armors.{nm}_{slot}') or f'{nm.title()} {slot.title()}', 'slot': slot, 'armor': a[i], 'toughness': float(t.group(1)) if t else 0, 'kb': float(k.group(1)) if k else 0})
json.dump(out, open('out/items_extra.json', 'w'), ensure_ascii=False)

# ---- armes à modificateurs d'attributs (Item avec "Tool modifier")
def weapons(srcdir, ns, regfile_glob):
    classes = {}
    for f in glob.glob(f'decomp/{srcdir}/**/*.java', recursive=True):
        s = open(f, encoding='utf8').read()
        c = re.search(r'public (?:abstract )?class (\w+)', s)
        if not c: continue
        d = re.search(r'f_22281_,\s*new AttributeModifier\([^,]+,\s*"[^"]*",\s*([\d.]+)F?,\s*Operation\.ADDITION', s)
        v = re.search(r'f_22283_,\s*new AttributeModifier\([^,]+,\s*"[^"]*",\s*(-?[\d.]+)F?,\s*Operation\.ADDITION', s)
        if d: classes[c.group(1)] = (float(d.group(1)), float(v.group(1)) if v else None)
    res = []
    for f in glob.glob(f'decomp/{srcdir}/{regfile_glob}', recursive=True):
        s = open(f, encoding='utf8').read()
        for m in re.finditer(r'register\(\s*"([a-z0-9_]+)",\s*\(\)\s*->\s*new (\w+)\(', s):
            rid, cls = m.groups()
            if cls in classes:
                dmg, spd = classes[cls]
                res.append({'id': f'{ns}:{rid}', 'name': L.get(f'item.{ns}.{rid}') or rid.replace('_', ' ').title(), 'slot': None, 'dmg': dmg + 1, 'spd': 4 + spd if spd is not None else None})
    return res
extra = weapons('cataclysm_src', 'cataclysm', '**/init/ModItems.java') + weapons('celesti_src', 'celestisynth', '**/*Items*.java')
out += extra
print('armes extra', len(extra), [x['id'] for x in extra][:12])
json.dump(out, open('out/items_extra.json', 'w'), ensure_ascii=False)
