package com.bobmowzie.mowziesmobs.server.potion;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectSunblock extends MowzieEffect {
   public EffectSunblock() {
      super(MobEffectCategory.BENEFICIAL, 16768834);
   }

   public void m_6742_(LivingEntity entityLivingBaseIn, int amplifier) {
      super.m_6742_(entityLivingBaseIn, amplifier);
      int k = 50 >> amplifier;
      if (k > 0 && entityLivingBaseIn.f_19797_ % k == 0 && entityLivingBaseIn.m_21223_() < entityLivingBaseIn.m_21233_()) {
         entityLivingBaseIn.m_5634_(1.0F);
      }
   }
}
