package net.xylonity.knightquest.common.entity.entities.ai;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.xylonity.knightquest.common.entity.entities.SwampmanEntity;

public class RangedAttackGoal<T extends Mob & RangedAttackMob> extends Goal {
   private final T mob;
   private final double speedModifier;
   private int attackIntervalMin;
   private final float attackRadiusSqr;
   private int attackTime = -1;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime = -1;

   public RangedAttackGoal(T pMob, double pSpeedModifier, int pAttackIntervalMin, float pAttackRadius) {
      this.mob = pMob;
      this.speedModifier = pSpeedModifier;
      this.attackIntervalMin = pAttackIntervalMin;
      this.attackRadiusSqr = pAttackRadius * pAttackRadius;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (this.mob.m_5448_() != null && this.isHoldingRangedWeapon()) {
         return this.mob instanceof SwampmanEntity swampmanEntity ? swampmanEntity.getPhase() != 1 : true;
      } else {
         return false;
      }
   }

   protected boolean isHoldingRangedWeapon() {
      return this.mob.m_21093_(is -> is.m_41720_() instanceof ProjectileWeaponItem);
   }

   public boolean m_8045_() {
      return (this.m_8036_() || !this.mob.m_21573_().m_26571_()) && this.isHoldingRangedWeapon();
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
      this.mob.m_5810_();
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      LivingEntity livingentity = this.mob.m_5448_();
      if (livingentity != null) {
         double d0 = this.mob.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
         boolean flag = this.mob.m_21574_().m_148306_(livingentity);
         boolean flag1 = this.seeTime > 0;
         if (flag != flag1) {
            this.seeTime = 0;
         }

         if (flag) {
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
            if (this.mob.m_20202_() instanceof Mob mob) {
               mob.m_21391_(livingentity, 30.0F, 30.0F);
            }

            this.mob.m_21391_(livingentity, 30.0F, 30.0F);
         } else {
            this.mob.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
         }

         if (this.mob.m_6117_()) {
            if (!flag && this.seeTime < -60) {
               this.mob.m_5810_();
            } else if (flag) {
               int i = this.mob.m_21252_();
               if (i >= 20) {
                  this.mob.m_5810_();
                  this.mob.m_6504_(livingentity, this.getPowerForTime(i));
                  this.attackTime = this.attackIntervalMin;
               }
            }
         } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
            this.mob.m_6672_(ProjectileUtil.getWeaponHoldingHand(this.mob, item -> item instanceof ProjectileWeaponItem));
         }
      }
   }

   private float getPowerForTime(int pCharge) {
      float f = (float)pCharge / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }
}
