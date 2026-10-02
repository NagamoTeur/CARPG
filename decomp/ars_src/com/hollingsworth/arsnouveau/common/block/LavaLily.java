package com.hollingsworth.arsnouveau.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LavaLily extends BushBlock {
   protected static final VoxelShape LILY_PAD_AABB = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
   public static final IntegerProperty LOC = IntegerProperty.m_61631_("loc", 0, 2);

   public LavaLily() {
      super(TickableModBlock.defaultProperties().m_60955_());
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return LILY_PAD_AABB;
   }

   public BlockState getState(Level world, BlockPos pos) {
      BlockState state = this.m_49966_();
      if (world.m_8055_(pos.m_7495_()).m_60734_() == Blocks.f_50069_) {
         state = (BlockState)state.m_61124_(LOC, 0);
      }

      if (world.m_8055_(pos.m_7495_()).m_60734_() == Blocks.f_50450_) {
         state = (BlockState)state.m_61124_(LOC, 1);
      }

      if (world.m_8055_(pos.m_7495_()).m_60734_() == Blocks.f_49991_) {
         state = (BlockState)state.m_61124_(LOC, 2);
      }

      return state;
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return this.getState(context.m_43725_(), context.m_8083_());
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{LOC});
   }

   public boolean m_7898_(BlockState state, LevelReader worldIn, BlockPos pos) {
      return super.m_7898_(state, worldIn, pos);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   public boolean m_7420_(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   protected boolean m_6266_(BlockState state, BlockGetter worldIn, BlockPos pos) {
      FluidState fluidstate = worldIn.m_6425_(pos);
      FluidState fluidstate1 = worldIn.m_6425_(pos.m_7494_());
      return fluidstate1.m_76152_() == Fluids.f_76191_;
   }
}
