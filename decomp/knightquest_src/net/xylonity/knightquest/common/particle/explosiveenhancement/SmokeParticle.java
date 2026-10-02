package net.xylonity.knightquest.common.particle.explosiveenhancement;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xylonity.knightquest.common.api.explosiveenhancement.ExplosiveValues;

public class SmokeParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   public SmokeParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, double velX, double velY, double velZ) {
      super(world, x, y, z);
      this.f_172258_ = 0.6F;
      this.sprites = spriteProvider;
      this.f_107225_ = this.f_107223_.m_188503_(35);
      if (velZ == 0.0) {
         this.f_107663_ = (float)velX * 0.25F;
         this.f_107225_ = this.f_107225_ + (int)(velX * (double)this.f_107223_.m_216339_(3, 22));
         this.f_107215_ = 0.0;
         this.f_107217_ = 0.0;
      } else if (velX == 0.15 || velX == -0.15) {
         this.f_107663_ = (float)velZ * 0.25F;
         this.f_107225_ = this.f_107225_ + (int)(velZ * (double)this.f_107223_.m_216339_(3, 22));
         this.f_107215_ = velX * velZ * 0.5;
         this.f_107217_ = 0.0;
      } else if (velZ == 0.15 || velZ == -0.15) {
         this.f_107663_ = (float)velX * 0.25F;
         this.f_107225_ = this.f_107225_ + (int)(velX * (double)this.f_107223_.m_216339_(3, 22));
         this.f_107215_ = 0.0;
         this.f_107217_ = velZ * velX * 0.5;
      }

      this.f_107216_ = velY / 1.85;
      this.f_107226_ = 3.0E-6F;
      this.f_107219_ = true;
      this.m_108339_(spriteProvider);
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.m_108339_(this.sprites);
         if (this.f_107224_ == 12) {
            this.f_107215_ = 0.0;
            this.f_107216_ = 0.05;
            this.f_107217_ = 0.0;
         }

         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   protected int m_6355_(float tint) {
      if (ExplosiveValues.emissiveExplosion && (double)this.f_107224_ <= (double)this.f_107225_ * 0.12) {
         return 15728880;
      } else {
         return ExplosiveValues.emissiveExplosion && (double)this.f_107224_ <= (double)this.f_107225_ * 0.17
            ? Mth.m_14045_(super.m_6355_(tint) + this.f_107224_ + 30, super.m_6355_(tint), 15728880)
            : super.m_6355_(tint);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new SmokeParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
