import json,re
def loads(s):
    try: return json.loads(s)
    except Exception: pass
    s=s.lstrip('﻿')
    out=[];i=0;ins=False;esc=False
    while i<len(s):
        ch=s[i]
        if ins:
            out.append(ch)
            if esc: esc=False
            elif ch=='\\': esc=True
            elif ch=='"': ins=False
        else:
            if ch=='"': ins=True; out.append(ch)
            elif s.startswith('//',i): 
                j=s.find('\n',i); i=len(s) if j<0 else j; continue
            elif s.startswith('/*',i):
                j=s.find('*/',i); i=len(s) if j<0 else j+2; continue
            else: out.append(ch)
        i+=1
    t=''.join(out)
    t=re.sub(r',(\s*[}\]])',r'\1',t)
    return json.loads(t,strict=False)
def load(p): return loads(open(p,encoding='utf8',errors='replace').read())
