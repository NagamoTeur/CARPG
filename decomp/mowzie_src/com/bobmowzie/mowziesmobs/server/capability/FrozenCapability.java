package com.bobmowzie.mowziesmobs.server.capability;

import com.bobmowzie.mowziesmobs.client.particle.ParticleCloud;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleSnowFlake;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class FrozenCapability {
   public static ResourceLocation ID = new ResourceLocation("mowziesmobs", "frozen_cap");
   public static int MAX_FREEZE_DECAY_DELAY = 10;

   public static class FrozenCapabilityImp implements FrozenCapability.IFrozenCapability {
      public boolean frozen;
      public float freezeProgress = 0.0F;
      public float frozenYaw;
      public float frozenPitch;
      public float frozenYawHead;
      public float frozenRenderYawOffset;
      public float frozenSwingProgress;
      public float frozenLimbSwingAmount;
      public boolean prevHasAI = true;
      public UUID prevAttackTarget;
      public int freezeDecayDelay;
      public boolean prevFrozen = false;
      public EntityFrozenController frozenController;

      @Override
      public boolean getFrozen() {
         return this.frozen;
      }

      @Override
      public float getFreezeProgress() {
         return this.freezeProgress;
      }

      @Override
      public void setFreezeProgress(float freezeProgress) {
         this.freezeProgress = freezeProgress;
      }

      @Override
      public float getFrozenYaw() {
         return this.frozenYaw;
      }

      @Override
      public void setFrozenYaw(float frozenYaw) {
         this.frozenYaw = frozenYaw;
      }

      @Override
      public float getFrozenPitch() {
         return this.frozenPitch;
      }

      @Override
      public void setFrozenPitch(float frozenPitch) {
         this.frozenPitch = frozenPitch;
      }

      @Override
      public float getFrozenYawHead() {
         return this.frozenYawHead;
      }

      @Override
      public void setFrozenYawHead(float frozenYawHead) {
         this.frozenYawHead = frozenYawHead;
      }

      @Override
      public float getFrozenRenderYawOffset() {
         return this.frozenRenderYawOffset;
      }

      @Override
      public void setFrozenRenderYawOffset(float frozenRenderYawOffset) {
         this.frozenRenderYawOffset = frozenRenderYawOffset;
      }

      @Override
      public float getFrozenSwingProgress() {
         return this.frozenSwingProgress;
      }

      @Override
      public void setFrozenSwingProgress(float frozenSwingProgress) {
         this.frozenSwingProgress = frozenSwingProgress;
      }

      @Override
      public float getFrozenLimbSwingAmount() {
         return this.frozenLimbSwingAmount;
      }

      @Override
      public void setFrozenLimbSwingAmount(float frozenLimbSwingAmount) {
         this.frozenLimbSwingAmount = frozenLimbSwingAmount;
      }

      @Override
      public boolean prevHasAI() {
         return this.prevHasAI;
      }

      @Override
      public void setPrevHasAI(boolean prevHasAI) {
         this.prevHasAI = prevHasAI;
      }

      @Override
      public int getFreezeDecayDelay() {
         return this.freezeDecayDelay;
      }

      @Override
      public void setFreezeDecayDelay(int freezeDecayDelay) {
         this.freezeDecayDelay = freezeDecayDelay;
      }

      @Override
      public boolean getPrevFrozen() {
         return this.prevFrozen;
      }

      @Override
      public void setPrevFrozen(boolean prevFrozen) {
         this.prevFrozen = prevFrozen;
      }

      @Override
      public UUID getPreAttackTarget() {
         return this.prevAttackTarget;
      }

      @Override
      public void setPreAttackTarget(UUID livingEntity) {
         this.prevAttackTarget = livingEntity;
      }

      @Override
      public EntityFrozenController getFrozenController() {
         return this.frozenController;
      }

      @Override
      public void setFrozenController(EntityFrozenController frozenController) {
         this.frozenController = frozenController;
      }

      @Override
      public void addFreezeProgress(LivingEntity entity, float amount) {
         if (!entity.f_19853_.f_46443_ && !entity.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
            this.freezeProgress += amount;
            this.freezeDecayDelay = FrozenCapability.MAX_FREEZE_DECAY_DELAY;
         }
      }

      @Override
      public void onFreeze(LivingEntity entity) {
         if (entity != null) {
            this.frozen = true;
            this.frozenController = new EntityFrozenController(
               (EntityType<? extends EntityFrozenController>)EntityHandler.FROZEN_CONTROLLER.get(), entity.f_19853_
            );
            this.frozenController.m_19890_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_());
            entity.f_19853_.m_7967_(this.frozenController);
            this.frozenController.m_5618_(entity.f_20883_);
            this.frozenYaw = entity.m_146908_();
            this.frozenPitch = entity.m_146909_();
            this.frozenYawHead = entity.f_20885_;
            this.frozenLimbSwingAmount = 0.0F;
            this.frozenRenderYawOffset = entity.f_20883_;
            this.frozenSwingProgress = entity.f_20921_;
            entity.m_7998_(this.frozenController, true);
            entity.m_5810_();
            if (entity instanceof Mob mobEntity) {
               if (mobEntity.m_5448_() != null) {
                  this.setPreAttackTarget(mobEntity.m_5448_().m_20148_());
               }

               this.prevHasAI = !((Mob)entity).m_21525_();
               mobEntity.m_21557_(true);
            }

            if (entity.f_19853_.f_46443_) {
               int particleCount = (int)(10.0F + 1.0F * entity.m_20206_() * entity.m_20205_() * entity.m_20205_());

               for (int i = 0; i < particleCount; i++) {
                  double snowX = entity.m_20185_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
                  double snowZ = entity.m_20189_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
                  double snowY = entity.m_20186_() + (double)(entity.m_20206_() * entity.m_217043_().m_188501_());
                  Vec3 motion = new Vec3(snowX - entity.m_20185_(), snowY - (entity.m_20186_() + (double)(entity.m_20206_() / 2.0F)), snowZ - entity.m_20189_())
                     .m_82541_();
                  entity.f_19853_
                     .m_7106_(
                        new ParticleSnowFlake.SnowflakeData(40.0F, false),
                        snowX,
                        snowY,
                        snowZ,
                        0.1 * motion.f_82479_,
                        0.1 * motion.f_82480_,
                        0.1 * motion.f_82481_
                     );
               }
            }

            entity.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_FROZEN_CRASH.get(), 1.0F, 1.0F);
         }
      }

      @Override
      public void onUnfreeze(LivingEntity entity) {
         if (entity != null) {
            this.freezeProgress = 0.0F;
            if (this.frozen) {
               entity.m_6234_((MobEffect)EffectHandler.FROZEN.get());
               this.frozen = false;
               if (this.frozenController != null) {
                  Vec3 oldPosition = entity.m_20182_();
                  entity.m_8127_();
                  entity.m_6021_(oldPosition.m_7096_(), oldPosition.m_7098_(), oldPosition.m_7094_());
                  this.frozenController.m_146870_();
               }

               entity.m_5496_((SoundEvent)MMSounds.ENTITY_FROSTMAW_FROZEN_CRASH.get(), 1.0F, 0.5F);
               if (entity.f_19853_.f_46443_) {
                  int particleCount = (int)(10.0F + 1.0F * entity.m_20206_() * entity.m_20205_() * entity.m_20205_());

                  for (int i = 0; i < particleCount; i++) {
                     double particleX = entity.m_20185_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
                     double particleZ = entity.m_20189_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
                     double particleY = entity.m_20186_() + (double)(entity.m_20206_() * entity.m_217043_().m_188501_()) + 0.3F;
                     entity.f_19853_
                        .m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_50126_.m_49966_()), particleX, particleY, particleZ, 0.0, 0.0, 0.0);
                  }
               }

               if (entity instanceof Mob) {
                  if (((Mob)entity).m_21525_() && this.prevHasAI) {
                     ((Mob)entity).m_21557_(false);
                  }

                  if (this.getPreAttackTarget() != null) {
                     Player target = entity.f_19853_.m_46003_(this.getPreAttackTarget());
                     if (target != null) {
                        ((Mob)entity).m_6710_(target);
                     }
                  }
               }
            }
         }
      }

      @Override
      public void tick(LivingEntity entity) {
         if (this.getFreezeProgress() >= 1.0F && !entity.m_21023_((MobEffect)EffectHandler.FROZEN.get())) {
            entity.m_7292_(new MobEffectInstance((MobEffect)EffectHandler.FROZEN.get(), 50, 0, false, false));
            this.freezeProgress = 1.0F;
         } else if (this.freezeProgress > 0.0F) {
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 9, Mth.m_14143_(this.freezeProgress * 5.0F + 1.0F), false, false));
         }

         if (this.frozenController == null) {
            Entity riding = entity.m_20202_();
            if (riding instanceof EntityFrozenController) {
               this.frozenController = (EntityFrozenController)riding;
            }
         }

         if (this.frozen) {
            entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 2, 50, false, false));
            entity.m_20260_(false);
            if (entity.f_19853_.f_46443_ && entity.f_19797_ % 2 == 0) {
               double cloudX = entity.m_20185_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
               double cloudZ = entity.m_20189_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
               double cloudY = entity.m_20186_() + (double)(entity.m_20206_() * entity.m_217043_().m_188501_());
               entity.f_19853_
                  .m_7106_(
                     new ParticleCloud.CloudData(
                        (ParticleType<ParticleCloud.CloudData>)ParticleHandler.CLOUD.get(),
                        0.75F,
                        0.75F,
                        1.0F,
                        15.0F,
                        25,
                        ParticleCloud.EnumCloudBehavior.CONSTANT,
                        1.0F
                     ),
                     cloudX,
                     cloudY,
                     cloudZ,
                     0.0,
                     -0.01F,
                     0.0
                  );
               double snowX = entity.m_20185_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
               double snowZ = entity.m_20189_() + (double)(entity.m_20205_() * entity.m_217043_().m_188501_()) - (double)(entity.m_20205_() / 2.0F);
               double snowY = entity.m_20186_() + (double)(entity.m_20206_() * entity.m_217043_().m_188501_());
               entity.f_19853_.m_7106_(new ParticleSnowFlake.SnowflakeData(40.0F, false), snowX, snowY, snowZ, 0.0, -0.01, 0.0);
            }
         } else if (!entity.f_19853_.f_46443_ && this.getPrevFrozen()) {
            this.onUnfreeze(entity);
         }

         if (this.freezeDecayDelay <= 0) {
            this.freezeProgress = (float)((double)this.freezeProgress - 0.1);
            if (this.freezeProgress < 0.0F) {
               this.freezeProgress = 0.0F;
            }
         } else {
            this.freezeDecayDelay--;
         }

         this.prevFrozen = entity.m_21023_((MobEffect)EffectHandler.FROZEN.get());
      }

      public CompoundTag serializeNBT() {
         CompoundTag compound = new CompoundTag();
         compound.m_128350_("freezeProgress", this.getFreezeProgress());
         compound.m_128405_("freezeDecayDelay", this.getFreezeDecayDelay());
         compound.m_128350_("frozenLimbSwingAmount", this.getFrozenLimbSwingAmount());
         compound.m_128350_("frozenRenderYawOffset", this.getFrozenRenderYawOffset());
         compound.m_128350_("frozenSwingProgress", this.getFrozenSwingProgress());
         compound.m_128350_("frozenPitch", this.getFrozenPitch());
         compound.m_128350_("frozenYaw", this.getFrozenYaw());
         compound.m_128350_("frozenYawHead", this.getFrozenYawHead());
         compound.m_128379_("prevHasAI", this.prevHasAI());
         if (this.getPreAttackTarget() != null) {
            compound.m_128362_("prevAttackTarget", this.getPreAttackTarget());
         }

         compound.m_128379_("frozen", this.frozen);
         compound.m_128379_("prevFrozen", this.prevFrozen);
         return compound;
      }

      public void deserializeNBT(CompoundTag compound) {
         this.setFreezeProgress(compound.m_128457_("freezeProgress"));
         this.setFreezeDecayDelay(compound.m_128451_("freezeDecayDelay"));
         this.setFrozenLimbSwingAmount(compound.m_128457_("frozenLimbSwingAmount"));
         this.setFrozenRenderYawOffset(compound.m_128457_("frozenRenderYawOffset"));
         this.setFrozenSwingProgress(compound.m_128457_("frozenSwingProgress"));
         this.setFrozenPitch(compound.m_128457_("frozenPitch"));
         this.setFrozenYaw(compound.m_128457_("frozenYaw"));
         this.setFrozenYawHead(compound.m_128457_("frozenYawHead"));
         this.setPrevHasAI(compound.m_128471_("prevHasAI"));

         try {
            this.setPreAttackTarget(compound.m_128342_("prevAttackTarget"));
         } catch (NullPointerException var3) {
         }

         this.frozen = compound.m_128471_("frozen");
         this.prevFrozen = compound.m_128471_("prevFrozen");
      }
   }

   public static class FrozenProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
      private final LazyOptional<FrozenCapability.IFrozenCapability> instance = LazyOptional.of(FrozenCapability.FrozenCapabilityImp::new);

      public CompoundTag serializeNBT() {
         return (CompoundTag)((FrozenCapability.IFrozenCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
      }

      public void deserializeNBT(CompoundTag nbt) {
         ((FrozenCapability.IFrozenCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
      }

      @Nonnull
      public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
         return CapabilityHandler.FROZEN_CAPABILITY.orEmpty(cap, this.instance.cast());
      }
   }

   public interface IFrozenCapability extends INBTSerializable<CompoundTag> {
      boolean getFrozen();

      float getFreezeProgress();

      void setFreezeProgress(float var1);

      float getFrozenYaw();

      void setFrozenYaw(float var1);

      float getFrozenPitch();

      void setFrozenPitch(float var1);

      float getFrozenYawHead();

      void setFrozenYawHead(float var1);

      float getFrozenRenderYawOffset();

      void setFrozenRenderYawOffset(float var1);

      float getFrozenSwingProgress();

      void setFrozenSwingProgress(float var1);

      float getFrozenLimbSwingAmount();

      void setFrozenLimbSwingAmount(float var1);

      boolean prevHasAI();

      void setPrevHasAI(boolean var1);

      int getFreezeDecayDelay();

      void setFreezeDecayDelay(int var1);

      boolean getPrevFrozen();

      void setPrevFrozen(boolean var1);

      UUID getPreAttackTarget();

      void setPreAttackTarget(UUID var1);

      EntityFrozenController getFrozenController();

      void setFrozenController(EntityFrozenController var1);

      void addFreezeProgress(LivingEntity var1, float var2);

      void onFreeze(LivingEntity var1);

      void onUnfreeze(LivingEntity var1);

      void tick(LivingEntity var1);
   }
}
