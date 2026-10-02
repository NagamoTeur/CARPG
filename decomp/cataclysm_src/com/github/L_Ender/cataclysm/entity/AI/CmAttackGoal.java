package com.github.L_Ender.cataclysm.entity.AI;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CmAttackGoal extends MeleeAttackGoal {
   private LivingEntity target;
   private int delayCounter;
   protected final double moveSpeed;

   public CmAttackGoal(PathfinderMob creatureEntity, double moveSpeed) {
      super(creatureEntity, moveSpeed, true);
      this.moveSpeed = moveSpeed;
   }

   public boolean m_8036_() {
      this.target = this.f_25540_.m_5448_();
      return this.target != null && this.target.m_6084_();
   }

   public void m_8041_() {
      this.f_25540_.m_21573_().m_26573_();
      if (this.f_25540_.m_5448_() == null) {
         this.f_25540_.m_21561_(false);
         this.f_25540_.m_21573_().m_26573_();
      }
   }

   public void m_8037_() {
      LivingEntity target = this.f_25540_.m_5448_();
      if (target != null) {
         this.f_25540_.m_21563_().m_24960_(target, 30.0F, 30.0F);
         double distSq = this.f_25540_.m_20275_(target.m_20185_(), target.m_20191_().f_82289_, target.m_20189_());
         if (--this.delayCounter <= 0) {
            this.delayCounter = 4 + this.f_25540_.m_217043_().m_188503_(7);
            if (distSq > Math.pow(this.f_25540_.m_21051_(Attributes.f_22277_).m_22135_(), 2.0)) {
               if (!this.f_25540_.m_21691_() && !this.f_25540_.m_21573_().m_5624_(target, 1.0)) {
                  this.delayCounter += 5;
               }
            } else {
               this.f_25540_.m_21573_().m_5624_(target, this.moveSpeed);
            }
         }
      }
   }
}
