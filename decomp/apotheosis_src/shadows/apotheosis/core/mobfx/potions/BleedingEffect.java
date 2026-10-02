package shadows.apotheosis.core.mobfx.potions;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedingEffect extends MobEffect {
   public static final DamageSource BLEEDING = new DamageSource("apotheosis.bleeding").m_19380_();

   public BleedingEffect() {
      super(MobEffectCategory.HARMFUL, 9109504);
   }

   public void m_6742_(LivingEntity entityLivingBaseIn, int amplifier) {
      entityLivingBaseIn.m_6469_(BLEEDING, 1.0F + (float)amplifier);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % 40 == 0;
   }
}
