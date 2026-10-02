package com.bobmowzie.mowziesmobs.server.ai;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class UseAbilityAI<T extends MowzieGeckoEntity> extends Goal {
   protected final T entity;
   protected AbilityType abilityType;

   public UseAbilityAI(T entity, AbilityType ability) {
      this(entity, ability, true);
   }

   public UseAbilityAI(T entity, AbilityType ability, boolean interruptsAI) {
      this.entity = entity;
      this.abilityType = ability;
      if (interruptsAI) {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }
   }

   public boolean m_8036_() {
      return this.entity.getActiveAbility() == null ? false : this.entity.getActiveAbility().getAbilityType() == this.abilityType;
   }

   public void m_8056_() {
      super.m_8056_();
   }

   public void m_8041_() {
      super.m_8041_();
      Ability ability = this.entity.getActiveAbility();
      if (ability != null && ability.getAbilityType() == this.abilityType) {
         AbilityHandler.INSTANCE.sendInterruptAbilityMessage(this.entity, ability.getAbilityType());
      }
   }

   public boolean m_183429_() {
      return true;
   }
}
