package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleVanillaCloudExtended;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityPoisonBall extends EntityMagicEffect {
   private static final byte EXPLOSION_PARTICLES_ID = 69;
   public static float GRAVITY = 0.05F;
   public double prevMotionX;
   public double prevMotionY;
   public double prevMotionZ;

   public EntityPoisonBall(EntityType<? extends EntityPoisonBall> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityPoisonBall(EntityType<? extends EntityPoisonBall> type, Level worldIn, LivingEntity caster) {
      super(type, worldIn, caster);
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      this.m_20334_(x * (double)velocity, y * (double)velocity, z * (double)velocity);
   }

   @Override
   public void m_8119_() {
      this.prevMotionX = this.m_20184_().f_82479_;
      this.prevMotionY = this.m_20184_().f_82480_;
      this.prevMotionZ = this.m_20184_().f_82481_;
      super.m_8119_();
      this.m_20256_(this.m_20184_().m_82492_(0.0, (double)GRAVITY, 0.0));
      this.m_6478_(MoverType.SELF, this.m_20184_());
      this.m_146922_(-((float)Mth.m_14136_(this.m_20184_().f_82479_, this.m_20184_().f_82481_)) * (180.0F / (float)Math.PI));
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(1.0);
      if (!entitiesHit.isEmpty()) {
         for (LivingEntity entity : entitiesHit) {
            if (entity != this.caster
               && !(entity instanceof EntityNaga)
               && entity.m_6469_(
                  DamageSource.m_19367_(this, this.caster), 3.0F * ((Double)ConfigHandler.COMMON.MOBS.NAGA.combatConfig.attackMultiplier.get()).floatValue()
               )) {
               entity.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 80, 1, false, true));
            }
         }
      }

      if (!this.f_19853_.m_45756_(this, this.m_20191_().m_82400_(0.1))) {
         this.explode();
      }

      if (this.f_19853_.f_46443_) {
         float scale = 1.0F;
         int steps = 4;
         double motionX = this.m_20184_().f_82479_;
         double motionY = this.m_20184_().f_82480_;
         double motionZ = this.m_20184_().f_82481_;

         for (int step = 0; step < steps; step++) {
            double x = this.f_19854_ + (double)step * (this.m_20185_() - this.f_19854_) / (double)steps;
            double y = this.f_19855_ + (double)step * (this.m_20186_() - this.f_19855_) / (double)steps + (double)(this.m_20206_() / 2.0F);
            double z = this.f_19856_ + (double)step * (this.m_20189_() - this.f_19856_) / (double)steps;

            for (int i = 0; i < 1; i++) {
               double xSpeed = (double)scale * 0.02 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
               double ySpeed = (double)scale * 0.02 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
               double zSpeed = (double)scale * 0.02 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
               double value = (double)(this.f_19796_.m_188501_() * 0.1F);
               double life = (double)(this.f_19796_.m_188501_() * 10.0F + 15.0F);
               ParticleVanillaCloudExtended.spawnVanillaCloud(
                  this.f_19853_,
                  x - motionX * 0.5,
                  y - motionY * 0.5,
                  z - motionZ * 0.5,
                  xSpeed,
                  ySpeed,
                  zSpeed,
                  (double)scale,
                  0.25 + value,
                  0.75 + value,
                  0.25 + value,
                  0.99,
                  life
               );
            }

            for (int i = 0; i < 2; i++) {
               double xSpeed = (double)scale * 0.06 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
               double ySpeed = (double)scale * 0.06 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
               double zSpeed = (double)scale * 0.06 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
               double value = (double)(this.f_19796_.m_188501_() * 0.1F);
               double life = (double)(this.f_19796_.m_188501_() * 5.0F + 10.0F);
               AdvancedParticleBase.spawnParticle(
                  this.f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
                  x + xSpeed - motionX * 0.5,
                  y + ySpeed - motionY * 0.5,
                  z + zSpeed - motionZ * 0.5,
                  xSpeed,
                  ySpeed,
                  zSpeed,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  (double)(scale * 3.0F),
                  0.07 + value,
                  0.25 + value,
                  0.07 + value,
                  1.0,
                  0.99,
                  life * 0.9,
                  false,
                  true
               );
            }

            for (int i = 0; i < 1; i++) {
               if (this.f_19796_.m_188501_() < 0.9F) {
                  double xSpeed = (double)scale * 0.06 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
                  double ySpeed = (double)scale * 0.06 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
                  double zSpeed = (double)scale * 0.06 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
                  double value = (double)(this.f_19796_.m_188501_() * 0.1F);
                  double life = (double)(this.f_19796_.m_188501_() * 5.0F + 10.0F);
                  AdvancedParticleBase.spawnParticle(
                     this.f_19853_,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.BUBBLE.get(),
                     x - motionX * 0.5,
                     y - motionY * 0.5,
                     z - motionZ * 0.5,
                     xSpeed,
                     ySpeed,
                     zSpeed,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     3.0,
                     0.25 + value,
                     0.75 + value,
                     0.25 + value,
                     1.0,
                     0.85,
                     life,
                     false,
                     true
                  );
               }
            }
         }
      }

      if (this.f_19797_ > 50) {
         this.m_146870_();
      }
   }

   private void explode() {
      this.f_19853_.m_7605_(this, (byte)69);
      this.m_5496_((SoundEvent)MMSounds.ENTITY_NAGA_ACID_HIT.get(), 1.0F, 1.0F);
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(2.0);
      if (!entitiesHit.isEmpty()) {
         for (LivingEntity entity : entitiesHit) {
            if (entity != this.caster
               && !(entity instanceof EntityNaga)
               && entity.m_6469_(
                  DamageSource.m_19367_(this, this.caster), 3.0F * ((Double)ConfigHandler.COMMON.MOBS.NAGA.combatConfig.attackMultiplier.get()).floatValue()
               )) {
               entity.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 80, 0, false, true));
            }
         }
      }

      this.m_146870_();
   }

   private void spawnExplosionParticles() {
      if (this.f_19853_.f_46443_) {
         float explodeSpeed = 3.5F;

         for (int i = 0; i < 26; i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.25, 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            double value = (double)(this.f_19796_.m_188501_() * 0.1F);
            double life = (double)(this.f_19796_.m_188501_() * 17.0F + 30.0F);
            ParticleVanillaCloudExtended.spawnVanillaCloud(
               this.f_19853_,
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               particlePos.f_82479_ * (double)explodeSpeed,
               particlePos.f_82480_ * (double)explodeSpeed,
               particlePos.f_82481_ * (double)explodeSpeed,
               1.0,
               0.25 + value,
               0.75 + value,
               0.25 + value,
               0.6,
               life
            );
         }

         for (int i = 0; i < 26; i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.25, 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            double value = (double)(this.f_19796_.m_188501_() * 0.1F);
            double life = (double)(this.f_19796_.m_188501_() * 5.0F + 10.0F);
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
               this.m_20185_() + particlePos.f_82479_,
               this.m_20186_() + particlePos.f_82480_,
               this.m_20189_() + particlePos.f_82481_,
               particlePos.f_82479_ * (double)explodeSpeed,
               particlePos.f_82480_ * (double)explodeSpeed,
               particlePos.f_82481_ * (double)explodeSpeed,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               3.0,
               0.07 + value,
               0.25 + value,
               0.07 + value,
               1.0,
               0.6,
               life * 0.95,
               false,
               true
            );
         }

         for (int i = 0; i < 23; i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.25, 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            double value = (double)(this.f_19796_.m_188501_() * 0.1F);
            double life = (double)(this.f_19796_.m_188501_() * 10.0F + 20.0F);
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.BUBBLE.get(),
               this.m_20185_() + particlePos.f_82479_,
               this.m_20186_() + particlePos.f_82480_,
               this.m_20189_() + particlePos.f_82481_,
               particlePos.f_82479_ * (double)explodeSpeed,
               particlePos.f_82480_ * (double)explodeSpeed,
               particlePos.f_82481_ * (double)explodeSpeed,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               3.0,
               0.25 + value,
               0.75 + value,
               0.25 + value,
               1.0,
               0.6,
               life * 0.95,
               false,
               true
            );
         }
      }
   }

   public void m_7822_(byte id) {
      if (id == 69) {
         this.spawnExplosionParticles();
      } else {
         super.m_7822_(id);
      }
   }
}
