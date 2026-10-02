package com.hollingsworth.arsnouveau.common.entity.goal;

import java.util.function.Supplier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class ConditionalWaterAvoidingGoal extends WaterAvoidingRandomStrollGoal {
   Supplier<Boolean> canUse;

   public ConditionalWaterAvoidingGoal(PathfinderMob pMob, double pSpeedModifier, Supplier<Boolean> canUse) {
      super(pMob, pSpeedModifier);
      this.canUse = canUse;
   }

   public ConditionalWaterAvoidingGoal(PathfinderMob pMob, double pSpeedModifier, float pProbability, Supplier<Boolean> canUse) {
      super(pMob, pSpeedModifier, pProbability);
      this.canUse = canUse;
   }

   public boolean m_8036_() {
      return this.canUse.get() && super.m_8036_();
   }

   public boolean m_8045_() {
      return this.canUse.get() && super.m_8045_();
   }
}
