package io.redspace.ironsspellbooks.entity.mobs.summoned_frog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.effect.SummonTimer;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericCopyOwnerTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtByTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.GenericOwnerHurtTargetGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.OwnerGetter;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import java.util.EnumSet;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
import net.minecraft.world.entity.ai.behavior.LongJumpToPreferredBlock;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.ShootTongue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class SummonedFrog extends Frog implements MagicSummon {
   protected LivingEntity cachedSummoner;
   protected UUID summonerUUID;

   public SummonedFrog(EntityType<? extends Animal> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   protected Brain<?> m_8075_(Dynamic<?> pDynamic) {
      Brain<Frog> brain = super.m_8075_(pDynamic);
      brain.m_147343_();
      brain.m_21891_(
         Activity.f_37978_,
         0,
         ImmutableList.of(
            new LookAtTargetSink(45, 90),
            new MoveToTargetSink(),
            new CountDownCooldownTicks(MemoryModuleType.f_148197_),
            new CountDownCooldownTicks(MemoryModuleType.f_148199_)
         )
      );
      initTongueActivity(brain);
      initJumpActivity(brain);
      return brain;
   }

   public void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(7, new SummonedFrog.FrogFollowerOwnerGoal(this, this::getSummoner, 0.9F, 15.0F, 5.0F, 25.0F));
      this.f_21345_.m_25352_(8, new WaterAvoidingRandomStrollGoal(this, 0.8));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
      this.f_21346_.m_25352_(4, new GenericHurtByTargetGoal(this, entity -> entity == this.getSummoner()).setAlertOthers());
   }

   @Override
   public LivingEntity getSummoner() {
      return OwnerHelper.getAndCacheOwner(this.f_19853_, this.cachedSummoner, this.summonerUUID);
   }

   public void setSummoner(@Nullable LivingEntity owner) {
      if (owner != null) {
         this.summonerUUID = owner.m_20148_();
         this.cachedSummoner = owner;
      }
   }

   public void m_6667_(DamageSource pDamageSource) {
      this.onDeathHelper();
      super.m_6667_(pDamageSource);
   }

   public void onRemovedFromWorld() {
      this.onRemovedHelper(this, (SummonTimer)MobEffectRegistry.POLAR_BEAR_TIMER.get());
      super.onRemovedFromWorld();
   }

   public void m_7378_(CompoundTag compoundTag) {
      super.m_7378_(compoundTag);
      this.summonerUUID = OwnerHelper.deserializeOwner(compoundTag);
   }

   public void m_7380_(CompoundTag compoundTag) {
      super.m_7380_(compoundTag);
      OwnerHelper.serializeOwner(compoundTag, this.summonerUUID);
   }

   public boolean m_7327_(Entity pEntity) {
      return Utils.doMeleeAttack(this, pEntity, ((AbstractSpell)SpellRegistry.SUMMON_POLAR_BEAR_SPELL.get()).getDamageSource(this, this.getSummoner()));
   }

   public boolean m_7307_(Entity pEntity) {
      return super.m_7307_(pEntity) || this.isAlliedHelper(pEntity);
   }

   @Override
   public void onUnSummon() {
      if (!this.f_19853_.f_46443_) {
         MagicManager.spawnParticles(this.f_19853_, ParticleTypes.f_123759_, this.m_20185_(), this.m_20186_(), this.m_20189_(), 25, 0.4, 0.8, 0.4, 0.03, false);
         this.m_146870_();
      }
   }

   public boolean m_6469_(DamageSource pSource, float pAmount) {
      return this.shouldIgnoreDamage(pSource) ? false : super.m_6469_(pSource, pAmount);
   }

   private static void initJumpActivity(Brain<Frog> pBrain) {
      pBrain.m_21903_(
         Activity.f_150239_,
         ImmutableList.of(
            Pair.of(0, new LongJumpMidJump(UniformInt.m_146622_(100, 140), SoundEvents.f_215696_)),
            Pair.of(
               1,
               new LongJumpToPreferredBlock(
                  UniformInt.m_146622_(100, 140),
                  2,
                  4,
                  1.5F,
                  p_218593_ -> SoundEvents.f_215695_,
                  BlockTags.f_215837_,
                  0.5F,
                  p_218583_ -> p_218583_.m_60713_(Blocks.f_50196_)
               )
            )
         ),
         ImmutableSet.of(
            Pair.of(MemoryModuleType.f_148196_, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.f_26375_, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.f_148199_, MemoryStatus.VALUE_ABSENT),
            Pair.of(MemoryModuleType.f_217766_, MemoryStatus.VALUE_ABSENT)
         )
      );
   }

   private static void initTongueActivity(Brain<Frog> pBrain) {
      pBrain.m_21895_(
         Activity.f_219846_,
         0,
         ImmutableList.of(new StopAttackingIfTargetInvalid(), new ShootTongue(SoundEvents.f_215697_, SoundEvents.f_215692_)),
         MemoryModuleType.f_26372_
      );
   }

   class FrogFollowerOwnerGoal extends Goal {
      private final PathfinderMob entity;
      private LivingEntity owner;
      private final LevelReader level;
      private final double speedModifier;
      private final PathNavigation navigation;
      private int timeToRecalcPath;
      private final float stopDistance;
      private final float startDistance;
      private float oldWaterCost;
      private final OwnerGetter ownerGetter;
      private final float teleportDistance;

      public FrogFollowerOwnerGoal(
         PathfinderMob entity, OwnerGetter ownerGetter, double pSpeedModifier, float pStartDistance, float pStopDistance, float teleportDistance
      ) {
         this.entity = entity;
         this.ownerGetter = ownerGetter;
         this.level = entity.f_19853_;
         this.speedModifier = pSpeedModifier;
         this.navigation = entity.m_21573_();
         this.startDistance = pStartDistance;
         this.stopDistance = pStopDistance;
         this.teleportDistance = teleportDistance * teleportDistance;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.ownerGetter.get();
         if (livingentity == null) {
            return false;
         } else if (livingentity.m_5833_()) {
            return false;
         } else if (this.entity.m_20280_(livingentity) < (double)(this.startDistance * this.startDistance)) {
            return false;
         } else {
            this.owner = livingentity;
            return true;
         }
      }

      public boolean m_8045_() {
         return this.navigation.m_26571_() ? false : !(this.entity.m_20280_(this.owner) <= (double)(this.stopDistance * this.stopDistance));
      }

      public void m_8056_() {
         this.timeToRecalcPath = 0;
         this.oldWaterCost = this.entity.m_21439_(BlockPathTypes.WATER);
         this.entity.m_21441_(BlockPathTypes.WATER, 0.0F);
      }

      public void m_8041_() {
         this.owner = null;
         this.navigation.m_26573_();
         this.entity.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
      }

      public void m_8037_() {
         this.entity.m_21563_().m_24960_(this.owner, 10.0F, (float)this.entity.m_8132_());
         if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.m_183277_(10);
            if (!this.entity.m_21523_() && !this.entity.m_20159_()) {
               if (this.entity.m_20280_(this.owner) >= (double)this.teleportDistance) {
                  this.teleportToOwner();
               } else {
                  this.navigation.m_5624_(this.owner, this.speedModifier);
               }
            }
         }
      }

      private void teleportToOwner() {
         BlockPos blockpos = this.owner.m_20183_();

         for (int i = 0; i < 10; i++) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.m_123341_() + j, blockpos.m_123342_() + k, blockpos.m_123343_() + l);
            if (flag) {
               return;
            }
         }
      }

      private boolean maybeTeleportTo(int pX, int pY, int pZ) {
         if (Math.abs((double)pX - this.owner.m_20185_()) < 2.0 && Math.abs((double)pZ - this.owner.m_20189_()) < 2.0) {
            return false;
         } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
            return false;
         } else {
            this.entity.m_7678_((double)pX + 0.5, (double)pY, (double)pZ + 0.5, this.entity.m_146908_(), this.entity.m_146909_());
            this.navigation.m_26573_();
            return true;
         }
      }

      private boolean canTeleportTo(BlockPos pPos) {
         BlockPathTypes blockpathtypes = WalkNodeEvaluator.m_77604_(this.level, pPos.m_122032_());
         if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
         } else {
            BlockState blockstate = this.level.m_8055_(pPos.m_7495_());
            if (blockstate.m_60734_() instanceof LeavesBlock) {
               return false;
            } else {
               BlockPos blockpos = pPos.m_121996_(this.entity.m_20183_());
               return this.level.m_45756_(this.entity, this.entity.m_20191_().m_82338_(blockpos));
            }
         }
      }

      private int randomIntInclusive(int pMin, int pMax) {
         return this.entity.m_217043_().m_188503_(pMax - pMin + 1) + pMin;
      }
   }

   class SummonAttackablesSensor extends NearestVisibleLivingEntitySensor {
      protected boolean m_142628_(LivingEntity pAttacker, LivingEntity pTarget) {
         return false;
      }

      protected MemoryModuleType<LivingEntity> m_142149_() {
         return MemoryModuleType.f_148194_;
      }
   }
}
