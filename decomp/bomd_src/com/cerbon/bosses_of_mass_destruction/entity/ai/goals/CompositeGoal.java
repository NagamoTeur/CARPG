package com.cerbon.bosses_of_mass_destruction.entity.ai.goals;

import java.util.Arrays;
import java.util.List;
import net.minecraft.world.entity.ai.goal.Goal;

public class CompositeGoal extends Goal {
   private final List<Goal> goals;

   public CompositeGoal(Goal... goals) {
      this.goals = Arrays.asList(goals);

      for (Goal goal : goals) {
         this.m_7684_().addAll(goal.m_7684_());
      }
   }

   public boolean m_8036_() {
      return this.goals.stream().allMatch(Goal::m_8036_);
   }

   public boolean m_6767_() {
      return this.goals.stream().allMatch(Goal::m_6767_);
   }

   public boolean m_183429_() {
      return true;
   }

   public void m_8037_() {
      this.goals.forEach(Goal::m_8037_);
   }

   public void m_8041_() {
      this.goals.forEach(Goal::m_8041_);
   }

   public void m_8056_() {
      this.goals.forEach(Goal::m_8056_);
   }
}
