package com.bobmowzie.mowziesmobs.client.particle;

import com.bobmowzie.mowziesmobs.client.render.MMRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class ParticleSparkle extends TextureSheetParticle {
   private final float red;
   private final float green;
   private final float blue;
   private final float scale;

   public ParticleSparkle(
      ClientLevel world, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, double scale, int duration
   ) {
      super(world, x, y, z);
      this.scale = (float)scale * 1.0F;
      this.f_107225_ = duration;
      this.f_107215_ = vx;
      this.f_107216_ = vy;
      this.f_107217_ = vz;
      this.red = (float)r;
      this.green = (float)g;
      this.blue = (float)b;
      this.f_107219_ = false;
   }

   protected float m_5952_() {
      return super.m_5952_() - (super.m_5952_() - super.m_5970_()) / 16.0F;
   }

   protected float m_5950_() {
      return super.m_5950_() - (super.m_5950_() - super.m_5951_()) / 16.0F;
   }

   public void m_5744_(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float a = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_;
      this.f_107230_ = -4.0F * a * a + 4.0F * a;
      if ((double)this.f_107230_ < 0.01) {
         this.f_107230_ = 0.01F;
      }

      this.f_107663_ = (-4.0F * a * a + 4.0F * a) * this.scale;
      super.m_5744_(buffer, renderInfo, partialTicks);
   }

   public ParticleRenderType m_7556_() {
      return MMRenderType.PARTICLE_SHEET_TRANSLUCENT_NO_DEPTH;
   }

   @OnlyIn(Dist.CLIENT)
   public static final class SparkleFactory implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public SparkleFactory(SpriteSet sprite) {
         this.spriteSet = sprite;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ParticleSparkle particle = new ParticleSparkle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 1.0, 1.0, 1.0, 0.4, 13);
         particle.m_108335_(this.spriteSet);
         return particle;
      }
   }
}
