/* Visionneuse d'arbre de talents pour les guides de builds : tous les nœuds en gris, ceux du build en couleur */
(function () {
  const COL = { lesser: '#8aa0b8', notable: '#58a6ff', keystone: '#e0a63a', class: '#5bd75b', gateway: '#c36bff' };
  let TREE = null, loading = null;
  function load() {
    if (TREE) return Promise.resolve(TREE);
    if (!loading) loading = fetch((window.ROOT || '../../') + 'data/skilltree.json').then(r => r.json()).then(t => { t.by = {}; t.nodes.forEach(n => t.by[n.id] = n); return (TREE = t); });
    return loading;
  }
  function init(box) {
    const stages = JSON.parse(box.dataset.stages), order = JSON.parse(box.dataset.order), labels = JSON.parse(box.dataset.labels);
    const bar = box.querySelector('.tbar'), cv = box.querySelector('canvas'), info = box.querySelector('.tinfo'), tip = box.querySelector('.ttip');
    const ctx = cv.getContext('2d');
    let stage = order[order.length - 1], sc = 1, ox = 0, oy = 0, hover = null, W = 0, H = 0, drag = null;
    order.forEach(k => { const b = document.createElement('button'); b.textContent = labels[k]; b.dataset.k = k; b.onclick = () => { stage = k; fit(); }; bar.appendChild(b); });
    function sel() { return new Set(stages[stage] || []); }
    function resize() { const r = cv.getBoundingClientRect(), d = window.devicePixelRatio || 1; cv.width = r.width * d; cv.height = r.height * d; W = r.width; H = r.height; ctx.setTransform(d, 0, 0, d, 0, 0); }
    function fit() {
      resize(); const A = (stages[stage] || []).map(i => TREE.by[i]).filter(Boolean);
      const xs = A.map(n => n.x), ys = A.map(n => n.y);
      const x0 = Math.min(...xs) - 40, x1 = Math.max(...xs) + 40, y0 = Math.min(...ys) - 40, y1 = Math.max(...ys) + 40;
      sc = Math.min(W / (x1 - x0), H / (y1 - y0), 2.2); ox = W / 2 - sc * (x0 + x1) / 2; oy = H / 2 - sc * (y0 + y1) / 2; draw(); summary();
    }
    function summary() {
      [...bar.children].forEach(b => b.classList.toggle('on', b.dataset.k === stage));
      const A = (stages[stage] || []).map(i => TREE.by[i]).filter(Boolean);
      const ks = A.filter(n => n.tier === 'keystone'), nt = A.filter(n => n.tier === 'notable');
      info.innerHTML = '<b>' + A.length + ' points</b> · ' + nt.length + ' notables · ' + ks.length + ' clés de voûte' + (ks.length ? ' (' + ks.map(n => n.name).join(', ') + ')' : '');
    }
    function draw() {
      ctx.clearRect(0, 0, W, H); const S = sel(), T = TREE;
      ctx.lineWidth = 1; ctx.strokeStyle = 'rgba(128,140,160,.18)'; ctx.beginPath();
      T.nodes.forEach(n => (n.adj || n.links || []).forEach(l => { const m = T.by[l]; if (!m || m.id < n.id) return; ctx.moveTo(n.x * sc + ox, n.y * sc + oy); ctx.lineTo(m.x * sc + ox, m.y * sc + oy); })); ctx.stroke();
      ctx.lineWidth = 2.5; ctx.strokeStyle = '#e0a63a'; ctx.beginPath();
      T.nodes.forEach(n => { if (!S.has(n.id)) return; (n.adj || n.links || []).forEach(l => { if (S.has(l) && l > n.id) { const m = T.by[l]; ctx.moveTo(n.x * sc + ox, n.y * sc + oy); ctx.lineTo(m.x * sc + ox, m.y * sc + oy); } }); }); ctx.stroke();
      T.nodes.forEach(n => {
        const on = S.has(n.id), r = Math.max(2, (n.tier === 'lesser' ? 5 : n.tier === 'notable' ? 8 : 11) * Math.min(sc, 1.6));
        ctx.beginPath(); ctx.arc(n.x * sc + ox, n.y * sc + oy, on ? r : r * 0.7, 0, 7);
        ctx.fillStyle = on ? COL[n.tier] || '#fff' : 'rgba(128,140,160,.30)'; ctx.fill();
        if (on) { ctx.lineWidth = 1.5; ctx.strokeStyle = '#fff'; ctx.stroke(); }
      });
      if (sc > 0.9) { ctx.font = '11px sans-serif'; ctx.fillStyle = getComputedStyle(cv).color; T.nodes.forEach(n => { if (S.has(n.id) && (n.tier === 'keystone' || n.tier === 'notable')) ctx.fillText(n.name, n.x * sc + ox + 10, n.y * sc + oy + 4); }); }
    }
    function pick(e) { const r = cv.getBoundingClientRect(), mx = e.clientX - r.left, my = e.clientY - r.top; let b = null, bd = 14; TREE.nodes.forEach(n => { const d = Math.hypot(n.x * sc + ox - mx, n.y * sc + oy - my); if (d < bd) { bd = d; b = n; } }); return b; }
    cv.addEventListener('pointerdown', e => { drag = { x: e.clientX, y: e.clientY, ox, oy }; cv.setPointerCapture(e.pointerId); });
    cv.addEventListener('pointerup', () => drag = null);
    cv.addEventListener('pointermove', e => {
      if (drag) { ox = drag.ox + e.clientX - drag.x; oy = drag.oy + e.clientY - drag.y; draw(); return; }
      const n = pick(e); if (n === hover) return; hover = n;
      if (!n) { tip.hidden = true; return; }
      const r = box.getBoundingClientRect(); tip.hidden = false; tip.style.left = Math.min(e.clientX - r.left + 14, r.width - 260) + 'px'; tip.style.top = (e.clientY - r.top + 14) + 'px';
      tip.innerHTML = '<b>' + n.name + '</b>' + (sel().has(n.id) ? ' ✔' : '') + '<br>' + (n.effects || []).map(x => '<span>' + x + '</span>').join('<br>');
    });
    cv.addEventListener('pointerleave', () => { tip.hidden = true; hover = null; });
    cv.addEventListener('wheel', e => { e.preventDefault(); const r = cv.getBoundingClientRect(), mx = e.clientX - r.left, my = e.clientY - r.top, ns = Math.min(5, Math.max(.3, sc * Math.exp(-e.deltaY * .0015))), k = ns / sc; ox = mx - (mx - ox) * k; oy = my - (my - oy) * k; sc = ns; draw(); }, { passive: false });
    box.querySelector('.tfit').onclick = fit; window.addEventListener('resize', fit);
    load().then(fit);
  }
  document.querySelectorAll('.treebox').forEach(init);
})();
