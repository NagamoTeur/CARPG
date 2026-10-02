# -*- coding: utf-8 -*-
import glob, re, json, os, tomllib
L = json.load(open('out/lang_en.json'))
CFG = '../minecraft/config/ars_nouveau/'
cls = {}
for f in glob.glob('decomp/ars_src/com/hollingsworth/arsnouveau/common/spell/*/*.java'):
    n = os.path.basename(f)[:-5]; d = f.split('/')[-2]
    m = re.match(r'(Method|Effect|Augment)(\w+)', n)
    if not m: continue
    gid = 'glyph_' + re.sub(r'(?<!^)(?=[A-Z])', '_', m.group(2)).lower()
    cls[gid] = {'method': 'forme', 'effect': 'effet', 'augment': 'augmentation'}.get(d, '?')
out = []
for f in sorted(glob.glob(CFG + 'glyph_*.toml')):
    gid = os.path.basename(f)[:-5]
    t = tomllib.load(open(f, 'rb')).get('general', {})
    out.append({'id': gid, 'name': L.get(f'ars_nouveau.glyph_name.{gid}', gid), 'desc': L.get(f'ars_nouveau.glyph_desc.{gid}', ''),
                'kind': cls.get(gid, '?'), 'cost': t.get('cost'), 'tier': t.get('glyph_tier'), 'starter': t.get('starter'), 'enabled': t.get('enabled', True),
                'damage': t.get('damage'), 'limit': t.get('per_spell_limit') if (t.get('per_spell_limit') or 0) < 1e6 else None})
mana = tomllib.load(open('../minecraft/config/ars_nouveau/../ars_nouveau-server.toml', 'rb')) if False else {}
json.dump(out, open('site/data/ars.json', 'w'), ensure_ascii=False)
import collections; print(len(out), collections.Counter(o['kind'] for o in out))
