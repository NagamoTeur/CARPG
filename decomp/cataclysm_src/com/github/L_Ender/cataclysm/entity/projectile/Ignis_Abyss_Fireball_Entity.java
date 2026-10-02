package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Ignis_Abyss_Fireball_Entity extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Integer> BOUNCES = SynchedEntityData.m_135353_(Ignis_Abyss_Fireball_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> FIRED = SynchedEntityData.m_135353_(Ignis_Abyss_Fireball_Entity.class, EntityDataSerializers.f_135035_);
   private int timer;

   public Ignis_Abyss_Fireball_Entity(EntityType<? extends Ignis_Abyss_Fireball_Entity> type, Level level) {
      super(type, level);
   }

   public Ignis_Abyss_Fireball_Entity(Level level, LivingEntity entity, double x, double y, double z) {
      super((EntityType)ModEntities.IGNIS_ABYSS_FIREBALL.get(), entity, x, y, z, level);
   }

   public Ignis_Abyss_Fireball_Entity(Level worldIn, LivingEntity entity) {
      this((EntityType<? extends Ignis_Abyss_Fireball_Entity>)ModEntities.IGNIS_ABYSS_FIREBALL.get(), worldIn);
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

      if (this.timer < -80) {
         float sqrt = (float)this.m_20184_().m_82553_();
         if (sqrt < 0.1F) {
            this.m_146870_();
         }
      }

      if ((this.timer == 0 || this.timer == -40) && this.getTotalBounces() == 0) {
         Entity entity = this.m_37282_();
         if (entity instanceof Mob && ((Mob)entity).m_5448_() != null) {
            LivingEntity target = ((Mob)entity).m_5448_();
            if (target == null) {
               this.m_146870_();
            }

            double d0 = target.m_20185_() - this.m_20185_();
            double d1 = target.m_20186_() + (double)(target.m_20206_() * 0.5F) - this.m_20186_();
            double d2 = target.m_20189_() - this.m_20189_();
            float speed = 2.0F;
            this.m_6686_(d0, d1, d2, speed, 0.0F);
            this.m_146922_(-((float)Mth.m_14136_(d0, d2)) * (180.0F / (float)Math.PI));
         }
      }
   }

   public void setUp(int delay) {
      this.setFired(false);
      this.timer = delay;
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      Entity shooter = this.m_37282_();
      if (!this.f_19853_.f_46443_
         && !(p_37626_.m_82443_() instanceof Ignis_Fireball_Entity)
         && !(p_37626_.m_82443_() instanceof Ignis_Abyss_Fireball_Entity)
         && !(p_37626_.m_82443_() instanceof Cm_Falling_Block_Entity)
         && (!(p_37626_.m_82443_() instanceof Ignis_Entity) || !(shooter instanceof Ignis_Entity))
         && this.getFired()) {
         Entity entity = p_37626_.m_82443_();
         boolean flag;
         if (shooter instanceof LivingEntity owner) {
            if (entity instanceof LivingEntity) {
               flag = entity.m_6469_(DamageSource.m_19340_(this, owner).m_19366_(), 10.0F + ((LivingEntity)entity).m_21233_() * 0.2F);
            } else {
               flag = entity.m_6469_(DamageSource.m_19340_(this, owner).m_19366_(), 10.0F);
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
            flag = entity.m_6469_(DamageSource.f_19319_, 5.0F);
         }

         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0F, true, BlockInteraction.NONE);
         this.m_146870_();
         if (flag && entity instanceof LivingEntity) {
            MobEffectInstance effectinstance1 = ((LivingEntity)entity).m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
            int i = 2;
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
      BlockState blockstate = this.f_19853_.m_8055_(result.m_82425_());
      if (!blockstate.m_60812_(this.f_19853_, result.m_82425_()).m_83281_() && this.getFired()) {
         Direction face = result.m_82434_();
         blockstate.m_60669_(this.f_19853_, blockstate, result, this);
         Vec3 motion = this.m_20184_();
         double motionX = motion.m_7096_();
         double motionY = motion.m_7098_();
         double motionZ = motion.m_7094_();
         if (face == Direction.EAST) {
            motionX = -motionX;
         } else if (face == Direction.SOUTH) {
            motionZ = -motionZ;
         } else if (face == Direction.WEST) {
            motionX = -motionX;
         } else if (face == Direction.NORTH) {
            motionZ = -motionZ;
         } else if (face == Direction.UP) {
            motionY = -motionY;
         } else if (face == Direction.DOWN) {
            motionY = -motionY;
         }

         this.m_20334_(motionX, motionY, motionZ);
         this.f_36813_ = motionX * 0.05;
         this.f_36814_ = motionY * 0.05;
         this.f_36815_ = motionZ * 0.05;
         if (this.f_19797_ <= 500 && this.getTotalBounces() <= 5) {
            this.setTotalBounces(this.getTotalBounces() + 1);
         } else if (!this.f_19853_.f_46443_) {
            this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0F, true, BlockInteraction.NONE);
            this.m_146870_();
         }
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

   protected void m_8097_() {
      this.f_19804_.m_135372_(BOUNCES, 0);
      this.f_19804_.m_135372_(FIRED, false);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("totalBounces", this.getTotalBounces());
      compound.m_128405_("timer", this.timer);
      compound.m_128379_("fired", this.getFired());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setTotalBounces(compound.m_128451_("totalBounces"));
      this.timer = compound.m_128451_("timer");
      this.setFired(compound.m_128471_("fired"));
   }

   public int getTotalBounces() {
      return (Integer)this.f_19804_.m_135370_(BOUNCES);
   }

   public void setTotalBounces(int bounces) {
      this.f_19804_.m_135381_(BOUNCES, bounces);
   }

   public void setFired(boolean fired) {
      this.f_19804_.m_135381_(FIRED, fired);
   }

   public boolean getFired() {
      return (Boolean)this.f_19804_.m_135370_(FIRED);
   }

   public boolean m_6469_(DamageSource p_36839_, float p_36840_) {
      if (this.m_6673_(p_36839_)) {
         return false;
      } else {
         this.m_5834_();
         Entity entity = p_36839_.m_7639_();
         if (entity != null && this.getFired()) {
            if (!this.f_19853_.f_46443_) {
               Vec3 vec3 = entity.m_20154_();
               this.m_20256_(vec3);
               this.f_36813_ = vec3.f_82479_ * 0.1;
               this.f_36814_ = vec3.f_82480_ * 0.1;
               this.f_36815_ = vec3.f_82481_ * 0.1;
               this.m_5602_(entity);
            }

            return true;
         } else {
            return false;
         }
      }
   }
}
