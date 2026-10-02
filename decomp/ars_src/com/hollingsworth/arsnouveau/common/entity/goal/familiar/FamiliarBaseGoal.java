package com.hollingsworth.arsnouveau.common.entity.goal.familiar;

import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class FamiliarBaseGoal extends Goal {
   public FamiliarEntity entity;

   public FamiliarBaseGoal(FamiliarEntity entity) {
      this.entity = entity;
   }

   public boolean m_8045_() {
      return super.m_8045_();
   }

   public void m_8037_() {
      super.m_8037_();
   }

   public void m_8041_() {
      super.m_8041_();
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public boolean m_8036_() {
      return false;
   }
}
