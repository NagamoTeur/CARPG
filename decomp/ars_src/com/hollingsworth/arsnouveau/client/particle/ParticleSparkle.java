package com.hollingsworth.arsnouveau.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class ParticleSparkle extends TextureSheetParticle {
   public float colorR;
   public float colorG;
   public float colorB;
   public float initScale;
   public float initAlpha = 0.0F;

   protected ParticleSparkle(
      ClientLevel worldIn,
      double x,
      double y,
      double z,
      double vx,
      double vy,
      double vz,
      float r,
      float g,
      float b,
      float scale,
      int lifetime,
      SpriteSet sprite
   ) {
      super(worldIn, x, y, z, 0.0, 0.0, 0.0);
      this.colorR = r;
      this.colorG = g;
      this.colorB = b;
      if ((double)this.colorR > 1.0) {
         this.colorR /= 255.0F;
      }

      if ((double)this.colorG > 1.0) {
         this.colorG /= 255.0F;
      }

      if ((double)this.colorB > 1.0) {
         this.colorB /= 255.0F;
      }

      this.m_107253_(this.colorR, this.colorG, this.colorB);
      this.f_107225_ = lifetime;
      this.f_107663_ = scale;
      this.f_107219_ = false;
      this.initScale = scale;
      this.f_107215_ = ParticleUtil.inRange(-0.01, 0.01);
      this.f_107216_ = -0.02;
      this.f_107217_ = ParticleUtil.inRange(-0.01, 0.01);
      this.m_108335_(sprite);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderTypes.EMBER_RENDER;
   }

   public int m_6355_(float pTicks) {
      return 255;
   }

   public void m_5989_() {
      super.m_5989_();
      float lifeCoeff = (float)this.f_107224_ / (float)this.f_107225_;
      this.f_107230_ = 1.0F - lifeCoeff;
   }

   public boolean m_107276_() {
      return this.f_107224_ < this.f_107225_;
   }
}
