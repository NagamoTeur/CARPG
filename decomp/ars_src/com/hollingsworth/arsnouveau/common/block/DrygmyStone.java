package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.DrygmyTile;
import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class DrygmyStone extends SummonBlock implements SimpleWaterloggedBlock {
   public static VoxelShape shape = Stream.of(
         Block.m_49796_(16.0, 3.0, 9.5, 16.0, 6.0, 11.5),
         Block.m_49796_(9.0, 0.0, 0.5, 12.0, 5.0, 2.5),
         Block.m_49796_(4.0, 0.0, 0.5, 7.0, 5.0, 2.5),
         Block.m_49796_(3.5, 5.0, 0.0, 12.5, 7.0, 3.0),
         Block.m_49796_(13.5, 0.0, 4.0, 15.5, 6.0, 7.0),
         Block.m_49796_(13.5, 0.0, 9.0, 15.5, 6.0, 12.0),
         Block.m_49796_(13.0, 6.0, 3.5, 16.0, 8.0, 12.5),
         Block.m_49796_(4.0, 0.0, 13.5, 7.0, 7.0, 15.5),
         Block.m_49796_(9.0, 0.0, 13.5, 12.0, 7.0, 15.5),
         Block.m_49796_(3.5, 7.0, 13.0, 12.5, 9.0, 16.0),
         Block.m_49796_(0.5, 0.0, 4.0, 2.5, 8.0, 7.0),
         Block.m_49796_(0.5, 0.0, 9.0, 2.5, 8.0, 12.0),
         Block.m_49796_(6.0, 9.0, 6.0, 10.0, 11.0, 10.0),
         Block.m_49796_(6.0, 0.0, 6.0, 10.0, 2.0, 10.0),
         Block.m_49796_(7.0, 2.0, 7.0, 9.0, 9.0, 9.0),
         Block.m_49796_(0.0, 8.0, 3.5, 3.0, 10.0, 12.5),
         Block.m_49796_(10.0, 5.0, 6.0, 10.0, 9.0, 10.0),
         Block.m_49796_(6.0, 5.0, 6.0, 6.0, 9.0, 10.0),
         Block.m_49796_(6.0, 5.0, 10.0, 10.0, 9.0, 10.0),
         Block.m_49796_(6.0, 5.0, 6.0, 10.0, 9.0, 6.0),
         Block.m_49796_(6.5, 1.0, 0.0, 10.5, 5.0, 0.0),
         Block.m_49796_(5.5, 2.0, 3.0, 8.5, 5.0, 3.0),
         Block.m_49796_(4.5, 5.0, 16.0, 8.5, 7.0, 16.0),
         Block.m_49796_(9.5, 3.0, 13.0, 11.5, 7.0, 13.0),
         Block.m_49796_(16.0, 4.0, 4.5, 16.0, 6.0, 8.5),
         Block.m_49796_(3.0, 4.0, 5.5, 3.0, 8.0, 9.5),
         Block.m_49796_(0.0, 4.0, 5.5, 0.0, 8.0, 7.5),
         Block.m_49796_(0.0, 3.0, 8.5, 0.0, 8.0, 11.5)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .orElse(Shapes.m_83144_());

   public DrygmyStone() {
      super(defaultProperties().m_60955_().m_60953_(b -> 8));
      this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(SummoningTile.CONVERTED, false)).m_61124_(BlockStateProperties.f_61362_, false));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new DrygmyTile(pos, state);
   }

   public VoxelShape m_5940_(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_, CollisionContext p_220053_4_) {
      return shape;
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }

   @Override
   protected void m_7926_(Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{BlockStateProperties.f_61362_});
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   @NotNull
   @Override
   public BlockState m_5573_(BlockPlaceContext context) {
      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      return (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_);
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }

   public void m_6861_(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
      super.m_6861_(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
      if (!pLevel.m_5776_() && pLevel.m_7702_(pPos) instanceof DrygmyTile henge) {
         henge.isOff = pLevel.m_46753_(pPos);
         henge.updateBlock();
      }
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
