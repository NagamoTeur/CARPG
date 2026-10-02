package com.hollingsworth.arsnouveau.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

public class ModBlock extends Block {
   public ModBlock(Properties properties) {
      super(properties);
   }

   public ModBlock() {
      super(defaultProperties());
   }

   public static Properties defaultProperties() {
      return Properties.m_60939_(Material.f_76278_).m_60918_(SoundType.f_56742_).m_60913_(2.0F, 6.0F);
   }

   public boolean shouldDisplayFluidOverlay(BlockState state, BlockAndTintGetter world, BlockPos pos, FluidState fluidState) {
      return true;
   }
}
