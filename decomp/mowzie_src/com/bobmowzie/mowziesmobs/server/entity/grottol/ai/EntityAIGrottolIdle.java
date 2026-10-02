package com.bobmowzie.mowziesmobs.server.entity.grottol.ai;

import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.sounds.SoundEvents;

public class EntityAIGrottolIdle extends SimpleAnimationAI<EntityGrottol> {
   private static final Animation ANIMATION = Animation.create(47);

   public EntityAIGrottolIdle(EntityGrottol entity) {
      super(entity, ANIMATION, false);
   }

   @Override
   public boolean m_8036_() {
      return this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION && this.entity.m_217043_().m_188503_(180) == 0;
   }

   @Override
   public void m_8056_() {
      AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, ANIMATION);
      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.entity.getAnimationTick() == 28 || this.entity.getAnimationTick() == 33) {
         this.entity.m_5496_(SoundEvents.f_12450_, 0.5F, 1.4F);
      }
   }

   public static Animation animation() {
      return ANIMATION;
   }
}
