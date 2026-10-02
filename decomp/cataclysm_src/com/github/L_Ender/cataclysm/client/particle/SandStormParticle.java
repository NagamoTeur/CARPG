package com.github.L_Ender.cataclysm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SandStormParticle extends TextureSheetParticle {
   protected SandStormParticle(ClientLevel level, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
      super(level, xCoord, yCoord, zCoord, xd, yd, zd);
      this.f_107215_ += xd;
      this.f_107216_ += yd;
      this.f_107217_ += zd;
      this.f_107663_ *= 2.5F;
      this.f_107225_ = (int)(Math.random() * 2.0) + 60;
      this.m_108339_(spriteSet);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public float m_5902_(float p_107608_) {
      float f = 1.0F - ((float)this.f_107224_ + p_107608_) / ((float)this.f_107225_ * 1.5F);
      return this.f_107663_ * f;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         float f = (float)this.f_107224_ / (float)this.f_107225_;
         this.f_107212_ = this.f_107212_ + this.f_107215_ * (double)f;
         this.f_107213_ = this.f_107213_ + this.f_107216_ * (double)f;
         this.f_107214_ = this.f_107214_ + this.f_107217_ * (double)f;
         this.m_107264_(this.f_107212_, this.f_107213_, this.f_107214_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Factory(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new SandStormParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
