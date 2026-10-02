package net.cisco.potion;

import net.cisco.procedures.CiscosMightEffectExpiresProcedure;
import net.cisco.procedures.CiscosMightEffectStartedappliedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class CiscosMightMobEffect extends MobEffect {
   public CiscosMightMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -1);
   }

   public String m_19481_() {
      return "effect.cisco_mod.ciscos_might";
   }

   public void m_6385_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      CiscosMightEffectStartedappliedProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      CiscosMightEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
