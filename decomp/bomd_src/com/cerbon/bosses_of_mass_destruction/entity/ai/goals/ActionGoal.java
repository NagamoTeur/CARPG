package com.cerbon.bosses_of_mass_destruction.entity.ai.goals;

import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IAction;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionStop;
import java.util.function.Supplier;
import net.minecraft.world.entity.ai.goal.Goal;

public class ActionGoal extends Goal {
   private final Supplier<Boolean> hasTarget;
   private final Supplier<Boolean> canContinue;
   private IAction tickAction = () -> {
   };
   private IAction startAction = () -> {
   };
   private IActionStop endAction = () -> {
   };

   public ActionGoal(Supplier<Boolean> hasTarget, Supplier<Boolean> canContinue, IAction tickAction, IAction startAction, IActionStop endAction) {
      this.hasTarget = hasTarget;
      this.canContinue = canContinue != null ? canContinue : hasTarget;
      this.tickAction = tickAction != null ? tickAction : this.tickAction;
      this.startAction = startAction != null ? startAction : this.startAction;
      this.endAction = endAction != null ? endAction : this.endAction;
   }

   public boolean m_8036_() {
      return this.hasTarget.get();
   }

   public boolean m_183429_() {
      return true;
   }

   public boolean m_8045_() {
      return this.canContinue.get();
   }

   public void m_8056_() {
      this.startAction.perform();
   }

   public void m_8037_() {
      this.tickAction.perform();
   }

   public void m_8041_() {
      this.endAction.stop();
   }
}
