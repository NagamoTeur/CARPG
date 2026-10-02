# -*- coding: utf-8 -*-
"""Génère le wiki (pages HTML statiques) à partir de wiki_src/*.md + des données du pack.
Usage : python3 build_wiki.py"""
import os, re, json, glob, html, shutil
import markdown
from fr import *

SRC = 'wiki_src'
OUT = 'site/wiki'
D = {n: json.load(open(f'site/data/{n}.json')) for n in ['skilltree', 'gems', 'affixes', 'origins', 'spells', 'attrs', 'quests', 'bosses', 'items']}
BUILDS = json.load(open('out/builds_report.json')) if os.path.exists('out/builds_report.json') else []
MODS = json.load(open('out/mods_classified.json'))
LANG = json.load(open('out/lang_en.json'))
LABFR = {'damage reduction': 'Réduction de dégâts', 'fang count': 'Crocs', 'hits dodged': 'Coups esquivés', 'hp': 'PV du bouclier', 'projectile count': 'Projectiles', 'rend': 'Intensité de Rend (armure −)', 'ring count': 'Anneaux', 'shatter damage': 'Dégâts d\'éclatement'}
from mods_fr import FR as FRM
CATS = json.loads(open('site/data/build_cats.js', encoding='utf8').read().split('=', 1)[1].rstrip().rstrip(';'))['cats']
BUILD_CAT = {b: c for c in CATS for b in c['builds']}


def TITLE_OF(b):
    m = re.search(r'^title:\s*(.+)$', open(f'wiki_src/builds/{b}.md', encoding='utf8').read(), re.M)
    return re.sub(r'\s*\(.*\)\s*$', '', m.group(1).strip()) if m else b


NAV = [
    ('Démarrer', [('index', 'Accueil'), ('premiers-pas', 'Premiers pas'), ('monde', 'Le monde & le danger'), ('campagne', 'La campagne'), ('survie', 'Mort, sauvegarde & sécurité')]),
    ('Personnage', [('origines', 'Origines'), ('classes', 'Classes'), ('benedictions', 'Bénédictions divines'), ('talents', 'Arbre de talents'), ('combat', 'Combat & statistiques')]),
    ('Équipement', [('equipement', 'Rareté, affixes & sockets'), ('gemmes', 'Gemmes'), ('affixes', 'Catalogue des affixes'), ('atelier', 'Ateliers & enchantement'), ('enchants', 'Tous les enchantements'), ('objets-campagne', 'Équipement de campagne'), ('accessoires', 'Accessoires & reliques'), ('ring-sept-maledictions', 'Anneau des Sept Malédictions')]),
    ('Magie', [('magie', 'Comprendre la magie'), ('sorts', 'Catalogue des sorts (Iron\'s)'), ('ars', 'Glyphes d\'Ars Nouveau')]),
    ('Boss', [('boss', 'Guide des boss')]),
    ('Builds', [('builds/index', 'Tous les builds'), ('builds/niveaux', 'Débutant → Optimisé'), ('builds/adapter', 'Adapter un build à son loot')]),
] + [(f"{c['icon']} {c['title']}", [(f"builds/{b}", TITLE_OF(b)) for b in c['builds']]) for c in CATS] + [
    ('Vie quotidienne', [('vie', 'Cuisine, ferme, colonie & stockage')]),
    ('Référence', [('outil', 'Utiliser le planificateur'), ('quetes', 'Le livre de quêtes'), ('mods', 'Liste des mods'), ('glossaire', 'Glossaire'), ('faq', 'FAQ')]),
]
TITLES = {p: t for _, items in NAV for p, t in items}

PAGES_SEARCH = []


def esc(s): return html.escape(str(s), quote=False)


def md(text):
    return markdown.markdown(text, extensions=['tables', 'fenced_code', 'toc', 'admonition', 'attr_list', 'md_in_html'], output_format='html5')


def layout(slug, title, body, desc=''):
    depth = slug.count('/')
    root = '../' * (depth + 1)
    nav = []
    for sec, items in NAV:
        cur = any(p == slug for p, _ in items)
        nav.append(f'<details class="navsec" {"open" if cur else ""}><summary>{esc(sec)}</summary>' + ''.join(
            f'<a href="{root}wiki/{p}.html" class="{"on" if p == slug else ""}">{esc(t)}</a>' for p, t in items) + '</details>')
    txt = re.sub(r'<[^>]+>', ' ', body)
    PAGES_SEARCH.append({'u': f'wiki/{slug}.html', 't': title, 'x': re.sub(r'\s+', ' ', txt)[:2500]})
    return f"""<!doctype html>
<html lang="fr"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>{esc(title)} — Wiki CARPG</title>
<meta name="description" content="{esc(desc or title)}">
<link rel="stylesheet" href="{root}css/app.css"><link rel="stylesheet" href="{root}css/wiki.css">
</head><body>
<header class="top"><a class="brand" href="{root}index.html">⚔ CARPG</a>
<button id="navbtn" class="sm" aria-label="Menu">☰ Menu</button>
<input id="q" type="search" placeholder="Rechercher dans le wiki…" autocomplete="off"><div id="results" class="hidden"></div>
<span class="spacer"></span><a href="{root}pob/index.html"><button class="pri">Ouvrir le planificateur de build</button></a></header>
<div class="wikiwrap"><nav id="wnav">{''.join(nav)}</nav>
<main class="wiki"><h1>{esc(title)}</h1>{body}
<footer class="mut small">Wiki généré à partir des fichiers du modpack « Cisco's Adventure RPG Ultimate » (V8E). Les chiffres viennent des fichiers de config et du code des mods ; en cas de doute, ce qui s'affiche en jeu fait foi.</footer></main></div>
<script>window.ROOT="{root}";</script><script src="{root}wiki/search-index.js"></script><script src="{root}wiki/wiki.js"></script>
</body></html>"""


