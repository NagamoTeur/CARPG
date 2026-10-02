package com.hollingsworth.arsnouveau.api.scrying;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public interface IScryer {
   Vec3i DEFAULT_SIZE = new Vec3i(20, 120, 20);

   boolean shouldRevealBlock(BlockState var1, BlockPos var2, Player var3);

   IScryer fromTag(CompoundTag var1);

   default CompoundTag toTag(CompoundTag tag) {
      tag.m_128359_("id", this.getRegistryName().toString());
      return tag;
   }

   ResourceLocation getRegistryName();

   default Vec3i getScryingSize() {
      return DEFAULT_SIZE;
   }

   default int getScryMax() {
      return 50;
   }
}
