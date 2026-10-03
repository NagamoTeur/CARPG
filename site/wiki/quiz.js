/* Quiz « Quel build pour moi ? » : on note chaque build selon les réponses */
(function () {
  const box = document.getElementById('quiz'); if (!box) return;
  const Q = [
    { k: 'style', t: 'Qu\'est-ce que tu as envie de faire en combat ?', o: [['magie', '🔮 Lancer des sorts'], ['distance', '🏹 Tirer de loin'], ['melee', '⚔ Frapper au corps à corps'], ['soutien', '✨ Soigner et aider les autres'], ['farm', '💰 Récolter, explorer, farmer']] },
    { k: 'role', t: 'Quel rôle dans le groupe ?', o: [['degats', 'Faire des dégâts'], ['tank', 'Encaisser pour les autres'], ['soutien', 'Soutenir (soins, invocations)'], ['any', 'Peu importe, le plus simple']] },
    { k: 'group', t: 'Tu joues comment ?', o: [['solo', 'Souvent seul'], ['nohealer', 'En groupe, sans soigneur'], ['healer', 'En groupe, avec un soigneur']] },
    { k: 'exp', t: 'Ton expérience des modpacks RPG ?', o: [[1, 'Débutant : je découvre'], [2, 'Moyen : je connais les bases'], [3, 'Confirmé : la difficulté ne me fait pas peur']] },
    { k: 'risk', t: 'Ton rapport au risque ?', o: [[1, 'Prudent : je veux survivre'], [2, 'Équilibré'], [3, 'Casse-cou : gros dégâts, gros risque']] },
    { k: 'leg', t: 'As-tu déjà (ou veux-tu viser) une arme légendaire ou l\'Anneau des Sept Malédictions ?', o: [[0, 'Non, pas pour l\'instant'], [1, 'Oui, montre-moi aussi ceux-là']] }
  ];
  let A = {}, i = 0, B = [];
  fetch((window.ROOT || '../') + 'data/quiz.json').then(r => r.json()).then(d => { B = d; show(); });
  const esc = s => String(s).replace(/[&<>]/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;' }[c]));
  function show() {
    if (i >= Q.length) return result();
    const q = Q[i];
    box.innerHTML = '<div class="small mut">Question ' + (i + 1) + ' / ' + Q.length + '</div><h3>' + q.t + '</h3><div class="qopts">' + q.o.map(o => '<button class="qopt" data-v="' + o[0] + '">' + o[1] + '</button>').join('') + '</div>' + (i ? '<p><button class="sm" id="qback">← Retour</button></p>' : '');
    box.querySelectorAll('.qopt').forEach(b => b.onclick = () => { const v = b.dataset.v; A[q.k] = isNaN(v) ? v : +v; i++; show(); });
    const bk = box.querySelector('#qback'); if (bk) bk.onclick = () => { i--; show(); };
  }
  function score(b) {
    if ((b.leg || b.cat === 'maudit') && !A.leg) return -99;
    let s = 0;
    if (b.style.includes(A.style)) s += 6;
    if (A.style === 'magie' && b.style.includes('soutien')) s += 1;
    if (A.role === 'any') s += b.diff === 1 ? 2 : 0; else if (b.role.includes(A.role)) s += 3;
    if (A.group === 'solo') s += (b.tags.some(t => /Vol de vie|Régénération|Tank|Hybride|Autonomie|Compagnons/.test(t)) ? 2 : 0) - (b.role.includes('soutien') && !b.role.includes('degats') ? 3 : 0);
    if (A.group === 'nohealer') s += b.tags.some(t => /Vol de vie|Régénération|Tank|Soigneur|Soutien/.test(t)) ? 2 : 0;
    if (A.group === 'healer') s += b.risk >= 2 ? 1 : 0;
    s -= Math.max(0, b.diff - A.exp) * 3;
    s -= Math.abs(b.risk - A.risk) * 1.5;
    return s;
  }
  function result() {
    const top = B.map(b => [score(b), b]).sort((a, b) => b[0] - a[0]).slice(0, 3);
    box.innerHTML = '<h3>Tes 3 builds</h3>' + top.map(([s, b], n) => '<div class="card"><h3>' + (n + 1) + '. <a href="builds/' + b.id + '.html">' + esc(b.title) + '</a> <span class="chip">' + esc(b.catTitle) + '</span></h3>'
      + '<p>' + esc(b.pitch) + '</p>'
      + '<p class="small"><b>Pour commencer :</b> ' + (b.gear ? esc(b.gear) : 'voir le guide') + '. Difficulté : ' + '★'.repeat(b.diff) + '☆'.repeat(3 - b.diff) + ' · Risque : ' + '●'.repeat(b.risk) + '○'.repeat(3 - b.risk) + '</p>'
      + '<p><a class="btn" href="builds/' + b.id + '.html">Lire le guide</a> <a class="btn" href="../pob/index.html">Ouvrir le planificateur</a></p></div>').join('')
      + '<p><button class="sm" id="qagain">Recommencer</button> <span class="mut small">Chaque guide existe en 4 niveaux : commence par « Débutant ».</span></p>';
    box.querySelector('#qagain').onclick = () => { A = {}; i = 0; show(); };
  }
})();
