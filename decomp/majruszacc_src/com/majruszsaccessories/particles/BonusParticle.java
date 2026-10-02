package com.majruszsaccessories.particles;

import com.majruszlibrary.client.CustomParticle;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.time.TimeHelper;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BonusParticle extends CustomParticle {
   private final SpriteSet spriteSet;

   public BonusParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      this.spriteSet = spriteSet;
      this.f_107215_ = xSpeed;
      this.f_107216_ = ySpeed;
      this.f_107217_ = zSpeed;
      this.xdFormula = xd -> xd * 0.8;
      this.ydFormula = yd -> yd * 0.8;
      this.zdFormula = zd -> zd * 0.8;
      this.f_107225_ = TimeHelper.toTicks((double)Random.nextFloat(1.8F, 2.4F));
      this.f_107224_ = 0;
      this.scaleFormula = lifetime -> 0.5F;
      this.m_108339_(this.spriteSet);
   }

   public void update(BonusParticleType.Options options) {
      int color = options.color;
      float colorRatio = Random.nextFloat(0.8F, 1.0F);
      this.m_107253_(
         (float)(color >> 16 & 0xFF) / 255.0F * colorRatio, (float)(color >> 8 & 0xFF) / 255.0F * colorRatio, (float)(color & 0xFF) / 255.0F * colorRatio
      );
   }

   public void m_5989_() {
      super.m_5989_();
      if (!this.f_107220_) {
         this.m_108337_(this.spriteSet.m_5819_(Math.min(8 * this.f_107224_, this.f_107225_), this.f_107225_));
      }

      this.m_107271_(Math.min(1.0F, 3.0F * (1.0F - (float)this.f_107224_ / (float)this.f_107225_)));
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory extends com.majruszlibrary.client.CustomParticle.Factory<BonusParticle, BonusParticleType.Options> {
      public Factory(SpriteSet sprite) {
         super(sprite, BonusParticle::new, BonusParticle::update);
      }
   }
}
