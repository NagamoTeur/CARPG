package io.redspace.ironsspellbooks.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EnchantedWardEffect extends MagicMobEffect {
   public EnchantedWardEffect(MobEffectCategory pCategory, int pColor) {
      super(pCategory, pColor);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return pDuration % 30 == 0;
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      pLivingEntity.m_6469_(DamageSource.f_19319_.m_238403_(), 5.0F);
   }
}
