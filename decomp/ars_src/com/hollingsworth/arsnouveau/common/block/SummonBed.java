package com.hollingsworth.arsnouveau.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SummonBed extends ModBlock {
   public static final VoxelShape collisionShape = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 3.0, 16.0);
   public static final VoxelShape shape = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;

   public SummonBed() {
      this(defaultProperties());
   }

   public SummonBed(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61448_, false));
   }

   public boolean isPowered(BlockState state, Level level, BlockPos pos) {
      return (Boolean)state.m_61143_(POWERED);
   }

   public void m_6861_(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
      boolean flag = pLevel.m_46753_(pPos);
      if (flag != (Boolean)pState.m_61143_(POWERED)) {
         pLevel.m_7731_(pPos, (BlockState)pState.m_61124_(POWERED, flag), 2);
      }
   }

   public VoxelShape m_5939_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return collisionShape;
   }

   public VoxelShape m_7947_(BlockState pState, BlockGetter pReader, BlockPos pPos) {
      return shape;
   }

   public VoxelShape m_5909_(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   protected void m_7926_(Builder<Block, BlockState> pBuilder) {
      pBuilder.m_61104_(new Property[]{POWERED});
   }

   public void m_142072_(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
      super.m_142072_(pLevel, pState, pPos, pEntity, pFallDistance * 0.5F);
   }

   public void m_5548_(BlockGetter pLevel, Entity pEntity) {
      if (pEntity.m_20162_()) {
         super.m_5548_(pLevel, pEntity);
      } else {
         this.bounceUp(pEntity);
      }
   }

   private void bounceUp(Entity pEntity) {
      Vec3 vec3 = pEntity.m_20184_();
      if (vec3.f_82480_ < 0.0) {
         double d0 = pEntity instanceof LivingEntity ? 1.0 : 0.8;
         pEntity.m_20334_(vec3.f_82479_, -vec3.f_82480_ * 0.66F * d0, vec3.f_82481_);
      }
   }
}
