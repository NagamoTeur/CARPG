# -*- coding: utf-8 -*-
"""Normalise l'arbre de talents (Passive Skill Tree) -> site/data/skilltree.json"""
import json, glob, os, re
from fr import *

D = 'merged/data/skilltree/skills/'
LANG = json.load(open('extract/skilltree/assets/skilltree/lang/en_us.json'))

EQUIP_FR = {'any': 'tout équipement', 'weapon': 'armes', 'melee_weapon': 'armes de mêlée', 'ranged_weapon': 'armes à distance',
            'armor': 'armures', 'shield': 'boucliers', 'sword': 'épées', 'bow': 'arcs', 'crossbow': 'arbalètes',
            'trident': 'tridents', 'pickaxe': 'pioches', 'axe': 'haches', 'hoe': 'houes', 'shovel': 'pelles',
            'helmet': 'casques', 'chestplate': 'plastrons', 'leggings': 'jambières', 'boots': 'bottes',
            'ring': 'anneaux', 'necklace': 'colliers', 'jewelry': 'bijoux', 'tool': 'outils', 'fishing_rod': 'cannes à pêche',
            'heavy_weapon': 'armes lourdes', 'elytra': 'élytres'}
LOOT_FR = {'mobs': 'butin des monstres', 'blocks': 'butin des blocs', 'fishing': 'pêche', 'gems': 'gemmes', 'chests': 'coffres',
           'archaeology': 'archéologie'}


def eq(c):
    if not c: return ''
    t = c.get('type', '')
    if t == 'skilltree:equipment_type':
        return EQUIP_FR.get(c.get('equipment_type'), c.get('equipment_type', ''))
    if t == 'skilltree:tag': return 'objets « %s »' % c.get('tag_id')
    if t == 'skilltree:potion':
        return {'any': 'potions', 'beneficial': 'potions bénéfiques', 'harmful': 'potions nocives', 'neutral': 'potions neutres'}.get(c.get('potion_type'), 'potions')
    if t == 'skilltree:food': return 'nourriture'
    if t == 'skilltree:enchanted': return 'objets enchantés'
    if t == 'skilltree:none': return ''
    return t.split(':')[-1]


def cond(b):
    """Conditions lisibles. Retourne (liste de textes, bool 'inconditionnel')."""
    out = []
    pc = b.get('player_condition') or {}
    t = pc.get('type', 'skilltree:none')
    if t == 'skilltree:food_level': out.append('si la faim ≥ %s' % fnum(pc.get('min')))
    elif t == 'skilltree:has_item_in_hand': out.append('en tenant : %s' % eq(pc.get('item_condition')))
    elif t == 'skilltree:has_item_equipped': out.append('avec un objet équipé : %s' % eq(pc.get('item_condition')))
    elif t == 'skilltree:has_gems': out.append('si %s ont ≥ %s gemme(s)' % (eq(pc.get('item_condition')), pc.get('min', 1)))
    elif t == 'skilltree:health_percentage':
        if 'max' in pc: out.append('si PV ≤ %s %%' % fnum(pc['max'] * 100, 0))
        if 'min' in pc: out.append('si PV ≥ %s %%' % fnum(pc['min'] * 100, 0))
    elif t == 'skilltree:effect_amount': out.append('selon vos effets de potion actifs')
    elif t == 'skilltree:fishing': out.append('en pêchant')
    elif t == 'skilltree:attribute_value':
        out.append('si %s ≥ %s' % (ATTR_FR.get(pc.get('attribute'), pc.get('attribute')), fnum(pc.get('min', 0))))
    elif t != 'skilltree:none': out.append(t.split(':')[-1])
    dc = b.get('damage_condition') or {}
    t = dc.get('type', 'skilltree:none')
    if t == 'skilltree:melee': out.append('dégâts de mêlée')
    elif t == 'skilltree:projectile': out.append('dégâts de projectile')
    tc = b.get('target_condition') or {}
    t = tc.get('type', 'skilltree:none')
    if t == 'skilltree:burning': out.append('contre une cible en feu')
    elif t == 'skilltree:has_effect': out.append('contre une cible sous « %s »' % tc.get('effect', '?').split(':')[-1])
    m = b.get('player_multiplier') or {}
    t = m.get('type', 'skilltree:none')
    mult = ''
    if t == 'skilltree:gems_amount': mult = 'par gemme sur %s' % (eq(m.get('item_condition')) or 'votre équipement')
    elif t == 'skilltree:distance_to_target': mult = 'par bloc de distance à la cible'
    elif t == 'skilltree:attribute_value':
        mult = 'par %s %s' % (fnum(m.get('divisor', 1)), ATTR_FR.get(m.get('attribute'), m.get('attribute')))
    elif t == 'skilltree:enchants_amount': mult = 'par enchantement sur %s' % (eq(m.get('item_condition')) or 'votre équipement')
    elif t == 'skilltree:enchants_levels': mult = 'par niveau d\'enchantement'
    elif t == 'skilltree:effect_amount': mult = 'par effet de potion actif'
    elif t == 'skilltree:food_level': mult = 'par point de faim'
    elif t != 'skilltree:none': mult = t.split(':')[-1]
    return out, mult


