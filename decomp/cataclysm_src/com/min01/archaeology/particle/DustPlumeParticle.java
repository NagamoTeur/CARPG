package com.min01.archaeology.particle;

import com.min01.archaeology.init.ArchaeologyParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

public class DustPlumeParticle extends BaseAshSmokeParticle {
   private static final int COLOR_RGB24 = 12235202;

   protected DustPlumeParticle(
      ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, float sizeMultiplier, SpriteSet spriteSet
   ) {
      super(level, x, y, z, 0.7F, 0.6F, 0.7F, xSpeed, ySpeed + 0.15F, zSpeed, sizeMultiplier, spriteSet, 0.5F, 7, 0.5F, false);
      float random = (float)Math.random() * 0.2F;
      this.f_107227_ = (float)ARGB32.m_13665_(12235202) / 255.0F - random;
      this.f_107228_ = (float)ARGB32.m_13667_(12235202) / 255.0F - random;
      this.f_107229_ = (float)ARGB32.m_13669_(12235202) / 255.0F - random;
   }

   public void m_5989_() {
      this.f_107226_ = 0.88F * this.f_107226_;
      this.f_172258_ = 0.92F * this.f_172258_;
      super.m_5989_();
   }

   @EventBusSubscriber(
      bus = Bus.MOD,
      value = {Dist.CLIENT}
   )
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Provider(SpriteSet spriteSet) {
         this.spriteSet = spriteSet;
      }

      @SubscribeEvent
      public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
         event.register((ParticleType)ArchaeologyParticleTypes.DUST_PLUME.get(), DustPlumeParticle.Provider::new);
      }

      public Particle createParticle(
         @NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed
      ) {
         return new DustPlumeParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, this.spriteSet);
      }
   }
}
