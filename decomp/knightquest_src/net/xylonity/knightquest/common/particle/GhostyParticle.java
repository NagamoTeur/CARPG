package net.xylonity.knightquest.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhostyParticle extends TextureSheetParticle {
   private float rotSpeed;
   private final float particleRandom;
   private final float spinAcceleration;

   GhostyParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSpriteSet) {
      super(pLevel, pX, pY, pZ);
      this.m_108337_(pSpriteSet.m_5819_(this.f_107223_.m_188503_(12), 12));
      this.rotSpeed = (float)Math.toRadians(this.f_107223_.m_188499_() ? -30.0 : 30.0);
      this.particleRandom = this.f_107223_.m_188501_();
      this.spinAcceleration = (float)Math.toRadians(this.f_107223_.m_188499_() ? -5.0 : 5.0);
      this.f_107225_ = 300;
      this.f_107226_ = 7.5E-4F;
      float $$5 = this.f_107223_.m_188499_() ? 0.05F : 0.075F;
      this.f_107663_ = $$5;
      this.m_107250_($$5, $$5);
      this.f_172258_ = 1.0F;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107225_-- <= 0) {
         this.m_107274_();
      }

      if (!this.f_107220_) {
         float $$0 = (float)(300 - this.f_107225_);
         float $$1 = Math.min($$0 / 300.0F, 1.0F);
         double $$2 = Math.cos(Math.toRadians((double)(this.particleRandom * 60.0F))) * 2.0 * Math.pow((double)$$1, 1.25);
         double $$3 = Math.sin(Math.toRadians((double)(this.particleRandom * 60.0F))) * 2.0 * Math.pow((double)$$1, 1.25);
         this.f_107215_ += $$2 * 0.0025F;
         this.f_107217_ += $$3 * 0.0025F;
         this.f_107216_ = this.f_107216_ - (double)this.f_107226_;
         this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
         this.f_107204_ = this.f_107231_;
         this.f_107231_ = this.f_107231_ + this.rotSpeed / 20.0F;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         if (this.f_107218_ || this.f_107225_ < 299 && (this.f_107215_ == 0.0 || this.f_107217_ == 0.0)) {
            this.m_107274_();
         }

         if (!this.f_107220_) {
            this.f_107215_ = this.f_107215_ * (double)this.f_172258_;
            this.f_107216_ = this.f_107216_ * (double)this.f_172258_;
            this.f_107217_ = this.f_107217_ * (double)this.f_172258_;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new GhostyParticle(level, x, y, z, this.sprites);
      }
   }
}
