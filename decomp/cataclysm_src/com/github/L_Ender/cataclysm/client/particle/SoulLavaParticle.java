package com.github.L_Ender.cataclysm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoulLavaParticle extends TextureSheetParticle {
   SoulLavaParticle(ClientLevel p_107074_, double p_107075_, double p_107076_, double p_107077_) {
      super(p_107074_, p_107075_, p_107076_, p_107077_, 0.0, 0.0, 0.0);
      this.f_107226_ = 0.75F;
      this.f_172258_ = 0.999F;
      this.f_107215_ *= 0.8F;
      this.f_107216_ *= 0.8F;
      this.f_107217_ *= 0.8F;
      this.f_107216_ = (double)(this.f_107223_.m_188501_() * 0.4F + 0.05F);
      this.f_107663_ = this.f_107663_ * (this.f_107223_.m_188501_() * 2.0F + 0.2F);
      this.f_107225_ = (int)(16.0 / (Math.random() * 0.8 + 0.2));
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public int m_6355_(float p_107086_) {
      int i = super.m_6355_(p_107086_);
      int j = 240;
      int k = i >> 16 & 0xFF;
      return 240 | k << 16;
   }

   public float m_5902_(float p_107089_) {
      float f = ((float)this.f_107224_ + p_107089_) / (float)this.f_107225_;
      return this.f_107663_ * (1.0F - f * f);
   }

   public void m_5989_() {
      super.m_5989_();
      if (!this.f_107220_) {
         float f = (float)this.f_107224_ / (float)this.f_107225_;
         if (this.f_107223_.m_188501_() > f) {
            this.f_107208_.m_7106_(ParticleTypes.f_123762_, this.f_107212_, this.f_107213_, this.f_107214_, this.f_107215_, this.f_107216_, this.f_107217_);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Factory(SpriteSet p_107092_) {
         this.sprite = p_107092_;
      }

      public Particle createParticle(
         SimpleParticleType p_107103_,
         ClientLevel p_107104_,
         double p_107105_,
         double p_107106_,
         double p_107107_,
         double p_107108_,
         double p_107109_,
         double p_107110_
      ) {
         SoulLavaParticle lavaparticle = new SoulLavaParticle(p_107104_, p_107105_, p_107106_, p_107107_);
         lavaparticle.m_108335_(this.sprite);
         return lavaparticle;
      }
   }
}
