package net.thirdlife.iterrpg.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElementalDropletParticle extends TextureSheetParticle {
   private final SpriteSet spriteSet;

   public static ElementalDropletParticle.ElementalDropletParticleProvider provider(SpriteSet spriteSet) {
      return new ElementalDropletParticle.ElementalDropletParticleProvider(spriteSet);
   }

   protected ElementalDropletParticle(ClientLevel world, double x, double y, double z, double vx, double vy, double vz, SpriteSet spriteSet) {
      super(world, x, y, z);
      this.spriteSet = spriteSet;
      this.m_107250_(0.2F, 0.2F);
      this.f_107663_ *= 1.5F;
      this.f_107225_ = Math.max(1, 20 + (this.f_107223_.m_188503_(10) - 5));
      this.f_107226_ = 1.0F;
      this.f_107219_ = true;
      this.f_107215_ = vx * 0.0;
      this.f_107216_ = vy * 0.0;
      this.f_107217_ = vz * 0.0;
      this.m_108335_(spriteSet);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   public void m_5989_() {
      super.m_5989_();
   }

   public static class ElementalDropletParticleProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public ElementalDropletParticleProvider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new ElementalDropletParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
      }
   }
}
