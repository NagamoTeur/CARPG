package com.hollingsworth.arsnouveau.common.entity;

import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EntityEvokerFangs extends EvokerFangs {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 22;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   float damage;

   public EntityEvokerFangs(EntityType<? extends EvokerFangs> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public EntityEvokerFangs(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, LivingEntity casterIn, float damage) {
      this(EntityType.f_20569_, worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.m_36938_(casterIn);
      this.f_19857_ = p_i47276_8_ * (180.0F / (float)Math.PI);
      this.m_6034_(x, y, z);
      this.damage = damage;
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         this.m_20115_(6, this.m_142038_());
      }

      this.m_6075_();
      if (this.f_19853_.f_46443_) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks--;
            if (this.lifeTicks == 14) {
               for (int i = 0; i < 12; i++) {
                  double d0 = this.m_20185_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
                  double d1 = this.m_20186_() + 0.05 + this.f_19796_.m_188500_();
                  double d2 = this.m_20189_() + (this.f_19796_.m_188500_() * 2.0 - 1.0) * (double)this.m_20205_() * 0.5;
                  double d3 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.3;
                  double d4 = 0.3 + this.f_19796_.m_188500_() * 0.3;
                  double d5 = (this.f_19796_.m_188500_() * 2.0 - 1.0) * 0.3;
                  this.f_19853_.m_7106_(ParticleTypes.f_123797_, d0, d1 + 1.0, d2, d3, d4, d5);
               }
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -8) {
            for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.2, 0.0, 0.2))) {
               this.damage(livingentity);
            }
         }

         if (!this.sentSpikeEvent) {
            this.f_19853_.m_7605_(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (--this.lifeTicks < 0) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }
   }

   private void damage(LivingEntity p_190551_1_) {
      LivingEntity livingentity = this.m_36947_();
      if (p_190551_1_.m_6084_() && !p_190551_1_.m_20147_() && p_190551_1_ != livingentity) {
         if (livingentity == null) {
            p_190551_1_.m_6469_(DamageSource.f_19319_, this.damage);
         } else {
            if (livingentity.m_7307_(p_190551_1_)) {
               return;
            }

            p_190551_1_.m_6469_(DamageSource.m_19367_(this, livingentity), this.damage);
         }
      }
   }

   protected void m_7378_(CompoundTag compound) {
      this.warmupDelayTicks = compound.m_128451_("Warmup");
      if (compound.m_128403_("OwnerUUID")) {
         this.casterUuid = compound.m_128342_("OwnerUUID");
      }
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.m_128362_("OwnerUUID", this.casterUuid);
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
                  SoundEvents.f_11865_,
                  this.m_5720_(),
                  1.0F,
                  this.f_19796_.m_188501_() * 0.2F + 0.85F,
                  false
               );
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public float m_36936_(float partialTicks) {
      if (!this.clientSideAttackStarted) {
         return 0.0F;
      } else {
         int i = this.lifeTicks - 2;
         return i <= 0 ? 1.0F : 1.0F - ((float)i - partialTicks) / 20.0F;
      }
   }
}
