package net.xylonity.knightquest.common.particle.explosiveenhancement.blue;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xylonity.knightquest.common.particle.explosiveenhancement.FireballParticle;

public class BlueFireballParticle extends FireballParticle {
   boolean important;

   BlueFireballParticle(ClientLevel world, double x, double y, double z, SpriteSet spriteProvider, double velX, double velY, double velZ) {
      super(world, x, y, z, spriteProvider, velX, velY, velZ);
      this.f_107225_ = (int)(9.0 + Math.floor(velX / 5.0));
      this.f_107663_ = (float)velX;
      this.important = velY == 1.0;
      this.m_172260_(0.0, 0.0, 0.0);
      this.m_108339_(spriteProvider);
   }

   @Override
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
         return new BlueFireballParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
