package io.redspace.ironsspellbooks.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class FogParticleOptions extends DustParticleOptionsBase {
   public static final Codec<FogParticleOptions> CODEC = RecordCodecBuilder.create(
      p_175793_ -> p_175793_.group(
               Vector3f.f_176762_.fieldOf("color").forGetter(p_175797_ -> p_175797_.f_175800_),
               Codec.FLOAT.fieldOf("scale").forGetter(p_175795_ -> p_175795_.f_175801_)
            )
            .apply(p_175793_, FogParticleOptions::new)
   );
   public static final Deserializer<FogParticleOptions> DESERIALIZER = new Deserializer<FogParticleOptions>() {
      @NotNull
      public FogParticleOptions fromCommand(@NotNull ParticleType<FogParticleOptions> p_123689_, @NotNull StringReader p_123690_) throws CommandSyntaxException {
         Vector3f vector3f = DustParticleOptionsBase.m_175806_(p_123690_);
         p_123690_.expect(' ');
         float f = p_123690_.readFloat();
         return new FogParticleOptions(vector3f, f);
      }

      @NotNull
      public FogParticleOptions fromNetwork(@NotNull ParticleType<FogParticleOptions> p_123692_, @NotNull FriendlyByteBuf p_123693_) {
         return new FogParticleOptions(DustParticleOptionsBase.m_175810_(p_123693_), p_123693_.readFloat());
      }
   };

   public FogParticleOptions(Vector3f color, float scale) {
      super(color, scale);
   }

   @NotNull
   public ParticleType<FogParticleOptions> m_6012_() {
      return (ParticleType<FogParticleOptions>)ParticleRegistry.FOG_PARTICLE.get();
   }
}
