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
import java.util.Random;
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

public class LightningParticle extends Particle {
   private int r;
   private int g;
   private int b;
   private float toX;
   private float toY;
   private float toZ;
   private LightningRender lightningRender = new LightningRender();

   public LightningParticle(ClientLevel world, double x, double y, double z, float xSpeed, float ySpeed, float zSpeed, int r, int g, int b) {
      super(world, x, y, z);
      this.m_107250_(1.0F, 1.0F);
      this.f_107226_ = 0.0F;
      this.f_107225_ = 5 + new Random().nextInt(3);
      this.toX = xSpeed;
      this.toY = ySpeed;
      this.toZ = zSpeed;
      this.r = r;
      this.g = g;
      this.b = b;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public void m_5744_(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
      Vec3 vec3 = camera.m_90583_();
      PoseStack posestack = new PoseStack();
      BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      float f = (float)(Mth.m_14139_((double)partialTick, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTick, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTick, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      float lerpAge = (float)this.f_107224_ + partialTick;
      float ageProgress = lerpAge / (float)this.f_107225_;
      float scale = 1.85F;
      posestack.m_85836_();
      posestack.m_85837_((double)f, (double)f1, (double)f2);
      posestack.m_85841_(scale, scale, scale);
      LightningBoltData.BoltRenderInfo lightningBoltData = new LightningBoltData.BoltRenderInfo(
         0.5F, 0.1F, 0.5F, 0.85F, new Vector4f((float)this.r / 255.0F, (float)this.g / 255.0F, (float)this.b / 255.0F, (1.0F - ageProgress) * 0.8F), 0.1F
      );
      LightningBoltData bolt = new LightningBoltData(lightningBoltData, Vec3.f_82478_, new Vec3((double)this.toX, (double)this.toY, (double)this.toZ), 4)
         .size(0.05F)
         .lifespan(this.f_107225_)
         .spawn(LightningBoltData.SpawnFunction.CONSECUTIVE);
      this.lightningRender.update(this, bolt, partialTick);
      this.lightningRender.render(partialTick, posestack, multibuffersource$buffersource);
      multibuffersource$buffersource.m_109911_();
      posestack.m_85849_();
   }

   public static class OrbData implements ParticleOptions {
      public static final Deserializer<LightningParticle.OrbData> DESERIALIZER = new Deserializer<LightningParticle.OrbData>() {
         public LightningParticle.OrbData fromCommand(ParticleType<LightningParticle.OrbData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            int r = reader.readInt();
            reader.expect(' ');
            int g = reader.readInt();
            reader.expect(' ');
            int b = reader.readInt();
            return new LightningParticle.OrbData(r, g, b);
         }

         public LightningParticle.OrbData fromNetwork(ParticleType<LightningParticle.OrbData> particleTypeIn, FriendlyByteBuf buffer) {
            return new LightningParticle.OrbData(buffer.readInt(), buffer.readInt(), buffer.readInt());
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

      public ParticleType<LightningParticle.OrbData> m_6012_() {
         return (ParticleType<LightningParticle.OrbData>)ModParticle.LIGHTNING.get();
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

      public static Codec<LightningParticle.OrbData> CODEC(ParticleType<LightningParticle.OrbData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.INT.fieldOf("r").forGetter(LightningParticle.OrbData::getR),
                     Codec.INT.fieldOf("g").forGetter(LightningParticle.OrbData::getG),
                     Codec.INT.fieldOf("b").forGetter(LightningParticle.OrbData::getB)
                  )
                  .apply(codecBuilder, LightningParticle.OrbData::new)
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class OrbFactory implements ParticleProvider<LightningParticle.OrbData> {
      public Particle createParticle(
         LightningParticle.OrbData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new LightningParticle(worldIn, x, y, z, (float)xSpeed, (float)ySpeed, (float)zSpeed, typeIn.getR(), typeIn.getG(), typeIn.getB());
      }
   }
}
