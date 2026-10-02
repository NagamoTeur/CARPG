package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Flame_Jet_Entity extends Entity {
   private int warmupDelayTicks;
   private boolean sentSpikeEvent;
   private int lifeTicks = 22;
   private boolean clientSideAttackStarted;
   private LivingEntity caster;
   private UUID casterUuid;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Flame_Jet_Entity.class, EntityDataSerializers.f_135029_);

   public Flame_Jet_Entity(EntityType<? extends Flame_Jet_Entity> p_i50170_1_, Level p_i50170_2_) {
      super(p_i50170_1_, p_i50170_2_);
   }

   public Flame_Jet_Entity(Level worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, float damage, LivingEntity casterIn) {
      this((EntityType<? extends Flame_Jet_Entity>)ModEntities.FLAME_JET.get(), worldIn);
      this.warmupDelayTicks = p_i47276_9_;
      this.setCaster(casterIn);
      this.setDamage(damage);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.m_6034_(x, y, z);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
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

      this.setDamage(compound.m_128457_("damage"));
   }

   protected void m_7380_(CompoundTag compound) {
      compound.m_128405_("Warmup", this.warmupDelayTicks);
      if (this.casterUuid != null) {
         compound.m_128362_("Owner", this.casterUuid);
      }

      compound.m_128350_("damage", this.getDamage());
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.clientSideAttackStarted) {
            this.lifeTicks--;
            if (this.lifeTicks == 14) {
               double d0 = this.m_20185_();
               double d1 = this.m_20186_() + 2.0;
               double d2 = this.m_20189_();
               this.f_19853_.m_7107_((ParticleOptions)ModParticle.FLAME_JET.get(), d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }
      } else if (--this.warmupDelayTicks < 0) {
         if (this.warmupDelayTicks == -8) {
            AABB aabb = this.m_20191_().m_82400_(0.1);
            AABB selection = new AABB(aabb.f_82288_, this.m_20186_() - 0.1, aabb.f_82290_, aabb.f_82291_, this.m_20186_() + 3.5, aabb.f_82293_);

            for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
               this.damage(livingentity);
            }
         }

         if (!this.sentSpikeEvent) {
            this.f_19853_.m_7605_(this, (byte)4);
            this.sentSpikeEvent = true;
         }

         if (--this.lifeTicks < 0) {
            this.m_146870_();
         }
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getCaster();
      if (Hitentity.m_6084_() && !Hitentity.m_20147_() && Hitentity != livingentity) {
         if (livingentity == null) {
            Hitentity.m_6469_(DamageSource.f_19319_, this.getDamage());
         } else {
            if (livingentity.m_7307_(Hitentity)) {
               return;
            }

            if (Hitentity.m_6469_(DamageSource.m_19340_(this, livingentity), this.getDamage())) {
               Hitentity.m_20254_(5);
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      super.m_7822_(id);
      if (id == 4) {
         this.clientSideAttackStarted = true;
         if (!this.m_20067_()) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11705_, this.m_5720_(), 0.3F, 1.25F, false);
         }
      }
   }

   public float m_213856_() {
      return 1.0F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
