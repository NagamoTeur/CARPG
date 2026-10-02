package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Harbinger_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;

public class Wither_Missile_Entity extends Projectile {
   public double xPower;
   public double yPower;
   public double zPower;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Wither_Missile_Entity.class, EntityDataSerializers.f_135029_);

   public Wither_Missile_Entity(EntityType<? extends Wither_Missile_Entity> type, Level level) {
      super(type, level);
   }

   public Wither_Missile_Entity(
      EntityType<? extends Wither_Missile_Entity> type, double getX, double gety, double getz, double p_36821_, double p_36822_, double p_36823_, Level level
   ) {
      this(type, level);
      this.m_7678_(getX, gety, getz, this.m_146908_(), this.m_146909_());
      this.m_20090_();
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0) {
         this.xPower = p_36821_ / d0 * 0.1;
         this.yPower = p_36822_ / d0 * 0.1;
         this.zPower = p_36823_ / d0 * 0.1;
      }
   }

   public Wither_Missile_Entity(LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_, float damage) {
      this(
         (EntityType<? extends Wither_Missile_Entity>)ModEntities.WITHER_MISSILE.get(),
         p_36827_.m_20185_(),
         p_36827_.m_20186_(),
         p_36827_.m_20189_(),
         p_36828_,
         p_36829_,
         p_36830_,
         p_36831_
      );
      this.m_5602_(p_36827_);
      this.setDamage(damage);
      this.m_19915_(p_36827_.m_146908_(), p_36827_.m_146909_());
   }

   public Wither_Missile_Entity(
      EntityType<? extends Wither_Missile_Entity> type,
      LivingEntity p_36827_,
      double getX,
      double gety,
      double getz,
      double p_36821_,
      double p_36822_,
      double p_36823_,
      float damage,
      Level level
   ) {
      this(type, level);
      this.m_7678_(getX, gety, getz, this.m_146908_(), this.m_146909_());
      this.m_5602_(p_36827_);
      this.setDamage(damage);
      this.m_20090_();
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0) {
         this.xPower = p_36821_ / d0 * 0.1;
         this.yPower = p_36822_ / d0 * 0.1;
         this.zPower = p_36823_ / d0 * 0.1;
      }
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

   public boolean m_6783_(double p_36837_) {
      double d0 = this.m_20191_().m_82309_() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
   }

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (this.f_19853_.f_46443_ || (entity == null || !entity.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         super.m_8119_();
         HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (hitresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }

         this.m_20101_();
         Vec3 vec3 = this.m_20184_();
         double d0 = this.m_20185_() + vec3.f_82479_;
         double d1 = this.m_20186_() + vec3.f_82480_;
         double d2 = this.m_20189_() + vec3.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.getInertia();
         if (!this.m_20069_()) {
            this.f_19853_
               .m_7106_(
                  ParticleTypes.f_123744_,
                  this.m_20185_() - vec3.f_82479_,
                  this.m_20186_() - vec3.f_82480_ + 0.35,
                  this.m_20189_() - vec3.f_82481_,
                  0.0,
                  0.0,
                  0.0
               );
         } else {
            for (int i = 0; i < 4; i++) {
               float f1 = 0.25F;
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123795_,
                     d0 - vec3.f_82479_ * 0.25,
                     d1 - vec3.f_82480_ * 0.25,
                     d2 - vec3.f_82481_ * 0.25,
                     vec3.f_82479_,
                     vec3.f_82480_,
                     vec3.f_82481_
                  );
            }

            f = 0.8F;
         }

         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123762_, this.m_20185_() - vec3.f_82479_, this.m_20186_() - vec3.f_82480_ + 0.35, this.m_20189_() - vec3.f_82481_, 0.0, 0.0, 0.0
            );
         this.m_20256_(vec3.m_82520_(this.xPower, this.yPower, this.zPower).m_82490_((double)f));
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_146870_();
      }
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         Entity entity1 = this.m_37282_();
         boolean flag;
         if (entity1 instanceof LivingEntity livingentity) {
            flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_(), this.getDamage());
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
            flag = entity.m_6469_(DamageSource.f_19319_, this.getDamage());
         }

         if (flag && entity instanceof LivingEntity) {
            int i = 5;
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
               i = 10;
            } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
               i = 15;
            }

            ((LivingEntity)entity).m_147207_(new MobEffectInstance(MobEffects.f_19615_, 20 * i, 1), this.m_150173_());
         }
      }
   }

   protected void m_6532_(HitResult p_37628_) {
      super.m_6532_(p_37628_);
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, false, BlockInteraction.NONE);
         this.m_146870_();
      }
   }

   protected boolean m_5603_(Entity p_36842_) {
      return super.m_5603_(p_36842_) && !p_36842_.f_19794_;
   }

   protected float getInertia() {
      return 1.0F;
   }

   public void m_7380_(CompoundTag p_36848_) {
      super.m_7380_(p_36848_);
      p_36848_.m_128365_("power", this.m_20063_(new double[]{this.xPower, this.yPower, this.zPower}));
   }

   public void m_7378_(CompoundTag p_36844_) {
      super.m_7378_(p_36844_);
      if (p_36844_.m_128425_("power", 9)) {
         ListTag listtag = p_36844_.m_128437_("power", 6);
         if (listtag.size() == 3) {
            this.xPower = listtag.m_128772_(0);
            this.yPower = listtag.m_128772_(1);
            this.zPower = listtag.m_128772_(2);
         }
      }
   }

   public boolean m_6087_() {
      return false;
   }

   public float m_6143_() {
      return 1.0F;
   }

   public boolean m_6469_(DamageSource p_37616_, float p_37617_) {
      return false;
   }

   public float m_213856_() {
      return 1.0F;
   }

   public Packet<?> m_5654_() {
      Entity entity = this.m_37282_();
      int i = entity == null ? 0 : entity.m_19879_();
      return new ClientboundAddEntityPacket(
         this.m_19879_(),
         this.m_20148_(),
         this.m_20185_(),
         this.m_20186_(),
         this.m_20189_(),
         this.m_146909_(),
         this.m_146908_(),
         this.m_6095_(),
         i,
         new Vec3(this.xPower, this.yPower, this.zPower),
         0.0
      );
   }

   public void m_141965_(ClientboundAddEntityPacket p_150128_) {
      super.m_141965_(p_150128_);
      double d0 = p_150128_.m_131503_();
      double d1 = p_150128_.m_131504_();
      double d2 = p_150128_.m_131505_();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      if (d3 != 0.0) {
         this.xPower = d0 / d3 * 0.1;
         this.yPower = d1 / d3 * 0.1;
         this.zPower = d2 / d3 * 0.1;
      }
   }
}
