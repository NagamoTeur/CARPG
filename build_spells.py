# -*- coding: utf-8 -*-
"""Iron's Spells 'n Spellbooks -> site/data/spells.json
Lit les classes décompilées (formules exactes) + irons_spellbooks-server.toml (réglages du pack)."""
import re, json, glob, os, tomllib

SRC = 'decomp/irons_src/io/redspace/ironsspellbooks/'
LANG = json.load(open('out/lang_en.json'))
SPFR = json.load(open('translations/spells_fr.json'))
CFG = tomllib.load(open('../minecraft/config/irons_spellbooks-server.toml', 'rb'))
SPELLCFG = CFG.get('Spells', {})

CAST = {'INSTANT': 'Instantané', 'LONG': 'Incantation', 'CONTINUOUS': 'Canalisé', 'CHARGE': 'Chargé'}
SCHOOLS_FR = {'fire': 'Feu', 'ice': 'Glace', 'lightning': 'Foudre', 'holy': 'Sacré', 'ender': 'Ender', 'blood': 'Sang',
              'evocation': 'Évocation', 'nature': 'Nature', 'eldritch': 'Occulte'}
RAR_FR = {'COMMON': 'Commun', 'UNCOMMON': 'Peu commun', 'RARE': 'Rare', 'EPIC': 'Épique', 'LEGENDARY': 'Légendaire'}
UI_FR = {'damage': 'Dégâts', 'base_damage': 'Dégâts de base', 'percent_damage': 'Dégâts (%)', 'impact_damage': 'Dégâts d\'impact',
         'aoe_damage': 'Dégâts de zone', 'healing': 'Soin', 'greater_healing': 'Soin max', 'aoe_healing': 'Soin de zone',
         'radius': 'Rayon (blocs)', 'distance': 'Portée (blocs)', 'cast_range': 'Portée d\'incantation', 'max_victims': 'Cibles max',
         'absorption': 'PV temporaires', 'mana_recovery': 'Mana récupéré', 'strength': 'Force', 'slowness_effect': 'Lenteur',
         'freeze_time': 'Durée de gel', 'portal_duration': 'Durée du portail', 'max_hp_on_kill': 'PV max à la mort',
         'reduced_healing': 'Soins réduits (%)', 'additional_poisoned_damage': 'Dégâts supplémentaires si empoisonné (%)',
         'duration': 'Durée', 'summon_count': 'Invocations', 'effect_length': 'Durée de l\'effet',
         'damage_reduction': 'Réduction de dégâts', 'fang_count': 'Crocs', 'hits_dodged': 'Coups esquivés', 'hp': 'PV du bouclier', 'projectile_count': 'Projectiles',
         'rend': 'Intensité de Rend (armure −)', 'ring_count': 'Anneaux', 'shatter_damage': 'Dégâts d\'éclatement'}


def java_to_py(expr, methods, depth=0):
    e = expr.strip().rstrip(';')
    e = re.sub(r'\(\s*(float|int|double|long)\s*\)', '', e)
    e = re.sub(r'(\d+\.?\d*)[FfDdL]\b', r'\1', e)
    e = e.replace('this.getSpellPower(spellLevel, caster)', 'sp').replace('this.getSpellPower(spellLevel, entity)', 'sp')
    e = e.replace('this.getSpellPower(spellLevel, null)', 'sp')
    e = re.sub(r'this\.getSpellPower\([^()]*\)', 'sp', e)
    e = re.sub(r'this\.getEntityPowerMultiplier\([^()]*\)', 'epm', e)
    e = re.sub(r'Utils\.timeFromTicks\((.*),\s*\d+\)', r'((\1)/20)', e)
    e = re.sub(r'Utils\.stringTruncation\((.*?),\s*\d+\)', r'(\1)', e)
    e = e.replace('Math.min', 'min').replace('Math.max', 'max').replace('Math.floor', 'floor').replace('Math.ceil', 'ceil')
    e = e.replace('Math.round', 'round').replace('Math.sqrt', 'sqrt').replace('Math.pow', 'pow').replace('Math.abs', 'abs')
    e = e.replace('this.baseSpellPower', 'BASE').replace('this.spellPowerPerLevel', 'PERLVL')
    e = re.sub(r'\bthis\.(\w+)\(spellLevel(?:,\s*\w+)?\)', lambda m: '(' + (java_to_py(methods[m.group(1)], methods, depth + 1) if m.group(1) in methods and depth < 4 else '0') + ')', e)
    e = re.sub(r'\bthis\.(\w+)\((?:spellLevel(?:,\s*)?)?(?:caster|entity)?\)', lambda m: '(' + (java_to_py(methods[m.group(1)], methods, depth + 1) if m.group(1) in methods and depth < 4 else '0') + ')', e)
    e = e.replace('spellLevel', 'lvl')
    return e


