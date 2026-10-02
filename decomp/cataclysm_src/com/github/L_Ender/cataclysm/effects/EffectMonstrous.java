package com.github.L_Ender.cataclysm.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class EffectMonstrous extends MobEffect {
   public EffectMonstrous() {
      super(MobEffectCategory.BENEFICIAL, 8803127);
      this.m_19472_(Attributes.f_22278_, "953533ED-0994-4421-9E4E-47557FA8EE2A", 0.5, Operation.ADDITION);
      this.m_19472_(Attributes.f_22284_, "E6C06C84-8021-4296-A512-AFB0C98806CA", 3.0, Operation.ADDITION);
      this.m_19472_(Attributes.f_22285_, "1F329CAC-F59E-41C1-A5E6-18A45A3237B8", 2.0, Operation.ADDITION);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
      if (LivingEntityIn.m_21223_() < LivingEntityIn.m_21233_() * 1.0F / 2.0F) {
         LivingEntityIn.m_5634_(1.0F);
      }
   }

   public boolean m_6584_(int duration, int amplifier) {
      int k = 50 >> amplifier;
      return k > 0 ? duration % k == 0 : true;
   }
}
