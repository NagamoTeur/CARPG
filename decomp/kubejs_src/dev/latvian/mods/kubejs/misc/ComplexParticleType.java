package dev.latvian.mods.kubejs.misc;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;

public class ComplexParticleType extends ParticleType<ParticleOptions> {
   public ComplexParticleType(boolean bl, Deserializer<ParticleOptions> deserializer) {
      super(bl, deserializer);
   }

   public Codec<ParticleOptions> m_7652_() {
      return null;
   }
}
