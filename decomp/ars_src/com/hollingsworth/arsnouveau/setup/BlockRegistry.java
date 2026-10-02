package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.client.renderer.tile.AgronomicRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.AlchemicalRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.AlterationTableRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ArcaneCoreRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.BasicTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.LecternRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.MycelialRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.RedstoneRelayRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ReducerTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.RepositoryRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ScribesRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.ScryerEyeRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.TimerTurretRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.VitalicRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.VolcanicRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.WhirlisprigFlowerRenderer;
import com.hollingsworth.arsnouveau.common.block.AgronomicSourcelinkBlock;
import com.hollingsworth.arsnouveau.common.block.AlchemicalSourcelinkBlock;
import com.hollingsworth.arsnouveau.common.block.AlterationTable;
import com.hollingsworth.arsnouveau.common.block.ArcaneCore;
import com.hollingsworth.arsnouveau.common.block.ArcanePedestal;
import com.hollingsworth.arsnouveau.common.block.ArcanePlatform;
import com.hollingsworth.arsnouveau.common.block.ArchfruitPod;
import com.hollingsworth.arsnouveau.common.block.ArchwoodChest;
import com.hollingsworth.arsnouveau.common.block.BasicSpellTurret;
import com.hollingsworth.arsnouveau.common.block.BrazierRelay;
import com.hollingsworth.arsnouveau.common.block.CraftingLecternBlock;
import com.hollingsworth.arsnouveau.common.block.CreativeSourceJar;
import com.hollingsworth.arsnouveau.common.block.DirectionalModBlock;
import com.hollingsworth.arsnouveau.common.block.DrygmyStone;
import com.hollingsworth.arsnouveau.common.block.EnchantedSpellTurret;
import com.hollingsworth.arsnouveau.common.block.EnchantingApparatusBlock;
import com.hollingsworth.arsnouveau.common.block.FalseWeave;
import com.hollingsworth.arsnouveau.common.block.GhostWeave;
import com.hollingsworth.arsnouveau.common.block.ImbuementBlock;
import com.hollingsworth.arsnouveau.common.block.IntangibleAirBlock;
import com.hollingsworth.arsnouveau.common.block.ItemDetector;
import com.hollingsworth.arsnouveau.common.block.LavaLily;
import com.hollingsworth.arsnouveau.common.block.LightBlock;
import com.hollingsworth.arsnouveau.common.block.MageBlock;
import com.hollingsworth.arsnouveau.common.block.MageBloomCrop;
import com.hollingsworth.arsnouveau.common.block.MagelightTorch;
import com.hollingsworth.arsnouveau.common.block.MagicLeaves;
import com.hollingsworth.arsnouveau.common.block.MirrorWeave;
import com.hollingsworth.arsnouveau.common.block.MobJar;
import com.hollingsworth.arsnouveau.common.block.ModBlock;
import com.hollingsworth.arsnouveau.common.block.MycelialSourcelinkBlock;
import com.hollingsworth.arsnouveau.common.block.PortalBlock;
import com.hollingsworth.arsnouveau.common.block.PotionDiffuserBlock;
import com.hollingsworth.arsnouveau.common.block.PotionJar;
import com.hollingsworth.arsnouveau.common.block.PotionMelder;
import com.hollingsworth.arsnouveau.common.block.RedstoneRelay;
import com.hollingsworth.arsnouveau.common.block.Relay;
import com.hollingsworth.arsnouveau.common.block.RelayCollectorBlock;
import com.hollingsworth.arsnouveau.common.block.RelayDepositBlock;
import com.hollingsworth.arsnouveau.common.block.RelaySplitter;
import com.hollingsworth.arsnouveau.common.block.RelayWarpBlock;
import com.hollingsworth.arsnouveau.common.block.RepositoryBlock;
import com.hollingsworth.arsnouveau.common.block.RitualBrazierBlock;
import com.hollingsworth.arsnouveau.common.block.RotatingSpellTurret;
import com.hollingsworth.arsnouveau.common.block.RuneBlock;
import com.hollingsworth.arsnouveau.common.block.SconceBlock;
import com.hollingsworth.arsnouveau.common.block.ScribesBlock;
import com.hollingsworth.arsnouveau.common.block.ScryerCrystal;
import com.hollingsworth.arsnouveau.common.block.ScryersOculus;
import com.hollingsworth.arsnouveau.common.block.SkyWeave;
import com.hollingsworth.arsnouveau.common.block.SourceBerryBush;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.common.block.SpellPrismBlock;
import com.hollingsworth.arsnouveau.common.block.SpellSensor;
import com.hollingsworth.arsnouveau.common.block.StrippableLog;
import com.hollingsworth.arsnouveau.common.block.SummonBed;
import com.hollingsworth.arsnouveau.common.block.TempLightBlock;
import com.hollingsworth.arsnouveau.common.block.TemporaryBlock;
import com.hollingsworth.arsnouveau.common.block.TimerSpellTurret;
import com.hollingsworth.arsnouveau.common.block.VitalicSourcelinkBlock;
import com.hollingsworth.arsnouveau.common.block.VoidPrism;
import com.hollingsworth.arsnouveau.common.block.VolcanicSourcelinkBlock;
import com.hollingsworth.arsnouveau.common.block.WhirlisprigFlower;
import com.hollingsworth.arsnouveau.common.block.WixieCauldron;
import com.hollingsworth.arsnouveau.common.block.tile.AgronomicSourcelinkTile;
import com.hollingsworth.arsnouveau.common.block.tile.AlchemicalSourcelinkTile;
import com.hollingsworth.arsnouveau.common.block.tile.AlterationTile;
import com.hollingsworth.arsnouveau.common.block.tile.ArcaneCoreTile;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import com.hollingsworth.arsnouveau.common.block.tile.ArchwoodChestTile;
import com.hollingsworth.arsnouveau.common.block.tile.BasicSpellTurretTile;
import com.hollingsworth.arsnouveau.common.block.tile.BrazierRelayTile;
import com.hollingsworth.arsnouveau.common.block.tile.CraftingLecternTile;
import com.hollingsworth.arsnouveau.common.block.tile.CreativeSourceJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.DrygmyTile;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantedTurretTile;
import com.hollingsworth.arsnouveau.common.block.tile.EnchantingApparatusTile;
import com.hollingsworth.arsnouveau.common.block.tile.FalseWeaveTile;
import com.hollingsworth.arsnouveau.common.block.tile.GhostWeaveTile;
import com.hollingsworth.arsnouveau.common.block.tile.ImbuementTile;
import com.hollingsworth.arsnouveau.common.block.tile.IntangibleAirTile;
import com.hollingsworth.arsnouveau.common.block.tile.ItemDetectorTile;
import com.hollingsworth.arsnouveau.common.block.tile.LightTile;
import com.hollingsworth.arsnouveau.common.block.tile.MageBlockTile;
import com.hollingsworth.arsnouveau.common.block.tile.MagelightTorchTile;
import com.hollingsworth.arsnouveau.common.block.tile.MirrorWeaveTile;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.MycelialSourcelinkTile;
import com.hollingsworth.arsnouveau.common.block.tile.PortalTile;
import com.hollingsworth.arsnouveau.common.block.tile.PotionDiffuserTile;
import com.hollingsworth.arsnouveau.common.block.tile.PotionJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.PotionMelderTile;
import com.hollingsworth.arsnouveau.common.block.tile.RedstoneRelayTile;
import com.hollingsworth.arsnouveau.common.block.tile.RelayCollectorTile;
import com.hollingsworth.arsnouveau.common.block.tile.RelayDepositTile;
import com.hollingsworth.arsnouveau.common.block.tile.RelaySplitterTile;
import com.hollingsworth.arsnouveau.common.block.tile.RelayTile;
import com.hollingsworth.arsnouveau.common.block.tile.RelayWarpTile;
import com.hollingsworth.arsnouveau.common.block.tile.RepositoryTile;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import com.hollingsworth.arsnouveau.common.block.tile.RotatingTurretTile;
import com.hollingsworth.arsnouveau.common.block.tile.RuneTile;
import com.hollingsworth.arsnouveau.common.block.tile.SconceTile;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.block.tile.ScryerCrystalTile;
import com.hollingsworth.arsnouveau.common.block.tile.ScryersOculusTile;
import com.hollingsworth.arsnouveau.common.block.tile.SkyBlockTile;
import com.hollingsworth.arsnouveau.common.block.tile.SourceJarTile;
import com.hollingsworth.arsnouveau.common.block.tile.SpellSensorTile;
import com.hollingsworth.arsnouveau.common.block.tile.TempLightTile;
import com.hollingsworth.arsnouveau.common.block.tile.TemporaryTile;
import com.hollingsworth.arsnouveau.common.block.tile.TimerSpellTurretTile;
import com.hollingsworth.arsnouveau.common.block.tile.VitalicSourcelinkTile;
import com.hollingsworth.arsnouveau.common.block.tile.VolcanicSourcelinkTile;
import com.hollingsworth.arsnouveau.common.block.tile.WhirlisprigTile;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import com.hollingsworth.arsnouveau.common.items.FluidBlockItem;
import com.hollingsworth.arsnouveau.common.items.MobJarItem;
import com.hollingsworth.arsnouveau.common.items.ModBlockItem;
import com.hollingsworth.arsnouveau.common.items.RendererBlockItem;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import com.hollingsworth.arsnouveau.common.util.RegistryWrapper;
import com.hollingsworth.arsnouveau.common.world.WorldEvent;
import com.hollingsworth.arsnouveau.common.world.tree.MagicTree;
import com.hollingsworth.arsnouveau.common.world.tree.SupplierBlockStateProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "ars_nouveau");
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "ars_nouveau");
   static final String BlockRegistryKey = "minecraft:block";
   static final String BlockEntityRegistryKey = "minecraft:block_entity_type";
   static final String prepend = "ars_nouveau:";
   public static Properties LOG_PROP = Properties.m_60944_(Material.f_76320_, MaterialColor.f_76411_).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_);
   public static Properties SAP_PROP = Properties.m_60939_(Material.f_76300_).m_60910_().m_60977_().m_60966_().m_60918_(SoundType.f_56740_);
   @ObjectHolder(
      value = "ars_nouveau:mage_block",
      registryName = "minecraft:block"
   )
   public static MageBlock MAGE_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:mage_block",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<MageBlockTile> MAGE_BLOCK_TILE;
   @ObjectHolder(
      value = "ars_nouveau:light_block",
      registryName = "minecraft:block"
   )
   public static LightBlock LIGHT_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:light_block",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<LightTile> LIGHT_TILE;
   @ObjectHolder(
      value = "ars_nouveau:temporary_light_block",
      registryName = "minecraft:block"
   )
   public static LightBlock T_LIGHT_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:temporary_light_block",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<LightTile> T_LIGHT_TILE;
   @ObjectHolder(
      value = "ars_nouveau:agronomic_sourcelink",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<AgronomicSourcelinkTile> AGRONOMIC_SOURCELINK_TILE;
   @ObjectHolder(
      value = "ars_nouveau:agronomic_sourcelink",
      registryName = "minecraft:block"
   )
   public static AgronomicSourcelinkBlock AGRONOMIC_SOURCELINK;
   @ObjectHolder(
      value = "ars_nouveau:enchanting_apparatus",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<EnchantingApparatusTile> ENCHANTING_APP_TILE;
   @ObjectHolder(
      value = "ars_nouveau:enchanting_apparatus",
      registryName = "minecraft:block"
   )
   public static EnchantingApparatusBlock ENCHANTING_APP_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:source_jar",
      registryName = "minecraft:block"
   )
   public static SourceJar SOURCE_JAR;
   @ObjectHolder(
      value = "ars_nouveau:source_jar",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<SourceJarTile> SOURCE_JAR_TILE;
   @ObjectHolder(
      value = "ars_nouveau:relay",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RelayTile> ARCANE_RELAY_TILE;
   @ObjectHolder(
      value = "ars_nouveau:magebloom_crop",
      registryName = "minecraft:block"
   )
   public static MageBloomCrop MAGE_BLOOM_CROP;
   @ObjectHolder(
      value = "ars_nouveau:scribes_table",
      registryName = "minecraft:block"
   )
   public static ScribesBlock SCRIBES_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:scribes_table",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<ScribesTile> SCRIBES_TABLE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:relay",
      registryName = "minecraft:block"
   )
   public static Relay RELAY;
   @ObjectHolder(
      value = "ars_nouveau:rune",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RuneTile> RUNE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:rune",
      registryName = "minecraft:block"
   )
   public static RuneBlock RUNE_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:portal",
      registryName = "minecraft:block"
   )
   public static PortalBlock PORTAL_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:portal",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<PortalTile> PORTAL_TILE_TYPE;
   @ObjectHolder(
      value = "ars_nouveau:imbuement_chamber",
      registryName = "minecraft:block"
   )
   public static ImbuementBlock IMBUEMENT_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:imbuement_chamber",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<ImbuementTile> IMBUEMENT_TILE;
   @ObjectHolder(
      value = "ars_nouveau:relay_splitter",
      registryName = "minecraft:block"
   )
   public static RelaySplitter RELAY_SPLITTER;
   @ObjectHolder(
      value = "ars_nouveau:relay_splitter",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RelaySplitterTile> RELAY_SPLITTER_TILE;
   @ObjectHolder(
      value = "ars_nouveau:arcane_core",
      registryName = "minecraft:block"
   )
   public static ArcaneCore ARCANE_CORE_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:arcane_core",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<ArcaneCoreTile> ARCANE_CORE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:spell_turret",
      registryName = "minecraft:block"
   )
   public static EnchantedSpellTurret ENCHANTED_SPELL_TURRET;
   @ObjectHolder(
      value = "ars_nouveau:spell_turret",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<EnchantedTurretTile> ENCHANTED_SPELL_TURRET_TYPE;
   @ObjectHolder(
      value = "ars_nouveau:intangible_air",
      registryName = "minecraft:block"
   )
   public static IntangibleAirBlock INTANGIBLE_AIR;
   @ObjectHolder(
      value = "ars_nouveau:intangible_air",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<IntangibleAirTile> INTANGIBLE_AIR_TYPE;
   @ObjectHolder(
      value = "ars_nouveau:volcanic_sourcelink",
      registryName = "minecraft:block"
   )
   public static VolcanicSourcelinkBlock VOLCANIC_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:volcanic_sourcelink",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<VolcanicSourcelinkTile> VOLCANIC_TILE;
   @ObjectHolder(
      value = "ars_nouveau:lava_lily",
      registryName = "minecraft:block"
   )
   public static LavaLily LAVA_LILY;
   @ObjectHolder(
      value = "ars_nouveau:sourceberry_bush",
      registryName = "minecraft:block"
   )
   public static SourceBerryBush SOURCEBERRY_BUSH;
   @ObjectHolder(
      value = "ars_nouveau:wixie_cauldron",
      registryName = "minecraft:block"
   )
   public static WixieCauldron WIXIE_CAULDRON;
   @ObjectHolder(
      value = "ars_nouveau:wixie_cauldron",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<WixieCauldronTile> WIXIE_CAULDRON_TYPE;
   @ObjectHolder(
      value = "ars_nouveau:creative_source_jar",
      registryName = "minecraft:block"
   )
   public static CreativeSourceJar CREATIVE_SOURCE_JAR;
   @ObjectHolder(
      value = "ars_nouveau:creative_source_jar",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<CreativeSourceJarTile> CREATIVE_SOURCE_JAR_TILE;
   @ObjectHolder(
      value = "ars_nouveau:blue_archwood_log",
      registryName = "minecraft:block"
   )
   public static StrippableLog CASCADING_LOG;
   @ObjectHolder(
      value = "ars_nouveau:blue_archwood_leaves",
      registryName = "minecraft:block"
   )
   public static MagicLeaves CASCADING_LEAVE;
   @ObjectHolder(
      value = "ars_nouveau:blue_archwood_sapling",
      registryName = "minecraft:block"
   )
   public static SaplingBlock CASCADING_SAPLING;
   @ObjectHolder(
      value = "ars_nouveau:blue_archwood_wood",
      registryName = "minecraft:block"
   )
   public static StrippableLog CASCADING_WOOD;
   @ObjectHolder(
      value = "ars_nouveau:red_archwood_log",
      registryName = "minecraft:block"
   )
   public static StrippableLog BLAZING_LOG;
   @ObjectHolder(
      value = "ars_nouveau:red_archwood_leaves",
      registryName = "minecraft:block"
   )
   public static MagicLeaves BLAZING_LEAVES;
   @ObjectHolder(
      value = "ars_nouveau:red_archwood_sapling",
      registryName = "minecraft:block"
   )
   public static SaplingBlock BLAZING_SAPLING;
   @ObjectHolder(
      value = "ars_nouveau:red_archwood_wood",
      registryName = "minecraft:block"
   )
   public static StrippableLog BLAZING_WOOD;
   @ObjectHolder(
      value = "ars_nouveau:purple_archwood_log",
      registryName = "minecraft:block"
   )
   public static StrippableLog VEXING_LOG;
   @ObjectHolder(
      value = "ars_nouveau:purple_archwood_leaves",
      registryName = "minecraft:block"
   )
   public static MagicLeaves VEXING_LEAVES;
   @ObjectHolder(
      value = "ars_nouveau:purple_archwood_sapling",
      registryName = "minecraft:block"
   )
   public static SaplingBlock VEXING_SAPLING;
   @ObjectHolder(
      value = "ars_nouveau:purple_archwood_wood",
      registryName = "minecraft:block"
   )
   public static StrippableLog VEXING_WOOD;
   @ObjectHolder(
      value = "ars_nouveau:green_archwood_log",
      registryName = "minecraft:block"
   )
   public static StrippableLog FLOURISHING_LOG;
   @ObjectHolder(
      value = "ars_nouveau:green_archwood_leaves",
      registryName = "minecraft:block"
   )
   public static MagicLeaves FLOURISHING_LEAVES;
   @ObjectHolder(
      value = "ars_nouveau:green_archwood_sapling",
      registryName = "minecraft:block"
   )
   public static SaplingBlock FLOURISHING_SAPLING;
   @ObjectHolder(
      value = "ars_nouveau:green_archwood_wood",
      registryName = "minecraft:block"
   )
   public static StrippableLog FLOURISHING_WOOD;
   @ObjectHolder(
      value = "ars_nouveau:archwood_planks",
      registryName = "minecraft:block"
   )
   public static ModBlock ARCHWOOD_PLANK;
   @ObjectHolder(
      value = "ars_nouveau:archwood_button",
      registryName = "minecraft:block"
   )
   public static WoodButtonBlock ARCHWOOD_BUTTON;
   @ObjectHolder(
      value = "ars_nouveau:archwood_stairs",
      registryName = "minecraft:block"
   )
   public static StairBlock ARCHWOOD_STAIRS;
   @ObjectHolder(
      value = "ars_nouveau:archwood_slab",
      registryName = "minecraft:block"
   )
   public static SlabBlock ARCHWOOD_SLABS;
   @ObjectHolder(
      value = "ars_nouveau:archwood_fence_gate",
      registryName = "minecraft:block"
   )
   public static FenceGateBlock ARCHWOOD_FENCE_GATE;
   @ObjectHolder(
      value = "ars_nouveau:archwood_trapdoor",
      registryName = "minecraft:block"
   )
   public static TrapDoorBlock ARCHWOOD_TRAPDOOR;
   @ObjectHolder(
      value = "ars_nouveau:archwood_pressure_plate",
      registryName = "minecraft:block"
   )
   public static PressurePlateBlock ARCHWOOD_PPlate;
   @ObjectHolder(
      value = "ars_nouveau:archwood_fence",
      registryName = "minecraft:block"
   )
   public static FenceBlock ARCHWOOD_FENCE;
   @ObjectHolder(
      value = "ars_nouveau:archwood_door",
      registryName = "minecraft:block"
   )
   public static DoorBlock ARCHWOOD_DOOR;
   @ObjectHolder(
      value = "ars_nouveau:stripped_blue_archwood_log",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWLOG_BLUE;
   @ObjectHolder(
      value = "ars_nouveau:stripped_blue_archwood_wood",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWWOOD_BLUE;
   @ObjectHolder(
      value = "ars_nouveau:stripped_green_archwood_log",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWLOG_GREEN;
   @ObjectHolder(
      value = "ars_nouveau:stripped_green_archwood_wood",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWWOOD_GREEN;
   @ObjectHolder(
      value = "ars_nouveau:stripped_red_archwood_log",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWLOG_RED;
   @ObjectHolder(
      value = "ars_nouveau:stripped_red_archwood_wood",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWWOOD_RED;
   @ObjectHolder(
      value = "ars_nouveau:stripped_purple_archwood_log",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWLOG_PURPLE;
   @ObjectHolder(
      value = "ars_nouveau:stripped_purple_archwood_wood",
      registryName = "minecraft:block"
   )
   public static RotatedPillarBlock STRIPPED_AWWOOD_PURPLE;
   @ObjectHolder(
      value = "ars_nouveau:source_gem_block",
      registryName = "minecraft:block"
   )
   public static ModBlock SOURCE_GEM_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:potion_jar",
      registryName = "minecraft:block"
   )
   public static PotionJar POTION_JAR;
   @ObjectHolder(
      value = "ars_nouveau:potion_jar",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<PotionJarTile> POTION_JAR_TYPE;
   @ObjectHolder(
      value = "ars_nouveau:potion_melder",
      registryName = "minecraft:block"
   )
   public static PotionMelder POTION_MELDER;
   @ObjectHolder(
      value = "ars_nouveau:potion_melder",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<PotionMelderTile> POTION_MELDER_TYPE;
   @ObjectHolder(
      value = "ars_nouveau:sconce",
      registryName = "minecraft:block"
   )
   public static SconceBlock SCONCE_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:sconce",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<SconceTile> SCONCE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:drygmy_stone",
      registryName = "minecraft:block"
   )
   public static DrygmyStone DRYGMY_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:drygmy_stone",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<DrygmyTile> DRYGMY_TILE;
   @ObjectHolder(
      value = "ars_nouveau:alchemical_sourcelink",
      registryName = "minecraft:block"
   )
   public static AlchemicalSourcelinkBlock ALCHEMICAL_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:alchemical_sourcelink",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<AlchemicalSourcelinkTile> ALCHEMICAL_TILE;
   @ObjectHolder(
      value = "ars_nouveau:vitalic_sourcelink",
      registryName = "minecraft:block"
   )
   public static VitalicSourcelinkBlock VITALIC_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:vitalic_sourcelink",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<VitalicSourcelinkTile> VITALIC_TILE;
   @ObjectHolder(
      value = "ars_nouveau:mycelial_sourcelink",
      registryName = "minecraft:block"
   )
   public static MycelialSourcelinkBlock MYCELIAL_BLOCK;
   @ObjectHolder(
      value = "ars_nouveau:mycelial_sourcelink",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<MycelialSourcelinkTile> MYCELIAL_TILE;
   @ObjectHolder(
      value = "ars_nouveau:relay_deposit",
      registryName = "minecraft:block"
   )
   public static RelayDepositBlock RELAY_DEPOSIT;
   @ObjectHolder(
      value = "ars_nouveau:relay_deposit",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RelayDepositTile> RELAY_DEPOSIT_TILE;
   @ObjectHolder(
      value = "ars_nouveau:relay_warp",
      registryName = "minecraft:block"
   )
   public static RelayWarpBlock RELAY_WARP;
   @ObjectHolder(
      value = "ars_nouveau:relay_warp",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RelayWarpTile> RELAY_WARP_TILE;
   @ObjectHolder(
      value = "ars_nouveau:basic_spell_turret",
      registryName = "minecraft:block"
   )
   public static BasicSpellTurret BASIC_SPELL_TURRET;
   @ObjectHolder(
      value = "ars_nouveau:basic_spell_turret",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<BasicSpellTurretTile> BASIC_SPELL_TURRET_TILE;
   @ObjectHolder(
      value = "ars_nouveau:timer_spell_turret",
      registryName = "minecraft:block"
   )
   public static TimerSpellTurret TIMER_SPELL_TURRET;
   @ObjectHolder(
      value = "ars_nouveau:timer_spell_turret",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<TimerSpellTurretTile> TIMER_SPELL_TURRET_TILE;
   @ObjectHolder(
      value = "ars_nouveau:archwood_chest",
      registryName = "minecraft:block"
   )
   public static ArchwoodChest ARCHWOOD_CHEST;
   @ObjectHolder(
      value = "ars_nouveau:archwood_chest",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<ArchwoodChestTile> ARCHWOOD_CHEST_TILE;
   @ObjectHolder(
      value = "ars_nouveau:spell_prism",
      registryName = "minecraft:block"
   )
   public static SpellPrismBlock SPELL_PRISM;
   @ObjectHolder(
      value = "ars_nouveau:whirlisprig_flower",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<WhirlisprigTile> WHIRLISPRIG_TILE;
   @ObjectHolder(
      value = "ars_nouveau:whirlisprig_flower",
      registryName = "minecraft:block"
   )
   public static WhirlisprigFlower WHIRLISPRIG_FLOWER;
   @ObjectHolder(
      value = "ars_nouveau:relay_collector",
      registryName = "minecraft:block"
   )
   public static RelayCollectorBlock RELAY_COLLECTOR;
   @ObjectHolder(
      value = "ars_nouveau:relay_collector",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RelayCollectorTile> RELAY_COLLECTOR_TILE;
   @ObjectHolder(
      value = "ars_nouveau:red_sbed",
      registryName = "minecraft:block"
   )
   public static SummonBed RED_SBED;
   @ObjectHolder(
      value = "ars_nouveau:blue_sbed",
      registryName = "minecraft:block"
   )
   public static SummonBed BLUE_SBED;
   @ObjectHolder(
      value = "ars_nouveau:green_sbed",
      registryName = "minecraft:block"
   )
   public static SummonBed GREEN_SBED;
   @ObjectHolder(
      value = "ars_nouveau:orange_sbed",
      registryName = "minecraft:block"
   )
   public static SummonBed ORANGE_SBED;
   @ObjectHolder(
      value = "ars_nouveau:yellow_sbed",
      registryName = "minecraft:block"
   )
   public static SummonBed YELLOW_SBED;
   @ObjectHolder(
      value = "ars_nouveau:purple_sbed",
      registryName = "minecraft:block"
   )
   public static SummonBed PURPLE_SBED;
   @ObjectHolder(
      value = "ars_nouveau:an_stateprovider",
      registryName = "minecraft:worldgen/block_state_provider_type"
   )
   public static BlockStateProviderType<?> stateProviderType;
   @ObjectHolder(
      value = "ars_nouveau:scryers_oculus",
      registryName = "minecraft:block"
   )
   public static ScryersOculus SCRYERS_OCULUS;
   @ObjectHolder(
      value = "ars_nouveau:scryers_oculus",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<ScryersOculusTile> SCRYERS_OCULUS_TILE;
   @ObjectHolder(
      value = "ars_nouveau:scryers_crystal",
      registryName = "minecraft:block"
   )
   public static ScryerCrystal SCRYERS_CRYSTAL;
   @ObjectHolder(
      value = "ars_nouveau:scryers_crystal",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<ScryerCrystalTile> SCRYER_CRYSTAL_TILE;
   @ObjectHolder(
      value = "ars_nouveau:mendosteen_pod",
      registryName = "minecraft:block"
   )
   public static ArchfruitPod MENDOSTEEN_POD;
   @ObjectHolder(
      value = "ars_nouveau:bastion_pod",
      registryName = "minecraft:block"
   )
   public static ArchfruitPod BASTION_POD;
   @ObjectHolder(
      value = "ars_nouveau:frostaya_pod",
      registryName = "minecraft:block"
   )
   public static ArchfruitPod FROSTAYA_POD;
   @ObjectHolder(
      value = "ars_nouveau:bombegranate_pod",
      registryName = "minecraft:block"
   )
   public static ArchfruitPod BOMBEGRANTE_POD;
   @ObjectHolder(
      value = "ars_nouveau:potion_diffuser",
      registryName = "minecraft:block"
   )
   public static PotionDiffuserBlock POTION_DIFFUSER;
   @ObjectHolder(
      value = "ars_nouveau:potion_diffuser",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<PotionDiffuserTile> POTION_DIFFUSER_TILE;
   @ObjectHolder(
      value = "ars_nouveau:alteration_table",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<AlterationTile> ARMOR_TILE;
   @ObjectHolder(
      value = "ars_nouveau:alteration_table",
      registryName = "minecraft:block"
   )
   public static AlterationTable ALTERATION_TABLE;
   @ObjectHolder(
      value = "ars_nouveau:mob_jar",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<MobJarTile> MOB_JAR_TILE;
   @ObjectHolder(
      value = "ars_nouveau:mob_jar",
      registryName = "minecraft:block"
   )
   public static MobJar MOB_JAR;
   @ObjectHolder(
      value = "ars_nouveau:void_prism",
      registryName = "minecraft:block"
   )
   public static VoidPrism VOID_PRISM;
   @ObjectHolder(
      value = "ars_nouveau:repository",
      registryName = "minecraft:block"
   )
   public static RepositoryBlock REPOSITORY;
   @ObjectHolder(
      value = "ars_nouveau:repository",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<RepositoryTile> REPOSITORY_TILE;
   @ObjectHolder(
      value = "ars_nouveau:falseweave",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<FalseWeaveTile> FALSE_WEAVE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:falseweave",
      registryName = "minecraft:block"
   )
   public static FalseWeave FALSE_WEAVE;
   @ObjectHolder(
      value = "ars_nouveau:mirrorweave",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<MirrorWeaveTile> MIRROR_WEAVE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:mirrorweave",
      registryName = "minecraft:block"
   )
   public static MirrorWeave MIRROR_WEAVE;
   @ObjectHolder(
      value = "ars_nouveau:ghostweave",
      registryName = "minecraft:block_entity_type"
   )
   public static BlockEntityType<GhostWeaveTile> GHOST_WEAVE_TILE;
   @ObjectHolder(
      value = "ars_nouveau:ghostweave",
      registryName = "minecraft:block"
   )
   public static GhostWeave GHOST_WEAVE;
   @ObjectHolder(
      value = "ars_nouveau:magebloom_block",
      registryName = "minecraft:block"
   )
   public static ModBlock MAGEBLOOM_BLOCK;
   static Properties woodProp = Properties.m_60944_(Material.f_76320_, MaterialColor.f_76411_).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_);
   public static final RegistryWrapper<Block> ROTATING_TURRET = registerBlock("rotating_spell_turret", RotatingSpellTurret::new);
   public static final RegistryObject<BlockEntityType<?>> ROTATING_TURRET_TILE = BLOCK_ENTITIES.register(
      "rotating_spell_turret", () -> Builder.m_155273_(RotatingTurretTile::new, new Block[]{ROTATING_TURRET.get()}).m_58966_(null)
   );
   public static final RegistryWrapper<ArcanePlatform> ARCANE_PLATFORM = registerBlock("arcane_platform", ArcanePlatform::new);
   public static final RegistryWrapper<MagelightTorch> MAGELIGHT_TORCH = registerBlock("magelight_torch", MagelightTorch::new);
   public static final RegistryWrapper<BrazierRelay> BRAZIER_RELAY = registerBlock("brazier_relay", BrazierRelay::new);
   public static final RegistryWrapper<CraftingLecternBlock> CRAFTING_LECTERN = registerBlock("storage_lectern", CraftingLecternBlock::new);
   public static RegistryObject<BlockEntityType<ArcanePedestalTile>> ARCANE_PEDESTAL_TILE = BLOCK_ENTITIES.register(
      "arcane_pedestal",
      () -> Builder.m_155273_(ArcanePedestalTile::new, new Block[]{BlockRegistry.ARCANE_PEDESTAL.get(), ARCANE_PLATFORM.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<MagelightTorchTile>> MAGELIGHT_TORCH_TILE = BLOCK_ENTITIES.register(
      "magelight_torch", () -> Builder.m_155273_(MagelightTorchTile::new, new Block[]{MAGELIGHT_TORCH.get()}).m_58966_(null)
   );
   public static RegistryWrapper<ArcanePedestal> ARCANE_PEDESTAL = registerBlock("arcane_pedestal", ArcanePedestal::new);
   public static RegistryWrapper<RitualBrazierBlock> RITUAL_BLOCK = registerBlock("ritual_brazier", RitualBrazierBlock::new);
   public static RegistryWrapper<SkyWeave> SKY_WEAVE = registerBlock(
      "sky_block", () -> new SkyWeave(Properties.m_60939_(Material.f_76299_).m_60978_(0.1F).m_60918_(SoundType.f_56745_).m_60955_())
   );
   public static RegistryWrapper<TemporaryBlock> TEMPORARY_BLOCK = registerBlock(
      "temporary_block", () -> new TemporaryBlock(Properties.m_60939_(Material.f_76278_).m_60913_(1.5F, 6.0F).m_60918_(SoundType.f_56742_))
   );
   public static RegistryWrapper<ItemDetector> ITEM_DETECTOR = registerBlock("item_detector", ItemDetector::new);
   public static RegistryWrapper<SpellSensor> SPELL_SENSOR = registerBlock("spell_sensor", SpellSensor::new);
   public static RegistryWrapper<RedstoneRelay> REDSTONE_RELAY = registerBlock("redstone_relay", RedstoneRelay::new);
   public static RegistryObject<BlockEntityType<RitualBrazierTile>> RITUAL_TILE = BLOCK_ENTITIES.register(
      "ritual_brazier", () -> Builder.m_155273_(RitualBrazierTile::new, new Block[]{RITUAL_BLOCK.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<BrazierRelayTile>> BRAZIER_RELAY_TILE = BLOCK_ENTITIES.register(
      "brazier_relay", () -> Builder.m_155273_(BrazierRelayTile::new, new Block[]{BRAZIER_RELAY.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<SkyBlockTile>> SKYWEAVE_TILE = BLOCK_ENTITIES.register(
      "sky_block", () -> Builder.m_155273_(SkyBlockTile::new, new Block[]{SKY_WEAVE.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<TemporaryTile>> TEMPORARY_TILE = BLOCK_ENTITIES.register(
      "temporary_block", () -> Builder.m_155273_(TemporaryTile::new, new Block[]{TEMPORARY_BLOCK.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<CraftingLecternTile>> CRAFTING_LECTERN_TILE = BLOCK_ENTITIES.register(
      "storage_lectern", () -> Builder.m_155273_(CraftingLecternTile::new, new Block[]{CRAFTING_LECTERN.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<ItemDetectorTile>> ITEM_DETECTOR_TILE = BLOCK_ENTITIES.register(
      "item_detector", () -> Builder.m_155273_(ItemDetectorTile::new, new Block[]{ITEM_DETECTOR.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<SpellSensorTile>> SPELL_SENSOR_TILE = BLOCK_ENTITIES.register(
      "spell_sensor", () -> Builder.m_155273_(SpellSensorTile::new, new Block[]{SPELL_SENSOR.get()}).m_58966_(null)
   );
   public static RegistryObject<BlockEntityType<RedstoneRelayTile>> REDSTONE_RELAY_TILE = BLOCK_ENTITIES.register(
      "redstone_relay", () -> Builder.m_155273_(RedstoneRelayTile::new, new Block[]{REDSTONE_RELAY.get()}).m_58966_(null)
   );
   public static RegistryObject<ModBlockItem> SPELL_SENSOR_ITEM = ItemsRegistry.ITEMS.register("spell_sensor", () -> getDefaultBlockItem(SPELL_SENSOR.get()));
   public static RegistryObject<ModBlockItem> REDSTONE_RELAY_ITEM = ItemsRegistry.ITEMS
      .register("redstone_relay", () -> new RendererBlockItem(REDSTONE_RELAY.get(), ItemsRegistry.defaultItemProperties()) {
            @Override
            public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
               return RedstoneRelayRenderer::getISTER;
            }
         });
   public static final Map<Supplier<ResourceLocation>, FlowerPotBlock> flowerPots = new HashMap<>();

   public static void onBlocksRegistry(IForgeRegistry<Block> registry) {
      registry.register("mage_block", new MageBlock());
      registry.register("light_block", new LightBlock());
      registry.register("temporary_light_block", new TempLightBlock());
      registry.register("source_jar", new SourceJar());
      registry.register("creative_source_jar", new CreativeSourceJar());
      registry.register("scribes_table", new ScribesBlock());
      registry.register("magebloom_crop", new MageBloomCrop());
      registry.register("imbuement_chamber", new ImbuementBlock());
      registry.register("enchanting_apparatus", new EnchantingApparatusBlock());
      registry.register("arcane_core", new ArcaneCore());
      registry.register("rune", new RuneBlock());
      registry.register("portal", new PortalBlock());
      registry.register("spell_prism", new SpellPrismBlock());
      registry.register("relay", new Relay());
      registry.register("relay_splitter", new RelaySplitter());
      registry.register("relay_deposit", new RelayDepositBlock());
      registry.register("relay_warp", new RelayWarpBlock());
      registry.register("relay_collector", new RelayCollectorBlock());
      registry.register("basic_spell_turret", new BasicSpellTurret());
      registry.register("timer_spell_turret", new TimerSpellTurret());
      registry.register("spell_turret", new EnchantedSpellTurret());
      registry.register("scryers_oculus", new ScryersOculus());
      registry.register("scryers_crystal", new ScryerCrystal());
      registry.register("intangible_air", new IntangibleAirBlock());
      registry.register("lava_lily", new LavaLily());
      registry.register("sourceberry_bush", new SourceBerryBush(Properties.m_60939_(Material.f_76300_).m_60977_().m_60910_().m_60918_(SoundType.f_56757_)));
      registry.register("blue_archwood_sapling", new SaplingBlock(new MagicTree(() -> WorldEvent.CASCADING_TREE), SAP_PROP));
      registry.register("red_archwood_sapling", new SaplingBlock(new MagicTree(() -> WorldEvent.BLAZING_TREE), SAP_PROP));
      registry.register("purple_archwood_sapling", new SaplingBlock(new MagicTree(() -> WorldEvent.VEXING_TREE), SAP_PROP));
      registry.register("green_archwood_sapling", new SaplingBlock(new MagicTree(() -> WorldEvent.FLOURISHING_TREE), SAP_PROP));
      registry.register("blue_archwood_log", new StrippableLog(LOG_PROP, () -> STRIPPED_AWLOG_BLUE));
      registry.register("blue_archwood_leaves", createLeavesBlock(MaterialColor.f_76361_));
      registry.register("red_archwood_log", new StrippableLog(LOG_PROP, () -> STRIPPED_AWLOG_RED));
      registry.register("red_archwood_leaves", createLeavesBlock(MaterialColor.f_76364_));
      registry.register("green_archwood_log", new StrippableLog(LOG_PROP, () -> STRIPPED_AWLOG_GREEN));
      registry.register("green_archwood_leaves", createLeavesBlock(MaterialColor.f_76417_));
      registry.register("purple_archwood_log", new StrippableLog(LOG_PROP, () -> STRIPPED_AWLOG_PURPLE));
      registry.register("purple_archwood_leaves", createLeavesBlock(MaterialColor.f_76422_));
      registry.register("purple_archwood_wood", new StrippableLog(LOG_PROP, () -> STRIPPED_AWWOOD_PURPLE));
      registry.register("blue_archwood_wood", new StrippableLog(LOG_PROP, () -> STRIPPED_AWWOOD_BLUE));
      registry.register("green_archwood_wood", new StrippableLog(LOG_PROP, () -> STRIPPED_AWWOOD_GREEN));
      registry.register("red_archwood_wood", new StrippableLog(LOG_PROP, () -> STRIPPED_AWWOOD_RED));
      registry.register("archwood_planks", new ModBlock(LOG_PROP));
      registry.register("archwood_button", new WoodButtonBlock(Properties.m_60939_(Material.f_76310_).m_60910_().m_60978_(0.5F).m_60918_(SoundType.f_56736_)));
      registry.register("archwood_stairs", new StairBlock(() -> ARCHWOOD_PLANK.m_49966_(), woodProp));
      registry.register("archwood_slab", new SlabBlock(woodProp));
      registry.register("archwood_fence_gate", new FenceGateBlock(woodProp));
      registry.register("archwood_fence", new FenceBlock(woodProp));
      registry.register("archwood_door", new DoorBlock(woodProp));
      registry.register("archwood_pressure_plate", new PressurePlateBlock(Sensitivity.EVERYTHING, woodProp));
      registry.register("archwood_trapdoor", new TrapDoorBlock(woodProp));
      registry.register("archwood_chest", new ArchwoodChest());
      registry.register("stripped_blue_archwood_log", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_blue_archwood_wood", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_green_archwood_log", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_green_archwood_wood", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_red_archwood_log", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_red_archwood_wood", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_purple_archwood_log", new RotatedPillarBlock(LOG_PROP));
      registry.register("stripped_purple_archwood_wood", new RotatedPillarBlock(LOG_PROP));
      registry.register("source_gem_block", new ModBlock(ModBlock.defaultProperties().m_60955_().m_60953_(sx -> 6)));
      registry.register("potion_jar", new PotionJar(ModBlock.defaultProperties().m_60955_()));
      registry.register("potion_melder", new PotionMelder(ModBlock.defaultProperties().m_60955_()));
      registry.register("alchemical_sourcelink", new AlchemicalSourcelinkBlock());
      registry.register("agronomic_sourcelink", new AgronomicSourcelinkBlock());
      registry.register("vitalic_sourcelink", new VitalicSourcelinkBlock());
      registry.register("mycelial_sourcelink", new MycelialSourcelinkBlock());
      registry.register("volcanic_sourcelink", new VolcanicSourcelinkBlock());
      registry.register("wixie_cauldron", new WixieCauldron());
      registry.register("whirlisprig_flower", new WhirlisprigFlower());
      registry.register("sconce", new SconceBlock());
      registry.register("drygmy_stone", new DrygmyStone());
      registry.register("red_sbed", new SummonBed());
      registry.register("blue_sbed", new SummonBed());
      registry.register("green_sbed", new SummonBed());
      registry.register("orange_sbed", new SummonBed());
      registry.register("yellow_sbed", new SummonBed());
      registry.register("purple_sbed", new SummonBed());
      registry.register("mendosteen_pod", new ArchfruitPod((Supplier<Block>)(() -> FLOURISHING_LOG)));
      registry.register("bastion_pod", new ArchfruitPod((Supplier<Block>)(() -> VEXING_LOG)));
      registry.register("frostaya_pod", new ArchfruitPod((Supplier<Block>)(() -> CASCADING_LOG)));
      registry.register("bombegranate_pod", new ArchfruitPod((Supplier<Block>)(() -> BLAZING_LOG)));
      registry.register("potion_diffuser", new PotionDiffuserBlock());

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         if (LibBlockNames.DIRECTIONAL_SOURCESTONE.contains(s)) {
            registry.register(s, new DirectionalModBlock());
         } else {
            registry.register(s, new ModBlock());
         }
      }

      for (String sx : LibBlockNames.DECORATIVE_SLABS) {
         registry.register(sx, new SlabBlock(Properties.m_60939_(Material.f_76278_).m_60913_(1.5F, 6.0F).m_60918_(SoundType.f_56742_)));
      }

      for (String sx : LibBlockNames.DECORATIVE_SOURCESTONE) {
         registry.register(
            sx + "_stairs",
            new StairBlock(
               () -> ((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s))).m_49966_(),
               Properties.m_60939_(Material.f_76278_).m_60913_(1.5F, 6.0F).m_60918_(SoundType.f_56742_)
            )
         );
      }

      registry.register("alteration_table", new AlterationTable());
      registry.register("mob_jar", new MobJar());
      registry.register("void_prism", new VoidPrism());
      registry.register("repository", new RepositoryBlock());
      registry.register("mirrorweave", new MirrorWeave(Properties.m_60939_(Material.f_76299_).m_60978_(0.1F).m_60918_(SoundType.f_56745_).m_60955_()));
      registry.register("ghostweave", new GhostWeave(Properties.m_60939_(Material.f_76299_).m_60978_(0.1F).m_60918_(SoundType.f_56745_).m_60955_()));
      registry.register("falseweave", new FalseWeave(Properties.m_60939_(Material.f_76299_).m_60978_(0.1F).m_60918_(SoundType.f_56745_).m_60955_().m_60910_()));
      registry.register(
         "magebloom_block", new ModBlock(Properties.m_60944_(Material.f_76299_, MaterialColor.f_76418_).m_60978_(0.1F).m_60918_(SoundType.f_56745_))
      );
      registry.register(LibBlockNames.Pot("magebloom_crop"), createPottedBlock(() -> MAGE_BLOOM_CROP));
      registry.register(LibBlockNames.Pot("red_archwood_sapling"), createPottedBlock(() -> BLAZING_SAPLING));
      registry.register(LibBlockNames.Pot("blue_archwood_sapling"), createPottedBlock(() -> CASCADING_SAPLING));
      registry.register(LibBlockNames.Pot("green_archwood_sapling"), createPottedBlock(() -> FLOURISHING_SAPLING));
      registry.register(LibBlockNames.Pot("purple_archwood_sapling"), createPottedBlock(() -> VEXING_SAPLING));
   }

   public static MagicLeaves createLeavesBlock(MaterialColor color) {
      return new MagicLeaves(
         Properties.m_60939_(Material.f_76274_)
            .m_155949_(color)
            .m_60978_(0.2F)
            .m_60977_()
            .m_60918_(SoundType.f_56740_)
            .m_60955_()
            .m_60922_(BlockRegistry::allowsSpawnOnLeaves)
            .m_60960_(BlockRegistry::isntSolid)
            .m_60971_(BlockRegistry::isntSolid)
      );
   }

   public static void onTileEntityRegistry(IForgeRegistry<BlockEntityType<?>> registry) {
      registry.register("mage_block", Builder.m_155273_(MageBlockTile::new, new Block[]{MAGE_BLOCK}).m_58966_(null));
      registry.register("agronomic_sourcelink", Builder.m_155273_(AgronomicSourcelinkTile::new, new Block[]{AGRONOMIC_SOURCELINK}).m_58966_(null));
      registry.register("source_jar", Builder.m_155273_(SourceJarTile::new, new Block[]{SOURCE_JAR}).m_58966_(null));
      registry.register("light_block", Builder.m_155273_(LightTile::new, new Block[]{LIGHT_BLOCK}).m_58966_(null));
      registry.register("temporary_light_block", Builder.m_155273_(TempLightTile::new, new Block[]{T_LIGHT_BLOCK}).m_58966_(null));
      registry.register("enchanting_apparatus", Builder.m_155273_(EnchantingApparatusTile::new, new Block[]{ENCHANTING_APP_BLOCK}).m_58966_(null));
      registry.register("scribes_table", Builder.m_155273_(ScribesTile::new, new Block[]{SCRIBES_BLOCK}).m_58966_(null));
      registry.register("relay", Builder.m_155273_(RelayTile::new, new Block[]{RELAY}).m_58966_(null));
      registry.register("rune", Builder.m_155273_(RuneTile::new, new Block[]{RUNE_BLOCK}).m_58966_(null));
      registry.register("portal", Builder.m_155273_(PortalTile::new, new Block[]{PORTAL_BLOCK}).m_58966_(null));
      registry.register("relay_splitter", Builder.m_155273_(RelaySplitterTile::new, new Block[]{RELAY_SPLITTER}).m_58966_(null));
      registry.register("arcane_core", Builder.m_155273_(ArcaneCoreTile::new, new Block[]{ARCANE_CORE_BLOCK}).m_58966_(null));
      registry.register("imbuement_chamber", Builder.m_155273_(ImbuementTile::new, new Block[]{IMBUEMENT_BLOCK}).m_58966_(null));
      registry.register("spell_turret", Builder.m_155273_(EnchantedTurretTile::new, new Block[]{ENCHANTED_SPELL_TURRET}).m_58966_(null));
      registry.register("intangible_air", Builder.m_155273_(IntangibleAirTile::new, new Block[]{INTANGIBLE_AIR}).m_58966_(null));
      registry.register("volcanic_sourcelink", Builder.m_155273_(VolcanicSourcelinkTile::new, new Block[]{VOLCANIC_BLOCK}).m_58966_(null));
      registry.register("wixie_cauldron", Builder.m_155273_(WixieCauldronTile::new, new Block[]{WIXIE_CAULDRON}).m_58966_(null));
      registry.register("creative_source_jar", Builder.m_155273_(CreativeSourceJarTile::new, new Block[]{CREATIVE_SOURCE_JAR}).m_58966_(null));
      registry.register("potion_jar", Builder.m_155273_(PotionJarTile::new, new Block[]{POTION_JAR}).m_58966_(null));
      registry.register("potion_melder", Builder.m_155273_(PotionMelderTile::new, new Block[]{POTION_MELDER}).m_58966_(null));
      registry.register("sconce", Builder.m_155273_(SconceTile::new, new Block[]{SCONCE_BLOCK}).m_58966_(null));
      registry.register("drygmy_stone", Builder.m_155273_(DrygmyTile::new, new Block[]{DRYGMY_BLOCK}).m_58966_(null));
      registry.register("alchemical_sourcelink", Builder.m_155273_(AlchemicalSourcelinkTile::new, new Block[]{ALCHEMICAL_BLOCK}).m_58966_(null));
      registry.register("vitalic_sourcelink", Builder.m_155273_(VitalicSourcelinkTile::new, new Block[]{VITALIC_BLOCK}).m_58966_(null));
      registry.register("mycelial_sourcelink", Builder.m_155273_(MycelialSourcelinkTile::new, new Block[]{MYCELIAL_BLOCK}).m_58966_(null));
      registry.register("relay_deposit", Builder.m_155273_(RelayDepositTile::new, new Block[]{RELAY_DEPOSIT}).m_58966_(null));
      registry.register("relay_warp", Builder.m_155273_(RelayWarpTile::new, new Block[]{RELAY_WARP}).m_58966_(null));
      registry.register("basic_spell_turret", Builder.m_155273_(BasicSpellTurretTile::new, new Block[]{BASIC_SPELL_TURRET}).m_58966_(null));
      registry.register("timer_spell_turret", Builder.m_155273_(TimerSpellTurretTile::new, new Block[]{TIMER_SPELL_TURRET}).m_58966_(null));
      registry.register("archwood_chest", Builder.m_155273_(ArchwoodChestTile::new, new Block[]{ARCHWOOD_CHEST}).m_58966_(null));
      registry.register("whirlisprig_flower", Builder.m_155273_(WhirlisprigTile::new, new Block[]{WHIRLISPRIG_FLOWER}).m_58966_(null));
      registry.register("relay_collector", Builder.m_155273_(RelayCollectorTile::new, new Block[]{RELAY_COLLECTOR}).m_58966_(null));
      registry.register("scryers_oculus", Builder.m_155273_(ScryersOculusTile::new, new Block[]{SCRYERS_OCULUS}).m_58966_(null));
      registry.register("scryers_crystal", Builder.m_155273_(ScryerCrystalTile::new, new Block[]{SCRYERS_CRYSTAL}).m_58966_(null));
      registry.register("potion_diffuser", Builder.m_155273_(PotionDiffuserTile::new, new Block[]{POTION_DIFFUSER}).m_58966_(null));
      registry.register("alteration_table", Builder.m_155273_(AlterationTile::new, new Block[]{ALTERATION_TABLE}).m_58966_(null));
      registry.register("mob_jar", Builder.m_155273_(MobJarTile::new, new Block[]{MOB_JAR}).m_58966_(null));
      registry.register("repository", Builder.m_155273_(RepositoryTile::new, new Block[]{REPOSITORY}).m_58966_(null));
      registry.register("falseweave", Builder.m_155273_(FalseWeaveTile::new, new Block[]{FALSE_WEAVE}).m_58966_(null));
      registry.register("mirrorweave", Builder.m_155273_(MirrorWeaveTile::new, new Block[]{MIRROR_WEAVE}).m_58966_(null));
      registry.register("ghostweave", Builder.m_155273_(GhostWeaveTile::new, new Block[]{GHOST_WEAVE}).m_58966_(null));
   }

   public static void onBlockItemsRegistry(IForgeRegistry<Item> registry) {
      registry.register("source_berry", new BlockItem(SOURCEBERRY_BUSH, ItemsRegistry.defaultItemProperties().m_41489_(ItemsRegistry.SOURCE_BERRY_FOOD)));
      registry.register("mage_block", new BlockItem(MAGE_BLOCK, ItemsRegistry.defaultItemProperties()));
      registry.register("light_block", new BlockItem(LIGHT_BLOCK, new net.minecraft.world.item.Item.Properties()));
      registry.register("agronomic_sourcelink", new RendererBlockItem(AGRONOMIC_SOURCELINK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return AgronomicRenderer::getISTER;
         }
      });
      registry.register("source_jar", new BlockItem(SOURCE_JAR, ItemsRegistry.defaultItemProperties()));
      registry.register("magebloom_crop", new BlockItem(MAGE_BLOOM_CROP, ItemsRegistry.defaultItemProperties()));
      registry.register("enchanting_apparatus", new RendererBlockItem(ENCHANTING_APP_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("enchanting_apparatus");
         }
      });
      registry.register("scribes_table", new RendererBlockItem(SCRIBES_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return ScribesRenderer::getISTER;
         }
      });
      registry.register("relay", new RendererBlockItem(RELAY, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("source_relay");
         }
      });
      registry.register("relay_splitter", new RendererBlockItem(RELAY_SPLITTER, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("source_splitter");
         }
      });
      registry.register("imbuement_chamber", new RendererBlockItem(IMBUEMENT_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("imbuement_chamber");
         }
      });
      registry.register("arcane_core", new RendererBlockItem(ARCANE_CORE_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return ArcaneCoreRenderer::getISTER;
         }
      });
      registry.register("volcanic_sourcelink", new RendererBlockItem(VOLCANIC_BLOCK, ItemsRegistry.defaultItemProperties().m_41486_()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return VolcanicRenderer::getISTER;
         }
      });
      registry.register("lava_lily", new FluidBlockItem(LAVA_LILY, ItemsRegistry.defaultItemProperties().m_41486_()));
      registry.register("wixie_cauldron", new BlockItem(WIXIE_CAULDRON, ItemsRegistry.defaultItemProperties()));
      registry.register("creative_source_jar", new BlockItem(CREATIVE_SOURCE_JAR, ItemsRegistry.defaultItemProperties()));
      registry.register("relay_warp", new RendererBlockItem(RELAY_WARP, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("source_warp");
         }
      });
      registry.register("relay_deposit", new RendererBlockItem(RELAY_DEPOSIT, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("source_deposit");
         }
      });
      registry.register("blue_archwood_leaves", getDefaultBlockItem(CASCADING_LEAVE));
      registry.register("blue_archwood_log", getDefaultBlockItem(CASCADING_LOG));
      registry.register("blue_archwood_sapling", getDefaultBlockItem(CASCADING_SAPLING));
      registry.register("blue_archwood_wood", getDefaultBlockItem(CASCADING_WOOD));
      registry.register("purple_archwood_leaves", getDefaultBlockItem(VEXING_LEAVES));
      registry.register("purple_archwood_log", getDefaultBlockItem(VEXING_LOG));
      registry.register("purple_archwood_sapling", getDefaultBlockItem(VEXING_SAPLING));
      registry.register("purple_archwood_wood", getDefaultBlockItem(VEXING_WOOD));
      registry.register("green_archwood_leaves", getDefaultBlockItem(FLOURISHING_LEAVES));
      registry.register("green_archwood_log", getDefaultBlockItem(FLOURISHING_LOG));
      registry.register("green_archwood_sapling", getDefaultBlockItem(FLOURISHING_SAPLING));
      registry.register("green_archwood_wood", getDefaultBlockItem(FLOURISHING_WOOD));
      registry.register("red_archwood_leaves", getDefaultBlockItem(BLAZING_LEAVES));
      registry.register("red_archwood_log", getDefaultBlockItem(BLAZING_LOG));
      registry.register("red_archwood_sapling", getDefaultBlockItem(BLAZING_SAPLING));
      registry.register("red_archwood_wood", getDefaultBlockItem(BLAZING_WOOD));
      registry.register("archwood_planks", getDefaultBlockItem(ARCHWOOD_PLANK));
      registry.register("archwood_button", getDefaultBlockItem(ARCHWOOD_BUTTON));
      registry.register("archwood_stairs", getDefaultBlockItem(ARCHWOOD_STAIRS));
      registry.register("archwood_slab", getDefaultBlockItem(ARCHWOOD_SLABS));
      registry.register("archwood_fence_gate", getDefaultBlockItem(ARCHWOOD_FENCE_GATE));
      registry.register("archwood_trapdoor", getDefaultBlockItem(ARCHWOOD_TRAPDOOR));
      registry.register("archwood_pressure_plate", getDefaultBlockItem(ARCHWOOD_PPlate));
      registry.register("archwood_fence", getDefaultBlockItem(ARCHWOOD_FENCE));
      registry.register("archwood_door", getDefaultBlockItem(ARCHWOOD_DOOR));
      registry.register("stripped_blue_archwood_log", getDefaultBlockItem(STRIPPED_AWLOG_BLUE));
      registry.register("stripped_blue_archwood_wood", getDefaultBlockItem(STRIPPED_AWWOOD_BLUE));
      registry.register("stripped_green_archwood_log", getDefaultBlockItem(STRIPPED_AWLOG_GREEN));
      registry.register("stripped_green_archwood_wood", getDefaultBlockItem(STRIPPED_AWWOOD_GREEN));
      registry.register("stripped_red_archwood_log", getDefaultBlockItem(STRIPPED_AWLOG_RED));
      registry.register("stripped_red_archwood_wood", getDefaultBlockItem(STRIPPED_AWWOOD_RED));
      registry.register("stripped_purple_archwood_log", getDefaultBlockItem(STRIPPED_AWLOG_PURPLE));
      registry.register("stripped_purple_archwood_wood", getDefaultBlockItem(STRIPPED_AWWOOD_PURPLE));
      registry.register("source_gem_block", getDefaultBlockItem(SOURCE_GEM_BLOCK));
      registry.register("potion_jar", getDefaultBlockItem(POTION_JAR));
      registry.register("potion_melder", getDefaultBlockItem(POTION_MELDER));
      registry.register("sconce", getDefaultBlockItem(SCONCE_BLOCK));
      registry.register("drygmy_stone", getDefaultBlockItem(DRYGMY_BLOCK));
      registry.register("alchemical_sourcelink", new RendererBlockItem(ALCHEMICAL_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return AlchemicalRenderer::getISTER;
         }
      });
      registry.register("vitalic_sourcelink", new RendererBlockItem(VITALIC_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return VitalicRenderer::getISTER;
         }
      });
      registry.register("mycelial_sourcelink", new RendererBlockItem(MYCELIAL_BLOCK, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return MycelialRenderer::getISTER;
         }
      });
      registry.register("timer_spell_turret", new RendererBlockItem(TIMER_SPELL_TURRET, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return TimerTurretRenderer::getISTER;
         }
      });
      registry.register("basic_spell_turret", new RendererBlockItem(BASIC_SPELL_TURRET, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return BasicTurretRenderer::getISTER;
         }
      });
      registry.register("spell_turret", new RendererBlockItem(ENCHANTED_SPELL_TURRET, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return ReducerTurretRenderer::getISTER;
         }
      });
      registry.register("archwood_chest", new ArchwoodChest.Item(ARCHWOOD_CHEST, ItemsRegistry.defaultItemProperties()));
      registry.register("spell_prism", getDefaultBlockItem(SPELL_PRISM));
      registry.register("whirlisprig_flower", new RendererBlockItem(WHIRLISPRIG_FLOWER, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return WhirlisprigFlowerRenderer::getISTER;
         }
      });
      registry.register("relay_collector", new RendererBlockItem(RELAY_COLLECTOR, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return GenericRenderer.getISTER("source_collector");
         }
      });
      registry.register("red_sbed", getDefaultBlockItem(RED_SBED));
      registry.register("blue_sbed", getDefaultBlockItem(BLUE_SBED));
      registry.register("green_sbed", getDefaultBlockItem(GREEN_SBED));
      registry.register("yellow_sbed", getDefaultBlockItem(YELLOW_SBED));
      registry.register("purple_sbed", getDefaultBlockItem(PURPLE_SBED));
      registry.register("orange_sbed", getDefaultBlockItem(ORANGE_SBED));
      registry.register("scryers_crystal", getDefaultBlockItem(SCRYERS_CRYSTAL));
      registry.register("scryers_oculus", (new RendererBlockItem(SCRYERS_OCULUS, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return ScryerEyeRenderer::getISTER;
         }
      }).withTooltip(Component.m_237115_("ars_nouveau.tooltip.scryers_oculus").m_130948_(Style.f_131099_.m_131140_(ChatFormatting.DARK_PURPLE))));
      registry.register("potion_diffuser", getDefaultBlockItem(POTION_DIFFUSER));
      registry.register("mendosteen_pod", new ItemNameBlockItem(MENDOSTEEN_POD, ItemsRegistry.defaultItemProperties().m_41489_(ItemsRegistry.MENDOSTEEN_FOOD)));
      registry.register("bastion_pod", new ItemNameBlockItem(BASTION_POD, ItemsRegistry.defaultItemProperties().m_41489_(ItemsRegistry.BASTION_FOOD)));
      registry.register("bombegranate_pod", new ItemNameBlockItem(BOMBEGRANTE_POD, ItemsRegistry.defaultItemProperties().m_41489_(ItemsRegistry.BLASTING_FOOD)));
      registry.register("frostaya_pod", new ItemNameBlockItem(FROSTAYA_POD, ItemsRegistry.defaultItemProperties().m_41489_(ItemsRegistry.FROSTAYA_FOOD)));

      for (String s : LibBlockNames.DECORATIVE_SOURCESTONE) {
         registry.register(s, getDefaultBlockItem((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s))));
      }

      for (String s : LibBlockNames.DECORATIVE_STAIRS) {
         registry.register(s, getDefaultBlockItem((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s))));
      }

      for (String s : LibBlockNames.DECORATIVE_SLABS) {
         registry.register(s, getDefaultBlockItem((Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s))));
      }

      registry.register("alteration_table", new RendererBlockItem(ALTERATION_TABLE, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return AlterationTableRenderer::getISTER;
         }
      });
      registry.register("mob_jar", new MobJarItem(MOB_JAR, ItemsRegistry.defaultItemProperties()));
      registry.register("void_prism", getDefaultBlockItem(VOID_PRISM));
      registry.register("repository", new RendererBlockItem(REPOSITORY, ItemsRegistry.defaultItemProperties()) {
         @Override
         public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
            return RepositoryRenderer::getISTER;
         }
      });
      registry.register("ghostweave", getDefaultBlockItem(GHOST_WEAVE));
      registry.register("falseweave", getDefaultBlockItem(FALSE_WEAVE));
      registry.register("mirrorweave", getDefaultBlockItem(MIRROR_WEAVE));
      registry.register("magebloom_block", getDefaultBlockItem(MAGEBLOOM_BLOCK));
   }

   public static ModBlockItem getDefaultBlockItem(Block block) {
      return new ModBlockItem(block, ItemsRegistry.defaultItemProperties());
   }

   public static void registerBlockProvider(IForgeRegistry<BlockStateProviderType<?>> registry) {
      registry.register(new ResourceLocation("ars_nouveau", "an_stateprovider"), new BlockStateProviderType(SupplierBlockStateProvider.CODEC));
   }

   private static Boolean allowsSpawnOnLeaves(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entity) {
      return entity == EntityType.f_20505_ || entity == EntityType.f_20508_;
   }

   private static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
      return false;
   }

   public static Block getBlock(String s) {
      return (Block)ForgeRegistries.BLOCKS.getValue(new ResourceLocation("ars_nouveau", s));
   }

   static RegistryWrapper registerBlock(String name, Supplier<Block> blockSupp) {
      return new RegistryWrapper(BLOCKS.register(name, blockSupp));
   }

   public static FlowerPotBlock createPottedBlock(Supplier<? extends Block> block) {
      FlowerPotBlock pot = new FlowerPotBlock(() -> (FlowerPotBlock)Blocks.f_50276_, block, Properties.m_60939_(Material.f_76310_).m_60966_().m_60955_());
      flowerPots.put(() -> ForgeRegistries.BLOCKS.getKey(block.get()), pot);
      return pot;
   }

   static {
      ItemsRegistry.ITEMS.register("rotating_spell_turret", () -> (new RendererBlockItem(ROTATING_TURRET.get(), ItemsRegistry.defaultItemProperties()) {
            @Override
            public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
               return BasicTurretRenderer::getISTER;
            }
         }).withTooltip(Component.m_237115_("ars_nouveau.turret.tooltip")));
      ItemsRegistry.ITEMS.register("arcane_pedestal", () -> getDefaultBlockItem(ARCANE_PEDESTAL.get()));
      ItemsRegistry.ITEMS
         .register(
            "arcane_platform",
            () -> new ModBlockItem(ARCANE_PLATFORM.get(), ItemsRegistry.defaultItemProperties())
                  .withTooltip(Component.m_237115_("ars_nouveau.arcane_platform.tooltip"))
         );
      ItemsRegistry.ITEMS.register("magelight_torch", () -> getDefaultBlockItem(MAGELIGHT_TORCH.get()));
      ItemsRegistry.ITEMS.register("brazier_relay", () -> getDefaultBlockItem(BRAZIER_RELAY.get()));
      ItemsRegistry.ITEMS.register("ritual_brazier", () -> getDefaultBlockItem(RITUAL_BLOCK.get()));
      ItemsRegistry.ITEMS.register("sky_block", () -> getDefaultBlockItem(SKY_WEAVE.get()));
      ItemsRegistry.ITEMS.register("temporary_block", () -> new ModBlockItem(TEMPORARY_BLOCK.get(), new net.minecraft.world.item.Item.Properties()));
      ItemsRegistry.ITEMS.register("storage_lectern", () -> new RendererBlockItem(CRAFTING_LECTERN.get(), ItemsRegistry.defaultItemProperties()) {
            @Override
            public Supplier<BlockEntityWithoutLevelRenderer> getRenderer() {
               return LecternRenderer::getISTER;
            }
         });
      ItemsRegistry.ITEMS.register("item_detector", () -> getDefaultBlockItem(ITEM_DETECTOR.get()));
   }
}