def write(slug, title, body, desc=''):
    p = os.path.join(OUT, slug + '.html')
    os.makedirs(os.path.dirname(p), exist_ok=True)
    open(p, 'w', encoding='utf8').write(layout(slug, title, body, desc))


def tfr(t):
    if t in TYPE_FR: return TYPE_FR[t]
    if t.startswith('curios:'): return 'Accessoire (' + t.split(':')[1].replace('_', ' ') + ')'
    return t.replace('_', ' ')


def rarity_chip(r): return f'<span class="rar-{r}">{RARITY_FR[r]}</span>'


# ------------------------------------------------------------------ pages à partir du Markdown
def load_md():
    pages = {}
    for f in sorted(glob.glob(SRC + '/**/*.md', recursive=True)):
        slug = os.path.relpath(f, SRC)[:-3]
        raw = open(f, encoding='utf8').read()
        meta = {}
        m = re.match(r'---\n(.*?)\n---\n', raw, re.S)
        if m:
            for line in m.group(1).split('\n'):
                if ':' in line:
                    k, v = line.split(':', 1); meta[k.strip()] = v.strip()
            raw = raw[m.end():]
        pages[slug] = (meta, raw)
    return pages


# ------------------------------------------------------------------ pages dynamiques
def stat_text(t): return t


def page_gemmes():
    out = ['<p>Une <b>gemme</b> se glisse dans un <b>socket</b> d\'un objet (arme, armure, accessoire…) et donne un bonus <b>qui dépend du type d\'objet</b> : la même gemme peut donner des dégâts critiques dans une arme et de la vie dans un plastron. '
           'La <b>qualité</b> de la gemme (Fissurée → Parfaite) change la valeur : ce tableau montre la valeur à chaque rareté.</p>',
           '<div class="note">Qualités : Cracked = <span class="rar-common">Commun</span>, Chipped = <span class="rar-uncommon">Peu commun</span>, Flawed = <span class="rar-rare">Rare</span>, normale = <span class="rar-epic">Épique</span>, Flawless = <span class="rar-mythic">Mythique</span>, Perfect = <span class="rar-ancient">Ancien</span>.</div>',
           '<input class="filter" data-target="gemlist" placeholder="Filtrer les gemmes (nom, effet, type d\'objet)…">', '<div id="gemlist">']
    CATS = [('apotheosis', 'Gemmes de base (Apotheosis)'), ('apotheotic_additions', 'Joyaux (Apotheotic Additions)'), ('irons_spellbooks', 'Gemmes de magie (Iron\'s Spells)')]
    for ns, title in CATS:
        gl = sorted([g for g in D['gems']['apotheosis'] if g['ns'] == ns], key=lambda g: g['name'])
        if not gl: continue
        out.append(f'<h2>{title}</h2>')
        for g in gl:
            out.append(f'<div class="card gem" data-q="{esc((g["name"] + " " + json.dumps([b.get("text") for b in g["bonuses"]], ensure_ascii=False)).lower())}"><h3>{esc(g["name"])}' + (' <span class="tag">unique par objet</span>' if g['unique'] else '') + '</h3>')
            if g.get('min') or g.get('max'): out.append(f'<div class="mut small">Rareté : {RARITY_FR.get(g.get("min") or "common")} → {RARITY_FR.get(g.get("max") or "ancient")}</div>')
            rows = []
            for b in g['bonuses']:
                types = ', '.join(tfr(t) for t in b['types'][:8]) + (' …' if len(b['types']) > 8 else '')
                txt = b.get('text') or {}
                rs = [r for r in RARITY_ORDER if r in txt]
                if not rs: continue
                lo, hi = rs[0], rs[-1]
                rows.append(f'<tr><td>{esc(types)}</td><td>{rarity_chip(lo)} : {esc(txt[lo])}<br>{rarity_chip(hi)} : {esc(txt[hi])}</td></tr>')
            out.append('<table class="t"><tr><th>Dans…</th><th>Effet (min → max)</th></tr>' + ''.join(rows) + '</table></div>')
    out.append('</div>')
    out.append('<h2>Gemmes de l\'arbre de talents (Passive Skill Tree)</h2><p>Citrine, Jade, Rubis, Saphir, Iriscite, Vacucite : elles se socketent comme les autres mais viennent de l\'arbre de talents (chance de chute de base : 5 %).</p>')
    base = {}
    for g in D['gems']['skilltree']: base.setdefault(g['base'], []).append(g)
    for k, lst in base.items():
        lst.sort(key=lambda g: g['tier'])
        top = lst[-1]
        out.append(f'<div class="card gem"><h3>{esc(top["name"])}</h3><table class="t"><tr><th>Dans…</th><th>Effet (palier {RARITY_FR[top["rarity"]]})</th></tr>' + ''.join(
            f'<tr><td>{esc(", ".join(tfr(t) for t in b["types"]))}</td><td>{esc(b["text"])}</td></tr>' for b in top['bonuses']) + '</table></div>')
    return '\n'.join(out)


