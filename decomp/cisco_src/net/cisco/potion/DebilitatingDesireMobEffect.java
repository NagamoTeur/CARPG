package net.cisco.potion;

import net.cisco.procedures.DebilitatingDesireEffectExpiresProcedure;
import net.cisco.procedures.DebilitatingDesireOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class DebilitatingDesireMobEffect extends MobEffect {
   public DebilitatingDesireMobEffect() {
      super(MobEffectCategory.NEUTRAL, -1);
   }

   public String m_19481_() {
      return "effect.cisco_mod.debilitating_desire";
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      DebilitatingDesireOnEffectActiveTickProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      DebilitatingDesireEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
