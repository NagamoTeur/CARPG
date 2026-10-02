package net.sweenus.simplyswords.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sweenus.simplyswords.registry.EffectRegistry;

public class EchoEffect extends MobEffect {
   public EchoEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_() && pLivingEntity.f_19797_ % 15 == 0) {
         pLivingEntity.f_19802_ = 0;
         pLivingEntity.m_6469_(DamageSource.f_19319_, (float)(2 + pAmplifier));
         pLivingEntity.m_21195_((MobEffect)EffectRegistry.ECHO.get());
      }

      super.m_6742_(pLivingEntity, pAmplifier);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
