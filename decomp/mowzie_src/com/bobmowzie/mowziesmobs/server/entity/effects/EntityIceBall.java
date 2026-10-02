package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRing;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import java.util.List;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityIceBall extends EntityMagicEffect {
   public EntityIceBall(Level world) {
      super((EntityType<? extends EntityMagicEffect>)EntityHandler.ICE_BALL.get(), world);
   }

   public EntityIceBall(EntityType<? extends EntityIceBall> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityIceBall(EntityType<? extends EntityIceBall> type, Level worldIn, LivingEntity caster) {
      super(type, worldIn, caster);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.m_6478_(MoverType.SELF, this.m_20184_());
      if (this.f_19797_ == 1 && this.f_19853_.f_46443_) {
         MowziesMobs.PROXY.playIceBreathSound(this);
      }

      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(2.0);
      if (!entitiesHit.isEmpty()) {
         for (LivingEntity entity : entitiesHit) {
            if (entity != this.caster
               && !entity.m_6095_().m_204039_(EntityTypeTags.f_144294_)
               && !(entity instanceof EnderDragon)
               && entity.m_6469_(DamageSource.f_146701_, 3.0F * ((Double)ConfigHandler.COMMON.MOBS.FROSTMAW.combatConfig.attackMultiplier.get()).floatValue())) {
               FrozenCapability.IFrozenCapability capability = CapabilityHandler.getCapability(entity, CapabilityHandler.FROZEN_CAPABILITY);
               if (capability != null) {
                  capability.addFreezeProgress(entity, 1.0F);
               }
            }
         }
      }

      if (!this.f_19853_.m_45756_(this, this.m_20191_().m_82400_(0.15))) {
         this.explode();
      }

      if (this.f_19853_.f_46443_) {
         float scale = 2.0F;
         double x = this.m_20185_();
         double y = this.m_20186_() + (double)(this.m_20206_() / 2.0F);
         double z = this.m_20189_();
         double motionX = this.m_20184_().f_82479_;
         double motionY = this.m_20184_().f_82480_;
         double motionZ = this.m_20184_().f_82481_;

         for (int i = 0; i < 4; i++) {
            double xSpeed = (double)scale * 0.01 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            double ySpeed = (double)scale * 0.01 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            double zSpeed = (double)scale * 0.01 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            float value = this.f_19796_.m_188501_() * 0.15F;
            this.f_19853_
               .m_7106_(
                  new ParticleCloud.CloudData(
                     (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                     0.75F + value,
                     0.75F + value,
                     1.0F,
                     scale * (10.0F + this.f_19796_.m_188501_() * 20.0F),
                     20,
                     ParticleCloud.EnumCloudBehavior.SHRINK,
                     1.0F
                  ),
                  x + xSpeed,
                  y + ySpeed,
                  z + zSpeed,
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }

         for (int i = 0; i < 1; i++) {
            double xSpeed = (double)scale * 0.01 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            double ySpeed = (double)scale * 0.01 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            double zSpeed = (double)scale * 0.01 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            this.f_19853_
               .m_7106_(
                  new ParticleCloud.CloudData(
                     (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                     1.0F,
                     1.0F,
                     1.0F,
                     scale * (5.0F + this.f_19796_.m_188501_() * 10.0F),
                     40,
                     ParticleCloud.EnumCloudBehavior.SHRINK,
                     1.0F
                  ),
                  x,
                  y,
                  z,
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }

         for (int i = 0; i < 5; i++) {
            double xSpeed = (double)scale * 0.05 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            double ySpeed = (double)scale * 0.05 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            double zSpeed = (double)scale * 0.05 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F);
            this.f_19853_
               .m_7106_(
                  new ParticleSnowFlake.SnowflakeData(40.0F, false),
                  x - 20.0 * xSpeed + motionX,
                  y - 20.0 * ySpeed + motionY,
                  z - 20.0 * zSpeed + motionZ,
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }

         float yaw = (float)Math.atan2(motionX, motionZ);
         float pitch = (float)(Math.acos(motionY / Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ)) + (Math.PI / 2));
         if (this.f_19797_ % 3 == 0) {
            this.f_19853_
               .m_7106_(
                  new ParticleRing.RingData(yaw, pitch, 40, 0.9F, 0.9F, 1.0F, 0.4F, scale * 16.0F, false, ParticleRing.EnumRingBehavior.GROW_THEN_SHRINK),
                  x + 1.5 * motionX,
                  y + 1.5 * motionY,
                  z + 1.5 * motionZ,
                  0.0,
                  0.0,
                  0.0
               );
         }

         if (this.f_19797_ == 1) {
            this.f_19853_
               .m_7106_(
                  new ParticleRing.RingData(yaw, pitch, 20, 0.9F, 0.9F, 1.0F, 0.4F, scale * 16.0F, false, ParticleRing.EnumRingBehavior.GROW),
                  x,
                  y,
                  z,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }

      if (this.f_19797_ > 50) {
         this.m_146870_();
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
   }

   public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
      this.m_20334_(x * (double)velocity, y * (double)velocity, z * (double)velocity);
   }

   private void explode() {
      if (this.f_19853_.f_46443_) {
         for (int i = 0; i < 8; i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.3, 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            float value = this.f_19796_.m_188501_() * 0.15F;
            this.f_19853_
               .m_7106_(
                  new ParticleCloud.CloudData(
                     (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                     0.75F + value,
                     0.75F + value,
                     1.0F,
                     10.0F + this.f_19796_.m_188501_() * 20.0F,
                     40,
                     ParticleCloud.EnumCloudBehavior.GROW,
                     1.0F
                  ),
                  this.m_20185_() + particlePos.f_82479_,
                  this.m_20186_() + particlePos.f_82480_,
                  this.m_20189_() + particlePos.f_82481_,
                  particlePos.f_82479_,
                  particlePos.f_82480_,
                  particlePos.f_82481_
               );
         }

         for (int i = 0; i < 10; i++) {
            Vec3 particlePos = new Vec3((double)this.f_19796_.m_188501_() * 0.3, 0.0, 0.0);
            particlePos = particlePos.m_82524_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            particlePos = particlePos.m_82496_((float)((double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI));
            this.f_19853_
               .m_7106_(
                  new ParticleSnowFlake.SnowflakeData(40.0F, false),
                  this.m_20185_() + particlePos.f_82479_,
                  this.m_20186_() + particlePos.f_82480_,
                  this.m_20189_() + particlePos.f_82481_,
                  particlePos.f_82479_,
                  particlePos.f_82480_,
                  particlePos.f_82481_
               );
         }
      }

      this.m_146870_();
   }
}
