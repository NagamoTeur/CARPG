package net.cisco.potion;

import net.cisco.procedures.CiscoRageEffectExpiresProcedure;
import net.cisco.procedures.CiscoRageOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class AdventofAscensionMobEffect extends MobEffect {
   public AdventofAscensionMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -205);
   }

   public String m_19481_() {
      return "effect.cisco_mod.adventof_ascension";
   }

   public void m_6385_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      CiscoRageOnEffectActiveTickProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      CiscoRageEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
