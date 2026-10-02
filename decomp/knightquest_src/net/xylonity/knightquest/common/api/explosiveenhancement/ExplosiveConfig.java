package net.xylonity.knightquest.common.api.explosiveenhancement;

import net.minecraft.world.level.Level;

public class ExplosiveConfig {
   static void spawnParticles(Level world, double x, double y, double z, float power) {
      spawnParticles(world, x, y, z, power, false, false, 1);
   }

   public static void spawnParticles(Level world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks) {
      spawnParticles(world, x, y, z, power, isUnderWater, didDestroyBlocks, 1);
   }

   public static void spawnParticles(Level world, double x, double y, double z, float power, boolean isUnderWater, boolean didDestroyBlocks, int phase) {
      ExplosiveHandler.spawnParticles(world, x, y, z, power, isUnderWater, didDestroyBlocks, true, phase);
   }
}
