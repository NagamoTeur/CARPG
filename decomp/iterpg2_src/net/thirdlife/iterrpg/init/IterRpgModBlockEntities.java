package net.thirdlife.iterrpg.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thirdlife.iterrpg.block.entity.ArcaneFlowerSeedsBlockEntity;
import net.thirdlife.iterrpg.block.entity.ArthropodCatacombsGenBlockBlockEntity;
import net.thirdlife.iterrpg.block.entity.BigVaseDecoBlockEntity;
import net.thirdlife.iterrpg.block.entity.CompactedSorrowsealedBlockEntity;
import net.thirdlife.iterrpg.block.entity.DrillBlockEntity;
import net.thirdlife.iterrpg.block.entity.GenericDungeonGenBlockBlockEntity;
import net.thirdlife.iterrpg.block.entity.GiantPhantomChainBlockEntity;
import net.thirdlife.iterrpg.block.entity.GoblinDungeonGenBlockEntity;
import net.thirdlife.iterrpg.block.entity.GoblinSpawnerBlockEntity;
import net.thirdlife.iterrpg.block.entity.GrimSoultrapBlockEntity;
import net.thirdlife.iterrpg.block.entity.GunpowderBarrelBlockEntity;
import net.thirdlife.iterrpg.block.entity.MournersSpawnerBlockEntity;
import net.thirdlife.iterrpg.block.entity.RunicGrimstoneActiveBlockEntity;
import net.thirdlife.iterrpg.block.entity.RunicGrimstoneBlockEntity;
import net.thirdlife.iterrpg.block.entity.SacredSaplingBlockEntity;
import net.thirdlife.iterrpg.block.entity.SmallVaseDecoBlockEntity;
import net.thirdlife.iterrpg.block.entity.SorrowSpireGenBlockBlockEntity;
import net.thirdlife.iterrpg.block.entity.TwiffleBlockBlockEntity;
import net.thirdlife.iterrpg.block.entity.VaseDecoBlockEntity;

public class IterRpgModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "iter_rpg");
   public static final RegistryObject<BlockEntityType<?>> TWIFFLE_BLOCK = register(
      "twiffle_block", IterRpgModBlocks.TWIFFLE_BLOCK, TwiffleBlockBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> SACRED_SAPLING = register(
      "sacred_sapling", IterRpgModBlocks.SACRED_SAPLING, SacredSaplingBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> SMALL_VASE_DECO = register(
      "small_vase_deco", IterRpgModBlocks.SMALL_VASE_DECO, SmallVaseDecoBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> VASE_DECO = register("vase_deco", IterRpgModBlocks.VASE_DECO, VaseDecoBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> BIG_VASE_DECO = register("big_vase_deco", IterRpgModBlocks.BIG_VASE_DECO, BigVaseDecoBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> GUNPOWDER_BARREL = register(
      "gunpowder_barrel", IterRpgModBlocks.GUNPOWDER_BARREL, GunpowderBarrelBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> DRILL = register("drill", IterRpgModBlocks.DRILL, DrillBlockEntity::new);
   public static final RegistryObject<BlockEntityType<?>> GOBLIN_SPAWNER = register(
      "goblin_spawner", IterRpgModBlocks.GOBLIN_SPAWNER, GoblinSpawnerBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ARCANE_FLOWER_SEEDS = register(
      "arcane_flower_seeds", IterRpgModBlocks.ARCANE_FLOWER_SEEDS, ArcaneFlowerSeedsBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> GENERIC_DUNGEON_GEN_BLOCK = register(
      "generic_dungeon_gen_block", IterRpgModBlocks.GENERIC_DUNGEON_GEN_BLOCK, GenericDungeonGenBlockBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> SORROW_SPIRE_GEN_BLOCK = register(
      "sorrow_spire_gen_block", IterRpgModBlocks.SORROW_SPIRE_GEN_BLOCK, SorrowSpireGenBlockBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> COMPACTED_SORROWSEALED = register(
      "compacted_sorrowsealed", IterRpgModBlocks.COMPACTED_SORROWSEALED, CompactedSorrowsealedBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> GOBLIN_DUNGEON_GEN = register(
      "goblin_dungeon_gen", IterRpgModBlocks.GOBLIN_DUNGEON_GEN, GoblinDungeonGenBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> ARTHROPOD_CATACOMBS_GEN_BLOCK = register(
      "arthropod_catacombs_gen_block", IterRpgModBlocks.ARTHROPOD_CATACOMBS_GEN_BLOCK, ArthropodCatacombsGenBlockBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> GRIM_SOULTRAP = register(
      "grim_soultrap", IterRpgModBlocks.GRIM_SOULTRAP, GrimSoultrapBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> RUNIC_GRIMSTONE = register(
      "runic_grimstone", IterRpgModBlocks.RUNIC_GRIMSTONE, RunicGrimstoneBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> RUNIC_GRIMSTONE_ACTIVE = register(
      "runic_grimstone_active", IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE, RunicGrimstoneActiveBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> MOURNERS_SPAWNER = register(
      "mourners_spawner", IterRpgModBlocks.MOURNERS_SPAWNER, MournersSpawnerBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<?>> GIANT_PHANTOM_CHAIN = register(
      "giant_phantom_chain", IterRpgModBlocks.GIANT_PHANTOM_CHAIN, GiantPhantomChainBlockEntity::new
   );

   private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntitySupplier<?> supplier) {
      return REGISTRY.register(registryname, () -> Builder.m_155273_(supplier, new Block[]{(Block)block.get()}).m_58966_(null));
   }
}