def page_affixes():
    out = ['<p>Les <b>affixes</b> sont les propriétés aléatoires d\'un objet « magique ». Les valeurs ci-dessous sont des <b>plages</b> : chaque objet tire une valeur au hasard dans la plage. Les raretés montrent la plage maximale (Mythique par défaut).</p>',
           '<input class="filter" data-target="afflist" placeholder="Filtrer (nom, effet, type d\'objet)…">', '<div id="afflist">']
    CAT_FR = {'armor': 'Armures', 'sword': 'Épées', 'heavy_weapon': 'Armes lourdes', 'ranged': 'Arcs & arbalètes', 'shield': 'Boucliers', 'breaker': 'Outils', 'spellbook': 'Livres de sorts', 'wand': 'Baguettes',
              'curios_jewelry': 'Bijoux (anneaux, colliers…)', 'curios_armor': 'Accessoires d\'armure', 'curios_aether': 'Accessoires Aether', 'jewelry': 'Bijoux', 'apothcurios': 'Accessoires (Curios)'}
    by = {}
    for a in D['affixes']: by.setdefault(a['cat'], []).append(a)
    for cat in sorted(by, key=lambda c: CAT_FR.get(c, c)):
        lst = sorted(by[cat], key=lambda a: a['name'])
        out.append(f'<h2>{esc(CAT_FR.get(cat, cat))}</h2><table class="t"><tr><th>Nom</th><th>Effet</th><th>Se pose sur</th></tr>')
        for a in lst:
            rs = [r for r in RARITY_ORDER if r in a['text']]
            if not rs: continue
            r = 'mythic' if 'mythic' in rs else rs[-1]
            kind = 'Stat' if a['kind'] == 'attribute' else 'Capacité'
            types = ', '.join(tfr(t) for t in a['types'][:6]) + (' …' if len(a['types']) > 6 else '')
            out.append(f'<tr data-q="{esc((a["name"] + " " + (a.get("suffix") or "") + " " + a["text"][r] + " " + types).lower())}"><td><b>{esc(a["name"])}</b><div class="mut small">{kind}{(" · suffixe « " + esc(a["suffix"]) + " »") if a.get("suffix") else ""}</div></td><td>{rarity_chip(r)} : {esc(a["text"][r])}</td><td class="small">{esc(types)}</td></tr>')
        out.append('</table>')
    out.append('</div>')
    return '\n'.join(out)


def origin_cards(layer_id, intro=''):
    L = D['origins'][layer_id]
    out = [intro, '<input class="filter" data-target="olist" placeholder="Filtrer…">', '<div id="olist">']
    for o in sorted(L['origins'], key=lambda o: o['name']):
        pw = []; seen = set()
        for p in o['powers']:
            k = (p['name'], p['desc'])
            if k in seen or not p['desc']: continue
            seen.add(k)
            pw.append(f'<div class="power"><b>{esc(p["name"])}</b><div class="small">{esc(p.get("desc_fr") or p["desc"])}</div></div>')
        out.append(f'<div class="card" data-q="{esc((o["name"] + " " + (o.get("desc_fr") or "")).lower())}"><h3 id="{o["id"].replace(":", "-")}">{esc(o["name"])}</h3><p class="small">{esc(o.get("desc_fr") or o["desc"])}</p>{"".join(pw)}</div>')
    out.append('</div>')
    return '\n'.join(out)


def page_talents():
    nodes = D['skilltree']['nodes']
    out = []
    CL = {'alchemist': 'Alchimiste', 'blacksmith': 'Forgeron', 'cook': 'Cuisinier', 'enchanter': 'Enchanteur', 'hunter': 'Chasseur', 'miner': 'Mineur'}
    for c, name in CL.items():
        ns = [n for n in nodes if n['id'].startswith(c + '_')]
        keys = [n for n in ns if n['tier'] == 'keystone']; nots = [n for n in ns if n['tier'] == 'notable']
        out.append(f'<h2>{name}</h2><div class="grid2"><div><h3>Clés de voûte</h3>' + ''.join(f'<div class="power"><b>{esc(n["name"])}</b><div class="small">{esc(" · ".join(n["effects"]))}</div></div>' for n in keys) +
                   '</div><div><h3>Nœuds notables</h3>' + ''.join(f'<div class="power"><b>{esc(n["name"])}</b><div class="small">{esc(" · ".join(n["effects"]))}</div></div>' for n in nots) + '</div></div>')
    return '\n'.join(out)


def page_sorts():
    SP = D['spells']; SCH = SP['schools']
    out = ['<p>Voici les sorts d\'<b>Iron\'s Spells \'n Spellbooks</b> avec les <b>réglages de ce pack</b> : école, niveau maximum, multiplicateur de puissance et recharge. Un multiplicateur de puissance bas (×0,2–0,3) signifie que le sort dépend beaucoup de ta puissance de sorts ; <b>×1</b> est le réglage normal du mod.</p>',
           '<input class="filter" data-target="splist" placeholder="Filtrer…">', '<div id="splist">']
    by = {}
    for s in SP['spells']: by.setdefault(s['cfg']['school'], []).append(s)
    for sc in SCH:
        if sc not in by: continue
        out.append(f'<h2>École : {SCH[sc]}</h2><table class="t"><tr><th>Sort</th><th>Rareté min.</th><th>Niv. max</th><th>Puiss. ×</th><th>Mana</th><th>Incant.</th><th>Recharge</th></tr>')
        for s in sorted(by[sc], key=lambda s: s['name']):
            c = s['cfg']
            flag = '' if c['enabled'] else ' <span class="bad">(désactivé)</span>'
            changed = ' <span class="tag" title="École modifiée par le pack">école modifiée</span>' if c['school'] != s['school'] else ''
            pcls = 'bad' if c['powerMult'] < 0.5 else ('good' if c['powerMult'] >= 0.9 else '')
            out.append(f'<tr data-q="{esc((s["name"] + " " + s["guide"]).lower())}"><td><b>{esc(s["name"])}</b>{flag}{changed}<div class="mut small">{esc(s["guide"])}</div></td><td>{SP["rarities"].get(c["minRarity"], c["minRarity"])}</td><td>{c["maxLevel"]}</td><td class="{pcls}">{c["powerMult"]}</td><td>{s["baseMana"]}+{s["manaPerLevel"]}/niv.</td><td>{ {"INSTANT": "instant", "LONG": fnum(s["castTime"]/20,1)+" s", "CONTINUOUS": "canalisé", "CHARGE": "chargé"}.get(s["castType"], "") }</td><td>{c["cooldown"]} s</td></tr>')
        out.append('</table>')
    out.append('</div>')
    return '\n'.join(out)


