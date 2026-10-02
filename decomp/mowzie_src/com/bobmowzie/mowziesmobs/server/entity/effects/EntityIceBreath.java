package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRing;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.capability.CapabilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class EntityIceBreath extends EntityMagicEffect {
   private static final int RANGE = 10;
   private static final int ARC = 45;
   private static final int DAMAGE_PER_HIT = 1;

   public EntityIceBreath(EntityType<? extends EntityIceBreath> type, Level world) {
      super(type, world);
   }

   public EntityIceBreath(EntityType<? extends EntityIceBreath> type, Level world, LivingEntity caster) {
      super(type, world, caster);
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ == 1 && this.f_19853_.f_46443_) {
         MowziesMobs.PROXY.playIceBreathSound(this);
      }

      if (this.caster == null) {
         this.m_146870_();
      }

      if (this.caster != null && !this.caster.m_6084_()) {
         this.m_146870_();
      }

      if (this.f_19797_ == 1) {
         this.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_ICEBREATH_START.get(), 1.0F, 0.6F);
      }

      if (this.caster instanceof Player player) {
         this.m_19890_(
            player.m_20185_(),
            player.m_20186_() + (double)player.m_6431_(player.m_20089_(), player.m_6972_(player.m_20089_())) - 0.5,
            player.m_20189_(),
            player.m_146908_(),
            player.m_146909_()
         );
      }

      float yaw = (float)Math.toRadians((double)(-this.m_146908_()));
      float pitch = (float)Math.toRadians((double)(-this.m_146909_()));
      float spread = 0.25F;
      float speed = 0.56F;
      float xComp = (float)(Math.sin((double)yaw) * Math.cos((double)pitch));
      float yComp = (float)Math.sin((double)pitch);
      float zComp = (float)(Math.cos((double)yaw) * Math.cos((double)pitch));
      if (this.f_19853_.f_46443_) {
         if (this.f_19797_ % 8 == 0) {
            this.f_19853_
               .m_7106_(
                  new ParticleRing.RingData(yaw, -pitch, 40, 1.0F, 1.0F, 1.0F, 1.0F, 110.0F * spread, false, ParticleRing.EnumRingBehavior.GROW),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (double)(0.5F * xComp),
                  (double)(0.5F * yComp),
                  (double)(0.5F * zComp)
               );
         }

         for (int i = 0; i < 6; i++) {
            double xSpeed = (double)(speed * 1.0F * xComp);
            double ySpeed = (double)(speed * 1.0F * yComp);
            double zSpeed = (double)(speed * 1.0F * zComp);
            this.f_19853_.m_7106_(new ParticleSnowFlake.SnowflakeData(37.0F, true), this.m_20185_(), this.m_20186_(), this.m_20189_(), xSpeed, ySpeed, zSpeed);
         }

         for (int i = 0; i < 5; i++) {
            double xSpeed = (double)(speed * xComp)
               + (double)spread * 0.7 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F) * Math.sqrt((double)(1.0F - xComp * xComp));
            double ySpeed = (double)(speed * yComp)
               + (double)spread * 0.7 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F) * Math.sqrt((double)(1.0F - yComp * yComp));
            double zSpeed = (double)(speed * zComp)
               + (double)spread * 0.7 * (double)(this.f_19796_.m_188501_() * 2.0F - 1.0F) * Math.sqrt((double)(1.0F - zComp * zComp));
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
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  xSpeed,
                  ySpeed,
                  zSpeed
               );
         }
      }

      if (this.f_19797_ > 10) {
         this.hitEntities();
      }

      if (this.f_19797_ > 10) {
         this.freezeBlocks();
      }

      if (this.f_19797_ > 65 && !(this.caster instanceof Player)) {
         this.m_146870_();
      }
   }

   public void hitEntities() {
      List<LivingEntity> entitiesHit = this.getEntityLivingBaseNearby(10.0, 10.0, 10.0, 10.0);
      float damage = 1.0F;
      if (this.caster instanceof EntityFrostmaw) {
         damage = (float)((double)damage * (Double)ConfigHandler.COMMON.MOBS.FROSTMAW.combatConfig.attackMultiplier.get());
      }

      if (this.caster instanceof Player) {
         damage = (float)((double)damage * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.attackMultiplier.get());
      }

      for (LivingEntity entityHit : entitiesHit) {
         if (entityHit != this.caster && !entityHit.m_6095_().m_204039_(EntityTypeTags.f_144294_) && !(entityHit instanceof EnderDragon)) {
            float entityHitYaw = (float)(
               (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
            );
            float entityAttackingYaw = this.m_146908_() % 360.0F;
            if (entityHitYaw < 0.0F) {
               entityHitYaw += 360.0F;
            }

            if (entityAttackingYaw < 0.0F) {
               entityAttackingYaw += 360.0F;
            }

            float entityRelativeYaw = entityHitYaw - entityAttackingYaw;
            float xzDistance = (float)Math.sqrt(
               (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
                  + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
            );
            double hitY = entityHit.m_20186_() + (double)entityHit.m_20206_() / 2.0;
            float entityHitPitch = (float)(Math.atan2(hitY - this.m_20186_(), (double)xzDistance) * (180.0 / Math.PI) % 360.0);
            float entityAttackingPitch = -this.m_146909_() % 360.0F;
            if (entityHitPitch < 0.0F) {
               entityHitPitch += 360.0F;
            }

            if (entityAttackingPitch < 0.0F) {
               entityAttackingPitch += 360.0F;
            }

            float entityRelativePitch = entityHitPitch - entityAttackingPitch;
            float entityHitDistance = (float)Math.sqrt(
               (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
                  + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
                  + (hitY - this.m_20186_()) * (hitY - this.m_20186_())
            );
            boolean inRange = entityHitDistance <= 10.0F;
            boolean yawCheck = entityRelativeYaw <= 22.5F && entityRelativeYaw >= -22.5F || entityRelativeYaw >= 337.5F || entityRelativeYaw <= -337.5F;
            boolean pitchCheck = entityRelativePitch <= 22.5F && entityRelativePitch >= -22.5F
               || entityRelativePitch >= 337.5F
               || entityRelativePitch <= -337.5F;
            boolean frostmawCloseCheck = this.caster instanceof EntityFrostmaw && entityHitDistance <= 2.0F;
            if ((inRange && yawCheck && pitchCheck || frostmawCloseCheck)
               && this.raytraceCheckEntity(entityHit)
               && entityHit.m_6469_(DamageSource.f_146701_, damage)) {
               entityHit.m_20256_(entityHit.m_20184_().m_82542_(0.25, 1.0, 0.25));
               FrozenCapability.IFrozenCapability capability = CapabilityHandler.getCapability(entityHit, CapabilityHandler.FROZEN_CAPABILITY);
               if (capability != null) {
                  capability.addFreezeProgress(entityHit, 0.23F);
               }
            }
         }
      }
   }

   public void freezeBlocks() {
      int checkDist = 10;

      for (int i = (int)this.m_20185_() - checkDist; i < (int)this.m_20185_() + checkDist; i++) {
         for (int j = (int)this.m_20186_() - checkDist; j < (int)this.m_20186_() + checkDist; j++) {
            for (int k = (int)this.m_20189_() - checkDist; k < (int)this.m_20189_() + checkDist; k++) {
               BlockPos pos = new BlockPos(i, j, k);
               BlockState blockState = this.f_19853_.m_8055_(pos);
               BlockState blockStateAbove = this.f_19853_.m_8055_(pos.m_7494_());
               if (blockState.m_60734_() == Blocks.f_49990_ && blockStateAbove.m_60734_() == Blocks.f_50016_) {
                  float blockHitYaw = (float)(
                     (Math.atan2((double)pos.m_123343_() - this.m_20189_(), (double)pos.m_123341_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
                  );
                  float entityAttackingYaw = this.m_146908_() % 360.0F;
                  if (blockHitYaw < 0.0F) {
                     blockHitYaw += 360.0F;
                  }

                  if (entityAttackingYaw < 0.0F) {
                     entityAttackingYaw += 360.0F;
                  }

                  float blockRelativeYaw = blockHitYaw - entityAttackingYaw;
                  float xzDistance = (float)Math.sqrt(
                     ((double)pos.m_123343_() - this.m_20189_()) * ((double)pos.m_123343_() - this.m_20189_())
                        + ((double)pos.m_123341_() - this.m_20185_()) * ((double)pos.m_123341_() - this.m_20185_())
                  );
                  float blockHitPitch = (float)(Math.atan2((double)pos.m_123342_() - this.m_20186_(), (double)xzDistance) * (180.0 / Math.PI) % 360.0);
                  float entityAttackingPitch = -this.m_146909_() % 360.0F;
                  if (blockHitPitch < 0.0F) {
                     blockHitPitch += 360.0F;
                  }

                  if (entityAttackingPitch < 0.0F) {
                     entityAttackingPitch += 360.0F;
                  }

                  float blockRelativePitch = blockHitPitch - entityAttackingPitch;
                  float blockHitDistance = (float)Math.sqrt(
                     ((double)pos.m_123343_() - this.m_20189_()) * ((double)pos.m_123343_() - this.m_20189_())
                        + ((double)pos.m_123341_() - this.m_20185_()) * ((double)pos.m_123341_() - this.m_20185_())
                        + ((double)pos.m_123342_() - this.m_20186_()) * ((double)pos.m_123342_() - this.m_20186_())
                  );
                  boolean inRange = blockHitDistance <= 10.0F;
                  boolean yawCheck = blockRelativeYaw <= 22.5F && blockRelativeYaw >= -22.5F || blockRelativeYaw >= 337.5F || blockRelativeYaw <= -337.5F;
                  boolean pitchCheck = blockRelativePitch <= 22.5F && blockRelativePitch >= -22.5F
                     || blockRelativePitch >= 337.5F
                     || blockRelativePitch <= -337.5F;
                  if (inRange && yawCheck && pitchCheck) {
                     EntityBlockSwapper.swapBlock(this.f_19853_, pos, Blocks.f_50126_.m_49966_(), 140, false, false);
                  }
               }
            }
         }
      }
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.f_19853_
         .m_6443_(
            entityClass,
            this.m_20191_().m_82377_(dX, dY, dZ),
            e -> e != this && (double)this.m_20270_(e) <= r + (double)(e.m_20205_() / 2.0F) && e.m_20186_() <= this.m_20186_() + dY
         );
   }
}
