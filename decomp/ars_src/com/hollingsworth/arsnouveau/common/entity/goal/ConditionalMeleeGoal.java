package com.hollingsworth.arsnouveau.common.entity.goal;

import java.util.function.Supplier;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ConditionalMeleeGoal extends MeleeAttackGoal {
   public Supplier<Boolean> canUse;

   public ConditionalMeleeGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen, Supplier<Boolean> canUse) {
      super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
      this.canUse = canUse;
   }

   public boolean m_8036_() {
      return this.canUse.get() && super.m_8036_();
   }
}
