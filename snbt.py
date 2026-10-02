import re
class P:
    def __init__(s,t): s.t=t; s.i=0
    def ws(s):
        t=s.t
        while s.i<len(t) and (t[s.i] in ' \t\r\n,'): s.i+=1
    def val(s):
        s.ws(); t=s.t; c=t[s.i]
        if c=='{': return s.obj()
        if c=='[': return s.arr()
        if c=='"' or c=="'": return s.string(c)
        m=re.compile(r'[^\s,\]\}:]+').match(t,s.i); w=m.group(0); s.i=m.end()
        if w in('true','false'): return w=='true'
        mm=re.fullmatch(r'(-?\d+(?:\.\d+)?)([bBsSlLfFdD]?)',w)
        if mm:
            n=mm.group(1); return float(n) if ('.' in n or mm.group(2).lower() in 'fd') else int(n)
        return w
    def string(s,q):
        t=s.t; s.i+=1; out=[]
        while t[s.i]!=q:
            if t[s.i]=='\\': s.i+=1; out.append(t[s.i])
            else: out.append(t[s.i])
            s.i+=1
        s.i+=1; return ''.join(out)
    def key(s):
        s.ws(); t=s.t
        if t[s.i] in '"\'': return s.string(t[s.i])
        m=re.compile(r'[^\s:]+').match(t,s.i); s.i=m.end(); return m.group(0)
    def obj(s):
        s.i+=1; d={}
        while True:
            s.ws()
            if s.t[s.i]=='}': s.i+=1; return d
            k=s.key(); s.ws(); assert s.t[s.i]==':'; s.i+=1
            d[k]=s.val()
    def arr(s):
        s.i+=1; a=[]
        # typed arrays [I; 1, 2]
        s.ws()
        m=re.compile(r'[BIL];').match(s.t,s.i)
        if m: s.i=m.end()
        while True:
            s.ws()
            if s.t[s.i]==']': s.i+=1; return a
            a.append(s.val())
def loads(t): return P(t).val() if not t.lstrip().startswith(('{','[')) is False else P(t).val()
def parse_file(p):
    t=open(p,encoding='utf8').read()
    return P(t).val()
