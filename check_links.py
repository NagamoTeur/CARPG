import os,re,glob,sys
root='site'
bad=[]
for f in glob.glob(root+'/**/*.html',recursive=True):
    s=open(f,encoding='utf8').read()
    for m in re.finditer(r'(?:href|src)="([^"#?]+)(?:[#?][^"]*)?"',s):
        u=m.group(1)
        if u.startswith(('http','mailto','javascript','data:')): continue
        p=os.path.normpath(os.path.join(os.path.dirname(f),u))
        if not os.path.exists(p): bad.append((f,u))
print(len(bad)); 
for b in sorted(set(bad))[:40]: print(b)
