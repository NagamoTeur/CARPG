# -*- coding: utf-8 -*-
import sys, glob, json, re, os
import snbt
Q = '../minecraft/config/ftbquests/quests/'
L = json.load(open('out/lang_en.json'))
groups = {g['id']: json.loads(g['title']) if g['title'].startswith('{') else {'text': g['title']} for g in snbt.parse_file(Q + 'chapter_groups.snbt')['chapter_groups']}
def clean(t):
    t = t or ''
    if isinstance(t, str) and t.startswith('{') and '"text"' in t:
        try:
            j = json.loads(t)
            t = j.get('text', '') + ''.join(e.get('text', '') if isinstance(e, dict) else str(e) for e in j.get('extra', []))
        except Exception: pass
    return re.sub(r'&[0-9a-fk-or]', '', t)
def itemname(i):
    if not isinstance(i, str): i = (i or {}).get('id', '?')
    ns, p = (i.split(':', 1) + [''])[:2] if ':' in i else ('minecraft', i)
    return L.get(f'item.{ns}.{p}') or L.get(f'block.{ns}.{p}') or p.replace('_', ' ').title()
# tables de récompense
tables = {}
for f in glob.glob(Q + 'reward_tables/*.snbt'):
    d = snbt.parse_file(f); tables[d['id']] = d
chapters = []
for f in sorted(glob.glob(Q + 'chapters/*.snbt')):
    d = snbt.parse_file(f)
    fn = os.path.basename(f)[:-5]
    title = d.get('title')
    if not title:
        title = re.sub(r'^text', '', fn)
        title = re.sub(r'color[0-9a-f]{6}.*$', '', title) if fn.startswith('text') else fn
        title = title.replace('_', ' ').title()
    qs = []
    for q in d['quests']:
        qid = q['id']
        tasks = []
        for t in q.get('tasks', []):
            tt = {'type': t.get('type'), 'title': clean(t.get('title', ''))}
            if t.get('item'): tt['item'] = t['item'] if isinstance(t['item'], str) else t['item'].get('id'); tt['name'] = itemname(t['item'])
            if t.get('entity'): tt['entity'] = t['entity']; tt['name'] = t['entity'].split(':')[-1].replace('_', ' ').title()
            if t.get('count'): tt['count'] = t['count']
            if t.get('value'): tt['count'] = t['value']
            if t.get('advancement'): tt['adv'] = t['advancement']
            if t.get('dimension'): tt['dim'] = t['dimension']
            if t.get('structure'): tt['structure'] = t['structure']
            tasks.append(tt)
        rews = []
        for r in q.get('rewards', []):
            rr = {'type': r.get('type')}
            if r.get('item'):
                it = r['item'] if isinstance(r['item'], str) else r['item'].get('id')
                rr['item'] = it; rr['name'] = itemname(it)
            if r.get('count'): rr['count'] = r['count']
            if r.get('xp'): rr['xp'] = r['xp']
            if r.get('table_id'):
                rr['table'] = tables.get(r['table_id'], {}).get('title', 'table de butin')
            if r.get('type') == 'command': rr['name'] = clean(r.get('title', 'commande'))
            rews.append(rr)
        qs.append({'id': qid, 'title': clean(q.get('title', '')) or (tasks[0].get('title') if tasks else ''), 'subtitle': clean(q.get('subtitle', '')),
                   'desc': [clean(x) for x in q.get('description', [])], 'deps': q.get('dependencies', []), 'tasks': tasks, 'rewards': rews,
                   'x': q.get('x', 0), 'y': q.get('y', 0), 'icon': (q.get('icon') if isinstance(q.get('icon'), str) else (q.get('icon') or {}).get('id')), 'hidden': bool(q.get('hide')), 'optional': bool(q.get('optional'))})
    chapters.append({'id': d['id'], 'file': fn, 'title': clean(title) if isinstance(title, str) else fn, 'group': groups.get(d.get('group'), {}).get('text', ''),
                     'order': d.get('order_index', 0), 'quests': qs})
chapters.sort(key=lambda c: (c['group'], c['order']))
TR = json.load(open('translations/quests_fr.json'))
for c in chapters:
    c['title_fr'] = TR.get('c:' + c['id'])
    for q in c['quests']:
        q['title_fr'] = TR.get('t:' + q['id']); q['subtitle_fr'] = TR.get('s:' + q['id'])
        d = TR.get('d:' + q['id']); q['desc_fr'] = d.split('\n') if d else None
        for i, t in enumerate(q['tasks']): t['title_fr'] = TR.get(f'k:{q["id"]}:{i}')
json.dump(chapters, open('site/data/quests.json', 'w'), ensure_ascii=False, separators=(',', ':'))
scrolls = sum((r.get('count', 1)) for c in chapters for q in c['quests'] for r in q['rewards'] if r.get('item') == 'skilltree:wisdom_scroll')
print(len(chapters), 'chapitres', sum(len(c['quests']) for c in chapters), 'quêtes;', scrolls, 'parchemins de sagesse')
for c in chapters:
    s = sum((r.get('count', 1)) for q in c['quests'] for r in q['rewards'] if r.get('item') == 'skilltree:wisdom_scroll')
    print(f"  {c['group']:14} {c['title'][:36]:36} {len(c['quests']):3} quêtes {s:3} parchemins")
