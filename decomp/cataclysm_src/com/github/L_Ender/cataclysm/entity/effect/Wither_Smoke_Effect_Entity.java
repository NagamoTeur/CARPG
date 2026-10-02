package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;

public class Wither_Smoke_Effect_Entity extends Entity {
   private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(Wither_Smoke_Effect_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.m_135353_(
      Wither_Smoke_Effect_Entity.class, EntityDataSerializers.f_135035_
   );
   private static final float MAX_RADIUS = 32.0F;
   private int duration = 600;
   private int waitTime = 20;
   private int durationOnUse;
   private float radiusOnUse;
   private float radiusPerTick;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public Wither_Smoke_Effect_Entity(EntityType<? extends Wither_Smoke_Effect_Entity> p_19704_, Level p_19705_) {
      super(p_19704_, p_19705_);
      this.f_19794_ = true;
      this.setRadius(3.0F);
   }

   public Wither_Smoke_Effect_Entity(Level p_19707_, double p_19708_, double p_19709_, double p_19710_) {
      this((EntityType<? extends Wither_Smoke_Effect_Entity>)ModEntities.WITHER_SMOKE_EFFECT.get(), p_19707_);
      this.m_6034_(p_19708_, p_19709_, p_19710_);
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(DATA_RADIUS, 0.5F);
      this.m_20088_().m_135372_(DATA_WAITING, false);
   }

   public void setRadius(float p_19713_) {
      if (!this.f_19853_.f_46443_) {
         this.m_20088_().m_135381_(DATA_RADIUS, Mth.m_14036_(p_19713_, 0.0F, 32.0F));
      }
   }

   public void m_6210_() {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      super.m_6210_();
      this.m_6034_(d0, d1, d2);
   }

   public float getRadius() {
      return (Float)this.m_20088_().m_135370_(DATA_RADIUS);
   }

   protected void setWaiting(boolean p_19731_) {
      this.m_20088_().m_135381_(DATA_WAITING, p_19731_);
   }

   public boolean isWaiting() {
      return (Boolean)this.m_20088_().m_135370_(DATA_WAITING);
   }

   public int getDuration() {
      return this.duration;
   }

   public void setDuration(int p_19735_) {
      this.duration = p_19735_;
   }

   public void m_8119_() {
      super.m_8119_();
      boolean flag = this.isWaiting();
      float f = this.getRadius();
      if (this.f_19853_.f_46443_) {
         if (flag && this.f_19796_.m_188499_()) {
            return;
         }

         float f1;
         if (flag) {
            int i = 2;
            f1 = 0.2F;
         } else {
            int i = Mth.m_14167_((float) Math.PI * f * f);
            f1 = f;
         }

         for (int j = 0; j < 10 + this.f_19796_.m_188503_(2); j++) {
            float f2 = this.f_19796_.m_188501_() * (float) (Math.PI * 2);
            float f3 = Mth.m_14116_(this.f_19796_.m_188501_()) * f1;
            double d0 = this.m_20185_() + (double)(Mth.m_14089_(f2) * f3);
            double d2 = this.m_20186_();
            double d4 = this.m_20189_() + (double)(Mth.m_14031_(f2) * f3);
            this.f_19853_.m_7107_(ParticleTypes.f_123762_, d0, d2, d4, 0.0, this.f_19796_.m_188583_() * 0.07, 0.0);
         }
      } else {
         if (this.f_19797_ >= this.waitTime + this.duration) {
            this.m_146870_();
            return;
         }

         boolean flag1 = this.f_19797_ < this.waitTime;
         if (flag != flag1) {
            this.setWaiting(flag1);
         }

         if (flag1) {
            return;
         }

         if (this.radiusPerTick != 0.0F) {
            f += this.radiusPerTick;
            if (f < 0.5F) {
               this.m_146870_();
               return;
            }

            this.setRadius(f);
         }

         if (this.f_19797_ % 5 == 0) {
            for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_())) {
               this.damage(livingentity);
            }
         }
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity caster = this.getOwner();
      if (Hitentity.m_6084_() && !Hitentity.m_20147_() && Hitentity != caster && this.f_19797_ % 5 == 0) {
         if (caster == null) {
            boolean flag = Hitentity.m_6469_(DamageSource.f_19320_, 3.0F);
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.f_19615_, 160, 0, false, false, true);
               Hitentity.m_7292_(effectinstance);
            }
         } else {
            if (caster.m_7307_(Hitentity)) {
               return;
            }

            boolean flag = Hitentity.m_6469_(DamageSource.m_19367_(this, caster), 3.0F);
            if (flag) {
               MobEffectInstance effectinstance = new MobEffectInstance(MobEffects.f_19615_, 160, 0, false, false, true);
               Hitentity.m_7292_(effectinstance);
            }
         }
      }
   }

   public float getRadiusOnUse() {
      return this.radiusOnUse;
   }

   public void setRadiusOnUse(float p_19733_) {
      this.radiusOnUse = p_19733_;
   }

   public float getRadiusPerTick() {
      return this.radiusPerTick;
   }

   public void setRadiusPerTick(float p_19739_) {
      this.radiusPerTick = p_19739_;
   }

   public int getDurationOnUse() {
      return this.durationOnUse;
   }

   public void setDurationOnUse(int p_146786_) {
      this.durationOnUse = p_146786_;
   }

   public int getWaitTime() {
      return this.waitTime;
   }

   public void setWaitTime(int p_19741_) {
      this.waitTime = p_19741_;
   }

   public void setOwner(@Nullable LivingEntity p_19719_) {
      this.owner = p_19719_;
      this.ownerUUID = p_19719_ == null ? null : p_19719_.m_20148_();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   protected void m_7378_(CompoundTag p_19727_) {
      this.f_19797_ = p_19727_.m_128451_("Age");
      this.duration = p_19727_.m_128451_("Duration");
      this.waitTime = p_19727_.m_128451_("WaitTime");
      this.durationOnUse = p_19727_.m_128451_("DurationOnUse");
      this.radiusOnUse = p_19727_.m_128457_("RadiusOnUse");
      this.radiusPerTick = p_19727_.m_128457_("RadiusPerTick");
      this.setRadius(p_19727_.m_128457_("Radius"));
      if (p_19727_.m_128403_("Owner")) {
         this.ownerUUID = p_19727_.m_128342_("Owner");
      }
   }

   protected void m_7380_(CompoundTag p_19737_) {
      p_19737_.m_128405_("Age", this.f_19797_);
      p_19737_.m_128405_("Duration", this.duration);
      p_19737_.m_128405_("WaitTime", this.waitTime);
      p_19737_.m_128405_("DurationOnUse", this.durationOnUse);
      p_19737_.m_128350_("RadiusOnUse", this.radiusOnUse);
      p_19737_.m_128350_("RadiusPerTick", this.radiusPerTick);
      p_19737_.m_128350_("Radius", this.getRadius());
   }

   public void m_7350_(EntityDataAccessor<?> p_19729_) {
      if (DATA_RADIUS.equals(p_19729_)) {
         this.m_6210_();
      }

      super.m_7350_(p_19729_);
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }

   public EntityDimensions m_6972_(Pose p_19721_) {
      return EntityDimensions.m_20395_(this.getRadius() * 2.0F, 0.5F);
   }
}
