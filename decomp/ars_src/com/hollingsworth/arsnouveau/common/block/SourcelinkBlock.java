package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.SourcelinkTile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public abstract class SourcelinkBlock extends TickableModBlock {
   public SourcelinkBlock(Properties properties) {
      super(properties);
   }

   public boolean m_6724_(BlockState p_149653_1_) {
      return true;
   }

   public void m_213898_(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
      super.m_213898_(state, worldIn, pos, random);
      SourcelinkTile tile = (SourcelinkTile)worldIn.m_7702_(pos);
      if (tile != null) {
         tile.doRandomAction();
      }
   }

   public RenderShape m_7514_(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
