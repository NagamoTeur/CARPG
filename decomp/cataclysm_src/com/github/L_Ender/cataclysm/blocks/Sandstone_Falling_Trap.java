package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class Sandstone_Falling_Trap extends SandStoneTrapBlock implements Fallable {
   public Sandstone_Falling_Trap(Properties properties) {
      super(properties);
   }

   @Override
   public void m_141947_(Level worldIn, BlockPos pos, BlockState state, Entity entityIn) {
      this.activate(worldIn.m_8055_(pos), worldIn, pos, entityIn);
      super.m_141947_(worldIn, pos, state, entityIn);
   }

   private void activate(BlockState state, Level world, BlockPos pos, Entity entity) {
      if (!(Boolean)state.m_61143_(LIT) && shouldTrigger(entity) && isFree(world.m_8055_(pos.m_7495_())) && pos.m_123342_() >= world.m_141937_()) {
         FallingBlockEntity fallingblockentity = FallingBlockEntity.m_201971_(world, pos, state);
         this.falling(fallingblockentity);
      }
   }

   public static boolean isFree(BlockState p_53242_) {
      return p_53242_.m_60795_() || p_53242_.m_204336_(BlockTags.f_13076_) || p_53242_.m_60767_().m_76332_() || p_53242_.m_60767_().m_76336_();
   }

   protected void falling(FallingBlockEntity p_53206_) {
   }
}