def page_boss():
    out = []
    for tier in ['Palier 1', 'Gardien', 'Palier 2', 'Palier 3', 'Histoire']:
        bl = [b for b in D['bosses'] if b['tier'] == tier]
        if not bl: continue
        out.append(f'<h2>{tier}</h2>')
        for b in sorted(bl, key=lambda b: (b['level'] or {'start': 0})['start']):
            lv = b['level']
            lvt = f"niveau {lv['start']} à {lv['start'] + lv['rand']} (+{lv['lpd']}/bloc)" if lv else '—'
            st = []
            if b['hp']: st.append(f"PV de base {fnum(b['hp'])}" + (f" ×{fnum(b['hpm'])}" if b['hpm'] != 1 else ''))
            if b['dmg']: st.append(f"dégâts {fnum(b['dmg'])}" + (f" ×{fnum(b['dmgm'])}" if b['dmgm'] != 1 else ''))
            if b['armor']: st.append(f"armure {fnum(b['armor'])}")
            if b['cap']: st.append(f"<b>plafond de dégâts par coup : {b['cap']}</b>")
            est = ''
            if lv and b['hp']:
                rows = []
                for dist in (0, 3000, 6000):
                    L = lv['start'] - 1 + int(lv['lpd'] * dist) + lv['rand'] / 2
                    rows.append(f"<td>{dist} blocs</td><td>niv. {round(L)}</td><td>{fnum(b['hp']*b['hpm']*(1+0.09*L),0)} PV</td><td>{fnum(b['dmg']*b['dmgm']*(1+0.14*L),0) if b['dmg'] else '—'} dégâts</td><td>armure {fnum(b['armor']*(1+0.08*L),0)}</td>")
                est = '<table class="t"><tr><th colspan="5">Stats estimées (niveau moyen) selon la distance</th></tr>' + ''.join(f'<tr>{r}</tr>' for r in rows) + '</table>'
            out.append(f'<div class="card" id="{b["id"].replace(":", "-")}"><h3>{esc(b["name"])} <span class="tag">{esc(b["mod"])}</span></h3><div class="small mut">{esc(b["dim"])} — {lvt}</div>'
                       f'<p class="small"><b>Invocation :</b> {esc(b["how"])}</p>' + (f'<p class="small">{" · ".join(st)}</p>' if st else '') +
                       ('<ul class="small">' + ''.join(f'<li>{esc(n)}</li>' for n in b['notes']) + '</ul>' if b['notes'] else '') + est + '</div>')
    return '\n'.join(out)


def page_objets_campagne():
    items = [i for i in D['items'] if i['id'].startswith(('cisco_mod:',)) and (i.get('armor') is not None or i.get('dmg') is not None)]
    sets = {}
    for i in items:
        if i.get('slot'): sets.setdefault(i['id'].rsplit('_', 1)[0], []).append(i)
    out = ['<p>L\'équipement de la campagne (mod « Cisco\'s Content ») : valeurs lues dans le code du mod, avec les corrections de KubeJS. L\'armure est donnée <b>par pièce</b> (casque / plastron / jambières / bottes).</p>', '<h2>Armures</h2>',
           '<table class="t"><tr><th>Set</th><th>Armure (tête/torse/jambes/pieds)</th><th>Robustesse</th><th>Effets</th></tr>']
    for k, v in sorted(sets.items(), key=lambda kv: -sum(x.get('armor') or 0 for x in kv[1])):
        v.sort(key=lambda e: ['helmet', 'chestplate', 'leggings', 'boots'].index(e['slot']))
        tips = list(dict.fromkeys(t for e in v for t in e['tooltip']))
        out.append(f'<tr><td><b>{esc(re.sub(r" (Helmet|Chestplate|Leggings|Boots)$", "", v[0]["name"]))}</b></td><td>{" / ".join(str(e.get("armor")) for e in v)} <span class="mut small">(total {sum(e.get("armor") or 0 for e in v)})</span></td><td>{v[0].get("toughness", "")}</td><td class="small">{esc(" ".join(tips))}</td></tr>')
    out.append('</table><h2>Armes & objets</h2><table class="t"><tr><th>Objet</th><th>Dégâts</th><th>Vitesse</th><th>Effet</th></tr>')
    for i in sorted([x for x in items if not x.get('slot') and x.get('dmg')], key=lambda x: -x['dmg']):
        out.append(f'<tr><td><b>{esc(i["name"])}</b></td><td>{fnum(i["dmg"],1)}</td><td>{fnum(i.get("spd", 0),2)}</td><td class="small">{esc(" ".join(i["tooltip"]))}</td></tr>')
    out.append('</table>')
    other = [i for i in D['items'] if i['id'].startswith('cisco_mod:') and not i.get('slot') and not i.get('dmg') and i['tooltip']]
    out.append('<h2>Accessoires, matériaux & objets spéciaux</h2><table class="t"><tr><th>Objet</th><th>Description</th></tr>')
    for i in other: out.append(f'<tr><td><b>{esc(i["name"])}</b></td><td class="small">{esc(" ".join(i["tooltip"]))}</td></tr>')
    out.append('</table>')
    kjs = [i for i in D['items'] if i.get('kjs') and not i['id'].startswith('cisco_mod:')]
    return '\n'.join(out)


