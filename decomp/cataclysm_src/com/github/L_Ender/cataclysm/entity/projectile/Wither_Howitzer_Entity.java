package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Wither_Smoke_Effect_Entity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class Wither_Howitzer_Entity extends ThrowableProjectile {
   private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.m_135353_(Wither_Howitzer_Entity.class, EntityDataSerializers.f_135029_);

   public Wither_Howitzer_Entity(EntityType<Wither_Howitzer_Entity> type, Level world) {
      super(type, world);
   }

   public Wither_Howitzer_Entity(EntityType<Wither_Howitzer_Entity> type, Level world, LivingEntity thrower) {
      super(type, thrower, world);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(RADIUS, 0.5F);
   }

   public void setRadius(float p_19713_) {
      if (!this.f_19853_.f_46443_) {
         this.m_20088_().m_135381_(RADIUS, Mth.m_14036_(p_19713_, 0.0F, 32.0F));
      }
   }

   public float getRadius() {
      return (Float)this.m_20088_().m_135370_(RADIUS);
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         Entity entity1 = this.m_37282_();
         boolean flag;
         if (entity1 instanceof LivingEntity livingentity) {
            flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_(), (float)CMConfig.WitherHowizterdamage);
            if (flag) {
               if (entity.m_6084_()) {
                  this.m_19970_(livingentity, entity);
               } else if (entity1 instanceof The_Harbinger_Entity) {
                  livingentity.m_5634_(5.0F * (float)CMConfig.HarbingerHealingMultiplier);
               } else {
                  livingentity.m_5634_(5.0F);
               }
            }
         } else {
            flag = entity.m_6469_(DamageSource.f_19319_, 5.0F);
         }

         if (flag && entity instanceof LivingEntity) {
            int i = 10;
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
               i = 20;
            } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
               i = 30;
            }

            ((LivingEntity)entity).m_147207_(new MobEffectInstance(MobEffects.f_19615_, 20 * i, 1), this.m_150173_());
         }
      }
   }

   protected void m_6532_(HitResult p_37628_) {
      super.m_6532_(p_37628_);
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.0F, false, BlockInteraction.NONE);
         Wither_Smoke_Effect_Entity areaeffectcloud = new Wither_Smoke_Effect_Entity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
         areaeffectcloud.setRadius(this.getRadius());
         LivingEntity entity1 = (LivingEntity)this.m_37282_();
         areaeffectcloud.setOwner(entity1);
         areaeffectcloud.setRadiusOnUse(-0.5F);
         areaeffectcloud.setWaitTime(10);
         areaeffectcloud.setDuration(areaeffectcloud.getDuration() / 2);
         areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
         this.f_19853_.m_7967_(areaeffectcloud);
         ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 40.0F, 0.05F, 0, 20);
         this.m_146870_();
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("radius", this.getRadius());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setRadius(compound.m_128457_("radius"));
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         Vec3 vec3 = this.m_20184_();
         this.f_19853_
            .m_7106_(ParticleTypes.f_123744_, this.m_20185_() - vec3.f_82479_, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_, 0.0, 0.0, 0.0);
         this.f_19853_
            .m_7106_(ParticleTypes.f_123762_, this.m_20185_() - vec3.f_82479_, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_, 0.0, 0.0, 0.0);
      }
   }

   protected float m_7139_() {
      return 0.03F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
