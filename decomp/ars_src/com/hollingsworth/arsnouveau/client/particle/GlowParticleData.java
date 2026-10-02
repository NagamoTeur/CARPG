package com.hollingsworth.arsnouveau.client.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

@Deprecated
public class GlowParticleData {
   public static ParticleOptions createData(ParticleColor color) {
      return new ColorParticleTypeData((ParticleType<ColorParticleTypeData>)ModParticles.GLOW_TYPE.get(), color, false);
   }

   public static ParticleOptions createData(ParticleColor color, boolean disableDepthTest) {
      return new ColorParticleTypeData((ParticleType<ColorParticleTypeData>)ModParticles.GLOW_TYPE.get(), color, disableDepthTest, 0.25F, 0.75F, 36);
   }

   public static ParticleOptions createData(ParticleColor color, boolean disableDepthTest, float size, float alpha, int age) {
      return new ColorParticleTypeData(color, disableDepthTest, size, alpha, age);
   }

   public static ParticleOptions createData(ParticleColor color, float size, float alpha, int age) {
      return new ColorParticleTypeData(color, false, size, alpha, age);
   }
}
