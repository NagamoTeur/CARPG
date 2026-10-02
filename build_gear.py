# -*- coding: utf-8 -*-
"""Gemmes + affixes (Apotheosis, Apotheotic Additions, Apothic Curios, Iron's Spellbooks, Passive Skill Tree)
-> site/data/gems.json, affixes.json"""
import json, glob, os, re
from fr import *

LANG = json.load(open('out/lang_en.json'))
IDX = json.load(open('out/mods_index.json'))
MODS = {o['modid'] for o in IDX} | {'minecraft', 'forge', 'apotheosis'}
ROOT = 'merged/data/'

EFFECT_FR = {'resistance': 'Résistance', 'regeneration': 'Régénération', 'speed': 'Vitesse', 'strength': 'Force',
             'weakness': 'Faiblesse', 'slowness': 'Lenteur', 'poison': 'Poison', 'wither': 'Wither', 'absorption': 'Absorption',
             'fire_resistance': 'Résistance au feu', 'haste': 'Hâte', 'jump_boost': 'Saut', 'night_vision': 'Vision nocturne',
             'blindness': 'Cécité', 'levitation': 'Lévitation', 'glowing': 'Lueur', 'hunger': 'Faim', 'nausea': 'Nausée',
             'mining_fatigue': 'Fatigue', 'invisibility': 'Invisibilité', 'luck': 'Chance', 'slow_falling': 'Chute lente',
             'water_breathing': 'Respiration aquatique', 'health_boost': 'Bonus de vie', 'saturation': 'Saturation',
             'instant_health': 'Soin instantané', 'instant_damage': 'Dégâts instantanés', 'bad_luck': 'Malchance',
             'dolphins_grace': 'Grâce du dauphin', 'conduit_power': 'Puissance de l\'océan', 'darkness': 'Ténèbres',
             'levitation': 'Lévitation', 'fire': 'Feu'}
TARGET_FR = {'ATTACK_SELF': 'en attaquant (sur vous)', 'ATTACK_TARGET': 'sur la cible touchée', 'HURT_SELF': 'quand vous êtes touché (sur vous)',
             'HURT_ATTACKER': 'sur l\'attaquant', 'BLOCK_SELF': 'en bloquant (sur vous)', 'BLOCK_ATTACKER': 'en bloquant (sur l\'attaquant)',
             'ARROW_SELF': 'au tir (sur vous)', 'ARROW_TARGET': 'sur la cible de la flèche', 'BREAK_SELF': 'en minant (sur vous)',
             'SHIELD_BLOCK': 'en bloquant', 'ATTACK_ENTITY': 'sur la cible touchée', 'BREAK_BLOCK': 'en cassant un bloc'}
DMG_FR = {'physical': 'physiques', 'fire': 'de feu', 'cold': 'de froid', 'magic': 'magiques', 'explosion': 'd\'explosion', 'fall': 'de chute',
          'projectile': 'de projectiles', 'lightning': 'de foudre', 'drown': 'de noyade', 'wither': 'du Wither', 'poison': 'de poison',
          'void': 'du Vide', 'bypasses_armor': 'qui ignorent l\'armure', 'all': 'tous types'}


def effname(e): return EFFECT_FR.get(e.split(':')[-1], e.split(':')[-1].replace('_', ' '))


def conditions_ok(d):
    for c in d.get('conditions', []) or []:
        if c.get('type') == 'forge:mod_loaded' and c.get('modid') not in MODS: return False
    return True


def val_of(v):
    """valeur d'un 'values' par rareté: nombre | {min,steps,step} | dict complexe"""
    if isinstance(v, dict) and 'min' in v:
        return {'min': v['min'], 'max': round(v['min'] + v.get('steps', 0) * v.get('step', 0), 4)}
    return v


def mid(v):
    return (v['min'] + v['max']) / 2 if isinstance(v, dict) and 'max' in v else v


def rng(v, pct=False, flat=False):
    if isinstance(v, dict) and 'max' in v:
        a, b = v['min'], v['max']
        if pct: return f"{fnum(a*100,1)}–{fnum(b*100,1)} %"
        return f"{fnum(a)}–{fnum(b)}"
    if pct: return f"{fnum(v*100,1)} %"
    return fnum(v)


def sec(ticks): return fnum(ticks / 20, 1) + ' s'


