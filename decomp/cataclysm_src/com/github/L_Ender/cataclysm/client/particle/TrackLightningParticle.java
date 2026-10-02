package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector4f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class TrackLightningParticle extends Particle {
   private int r;
   private int g;
   private int b;
   private LightningRender lightningRender = new LightningRender();

   public TrackLightningParticle(ClientLevel world, double x, double y, double z, double xd, double yd, double zd, int r, int g, int b) {
      super(world, x, y, z);
      this.m_107250_(6.0F, 6.0F);
      this.f_107212_ = x;
      this.f_107213_ = y;
      this.f_107214_ = z;
      this.r = r;
      this.g = g;
      this.b = b;
      Vec3 lightningTo = new Vec3(xd - x, yd - y, zd - z);
      this.f_107225_ = 5;
      int sections = 1 + (int)(9.0 * lightningTo.m_82553_());
      LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(
         0.5F, 0.1F, 0.5F, 0.85F, new Vector4f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F, 0.8F), 0.1F
      );
      LightningBoltData bolt = new LightningBoltData(boltData, Vec3.f_82478_, lightningTo, sections)
         .size(0.1F + this.f_107223_.m_188501_() * 0.1F)
         .lifespan(this.f_107225_)
         .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);
      this.lightningRender.update(this, bolt, 1.0F);
   }

   public boolean shouldCull() {
      return false;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      this.f_107215_ = 0.0;
      this.f_107216_ = 0.0;
      this.f_107217_ = 0.0;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107216_ = this.f_107216_ - (double)this.f_107226_;
      }
   }

   public void m_5744_(VertexConsumer consumer, Camera camera, float partialTick) {
      BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      Vec3 cameraPos = camera.m_90583_();
      float x = (float)Mth.m_14139_((double)partialTick, this.f_107209_, this.f_107212_);
      float y = (float)Mth.m_14139_((double)partialTick, this.f_107210_, this.f_107213_);
      float z = (float)Mth.m_14139_((double)partialTick, this.f_107211_, this.f_107214_);
      PoseStack posestack = new PoseStack();
      posestack.m_85836_();
      posestack.m_85837_(-cameraPos.f_82479_, -cameraPos.f_82480_, -cameraPos.f_82481_);
      posestack.m_85837_((double)x, (double)y, (double)z);
      this.lightningRender.render(partialTick, posestack, multibuffersource$buffersource);
      multibuffersource$buffersource.m_109911_();
      posestack.m_85849_();
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public static class OrbData implements ParticleOptions {
      public static final Deserializer<TrackLightningParticle.OrbData> DESERIALIZER = new Deserializer<TrackLightningParticle.OrbData>() {
         public TrackLightningParticle.OrbData fromCommand(ParticleType<TrackLightningParticle.OrbData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int r = reader.readInt();
            reader.expect(' ');
            int g = reader.readInt();
            reader.expect(' ');
            int b = reader.readInt();
            return new TrackLightningParticle.OrbData(r, g, b);
         }

         public TrackLightningParticle.OrbData fromNetwork(ParticleType<TrackLightningParticle.OrbData> particleTypeIn, FriendlyByteBuf buffer) {
            return new TrackLightningParticle.OrbData(buffer.readInt(), buffer.readInt(), buffer.readInt());
         }
      };
      private final int r;
      private final int g;
      private final int b;

      public OrbData(int r, int g, int b) {
         this.r = r;
         this.g = g;
         this.b = b;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeInt(this.r);
         buffer.writeInt(this.g);
         buffer.writeInt(this.b);
      }

      public String m_5942_() {
         return String.format(Locale.ROOT, "%s %d %d %d", ForgeRegistries.PARTICLE_TYPES.getKey(this.m_6012_()), this.r, this.g, this.b);
      }

      public ParticleType<TrackLightningParticle.OrbData> m_6012_() {
         return (ParticleType<TrackLightningParticle.OrbData>)ModParticle.TRACK_LIGHTNING.get();
      }

      @OnlyIn(Dist.CLIENT)
      public int getR() {
         return this.r;
      }

      @OnlyIn(Dist.CLIENT)
      public int getG() {
         return this.g;
      }

      @OnlyIn(Dist.CLIENT)
      public int getB() {
         return this.b;
      }

      public static Codec<TrackLightningParticle.OrbData> CODEC(ParticleType<TrackLightningParticle.OrbData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.INT.fieldOf("r").forGetter(TrackLightningParticle.OrbData::getR),
                     Codec.INT.fieldOf("g").forGetter(TrackLightningParticle.OrbData::getG),
                     Codec.INT.fieldOf("b").forGetter(TrackLightningParticle.OrbData::getB)
                  )
                  .apply(codecBuilder, TrackLightningParticle.OrbData::new)
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class OrbFactory implements ParticleProvider<TrackLightningParticle.OrbData> {
      public Particle createParticle(
         TrackLightningParticle.OrbData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new TrackLightningParticle(
            worldIn, x, y, z, (double)((float)xSpeed), (double)((float)ySpeed), (double)((float)zSpeed), typeIn.getR(), typeIn.getG(), typeIn.getB()
         );
      }
   }
}
