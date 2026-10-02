# -*- coding: utf-8 -*-
"""Dictionnaires de traduction FR (libellés d'attributs, types d'équipement, raretés...)
et formatage des valeurs. Les NOMS propres d'objets/sorts/talents restent ceux affichés
en jeu (le jeu est en anglais) ; ce qui est traduit, ce sont les descriptions d'effets."""

ATTR_FR = {
    'minecraft:generic.armor': 'Armure',
    'generic.armor': 'Armure',
    'minecraft:generic.armor_toughness': 'Robustesse d\'armure',
    'minecraft:generic.max_health': 'Vie max',
    'generic.max_health': 'Vie max',
    'minecraft:generic.attack_damage': 'Dégâts d\'attaque',
    'generic.attack_damage': 'Dégâts d\'attaque',
    'minecraft:generic.attack_speed': 'Vitesse d\'attaque',
    'generic.attack_speed': 'Vitesse d\'attaque',
    'minecraft:generic.attack_knockback': 'Recul d\'attaque',
    'minecraft:generic.movement_speed': 'Vitesse de déplacement',
    'generic.movement_speed': 'Vitesse de déplacement',
    'minecraft:generic.flying_speed': 'Vitesse de vol',
    'minecraft:generic.luck': 'Chance',
    'minecraft:generic.knockback_resistance': 'Résistance au recul',
    'skilltree:evasion': 'Esquive (points)',
    'skilltree:blocking': 'Blocage (points)',
    'skilltree:regeneration': 'Régénération de vie',
    'skilltree:stealth': 'Discrétion (%)',
    'skilltree:exp_per_minute': 'XP par minute',
    'apotheosis:fire_damage': 'Dégâts de feu',
    'apotheosis:cold_damage': 'Dégâts de froid',
    'apotheosis:crit_chance': 'Chance de critique',
    'apotheosis:crit_damage': 'Dégâts critiques',
    'apotheosis:experience_gained': 'XP gagnée',
    'apotheosis:mining_speed': 'Vitesse de minage',
    'apotheosis:life_steal': 'Vol de vie',
    'apotheosis:armor_pierce': 'Perforation d\'armure (fixe)',
    'apotheosis:armor_shred': 'Déchirement d\'armure (%)',
    'apotheosis:healing_received': 'Soins reçus',
    'apotheosis:overheal': 'Sur-soin (bouclier)',
    'apotheosis:prot_pierce': 'Perforation de protection (fixe)',
    'apotheosis:prot_shred': 'Déchirement de protection (%)',
    'apotheosis:arrow_velocity': 'Vitesse des flèches',
    'apotheosis:arrow_damage': 'Dégâts des flèches',
    'apotheosis:draw_speed': 'Vitesse de tension de l\'arc',
    'apotheosis:dodge_chance': 'Chance d\'esquive',
    'apotheosis:current_hp_damage': 'Dégâts en % des PV actuels de la cible',
    'irons_spellbooks:max_mana': 'Mana max',
    'irons_spellbooks:mana_regen': 'Régénération de mana',
    'irons_spellbooks:cast_time_reduction': 'Réduction du temps d\'incantation',
    'irons_spellbooks:cooldown_reduction': 'Réduction de recharge des sorts',
    'irons_spellbooks:spell_power': 'Puissance des sorts',
    'irons_spellbooks:spell_resist': 'Résistance aux sorts',
    'irons_spellbooks:summon_damage': 'Dégâts des invocations',
    'irons_spellbooks:fire_spell_power': 'Puissance des sorts de Feu',
    'irons_spellbooks:ice_spell_power': 'Puissance des sorts de Glace',
    'irons_spellbooks:lightning_spell_power': 'Puissance des sorts de Foudre',
    'irons_spellbooks:holy_spell_power': 'Puissance des sorts Sacrés',
    'irons_spellbooks:ender_spell_power': 'Puissance des sorts d\'Ender',
    'irons_spellbooks:blood_spell_power': 'Puissance des sorts de Sang',
    'irons_spellbooks:evocation_spell_power': 'Puissance des sorts d\'Évocation',
    'irons_spellbooks:nature_spell_power': 'Puissance des sorts de Nature',
    'irons_spellbooks:eldritch_spell_power': 'Puissance des sorts Occultes',
    'irons_spellbooks:blood_magic_resist': 'Résistance à la magie de Sang',
    'irons_spellbooks:holy_magic_resist': 'Résistance à la magie Sacrée',
    'ars_nouveau:ars_nouveau.perk.flat_max_mana': 'Mana max Ars (fixe)',
    'ars_nouveau:ars_nouveau.perk.percent_max_mana': 'Mana max Ars (%)',
    'ars_nouveau:ars_nouveau.perk.mana_regen': 'Régénération de mana Ars',
    'ars_nouveau:ars_nouveau.perk.spell_damage': 'Dégâts des sorts Ars',
    'ars_nouveau:ars_nouveau.perk.warding': 'Protection magique Ars',
    'eidolon:magic_power': 'Puissance magique',
    'eidolon:chanting_speed': 'Vitesse d\'incantation',
    'forge:entity_gravity': 'Gravité',
    'forge:reach_distance': 'Portée de bloc',
    'forge:block_reach': 'Portée de bloc',
    'forge:entity_reach': 'Portée d\'attaque',
    'forge:attack_range': 'Portée d\'attaque',
    'forge:swim_speed': 'Vitesse de nage',
    'forge:step_height_addition': 'Hauteur de marche',
    'forge:step_height': 'Hauteur de marche',
    'forge:nametag_distance': 'Distance d\'affichage du nom',
    'reach-entity-attributes:reach': 'Portée',
    'reach-entity-attributes:attack_range': 'Portée d\'attaque',
    'botania:pixie_spawn_chance': 'Chance de lutin',
    'obscure_api:resilience': 'Résilience',
    'obscure_api:magic_damage': 'Dégâts magiques',
    'obscure_api:dodge': 'Esquive',
    'obscure_api:healing_power': 'Puissance de soin',
    'voidscape:voidic_infusion_res': 'Résistance à l\'infusion du Vide',
    'voidscape:voidic_res': 'Résistance au Vide',
    'voidscape:voidic_dmg': 'Dégâts du Vide',
    'ciscounbound:divine_damage': 'Dégâts divins',
    'ciscounbound:fell_damage': 'Dégâts maléfiques (Fell)',
    'attributeslib:fire_damage': 'Dégâts de feu',
    'vampirism:blood_exhaustion': 'Épuisement de sang',
    'combatroll:count': 'Nombre de roulades',
    'combatroll:recharge': 'Recharge de roulade',
    'combatroll:distance': 'Distance de roulade',
    'feathers:feathers.max_feathers': 'Plumes max (endurance)',
    'additionalentityattributes:lava_speed': 'Vitesse dans la lave',
    'malum:spirit_spoils': 'Butin d\'esprits',
    'curios:ring': 'Emplacement d\'anneau',
}

