package com.hollingsworth.arsnouveau.common.entity.goal;

import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketAnimEntity;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

public class AnimatedAttackGoal extends Goal {
   protected final Mob mob;
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
   private int failedPathFindingPenalty = 0;
   private boolean canPenalize = false;
   public int timeAnimating = 0;
   public boolean arrived = false;
   public boolean done = false;
   public Supplier<Boolean> canAttack;
   int animationID;
   int animationLength;
   int attackRange;

   public AnimatedAttackGoal(
      Mob entity, boolean followUnseen, Supplier<Boolean> canAttack, int animationID, int animationLength, int attackRange, double speedModifier
   ) {
      this.mob = entity;
      this.speedModifier = speedModifier;
      this.followingTargetEvenIfNotSeen = followUnseen;
      this.canAttack = canAttack;
      this.animationID = animationID;
      this.animationLength = animationLength;
      this.attackRange = attackRange;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_6767_() {
      return true;
   }

   public boolean m_8036_() {
      long i = this.mob.f_19853_.m_46467_();
      if (!this.canAttack.get()) {
         return false;
      } else if (i - this.lastCanUseCheck < 20L) {
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
      if (!this.canAttack.get()) {
         return false;
      } else if (livingentity == null || this.done) {
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
      this.timeAnimating = 0;
      this.arrived = false;
      this.done = false;
   }

   public void m_8041_() {
   }

   public void arrivedTick() {
      this.timeAnimating++;
      if (this.timeAnimating >= this.animationLength) {
         if (this.mob.m_5448_() != null) {
            this.attack(this.mob.m_5448_());
         }

         this.done = true;
      }
   }

   public void look(LivingEntity entity) {
      if (entity != null) {
         this.mob.m_21563_().m_24960_(entity, 30.0F, 30.0F);
      }
   }

   public void onArrive() {
      this.arrived = true;
      Networking.sendToNearby(this.mob.f_19853_, this.mob, new PacketAnimEntity(this.mob.m_19879_(), this.animationID));
   }

   public void m_8037_() {
      LivingEntity livingentity = this.mob.m_5448_();
      this.look(livingentity);
      if (this.arrived) {
         this.arrivedTick();
      } else {
         double d0 = this.mob.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
         this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
         if (BlockUtil.distanceFrom(this.mob.f_19825_, livingentity.f_19825_) <= (double)this.attackRange) {
            this.onArrive();
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
         }

         this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
      }
   }

   protected void attack(LivingEntity target) {
      if (BlockUtil.distanceFrom(target.f_19825_, this.mob.f_19825_) <= (double)this.attackRange) {
         this.ticksUntilNextAttack = 20;
         this.mob.m_7327_(target);
      }
   }

   protected double getAttackReachSqr(LivingEntity p_179512_1_) {
      return (double)(this.mob.m_20205_() * 2.0F * this.mob.m_20205_() * 2.0F + p_179512_1_.m_20205_());
   }
}
