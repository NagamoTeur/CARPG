package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;

public class AnimationDeactivateAI<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
   public AnimationDeactivateAI(T entity, Animation animation) {
      super(entity, animation);
   }

   @Override
   public void m_8041_() {
      super.m_8041_();
      this.entity.active = false;
   }
}
