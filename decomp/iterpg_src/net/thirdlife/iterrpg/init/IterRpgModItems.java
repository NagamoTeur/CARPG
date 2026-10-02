package net.thirdlife.iterrpg.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thirdlife.iterrpg.item.AbyssquartzShardItem;
import net.thirdlife.iterrpg.item.AirArmorItem;
import net.thirdlife.iterrpg.item.AirAxeItem;
import net.thirdlife.iterrpg.item.AirFlailItem;
import net.thirdlife.iterrpg.item.AirFragmentItem;
import net.thirdlife.iterrpg.item.AirHoeItem;
import net.thirdlife.iterrpg.item.AirIngotItem;
import net.thirdlife.iterrpg.item.AirPickaxeItem;
import net.thirdlife.iterrpg.item.AirRuneItem;
import net.thirdlife.iterrpg.item.AirScytheItem;
import net.thirdlife.iterrpg.item.AirShovelItem;
import net.thirdlife.iterrpg.item.AirSwordItem;
import net.thirdlife.iterrpg.item.AmetrineIngotItem;
import net.thirdlife.iterrpg.item.AmetrineNuggetItem;
import net.thirdlife.iterrpg.item.ArcanePowderItem;
import net.thirdlife.iterrpg.item.BlackstoneGeodeItem;
import net.thirdlife.iterrpg.item.BloodbranchItem;
import net.thirdlife.iterrpg.item.BloodthirstItem;
import net.thirdlife.iterrpg.item.CaltropItem;
import net.thirdlife.iterrpg.item.CoinItem;
import net.thirdlife.iterrpg.item.CrescentItem;
import net.thirdlife.iterrpg.item.CrystalTearItem;
import net.thirdlife.iterrpg.item.DeepslateGeodeItem;
import net.thirdlife.iterrpg.item.DemonbloodProjectileItem;
import net.thirdlife.iterrpg.item.DemonboneItem;
import net.thirdlife.iterrpg.item.DiamondFlailItem;
import net.thirdlife.iterrpg.item.DiamondScytheItem;
import net.thirdlife.iterrpg.item.DropletProjectileItem;
import net.thirdlife.iterrpg.item.EarthArmorItem;
import net.thirdlife.iterrpg.item.EarthAxeItem;
import net.thirdlife.iterrpg.item.EarthFlailItem;
import net.thirdlife.iterrpg.item.EarthFragmentItem;
import net.thirdlife.iterrpg.item.EarthHoeItem;
import net.thirdlife.iterrpg.item.EarthIngotItem;
import net.thirdlife.iterrpg.item.EarthPickaxeItem;
import net.thirdlife.iterrpg.item.EarthRuneItem;
import net.thirdlife.iterrpg.item.EarthScytheItem;
import net.thirdlife.iterrpg.item.EarthShovelItem;
import net.thirdlife.iterrpg.item.EarthSwordItem;
import net.thirdlife.iterrpg.item.ElementalFragmentItem;
import net.thirdlife.iterrpg.item.ElementalIngotItem;
import net.thirdlife.iterrpg.item.ElementalSwordItem;
import net.thirdlife.iterrpg.item.EmptyRuneItem;
import net.thirdlife.iterrpg.item.EndstoneGeodeItem;
import net.thirdlife.iterrpg.item.EssenceChunkItem;
import net.thirdlife.iterrpg.item.FireArmorItem;
import net.thirdlife.iterrpg.item.FireAxeItem;
import net.thirdlife.iterrpg.item.FireFlailItem;
import net.thirdlife.iterrpg.item.FireFragmentItem;
import net.thirdlife.iterrpg.item.FireHoeItem;
import net.thirdlife.iterrpg.item.FireIngotItem;
import net.thirdlife.iterrpg.item.FirePickaxeItem;
import net.thirdlife.iterrpg.item.FireRuneItem;
import net.thirdlife.iterrpg.item.FireScytheItem;
import net.thirdlife.iterrpg.item.FireShovelItem;
import net.thirdlife.iterrpg.item.FireSwordItem;
import net.thirdlife.iterrpg.item.FireballProjectileItem;
import net.thirdlife.iterrpg.item.GiantWeeperTearItem;
import net.thirdlife.iterrpg.item.GobsteelScrapItem;
import net.thirdlife.iterrpg.item.GoldenFlailItem;
import net.thirdlife.iterrpg.item.GoldenScytheItem;
import net.thirdlife.iterrpg.item.HollowVisageItem;
import net.thirdlife.iterrpg.item.HumieButcherItem;
import net.thirdlife.iterrpg.item.IronFlailItem;
import net.thirdlife.iterrpg.item.IronScytheItem;
import net.thirdlife.iterrpg.item.MagmanumArmorItem;
import net.thirdlife.iterrpg.item.MagmanumChunkItem;
import net.thirdlife.iterrpg.item.MagmanumPickaxeItem;
import net.thirdlife.iterrpg.item.MagmanumSwordItem;
import net.thirdlife.iterrpg.item.MarrowItem;
import net.thirdlife.iterrpg.item.MobPlacerItem;
import net.thirdlife.iterrpg.item.MournfulAbyssDiscItem;
import net.thirdlife.iterrpg.item.NetheriteFlailItem;
import net.thirdlife.iterrpg.item.NetheriteScytheItem;
import net.thirdlife.iterrpg.item.NetherrackGeodeItem;
import net.thirdlife.iterrpg.item.PearlItem;
import net.thirdlife.iterrpg.item.PotsherdItem;
import net.thirdlife.iterrpg.item.SacredTwigItem;
import net.thirdlife.iterrpg.item.SacrificialDaggerItem;
import net.thirdlife.iterrpg.item.SeashellItem;
import net.thirdlife.iterrpg.item.SpawnerFragmentItem;
import net.thirdlife.iterrpg.item.SpellbookItem;
import net.thirdlife.iterrpg.item.SpiderSilkItem;
import net.thirdlife.iterrpg.item.StarfishItem;
import net.thirdlife.iterrpg.item.StingerItem;
import net.thirdlife.iterrpg.item.StoneFlailItem;
import net.thirdlife.iterrpg.item.StoneGeodeItem;
import net.thirdlife.iterrpg.item.StoneScytheItem;
import net.thirdlife.iterrpg.item.TormentorItem;
import net.thirdlife.iterrpg.item.TwiffleLumpItem;
import net.thirdlife.iterrpg.item.VeryGoodAndCheerfulRapierItem;
import net.thirdlife.iterrpg.item.VoidArmorItem;
import net.thirdlife.iterrpg.item.VoidFragmentItem;
import net.thirdlife.iterrpg.item.VoidIngotItem;
import net.thirdlife.iterrpg.item.VoidRuneItem;
import net.thirdlife.iterrpg.item.VoidSwordItem;
import net.thirdlife.iterrpg.item.WaterArmorItem;
import net.thirdlife.iterrpg.item.WaterAxeItem;
import net.thirdlife.iterrpg.item.WaterFlailItem;
import net.thirdlife.iterrpg.item.WaterFragmentItem;
import net.thirdlife.iterrpg.item.WaterHoeItem;
import net.thirdlife.iterrpg.item.WaterIngotItem;
import net.thirdlife.iterrpg.item.WaterPickaxeItem;
import net.thirdlife.iterrpg.item.WaterRuneItem;
import net.thirdlife.iterrpg.item.WaterScytheItem;
import net.thirdlife.iterrpg.item.WaterShovelItem;
import net.thirdlife.iterrpg.item.WaterSwordItem;
import net.thirdlife.iterrpg.item.WeeperTearItem;
import net.thirdlife.iterrpg.item.WoodenFlailItem;
import net.thirdlife.iterrpg.item.WoodenScytheItem;

