package com.hollingsworth.arsnouveau.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

public class SparkleParticleType extends ParticleType<ColoredDynamicTypeData> {
   public SparkleParticleType() {
      super(false, ColoredDynamicTypeData.DESERIALIZER);
   }

   public Codec<ColoredDynamicTypeData> m_7652_() {
      return ColoredDynamicTypeData.CODEC;
   }
}
