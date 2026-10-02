package net.xylonity.knightquest.common.api.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class ParticleGenerator {
   public static void specialAttackParticles(
      Entity entity, int particleCount, double particleSpeed, double maxRadius, double ySpeed, ParticleOptions particleType
   ) {
      RandomSource random = entity.f_19853_.m_213780_();

      for (int i = 0; i < particleCount; i++) {
         if (random.m_188503_(2) == 0) {
            double baseAngle = (Math.PI * 2) * (double)i / (double)particleCount;
            double angleVariation = (random.m_188500_() - 0.5) * (Math.PI / 8);
            double angle = baseAngle + angleVariation;
            double radiusVariation = 1.0 + (random.m_188500_() - 0.5) * 0.5;
            double radius = maxRadius * radiusVariation;
            double offsetX = radius * Math.cos(angle);
            double offsetZ = radius * Math.sin(angle);
            double velocityVariation = (random.m_188500_() - 0.5) * 0.1;
            double velocityX = (particleSpeed + velocityVariation) * Math.cos(angle);
            double velocityZ = (particleSpeed + velocityVariation) * Math.sin(angle);
            double heightVariation = (random.m_188500_() - 0.5) * 0.5;
            double particleX = entity.m_20185_() + offsetX;
            double particleY = entity.m_20186_() + 0.2 + heightVariation;
            double particleZ = entity.m_20189_() + offsetZ;
            entity.f_19853_.m_7106_(particleType, particleX, particleY, particleZ, velocityX, ySpeed, velocityZ);
         }
      }
   }
}
