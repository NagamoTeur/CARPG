/* Interface du planificateur de build */
(function () {
  const { SLOT_DEFS } = ENG;
  const TF = D.typeFr, RO = D.rarityOrder, RA = D.rarities;
  const $ = (s, r = document) => r.querySelector(s);
  const el = (tag, props, ...kids) => {
    const e = document.createElement(tag);
    for (const k in props || {}) {
      if (k === 'class') e.className = props[k]; else if (k === 'html') e.innerHTML = props[k];
      else if (k.startsWith('on')) e.addEventListener(k.slice(2), props[k]); else if (props[k] !== false && props[k] != null) e.setAttribute(k, props[k] === true ? '' : props[k]);
    }
    for (const c of kids.flat()) if (c != null && c !== false) e.append(c.nodeType ? c : document.createTextNode(c));
    return e;
  };
  const fnum = (x, nd = 2) => { if (!isFinite(x)) return '∞'; const r = Math.round(x * 10 ** nd) / 10 ** nd; return String(r).replace('.', ','); };
  const pct = (x, nd = 1) => fnum(x * 100, nd) + ' %';
  const rname = r => RA[r].name;

  /* ---------- état ---------- */
  const DEFAULT = () => ({ v: 1, name: 'Nouveau build', origin: 'origins:human', cls: 'origins-classes:warrior', blessing: 'cisco_rpg_origins:no_blessing',
    flags: {}, points: 40, nodes: [], gear: {}, spells: [], manual: [], notes: '', hit: 30 });
  let S = load();
  function load() {
    try {
      const h = location.hash.slice(1);
      if (h.startsWith('b=')) return Object.assign(DEFAULT(), JSON.parse(decodeURIComponent(escape(atob(h.slice(2).replace(/-/g, '+').replace(/_/g, '/'))))));
    } catch (e) { console.warn('hash invalide', e); }
    try { const j = localStorage.getItem('carpg_pob'); if (j) return Object.assign(DEFAULT(), JSON.parse(j)); } catch (e) { }
    return DEFAULT();
  }
  const save = () => { try { localStorage.setItem('carpg_pob', JSON.stringify(S)); } catch (e) { } };
  const shareLink = () => location.href.split('#')[0] + '#b=' + btoa(unescape(encodeURIComponent(JSON.stringify(S)))).replace(/\+/g, '-').replace(/\//g, '_');

  let tab = 'simple', slotSel = 'main', R = null, treeView = null;
  function recalc() { R = ENG.compute(S); save(); renderSheet(); }

  /* ---------- aides d'affichage ---------- */
  const layer = id => D.origins[id];
  const nameOf = o => o.name_fr || o.name;
  const descOf = o => o.desc_fr || o.desc;
  const isFr = o => !!(o.desc_fr);
  function affixLabel(a, rar) { const t = a.text && (a.text[rar] || a.text[a.rarities[0]]); return a.name + (a.suffix ? ' / ' + a.suffix : '') + (t ? '  —  ' + t : ''); }
  function slotTitle(sd) { const it = S.gear[sd.id]; return it ? (it.name || '') : ''; }

  /* ---------- onglets ---------- */
  const TABS = [['simple', 'Mode simple'], ['perso', 'Personnage'], ['arbre', 'Arbre de talents'], ['equip', 'Équipement'], ['sorts', 'Sorts'], ['boss', 'Simulateur de boss'], ['stats', 'Statistiques détaillées'], ['builds', 'Builds prêts']];
  function renderTabs() {
    const t = $('#tabs'); t.innerHTML = '';
    TABS.forEach(([id, n]) => t.append(el('button', { class: tab === id ? 'on' : '', onclick: () => { tab = id; render(); } }, n)));
  }
  function render() {
    renderTabs();
    const m = $('#main'); m.innerHTML = '';
    ({ simple: renderSimple, perso: renderPerso, arbre: renderTree, equip: renderGear, sorts: renderSpells, boss: renderBoss, stats: renderStats, builds: renderBuilds })[tab](m);
    renderSheet();
  }


  /* état d'un build : téléchargé à la demande (data/builds/<archétype>.json) */
  const STC = {};
  const stateOf = b => { const a = b.arch || b.id; return STC[a] ? Promise.resolve(STC[a][b.level]) : fetch('../data/builds/' + a + '.json').then(r => r.json()).then(d => { STC[a] = d; return d[b.level]; }); };
  const loadState = (b, st) => { S = Object.assign(DEFAULT(), JSON.parse(JSON.stringify(st)), { name: b.title }); $('#bname').value = S.name; };

  /* ---------- Mode simple : choisir un build, lire l'essentiel, savoir quoi améliorer ---------- */
  let sm = { arch: '', level: 'debutant' };
  function renderSimple(m) {
    const all = window.BUILDS_IDX || [], CT = (window.BUILD_CATS || { cats: [] }).cats;
    m.append(el('div', { class: 'note' }, 'Nouveau ? Choisis un build ci-dessous : l\'outil charge un personnage complet, affiche l\'essentiel et te dit quoi chercher en priorité. Tu peux ensuite passer en mode avancé pour tout modifier. Pas sûr de ton choix ? Fais le ', el('a', { href: '../wiki/quiz.html' }, 'quiz « Quel build pour moi ? »'), '.'));
    const archs = [...new Set(all.map(b => b.arch || b.id))];
    const label = a => (all.find(b => (b.arch || b.id) === a) || { title: a }).title.replace(/ — .*/, '');
    const sa = el('select', { onchange: e => { sm.arch = e.target.value; load(); } }, el('option', { value: '' }, '— Choisis un build —'),
      ...CT.map(c => el('optgroup', { label: c.icon + ' ' + c.title }, ...c.builds.filter(b => archs.includes(b)).map(b => el('option', { value: b, selected: sm.arch === b }, label(b))))));
    const sl = el('select', { onchange: e => { sm.level = e.target.value; load(); } }, ...LEVELS.map(([v, n]) => el('option', { value: v, selected: sm.level === v }, n)));
    m.append(el('div', { class: 'card' }, el('h3', {}, '1. Ton build'), el('div', { class: 'row' }, sa, sl)));
    const box = el('div', {}); m.append(box);
    function pickEntry(a, lv) { return all.find(b => (b.arch || b.id) === a && b.level === lv) || LEVELS.map(l => all.find(b => (b.arch || b.id) === a && b.level === l[0])).find(Boolean); }
    async function load() {
      box.innerHTML = '';
      if (!sm.arch) { box.append(el('p', { class: 'mut' }, 'Aucun build choisi : l\'outil affiche le personnage vide. Choisis-en un pour commencer.')); return; }
      const b = pickEntry(sm.arch, sm.level); if (!b) return;
      sm.level = b.level;
      const key = sm.arch + '|' + b.level;
      if (sm.key !== key) { sm.key = key; loadState(b, await stateOf(b)); }
      recalc();
      const card = (k, v, sub) => el('div', { class: 'card', style: 'flex:1;min-width:140px;text-align:center' }, el('div', { class: 'mut small' }, k), el('div', { style: 'font-size:1.6rem;font-weight:700' }, v), sub ? el('div', { class: 'mut small' }, sub) : null);
      const red = R.armorReduction(S.hit || 30);
      box.append(el('div', { class: 'card' }, el('h3', {}, '2. L\'essentiel de ce build'), el('p', { class: 'small' }, b.summary),
        el('div', { class: 'row', style: 'gap:10px;flex-wrap:wrap' },
          card('Vie max', fnum(R.hp, 0)), card('Armure', fnum(R.armor, 0), 'réduit un coup de ' + (S.hit || 30) + ' de ' + pct(red, 0)), card('Esquive', pct(R.dodgeTotal, 0)),
          R.ranged ? card('Dégâts par flèche', fnum(R.arrowHit, 0)) : card('Dégâts par coup', fnum(R.hit, 0)), card('Critique', pct(R.critC, 0), '× ' + fnum(R.critD, 1)), R.mana > 100 ? card('Mana', fnum(R.mana, 0)) : null)));
      // quoi chercher : stats visées par emplacement, par ordre de priorité (on lit des lignes de statistiques, pas des noms d'affixes)
      const WHY = D.statWhy || {};
      const statsOf = it => (it.affixes || []).map(a => IDX.AFFIX[a.id]).filter(af => af && af.kind === 'attribute').map(af => {
        const t = (af.text[it.rarity] || af.text[af.rarities[0]] || '').replace(/^([+\-][\d.,]+)–([\d.,]+)/, (m0, a, b2) => a.replace(/^[+\-]/, '') === b2 ? a : m0);
        const mm = t.match(/^([+\-][\d.,]+(?:–[\d.,]+)?)\s*(%?)\s*(\(mult\.\)\s*)?(.+)$/);
        return { label: mm ? mm[4] : af.name, val: mm ? mm[1] + (mm[2] ? ' %' : '') : '', why: WHY[af.attr] || '' };
      });
      const rows = Object.entries(S.gear).filter(([, it]) => statsOf(it).length).map(([id, it]) => {
        const sd = ENG.SLOT_DEFS.find(x => x.id === id);
        return el('tr', {}, el('td', {}, icn(it.name), sd ? sd.name : id), el('td', { class: 'small' }, ...statsOf(it).flatMap((x, i) => [el('div', { title: x.why }, el('b', {}, (i + 1) + '. ' + x.label), ' ', el('span', { class: 'mut' }, x.val))])));
      });
      box.append(el('div', { class: 'card' }, el('h3', {}, '3. Quoi chercher sur chaque pièce'), el('div', { class: 'small mut' }, 'Lis les lignes de statistiques de l\'info-bulle de tes objets : une pièce qui a 2 de ces 3 lignes est déjà très bonne. Survole une ligne pour savoir à quoi elle sert. Valeurs : plafond de la rareté visée.'),
        el('table', { class: 't' }, el('tr', {}, el('th', {}, 'Pièce'), el('th', {}, 'Stats à chercher (par ordre d\'importance)')), ...rows),
        el('p', { class: 'small' }, el('a', { href: '../wiki/stats-par-piece.html' }, 'Que chercher selon mon rôle, et que peut-on trouver sur chaque pièce ? →'))));
      // prochain niveau
      const li = LEVELS.findIndex(l => l[0] === b.level), nx = LEVELS.slice(li + 1).map(l => all.find(x => (x.arch || x.id) === sm.arch && x.level === l[0])).find(Boolean);
      if (nx) { const R2 = ENG.compute(await stateOf(nx)), d = (a, c) => (c >= a ? '+' : '') + fnum(c - a, 0);
        box.append(el('div', { class: 'card' }, el('h3', {}, '4. Prochain palier : ' + (LEVELS.find(l => l[0] === nx.level) || [0, nx.level])[1]),
          el('p', { class: 'small' }, 'Si tu atteins ce palier : ', el('b', {}, d(R.hp, R2.hp) + ' PV'), ', ', el('b', {}, d(R.armor, R2.armor) + ' armure'), ', ', el('b', {}, d(R.ranged ? R.arrowHit : R.hit, R2.ranged ? R2.arrowHit : R2.hit) + ' dégâts par coup'), '.'),
          el('button', { class: 'sm', onclick: () => { sm.level = nx.level; render(); } }, 'Voir ce palier'))); }
      box.append(el('div', { class: 'row' }, el('button', { class: 'pri', onclick: () => { tab = 'perso'; render(); } }, 'Passer en mode avancé'), el('button', { onclick: () => { tab = 'arbre'; render(); } }, 'Voir l\'arbre de talents'),
        b.wiki ? el('a', { class: 'btn', href: '../wiki/' + b.wiki }, 'Lire le guide complet') : null));
    }
    if (sm.arch) load();
  }

  /* ---------- Personnage ---------- */
  function powerBlock(o) {
    const seen = new Set();
    return o.powers.filter(p => { const k = (p.name || '') + (p.desc || ''); if (seen.has(k) || !p.desc) return false; seen.add(k); return true; })
      .map(p => el('div', { class: 'power' }, el('b', {}, p.name_fr || p.name), p.desc_fr ? null : el('span', { class: 'tag', title: 'Description originale (anglais) — pas encore traduite' }, 'EN'), el('div', { class: 'small' }, p.desc_fr || p.desc)));
  }
  function renderPerso(m) {
    const pick = (lid, key, title, hint) => {
      const L = layer(lid); const sel = el('select', { onchange: e => { S[key] = e.target.value; recalc(); render(); } },
        ...L.origins.slice().sort((a, b) => nameOf(a).localeCompare(nameOf(b))).map(o => el('option', { value: o.id, selected: S[key] === o.id }, nameOf(o))));
      const cur = L.origins.find(o => o.id === S[key]) || L.origins[0];
      return el('div', { class: 'card' }, el('h3', {}, title), el('div', { class: 'mut small' }, hint), sel,
        el('p', { class: 'small' }, cur ? descOf(cur) : ''), ...(cur ? powerBlock(cur) : []));
    };
    m.append(el('div', { class: 'note' }, 'Chaque joueur choisit 1 origine + 1 classe + 1 bénédiction divine. Les bonus chiffrés sont ajoutés automatiquement à la fiche de droite ; les pouvoirs sans chiffre sont décrits à titre indicatif.'));
    m.append(el('div', { class: 'row' }, el('label', {}, 'Dragon vaincu ?'),
      el('input', { type: 'checkbox', checked: !!S.flags.dragon, onchange: e => { S.flags.dragon = e.target.checked; recalc(); } }), el('span', { class: 'mut small' }, 'Active les bonus de bénédiction qui se débloquent après avoir tué l\'Ender Dragon.')));
    m.append(el('div', { class: 'grid3' }, pick('origins:origin', 'origin', 'Origine', 'Race : capacités passives, avantages et handicaps.'),
      pick('origins-classes:class', 'cls', 'Classe', 'Petits bonus de métier (forgeron, archer, guerrier…).'),
      pick('cisco_rpg_origins:divineblessings', 'blessing', 'Bénédiction divine', 'Un dieu te protège ; les « grands bienfaits » arrivent après le dragon.')));
    const as = S.assume = S.assume || {};
    const ck = (k, label) => el('label', { class: 'small', style: 'margin-right:14px' }, el('input', { type: 'checkbox', checked: !!as[k], onchange: e => { as[k] = e.target.checked; recalc(); } }), ' ' + label);
    m.append(el('div', { class: 'card' }, el('h3', {}, 'Hypothèses de combat'), el('div', { class: 'small mut' }, 'Certains talents et pouvoirs dépendent de la situation. Coche ce qui s\'applique à ton calcul.'),
      el('div', { class: 'row' }, ck('burning', 'Cible en feu'), ck('lowHp', 'PV ≤ 50 %'), ck('targetEffect', 'Cible sous effet (poison…)'), ck('sun', 'Exposé au soleil / au ciel'), ck('active', 'Capacité active de l\'arme (clic droit : Hellbrand, Frostfang…)'), ck('cursed', 'Porte l\'Anneau des Sept Malédictions (dégâts subis ×2, armure −30 %, dégâts infligés −50 %)')),
      el('div', { class: 'row' }, el('label', { class: 'small' }, 'Distance moyenne de la cible (blocs) '), el('input', { type: 'number', min: 0, value: as.dist ?? '', placeholder: 'auto', onchange: e => { as.dist = e.target.value === '' ? undefined : +e.target.value; recalc(); } }),
        el('label', { class: 'small', title: 'Ascension divine : après avoir vaincu le Roi déchu. Ascension déchue : après avoir vaincu Cisco descendu. On ne peut avoir que l\'une des deux.' }, ' Ascension '), el('select', { onchange: e => { as.godhood = e.target.value; recalc(); } }, ...[['', 'aucune'], ['divine', 'divine (Roi déchu vaincu)'], ['umbral', 'déchue (Cisco descendu vaincu)']].map(([v, n]) => el('option', { value: v, selected: (as.godhood || '') === v }, n))),
        el('label', { class: 'small', title: 'Part du temps passée à frapper à pleine charge (esquives, déplacements, recharge). 90 % = presque toujours en train de frapper.' }, ' Efficacité d\'attaque (%) '), el('input', { type: 'number', min: 10, max: 100, value: Math.round((as.uptime ?? 0.9) * 100), onchange: e => { as.uptime = Math.max(0.1, Math.min(1, +e.target.value / 100)); recalc(); } }),
        el('label', { class: 'small' }, ' Effets de potion actifs '), el('input', { type: 'number', min: 0, value: as.potions || 0, onchange: e => { as.potions = +e.target.value; recalc(); } }),
        el('label', { class: 'small' }, ' Niveaux d\'enchantement sur l\'arme '), el('input', { type: 'number', min: 0, value: as.enchants || 0, onchange: e => { as.enchants = +e.target.value; recalc(); } }))));
    m.append(el('div', { class: 'card' }, el('h3', {}, 'Notes de build'), el('textarea', { rows: 4, style: 'width:100%', oninput: e => { S.notes = e.target.value; save(); } }, S.notes || '')));
  }

  /* ---------- Arbre ---------- */
  function renderTree(m) {
    const used = S.nodes.length, max = D.skilltree.config.maxPoints;
    const bar = el('div', { class: 'treebar' },
      el('b', {}, 'Points :'), el('span', { id: 'ptsused' }, used + ' utilisés'),
      el('label', { class: 'mut small' }, 'dispo ', el('input', { type: 'number', min: 0, max, value: S.points, onchange: e => { S.points = +e.target.value; renderTree2(); save(); } })),
      el('div', { class: 'meter' }, el('i', { id: 'ptsbar', style: `width:${Math.min(100, used / S.points * 100)}%` })),
      el('input', { placeholder: 'Chercher un talent…', oninput: e => { treeView.search = e.target.value; treeView.draw(); } }),
      el('button', { onclick: () => { S.nodes = []; recalc(); treeView.draw(); updatePts(); } }, 'Tout retirer'),
      el('button', { onclick: () => { treeView.scale = 1.3; treeView.ox = treeView.w / 2; treeView.oy = treeView.h / 2; treeView.draw(); } }, 'Recentrer'),
      ...['alchemist', 'blacksmith', 'cook', 'enchanter', 'hunter', 'miner'].map(c => el('button', { class: 'sm', onclick: () => treeView.focus(c + '_class') }, ({ alchemist: 'Alchimiste', blacksmith: 'Forgeron', cook: 'Cuisinier', enchanter: 'Enchanteur', hunter: 'Chasseur', miner: 'Mineur' })[c])));
    m.append(bar);
    m.append(el('div', { id: 'xpcost', class: 'small mut', style: 'margin:0 0 6px' }));
    m.append(el('div', { class: 'note small' }, 'Clic = alloue un nœud (ou tout le chemin jusqu\'à lui) · clic sur un nœud alloué ou clic droit = le retire · molette = zoom · glisser = déplacer. Les points viennent surtout des parchemins de sagesse des quêtes (≈74 dans le livre de quêtes).'));
    const wrap = el('div', { id: 'treewrap' }, el('canvas', { id: 'treecv' })); m.append(wrap);
    m.append(el('div', { class: 'card', style: 'margin-top:14px' }, el('h3', {}, 'Bonus apportés par l\'arbre'), el('div', { id: 'treesum' })));
    treeView = new TreeView($('#treecv'), S, (orph) => { recalc(); updatePts(); treeView.draw(); renderTreeSum(); });
    window.TREEVIEW = treeView;
    setTimeout(() => { treeView.resize(); if (S.nodes.length) { const n = IDX.TREE.byId[S.nodes[0]]; treeView.focus(S.nodes[0]); } }, 30);
    updatePts(); renderTreeSum();
  }
  function renderTree2() { updatePts(); }
  const xpCost = (a, b) => { let t = 0; for (let l = a; l < b; l++) t += Math.floor(15 + Math.floor(19985 * l / 115)); return t; }; // Config.getSkillPointCost : 15 + (20000-15)*niveau/115
  function updatePts() {
    const xc = $('#xpcost'); if (xc) { const used = S.nodes.length, sc = Math.min(used, S.scrolls ?? 74); xc.innerHTML = ''; xc.append(`Coût en XP pour obtenir ces ${used} points : `, el('b', {}, fnum(xpCost(sc, used), 0)), ` points d'expérience (en supposant ${sc} points gagnés par les parchemins de sagesse des quêtes ; il y en a ${74} dans le livre). `, el('input', { type: 'number', min: 0, max: 100, value: S.scrolls ?? 74, style: 'width:60px', title: 'Parchemins déjà utilisés', onchange: e => { S.scrolls = +e.target.value; updatePts(); save(); } })); }
    const used = S.nodes.length; const u = $('#ptsused'); if (u) { u.textContent = used + ' utilisés'; u.className = used > S.points ? 'bad' : ''; }
    const b = $('#ptsbar'); if (b) b.style.width = Math.min(100, used / Math.max(1, S.points) * 100) + '%';
  }
  function renderTreeSum() {
    const box = $('#treesum'); if (!box) return;
    const agg = {};
    S.nodes.forEach(id => { const n = IDX.TREE.byId[id]; n.effects.forEach(e => { agg[e] = (agg[e] || 0) + 1; }); });
    const keys = Object.keys(agg).sort(); box.innerHTML = '';
    if (!keys.length) { box.append(el('span', { class: 'mut' }, 'Aucun talent alloué.')); return; }
    const notables = S.nodes.map(id => IDX.TREE.byId[id]).filter(n => n.tier === 'notable' || n.tier === 'keystone');
    if (notables.length) box.append(el('div', {}, ...notables.map(n => el('span', { class: 'chip', title: n.effects.join('\n'), style: n.tier === 'keystone' ? 'border-color:var(--acc);color:var(--acc)' : '' }, n.name))));
    box.append(el('table', { class: 't' }, ...keys.map(k => el('tr', {}, el('td', {}, k), el('td', { class: 'mut' }, agg[k] > 1 ? '× ' + agg[k] : '')))));
  }

  /* ---------- Équipement ---------- */
  const TYPE_NAME = t => TF[t] || t;
  function gemOptionsFor(type) {
    const opts = [];
    for (const g of D.gems.apotheosis) if (g.bonuses.some(b => b.types.includes(type))) opts.push(g);
    return opts;
  }
  function gemBonusTexts(g, type, rar) {
    if (g.skilltree) return g.bonuses.filter(b => b.types.includes(type)).map(b => b.text);
    return g.bonuses.filter(b => b.types.includes(type)).map(b => (b.text && b.text[rar]) || '').filter(Boolean);
  }
  function gearItem(id) { return S.gear[id]; }
  function ensureItem(sd) {
    if (!S.gear[sd.id]) S.gear[sd.id] = { type: sd.types[0], rarity: 'epic', affixes: [], gems: [], base: {}, extra: [], name: '' };
    return S.gear[sd.id];
  }
  function renderGear(m) {
    const left = el('div', { class: 'card slots' }, el('h3', {}, 'Emplacements'));
    SLOT_DEFS.forEach(sd => {
      const it = S.gear[sd.id];
      left.append(el('button', { class: 'slotbtn' + (slotSel === sd.id ? ' on' : '') + (sd.needs && ENG.treeSocketInfo(S).rings < 1 ? ' mut' : ''), style: it ? `border-left-color:${RA[it.rarity].color}` : '', onclick: () => { slotSel = sd.id; render(); } },
        el('span', {}, sd.name), el('span', { class: 'mut small' }, it ? ((it.name ? it.name + ' · ' : '') + rname(it.rarity) + ' · ' + it.affixes.length + ' aff.') : '—')));
    });
    left.append(el('div', { class: 'row' }, el('button', { class: 'sm', onclick: () => { if (confirm('Vider tout l\'équipement ?')) { S.gear = {}; recalc(); render(); } } }, 'Tout vider')));
    const sd = SLOT_DEFS.find(s => s.id === slotSel);
    m.append(el('div', { style: 'display:grid;grid-template-columns:230px minmax(0,1fr);gap:14px' }, left, el('div', {}, renderSlotEditor(sd))));
  }
  function renderSlotEditor(sd) {
    const it = S.gear[sd.id];
    const card = el('div', { class: 'card' }, el('h3', {}, sd.name));
    if (!it) { card.append(el('p', { class: 'mut' }, 'Emplacement vide.'), el('button', { class: 'pri', onclick: () => { ensureItem(sd); render(); recalc(); } }, 'Équiper un objet')); return card; }
    const type = it.type || sd.types[0]; const rar = it.rarity;
    const upd = () => { recalc(); render(); };
    card.append(el('div', { class: 'row' }, el('label', {}, 'Nom'), el('input', { value: it.name || '', placeholder: 'ex. Épée de Gabin', oninput: e => { it.name = e.target.value; save(); } }),
      el('button', { class: 'sm', onclick: () => { delete S.gear[sd.id]; upd(); } }, 'Retirer')));
    if (sd.types.length > 1) card.append(el('div', { class: 'row' }, el('label', {}, 'Type'), el('select', { onchange: e => { it.type = e.target.value; it.affixes = []; it.gems = []; upd(); } }, ...sd.types.map(t => el('option', { value: t, selected: t === type }, TYPE_NAME(t))))));
    card.append(el('div', { class: 'row' }, el('label', {}, 'Rareté'), el('select', { class: 'rar-' + rar, onchange: e => { it.rarity = e.target.value; clampItem(it); upd(); } },
      ...RO.map(r => el('option', { value: r, selected: r === rar, class: 'rar-' + r }, rname(r)))),
      el('span', { class: 'mut small' }, `${RA[rar].stat} stats · ${RA[rar].ability} capacités · ${RA[rar].sockets} sockets max`)));
    // base
    const nb = (key, label, step = 0.1) => el('span', {}, el('label', { class: 'mut small' }, label + ' '), el('input', { type: 'number', step, value: it.base[key] ?? '', oninput: e => { it.base[key] = e.target.value === '' ? '' : +e.target.value; recalc(); } }), ' ');
    const baseRow = el('div', { class: 'row' }, el('label', {}, 'Base'));
    if (sd.weapon) baseRow.append(nb('dmg', 'Dégâts (affichés)', 0.5), nb('spd', 'Vitesse', 0.05));
    if (sd.armor) baseRow.append(nb('armor', 'Armure', 0.5), nb('tough', 'Robustesse', 0.5), nb('kb', 'Rés. recul', 0.1));
    const presets = (D.presets && D.presets[type]) || [];
    if (presets.length) baseRow.append(el('select', { onchange: e => { const p = presets[+e.target.value]; if (p) { it.base = Object.assign({}, p.base); if (!it.name) it.name = p.name; upd(); } } },
      el('option', { value: '' }, '— base prédéfinie —'), ...presets.map((p, i) => el('option', { value: i }, p.name))));
    card.append(baseRow);
    if (!sd.weapon && !sd.armor) card.append(el('div', { class: 'mut small' }, 'Les objets d\'accessoire n\'ont pas de stat de base : tout vient des affixes, des gemmes et de leur propre effet (voir « Stats supplémentaires »).'));
    // affixes
    clampItem(it);
    const slotsOf = k => RA[rar][k];
    for (const kind of ['stat', 'ability']) {
      const label = kind === 'stat' ? 'Affixes de statistique' : 'Affixes de capacité (effets spéciaux)';
      card.append(el('div', { class: 'sec' }, label + ` (${it.affixes.filter(a => (IDX.AFFIX[a.id] || {}).slot === kind).length}/${slotsOf(kind)})`));
      const cand = D.affixes.filter(a => a.slot === kind && a.types.includes(type) && a.rarities.includes(rar));
      it.affixes.forEach((af, idx) => {
        const a = IDX.AFFIX[af.id]; if (!a || a.slot !== kind) return;
        const rangeable = a.kind === 'attribute' && a.values[rar] && typeof a.values[rar] === 'object';
        const t = a.text[rar];
        card.append(el('div', { class: 'affrow' }, el('div', {}, el('b', {}, a.name), el('div', { class: 'txt' }, rangeable ? ENG_TEXT(a, rar, af.roll ?? 1) : t), a.suffix ? el('div', { class: 'mut small' }, a.suffix) : null),
          el('div', { class: 'row' }, rangeable ? el('input', { type: 'range', min: 0, max: 1, step: 0.05, value: af.roll ?? 1, title: 'Roll : position entre le min et le max', oninput: e => { af.roll = +e.target.value; recalc(); e.target.closest('.affrow').querySelector('.txt').textContent = ENG_TEXT(a, rar, af.roll); } }) : null,
            el('button', { class: 'sm', onclick: () => { it.affixes.splice(idx, 1); upd(); } }, '✕'))));
      });
      if (it.affixes.filter(a => (IDX.AFFIX[a.id] || {}).slot === kind).length < slotsOf(kind)) {
        const have = new Set(it.affixes.map(a => a.id));
        const sel = el('select', { onchange: e => { if (e.target.value) { it.affixes.push({ id: e.target.value, roll: 1 }); upd(); } } },
          el('option', { value: '' }, `+ ajouter (${cand.filter(a => !have.has(a.id)).length} dispo)`),
          ...cand.filter(a => !have.has(a.id)).sort((a, b) => a.name.localeCompare(b.name)).map(a => el('option', { value: a.id }, affixLabel(a, rar).slice(0, 110))));
        card.append(el('div', { class: 'row' }, sel));
      }
    }
    // sockets / gemmes
    const xs = ENG.extraSockets(S, sd, it); const maxS = RA[rar].sockets + xs;
    card.append(el('div', { class: 'sec' }, `Sockets & gemmes (${it.gems.length}/${maxS}${xs ? ' dont +' + xs + ' via l\'arbre de talents' : ''})`));
    const gopts = gemOptionsFor(type);
    const stOpts = D.gems.skilltree.filter(g => g.bonuses.some(b => b.types.includes(type)));
    it.gems.forEach((g, idx) => {
      const gem = g.id && IDX.GEM[g.id];
      const sel = el('select', { onchange: e => { g.id = e.target.value; if (g.id.startsWith('skilltree:')) g.rar = IDX.GEM[g.id].rarity; upd(); } },
        el('option', { value: '' }, '— gemme —'),
        el('optgroup', { label: 'Gemmes Apotheosis' }, ...gopts.sort((a, b) => a.name.localeCompare(b.name)).map(x => el('option', { value: x.id, selected: g.id === x.id }, x.name))),
        el('optgroup', { label: 'Gemmes de l\'arbre' }, ...stOpts.map(x => el('option', { value: x.id, selected: g.id === x.id }, x.name + ' (' + rname(x.rarity) + ')'))));
      const rsel = (gem && !gem.skilltree) ? el('select', { onchange: e => { g.rar = e.target.value; upd(); } }, ...RO.map(r => el('option', { value: r, selected: (g.rar || 'common') === r }, rname(r)))) : null;
      card.append(el('div', { class: 'affrow' }, el('div', {}, sel, rsel, gem ? el('div', { class: 'txt' }, ...gemBonusTexts(gem, type, g.rar || 'common').map(t => el('div', {}, '◆ ' + t))) : null),
        el('button', { class: 'sm', onclick: () => { it.gems.splice(idx, 1); upd(); } }, '✕')));
    });
    if (it.gems.length < Math.max(maxS, 1)) card.append(el('div', { class: 'row' }, el('button', { class: 'sm', onclick: () => { it.gems.push({ id: '', rar: 'epic' }); upd(); } }, '+ socket')));
    else card.append(el('div', { class: 'row' }, el('button', { class: 'sm', onclick: () => { it.gems.push({ id: '', rar: 'epic' }); upd(); } }, '+ socket supplémentaire (compétence/reforge)')));
    // enchantements
    card.append(el('div', { class: 'sec' }, 'Enchantements (niveau max du pack indiqué ; tu peux dépasser avec l\'enchantement apothique)'));
    (it.ench = it.ench || []).forEach((en, idx) => { const E = D.enchants.find(x => x.id === en.id) || { name: en.id, desc: '' };
      card.append(el('div', { class: 'affrow' }, el('div', {}, el('b', {}, E.name), el('div', { class: 'txt' }, E.desc)), el('div', { class: 'row' }, el('input', { type: 'number', min: 1, max: 255, value: en.lvl, style: 'width:64px', onchange: e => { en.lvl = +e.target.value || 1; recalc(); } }), el('span', { class: 'mut small' }, '/' + (E.max || '?')), el('button', { class: 'sm', onclick: () => { it.ench.splice(idx, 1); upd(); } }, '✕')))); });
    const have = new Set(it.ench.map(x => x.id));
    const esel = el('select', { onchange: e => { if (e.target.value) { const E = D.enchants.find(x => x.id === e.target.value); it.ench.push({ id: E.id, lvl: E.max || 1 }); upd(); } } }, el('option', { value: '' }, '+ ajouter un enchantement'),
      ...['Arme', 'Arc', 'Arbalète', 'Trident', 'Armure', 'Outil', 'Canne à pêche', 'Tous', 'Autre', 'Malédiction'].map(cat => el('optgroup', { label: cat }, ...D.enchants.filter(x => x.cat === cat && !have.has(x.id)).sort((a, b) => a.name.localeCompare(b.name)).map(x => el('option', { value: x.id }, x.name + (x.max ? ' (max ' + x.max + ')' : ''))))));
    card.append(el('div', { class: 'row' }, esel));
    // extra
    card.append(el('div', { class: 'sec' }, 'Stats supplémentaires (effet propre à l\'objet, enchantements…)'));
    (it.extra = it.extra || []).forEach((x, idx) => card.append(manualRow(x, () => { it.extra.splice(idx, 1); upd(); })));
    card.append(el('button', { class: 'sm', onclick: () => { it.extra.push({ attr: 'minecraft:generic.max_health', op: 0, val: 0 }); upd(); } }, '+ stat'));
    return card;
  }
  function ENG_TEXT(a, rar, roll) {
    const v = ENG.rollVal(a.values[rar], roll); const pctv = a.op !== 0 || D.attrs[a.attr]?.pct;
    const nm = (D.attrs[ENG.norm(a.attr)] || D.attrs[a.attr] || { name: a.attr }).name;
    return (v >= 0 ? '+' : '−') + (pctv ? fnum(Math.abs(v) * 100, 1) + ' %' : fnum(Math.abs(v))) + (a.op === 2 ? ' (mult.)' : '') + ' ' + nm;
  }
  function clampItem(it) {
    const rar = it.rarity; const type = it.type;
    it.affixes = it.affixes.filter(a => { const x = IDX.AFFIX[a.id]; return x && x.rarities.includes(rar); });
    ['stat', 'ability'].forEach(k => { let n = 0; it.affixes = it.affixes.filter(a => { if (IDX.AFFIX[a.id].slot !== k) return true; return ++n <= RA[rar][k]; }); });
  }
  function manualRow(x, onDel) {
    const attrs = Object.keys(D.attrs).filter(k => !k.startsWith('generic.')).sort((a, b) => D.attrs[a].name.localeCompare(D.attrs[b].name));
    return el('div', { class: 'row' }, el('select', { onchange: e => { x.attr = e.target.value; recalc(); } }, ...attrs.map(a => el('option', { value: a, selected: ENG.norm(x.attr) === a }, D.attrs[a].name))),
      el('select', { onchange: e => { x.op = +e.target.value; recalc(); } }, el('option', { value: 0, selected: x.op === 0 }, 'ajoute (+)'), el('option', { value: 1, selected: x.op === 1 }, '× base'), el('option', { value: 2, selected: x.op === 2 }, '× total')),
      el('input', { type: 'number', step: 'any', value: x.val, oninput: e => { x.val = +e.target.value; recalc(); } }),
      el('span', { class: 'mut small' }, 'valeur brute (0,10 = 10 % pour les %)'), el('button', { class: 'sm', onclick: onDel }, '✕'));
  }

  const implausible = i => (/Portée|Rayon/.test(i.label) && i.val > 64) || (/Durée/.test(i.label) && i.val > 600) || (/esquiv|Cibles/.test(i.label) && i.val > 50);
  /* ---------- Sorts ---------- */
  let IC = { byId: {}, byName: {} };
  fetch('../data/icons.json').then(r => r.json()).then(d => { IC = d; if (window.POB && POB.rerender) POB.rerender(); }).catch(() => {});
  const icn = name => { const f = IC.byId[IC.byName[name]]; return f ? el('img', { src: '../assets/icons/' + f, class: 'ic', alt: '' }) : ''; };
  const SCH = D.spells.schools;
  const spn = x => x.fr && x.fr !== x.name ? x.fr + ' (' + x.name + ')' : x.name;
  let spellFilter = '';
  function renderSpells(m) {
    const at = R.at;
    m.append(el('div', { class: 'note' }, 'Les formules (dégâts, mana, recharge) sont lues dans le code d\'Iron\'s Spells ; les multiplicateurs de puissance et niveaux max sont ceux de TA config (Cisco les a modifiés : plusieurs sorts sont bridés à ×0,2–0,3, certains à presque rien). Les sorts d\'Ars Nouveau (glyphes) ne sont pas modélisés ici.'));
    const chosen = el('div', { class: 'card' }, el('h3', {}, 'Mes sorts'));
    if (!S.spells.length) chosen.append(el('p', { class: 'mut' }, 'Aucun sort choisi — ajoute-en depuis la liste ci-dessous.'));
    const tbl = el('table', { class: 't' }, el('tr', {}, ...['Sort', 'École', 'Niv.', 'Puissance', 'Mana', 'Incantation', 'Recharge', 'Effets', ''].map(h => el('th', {}, h))));
    S.spells.forEach((s, i) => {
      const sp = D.spells.spells.find(x => x.id === s.id); if (!sp) return;
      const c = ENG.spellCalc(sp, s.level, at);
      tbl.append(el('tr', {}, el('td', {}, icn(sp.name), el('b', {}, spn(sp)), sp.cfg.enabled ? '' : el('span', { class: 'bad' }, ' (désactivé)')), el('td', {}, SCH[sp.cfg.school] || sp.cfg.school),
        el('td', {}, el('input', { type: 'number', min: 1, max: sp.cfg.maxLevel, value: s.level, style: 'width:60px', onchange: e => { s.level = Math.max(1, Math.min(sp.cfg.maxLevel, +e.target.value)); recalc(); render(); } }), el('span', { class: 'mut small' }, ' /' + sp.cfg.maxLevel)),
        el('td', {}, fnum(c.sp, 3)), el('td', {}, c.mana), el('td', {}, c.cast ? fnum(c.cast, 1) + ' s' : 'instant'), el('td', {}, fnum(c.cd, 1) + ' s'),
        el('td', { class: 'small' }, ...c.info.map(i => el('div', {}, i.label + ' : ', i.val == null ? el('span', { class: 'mut' }, 'voir en jeu') : implausible(i) ? el('span', { class: 'mut', title: 'La formule donne ' + fnum(i.val, 0) + ' : le jeu borne probablement cette valeur' }, 'très élevé (borné ?)') : el('b', {}, fnum(i.val, 1) + (i.unit || ''))))),
        el('td', {}, el('button', { class: 'sm', onclick: () => { S.spells.splice(i, 1); recalc(); render(); } }, '✕'))));
    });
    chosen.append(tbl);
    chosen.append(el('div', { class: 'mut small' }, `Mana max ${fnum(R.mana, 0)} · régénération ≈ ${fnum(R.manaPerSec, 1)} mana/s · puissance de sorts globale ×${fnum(R.spellPower, 2)}.`));
    m.append(chosen);
    const f = el('input', { placeholder: 'Filtrer…', value: spellFilter, oninput: e => { spellFilter = e.target.value; renderSpellList(); } });
    m.append(el('div', { class: 'card' }, el('h3', {}, 'Tous les sorts d\'Iron\'s Spells'), f, el('div', { id: 'spelllist', style: 'margin-top:8px' })));
    renderSpellList();
  }
  function renderSpellList() {
    const box = $('#spelllist'); if (!box) return; box.innerHTML = '';
    const q = spellFilter.toLowerCase();
    const list = D.spells.spells.filter(s => !q || (s.name + ' ' + (s.fr || '') + ' ' + (SCH[s.cfg.school] || '')).toLowerCase().includes(q)).sort((a, b) => a.cfg.school.localeCompare(b.cfg.school) || a.name.localeCompare(b.name));
    const t = el('table', { class: 't' }, el('tr', {}, ...['Sort', 'École', 'Rareté min.', 'Niv. max', 'Puiss. ×', 'Recharge', ''].map(h => el('th', {}, h))));
    list.forEach(s => t.append(el('tr', {}, el('td', {}, icn(s.name), el('b', {}, spn(s)), el('div', { class: 'mut small' }, s.guide)), el('td', {}, SCH[s.cfg.school] || s.cfg.school), el('td', {}, D.spells.rarities[s.cfg.minRarity] || s.cfg.minRarity), el('td', {}, s.cfg.maxLevel),
      el('td', { class: s.cfg.powerMult < 0.5 ? 'bad' : '' }, s.cfg.powerMult), el('td', {}, s.cfg.cooldown + ' s'), el('td', {}, el('button', { class: 'sm', onclick: () => { if (!S.spells.find(x => x.id === s.id)) { S.spells.push({ id: s.id, level: s.cfg.maxLevel }); recalc(); render(); } } }, '+ ajouter')))));
    box.append(t);
  }


  /* ---------- Simulateur de boss ---------- */
  let bossSel = 'cataclysm:ignis', bossDist = 1500, bossWl = 0, bossWhich = 'avg';
  const TIER_ORDER = ['Palier 1', 'Gardien', 'Palier 2', 'Palier 3', 'Histoire', 'Twilight Forest', 'Aether', 'Blue Skies', 'Sept péchés'];
  function verdict(r) {
    if (!isFinite(r.ttk)) return ['—', 'mut'];
    if (r.ttk < 90 && r.hitsToDie > 8) return ['Facile', 'good'];
    if (r.ttk < 240 && r.hitsToDie > 4) return ['Jouable', 'good'];
    if (r.ttk < 600 && r.hitsToDie > 2) return ['Difficile', ''];
    return ['Très dur', 'bad'];
  }
  function fmtT(t) { if (!isFinite(t)) return '∞'; if (t < 120) return fnum(t, 0) + ' s'; return fnum(t / 60, 1) + ' min'; }
  function renderBoss(m) {
    m.append(el('div', { class: 'warn' }, 'Estimation de ligne de base : niveau du boss = niveau de départ + distance × niveaux/bloc + bonus aléatoire (+ niveau du monde). Stats × (1 + coefficient × niveau). Ne compte pas les phases, l\'infernal, ni les sorts d\'invocation. À utiliser pour comparer des builds, pas comme une promesse.'));
    const sel = el('select', { onchange: e => { bossSel = e.target.value; render(); } },
      ...TIER_ORDER.map(t => el('optgroup', { label: t }, ...D.bosses.filter(b => b.tier === t).map(b => el('option', { value: b.id, selected: b.id === bossSel }, b.name)))));
    const ctr = el('div', { class: 'card' }, el('div', { class: 'row' }, el('label', {}, 'Boss'), sel),
      el('div', { class: 'row' }, el('label', {}, 'Distance du spawn'), el('input', { type: 'number', min: 0, step: 250, value: bossDist, onchange: e => { bossDist = +e.target.value || 0; render(); } }), el('span', { class: 'mut small' }, 'blocs (le niveau monte avec la distance)')),
      el('div', { class: 'row' }, el('label', {}, 'Niveau du monde'), el('select', { onchange: e => { bossWl = +e.target.value; render(); } },
        ...[[0, 'Normal'], [150, 'Ascendant (Azure) +150'], [300, 'Divin +300'], [500, 'Hellheim +500']].map(([v, n]) => el('option', { value: v, selected: bossWl === v }, n))),
        el('select', { onchange: e => { bossWhich = e.target.value; render(); } }, ...[['min', 'niveau mini'], ['avg', 'niveau moyen'], ['max', 'niveau maxi']].map(([v, n]) => el('option', { value: v, selected: bossWhich === v }, n)))));
    const asx = S.assume = S.assume || {};
    ctr.append(el('div', { class: 'row' }, el('label', { title: 'Majrusz\'s Progressive Difficulty : normal au début, expert dès qu\'un joueur change de dimension, maître après la mort de l\'Ender Dragon' }, 'Palier de difficulté du monde'),
      el('select', { onchange: e => { asx.stage = e.target.value; render(); } }, ...[['', 'Automatique (maître si « Dragon vaincu »)'], ['normal', 'Normal'], ['expert', 'Expert : +15 % vie, +10 % dégâts'], ['master', 'Maître : +30 % vie, +20 % dégâts']].map(([v, n]) => el('option', { value: v, selected: (asx.stage || '') === v }, n))),
      el('label', { class: 'small', title: 'Progressive Bosses : chaque Wither invoqué après un autre est plus dur (jusqu\'à 8)' }, ' Difficulté du Wither (0 à 8) '), el('input', { type: 'number', min: 0, max: 8, value: asx.pbDiff || 0, style: 'width:60px', onchange: e => { asx.pbDiff = Math.max(0, Math.min(8, +e.target.value || 0)); render(); } })));
    m.append(ctr);
    const b = D.bosses.find(x => x.id === bossSel); const r = ENG.bossSim(R, S, b, bossDist, bossWl, bossWhich);
    const card = el('div', { class: 'card' }, el('h3', {}, b.name, el('span', { class: 'tag' }, b.tier), el('span', { class: 'tag' }, b.mod)), el('div', { class: 'small mut' }, b.dim + ' — ' + b.how));
    const row = (k, v, cls) => el('div', { class: 'stat ' + (cls || '') }, el('span', {}, k), el('b', {}, v));
    const [vt, vc] = verdict(r);
    card.append(el('div', { class: 'grid2' }, el('div', {},
      el('div', { class: 'sec' }, 'Le boss'), row('Niveau', r.L), row('PV', b.hp ? fnum(r.hp, 0) : '?'), row('Dégâts par coup', b.dmg ? fnum(r.dmg, 1) : '?'), row('Armure', fnum(r.armor, 1) + (r.tough ? ' · rob. ' + r.tough : '')),
      b.cap ? row('Plafond de dégâts par coup', b.cap, 'hl') : null),
      el('div', {}, el('div', { class: 'sec' }, 'Ton build'),
        row('Coup avant armure', fnum(r.raw, 1)), row('Réduction par son armure', pct(r.red, 0)), row('Coup effectif (crit. inclus)', fnum(r.perHit, 1) + (r.capped ? ' (plafonné)' : '')),
        row('DPS armes', fnum(r.dps, 1)), r.spellBest ? row('Meilleur sort : ' + r.spellBest.name, fnum(r.spellBest.dps, 1) + ' /s') : null,
        row('Temps pour le tuer', fmtT(r.ttk), 'hl'), row('Ce qu\'il t\'inflige (après armure)', fnum(r.takes, 1) + ' → ' + (isFinite(r.hitsToDie) ? fnum(r.hitsToDie, 1) + ' coups pour te tuer' : '—')),
        el('div', { class: 'stat' }, el('span', {}, 'Verdict'), el('b', { class: vc }, vt)))));
    card.append(el('ul', { class: 'small' }, ...b.notes.map(n => el('li', {}, n))));
    m.append(card);
    const t = el('table', { class: 't' }, el('tr', {}, ...['Boss', 'Palier', 'Niv.', 'PV', 'Plafond', 'Temps pour le tuer', 'Coups pour te tuer', 'Verdict'].map(h => el('th', {}, h))));
    D.bosses.slice().sort((a, b) => a.tier.localeCompare(b.tier) || ((a.level || { start: 0 }).start - (b.level || { start: 0 }).start)).forEach(bb => {
      const rr = ENG.bossSim(R, S, bb, bossDist, bossWl, bossWhich); const [v, c] = verdict(rr);
      t.append(el('tr', { style: 'cursor:pointer', onclick: () => { bossSel = bb.id; render(); } }, el('td', {}, bb.name), el('td', { class: 'mut small' }, bb.tier), el('td', {}, rr.L), el('td', {}, bb.hp ? fnum(rr.hp, 0) : '?'), el('td', {}, bb.cap || '—'),
        el('td', {}, bb.hp ? fmtT(rr.ttk) : '?'), el('td', {}, bb.dmg && isFinite(rr.hitsToDie) ? fnum(rr.hitsToDie, 1) : '—'), el('td', { class: c }, bb.hp ? v : '?')));
    });
    m.append(el('div', { class: 'card' }, el('h3', {}, 'Tous les boss avec ton build'), t));
  }

  /* ---------- Stats détaillées ---------- */
  function renderStats(m) {
    m.append(el('div', { class: 'card' }, el('h3', {}, 'Toutes les statistiques (valeurs finales)'), el('div', { class: 'small mut' }, 'Formule : (base + Σ ajouts) × (1 + Σ « × base ») × Π(1 + « × total »), puis plafonds du pack.'), (() => {
      const t = el('table', { class: 't' }, el('tr', {}, el('th', {}, 'Attribut'), el('th', {}, 'Base'), el('th', {}, 'Final'), el('th', {}, 'Sources')));
      const ids = Object.keys(R.at).filter(id => { const mm = R.mods.filter(x => x.attr === id); return mm.length; }).sort((a, b) => (D.attrs[a]?.name || a).localeCompare(D.attrs[b]?.name || b));
      ids.forEach(id => {
        const info = D.attrs[id] || D.attrs[id.replace('minecraft:', '')] || { name: id, base: 0, pct: false };
        const fmt = v => info.pct ? pct(v, 1) : fnum(v, 2);
        const src = {}; R.mods.filter(x => x.attr === id).forEach(x => { const k = x.src; (src[k] = src[k] || []).push(x); });
        t.append(el('tr', {}, el('td', {}, info.name), el('td', { class: 'mut' }, fmt(info.base)), el('td', {}, el('b', {}, fmt(R.at[id]))),
          el('td', { class: 'small' }, Object.entries(src).map(([k, v]) => k + ' (' + v.map(x => (x.op === 0 ? '+' : x.op === 1 ? '×b ' : '×t ') + fnum(x.val, 3)).join(', ') + ')').join(' · '))));
      });
      return t;
    })()));
    if (R.texts.length) m.append(el('div', { class: 'card' }, el('h3', {}, 'Effets non chiffrés / conditionnels'), el('div', { class: 'small mut' }, 'Pris en compte à la main : ils dépendent d\'une situation (arme en main, PV bas, cible en feu…).'),
      el('ul', { class: 'small' }, ...R.texts.map(x => el('li', {}, el('span', { class: 'mut' }, x.src + ' — '), x.text)))));
  }

  /* ---------- Builds prêts ---------- */
  let bf = { level: '', tag: '', q: '', cat: '' };
  const CATS = (window.BUILD_CATS || { cats: [] }).cats;
  const catOf = b => (CATS.find(c => c.builds.includes(b.arch || b.id)) || { id: 'autre' }).id;
  const LEVELS = [['debutant', 'Débutant'], ['milieu', 'Intermédiaire'], ['fin', 'Avancé'], ['optimise', 'Optimisé (BiS)']];
  function renderBuilds(m) {
    m.append(el('div', { class: 'note' }, 'Builds « théoriques » construits à partir des données du pack. Choisis un niveau selon ton avancement, charge-le puis ajuste selon ton loot. Le guide complet de chaque build est dans le wiki.'));
    const all = window.BUILDS_IDX || [];
    const tags = [...new Set(all.flatMap(b => b.tags || []).filter(t => !LEVELS.some(l => l[1] === t)))].sort();
    const q = el('input', { placeholder: 'Rechercher un build…', value: bf.q, oninput: e => { bf.q = e.target.value; draw(); } });
    const ls = el('select', { onchange: e => { bf.level = e.target.value; draw(); } }, el('option', { value: '' }, 'Tous les niveaux'), ...LEVELS.map(([v, n]) => el('option', { value: v, selected: bf.level === v }, n)));
    const ts = el('select', { onchange: e => { bf.tag = e.target.value; draw(); } }, el('option', { value: '' }, 'Tous les rôles'), ...tags.map(t => el('option', { value: t, selected: bf.tag === t }, t)));
    const cs = el('select', { onchange: e => { bf.cat = e.target.value; draw(); } }, el('option', { value: '' }, 'Toutes les catégories'), ...CATS.map(c => el('option', { value: c.id, selected: bf.cat === c.id }, c.icon + ' ' + c.title)));
    m.append(el('div', { class: 'row' }, q, cs, ls, ts, el('span', { class: 'mut small', id: 'bcount' })));
    const box = el('div', { id: 'blist' }); m.append(box);
    function draw() {
      box.innerHTML = '';
      const rows = all.filter(b => (!bf.cat || catOf(b) === bf.cat) && (!bf.level || b.level === bf.level) && (!bf.tag || (b.tags || []).includes(bf.tag)) && (!bf.q || (b.title + ' ' + b.summary + ' ' + (b.tags || []).join(' ')).toLowerCase().includes(bf.q.toLowerCase())));
      $('#bcount').textContent = rows.length + ' / ' + all.length + ' builds';
      const order = {}; LEVELS.forEach(([v], i) => order[v] = i);
      const corder = {}; CATS.forEach((c, i) => corder[c.id] = i);
      let lastCat = null;
      rows.sort((a, b) => (corder[catOf(a)] ?? 99) - (corder[catOf(b)] ?? 99) || (CATS.find(c => c.id === catOf(a)) || { builds: [] }).builds.indexOf(a.arch || a.id) - (CATS.find(c => c.id === catOf(b)) || { builds: [] }).builds.indexOf(b.arch || b.id) || (order[a.level] ?? 9) - (order[b.level] ?? 9)).forEach(b => { const ck = catOf(b); if (ck !== lastCat) { lastCat = ck; const c = CATS.find(x => x.id === ck); if (c) box.append(el('div', { style: 'margin:14px 0 4px' }, el('h2', { style: 'margin:0' }, c.icon + ' ' + c.title), el('div', { class: 'small mut' }, c.desc))); } box.append(el('div', { class: 'build' }, el('div', {}, el('h3', {}, b.title), el('div', { class: 'small' }, b.summary), el('div', {}, ...(b.tags || []).map(t => el('span', { class: 'chip' }, t)))),
        el('div', {}, el('button', { class: 'pri', onclick: async () => { loadState(b, await stateOf(b)); recalc(); tab = 'perso'; render(); } }, 'Charger'), el('button', { class: 'sm', title: 'Charger ce build et voir son arbre de talents', onclick: async () => { loadState(b, await stateOf(b)); recalc(); tab = 'arbre'; render(); } }, 'Voir l\'arbre'), b.wiki ? el('div', {}, el('a', { href: '../wiki/' + b.wiki }, 'Guide complet →')) : null))); });
      if (!rows.length) box.append(el('p', { class: 'mut' }, 'Aucun build ne correspond.'));
    }
    draw();
  }

  /* ---------- Fiche (colonne de droite) ---------- */
  function renderSheet() {
    const box = $('#sheet'); if (!box || !R) return; box.innerHTML = '';
    const row = (k, v, hl) => el('div', { class: 'stat' + (hl ? ' hl' : '') }, el('span', {}, k), el('b', {}, v));
    const hit = S.hit || 30;
    box.append(el('h3', {}, S.name || 'Build'));
    const wr = ENG.restrictions(S); if (wr.length) box.append(el('div', { class: 'warn small' }, el('b', {}, '⚠ Restriction d\'origine'), ...wr.map(x => el('div', {}, x))));
    box.append(el('div', { class: 'sec' }, 'Défense'));
    box.append(row('Vie max', fnum(R.hp, 0), 1), row('Armure', fnum(R.armor, 1) + (R.tough ? ' · rob. ' + fnum(R.tough, 1) : '')));
    const red = R.armorReduction(hit);
    box.append(el('div', { class: 'stat' }, el('span', {}, 'Réduction vs coup de ', el('input', { type: 'number', value: hit, style: 'width:56px;padding:0 4px', onchange: e => { S.hit = +e.target.value || 30; renderSheet(); save(); } })), el('b', {}, pct(red, 1))));
    box.append(row('Vie effective (vs ce coup)', fnum(R.hp / Math.max(0.01, 1 - red) / R.taken, 0), 1));
    if (R.taken !== 1) box.append(row('Dégâts subis (origine)', '×' + fnum(R.taken, 2)));
    box.append(row('Esquive (points → %)', fnum(R.evasion, 1) + ' → ' + pct(R.evasionChance, 1)));
    if (R.dodgeAttr) box.append(row('Esquive Apotheosis', pct(R.dodgeAttr, 1)));
    if (R.blocking) box.append(row('Blocage (bouclier)', fnum(R.blocking, 1) + ' → ' + pct(R.blockChance, 1)));
    if (R.regen) box.append(row('Régénération', fnum(R.regen, 2) + ' PV/s'));
    box.append(el('div', { class: 'sec' }, 'Attaque (mêlée / distance)'));
    if (R.ranged) box.append(row('Flèche (base × attributs)', fnum(R.arrowHit, 1), 1), row('Cadence de tir', fnum(R.drawSpeed, 2) + ' /s'));
    else box.append(row('Dégâts par coup (base)', fnum(R.hit, 1) + (R.dmgPct ? ' (+' + pct(R.dmgPct, 0) + ' dégâts)' : ''), 1), row('Vitesse d\'attaque', fnum(R.spd, 2)));
    const base = R.ranged ? R.arrowHit : R.hit;
    box.append(row('Critique', pct(R.critC, 1) + ' × ' + fnum(R.critD, 2)), row('Multiplicateur de crit. moyen', '×' + fnum(R.critE, 2)), row('Dégâts moyens avec crit.', fnum(base * R.critE, 1), 1), row('DPS estimé', fnum(R.dps, 1), 1));
    if (R.fire || R.cold) box.append(row('Dégâts élémentaires', (R.fire ? '🔥' + fnum(R.fire) : '') + ' ' + (R.cold ? '❄' + fnum(R.cold) : '')));
    if (R.lifesteal) box.append(row('Vol de vie', pct(R.lifesteal, 1)));
    if (R.activeMult > 1) box.append(row('Attaque avec capacité active', '×' + fnum(R.activeMult, 1) + ((S.assume || {}).active ? ' (appliquée)' : ' (case à cocher)')));
    if (R.divine > 0) box.append(row('Divinité par coup', fnum(R.divine, 0) + ' · crit. ' + pct(R.divCrit[0], 0) + ' ×' + R.divCrit[1]));
    if (R.fell > 0) box.append(row('Corruption par coup', fnum(R.fell, 0) + ' · ' + pct(R.fellProc[0], 0) + ' : +' + pct(R.fellProc[1], 0) + ' PV max cible'));
    if (R.flatTrue) box.append(row('Dégâts réels par coup (arme)', '+' + fnum(R.flatTrue, 1)));
    if (R.truePct) box.append(row('Dégâts réels (% PV max cible)', pct(R.truePct, 1)));
    if (R.hitCap) box.append(row('Plafond des coups reçus', pct(R.hitCap, 0) + ' des PV max'));
    if (!R.hasWeapon) box.append(el('div', { class: 'warn small' }, 'Aucune arme équipée : mets tes dégâts/vitesse de base dans « Équipement → Arme principale ».'));
    box.append(el('div', { class: 'sec' }, 'Magie'));
    box.append(row('Mana max', fnum(R.mana, 0), 1), row('Régénération', fnum(R.manaPerSec, 1) + ' /s'), row('Puissance de sorts', '×' + fnum(R.spellPower, 2)));
    const schools = Object.keys(SCH).map(s => [s, R.at['irons_spellbooks:' + s + '_spell_power']]).filter(([s, v]) => Math.abs(v - 1) > 1e-6);
    schools.forEach(([s, v]) => box.append(row('· ' + SCH[s], '×' + fnum(v, 2))));
    const cdr = R.at['irons_spellbooks:cooldown_reduction'], ctr = R.at['irons_spellbooks:cast_time_reduction'];
    if (cdr !== 1) box.append(row('Réduction de recharge', 'recharge × ' + fnum(2 - ENG.softCap(cdr), 2)));
    if (ctr !== 1) box.append(row('Vitesse d\'incantation', 'temps × ' + fnum(2 - ENG.softCap(ctr), 2)));
    box.append(el('div', { class: 'sec' }, 'Divers'));
    const mv = R.at['minecraft:generic.movement_speed']; box.append(row('Vitesse de déplacement', pct(mv / 0.1, 0)), row('Chance', fnum(R.at['minecraft:generic.luck'], 1)));
    const hl = R.at['apotheosis:healing_received']; if (hl !== 1) box.append(row('Soins reçus', pct(hl, 0)));
    const xp = R.at['skilltree:exp_per_minute']; if (xp) box.append(row('XP passive', fnum(xp, 1) + ' /min'));
    box.append(el('div', { class: 'sec' }, 'Talents'));
    box.append(row('Points utilisés', S.nodes.length + ' / ' + S.points));
  }

  /* ---------- barre du haut ---------- */
  function init() {
    $('#bname').value = S.name; $('#bname').addEventListener('input', e => { S.name = e.target.value; save(); renderSheet(); });
    $('#btnShare').addEventListener('click', async () => { const l = shareLink(); try { await navigator.clipboard.writeText(l); toast('Lien copié ! Colle-le à tes amis.'); } catch (e) { prompt('Copie ce lien :', l); } history.replaceState(null, '', '#' + l.split('#')[1]); });
    $('#btnNew').addEventListener('click', () => { if (confirm('Repartir d\'un build vierge ?')) { S = DEFAULT(); $('#bname').value = S.name; history.replaceState(null, '', location.pathname); recalc(); render(); } });
    $('#btnExport').addEventListener('click', () => { const b = new Blob([JSON.stringify(S, null, 1)], { type: 'application/json' }); const a = el('a', { href: URL.createObjectURL(b), download: (S.name || 'build').replace(/[^\w-]+/g, '_') + '.json' }); a.click(); });
    $('#fileImport').addEventListener('change', e => { const f = e.target.files[0]; if (!f) return; f.text().then(t => { S = Object.assign(DEFAULT(), JSON.parse(t)); $('#bname').value = S.name; recalc(); render(); }); });
    recalc(); render();
  }
  function toast(t) { const d = el('div', { class: 'tip', style: 'left:50%;top:70px;transform:translateX(-50%);border-color:var(--good)' }, t); document.body.append(d); setTimeout(() => d.remove(), 2200); }
  window.POB = { init, getState: () => S, rerender: () => { if (tab === 'sorts' || tab === 'builds') render(); } };
  document.addEventListener('DOMContentLoaded', init);
})();
