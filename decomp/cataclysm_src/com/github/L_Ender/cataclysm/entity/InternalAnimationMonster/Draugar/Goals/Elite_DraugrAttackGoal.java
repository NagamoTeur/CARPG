package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Goals;

import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Draugar.Elite_Draugr_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.EnumSet;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class Elite_DraugrAttackGoal extends Goal {
   public static final UniformInt PATHFINDING_DELAY_RANGE = TimeUtil.m_145020_(1, 2);
   private final Elite_Draugr_Entity mob;
   private final double speedModifier;
   private final float attackRadiusSqr;
   private int seeTime;
   private int updatePathDelay;
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

   public Elite_DraugrAttackGoal(Elite_Draugr_Entity p_25814_, double p_25815_, float p_25816_, boolean p_25554_) {
      this.mob = p_25814_;
      this.speedModifier = p_25815_;
      this.attackRadiusSqr = p_25816_ * p_25816_;
      this.followingTargetEvenIfNotSeen = p_25554_;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (this.isHoldingCrossbow()) {
         return true;
      } else {
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
   }

   private boolean isHoldingCrossbow() {
      return this.mob.m_21093_(is -> is.m_41720_() instanceof CrossbowItem);
   }

   public boolean m_8045_() {
      LivingEntity livingentity = this.mob.m_5448_();
      if (this.isHoldingCrossbow()) {
         return this.isValidTarget() && (this.m_8036_() || !this.mob.m_21573_().m_26571_());
      } else if (livingentity == null) {
         return false;
      } else if (!livingentity.m_6084_()) {
         return false;
      } else if (this.isHoldingCrossbow()) {
         return false;
      } else if (!this.followingTargetEvenIfNotSeen) {
         return !this.mob.m_21573_().m_26571_();
      } else {
         return !this.mob.m_21444_(livingentity.m_20183_())
            ? false
            : !(livingentity instanceof Player) || !livingentity.m_5833_() && !((Player)livingentity).m_7500_();
      }
   }

   private boolean isValidTarget() {
      return this.mob.m_5448_() != null && this.mob.m_5448_().m_6084_();
   }

   public void m_8041_() {
      super.m_8041_();
      LivingEntity livingentity = this.mob.m_5448_();
      if (!EntitySelector.f_20406_.test(livingentity)) {
         this.mob.m_6710_(null);
      }

      this.mob.m_21561_(false);
      this.seeTime = 0;
      if (this.isHoldingCrossbow() && this.mob.m_6117_()) {
         this.mob.m_5810_();
         this.mob.m_6136_(false);
         CrossbowItem.m_40884_(this.mob.m_21211_(), false);
      }
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      LivingEntity target = this.mob.m_5448_();
      if (target != null) {
         double t0 = this.mob.m_20280_(target);
         if (this.isHoldingCrossbow()) {
            boolean flag = this.mob.m_21574_().m_148306_(target);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
               this.seeTime = 0;
            }

            if (flag) {
               this.seeTime++;
            } else {
               this.seeTime--;
            }

            boolean flag2 = t0 > (double)this.attackRadiusSqr || this.seeTime < 5;
            if (flag2) {
               this.updatePathDelay--;
               if (this.updatePathDelay <= 0) {
                  this.mob.m_21573_().m_5624_(target, this.speedModifier * 0.5);
                  this.updatePathDelay = PATHFINDING_DELAY_RANGE.m_214085_(this.mob.m_217043_());
               }
            } else {
               this.updatePathDelay = 0;
               this.mob.m_21573_().m_26573_();
            }

            if (t0 < 10.0) {
               this.mob.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)ModItems.BLACK_STEEL_SWORD.get()));
            }

            this.mob.m_21563_().m_24960_(target, 30.0F, 30.0F);
         } else {
            this.mob.m_21563_().m_24960_(target, 30.0F, 30.0F);
            double d0 = this.mob.m_20275_(target.m_20185_(), target.m_20186_(), target.m_20189_());
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.mob.m_21574_().m_148306_(target))
               && this.ticksUntilNextPathRecalculation <= 0
               && (
                  this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0
                     || target.m_20275_(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0
                     || this.mob.m_217043_().m_188501_() < 0.05F
               )) {
               this.pathedTargetX = target.m_20185_();
               this.pathedTargetY = target.m_20186_();
               this.pathedTargetZ = target.m_20189_();
               this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(7);
               if (this.canPenalize) {
                  this.ticksUntilNextPathRecalculation = this.ticksUntilNextPathRecalculation + this.failedPathFindingPenalty;
                  if (this.mob.m_21573_().m_26570_() != null) {
                     Node finalPathPoint = this.mob.m_21573_().m_26570_().m_77395_();
                     if (finalPathPoint != null
                        && target.m_20275_((double)finalPathPoint.f_77271_, (double)finalPathPoint.f_77272_, (double)finalPathPoint.f_77273_) < 1.0) {
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

               if (!this.mob.m_21573_().m_5624_(target, this.speedModifier)) {
                  this.ticksUntilNextPathRecalculation += 15;
               }

               this.ticksUntilNextPathRecalculation = this.m_183277_(this.ticksUntilNextPathRecalculation);
            }

            if (t0 > 10.0) {
               this.mob.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42717_));
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(target, d0);
         }
      }
   }

   protected void checkAndPerformAttack(LivingEntity p_25557_, double p_25558_) {
      double d0 = this.getAttackReachSqr(p_25557_);
      if (p_25558_ <= d0 && this.ticksUntilNextAttack <= 0) {
         this.resetAttackCooldown();
         this.mob.m_6674_(InteractionHand.MAIN_HAND);
         this.mob.m_7327_(p_25557_);
      }
   }

   protected void resetAttackCooldown() {
      this.ticksUntilNextAttack = this.m_183277_(20);
   }

   protected double getAttackReachSqr(LivingEntity p_25556_) {
      float f = p_25556_.m_20205_();
      return (double)(f * 2.25F * f * 2.25F + p_25556_.m_20205_());
   }
}
