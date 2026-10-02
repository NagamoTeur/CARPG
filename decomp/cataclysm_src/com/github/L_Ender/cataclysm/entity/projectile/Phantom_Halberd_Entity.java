package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Phantom_Halberd_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   public int lifeTicks;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.m_135353_(Phantom_Halberd_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Phantom_Halberd_Entity.class, EntityDataSerializers.f_135029_);
   public AnimationState OneAnimationState = new AnimationState();
   public AnimationState TwospawnAnimationState = new AnimationState();
   public AnimationState ThreespawnAnimationState = new AnimationState();
   public AnimationState FourspawnAnimationState = new AnimationState();

   public Phantom_Halberd_Entity(EntityType<? extends Phantom_Halberd_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public Phantom_Halberd_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, LivingEntity casterIn, float damage) {
      this((EntityType<? extends Phantom_Halberd_Entity>)ModEntities.PHANTOM_HALBERD.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.setDamage(damage);
      this.m_6034_(x, y, z);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(STATE, 0);
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
   }

   public AnimationState getAnimationState(String input) {
      if (input == "one") {
         return this.OneAnimationState;
      } else if (input == "two") {
         return this.TwospawnAnimationState;
      } else if (input == "three") {
         return this.ThreespawnAnimationState;
      } else {
         return input == "four" ? this.FourspawnAnimationState : new AnimationState();
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      if (STATE.equals(p_21104_) && this.f_19853_.f_46443_) {
         switch (this.getState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.OneAnimationState.m_216982_(this.f_19797_);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.TwospawnAnimationState.m_216982_(this.f_19797_);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.ThreespawnAnimationState.m_216982_(this.f_19797_);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.FourspawnAnimationState.m_216982_(this.f_19797_);
         }
      }

      super.m_7350_(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.OneAnimationState.m_216973_();
      this.TwospawnAnimationState.m_216973_();
      this.ThreespawnAnimationState.m_216973_();
      this.FourspawnAnimationState.m_216973_();
   }

   public int getState() {
      return (Integer)this.f_19804_.m_135370_(STATE);
   }

   public void setState(int state) {
      this.f_19804_.m_135381_(STATE, state);
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public void setCaster(@Nullable LivingEntity p_190549_1_) {
      this.caster = p_190549_1_;
      this.casterUuid = p_190549_1_ == null ? null : p_190549_1_.m_20148_();
   }

   @Nullable
   public LivingEntity getCaster() {
      if (this.caster == null && this.casterUuid != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.casterUuid);
         if (entity instanceof LivingEntity) {
            this.caster = (LivingEntity)entity;
         }
      }

      return this.caster;
   }

   protected void m_7378_(CompoundTag compound) {
      this.warmupDelayTicks = compound.m_128451_("Warmup");
      if (compound.m_128403_("Owner")) {
         this.casterUuid = compound.m_128342_("Owner");
      }
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks++;
            if (this.lifeTicks < 70 && this.lifeTicks > 22 && this.f_19797_ % 6 == 0) {
               double d0 = this.m_20185_();
               double d1 = this.m_20186_() + 0.5 + this.f_19796_.m_188500_();
               double d2 = this.m_20189_();
               double d3 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.15;
               double d4 = 0.15 + this.f_19796_.m_188500_() * 0.15;
               double d5 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.15;
               this.f_19853_.m_7106_((ParticleOptions)ModParticle.CURSED_FLAME.get(), d0, d1 + 0.35, d2, d3, d4, d5);
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -10 && this.getState() == 0) {
            this.setState(1 + this.f_19796_.m_188503_(3));
         }

         if (this.warmupDelayTicks < -12 && this.warmupDelayTicks > -34) {
            for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_())) {
               this.damage(livingentity);
            }
         }

         if (!this.sentSpikeEvent) {
            this.f_19853_.m_7605_(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (++this.lifeTicks > 70) {
            this.m_146870_();
         }
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.m_6084_() && !Hitentity.m_20147_() && Hitentity != livingentity && this.f_19797_ % 5 == 0) {
         if (livingentity == null) {
            Hitentity.m_6469_(DamageSource.f_19319_, this.getDamage());
         } else {
            if (livingentity.m_7307_(Hitentity)) {
               return;
            }

            Hitentity.m_6469_(CMDamageTypes.causeMaledictioMagicaeDamage(this, livingentity), this.getDamage());
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
         if (!this.m_20067_()) {
            this.f_19853_
               .m_7785_(
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)ModSounds.PHANTOM_SPEAR.get(),
                  this.m_5720_(),
                  0.3F,
                  this.f_19796_.m_188501_() * 0.2F + 0.85F,
                  false
               );
         }
      }
   }

   public float getAnimationProgress(float p_36937_) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      } else {
         int i = this.lifeTicks - 2;
         return i <= 0 ? 1.0F : 1.0F - ((float)i - p_36937_) / 20.0F;
      }
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
