package net.xylonity.knightquest.common.particle.explosiveenhancement;

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
import net.xylonity.knightquest.common.api.explosiveenhancement.ExplosiveValues;

public class BlastWaveParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private static final Quaternion QUATERNION = new Quaternion(0.0F, -0.7F, 0.7F, 0.0F);

   public BlastWaveParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites, double velX, double velY, double velZ) {
      super(world, x, y + 0.5, z, 0.0, 0.0, 0.0);
      this.f_107663_ = (float)velX;
      this.m_172260_(0.0, 0.0, 0.0);
      this.f_107225_ = (int)(15.0 + Math.floor(velX / 5.0));
      this.sprites = sprites;
      this.m_108339_(sprites);
   }

   public void m_5744_(VertexConsumer buffer, Camera camera, float ticks) {
      Vec3 vec3 = camera.m_90583_();
      float x = (float)(Mth.m_14139_((double)ticks, this.f_107209_, this.f_107212_) - vec3.m_7096_());
      float y = (float)(Mth.m_14139_((double)ticks, this.f_107210_, this.f_107213_) - vec3.m_7098_());
      float z = (float)(Mth.m_14139_((double)ticks, this.f_107211_, this.f_107214_) - vec3.m_7094_());
      Vector3f[] vector3fs = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)
      };
      Vector3f[] vector3fsBottom = new Vector3f[]{
         new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)
      };
      float f4 = this.m_5902_(ticks);

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
      int light = this.m_6355_(ticks);
      buffer.m_5483_((double)vector3fs[0].m_122239_(), (double)vector3fs[0].m_122260_(), (double)vector3fs[0].m_122269_())
         .m_7421_(f8, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[1].m_122239_(), (double)vector3fs[1].m_122260_(), (double)vector3fs[1].m_122269_())
         .m_7421_(f8, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[2].m_122239_(), (double)vector3fs[2].m_122260_(), (double)vector3fs[2].m_122269_())
         .m_7421_(f7, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[3].m_122239_(), (double)vector3fs[3].m_122260_(), (double)vector3fs[3].m_122269_())
         .m_7421_(f7, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[3].m_122239_(), (double)vector3fs[3].m_122260_(), (double)vector3fs[3].m_122269_())
         .m_7421_(f7, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[2].m_122239_(), (double)vector3fs[2].m_122260_(), (double)vector3fs[2].m_122269_())
         .m_7421_(f7, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[1].m_122239_(), (double)vector3fs[1].m_122260_(), (double)vector3fs[1].m_122269_())
         .m_7421_(f8, f5)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
      buffer.m_5483_((double)vector3fs[0].m_122239_(), (double)vector3fs[0].m_122260_(), (double)vector3fs[0].m_122269_())
         .m_7421_(f8, f6)
         .m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_)
         .m_85969_(light);
   }

   protected int m_6355_(float pPartialTick) {
      return ExplosiveValues.emissiveExplosion ? 15728880 : super.m_6355_(pPartialTick);
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.sprites);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new BlastWaveParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
