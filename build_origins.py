# -*- coding: utf-8 -*-
"""Origines / Classes / Bénédictions divines -> site/data/origins.json (+ out/translate_origins.json)"""
import json, glob, os, zipfile, collections
import jl
from fr import *

M = 'merged/data/'
L = json.load(open('out/lang_en.json'))
PX = '../minecraft/config/paxi/datapacks/'
TR = {}
if os.path.exists('translations/origins_fr.json'):
    TR = json.load(open('translations/origins_fr.json'))

LAYER_NAMES = {'origins:origin': 'Origine', 'origins-classes:class': 'Classe', 'cisco_rpg_origins:divineblessings': 'Bénédiction divine'}


def collect_layers():
    layers = collections.OrderedDict()
    def add(layer, data):
        for o in data['origins']:
            o = o if isinstance(o, str) else o['origin']
            if o not in layers.setdefault(layer, []): layers[layer].append(o)
    for f in sorted(glob.glob('extract/*/data/*/origin_layers/*.json')):
        d = jl.load(f); add(f.split('/')[3] + ':' + os.path.basename(f)[:-5], d)
    for z in sorted(glob.glob(PX + '*.zip')):
        zf = zipfile.ZipFile(z)
        for n in zf.namelist():
            if '/origin_layers/' in n and n.endswith('.json'):
                add(n.split('/')[1] + ':' + os.path.basename(n)[:-5], jl.loads(zf.read(n).decode('utf8')))
    for d in sorted(glob.glob(PX + '*/')):
        for f in glob.glob(d + 'data/*/origin_layers/*.json'):
            add(f.split('/')[-3] + ':' + os.path.basename(f)[:-5], jl.load(f))
    return layers


def pload(pid):
    ns, p = pid.split(':', 1)
    f = f'{M}{ns}/powers/{p}.json'
    return jl.load(f) if os.path.exists(f) else None


def mods_of(d, out, cond=None):
    """collecte récursive des modificateurs d'attributs d'un pouvoir"""
    if isinstance(d, dict):
        c = d.get('condition')
        cc = cond
        if isinstance(c, dict) and c.get('type'):
            cc = c
        elif isinstance(c, str):
            cc = {'type': c}
        m = d.get('modifier')
        ms = [m] if isinstance(m, dict) else (d.get('modifiers') or [])
        if str(d.get('type', '')).endswith(('attribute', 'conditioned_attribute')):
            for mm in ms:
                if isinstance(mm, dict) and 'attribute' in mm and 'value' in mm:
                    out.append({'attr': mm['attribute'], 'op': OPS.get(mm.get('operation', 'addition'), 0), 'val': mm['value'],
                                'cond': cc.get('type') if cc else None, 'adv': cc.get('advancement') if cc else None})
        t = str(d.get('type', ''))
        PM = {'modify_damage_dealt': 'pob:dmg_dealt', 'modify_projectile_damage': 'pob:proj_dmg', 'modify_damage_taken': 'pob:dmg_taken'}
        for key, attr in PM.items():
            if t.endswith(key) and isinstance(d.get('modifier'), dict) and 'value' in d['modifier']:
                c2 = cc
                tc = d.get('target_condition')
                if tc: c2 = tc if isinstance(tc, dict) else {'type': tc}
                dc = d.get('damage_condition') or d.get('bientity_condition')
                if isinstance(dc, dict) and dc.get('type') not in (None, 'origins:and') and not c2: c2 = dc
                out.append({'attr': attr, 'op': 0, 'val': d['modifier']['value'], 'cond': c2.get('type') if c2 else None, 'adv': c2.get('advancement') if c2 else None})
        for k, v in d.items():
            if k in ('condition',): continue
            mods_of(v, out, cc)
    elif isinstance(d, list):
        for x in d: mods_of(x, out, cond)


def build_origin(oid):
    ns, p = oid.split(':', 1)
    f = f'{M}{ns}/origins/{p}.json'
    if not os.path.exists(f): return None
    d = jl.load(f)
    if d.get('unchoosable'): return None
    name = d.get('name') or L.get(f'origin.{ns}.{p}.name') or p
    desc = d.get('description') or L.get(f'origin.{ns}.{p}.description') or ''
    icon = d.get('icon'); icon = icon.get('item') if isinstance(icon, dict) else icon
    powers = []
    for pid in d.get('powers', []):
        pd = pload(pid)
        pns, pp = pid.split(':', 1)
        if pd is None: continue
        if pd.get('hidden') and not (pd.get('name') or L.get(f'power.{pns}.{pp}.name')): continue
        pname = pd.get('name') or L.get(f'power.{pns}.{pp}.name')
        pdesc = pd.get('description') or L.get(f'power.{pns}.{pp}.description')
        if not pname: continue
        st = []
        # sous-pouvoirs "multiple"
        mods_of(pd, st)
        for sub in [k for k in pd if k not in ('type', 'name', 'description', 'hidden', 'loading_priority', 'badges') and isinstance(pd[k], dict)]:
            pass
        powers.append({'id': pid, 'name': pname, 'desc': pdesc or '', 'stats': st, 'hidden': bool(pd.get('hidden')), 'type': pd.get('type', '').split(':')[-1]})
    return {'id': oid, 'name': name, 'desc': desc, 'impact': d.get('impact', 0), 'icon': icon, 'powers': powers, 'order': d.get('order', 99)}


def main():
    layers = collect_layers()
    out = {}
    todo = {}
    for layer, ids in layers.items():
        lst = []
        for oid in ids:
            o = build_origin(oid)
            if not o: continue
            # traductions
            o['name_fr'] = TR.get(o['id'] + '#name')
            o['desc_fr'] = TR.get(o['id'] + '#desc')
            if o['desc']: todo[o['id'] + '#desc'] = o['desc']
            for pw in o['powers']:
                pw['desc_fr'] = TR.get(pw['id'] + '#desc')
                pw['name_fr'] = TR.get(pw['id'] + '#name')
                if pw['desc']: todo[pw['id'] + '#desc'] = pw['desc']
                if pw['name']: todo[pw['id'] + '#name'] = pw['name']
            lst.append(o)
        out[layer] = {'title': LAYER_NAMES.get(layer, layer), 'origins': lst}
    json.dump(out, open('site/data/origins.json', 'w'), ensure_ascii=False, separators=(',', ':'))
    json.dump(todo, open('out/translate_origins.json', 'w'), ensure_ascii=False, indent=0)
    for l, v in out.items(): print(l, len(v['origins']), sum(len(o['powers']) for o in v['origins']))
    print('à traduire:', len(todo))


if __name__ == '__main__':
    main()
