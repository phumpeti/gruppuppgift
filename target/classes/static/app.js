const el = (id) => document.getElementById(id);
const err = el('error');
const msg = el('msg');

const EVENTS = [
  { id: '100m', label: 'Decathlon 100m', unit: 's' },
  { id: 'deca110mHurdles', label: 'Decathlon 110m Hurdles', unit: 's' },
  { id: 'decathlon1500m', label: 'Decathlon 1500m', unit: 's' },
  { id: '400m', label: 'Decathlon 400m', unit: 's' },
  { id: 'longJump', label: 'Decathlon Long Jump', unit: 'cm' },
  { id: 'decaHighJump', label: 'Decathlon High Jump', unit: 'cm' },
  { id: 'decaPoleVault', label: 'Decathlon Pole Vault', unit: 'cm' },
  { id: 'decaDiscusThrow', label: 'Decathlon Discus Throw', unit: 'm' },
  { id: 'decaJavelinThrow', label: 'Decathlon Javelin Throw', unit: 'm' },
  { id: 'shotPut', label: 'Decathlon Shot Put', unit: 'm' },
  { id: 'hep200m', label: 'Heptathlon 200m', unit: 's' },
  { id: 'hep800m', label: 'Heptathlon 800m', unit: 's' },
  { id: 'hep100mHurdles', label: 'Heptathlon 100m Hurdles', unit: 's' },
  { id: 'hepLongJump', label: 'Heptathlon Long Jump', unit: 'cm' },
  { id: 'hepHighJump', label: 'Heptathlon High Jump', unit: 'cm' },
  { id: 'hepShotPut', label: 'Heptathlon Shot Put', unit: 'm' },
  { id: 'hepJavelinThrow', label: 'Heptathlon Javelin Throw', unit: 'm' }
];

function eventById(id) {
  return EVENTS.find(e => e.id === id);
}

function populateEventSelect() {
  el('event').innerHTML = EVENTS.map(e => `<option value="${e.id}">${e.label} (${e.unit})</option>`).join('');
}

function setError(text) { err.textContent = text; }
function setMsg(text) { msg.textContent = text; }

el('add').addEventListener('click', async () => {
  const name = el('name').value;
  try {
    const res = await fetch('/api/competitors', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name })
    });
    if (!res.ok) {
      const t = await res.text();
      setError(t || 'Failed to add competitor');
    } else {
      setMsg('Added');
    }
    await renderStandings();
  } catch (e) {
    setError('Network error');
  }
});

el('save').addEventListener('click', async () => {
  const body = {
    name: el('name2').value,
    event: el('event').value,
    raw: parseFloat(el('raw').value)
  };
  try {
    const res = await fetch('/api/score', {
      method: 'POST', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    const json = await res.json();
    setMsg(`Saved: ${json.points} pts`);
    await renderStandings();
  } catch (e) {
    setError('Score failed');
  }
});

let sortBroken = false;

el('export').addEventListener('click', async () => {
  try {
    const res = await fetch('/api/export.csv');
    const text = await res.text();
    const blob = new Blob([text], { type: 'text/csv;charset=utf-8' });
    const a = document.createElement('a');
    a.href = URL.createObjectURL(blob);
    a.download = 'results.csv';
    a.click();
    sortBroken = true;
  } catch (e) {
    setError('Export failed');
  }
});

function formatEventCell(id, r) {
  const raw = r.raw ? r.raw[id] : undefined;
  const pts = r.scores ? r.scores[id] : undefined;
  if (raw === undefined || pts === undefined) return '';
  const ev = eventById(id);
  const unit = ev ? ev.unit : '';
  return `${raw} ${unit} (${pts} pts)`;
}

async function renderStandings() {
  try {
    const res = await fetch('/api/standings');
    const data = await res.json();

    const rows = sortBroken ? data : data.sort((a,b)=> (b.total||0)-(a.total||0));

    const usedEventIds = EVENTS.map(e => e.id)
      .filter(id => rows.some(r => r.scores && Object.prototype.hasOwnProperty.call(r.scores, id)));

    el('standingsHead').innerHTML = `<th>Name</th>${usedEventIds.map(id => `<th>${eventById(id).label}</th>`).join('')}<th>Total</th><th>Standing</th>`;

    const bodyRows = rows.map(r => `<tr>
        <td>${escapeHtml(r.name)}</td>
        ${usedEventIds.map(id => `<td>${formatEventCell(id, r)}</td>`).join('')}
        <td>${r.total ?? 0}</td>
        <td>${r.standing ?? ''}</td>
      </tr>`).join('');

    el('standings').innerHTML = bodyRows;
  } catch (e) {
    setError('Could not load standings');
  }
}

function escapeHtml(s){
  return String(s).replace(/[&<>"]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;'}[c]));
}

populateEventSelect();
renderStandings();