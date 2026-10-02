package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Scatter_Arrow_Entity;
import com.github.L_Ender.cataclysm.items.Ancient_Spear;
import com.github.L_Ender.cataclysm.items.Armortier;
import com.github.L_Ender.cataclysm.items.Athame;
import com.github.L_Ender.cataclysm.items.Black_Steel_Targe;
import com.github.L_Ender.cataclysm.items.Blessed_Amethyst_Crab_Meat;
import com.github.L_Ender.cataclysm.items.Bloom_Stone_Pauldrons;
import com.github.L_Ender.cataclysm.items.Bone_Reptile_Armor;
import com.github.L_Ender.cataclysm.items.Bulwark_of_the_flame;
import com.github.L_Ender.cataclysm.items.CataclysmSkullItem;
import com.github.L_Ender.cataclysm.items.Coral_Bardiche;
import com.github.L_Ender.cataclysm.items.Coral_Spear;
import com.github.L_Ender.cataclysm.items.Cursed_bow;
import com.github.L_Ender.cataclysm.items.Cursium_Armor;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Bulwark;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Guard;
import com.github.L_Ender.cataclysm.items.Gauntlet_of_Maelstrom;
import com.github.L_Ender.cataclysm.items.Ignitium_Armor;
import com.github.L_Ender.cataclysm.items.Ignitium_Elytra_ChestPlate;
import com.github.L_Ender.cataclysm.items.ItemInventoryOnly;
import com.github.L_Ender.cataclysm.items.Khopesh;
import com.github.L_Ender.cataclysm.items.Laser_Gatling;
import com.github.L_Ender.cataclysm.items.Meat_Shredder;
import com.github.L_Ender.cataclysm.items.ModFishBucket;
import com.github.L_Ender.cataclysm.items.ModItemTier;
import com.github.L_Ender.cataclysm.items.ModernRemantBucket;
import com.github.L_Ender.cataclysm.items.Monstrous_Helm;
import com.github.L_Ender.cataclysm.items.Necklace_Of_The_Desert;
import com.github.L_Ender.cataclysm.items.Netherite_Effigy;
import com.github.L_Ender.cataclysm.items.Remnant_Skull;
import com.github.L_Ender.cataclysm.items.Sandstorm_In_A_Bottle;
import com.github.L_Ender.cataclysm.items.Soul_Render;
import com.github.L_Ender.cataclysm.items.The_Annihilator;
import com.github.L_Ender.cataclysm.items.The_Immolator;
import com.github.L_Ender.cataclysm.items.The_Incinerator;
import com.github.L_Ender.cataclysm.items.Tidal_Claws;
import com.github.L_Ender.cataclysm.items.Tooltier;
import com.github.L_Ender.cataclysm.items.Void_Assault_SHoulder_Weapon;
import com.github.L_Ender.cataclysm.items.Void_Scatter_Arrow_Item;
import com.github.L_Ender.cataclysm.items.Wither_Assault_SHoulder_Weapon;
import com.github.L_Ender.cataclysm.items.Wrath_of_the_desert;
import com.github.L_Ender.cataclysm.items.final_fractal;
import com.github.L_Ender.cataclysm.items.infernal_forge;
import com.github.L_Ender.cataclysm.items.void_core;
import com.github.L_Ender.cataclysm.items.void_forge;
import com.github.L_Ender.cataclysm.items.zweiender;
import com.github.L_Ender.cataclysm.items.CuriosItem.Blazing_Grips;
import com.github.L_Ender.cataclysm.items.CuriosItem.Sticky_Gloves;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.AbyssEyeItem;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.CursedEyeItem;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.DesertEyeItem;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.FlameEyeItem;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.MechEyeItem;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.MonstrousEyeItem;
import com.github.L_Ender.cataclysm.items.Dungeon_Eye.VoidEyeItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "cataclysm");
   public static final RegistryObject<BlockItem> ENDERITE_BLOCK = ITEMS.register(
      "enderite_block", () -> new BlockItem((Block)ModBlocks.ENDERRITE_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<BlockItem> WITHERITE_BLCOK = ITEMS.register(
      "witherite_block",
      () -> new BlockItem((Block)ModBlocks.WITHERITE_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<BlockItem> IGNITIUM_BLOCK = ITEMS.register(
      "ignitium_block", () -> new BlockItem((Block)ModBlocks.IGNITIUM_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<BlockItem> ANCIENT_METAL_BLOCK = ITEMS.register(
      "ancient_metal_block",
      () -> new BlockItem((Block)ModBlocks.ANCIENT_METAL_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_().m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<BlockItem> CURSIUM_BLOCK = ITEMS.register(
      "cursium_block", () -> new BlockItem((Block)ModBlocks.CURSIUM_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<BlockItem> DUNGEON_BLOCK = ITEMS.register(
      "dungeon_block", () -> new BlockItem((Block)ModBlocks.DUNGEON_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_SANDSTONE = ITEMS.register(
      "polished_sandstone", () -> new BlockItem((Block)ModBlocks.POLISHED_SANDSTONE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_END_STONE = ITEMS.register(
      "polished_end_stone", () -> new BlockItem((Block)ModBlocks.POLISHED_END_STONE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_END_STONE_SLAB = ITEMS.register(
      "polished_end_stone_slab", () -> new BlockItem((Block)ModBlocks.POLISHED_END_STONE_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_END_STONE_STAIRS = ITEMS.register(
      "polished_end_stone_stairs", () -> new BlockItem((Block)ModBlocks.POLISHED_END_STONE_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_END_STONE_BRICKS = ITEMS.register(
      "chiseled_end_stone_bricks", () -> new BlockItem((Block)ModBlocks.CHISELED_END_STONE_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> END_STONE_PILLAR = ITEMS.register(
      "end_stone_pillar", () -> new BlockItem((Block)ModBlocks.END_STONE_PILLAR.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> VOID_INFUSED_END_STONE_BRICKS = ITEMS.register(
      "void_infused_end_stone_bricks", () -> new BlockItem((Block)ModBlocks.VOID_INFUSED_END_STONE_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> VOID_STONE = ITEMS.register(
      "void_stone", () -> new BlockItem((Block)ModBlocks.VOID_STONE.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_())
   );
   public static final RegistryObject<BlockItem> VOID_CRYSTAL = ITEMS.register(
      "void_crystal", () -> new BlockItem((Block)ModBlocks.VOID_CRYSTAL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> VOID_LANTERN_BLOCK = ITEMS.register(
      "void_lantern_block", () -> new BlockItem((Block)ModBlocks.VOID_LANTERN_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41486_())
   );
   public static final RegistryObject<BlockItem> OBSIDIAN_BRICKS = ITEMS.register(
      "obsidian_bricks", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_OBSIDIAN = ITEMS.register(
      "polished_obsidian", () -> new BlockItem((Block)ModBlocks.POLISHED_OBSIDIAN.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_OBSIDIAN_BRICKS = ITEMS.register(
      "chiseled_obsidian_bricks", () -> new BlockItem((Block)ModBlocks.CHISELED_OBSIDIAN_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> OBSIDIAN_BRICK_SLAB = ITEMS.register(
      "obsidian_brick_slab", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICK_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> OBSIDIAN_BRICK_STAIRS = ITEMS.register(
      "obsidian_brick_stairs", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICK_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> OBSIDIAN_BRICK_WALL = ITEMS.register(
      "obsidian_brick_wall", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_BRICK_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_PURPUR_BLOCK = ITEMS.register(
      "chiseled_purpur_block", () -> new BlockItem((Block)ModBlocks.CHISELED_PURPUR_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> PURPUR_WALL = ITEMS.register(
      "purpur_wall", () -> new BlockItem((Block)ModBlocks.PURPUR_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> PURPUR_VOID_RUNE_TRAP_BLOCK = ITEMS.register(
      "purpur_void_rune_trap_block", () -> new BlockItem((Block)ModBlocks.PURPUR_VOID_RUNE_TRAP_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> END_STONE_TELEPORT_TRAP_BRICKS = ITEMS.register(
      "end_stone_teleport_trap_bricks", () -> new BlockItem((Block)ModBlocks.END_STONE_TELEPORT_TRAP_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> OBSIDIAN_EXPLOSION_TRAP_BRICKS = ITEMS.register(
      "obsidian_explosion_trap_bricks", () -> new BlockItem((Block)ModBlocks.OBSIDIAN_EXPLOSION_TRAP_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> SANDSTONE_POISON_DART_TRAP = ITEMS.register(
      "sandstone_poison_dart_trap", () -> new BlockItem((Block)ModBlocks.SANDSTONE_POISON_DART_TRAP.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> SANDSTONE_IGNITE_TRAP = ITEMS.register(
      "sandstone_ignite_trap", () -> new BlockItem((Block)ModBlocks.SANDSTONE_IGNITE_TRAP.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> SANDSTONE_FALLING_TRAP = ITEMS.register(
      "sandstone_falling_trap", () -> new BlockItem((Block)ModBlocks.SANDSTONE_FALLING_TRAP.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHORUS_STEM = ITEMS.register(
      "chorus_stem", () -> new BlockItem((Block)ModBlocks.CHORUS_STEM.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHORUS_PLANKS = ITEMS.register(
      "chorus_planks", () -> new BlockItem((Block)ModBlocks.CHORUS_PLANKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHORUS_SLAB = ITEMS.register(
      "chorus_slab", () -> new BlockItem((Block)ModBlocks.CHORUS_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHORUS_STAIRS = ITEMS.register(
      "chorus_stairs", () -> new BlockItem((Block)ModBlocks.CHORUS_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHORUS_FENCE = ITEMS.register(
      "chorus_fence", () -> new BlockItem((Block)ModBlocks.CHORUS_FENCE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> PRISMARINE_BRICK_FENCE = ITEMS.register(
      "prismarine_brick_fence", () -> new BlockItem((Block)ModBlocks.PRISMARINE_BRICK_FENCE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> QUARTZ_BRICK_WALL = ITEMS.register(
      "quartz_brick_wall", () -> new BlockItem((Block)ModBlocks.QUARTZ_BRICK_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> PRISMARINE_BRICK_WALL = ITEMS.register(
      "prismarine_brick_wall", () -> new BlockItem((Block)ModBlocks.PRISMARINE_BRICK_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> STONE_PILLAR = ITEMS.register(
      "stone_pillar", () -> new BlockItem((Block)ModBlocks.STONE_PILLAR.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_STONE_BRICK_PILLAR = ITEMS.register(
      "chiseled_stone_brick_pillar", () -> new BlockItem((Block)ModBlocks.CHISELED_STONE_BRICK_PILLAR.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> STONE_TILES = ITEMS.register(
      "stone_tiles", () -> new BlockItem((Block)ModBlocks.STONE_TILES.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> STONE_TILE_SLAB = ITEMS.register(
      "stone_tile_slab", () -> new BlockItem((Block)ModBlocks.STONE_TILE_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> STONE_TILE_STAIRS = ITEMS.register(
      "stone_tile_stairs", () -> new BlockItem((Block)ModBlocks.STONE_TILE_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> STONE_TILE_WALL = ITEMS.register(
      "stone_tile_wall", () -> new BlockItem((Block)ModBlocks.STONE_TILE_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> BLACKSTONE_PILLAR = ITEMS.register(
      "blackstone_pillar", () -> new BlockItem((Block)ModBlocks.BLACKSTONE_PILLAR.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE = ITEMS.register(
      "azure_seastone", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_SLAB = ITEMS.register(
      "azure_seastone_slab", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_STAIRS = ITEMS.register(
      "azure_seastone_stairs", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_WALL = ITEMS.register(
      "azure_seastone_wall", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_FENCE = ITEMS.register(
      "azure_seastone_fence", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_FENCE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_TILES = ITEMS.register(
      "azure_seastone_tiles", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_TILES.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_AZURE_SEASTONE = ITEMS.register(
      "chiseled_azure_seastone", () -> new BlockItem((Block)ModBlocks.CHISELED_AZURE_SEASTONE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_BRICKS = ITEMS.register(
      "azure_seastone_bricks", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_BRICK_SLAB = ITEMS.register(
      "azure_seastone_brick_slab", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICK_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_BRICK_STAIRS = ITEMS.register(
      "azure_seastone_brick_stairs", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICK_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_BRICK_WALL = ITEMS.register(
      "azure_seastone_brick_wall", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_BRICK_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_AZURE_SEASTONE = ITEMS.register(
      "polished_azure_seastone", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_AZURE_SEASTONE_SLAB = ITEMS.register(
      "polished_azure_seastone_slab", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_AZURE_SEASTONE_STAIRS = ITEMS.register(
      "polished_azure_seastone_stairs", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POLISHED_AZURE_SEASTONE_WALL = ITEMS.register(
      "polished_azure_seastone_wall", () -> new BlockItem((Block)ModBlocks.POLISHED_AZURE_SEASTONE_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_PILLAR = ITEMS.register(
      "azure_seastone_pillar", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_PILLAR.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> AZURE_SEASTONE_PILLAR_WALL = ITEMS.register(
      "azure_seastone_pillar_wall", () -> new BlockItem((Block)ModBlocks.AZURE_SEASTONE_PILLAR_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_AZURE_SEASTONE_PILLAR = ITEMS.register(
      "chiseled_azure_seastone_pillar", () -> new BlockItem((Block)ModBlocks.CHISELED_AZURE_SEASTONE_PILLAR.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> CHISELED_AZURE_SEASTONE_PILLAR_WALL = ITEMS.register(
      "chiseled_azure_seastone_pillar_wall",
      () -> new BlockItem((Block)ModBlocks.CHISELED_AZURE_SEASTONE_PILLAR_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> FROSTED_STONE_BRICKS = ITEMS.register(
      "frosted_stone_bricks", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICKS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> FROSTED_STONE_BRICK_SLAB = ITEMS.register(
      "frosted_stone_brick_slab", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICK_SLAB.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> FROSTED_STONE_BRICK_STAIRS = ITEMS.register(
      "frosted_stone_brick_stairs", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICK_STAIRS.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> FROSTED_STONE_BRICK_WALL = ITEMS.register(
      "frosted_stone_brick_wall", () -> new BlockItem((Block)ModBlocks.FROSTED_STONE_BRICK_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> BLACK_STEEL_BLOCK = ITEMS.register(
      "black_steel_block", () -> new BlockItem((Block)ModBlocks.BLACK_STEEL_BLOCK.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> BLACK_STEEL_FENCE = ITEMS.register(
      "black_steel_fence", () -> new BlockItem((Block)ModBlocks.BLACK_STEEL_FENCE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> BLACK_STEEL_WALL = ITEMS.register(
      "black_steel_wall", () -> new BlockItem((Block)ModBlocks.BLACK_STEEL_WALL.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<BlockItem> POINTED_ICICLE = ITEMS.register(
      "pointed_icicle", () -> new BlockItem((Block)ModBlocks.POINTED_ICICLE.get(), new Properties().m_41491_(ModGroup.BLOCK))
   );
   public static final RegistryObject<Item> WITHERITE_INGOT = ITEMS.register(
      "witherite_ingot", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> ENDERITE_INGOT = ITEMS.register(
      "enderite_ingot", () -> new ItemInventoryOnly(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> ANCIENT_METAL_INGOT = ITEMS.register(
      "ancient_metal_ingot", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> ANCIENT_METAL_NUGGET = ITEMS.register(
      "ancient_metal_nugget", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> BLACK_STEEL_INGOT = ITEMS.register("black_steel_ingot", () -> new Item(new Properties().m_41491_(ModGroup.ITEM)));
   public static final RegistryObject<Item> BLACK_STEEL_NUGGET = ITEMS.register("black_steel_nugget", () -> new Item(new Properties().m_41491_(ModGroup.ITEM)));
   public static final RegistryObject<Item> IGNITIUM_INGOT = ITEMS.register(
      "ignitium_ingot", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> CURSIUM_INGOT = ITEMS.register(
      "cursium_ingot", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> CHAIN_OF_SOUL_BINDING = ITEMS.register("chain_of_soul_binding", () -> new ItemInventoryOnly(new Properties()));
   public static final RegistryObject<Item> CORAL_SPEAR = ITEMS.register(
      "coral_spear", () -> new Coral_Spear(new Properties().m_41491_(ModGroup.ITEM).m_41503_(110))
   );
   public static final RegistryObject<Item> CORAL_BARDICHE = ITEMS.register(
      "coral_bardiche", () -> new Coral_Bardiche(new Properties().m_41491_(ModGroup.ITEM).m_41503_(160))
   );
   public static final RegistryObject<Item> ATHAME = ITEMS.register("athame", () -> new Athame(new Properties().m_41491_(ModGroup.ITEM).m_41503_(250)));
   public static final RegistryObject<Item> KHOPESH = ITEMS.register(
      "khopesh", () -> new Khopesh(Tooltier.ANCIENT_METAL, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BLACK_STEEL_SWORD = ITEMS.register(
      "black_steel_sword", () -> new SwordItem(Tooltier.BLACK_STEEL, 3, -2.4F, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BLACK_STEEL_SHOVEL = ITEMS.register(
      "black_steel_shovel", () -> new ShovelItem(Tooltier.BLACK_STEEL, 1.5F, -3.0F, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BLACK_STEEL_PICKAXE = ITEMS.register(
      "black_steel_pickaxe", () -> new PickaxeItem(Tooltier.BLACK_STEEL, 1, -2.8F, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BLACK_STEEL_AXE = ITEMS.register(
      "black_steel_axe", () -> new AxeItem(Tooltier.BLACK_STEEL, 6.0F, -3.1F, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BLACK_STEEL_HOE = ITEMS.register(
      "black_steel_hoe", () -> new HoeItem(Tooltier.BLACK_STEEL, -2, -1.0F, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BLACK_STEEL_TARGE = ITEMS.register(
      "black_steel_targe", () -> new Black_Steel_Targe(new Properties().m_41491_(ModGroup.ITEM).m_41503_(840))
   );
   public static final RegistryObject<Item> BULWARK_OF_THE_FLAME = ITEMS.register(
      "bulwark_of_the_flame", () -> new Bulwark_of_the_flame(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> GAUNTLET_OF_GUARD = ITEMS.register(
      "gauntlet_of_guard", () -> new Gauntlet_of_Guard(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> GAUNTLET_OF_BULWARK = ITEMS.register(
      "gauntlet_of_bulwark", () -> new Gauntlet_of_Bulwark(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> GAUNTLET_OF_MAELSTROM = ITEMS.register(
      "gauntlet_of_maelstrom", () -> new Gauntlet_of_Maelstrom(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> THE_INCINERATOR = ITEMS.register(
      "the_incinerator", () -> new The_Incinerator(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> BLAZING_GRIPS = ITEMS.register(
      "blazing_grips", () -> new Blazing_Grips(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> CURSED_BOW = ITEMS.register(
      "cursed_bow", () -> new Cursed_bow(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> WRATH_OF_THE_DESERT = ITEMS.register(
      "wrath_of_the_desert", () -> new Wrath_of_the_desert(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> SOUL_RENDER = ITEMS.register(
      "soul_render", () -> new Soul_Render(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> THE_ANNIHILATOR = ITEMS.register(
      "the_annihilator", () -> new The_Annihilator(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> THE_IMMOLATOR = ITEMS.register(
      "the_immolator", () -> new The_Immolator(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> MEAT_SHREDDER = ITEMS.register(
      "meat_shredder", () -> new Meat_Shredder(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> LASER_GATLING = ITEMS.register(
      "laser_gatling", () -> new Laser_Gatling(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41486_().m_41503_(50).m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> WITHER_ASSULT_SHOULDER_WEAPON = ITEMS.register(
      "wither_assault_shoulder_weapon",
      () -> new Wither_Assault_SHoulder_Weapon(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> VOID_ASSULT_SHOULDER_WEAPON = ITEMS.register(
      "void_assault_shoulder_weapon",
      () -> new Void_Assault_SHoulder_Weapon(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> VOID_FORGE = ITEMS.register(
      "void_forge", () -> new void_forge(Tooltier.MONSTROSITY, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> TIDAL_CLAWS = ITEMS.register(
      "tidal_claws", () -> new Tidal_Claws(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> FINAL_FRACTAL = ITEMS.register(
      "final_fractal", () -> new final_fractal(ModItemTier.TOOL_WITHERITE, new Properties().m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> ZWEIENDER = ITEMS.register(
      "zweiender", () -> new zweiender(ModItemTier.TOOL_ENDERITE, new Properties().m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> INFERNAL_FORGE = ITEMS.register(
      "infernal_forge", () -> new infernal_forge(Tooltier.MONSTROSITY, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> SANDSTORM_IN_A_BOTTLE = ITEMS.register(
      "sandstorm_in_a_bottle", () -> new Sandstorm_In_A_Bottle(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> ANCIENT_SPEAR = ITEMS.register(
      "ancient_spear", () -> new Ancient_Spear(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_().m_41503_(1800))
   );
   public static final RegistryObject<Item> STICKY_GLOVES = ITEMS.register(
      "sticky_gloves", () -> new Sticky_Gloves(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1))
   );
   public static final RegistryObject<Item> REMNANT_SKULL = ITEMS.register(
      "remnant_skull", () -> new Remnant_Skull(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> NETHERITE_EFFIGY = ITEMS.register(
      "netherite_effigy", () -> new Netherite_Effigy(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_())
   );
   public static final RegistryObject<Item> VOID_SCATTER_ARROW = ITEMS.register(
      "void_scatter_arrow", () -> new Void_Scatter_Arrow_Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<Item> VOID_SHARD = ITEMS.register("void_shard", () -> new ItemInventoryOnly(new Properties().m_41486_()));
   public static final RegistryObject<Item> BLAZING_BONE = ITEMS.register("blazing_bone", () -> new ItemInventoryOnly(new Properties().m_41486_()));
   public static final RegistryObject<Item> KOBOLETON_BONE = ITEMS.register("koboleton_bone", () -> new Item(new Properties().m_41491_(ModGroup.ITEM)));
   public static final RegistryObject<Item> VOID_JAW = ITEMS.register("void_jaw", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_()));
   public static final RegistryObject<Item> VOID_CORE = ITEMS.register(
      "void_core", () -> new void_core(new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41486_().m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> CRYSTALLIZED_CORAL_FRAGMENTS = ITEMS.register(
      "crystallized_coral_fragments", () -> new Item(new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> CRYSTALLIZED_CORAL = ITEMS.register("crystallized_coral", () -> new Item(new Properties().m_41491_(ModGroup.ITEM)));
   public static final RegistryObject<Item> CORAL_CHUNK = ITEMS.register("coral_chunk", () -> new Item(new Properties().m_41491_(ModGroup.ITEM)));
   public static final RegistryObject<Item> ABYSSAL_SACRIFICE = ITEMS.register(
      "abyssal_sacrifice", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.RARE))
   );
   public static final RegistryObject<Item> NECKLACE_OF_THE_DESERT = ITEMS.register(
      "necklace_of_the_desert", () -> new Necklace_Of_The_Desert(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.RARE))
   );
   public static final RegistryObject<Item> APTRGANGR_HEAD = ITEMS.register(
      "aptrgangr_head",
      () -> new CataclysmSkullItem(
            (Block)ModBlocks.APTRGANGR_HEAD.get(),
            (Block)ModBlocks.APTRGANGR_WALL_HEAD.get(),
            new Properties().m_41491_(ModGroup.BLOCK).m_41497_(Rarity.UNCOMMON)
         )
   );
   public static final RegistryObject<Item> DRAUGR_HEAD = ITEMS.register(
      "draugr_head",
      () -> new CataclysmSkullItem(
            (Block)ModBlocks.DRAUGR_HEAD.get(), (Block)ModBlocks.DRAUGR_WALL_HEAD.get(), new Properties().m_41491_(ModGroup.BLOCK).m_41497_(Rarity.UNCOMMON)
         )
   );
   public static final RegistryObject<Item> KOBOLEDIATOR_SKULL = ITEMS.register(
      "kobolediator_skull",
      () -> new CataclysmSkullItem(
            (Block)ModBlocks.KOBOLEDIATOR_SKULL.get(),
            (Block)ModBlocks.KOBOLEDIATOR_WALL_SKULL.get(),
            new Properties().m_41491_(ModGroup.BLOCK).m_41497_(Rarity.UNCOMMON)
         )
   );
   public static final RegistryObject<Item> BONE_REPTILE_HELMET = ITEMS.register(
      "bone_reptile_helmet", () -> new Bone_Reptile_Armor(Armortier.BONE_REPTILE, EquipmentSlot.HEAD, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> BONE_REPTILE_CHESTPLATE = ITEMS.register(
      "bone_reptile_chestplate", () -> new Bone_Reptile_Armor(Armortier.BONE_REPTILE, EquipmentSlot.CHEST, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> IGNITIUM_HELMET = ITEMS.register(
      "ignitium_helmet",
      () -> new Ignitium_Armor(Armortier.IGNITIUM, EquipmentSlot.HEAD, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> IGNITIUM_CHESTPLATE = ITEMS.register(
      "ignitium_chestplate",
      () -> new Ignitium_Armor(Armortier.IGNITIUM, EquipmentSlot.CHEST, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> IGNITIUM_ELYTRA_CHESTPLATE = ITEMS.register(
      "ignitium_elytra_chestplate",
      () -> new Ignitium_Elytra_ChestPlate(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC), Armortier.IGNITIUM)
   );
   public static final RegistryObject<Item> IGNITIUM_LEGGINGS = ITEMS.register(
      "ignitium_leggings",
      () -> new Ignitium_Armor(Armortier.IGNITIUM, EquipmentSlot.LEGS, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> IGNITIUM_BOOTS = ITEMS.register(
      "ignitium_boots",
      () -> new Ignitium_Armor(Armortier.IGNITIUM, EquipmentSlot.FEET, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> CURSIUM_HELMET = ITEMS.register(
      "cursium_helmet",
      () -> new Cursium_Armor(Armortier.CURSIUM, EquipmentSlot.HEAD, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> CURSIUM_CHESTPLATE = ITEMS.register(
      "cursium_chestplate",
      () -> new Cursium_Armor(Armortier.CURSIUM, EquipmentSlot.CHEST, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> CURSIUM_LEGGINGS = ITEMS.register(
      "cursium_leggings",
      () -> new Cursium_Armor(Armortier.CURSIUM, EquipmentSlot.LEGS, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> CURSIUM_BOOTS = ITEMS.register(
      "cursium_boots",
      () -> new Cursium_Armor(Armortier.CURSIUM, EquipmentSlot.FEET, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> MONSTROUS_HORN = ITEMS.register(
      "monstrous_horn", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> LAVA_POWER_CELL = ITEMS.register(
      "lava_power_cell", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> MONSTROUS_HELM = ITEMS.register(
      "monstrous_helm",
      () -> new Monstrous_Helm(ArmorMaterials.NETHERITE, EquipmentSlot.HEAD, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.EPIC))
   );
   public static final RegistryObject<Item> BLOOM_STONE_PAULDRONS = ITEMS.register(
      "bloom_stone_pauldrons",
      () -> new Bloom_Stone_Pauldrons(Armortier.CRAB, EquipmentSlot.CHEST, new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> BURNING_ASHES = ITEMS.register(
      "burning_ashes", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.RARE))
   );
   public static final RegistryObject<Item> DYING_EMBER = ITEMS.register(
      "dying_ember", () -> new Item(new Properties().m_41491_(ModGroup.ITEM).m_41486_().m_41497_(Rarity.UNCOMMON))
   );
   public static final RegistryObject<Item> MUSIC_DISC_NETHERITE_MONSTROSITY = ITEMS.register(
      "music_disc_netherite_monstrosity",
      () -> new RecordItem(14, ModSounds.MONSTROSITY_MUSIC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 5780)
   );
   public static final RegistryObject<Item> MUSIC_DISC_ENDER_GUARDIAN = ITEMS.register(
      "music_disc_ender_guardian",
      () -> new RecordItem(14, ModSounds.ENDERGUARDIAN_MUSIC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 4520)
   );
   public static final RegistryObject<Item> MUSIC_DISC_IGNIS = ITEMS.register(
      "music_disc_ignis",
      () -> new RecordItem(14, ModSounds.IGNIS_MUSIC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 3060)
   );
   public static final RegistryObject<Item> MUSIC_DISC_THE_HARBINGER = ITEMS.register(
      "music_disc_the_harbinger",
      () -> new RecordItem(14, ModSounds.HARBINGER_MUSIC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 2880)
   );
   public static final RegistryObject<Item> MUSIC_DISC_THE_LEVIATHAN = ITEMS.register(
      "music_disc_the_leviathan",
      () -> new RecordItem(14, ModSounds.LEVIATHAN_MUSIC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 5820)
   );
   public static final RegistryObject<Item> MUSIC_DISC_ANCIENT_REMNANT = ITEMS.register(
      "music_disc_ancient_remnant",
      () -> new RecordItem(14, ModSounds.REMNANT_MUSIC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 4240)
   );
   public static final RegistryObject<Item> MUSIC_DISC_MALEDICTUS = ITEMS.register(
      "music_disc_maledictus",
      () -> new RecordItem(14, ModSounds.MALEDICTUS_MUSIC_DISC, new Properties().m_41491_(ModGroup.ITEM).m_41487_(1).m_41497_(Rarity.EPIC).m_41486_(), 4020)
   );
   public static final RegistryObject<Item> MECH_EYE = ITEMS.register("mech_eye", () -> new MechEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_()));
   public static final RegistryObject<Item> FLAME_EYE = ITEMS.register("flame_eye", () -> new FlameEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_()));
   public static final RegistryObject<Item> VOID_EYE = ITEMS.register("void_eye", () -> new VoidEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_()));
   public static final RegistryObject<Item> MONSTROUS_EYE = ITEMS.register(
      "monstrous_eye", () -> new MonstrousEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<Item> ABYSS_EYE = ITEMS.register("abyss_eye", () -> new AbyssEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_()));
   public static final RegistryObject<Item> DESERT_EYE = ITEMS.register(
      "desert_eye", () -> new DesertEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<Item> CURSED_EYE = ITEMS.register(
      "cursed_eye", () -> new CursedEyeItem(new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<Item> LIONFISH = ITEMS.register(
      "lionfish",
      () -> new Item(
            new Properties()
               .m_41491_(ModGroup.ITEM)
               .m_41489_(
                  new Builder()
                     .m_38760_(1)
                     .m_38758_(0.1F)
                     .m_38762_(new MobEffectInstance(MobEffects.f_19614_, 300, 0), 1.0F)
                     .m_38762_(new MobEffectInstance(MobEffects.f_19604_, 300, 0), 1.0F)
                     .m_38767_()
               )
         )
   );
   public static final RegistryObject<Item> AMETHYST_CRAB_MEAT = ITEMS.register(
      "amethyst_crab_meat",
      () -> new Item(
            new Properties()
               .m_41491_(ModGroup.ITEM)
               .m_41489_(new Builder().m_38760_(6).m_38758_(1.2F).m_38762_(new MobEffectInstance(MobEffects.f_19605_, 100, 0), 1.0F).m_38767_())
         )
   );
   public static final RegistryObject<Item> BLESSED_AMETHYST_CRAB_MEAT = ITEMS.register(
      "blessed_amethyst_crab_meat",
      () -> new Blessed_Amethyst_Crab_Meat(
            new Properties()
               .m_41491_(ModGroup.ITEM)
               .m_41497_(Rarity.EPIC)
               .m_41489_(
                  new Builder()
                     .m_38760_(6)
                     .m_38758_(1.2F)
                     .m_38762_(new MobEffectInstance(MobEffects.f_19605_, 100, 0), 1.0F)
                     .m_38762_(new MobEffectInstance((MobEffect)ModEffect.EFFECTBLESSING_OF_AMETHYST.get(), 1800, 0), 1.0F)
                     .m_38765_()
                     .m_38767_()
               )
         )
   );
   public static final RegistryObject<Item> AMETHYST_CRAB_SHELL = ITEMS.register(
      "amethyst_crab_shell", () -> new Item(new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<Item> LIONFISH_SPIKE = ITEMS.register("lionfish_spike", () -> new ItemInventoryOnly(new Properties()));
   public static final RegistryObject<Item> THE_BABY_LEVIATHAN_BUCKET = ITEMS.register(
      "the_baby_leviathan_bucket",
      () -> new ModFishBucket(ModEntities.THE_BABY_LEVIATHAN, Fluids.f_76193_, new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<Item> MODERN_REMNANT_BUCKET = ITEMS.register(
      "modern_remnant_bucket", () -> new ModernRemantBucket(ModEntities.MODERN_REMNANT, Fluids.f_76191_, new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<Item> NETHERITE_MINISTROSITY_BUCKET = ITEMS.register(
      "netherite_ministrosity_bucket",
      () -> new ModernRemantBucket(ModEntities.NETHERITE_MINISTROSITY, Fluids.f_76191_, new Properties().m_41491_(ModGroup.ITEM).m_41486_())
   );
   public static final RegistryObject<SpawnEggItem> ENDER_GOLEM_SPAWN_EGG = ITEMS.register(
      "ender_golem_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENDER_GOLEM, 2759234, 10572798, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> NETHERITE_MONSTROSITY_SPAWN_EGG = ITEMS.register(
      "netherite_monstrosity_spawn_egg",
      () -> new ForgeSpawnEggItem(ModEntities.NETHERITE_MONSTROSITY, 5065037, 16024866, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> NETHERITE_MINISTROSITY_SPAWN_EGG = ITEMS.register(
      "netherite_ministrosity_spawn_egg",
      () -> new ForgeSpawnEggItem(ModEntities.NETHERITE_MINISTROSITY, 7039083, 12738305, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> NAMELESS_SORCERER_SPAWN_EGG = ITEMS.register(
      "nameless_sorcerer_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.NAMELESS_SORCERER, 9804699, 12133412, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> ENDER_GUARDIAN_SPAWN_EGG = ITEMS.register(
      "ender_guardian_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENDER_GUARDIAN, 2759234, 9725844, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> ENDERMAPTERA_SPAWN_EGG = ITEMS.register(
      "endermaptera_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ENDERMAPTERA, 2759234, 7237230, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> IGNIS_SPAWN_EGG = ITEMS.register(
      "ignis_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.IGNIS, 16167425, 16579584, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> IGNITED_REVENANT_SPAWN_EGG = ITEMS.register(
      "ignited_revenant_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.IGNITED_REVENANT, 4672845, 16579584, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> IGNITED_BERSERKER_SPAWN_EGG = ITEMS.register(
      "ignited_berserker_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.IGNITED_BERSERKER, 4672845, 16579584, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> THE_WATCHER_SPAWN_EGG = ITEMS.register(
      "the_watcher_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.THE_WATCHER, 7568523, 15219515, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> THE_PROWLER_SPAWN_EGG = ITEMS.register(
      "the_prowler_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.THE_PROWLER, 1974305, 6827554, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> THE_HARBINGER_SPAWN_EGG = ITEMS.register(
      "the_harbinger_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.THE_HARBINGER, 1974305, 11412276, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> THE_LEVIATHAN_SPAWN_EGG = ITEMS.register(
      "the_leviathan_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.THE_LEVIATHAN, 1379867, 6619391, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> THE_BABY_LEVIATHAN_SPAWN_EGG = ITEMS.register(
      "the_baby_leviathan_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.THE_BABY_LEVIATHAN, 3285313, 9060095, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> DEEPLING_SPAWN_EGG = ITEMS.register(
      "deepling_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DEEPLING, 1583676, 12250612, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> DEEPLING_BRUTE_SPAWN_EGG = ITEMS.register(
      "deepling_brute_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DEEPLING_BRUTE, 1583676, 6619391, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> DEEPLING_ANGLER_SPAWN_EGG = ITEMS.register(
      "deepling_angler_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DEEPLING_ANGLER, 1583676, 10016994, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> DEEPLING_PRIEST_SPAWN_EGG = ITEMS.register(
      "deepling_priest_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DEEPLING_PRIEST, 1583676, 532564, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> DEEPLING_WARLOCK_SPAWN_EGG = ITEMS.register(
      "deepling_warlock_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DEEPLING_WARLOCK, 1583676, 14051992, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> LIONFISH_SPAWN_EGG = ITEMS.register(
      "lionfish_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.LIONFISH, 10016994, 1583676, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> CORAL_GOLEM_SPAWN_EGG = ITEMS.register(
      "coral_golem_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.CORAL_GOLEM, 2180004, 10756655, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> CORALSSUS_SPAWN_EGG = ITEMS.register(
      "coralssus_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.CORALSSUS, 4156645, 15592524, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> AMETHYST_CRAB_SPAWN_EGG = ITEMS.register(
      "amethyst_crab_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.AMETHYST_CRAB, 6579300, 8018869, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> KOBOLETON_SPAWN_EGG = ITEMS.register(
      "koboleton_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.KOBOLETON, 12038550, 14778627, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> KOBOLEDIATOR_SPAWN_EGG = ITEMS.register(
      "kobolediator_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.KOBOLEDIATOR, 12038550, 9722673, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> WADJET_SPAWN_EGG = ITEMS.register(
      "wadjet_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.WADJET, 12038550, 14399594, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> ANCIENT_REMNANT_SPAWN_EGG = ITEMS.register(
      "ancient_remnant_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ANCIENT_REMNANT, 12038550, 6827554, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> MODERN_REMNANT_SPAWN_EGG = ITEMS.register(
      "modern_remnant_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MODERN_REMNANT, 12038550, 14404775, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> MALEDICTUS_SPAWN_EGG = ITEMS.register(
      "maledictus_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MALEDICTUS, 3789490, 9722673, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> ELITE_DRAUGR_SPAWN_EGG = ITEMS.register(
      "elite_draugr_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ELITE_DRAUGR, 3744022, 4465432, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> APTRGANGR_SPAWN_EGG = ITEMS.register(
      "aptrgangr_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.APTRGANGR, 3744022, 15263716, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> ROYAL_DRAUGR_SPAWN_EGG = ITEMS.register(
      "royal_draugr_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ROYAL_DRAUGR, 3744022, 9722673, new Properties().m_41491_(ModGroup.ITEM))
   );
   public static final RegistryObject<SpawnEggItem> DRAUGR_SPAWN_EGG = ITEMS.register(
      "draugr_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.DRAUGR, 3744022, 2828325, new Properties().m_41491_(ModGroup.ITEM))
   );

   public static void initDispenser() {
      DispenserBlock.m_52672_(
         (ItemLike)VOID_SCATTER_ARROW.get(),
         new AbstractProjectileDispenseBehavior() {
            protected Projectile m_6895_(Level worldIn, Position position, ItemStack stackIn) {
               return new Void_Scatter_Arrow_Entity(
                  (EntityType)ModEntities.VOID_SCATTER_ARROW.get(), position.m_7096_(), position.m_7098_(), position.m_7094_(), worldIn
               );
            }
         }
      );
      DispenseItemBehavior bucketDispenseBehavior = new DefaultDispenseItemBehavior() {
         private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

         public ItemStack m_7498_(BlockSource blockSource, ItemStack stack) {
            DispensibleContainerItem dispensiblecontaineritem = (DispensibleContainerItem)stack.m_41720_();
            BlockPos blockpos = blockSource.m_7961_().m_121945_((Direction)blockSource.m_6414_().m_61143_(DispenserBlock.f_52659_));
            Level level = blockSource.m_7727_();
            if (dispensiblecontaineritem.m_142073_((Player)null, level, blockpos, (BlockHitResult)null)) {
               dispensiblecontaineritem.m_142131_((Player)null, level, stack, blockpos);
               return new ItemStack(Items.f_42446_);
            } else {
               return this.defaultDispenseItemBehavior.m_6115_(blockSource, stack);
            }
         }
      };
      DispenserBlock.m_52672_((ItemLike)THE_BABY_LEVIATHAN_BUCKET.get(), bucketDispenseBehavior);
   }
}