public class IterRpgModItems {
   public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "iter_rpg");
   public static final RegistryObject<Item> ARCANE_FLOWER = block(IterRpgModBlocks.ARCANE_FLOWER, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> ABYSS_QUARTZ = block(IterRpgModBlocks.ABYSS_QUARTZ, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> SPIDER_EGG = block(IterRpgModBlocks.SPIDER_EGG, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> GRIMSTONE = block(IterRpgModBlocks.GRIMSTONE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> MAGMANUM_ORE = block(IterRpgModBlocks.MAGMANUM_ORE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> GEODITE = block(IterRpgModBlocks.GEODITE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> DEEPSLATE_GEODITE = block(IterRpgModBlocks.DEEPSLATE_GEODITE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> NETHERRACK_GEODITE = block(IterRpgModBlocks.NETHERRACK_GEODITE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> BLACKSTONE_GEODITE = block(IterRpgModBlocks.BLACKSTONE_GEODITE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> ENDSTONE_GEODITE = block(IterRpgModBlocks.ENDSTONE_GEODITE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> TWIFFLE_BLOCK = block(IterRpgModBlocks.TWIFFLE_BLOCK, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> TWIFFLE = block(IterRpgModBlocks.TWIFFLE, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> WITCHMUD = block(IterRpgModBlocks.WITCHMUD, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> CATTAIL = doubleBlock(IterRpgModBlocks.CATTAIL, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> CLOUD_BLOCK = block(IterRpgModBlocks.CLOUD_BLOCK, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> FROSTY_GRASS = block(IterRpgModBlocks.FROSTY_GRASS, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> SACRED_SAPLING = block(IterRpgModBlocks.SACRED_SAPLING, CreativeModeTab.f_40750_);
   public static final RegistryObject<Item> AMETRINE_BLOCK = block(IterRpgModBlocks.AMETRINE_BLOCK, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> AMETRINE_BRICKS = block(IterRpgModBlocks.AMETRINE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ABYSSQUARTZ_BLOCK = block(IterRpgModBlocks.ABYSSQUARTZ_BLOCK, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> POLISHED_GRIMSTONE = block(IterRpgModBlocks.POLISHED_GRIMSTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GRIMSTONE_BRICKS = block(IterRpgModBlocks.GRIMSTONE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> CRACKED_GRIMSTONE_BRICKS = block(IterRpgModBlocks.CRACKED_GRIMSTONE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> CHISELED_GRIMSTONE_BRICKS = block(IterRpgModBlocks.CHISELED_GRIMSTONE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> POLISHED_GRIMSTONE_STAIRS = block(IterRpgModBlocks.POLISHED_GRIMSTONE_STAIRS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GRIMSTONE_BRICKS_STAIRS = block(IterRpgModBlocks.GRIMSTONE_BRICKS_STAIRS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GRIMSTONE_SLAB = block(IterRpgModBlocks.GRIMSTONE_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> POLISHED_GRIMSTONE_SLAB = block(IterRpgModBlocks.POLISHED_GRIMSTONE_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GRIMSTONE_BRICKS_SLAB = block(IterRpgModBlocks.GRIMSTONE_BRICKS_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> POLISHED_GRIMSTONE_WALL = block(IterRpgModBlocks.POLISHED_GRIMSTONE_WALL, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GRIMSTONE_BRICKS_WALL = block(IterRpgModBlocks.GRIMSTONE_BRICKS_WALL, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> CHARGED_GRIMSTONE_BRICKS = block(IterRpgModBlocks.CHARGED_GRIMSTONE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GRIM_KEYHOLE = block(IterRpgModBlocks.GRIM_KEYHOLE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GIANT_CHAIN = block(IterRpgModBlocks.GIANT_CHAIN, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SMALL_VASE = block(IterRpgModBlocks.SMALL_VASE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> VASE = block(IterRpgModBlocks.VASE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> BIG_VASE = block(IterRpgModBlocks.BIG_VASE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SMALL_VASE_DECO = block(IterRpgModBlocks.SMALL_VASE_DECO, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> VASE_DECO = block(IterRpgModBlocks.VASE_DECO, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> BIG_VASE_DECO = block(IterRpgModBlocks.BIG_VASE_DECO, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SPIDER_SILK_BLOCK = block(IterRpgModBlocks.SPIDER_SILK_BLOCK, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> MAGMANUM_BLOCK = block(IterRpgModBlocks.MAGMANUM_BLOCK, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> HIVESTONE = block(IterRpgModBlocks.HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> CUT_HIVESTONE = block(IterRpgModBlocks.CUT_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> CRACKED_HIVESTONE = block(IterRpgModBlocks.CRACKED_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> CRUMBLING_HIVESTONE = block(IterRpgModBlocks.CRUMBLING_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> RUNIC_HIVESTONE = block(IterRpgModBlocks.RUNIC_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SILKY_HIVESTONE = block(IterRpgModBlocks.SILKY_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> WEAVED_HIVESTONE = block(IterRpgModBlocks.WEAVED_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> INFESTED_HIVESTONE = block(IterRpgModBlocks.INFESTED_HIVESTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> HIVESTONE_SLAB = block(IterRpgModBlocks.HIVESTONE_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> HIVESTONE_STAIRS = block(IterRpgModBlocks.HIVESTONE_STAIRS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> HIVESTONE_WALL = block(IterRpgModBlocks.HIVESTONE_WALL, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PACKED_SNOW = block(IterRpgModBlocks.PACKED_SNOW, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PACKED_SNOW_BRICKS = block(IterRpgModBlocks.PACKED_SNOW_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ORNATE_PACKED_SNOW_BRICKS = block(IterRpgModBlocks.ORNATE_PACKED_SNOW_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PACKED_SNOW_BRICKS_STAIRS = block(IterRpgModBlocks.PACKED_SNOW_BRICKS_STAIRS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PACKED_SNOW_SLAB = block(IterRpgModBlocks.PACKED_SNOW_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PACKED_SNOW_BRICKS_SLAB = block(IterRpgModBlocks.PACKED_SNOW_BRICKS_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PACKED_SNOW_BRICKS_WALL = block(IterRpgModBlocks.PACKED_SNOW_BRICKS_WALL, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SMOOTH_ICE = block(IterRpgModBlocks.SMOOTH_ICE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ICE_BRICKS = block(IterRpgModBlocks.ICE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ORNATE_ICE_BRICKS = block(IterRpgModBlocks.ORNATE_ICE_BRICKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ICE_BRICK_STAIRS = block(IterRpgModBlocks.ICE_BRICK_STAIRS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ICE_BRICK_SLAB = block(IterRpgModBlocks.ICE_BRICK_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SMOOTH_ICE_SLAB = block(IterRpgModBlocks.SMOOTH_ICE_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> ICE_BRICK_WALL = block(IterRpgModBlocks.ICE_BRICK_WALL, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> GUNPOWDER_BARREL = block(IterRpgModBlocks.GUNPOWDER_BARREL, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> PEARL_BLOCK = block(IterRpgModBlocks.PEARL_BLOCK, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_LOG = block(IterRpgModBlocks.SACRED_LOG, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_WOOD = block(IterRpgModBlocks.SACRED_WOOD, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> STRIPED_SACRED_LOG = block(IterRpgModBlocks.STRIPED_SACRED_LOG, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_LEAVES = block(IterRpgModBlocks.SACRED_LEAVES, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_PLANKS = block(IterRpgModBlocks.SACRED_PLANKS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_STAIRS = block(IterRpgModBlocks.SACRED_STAIRS, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_SLAB = block(IterRpgModBlocks.SACRED_SLAB, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> SACRED_FENCE = block(IterRpgModBlocks.SACRED_FENCE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> DRILL = block(IterRpgModBlocks.DRILL, CreativeModeTab.f_40751_);
   public static final RegistryObject<Item> SACRED_DOOR = doubleBlock(IterRpgModBlocks.SACRED_DOOR, CreativeModeTab.f_40751_);
   public static final RegistryObject<Item> SACRED_FENCE_GATE = block(IterRpgModBlocks.SACRED_FENCE_GATE, CreativeModeTab.f_40751_);
   public static final RegistryObject<Item> SACRED_TRAPDOOR = block(IterRpgModBlocks.SACRED_TRAPDOOR, CreativeModeTab.f_40751_);
   public static final RegistryObject<Item> SACRED_PRESSURE_PLATE = block(IterRpgModBlocks.SACRED_PRESSURE_PLATE, CreativeModeTab.f_40751_);
   public static final RegistryObject<Item> SACRED_BUTTON = block(IterRpgModBlocks.SACRED_BUTTON, CreativeModeTab.f_40751_);
   public static final RegistryObject<Item> GOBLIN_SPAWNER = block(IterRpgModBlocks.GOBLIN_SPAWNER, CreativeModeTab.f_40753_);
   public static final RegistryObject<Item> GOBLIN_SPAWN_EGG = REGISTRY.register(
      "goblin_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.GOBLIN, -5392333, -9359322, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> GOBLIN_WARRIOR_SPAWN_EGG = REGISTRY.register(
      "goblin_warrior_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.GOBLIN_WARRIOR, -5392333, -7500906, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> HOBGOBLIN_SPAWN_EGG = REGISTRY.register(
      "hobgoblin_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.HOBGOBLIN, -15633798, -13230801, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SCALLOP_SPAWN_EGG = REGISTRY.register(
      "scallop_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.SCALLOP, -10134683, -2706222, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> GIANT_SPIDER_SPAWN_EGG = REGISTRY.register(
      "giant_spider_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.GIANT_SPIDER, -12766183, -5763570, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SPIDERLING_SPAWN_EGG = REGISTRY.register(
      "spiderling_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.SPIDERLING, -2372425, -13029294, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> PEEPER_SPAWN_EGG = REGISTRY.register(
      "peeper_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.PEEPER, -12368305, -15592681, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> MUDKIN_SPAWN_EGG = REGISTRY.register(
      "mudkin_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.MUDKIN, -11060690, -11574471, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> GRIEVER_SPAWN_EGG = REGISTRY.register(
      "griever_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.GRIEVER, -13686721, -15531998, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> MOURNSTONE_SPAWN_EGG = REGISTRY.register(
      "mournstone_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.MOURNSTONE, -13686721, -1020441, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> WEEPER_SPAWN_EGG = REGISTRY.register(
      "weeper_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.WEEPER, -13686721, -14349255, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> SORROWSEALED_SPAWN_EGG = REGISTRY.register(
      "sorrowsealed_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.SORROWSEALED, -11580839, -1020441, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> CARCASS_SPAWN_EGG = REGISTRY.register(
      "carcass_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.CARCASS, -3490672, -2279376, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> DEMONSOUL_SPAWN_EGG = REGISTRY.register(
      "demonsoul_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.DEMONSOUL, -4973520, -10662293, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> REVENANT_SPAWN_EGG = REGISTRY.register(
      "revenant_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.REVENANT, -3490672, -4973520, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> EARTH_ELEMENTAL_SPAWN_EGG = REGISTRY.register(
      "earth_elemental_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.EARTH_ELEMENTAL, -10730928, -11030686, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> WATER_ELEMENTAL_SPAWN_EGG = REGISTRY.register(
      "water_elemental_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.WATER_ELEMENTAL, -9521193, -14795684, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> AIR_ELEMENTAL_SPAWN_EGG = REGISTRY.register(
      "air_elemental_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.AIR_ELEMENTAL, -3542273, -1917878, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> FIRE_ELEMENTAL_SPAWN_EGG = REGISTRY.register(
      "fire_elemental_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.FIRE_ELEMENTAL, -6213352, -1917878, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> VOID_ELEMENTAL_SPAWN_EGG = REGISTRY.register(
      "void_elemental_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.VOID_ELEMENTAL, -15462119, -34575, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> BLOATED_SPAWN_EGG = REGISTRY.register(
      "bloated_spawn_egg", () -> new ForgeSpawnEggItem(IterRpgModEntities.BLOATED, -11693440, -10100515, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> INSATIABLE_SPAWN_EGG = REGISTRY.register(
      "insatiable_spawn_egg",
      () -> new ForgeSpawnEggItem(IterRpgModEntities.INSATIABLE, -11107202, -11753854, new Properties().m_41491_(CreativeModeTab.f_40753_))
   );
   public static final RegistryObject<Item> ARCANE_FLOWER_SEEDS = block(IterRpgModBlocks.ARCANE_FLOWER_SEEDS, CreativeModeTab.f_40759_);
   public static final RegistryObject<Item> ABYSSQUARTZ_SHARD = REGISTRY.register("abyssquartz_shard", () -> new AbyssquartzShardItem());
   public static final RegistryObject<Item> AMETRINE_INGOT = REGISTRY.register("ametrine_ingot", () -> new AmetrineIngotItem());
   public static final RegistryObject<Item> AMETRINE_NUGGET = REGISTRY.register("ametrine_nugget", () -> new AmetrineNuggetItem());
   public static final RegistryObject<Item> ARCANE_POWDER = REGISTRY.register("arcane_powder", () -> new ArcanePowderItem());
   public static final RegistryObject<Item> COIN = REGISTRY.register("coin", () -> new CoinItem());
   public static final RegistryObject<Item> SPAWNER_FRAGMENT = REGISTRY.register("spawner_fragment", () -> new SpawnerFragmentItem());
   public static final RegistryObject<Item> POTSHERD = REGISTRY.register("potsherd", () -> new PotsherdItem());
   public static final RegistryObject<Item> GOBSTEEL_SCRAP = REGISTRY.register("gobsteel_scrap", () -> new GobsteelScrapItem());
   public static final RegistryObject<Item> SEASHELL = REGISTRY.register("seashell", () -> new SeashellItem());
   public static final RegistryObject<Item> STARFISH = REGISTRY.register("starfish", () -> new StarfishItem());
   public static final RegistryObject<Item> PEARL = REGISTRY.register("pearl", () -> new PearlItem());
   public static final RegistryObject<Item> SPIDER_SILK = REGISTRY.register("spider_silk", () -> new SpiderSilkItem());
   public static final RegistryObject<Item> MAGMANUM_CHUNK = REGISTRY.register("magmanum_chunk", () -> new MagmanumChunkItem());
   public static final RegistryObject<Item> DEMONBONE = REGISTRY.register("demonbone", () -> new DemonboneItem());
   public static final RegistryObject<Item> STONE_GEODE = REGISTRY.register("stone_geode", () -> new StoneGeodeItem());
   public static final RegistryObject<Item> DEEPSLATE_GEODE = REGISTRY.register("deepslate_geode", () -> new DeepslateGeodeItem());
   public static final RegistryObject<Item> NETHERRACK_GEODE = REGISTRY.register("netherrack_geode", () -> new NetherrackGeodeItem());
   public static final RegistryObject<Item> BLACKSTONE_GEODE = REGISTRY.register("blackstone_geode", () -> new BlackstoneGeodeItem());
   public static final RegistryObject<Item> ENDSTONE_GEODE = REGISTRY.register("endstone_geode", () -> new EndstoneGeodeItem());
   public static final RegistryObject<Item> EMPTY_RUNE = REGISTRY.register("empty_rune", () -> new EmptyRuneItem());
   public static final RegistryObject<Item> EARTH_RUNE = REGISTRY.register("earth_rune", () -> new EarthRuneItem());
   public static final RegistryObject<Item> EARTH_INGOT = REGISTRY.register("earth_ingot", () -> new EarthIngotItem());
   public static final RegistryObject<Item> EARTH_FRAGMENT = REGISTRY.register("earth_fragment", () -> new EarthFragmentItem());
   public static final RegistryObject<Item> WATER_RUNE = REGISTRY.register("water_rune", () -> new WaterRuneItem());
   public static final RegistryObject<Item> WATER_INGOT = REGISTRY.register("water_ingot", () -> new WaterIngotItem());
   public static final RegistryObject<Item> WATER_FRAGMENT = REGISTRY.register("water_fragment", () -> new WaterFragmentItem());
   public static final RegistryObject<Item> AIR_RUNE = REGISTRY.register("air_rune", () -> new AirRuneItem());
   public static final RegistryObject<Item> AIR_INGOT = REGISTRY.register("air_ingot", () -> new AirIngotItem());
   public static final RegistryObject<Item> AIR_FRAGMENT = REGISTRY.register("air_fragment", () -> new AirFragmentItem());
   public static final RegistryObject<Item> FIRE_RUNE = REGISTRY.register("fire_rune", () -> new FireRuneItem());
   public static final RegistryObject<Item> FIRE_INGOT = REGISTRY.register("fire_ingot", () -> new FireIngotItem());
   public static final RegistryObject<Item> FIRE_FRAGMENT = REGISTRY.register("fire_fragment", () -> new FireFragmentItem());
   public static final RegistryObject<Item> VOID_RUNE = REGISTRY.register("void_rune", () -> new VoidRuneItem());
   public static final RegistryObject<Item> VOID_INGOT = REGISTRY.register("void_ingot", () -> new VoidIngotItem());
   public static final RegistryObject<Item> VOID_FRAGMENT = REGISTRY.register("void_fragment", () -> new VoidFragmentItem());
   public static final RegistryObject<Item> ELEMENTAL_INGOT = REGISTRY.register("elemental_ingot", () -> new ElementalIngotItem());
   public static final RegistryObject<Item> ELEMENTAL_FRAGMENT = REGISTRY.register("elemental_fragment", () -> new ElementalFragmentItem());
   public static final RegistryObject<Item> HOLLOW_VISAGE = REGISTRY.register("hollow_visage", () -> new HollowVisageItem());
   public static final RegistryObject<Item> CRYSTAL_TEAR = REGISTRY.register("crystal_tear", () -> new CrystalTearItem());
   public static final RegistryObject<Item> SACRED_TWIG = REGISTRY.register("sacred_twig", () -> new SacredTwigItem());
   public static final RegistryObject<Item> MOURNFUL_ABYSS_DISC = REGISTRY.register("mournful_abyss_disc", () -> new MournfulAbyssDiscItem());
   public static final RegistryObject<Item> TWIFFLE_LUMP = REGISTRY.register("twiffle_lump", () -> new TwiffleLumpItem());
   public static final RegistryObject<Item> WOODEN_SCYTHE = REGISTRY.register("wooden_scythe", () -> new WoodenScytheItem());
   public static final RegistryObject<Item> STONE_SCYTHE = REGISTRY.register("stone_scythe", () -> new StoneScytheItem());
   public static final RegistryObject<Item> IRON_SCYTHE = REGISTRY.register("iron_scythe", () -> new IronScytheItem());
   public static final RegistryObject<Item> GOLDEN_SCYTHE = REGISTRY.register("golden_scythe", () -> new GoldenScytheItem());
   public static final RegistryObject<Item> DIAMOND_SCYTHE = REGISTRY.register("diamond_scythe", () -> new DiamondScytheItem());
   public static final RegistryObject<Item> NETHERITE_SCYTHE = REGISTRY.register("netherite_scythe", () -> new NetheriteScytheItem());
   public static final RegistryObject<Item> WOODEN_FLAIL = REGISTRY.register("wooden_flail", () -> new WoodenFlailItem());
   public static final RegistryObject<Item> STONE_FLAIL = REGISTRY.register("stone_flail", () -> new StoneFlailItem());
   public static final RegistryObject<Item> IRON_FLAIL = REGISTRY.register("iron_flail", () -> new IronFlailItem());
   public static final RegistryObject<Item> GOLDEN_FLAIL = REGISTRY.register("golden_flail", () -> new GoldenFlailItem());
   public static final RegistryObject<Item> DIAMOND_FLAIL = REGISTRY.register("diamond_flail", () -> new DiamondFlailItem());
   public static final RegistryObject<Item> NETHERITE_FLAIL = REGISTRY.register("netherite_flail", () -> new NetheriteFlailItem());
   public static final RegistryObject<Item> HUMIE_BUTCHER = REGISTRY.register("humie_butcher", () -> new HumieButcherItem());
   public static final RegistryObject<Item> VERY_GOOD_AND_CHEERFUL_RAPIER = REGISTRY.register(
      "very_good_and_cheerful_rapier", () -> new VeryGoodAndCheerfulRapierItem()
   );
   public static final RegistryObject<Item> CALTROP = REGISTRY.register("caltrop", () -> new CaltropItem());
   public static final RegistryObject<Item> MAGMANUM_ARMOR_HELMET = REGISTRY.register("magmanum_armor_helmet", () -> new MagmanumArmorItem.Helmet());
   public static final RegistryObject<Item> MAGMANUM_ARMOR_CHESTPLATE = REGISTRY.register("magmanum_armor_chestplate", () -> new MagmanumArmorItem.Chestplate());
   public static final RegistryObject<Item> MAGMANUM_ARMOR_LEGGINGS = REGISTRY.register("magmanum_armor_leggings", () -> new MagmanumArmorItem.Leggings());
   public static final RegistryObject<Item> MAGMANUM_ARMOR_BOOTS = REGISTRY.register("magmanum_armor_boots", () -> new MagmanumArmorItem.Boots());
   public static final RegistryObject<Item> MAGMANUM_SWORD = REGISTRY.register("magmanum_sword", () -> new MagmanumSwordItem());
   public static final RegistryObject<Item> BLOODTHIRST = REGISTRY.register("bloodthirst", () -> new BloodthirstItem());
   public static final RegistryObject<Item> BLOODBRANCH = REGISTRY.register("bloodbranch", () -> new BloodbranchItem());
   public static final RegistryObject<Item> MARROW = REGISTRY.register("marrow", () -> new MarrowItem());
   public static final RegistryObject<Item> TORMENTOR = REGISTRY.register("tormentor", () -> new TormentorItem());
   public static final RegistryObject<Item> CRESCENT = REGISTRY.register("crescent", () -> new CrescentItem());
   public static final RegistryObject<Item> EARTH_SWORD = REGISTRY.register("earth_sword", () -> new EarthSwordItem());
   public static final RegistryObject<Item> EARTH_SCYTHE = REGISTRY.register("earth_scythe", () -> new EarthScytheItem());
   public static final RegistryObject<Item> EARTH_FLAIL = REGISTRY.register("earth_flail", () -> new EarthFlailItem());
   public static final RegistryObject<Item> WATER_SWORD = REGISTRY.register("water_sword", () -> new WaterSwordItem());
   public static final RegistryObject<Item> WATER_SCYTHE = REGISTRY.register("water_scythe", () -> new WaterScytheItem());
   public static final RegistryObject<Item> WATER_FLAIL = REGISTRY.register("water_flail", () -> new WaterFlailItem());
   public static final RegistryObject<Item> AIR_SWORD = REGISTRY.register("air_sword", () -> new AirSwordItem());
   public static final RegistryObject<Item> AIR_SCYTHE = REGISTRY.register("air_scythe", () -> new AirScytheItem());
   public static final RegistryObject<Item> AIR_FLAIL = REGISTRY.register("air_flail", () -> new AirFlailItem());
   public static final RegistryObject<Item> FIRE_SWORD = REGISTRY.register("fire_sword", () -> new FireSwordItem());
   public static final RegistryObject<Item> FIRE_SCYTHE = REGISTRY.register("fire_scythe", () -> new FireScytheItem());
   public static final RegistryObject<Item> FIRE_FLAIL = REGISTRY.register("fire_flail", () -> new FireFlailItem());
   public static final RegistryObject<Item> VOID_SWORD = REGISTRY.register("void_sword", () -> new VoidSwordItem());
   public static final RegistryObject<Item> EARTH_ARMOR_HELMET = REGISTRY.register("earth_armor_helmet", () -> new EarthArmorItem.Helmet());
   public static final RegistryObject<Item> EARTH_ARMOR_CHESTPLATE = REGISTRY.register("earth_armor_chestplate", () -> new EarthArmorItem.Chestplate());
   public static final RegistryObject<Item> EARTH_ARMOR_LEGGINGS = REGISTRY.register("earth_armor_leggings", () -> new EarthArmorItem.Leggings());
   public static final RegistryObject<Item> EARTH_ARMOR_BOOTS = REGISTRY.register("earth_armor_boots", () -> new EarthArmorItem.Boots());
   public static final RegistryObject<Item> WATER_ARMOR_HELMET = REGISTRY.register("water_armor_helmet", () -> new WaterArmorItem.Helmet());
   public static final RegistryObject<Item> WATER_ARMOR_CHESTPLATE = REGISTRY.register("water_armor_chestplate", () -> new WaterArmorItem.Chestplate());
   public static final RegistryObject<Item> WATER_ARMOR_LEGGINGS = REGISTRY.register("water_armor_leggings", () -> new WaterArmorItem.Leggings());
   public static final RegistryObject<Item> WATER_ARMOR_BOOTS = REGISTRY.register("water_armor_boots", () -> new WaterArmorItem.Boots());
   public static final RegistryObject<Item> AIR_ARMOR_HELMET = REGISTRY.register("air_armor_helmet", () -> new AirArmorItem.Helmet());
   public static final RegistryObject<Item> AIR_ARMOR_CHESTPLATE = REGISTRY.register("air_armor_chestplate", () -> new AirArmorItem.Chestplate());
   public static final RegistryObject<Item> AIR_ARMOR_LEGGINGS = REGISTRY.register("air_armor_leggings", () -> new AirArmorItem.Leggings());
   public static final RegistryObject<Item> AIR_ARMOR_BOOTS = REGISTRY.register("air_armor_boots", () -> new AirArmorItem.Boots());
   public static final RegistryObject<Item> FIRE_ARMOR_HELMET = REGISTRY.register("fire_armor_helmet", () -> new FireArmorItem.Helmet());
   public static final RegistryObject<Item> FIRE_ARMOR_CHESTPLATE = REGISTRY.register("fire_armor_chestplate", () -> new FireArmorItem.Chestplate());
   public static final RegistryObject<Item> FIRE_ARMOR_LEGGINGS = REGISTRY.register("fire_armor_leggings", () -> new FireArmorItem.Leggings());
   public static final RegistryObject<Item> FIRE_ARMOR_BOOTS = REGISTRY.register("fire_armor_boots", () -> new FireArmorItem.Boots());
   public static final RegistryObject<Item> VOID_ARMOR_HELMET = REGISTRY.register("void_armor_helmet", () -> new VoidArmorItem.Helmet());
   public static final RegistryObject<Item> VOID_ARMOR_CHESTPLATE = REGISTRY.register("void_armor_chestplate", () -> new VoidArmorItem.Chestplate());
   public static final RegistryObject<Item> VOID_ARMOR_LEGGINGS = REGISTRY.register("void_armor_leggings", () -> new VoidArmorItem.Leggings());
   public static final RegistryObject<Item> VOID_ARMOR_BOOTS = REGISTRY.register("void_armor_boots", () -> new VoidArmorItem.Boots());
   public static final RegistryObject<Item> ELEMENTAL_SWORD = REGISTRY.register("elemental_sword", () -> new ElementalSwordItem());
   public static final RegistryObject<Item> MAGMANUM_PICKAXE = REGISTRY.register("magmanum_pickaxe", () -> new MagmanumPickaxeItem());
   public static final RegistryObject<Item> EARTH_PICKAXE = REGISTRY.register("earth_pickaxe", () -> new EarthPickaxeItem());
   public static final RegistryObject<Item> EARTH_AXE = REGISTRY.register("earth_axe", () -> new EarthAxeItem());
   public static final RegistryObject<Item> EARTH_SHOVEL = REGISTRY.register("earth_shovel", () -> new EarthShovelItem());
   public static final RegistryObject<Item> EARTH_HOE = REGISTRY.register("earth_hoe", () -> new EarthHoeItem());
   public static final RegistryObject<Item> WATER_PICKAXE = REGISTRY.register("water_pickaxe", () -> new WaterPickaxeItem());
   public static final RegistryObject<Item> WATER_AXE = REGISTRY.register("water_axe", () -> new WaterAxeItem());
   public static final RegistryObject<Item> WATER_SHOVEL = REGISTRY.register("water_shovel", () -> new WaterShovelItem());
   public static final RegistryObject<Item> WATER_HOE = REGISTRY.register("water_hoe", () -> new WaterHoeItem());
   public static final RegistryObject<Item> AIR_PICKAXE = REGISTRY.register("air_pickaxe", () -> new AirPickaxeItem());
   public static final RegistryObject<Item> AIR_AXE = REGISTRY.register("air_axe", () -> new AirAxeItem());
   public static final RegistryObject<Item> AIR_SHOVEL = REGISTRY.register("air_shovel", () -> new AirShovelItem());
   public static final RegistryObject<Item> AIR_HOE = REGISTRY.register("air_hoe", () -> new AirHoeItem());
   public static final RegistryObject<Item> FIRE_PICKAXE = REGISTRY.register("fire_pickaxe", () -> new FirePickaxeItem());
   public static final RegistryObject<Item> FIRE_AXE = REGISTRY.register("fire_axe", () -> new FireAxeItem());
   public static final RegistryObject<Item> FIRE_SHOVEL = REGISTRY.register("fire_shovel", () -> new FireShovelItem());
   public static final RegistryObject<Item> FIRE_HOE = REGISTRY.register("fire_hoe", () -> new FireHoeItem());
   public static final RegistryObject<Item> GENERIC_DUNGEON_GEN_BLOCK = block(IterRpgModBlocks.GENERIC_DUNGEON_GEN_BLOCK, null);
   public static final RegistryObject<Item> FIREBALL_PROJECTILE = REGISTRY.register("fireball_projectile", () -> new FireballProjectileItem());
   public static final RegistryObject<Item> DROPLET_PROJECTILE = REGISTRY.register("droplet_projectile", () -> new DropletProjectileItem());
   public static final RegistryObject<Item> WEEPER_TEAR = REGISTRY.register("weeper_tear", () -> new WeeperTearItem());
   public static final RegistryObject<Item> SORROW_SPIRE_GEN_BLOCK = block(IterRpgModBlocks.SORROW_SPIRE_GEN_BLOCK, null);
   public static final RegistryObject<Item> COMPACTED_SORROWSEALED = block(IterRpgModBlocks.COMPACTED_SORROWSEALED, null);
   public static final RegistryObject<Item> DEMONBLOOD_PROJECTILE = REGISTRY.register("demonblood_projectile", () -> new DemonbloodProjectileItem());
   public static final RegistryObject<Item> GOBLIN_DUNGEON_GEN = block(IterRpgModBlocks.GOBLIN_DUNGEON_GEN, null);
   public static final RegistryObject<Item> ARTHROPOD_CATACOMBS_GEN_BLOCK = block(IterRpgModBlocks.ARTHROPOD_CATACOMBS_GEN_BLOCK, null);
   public static final RegistryObject<Item> GIANT_WEEPER_TEAR = REGISTRY.register("giant_weeper_tear", () -> new GiantWeeperTearItem());
   public static final RegistryObject<Item> MOB_PLACER = REGISTRY.register("mob_placer", () -> new MobPlacerItem());
   public static final RegistryObject<Item> GRIM_SOULTRAP = block(IterRpgModBlocks.GRIM_SOULTRAP, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> BARRIER_PROJECTOR = block(IterRpgModBlocks.BARRIER_PROJECTOR, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> RUNIC_GRIMSTONE = block(IterRpgModBlocks.RUNIC_GRIMSTONE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> RUNIC_GRIMSTONE_ACTIVE = block(IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE, null);
   public static final RegistryObject<Item> MOURNERS_SPAWNER = block(IterRpgModBlocks.MOURNERS_SPAWNER, null);
   public static final RegistryObject<Item> GIANT_PHANTOM_CHAIN = block(IterRpgModBlocks.GIANT_PHANTOM_CHAIN, null);
   public static final RegistryObject<Item> ESSENCE_CHUNK = REGISTRY.register("essence_chunk", () -> new EssenceChunkItem());
   public static final RegistryObject<Item> COIN_PILE = block(IterRpgModBlocks.COIN_PILE, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> COIN_BLOCK = block(IterRpgModBlocks.COIN_BLOCK, CreativeModeTab.f_40749_);
   public static final RegistryObject<Item> STINGER = REGISTRY.register("stinger", () -> new StingerItem());
   public static final RegistryObject<Item> SPELLBOOK = REGISTRY.register("spellbook", () -> new SpellbookItem());
   public static final RegistryObject<Item> SACRIFICIAL_DAGGER = REGISTRY.register("sacrificial_dagger", () -> new SacrificialDaggerItem());

   private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
      return REGISTRY.register(block.getId().m_135815_(), () -> new BlockItem((Block)block.get(), new Properties().m_41491_(tab)));
   }

   private static RegistryObject<Item> doubleBlock(RegistryObject<Block> block, CreativeModeTab tab) {
      return REGISTRY.register(block.getId().m_135815_(), () -> new DoubleHighBlockItem((Block)block.get(), new Properties().m_41491_(tab)));
   }
}
