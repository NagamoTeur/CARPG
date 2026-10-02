package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;

public class Laser_Beam_Entity extends Projectile {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Laser_Beam_Entity.class, EntityDataSerializers.f_135029_);

   public Laser_Beam_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public Laser_Beam_Entity(EntityType type, double x, double y, double z, Level worldIn) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
   }

   public Laser_Beam_Entity(Level worldIn, LivingEntity shooter) {
      this((EntityType)ModEntities.LASER_BEAM.get(), shooter.m_20185_(), shooter.m_20188_(), shooter.m_20189_(), worldIn);
      this.m_5602_(shooter);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(DAMAGE, 0.0F);
   }

   public boolean m_6060_() {
      return false;
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         LivingEntity entity1 = (LivingEntity)this.m_37282_();
         int i = entity.m_20094_();
         entity.m_20254_(5);
         if (!entity.m_6469_(CMDamageTypes.causeLaserDamage(this, entity1), this.getDamage())) {
            entity.m_7311_(i);
         } else if (entity1 != null) {
            this.m_19970_(entity1, entity);
         }
      }
   }

   protected void m_8060_(BlockHitResult p_37384_) {
      super.m_8060_(p_37384_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = this.m_37282_();
         if (CMConfig.HarbingerLightFire) {
            BlockPos blockpos = p_37384_.m_82425_().m_121945_(p_37384_.m_82434_());
            if (this.f_19853_.m_46859_(blockpos)) {
               this.f_19853_.m_46597_(blockpos, BaseFireBlock.m_49245_(this.f_19853_, blockpos));
            }
         } else if (!(entity instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.f_19853_, entity)) {
            BlockPos blockpos = p_37384_.m_82425_().m_121945_(p_37384_.m_82434_());
            if (this.f_19853_.m_46859_(blockpos)) {
               this.f_19853_.m_46597_(blockpos, BaseFireBlock.m_49245_(this.f_19853_, blockpos));
            }
         }
      }
   }

   protected void m_6532_(HitResult p_37628_) {
      super.m_6532_(p_37628_);
      if (!this.f_19853_.f_46443_) {
         this.m_146870_();
      }
   }

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (this.f_19853_.f_46443_ || (entity == null || !entity.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         super.m_8119_();
         HitResult hitresult = ProjectileUtil.m_37294_(this, x$0 -> this.m_5603_(x$0));
         if (hitresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }

         this.m_20101_();
         Vec3 vec3 = this.m_20184_();
         double k0 = this.m_20185_() + vec3.f_82479_;
         double k1 = this.m_20186_() + vec3.f_82480_;
         double k2 = this.m_20189_() + vec3.f_82481_;
         if (this.m_20069_()) {
            for (int i = 0; i < 4; i++) {
               float f1 = 0.25F;
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123795_,
                     k0 - vec3.f_82479_ * 0.25,
                     k1 - vec3.f_82480_ * 0.25,
                     k2 - vec3.f_82481_ * 0.25,
                     vec3.f_82479_,
                     vec3.f_82480_,
                     vec3.f_82481_
                  );
            }
         }

         if (this.f_19860_ == 0.0F && this.f_19859_ == 0.0F) {
            double d0 = vec3.m_165924_();
            this.m_146922_((float)(Mth.m_14136_(vec3.f_82479_, vec3.f_82481_) * 180.0F / (float)Math.PI));
            this.m_146926_((float)(Mth.m_14136_(vec3.f_82480_, d0) * 180.0F / (float)Math.PI));
            this.f_19859_ = this.m_146908_();
            this.f_19860_ = this.m_146909_();
         }

         double d5 = vec3.f_82479_;
         double d6 = vec3.f_82480_;
         double d1 = vec3.f_82481_;
         double d7 = this.m_20185_() + d5;
         double d2 = this.m_20186_() + d6;
         double d3 = this.m_20189_() + d1;
         double d4 = vec3.m_165924_();
         this.m_146922_((float)(Mth.m_14136_(d5, d1) * 180.0F / (float)Math.PI));
         this.m_146926_((float)(Mth.m_14136_(d6, d4) * 180.0F / (float)Math.PI));
         this.m_146926_(m_37273_(this.f_19860_, this.m_146909_()));
         this.m_146922_(m_37273_(this.f_19859_, this.m_146908_()));
         float f = 0.99F;
         float f1 = 0.05F;
         float sqrt = (float)this.m_20184_().m_82553_();
         if (sqrt < 0.1F) {
            this.m_146870_();
         }

         this.m_20256_(vec3.m_82490_((double)f));
         this.m_6034_(d7, d2, d3);
      } else {
         this.m_146870_();
      }
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource p_37616_, float p_37617_) {
      return false;
   }
}
