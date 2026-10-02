package net.xylonity.knightquest.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class PoisonParticle extends TextureSheetParticle {
   private final SpriteSet sprites;

   protected PoisonParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
      super(pLevel, pX, pY, pZ);
      this.f_172258_ = 1.0F;
      this.sprites = pSprites;
      this.f_107215_ = pXSpeed + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.f_107216_ = pYSpeed + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.f_107217_ = pZSpeed + (Math.random() * 2.0 - 1.0) * 0.05F;
      float minSize = 1.5F;
      float maxSize = 3.0F;
      this.f_107663_ = minSize + this.f_107223_.m_188501_() * (maxSize - minSize);
      this.f_107225_ = (int)(10.0 / ((double)this.f_107223_.m_188501_() * 0.5 + 0.2)) + 2;
      this.m_108339_(pSprites);
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.sprites);
      this.f_107215_ *= 0.95F;
      this.f_107216_ *= 0.9F;
      this.f_107217_ *= 0.95F;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet pSprites) {
         this.sprites = pSprites;
      }

      public Particle createParticle(
         SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed
      ) {
         PoisonParticle snowflakeparticle = new PoisonParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
         snowflakeparticle.m_107253_(0.923F, 0.964F, 0.999F);
         return snowflakeparticle;
      }
   }
}
