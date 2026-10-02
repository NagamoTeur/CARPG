package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.blockentities.Abyssal_Egg_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfAbyss_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfAmethyst_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfFire_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.AltarOfVoid_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.Cataclysm_Skull_BlockEntity;
import com.github.L_Ender.cataclysm.blockentities.Cursed_tombstone_Entity;
import com.github.L_Ender.cataclysm.blockentities.Door_Of_Seal_BlockEntity;
import com.github.L_Ender.cataclysm.blockentities.EMP_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.Mechanical_fusion_Anvil_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.ObsidianExplosionTrapBricks_Block_Entity;
import com.github.L_Ender.cataclysm.blockentities.SandstoneIgniteTrap_Block_Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileentites {
   public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "cataclysm");
   public static final RegistryObject<BlockEntityType<ObsidianExplosionTrapBricks_Block_Entity>> OBSIDIAN_EXPLOSION_TRAP_BRICKS = TILE_ENTITY_TYPES.register(
      "obsidian_explosion_trap_bricks",
      () -> Builder.m_155273_(ObsidianExplosionTrapBricks_Block_Entity::new, new Block[]{(Block)ModBlocks.OBSIDIAN_EXPLOSION_TRAP_BRICKS.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<SandstoneIgniteTrap_Block_Entity>> SANDSTONE_IGNITE_TRAP = TILE_ENTITY_TYPES.register(
      "sadsotne_ignite_trap",
      () -> Builder.m_155273_(SandstoneIgniteTrap_Block_Entity::new, new Block[]{(Block)ModBlocks.SANDSTONE_IGNITE_TRAP.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<AltarOfVoid_Block_Entity>> ALTAR_OF_VOID = TILE_ENTITY_TYPES.register(
      "altar_of_void", () -> Builder.m_155273_(AltarOfVoid_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_VOID.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<AltarOfFire_Block_Entity>> ALTAR_OF_FIRE = TILE_ENTITY_TYPES.register(
      "altar_of_fire", () -> Builder.m_155273_(AltarOfFire_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_FIRE.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<AltarOfAmethyst_Block_Entity>> ALTAR_OF_AMETHYST = TILE_ENTITY_TYPES.register(
      "altar_of_amethyst", () -> Builder.m_155273_(AltarOfAmethyst_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_AMETHYST.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<AltarOfAbyss_Block_Entity>> ALTAR_OF_ABYSS = TILE_ENTITY_TYPES.register(
      "altar_of_abyss", () -> Builder.m_155273_(AltarOfAbyss_Block_Entity::new, new Block[]{(Block)ModBlocks.ALTAR_OF_ABYSS.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<Cursed_tombstone_Entity>> CURSED_TOMBSTONE = TILE_ENTITY_TYPES.register(
      "cursed_tombstone", () -> Builder.m_155273_(Cursed_tombstone_Entity::new, new Block[]{(Block)ModBlocks.CURSED_TOMBSTONE.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<Abyssal_Egg_Block_Entity>> ABYSSAL_EGG = TILE_ENTITY_TYPES.register(
      "abyssal_egg", () -> Builder.m_155273_(Abyssal_Egg_Block_Entity::new, new Block[]{(Block)ModBlocks.ABYSSAL_EGG.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<EMP_Block_Entity>> EMP = TILE_ENTITY_TYPES.register(
      "emp", () -> Builder.m_155273_(EMP_Block_Entity::new, new Block[]{(Block)ModBlocks.EMP.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<Cataclysm_Skull_BlockEntity>> CATACLYSM_SKULL = TILE_ENTITY_TYPES.register(
      "cataclysm_skull",
      () -> Builder.m_155273_(
               Cataclysm_Skull_BlockEntity::new,
               new Block[]{
                  (Block)ModBlocks.KOBOLEDIATOR_SKULL.get(),
                  (Block)ModBlocks.KOBOLEDIATOR_WALL_SKULL.get(),
                  (Block)ModBlocks.APTRGANGR_HEAD.get(),
                  (Block)ModBlocks.APTRGANGR_WALL_HEAD.get(),
                  (Block)ModBlocks.DRAUGR_HEAD.get(),
                  (Block)ModBlocks.DRAUGR_WALL_HEAD.get()
               }
            )
            .m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<Mechanical_fusion_Anvil_Block_Entity>> MECHANICAL_FUSION_ANVIL = TILE_ENTITY_TYPES.register(
      "mechanical_fusion_anvil",
      () -> Builder.m_155273_(Mechanical_fusion_Anvil_Block_Entity::new, new Block[]{(Block)ModBlocks.MECHANICAL_FUSION_ANVIL.get()}).m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<Door_Of_Seal_BlockEntity>> DOOR_OF_SEAL = TILE_ENTITY_TYPES.register(
      "door_of_seal", () -> Builder.m_155273_(Door_Of_Seal_BlockEntity::new, new Block[]{(Block)ModBlocks.DOOR_OF_SEAL.get()}).m_58966_(null)
   );
}
