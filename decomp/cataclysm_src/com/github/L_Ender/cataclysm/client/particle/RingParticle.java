package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.util.CMMathUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
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
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleOptions.Deserializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class RingParticle extends TextureSheetParticle {
   public float r;
   public float g;
   public float b;
   public float opacity;
   public boolean facesCamera;
   public float yaw;
   public float pitch;
   public float size;
   private final SpriteSet sprites;
   private final RingParticle.EnumRingBehavior behavior;

   public RingParticle(
      ClientLevel world,
      double x,
      double y,
      double z,
      double motionX,
      double motionY,
      double motionZ,
      float yaw,
      float pitch,
      int duration,
      float r,
      float g,
      float b,
      float opacity,
      float size,
      boolean facesCamera,
      RingParticle.EnumRingBehavior behavior,
      SpriteSet sprites
   ) {
      super(world, x, y, z);
      this.sprites = sprites;
      this.m_107250_(1.0F, 1.0F);
      this.m_108339_(this.sprites);
      this.size = size * 0.1F;
      this.f_107225_ = duration;
      this.f_107230_ = 1.0F;
      this.r = r;
      this.g = g;
      this.b = b;
      this.opacity = opacity;
      this.yaw = yaw;
      this.pitch = pitch;
      this.facesCamera = facesCamera;
      this.f_107215_ = motionX;
      this.f_107216_ = motionY;
      this.f_107217_ = motionZ;
      this.behavior = behavior;
   }

   public int m_6355_(float delta) {
      return 240 | super.m_6355_(delta) & 0xFF0000;
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.sprites);
      if (this.f_107224_ >= this.f_107225_) {
         this.m_107274_();
      }

      this.f_107224_++;
   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float var = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_;
      if (this.behavior == RingParticle.EnumRingBehavior.GROW) {
         this.f_107663_ = this.size * var;
      } else if (this.behavior == RingParticle.EnumRingBehavior.SHRINK) {
         this.f_107663_ = this.size * (1.0F - var);
      } else if (this.behavior == RingParticle.EnumRingBehavior.GROW_THEN_SHRINK) {
         this.f_107663_ = (float)((double)this.size * ((double)(1.0F - var) - Math.pow(2000.0, (double)(-var))));
      } else {
         this.f_107663_ = this.size;
      }

      this.f_107230_ = this.opacity * 0.95F * (1.0F - ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_) + 0.05F;
      this.f_107227_ = this.r;
      this.f_107228_ = this.g;
      this.f_107229_ = this.b;
      Vec3 Vector3d = renderInfo.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTicks, this.f_107209_, this.f_107212_) - Vector3d.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTicks, this.f_107210_, this.f_107213_) - Vector3d.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTicks, this.f_107211_, this.f_107214_) - Vector3d.m_7094_());
      Quaternion quaternionf = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
      if (this.facesCamera) {
         if (this.f_107231_ == 0.0F) {
            quaternionf = renderInfo.m_90591_();
         } else {
            quaternionf = new Quaternion(renderInfo.m_90591_());
            float f3 = Mth.m_14179_(partialTicks, this.f_107204_, this.f_107231_);
            quaternionf.m_80148_(Vector3f.f_122227_.m_122270_(f3));
         }
      } else {
         Quaternion quatX = CMMathUtil.quatFromRotationXYZ(this.pitch, 0.0F, 0.0F, false);
         Quaternion quatY = CMMathUtil.quatFromRotationXYZ(0.0F, this.yaw, 0.0F, false);
         quaternionf.m_80148_(quatY);
         quaternionf.m_80148_(quatX);
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      vector3f1.m_122251_(quaternionf);
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f4 = this.m_5902_(partialTicks);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.m_122251_(quaternionf);
         vector3f.m_122261_(f4);
         vector3f.m_122272_(f, f1, f2);
      }

      float f7 = this.m_5970_();
      float f8 = this.m_5952_();
      float f5 = this.m_5951_();
      float f6 = this.m_5950_();
      int j = this.m_6355_(partialTicks);
      buffer.m_5483_((double)avector3f[0].m_122239_(), (double)avector3f[0].m_122260_(), (double)avector3f[0].m_122269_())
         .m_7421_(f8, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
      buffer.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_())
         .m_7421_(f8, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
      buffer.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_())
         .m_7421_(f7, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
      buffer.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_())
         .m_7421_(f7, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public static enum EnumRingBehavior {
      SHRINK,
      GROW,
      CONSTANT,
      GROW_THEN_SHRINK;
   }

   public static class RingData implements ParticleOptions {
      public static final Deserializer<RingParticle.RingData> DESERIALIZER = new Deserializer<RingParticle.RingData>() {
         public RingParticle.RingData fromCommand(ParticleType<RingParticle.RingData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float yaw = (float)reader.readDouble();
            reader.expect(' ');
            float pitch = (float)reader.readDouble();
            reader.expect(' ');
            float r = (float)reader.readDouble();
            reader.expect(' ');
            float g = (float)reader.readDouble();
            reader.expect(' ');
            float b = (float)reader.readDouble();
            reader.expect(' ');
            float a = (float)reader.readDouble();
            reader.expect(' ');
            float scale = (float)reader.readDouble();
            reader.expect(' ');
            int duration = reader.readInt();
            reader.expect(' ');
            boolean facesCamera = reader.readBoolean();
            return new RingParticle.RingData(yaw, pitch, duration, r, g, b, a, scale, facesCamera, RingParticle.EnumRingBehavior.GROW);
         }

         public RingParticle.RingData fromNetwork(ParticleType<RingParticle.RingData> particleTypeIn, FriendlyByteBuf buffer) {
            return new RingParticle.RingData(
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readInt(),
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readFloat(),
               buffer.readBoolean(),
               RingParticle.EnumRingBehavior.GROW
            );
         }
      };
      private final float yaw;
      private final float pitch;
      private final float r;
      private final float g;
      private final float b;
      private final float a;
      private final float scale;
      private final int duration;
      private final boolean facesCamera;
      private final RingParticle.EnumRingBehavior behavior;

      public RingData(
         float yaw, float pitch, int duration, float r, float g, float b, float a, float scale, boolean facesCamera, RingParticle.EnumRingBehavior behavior
      ) {
         this.yaw = yaw;
         this.pitch = pitch;
         this.r = r;
         this.g = g;
         this.b = b;
         this.a = a;
         this.scale = scale;
         this.duration = duration;
         this.facesCamera = facesCamera;
         this.behavior = behavior;
      }

      public void m_7711_(FriendlyByteBuf buffer) {
         buffer.writeFloat(this.r);
         buffer.writeFloat(this.g);
         buffer.writeFloat(this.b);
         buffer.writeFloat(this.scale);
         buffer.writeInt(this.duration);
      }

      public String m_5942_() {
         return String.format(
            Locale.ROOT,
            "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f %d %b",
            ForgeRegistries.PARTICLE_TYPES.getKey(this.m_6012_()),
            this.yaw,
            this.pitch,
            this.r,
            this.g,
            this.b,
            this.scale,
            this.a,
            this.duration,
            this.facesCamera
         );
      }

      public ParticleType<RingParticle.RingData> m_6012_() {
         return (ParticleType<RingParticle.RingData>)ModParticle.RING.get();
      }

      @OnlyIn(Dist.CLIENT)
      public float getYaw() {
         return this.yaw;
      }

      @OnlyIn(Dist.CLIENT)
      public float getPitch() {
         return this.pitch;
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
      public float getA() {
         return this.a;
      }

      @OnlyIn(Dist.CLIENT)
      public float getScale() {
         return this.scale;
      }

      @OnlyIn(Dist.CLIENT)
      public int getDuration() {
         return this.duration;
      }

      @OnlyIn(Dist.CLIENT)
      public boolean getFacesCamera() {
         return this.facesCamera;
      }

      @OnlyIn(Dist.CLIENT)
      public RingParticle.EnumRingBehavior getBehavior() {
         return this.behavior;
      }

      public static Codec<RingParticle.RingData> CODEC(ParticleType<RingParticle.RingData> particleType) {
         return RecordCodecBuilder.create(
            codecBuilder -> codecBuilder.group(
                     Codec.FLOAT.fieldOf("yaw").forGetter(RingParticle.RingData::getYaw),
                     Codec.FLOAT.fieldOf("pitch").forGetter(RingParticle.RingData::getPitch),
                     Codec.FLOAT.fieldOf("r").forGetter(RingParticle.RingData::getR),
                     Codec.FLOAT.fieldOf("g").forGetter(RingParticle.RingData::getG),
                     Codec.FLOAT.fieldOf("b").forGetter(RingParticle.RingData::getB),
                     Codec.FLOAT.fieldOf("a").forGetter(RingParticle.RingData::getA),
                     Codec.FLOAT.fieldOf("scale").forGetter(RingParticle.RingData::getScale),
                     Codec.INT.fieldOf("duration").forGetter(RingParticle.RingData::getDuration),
                     Codec.BOOL.fieldOf("facesCamera").forGetter(RingParticle.RingData::getFacesCamera),
                     Codec.STRING.fieldOf("behavior").forGetter(ringData -> ringData.getBehavior().toString())
                  )
                  .apply(
                     codecBuilder,
                     (yaw, pitch, r, g, b, a, scale, duration, facesCamera, behavior) -> new RingParticle.RingData(
                           yaw, pitch, duration, r, g, b, a, scale, facesCamera, RingParticle.EnumRingBehavior.valueOf(behavior)
                        )
                  )
         );
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static final class RingFactory implements ParticleProvider<RingParticle.RingData> {
      private final SpriteSet spriteSet;

      public RingFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(
         RingParticle.RingData typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new RingParticle(
            worldIn,
            x,
            y,
            z,
            xSpeed,
            ySpeed,
            zSpeed,
            typeIn.getYaw(),
            typeIn.getPitch(),
            typeIn.getDuration(),
            typeIn.getR(),
            typeIn.getG(),
            typeIn.getB(),
            typeIn.getA(),
            typeIn.getScale(),
            typeIn.getFacesCamera(),
            typeIn.getBehavior(),
            this.spriteSet
         );
      }
   }
}
