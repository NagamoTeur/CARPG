package com.github.L_Ender.cataclysm.effects;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EffectBlessing_Of_Amethyst extends MobEffect {
   public EffectBlessing_Of_Amethyst() {
      super(MobEffectCategory.BENEFICIAL, 16698342);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
      if (LivingEntityIn.m_21023_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get())) {
         LivingEntityIn.m_21195_((MobEffect)ModEffect.EFFECTABYSSAL_BURN.get());
      }

      if (LivingEntityIn.m_21023_((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get())) {
         LivingEntityIn.m_21195_((MobEffect)ModEffect.EFFECTABYSSAL_FEAR.get());
      }

      if (LivingEntityIn.m_21023_(MobEffects.f_216964_)) {
         LivingEntityIn.m_21195_(MobEffects.f_216964_);
      }
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration > 0;
   }
}
