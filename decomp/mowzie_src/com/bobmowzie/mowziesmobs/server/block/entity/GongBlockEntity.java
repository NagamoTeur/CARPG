package com.bobmowzie.mowziesmobs.server.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GongBlockEntity extends BlockEntity {
   public int ticks;
   public boolean shaking;
   public Direction clickDirection;
   public Direction facing;

   public GongBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityHandler.GONG_BLOCK_ENTITY.get(), pos, state);
      this.facing = (Direction)state.m_61143_(BlockStateProperties.f_61374_);
   }

   public boolean m_7531_(int p_58837_, int p_58838_) {
      if (p_58837_ == 1) {
         this.clickDirection = Direction.m_122376_(p_58838_);
         this.ticks = 0;
         this.shaking = true;
         return true;
      } else {
         return super.m_7531_(p_58837_, p_58838_);
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState blockState, GongBlockEntity entity) {
      if (entity.shaking) {
         entity.ticks++;
      }

      if (entity.ticks >= 148) {
         entity.shaking = false;
         entity.ticks = 0;
      }
   }

   public void onHit(Direction p_58835_) {
      BlockPos blockpos = this.m_58899_();
      this.clickDirection = p_58835_;
      if (this.shaking) {
         this.ticks = 0;
      } else {
         this.shaking = true;
      }

      this.f_58857_.m_7696_(blockpos, this.m_58900_().m_60734_(), 1, p_58835_.m_122411_());
   }

   public AABB getRenderBoundingBox() {
      AABB bounds = super.getRenderBoundingBox();
      bounds = bounds.m_82369_(new Vec3(this.facing.m_122427_().m_122432_()));
      bounds = bounds.m_82369_(new Vec3(this.facing.m_122428_().m_122432_()));
      return bounds.m_82363_(0.0, 2.0, 0.0);
   }
}