def page_quetes():
    out = ['<p>Le livre de quêtes (FTB Quests, touche <code>0</code>) est la colonne vertébrale du pack. Tous les textes ci-dessous sont <b>traduits en français</b> ; les noms d\'objets et de monstres restent en anglais, comme en jeu. Clique sur une quête pour lire son histoire complète.</p>',
           '<input class="filter" data-target="qlist" placeholder="Chercher une quête (boss, objet, mot-clé)…"><div id="qlist">']
    for c in D['quests']:
        sc = sum((r.get('count', 1)) for q in c['quests'] for r in q['rewards'] if r.get('item') == 'skilltree:wisdom_scroll')
        coins = sum((r.get('count', 1)) for q in c['quests'] for r in q['rewards'] if r.get('item') == 'cisco_mod:champion_coin')
        out.append(f'<h2>{esc(c.get("title_fr") or c["title"])} <span class="tag">{esc(c["group"])}</span></h2><div class="small mut">{len(c["quests"])} quêtes · {int(sc)} parchemins de sagesse · {int(coins)} pièces de champion</div><div class="qchap">')
        for q in c['quests']:
            tt = q.get('title_fr') or q.get('subtitle_fr') or q['title'] or q['subtitle'] or '(sans titre)'
            sub = q.get('subtitle_fr') if q.get('title_fr') else ''
            desc = q.get('desc_fr') or q['desc']
            task = ', '.join(((t.get('title_fr') if t.get('title_fr') and not t.get('name') else None) or t.get('name') or t.get('title') or '') + (f' ×{int(t["count"])}' if t.get('count', 1) and t.get('count', 1) > 1 else '') for t in q['tasks'] if (t.get('name') or t.get('title')))
            rew = ', '.join(((r.get('name') or r.get('table') or '') + (f' ×{int(r["count"])}' if r.get('count', 1) and r.get('count', 1) > 1 else '')) for r in q['rewards'] if (r.get('name') or r.get('table')))
            body = ''.join(f'<p>{esc(x)}</p>' for x in desc if x.strip())
            out.append(f'<details class="quest" data-q="{esc((tt + " " + sub + " " + " ".join(desc) + " " + task).lower())}"><summary><b>{esc(tt)}</b> <span class="mut small">{esc(sub)}</span></summary>{body}'
                       + (f'<p class="small"><b>Objectif :</b> {esc(task)}</p>' if task else '') + (f'<p class="small"><b>Récompenses :</b> {esc(rew)}</p>' if rew else '') + '</details>')
        out.append('</div>')
    out.append('</div>')
    return '\n'.join(out)


def page_enchants():
    E = json.load(open('site/data/enchants.json'))
    RF = {'COMMON': 'Commun', 'UNCOMMON': 'Peu commun', 'RARE': 'Rare', 'VERY_RARE': 'Très rare'}
    out = ['<p>Tous les enchantements du pack (vanilla + mods), avec leur <b>niveau maximum à la table d\'enchantement</b> (réglage du pack) et leur effet en français. Les noms restent ceux du jeu. « Butin max » = niveau maximal qu\'on trouve dans les coffres/sur les monstres ; au-delà, il faut enchanter soi-même (voir <a href="atelier.html">Ateliers & enchantement</a>).</p>',
           '<input class="filter" data-target="enlist" placeholder="Filtrer (nom, effet, mod)…"><div id="enlist">']
    order = ['Arme', 'Arc', 'Arbalète', 'Trident', 'Armure', 'Outil', 'Canne à pêche', 'Tous', 'Autre', 'Malédiction']
    for cat in order:
        lst = sorted([e for e in E if e['cat'] == cat], key=lambda e: (e['mod'] != 'Vanilla', e['mod'], e['name']))
        if not lst: continue
        out.append(f'<h2>{cat} ({len(lst)})</h2><table class="t"><tr><th>Enchantement</th><th>Effet</th><th>Niv. max</th><th>Butin max</th><th>Rareté</th></tr>')
        for e in lst:
            fl = ' <span class="tag">trésor</span>' if e['treasure'] else ''
            out.append(f'<tr data-q="{esc((e["name"] + " " + e["desc"] + " " + e["mod"]).lower())}"><td><b>{esc(e["name"])}</b>{fl}<div class="mut small">{esc(e["mod"])}</div></td><td class="small">{esc(e["desc"])}</td><td>{e["max"] or "?"}</td><td>{e["loot"] or "—"}</td><td class="small">{RF.get(e["rarity"], "—")}</td></tr>')
        out.append('</table>')
    out.append('</div>')
    return '\n'.join(out)


def page_ars():
    G = json.load(open('site/data/ars.json'))
    out = ['<p>Coût en <b>Source</b> de chaque glyphe (config du pack). <b>Compose ton sort</b> : coche des glyphes, le total s\'affiche.</p>',
           '<div class="card calc" id="arscalc"><b>Coût total : <span id="arstotal">0</span></b> <span class="mut small">· glyphes cochés : <span id="arsn">0</span></span></div>',
           '<input class="filter" data-target="arslist" placeholder="Filtrer…"><div id="arslist">']
    for kind in ['forme', 'effet', 'augmentation']:
        out.append(f'<h2>{ {"forme": "Formes (comment le sort est lancé)", "effet": "Effets", "augmentation": "Augmentations"}[kind] }</h2><table class="t"><tr><th></th><th>Glyphe</th><th>Coût</th><th>Palier</th><th>Description</th></tr>')
        for g in sorted([x for x in G if x['kind'] == kind and x['enabled']], key=lambda x: (x['tier'] or 0, x['name'])):
            d = g.get('desc_fr') or g['desc']
            tag = '' if g.get('desc_fr') else ' <span class="tag">EN</span>'
            out.append(f'<tr data-q="{esc((g["name"] + " " + d).lower())}"><td><input type="checkbox" class="arsck" data-c="{g["cost"] or 0}"></td><td><b>{esc(g["name"])}</b>{" <span class=tag>départ</span>" if g["starter"] else ""}</td><td>{g["cost"]}</td><td>{g["tier"]}</td><td class="small">{esc(d)}{tag}</td></tr>')
        out.append('</table>')
    out.append('</div><script>document.querySelectorAll(".arsck").forEach(c=>c.addEventListener("change",()=>{let t=0,n=0;document.querySelectorAll(".arsck:checked").forEach(x=>{t+=+x.dataset.c;n++});document.getElementById("arstotal").textContent=t;document.getElementById("arsn").textContent=n}))</script>')
    return '\n'.join(out)


