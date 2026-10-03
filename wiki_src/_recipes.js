/* Navigateur de recettes : recherche par objet, filtre par mod */
(function () {
  const box = document.getElementById('recipes'); if (!box) return;
  const q = box.querySelector('input'), sel = box.querySelector('select'), out = box.querySelector('.rlist'), cnt = box.querySelector('.rcount');
  let R = [], IC = {};
  fetch((window.ROOT || '../') + 'data/icons.json').then(r => r.json()).then(d => { IC = d.byId; if (R.length) draw(); }).catch(() => {});
  fetch((window.ROOT || '../') + 'data/recipes.json').then(r => r.json()).then(d => {
    R = d.recipes; const mods = [...new Set(R.map(r => r.mod))].sort();
    mods.forEach(m => { const o = document.createElement('option'); o.value = m; o.textContent = m + ' (' + R.filter(r => r.mod === m).length + ')'; sel.appendChild(o); });
    const hash = decodeURIComponent(location.hash.slice(1)); if (hash) q.value = hash;
    draw();
  });
  const TYPE = { crafting_shaped: 'Établi (forme)', crafting_shapeless: 'Établi (sans forme)', smelting: 'Four', blasting: 'Haut fourneau', smoking: 'Fumoir', campfire_cooking: 'Feu de camp', smithing: 'Table de forge', smithing_transform: 'Table de forge', stonecutting: 'Tailleur de pierre' };
  const esc = s => String(s).replace(/[&<>]/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;' }[c]));
  function draw() {
    const t = q.value.trim().toLowerCase(), m = sel.value;
    const rows = R.filter(r => (!m || r.mod === m) && (!t || (r.name + ' ' + (r.fr || '') + ' ' + r.out + ' ' + r.ing.map(i => i[0]).join(' ')).toLowerCase().includes(t)));
    cnt.textContent = rows.length + ' recette' + (rows.length > 1 ? 's' : '');
    out.innerHTML = rows.slice(0, 60).map(r => {
      const grid = r.grid ? '<table class="rgrid">' + r.grid.map(row => '<tr>' + row.map(c => '<td>' + esc(c) + '</td>').join('') + '</tr>').join('') + '</table>' : '';
      const title = r.fr && r.fr !== r.name ? esc(r.fr) + ' <span class="mut small">(' + esc(r.name) + ')</span>' : esc(r.name);
      const im = IC[r.out] ? '<img class="ic" src="' + (window.ROOT || '../') + 'assets/icons/' + IC[r.out] + '" alt=""> ' : '';
      return '<div class="card recipe"><div>' + im + '<b>' + title + '</b>' + (r.n > 1 ? ' ×' + r.n : '') + ' <span class="chip">' + esc(TYPE[r.type] || r.type) + '</span> <span class="chip">' + esc(r.mod) + '</span>' + (r.src !== 'mod' ? ' <span class="chip" style="background:#e0a63a;color:#111">' + esc(r.src === 'CraftTweaker' ? 'modifié par le serveur' : r.src) + '</span>' : '') + '</div>'
        + '<div class="small">' + r.ing.map(i => esc(i[0]) + (i[1] > 1 ? ' ×' + i[1] : '')).join(' · ') + '</div>' + grid + '</div>';
    }).join('') + (rows.length > 60 ? '<p class="mut small">… ' + (rows.length - 60) + ' autres : affine la recherche.</p>' : '');
  }
  q.addEventListener('input', draw); sel.addEventListener('change', draw);
})();
