package com.aqutheseal.celestisynth.common.entity.base;

import com.aqutheseal.celestisynth.Celestisynth;
import com.aqutheseal.celestisynth.common.entity.helper.CSVisualType;
import com.aqutheseal.celestisynth.common.registry.CSEntityTypes;
import com.aqutheseal.celestisynth.common.registry.CSVisualTypes;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CSEffectEntity extends Entity implements IAnimatable {
   private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135041_);
   private static final EntityDataAccessor<String> VISUAL_ID = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135030_);
   private static final EntityDataAccessor<String> ANIMATION_ID = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135030_);
   private static final EntityDataAccessor<Integer> FRAME_LEVEL = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> SET_ROT_X = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> SET_ROT_Z = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Float> CUSTOMIZABLE_SIZE = SynchedEntityData.m_135353_(CSEffectEntity.class, EntityDataSerializers.f_135029_);
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private static final AnimationBuilder DEFAULT_ANIMATION = new AnimationBuilder().addAnimation("animation.cs_effect.spin", EDefaultLoopTypes.LOOP);
   private Entity toFollow;
   private int lifespan;
   private int frameTimer;
   private final AnimationController<CSEffectEntity> mainController = new AnimationController(this, "main_controller", 2.0F, this::mainPredicate);

   public CSEffectEntity(EntityType<? extends CSEffectEntity> type, Level level) {
      super(type, level);
      this.f_19811_ = true;
   }

   private <E extends IAnimatable> PlayState mainPredicate(AnimationEvent<E> event) {
      if (this.getAnimationID() != null && this.getVisualType().getAnimation() != null) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation(this.getAnimationID(), EDefaultLoopTypes.LOOP));
         if (event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
         }
      } else {
         Celestisynth.LOGGER.warn("Animation is null!");
         event.getController().setAnimation(DEFAULT_ANIMATION);
      }

      return PlayState.CONTINUE;
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(this.mainController);
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public CSVisualType getVisualType() {
      if (this.getVisualID() != null && !this.getVisualID().equals("none")) {
         for (RegistryObject<CSVisualType> visual : CSVisualTypes.VISUALS.getEntries()) {
            if (this.getVisualID().equals(((CSVisualType)visual.get()).getName())) {
               return (CSVisualType)visual.get();
            }
         }
      }

      return this.getDefaultVisual();
   }

   public void setVisualType(CSVisualType getEffectType) {
      this.setVisualID(getEffectType.getName());
   }

   public String getVisualID() {
      return (String)this.f_19804_.m_135370_(VISUAL_ID);
   }

   public void setVisualID(String value) {
      this.f_19804_.m_135381_(VISUAL_ID, value);
   }

   public String getAnimationID() {
      return (String)this.f_19804_.m_135370_(ANIMATION_ID);
   }

   public void setAnimationID(String value) {
      this.f_19804_.m_135381_(ANIMATION_ID, value);
   }

   public int getFrameLevel() {
      return (Integer)this.f_19804_.m_135370_(FRAME_LEVEL);
   }

   public void setFrameLevel(int value) {
      this.f_19804_.m_135381_(FRAME_LEVEL, value);
   }

   public void setRotationX(int rotationX) {
      this.f_19804_.m_135381_(SET_ROT_X, rotationX);
   }

   public void setRotationZ(int rotationZ) {
      this.f_19804_.m_135381_(SET_ROT_Z, rotationZ);
   }

   public int getRotationX() {
      return (Integer)this.f_19804_.m_135370_(SET_ROT_X);
   }

   public int getRotationZ() {
      return (Integer)this.f_19804_.m_135370_(SET_ROT_Z);
   }

   public float getCustomizableSize() {
      return (Float)this.f_19804_.m_135370_(CUSTOMIZABLE_SIZE);
   }

   public void setCustomizableSize(float size) {
      this.f_19804_.m_135381_(CUSTOMIZABLE_SIZE, size);
   }

   public void setOwnerUuid(@Nullable UUID ownerUuid) {
      this.f_19804_.m_135381_(OWNER_UUID, Optional.ofNullable(ownerUuid));
   }

   @Nullable
   public UUID getOwnerUuid() {
      return (UUID)((Optional)this.f_19804_.m_135370_(OWNER_UUID)).orElse(null);
   }

   public Entity getToFollow() {
      return this.toFollow;
   }

   public void setToFollow(Entity livingEntity) {
      this.toFollow = livingEntity;
   }

   public void setLifespan(int value) {
      this.lifespan = value;
   }

   @Nullable
   public static CSEffectEntity getEffectInstance(Player owner, @Nullable Entity toFollow, CSVisualType visual, double offsetX, double offsetY, double offsetZ) {
      if (owner == null) {
         return null;
      } else {
         CSEffectEntity slash = (CSEffectEntity)((EntityType)CSEntityTypes.CS_EFFECT.get()).m_20615_(owner.f_19853_);
         slash.setVisualID(visual.getName());
         slash.setAnimationID(visual.getAnimation().getAnimName());
         if (toFollow != null) {
            slash.m_6027_(toFollow.m_20185_() + offsetX, toFollow.m_20186_() - 1.5 + offsetY, toFollow.m_20189_() + offsetZ);
         } else {
            slash.m_6027_(owner.m_20185_() + offsetX, owner.m_20186_() - 1.5 + offsetY, owner.m_20189_() + offsetZ);
         }

         slash.setOwnerUuid(owner.m_20148_());
         slash.setToFollow(toFollow);
         slash.setRandomRotation();
         slash.m_146926_(owner.m_146909_());
         slash.f_19860_ = slash.m_146909_();
         slash.m_146922_(owner.m_146908_());
         slash.f_19859_ = slash.m_146908_();
         slash.m_19915_(slash.m_146908_(), slash.m_146909_());
         return slash;
      }
   }

   public void setRandomRotation() {
      int rotationX = this.f_19796_.m_188503_(360);
      int rotationZ = this.f_19796_.m_188503_(360);
      this.setRotationX(rotationX);
      this.setRotationZ(rotationZ);
   }

   public static void createInstance(Player owner, @Nullable Entity toFollow, CSVisualType effectTypes) {
      createInstance(owner, toFollow, effectTypes, 0.0, 0.0, 0.0);
   }

   public static void createInstance(Player owner, @Nullable Entity toFollow, CSVisualType effectTypes, double xOffset, double yOffset, double zOffset) {
      if (owner != null) {
         CSEffectEntity slash = getEffectInstance(owner, toFollow, effectTypes, xOffset, yOffset, zOffset);
         slash.setOwnerUuid(owner.m_20148_());
         slash.setToFollow(toFollow);
         owner.f_19853_.m_7967_(slash);
      }
   }

   public void m_8119_() {
      if (this.getOwnerUuid() == null) {
         this.m_142687_(RemovalReason.DISCARDED);
         Celestisynth.LOGGER.debug("Removed CSEffect with no owner!");
      }

      this.frameTimer++;
      if (this.frameTimer >= this.getVisualType().getFramesSpeed()) {
         this.setFrameLevel(this.getFrameLevel() == this.getVisualType().getFrames() ? 1 : this.getFrameLevel() + 1);
         this.frameTimer = 0;
      }

      if (this.toFollow != null && this.getVisualType() == CSVisualTypes.AQUAFLORA_FLOWER_BIND.get()) {
         this.calculateCustomizableSize(
            (float)((double)this.toFollow.m_20205_() - (double)this.toFollow.m_20205_() / 4.5), (double)(-this.getCustomizableSize())
         );
      }

      this.lifespan++;
      if (this.lifespan >= this.getVisualType().getAnimation().getLifespan()) {
         this.setLifespan(0);
         this.m_142687_(RemovalReason.DISCARDED);
      }

      super.m_8119_();
   }

   public void calculateCustomizableSize(float size, double yOff) {
      this.m_146922_(this.toFollow.m_146908_());
      this.f_19859_ = this.m_146908_();
      this.m_19915_(this.m_146908_(), this.m_146909_());
      this.setCustomizableSize(size);
      this.m_6027_(this.toFollow.m_20185_(), this.toFollow.m_20186_() + yOff, this.toFollow.m_20189_());
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(OWNER_UUID, Optional.empty());
      this.f_19804_.m_135372_(VISUAL_ID, "none");
      this.f_19804_.m_135372_(ANIMATION_ID, "none");
      this.f_19804_.m_135372_(FRAME_LEVEL, 1);
      this.f_19804_.m_135372_(SET_ROT_X, 0);
      this.f_19804_.m_135372_(SET_ROT_Z, 0);
      this.f_19804_.m_135372_(CUSTOMIZABLE_SIZE, 1.0F);
   }

   public void m_7378_(CompoundTag compoundNBT) {
      this.m_142687_(RemovalReason.DISCARDED);
   }

   public void m_7380_(CompoundTag compoundNBT) {
      this.m_142687_(RemovalReason.DISCARDED);
   }

   public CSVisualType getDefaultVisual() {
      return (CSVisualType)CSVisualTypes.SOLARIS_BLITZ.get();
   }

   public Packet<?> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
