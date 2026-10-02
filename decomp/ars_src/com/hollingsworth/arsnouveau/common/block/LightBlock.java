package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.tile.LightTile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LightBlock extends ModBlock implements EntityBlock, ITickableBlock, SimpleWaterloggedBlock {
   protected static final VoxelShape SHAPE = Block.m_49796_(4.0, 4.0, 4.0, 12.0, 12.0, 12.0);

   public LightBlock() {
      super(
         defaultProperties()
            .m_60953_(bs -> bs.m_61143_(SconceBlock.LIGHT_LEVEL) == 0 ? 14 : (Integer)bs.m_61143_(SconceBlock.LIGHT_LEVEL))
            .m_60910_()
            .m_60955_()
            .m_60988_()
            .m_60913_(0.0F, 0.0F)
      );
      this.m_49959_((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, false));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new LightTile(pos, state);
   }

   public boolean m_6724_(BlockState state) {
      return false;
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{SconceBlock.LIGHT_LEVEL, BlockStateProperties.f_61362_});
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      if (context.m_43725_().m_7702_(context.m_8083_()) instanceof LightTile tile) {
         RandomSource random = context.m_43725_().f_46441_;
         tile.color = new ParticleColor(Math.max(10, random.m_188503_(255)), Math.max(10, random.m_188503_(255)), Math.max(10, random.m_188503_(255)));
      }

      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      return (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_);
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }
}
