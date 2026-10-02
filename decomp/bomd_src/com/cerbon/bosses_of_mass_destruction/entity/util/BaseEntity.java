package com.cerbon.bosses_of_mass_destruction.entity.util;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.entity.damage.IDamageHandler;
import com.google.errorprone.annotations.ForOverride;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public abstract class BaseEntity extends PathfinderMob implements IAnimatable, IAnimationTickable {
   public Vec3 idlePosition = Vec3.f_82478_;
   protected ServerBossEvent bossBar;
   protected IDamageHandler damageHandler;
   protected IEntityEventHandler entityEventHandler;
   protected IEntityTick<Level> clientTick;
   protected IEntityTick<ServerLevel> serverTick;
   protected IDataAccessorHandler dataAccessorHandler;
   protected IMobEffectFilter mobEffectHandler;
   protected IMoveHandler moveHandler;
   protected INbtHandler nbtHandler;
   protected IEntityTick<Level> deathClientTick;
   protected IEntityTick<ServerLevel> deathServerTick;
   protected final EventScheduler preTickEvents = new EventScheduler();
   protected final EventScheduler postTickEvents = new EventScheduler();
   protected AnimationFactory animationFactory;

   public BaseEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
      super(entityType, level);
   }

   public void m_8119_() {
      this.preTickEvents.updateEvents();
      if (this.idlePosition == Vec3.f_82478_) {
         this.idlePosition = this.m_20182_();
      }

      if (this.f_19853_.m_5776_()) {
         this.clientTick();
         if (this.clientTick != null) {
            this.clientTick.tick(this.f_19853_);
         }
      } else if (this.f_19853_ instanceof ServerLevel serverLevel) {
         this.serverTick(serverLevel);
         if (this.serverTick != null) {
            this.serverTick.tick(serverLevel);
         }
      }

      super.m_8119_();
      this.postTickEvents.updateEvents();
   }

   protected void m_6153_() {
      if (this.f_19853_.m_5776_() && this.deathClientTick != null) {
         this.deathClientTick.tick(this.f_19853_);
      } else {
         if (this.f_19853_ instanceof ServerLevel serverLevel && this.deathServerTick != null) {
            this.deathServerTick.tick(serverLevel);
            return;
         }

         super.m_6153_();
      }
   }

   @ForOverride
   public void clientTick() {
   }

   @ForOverride
   public void serverTick(ServerLevel serverLevel) {
   }

   public void m_8107_() {
      super.m_8107_();
      this.m_21574_().m_26789_();
   }

   protected void m_8024_() {
      super.m_8024_();
      if (this.bossBar != null) {
         this.bossBar.m_142711_(this.m_21223_() / this.m_21233_());
      }
   }

   public void m_20258_(@NotNull CompoundTag compound) {
      super.m_20258_(compound);
      if (this.m_8077_() && this.bossBar != null) {
         this.bossBar.m_6456_(this.m_5446_());
      }

      if (this.nbtHandler != null) {
         this.nbtHandler.fromTag(compound);
      }
   }

   public void m_6593_(@Nullable Component name) {
      super.m_6593_(name);
      if (this.bossBar != null) {
         this.bossBar.m_6456_(this.m_5446_());
      }
   }

   public void m_6457_(@NotNull ServerPlayer serverPlayer) {
      super.m_6457_(serverPlayer);
      if (this.bossBar != null) {
         this.bossBar.m_6543_(serverPlayer);
      }
   }

   public void m_6452_(@NotNull ServerPlayer serverPlayer) {
      super.m_6452_(serverPlayer);
      if (this.bossBar != null) {
         this.bossBar.m_6539_(serverPlayer);
      }
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
   }

   public void m_7822_(byte id) {
      if (this.entityEventHandler != null) {
         this.entityEventHandler.handleEntityEvent(id);
      }

      super.m_7822_(id);
   }

   public void m_7350_(@NotNull EntityDataAccessor<?> data) {
      super.m_7350_(data);
      if (this.dataAccessorHandler != null) {
         this.dataAccessorHandler.onSyncedDataUpdated(data);
      }
   }

   public boolean m_7301_(@NotNull MobEffectInstance effectInstance) {
      return this.mobEffectHandler != null ? this.mobEffectHandler.canBeAffected(effectInstance) : super.m_7301_(effectInstance);
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      EntityStats stats = new EntityStats(this);
      IDamageHandler handler = this.damageHandler;
      if (!this.f_19853_.m_5776_() && handler != null) {
         handler.beforeDamage(stats, source, amount);
      }

      boolean result = handler != null ? handler.shouldDamage(this, source, amount) && super.m_6469_(source, amount) : super.m_6469_(source, amount);
      if (!this.f_19853_.m_5776_() && handler != null) {
         handler.afterDamage(stats, source, amount, result);
      }

      return result;
   }

   public void m_6710_(@Nullable LivingEntity target) {
      if (target == null) {
         this.idlePosition = this.m_20182_();
      }

      super.m_6710_(target);
   }

   public void m_6478_(@NotNull MoverType type, @NotNull Vec3 movement) {
      boolean shouldDoDefault = this.moveHandler != null && this.moveHandler.canMove(type, movement);
      if (this.moveHandler == null || shouldDoDefault) {
         super.m_6478_(type, movement);
      }
   }

   @NotNull
   public CompoundTag m_20240_(@NotNull CompoundTag compound) {
      CompoundTag superCompound = super.m_20240_(compound);
      return this.nbtHandler != null ? this.nbtHandler.toTag(superCompound) : superCompound;
   }

   public AnimationFactory getFactory() {
      if (this.animationFactory == null) {
         this.animationFactory = new AnimationFactory(this);
      }

      return this.animationFactory;
   }

   public int tickTimer() {
      return this.f_19797_;
   }

   public Vec3 safeGetTargetPos() {
      LivingEntity target = this.m_5448_();
      return target == null ? Vec3.f_82478_ : target.m_20182_();
   }
}
