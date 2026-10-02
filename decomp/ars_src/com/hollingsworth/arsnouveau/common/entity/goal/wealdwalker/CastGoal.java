package com.hollingsworth.arsnouveau.common.entity.goal.wealdwalker;

import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class CastGoal<T extends Mob & RangedAttackMob> extends Goal {
   private final T mob;
   private final double speedModifier;
   private int attackIntervalMin;
   private final float attackRadiusSqr;
   private int attackTime = -1;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime = -1;
   boolean hasAnimated;
   int animatedTicks;
   int delayTicks;
   int animId;
   boolean done;
   Supplier<Boolean> canUse;

   public CastGoal(T entity, double speed, int attackInterval, float attackRange, Supplier<Boolean> canUse, int animId, int delayTicks) {
      this.mob = entity;
      this.speedModifier = speed;
      this.attackIntervalMin = attackInterval;
      this.attackRadiusSqr = attackRange * attackRange;
      this.canUse = canUse;
      this.animId = animId;
      this.delayTicks = delayTicks;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      return this.canUse.get() && this.mob.m_5448_() != null;
   }

   public boolean m_8045_() {
      return (this.m_8036_() || !this.mob.m_21573_().m_26571_()) && !this.done;
   }

   public void m_8056_() {
      super.m_8056_();
      this.mob.m_21561_(true);
   }

   public void m_8041_() {
      super.m_8041_();
      this.mob.m_21561_(false);
      this.seeTime = 0;
      this.attackTime = -1;
      this.animatedTicks = 0;
      this.done = false;
      this.hasAnimated = false;
   }

   public void m_8037_() {
      LivingEntity livingentity = this.mob.m_5448_();
      if (livingentity != null) {
         double d0 = this.mob.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
         boolean canSeeEnemy = this.mob.m_21574_().m_148306_(livingentity);
         if (canSeeEnemy != this.seeTime > 0) {
            this.seeTime = 0;
         }

         if (canSeeEnemy) {
            this.seeTime++;
         } else {
            this.seeTime--;
         }

         if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 20) {
            this.mob.m_21573_().m_26573_();
            this.strafingTime++;
         } else {
            this.mob.m_21573_().m_5624_(livingentity, this.speedModifier);
            this.strafingTime = -1;
         }

         if (this.strafingTime >= 20) {
            if ((double)this.mob.m_217043_().m_188501_() < 0.3) {
               this.strafingClockwise = !this.strafingClockwise;
            }

            if ((double)this.mob.m_217043_().m_188501_() < 0.3) {
               this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
         }

         if (this.strafingTime > -1) {
            if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
               this.strafingBackwards = false;
            } else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
               this.strafingBackwards = true;
            }

            this.mob.m_21566_().m_24988_(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.mob.m_21391_(livingentity, 30.0F, 30.0F);
         } else {
            this.mob.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
         }

         if (this.seeTime >= 40 && !this.hasAnimated) {
            this.hasAnimated = true;
            Networking.sendToNearby(this.mob.f_19853_, this.mob, new PacketAnimEntity(this.mob.m_19879_(), this.animId));
         }

         if (this.hasAnimated) {
            this.animatedTicks++;
            if (this.animatedTicks >= this.delayTicks) {
               this.mob.m_6504_(this.mob.m_5448_(), 1.0F);
               this.done = true;
            }
         }
      }
   }
}
