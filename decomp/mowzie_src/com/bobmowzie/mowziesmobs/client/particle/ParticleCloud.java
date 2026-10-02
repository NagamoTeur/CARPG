package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ParticleCloud extends TextureSheetParticle {
   private final float red;
   private final float green;
   private final float blue;
   private final float scale;
   private final ParticleCloud.EnumCloudBehavior behavior;
   private final float airDrag;

   public ParticleCloud(
      ClientLevel world,
      double x,
      double y,
      double z,
      double vx,
      double vy,
      double vz,
      double r,
      double g,
      double b,
      double scale,
      int duration,
      ParticleCloud.EnumCloudBehavior behavior,
      double airDrag
   ) {
      super(world, x, y, z);
      this.scale = (float)scale * 0.5F * 0.1F;
      this.f_107225_ = duration;
      this.f_107215_ = vx * 0.5;
      this.f_107216_ = vy * 0.5;
      this.f_107217_ = vz * 0.5;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.behavior = behavior;
      this.f_107231_ = this.f_107204_ = (float)((double)this.f_107223_.m_188503_(4) * Math.PI / 2.0);
      this.airDrag = (float)airDrag;
   }

   public ParticleRenderType m_7556_() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   public void m_5989_() {
      super.m_5989_();
      this.f_107215_ = this.f_107215_ * (double)this.airDrag;
      this.f_107216_ = this.f_107216_ * (double)this.airDrag;
      this.f_107217_ = this.f_107217_ * (double)this.airDrag;
   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_;
      this.f_107230_ = 0.2F * (float)(1.0 - Math.exp((double)(5.0F * (var - 1.0F))) - Math.pow(2000.0, (double)(-var)));
      if ((double)this.f_107230_ < 0.01) {
         this.f_107230_ = 0.01F;
      }

      if (this.behavior == ParticleCloud.EnumCloudBehavior.SHRINK) {
         this.f_107663_ = this.scale * (1.0F - 0.7F * var + 0.3F);
      } else if (this.behavior == ParticleCloud.EnumCloudBehavior.GROW) {
         this.f_107663_ = this.scale * (0.7F * var + 0.3F);
      } else {
         this.f_107663_ = this.scale;
      }

      super.m_5744_(buffer, renderInfo, partialTicks);
   }

   public static class CloudData implements ParticleOptions {
      public static final Deserializer<ParticleCloud.CloudData> DESERIALIZER = new Deserializer<ParticleCloud.CloudData>() {
         public ParticleCloud.CloudData fromCommand(ParticleType<ParticleCloud.CloudData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float r = (float)reader.readDouble();
            reader.expect(' ');
            float g = (float)reader.readDouble();
            reader.expect(' ');
            float b = (float)reader.readDouble();
            reader.expect(' ');
            float scale = (float)reader.readDouble();
            reader.expect(' ');
            int duration = reader.readInt();
            reader.expect(' ');
            float airDrag = (float)reader.readDouble();
            return new ParticleCloud.CloudData(particleTypeIn, r, g, b, scale, duration, ParticleCloud.EnumCloudBehavior.CONSTANT, airDrag);
         }

         public ParticleCloud.CloudData fromNetwork(ParticleType<ParticleCloud.CloudData> particleTypeIn, FriendlyByteBuf buffer) {
            return new ParticleCloud.CloudData(
               particleTypeIn,
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readInt(),
               ParticleCloud.EnumCloudBehavior.CONSTANT,
               buffer.readFloat()
            );
         }
      };
      private final ParticleType<ParticleCloud.CloudData> type;
      private final float r;
      private final float g;
      private final float b;
      private final float scale;
      private final int duration;
      private final ParticleCloud.EnumCloudBehavior behavior;
      private final float airDrag;

      public CloudData(
         ParticleType<ParticleCloud.CloudData> type,
         float r,
         float g,
         float b,
         float scale,
         int duration,
         ParticleCloud.EnumCloudBehavior behavior,
         float airDrag
      ) {
         this.type = type;
         this.r = r;
         this.g = g;
         this.b = b;
         this.scale = scale;
         this.behavior = behavior;
         this.airDrag = airDrag;
         this.duration = duration;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeFloat(this.r);
         buffer.writeFloat(this.g);
         buffer.writeFloat(this.b);
         buffer.writeFloat(this.scale);
         buffer.writeInt(this.duration);
         buffer.writeFloat(this.airDrag);
      }

      public String m_5942_() {
         return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %.2f %d %.2f",
            Registry.f_122829_.m_7981_(this.m_6012_()),
            this.r,
            this.g,
            this.b,
            this.scale,
            this.duration,
            this.airDrag
         );
      }

      public ParticleType<ParticleCloud.CloudData> m_6012_() {
         return this.type;
      }

      @OnlyIn(Dist.CLIENT)
      public float getR() {
         return this.r;
      }

      @OnlyIn(Dist.CLIENT)
      public float getG() {
         return this.g;
      }

      @OnlyIn(Dist.CLIENT)
      public float getB() {
         return this.b;
      }

      @OnlyIn(Dist.CLIENT)
      public float getScale() {
         return this.scale;
      }

      @OnlyIn(Dist.CLIENT)
      public ParticleCloud.EnumCloudBehavior getBehavior() {
         return this.behavior;
      }

      @OnlyIn(Dist.CLIENT)
      public int getDuration() {
         return this.duration;
      }

      @OnlyIn(Dist.CLIENT)
      public float getAirDrag() {
         return this.airDrag;
      }

      public static Codec<ParticleCloud.CloudData> CODEC(ParticleType<ParticleCloud.CloudData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.FLOAT.fieldOf("r").forGetter(ParticleCloud.CloudData::getR),
                     Codec.FLOAT.fieldOf("g").forGetter(ParticleCloud.CloudData::getG),
                     Codec.FLOAT.fieldOf("b").forGetter(ParticleCloud.CloudData::getB),
                     Codec.FLOAT.fieldOf("scale").forGetter(ParticleCloud.CloudData::getScale),
                     Codec.STRING.fieldOf("behavior").forGetter(cloudData -> cloudData.getBehavior().toString()),
                     Codec.INT.fieldOf("duration").forGetter(ParticleCloud.CloudData::getDuration),
                     Codec.FLOAT.fieldOf("airdrag").forGetter(ParticleCloud.CloudData::getAirDrag)
                  )
                  .apply(
                     codecBuilder,
                     (r, g, b, scale, behavior, duration, airdrag) -> new ParticleCloud.CloudData(
                           particleType, r, g, b, scale, duration, ParticleCloud.EnumCloudBehavior.valueOf(behavior), airdrag
                        )
                  )
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class CloudFactory implements ParticleProvider<ParticleCloud.CloudData> {
      private final SpriteSet spriteSet;

      public CloudFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         ParticleCloud.CloudData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         ParticleCloud particleCloud = new ParticleCloud(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            (double)typeIn.getR(),
            (double)typeIn.getG(),
            (double)typeIn.getB(),
            (double)typeIn.getScale(),
            typeIn.getDuration(),
            typeIn.getBehavior(),
            (double)typeIn.getAirDrag()
         );
         particleCloud.m_108339_(this.spriteSet);
         particleCloud.m_107253_(typeIn.getR(), typeIn.getG(), typeIn.getB());
         return particleCloud;
      }
   }

   public static enum EnumCloudBehavior {
      SHRINK,
      GROW,
      CONSTANT;
   }
}
