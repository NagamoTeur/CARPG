package com.github.L_Ender.cataclysm.entity.effect;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.material.PushReaction;

public class Flame_Strike_Entity extends Entity {
   private static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.m_135353_(Flame_Strike_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Boolean> DATA_WAITING = SynchedEntityData.m_135353_(Flame_Strike_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> DATA_SEE = SynchedEntityData.m_135353_(Flame_Strike_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> SOUL = SynchedEntityData.m_135353_(Flame_Strike_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Flame_Strike_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HPDAMAGE = SynchedEntityData.m_135353_(Flame_Strike_Entity.class, EntityDataSerializers.f_135029_);
   private static final float MAX_RADIUS = 32.0F;
   private int duration = 600;
   private int waitTime;
   private int warmupDelayTicks;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;

   public Flame_Strike_Entity(EntityType<? extends Flame_Strike_Entity> p_19704_, Level p_19705_) {
      super(p_19704_, p_19705_);
      this.f_19794_ = true;
      this.setRadius(3.0F);
   }

   public Flame_Strike_Entity(
      Level level,
      double x,
      double y,
      double z,
      float p_i47276_8_,
      int duration,
      int wait,
      int delay,
      float radius,
      float damage,
      float Hpdamage,
      boolean soul,
      LivingEntity casterIn
   ) {
      this((EntityType<? extends Flame_Strike_Entity>)ModEntities.FLAME_STRIKE.get(), level);
      this.setOwner(casterIn);
      this.setDuration(duration);
      this.waitTime = wait;
      this.warmupDelayTicks = delay;
      this.setRadius(radius);
      this.setDamage(damage);
      this.setHpDamage(Hpdamage);
      this.setSoul(soul);
      this.m_146922_(p_i47276_8_ * (180.0F / (float)Math.PI));
      this.m_6034_(x, y, z);
   }

   protected void m_8097_() {
      this.m_20088_().m_135372_(DATA_RADIUS, 0.5F);
      this.m_20088_().m_135372_(DAMAGE, 0.0F);
      this.m_20088_().m_135372_(HPDAMAGE, 0.0F);
      this.m_20088_().m_135372_(DATA_WAITING, true);
      this.m_20088_().m_135372_(DATA_SEE, false);
      this.m_20088_().m_135372_(SOUL, false);
   }

   public void setRadius(float p_19713_) {
      if (!this.f_19853_.f_46443_) {
         this.m_20088_().m_135381_(DATA_RADIUS, Mth.m_14036_(p_19713_, 0.0F, 32.0F));
      }
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public float getHpDamage() {
      return (Float)this.f_19804_.m_135370_(HPDAMAGE);
   }

   public void setHpDamage(float damage) {
      this.f_19804_.m_135381_(HPDAMAGE, damage);
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

   protected void setSee(boolean p_19731_) {
      this.m_20088_().m_135381_(DATA_SEE, p_19731_);
   }

   public boolean isSee() {
      return (Boolean)this.m_20088_().m_135370_(DATA_SEE);
   }

   public void setSoul(boolean Soul) {
      this.m_20088_().m_135381_(SOUL, Soul);
   }

   public boolean isSoul() {
      return (Boolean)this.m_20088_().m_135370_(SOUL);
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

         ParticleOptions particleoptions = this.isSoul() ? ParticleTypes.f_123745_ : ParticleTypes.f_123744_;
         float f1 = flag ? 0.2F : f;
         double spread = Math.PI * 2;
         int arcLen = Mth.m_14165_((double)this.getRadius() * spread);
         if (!flag) {
            if (this.f_19797_ % 2 == 0) {
               for (int j = 0; j < arcLen; j++) {
                  float f2 = this.f_19796_.m_188501_() * (float) (Math.PI * 2);
                  double d0 = this.m_20185_() + (double)(Mth.m_14089_(f2) * f1) * 0.9;
                  double d2 = this.m_20186_();
                  double d4 = this.m_20189_() + (double)(Mth.m_14031_(f2) * f1) * 0.9;
                  this.f_19853_
                     .m_7106_(
                        particleoptions, d0, d2, d4, this.f_19796_.m_188583_() * 0.07, 0.125 * (double)this.getRadius() + 0.4, this.f_19796_.m_188583_() * 0.07
                     );
               }
            }

            if (this.f_19796_.m_188503_(24) == 0) {
               this.f_19853_
                  .m_7785_(
                     this.m_20185_() + 0.5,
                     this.m_20186_() + 0.5,
                     this.m_20189_() + 0.5,
                     SoundEvents.f_11702_,
                     this.m_5720_(),
                     1.0F + this.f_19796_.m_188501_(),
                     this.f_19796_.m_188501_() * 0.7F + 0.3F,
                     false
                  );
            }
         }
      } else {
         if (this.f_19797_ >= this.waitTime + this.duration + this.warmupDelayTicks) {
            if (this.getRadius() > 0.0F) {
               this.setRadius(this.getRadius() - 0.1F);
            } else {
               if (!this.isSoul()) {
                  int explosionradius = this.owner instanceof Player ? 1 : 2;
                  this.f_19853_.m_46511_(this.owner, this.m_20185_(), this.m_20186_(), this.m_20189_(), (float)explosionradius, BlockInteraction.NONE);
               }

               this.m_146870_();
            }
         }

         if (this.f_19797_ >= this.warmupDelayTicks) {
            this.setSee(true);
         }

         boolean flag1 = this.f_19797_ < this.waitTime + this.warmupDelayTicks;
         if (flag != flag1) {
            this.setWaiting(flag1);
         }

         if (flag1) {
            return;
         }
      }

      if (!flag && this.f_19797_ % 5 == 0) {
         for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_())) {
            this.damage(livingentity);
         }
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity caster = this.getOwner();
      if (Hitentity.m_6084_() && !Hitentity.m_20147_() && Hitentity != caster && this.f_19797_ % 2 == 0) {
         if (caster == null) {
            boolean flag = Hitentity.m_6469_(DamageSource.f_19319_, this.getDamage() + Hitentity.m_21233_() * 0.01F * this.getHpDamage());
            if (flag) {
               MobEffectInstance effectinstance1 = Hitentity.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               int i = 1;
               if (effectinstance1 != null) {
                  i += effectinstance1.m_19564_();
                  Hitentity.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               } else {
                  i--;
               }

               i = Mth.m_14045_(i, 0, 4);
               MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 200, i, false, false, true);
               Hitentity.m_7292_(effectinstance);
            }
         } else {
            if (caster.m_7307_(Hitentity)) {
               return;
            }

            boolean flag = Hitentity.m_6469_(DamageSource.m_19367_(this, caster), this.getDamage() + Hitentity.m_21233_() * 0.01F * this.getHpDamage());
            if (flag) {
               MobEffectInstance effectinstance1 = Hitentity.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               int i = 1;
               if (effectinstance1 != null) {
                  i += effectinstance1.m_19564_();
                  Hitentity.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               } else {
                  i--;
               }

               i = Mth.m_14045_(i, 0, 4);
               MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 200, i, false, false, true);
               Hitentity.m_7292_(effectinstance);
            }
         }
      }
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
      this.warmupDelayTicks = p_19727_.m_128451_("Delay");
      this.setRadius(p_19727_.m_128457_("Radius"));
      if (p_19727_.m_128403_("Owner")) {
         this.ownerUUID = p_19727_.m_128342_("Owner");
      }

      this.setSoul(p_19727_.m_128471_("is_soul"));
      this.setDamage(p_19727_.m_128457_("damage"));
      this.setHpDamage(p_19727_.m_128457_("Hpdamage"));
   }

   protected void m_7380_(CompoundTag p_19737_) {
      p_19737_.m_128405_("Age", this.f_19797_);
      p_19737_.m_128405_("Duration", this.duration);
      p_19737_.m_128405_("WaitTime", this.waitTime);
      p_19737_.m_128405_("Delay", this.warmupDelayTicks);
      p_19737_.m_128350_("Radius", this.getRadius());
      if (this.ownerUUID != null) {
         p_19737_.m_128362_("Owner", this.ownerUUID);
      }

      p_19737_.m_128379_("is_soul", this.isSoul());
      p_19737_.m_128350_("damage", this.getDamage());
      p_19737_.m_128350_("Hpdamage", this.getHpDamage());
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
      return EntityDimensions.m_20395_(this.getRadius() * 1.8F, this.getRadius() * 3.0F);
   }
}
