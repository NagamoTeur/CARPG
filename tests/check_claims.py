# -*- coding: utf-8 -*-
"""Tests « wiki ↔ configuration » : les chiffres clés écrits dans le wiki doivent correspondre aux fichiers du serveur.
Usage : python3 tests/check_claims.py   (code de sortie 1 si une affirmation est contredite)"""
import json, re, sys, tomllib, glob, os
ROOT = '/home/nagamo/Documents/Dev/Minecraft/minecraft/config'
W = {os.path.basename(f)[:-3]: open(f, encoding='utf8').read() for f in glob.glob('wiki_src/*.md')}
ALL = '\n'.join(W.values())
fail = []; ok = 0


def check(name, cond, detail=''):
    global ok
    if cond: ok += 1
    else: fail.append(f'{name} {detail}')


# 1. AutoLeveling : 0,008 niveau par bloc = 1 niveau tous les 125 blocs
al = tomllib.load(open(ROOT + '/autoleveling-common.toml', 'rb'))
lpd = al['default_leveling_settings']['levels_per_distance']
check('autoleveling: niveaux par bloc', f'{lpd:g}'.replace('.', ',') in W['monde'] and str(round(1 / lpd)) in W['premiers-pas'], f'(config {lpd}, attendu « {round(1/lpd)} blocs »)')
# 2. niveaux de départ des dimensions
lv = json.load(open('out/leveling.json'))
for dim, label in (('minecraft:the_nether', 'Nether'), ('minecraft:the_end', 'End'), ('aether:the_aether', 'Aether')):
    st = lv[dim]['starting_level']
    check(f'niveau de départ {label}', re.search(label + r' \| \*{0,2}' + str(st) + r'\*{0,2} \|', W['monde']) is not None, f'(config {st})')
# 3. règles de butin Apotheosis
cfg = open(ROOT + '/apotheosis/adventure.cfg', encoding='utf8').read()
conv = re.search(r'Affix Convert Loot Rules" <\s*(.*?)\s*>', cfg, re.S).group(1)
p = re.findall(r'\|([0-9.]+)', conv)
check('butin converti 35 %', any(f'{round(float(x) * 100)} %' in W['equipement'] for x in p if float(x) > 0), f'(config {p})')
# 4. Majrusz : fonctionnalités désactivées
mj = json.load(open(ROOT + '/majruszsdifficulty.json'))
for k in ('blood_moon', 'undead_army', 'bleeding'):
    if not mj[k]['is_enabled']: check(f'majrusz {k} désactivé', 'désactiv' in W['monde'] and not re.search(r'(?<!désactivée)\bpossibilité de lune de sang\*{0,2}\.$', W['monde'], re.M))
ms = mj['features']['mobs_spawn_stronger']
check('majrusz expert +15 % vie', f"+{round(ms['health_bonus']['expert'] * 100)} %" in W['monde'])
check('majrusz maître +30 % vie', f"+{round(ms['health_bonus']['master'] * 100)} %" in W['monde'])
# 5. Anneau des Sept Malédictions (omniconf)
oc = open(ROOT + '/enigmaticlegacy-common.omniconf', encoding='utf8').read()
for key, label in (('CursedRingPainModifier', '200'), ('CursedRingArmorDebuff', '30'), ('CursedRingMonsterDamageDebuff', '50'), ('CursedRingExperienceBonus', '400'), ('CursedRingEnchantingBonus', '10')):
    v = re.search(key + r'=(\d+)', oc).group(1)
    check(f'anneau {key}', v == label and v in W['ring-sept-maledictions'], f'(config {v})')
# 6. Corpse : le cadavre devient un squelette après skeleton_time ticks (20 ticks/s)
cp = tomllib.load(open(ROOT + '/corpse-server.toml', 'rb'))
check('corpse squelette ≈ 1 heure', cp['corpse']['skeleton_time'] == 72000 and '1 heure' in W['survie'])
check('corpse accès à tous', cp['corpse']['access']['only_owner'] is False and "n'importe quel joueur" in W['survie'])
# 7. les 33 entrées de niveaux de boss existent dans bosses.json
bj = json.load(open('site/data/bosses.json'))
ids = {b['id'] for b in bj}
check('boss: entités avec niveau sans fiche', not [k for k, v in lv.items() if v['kind'] == 'entities' and k not in ids and k not in ('cisco_mod:fellkingboss', 'cisco_mod:cisco')], str([k for k, v in lv.items() if v['kind'] == 'entities' and k not in ids])[:200])
# 8. plafonds d'attributs : table du moteur = source
import subprocess
r = subprocess.run([sys.executable, 'build_caps.py'], capture_output=True, text=True); check('plafonds moteur = sources', 'aucun' in r.stdout, r.stdout[-200:])
# 9. restrictions d'origine : aucun build ne les viole
r = subprocess.run(['node', 'sim/check_rules.js'], capture_output=True, text=True); check('builds respectent les restrictions d\'origine', r.returncode == 0, r.stdout[-300:])
print(f'{ok} vérifications réussies, {len(fail)} échec(s)')
for f in fail: print('  ✘', f)
sys.exit(1 if fail else 0)
