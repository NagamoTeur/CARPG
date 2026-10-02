package com.aqutheseal.celestisynth.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.jetbrains.annotations.Nullable;

public class SolarCrystalBlock extends Block {
   public SolarCrystalBlock(Properties properties) {
      super(properties);
   }

   public boolean m_7898_(BlockState state, LevelReader level, BlockPos pos) {
      return level.m_8055_(pos.m_7495_()).m_60783_(level, pos.m_7495_(), Direction.UP);
   }

   public void m_6240_(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      super.m_6240_(level, player, pos, state, blockEntity, stack);
      if (level.m_213780_().m_188503_(5) == 0) {
         level.m_46511_(null, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), 2.0F, BlockInteraction.DESTROY);
      }
   }
}
