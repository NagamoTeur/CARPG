package com.hollingsworth.arsnouveau.common.entity.goal;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;

public class GoBackHomeGoal extends DistanceRestrictedGoal {
   Mob entity;
   Supplier<Boolean> shouldGo;

   public GoBackHomeGoal(Mob entity, Supplier<BlockPos> pos, int maxDistance) {
      super(pos, maxDistance);
      this.entity = entity;
      this.shouldGo = () -> true;
   }

   public GoBackHomeGoal(Mob entity, Supplier<BlockPos> pos, int maxDistance, Supplier<Boolean> shouldGo) {
      super(pos, maxDistance);
      this.entity = entity;
      this.shouldGo = shouldGo;
   }

   public void m_8037_() {
      if (this.positionFrom.get() != null && BlockUtil.distanceFrom(this.entity.m_20183_(), this.positionFrom.get()) > 5.0) {
         BlockPos homePos = this.positionFrom.get();
         this.entity.m_21573_().m_26519_((double)homePos.m_123341_(), (double)homePos.m_123342_(), (double)homePos.m_123343_(), 1.5);
      }
   }

   public boolean m_8045_() {
      return this.positionFrom != null && BlockUtil.distanceFrom(this.entity.m_20183_(), this.positionFrom.get()) > 5.0 && this.shouldGo.get();
   }

   public boolean m_8036_() {
      return this.entity.f_19853_.f_46441_.m_188501_() < 0.02F && this.positionFrom != null && !this.isInRange(this.entity.m_20183_()) && this.shouldGo.get();
   }
}
