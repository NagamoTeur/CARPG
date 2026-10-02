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

public class ColoredDynamicTypeData implements ParticleOptions {
   private ParticleType<ColoredDynamicTypeData> type;
   public ParticleColor color;
   float scale;
   int age;
   public static final Codec<ColoredDynamicTypeData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
               Codec.FLOAT.fieldOf("r").forGetter(d -> d.color.getRed()),
               Codec.FLOAT.fieldOf("g").forGetter(d -> d.color.getGreen()),
               Codec.FLOAT.fieldOf("b").forGetter(d -> d.color.getBlue()),
               Codec.FLOAT.fieldOf("scale").forGetter(d -> d.scale),
               Codec.INT.fieldOf("age").forGetter(d -> d.age)
            )
            .apply(instance, ColoredDynamicTypeData::new)
   );
   static final Deserializer<ColoredDynamicTypeData> DESERIALIZER = new Deserializer<ColoredDynamicTypeData>() {
      public ColoredDynamicTypeData fromCommand(ParticleType<ColoredDynamicTypeData> type, StringReader reader) throws CommandSyntaxException {
         reader.expect(' ');
         return new ColoredDynamicTypeData(type, ParticleColor.fromString(reader.readString()), reader.readFloat(), reader.readInt());
      }

      public ColoredDynamicTypeData fromNetwork(ParticleType<ColoredDynamicTypeData> type, FriendlyByteBuf buffer) {
         return new ColoredDynamicTypeData(type, ParticleColorRegistry.from(buffer.m_130260_()), buffer.readFloat(), buffer.readInt());
      }
   };

   public ParticleType<?> m_6012_() {
      return this.type;
   }

   public ColoredDynamicTypeData(float r, float g, float b, float scale, int age) {
      this.type = (ParticleType<ColoredDynamicTypeData>)ModParticles.LINE_TYPE.get();
      this.color = new ParticleColor(r, g, b);
      this.scale = scale;
      this.age = age;
   }

   public ColoredDynamicTypeData(ParticleType<ColoredDynamicTypeData> particleTypeData, ParticleColor color, float scale, int age) {
      this.type = particleTypeData;
      this.color = color;
      this.scale = scale;
      this.age = age;
   }

   public void m_7711_(FriendlyByteBuf buffer) {
      buffer.m_130079_(this.color.serialize());
      buffer.writeFloat(this.scale);
      buffer.writeInt(this.age);
   }

   public String m_5942_() {
      return RegistryHelper.getRegistryName(this.type).toString() + " " + this.color.serialize() + " " + this.scale + " " + this.age;
   }
}
