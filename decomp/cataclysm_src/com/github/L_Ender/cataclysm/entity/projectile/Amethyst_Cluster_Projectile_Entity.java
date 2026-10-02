package com.github.L_Ender.cataclysm.entity.projectile;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Amethyst_Cluster_Projectile_Entity extends ThrowableProjectile {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(
      Amethyst_Cluster_Projectile_Entity.class, EntityDataSerializers.f_135029_
   );

   public Amethyst_Cluster_Projectile_Entity(EntityType<Amethyst_Cluster_Projectile_Entity> type, Level world) {
      super(type, world);
   }

   public Amethyst_Cluster_Projectile_Entity(EntityType<Amethyst_Cluster_Projectile_Entity> type, Level world, LivingEntity thrower, float damage) {
      super(type, thrower, world);
      this.setDamage(damage);
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity shooter = this.m_37282_();
      Entity entity = result.m_82443_();
      if (shooter instanceof LivingEntity) {
         if (entity != shooter && !shooter.m_7307_(entity)) {
            entity.m_6469_(DamageSource.m_19340_(this, (LivingEntity)shooter).m_19366_(), this.getDamage());
         }
      } else {
         entity.m_6469_(DamageSource.f_19319_, this.getDamage());
      }
   }

   protected void m_6532_(HitResult result) {
      super.m_6532_(result);
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_7605_(this, (byte)3);
         this.m_5496_(SoundEvents.f_11983_, 1.1F, 0.8F);
         this.m_146870_();
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

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("damage", this.getDamage());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setDamage(compound.m_128457_("damage"));
   }

   protected float m_7139_() {
      return 0.03F;
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      if (id == 3) {
         for (int i = 0; i < 20; i++) {
            this.f_19853_
               .m_7106_(
                  new BlockParticleOption(ParticleTypes.f_123794_, Blocks.f_152492_.m_49966_()),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  this.f_19796_.m_188583_() * 0.2,
                  this.f_19796_.m_188583_() * 0.2,
                  this.f_19796_.m_188583_() * 0.2
               );
         }
      }
   }
}
