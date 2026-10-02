package com.github.L_Ender.cataclysm.effects;

import com.github.L_Ender.cataclysm.init.ModEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectGhostForm extends MobEffect {
   private int lastDuration = -1;

   public EffectGhostForm() {
      super(MobEffectCategory.BENEFICIAL, 3789490);
      this.m_19472_(Attributes.f_22279_, "FBF4116E-056E-4420-865B-C098705DDAB2", 0.4, Operation.MULTIPLY_TOTAL);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
      if (this.lastDuration == 1) {
         LivingEntityIn.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTGHOST_SICKNESS.get(), 7200, 0, false, false, true));
      }
   }

   public boolean m_6584_(int duration, int amplifier) {
      this.lastDuration = duration;
      return duration > 0;
   }
}
