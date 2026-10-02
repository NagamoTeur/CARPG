package com.bobmowzie.mowziesmobs.server.ai.animation;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class AnimationProjectileAttackAI<T extends MowzieEntity & IAnimatedEntity & RangedAttackMob> extends SimpleAnimationAI<T> {
   private final int attackFrame;
   private final SoundEvent attackSound;

   public AnimationProjectileAttackAI(T entity, Animation animation, int attackFrame, SoundEvent attackSound) {
      this(entity, animation, attackFrame, attackSound, false);
   }

   public AnimationProjectileAttackAI(T entity, Animation animation, int attackFrame, SoundEvent attackSound, boolean hurtInterrupts) {
      super(entity, animation, true, hurtInterrupts);
      this.attackFrame = attackFrame;
      this.attackSound = attackSound;
   }

   public void m_8037_() {
      super.m_8037_();
      LivingEntity entityTarget = this.entity.m_5448_();
      if (entityTarget != null) {
         this.entity.m_21391_(entityTarget, 100.0F, 100.0F);
         this.entity.m_21563_().m_24960_(entityTarget, 30.0F, 30.0F);
         if (this.entity.getAnimationTick() == this.attackFrame) {
            this.entity.m_6504_(entityTarget, 0.0F);
            this.entity.m_5496_(this.attackSound, 1.0F, 1.0F);
         }
      }
   }
}
