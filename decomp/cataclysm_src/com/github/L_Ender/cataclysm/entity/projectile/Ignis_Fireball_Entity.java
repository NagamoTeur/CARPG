package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public class Ignis_Fireball_Entity extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Boolean> SOUL = SynchedEntityData.m_135353_(Ignis_Fireball_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> FIRED = SynchedEntityData.m_135353_(Ignis_Fireball_Entity.class, EntityDataSerializers.f_135035_);
   private int timer;

   public Ignis_Fireball_Entity(EntityType<? extends Ignis_Fireball_Entity> type, Level level) {
      super(type, level);
   }

   public Ignis_Fireball_Entity(Level level, LivingEntity entity, double x, double y, double z) {
      super((EntityType)ModEntities.IGNIS_FIREBALL.get(), entity, x, y, z, level);
   }

   public Ignis_Fireball_Entity(Level worldIn, LivingEntity entity) {
      this((EntityType<? extends Ignis_Fireball_Entity>)ModEntities.IGNIS_FIREBALL.get(), worldIn);
      this.m_5602_(entity);
   }

   public boolean m_6060_() {
      return false;
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         this.timer--;
         if (this.timer <= 0 && !this.getFired()) {
            this.setFired(true);
         }
      }

      if (this.getFired() && this.timer < -80) {
         float sqrt = (float)this.m_20184_().m_82553_();
         if (sqrt < 0.05F) {
            this.m_146870_();
         }
      }

      if (this.timer == 0 || this.timer == -40) {
         Entity entity = this.m_37282_();
         if (entity instanceof Mob && ((Mob)entity).m_5448_() != null) {
            LivingEntity target = ((Mob)entity).m_5448_();
            if (target == null) {
               this.m_146870_();
            }

            double d0 = target.m_20185_() - this.m_20185_();
            double d1 = target.m_20186_() + (double)(target.m_20206_() * 0.5F) - this.m_20186_();
            double d2 = target.m_20189_() - this.m_20189_();
            float speed = this.isSoul() ? 2.5F : 2.0F;
            this.m_6686_(d0, d1, d2, speed, 0.0F);
            this.m_146922_(-((float)Mth.m_14136_(d0, d2)) * (180.0F / (float)Math.PI));
         }
      }
   }

   public void setUp(int delay) {
      this.setFired(false);
      this.timer = delay;
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity shooter = this.m_37282_();
      if (!this.f_19853_.f_46443_
         && this.getFired()
         && !(result.m_82443_() instanceof Ignis_Fireball_Entity)
         && !(result.m_82443_() instanceof Ignis_Abyss_Fireball_Entity)
         && !(result.m_82443_() instanceof Cm_Falling_Block_Entity)
         && (!(result.m_82443_() instanceof Ignis_Entity) || !(shooter instanceof Ignis_Entity))) {
         Entity entity = result.m_82443_();
         boolean flag;
         if (shooter instanceof LivingEntity owner) {
            float damage = this.isSoul() ? 8.0F : 6.0F;
            if (entity instanceof LivingEntity) {
               flag = entity.m_6469_(DamageSource.m_19340_(this, owner).m_19366_(), damage + ((LivingEntity)entity).m_21233_() * 0.07F);
            } else {
               flag = entity.m_6469_(DamageSource.m_19340_(this, owner).m_19366_(), damage);
            }

            if (flag) {
               this.m_19970_(owner, entity);
               if (owner instanceof Ignis_Entity) {
                  owner.m_5634_(5.0F * (float)CMConfig.IgnisHealingMultiplier);
               } else {
                  owner.m_5634_(5.0F);
               }
            }
         } else {
            flag = entity.m_6469_(DamageSource.f_19319_, 6.0F);
         }

         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, true, BlockInteraction.NONE);
         this.m_146870_();
         if (flag && entity instanceof LivingEntity) {
            MobEffectInstance effectinstance1 = ((LivingEntity)entity).m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
            int i = 1;
            if (effectinstance1 != null) {
               i += effectinstance1.m_19564_();
               ((LivingEntity)entity).m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
            } else {
               i--;
            }

            i = Mth.m_14045_(i, 0, 4);
            MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 200, i, false, false, true);
            ((LivingEntity)entity).m_7292_(effectinstance);
         }
      }
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
      if (!this.f_19853_.f_46443_ && this.getFired()) {
         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, true, BlockInteraction.NONE);
         this.m_146870_();
      }
   }

   protected void m_6532_(HitResult ray) {
      Type raytraceresult$type = ray.m_6662_();
      if (raytraceresult$type == Type.ENTITY) {
         this.m_5790_((EntityHitResult)ray);
      } else if (raytraceresult$type == Type.BLOCK) {
         this.m_8060_((BlockHitResult)ray);
      }
   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource p_37616_, float p_37617_) {
      return false;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(SOUL, false);
      this.f_19804_.m_135372_(FIRED, false);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("is_soul", this.isSoul());
      compound.m_128405_("timer", this.timer);
      compound.m_128379_("fired", this.getFired());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setSoul(compound.m_128471_("is_soul"));
      this.timer = compound.m_128451_("timer");
      this.setFired(compound.m_128471_("fired"));
   }

   public boolean isSoul() {
      return (Boolean)this.f_19804_.m_135370_(SOUL);
   }

   public void setSoul(boolean IsSoul) {
      this.f_19804_.m_135381_(SOUL, IsSoul);
   }

   public void setFired(boolean fired) {
      this.f_19804_.m_135381_(FIRED, fired);
   }

   public boolean getFired() {
      return (Boolean)this.f_19804_.m_135370_(FIRED);
   }
}
