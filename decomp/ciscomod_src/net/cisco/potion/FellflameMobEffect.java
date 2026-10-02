package net.cisco.potion;

import net.cisco.procedures.FellflameEffectExpiresProcedure;
import net.cisco.procedures.FellflameEffectStartedappliedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class FellflameMobEffect extends MobEffect {
   public FellflameMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -6737152);
   }

   public String m_19481_() {
      return "effect.cisco_mod.fellflame";
   }

   public void m_6385_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      FellflameEffectStartedappliedProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      FellflameEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
