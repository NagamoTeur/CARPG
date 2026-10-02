package com.github.L_Ender.cataclysm.entity.Pet.AI;

import com.github.L_Ender.cataclysm.entity.Pet.AnimationPet;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public abstract class PetAnimationGoal<T extends AnimationPet & IAnimatedEntity> extends Goal {
   protected final T entity;

   protected PetAnimationGoal(T entity) {
      this(entity, true);
   }

   protected PetAnimationGoal(T entity, boolean interruptsAI) {
      this.entity = entity;
      if (interruptsAI) {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }
   }

   public boolean m_8036_() {
      return this.test(this.entity.getAnimation());
   }

   public boolean m_183429_() {
      return true;
   }

   protected abstract boolean test(Animation var1);
}
