package com.hollingsworth.arsnouveau.common.potions;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FreezingEffect extends MobEffect {
   protected FreezingEffect() {
      super(MobEffectCategory.HARMFUL, new ParticleColor(0, 0, 250).getColor());
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      pLivingEntity.m_146917_(Math.min(pLivingEntity.m_146891_() + 3, pLivingEntity.m_146888_() + 3 + pAmplifier));
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return true;
   }
}