def trigger(b):
    el = b.get('event_listener')
    if not el: return ''
    t = el['type'].split(':')[-1]
    return {'attack': 'quand vous frappez', 'block': 'quand vous bloquez', 'item_used': 'à l\'usage d\'un objet'}.get(t, t)


def pct(x, nd=1): return fnum(x * 100, nd) + ' %'


def describe(b, depth=0):
    """-> (texte FR, [stat calculables])  stat = {attr,op,val}"""
    t = b['type'].split(':')[-1]
    conds, mult = cond(b)
    stats = []
    txt = ''
    if t == 'attribute':
        txt = fmt_attr(b['attribute'], b['operation'], b['amount'])
        if not conds and not mult:
            stats.append({'attr': b['attribute'], 'op': b['operation'], 'val': b['amount']})
    elif t == 'damage':
        a = b['amount']
        txt = ('+' if a >= 0 else '−') + pct(abs(a)) + ' de dégâts' if b.get('operation', 1) != 0 else ('+%s dégâts' % fnum(a))
        if b.get('operation', 1) != 0 and not conds and not mult:
            stats.append({'attr': 'pob:damage_pct', 'op': 0, 'val': a})
    elif t == 'crit_chance':
        txt = '+' + pct(b['chance']) + ' de chance de critique'
        if not conds and not mult: stats.append({'attr': 'apotheosis:crit_chance', 'op': 0, 'val': b['chance']})
    elif t == 'crit_damage':
        txt = '+' + pct(b['amount']) + ' de dégâts critiques'
        if not conds and not mult: stats.append({'attr': 'apotheosis:crit_damage', 'op': 0, 'val': b['amount']})
    elif t == 'healing':
        txt = 'Soigne %s PV (chance %s)' % (fnum(b['amount']), pct(b.get('chance', 1), 0)) if b['amount'] >= 1 else 'Soigne %s des dégâts infligés/subis (chance %s)' % (pct(b['amount'], 0), pct(b.get('chance', 1), 0))
        tr = trigger(b)
        if tr: txt += ' ' + tr
    elif t == 'incoming_healing': txt = '+' + pct(b['multiplier']) + ' de soins reçus'
    elif t == 'gem_power': txt = '+' + pct(b['multiplier'], 0) + ' d\'efficacité des gemmes (%s)' % (eq(b.get('item_condition')) or 'équipement')
    elif t == 'loot_duplication':
        txt = '%s de chance de doubler le butin (%s)' % (pct(b['chance'], 0), LOOT_FR.get(b.get('loot_type'), b.get('loot_type')))
        if b.get('multiplier', 1) not in (1, 1.0): txt = '%s de chance de multiplier ×%s le butin (%s)' % (pct(b['chance'], 0), fnum(b['multiplier'] + 1), LOOT_FR.get(b.get('loot_type'), b.get('loot_type')))
    elif t == 'gained_experience': txt = '+' + pct(b['multiplier'], 0) + ' d\'XP gagnée (%s)' % {'fishing': 'pêche', 'mining': 'minage', 'mobs': 'monstres', 'ore': 'minerais', 'breeding': 'élevage'}.get(b.get('experience_source'), b.get('experience_source'))
    elif t == 'repair_efficiency': txt = '+' + pct(b['multiplier'], 0) + ' d\'efficacité de réparation'
    elif t == 'block_break_speed': txt = '+' + pct(b['multiplier'], 0) + ' de vitesse de minage'
    elif t == 'enchantment_requirement': txt = '−' + pct(abs(b['multiplier']), 0) + ' de niveaux requis pour enchanter'
    elif t == 'enchantment_amplification': txt = '%s de chance +1 niveau sur les enchantements (%s)' % (pct(b['chance'], 0), {'skilltree:armor': 'armures', 'skilltree:weapon': 'armes'}.get((b.get('enchantment_condition') or {}).get('type'), 'équipement'))
    elif t == 'free_enchantment': txt = '%s de chance d\'enchanter gratuitement' % pct(b['chance'], 0)
    elif t == 'player_sockets': txt = '+%s emplacement(s) de gemme sur : %s' % (b['sockets'], eq(b.get('item_condition')))
    elif t == 'arrow_retrieval': txt = '%s de chance de récupérer les flèches' % pct(b['chance'], 0)
    elif t == 'ignite': txt = '%s de chance d\'enflammer la cible (%s s)' % (pct(b['chance'], 0), fnum(b['duration'] / 20 if b['duration'] > 20 else b['duration']))
    elif t == 'jump_height': txt = '+' + pct(b['multiplier'], 0) + ' de hauteur de saut'
    elif t == 'recipe_unlock': txt = 'Débloque la recette : ' + b.get('recipe_id', '?')
    elif t == 'crafted_item_bonus':
        ib = b['item_bonus']; it = ib['type'].split(':')[-1]; tgt = eq(b.get('item_condition'))
        if it == 'skill_bonus':
            inner, _ = describe(ib['skill_bonus'], depth + 1)
            txt = 'Objets fabriqués (%s) : %s' % (tgt, inner)
        elif it == 'potion_duration': txt = 'Potions fabriquées : +%s de durée' % pct(ib['multiplier'], 0)
        elif it == 'potion_amplification': txt = 'Potions fabriquées : %s de chance +1 niveau' % pct(ib['chance'], 0)
        elif it == 'durability': txt = 'Objets fabriqués (%s) : +%s de durabilité' % (tgt, pct(ib['amount'], 0) if ib.get('operation', 1) else fnum(ib['amount']))
        elif it == 'quiver_capacity': txt = 'Carquois fabriqués : +%s de capacité' % fnum(ib['chance'])
        elif it == 'food_effect': txt = 'Nourriture fabriquée : effet « %s » niv. %s' % (ib.get('effect', '?').split(':')[-1], ib['amplifier'] + 1)
        elif it == 'food_healing': txt = 'Nourriture fabriquée : +%s de soin' % fnum(ib['amount'])
        elif it == 'food_saturation': txt = 'Nourriture fabriquée : +%s de saturation' % pct(ib['multiplier'], 0)
        elif it == 'sockets': txt = 'Objets fabriqués (%s) : +%s emplacement(s) de gemme' % (tgt, ib['amount'])
        else: txt = 'Objets fabriqués (%s) : %s' % (tgt, it)
    else:
        txt = t
    extra = []
    if mult: extra.append(mult)
    extra += conds
    tr = trigger(b) if t != 'healing' else ''
    if tr: extra.append(tr)
    if extra: txt += ' — ' + ', '.join(extra)
    return txt, stats



