package net.cisco.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class HellbrandEffectMobEffect extends MobEffect {
   public HellbrandEffectMobEffect() {
      super(MobEffectCategory.HARMFUL, -52429);
   }

   public String m_19481_() {
      return "effect.cisco_mod.hellbrand_effect";
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }
}