# attributs dont la valeur "ADDITION" est une fraction affichée en %
PCT_ADD = {
    'apotheosis:crit_chance', 'apotheosis:crit_damage', 'apotheosis:experience_gained',
    'apotheosis:mining_speed', 'apotheosis:life_steal', 'apotheosis:armor_shred', 'apotheosis:prot_shred',
    'apotheosis:healing_received', 'apotheosis:arrow_velocity', 'apotheosis:arrow_damage',
    'apotheosis:draw_speed', 'apotheosis:dodge_chance', 'apotheosis:current_hp_damage',
    'minecraft:generic.movement_speed', 'generic.movement_speed', 'minecraft:generic.knockback_resistance',
    'irons_spellbooks:cast_time_reduction', 'irons_spellbooks:cooldown_reduction', 'irons_spellbooks:spell_power',
    'irons_spellbooks:spell_resist', 'irons_spellbooks:summon_damage', 'irons_spellbooks:mana_regen',
    'irons_spellbooks:fire_spell_power', 'irons_spellbooks:ice_spell_power', 'irons_spellbooks:lightning_spell_power',
    'irons_spellbooks:holy_spell_power', 'irons_spellbooks:ender_spell_power', 'irons_spellbooks:blood_spell_power',
    'irons_spellbooks:evocation_spell_power', 'irons_spellbooks:nature_spell_power', 'irons_spellbooks:eldritch_spell_power',
    'irons_spellbooks:blood_magic_resist', 'irons_spellbooks:holy_magic_resist',
    'eidolon:magic_power', 'eidolon:chanting_speed', 'forge:swim_speed', 'minecraft:generic.flying_speed',
    'obscure_api:dodge', 'obscure_api:healing_power', 'obscure_api:magic_damage', 'obscure_api:resilience',
    'ciscounbound:divine_damage', 'ciscounbound:fell_damage', 'combatroll:recharge',
}

