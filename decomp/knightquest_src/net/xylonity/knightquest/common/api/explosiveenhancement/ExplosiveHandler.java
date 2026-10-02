package net.xylonity.knightquest.common.api.explosiveenhancement;

import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.xylonity.knightquest.registry.KnightQuestParticles;

public class ExplosiveHandler {
   public static void spawnParticles(
      Level world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks, boolean isImportant, int phase
   ) {
      if (isUnderWater) {
         power = ExplosiveValues.dynamicUnderwater ? power : 4.0F;
      } else {
         power = ExplosiveValues.dynamicSize ? power : 4.0F;
      }

      y = ExplosiveValues.attemptBetterSmallExplosions && power == 1.0F ? y + ExplosiveValues.smallExplosionYOffset : y;
      isImportant = isImportant || ExplosiveValues.alwaysShow;
      float blastwavePower = power * 1.75F;
      float fireballPower = power * 1.25F;
      float smokePower = power * 0.4F;
      if (isUnderWater) {
         if (ExplosiveValues.showUnderwaterBlastWave) {
            world.m_6493_((ParticleOptions)KnightQuestParticles.UNDERWATERBLASTWAVE.get(), isImportant, x, y + 0.5, z, (double)blastwavePower, 0.0, 0.0);
         }

         if (ExplosiveValues.showShockwave) {
            world.m_6493_(
               (ParticleOptions)KnightQuestParticles.SHOCKWAVE.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
            );
         } else if (ExplosiveValues.showUnderwaterSparks) {
            world.m_6493_(
               (ParticleOptions)KnightQuestParticles.BLANK_SHOCKWAVE.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
            );
         }

         for (int total = ExplosiveValues.bubbleAmount; total >= 1; total--) {
            world.m_6493_(
               (ParticleOptions)KnightQuestParticles.BUBBLE.get(),
               isImportant,
               x,
               y,
               z,
               (double)nextBetween(1, 7) * 0.3 * (double)nextBetween(-1, 1),
               (double)nextBetween(1, 10) * 0.1,
               (double)nextBetween(1, 7) * 0.3 * (double)nextBetween(-1, 1)
            );
         }
      } else {
         if (ExplosiveValues.showBlastWave) {
            if (phase == 0) {
               world.m_6493_(
                  (ParticleOptions)KnightQuestParticles.REDBLASTWAVE.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
               );
            } else if (phase == 1) {
               world.m_6493_((ParticleOptions)KnightQuestParticles.BLUEBLASTWAVE.get(), isImportant, x, y, z, (double)blastwavePower, 0.0, 0.0);
            } else {
               world.m_6493_((ParticleOptions)KnightQuestParticles.BLASTWAVE.get(), isImportant, x, y, z, (double)blastwavePower, 0.0, 0.0);
            }
         }

         if (ExplosiveValues.showFireball) {
            if (phase == 0) {
               world.m_6493_(
                  (ParticleOptions)KnightQuestParticles.REDFIREBALL.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
               );
            } else if (phase == 1) {
               world.m_6493_(
                  (ParticleOptions)KnightQuestParticles.BLUEFIREBALL.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
               );
            } else {
               world.m_6493_(
                  (ParticleOptions)KnightQuestParticles.FIREBALL.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
               );
            }
         } else if (ExplosiveValues.showSparks) {
            world.m_6493_(
               (ParticleOptions)KnightQuestParticles.BLANK_FIREBALL.get(), isImportant, x, y + 0.5, z, (double)fireballPower, isImportant ? 1.0 : 0.0, 0.0
            );
         }

         if (ExplosiveValues.showMushroomCloud && phase == 2) {
            world.m_6493_((ParticleOptions)KnightQuestParticles.SMOKE.get(), isImportant, x, y, z, (double)power, (double)power * 0.25, 0.0);
            world.m_6493_((ParticleOptions)KnightQuestParticles.SMOKE.get(), isImportant, x, y, z, (double)power, (double)smokePower, 0.0);
            world.m_6493_((ParticleOptions)KnightQuestParticles.SMOKE.get(), isImportant, x, y, z, 0.15, (double)smokePower, (double)power);
            world.m_6493_((ParticleOptions)KnightQuestParticles.SMOKE.get(), isImportant, x, y, z, -0.15, (double)smokePower, (double)power);
            world.m_6493_((ParticleOptions)KnightQuestParticles.SMOKE.get(), isImportant, x, y, z, (double)power, (double)smokePower, 0.15);
            world.m_6493_((ParticleOptions)KnightQuestParticles.SMOKE.get(), isImportant, x, y, z, (double)power, (double)smokePower, -0.15);
         }
      }
   }

   private static int nextBetween(int min, int max) {
      Random random = new Random();
      return random.nextInt(min, max);
   }
}
