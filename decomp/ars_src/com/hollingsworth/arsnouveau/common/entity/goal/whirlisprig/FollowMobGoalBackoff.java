package com.hollingsworth.arsnouveau.common.entity.goal.whirlisprig;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;

public class FollowMobGoalBackoff extends FollowMobGoal {
   float chance;
   Mob f_25261_;

   public FollowMobGoalBackoff(Mob mob, double speed, float stopDistance, float areaSize, float chance) {
      super(mob, speed, stopDistance, areaSize);
      this.chance = chance;
      this.f_25261_ = mob;
   }

   public boolean m_8036_() {
      return this.f_25261_.f_19853_.f_46441_.m_188501_() <= this.chance && super.m_8036_();
   }
}
