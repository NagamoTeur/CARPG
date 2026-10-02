package com.hollingsworth.arsnouveau.api.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class BounceTimedEvent implements ITimedEvent {
   LivingEntity livingEntity;
   int duration;
   double oldY;

   public BounceTimedEvent(LivingEntity e, double oldY) {
      this.livingEntity = e;
      this.oldY = oldY;
   }

   @Override
   public void tick(boolean serverSide) {
      this.duration++;
      if (this.duration == 1) {
         double f = 0.935;
         Vec3 vec3d = this.livingEntity.m_20184_();
         this.livingEntity.m_20334_(vec3d.f_82479_ / f, this.oldY, vec3d.f_82481_ / f);
         this.livingEntity.f_19864_ = true;
      }
   }

   @Override
   public boolean isExpired() {
      return this.duration >= 1;
   }
}
