package com.hollingsworth.arsnouveau.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.level.Level;

public class ParticleLine extends TextureSheetParticle {
   public float colorR;
   public float colorG;
   public float colorB;
   public float initScale;
   public float initX;
   public float initY;
   public float initZ;
   public float destX;
   public float destY;
   public float destZ;

   protected ParticleLine(
      Level worldIn, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float scale, int lifetime, SpriteSet sprite
   ) {
      super((ClientLevel)worldIn, x, y, z, 0.0, 0.0, 0.0);
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
      this.initScale = scale;
      this.f_107215_ = 0.0;
      this.f_107216_ = 0.0;
      this.f_107217_ = 0.0;
      this.initX = (float)x;
      this.initY = (float)y;
      this.initZ = (float)z;
      this.destX = (float)vx;
      this.destY = (float)vy;
      this.destZ = (float)vz;
      this.f_107231_ = (float) (Math.PI * 2);
      this.m_108335_(sprite);
   }

   public void m_5989_() {
      super.m_5989_();
      if (this.f_107208_.f_46441_.m_188503_(6) == 0) {
         this.f_107224_++;
      }

      float lifeCoeff = (float)this.f_107224_ / (float)this.f_107225_;
      this.f_107212_ = (double)((1.0F - lifeCoeff) * this.initX + lifeCoeff * this.destX);
      this.f_107213_ = (double)((1.0F - lifeCoeff) * this.initY + lifeCoeff * this.destY);
      this.f_107214_ = (double)((1.0F - lifeCoeff) * this.initZ + lifeCoeff * this.destZ);
      this.f_107663_ = this.initScale - this.initScale * lifeCoeff;
      this.f_107230_ = 1.0F - lifeCoeff;
      this.f_107204_ = this.f_107231_;
   }

   public boolean m_107276_() {
      return this.f_107224_ < this.f_107225_;
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderTypes.EMBER_RENDER;
   }

   public int m_6355_(float pTicks) {
      return 255;
   }
}
