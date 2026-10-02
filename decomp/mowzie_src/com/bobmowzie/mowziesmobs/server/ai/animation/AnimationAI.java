package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public abstract class AnimationAI<T extends MowzieEntity & IAnimatedEntity> extends Goal {
   protected final T entity;
   protected final boolean hurtInterruptsAnimation;

   protected AnimationAI(T entity) {
      this(entity, true, false);
   }

   protected AnimationAI(T entity, boolean interruptsAI) {
      this(entity, interruptsAI, false);
   }

   protected AnimationAI(T entity, boolean interruptsAI, boolean hurtInterruptsAnimation) {
      this.entity = entity;
      if (interruptsAI) {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      this.hurtInterruptsAnimation = hurtInterruptsAnimation;
   }

   public boolean m_8036_() {
      return this.test(this.entity.getAnimation());
   }

   public void m_8056_() {
      this.entity.hurtInterruptsAnimation = this.hurtInterruptsAnimation;
   }

   public boolean m_8045_() {
      return this.test(this.entity.getAnimation()) && this.entity.getAnimationTick() < this.entity.getAnimation().getDuration();
   }

   public void m_8041_() {
      if (this.test(this.entity.getAnimation())) {
         AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, IAnimatedEntity.NO_ANIMATION);
      }
   }

   public boolean m_183429_() {
      return true;
   }

   protected abstract boolean test(Animation var1);
}
