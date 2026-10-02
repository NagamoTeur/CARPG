package io.redspace.ironsspellbooks.effect;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class ChargeEffect extends MagicMobEffect {
   public static final float ATTACK_DAMAGE_PER_LEVEL = 0.1F;
   public static final float SPEED_PER_LEVEL = 0.2F;
   public static final float SPELL_POWER_PER_LEVEL = 0.05F;

   public ChargeEffect(MobEffectCategory mobEffectCategory, int color) {
      super(mobEffectCategory, color);
   }

   public void m_6386_(LivingEntity livingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6386_(livingEntity, pAttributeMap, pAmplifier);
      MagicData.getPlayerMagicData(livingEntity).getSyncedData().removeEffects(64L);
   }

   public void m_6385_(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
      super.m_6385_(pLivingEntity, pAttributeMap, pAmplifier);
      MagicData.getPlayerMagicData(pLivingEntity).getSyncedData().addEffects(64L);
   }
}
