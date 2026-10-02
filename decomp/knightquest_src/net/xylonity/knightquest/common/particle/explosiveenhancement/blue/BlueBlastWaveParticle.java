package net.xylonity.knightquest.common.particle.explosiveenhancement.blue;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xylonity.knightquest.common.particle.explosiveenhancement.BlastWaveParticle;

public class BlueBlastWaveParticle extends BlastWaveParticle {
   BlueBlastWaveParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites, double velX, double velY, double velZ) {
      super(world, x, y + 0.5, z, sprites, velX, 0.0, 0.0);
      this.f_107663_ = (float)velX;
      this.m_172260_(0.0, 0.0, 0.0);
      this.f_107225_ = (int)(15.0 + Math.floor(velX / 5.0));
      this.m_108339_(sprites);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new BlueBlastWaveParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
