package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.entity.util.TidalTentacleUtil;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages.SpawnEntity;

public class Tidal_Tentacle_Entity extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> CREATOR_ID = SynchedEntityData.m_135353_(
      Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135041_
   );
   private static final EntityDataAccessor<Integer> FROM_ID = SynchedEntityData.m_135353_(Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> TARGET_COUNT = SynchedEntityData.m_135353_(Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> CURRENT_TARGET_ID = SynchedEntityData.m_135353_(
      Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135028_
   );
   private static final EntityDataAccessor<Float> PROGRESS = SynchedEntityData.m_135353_(Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.m_135353_(Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Boolean> RETRACTING = SynchedEntityData.m_135353_(Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> HAS_CLAW = SynchedEntityData.m_135353_(Tidal_Tentacle_Entity.class, EntityDataSerializers.f_135035_);
   private List<Entity> previouslyTouched = new ArrayList<>();
   private boolean hasChained = false;
   public float prevProgress = 0.0F;
   public static final float MAX_EXTEND_TIME = 5.0F;

   public Tidal_Tentacle_Entity(EntityType<?> type, Level level) {
      super(type, level);
   }

   public Tidal_Tentacle_Entity(SpawnEntity spawnEntity, Level world) {
      this((EntityType<?>)ModEntities.TIDAL_TENTACLE.get(), world);
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(CREATOR_ID, Optional.empty());
      this.f_19804_.m_135372_(FROM_ID, -1);
      this.f_19804_.m_135372_(TARGET_COUNT, 0);
      this.f_19804_.m_135372_(CURRENT_TARGET_ID, -1);
      this.f_19804_.m_135372_(PROGRESS, 0.0F);
      this.f_19804_.m_135372_(DAMAGE, 3.0F);
      this.f_19804_.m_135372_(RETRACTING, false);
      this.f_19804_.m_135372_(HAS_CLAW, true);
   }

   public void m_8119_() {
      float progress = this.getProgress();
      this.prevProgress = progress;
      super.m_8119_();
      Entity creator = this.getCreatorEntity();
      Entity current = this.getToEntity();
      if (this.f_19797_ == 1 && !this.f_19853_.f_46443_) {
         this.m_5496_((SoundEvent)ModSounds.TIDAL_TENTACLE.get(), 1.0F, 0.8F + this.f_19796_.m_188501_() * 0.4F);
      }

      if (!this.isRetracting() && progress < 5.0F) {
         this.setProgress(progress + 1.0F);
      }

      if (this.isRetracting() && progress > 0.0F) {
         this.setProgress(progress - 1.0F);
      }

      if (this.isRetracting() && progress == 0.0F) {
         if (this.getFromEntity() instanceof Tidal_Tentacle_Entity tendonSegment) {
            tendonSegment.setRetracting(true);
            this.updateLastTendon(tendonSegment);
         } else {
            this.updateLastTendon(null);
         }

         this.m_142687_(RemovalReason.DISCARDED);
      }

      if (creator instanceof LivingEntity && current != null) {
         Vec3 target = new Vec3(current.m_20185_(), current.m_20227_(0.4F), current.m_20189_());
         Vec3 lerp = target.m_82546_(this.m_20182_());
         this.m_20256_(lerp.m_82490_(0.5));
         if (!this.f_19853_.f_46443_ && progress >= 5.0F && this.f_19797_ % 2 == 0) {
            Entity entity = this.getCreatorEntity();
            if (entity instanceof LivingEntity
               && current != creator
               && current.m_6469_(DamageSource.m_19340_(this, (LivingEntity)entity), this.getBaseDamage())) {
               MobEffectInstance effectinstance1 = ((LivingEntity)current).m_21124_((MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get());
               int i = 1;
               if (effectinstance1 != null) {
                  i += effectinstance1.m_19564_();
                  ((LivingEntity)current).m_6234_((MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get());
               } else {
                  i--;
               }

               i = Mth.m_14045_(i, 0, 4);
               MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTABYSSAL_CURSE.get(), 60, i, false, true, true);
               ((LivingEntity)current).m_7292_(effectinstance);
               this.m_19970_((LivingEntity)creator, current);
            }
         }
      }

      Vec3 vector3d = this.m_20184_();
      if (!this.f_19853_.f_46443_ && !this.hasChained) {
         if (this.getTargetsHit() > 5) {
            this.setRetracting(true);
         } else if (creator instanceof LivingEntity && this.getProgress() >= 5.0F) {
            Entity closestValid = null;

            for (Entity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(12.0))) {
               if (!entity.equals(creator)
                  && !this.previouslyTouched.contains(entity)
                  && this.isValidTarget((LivingEntity)creator, entity)
                  && this.hasLineOfSight(entity)
                  && (closestValid == null || this.m_20270_(entity) < this.m_20270_(closestValid))) {
                  closestValid = entity;
               }
            }

            if (closestValid != null) {
               this.createChain(closestValid);
               this.hasChained = true;
            } else {
               this.setRetracting(true);
            }
         }
      }

      double d0 = this.m_20185_() + vector3d.f_82479_;
      double d1 = this.m_20186_() + vector3d.f_82480_;
      double d2 = this.m_20189_() + vector3d.f_82481_;
      this.m_20256_(vector3d.m_82490_(0.99F));
      this.m_6034_(d0, d1, d2);
   }

   private boolean isValidTarget(LivingEntity creator, Entity entity) {
      return !creator.m_7307_(entity) && !entity.m_7307_(creator) && entity instanceof Mob
         ? true
         : creator.m_21214_() != null && creator.m_21214_().m_20148_().equals(entity.m_20148_())
            || creator.m_21188_() != null && creator.m_21188_().m_20148_().equals(entity.m_20148_());
   }

   private double getDamageForItem(ItemStack itemStack) {
      Multimap<Attribute, AttributeModifier> map = itemStack.m_41638_(EquipmentSlot.MAINHAND);
      if (map.isEmpty()) {
         return 0.0;
      } else {
         double d = 0.0;

         for (AttributeModifier mod : map.get(Attributes.f_22281_)) {
            d += mod.m_22218_();
         }

         return d;
      }
   }

   private boolean hasLineOfSight(Entity entity) {
      if (entity.f_19853_ != this.f_19853_) {
         return false;
      } else {
         Vec3 vec3 = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
         Vec3 vec31 = new Vec3(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
         return vec31.m_82554_(vec3) > 128.0
            ? false
            : this.f_19853_.m_45547_(new ClipContext(vec3, vec31, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
      }
   }

   private void updateLastTendon(Tidal_Tentacle_Entity lastTendon) {
      Entity creator = this.getCreatorEntity();
      if (creator == null) {
         creator = this.f_19853_.m_46003_(this.getCreatorEntityUUID());
      }

      if (creator instanceof LivingEntity) {
         TidalTentacleUtil.setLastTentacle((LivingEntity)creator, lastTendon);
      }
   }

   private void createChain(Entity closestValid) {
      this.f_19804_.m_135381_(HAS_CLAW, false);
      Tidal_Tentacle_Entity child = (Tidal_Tentacle_Entity)((EntityType)ModEntities.TIDAL_TENTACLE.get()).m_20615_(this.f_19853_);
      child.previouslyTouched = new ArrayList<>(this.previouslyTouched);
      child.previouslyTouched.add(closestValid);
      child.setCreatorEntityUUID(this.getCreatorEntityUUID());
      child.setFromEntityID(this.m_19879_());
      child.setToEntityID(closestValid.m_19879_());
      child.m_6034_(closestValid.m_20185_(), closestValid.m_20227_(0.4F), closestValid.m_20189_());
      child.setTargetsHit(this.getTargetsHit() + 1);
      this.updateLastTendon(child);
      this.f_19853_.m_7967_(child);
   }

   private float getBaseDamage() {
      return (Float)this.f_19804_.m_135370_(DAMAGE);
   }

   public UUID getCreatorEntityUUID() {
      return (UUID)((Optional)this.f_19804_.m_135370_(CREATOR_ID)).orElse(null);
   }

   public void setCreatorEntityUUID(UUID id) {
      this.f_19804_.m_135381_(CREATOR_ID, Optional.ofNullable(id));
   }

   public Entity getCreatorEntity() {
      UUID uuid = this.getCreatorEntityUUID();
      return uuid != null && !this.f_19853_.f_46443_ ? ((ServerLevel)this.f_19853_).m_8791_(uuid) : null;
   }

   public int getFromEntityID() {
      return (Integer)this.f_19804_.m_135370_(FROM_ID);
   }

   public void setFromEntityID(int id) {
      this.f_19804_.m_135381_(FROM_ID, id);
   }

   public Entity getFromEntity() {
      return this.getFromEntityID() == -1 ? null : this.f_19853_.m_6815_(this.getFromEntityID());
   }

   public int getToEntityID() {
      return (Integer)this.f_19804_.m_135370_(CURRENT_TARGET_ID);
   }

   public void setToEntityID(int id) {
      this.f_19804_.m_135381_(CURRENT_TARGET_ID, id);
   }

   public Entity getToEntity() {
      return this.getToEntityID() == -1 ? null : this.f_19853_.m_6815_(this.getToEntityID());
   }

   public int getTargetsHit() {
      return (Integer)this.f_19804_.m_135370_(TARGET_COUNT);
   }

   public void setTargetsHit(int i) {
      this.f_19804_.m_135381_(TARGET_COUNT, i);
   }

   public float getProgress() {
      return (Float)this.f_19804_.m_135370_(PROGRESS);
   }

   public void setProgress(float progress) {
      this.f_19804_.m_135381_(PROGRESS, progress);
   }

   public boolean isRetracting() {
      return (Boolean)this.f_19804_.m_135370_(RETRACTING);
   }

   public void setRetracting(boolean retract) {
      this.f_19804_.m_135381_(RETRACTING, retract);
   }

   public boolean hasClaw() {
      return (Boolean)this.f_19804_.m_135370_(HAS_CLAW);
   }

   protected void m_7378_(CompoundTag p_20052_) {
   }

   protected void m_7380_(CompoundTag p_20139_) {
   }

   public boolean isCreator(Entity mob) {
      return this.getCreatorEntityUUID() != null && mob.m_20148_().equals(this.getCreatorEntityUUID());
   }
}
