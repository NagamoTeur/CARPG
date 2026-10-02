package com.aqutheseal.celestisynth.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class RainfallEnergyParticle extends SimpleAnimatedParticle {
   protected float rotSpeed;
   protected float startQuadSize;

   RainfallEnergyParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
      super(pLevel, pX, pY, pZ, pSprites, 0.0F);
      this.f_107215_ = pXSpeed;
      this.f_107216_ = pYSpeed;
      this.f_107217_ = pZSpeed;
      this.f_107225_ = 20;
      this.rotSpeed = ((float)Math.random() - 0.5F) * 0.3F;
      this.startQuadSize = 1.5F;
      this.m_107659_(15916745);
      this.m_108339_(pSprites);
   }

   public void m_5989_() {
      super.m_5989_();
      float endQuadSize = 0.0F;
      if (this.f_107224_ < this.f_107225_) {
         float progress = (float)this.f_107224_ / (float)this.f_107225_;
         this.f_107663_ = this.startQuadSize + progress * (endQuadSize - this.startQuadSize);
         this.changeAlpha(this.f_107663_);
      } else {
         this.f_107663_ = endQuadSize;
      }

      this.f_107204_ = this.f_107231_;
      this.f_107231_ = this.f_107231_ + (float) Math.PI * this.rotSpeed * 2.0F;
   }

   public void changeAlpha(float value) {
      this.f_107230_ = Mth.m_14036_(value, 0.0F, 1.0F);
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
         return new RainfallEnergyParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
      }
   }

   public static class Small extends RainfallEnergyParticle {
      Small(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
         super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, pSprites);
         this.rotSpeed = ((float)Math.random() - 0.5F) * 0.15F;
         this.startQuadSize = 0.7F;
      }

      public static class Provider extends RainfallEnergyParticle.Provider {
         public Provider(SpriteSet pSprites) {
            super(pSprites);
         }

         @Override
         public Particle createParticle(
            SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
         ) {
            return new RainfallEnergyParticle.Small(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
         }
      }
   }
}
