package com.obscuria.aquamirae.common.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;

public class CrystallizationMobEffect extends MobEffect {
   public CrystallizationMobEffect() {
      super(MobEffectCategory.HARMFUL, -6750055);
      this.m_19472_(Attributes.f_22281_, "5D6F0BA2-1286-46AC-B896-C61C5CAE91CA", -0.8, Operation.MULTIPLY_TOTAL);
   }

   @NotNull
   public String m_19481_() {
      return "effect.aquamirae.crystallization";
   }

   public void m_6386_(@NotNull LivingEntity entity, @NotNull AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      entity.getPersistentData().m_128379_("crystallization", true);
      entity.m_6469_(new DamageSource("crystallization").m_19380_().m_19389_(), 9999999.0F);
      if (entity.m_6084_()) {
         entity.getPersistentData().m_128379_("crystallization", false);
      }
   }
}
