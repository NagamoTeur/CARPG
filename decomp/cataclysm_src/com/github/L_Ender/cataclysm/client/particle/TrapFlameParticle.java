package com.github.L_Ender.cataclysm.client.particle;

import com.github.L_Ender.cataclysm.Cataclysm;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TrapFlameParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private float prevAlpha = 0.0F;

   protected TrapFlameParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed);
      this.sprites = spriteSet;
      this.m_108339_(this.sprites);
      this.f_107215_ = xSpeed;
      this.f_107216_ = ySpeed;
      this.f_107217_ = zSpeed;
      this.f_107663_ = 0.4F + world.f_46441_.m_188501_() * 0.25F;
      this.f_107225_ = 10 + world.f_46441_.m_188503_(20);
      this.f_172258_ = 0.99F;
   }

   public void m_5989_() {
      this.m_108339_(this.sprites);
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      float ageProgress = (float)this.f_107224_ / (float)this.f_107225_;
      float f = ageProgress - 0.5F;
      float scale = 1.0F + ageProgress * 0.5F;
      float f1 = 1.0F - f * 2.0F;
      if (ageProgress > 0.5F) {
         this.prevAlpha = this.f_107230_;
         this.m_107271_(this.prevAlpha + (f1 - this.prevAlpha) * Cataclysm.PROXY.getPartialTicks());
      }

      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_6569_(scale);
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107215_ = this.f_107215_ * (double)this.f_172258_;
         this.f_107216_ = this.f_107216_ * (double)this.f_172258_;
         this.f_107217_ = this.f_107217_ * (double)this.f_172258_;
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public int m_6355_(float partialTicks) {
      return 240;
   }

   public Particle m_6569_(float p_107683_) {
      this.f_107663_ = p_107683_;
      return this;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Factory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new TrapFlameParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
