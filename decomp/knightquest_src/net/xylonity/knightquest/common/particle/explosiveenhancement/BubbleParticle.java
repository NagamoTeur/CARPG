package net.xylonity.knightquest.common.particle.explosiveenhancement;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BubbleParticle extends TextureSheetParticle {
   private final SpriteSet sprites;
   int startingAirTick = 0;
   int extraTimeBeforePopping = this.f_107223_.m_216332_(1, 10);
   boolean startAirTick = true;

   BubbleParticle(ClientLevel clientWorld, double x, double y, double z, SpriteSet spriteProvider, double velX, double velY, double velZ) {
      super(clientWorld, x, y, z);
      this.sprites = spriteProvider;
      this.m_107250_(0.02F, 0.02F);
      this.f_107663_ = this.f_107663_ * (this.f_107223_.m_188501_() * 1.5F + 0.2F);
      double theta = this.f_107223_.m_188500_() * 2.0 * Math.PI;
      double phi = this.f_107223_.m_188500_() * Math.PI;
      this.f_107215_ = Math.sin(phi) * Math.cos(theta) * (this.f_107223_.m_188500_() * 0.5 + 0.5);
      this.f_107216_ = Math.abs(this.f_107223_.m_188500_() * 0.5 + 0.5);
      this.f_107217_ = Math.sin(phi) * Math.sin(theta) * (this.f_107223_.m_188500_() * 0.5 + 0.5);
      this.f_107225_ = 120 + this.f_107223_.m_216332_(0, 40);
      this.m_108339_(spriteProvider);
      this.f_107224_ = this.f_107225_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107225_-- <= 0) {
         this.m_107274_();
         this.f_107208_.m_7106_(ParticleTypes.f_123772_, this.f_107212_, this.f_107213_, this.f_107214_, this.f_107215_, this.f_107216_, this.f_107217_);
      } else {
         this.f_107216_ += 0.002;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         this.f_107216_ *= 0.8200000238418579;
         if ((double)this.f_107225_ >= (double)this.f_107224_ * 0.97) {
            this.f_107215_ *= 0.8300000238418579;
            this.f_107217_ *= 0.8300000238418579;
         } else {
            this.f_107215_ *= 0.6200000238418579;
            this.f_107217_ *= 0.6200000238418579;
         }

         if (!this.f_107208_.m_6425_(new BlockPos((int)this.f_107212_, (int)this.f_107213_, (int)this.f_107214_)).m_205070_(FluidTags.f_13131_)) {
            this.f_107216_ -= 0.002;
            if (this.startAirTick) {
               this.startingAirTick = this.f_107225_;
               this.f_107216_ = 0.0;
               this.startAirTick = false;
            }

            if (!this.startAirTick && this.f_107225_ == this.startingAirTick - this.extraTimeBeforePopping) {
               this.m_107274_();
               this.f_107208_.m_7106_(ParticleTypes.f_123772_, this.f_107212_, this.f_107213_, this.f_107214_, this.f_107215_, this.f_107216_, this.f_107217_);
               this.f_107208_.m_6263_(null, this.f_107212_, this.f_107213_, this.f_107214_, SoundEvents.f_11773_, SoundSource.AMBIENT, 0.5F, 1.0F);
            }
         }
      }
   }

   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107430_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprites;

      public Provider(SpriteSet spriteSet) {
         this.sprites = spriteSet;
      }

      public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
         return new BubbleParticle(level, x, y, z, this.sprites, dx, dy, dz);
      }
   }
}
