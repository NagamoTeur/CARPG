package net.xylonity.knightquest.common.particle;

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
import org.jetbrains.annotations.NotNull;

public class PoisonCloudParticle extends TextureSheetParticle {
   private final SpriteSet spritesset;
   private static Quaternion QUATERNION = new Quaternion(0.0F, 0.7F, -0.7F, 0.0F);

   protected PoisonCloudParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites, double velX, double velY, double velZ) {
      super(world, x, y + 0.5, z, 0.0, 0.0, 0.0);
      this.f_107663_ = 2.0F;
      this.f_107227_ = 1.0F;
      this.f_107228_ = 1.0F;
      this.f_107229_ = 1.0F;
      this.f_107225_ = (int)((1.4 + this.f_107223_.m_188500_() * 2.0) * 20.0);
      this.m_108339_(sprites);
      this.spritesset = sprites;
      this.m_172260_(0.0, 0.0, 0.0);
      this.f_107230_ = 0.0F;
      float darknessFactor = 0.75F + this.f_107223_.m_188501_() * 0.25F;
      this.m_107253_(darknessFactor * this.f_107227_, darknessFactor * this.f_107228_, darknessFactor * this.f_107229_);
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public void m_5744_(@NotNull VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
      Vec3 vec3 = pRenderInfo.m_90583_();
      float x = (float)(Mth.m_14139_((double)pPartialTicks, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float y = (float)(Mth.m_14139_((double)pPartialTicks, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float z = (float)(Mth.m_14139_((double)pPartialTicks, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Vector3f[] vector3fs = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      Vector3f[] vector3fsBottom = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)
      };
      float f4 = this.m_5902_(pPartialTicks);

      for (int i = 0; i < 4; i++) {
         Vector3f vector3f = vector3fs[i];
         vector3f.m_122251_(QUATERNION);
         vector3f.m_122261_(f4);
         vector3f.m_122272_(x, y, z);
         Vector3f vector3fBottom = vector3fsBottom[i];
         vector3fBottom.m_122251_(QUATERNION);
         vector3fBottom.m_122261_(f4);
         vector3fBottom.m_122272_(x, y - 0.1F, z);
      }

      float f7 = this.m_5970_();
      float f8 = this.m_5952_();
      float f5 = this.m_5951_();
      float f6 = this.m_5950_();
      int light = this.m_6355_(pPartialTicks);
      pBuffer.m_5483_((double)vector3fs[0].m_122239_(), (double)vector3fs[0].m_122260_(), (double)vector3fs[0].m_122269_())
         .m_7421_(f8, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[1].m_122239_(), (double)vector3fs[1].m_122260_(), (double)vector3fs[1].m_122269_())
         .m_7421_(f8, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[2].m_122239_(), (double)vector3fs[2].m_122260_(), (double)vector3fs[2].m_122269_())
         .m_7421_(f7, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[3].m_122239_(), (double)vector3fs[3].m_122260_(), (double)vector3fs[3].m_122269_())
         .m_7421_(f7, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[3].m_122239_(), (double)vector3fs[3].m_122260_(), (double)vector3fs[3].m_122269_())
         .m_7421_(f7, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[2].m_122239_(), (double)vector3fs[2].m_122260_(), (double)vector3fs[2].m_122269_())
         .m_7421_(f7, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[1].m_122239_(), (double)vector3fs[1].m_122260_(), (double)vector3fs[1].m_122269_())
         .m_7421_(f8, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
      pBuffer.m_5483_((double)vector3fs[0].m_122239_(), (double)vector3fs[0].m_122260_(), (double)vector3fs[0].m_122269_())
         .m_7421_(f8, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light)
         .m_5752_();
   }

   public void m_5989_() {
      super.m_5989_();
      int fadeInEnd = 20;
      int fadeOutStart = this.f_107225_ - 20;
      if (this.f_107224_ < fadeInEnd) {
         this.f_107230_ = (float)this.f_107224_ / (float)fadeInEnd;
      } else if (this.f_107224_ >= fadeOutStart) {
         float fadeProgress = (float)(this.f_107224_ - fadeOutStart) / 20.0F;
         this.f_107230_ = 1.0F - fadeProgress;
      } else {
         this.f_107230_ = 1.0F;
      }

      if (this.f_107224_ >= this.f_107225_) {
         this.m_107274_();
      }

      this.m_108339_(this.spritesset);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(
         @NotNull SimpleParticleType particleType, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz
      ) {
         return new PoisonCloudParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
