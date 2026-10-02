package net.cisco.potion;

import net.cisco.procedures.AvatarofTheDarkOneEffectExpiresProcedure;
import net.cisco.procedures.AvatarofTheDarkOneEffectStartedappliedProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class AvatarofTheDarkOneMobEffect extends MobEffect {
   public AvatarofTheDarkOneMobEffect() {
      super(MobEffectCategory.BENEFICIAL, -16777216);
   }

   public String m_19481_() {
      return "effect.cisco_mod.avatarof_the_dark_one";
   }

   public void m_6385_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      AvatarofTheDarkOneEffectStartedappliedProcedure.execute(entity);
   }

   public void m_6386_(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
      super.m_6386_(entity, attributeMap, amplifier);
      AvatarofTheDarkOneEffectExpiresProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
