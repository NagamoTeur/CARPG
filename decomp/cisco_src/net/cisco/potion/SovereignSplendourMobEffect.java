package net.cisco.potion;

import net.cisco.procedures.SovereignSplendourEffectExpiresProcedure;
import net.cisco.procedures.SovereignSplendourEffectStartedappliedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class SovereignSplendourMobEffect extends MobEffect {
   public SovereignSplendourMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -16711885);
   }

   public String m_19481_() {
      return "effect.cisco_mod.sovereign_splendour";
   }

   public void m_6385_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      SovereignSplendourEffectStartedappliedProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      SovereignSplendourEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