def attr_text(attr, op, v):
    """-> 'Texte' avec plage si besoin"""
    if isinstance(op, str): op = OPS.get(op, 0)
    name = ATTR_FR.get(attr) or attr.split(':')[-1].replace('_', ' ')
    pct = op in (1, 2) or attr in PCT_ADD
    if isinstance(v, dict) and 'max' in v:
        s = '+' if v['min'] >= 0 else '−'
        a, b = abs(v['min']), abs(v['max'])
        t = (f"{s}{fnum(a*100,1)}–{fnum(b*100,1)} %" if pct else f"{s}{fnum(a)}–{fnum(b)}")
    else:
        s = '+' if v >= 0 else '−'
        t = f"{s}{fnum(abs(v)*100,1)} %" if pct else f"{s}{fnum(abs(v))}"
    if op == 2: t += ' (mult.)'
    return f"{t} {name}"


def describe_special(t, values):
    """t = type sans namespace ; values = dict rarete->valeur. Retourne dict rarete->texte FR"""
    out = {}
    for r, v in values.items():
        if t == 'durability': s = f"+{fnum(v*100,0)} % de durabilité"
        elif t == 'damage_reduction' and not isinstance(v, dict): s = f"{fnum(v*100,1)} % de réduction"
        elif t == 'enchantment': s = f"+{v} niveau(x) d'enchantement"
        elif t == 'mageslayer': s = f"+{fnum(mid(v)*100,0)} % de dégâts contre les lanceurs de sorts"
        elif t == 'leech_block': s = f"En bloquant : soigne {fnum(v['heal_factor']*100,0)} % des dégâts bloqués (recharge {sec(v['cooldown'])})"
        elif t == 'bloody_arrow': s = f"Flèche sanglante : coûte {fnum(v['health_cost']*100,0)} % PV, ×{fnum(v['damage_mult'])} dégâts (recharge {sec(v['cooldown'])})"
        elif t == 'twilight_treasure_goblin': s = f"{fnum(v['chance']*100,2)} % d'invoquer un gobelin au trésor (recharge {sec(v['cooldown'])})"
        elif t == 'twilight_ore_magnet': s = f"Aimante les minerais dans un rayon lié au niveau ({v})"
        elif t == 'twilight_fortification': s = f"{fnum(v['chance']*100,1)} % d'obtenir une fortification (recharge {sec(v['cooldown'])})"
        elif t == 'drop_transform': s = f"{fnum(v*100,0)} % de chance de transformer le butin"
        elif t == 'all_stats': s = f"+{fnum(v*100,1)} % à toutes les stats"
        elif t == 'cleaving': s = f"Frappe jusqu'à {fnum(v['chance']*100,0) if isinstance(v,dict) and 'chance' in v else ''}"
        else: s = f"{t} : {json.dumps(v, ensure_ascii=False)}"
        out[r] = s
    return out


def mob_effect_text(b):
    tgt = TARGET_FR.get(b.get('target'), (b.get('target') or '').lower())
    out = {}
    for r, v in b['values'].items():
        amp = v.get('amplifier', 0)
        if isinstance(amp, dict): amp = (amp['min'] + amp['min'] + amp['steps'] * amp['step']) / 2
        dur = v.get('duration', 0)
        if isinstance(dur, dict): dur = dur['min'] + dur['steps'] * dur['step']
        out[r] = f"{effname(b['mob_effect'])} {int(amp)+1} pendant {sec(dur)} {tgt} (recharge {sec(v.get('cooldown',0))})"
    return out


def gem_bonus(b):
    t = b['type'].split(':')[-1]
    gc = b.get('gem_class') or {}
    res = {'class': gc.get('key', ''), 'types': gc.get('types', [])}
    if t == 'attribute':
        vals = {r: val_of(v) for r, v in b['values'].items()}
        res.update(kind='attr', attr=b['attribute'], op=OPS.get(b['operation'], 0), values=vals,
                   text={r: attr_text(b['attribute'], b['operation'], v) for r, v in vals.items()})
    elif t == 'multi_attribute':
        rar = set()
        for m in b['modifiers']: rar |= set(m['values'])
        res.update(kind='multi', mods=[{'attr': m['attribute'], 'op': OPS.get(m['operation'], 0), 'values': {r: val_of(x) for r, x in m['values'].items()}} for m in b['modifiers']],
                   text={r: ' ; '.join(attr_text(m['attribute'], m['operation'], val_of(m['values'][r])) for m in b['modifiers'] if r in m['values']) for r in rar})
    elif t == 'mob_effect':
        res.update(kind='text', text=mob_effect_text(b))
    elif t == 'damage_reduction':
        dt = DMG_FR.get((b.get('damage_type') or '').lower(), (b.get('damage_type') or '').lower())
        res.update(kind='text', text={r: f"−{fnum(v*100,1)} % de dégâts {dt}" for r, v in b['values'].items()})
    elif t == 'all_stats':
        res.update(kind='text', text=describe_special('all_stats', b['values']))
    elif t == 'enchantment':
        en = b['enchantment'].split(':')[-1].replace('_', ' ')
        res.update(kind='text', text={r: f"+{v} {en}" for r, v in b['values'].items()})
    elif t == 'skilltree:gem_bonus' or t == 'gem_bonus':
        res = None
    else:
        res.update(kind='text', text=describe_special(t, b['values']) if 'values' in b else {'_': t})
    return res


