package com.hollingsworth.arsnouveau.common.entity.goal.wealdwalker;

import com.hollingsworth.arsnouveau.common.entity.WealdWalker;
import java.util.function.Supplier;

public class CastSpellGoal extends CastGoal<WealdWalker> {
   WealdWalker walker;

   public CastSpellGoal(WealdWalker entity, double speed, int attackInterval, float attackRange, Supplier<Boolean> canUse, int animId, int delayTicks) {
      super(entity, speed, attackInterval, attackRange, canUse, animId, delayTicks);
      this.walker = entity;
   }

   @Override
   public void m_8056_() {
      super.m_8056_();
      this.walker.m_20088_().m_135381_(WealdWalker.CASTING, true);
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
      this.walker.m_20088_().m_135381_(WealdWalker.CASTING, false);
   }
}
