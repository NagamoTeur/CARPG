package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;

public class AnimationBlockAI<T extends MowzieEntity & IAnimatedEntity> extends SimpleAnimationAI<T> {
   public AnimationBlockAI(T entity, Animation animation) {
      super(entity, animation);
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.entity != null && this.entity.blockingEntity != null) {
         this.entity.m_21391_(this.entity.blockingEntity, 100.0F, 100.0F);
         this.entity.m_21563_().m_24960_(this.entity.blockingEntity, 200.0F, 30.0F);
      }
   }
}
