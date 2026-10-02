package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class EarthQuake_Entity extends ThrowableProjectile {
   private int lifeTime = 60;
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(EarthQuake_Entity.class, EntityDataSerializers.f_135029_);

   public EarthQuake_Entity(EntityType<? extends EarthQuake_Entity> type, Level worldIn) {
      super(type, worldIn);
   }

   public EarthQuake_Entity(Level worldIn, double x, double y, double z) {
      this((EntityType<? extends EarthQuake_Entity>)ModEntities.EARTHQUAKE.get(), worldIn);
      this.m_6034_(x, y + 1.5, z);
   }

   public EarthQuake_Entity(Level worldIn, LivingEntity throwerIn) {
      this((EntityType<? extends EarthQuake_Entity>)ModEntities.EARTHQUAKE.get(), worldIn);
      this.m_5602_(throwerIn);
      this.m_20334_(0.1, 0.0, 0.1);
   }

   public void m_6686_(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
      Vec3 vector3d = new Vec3(pX, pY, pZ)
         .m_82541_()
         .m_82520_(
            this.f_19796_.m_188583_() * 0.0075F * (double)pInaccuracy,
            this.f_19796_.m_188583_() * 0.0075F * (double)pInaccuracy,
            this.f_19796_.m_188583_() * 0.0075F * (double)pInaccuracy
         )
         .m_82490_((double)pVelocity);
      this.m_20256_(vector3d);
      double d0 = vector3d.m_165924_();
      this.m_146922_((float)(Mth.m_14136_(vector3d.f_82479_, vector3d.f_82481_) * 180.0F / (float)Math.PI));
      this.m_146926_((float)(Mth.m_14136_(vector3d.f_82480_, d0) * 180.0F / (float)Math.PI));
      this.f_19859_ = this.m_146908_();
      this.f_19860_ = this.m_146909_();
   }

   public void m_37251_(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
      float f = -Mth.m_14031_(pY * (float) (Math.PI / 180.0)) * Mth.m_14089_(pX * (float) (Math.PI / 180.0));
      float f1 = -1.0F;
      float f2 = Mth.m_14089_(pY * (float) (Math.PI / 180.0)) * Mth.m_14089_(pX * (float) (Math.PI / 180.0));
      this.m_6686_((double)f, (double)f1, (double)f2, pVelocity, pInaccuracy);
      Vec3 vector3d = pShooter.m_20184_();
      this.m_20256_(this.m_20184_().m_82520_(vector3d.f_82479_, pShooter.m_20096_() ? 0.0 : vector3d.f_82480_, vector3d.f_82481_));
   }

   public boolean m_7337_(Entity pEntity) {
      return this.m_5603_(pEntity);
   }

   public boolean m_5829_() {
      return false;
   }

   public void m_8119_() {
      if (this.m_37282_() != null && !this.m_37282_().m_6084_()) {
         this.m_146870_();
      } else {
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.onUpdateInAir();
      }

      super.m_8119_();
   }

   protected boolean m_5603_(Entity pTarget) {
      return super.m_5603_(pTarget) && pTarget != this.m_37282_();
   }

   public boolean m_6060_() {
      return false;
   }

   private void onUpdateInAir() {
      this.lifeTime--;
      if (this.lifeTime <= 0) {
         this.m_146870_();
      }

      BlockPos pos = new BlockPos(this.m_20185_(), this.m_20186_() - 1.0, this.m_20189_());
      BlockState iblockstate = this.f_19853_.m_8055_(pos);
      Entity entity1 = this.m_37282_();
      LivingEntity livingonwer = entity1 instanceof LivingEntity ? (LivingEntity)entity1 : null;

      for (LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82377_(0.5, 0.5, 0.5))) {
         if (this.m_37282_() != null
            && this.f_19797_ % 5 == 0
            && livingentity != this.m_37282_()
            && livingentity.m_20096_()
            && !this.m_37282_().m_7307_(livingentity)
            && livingentity.m_6084_()
            && livingentity.m_6469_(DamageSource.m_19340_(this, livingonwer), this.getDamage())) {
            this.strongKnockback(livingentity, 0.5);
         }
      }

      if (this.f_19853_.f_46443_) {
         for (int i = 0; i < 3; i++) {
            this.f_19853_
               .m_7106_(
                  new BlockParticleOption(ParticleTypes.f_123794_, iblockstate),
                  this.m_20185_() + (double)this.f_19796_.m_188501_() - 0.5,
                  this.m_20186_() + (double)this.f_19796_.m_188501_() - 0.5,
                  this.m_20189_() + (double)this.f_19796_.m_188501_() - 0.5,
                  4.0 * ((double)this.f_19796_.m_188501_() - 0.5),
                  (double)this.f_19796_.m_188501_() * 5.0 + 0.5,
                  ((double)this.f_19796_.m_188501_() - 0.5) * 4.0
               );
         }
      }
   }

   private void strongKnockback(Entity p_33340_, double modifier) {
      double d0 = p_33340_.m_20185_() - this.m_20185_();
      double d1 = p_33340_.m_20189_() - this.m_20189_();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001) * 2.0;
      p_33340_.m_5997_(d0 * modifier / d2, 0.5 * modifier, d1 * modifier / d2);
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

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
