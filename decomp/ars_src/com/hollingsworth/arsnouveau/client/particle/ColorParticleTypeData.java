package com.hollingsworth.arsnouveau.client.particle;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;

public class ColorParticleTypeData implements ParticleOptions {
   private ParticleType<ColorParticleTypeData> type;
   public static final Codec<ColorParticleTypeData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               Codec.FLOAT.fieldOf("r").forGetter(d -> d.color.getRed()),
               Codec.FLOAT.fieldOf("g").forGetter(d -> d.color.getGreen()),
               Codec.FLOAT.fieldOf("b").forGetter(d -> d.color.getBlue()),
               Codec.BOOL.fieldOf("disableDepthTest").forGetter(d -> d.disableDepthTest),
               Codec.FLOAT.fieldOf("size").forGetter(d -> d.size),
               Codec.FLOAT.fieldOf("alpha").forGetter(d -> d.alpha),
               Codec.INT.fieldOf("age").forGetter(d -> d.age)
            )
            .apply(instance, ColorParticleTypeData::new)
   );
   public ParticleColor color;
   public boolean disableDepthTest;
   public float size = 0.25F;
   public float alpha = 1.0F;
   public int age = 36;
   static final Deserializer<ColorParticleTypeData> DESERIALIZER = new Deserializer<ColorParticleTypeData>() {
      public ColorParticleTypeData fromCommand(ParticleType<ColorParticleTypeData> type, StringReader reader) throws CommandSyntaxException {
         reader.expect(' ');
         return new ColorParticleTypeData(type, ParticleColor.fromString(reader.readString()), reader.readBoolean());
      }

      public ColorParticleTypeData fromNetwork(ParticleType<ColorParticleTypeData> type, FriendlyByteBuf buffer) {
         return new ColorParticleTypeData(type, ParticleColorRegistry.from(buffer.m_130260_()), buffer.readBoolean());
      }
   };

   public ColorParticleTypeData(float r, float g, float b, boolean disableDepthTest, float size, float alpha, int age) {
      this((ParticleType<ColorParticleTypeData>)ModParticles.GLOW_TYPE.get(), new ParticleColor(r, g, b), disableDepthTest, size, alpha, age);
   }

   public ColorParticleTypeData(ParticleColor color, boolean disableDepthTest, float size, float alpha, int age) {
      this((ParticleType<ColorParticleTypeData>)ModParticles.GLOW_TYPE.get(), color, disableDepthTest, size, alpha, age);
   }

   public ColorParticleTypeData(ParticleType<ColorParticleTypeData> particleTypeData, ParticleColor color, boolean disableDepthTest) {
      this(particleTypeData, color, disableDepthTest, 0.25F, 1.0F, 36);
   }

   public ColorParticleTypeData(
      ParticleType<ColorParticleTypeData> particleTypeData, ParticleColor color, boolean disableDepthTest, float size, float alpha, int age
   ) {
      this.type = particleTypeData;
      this.color = color;
      this.disableDepthTest = disableDepthTest;
      this.size = size;
      this.alpha = alpha;
      this.age = age;
   }

   public ParticleType<ColorParticleTypeData> m_6012_() {
      return this.type;
   }

   public void m_7711_(FriendlyByteBuf packetBuffer) {
      packetBuffer.m_130079_(this.color.serialize());
   }

   public String m_5942_() {
      return RegistryHelper.getRegistryName(this.type).toString() + " " + this.color.serialize();
   }
}
