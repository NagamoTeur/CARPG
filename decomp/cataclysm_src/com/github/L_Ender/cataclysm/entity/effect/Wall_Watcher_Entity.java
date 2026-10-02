package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class Wall_Watcher_Entity extends Entity {
   static final EntityDataAccessor<Integer> TIMER = SynchedEntityData.m_135353_(Wall_Watcher_Entity.class, EntityDataSerializers.f_135028_);
   int effectiveChargeTime;
   double knockbackSpeedIndex;
   float damagePerEffectiveCharge;
   double dx;
   double dz;
   LivingEntity source;
   List<Wall_Watcher_Entity.YUnchangedLivingEntity> watchedEntities;

   public Wall_Watcher_Entity(EntityType<? extends Wall_Watcher_Entity> entityTypeIn, Level level) {
      super(entityTypeIn, level);
   }

   public Wall_Watcher_Entity(
      Level level,
      BlockPos pos,
      int timer,
      int effectiveChargeTime,
      double knockbackSpeedIndex,
      float damagePerEffectiveCharge,
      double dx,
      double dz,
      LivingEntity source
   ) {
      super((EntityType)ModEntities.WALL_WATCHER.get(), level);
      this.m_6034_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
      this.f_19804_.m_135381_(TIMER, timer);
      this.effectiveChargeTime = effectiveChargeTime;
      this.knockbackSpeedIndex = knockbackSpeedIndex;
      this.damagePerEffectiveCharge = damagePerEffectiveCharge;
      this.dx = dx;
      this.dz = dz;
      this.source = source;
      this.watchedEntities = new ArrayList<>();
   }

   public void watch(LivingEntity livingEntity) {
      if (livingEntity != null) {
         this.watchedEntities.add(new Wall_Watcher_Entity.YUnchangedLivingEntity(livingEntity));
      }
   }

   public void removeFromWatchList(Wall_Watcher_Entity.YUnchangedLivingEntity yUnchangedLivingEntity) {
      if (yUnchangedLivingEntity != null) {
         this.watchedEntities.remove(yUnchangedLivingEntity);
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.m_5776_()) {
         int temp = (Integer)this.f_19804_.m_135370_(TIMER);
         if (this.watchedEntities == null || this.source == null) {
            this.m_142687_(RemovalReason.DISCARDED);
         } else if (!this.watchedEntities.isEmpty()) {
            List<Wall_Watcher_Entity.YUnchangedLivingEntity> entitiesRemoveFromWatchList = new ArrayList<>();

            for (Wall_Watcher_Entity.YUnchangedLivingEntity entity : this.watchedEntities) {
               if (entity.livingEntity.f_19862_) {
                  if (!entity.livingEntity.m_7307_(this.source)) {
                     entity.livingEntity.f_19802_ = 0;
                     float realDamageApplied = this.damagePerEffectiveCharge * (float)this.effectiveChargeTime + 1.0F;
                     boolean flag = entity.livingEntity.m_6469_(DamageSource.m_19340_(this, this.source), realDamageApplied);
                     if (flag) {
                        entity.livingEntity.m_5496_(SoundEvents.f_11913_, 0.3F, 1.0F);
                        entity.livingEntity.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), 50));
                     }
                  }

                  entitiesRemoveFromWatchList.add(entity);
               } else {
                  entity.setMotion(this.dx * this.knockbackSpeedIndex, this.dz * this.knockbackSpeedIndex);
               }
            }

            for (Wall_Watcher_Entity.YUnchangedLivingEntity remove : entitiesRemoveFromWatchList) {
               this.removeFromWatchList(remove);
            }

            if (temp - 1 == 0) {
               this.watchedEntities.clear();
               this.m_142687_(RemovalReason.DISCARDED);
            } else {
               this.f_19804_.m_135381_(TIMER, temp - 1);
            }
         } else if (temp - 1 == 0) {
            this.m_142687_(RemovalReason.DISCARDED);
         } else {
            this.f_19804_.m_135381_(TIMER, temp - 1);
         }
      }
   }

   public boolean m_20068_() {
      return true;
   }

   protected void m_7378_(CompoundTag p_20052_) {
      this.source = null;
   }

   protected void m_7380_(CompoundTag p_20139_) {
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(TIMER, 0);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   static class YUnchangedLivingEntity {
      LivingEntity livingEntity;
      double Y;

      public YUnchangedLivingEntity(LivingEntity livingEntity) {
         this.livingEntity = livingEntity;
         this.Y = livingEntity.m_20186_();
      }

      void setMotion(double X, double Z) {
         this.livingEntity.m_20334_(X, 0.0, Z);
         this.livingEntity.m_6034_(this.livingEntity.m_20185_(), this.Y, this.livingEntity.m_20189_());
         this.livingEntity.f_19864_ = true;
      }
   }
}
