package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class MeltingNetherrack extends Block {
   public static final IntegerProperty AGE = BlockStateProperties.f_61407_;

   public MeltingNetherrack(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(AGE, 0));
   }

   public void m_213898_(BlockState p_221238_, ServerLevel p_221239_, BlockPos p_221240_, RandomSource p_221241_) {
      this.m_213897_(p_221238_, p_221239_, p_221240_, p_221241_);
   }

   public void m_213897_(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if (randomSource.m_188503_(3) == 0 && this.slightlyMelt(blockState, serverLevel, blockPos)) {
         serverLevel.m_186460_(blockPos, this, Mth.m_216271_(randomSource, 20, 40));
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{AGE});
   }

   private boolean slightlyMelt(BlockState blockState, ServerLevel level, BlockPos blockPos) {
      int MAX_AGE_BEFORE_LAVA = 3;
      int i = (Integer)blockState.m_61143_(AGE);
      if (i < 3) {
         level.m_7731_(blockPos, (BlockState)blockState.m_61124_(AGE, i + 1), 2);
         return false;
      } else {
         this.melt(blockState, level, blockPos);
         return true;
      }
   }

   private void melt(BlockState blockState, ServerLevel level, BlockPos blockPos) {
      level.m_46597_(blockPos, Blocks.f_49991_.m_49966_());
   }

   public ItemStack m_7397_(BlockGetter p_53570_, BlockPos p_53571_, BlockState p_53572_) {
      return ItemStack.f_41583_;
   }
}
