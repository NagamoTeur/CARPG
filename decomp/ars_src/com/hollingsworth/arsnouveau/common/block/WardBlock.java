package com.hollingsworth.arsnouveau.common.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class WardBlock extends ModBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;

   public WardBlock() {
      super(defaultProperties().m_60953_(bs -> 7));
   }

   public void m_7892_(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
      super.m_7892_(state, worldIn, pos, entityIn);
   }

   @Nullable
   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter world, BlockPos pos, @Nullable Mob entity) {
      return BlockPathTypes.LAVA;
   }

   public void m_6402_(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
      if (entity != null) {
         world.m_7731_(pos, (BlockState)state.m_61124_(BlockStateProperties.f_61372_, getFacingFromEntity(pos, entity)), 2);
      }
   }

   public boolean collisionExtendsVertically(BlockState state, BlockGetter world, BlockPos pos, Entity collidingEntity) {
      return collidingEntity instanceof Mob;
   }

   public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
      Vec3 vec = entity.m_20182_();
      return Direction.m_122372_(
         (float)(vec.f_82479_ - (double)clickedBlock.m_123341_()),
         (float)(vec.f_82480_ - (double)clickedBlock.m_123342_()),
         (float)(vec.f_82481_ - (double)clickedBlock.m_123343_())
      );
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{BlockStateProperties.f_61372_});
   }
}
