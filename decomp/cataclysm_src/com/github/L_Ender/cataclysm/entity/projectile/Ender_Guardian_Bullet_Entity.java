package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import javax.annotation.Nonnull;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

public class Ender_Guardian_Bullet_Entity extends AbstractHurtingProjectile {
   private double dirX;
   private double dirY;
   private double dirZ;
   private double startX;
   private double startY;
   private double startZ;
   private int timer;
   private boolean fired;

   public Ender_Guardian_Bullet_Entity(EntityType<? extends Ender_Guardian_Bullet_Entity> type, Level world) {
      super(type, world);
   }

   public Ender_Guardian_Bullet_Entity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
      super((EntityType)ModEntities.ENDER_GUARDIAN_BULLET.get(), shooter, accelX, accelY, accelZ, worldIn);
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      if (!this.f_19853_.f_46443_ && this.fired) {
         Entity entity = result.m_82443_();
         Entity Shooter = this.m_37282_();
         LivingEntity livingentity = Shooter instanceof LivingEntity ? (LivingEntity)Shooter : null;
         boolean flag = entity.m_6469_(DamageSource.m_19340_(this, livingentity).m_19366_(), 6.0F);
         if (flag) {
            this.m_19970_(livingentity, entity);
            if (entity instanceof LivingEntity) {
               ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19620_, 100));
            }
         }
      }
   }

   protected void m_8060_(BlockHitResult result) {
      super.m_8060_(result);
      if (this.fired) {
         ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123813_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2, 0.2, 0.2, 0.2, 0.0);
         this.m_5496_(SoundEvents.f_12410_, 1.0F, 1.0F);
      }
   }

   protected void m_6532_(HitResult result) {
      super.m_6532_(result);
      if (this.fired) {
         this.m_146870_();
      }
   }

   public void setUp(int delay, double dirX, double dirY, double dirZ, double startX, double startY, double startZ) {
      this.fired = false;
      this.timer = delay;
      this.dirX = dirX;
      this.dirY = dirY;
      this.dirZ = dirZ;
      this.startX = startX;
      this.startY = startY;
      this.startZ = startZ;
   }

   public void setUpTowards(int delay, double startX, double startY, double startZ, double endX, double endY, double endZ, double speed) {
      Vec3 vec = new Vec3(endX - startX, endY - startY, endZ - startZ).m_82541_().m_82490_(speed);
      this.setUp(delay, vec.f_82479_, vec.f_82480_, vec.f_82481_, startX, startY, startZ);
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_) {
         this.timer--;
         if (this.timer <= 0) {
            if (this.fired) {
               this.m_146870_();
            } else {
               this.fired = true;
               this.m_20256_(new Vec3(0.0, 0.0, 0.0));
               this.timer = 30;
            }
         }

         Vec3 DeltaMovement = this.m_20184_();
         double d0 = this.m_20185_();
         double d1 = this.m_20186_();
         double d2 = this.m_20189_();
         if (this.fired) {
            if (DeltaMovement.m_82556_() <= 16.0) {
               this.m_20256_(DeltaMovement.m_82520_(this.dirX * 0.1, this.dirY * 0.1, this.dirZ * 0.1));
            }
         } else {
            this.m_20256_(new Vec3(this.startX - d0, this.startY - d1, this.startZ - d2).m_82490_(1.0 / (double)this.timer));
         }
      }

      Entity shooter = this.m_37282_();
      if (this.f_19853_.f_46443_ || (shooter == null || !shooter.m_213877_()) && this.f_19853_.m_46805_(this.m_20183_())) {
         HitResult HitResult = ProjectileUtil.m_37294_(this, x$0 -> this.m_5603_(x$0));
         if (HitResult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, HitResult)) {
            this.m_6532_(HitResult);
         }

         this.m_20101_();
         Vec3 Vec3 = this.m_20184_();
         double d0 = this.m_20185_() + Vec3.f_82479_;
         double d1 = this.m_20186_() + Vec3.f_82480_;
         double d2 = this.m_20189_() + Vec3.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         this.f_19853_
            .m_7106_(
               ParticleTypes.f_123810_, this.m_20185_() - Vec3.f_82479_, this.m_20186_() - Vec3.f_82480_ + 0.15, this.m_20189_() - Vec3.f_82481_, 0.0, 0.0, 0.0
            );
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_146870_();
      }
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128347_("DX", this.dirX);
      compound.m_128347_("DY", this.dirY);
      compound.m_128347_("DZ", this.dirZ);
      compound.m_128347_("SX", this.startX);
      compound.m_128347_("SY", this.startY);
      compound.m_128347_("SZ", this.startZ);
      compound.m_128405_("Timer", this.timer);
      compound.m_128379_("Fired", this.fired);
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.dirX = compound.m_128459_("DX");
      this.dirY = compound.m_128459_("DY");
      this.dirZ = compound.m_128459_("DZ");
      this.startX = compound.m_128459_("SX");
      this.startY = compound.m_128459_("SY");
      this.startZ = compound.m_128459_("SZ");
      this.timer = compound.m_128451_("Timer");
      this.fired = compound.m_128471_("Fired");
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public boolean m_5829_() {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (!this.f_19853_.f_46443_ && this.fired) {
         this.m_5496_(SoundEvents.f_12411_, 1.0F, 1.0F);
         ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123797_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 15, 0.2, 0.2, 0.2, 0.0);
         this.m_146870_();
      }

      return true;
   }

   @Nonnull
   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