def skilltree_bonus_text(b):
    """gem skilltree: bonus.item_bonus.skill_bonus ..."""
    from build_skilltree import describe  # noqa
    ib = b['bonus']['item_bonus']
    if ib['type'].endswith('durability'):
        return f"+{fnum(ib['amount'])} de durabilité", []
    sb = ib['skill_bonus']
    txt, stats = describe(sb)
    return txt, stats


def gem_name(gid):
    ns, p = gid.split(':', 1)
    return LANG.get(f'item.apotheosis.gem.{gid}') or LANG.get(f'gem.{gid}') or p.split('/')[-1].replace('_', ' ').title()



def _rg(v, pct=False):
    if isinstance(v, dict) and 'min' in v:
        lo, hi = v['min'], v['min'] + v.get('steps', 0) * v.get('step', 0)
    else: lo = hi = v
    f = (lambda x: fnum(x * 100, 1)) if pct else (lambda x: fnum(x))
    return f(lo) + ('–' + f(hi) if hi != lo else '') + (' %' if pct else '')


def special_fr(t, v, rar):
    x = v.get(rar) if isinstance(v, dict) else None
    if t == 'cleaving' and isinstance(x, dict): return f"{_rg(x['chance'], True)} de chance de toucher aussi jusqu'à {_rg(x['targets'])} ennemis proches"
    if t == 'executing': return f"Exécute les ennemis sous {_rg(x, True)} de leurs PV max"
    if t == 'spectral': return f"{_rg(x, True)} de chance de tirer une seconde flèche"
    if t == 'psychic': return f"Bloquer un projectile renvoie {_rg(x, True)} des dégâts au tireur"
    if t == 'festive': return f"{_rg(x, True)} de chance que l'ennemi tué devienne une piñata de butin"
    if t == 'thunderstruck': return f"Vos attaques infligent {_rg(x)} dégâts aux ennemis proches"
    if t == 'enlightened': return "Peut poser une torche (coûte de la durabilité)"
    if t == 'omnetic': return f"Efficacité « {x.get('name', '?')} » contre tous les blocs" if isinstance(x, dict) else "Efficace contre tous les blocs"
    if t == 'radial':
        last = x[-1] if isinstance(x, list) and x else None
        return f"Casse une zone jusqu'à {last['x']}×{last['y']}" if last else "Casse une zone"
    if t == 'magical': return "Vos flèches infligent des dégâts magiques"
    if t == 'retreating': return "Vous bondissez en arrière en bloquant au corps à corps"
    if t == 'catalyzing': return "Bloquer une explosion vous donne une grande force"
    if t == 'durable': return "L'objet ignore une partie des dégâts de durabilité"
    if t == 'telepathic': return "Le butin des monstres / blocs est téléporté directement vers vous"
    return None