def page_mods():
    out = ['<p>288 mods, Forge 1.19.2. Seuls les mods « gameplay » changent ce que tu vois en jeu ; le reste (performances, bibliothèques) travaille en coulisses.</p>']
    order = ['🎮 Gameplay', '🧭 Outils & interface', '🏗️ Décoration & construction', '🛠️ Admin / modpack', '⚙️ Performance', '📚 Bibliothèques']
    by = {}
    for k, v in MODS.items():
        cat, sub = k.split(' / ', 1)
        by.setdefault(cat, {}).setdefault(sub, []).extend(v)
    for cat in order:
        if cat not in by: continue
        n = sum(len(v) for v in by[cat].values())
        out.append(f'<h2>{esc(cat)} ({n})</h2>')
        for sub, lst in by[cat].items():
            if cat.startswith('🎮'):
                out.append(f'<h3>{esc(sub)} ({len(lst)})</h3><table class="t"><tr><th>Mod</th><th>À quoi ça sert</th></tr>' + ''.join(
                    f'<tr><td><b>{esc(m[1])}</b></td><td class="small">{esc(FRM.get(m[0], ""))}</td></tr>' for m in sorted(lst, key=lambda m: (m[1] or '').lower())) + '</table>')
            else:
                out.append(f'<h3>{esc(sub)} ({len(lst)})</h3><p class="small">' + ' · '.join(f'<b>{esc(m[1])}</b>' for m in sorted(lst, key=lambda m: (m[1] or '').lower())) + '</p>')
    return '\n'.join(out)


# ------------------------------------------------------------------ builds
def stat(k, v): return f'<div class="stat"><span>{k}</span><b>{v}</b></div>'


def build_page(bid, md_text):
    rs = [b for b in BUILDS if b['id'] == bid]
    cat = BUILD_CAT.get(bid)
    if not rs: return md(md_text)
    out = [md(md_text)]
    if cat:
        out.append(f'<h2>Comment adapter ce build</h2><p class="small">Catégorie : <b>{cat["icon"]} {esc(cat["title"])}</b> — <a href="index.html#{cat["id"]}">voir les autres builds de la catégorie</a>.</p>')
        out.append(md('\n'.join(f'- {t}' for t in cat['adapt']) + '\n\n**Pour aller plus loin :** [Adapter un build à son loot](adapter.html) · [Débutant → Optimisé](niveaux.html) · charge-le dans le [planificateur](../../pob/index.html) pour tester tes modifications.'))
    STD = {'debutant': 'Rare, affixes à 30 % de leur plage, 1 affixe et 1 gemme par pièce, armure d\'aventurier et arme en fer', 'milieu': 'Épique, affixes à 40 % de leur plage, 2 affixes et 1 gemme optimisés par pièce', 'fin': 'Mythique, affixes à 60 % de leur plage, 3 affixes et 2 gemmes optimisés par pièce', 'optimise': 'Mythique, affixes à 90 % de leur plage, 4 affixes et 3 gemmes optimisés par pièce, tous les accessoires'}
    out.append(choices_detail(rs))
    out.append(progression_table(rs))
    out.append(tree_box(rs))
    for st in ['debutant', 'milieu', 'fin', 'optimise']:
        b = next((x for x in rs if x['stage'] == st), None)
        if not b: continue
        s = b['summary']
        out.append(f'<h2>Fiche chiffrée — {b["label"]}</h2><div class="note small">Calculée avec le moteur du planificateur : équipement : {STD[st]} ; {s["nodes"]} points de talent {"fixés sur la classe de départ" if st == "debutant" else "choisis automatiquement"}. <b>C\'est une cible théorique</b>, pas un loot garanti.</div><div class="grid2"><div class="card">')
        out.append(stat('Vie max', fnum(s['hp'], 0)) + stat('Armure / robustesse', f"{s['armor']} / {fnum(s['tough'],0)}") + stat('Esquive', pct(s['dodge'], 0)))
        if s['arrow'] and b['id'] == 'archer' or (bid == 'archer'):
            out.append(stat('Flèche', fnum(s['arrow'], 0)))
        else:
            out.append(stat('Coup de mêlée', fnum(s['hit'], 0)) + stat('Vitesse d\'attaque', fnum(s['spd'], 2)))
        out.append(stat('Critique', f"{pct(s['critC'],0)} × {fnum(s['critD'],2)}"))
        out.append('</div><div class="card">')
        out.append(stat('Mana max', fnum(s['mana'], 0)) + stat('Régénération de mana', fnum(s['regen'], 1) + ' /s'))
        for sc, key in [('Feu', 'fire'), ('Glace', 'ice'), ('Foudre', 'lightning'), ('Sacré', 'holy'), ('Ender', 'ender'), ('Sang', 'blood'), ('Nature', 'nature')]:
            if s[key] and abs(s[key] - 1) > 1e-6: out.append(stat('Puissance ' + sc, '×' + fnum(s[key], 1)))
        if abs(s['summon'] - 1) > 1e-6: out.append(stat('Puissance d\'invocation', '×' + fnum(s['summon'], 1)))
        out.append(stat('Recharge des sorts', '× ' + fnum(2 - softcap(s['cdr']), 2)))
        out.append('</div></div>')
        if b['spells']:
            out.append('<h3>Sorts</h3><table class="t"><tr><th>Sort</th><th>Niv.</th><th>Mana</th><th>Incantation</th><th>Recharge</th><th>Effets (calculés)</th></tr>')
            for sp in b['spells']:
                eff = ' · '.join(f"{LABFR.get(lab, lab)} {fnum(v,0)}{u}" for lab, v, u in sp['info'] if v is not None and plausible(lab, v, u)) or '—'
                out.append(f'<tr><td><b>{esc(sp["name"])}</b></td><td>{sp["level"]}</td><td>{sp["mana"]}</td><td>{fnum(sp["cast"],2)} s</td><td>{fnum(sp["cd"],1)} s</td><td class="small">{esc(eff)}</td></tr>')
            out.append('</table>')
        out.append(f'<h3>Équipement visé ({b["label"]})</h3><table class="t"><tr><th>Emplacement</th><th>Objet</th><th>Affixes de stat (valeur max de la plage)</th><th>Gemmes</th></tr>')
        SLOTN = {'main': 'Arme', 'off': 'Main secondaire', 'head': 'Casque', 'chest': 'Plastron', 'legs': 'Jambières', 'boots': 'Bottes', 'ring1': 'Anneau 1', 'ring2': 'Anneau 2', 'neck': 'Collier', 'belt': 'Ceinture', 'back': 'Dos', 'body': 'Corps', 'hands': 'Mains', 'wrist': 'Bracelet', 'talis': 'Talisman', 'charm': 'Charme', 'book': 'Livre de sorts'}
        for g in b['gear']:
            aff = '<br>'.join(f"{esc(a['name'])} : {esc(a['text'])}" for a in g['affixes']) or '<span class="mut">—</span>'
            gems = '<br>'.join(esc(x['name']) for x in g['gems']) or '<span class="mut">—</span>'
            base = ''
            if g['base']:
                bb = g['base']
                base = ' ' + ('dégâts ' + str(bb['dmg']) if 'dmg' in bb else ('armure ' + str(bb.get('armor', 0)) if 'armor' in bb else ''))
            out.append(f'<tr><td>{SLOTN.get(g["slot"], g["slot"])}</td><td><b>{esc(g["name"])}</b><div class="mut small">{rarity_chip(g["rarity"])}{base}</div></td><td class="small">{aff}</td><td class="small">{gems}</td></tr>')
        out.append('</table>')
        ks = b['keystones']
        CLN = {'alchemist': 'Alchimiste', 'blacksmith': 'Forgeron', 'cook': 'Cuisinier', 'enchanter': 'Enchanteur', 'hunter': 'Chasseur', 'miner': 'Mineur'}
        start = b['nodes'][0].split('_')[0] if b.get('nodes') else b.get('tree')
        out.append(f'<h3>Talents choisis ({b["summary"]["nodes"]} points) — départ : classe {CLN.get(start, start)}</h3><p class="small">' + ' · '.join(f'<b>{esc(k["name"])}</b>' if k['tier'] == 'keystone' else esc(k['name']) for k in ks) + '</p>')
        out.append(f'<p><a href="../../pob/index.html" class="btn">Ouvrir ce build dans le planificateur</a> <span class="mut small">(onglet « Builds prêts » → {esc(b["title"])} — {b["label"]})</span></p>')
    return '\n'.join(out)


