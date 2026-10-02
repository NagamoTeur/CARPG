package com.github.L_Ender.cataclysm.effects;

import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectAbyssal_Curse extends MobEffect {
   public EffectAbyssal_Curse() {
      super(MobEffectCategory.HARMFUL, 9060095);
   }

   public void m_6742_(LivingEntity LivingEntityIn, int amplifier) {
      LivingEntityIn.m_6469_(CMDamageTypes.ABYSSAL_BURN, 1.0F);
   }

   public boolean m_6584_(int duration, int amplifier) {
      int k = 50 >> amplifier;
      return k > 0 ? duration % k == 0 : true;
   }
}
