package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.IntangibleAirTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IntangibleAirBlock extends TickableModBlock implements LiquidBlockContainer {
   public IntangibleAirBlock() {
      super(Properties.m_60939_(Material.f_76310_).m_60910_().m_222994_());
   }

   public RenderShape m_7514_(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return Shapes.m_83040_();
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new IntangibleAirTile(pos, state);
   }

   protected boolean isAir(BlockState state) {
      return false;
   }

   public boolean m_6044_(BlockGetter worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
      return false;
   }

   public boolean m_7361_(LevelAccessor worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
      return false;
   }
}
