package net.cisco.init;

import net.cisco.item.AbsoluteEquillibriumItem;
import net.cisco.item.AdjudicatorItem;
import net.cisco.item.AdventurerItem;
import net.cisco.item.AmuletofLuckItem;
import net.cisco.item.AmuletofSwimSpeedItem;
import net.cisco.item.AmuletofVitalityItem;
import net.cisco.item.AncientBootsTabletItem;
import net.cisco.item.AncientChesplateTabletItem;
import net.cisco.item.AncientEssenceItem;
import net.cisco.item.AncientHelmetTabletItem;
import net.cisco.item.AncientLeggingsTabletItem;
import net.cisco.item.ArcaneApparatusItem;
import net.cisco.item.AscendedHeroItem;
import net.cisco.item.AscendedHeroRougeItem;
import net.cisco.item.AscendedHeroVioletItem;
import net.cisco.item.AzureThunderItem;
import net.cisco.item.BandOfBrillianceItem;
import net.cisco.item.BeltofBracingItem;
import net.cisco.item.BjornItem;
import net.cisco.item.BraveSoulItem;
import net.cisco.item.BrightEssenceItem;
import net.cisco.item.BrightPickaxeItem;
import net.cisco.item.BrightsteelIngotItem;
import net.cisco.item.BrightsteelItem;
import net.cisco.item.BrightsteelMultiToolItem;
import net.cisco.item.BrilliantDiamondItem;
import net.cisco.item.CastorItem;
import net.cisco.item.ChampionCoinItem;
import net.cisco.item.ChampionlootbagItem;
import net.cisco.item.ChaseDiscItem;
import net.cisco.item.CiscosArmorItem;
import net.cisco.item.CrownOfDebilitatingDesireItem;
import net.cisco.item.CrownoftheEmperorItem;
import net.cisco.item.DarkCharmItem;
import net.cisco.item.DarkCoreItem;
import net.cisco.item.DarkPickaxeItem;
import net.cisco.item.DarksteelArmorItem;
import net.cisco.item.DarksteelItem;
import net.cisco.item.DarksteelMultitoolItem;
import net.cisco.item.DemoniumIngotItem;
import net.cisco.item.DescendedHeroItem;
import net.cisco.item.DivineCoreItem;
import net.cisco.item.EmberCoreItem;
import net.cisco.item.EquillibriumItem;
import net.cisco.item.FallenHeroArmorItem;
import net.cisco.item.FellFragmentItem;
import net.cisco.item.FellKingArmorItem;
import net.cisco.item.FellRagnarokItem;
import net.cisco.item.FrigidiumingotItem;
import net.cisco.item.FrostfangItem;
import net.cisco.item.GildedEagleItem;
import net.cisco.item.GlacialCoreItem;
import net.cisco.item.GlaciesItem;
import net.cisco.item.HellbrandItem;
import net.cisco.item.KeystoneItem;
import net.cisco.item.LightCoreItem;
import net.cisco.item.NightfallItem;
import net.cisco.item.NocturnalAmethystItem;
import net.cisco.item.PolluxItem;
import net.cisco.item.RadiantRubyItem;
import net.cisco.item.RadiantSunshardItem;
import net.cisco.item.RawBrightsteelItem;
import net.cisco.item.RefinedEquillibriumItem;
import net.cisco.item.SkysplitterItem;
import net.cisco.item.SlumberingEquillibriumItem;
import net.cisco.item.SovereignAscendantItem;
import net.cisco.item.SupremeNightfallItem;
import net.cisco.item.SylviItem;
import net.cisco.item.TabletOfDescensionItem;
import net.cisco.item.TabletofAscensionItem;
import net.cisco.item.TaintedTalismanItem;
import net.cisco.item.TalismanOfBetrayalItem;
import net.cisco.item.TalismanOfChallengeItem;
import net.cisco.item.TemporalCoreItem;
import net.cisco.item.TestItem;
import net.cisco.item.ThedarkoneItem;
import net.cisco.item.ThunderCoreItem;
import net.cisco.item.TrueDragonsBreathItem;
import net.cisco.item.UnattunedCoreItem;
import net.cisco.item.WhereisyourgodnowItem;
import net.cisco.item.WindCoreItem;
import net.cisco.item.ZephyrsWingsItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CiscoModModItems {
   public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "cisco_mod");
   public static final RegistryObject<Item> CISCOS_ARMOR_HELMET = REGISTRY.register("ciscos_armor_helmet", () -> new CiscosArmorItem.Helmet());
   public static final RegistryObject<Item> CISCOS_ARMOR_CHESTPLATE = REGISTRY.register("ciscos_armor_chestplate", () -> new CiscosArmorItem.Chestplate());
   public static final RegistryObject<Item> CISCOS_ARMOR_LEGGINGS = REGISTRY.register("ciscos_armor_leggings", () -> new CiscosArmorItem.Leggings());
   public static final RegistryObject<Item> CISCOS_ARMOR_BOOTS = REGISTRY.register("ciscos_armor_boots", () -> new CiscosArmorItem.Boots());
   public static final RegistryObject<Item> EQUILLIBRIUM = REGISTRY.register("equillibrium", () -> new EquillibriumItem());
   public static final RegistryObject<Item> DIVINE_CORE = REGISTRY.register("divine_core", () -> new DivineCoreItem());
   public static final RegistryObject<Item> SLUMBERING_EQUILLIBRIUM = REGISTRY.register("slumbering_equillibrium", () -> new SlumberingEquillibriumItem());
   public static final RegistryObject<Item> AZURE_THUNDER = REGISTRY.register("azure_thunder", () -> new AzureThunderItem());
   public static final RegistryObject<Item> REFINED_EQUILLIBRIUM = REGISTRY.register("refined_equillibrium", () -> new RefinedEquillibriumItem());
   public static final RegistryObject<Item> NIGHTFALL = REGISTRY.register("nightfall", () -> new NightfallItem());
   public static final RegistryObject<Item> ADJUDICATOR = REGISTRY.register("adjudicator", () -> new AdjudicatorItem());
   public static final RegistryObject<Item> FALLEN_HERO_ARMOR_HELMET = REGISTRY.register("fallen_hero_armor_helmet", () -> new FallenHeroArmorItem.Helmet());
   public static final RegistryObject<Item> FALLEN_HERO_ARMOR_CHESTPLATE = REGISTRY.register(
      "fallen_hero_armor_chestplate", () -> new FallenHeroArmorItem.Chestplate()
   );
   public static final RegistryObject<Item> FALLEN_HERO_ARMOR_LEGGINGS = REGISTRY.register(
      "fallen_hero_armor_leggings", () -> new FallenHeroArmorItem.Leggings()
   );
   public static final RegistryObject<Item> FALLEN_HERO_ARMOR_BOOTS = REGISTRY.register("fallen_hero_armor_boots", () -> new FallenHeroArmorItem.Boots());
   public static final RegistryObject<Item> BRIGHTSTEEL_INGOT = REGISTRY.register("brightsteel_ingot", () -> new BrightsteelIngotItem());
   public static final RegistryObject<Item> BRIGHTSTEEL_MULTI_TOOL = REGISTRY.register("brightsteel_multi_tool", () -> new BrightsteelMultiToolItem());
   public static final RegistryObject<Item> BAND_OF_BRILLIANCE = REGISTRY.register("band_of_brilliance", () -> new BandOfBrillianceItem());
   public static final RegistryObject<Item> TEST_HELMET = REGISTRY.register("test_helmet", () -> new TestItem.Helmet());
   public static final RegistryObject<Item> TEST_CHESTPLATE = REGISTRY.register("test_chestplate", () -> new TestItem.Chestplate());
   public static final RegistryObject<Item> TEST_LEGGINGS = REGISTRY.register("test_leggings", () -> new TestItem.Leggings());
   public static final RegistryObject<Item> TEST_BOOTS = REGISTRY.register("test_boots", () -> new TestItem.Boots());
   public static final RegistryObject<Item> RADIANT_RUBY = REGISTRY.register("radiant_ruby", () -> new RadiantRubyItem());
   public static final RegistryObject<Item> KEYSTONE = REGISTRY.register("keystone", () -> new KeystoneItem());
   public static final RegistryObject<Item> SKYSPLITTER = REGISTRY.register("skysplitter", () -> new SkysplitterItem());
   public static final RegistryObject<Item> CASTOR = REGISTRY.register("castor", () -> new CastorItem());
   public static final RegistryObject<Item> POLLUX = REGISTRY.register("pollux", () -> new PolluxItem());
   public static final RegistryObject<Item> GLACIES = REGISTRY.register("glacies", () -> new GlaciesItem());
   public static final RegistryObject<Item> GILDED_EAGLE_HELMET = REGISTRY.register("gilded_eagle_helmet", () -> new GildedEagleItem.Helmet());
   public static final RegistryObject<Item> GILDED_EAGLE_CHESTPLATE = REGISTRY.register("gilded_eagle_chestplate", () -> new GildedEagleItem.Chestplate());
   public static final RegistryObject<Item> GILDED_EAGLE_LEGGINGS = REGISTRY.register("gilded_eagle_leggings", () -> new GildedEagleItem.Leggings());
   public static final RegistryObject<Item> GILDED_EAGLE_BOOTS = REGISTRY.register("gilded_eagle_boots", () -> new GildedEagleItem.Boots());
   public static final RegistryObject<Item> BRIGHTSTEEL_HELMET = REGISTRY.register("brightsteel_helmet", () -> new BrightsteelItem.Helmet());
   public static final RegistryObject<Item> BRIGHTSTEEL_CHESTPLATE = REGISTRY.register("brightsteel_chestplate", () -> new BrightsteelItem.Chestplate());
   public static final RegistryObject<Item> BRIGHTSTEEL_LEGGINGS = REGISTRY.register("brightsteel_leggings", () -> new BrightsteelItem.Leggings());
   public static final RegistryObject<Item> BRIGHTSTEEL_BOOTS = REGISTRY.register("brightsteel_boots", () -> new BrightsteelItem.Boots());
   public static final RegistryObject<Item> UNATTUNED_CORE = REGISTRY.register("unattuned_core", () -> new UnattunedCoreItem());
   public static final RegistryObject<Item> EMBER_CORE = REGISTRY.register("ember_core", () -> new EmberCoreItem());
   public static final RegistryObject<Item> DARK_CORE = REGISTRY.register("dark_core", () -> new DarkCoreItem());
   public static final RegistryObject<Item> THUNDER_CORE = REGISTRY.register("thunder_core", () -> new ThunderCoreItem());
   public static final RegistryObject<Item> WIND_CORE = REGISTRY.register("wind_core", () -> new WindCoreItem());
   public static final RegistryObject<Item> LIGHT_CORE = REGISTRY.register("light_core", () -> new LightCoreItem());
   public static final RegistryObject<Item> GLACIAL_CORE = REGISTRY.register("glacial_core", () -> new GlacialCoreItem());
   public static final RegistryObject<Item> AMULETOF_VITALITY = REGISTRY.register("amuletof_vitality", () -> new AmuletofVitalityItem());
   public static final RegistryObject<Item> AMULETOF_LUCK = REGISTRY.register("amuletof_luck", () -> new AmuletofLuckItem());
   public static final RegistryObject<Item> AMULETOF_SWIM_SPEED = REGISTRY.register("amuletof_swim_speed", () -> new AmuletofSwimSpeedItem());
   public static final RegistryObject<Item> CROWNOFTHE_EMPEROR = REGISTRY.register("crownofthe_emperor", () -> new CrownoftheEmperorItem());
   public static final RegistryObject<Item> BELTOF_BRACING = REGISTRY.register("beltof_bracing", () -> new BeltofBracingItem());
   public static final RegistryObject<Item> DARK_CHARM = REGISTRY.register("dark_charm", () -> new DarkCharmItem());
   public static final RegistryObject<Item> STRUCUTEBLOCKFIX = block(CiscoModModBlocks.STRUCUTEBLOCKFIX, null);
   public static final RegistryObject<Item> RAW_BRIGHTSTEEL = REGISTRY.register("raw_brightsteel", () -> new RawBrightsteelItem());
   public static final RegistryObject<Item> DARKSTEEL = REGISTRY.register("darksteel", () -> new DarksteelItem());
   public static final RegistryObject<Item> FELL_KING_ARMOR_HELMET = REGISTRY.register("fell_king_armor_helmet", () -> new FellKingArmorItem.Helmet());
   public static final RegistryObject<Item> FELL_KING_ARMOR_CHESTPLATE = REGISTRY.register(
      "fell_king_armor_chestplate", () -> new FellKingArmorItem.Chestplate()
   );
   public static final RegistryObject<Item> FELL_KING_ARMOR_LEGGINGS = REGISTRY.register("fell_king_armor_leggings", () -> new FellKingArmorItem.Leggings());
   public static final RegistryObject<Item> FELL_KING_ARMOR_BOOTS = REGISTRY.register("fell_king_armor_boots", () -> new FellKingArmorItem.Boots());
   public static final RegistryObject<Item> DARKSTEEL_MULTITOOL = REGISTRY.register("darksteel_multitool", () -> new DarksteelMultitoolItem());
   public static final RegistryObject<Item> NOCTURNAL_AMETHYST = REGISTRY.register("nocturnal_amethyst", () -> new NocturnalAmethystItem());
   public static final RegistryObject<Item> BRILLIANT_DIAMOND = REGISTRY.register("brilliant_diamond", () -> new BrilliantDiamondItem());
   public static final RegistryObject<Item> FELL_RAGNAROK = REGISTRY.register("fell_ragnarok", () -> new FellRagnarokItem());
   public static final RegistryObject<Item> ARCANE_APPARATUS = REGISTRY.register("arcane_apparatus", () -> new ArcaneApparatusItem());
   public static final RegistryObject<Item> ANCIENT_ESSENCE = REGISTRY.register("ancient_essence", () -> new AncientEssenceItem());
   public static final RegistryObject<Item> BRIGHT_ESSENCE = REGISTRY.register("bright_essence", () -> new BrightEssenceItem());
   public static final RegistryObject<Item> ADVENTURER_HELMET = REGISTRY.register("adventurer_helmet", () -> new AdventurerItem.Helmet());
   public static final RegistryObject<Item> ADVENTURER_CHESTPLATE = REGISTRY.register("adventurer_chestplate", () -> new AdventurerItem.Chestplate());
   public static final RegistryObject<Item> ADVENTURER_LEGGINGS = REGISTRY.register("adventurer_leggings", () -> new AdventurerItem.Leggings());
   public static final RegistryObject<Item> ADVENTURER_BOOTS = REGISTRY.register("adventurer_boots", () -> new AdventurerItem.Boots());
   public static final RegistryObject<Item> BRIGHT_PICKAXE = REGISTRY.register("bright_pickaxe", () -> new BrightPickaxeItem());
   public static final RegistryObject<Item> DARK_PICKAXE = REGISTRY.register("dark_pickaxe", () -> new DarkPickaxeItem());
   public static final RegistryObject<Item> CISCO_SPAWN_EGG = REGISTRY.register(
      "cisco_spawn_egg", () -> new ForgeSpawnEggItem(CiscoModModEntities.CISCO, -1, -3355648, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<Item> BRAVE_SOUL = REGISTRY.register("brave_soul", () -> new BraveSoulItem());
   public static final RegistryObject<Item> WHEREISYOURGODNOW = REGISTRY.register("whereisyourgodnow", () -> new WhereisyourgodnowItem());
   public static final RegistryObject<Item> AFTER_IMAGE_SPAWN_EGG = REGISTRY.register(
      "after_image_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.AFTER_IMAGE, -3355648, -1, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<Item> TALISMAN_OF_CHALLENGE = REGISTRY.register("talisman_of_challenge", () -> new TalismanOfChallengeItem());
   public static final RegistryObject<Item> BRIGHTSTEELBLOCK = block(CiscoModModBlocks.BRIGHTSTEELBLOCK, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> BRIGHTSTEELSTAIRS = block(CiscoModModBlocks.BRIGHTSTEELSTAIRS, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> BRIGHTSTEELSLAB = block(CiscoModModBlocks.BRIGHTSTEELSLAB, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> ARCANE_BRIGHTSTEEL_BLOCK = block(CiscoModModBlocks.ARCANE_BRIGHTSTEEL_BLOCK, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> DARKSTEEL_BLOCK = block(CiscoModModBlocks.DARKSTEEL_BLOCK, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> DARKSTEEL_STAIRS = block(CiscoModModBlocks.DARKSTEEL_STAIRS, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> DARKSTEEL_SLAB = block(CiscoModModBlocks.DARKSTEEL_SLAB, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> PODIUMOFPURGATORY = block(CiscoModModBlocks.PODIUMOFPURGATORY, CiscoModModTabs.TAB_CISCO_MOD);
   public static final RegistryObject<Item> ASCENDED_HERO_HELMET = REGISTRY.register("ascended_hero_helmet", () -> new AscendedHeroItem.Helmet());
   public static final RegistryObject<Item> ASCENDED_HERO_CHESTPLATE = REGISTRY.register("ascended_hero_chestplate", () -> new AscendedHeroItem.Chestplate());
   public static final RegistryObject<Item> ASCENDED_HERO_LEGGINGS = REGISTRY.register("ascended_hero_leggings", () -> new AscendedHeroItem.Leggings());
   public static final RegistryObject<Item> ASCENDED_HERO_BOOTS = REGISTRY.register("ascended_hero_boots", () -> new AscendedHeroItem.Boots());
   public static final RegistryObject<Item> TABLETOF_ASCENSION = REGISTRY.register("tabletof_ascension", () -> new TabletofAscensionItem());
   public static final RegistryObject<Item> FELLKINGBOSS_SPAWN_EGG = REGISTRY.register(
      "fellkingboss_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.FELLKINGBOSS, -16777216, -256, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<Item> LEGIONNAIRE_KINGSGUARD_SPAWN_EGG = REGISTRY.register(
      "legionnaire_kingsguard_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.LEGIONNAIRE_KINGSGUARD, -6710887, -6711040, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<Item> FELL_SHIELD_SPAWN_EGG = REGISTRY.register(
      "fell_shield_spawn_egg", () -> new ForgeSpawnEggItem(CiscoModModEntities.FELL_SHIELD, -1, -1, new Properties().m_41491_(null))
   );
   public static final RegistryObject<Item> ASCENDED_HERO_VIOLET_HELMET = REGISTRY.register(
      "ascended_hero_violet_helmet", () -> new AscendedHeroVioletItem.Helmet()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_VIOLET_CHESTPLATE = REGISTRY.register(
      "ascended_hero_violet_chestplate", () -> new AscendedHeroVioletItem.Chestplate()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_VIOLET_LEGGINGS = REGISTRY.register(
      "ascended_hero_violet_leggings", () -> new AscendedHeroVioletItem.Leggings()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_VIOLET_BOOTS = REGISTRY.register(
      "ascended_hero_violet_boots", () -> new AscendedHeroVioletItem.Boots()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_ROUGE_HELMET = REGISTRY.register(
      "ascended_hero_rouge_helmet", () -> new AscendedHeroRougeItem.Helmet()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_ROUGE_CHESTPLATE = REGISTRY.register(
      "ascended_hero_rouge_chestplate", () -> new AscendedHeroRougeItem.Chestplate()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_ROUGE_LEGGINGS = REGISTRY.register(
      "ascended_hero_rouge_leggings", () -> new AscendedHeroRougeItem.Leggings()
   );
   public static final RegistryObject<Item> ASCENDED_HERO_ROUGE_BOOTS = REGISTRY.register("ascended_hero_rouge_boots", () -> new AscendedHeroRougeItem.Boots());
   public static final RegistryObject<Item> TAINTED_TALISMAN = REGISTRY.register("tainted_talisman", () -> new TaintedTalismanItem());
   public static final RegistryObject<Item> FELL_FRAGMENT = REGISTRY.register("fell_fragment", () -> new FellFragmentItem());
   public static final RegistryObject<Item> CHAMPION_COIN = REGISTRY.register("champion_coin", () -> new ChampionCoinItem());
   public static final RegistryObject<Item> LEGIONNAIRE_JOTUNN_SPAWN_EGG = REGISTRY.register(
      "legionnaire_jotunn_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.LEGIONNAIRE_JOTUNN, -16777216, -52429, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<Item> ABSOLUTE_EQUILLIBRIUM = REGISTRY.register("absolute_equillibrium", () -> new AbsoluteEquillibriumItem());
   public static final RegistryObject<Item> RADIANT_SUNSHARD = REGISTRY.register("radiant_sunshard", () -> new RadiantSunshardItem());
   public static final RegistryObject<Item> BJORN_HELMET = REGISTRY.register("bjorn_helmet", () -> new BjornItem.Helmet());
   public static final RegistryObject<Item> BJORN_CHESTPLATE = REGISTRY.register("bjorn_chestplate", () -> new BjornItem.Chestplate());
   public static final RegistryObject<Item> BJORN_LEGGINGS = REGISTRY.register("bjorn_leggings", () -> new BjornItem.Leggings());
   public static final RegistryObject<Item> BJORN_BOOTS = REGISTRY.register("bjorn_boots", () -> new BjornItem.Boots());
   public static final RegistryObject<Item> SYLVI_HELMET = REGISTRY.register("sylvi_helmet", () -> new SylviItem.Helmet());
   public static final RegistryObject<Item> SYLVI_CHESTPLATE = REGISTRY.register("sylvi_chestplate", () -> new SylviItem.Chestplate());
   public static final RegistryObject<Item> SYLVI_LEGGINGS = REGISTRY.register("sylvi_leggings", () -> new SylviItem.Leggings());
   public static final RegistryObject<Item> SYLVI_BOOTS = REGISTRY.register("sylvi_boots", () -> new SylviItem.Boots());
   public static final RegistryObject<Item> DRAGON_SEEKER_MISSILE_SPAWN_EGG = REGISTRY.register(
      "dragon_seeker_missile_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.DRAGON_SEEKER_MISSILE, -1, -1, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> DEMONIUM_INGOT = REGISTRY.register("demonium_ingot", () -> new DemoniumIngotItem());
   public static final RegistryObject<Item> FRIGIDIUMINGOT = REGISTRY.register("frigidiumingot", () -> new FrigidiumingotItem());
   public static final RegistryObject<Item> TRUE_DRAGONS_BREATH = REGISTRY.register("true_dragons_breath", () -> new TrueDragonsBreathItem());
   public static final RegistryObject<Item> DARKSTEEL_ARMOR_HELMET = REGISTRY.register("darksteel_armor_helmet", () -> new DarksteelArmorItem.Helmet());
   public static final RegistryObject<Item> DARKSTEEL_ARMOR_CHESTPLATE = REGISTRY.register(
      "darksteel_armor_chestplate", () -> new DarksteelArmorItem.Chestplate()
   );
   public static final RegistryObject<Item> DARKSTEEL_ARMOR_LEGGINGS = REGISTRY.register("darksteel_armor_leggings", () -> new DarksteelArmorItem.Leggings());
   public static final RegistryObject<Item> DARKSTEEL_ARMOR_BOOTS = REGISTRY.register("darksteel_armor_boots", () -> new DarksteelArmorItem.Boots());
   public static final RegistryObject<Item> ZEPHYRS_WINGS = REGISTRY.register("zephyrs_wings", () -> new ZephyrsWingsItem());
   public static final RegistryObject<Item> THEDARKONE = REGISTRY.register("thedarkone", () -> new ThedarkoneItem());
   public static final RegistryObject<Item> CHAMPIONLOOTBAG = REGISTRY.register("championlootbag", () -> new ChampionlootbagItem());
   public static final RegistryObject<Item> CROWN_OF_DEBILITATING_DESIRE = REGISTRY.register(
      "crown_of_debilitating_desire", () -> new CrownOfDebilitatingDesireItem()
   );
   public static final RegistryObject<Item> HELLBRAND = REGISTRY.register("hellbrand", () -> new HellbrandItem());
   public static final RegistryObject<Item> FROSTFANG = REGISTRY.register("frostfang", () -> new FrostfangItem());
   public static final RegistryObject<DescendedHeroItem> DESCENDED_HERO_HELMET = REGISTRY.register(
      "descended_hero_helmet", () -> new DescendedHeroItem(EquipmentSlot.HEAD, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<DescendedHeroItem> DESCENDED_HERO_CHESTPLATE = REGISTRY.register(
      "descended_hero_chestplate", () -> new DescendedHeroItem(EquipmentSlot.CHEST, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<DescendedHeroItem> DESCENDED_HERO_LEGGINGS = REGISTRY.register(
      "descended_hero_leggings", () -> new DescendedHeroItem(EquipmentSlot.LEGS, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<DescendedHeroItem> DESCENDED_HERO_BOOTS = REGISTRY.register(
      "descended_hero_boots", () -> new DescendedHeroItem(EquipmentSlot.FEET, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<SovereignAscendantItem> SOVEREIGN_ASCENDANT_HELMET = REGISTRY.register(
      "sovereign_ascendant_helmet", () -> new SovereignAscendantItem(EquipmentSlot.HEAD, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_())
   );
   public static final RegistryObject<SovereignAscendantItem> SOVEREIGN_ASCENDANT_CHESTPLATE = REGISTRY.register(
      "sovereign_ascendant_chestplate",
      () -> new SovereignAscendantItem(EquipmentSlot.CHEST, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_())
   );
   public static final RegistryObject<SovereignAscendantItem> SOVEREIGN_ASCENDANT_LEGGINGS = REGISTRY.register(
      "sovereign_ascendant_leggings", () -> new SovereignAscendantItem(EquipmentSlot.LEGS, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_())
   );
   public static final RegistryObject<SovereignAscendantItem> SOVEREIGN_ASCENDANT_BOOTS = REGISTRY.register(
      "sovereign_ascendant_boots", () -> new SovereignAscendantItem(EquipmentSlot.FEET, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD).m_41486_())
   );
   public static final RegistryObject<Item> DESCENDED_CISCO_SPAWN_EGG = REGISTRY.register(
      "descended_cisco_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.DESCENDED_CISCO, -1, -1, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> VENGEFUL_AFTER_IMAGE_SPAWN_EGG = REGISTRY.register(
      "vengeful_after_image_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.VENGEFUL_AFTER_IMAGE, -1, -1, new Properties().m_41491_(CiscoModModTabs.TAB_CISCO_MOD))
   );
   public static final RegistryObject<Item> SUPREME_NIGHTFALL_AEGIS_MODE_SPAWN_EGG = REGISTRY.register(
      "supreme_nightfall_aegis_mode_spawn_egg",
      () -> new ForgeSpawnEggItem(CiscoModModEntities.SUPREME_NIGHTFALL_AEGIS_MODE, -1, -1, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SUPREME_NIGHTFALL = REGISTRY.register("supreme_nightfall", () -> new SupremeNightfallItem());
   public static final RegistryObject<Item> TABLET_OF_DESCENSION = REGISTRY.register("tablet_of_descension", () -> new TabletOfDescensionItem());
   public static final RegistryObject<Item> ANCIENT_CHESPLATE_TABLET = REGISTRY.register("ancient_chesplate_tablet", () -> new AncientChesplateTabletItem());
   public static final RegistryObject<Item> CHASE_DISC = REGISTRY.register("chase_disc", () -> new ChaseDiscItem());
   public static final RegistryObject<Item> ANCIENT_LEGGINGS_TABLET = REGISTRY.register("ancient_leggings_tablet", () -> new AncientLeggingsTabletItem());
   public static final RegistryObject<Item> ANCIENT_BOOTS_TABLET = REGISTRY.register("ancient_boots_tablet", () -> new AncientBootsTabletItem());
   public static final RegistryObject<Item> ANCIENT_HELMET_TABLET = REGISTRY.register("ancient_helmet_tablet", () -> new AncientHelmetTabletItem());
   public static final RegistryObject<Item> TEMPORAL_CORE = REGISTRY.register("temporal_core", () -> new TemporalCoreItem());
   public static final RegistryObject<Item> TALISMAN_OF_BETRAYAL = REGISTRY.register("talisman_of_betrayal", () -> new TalismanOfBetrayalItem());

   private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
      return REGISTRY.register(block.getId().m_135815_(), () -> new BlockItem((Block)block.get(), new Properties().m_41491_(tab)));
   }
}
