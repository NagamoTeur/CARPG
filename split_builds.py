# -*- coding: utf-8 -*-
"""site/data/builds.js (source complète, 1,7 Mo) -> site/data/builds_index.js (liste légère) + site/data/builds/<archétype>.json (états, chargés à la demande).
Le planificateur ne charge plus que l'index ; l'état d'un build n'est téléchargé qu'au moment de le charger."""
import json, os, shutil
B = json.loads(open('site/data/builds.js', encoding='utf8').read().split('=', 1)[1].rstrip().rstrip(';'))
shutil.rmtree('site/data/builds', ignore_errors=True); os.makedirs('site/data/builds')
idx = []; by = {}
for b in B:
    a = b.get('arch') or b['id']
    by.setdefault(a, {})[b['level']] = b['state']
    idx.append({k: v for k, v in b.items() if k != 'state'})
for a, d in by.items(): json.dump(d, open(f'site/data/builds/{a}.json', 'w'), ensure_ascii=False, separators=(',', ':'))
open('site/data/builds_index.js', 'w', encoding='utf8').write('window.BUILDS_IDX=' + json.dumps(idx, ensure_ascii=False, separators=(',', ':')) + ';')
print(len(idx), 'builds ; index', os.path.getsize('site/data/builds_index.js') // 1024, 'Ko ;', len(by), 'fichiers d\'état')
