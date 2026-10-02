package com.aqutheseal.celestisynth.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class RainfallBeamParticle extends SimpleAnimatedParticle {
   private final float rotSpeed;

   RainfallBeamParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
      super(pLevel, pX, pY, pZ, pSprites, 0.0F);
      this.f_107215_ = pXSpeed;
      this.f_107216_ = pYSpeed;
      this.f_107217_ = pZSpeed;
      this.f_107225_ = 10;
      this.rotSpeed = ((float)Math.random() - 0.5F) * 0.5F;
      this.m_107659_(15916745);
      this.m_108339_(pSprites);
   }

   public void m_5989_() {
      super.m_5989_();
      float startQuadSize = 1.0F;
      float endQuadSize = 0.0F;
      if (this.f_107224_ < this.f_107225_) {
         float progress = (float)this.f_107224_ / (float)this.f_107225_;
         this.f_107663_ = startQuadSize + progress * (endQuadSize - startQuadSize);
         this.f_107230_ = Mth.m_14036_(this.f_107663_, 0.0F, 1.0F);
      } else {
         this.f_107663_ = endQuadSize;
      }

      this.f_107204_ = this.f_107231_;
      this.f_107231_ = this.f_107231_ + (float) Math.PI * this.rotSpeed * 2.0F;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_6257_(double pX, double pY, double pZ) {
      this.m_107259_(this.m_107277_().m_82386_(pX, pY, pZ));
      this.m_107275_();
   }

   public static class Provider implements ParticleProvider<SimpleParticleType> {
      protected final SpriteSet sprites;

      public Provider(SpriteSet pSprites) {
         this.sprites = pSprites;
      }

      public Particle createParticle(
         SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
      ) {
         return new RainfallBeamParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
      }
   }

   public static class Quasar extends RainfallBeamParticle {
      Quasar(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
         super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pSprites);
      }

      public static class Provider extends RainfallBeamParticle.Provider {
         public Provider(SpriteSet pSprites) {
            super(pSprites);
         }

         @Override
         public Particle createParticle(
            SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
         ) {
            return new RainfallBeamParticle.Quasar(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
         }
      }
   }
}
