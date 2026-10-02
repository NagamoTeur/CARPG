package com.github.L_Ender.cataclysm.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectBone_Fracture extends MobEffect {
   public EffectBone_Fracture() {
      super(MobEffectCategory.HARMFUL, 11381677);
      this.m_19472_(Attributes.f_22279_, "AEB87FD5-A4E9-4BA8-A52C-38BBE582D491", -0.02, Operation.ADDITION);
      this.m_19472_(Attributes.f_22283_, "804053B1-7703-4E67-AB0E-FC53D5A2CE10", -0.15, Operation.ADDITION);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration > 0;
   }
}
