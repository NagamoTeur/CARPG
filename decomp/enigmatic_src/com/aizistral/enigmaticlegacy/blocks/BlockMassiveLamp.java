package com.aizistral.enigmaticlegacy.blocks;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;

public class BlockMassiveLamp extends Block {
   public BlockMassiveLamp() {
      super(Properties.m_60926_(Blocks.f_50681_).m_60918_(SoundType.f_56744_).m_60955_());
   }

   public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidState) {
      return super.shouldDisplayFluidOverlay(state, world, pos, fluidState);
   }

   public RenderShape m_7514_(BlockState p_60550_) {
      return RenderShape.MODEL;
   }

   public List<ItemStack> m_7381_(BlockState state, Builder builder) {
      return List.of(new ItemStack(this));
   }

   public boolean m_49967_() {
      return true;
   }

   public boolean m_7923_(BlockState state) {
      return true;
   }
}
