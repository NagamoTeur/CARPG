package net.xylonity.knightquest.common.particle.explosiveenhancement;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xylonity.knightquest.common.api.explosiveenhancement.ExplosiveValues;

public class UnderwaterSparksParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   UnderwaterSparksParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites, double velX, double velY, double velZ) {
      super(world, x, y, z);
      this.sprites = sprites;
      this.f_107225_ = (int)(5.0 + Math.floor(velX / 5.0));
      if (velX == 0.0) {
         this.f_107663_ = (float)ExplosiveValues.underwaterSparkSize;
      } else {
         this.f_107663_ = (float)(ExplosiveValues.underwaterSparkSize * velX * 0.25);
      }

      this.m_172260_(0.0, 0.0, 0.0);
      this.f_107230_ = (float)ExplosiveValues.underwaterSparkOpacity;
      this.m_108339_(sprites);
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.f_107216_ = this.f_107216_ - (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.m_108339_(this.sprites);
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   protected int m_6355_(float pPartialTick) {
      return ExplosiveValues.emissiveWaterExplosion ? 15728880 : super.m_6355_(pPartialTick);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new UnderwaterSparksParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
