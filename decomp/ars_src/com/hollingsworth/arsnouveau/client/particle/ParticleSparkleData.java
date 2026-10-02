package com.hollingsworth.arsnouveau.client.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class ParticleSparkleData {
   public static ParticleOptions createData(ParticleColor color) {
      return new ColoredDynamicTypeData((ParticleType<ColoredDynamicTypeData>)ModParticles.SPARKLE_TYPE.get(), color, 0.25F, 36);
   }

   public static ParticleOptions createData(ParticleColor color, float scale, int age) {
      return new ColoredDynamicTypeData((ParticleType<ColoredDynamicTypeData>)ModParticles.SPARKLE_TYPE.get(), color, scale, age);
   }
}
