# -*- coding: utf-8 -*-
"""Plafonds d'attributs -> site/data/caps.json, lus à la source (plus de constantes recopiées) :
  - code décompilé : RangedAttribute d'Apotheosis, d'Iron's Spells, du Passive Skill Tree (valeurs min/max natives)
  - config/attributefix.json : plafonds du serveur (attributs activés), prioritaires.
Compare ensuite avec la table CAP d'origine du moteur et signale tout écart."""
import json, re, glob

caps = {}
# Apotheosis
for f in glob.glob('decomp/apotheosis_src/**/*.java', recursive=True):
    for m in re.finditer(r'new (?:Ranged|PercentBased)Attribute\("apotheosis:(\w+)",\s*([\d.E-]+),\s*([\d.E-]+),\s*([\d.E-]+)\)', open(f, encoding='utf8', errors='replace').read()):
        caps['apotheosis:' + m.group(1)] = [float(m.group(3)), float(m.group(4))]
# Iron's Spells
for f in glob.glob('decomp/irons_src/**/AttributeRegistry.java', recursive=True):
    for m in re.finditer(r'"(\w+)",\s*\(\)\s*->\s*new (?:Magic)?RangedAttribute\("[^"]+",\s*([\d.E-]+),\s*([\d.E-]+),\s*([\d.E-]+)\)', open(f, encoding='utf8', errors='replace').read()):
        caps['irons_spellbooks:' + m.group(1)] = [float(m.group(3)), float(m.group(4))]
# Passive Skill Tree
t = open('decomp/skilltree_src/daripher/skilltree/init/PSTAttributes.java', encoding='utf8', errors='replace').read()
for m in re.finditer(r'create\("(\w+)",\s*([\d.E-]+)\)', t): caps['skilltree:' + m.group(1)] = [0.0, float(m.group(2))]
# plafonds du serveur (AttributeFix)
af = json.load(open('../minecraft/config/attributefix.json'))['attributes']
srv = {k: [v['min']['value'], v['max']['value']] for k, v in af.items() if v['enabled']}
caps.update(srv)
json.dump(caps, open('site/data/caps.json', 'w'), indent=0)
print(len(caps), 'plafonds (dont', len(srv), 'du serveur)')
# comparaison avec la table codée en dur dans engine.js
src = open('site/pob/engine.js', encoding='utf8').read()
blk = re.search(r'const CAP = \{(.*?)\n  \};', src, re.S).group(1)
hard = {m.group(1): [float(m.group(2)), float(m.group(3))] for m in re.finditer(r"'([\w:.]+)': \[([\d.e-]+), ([\d.e-]+)\]", blk)}
diff = {k: (hard[k], caps.get(k)) for k in hard if caps.get(k) != hard[k]}
print('écarts avec la table du moteur :', diff or 'aucun')