OPS = {'ADDITION': 0, 'MULTIPLY_BASE': 1, 'MULTIPLY_TOTAL': 2,
       'addition': 0, 'multiply_base': 1, 'multiply_total': 2, 0: 0, 1: 1, 2: 2}

RARITY_FR = {'common': 'Commun', 'uncommon': 'Peu commun', 'rare': 'Rare', 'epic': 'Épique',
             'mythic': 'Mythique', 'ancient': 'Ancien'}
RARITY_ORDER = ['common', 'uncommon', 'rare', 'epic', 'mythic', 'ancient']
RARITY_COLOR = {'common': '#bdbdbd', 'uncommon': '#5bd75b', 'rare': '#5b9bff', 'epic': '#c36bff',
                'mythic': '#ff8a3d', 'ancient': '#ff4d6d'}

# types de "gem_class" / "types" Apotheosis
TYPE_FR = {
    'sword': 'Épées', 'heavy_weapon': 'Armes lourdes', 'light_weapon': 'Armes légères', 'trident': 'Tridents',
    'bow': 'Arcs', 'crossbow': 'Arbalètes', 'ranged': 'Armes à distance', 'melee_weapon': 'Armes de mêlée',
    'pickaxe': 'Pioches', 'axe': 'Haches', 'shovel': 'Pelles', 'hoe': 'Houes', 'breaker': 'Outils de minage',
    'helmet': 'Casque', 'chestplate': 'Plastron', 'leggings': 'Jambières', 'boots': 'Bottes', 'armor': 'Armures',
    'shield': 'Boucliers', 'wand': 'Baguettes', 'spellbook': 'Livres de sorts',
    'curios': 'Accessoires (Curios)', 'jewelry': 'Bijoux', 'weapon': 'Armes',
    'curios:ring': 'Anneau', 'curios:necklace': 'Collier', 'curios:head': 'Tête (curio)', 'curios:back': 'Dos',
    'curios:belt': 'Ceinture', 'curios:charm': 'Charme', 'curios:hands': 'Mains', 'curios:feet': 'Pieds',
    'curios:body': 'Corps', 'curios:bracelet': 'Bracelet', 'curios:bangle': 'Bracelet', 'curios:talisman': 'Talisman',
    'curios:spellstone': 'Pierre de sort', 'curios:an_focus': 'Focus Ars', 'curios:quiver': 'Carquois',
    'curios:bundle': 'Sacoche',
}

def fnum(x, nd=2):
    """Nombre lisible: 1.0 -> 1 ; 0.25 -> 0.25"""
    if isinstance(x, float):
        x = round(x, nd)
        if x == int(x): x = int(x)
    return str(x).replace('.', ',')

def fmt_attr(attr, op, val):
    """Texte FR d'un modificateur d'attribut: '+12 % Dégâts critiques'."""
    if isinstance(op, str): op = OPS.get(op, 0)
    name = ATTR_FR.get(attr)
    if name is None:
        name = attr.split(':')[-1].replace('_', ' ').replace('.', ' ')
    sign = '+' if val >= 0 else '−'
    a = abs(val)
    if op in (1, 2):
        txt = f"{sign}{fnum(a*100, 1)} %"
        if op == 2: txt += ' (multiplicatif)'
    elif attr in PCT_ADD:
        txt = f"{sign}{fnum(a*100, 1)} %"
    else:
        txt = f"{sign}{fnum(a)}"
    return f"{txt} {name}"
