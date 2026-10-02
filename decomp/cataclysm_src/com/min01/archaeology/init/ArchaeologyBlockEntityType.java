package com.min01.archaeology.init;

import com.min01.archaeology.blockentity.BrushableBlockEntity;
import com.min01.archaeology.blockentity.DecoratedPotBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArchaeologyBlockEntityType {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "minecraft");
   public static final RegistryObject<BlockEntityType<BrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITIES.register(
      "brushable_block",
      () -> Builder.m_155273_(
               BrushableBlockEntity::new, new Block[]{(Block)ArchaeologyBlocks.SUSPICIOUS_SAND.get(), (Block)ArchaeologyBlocks.SUSPICIOUS_GRAVEL.get()}
            )
            .m_58966_(null)
   );
   public static final RegistryObject<BlockEntityType<DecoratedPotBlockEntity>> DECORATED_POT = BLOCK_ENTITIES.register(
      "decorated_pot", () -> Builder.m_155273_(DecoratedPotBlockEntity::new, new Block[]{(Block)ArchaeologyBlocks.DECORATED_POT.get()}).m_58966_(null)
   );
}
