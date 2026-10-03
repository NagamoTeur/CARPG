# -*- coding: utf-8 -*-
"""Reliques (mod Relics) -> site/data/relics.json
Valeurs : config/relics/*.json du serveur ; formules de montée de niveau : code décompilé (ADD / MULTIPLY_BASE / MULTIPLY_TOTAL) ; textes : fichiers de langue."""
import json, glob, os, re
import jl, sys
sys.path.insert(0, 'translations')
from relics_fr_list import FR as FRL, TEMPLATES as TPL

CFG = '/home/nagamo/Documents/Dev/Minecraft/minecraft/config/relics'
EN = json.load(open('out/lang_en.json')); FR = json.load(open('out/lang_fr.json'))
SRC = 'decomp/relics_src'


def val(op, cur, step, pts):
    if op == 'ADD': return cur + pts * step
    if op == 'MULTIPLY_BASE': return cur + cur * step * pts
    return cur * (step + 1) ** pts


# drapeau « pourcentage » : le formatValue du code multiplie par 100
pct = {}
for f in glob.glob(SRC + '/**/*.java', recursive=True):
    t = open(f, encoding='utf8', errors='replace').read()
    m = re.search(r'class (\w+)Item', t)
    if not m: continue
    for sm in re.finditer(r'StatData\.builder\("(\w+)"\)(.*?)\.build\(\)', t, re.S):
        pct[(m.group(1), sm.group(1))] = '100' in sm.group(2)
    pct['_file_' + m.group(1)] = f


def fmt(v, is_pct, tmpl):
    x = v * 100 if is_pct else v
    s = (f'{x:.3f}' if abs(x) < 1 else f'{x:.1f}' if abs(x) < 1000 else f'{x:.0f}').rstrip('0').rstrip('.') if x else '0'
    return tmpl.replace('%1$s', s.replace('.', ',')).replace('%%', '%')


def lang(k): return EN.get(k) or ''


LOOT_FR = {
    r'[\w]+:chests\/[\w_\/]*(bastion|piglin)[\w_\/]*': 'coffres des bastions', r'[\w]+:chests\/[\w_\/]*village[\w_\/]*': 'coffres de village', 'minecraft:chests/ruined_portal': 'portails en ruine',
    r'[\w]+:chests\/[\w_\/]*(nether|hell|lava|magma|fire|burn|fortress)[\w_\/]*': 'coffres du Nether (forteresses, lave, feu)', r'[\w]+:chests\/[\w_\/]*(mineshaft|city|stronghold)[\w_\/]*': 'mines abandonnées, cités et forteresses (strongholds)',
    'minecraft:chests/woodland_mansion': 'manoirs', r'[\w]+:chests\/[\w_\/]*(pillag|outpost)[\w_\/]*': 'avant-postes de pillards', r'[\w]+:chests\/[\w_\/]*(water|ocean|river|(?<!air)ship|aqua)[\w_\/]*': 'océans, rivières et épaves',
    'minecraft:chests/buried_treasure': 'trésors enterrés', r'[\w]+:chests\/[\w_\/]*(end|warp)[\w_\/]*': "coffres de l'End et du biome tordu", r'[\w]+:chests\/[\w_\/]*(frosz?|taiga|cold|winter|snow|icey?|glac)[\w_\/]*': 'coffres glacés (taïga, neige, glace)',
    'minecraft:chests/village/village_fletcher': 'maison de l\'archer des villages', 'minecraft:chests/igloo_chest': 'igloos', r'[\w]+:chests\/[\w_\/]*(desert|sand|pyramid)[\w_\/]*': 'déserts et pyramides',
    'minecraft:chests/ancient_city': 'cités antiques', 'minecraft:chests/ancient_city_ice_box': 'cités antiques (chambre froide)', r'[\w]+:chests\/[\w_\/]*(warden|sculk|echo)[\w_\/]*': 'Abysses profonds (Warden, sculk)',
    r'[\w]+:chests\/[\w_\/]*(jungle|temple)[\w_\/]*': 'jungles et temples'}
R = []
for f in sorted(glob.glob(CFG + '/*.json')):
    rid = os.path.basename(f)[:-5]
    cfg = jl.load(f)
    cls = ''.join(w.title() for w in rid.split('_'))
    abil = []
    for ab, ad in cfg.get('ability', {}).get('abilities', {}).items():
        stats = []
        for st, sd in ad.get('stats', {}).items():
            op = sd['upgradeOperation']; mn, mx = sd['minInitialValue'], sd['maxInitialValue']; step = sd['upgradeModifier']; ml = ad.get('maxLevel', 10)
            p = pct.get((cls, st), False)
            tmpl = TPL.get(lang(f'tooltip.relics.{rid}.ability.{ab}.stat.{st}.value'), lang(f'tooltip.relics.{rid}.ability.{ab}.stat.{st}.value')) or '%1$s'
            a0 = fmt(mn, p, tmpl) if mn == mx else fmt(mn, p, '%1$s') + ' à ' + fmt(mx, p, tmpl)
            a1 = fmt(val(op, mn, step, ml), p, '%1$s') + (' à ' + fmt(val(op, mx, step, ml), p, tmpl) if mn != mx else '')
            if mn == mx: a1 = fmt(val(op, mn, step, ml), p, tmpl)
            stats.append({'title': lang(f'tooltip.relics.{rid}.ability.{ab}.stat.{st}.title').rstrip(':'), 'level0': a0, 'levelmax': a1})
        abil.append({'name': lang(f'tooltip.relics.{rid}.ability.{ab}') or ab, 'desc': lang(f'tooltip.relics.{rid}.ability.{ab}.description'), 'points': ad.get('requiredPoints', 1), 'level': ad.get('requiredLevel', 0), 'max': ad.get('maxLevel', 10), 'stats': stats})
    loot = []
    for rx, ch in (cfg.get('loot', {}).get('entries') or {}).items():
        loot.append({'where': LOOT_FR.get(rx, rx), 'chance': ch})
    R.append({'id': rid, 'name': EN.get(f'item.relics.{rid}') or rid.replace('_', ' ').title(), 'fr': FR.get(f'item.relics.{rid}'), 'leveling': lang(f'tooltip.relics.{rid}.leveling'), 'maxLevel': cfg.get('leveling', {}).get('maxLevel', 10),
              'abilities': abil, 'loot': loot})
# traduction : même ordre que la liste de chaînes (noms, textes de niveau, capacités, statistiques)
S = []
def add(x):
    if x and x not in S: S.append(x)
for x in R:
    add(x['name']); add(x['leveling'])
    for a in x['abilities']:
        add(a['name']); add(a['desc'])
        for st in a['stats']: add(st['title'])
assert len(S) == len(FRL), (len(S), len(FRL))
T = dict(zip(S, FRL))
for x in R:
    x['fr'] = x['fr'] or T[x['name']]; x['leveling'] = T.get(x['leveling'], x['leveling'])
    for a in x['abilities']:
        a['name'] = T.get(a['name'], a['name']); a['desc'] = T.get(a['desc'], a['desc'])
        for st in a['stats']: st['title'] = T.get(st['title'], st['title'])
json.dump(R, open('site/data/relics.json', 'w'), ensure_ascii=False, separators=(',', ':'))
print(len(R), 'reliques ;', sum(len(r['abilities']) for r in R), 'capacités')
