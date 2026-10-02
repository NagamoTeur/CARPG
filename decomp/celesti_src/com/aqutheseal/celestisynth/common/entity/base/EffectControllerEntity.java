package com.aqutheseal.celestisynth.common.entity.base;

import com.aqutheseal.celestisynth.api.item.CSWeapon;
import com.aqutheseal.celestisynth.common.registry.CSSoundEvents;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public abstract class EffectControllerEntity extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(
      EffectControllerEntity.class, EntityDataSerializers.f_135041_
   );
   private static final EntityDataAccessor<Float> ANGLE_X = SynchedEntityData.m_135353_(EffectControllerEntity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> ANGLE_Y = SynchedEntityData.m_135353_(EffectControllerEntity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> ANGLE_Z = SynchedEntityData.m_135353_(EffectControllerEntity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> ANGLE_ADD_X = SynchedEntityData.m_135353_(EffectControllerEntity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> ANGLE_ADD_Y = SynchedEntityData.m_135353_(EffectControllerEntity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> ANGLE_ADD_Z = SynchedEntityData.m_135353_(EffectControllerEntity.class, EntityDataSerializers.f_135029_);
   public static final SoundEvent[] BASE_WEAPON_EFFECTS = new SoundEvent[]{
      (SoundEvent)CSSoundEvents.CS_SWORD_SWING.get(),
      (SoundEvent)CSSoundEvents.CS_SWORD_SWING_FIRE.get(),
      (SoundEvent)CSSoundEvents.CS_AIR_SWING.get(),
      (SoundEvent)CSSoundEvents.CS_SWORD_CLASH.get(),
      (SoundEvent)CSSoundEvents.CS_FIRE_SHOOT.get(),
      (SoundEvent)CSSoundEvents.CS_IMPACT_HIT.get()
   };

   public EffectControllerEntity(EntityType<?> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public void playRandomBladeSound(Entity entity, int length) {
      SoundEvent randomSound = BASE_WEAPON_EFFECTS[entity.f_19853_.m_213780_().m_188503_(length)];
      entity.m_5496_(randomSound, 0.35F, 0.5F + new Random().nextFloat());
   }

   public void m_8119_() {
      super.m_8119_();
      UUID ownerUuid = this.getOwnerUuid();
      Player ownerPlayer = ownerUuid == null ? null : this.m_9236_().m_46003_(ownerUuid);
      if (ownerPlayer == null || ownerPlayer.m_21224_()) {
         this.m_142687_(RemovalReason.DISCARDED);
      }
   }

   public abstract Item getCorrespondingItem();

   public CSWeapon fromInterfaceWeapon() {
      Item var2 = this.getCorrespondingItem();
      if (var2 instanceof CSWeapon) {
         return (CSWeapon)var2;
      } else {
         throw new IllegalStateException("Item [" + this.getCorrespondingItem() + "] is an invalid Celestisynth weapon for [" + this.m_5446_() + "].");
      }
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(OWNER_UUID, Optional.empty());
      this.f_19804_.m_135372_(ANGLE_X, 0.0F);
      this.f_19804_.m_135372_(ANGLE_Y, 0.0F);
      this.f_19804_.m_135372_(ANGLE_Z, 0.0F);
      this.f_19804_.m_135372_(ANGLE_ADD_X, 0.0F);
      this.f_19804_.m_135372_(ANGLE_ADD_Y, 0.0F);
      this.f_19804_.m_135372_(ANGLE_ADD_Z, 0.0F);
   }

   public void m_7378_(CompoundTag compoundNBT) {
      this.m_142687_(RemovalReason.DISCARDED);
   }

   public void m_7380_(CompoundTag compoundNBT) {
      this.m_142687_(RemovalReason.DISCARDED);
   }

   public void setOwnerUuid(@Nullable UUID ownerUuid) {
      this.f_19804_.m_135381_(OWNER_UUID, Optional.ofNullable(ownerUuid));
   }

   @Nullable
   public UUID getOwnerUuid() {
      return (UUID)((Optional)this.f_19804_.m_135370_(OWNER_UUID)).orElse(null);
   }

   public void setAngleX(float angleX) {
      this.f_19804_.m_135381_(ANGLE_X, angleX);
   }

   public float getAngleX() {
      return (Float)this.f_19804_.m_135370_(ANGLE_X);
   }

   public void setAngleY(float angleY) {
      this.f_19804_.m_135381_(ANGLE_Y, angleY);
   }

   public float getAngleY() {
      return (Float)this.f_19804_.m_135370_(ANGLE_Y);
   }

   public void setAngleZ(float angleZ) {
      this.f_19804_.m_135381_(ANGLE_Z, angleZ);
   }

   public float getAngleZ() {
      return (Float)this.f_19804_.m_135370_(ANGLE_Z);
   }

   public void setAddAngleX(float angleX) {
      this.f_19804_.m_135381_(ANGLE_ADD_X, angleX);
   }

   public float getAddAngleX() {
      return (Float)this.f_19804_.m_135370_(ANGLE_ADD_X);
   }

   public void setAddAngleY(float angleY) {
      this.f_19804_.m_135381_(ANGLE_ADD_Y, angleY);
   }

   public float getAddAngleY() {
      return (Float)this.f_19804_.m_135370_(ANGLE_ADD_Y);
   }

   public void setAddAngleZ(float angleZ) {
      this.f_19804_.m_135381_(ANGLE_ADD_Z, angleZ);
   }

   public float getAddAngleZ() {
      return (Float)this.f_19804_.m_135370_(ANGLE_ADD_Z);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
