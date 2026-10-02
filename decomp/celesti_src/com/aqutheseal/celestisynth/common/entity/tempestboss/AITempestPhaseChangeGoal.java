package com.aqutheseal.celestisynth.common.entity.tempestboss;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class AITempestPhaseChangeGoal extends AITempestAbilityGoal {
   public int chargeTime;

   public AITempestPhaseChangeGoal(TempestBoss tempest) {
      super(tempest, TempestBoss.PHASE_TRANSITION_DASH_1);
   }

   @Override
   public void tickAttack(Level level) {
      if (this.chargeTime == 40 && !level.m_5776_()) {
         level.m_7967_(EntityType.f_20465_.m_20615_(level));
      }

      if (this.chargeTime >= 80) {
         this.tempest.cyclePhase();
         this.m_8041_();
      }
   }
}
