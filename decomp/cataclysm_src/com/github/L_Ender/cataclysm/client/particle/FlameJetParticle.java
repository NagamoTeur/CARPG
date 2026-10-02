package com.github.L_Ender.cataclysm.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlameJetParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected FlameJetParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed);
      this.sprites = spriteSet;
      this.m_108339_(this.sprites);
      this.f_107215_ = xSpeed;
      this.f_107216_ = ySpeed;
      this.f_107217_ = zSpeed;
      this.f_107663_ = 2.0F;
      this.f_107225_ = 10;
      this.m_107250_(3.0F, 3.0F);
   }

   public void m_5989_() {
      this.m_108339_(this.sprites);
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      }
   }

   public void m_5744_(VertexConsumer buffer, Camera camera, float partialTicks) {
      Vec3 vec3 = camera.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTicks, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTicks, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTicks, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Quaternion quaternionf = Vector3f.f_122225_.m_122240_(-camera.m_90590_());
      Vector3f[] avector3f = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      float f3 = this.m_5902_(partialTicks);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = avector3f[i];
         vector3f.m_122251_(quaternionf);
         vector3f.m_122261_(f3);
         vector3f.m_122272_(f, f1, f2);
      }

      float f6 = this.m_5970_();
      float f7 = this.m_5952_();
      float f4 = this.m_5951_();
      float f5 = this.m_5950_();
      int j = this.m_6355_(partialTicks);
      buffer.m_5483_((double)avector3f[0].m_122239_(), (double)avector3f[0].m_122260_(), (double)avector3f[0].m_122269_())
         .m_7421_(f7, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
      buffer.m_5483_((double)avector3f[1].m_122239_(), (double)avector3f[1].m_122260_(), (double)avector3f[1].m_122269_())
         .m_7421_(f7, f4)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
      buffer.m_5483_((double)avector3f[2].m_122239_(), (double)avector3f[2].m_122260_(), (double)avector3f[2].m_122269_())
         .m_7421_(f6, f4)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
      buffer.m_5483_((double)avector3f[3].m_122239_(), (double)avector3f[3].m_122260_(), (double)avector3f[3].m_122269_())
         .m_7421_(f6, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(j)
         .m_5752_();
   }

   public int m_6355_(float p_106821_) {
      float f = ((float)this.f_107224_ + p_106821_) / (float)this.f_107225_;
      f = Mth.m_14036_(f, 0.0F, 1.0F);
      int i = super.m_6355_(p_106821_);
      int j = i & 0xFF;
      int k = i >> 16 & 0xFF;
      j += (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         int var8 = 240;
      }

      return 240;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new FlameJetParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
