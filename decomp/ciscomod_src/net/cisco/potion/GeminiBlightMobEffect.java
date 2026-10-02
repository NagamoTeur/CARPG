package net.cisco.potion;

import net.cisco.procedures.GeminiBlightOnEffectActiveTickProcedure;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GeminiBlightMobEffect extends MobEffect {
   public GeminiBlightMobEffect() {
      super(MobEffectCategory.HARMFUL, -6750055);
   }

   public String m_19481_() {
      return "effect.cisco_mod.gemini_blight";
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      GeminiBlightOnEffectActiveTickProcedure.execute(entity);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
