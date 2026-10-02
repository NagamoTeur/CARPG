import zipfile, os, re, json, tomllib, sys
MODS='/home/nagamo/Documents/Dev/Minecraft/minecraft/mods'
out=[]
for f in sorted(os.listdir(MODS)):
    if not f.endswith('.jar'): continue
    p=os.path.join(MODS,f)
    try: z=zipfile.ZipFile(p)
    except Exception as e: out.append({'file':f,'error':str(e)}); continue
    names=z.namelist()
    info={'file':f,'size':os.path.getsize(p)}
    for cand in ('META-INF/mods.toml',):
        if cand in names:
            try:
                t=tomllib.loads(z.read(cand).decode('utf8','ignore'))
                m=t.get('mods',[{}])[0]
                info.update(modid=m.get('modId'),name=m.get('displayName'),version=m.get('version'),desc=(m.get('description') or '').strip()[:300],
                  authors=t.get('authors') or m.get('authors'))
            except Exception as e:
                s=z.read(cand).decode('utf8','ignore')
                mm=re.search(r'modId\s*=\s*"([^"]+)"',s); info['modid']=mm and mm.group(1)
                mm=re.search(r'displayName\s*=\s*"([^"]+)"',s); info['name']=mm and mm.group(1)
    info['has_data']=sum(1 for n in names if n.startswith('data/') and n.endswith('.json'))
    info['classes']=sum(1 for n in names if n.endswith('.class'))
    info['namespaces']=sorted({n.split('/')[1] for n in names if n.startswith('data/') and n.count('/')>1})[:8]
    out.append(info)
json.dump(out,open('out/mods_index.json','w'),ensure_ascii=False,indent=1)
print(len(out)); print(sum(1 for o in out if not o.get('modid')))
for o in out:
    print(o.get('modid'),'|',o.get('name'),'|',o.get('version'),'|',o['has_data'])
