package io.redspace.ironsspellbooks.entity.mobs.frozen_humanoid;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleProjectile;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import java.util.Collections;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class FrozenHumanoid extends LivingEntity {
   protected static final EntityDataAccessor<Boolean> DATA_IS_BABY = SynchedEntityData.m_135353_(FrozenHumanoid.class, EntityDataSerializers.f_135035_);
   protected static final EntityDataAccessor<Boolean> DATA_IS_SITTING = SynchedEntityData.m_135353_(FrozenHumanoid.class, EntityDataSerializers.f_135035_);
   protected static final EntityDataAccessor<Float> DATA_FROZEN_SPEED = SynchedEntityData.m_135353_(FrozenHumanoid.class, EntityDataSerializers.f_135029_);
   protected static final EntityDataAccessor<Float> DATA_LIMB_SWING = SynchedEntityData.m_135353_(FrozenHumanoid.class, EntityDataSerializers.f_135029_);
   protected static final EntityDataAccessor<Float> DATA_LIMB_SWING_AMOUNT = SynchedEntityData.m_135353_(FrozenHumanoid.class, EntityDataSerializers.f_135029_);
   protected static final EntityDataAccessor<Float> DATA_ATTACK_TIME = SynchedEntityData.m_135353_(FrozenHumanoid.class, EntityDataSerializers.f_135029_);
   private float shatterProjectileDamage;
   private int deathTimer = -1;
   private UUID summonerUUID;
   private LivingEntity cachedSummoner;
   private boolean isAutoSpinAttack;
   private HumanoidArm mainArm = HumanoidArm.RIGHT;

   public FrozenHumanoid(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(DATA_IS_BABY, false);
      this.f_19804_.m_135372_(DATA_IS_SITTING, false);
      this.f_19804_.m_135372_(DATA_FROZEN_SPEED, 0.0F);
      this.f_19804_.m_135372_(DATA_LIMB_SWING, 0.0F);
      this.f_19804_.m_135372_(DATA_LIMB_SWING_AMOUNT, 0.0F);
      this.f_19804_.m_135372_(DATA_ATTACK_TIME, 0.0F);
   }

   public FrozenHumanoid(Level level, LivingEntity entityToCopy) {
      this((EntityType<? extends LivingEntity>)EntityRegistry.FROZEN_HUMANOID.get(), level);
      this.m_7678_(entityToCopy.m_20185_(), entityToCopy.m_20186_(), entityToCopy.m_20189_(), entityToCopy.m_146908_(), entityToCopy.m_146909_());
      if (entityToCopy.m_6162_()) {
         this.f_19804_.m_135381_(DATA_IS_BABY, true);
      }

      if (entityToCopy.m_20159_() && entityToCopy.m_20202_() != null && entityToCopy.m_20202_().shouldRiderSit()) {
         this.f_19804_.m_135381_(DATA_IS_SITTING, true);
      }

      this.m_5618_(entityToCopy.f_20883_);
      this.f_20884_ = this.f_20883_;
      this.m_5616_(entityToCopy.m_6080_());
      this.f_20886_ = this.f_20885_;
      float limbSwing = entityToCopy.f_20925_;
      float limbSwingAmount = entityToCopy.f_20924_;
      this.f_19804_.m_135381_(DATA_LIMB_SWING, limbSwing);
      this.f_19804_.m_135381_(DATA_LIMB_SWING_AMOUNT, limbSwingAmount);
      this.f_19804_.m_135381_(DATA_ATTACK_TIME, entityToCopy.f_20921_);
      this.m_20124_(entityToCopy.m_20089_());
      this.isAutoSpinAttack = entityToCopy.m_21209_();
      this.mainArm = entityToCopy.m_5737_();
      if (entityToCopy instanceof Player player) {
         this.m_6593_(player.m_5446_());
         this.m_20340_(true);
      } else {
         this.m_20340_(false);
      }

      this.setSummoner(entityToCopy);
   }

   public void setSummoner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.summonerUUID = owner.m_20148_();
         this.cachedSummoner = owner;
      }
   }

   public LivingEntity getSummoner() {
      if (this.cachedSummoner != null && this.cachedSummoner.m_6084_()) {
         return this.cachedSummoner;
      } else if (this.summonerUUID != null && this.f_19853_ instanceof ServerLevel) {
         if (((ServerLevel)this.f_19853_).m_8791_(this.summonerUUID) instanceof LivingEntity livingEntity) {
            this.cachedSummoner = livingEntity;
         }

         return this.cachedSummoner;
      } else {
         return null;
      }
   }

   public boolean isSitting() {
      return (Boolean)this.f_19804_.m_135370_(DATA_IS_SITTING);
   }

   public boolean m_6162_() {
      return (Boolean)this.f_19804_.m_135370_(DATA_IS_BABY);
   }

   public float getLimbSwing() {
      return (Float)this.f_19804_.m_135370_(DATA_LIMB_SWING);
   }

   public float getLimbSwingAmount() {
      return (Float)this.f_19804_.m_135370_(DATA_LIMB_SWING_AMOUNT);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.deathTimer > 0) {
         this.deathTimer--;
      }

      if (this.deathTimer == 0) {
         this.m_6469_(DamageSource.f_19317_, 100.0F);
      }
   }

   public void setDeathTimer(int timeInTicks) {
      this.deathTimer = timeInTicks;
   }

   public float getAttacktime() {
      return (Float)this.f_19804_.m_135370_(DATA_ATTACK_TIME);
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean m_5829_() {
      return true;
   }

   public boolean m_6087_() {
      return true;
   }

   @org.jetbrains.annotations.Nullable
   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_11983_;
   }

   @org.jetbrains.annotations.Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_11983_;
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      if (!this.f_19853_.f_46443_ && !this.m_6673_(pSource)) {
         this.spawnIcicleShards(this.m_146892_(), this.shatterProjectileDamage);
         this.m_6677_(pSource);
         this.m_146870_();
         return true;
      } else {
         return false;
      }
   }

   private void spawnIcicleShards(Vec3 origin, float damage) {
      int count = 8;
      int offset = 360 / count;

      for (int i = 0; i < count; i++) {
         Vec3 motion = new Vec3(0.0, 0.0, 0.55);
         motion = motion.m_82496_((float) (Math.PI / 6));
         motion = motion.m_82524_((float)(offset * i) * (float) (Math.PI / 180.0));
         IcicleProjectile shard = new IcicleProjectile(this.f_19853_, this.getSummoner());
         shard.setDamage(damage);
         shard.m_20256_(motion);
         Vec3 spawn = origin.m_82549_(motion.m_82542_(1.0, 0.0, 1.0).m_82541_().m_82490_(0.5));
         Vec2 angle = Utils.rotationFromDirection(motion);
         shard.m_7678_(spawn.f_82479_, spawn.f_82480_ - shard.m_20191_().m_82376_() / 2.0, spawn.f_82481_, angle.f_82471_, angle.f_82470_);
         this.f_19853_.m_7967_(shard);
      }
   }

   public void setShatterDamage(float damage) {
      this.shatterProjectileDamage = damage;
   }

   public boolean m_21209_() {
      return this.isAutoSpinAttack;
   }

   public Iterable<ItemStack> m_6168_() {
      return Collections.singleton(ItemStack.f_41583_);
   }

   public ItemStack m_6844_(EquipmentSlot pSlot) {
      return ItemStack.f_41583_;
   }

   public void m_8061_(EquipmentSlot pSlot, ItemStack pStack) {
   }

   public void m_7378_(CompoundTag compoundTag) {
      super.m_7378_(compoundTag);
      if (compoundTag.m_128403_("Summoner")) {
         this.summonerUUID = compoundTag.m_128342_("Summoner");
      }
   }

   public void m_7380_(CompoundTag compoundTag) {
      super.m_7380_(compoundTag);
      if (this.summonerUUID != null) {
         compoundTag.m_128362_("Summoner", this.summonerUUID);
      }
   }

   public HumanoidArm m_5737_() {
      return this.mainArm;
   }

   public static Builder prepareAttributes() {
      return LivingEntity.m_21183_()
         .m_22268_(Attributes.f_22281_, 0.0)
         .m_22268_(Attributes.f_22276_, 1.0)
         .m_22268_(Attributes.f_22277_, 0.0)
         .m_22268_(Attributes.f_22278_, 100.0)
         .m_22268_(Attributes.f_22279_, 0.0);
   }
}