def eqkey(c):
    if not c: return 'any'
    t = c.get('type', '')
    if t == 'skilltree:equipment_type': return c.get('equipment_type', 'any')
    if t == 'skilltree:tag': return 'tag:' + c.get('tag_id', '')
    return 'any'


def struct(b):
    """Représentation calculable d'un bonus : liste de stats {attr,op,val,c,m}. c = conditions (toutes requises), m = multiplicateur par unité."""
    t = b['type'].split(':')[-1]
    c = {}
    pc = b.get('player_condition') or {}
    pt = pc.get('type', 'skilltree:none')
    if pt == 'skilltree:food_level': c['food'] = pc.get('min', 0)
    elif pt == 'skilltree:has_item_in_hand': c['hand'] = eqkey(pc.get('item_condition'))
    elif pt == 'skilltree:has_item_equipped': c['equipped'] = eqkey(pc.get('item_condition'))
    elif pt == 'skilltree:has_gems': c['gems'] = [eqkey(pc.get('item_condition')), pc.get('min', 1)]
    elif pt == 'skilltree:health_percentage': c['hp'] = [pc.get('min'), pc.get('max')]
    elif pt == 'skilltree:effect_amount': c['potions'] = 1
    elif pt == 'skilltree:attribute_value': c['attr'] = [pc.get('attribute'), pc.get('min', 0)]
    elif pt != 'skilltree:none': c['unknown'] = pt
    dc = (b.get('damage_condition') or {}).get('type', 'skilltree:none')
    if dc == 'skilltree:melee': c['melee'] = 1
    elif dc == 'skilltree:projectile': c['proj'] = 1
    tc = b.get('target_condition') or {}
    tt = tc.get('type', 'skilltree:none')
    if tt == 'skilltree:burning': c['burning'] = 1
    elif tt == 'skilltree:has_effect': c['targetEffect'] = tc.get('effect')
    mm = None
    pm = b.get('player_multiplier') or {}
    mt = pm.get('type', 'skilltree:none')
    if mt == 'skilltree:gems_amount': mm = {'k': 'gems', 'item': eqkey(pm.get('item_condition'))}
    elif mt == 'skilltree:distance_to_target': mm = {'k': 'dist'}
    elif mt == 'skilltree:attribute_value': mm = {'k': 'attr', 'attr': pm.get('attribute'), 'div': pm.get('divisor', 1)}
    elif mt == 'skilltree:food_level': mm = {'k': 'food'}
    elif mt == 'skilltree:enchants_amount': mm = {'k': 'enchants', 'item': eqkey(pm.get('item_condition'))}
    elif mt == 'skilltree:enchants_levels': mm = {'k': 'enchlevels'}
    elif mt == 'skilltree:effect_amount': mm = {'k': 'potions'}
    elif mt != 'skilltree:none': mm = {'k': 'unknown'}
    def mk(attr, op, val, **extra):
        d = {'attr': attr, 'op': op, 'val': val}
        if c: d['c'] = dict(c)
        if mm: d['m'] = dict(mm)
        d.update(extra); return d
    if t == 'attribute':
        if b['attribute'] == 'curios:ring': return [mk('pob:ring_slots', 0, b['amount'])]
        return [mk(b['attribute'], b['operation'], b['amount'])]
    if t == 'damage':
        if b.get('operation', 1) != 0: return [mk('pob:damage_pct', 0, b['amount'])]
        return [mk('minecraft:generic.attack_damage', 0, b['amount'])]
    if t == 'crit_chance': return [mk('apotheosis:crit_chance', 0, b['chance'])]
    if t == 'crit_damage': return [mk('apotheosis:crit_damage', 0, b['amount'])]
    if t == 'gem_power': return [mk('pob:gem_power', 0, b['multiplier'], item=eqkey(b.get('item_condition')))]
    if t == 'player_sockets': return [mk('pob:sockets', 0, b['sockets'], item=eqkey(b.get('item_condition')))]
    if t == 'incoming_healing': return [mk('apotheosis:healing_received', 0, b['multiplier'])]
    return []