def main():
    gems = []
    for f in sorted(glob.glob(ROOT + '*/gems/**/*.json', recursive=True)):
        rel = f[len(ROOT):]
        ns, rest = rel.split('/gems/', 1)
        if ns in ('skilltree',): continue
        d = json.load(open(f))
        if not conditions_ok(d): continue
        gid = f'{ns}:{rest[:-5]}'
        bonuses = []
        for b in d.get('bonuses', []):
            r = gem_bonus(b)
            if r: bonuses.append(r)
        if not bonuses: continue
        gems.append({'id': gid, 'name': gem_name(gid), 'ns': ns, 'weight': d.get('weight', 0), 'min': d.get('min_rarity'), 'max': d.get('max_rarity'),
                     'unique': d.get('unique', False), 'dims': d.get('dimensions', []), 'bonuses': bonuses})
    # gemmes Passive Skill Tree (citrine, jade, ...)
    st = []
    for f in sorted(glob.glob(ROOT + 'skilltree/gems/*.json')):
        d = json.load(open(f))
        gid = 'skilltree:' + os.path.basename(f)[:-5]
        bons = []
        for b in d['bonuses']:
            gc = b['gem_class']; txt, stats = skilltree_bonus_text(b)
            bons.append({'class': gc['key'], 'types': gc['types'], 'text': txt, 'stats': stats})
        fn = os.path.basename(f)[:-5]
        nm = LANG.get('item.apotheosis.gem.' + gid) or fn
        base, _, tier = fn.rpartition('_')
        st.append({'id': gid, 'name': nm, 'base': base, 'tier': int(tier), 'rarity': RARITY_ORDER[int(tier)], 'bonuses': bons,
                   'weight': d.get('weight'), 'min': d.get('min_rarity'), 'max': d.get('max_rarity')})
    json.dump({'apotheosis': gems, 'skilltree': st}, open('site/data/gems.json', 'w'), ensure_ascii=False, separators=(',', ':'))
    print('gems', len(gems), 'skilltree gems', len(st))
    print([g['id'] for g in gems][:10])

    # ---- affixes
    aff = []
    for f in sorted(glob.glob(ROOT + '*/affixes/**/*.json', recursive=True)):
        rel = f[len(ROOT):]
        ns, rest = rel.split('/affixes/', 1)
        if ns == 'skilltree' and False: continue
        d = json.load(open(f))
        if not conditions_ok(d): continue
        aid = f'{ns}:{rest[:-5]}'
        t = d.get('type', '').split(':')[-1]
        if t in ('socket',): continue
        name = LANG.get(f'affix.{aid}') or rest[:-5].split('/')[-1].replace('_', ' ').title()
        suffix = LANG.get(f'affix.{aid}.suffix')
        desc_en = LANG.get(f'affix.{aid}.desc')
        v = d.get('values', {})
        e = {'id': aid, 'name': name, 'suffix': suffix, 'kind': t, 'types': d.get('types', []), 'ns': ns,
             'cat': rest.split('/')[0]}
        if t == 'attribute':
            e['attr'] = d['attribute']; e['op'] = OPS.get(d['operation'], 0)
            e['values'] = {r: val_of(x) for r, x in v.items()}
            e['text'] = {r: attr_text(d['attribute'], d['operation'], val_of(x)) for r, x in v.items()}
        elif t == 'mob_effect':
            e['text'] = mob_effect_text({'mob_effect': d['mob_effect'], 'target': d['target'], 'values': {r: dict(x, duration=val_of(x.get('duration', 0)) if isinstance(x.get('duration'), dict) else x.get('duration', 0), amplifier=x.get('amplifier', 0)) for r, x in v.items()}}) if False else None
            tx = {}
            for r, x in v.items():
                amp = x.get('amplifier', 0)
                if isinstance(amp, dict): amp = amp['min'] + amp['steps'] * amp['step']
                dur = x.get('duration', 0)
                if isinstance(dur, dict): dur = dur['min'] + dur['steps'] * dur['step']
                tx[r] = f"{effname(d['mob_effect'])} {int(amp)+1} pendant {sec(dur)} {TARGET_FR.get(d.get('target'), (d.get('target') or '').lower())} (recharge {sec(x.get('cooldown',0))})"
            e['text'] = tx
        elif t == 'damage_reduction':
            dt = DMG_FR.get((d.get('damage_type') or '').lower(), (d.get('damage_type') or '').lower())
            e['text'] = {r: (f"−{rng(val_of(x), True)} de dégâts {dt}" if not isinstance(x, dict) or 'min' in x else json.dumps(x)) for r, x in v.items()}
        else:
            rs = list(v.keys()) if v else [r for r in RARITY_ORDER if RARITY_ORDER.index(r) >= RARITY_ORDER.index(d.get('min_rarity', 'rare'))]
            e['text'] = {r: (special_fr(t, v, r) or desc_en or t) for r in rs}
            e['desc_en'] = desc_en
            v = {r: 1 for r in rs}
        e['rarities'] = sorted(v.keys(), key=lambda r: RARITY_ORDER.index(r) if r in RARITY_ORDER else 9)
        aff.append(e)
    for e in aff:
        if e['kind'] == 'telepathic' and not e['types']: e['types'] = ['sword', 'heavy_weapon', 'trident', 'bow', 'crossbow', 'pickaxe', 'shovel']; e['cat'] = 'sword'
    json.dump(aff, open('site/data/affixes.json', 'w'), ensure_ascii=False, separators=(',', ':'))
    import collections
    print('affixes', len(aff), collections.Counter(a['kind'] for a in aff))
    print(collections.Counter(a['cat'] for a in aff))


if __name__ == '__main__':
    main()
