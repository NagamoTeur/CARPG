package com.hollingsworth.arsnouveau.common.potions;

import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class BlastEffect extends MobEffect {
   public BlastEffect() {
      super(MobEffectCategory.HARMFUL, new ParticleColor(250, 0, 0).getColor());
   }

   public void m_6742_(LivingEntity pLivingEntity, int pAmplifier) {
      pLivingEntity.f_19853_
         .m_46518_(
            null, pLivingEntity.m_20185_(), pLivingEntity.m_20186_() + 1.0, pLivingEntity.m_20189_(), 2.0F + (float)pAmplifier, false, BlockInteraction.NONE
         );
   }

   public boolean m_6584_(int pDuration, int pAmplifier) {
      return pDuration == 1;
   }
}
