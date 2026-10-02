package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.capabilities.HookCapability;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.items.Tidal_Claws;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class Tidal_Hook_Entity extends AbstractArrow {
   private static final EntityDataAccessor<Integer> HOOKED_ENTITY_ID = SynchedEntityData.m_135353_(Tidal_Hook_Entity.class, EntityDataSerializers.f_135028_);
   private double maxRange = 0.0;
   private double maxSpeed = 0.0;
   private boolean isPulling = false;
   private Entity hookedEntity;
   private ItemStack stack;

   public Tidal_Hook_Entity(EntityType<? extends AbstractArrow> type, Player owner, Level world) {
      super(type, owner, world);
      this.m_20242_(true);
      this.m_36781_(0.0);
   }

   public Tidal_Hook_Entity(Level world, double x, double y, double z) {
      super((EntityType)ModEntities.TIDAL_HOOK.get(), x, y, z, world);
      this.m_20242_(true);
      this.m_36781_(0.0);
   }

   public Tidal_Hook_Entity(EntityType<Tidal_Hook_Entity> tidal_hook_entityEntityType, Level level) {
      super(tidal_hook_entityEntityType, level);
      this.m_20242_(true);
      this.m_36781_(0.0);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HOOKED_ENTITY_ID, 0);
   }

   public void m_8119_() {
      super.m_8119_();
      HookCapability.IHookCapability hookCapability = ModCapabilities.getCapability(this.m_37282_(), ModCapabilities.HOOK_CAPABILITY);
      if (this.m_37282_() instanceof LivingEntity owner) {
         if (hookCapability != null) {
            if (this.isPulling && this.f_19797_ % 3 == 0) {
               this.f_19853_.m_5594_(null, this.m_37282_().m_20183_(), (SoundEvent)ModSounds.TIDAL_HOOK_LOOP.get(), SoundSource.PLAYERS, 0.4F, 1.0F);
            }

            if (!this.f_19853_.f_46443_) {
               if (owner.m_21224_()
                  || !hookCapability.hasHook()
                  || (double)owner.m_20270_(this) > this.maxRange
                  || !(owner.m_21205_().m_41720_() instanceof Tidal_Claws) && !(owner.m_21206_().m_41720_() instanceof Tidal_Claws)) {
                  this.m_6074_();
               }

               if (this.hookedEntity != null) {
                  if (this.hookedEntity.m_213877_()) {
                     this.hookedEntity = null;
                     this.m_146870_();
                  } else {
                     this.m_6027_(this.hookedEntity.m_20185_(), this.hookedEntity.m_20227_(0.8), this.hookedEntity.m_20189_());
                  }
               }

               if (owner.m_21205_() != this.stack && owner.m_21206_() != this.stack) {
                  this.m_6074_();
               } else if (this.isPulling) {
                  Entity target = owner;
                  Entity origin = this;
                  if (owner.m_6144_() && this.hookedEntity != null) {
                     target = this.hookedEntity;
                     origin = owner;
                  }

                  double brakeZone = 6.0 * (this.maxSpeed / 10.0);
                  double pullSpeed = this.maxSpeed / 6.0;
                  Vec3 distance = origin.m_20182_().m_82546_(target.m_20182_().m_82520_(0.0, (double)(target.m_20206_() / 2.0F), 0.0));
                  Vec3 motion = distance.m_82541_().m_82490_(distance.m_82553_() < brakeZone ? pullSpeed * distance.m_82553_() / brakeZone : pullSpeed);
                  if (Math.abs(distance.f_82480_) < 0.1) {
                     motion = new Vec3(motion.f_82479_, 0.0, motion.f_82481_);
                  }

                  if (new Vec3(distance.f_82479_, 0.0, distance.f_82481_).m_82553_()
                     < new Vec3((double)(target.m_20205_() / 2.0F), 0.0, (double)(target.m_20205_() / 2.0F)).m_82553_() / 1.4) {
                     motion = new Vec3(0.0, motion.f_82480_, 0.0);
                  }

                  target.m_20256_(motion);
                  target.f_19864_ = true;
               }
            }
         } else {
            this.m_6074_();
         }
      }
   }

   public void m_6074_() {
      HookCapability.IHookCapability hookCapability = ModCapabilities.getCapability(this.m_37282_(), ModCapabilities.HOOK_CAPABILITY);
      if (!this.f_19853_.f_46443_ && this.m_37282_() instanceof LivingEntity owner && hookCapability != null) {
         hookCapability.setHasHook(false);
         owner.m_20242_(false);
      }

      super.m_6074_();
   }

   public boolean m_6783_(double distance) {
      return true;
   }

   protected float m_6882_() {
      return 1.0F;
   }

   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   protected void m_8060_(BlockHitResult blockHitResult) {
      super.m_8060_(blockHitResult);
      this.isPulling = true;
      if (!this.f_19853_.f_46443_ && this.m_37282_() instanceof Player owner && this.hookedEntity == null) {
         owner.m_20242_(true);
      }
   }

   protected SoundEvent m_7239_() {
      return (SoundEvent)ModSounds.TIDAL_HOOK_HIT.get();
   }

   protected void m_5790_(EntityHitResult entityHitResult) {
      if (!this.f_19853_.f_46443_
         && this.m_37282_() instanceof Player owner
         && entityHitResult.m_82443_() != owner
         && (entityHitResult.m_82443_() instanceof LivingEntity || entityHitResult.m_82443_() instanceof EnderDragonPart)
         && this.hookedEntity == null) {
         this.hookedEntity = entityHitResult.m_82443_();
         this.m_20088_().m_135381_(HOOKED_ENTITY_ID, this.hookedEntity.m_19879_() + 1);
         this.isPulling = true;
      }
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.maxRange = tag.m_128459_("maxRange");
      this.maxSpeed = tag.m_128459_("maxSpeed");
      this.isPulling = tag.m_128471_("isPulling");
      this.stack = ItemStack.m_41712_(tag.m_128469_("hookshotItem"));
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128347_("maxRange", this.maxRange);
      tag.m_128347_("maxSpeed", this.maxSpeed);
      tag.m_128379_("isPulling", this.isPulling);
      tag.m_128365_("hookshotItem", this.stack.m_41739_(new CompoundTag()));
   }

   public void setProperties(ItemStack stack, double maxRange, double maxVelocity, float pitch, float yaw, float roll, float modifierZ) {
      float f = (float) (Math.PI / 180.0);
      float x = -Mth.m_14031_(yaw * f) * Mth.m_14089_(pitch * f);
      float y = -Mth.m_14031_((pitch + roll) * f);
      float z = Mth.m_14089_(yaw * f) * Mth.m_14089_(pitch * f);
      this.m_6686_((double)x, (double)y, (double)z, modifierZ, 0.0F);
      this.stack = stack;
      this.maxRange = maxRange;
      this.maxSpeed = maxVelocity;
   }
}
