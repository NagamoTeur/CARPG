package net.sweenus.simplyswords.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class WardEffect extends MobEffect {
   public WardEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_() && pLivingEntity instanceof Player && pLivingEntity.f_19797_ % 20 == 0) {
         pLivingEntity.m_147207_(new MobEffectInstance(MobEffects.f_19617_, 20, Math.round(pLivingEntity.m_21223_() / 4.0F)), pLivingEntity);
      }

      super.m_6742_(pLivingEntity, pAmplifier);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
