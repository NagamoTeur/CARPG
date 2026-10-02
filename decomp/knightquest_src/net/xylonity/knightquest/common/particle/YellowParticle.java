package net.xylonity.knightquest.common.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class YellowParticle extends TextureSheetParticle {
   private final SpriteSet spritesset;
   private static Quaternion QUATERNION = new Quaternion(0.0F, -0.7F, 0.7F, 0.0F);

   YellowParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites, double velX, double velY, double velZ) {
      super(world, x, y + 0.5, z, 0.0, 0.0, 0.0);
      this.f_107663_ = 0.27F;
      this.f_107227_ = 1.0F;
      this.f_107228_ = 1.0F;
      this.f_107229_ = 1.0F;
      this.f_107225_ = (int)(11.0 + Math.floor(velX / 5.0));
      this.m_108339_(sprites);
      this.spritesset = sprites;
      this.m_172260_(0.0, 0.0, 0.0);
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107431_;
   }

   public void m_5744_(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
      super.m_5744_(pBuffer, pRenderInfo, pPartialTicks);
   }

   public void m_5989_() {
      super.m_5989_();
      this.m_108339_(this.spritesset);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new YellowParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
