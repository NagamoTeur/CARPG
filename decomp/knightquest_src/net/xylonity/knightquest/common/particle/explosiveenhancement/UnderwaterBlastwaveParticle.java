package net.xylonity.knightquest.common.particle.explosiveenhancement;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.xylonity.knightquest.common.api.explosiveenhancement.ExplosiveValues;

public class UnderwaterBlastwaveParticle extends BlastWaveParticle {
   UnderwaterBlastwaveParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites, double velX, double velY, double velZ) {
      super(world, x, y, z, sprites, velX, velY, velZ);
   }

   @Override
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   @Override
   protected int m_6355_(float pPartialTick) {
      BlockPos blockPos = new BlockPos((int)this.f_107212_, (int)this.f_107213_, (int)this.f_107214_);
      return ExplosiveValues.emissiveWaterExplosion
         ? 15728880
         : (this.f_107208_.m_7232_(blockPos.m_123341_(), blockPos.m_123343_()) ? LevelRenderer.m_109541_(this.f_107208_, blockPos) : 0);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new UnderwaterBlastwaveParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
