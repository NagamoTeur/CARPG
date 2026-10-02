package com.github.L_Ender.cataclysm.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectBlazing_Brand extends MobEffect {
   public EffectBlazing_Brand() {
      super(MobEffectCategory.HARMFUL, 14423100);
      this.m_19472_(Attributes.f_22284_, "68078724-8653-42D5-A245-9D14A1F54685", -0.2, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22285_, "B237E76D-15E8-4513-A735-55BB25C33603", -0.2, Operation.MULTIPLY_TOTAL);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration > 0;
   }
}
