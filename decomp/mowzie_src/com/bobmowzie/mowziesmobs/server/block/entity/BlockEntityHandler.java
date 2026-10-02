package com.bobmowzie.mowziesmobs.server.block.entity;

import com.bobmowzie.mowziesmobs.server.block.BlockHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityHandler {
   public static final DeferredRegister<BlockEntityType<?>> REG = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "mowziesmobs");
   public static RegistryObject<BlockEntityType<GongBlockEntity>> GONG_BLOCK_ENTITY = REG.register(
      "gong_entity", () -> Builder.m_155273_(GongBlockEntity::new, new Block[]{(Block)BlockHandler.GONG.get()}).m_58966_(null)
   );
}