def tier_of(d):
    bt = d['borderTexture'].split('/')[-1].replace('.png', '')
    if d['isStartingPoint']: return 'class'
    return bt  # lesser / notable / keystone / gateway


def main():
    nodes = {}
    for f in sorted(glob.glob(D + '*.json')):
        d = json.load(open(f))
        sid = d['id'].split(':')[1]
        name = LANG.get('skill.skilltree.%s.name' % sid) or LANG.get('skill.skilltree.%s' % sid)
        eff = []; stats = []; calc = []
        for b in d['bonuses']:
            t, _s = describe(b)
            st_ = struct(b)
            eff.append(t); stats += st_; calc.append(bool(st_))
        nodes[sid] = {
            'id': sid, 'name': name or sid, 'x': round(d['positionX'], 2), 'y': round(d['positionY'], 2),
            'size': d['buttonSize'], 'tier': tier_of(d), 'start': d['isStartingPoint'],
            'icon': d['iconTexture'].split('/')[-1].replace('.png', ''),
            'links': [x.split(':')[1] for x in d['directConnections']],
            'long': [x.split(':')[1] for x in d['longConnections']],
            'oneway': [x.split(':')[1] for x in d['oneWayConnections']],
            'effects': eff, 'stats': stats, 'calc': calc,
            'gateway': 'gateway' in sid,
        }
        if LANG.get('skill.skilltree.%s.description' % sid): nodes[sid]['desc'] = LANG['skill.skilltree.%s.description' % sid]
    # voisinage symétrique pour direct + long
    adj = {k: set() for k in nodes}
    for k, n in nodes.items():
        for o in n['links'] + n['long']:
            if o in nodes: adj[k].add(o); adj[o].add(k)
        for o in n['oneway']:
            if o in nodes: adj[k].add(o)  # one-way: k -> o
    for k, n in nodes.items(): n['adj'] = sorted(adj[k])
    cfg = {}
    t = open('../minecraft/config/skilltree-common.toml').read()
    m = re.search(r'"Levelup costs" = \[([^\]]*)\]', t)
    cfg['costs'] = [int(x) for x in m.group(1).split(',')]
    cfg['maxPoints'] = int(re.search(r'"Maximum skill points" = (\d+)', t).group(1))
    out = {'nodes': list(nodes.values()), 'config': cfg}
    json.dump(out, open('site/data/skilltree.json', 'w'), ensure_ascii=False, separators=(',', ':'))
    print(len(nodes), 'nodes; classes:', [k for k, n in nodes.items() if n['start']])
    import collections
    print(collections.Counter(n['tier'] for n in nodes.values()))
    # contrôle : noeuds sans texte reconnu
    bad = [(k, e) for k, n in nodes.items() for e in n['effects'] if re.fullmatch(r'[a-z_]+', e)]
    print('non formatés:', bad[:20], len(bad))


if __name__ == "__main__":
    main()
