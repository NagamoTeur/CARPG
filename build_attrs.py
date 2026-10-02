# -*- coding: utf-8 -*-
import json
from fr import ATTR_FR, PCT_ADD
BASE = {
 'minecraft:generic.max_health': 20, 'generic.max_health': 20, 'minecraft:generic.armor': 0, 'generic.armor': 0,
 'minecraft:generic.armor_toughness': 0, 'minecraft:generic.attack_damage': 1, 'generic.attack_damage': 1,
 'minecraft:generic.attack_speed': 4, 'generic.attack_speed': 4, 'minecraft:generic.movement_speed': 0.1, 'generic.movement_speed': 0.1,
 'minecraft:generic.luck': 0, 'minecraft:generic.knockback_resistance': 0, 'minecraft:generic.attack_knockback': 0,
 'minecraft:generic.flying_speed': 0.4,
 'skilltree:evasion': 0, 'skilltree:blocking': 0, 'skilltree:stealth': 0, 'skilltree:regeneration': 0, 'skilltree:exp_per_minute': 0,
 'apotheosis:crit_chance': 0.05, 'apotheosis:crit_damage': 1.5, 'apotheosis:cold_damage': 0, 'apotheosis:fire_damage': 0,
 'apotheosis:life_steal': 0, 'apotheosis:current_hp_damage': 0, 'apotheosis:overheal': 0, 'apotheosis:mining_speed': 1,
 'apotheosis:arrow_damage': 1, 'apotheosis:arrow_velocity': 1, 'apotheosis:draw_speed': 1, 'apotheosis:experience_gained': 1,
 'apotheosis:healing_received': 1, 'apotheosis:armor_pierce': 0, 'apotheosis:armor_shred': 0, 'apotheosis:prot_pierce': 0,
 'apotheosis:prot_shred': 0, 'apotheosis:dodge_chance': 0,
 'irons_spellbooks:max_mana': 100, 'irons_spellbooks:mana_regen': 1, 'irons_spellbooks:cooldown_reduction': 1,
 'irons_spellbooks:spell_power': 1, 'irons_spellbooks:spell_resist': 1, 'irons_spellbooks:cast_time_reduction': 1,
 'irons_spellbooks:summon_damage': 1,
 'ciscounbound:divine_damage': 0, 'ciscounbound:fell_damage': 0,
}
for s in ['fire','ice','lightning','holy','ender','blood','evocation','nature','eldritch']:
    BASE[f'irons_spellbooks:{s}_spell_power'] = 1
    BASE[f'irons_spellbooks:{s}_magic_resist'] = 1
out = {}
for a, n in ATTR_FR.items():
    out[a] = {'name': n, 'base': BASE.get(a, 0), 'pct': a in PCT_ADD}
for a in BASE:
    if a not in out: out[a] = {'name': a.split(':')[-1], 'base': BASE[a], 'pct': a in PCT_ADD}
json.dump(out, open('site/data/attrs.json','w'), ensure_ascii=False, separators=(',',':'))
print(len(out))
