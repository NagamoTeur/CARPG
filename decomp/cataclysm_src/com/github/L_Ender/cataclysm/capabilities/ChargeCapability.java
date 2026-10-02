package com.github.L_Ender.cataclysm.capabilities;

import com.github.L_Ender.cataclysm.entity.effect.Wall_Watcher_Entity;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class ChargeCapability {
   public static ResourceLocation ID = new ResourceLocation("cataclysm", "charge_cap");

   public static class ChargeCapabilityImp implements ChargeCapability.IChargeCapability {
      private boolean charge;
      public int Timer;
      public int effectiveChargeTime;
      public float dx;
      public float dz;
      public float damagePerEffectiveCharge;
      public float knockbackSpeedIndex;

      @Override
      public void tick(LivingEntity entity) {
         if (this.isCharge() && !entity.f_19853_.m_5776_()) {
            int temp = this.getTimer();
            this.setTimer(temp - 1);
            if (temp > 0) {
               AABB collideBox = entity.m_20191_().m_82377_(0.75, 0.75, 0.75);
               List<LivingEntity> checks = entity.f_19853_.m_45976_(LivingEntity.class, collideBox);
               checks.remove(entity);
               if (!checks.isEmpty()) {
                  Wall_Watcher_Entity watchEntity = new Wall_Watcher_Entity(
                     entity.f_19853_,
                     entity.m_20183_(),
                     temp,
                     this.effectiveChargeTime,
                     (double)this.knockbackSpeedIndex,
                     this.damagePerEffectiveCharge,
                     (double)this.dx,
                     (double)this.dz,
                     entity
                  );
                  List<LivingEntity> impact = entity.f_19853_.m_45976_(LivingEntity.class, entity.m_20191_().m_82377_(3.5, 0.75, 3.5));
                  impact.remove(entity);

                  for (LivingEntity target : impact) {
                     if (!target.m_7307_(entity)) {
                        boolean flag = target.m_6469_(DamageSource.m_19340_(entity, entity), this.damagePerEffectiveCharge * (float)this.effectiveChargeTime);
                        watchEntity.watch(target);
                        if (flag) {
                           target.m_5496_(SoundEvents.f_11668_, 1.5F, 0.8F);
                        }
                     }
                  }

                  entity.f_19853_.m_7967_(watchEntity);
                  entity.m_20334_(0.0, 0.0, 0.0);
                  entity.f_19864_ = true;
                  this.setCharge(false);
               }
            }

            if (entity.f_19862_ || temp == 0) {
               this.setCharge(false);
            }
         }
      }

      @Override
      public void setCharge(boolean charge) {
         this.charge = charge;
      }

      @Override
      public boolean isCharge() {
         return this.charge;
      }

      @Override
      public void setdamagePerEffectiveCharge(float damage) {
         this.damagePerEffectiveCharge = damage;
      }

      @Override
      public float getdamagePerEffectiveCharge() {
         return this.damagePerEffectiveCharge;
      }

      @Override
      public void setknockbackSpeedIndex(float knockback) {
         this.knockbackSpeedIndex = knockback;
      }

      @Override
      public float getknockbackSpeedIndex() {
         return this.knockbackSpeedIndex;
      }

      @Override
      public void seteffectiveChargeTime(int chargetime) {
         this.effectiveChargeTime = chargetime;
      }

      @Override
      public int geteffectiveChargeTime() {
         return this.effectiveChargeTime;
      }

      @Override
      public void setdx(float dx) {
         this.dx = dx;
      }

      @Override
      public float getdx() {
         return this.dx;
      }

      @Override
      public void setdZ(float dz) {
         this.dz = dz;
      }

      @Override
      public float getdZ() {
         return this.dz;
      }

      @Override
      public void setTimer(int timer) {
         this.Timer = timer;
      }

      @Override
      public int getTimer() {
         return this.Timer;
      }

      public CompoundTag serializeNBT() {
         CompoundTag tag = new CompoundTag();
         tag.m_128379_("isCharge", this.isCharge());
         tag.m_128405_("ChargeTime", this.geteffectiveChargeTime());
         tag.m_128350_("ChargeDamage", this.getdamagePerEffectiveCharge());
         tag.m_128350_("KnockbackSpeed", this.getknockbackSpeedIndex());
         tag.m_128350_("dx", this.getdx());
         tag.m_128350_("dz", this.getdZ());
         tag.m_128405_("timer", this.getTimer());
         return tag;
      }

      public void deserializeNBT(CompoundTag nbt) {
         this.setCharge(nbt.m_128471_("isCharge"));
         this.seteffectiveChargeTime(nbt.m_128451_("ChargeTime"));
         this.setdamagePerEffectiveCharge(nbt.m_128457_("ChargeDamage"));
         this.setknockbackSpeedIndex(nbt.m_128457_("KnockbackSpeed"));
         this.setdx(nbt.m_128457_("dx"));
         this.setdZ(nbt.m_128457_("dz"));
         this.setTimer(nbt.m_128451_("timer"));
      }

      public static class ChargeProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
         private final LazyOptional<ChargeCapability.IChargeCapability> instance = LazyOptional.of(ChargeCapability.ChargeCapabilityImp::new);

         public CompoundTag serializeNBT() {
            return (CompoundTag)((ChargeCapability.IChargeCapability)this.instance.orElseThrow(NullPointerException::new)).serializeNBT();
         }

         public void deserializeNBT(CompoundTag nbt) {
            ((ChargeCapability.IChargeCapability)this.instance.orElseThrow(NullPointerException::new)).deserializeNBT(nbt);
         }

         @Nonnull
         public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
            return ModCapabilities.CHARGE_CAPABILITY.orEmpty(cap, this.instance.cast());
         }
      }
   }

   public interface IChargeCapability extends INBTSerializable<CompoundTag> {
      void tick(LivingEntity var1);

      void setCharge(boolean var1);

      boolean isCharge();

      void setTimer(int var1);

      int getTimer();

      void setdamagePerEffectiveCharge(float var1);

      float getdamagePerEffectiveCharge();

      void setknockbackSpeedIndex(float var1);

      float getknockbackSpeedIndex();

      void seteffectiveChargeTime(int var1);

      int geteffectiveChargeTime();

      void setdx(float var1);

      float getdx();

      void setdZ(float var1);

      float getdZ();
   }
}
