package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.EntityBounds;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.entity.MultipartAwareEntity;
import com.cerbon.bosses_of_mass_destruction.api.multipart_entities.util.CompoundOrientedBox;
import com.cerbon.bosses_of_mass_destruction.capability.util.BMDCapabilities;
import com.cerbon.bosses_of_mass_destruction.config.mob.GauntletConfig;
import com.cerbon.bosses_of_mass_destruction.entity.damage.CompositeDamageHandler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeDataAccessorHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.CompositeEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.EffectsImmunity;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.bosses_of_mass_destruction.util.VanillaCopiesServer;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;

public class GauntletEntity extends BaseEntity implements MultipartAwareEntity {
   public final GauntletHitboxes hitboxHelper = new GauntletHitboxes(this);
   public final GauntletClientLaserHandler laserHandler = new GauntletClientLaserHandler(this, this.postTickEvents);
   public final GauntletClientEnergyShieldHandler energyShieldHandler = new GauntletClientEnergyShieldHandler(this, this.postTickEvents);
   public final GauntletBlindnessIndicatorParticles clientBlindnessHandler = new GauntletBlindnessIndicatorParticles(this, this.preTickEvents);
   public final DamageMemory damageMemory = new DamageMemory(5, this);
   private final AnimationHolder animationHandler;
   public static final EntityDataAccessor<Integer> laserTarget = SynchedEntityData.m_135353_(GauntletEntity.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Boolean> isEnergized = SynchedEntityData.m_135353_(GauntletEntity.class, EntityDataSerializers.f_135035_);

   public GauntletEntity(EntityType<? extends PathfinderMob> entityType, Level level, GauntletConfig mobConfig) {
      super(entityType, level);
      GauntletGoalHandler gauntletGoalHandler = new GauntletGoalHandler(this, this.f_21345_, this.f_21346_, this.postTickEvents, mobConfig);
      this.animationHandler = new AnimationHolder(
         this,
         Map.of(
            (byte)4,
            new AnimationHolder.Animation("punch_start", "punch_loop"),
            (byte)5,
            new AnimationHolder.Animation("punch_stop", "idle"),
            (byte)7,
            new AnimationHolder.Animation("pound_stop", "idle"),
            (byte)8,
            new AnimationHolder.Animation("laser_eye_start", "laser_eye_loop"),
            (byte)9,
            new AnimationHolder.Animation("laser_eye_stop", "idle"),
            (byte)10,
            new AnimationHolder.Animation("swirl_punch", "idle"),
            (byte)11,
            new AnimationHolder.Animation("cast", "idle"),
            (byte)3,
            new AnimationHolder.Animation("death", "idle")
         ),
         (byte)6,
         5
      );
      this.f_19811_ = true;
      this.laserHandler.initDataTracker();
      this.energyShieldHandler.initDataTracker();
      this.damageHandler = new CompositeDamageHandler(this.hitboxHelper, gauntletGoalHandler, this.damageMemory);
      this.entityEventHandler = new CompositeEntityEventHandler(this.animationHandler, this.laserHandler, this.clientBlindnessHandler);
      this.dataAccessorHandler = new CompositeDataAccessorHandler(this.laserHandler, this.energyShieldHandler);
      this.clientTick = this.laserHandler;
      this.serverTick = serverLevel -> {
         if (this.m_5448_() == null) {
            this.m_5634_(mobConfig.idleHealingPerTick);
         }
      };
      this.bossBar = new ServerBossEvent(this.m_5446_(), BossBarColor.RED, BossBarOverlay.NOTCHED_6);
      this.mobEffectHandler = new EffectsImmunity(MobEffects.f_19615_, MobEffects.f_19614_);
      this.moveHandler = gauntletGoalHandler;
      this.nbtHandler = gauntletGoalHandler;
      this.deathClientTick = new ClientGauntletDeathHandler(this);
      this.deathServerTick = new ServerGauntletDeathHandler(this, BMDCapabilities.getLevelEventScheduler(level), mobConfig);
   }

   public void registerControllers(AnimationData data) {
      this.animationHandler.registerControllers(data);
   }

   protected void m_7840_(double y, boolean onGround, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void m_7023_(@NotNull Vec3 travelVector) {
      VanillaCopiesServer.travel(travelVector, this, 0.85F);
   }

   @Override
   public void setNextDamagedPart(@Nullable String part) {
      this.hitboxHelper.setNextDamagedPart(part);
   }

   @Override
   public void onSetPos(double x, double y, double z) {
      if (this.hitboxHelper != null) {
         this.hitboxHelper.updatePosition();
      }
   }

   public boolean m_6147_() {
      return false;
   }

   public boolean m_142535_(float fallDistance, float multiplier, @NotNull DamageSource source) {
      return false;
   }

   public int m_8132_() {
      return 90;
   }

   @Override
   public CompoundOrientedBox getCompoundBoundingBox(AABB bounds) {
      return this.hitboxHelper.getHitbox().getBox(bounds);
   }

   @Override
   public EntityBounds getBounds() {
      return this.hitboxHelper.getHitbox();
   }

   protected float m_6431_(@NotNull Pose pose, EntityDimensions dimensions) {
      return dimensions.f_20378_ * 0.4F;
   }

   public boolean m_5830_() {
      return false;
   }

   public int m_21230_() {
      return this.m_5448_() != null ? super.m_21230_() : 24;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return (SoundEvent)BMDSounds.GAUNTLET_IDLE.get();
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSource) {
      return (SoundEvent)BMDSounds.GAUNTLET_HURT.get();
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return (SoundEvent)BMDSounds.GAUNTLET_DEATH.get();
   }

   protected float m_6121_() {
      return 2.0F;
   }

   public void m_6043_() {
      BMDUtils.preventDespawnExceptPeaceful(this, this.f_19853_);
   }
}
