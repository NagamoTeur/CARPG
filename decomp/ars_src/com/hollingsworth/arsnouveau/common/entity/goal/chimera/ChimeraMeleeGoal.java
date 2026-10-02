package com.hollingsworth.arsnouveau.common.entity.goal.chimera;

import com.hollingsworth.arsnouveau.common.entity.goal.ConditionalMeleeGoal;
import java.util.function.Supplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;

public class ChimeraMeleeGoal extends ConditionalMeleeGoal {
   public ChimeraMeleeGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen, Supplier<Boolean> canUse) {
      super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen, canUse);
   }

   protected double m_6639_(LivingEntity pAttackTarget) {
      return 10.0;
   }
}
