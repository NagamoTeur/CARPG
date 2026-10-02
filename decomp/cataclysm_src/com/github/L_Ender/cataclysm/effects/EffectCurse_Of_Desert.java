package com.github.L_Ender.cataclysm.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectCurse_Of_Desert extends MobEffect {
   public EffectCurse_Of_Desert() {
      super(MobEffectCategory.HARMFUL, 16773835);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration > 0;
   }
}
