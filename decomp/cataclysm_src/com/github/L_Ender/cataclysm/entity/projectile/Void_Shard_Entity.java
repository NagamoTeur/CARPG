package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class Void_Shard_Entity extends ThrowableItemProjectile {
   private BlockState lastState;
   private Entity ignoreEntity = null;

   public Void_Shard_Entity(EntityType<? extends Void_Shard_Entity> type, Level world) {
      super(type, world);
   }

   public Void_Shard_Entity(EntityType type, Level worldIn, LivingEntity throwerIn) {
      super(type, throwerIn, worldIn);
   }

   public Void_Shard_Entity(Level worldIn, LivingEntity throwerIn, double x, double y, double z, Vec3 movement, @Nullable Entity ignore) {
      super((EntityType)ModEntities.VOID_SHARD.get(), x, y, z, worldIn);
      this.m_5602_(throwerIn);
      this.m_20256_(movement);
      this.ignoreEntity = ignore;
   }

   public Void_Shard_Entity(SpawnEntity spawnEntity, Level world) {
      this((EntityType<? extends Void_Shard_Entity>)ModEntities.VOID_SHARD.get(), world);
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      if (this.lastState != null) {
         tag.m_128365_("inBlockState", NbtUtils.m_129202_(this.lastState));
      }
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      if (tag.m_128425_("inBlockState", 10)) {
         this.lastState = NbtUtils.m_129241_(tag.m_128469_("inBlockState"));
      }
   }

   protected Item m_7881_() {
      return (Item)ModItems.VOID_SHARD.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8060_(BlockHitResult hit) {
      this.lastState = this.f_19853_.m_8055_(hit.m_82425_());
      super.m_8060_(hit);
      Vec3 Vec3 = hit.m_82450_().m_82492_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      this.m_20256_(Vec3);
      Vec3 Vec31 = Vec3.m_82541_().m_82490_((double)this.m_7139_());
      this.m_20343_(this.m_20185_() - Vec31.f_82479_, this.m_20186_() - Vec31.f_82480_, this.m_20189_() - Vec31.f_82481_);
   }

   protected void m_5790_(EntityHitResult result) {
      super.m_5790_(result);
      Entity shooter = this.m_37282_();
      Entity entity = result.m_82443_();
      float i = 1.5F;
      if (shooter == null) {
         entity.m_6469_(DamageSource.f_19319_, i);
         entity.f_19802_ = 0;
      } else if (entity != shooter && !shooter.m_7307_(entity)) {
         entity.m_6469_(DamageSource.m_19367_(this, this.m_37282_()), i);
         entity.f_19802_ = 0;
      }
   }

   public void m_37251_(Entity p_234612_1_, float p_234612_2_, float p_234612_3_, float p_234612_4_, float p_234612_5_, float p_234612_6_) {
      float f = (float)(-Math.sin((double)(p_234612_3_ * (float) (Math.PI / 180.0))) * Math.cos((double)(p_234612_2_ * (float) (Math.PI / 180.0))));
      float f1 = (float)(-Math.sin((double)((p_234612_2_ + p_234612_4_) * (float) (Math.PI / 180.0))));
      float f2 = (float)(Math.cos((double)(p_234612_3_ * (float) (Math.PI / 180.0))) * Math.cos((double)(p_234612_2_ * (float) (Math.PI / 180.0))));
      this.m_6686_((double)f, (double)f1, (double)f2, p_234612_5_, p_234612_6_);
      Vec3 Vec3 = p_234612_1_.m_20184_();
      this.m_20256_(this.m_20184_().m_82520_(Vec3.f_82479_, p_234612_1_.m_20096_() ? 0.0 : Vec3.f_82480_, Vec3.f_82481_));
   }

   protected boolean m_5603_(Entity entity) {
      return entity == this.ignoreEntity ? false : super.m_5603_(entity);
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
                  new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack((ItemLike)ModItems.VOID_SHARD.get())),
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  this.f_19796_.m_188583_() * 0.1,
                  this.f_19796_.m_188583_() * 0.1,
                  this.f_19796_.m_188583_() * 0.1
               );
         }
      }
   }
}
