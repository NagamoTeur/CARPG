package io.redspace.ironsspellbooks.effect.guiding_bolt;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class GuidingBoltEffect extends MagicMobEffect {
   public GuidingBoltEffect(MobEffectCategory pCategory, int pColor) {
      super(pCategory, pColor);
   }

   public boolean m_6584_(int duration, int pAmplifier) {
      return duration % 2 == 0;
   }

   public void m_6742_(LivingEntity livingEntity, int pAmplifier) {
   }

   public void m_6385_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(pLivingEntity, pAttributeMap, pAmplifier);
      GuidingBoltManager.INSTANCE.startTracking(pLivingEntity);
   }

   public void m_6386_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(pLivingEntity, pAttributeMap, pAmplifier);
      GuidingBoltManager.INSTANCE.stopTracking(pLivingEntity);
   }
}
