package net.sweenus.simplyswords.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.sweenus.simplyswords.registry.EffectRegistry;

public class OnslaughtEffect extends MobEffect {
   public OnslaughtEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_() && pLivingEntity instanceof Player) {
         if (pLivingEntity.f_19797_ % 40 == 0) {
            Player player = (Player)pLivingEntity;
            pLivingEntity.m_147207_(new MobEffectInstance(MobEffects.f_19598_, 10, 15), pLivingEntity);
         }

         if (pLivingEntity.f_19797_ % 65 == 0 && pLivingEntity.m_21023_(MobEffects.f_19598_)) {
            pLivingEntity.m_21195_(MobEffects.f_19598_);
         }

         if (pLivingEntity.m_21023_((MobEffect)EffectRegistry.ONSLAUGHT.get())) {
            MobEffectInstance statusEffect = pLivingEntity.m_21124_((MobEffect)EffectRegistry.ONSLAUGHT.get());

            assert statusEffect != null;

            if (statusEffect.m_19557_() < 10 && pLivingEntity.m_21023_(MobEffects.f_19598_)) {
               pLivingEntity.m_147207_(new MobEffectInstance(MobEffects.f_19613_, 80, 0), pLivingEntity);
               pLivingEntity.m_21195_(MobEffects.f_19598_);
               pLivingEntity.m_21195_((MobEffect)EffectRegistry.ONSLAUGHT.get());
            }
         }
      }

      super.m_6742_(pLivingEntity, pAmplifier);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
