package net.xylonity.knightquest.common.particle.explosiveenhancement;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xylonity.knightquest.common.api.explosiveenhancement.ExplosiveValues;
import net.xylonity.knightquest.registry.KnightQuestParticles;

public class FireballParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   boolean important;
   private double velX;
   private double velY;
   private double velZ;

   public FireballParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, double velX, double velY, double velZ) {
      super(world, x, y, z);
      this.sprites = spriteProvider;
      this.f_107225_ = (int)(9.0 + Math.floor(velX / 5.0));
      this.f_107663_ = (float)velX;
      this.important = velY == 1.0;
      this.m_172260_(0.0, 0.0, 0.0);
      this.m_108339_(spriteProvider);
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
         if ((double)this.f_107224_ >= (double)this.f_107225_ * 0.65 && ExplosiveValues.showSparks) {
            this.f_107208_
               .m_6493_(
                  (ParticleOptions)KnightQuestParticles.SPARKS.get(),
                  this.important,
                  this.f_107212_,
                  this.f_107213_,
                  this.f_107214_,
                  (double)this.f_107663_,
                  this.f_107216_,
                  this.f_107217_
               );
         }

         this.m_108339_(this.sprites);
      }
   }

   protected int m_6355_(float pPartialTick) {
      return ExplosiveValues.emissiveExplosion ? 15728880 : super.m_6355_(pPartialTick);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new FireballParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
