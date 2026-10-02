(function () {
  const $ = (s, r = document) => r.querySelector(s);
  // menu mobile
  const nb = $('#navbtn'); if (nb) nb.addEventListener('click', () => $('#wnav').classList.toggle('open'));
  // recherche
  const q = $('#q'), res = $('#results');
  function norm(s) { return s.toLowerCase().normalize('NFD').replace(/[̀-ͯ]/g, ''); }
  if (q && window.SEARCH) {
    const idx = window.SEARCH.map(p => ({ p, t: norm(p.t), x: norm(p.x) }));
    q.addEventListener('input', () => {
      const v = norm(q.value.trim()); if (v.length < 2) { res.classList.add('hidden'); return; }
      const words = v.split(/\s+/);
      const hits = idx.map(e => { let s = 0; for (const w of words) { if (e.t.includes(w)) s += 5; if (e.x.includes(w)) s += 1; else if (!e.t.includes(w)) return null; } return { e, s }; }).filter(Boolean).sort((a, b) => b.s - a.s).slice(0, 12);
      res.innerHTML = hits.length ? hits.map(h => { const i = h.e.x.indexOf(words[0]); const sn = h.e.p.x.slice(Math.max(0, i - 40), i + 80); return `<a href="${window.ROOT}${h.e.p.u}">${h.e.p.t}<small>…${sn}…</small></a>`; }).join('') : '<a>Aucun résultat</a>';
      res.classList.remove('hidden');
    });
    document.addEventListener('click', e => { if (!res.contains(e.target) && e.target !== q) res.classList.add('hidden'); });
  }
  // filtres de listes
  document.querySelectorAll('.filter').forEach(inp => inp.addEventListener('input', () => {
    const v = norm(inp.value); const box = document.getElementById(inp.dataset.target);
    box.querySelectorAll('[data-q]').forEach(e => { e.style.display = norm(e.dataset.q).includes(v) ? '' : 'none'; });
  }));
  // calculateur de danger (page « Le monde »)
  const calc = $('#dangercalc');
  if (calc) {
    const AL = { hp: 0.09, dmg: 0.14, armor: 0.08 };
    const f = x => Math.round(x * 10) / 10;
    function upd() {
      const dist = +$('#dc-dist').value || 0, y = +$('#dc-y').value || 64, wl = +$('#dc-wl').value || 0;
      const lpd = 0.008, lpdeep = 0.01;
      const L = 1 + Math.floor(lpd * dist) + (y < 64 ? Math.floor(lpdeep * (64 - y)) : 0) + wl;
      $('#dc-out').innerHTML = `Niveau d'un monstre ordinaire de l'Overworld : <b>${L}</b><br>PV ×<b>${f(1 + AL.hp * L)}</b> · Dégâts ×<b>${f(1 + AL.dmg * L)}</b> · Armure ×<b>${f(1 + AL.armor * L)}</b><br>Exemple : un zombie (20 PV, 3 dégâts) → <b>${f(20 * (1 + AL.hp * L))} PV</b> et <b>${f(3 * (1 + AL.dmg * L))} dégâts</b> par coup (avant ton armure).`;
    }
    calc.querySelectorAll('input,select').forEach(e => e.addEventListener('input', upd)); upd();
  }
})();
