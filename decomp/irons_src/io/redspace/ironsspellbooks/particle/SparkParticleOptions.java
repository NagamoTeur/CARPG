package io.redspace.ironsspellbooks.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import java.util.Locale;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class SparkParticleOptions implements ParticleOptions {
   protected final Vector3f color;
   public static final Codec<SparkParticleOptions> CODEC = RecordCodecBuilder.create(
      p_175793_ -> p_175793_.group(Vector3f.f_176762_.fieldOf("color").forGetter(p_175797_ -> p_175797_.color)).apply(p_175793_, SparkParticleOptions::new)
   );
   public static final Deserializer<SparkParticleOptions> DESERIALIZER = new Deserializer<SparkParticleOptions>() {
      @NotNull
      public SparkParticleOptions fromCommand(@NotNull ParticleType<SparkParticleOptions> p_123689_, @NotNull StringReader p_123690_) throws CommandSyntaxException {
         Vector3f vector3f = SparkParticleOptions.readVector3f(p_123690_);
         return new SparkParticleOptions(vector3f);
      }

      @NotNull
      public SparkParticleOptions fromNetwork(@NotNull ParticleType<SparkParticleOptions> p_123692_, @NotNull FriendlyByteBuf p_123693_) {
         return new SparkParticleOptions(SparkParticleOptions.readVector3f(p_123693_));
      }
   };

   public SparkParticleOptions(Vector3f pColor) {
      this.color = pColor;
   }

   public static Vector3f readVector3f(StringReader pStringInput) throws CommandSyntaxException {
      pStringInput.expect(' ');
      float f = pStringInput.readFloat();
      pStringInput.expect(' ');
      float f1 = pStringInput.readFloat();
      pStringInput.expect(' ');
      float f2 = pStringInput.readFloat();
      return new Vector3f(f, f1, f2);
   }

   public static Vector3f readVector3f(FriendlyByteBuf pBuffer) {
      return new Vector3f(pBuffer.readFloat(), pBuffer.readFloat(), pBuffer.readFloat());
   }

   public ParticleType<?> m_6012_() {
      return (ParticleType<?>)ParticleRegistry.SPARK_PARTICLE.get();
   }

   public void m_7711_(FriendlyByteBuf pBuffer) {
      pBuffer.writeFloat(this.color.m_122239_());
      pBuffer.writeFloat(this.color.m_122260_());
      pBuffer.writeFloat(this.color.m_122269_());
   }

   public String m_5942_() {
      return String.format(
         Locale.ROOT, "%s %.2f %.2f %.2f", Registry.f_122829_.m_7981_(this.m_6012_()), this.color.m_122239_(), this.color.m_122260_(), this.color.m_122269_()
      );
   }

   public Vector3f getColor() {
      return this.color;
   }
}
