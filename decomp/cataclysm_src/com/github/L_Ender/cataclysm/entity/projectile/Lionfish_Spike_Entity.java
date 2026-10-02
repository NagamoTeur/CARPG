package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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

public class Lionfish_Spike_Entity extends ThrowableItemProjectile {
   public Lionfish_Spike_Entity(EntityType<? extends Lionfish_Spike_Entity> type, Level world) {
      super(type, world);
   }

   public Lionfish_Spike_Entity(Level worldIn, LivingEntity throwerIn) {
      super((EntityType)ModEntities.LIONFISH_SPIKE.get(), throwerIn, worldIn);
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
   }

   protected Item m_7881_() {
      return (Item)ModItems.LIONFISH_SPIKE.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity shooter = this.m_37282_();
      Entity entity = result.m_82443_();
      float i = (float)CMConfig.BlazingBonedamage;
      if (shooter instanceof LivingEntity) {
         if (entity != shooter
            && !shooter.m_7307_(entity)
            && entity.m_6469_(DamageSource.m_19340_(this, (LivingEntity)shooter), i)
            && entity instanceof LivingEntity) {
            ((LivingEntity)entity).m_147207_(new MobEffectInstance(MobEffects.f_19614_, 60, 0), this);
         }
      } else {
         entity.m_6469_(DamageSource.m_19340_(this, null).m_19366_(), i);
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
                  new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack((ItemLike)ModItems.LIONFISH_SPIKE.get())),
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
