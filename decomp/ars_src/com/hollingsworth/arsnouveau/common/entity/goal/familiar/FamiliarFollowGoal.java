package com.hollingsworth.arsnouveau.common.entity.goal.familiar;

import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class FamiliarFollowGoal extends FamiliarBaseGoal {
   private LivingEntity theOwner;
   private final float maxDist;
   private final float minDist;
   double moveSpeed;

   public FamiliarFollowGoal(FamiliarEntity entityRobit, double moveSpeed, float min, float max) {
      super(entityRobit);
      this.moveSpeed = moveSpeed;
      this.minDist = min;
      this.maxDist = max;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   @Override
   public boolean m_8036_() {
      LivingEntity player = this.entity.getOwner();
      if (player != null && !player.m_5833_()) {
         if (this.entity.f_19853_.m_46472_() != player.f_19853_.m_46472_()) {
            return false;
         } else if (this.entity.m_20280_(player) < (double)(this.minDist * this.minDist)) {
            return false;
         } else {
            this.theOwner = player;
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean m_8045_() {
      boolean stillRunning = !this.entity.m_21573_().m_26571_()
         && this.entity.m_20280_(this.theOwner) > (double)(this.maxDist * this.maxDist)
         && this.theOwner.f_19853_.m_46472_() == this.entity.f_19853_.m_46472_();
      if (!stillRunning) {
         this.entity.m_21573_().m_26573_();
      }

      return stillRunning;
   }

   @Override
   public void m_8041_() {
      this.theOwner = null;
      super.m_8041_();
   }

   @Override
   public void m_8037_() {
      this.entity.m_21563_().m_24960_(this.theOwner, 6.0F, (float)this.entity.m_8132_() / 10.0F);
      if (!this.entity.m_20159_()) {
         if (this.entity.m_20280_(this.theOwner) >= 144.0 && this.entity.canTeleport()) {
            BlockPos targetPos = this.theOwner.m_20183_();
            this.teleportTo(this.entity, targetPos.m_123341_(), targetPos.m_123342_(), targetPos.m_123343_());
         } else {
            this.entity.m_21573_().m_5624_(this.theOwner, this.moveSpeed);
         }
      }
   }

   private int randomize(int min, int max) {
      return this.entity.m_217043_().m_188503_(max - min + 1) + min;
   }

   private void teleportTo(Entity target, int x, int y, int z) {
      this.entity.m_6034_((double)x, (double)y + 0.5, (double)z);
      this.entity.m_21573_().m_26573_();
   }
}
