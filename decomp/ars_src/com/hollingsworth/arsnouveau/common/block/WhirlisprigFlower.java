package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.SummoningTile;
import com.hollingsworth.arsnouveau.common.block.tile.WhirlisprigTile;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WhirlisprigFlower extends SummonBlock implements SimpleWaterloggedBlock {
   VoxelShape shape = Stream.of(
         Block.m_49796_(7.0, 0.0, 7.0, 9.0, 10.0, 9.0),
         Block.m_49796_(6.0, 10.0, 6.0, 10.0, 12.0, 10.0),
         Block.m_49796_(1.92, 11.107, 6.0, 5.93, 12.107, 10.0),
         Block.m_49796_(10.07, 11.107, 6.0, 14.072, 12.107, 10.0),
         Block.m_49796_(6.0, 11.107, 10.077, 10.0, 12.1, 14.07),
         Block.m_49796_(6.0, 11.107, 1.93, 10.0, 12.1, 5.93)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();

   public WhirlisprigFlower(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(SummoningTile.CONVERTED, false)).m_61124_(BlockStateProperties.f_61362_, false));
   }

   public WhirlisprigFlower() {
      this(Properties.m_60939_(Material.f_76300_).m_60918_(SoundType.f_154665_).m_60955_().m_60913_(2.0F, 6.0F));
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new WhirlisprigTile(pPos, pState);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return this.shape;
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
      if (!pLevel.m_5776_() && pLevel.m_7702_(pPos) instanceof WhirlisprigTile flower) {
         flower.isOff = pLevel.m_46753_(pPos);
         flower.updateBlock();
      }
   }
}