ORI = {}
for _k in ('origins:origin', 'origins-classes:class', 'cisco_rpg_origins:divineblessings'):
    for _o in D['origins'][_k]['origins']: ORI[_o['id']] = (_k, _o)


def choices_detail(rs):
    """Détail des 3 choix de départ (origine, classe, bénédiction) : description et pouvoirs, tirés des données du pack."""
    b = rs[0]; out = ['<h2 id="choix">Tes 3 choix en détail</h2><p class="small">Ce que chaque choix te donne réellement, tiré des fichiers du pack. Les pouvoirs sans chiffre sont décrits à titre indicatif.</p><div class="grid3">']
    for lab, key in (('Origine', 'origin'), ('Classe', 'cls'), ('Bénédiction divine', 'blessing')):
        k = ORI.get(b.get(key))
        if not k: continue
        o = k[1]
        pw = ''.join(f'<li><b>{esc(p.get("name_fr") or p["name"])}</b> — {esc(p.get("desc_fr") or p["desc"])}</li>' for p in o['powers'] if not p.get('hidden') and (p.get('desc_fr') or p.get('desc')))
        out.append(f'<div class="card"><div class="mut small">{lab}</div><h3>{esc(o.get("name_fr") or o["name"])}</h3><p class="small">{esc(o.get("desc_fr") or o["desc"])}</p><ul class="small">{pw}</ul></div>')
    out.append('</div>')
    return ''.join(out)


def progression_table(rs):
    """Tableau d'évolution des 4 niveaux : on voit ce que chaque palier apporte."""
    st = [(k, next((x for x in rs if x['stage'] == k), None)) for k in ('debutant', 'milieu', 'fin', 'optimise')]
    st = [(k, b) for k, b in st if b]
    if len(st) < 2: return ''
    ranged = any(b['summary'].get('arrow') and b['id'] in ('archer', 'archer-elfe', 'arbalete') for _, b in st) or rs[0]['id'].startswith(('archer', 'arbalete'))
    rows = [('Vie max', lambda s: fnum(s['hp'], 0)), ('Armure', lambda s: fnum(s['armor'], 0)), ('Esquive', lambda s: pct(s['dodge'], 0)),
            ('Dégâts par flèche' if ranged else 'Dégâts par coup', lambda s: fnum(s['arrow'] if ranged else s['hit'], 0)), ('Critique', lambda s: pct(s['critC'], 0) + ' × ' + fnum(s['critD'], 1)),
            ('Mana max', lambda s: fnum(s['mana'], 0)), ('Points de talent', lambda s: str(s['nodes']))]
    out = ['<h2 id="progression">Ce que chaque niveau t\'apporte</h2><table class="t"><tr><th></th>' + ''.join(f'<th>{STAGE_LABELS[k]}</th>' for k, _ in st) + '</tr>']
    for lab, f in rows:
        out.append(f'<tr><td><b>{lab}</b></td>' + ''.join(f'<td>{f(b["summary"])}</td>' for _, b in st) + '</tr>')
    out.append('</table><p class="small mut">Les chiffres augmentent surtout grâce à la rareté de l\'équipement, au nombre d\'affixes/gemmes et aux points de talent. Ils servent à comparer les niveaux entre eux.</p>')
    return ''.join(out)


