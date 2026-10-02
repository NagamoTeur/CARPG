package net.sweenus.simplyswords.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FreezeEffect extends MobEffect {
   public FreezeEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_()) {
         double x = pLivingEntity.m_20185_();
         double y = pLivingEntity.m_20186_();
         double z = pLivingEntity.m_20189_();
         pLivingEntity.m_20324_(x, y, z);
         pLivingEntity.m_20334_(0.0, 0.0, 0.0);
      }

      super.m_6742_(pLivingEntity, pAmplifier);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
