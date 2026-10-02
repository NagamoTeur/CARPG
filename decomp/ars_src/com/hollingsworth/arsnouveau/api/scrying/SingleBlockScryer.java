package com.hollingsworth.arsnouveau.api.scrying;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SingleBlockScryer implements IScryer {
   public static SingleBlockScryer INSTANCE = new SingleBlockScryer(null);
   public Block block;

   public SingleBlockScryer(Block block) {
      this.block = block;
   }

   @Override
   public boolean shouldRevealBlock(BlockState state, BlockPos p, Player player) {
      return this.block == null ? false : state.m_60734_() == this.block;
   }

   @Override
   public IScryer fromTag(CompoundTag tag) {
      SingleBlockScryer scryer = new SingleBlockScryer(null);
      scryer.block = tag.m_128441_("block") ? (Block)Registry.f_122824_.m_7745_(new ResourceLocation(tag.m_128461_("block"))) : null;
      return scryer;
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      if (this.block != null) {
         tag.m_128359_("block", RegistryHelper.getRegistryName(this.block).toString());
      }

      return IScryer.super.toTag(tag);
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", "single_block");
   }
}
