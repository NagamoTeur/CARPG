import os, json, zipfile, shutil
src='extract'; dst='merged'
shutil.rmtree(dst,ignore_errors=True)
log=[]
def put(path,data,origin):
    p=os.path.join(dst,path); os.makedirs(os.path.dirname(p),exist_ok=True)
    existed=os.path.exists(p); open(p,'wb').write(data)
    if existed and origin!='mod': log.append((path,origin))
# mods
for mid in os.listdir(src):
    for root,_,fs in os.walk(os.path.join(src,mid)):
        for f in fs:
            fp=os.path.join(root,f); rel=os.path.relpath(fp,os.path.join(src,mid))
            put(rel,open(fp,'rb').read(),'mod')
# paxi datapacks overlay
PX='/home/nagamo/Documents/Dev/Minecraft/minecraft/config/paxi/datapacks'
order=json.load(open('/home/nagamo/Documents/Dev/Minecraft/minecraft/config/paxi/datapack_load_order.json')) if os.path.exists('/home/nagamo/Documents/Dev/Minecraft/minecraft/config/paxi/datapack_load_order.json') else {}
print(order)
for name in sorted(os.listdir(PX)):
    p=os.path.join(PX,name)
    if os.path.isdir(p):
        for root,_,fs in os.walk(p):
            for f in fs:
                fp=os.path.join(root,f); rel=os.path.relpath(fp,p)
                if rel.startswith('data/') and rel.endswith('.json'): put(rel,open(fp,'rb').read(),name)
    elif name.endswith('.zip'):
        z=zipfile.ZipFile(p)
        for n in z.namelist():
            if n.startswith('data/') and n.endswith('.json'): put(n,z.read(n),name)
json.dump(log,open('out/overrides.json','w'),indent=1)
print('overrides',len(log))
import collections; print(collections.Counter(o for _,o in log))
