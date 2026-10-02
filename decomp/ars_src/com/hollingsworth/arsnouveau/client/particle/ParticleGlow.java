package com.hollingsworth.arsnouveau.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleGlow extends TextureSheetParticle {
   public float colorR = 0.0F;
   public float colorG = 0.0F;
   public float colorB = 0.0F;
   public float initScale = 0.0F;
   public float initAlpha = 0.0F;
   public boolean disableDepthTest;

   public ParticleGlow(
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
      float a,
      float scale,
      int lifetime,
      SpriteSet sprite,
      boolean disableDepthTest
   ) {
      super(worldIn, x, y, z, 0.0, 0.0, 0.0);
      this.f_107219_ = false;
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
      this.f_107225_ = (int)((float)lifetime * 0.5F);
      this.f_107663_ = 0.0F;
      this.initScale = scale;
      this.f_107215_ = vx * 2.0;
      this.f_107216_ = vy * 2.0;
      this.f_107217_ = vz * 2.0;
      this.initAlpha = a;
      this.m_108335_(sprite);
      this.disableDepthTest = disableDepthTest;
   }

   public ParticleRenderType m_7556_() {
      return this.disableDepthTest ? ParticleRenderTypes.EMBER_RENDER_NO_MASK : ParticleRenderTypes.EMBER_RENDER;
   }

   public int m_6355_(float pTicks) {
      return 255;
   }

   public void m_5989_() {
      super.m_5989_();
      if (this.f_107208_.f_46441_.m_188503_(6) == 0) {
         this.f_107224_++;
      }

      float lifeCoeff = (float)this.f_107224_ / (float)this.f_107225_;
      this.f_107663_ = this.initScale - this.initScale * lifeCoeff;
      this.f_107230_ = this.initAlpha * (1.0F - lifeCoeff);
      this.f_107204_ = this.f_107231_++;
   }

   public boolean m_107276_() {
      return this.f_107224_ < this.f_107225_;
   }
}
