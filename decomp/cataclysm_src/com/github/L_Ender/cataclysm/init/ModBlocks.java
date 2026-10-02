package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.blocks.Abyssal_Egg_Block;
import com.github.L_Ender.cataclysm.blocks.Altar_Of_Abyss_Block;
import com.github.L_Ender.cataclysm.blocks.Altar_Of_Amethyst_Block;
import com.github.L_Ender.cataclysm.blocks.Altar_Of_Fire_Block;
import com.github.L_Ender.cataclysm.blocks.Altar_Of_Void_Block;
import com.github.L_Ender.cataclysm.blocks.Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.blocks.Cursed_Tombstone_Block;
import com.github.L_Ender.cataclysm.blocks.Door_of_Seal_Block;
import com.github.L_Ender.cataclysm.blocks.EMP_Block;
import com.github.L_Ender.cataclysm.blocks.EndStoneTeleportTrapBricks;
import com.github.L_Ender.cataclysm.blocks.FacingBlock;
import com.github.L_Ender.cataclysm.blocks.FacingPillarBlock;
import com.github.L_Ender.cataclysm.blocks.Mechanical_fusion_Anvil;
import com.github.L_Ender.cataclysm.blocks.MeltingNetherrack;
import com.github.L_Ender.cataclysm.blocks.ObsidianExplosionTrapBricks;
import com.github.L_Ender.cataclysm.blocks.PointedIcicleBlock;
import com.github.L_Ender.cataclysm.blocks.PurpurVoidRuneTrapBlock;
import com.github.L_Ender.cataclysm.blocks.Sandstone_Falling_Trap;
import com.github.L_Ender.cataclysm.blocks.Sandstone_Ignite_Trap;
import com.github.L_Ender.cataclysm.blocks.Sandstone_Poison_Dart_Trap;
import com.github.L_Ender.cataclysm.blocks.Wall_Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.items.BlockItemCMRender;
import com.github.L_Ender.cataclysm.items.CMBlockItem;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "cataclysm");
   public static final RegistryObject<Block> WITHERITE_BLOCK = BLOCKS.register(
      "witherite_block",
      () -> new Block(Properties.m_60939_(Material.f_76279_).m_155949_(MaterialColor.f_76365_).m_60913_(50.0F, 1200.0F).m_60999_().m_60918_(SoundType.f_56725_))
   );
   public static final RegistryObject<Block> ENDERRITE_BLOCK = BLOCKS.register(
      "enderite_block",
      () -> new Block(Properties.m_60939_(Material.f_76279_).m_155949_(MaterialColor.f_76365_).m_60913_(50.0F, 1200.0F).m_60918_(SoundType.f_56725_))
   );
   public static final RegistryObject<Block> IGNITIUM_BLOCK = BLOCKS.register(
      "ignitium_block",
      () -> new Block(
            Properties.m_60939_(Material.f_76279_)
               .m_155949_(MaterialColor.f_76365_)
               .m_60913_(50.0F, 1200.0F)
               .m_60918_(SoundType.f_56725_)
               .m_60953_(state -> 15)
         )
   );
   public static final RegistryObject<Block> ANCIENT_METAL_BLOCK = BLOCKS.register(
      "ancient_metal_block",
      () -> new Block(Properties.m_60939_(Material.f_76279_).m_155949_(MaterialColor.f_76416_).m_60913_(25.0F, 600.0F).m_60918_(SoundType.f_56743_))
   );
   public static final RegistryObject<Block> CURSIUM_BLOCK = BLOCKS.register(
      "cursium_block",
      () -> new Block(Properties.m_60939_(Material.f_76279_).m_155949_(MaterialColor.f_76417_).m_60913_(50.0F, 1200.0F).m_60918_(SoundType.f_56743_))
   );
   public static final RegistryObject<Block> POLISHED_END_STONE = BLOCKS.register("polished_end_stone", () -> new Block(Properties.m_60926_(Blocks.f_50259_)));
   public static final RegistryObject<Block> POLISHED_END_STONE_SLAB = BLOCKS.register(
      "polished_end_stone_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)POLISHED_END_STONE.get()))
   );
   public static final RegistryObject<Block> POLISHED_END_STONE_STAIRS = BLOCKS.register(
      "polished_end_stone_stairs",
      () -> new StairBlock(((Block)POLISHED_END_STONE.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)POLISHED_END_STONE.get()))
   );
   public static final RegistryObject<Block> CHISELED_END_STONE_BRICKS = BLOCKS.register(
      "chiseled_end_stone_bricks", () -> new Block(Properties.m_60926_(Blocks.f_50443_))
   );
   public static final RegistryObject<Block> VOID_INFUSED_END_STONE_BRICKS = BLOCKS.register(
      "void_infused_end_stone_bricks", () -> new Block(Properties.m_60926_(Blocks.f_50443_).m_60953_(state -> 7))
   );
   public static final RegistryObject<Block> VOID_STONE = BLOCKS.register(
      "void_stone",
      () -> new Block(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76422_).m_60999_().m_60913_(50.0F, 1200.0F).m_60953_(state -> 7))
   );
   public static final RegistryObject<Block> VOID_CRYSTAL = BLOCKS.register(
      "void_crystal",
      () -> new Block(
            Properties.m_60939_(Material.f_76278_)
               .m_155949_(MaterialColor.f_76422_)
               .m_60918_(SoundType.f_56744_)
               .m_60999_()
               .m_60913_(50.0F, 1200.0F)
               .m_60953_(state -> 7)
         )
   );
   public static final RegistryObject<Block> VOID_LANTERN_BLOCK = BLOCKS.register(
      "void_lantern_block",
      () -> new Block(
            Properties.m_60939_(Material.f_76278_)
               .m_155949_(MaterialColor.f_76412_)
               .m_60918_(SoundType.f_56744_)
               .m_60999_()
               .m_60913_(50.0F, 1200.0F)
               .m_60953_(state -> 15)
         )
   );
   public static final RegistryObject<Block> END_STONE_PILLAR = BLOCKS.register(
      "end_stone_pillar",
      () -> new RotatedPillarBlock(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76400_).m_60999_().m_60913_(3.0F, 9.0F))
   );
   public static final RegistryObject<Block> CHISELED_PURPUR_BLOCK = BLOCKS.register(
      "chiseled_purpur_block", () -> new Block(Properties.m_60926_(Blocks.f_50492_))
   );
   public static final RegistryObject<Block> OBSIDIAN_BRICKS = BLOCKS.register("obsidian_bricks", () -> new Block(Properties.m_60926_(Blocks.f_50080_)));
   public static final RegistryObject<Block> POLISHED_OBSIDIAN = BLOCKS.register("polished_obsidian", () -> new Block(Properties.m_60926_(Blocks.f_50080_)));
   public static final RegistryObject<Block> CHISELED_OBSIDIAN_BRICKS = BLOCKS.register(
      "chiseled_obsidian_bricks", () -> new Block(Properties.m_60926_((BlockBehaviour)OBSIDIAN_BRICKS.get()))
   );
   public static final RegistryObject<Block> OBSIDIAN_BRICK_SLAB = BLOCKS.register(
      "obsidian_brick_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)OBSIDIAN_BRICKS.get()))
   );
   public static final RegistryObject<Block> OBSIDIAN_BRICK_STAIRS = BLOCKS.register(
      "obsidian_brick_stairs", () -> new StairBlock(((Block)OBSIDIAN_BRICKS.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)OBSIDIAN_BRICKS.get()))
   );
   public static final RegistryObject<Block> OBSIDIAN_BRICK_WALL = BLOCKS.register(
      "obsidian_brick_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)OBSIDIAN_BRICKS.get()))
   );
   public static final RegistryObject<Block> PURPUR_WALL = BLOCKS.register("purpur_wall", () -> new WallBlock(Properties.m_60926_(Blocks.f_50492_)));
   public static final RegistryObject<Block> PURPUR_VOID_RUNE_TRAP_BLOCK = BLOCKS.register(
      "purpur_void_rune_trap_block", () -> new PurpurVoidRuneTrapBlock(Properties.m_60926_(Blocks.f_50492_).m_60977_().m_60953_(getLightValueLit(7)))
   );
   public static final RegistryObject<Block> END_STONE_TELEPORT_TRAP_BRICKS = BLOCKS.register(
      "end_stone_teleport_trap_bricks", () -> new EndStoneTeleportTrapBricks(Properties.m_60926_(Blocks.f_50443_).m_60977_().m_60953_(getLightValueLit(7)))
   );
   public static final RegistryObject<Block> OBSIDIAN_EXPLOSION_TRAP_BRICKS = BLOCKS.register(
      "obsidian_explosion_trap_bricks",
      () -> new ObsidianExplosionTrapBricks(Properties.m_60926_((BlockBehaviour)OBSIDIAN_BRICKS.get()).m_60977_().m_60953_(getLightValueLit(7)))
   );
   public static final RegistryObject<Block> SANDSTONE_POISON_DART_TRAP = BLOCKS.register(
      "sandstone_poison_dart_trap",
      () -> new Sandstone_Poison_Dart_Trap(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76400_).m_60999_().m_60978_(0.8F))
   );
   public static final RegistryObject<Block> SANDSTONE_IGNITE_TRAP = BLOCKS.register(
      "sandstone_ignite_trap",
      () -> new Sandstone_Ignite_Trap(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76400_).m_60977_().m_60999_().m_60978_(0.8F))
   );
   public static final RegistryObject<Block> SANDSTONE_FALLING_TRAP = BLOCKS.register(
      "sandstone_falling_trap",
      () -> new Sandstone_Falling_Trap(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76400_).m_60977_().m_60999_().m_60978_(0.8F))
   );
   public static final RegistryObject<Block> ALTAR_OF_FIRE = registerBlockAndItem(
      "altar_of_fire", Altar_Of_Fire_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> ALTAR_OF_VOID = registerBlockAndItem(
      "altar_of_void", Altar_Of_Void_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> ALTAR_OF_AMETHYST = registerBlockAndItem(
      "altar_of_amethyst", Altar_Of_Amethyst_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> ALTAR_OF_ABYSS = registerBlockAndItem(
      "altar_of_abyss", Altar_Of_Abyss_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> CURSED_TOMBSTONE = registerBlockAndItem(
      "cursed_tombstone", Cursed_Tombstone_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> DUNGEON_BLOCK = BLOCKS.register(
      "dungeon_block", () -> new Block(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76409_).m_60913_(-1.0F, 3600000.0F).m_222994_())
   );
   public static final RegistryObject<Block> EMP = registerBlockAndItem(
      "emp", EMP_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC), true
   );
   public static final RegistryObject<Block> MECHANICAL_FUSION_ANVIL = registerBlockAndItem(
      "mechanical_fusion_anvil", Mechanical_fusion_Anvil::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> DOOR_OF_SEAL = registerBlockAndItem(
      "door_of_seal", Door_of_Seal_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC), true
   );
   public static final RegistryObject<Block> DOOR_OF_SEAL_PART = BLOCKS.register(
      "door_of_seal_part", () -> new Door_of_Seal_Block.Door_Of_Seal_Part_Block(Properties.m_60926_((BlockBehaviour)DOOR_OF_SEAL.get()))
   );
   public static final RegistryObject<Block> KOBOLEDIATOR_SKULL = BLOCKS.register(
      "kobolediator_skull", () -> new Cataclysm_Skull_Block(Cataclysm_Skull_Block.Types.KOBOLEDIATOR, Properties.m_60939_(Material.f_76310_).m_60978_(1.0F))
   );
   public static final RegistryObject<Block> KOBOLEDIATOR_WALL_SKULL = BLOCKS.register(
      "kobolediator_wall_skull",
      () -> new Wall_Cataclysm_Skull_Block(
            Cataclysm_Skull_Block.Types.KOBOLEDIATOR, Properties.m_60939_(Material.f_76310_).m_60978_(1.0F).lootFrom(() -> (Block)KOBOLEDIATOR_SKULL.get())
         )
   );
   public static final RegistryObject<Block> APTRGANGR_HEAD = BLOCKS.register(
      "aptrgangr_head", () -> new Cataclysm_Skull_Block(Cataclysm_Skull_Block.Types.APTRGANGR, Properties.m_60939_(Material.f_76310_).m_60978_(1.0F))
   );
   public static final RegistryObject<Block> APTRGANGR_WALL_HEAD = BLOCKS.register(
      "aptrgangr_wall_head",
      () -> new Wall_Cataclysm_Skull_Block(
            Cataclysm_Skull_Block.Types.APTRGANGR, Properties.m_60939_(Material.f_76310_).m_60978_(1.0F).lootFrom(() -> (Block)APTRGANGR_HEAD.get())
         )
   );
   public static final RegistryObject<Block> DRAUGR_HEAD = BLOCKS.register(
      "draugr_head", () -> new Cataclysm_Skull_Block(Cataclysm_Skull_Block.Types.DRAUGR, Properties.m_60939_(Material.f_76310_).m_60978_(1.0F))
   );
   public static final RegistryObject<Block> DRAUGR_WALL_HEAD = BLOCKS.register(
      "draugr_wall_head",
      () -> new Wall_Cataclysm_Skull_Block(
            Cataclysm_Skull_Block.Types.DRAUGR, Properties.m_60939_(Material.f_76310_).m_60978_(1.0F).lootFrom(() -> (Block)APTRGANGR_HEAD.get())
         )
   );
   public static final RegistryObject<Block> ABYSSAL_EGG = registerBlockAndItem(
      "abyssal_egg", Abyssal_Egg_Block::new, new net.minecraft.world.item.Item.Properties().m_41497_(Rarity.EPIC).m_41486_(), true
   );
   public static final RegistryObject<Block> CHORUS_STEM = BLOCKS.register(
      "chorus_stem",
      () -> new RotatedPillarBlock(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76422_).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_))
   );
   public static final RegistryObject<Block> CHORUS_PLANKS = BLOCKS.register(
      "chorus_planks",
      () -> new Block(Properties.m_60939_(Material.f_76320_).m_155949_(MaterialColor.f_76422_).m_60913_(2.0F, 3.0F).m_60918_(SoundType.f_56736_))
   );
   public static final RegistryObject<Block> CHORUS_SLAB = BLOCKS.register(
      "chorus_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)CHORUS_PLANKS.get()))
   );
   public static final RegistryObject<Block> CHORUS_STAIRS = BLOCKS.register(
      "chorus_stairs", () -> new StairBlock(((Block)CHORUS_PLANKS.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)CHORUS_PLANKS.get()))
   );
   public static final RegistryObject<Block> CHORUS_FENCE = BLOCKS.register(
      "chorus_fence", () -> new FenceBlock(Properties.m_60926_((BlockBehaviour)CHORUS_PLANKS.get()))
   );
   public static final RegistryObject<Block> PRISMARINE_BRICK_FENCE = BLOCKS.register(
      "prismarine_brick_fence", () -> new FenceBlock(Properties.m_60926_(Blocks.f_50378_))
   );
   public static final RegistryObject<Block> QUARTZ_BRICK_WALL = BLOCKS.register("quartz_brick_wall", () -> new WallBlock(Properties.m_60926_(Blocks.f_50714_)));
   public static final RegistryObject<Block> PRISMARINE_BRICK_WALL = BLOCKS.register(
      "prismarine_brick_wall", () -> new WallBlock(Properties.m_60926_(Blocks.f_50378_))
   );
   public static final RegistryObject<Block> STONE_PILLAR = BLOCKS.register(
      "stone_pillar", () -> new RotatedPillarBlock(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76409_).m_60999_().m_60913_(1.5F, 6.0F))
   );
   public static final RegistryObject<Block> CHISELED_STONE_BRICK_PILLAR = BLOCKS.register(
      "chiseled_stone_brick_pillar",
      () -> new RotatedPillarBlock(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76409_).m_60999_().m_60913_(1.5F, 6.0F))
   );
   public static final RegistryObject<Block> STONE_TILES = BLOCKS.register(
      "stone_tiles", () -> new Block(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76409_).m_60999_().m_60913_(1.5F, 6.0F))
   );
   public static final RegistryObject<Block> FROSTED_STONE_BRICKS = BLOCKS.register(
      "frosted_stone_bricks", () -> new Block(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76409_).m_60999_().m_60913_(1.5F, 6.0F))
   );
   public static final RegistryObject<Block> FROSTED_STONE_BRICK_SLAB = BLOCKS.register(
      "frosted_stone_brick_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)FROSTED_STONE_BRICKS.get()))
   );
   public static final RegistryObject<Block> FROSTED_STONE_BRICK_STAIRS = BLOCKS.register(
      "frosted_stone_brick_stairs",
      () -> new StairBlock(((Block)FROSTED_STONE_BRICKS.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)FROSTED_STONE_BRICKS.get()))
   );
   public static final RegistryObject<Block> FROSTED_STONE_BRICK_WALL = BLOCKS.register(
      "frosted_stone_brick_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)FROSTED_STONE_BRICKS.get()))
   );
   public static final RegistryObject<Block> BLACK_STEEL_BLOCK = BLOCKS.register(
      "black_steel_block",
      () -> new Block(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76365_).m_60913_(25.0F, 600.0F).m_60918_(SoundType.f_56743_))
   );
   public static final RegistryObject<Block> BLACK_STEEL_FENCE = BLOCKS.register(
      "black_steel_fence", () -> new FenceBlock(Properties.m_60926_((BlockBehaviour)BLACK_STEEL_BLOCK.get()))
   );
   public static final RegistryObject<Block> BLACK_STEEL_WALL = BLOCKS.register(
      "black_steel_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)BLACK_STEEL_BLOCK.get()))
   );
   public static final RegistryObject<Block> STONE_TILE_SLAB = BLOCKS.register(
      "stone_tile_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)STONE_TILES.get()))
   );
   public static final RegistryObject<Block> STONE_TILE_STAIRS = BLOCKS.register(
      "stone_tile_stairs", () -> new StairBlock(((Block)STONE_TILES.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)STONE_TILES.get()))
   );
   public static final RegistryObject<Block> STONE_TILE_WALL = BLOCKS.register(
      "stone_tile_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)STONE_TILES.get()))
   );
   public static final RegistryObject<Block> POLISHED_SANDSTONE = BLOCKS.register("polished_sandstone", () -> new Block(Properties.m_60926_(Blocks.f_50062_)));
   public static final RegistryObject<Block> BLACKSTONE_PILLAR = BLOCKS.register(
      "blackstone_pillar",
      () -> new RotatedPillarBlock(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76365_).m_60999_().m_60913_(1.5F, 6.0F))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE = BLOCKS.register(
      "azure_seastone", () -> new Block(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76412_).m_60999_().m_60913_(2.0F, 6.0F))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_SLAB = BLOCKS.register(
      "azure_seastone_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_STAIRS = BLOCKS.register(
      "azure_seastone_stairs", () -> new StairBlock(((Block)AZURE_SEASTONE.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_WALL = BLOCKS.register(
      "azure_seastone_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_FENCE = BLOCKS.register(
      "azure_seastone_fence", () -> new FenceBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_TILES = BLOCKS.register(
      "azure_seastone_tiles", () -> new FacingBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> CHISELED_AZURE_SEASTONE = BLOCKS.register(
      "chiseled_azure_seastone", () -> new FacingBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_BRICKS = BLOCKS.register(
      "azure_seastone_bricks", () -> new Block(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_BRICK_SLAB = BLOCKS.register(
      "azure_seastone_brick_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE_BRICKS.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_BRICK_STAIRS = BLOCKS.register(
      "azure_seastone_brick_stairs",
      () -> new StairBlock(((Block)AZURE_SEASTONE_BRICKS.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE_BRICKS.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_BRICK_WALL = BLOCKS.register(
      "azure_seastone_brick_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE_BRICKS.get()))
   );
   public static final RegistryObject<Block> POLISHED_AZURE_SEASTONE = BLOCKS.register(
      "polished_azure_seastone", () -> new Block(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> POLISHED_AZURE_SEASTONE_SLAB = BLOCKS.register(
      "polished_azure_seastone_slab", () -> new SlabBlock(Properties.m_60926_((BlockBehaviour)POLISHED_AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> POLISHED_AZURE_SEASTONE_STAIRS = BLOCKS.register(
      "polished_azure_seastone_stairs",
      () -> new StairBlock(((Block)POLISHED_AZURE_SEASTONE.get()).m_49966_(), Properties.m_60926_((BlockBehaviour)POLISHED_AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> POLISHED_AZURE_SEASTONE_WALL = BLOCKS.register(
      "polished_azure_seastone_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_PILLAR = BLOCKS.register(
      "azure_seastone_pillar", () -> new RotatedPillarBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> AZURE_SEASTONE_PILLAR_WALL = BLOCKS.register(
      "azure_seastone_pillar_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> CHISELED_AZURE_SEASTONE_PILLAR = BLOCKS.register(
      "chiseled_azure_seastone_pillar", () -> new FacingPillarBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> CHISELED_AZURE_SEASTONE_PILLAR_WALL = BLOCKS.register(
      "chiseled_azure_seastone_pillar_wall", () -> new WallBlock(Properties.m_60926_((BlockBehaviour)AZURE_SEASTONE.get()))
   );
   public static final RegistryObject<Block> POINTED_ICICLE = BLOCKS.register(
      "pointed_icicle",
      () -> new PointedIcicleBlock(
            Properties.m_60939_(Material.f_76276_)
               .m_155949_(MaterialColor.f_76403_)
               .m_60955_()
               .m_60977_()
               .m_60918_(SoundType.f_56744_)
               .m_60978_(0.5F)
               .m_60988_()
               .m_222979_(OffsetType.XZ)
         )
   );
   public static final RegistryObject<Block> MELTING_NETHERRACK = BLOCKS.register(
      "melting_netherrack",
      () -> new MeltingNetherrack(Properties.m_60939_(Material.f_76278_).m_155949_(MaterialColor.f_76371_).m_60977_().m_60953_(state -> 3))
   );

   private static ToIntFunction<BlockState> getLightValueLit(int lightValue) {
      return state -> state.m_61143_(BlockStateProperties.f_61443_) ? lightValue : 0;
   }

   public static RegistryObject<Block> registerBlockAndItem(
      String name, Supplier<Block> block, net.minecraft.world.item.Item.Properties blockItemProps, boolean specialRender
   ) {
      RegistryObject<Block> blockObj = BLOCKS.register(name, block);
      ModItems.ITEMS
         .register(
            name,
            () -> (CMBlockItem)(specialRender
                  ? new BlockItemCMRender(blockObj, blockItemProps.m_41491_(ModGroup.BLOCK))
                  : new CMBlockItem(blockObj, blockItemProps.m_41491_(ModGroup.BLOCK)))
         );
      return blockObj;
   }
}
