package net.sweenus.simplyswords.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.sweenus.simplyswords.config.SimplySwordsConfig;
import net.sweenus.simplyswords.registry.SoundRegistry;

public class OmenEffect extends MobEffect {
   public OmenEffect(MobEffectCategory statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      if (!pLivingEntity.f_19853_.m_5776_()) {
         ServerLevel world = (ServerLevel)pLivingEntity.f_19853_;
         BlockPos position = pLivingEntity.m_20183_();
         double x = pLivingEntity.m_20185_();
         double y = pLivingEntity.m_20186_();
         double z = pLivingEntity.m_20189_();
         LivingEntity pPlayer = pLivingEntity.m_21188_();
         float absAmount = SimplySwordsConfig.getFloatValue("omen_absorption_amount");
         float pthreshold = SimplySwordsConfig.getFloatValue("omen_instantkill_threshold") * pLivingEntity.m_21233_();
         new AABB(x + 20.0, y + 10.0, z + 20.0, x - 20.0, y - 10.0, z - 20.0);
         if (pLivingEntity.m_21223_() <= pthreshold && pPlayer != null) {
            if (!pPlayer.m_21023_(MobEffects.f_19605_)) {
               pPlayer.m_147207_(new MobEffectInstance(MobEffects.f_19605_, 40, (int)absAmount), pPlayer);
               world.m_5594_(null, position, (SoundEvent)SoundRegistry.ELEMENTAL_BOW_SCIFI_SHOOT_IMPACT_03.get(), SoundSource.PLAYERS, 0.7F, 1.2F);
            }

            pLivingEntity.m_6469_(DamageSource.f_19318_, 1000.0F);
         }
      }

      super.m_6742_(pLivingEntity, pAmplifier);
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
