import zipfile, os, json, glob, re
MODS = '/home/nagamo/Documents/Dev/Minecraft/minecraft/mods'
idx = json.load(open('out/mods_index.json'))
n = 0
for o in idx:
    mid = o.get('modid') or o['file']
    z = zipfile.ZipFile(os.path.join(MODS, o['file']))
    for name in z.namelist():
        if name.endswith('/'): continue
        if (name.startswith('data/') and name.endswith(('.json', '.mcfunction', '.nbt'))) or name.endswith('en_us.json') or name.endswith('fr_fr.json'):
            if '/patchouli_books/' in name or '/structures/' in name or name.endswith('.nbt'): continue
            dst = os.path.join('extract', mid, name)
            os.makedirs(os.path.dirname(dst), exist_ok=True)
            open(dst, 'wb').write(z.read(name)); n += 1
print(n, 'fichiers extraits')
# langue anglaise fusionnée (sans codes couleur)
import jl
L = {}
for f in glob.glob('extract/*/assets/*/lang/en_us.json'):
    try: L.update(jl.load(f))
    except Exception as e: print('ERR', f, e)
L = {k: (re.sub(r'§.', '', v) if isinstance(v, str) else v) for k, v in L.items()}
json.dump(L, open('out/lang_en.json', 'w'), ensure_ascii=False)
print(len(L), 'clés de langue')
