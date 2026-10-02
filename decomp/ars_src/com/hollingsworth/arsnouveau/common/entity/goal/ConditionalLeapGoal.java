package com.hollingsworth.arsnouveau.common.entity.goal;

import java.util.function.Supplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;

public class ConditionalLeapGoal extends LeapAtTargetGoal {
   public Supplier<Boolean> canUse;

   public ConditionalLeapGoal(Mob pMob, float pYd, Supplier<Boolean> canUse) {
      super(pMob, pYd);
      this.canUse = canUse;
   }

   public boolean m_8036_() {
      return this.canUse.get() && super.m_8036_();
   }
}