STAGE_LABELS = {'debutant': 'Débutant', 'milieu': 'Intermédiaire', 'fin': 'Avancé', 'optimise': 'Optimisé'}


def tree_box(rs):
    """Arbre de talents du build : visionneuse canvas (tous les nœuds en gris, ceux du build en couleur), un onglet par niveau."""
    st = {b['stage']: b['nodes'] for b in rs if b.get('nodes')}
    if not st: return ''
    order = [k for k in STAGE_LABELS if k in st]
    return ('<h2 id="arbre">Arbre de talents</h2><p class="small">Les nœuds <b>en couleur</b> sont ceux à prendre ; choisis le niveau avec les boutons. Molette pour zoomer, glisser pour déplacer, survole un nœud pour lire son effet. '
            '<span class="tleg"><i style="background:#5bd75b"></i>départ <i style="background:#8aa0b8"></i>mineur <i style="background:#58a6ff"></i>notable <i style="background:#e0a63a"></i>clé de voûte</span></p>'
            f'<div class="treebox" data-stages=\'{json.dumps(st)}\' data-order=\'{json.dumps(order)}\' data-labels=\'{json.dumps(STAGE_LABELS, ensure_ascii=False)}\'>'
            '<div class="tbar"></div><div class="tinfo small"></div><canvas></canvas><div class="ttip" hidden></div><button class="tfit sm">Recentrer</button></div>'
            '<script src="../tree.js"></script>')


def builds_index():
    """Index des builds rangés par catégorie (source : site/data/build_cats.js + wiki_src/builds/*.md)."""
    meta = {}
    for f in glob.glob(SRC + '/builds/*.md'):
        slug = os.path.basename(f)[:-3]
        txt = open(f, encoding='utf8').read()
        t = re.search(r'^title:\s*(.+)$', txt, re.M); one = re.search(r'En une phrase :\*\*\s*(.+)', txt)
        meta[slug] = (t.group(1).strip() if t else slug, re.sub(r'[*_`]|\[([^\]]*)\]\([^)]*\)', lambda m: m.group(1) or '', one.group(1)) if one else '')
    out = ['<h2>Choisir par catégorie</h2><p>' + ' · '.join(f'<a href="#{c["id"]}">{c["icon"]} {esc(c["title"])}</a>' for c in CATS) + '</p>']
    for c in CATS:
        out.append(f'<h2 id="{c["id"]}">{c["icon"]} {esc(c["title"])}</h2><p>{esc(c["desc"])}</p>')
        out.append('<table class="t"><tr><th>Build</th><th>En bref</th></tr>' + ''.join(
            f'<tr><td><a href="{b}.html"><b>{esc(meta.get(b, (b, ""))[0])}</b></a></td><td class="small">{esc(meta.get(b, (b, ""))[1])}</td></tr>' for b in c['builds']) + '</table>')
        out.append('<details><summary>Comment adapter cette catégorie ?</summary>' + md('\n'.join(f'- {t}' for t in c['adapt'])) + '</details>')
    return '\n'.join(out)


def plausible(lab, v, u):
    """Écarte les durées / portées absurdes (la formule du sort explose avec une grosse puissance ; le jeu les borne probablement)."""
    if lab in ('Portée (blocs)', 'Rayon (blocs)', "Portée d'incantation") and v > 64: return False
    if lab in ("Durée de l'effet", 'Durée', 'Durée de gel', 'Durée du portail') and v > 600: return False
    if lab in ('Coups esquivés', 'hits dodged', 'Cibles max') and v > 50: return False
    return True


def softcap(x): return x if x <= 1.75 else 1 / (-16 * (x - 1.5)) + 2


def pct(x, nd=1): return fnum(x * 100, nd) + ' %'


def main():
    shutil.rmtree(OUT, ignore_errors=True); os.makedirs(OUT, exist_ok=True); shutil.copy(SRC + '/_wiki.js', OUT + '/wiki.js'); shutil.copy(SRC + '/_tree.js', OUT + '/tree.js')
    pages = load_md()
    DYN = {'gemmes': page_gemmes, 'affixes': page_affixes, 'talents': page_talents, 'sorts': page_sorts, 'boss': page_boss, 'objets-campagne': page_objets_campagne, 'quetes': page_quetes, 'mods': page_mods, 'ars': page_ars, 'enchants': page_enchants,
           'origines': lambda: origin_cards('origins:origin'), 'classes': lambda: origin_cards('origins-classes:class'), 'benedictions': lambda: origin_cards('cisco_rpg_origins:divineblessings')}
    done = set()
    for slug, (meta, raw) in pages.items():
        title = meta.get('title') or TITLES.get(slug) or slug
        body = md(raw)
        if slug in DYN: body += DYN[slug]()
        elif slug == 'builds/index': body += builds_index()
        elif slug.startswith('builds/') and slug not in ('builds/niveaux', 'builds/adapter'): body = build_page(slug.split('/')[1], raw)
        write(slug, title, body, meta.get('desc', '')); done.add(slug)
    for slug in DYN:
        if slug not in done: write(slug, TITLES.get(slug, slug), DYN[slug]())
    # index des builds
    if 'builds/index' not in done:
        write('builds/index', 'Tous les builds', '<p>Aucun guide pour l\'instant.</p>')
    # recherche
    open(OUT + '/search-index.js', 'w', encoding='utf8').write('window.SEARCH=' + json.dumps(PAGES_SEARCH, ensure_ascii=False) + ';')
    print(len(PAGES_SEARCH), 'pages')


if __name__ == '__main__':
    main()
