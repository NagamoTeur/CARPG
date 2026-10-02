/* Arbre de talents interactif (canvas) */
(function () {
  const TIER_COL = { lesser: '#7d8a9c', notable: '#58a6ff', keystone: '#e0a63a', class: '#5bd75b', gateway: '#c36bff' };
  const imgs = {};
  function img(name) {
    if (!imgs[name]) { const i = new Image(); i.src = '../assets/skilltree/textures/icons/' + name + '.png'; i.onload = () => window.TREEVIEW && TREEVIEW.draw(); imgs[name] = i; }
    return imgs[name];
  }
  const nodes = D.skilltree.nodes, byId = IDX.TREE.byId;

  class TreeView {
    constructor(cv, S, onChange) {
      this.cv = cv; this.S = S; this.onChange = onChange; this.ctx = cv.getContext('2d');
      this.scale = 1.3; this.ox = 0; this.oy = 0; this.hover = null; this.search = ''; this.path = null;
      this.tip = document.createElement('div'); this.tip.className = 'tip hidden'; document.body.appendChild(this.tip);
      this.bind(); this.resize();
    }
    get alloc() { return new Set(this.S.nodes); }
    resize() {
      const r = this.cv.getBoundingClientRect(); const dpr = window.devicePixelRatio || 1;
      this.cv.width = r.width * dpr; this.cv.height = r.height * dpr; this.w = r.width; this.h = r.height; this.dpr = dpr;
      if (!this._init) { this.ox = this.w / 2; this.oy = this.h / 2; this._init = true; }
      this.draw();
    }
    toScreen(n) { return [n.x * this.scale + this.ox, n.y * this.scale + this.oy]; }
    bind() {
      const cv = this.cv; let drag = null, moved = 0;
      cv.addEventListener('pointerdown', e => { drag = { x: e.clientX, y: e.clientY, ox: this.ox, oy: this.oy }; moved = 0; cv.setPointerCapture(e.pointerId); cv.classList.add('drag'); });
      cv.addEventListener('pointermove', e => {
        if (drag) { const dx = e.clientX - drag.x, dy = e.clientY - drag.y; moved = Math.max(moved, Math.abs(dx) + Math.abs(dy)); this.ox = drag.ox + dx; this.oy = drag.oy + dy; this.draw(); this.showTip(null); return; }
        const n = this.pick(e); if (n !== this.hover) { this.hover = n; this.path = n ? this.findPath(n) : null; this.draw(); }
        this.showTip(n, e);
      });
      cv.addEventListener('pointerup', e => { cv.classList.remove('drag'); if (drag && moved < 5) this.click(this.pick(e), e); drag = null; });
      cv.addEventListener('pointerleave', () => { this.hover = null; this.path = null; this.showTip(null); this.draw(); });
      cv.addEventListener('contextmenu', e => { e.preventDefault(); const n = this.pick(e); if (n) this.deallocate(n.id); });
      cv.addEventListener('wheel', e => {
        e.preventDefault(); const r = cv.getBoundingClientRect(); const mx = e.clientX - r.left, my = e.clientY - r.top;
        const f = Math.exp(-e.deltaY * 0.0015); const ns = Math.min(4, Math.max(0.35, this.scale * f)); const k = ns / this.scale;
        this.ox = mx - (mx - this.ox) * k; this.oy = my - (my - this.oy) * k; this.scale = ns; this.draw();
      }, { passive: false });
      window.addEventListener('resize', () => this.resize());
    }
    pick(e) {
      const r = this.cv.getBoundingClientRect(); const mx = e.clientX - r.left, my = e.clientY - r.top;
      let best = null, bd = 1e9;
      for (const n of nodes) { const [x, y] = this.toScreen(n); const d = Math.hypot(x - mx, y - my); const rad = Math.max(9, n.size * this.scale * 0.55); if (d < rad && d < bd) { best = n; bd = d; } }
      return best;
    }
    neighbors(id) { return byId[id].adj; }
    canAllocate(id, A) {
      if (A.has(id)) return false;
      if (A.size === 0) return byId[id].start;
      for (const j of A) if (byId[j].adj.includes(id)) return true;
      return false;
    }
    /* plus court chemin depuis le réseau actuel (ou depuis une classe de départ) jusqu'à la cible */
    findPath(target) {
      const A = this.alloc; if (A.has(target.id) || this.canAllocate(target.id, A)) return null;
      const prev = {}, q = [];
      const starts = A.size ? [...A] : nodes.filter(n => n.start).map(n => n.id);
      starts.forEach(s => { prev[s] = null; q.push(s); });
      while (q.length) {
        const c = q.shift(); if (c === target.id) break;
        for (const nb of byId[c].adj) if (!(nb in prev)) { prev[nb] = c; q.push(nb); }
      }
      if (!(target.id in prev)) return null;
      const p = []; for (let c = target.id; c !== null && !A.has(c); c = prev[c]) p.push(c);
      return p.reverse();
    }
    allocate(id) {
      const A = this.alloc; const n = byId[id];
      if (this.canAllocate(id, A)) { this.S.nodes.push(id); }
      else { const p = this.findPath(n); if (!p) return; p.forEach(x => this.S.nodes.push(x)); }
      this.onChange();
    }
    deallocate(id) {
      const A = this.alloc; if (!A.has(id)) return;
      A.delete(id);
      // tout nœud restant doit être joignable depuis un point de départ alloué (ou, à défaut, depuis un nœud quelconque pour le tout premier)
      const starts = [...A].filter(x => byId[x].start);
      const seen = new Set(starts), q = [...starts];
      while (q.length) { const c = q.shift(); for (const nb of byId[c].adj) if (A.has(nb) && !seen.has(nb)) { seen.add(nb); q.push(nb); } }
      const orphans = [...A].filter(x => !seen.has(x));
      if (A.size && starts.length === 0) return; // il doit rester une classe de départ
      this.S.nodes = [...A].filter(x => seen.has(x));
      this.onChange(orphans.length ? orphans.length : 0);
    }
    click(n) { if (!n) return; if (this.S.nodes.includes(n.id)) this.deallocate(n.id); else this.allocate(n.id); }
    showTip(n, e) {
      const t = this.tip; if (!n) { t.classList.add('hidden'); return; }
      const A = this.alloc; const p = (!A.has(n.id) && !this.canAllocate(n.id, A)) ? this.findPath(n) : null;
      t.innerHTML = `<h4>${n.name}</h4><div class="c">${({ lesser: 'Nœud mineur', notable: 'Nœud notable', keystone: 'Clé de voûte', class: 'Départ de classe', gateway: 'Passerelle' })[n.tier]}</div>` +
        n.effects.map(x => `<div class="e">• ${x}</div>`).join('') + (n.desc ? `<div class="c">${n.desc}</div>` : '') +
        (A.has(n.id) ? '<div class="c">Cliquer pour retirer</div>' : p ? `<div class="c">Clic : alloue le chemin (${p.length} points)</div>` : '<div class="c">Clic : allouer (1 point)</div>');
      t.classList.remove('hidden');
      const w = 340; let x = e.clientX + 16, y = e.clientY + 12; if (x + w > innerWidth) x = e.clientX - w - 10; if (y + 200 > innerHeight) y = Math.max(8, innerHeight - 220);
      t.style.left = x + 'px'; t.style.top = y + 'px';
    }
    focus(id) { const n = byId[id]; if (!n) return; this.scale = 2.2; this.ox = this.w / 2 - n.x * this.scale; this.oy = this.h / 2 - n.y * this.scale; this.draw(); }
    draw() {
      const c = this.ctx, A = this.alloc, dpr = this.dpr; c.setTransform(dpr, 0, 0, dpr, 0, 0); c.clearRect(0, 0, this.w, this.h);
      const pathSet = new Set(this.path || []); const q = this.search.trim().toLowerCase();
      c.lineCap = 'round';
      // liens
      for (const n of nodes) {
        const [x1, y1] = this.toScreen(n);
        for (const o of n.links.concat(n.long, n.oneway)) {
          const m = byId[o]; if (!m) continue; if (n.oneway.includes(o) === false && o < n.id && (m.links.includes(n.id) || m.long.includes(n.id))) continue;
          const [x2, y2] = this.toScreen(m);
          const a = A.has(n.id), b = A.has(m.id); const inPath = pathSet.has(n.id) && (pathSet.has(m.id) || A.has(m.id)) || pathSet.has(m.id) && A.has(n.id);
          c.strokeStyle = a && b ? '#e0a63a' : inPath ? '#ffe08a' : (a || b) ? '#3f6aa5' : '#2a3140'; c.lineWidth = (a && b ? 3 : 1.6) * Math.min(1.5, this.scale);
          if (n.long.includes(o)) c.setLineDash([6, 5]); else c.setLineDash([]);
          c.beginPath(); c.moveTo(x1, y1); c.lineTo(x2, y2); c.stroke();
        }
      }
      c.setLineDash([]);
      for (const n of nodes) {
        const [x, y] = this.toScreen(n); if (x < -40 || y < -40 || x > this.w + 40 || y > this.h + 40) continue;
        const r = Math.max(5, n.size * this.scale * 0.5);
        const on = A.has(n.id), can = !on && this.canAllocate(n.id, A), inPath = pathSet.has(n.id);
        const hit = q && (n.name.toLowerCase().includes(q) || n.effects.join(' ').toLowerCase().includes(q));
        c.beginPath();
        if (n.tier === 'keystone' || n.tier === 'gateway') { // losange
          c.moveTo(x, y - r * 1.15); c.lineTo(x + r * 1.15, y); c.lineTo(x, y + r * 1.15); c.lineTo(x - r * 1.15, y); c.closePath();
        } else c.arc(x, y, r, 0, 7);
        c.fillStyle = on ? '#3a2d12' : '#141821'; c.fill();
        c.lineWidth = on ? 3 : can ? 2.4 : 1.6; c.strokeStyle = on ? '#e0a63a' : inPath ? '#ffe08a' : can ? '#7fc4ff' : TIER_COL[n.tier] + (hit ? '' : '99');
        if (hit) { c.shadowColor = '#fff'; c.shadowBlur = 14; } c.stroke(); c.shadowBlur = 0;
        const im = img(n.icon); if (im.complete && im.naturalWidth) {
          const s = r * 1.35; c.imageSmoothingEnabled = false; c.globalAlpha = on ? 1 : can ? .9 : .55; c.drawImage(im, x - s / 2, y - s / 2, s, s); c.globalAlpha = 1;
        }
        if (this.hover === n) { c.strokeStyle = '#fff'; c.lineWidth = 1.5; c.beginPath(); c.arc(x, y, r + 4, 0, 7); c.stroke(); }
        if (this.scale > 1.9 && (n.tier !== 'lesser' || this.scale > 2.6)) { c.fillStyle = '#cfd6e4'; c.font = '11px system-ui'; c.textAlign = 'center'; c.fillText(n.name, x, y + r + 13); }
      }
    }
  }
  window.TreeView = TreeView;
})();
