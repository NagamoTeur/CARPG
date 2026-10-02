package com.aqutheseal.celestisynth.common.entity.tempestboss;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

public abstract class AITempestAbilityGoal extends Goal {
   public final TempestBoss tempest;
   public final int attackId;
   public int chargeTime;

   public AITempestAbilityGoal(TempestBoss tempest, int attackId) {
      this.tempest = tempest;
      this.attackId = attackId;
   }

   public boolean m_183429_() {
      return true;
   }

   public boolean m_8036_() {
      return this.tempest.getAttackState() == this.attackId;
   }

   public void m_8056_() {
      this.chargeTime = 0;
   }

   public void m_8037_() {
      LivingEntity target = this.tempest.m_5448_();
      if (target != null) {
         Level level = this.tempest.f_19853_;
         this.chargeTime++;
         if (target.m_20280_(this.tempest) < 4096.0) {
            this.tickAttack(level);
         } else if (this.chargeTime > 0) {
            this.chargeTime = 0;
         }
      }
   }

   public abstract void tickAttack(Level var1);

   public void m_8041_() {
      this.tempest.setAttackState(TempestBoss.NONE);
   }
}
