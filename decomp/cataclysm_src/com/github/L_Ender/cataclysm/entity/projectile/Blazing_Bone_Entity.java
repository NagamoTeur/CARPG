package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

public class Blazing_Bone_Entity extends ThrowableItemProjectile {
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Blazing_Bone_Entity.class, EntityDataSerializers.f_135029_);

   public Blazing_Bone_Entity(EntityType<? extends Blazing_Bone_Entity> type, Level world) {
      super(type, world);
   }

   public Blazing_Bone_Entity(Level worldIn, float damage, LivingEntity throwerIn) {
      super((EntityType)ModEntities.BLAZING_BONE.get(), throwerIn, worldIn);
      this.setDamage(damage);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(DAMAGE, 0.0F);
   }

   public float getDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public void setDamage(float damage) {
      this.f_19804_.m_135381_(DAMAGE, damage);
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128350_("damage", this.getDamage());
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.setDamage(tag.m_128457_("damage"));
   }

   protected Item m_7881_() {
      return (Item)ModItems.BLAZING_BONE.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
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

   public boolean m_20068_() {
      return false;
   }

   protected void m_6532_(HitResult result) {
      super.m_6532_(result);
      if (!this.f_19853_.f_46443_) {
         this.f_19853_.m_7605_(this, (byte)3);
         this.m_146870_();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7822_(byte id) {
      if (id == 3) {
         for (int i = 0; i < 8; i++) {
            this.f_19853_
               .m_7106_(
                  new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack((ItemLike)ModItems.BLAZING_BONE.get())),
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