def parse(path):
    s = open(path, encoding='utf8').read()
    if 'extends AbstractSpell' not in s: return None
    m = re.search(r'new ResourceLocation\("irons_spellbooks",\s*"(\w+)"\)', s)
    if not m: return None
    sid = m.group(1)
    def g(pat, default=None, cast=float):
        mm = re.search(pat, s)
        return cast(mm.group(1)) if mm else default
    school = re.search(r'setSchoolResource\(SchoolRegistry\.(\w+)_RESOURCE\)', s)
    rarity = re.search(r'setMinRarity\(SpellRarity\.(\w+)\)', s)
    ctype = re.search(r'getCastType\(\)\s*\{\s*return CastType\.(\w+)', s)
    sp = {
        'id': sid, 'school': school.group(1).lower() if school else None,
        'rarityMin': rarity.group(1) if rarity else 'COMMON',
        'maxLevel': g(r'setMaxLevel\((\d+)\)', 1, int),
        'cooldown': g(r'setCooldownSeconds\(([\d.]+)\)', 0.0),
        'baseMana': g(r'this\.baseManaCost\s*=\s*(\d+)', 0, int),
        'manaPerLevel': g(r'this\.manaCostPerLevel\s*=\s*(\d+)', 0, int),
        'baseSpellPower': g(r'this\.baseSpellPower\s*=\s*(\d+)', 1, int),
        'spellPowerPerLevel': g(r'this\.spellPowerPerLevel\s*=\s*(\d+)', 0, int),
        'castTime': g(r'this\.castTime\s*=\s*(\d+)', 0, int),
        'castType': ctype.group(1) if ctype else 'INSTANT',
        'file': os.path.relpath(path, SRC),
    }
    # méthodes à retour simple
    methods = {}
    for mm in re.finditer(r'(?:public|private|protected)\s+(?:static\s+)?(?:float|int|double)\s+(\w+)\((?:int spellLevel(?:,\s*)?)?(?:LivingEntity \w+)?\)\s*\{\s*return\s+(.*?);\s*\}', s, re.S):
        methods[mm.group(1)] = mm.group(2)
    # infos uniques
    info = []
    gu = re.search(r'getUniqueInfo\(.*?\)\s*\{(.*?)\n   \}', s, re.S)
    if gu:
        for mm in re.finditer(r'Component\.m_237110_\("ui\.irons_spellbooks\.(\w+)",\s*new Object\[\]\{(.*?)\}\)', gu.group(1), re.S):
            key, arg = mm.group(1), mm.group(2)
            try:
                py = java_to_py(arg, methods)
            except Exception:
                py = None
            info.append({'unit': 's' if 'timeFromTicks' in arg else '', 'key': key, 'label': UI_FR.get(key, key.replace('_', ' ')), 'expr': py, 'raw': arg.strip()[:200]})
        # lignes en Component.m_237115_ / autre : ignorées
    sp['info'] = info
    return sp


def main():
    spells = []
    for f in sorted(glob.glob(SRC + 'spells/**/*.java', recursive=True)) + sorted(glob.glob(SRC + 'api/spells/*.java')):
        try:
            r = parse(f)
        except Exception as ex:
            print('ERR', f, ex); continue
        if r and r['school']: spells.append(r)
    print(len(spells), 'sorts')
    for sp in spells:
        c = SPELLCFG.get('irons_spellbooks:' + sp['id'], {})
        sp['name'] = LANG.get('spell.irons_spellbooks.' + sp['id']) or sp['id'].replace('_', ' ').title()
        sp['fr'] = SPFR.get(sp['id'])
        sp['guide'] = LANG.get('spell.irons_spellbooks.%s.guide' % sp['id'], '')
        sp['cfg'] = {'enabled': c.get('Enabled', True), 'maxLevel': c.get('MaxLevel', sp['maxLevel']),
                     'minRarity': c.get('MinRarity', sp['rarityMin']), 'manaMult': c.get('ManaCostMultiplier', 1.0),
                     'powerMult': c.get('SpellPowerMultiplier', 1.0), 'cooldown': c.get('CooldownInSeconds', sp['cooldown']),
                     'school': (c.get('School') or 'irons_spellbooks:' + sp['school']).split(':')[-1], 'craft': c.get('AllowCrafting', True)}
    out = {'spells': spells, 'schools': SCHOOLS_FR, 'castTypes': CAST, 'rarities': RAR_FR,
           'misc': {k: v for k, v in CFG.get('Misc', {}).items()}}
    json.dump(out, open('site/data/spells.json', 'w'), ensure_ascii=False, separators=(',', ':'))
    ok = sum(1 for s in spells if s['info'] and all(i['expr'] for i in s['info']))
    print('avec formules:', ok, 'sans info:', sum(1 for s in spells if not s['info']))
    import collections
    print(collections.Counter(s['cfg']['school'] for s in spells))
    print([s['id'] for s in spells if not s['cfg']['enabled']])


if __name__ == '__main__':
    main()
