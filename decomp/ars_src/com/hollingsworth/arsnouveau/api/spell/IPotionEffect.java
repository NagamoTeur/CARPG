package com.hollingsworth.arsnouveau.api.spell;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public interface IPotionEffect {
   default void applyConfigPotion(LivingEntity entity, MobEffect potionEffect, SpellStats spellStats) {
      this.applyConfigPotion(entity, potionEffect, spellStats, true);
   }

   default void applyConfigPotion(LivingEntity entity, MobEffect potionEffect, SpellStats spellStats, boolean showParticles) {
      this.applyPotion(entity, potionEffect, spellStats, this.getBaseDuration(), this.getExtendTimeDuration(), showParticles);
   }

   default void applyPotion(
      LivingEntity entity, MobEffect potionEffect, SpellStats stats, int baseDurationSeconds, int durationBuffSeconds, boolean showParticles
   ) {
      if (entity != null) {
         int ticks = baseDurationSeconds * 20 + durationBuffSeconds * stats.getDurationInTicks();
         int amp = (int)stats.getAmpMultiplier();
         entity.m_7292_(new MobEffectInstance(potionEffect, ticks, amp, false, showParticles, true));
      }
   }

   int getBaseDuration();

   int getExtendTimeDuration();
}
