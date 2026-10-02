package net.cisco.potion;

import net.cisco.procedures.FellFrostEffectExpiresProcedure;
import net.cisco.procedures.FellFrostOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class FellFrostMobEffect extends MobEffect {
   public FellFrostMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -13382401);
   }

   public String m_19481_() {
      return "effect.cisco_mod.fell_frost";
   }

   public void m_6385_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      FellFrostOnEffectActiveTickProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      FellFrostEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
