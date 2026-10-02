package com.github.L_Ender.cataclysm.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectStun extends MobEffect {
   public EffectStun() {
      super(MobEffectCategory.HARMFUL, 16747520);
      this.m_19472_(Attributes.f_22279_, "57F1BADC-F545-4D89-B218-751C2FF8053D", -0.5, Operation.ADDITION);
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration > 0;
   }
}
