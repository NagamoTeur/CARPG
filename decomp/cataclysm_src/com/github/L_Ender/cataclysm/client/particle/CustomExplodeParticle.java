package com.github.L_Ender.cataclysm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor.ARGB32;

public class CustomExplodeParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   private boolean hasFadeColor = false;
   private float fadeR;
   private float fadeG;
   private float fadeB;

   protected CustomExplodeParticle(
      ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites, boolean shortLifespan, int color1
   ) {
      super(world, x, y, z, xSpeed, ySpeed, zSpeed);
      this.f_107215_ = xSpeed;
      this.f_107216_ = ySpeed;
      this.f_107217_ = zSpeed;
      this.m_107250_(0.5F, 0.5F);
      this.f_107663_ = (shortLifespan ? 1.0F : 0.8F) + world.f_46441_.m_188501_() * 0.3F;
      this.f_107225_ = shortLifespan ? 5 + world.f_46441_.m_188503_(3) : 15 + world.f_46441_.m_188503_(10);
      this.f_172258_ = 0.96F;
      float randCol = world.f_46441_.m_188501_() * 0.05F;
      this.sprites = sprites;
      this.m_107253_(
         Math.min((float)ARGB32.m_13665_(color1) / 255.0F + randCol, 1.0F),
         Math.min(1.0F, (float)ARGB32.m_13667_(color1) / 255.0F + randCol),
         Math.min(1.0F, (float)ARGB32.m_13669_(color1) / 255.0F + randCol)
      );
   }

   public void setFadeColor(int i) {
      this.hasFadeColor = true;
      this.fadeR = (float)((i & 0xFF0000) >> 16) / 255.0F;
      this.fadeG = (float)((i & 0xFF00) >> 8) / 255.0F;
      this.fadeB = (float)((i & 0xFF) >> 0) / 255.0F;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      this.m_108339_(this.sprites);
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         if (this.hasFadeColor) {
            this.f_107227_ = this.f_107227_ + (this.fadeR - this.f_107227_) * 0.2F;
            this.f_107228_ = this.f_107228_ + (this.fadeG - this.f_107228_) * 0.2F;
            this.f_107229_ = this.f_107229_ + (this.fadeB - this.f_107229_) * 0.2F;
         } else {
            this.f_107227_ *= 0.95F;
            this.f_107228_ *= 0.95F;
            this.f_107229_ *= 0.95F;
         }

         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107215_ = this.f_107215_ * (double)this.f_172258_;
         this.f_107216_ = this.f_107216_ * (double)this.f_172258_;
         this.f_107217_ = this.f_107217_ * (double)this.f_172258_;
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107432_;
   }

   public float m_5902_(float scaleFactor) {
      return super.m_5902_(scaleFactor);
   }

   public int m_6355_(float partialTicks) {
      return 240;
   }

   public static class FlareFactory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public FlareFactory(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CustomExplodeParticle particle = new CustomExplodeParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet, false, 16777215);
         particle.m_108339_(this.spriteSet);
         particle.m_6569_(1.0F + worldIn.f_46441_.m_188501_() * 0.9F);
         particle.setFadeColor(12803843);
         return particle;
      }
   }
}
