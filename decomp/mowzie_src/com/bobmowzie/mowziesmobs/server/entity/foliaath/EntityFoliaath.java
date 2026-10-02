package com.bobmowzie.mowziesmobs.server.entity.foliaath;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.model.tools.IntermittentAnimation;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationAttackAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class EntityFoliaath extends MowzieLLibraryEntity implements Enemy {
   public static final Animation DIE_ANIMATION = Animation.create(50);
   public static final Animation HURT_ANIMATION = Animation.create(10);
   public static final Animation ATTACK_ANIMATION = Animation.create(14);
   private static final EntityDataAccessor<Boolean> CAN_DESPAWN = SynchedEntityData.m_135353_(EntityFoliaath.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> ACTIVATE_TARGET = SynchedEntityData.m_135353_(EntityFoliaath.class, EntityDataSerializers.f_135028_);
   private static final int ACTIVATE_DURATION = 30;
   public IntermittentAnimation<EntityFoliaath> openMouth = new IntermittentAnimation(this, 15, 30, 50, !this.f_19853_.f_46443_);
   public ControlledAnimation activate = new ControlledAnimation(30);
   public ControlledAnimation deathFlail = new ControlledAnimation(5);
   public ControlledAnimation stopDance = new ControlledAnimation(10);
   public int lastTimeDecrease = 0;
   private int resettingTargetTimer = 0;
   private double prevOpenMouth;
   private double prevActivate;
   private int activateTarget;

   public EntityFoliaath(EntityType<? extends EntityFoliaath> type, Level world) {
      super(type, world);
      this.f_21364_ = 5;
      this.addIntermittentAnimation(this.openMouth);
   }

   protected boolean isMovementNoisy() {
      return false;
   }

   public PushReaction m_7752_() {
      return PushReaction.IGNORE;
   }

   public boolean m_6063_() {
      return false;
   }

   public void m_5997_(double x, double y, double z) {
      super.m_5997_(0.0, y, 0.0);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new AnimationAttackAI<>(this, ATTACK_ANIMATION, (SoundEvent)MMSounds.ENTITY_FOLIAATH_BITE_1.get(), null, 2.0F, 4.0F, 1.0F, 3));
      this.f_21345_.m_25352_(1, new AnimationTakeDamage<>(this));
      this.f_21345_.m_25352_(1, new AnimationDieAI<>(this));
      this.f_21346_
         .m_25352_(
            3,
            new NearestAttackableTargetGoal(
               this,
               LivingEntity.class,
               0,
               true,
               false,
               e -> (PathfinderMob.class.isAssignableFrom(e.getClass()) || e instanceof Player)
                     && !(e instanceof EntityFoliaath)
                     && !(e instanceof EntityBabyFoliaath)
                     && !(e instanceof Creeper)
            ) {
               public boolean m_8045_() {
                  this.m_26073_();
                  return this.f_26050_ != EntityFoliaath.this.m_5448_() ? false : super.m_8045_();
               }
            }
         );
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(CAN_DESPAWN, true);
      this.m_20088_().m_135372_(ACTIVATE_TARGET, 0);
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22281_, 8.0).m_22268_(Attributes.f_22276_, 10.0).m_22268_(Attributes.f_22278_, 1.0);
   }

   protected SoundEvent m_7975_(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_FOLIAATH_HURT.get();
   }

   public SoundEvent m_5592_() {
      return (SoundEvent)MMSounds.ENTITY_FOLIAATH_DIE.get();
   }

   public boolean m_6094_() {
      return false;
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      this.activate.updatePrevTimer();
      this.deathFlail.updatePrevTimer();
      this.stopDance.updatePrevTimer();
      this.openMouth.updatePrevTimer();
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      if (this.getAnimation() == NO_ANIMATION && !this.activate.canIncreaseTimer()) {
         this.openMouth.update();
      } else {
         this.openMouth.stop();
      }

      if (this.activate.getAnimationFraction() >= 0.8F) {
         if (!this.active) {
            this.active = true;
         }
      } else if (this.activate.getAnimationFraction() < 0.8F && this.active) {
         this.active = false;
      }

      if (this.frame % 13 == 3 && this.getAnimation() != DIE_ANIMATION) {
         if (this.openMouth.getTimeRunning() >= 10) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_FOLIAATH_PANT_1.get(), 1.0F, 1.0F);
         } else if (this.activate.getTimer() >= 25) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_FOLIAATH_PANT_2.get(), 1.0F, 1.0F);
         }
      }

      int openMouthTime = this.openMouth.getTimeRunning();
      if (this.prevOpenMouth - (double)openMouthTime < 0.0) {
         if (openMouthTime == 1) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_FOLIAATH_RUSTLE.get(), 1.0F, 1.0F);
         } else if (openMouthTime == 13) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_FOLIAATH_GRUNT.get(), 1.0F, 1.0F);
         }
      }

      this.prevOpenMouth = (double)openMouthTime;
      int activateTime = this.activate.getTimer();
      if (!this.f_19853_.f_46443_) {
         SoundEvent sound = null;
         if (this.prevActivate - (double)activateTime < 0.0) {
            switch (activateTime) {
               case 1:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_RUSTLE.get();
                  break;
               case 5:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_MERGE.get();
            }
         } else if (this.prevActivate - (double)activateTime > 0.0) {
            switch (activateTime) {
               case 24:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_RETREAT.get();
                  break;
               case 28:
                  sound = (SoundEvent)MMSounds.ENTITY_FOLIAATH_RUSTLE.get();
            }
         }

         if (sound != null) {
            this.m_5496_(sound, 1.0F, 1.0F);
         }
      }

      this.prevActivate = (double)activateTime;
      this.f_20883_ = 0.0F;
      this.m_146922_(0.0F);
      if (this.resettingTargetTimer > 0 && !this.f_19853_.f_46443_) {
         this.f_20885_ = this.f_20886_;
      }

      if (this.m_5448_() != null) {
         this.f_20885_ = this.targetAngle;
         if (this.targetDistance <= 4.0F
            && this.m_5448_().m_20186_() - this.m_20186_() >= -1.0
            && this.m_5448_().m_20186_() - this.m_20186_() <= 2.0
            && this.getAnimation() == NO_ANIMATION
            && this.active) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, ATTACK_ANIMATION);
         }

         if ((double)this.targetDistance <= 10.5 && this.m_5448_().m_20186_() - this.m_20186_() >= -1.5 && this.m_5448_().m_20186_() - this.m_20186_() <= 2.0) {
            this.setActivateTarget(30);
            this.lastTimeDecrease = 0;
         } else if (this.lastTimeDecrease <= 30 && this.getAnimation() == NO_ANIMATION) {
            this.setActivateTarget(0);
            this.lastTimeDecrease++;
         }
      } else if (!this.f_19853_.f_46443_ && this.lastTimeDecrease <= 30 && this.getAnimation() == NO_ANIMATION && this.resettingTargetTimer == 0) {
         this.setActivateTarget(0);
         this.lastTimeDecrease++;
      }

      if (this.getAnimation() == DIE_ANIMATION) {
         if (this.getAnimationTick() <= 12) {
            this.deathFlail.increaseTimer();
         } else {
            this.deathFlail.decreaseTimer();
         }

         this.stopDance.increaseTimer();
         this.setActivateTarget(30);
      }

      if (this.resettingTargetTimer > 0) {
         this.resettingTargetTimer--;
      }

      if (this.activateTarget == activateTime) {
         this.activateTarget = this.getActivateTarget();
      } else if (activateTime < this.activateTarget && this.activate.canIncreaseTimer()
         || activateTime > this.activateTarget && this.activate.canDecreaseTimer()) {
         this.activate.increaseTimer(activateTime < this.activateTarget ? 1 : -2);
      }

      if (!this.f_19853_.f_46443_ && this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
         this.m_146870_();
      }
   }

   @Override
   public boolean m_6469_(DamageSource damageSource, float amount) {
      this.openMouth.resetTimeRunning();
      return (damageSource.m_19378_() || this.active) && super.m_6469_(damageSource, amount);
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   public boolean m_6087_() {
      return true;
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.FOLIAATH.spawnConfig;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.FOLIAATH.combatConfig;
   }

   private boolean isInTree(LevelAccessor world) {
      int x = Mth.m_14107_(this.m_20185_());
      int y = Mth.m_14107_(this.m_20191_().f_82289_);
      int z = Mth.m_14107_(this.m_20189_());
      BlockPos pos = new BlockPos(x, y, z);
      BlockState floor = world.m_8055_(pos.m_7495_());
      if (floor.m_204336_(BlockTags.f_13035_)) {
         for (int i = 2; i < 4; i++) {
            BlockState toCheck = world.m_8055_(pos.m_6625_(i));
            if (!toCheck.m_204336_(BlockTags.f_13035_) && !toCheck.m_204336_(BlockTags.f_13106_) && !toCheck.m_60795_()) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean m_5545_(LevelAccessor world, MobSpawnType reason) {
      return !this.isInTree(world)
         && super.m_5545_(world, reason)
         && this.getEntitiesNearby(Animal.class, 5.0, 5.0, 5.0, 5.0).isEmpty()
         && world.m_46791_() != Difficulty.PEACEFUL;
   }

   public boolean m_214076_(ServerLevel world, LivingEntity killedEntity) {
      this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 300, 1, true, true));
      return super.m_214076_(world, killedEntity);
   }

   public boolean m_8023_() {
      return !(Boolean)this.m_20088_().m_135370_(CAN_DESPAWN);
   }

   public void setCanDespawn(boolean canDespawn) {
      this.m_20088_().m_135381_(CAN_DESPAWN, canDespawn);
   }

   public int getActivateTarget() {
      return (Integer)this.m_20088_().m_135370_(ACTIVATE_TARGET);
   }

   public void setActivateTarget(int activateTarget) {
      this.m_20088_().m_135381_(ACTIVATE_TARGET, activateTarget);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("canDespawn", (Boolean)this.m_20088_().m_135370_(CAN_DESPAWN));
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setCanDespawn(compound.m_128471_("canDespawn"));
   }

   protected void m_7355_(BlockPos p_180429_1_, BlockState p_180429_2_) {
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{DIE_ANIMATION, HURT_ANIMATION, ATTACK_ANIMATION};
   }

   protected ResourceLocation m_7582_() {
      return LootTableHandler.FOLIAATH;
   }
}
