package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Goals;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Royal_Draugr_Entity;
import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.common.ToolActions;

public class Royal_DraugrAttackGoal extends Goal {
   protected final Royal_Draugr_Entity mob;
   private final double speedModifier;
   private final boolean followingTargetEvenIfNotSeen;
   private Path path;
   private double pathedTargetX;
   private double pathedTargetY;
   private double pathedTargetZ;
   private int ticksUntilNextPathRecalculation;
   private int ticksUntilNextAttack;
   private final int attackInterval = 20;
   private long lastCanUseCheck;
   private static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
   private int failedPathFindingPenalty = 0;
   private boolean canPenalize = false;

   public Royal_DraugrAttackGoal(Royal_Draugr_Entity p_25552_, double p_25553_, boolean p_25554_) {
      this.mob = p_25552_;
      this.speedModifier = p_25553_;
      this.followingTargetEvenIfNotSeen = p_25554_;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      long i = this.mob.f_19853_.m_46467_();
      if (i - this.lastCanUseCheck < 20L) {
         return false;
      } else {
         this.lastCanUseCheck = i;
         LivingEntity livingentity = this.mob.m_5448_();
         if (livingentity == null) {
            return false;
         } else if (!livingentity.m_6084_()) {
            return false;
         } else if (this.canPenalize) {
            if (--this.ticksUntilNextPathRecalculation <= 0) {
               this.path = this.mob.m_21573_().m_6570_(livingentity, 0);
               this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(7);
               return this.path != null;
            } else {
               return true;
            }
         } else {
            this.path = this.mob.m_21573_().m_6570_(livingentity, 0);
            return this.path != null
               ? true
               : this.getAttackReachSqr(livingentity) >= this.mob.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
         }
      }
   }

   public boolean m_8045_() {
      LivingEntity livingentity = this.mob.m_5448_();
      if (livingentity == null) {
         return false;
      } else if (!livingentity.m_6084_()) {
         return false;
      } else if (!this.followingTargetEvenIfNotSeen) {
         return !this.mob.m_21573_().m_26571_();
      } else {
         return !this.mob.m_21444_(livingentity.m_20183_())
            ? false
            : !(livingentity instanceof Player) || !livingentity.m_5833_() && !((Player)livingentity).m_7500_();
      }
   }

   public void m_8056_() {
      this.mob.m_21573_().m_26536_(this.path, this.speedModifier);
      this.mob.m_21561_(true);
      this.ticksUntilNextPathRecalculation = 0;
      this.ticksUntilNextAttack = 0;
   }

   public void m_8041_() {
      LivingEntity livingentity = this.mob.m_5448_();
      if (!EntitySelector.f_20406_.test(livingentity)) {
         this.mob.m_6710_((LivingEntity)null);
      }

      this.mob.m_21561_(false);
      this.mob.m_21573_().m_26573_();
      if (this.mob.isDraugrBlocking()) {
         this.mob.m_5810_();
         this.mob.setShieldCooldownTime(60);
      }
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      LivingEntity livingentity = this.mob.m_5448_();
      if (livingentity != null) {
         this.mob.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
         double d0 = this.mob.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
         this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
         if (!this.isShieldDisabled(this.mob) && this.mob.m_21206_().canPerformAction(ToolActions.SHIELD_BLOCK) && this.mob.m_217043_().m_188503_(6) == 0) {
            this.mob.m_6672_(InteractionHand.OFF_HAND);
         }

         if ((this.followingTargetEvenIfNotSeen || this.mob.m_21574_().m_148306_(livingentity))
            && this.ticksUntilNextPathRecalculation <= 0
            && (
               this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
                  || livingentity.m_20275_(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
                  || this.mob.m_217043_().m_188501_() < 0.05F
            )) {
            this.pathedTargetX = livingentity.m_20185_();
            this.pathedTargetY = livingentity.m_20186_();
            this.pathedTargetZ = livingentity.m_20189_();
            this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(7);
            if (this.canPenalize) {
               this.ticksUntilNextPathRecalculation = this.ticksUntilNextPathRecalculation + this.failedPathFindingPenalty;
               if (this.mob.m_21573_().m_26570_() != null) {
                  Node finalPathPoint = this.mob.m_21573_().m_26570_().m_77395_();
                  if (finalPathPoint != null
                     && livingentity.m_20275_((double)finalPathPoint.f_77271_, (double)finalPathPoint.f_77272_, (double)finalPathPoint.f_77273_) < 1.0) {
                     this.failedPathFindingPenalty = 0;
                  } else {
                     this.failedPathFindingPenalty += 10;
                  }
               } else {
                  this.failedPathFindingPenalty += 10;
               }
            }

            if (d0 > 1024.0) {
               this.ticksUntilNextPathRecalculation += 10;
            } else if (d0 > 256.0) {
               this.ticksUntilNextPathRecalculation += 5;
            }

            if (!this.mob.m_21573_().m_5624_(livingentity, this.speedModifier)) {
               this.ticksUntilNextPathRecalculation += 15;
            }

            this.ticksUntilNextPathRecalculation = this.m_183277_(this.ticksUntilNextPathRecalculation);
         }

         this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
         this.checkAndPerformAttack(livingentity, d0);
      }
   }

   public boolean isShieldDisabled(Royal_Draugr_Entity shieldUser) {
      return shieldUser.isShieldDisabled();
   }

   protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
      double d0 = this.getAttackReachSqr(p_25557_);
      if (p_25558_ <= d0 && this.ticksUntilNextAttack <= 0) {
         this.resetAttackCooldown();
         if (this.mob.isDraugrBlocking()) {
            this.mob.m_5810_();
            this.mob.setShieldCooldownTime(30);
         }

         this.mob.m_6674_(InteractionHand.MAIN_HAND);
         this.mob.m_7327_(p_25557_);
      }
   }

   protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = this.m_183277_(20);
   }

   protected boolean isTimeToAttack() {
      return this.ticksUntilNextAttack <= 0;
   }

   protected int getTicksUntilNextAttack() {
      return this.ticksUntilNextAttack;
   }

   protected int getAttackInterval() {
      return this.m_183277_(20);
   }

   protected double getAttackReachSqr(LivingEntity p_25556_) {
      float f = p_25556_.m_20205_();
      return (double)(f * 2.25F * f * 2.25F + p_25556_.m_20205_());
   }
}
