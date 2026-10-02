package com.hollingsworth.arsnouveau.common.entity;

import com.hollingsworth.arsnouveau.api.entity.ISummon;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleLineData;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraDiveGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraLeapRamGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraMeleeGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraRageGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraRamGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraSpikeGoal;
import com.hollingsworth.arsnouveau.common.entity.goal.chimera.ChimeraSummonGoal;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.potions.SnareEffect;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentAmplify;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectDelay;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectKnockback;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectLaunch;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.SoundRegistry;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidType;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class WildenChimera extends Monster implements IAnimatable {
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(this.m_5446_(), BossBarColor.PURPLE, BossBarOverlay.PROGRESS)
      .m_7003_(true)
      .m_7006_(true);
   public static final EntityDataAccessor<Boolean> HAS_SPIKES = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> HAS_HORNS = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> HAS_WINGS = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135028_);
   public static final EntityDataAccessor<Boolean> DEFENSIVE_MODE = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> PHASE_SWAPPING = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> IS_HOWLING = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> IS_DIVING = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> IS_RAMMING = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public static final EntityDataAccessor<Boolean> RAM_PREP = SynchedEntityData.m_135353_(WildenChimera.class, EntityDataSerializers.f_135035_);
   public boolean initMusic;
   public boolean isRamGoal;
   public int summonCooldown;
   public int diveCooldown;
   public int spikeCooldown;
   public int ramCooldown;
   public int rageTimer;
   public boolean diving;
   public int swapTicks;
   public FlyingPathNavigation flyingNavigator;
   protected final WaterBoundPathNavigation waterNavigation;
   protected final GroundPathNavigation groundNavigation;
   AnimationController<WildenChimera> crouchController;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);
   public Vec3 orbitOffset = Vec3.f_82478_;

   public WildenChimera(EntityType<? extends Monster> type, Level level) {
      super(type, level);
      this.f_21342_ = new WildenChimera.ChimeraMoveController(this, true);
      this.f_19793_ = 3.0F;
      this.m_21530_();
      FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, level);
      flyingpathnavigator.m_26440_(true);
      flyingpathnavigator.m_7008_(false);
      flyingpathnavigator.m_26443_(true);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.flyingNavigator = flyingpathnavigator;
      this.waterNavigation = new WaterBoundPathNavigation(this, level);
      this.groundNavigation = new GroundPathNavigation(this, level);
      this.rageTimer = 300;
      this.f_21364_ = 75;
   }

   public WildenChimera(Level level) {
      this((EntityType<? extends Monster>)ModEntities.WILDEN_BOSS.get(), level);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_
         .m_25352_(
            5,
            new ChimeraMeleeGoal(
               this,
               1.2,
               true,
               () -> !this.isHowling()
                     && !this.isFlying()
                     && !this.isDefensive()
                     && !this.isDiving()
                     && !this.getPhaseSwapping()
                     && !this.isRamming()
                     && !this.isRamPrep()
            )
         );
      this.f_21345_.m_25352_(3, new ChimeraSummonGoal(this));
      this.f_21346_.m_25352_(0, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21345_.m_25352_(1, new ChimeraRageGoal(this));
      this.f_21345_.m_25352_(3, new ChimeraLeapRamGoal(this));
      this.f_21345_.m_25352_(3, new ChimeraRamGoal(this));
      this.f_21345_.m_25352_(3, new ChimeraDiveGoal(this));
      this.f_21345_.m_25352_(3, new ChimeraSpikeGoal(this));
   }

   public boolean m_6063_() {
      return !this.m_6069_();
   }

   public boolean isPushedByFluid(FluidType type) {
      return !this.m_6069_();
   }

   @Override
   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController<>(this, "walkController", 1.0F, e -> {
         if (!this.isDefensive() && e.isMoving() && !this.isFlying() && !this.isHowling() && !this.m_6069_() && !this.isRamPrep() && !this.isRamming()) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("run"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      this.crouchController = new AnimationController<>(this, "crouchController", 1.0F, event -> {
         if (this.isDefensive() && !this.isFlying() && !this.isHowling()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("defending"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      });
      animationData.addAnimationController(this.crouchController);
      animationData.addAnimationController(new AnimationController<>(this, "idleController", 1.0F, event -> {
         if (!event.isMoving() && !this.isDefensive() && !this.isFlying() && !this.isHowling() && !this.isRamPrep() && !this.isRamming()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "flyController", 1.0F, event -> {
         if (this.isFlying() && !this.isDiving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fly_rising"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "diveController", 1.0F, event -> {
         if (this.isDiving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("dive"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "howlController", 1.0F, e -> {
         if (this.isHowling()) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("roar"));
            return PlayState.CONTINUE;
         } else {
            e.getController().markNeedsReload();
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "swimController", 1.0F, e -> {
         if (!this.isDefensive() && e.isMoving() && !this.isFlying() && !this.isHowling() && this.m_6069_()) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("swim"));
            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "ramController", 1.0F, event -> {
         if (this.isRamming() && !this.isRamPrep()) {
            if (!this.hasWings()) {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("charge"));
            }

            return PlayState.CONTINUE;
         } else {
            return PlayState.STOP;
         }
      }));
      animationData.addAnimationController(new AnimationController<>(this, "ramPrep", 1.0F, event -> {
         if (this.isRamPrep() && !this.isRamming()) {
            if (this.hasWings()) {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("wing_charge_prep"));
            } else {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("charge_prep"));
            }

            return PlayState.CONTINUE;
         } else {
            event.getController().markNeedsReload();
            return PlayState.STOP;
         }
      }));
   }

   public void m_5844_() {
      if (!this.f_19853_.f_46443_) {
         if (this.m_6142_() && this.m_20069_() && this.wantsToSwim()) {
            this.f_21344_ = this.waterNavigation;
            this.m_20282_(true);
         } else {
            this.f_21344_ = this.groundNavigation;
            this.m_20282_(false);
         }
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_ && !this.initMusic) {
         this.initMusic = true;
         (() -> WildenChimera.ChimeraMusic.play(this)).run();
      }

      if (!this.f_19853_.f_46443_ && this.isDefensive()) {
         this.m_21573_().m_26573_();
      }

      if (this.f_19853_.f_46443_ && this.isFlying() && this.f_19796_.m_188503_(18) == 0) {
         this.f_19853_
            .m_7785_(
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               SoundEvents.f_11734_,
               this.m_5720_(),
               0.95F + this.f_19796_.m_188501_() * 0.05F,
               0.3F + this.f_19796_.m_188501_() * 0.05F,
               false
            );
      }

      if (!this.f_19853_.f_46443_ && this.m_20077_() && this.f_19853_.m_46467_() % 10L == 0L) {
         this.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 20, 4));
         this.m_7292_(new MobEffectInstance(MobEffects.f_19606_, 20, 3));
         this.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 20));
         this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 20, 3));
      }

      if (this.isFlying()) {
         this.f_21344_.m_26573_();
         this.flyingNavigator.m_7638_();
      }

      if (!this.f_19853_.f_46443_) {
         if (this.summonCooldown > 0) {
            this.summonCooldown--;
         }

         if (this.diveCooldown > 0) {
            this.diveCooldown--;
            if (this.m_20077_() || this.m_20069_()) {
               this.spikeCooldown -= 2;
            }
         }

         if (this.spikeCooldown > 0) {
            this.spikeCooldown--;
         }

         if (this.ramCooldown > 0) {
            this.ramCooldown--;
         }
      }

      if (!this.f_19853_.f_46443_ && this.getPhaseSwapping() && !this.f_20890_) {
         if (this.swapTicks < 60) {
            this.swapTicks++;
            this.f_21344_.m_26573_();
         } else {
            this.m_21219_();
            this.setPhaseSwapping(false);

            for (LivingEntity e : this.f_19853_.m_45976_(Player.class, new AABB(this.m_20183_()).m_82400_(5.0))) {
               EntitySpellResolver resolver = new EntitySpellResolver(
                  new SpellContext(
                     this.f_19853_,
                     new Spell()
                        .add(MethodTouch.INSTANCE)
                        .add(EffectLaunch.INSTANCE)
                        .add(AugmentAmplify.INSTANCE, 2)
                        .add(EffectDelay.INSTANCE)
                        .add(EffectKnockback.INSTANCE)
                        .add(AugmentAmplify.INSTANCE, 2),
                     this,
                     new LivingCaster(this)
                  )
               );
               resolver.onCastOnEntity(ItemStack.f_41583_, e, InteractionHand.MAIN_HAND);
            }

            this.getRandomUpgrade();
            ParticleUtil.spawnPoof((ServerLevel)this.f_19853_, this.m_20183_().m_7494_());
            if (this.getPhase() == 1) {
               this.rageTimer = 200;
            }

            this.swapTicks = 0;
         }
      }

      if (this.getPhaseSwapping() && this.f_19853_.f_46443_) {
         spawnPhaseParticles(this.m_20183_(), this.f_19853_, this.getPhase());
      }
   }

   public static void spawnPhaseParticles(BlockPos pos, Level level, int multiplier) {
      if (level.f_46443_) {
         int baseAge = 40;
         float scaleAge = (float)ParticleUtil.inRange(0.1, 0.2);

         for (int i = 0; i < 10 * Math.min(1, multiplier); i++) {
            Vec3 particlePos = new Vec3((double)pos.m_123341_(), (double)(pos.m_123342_() + 1), (double)pos.m_123343_()).m_82520_(0.5, 0.5, 0.5);
            particlePos = particlePos.m_82549_(ParticleUtil.pointInSphere().m_82542_(3.0, 3.0, 3.0));
            level.m_7106_(
               ParticleLineData.createData(ParticleColor.makeRandomColor(255, 255, 255, level.f_46441_), scaleAge, baseAge + level.f_46441_.m_188503_(20)),
               particlePos.m_7096_(),
               particlePos.m_7098_(),
               particlePos.m_7094_(),
               (double)pos.m_123341_() + 0.5,
               (double)pos.m_123342_() + 0.5,
               (double)pos.m_123343_() + 0.5
            );
         }
      }
   }

   protected void m_7472_(DamageSource p_213333_1_, int p_213333_2_, boolean p_213333_3_) {
      super.m_7472_(p_213333_1_, p_213333_2_, p_213333_3_);
      ItemEntity itementity = this.m_19998_((ItemLike)ItemsRegistry.WILDEN_TRIBUTE.get());
      if (itementity != null) {
         itementity.m_32064_();
      }
   }

   protected void m_7355_(BlockPos p_180429_1_, BlockState p_180429_2_) {
      this.m_5496_(SoundEvents.f_12284_, 0.15F + this.f_19796_.m_188501_() * 0.3F, 0.8F + this.f_19796_.m_188501_() * 0.1F);
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_12042_;
   }

   public boolean m_20067_() {
      return false;
   }

   protected SoundEvent m_7975_(DamageSource p_184601_1_) {
      return SoundEvents.f_12283_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12282_;
   }

   public void resetCooldowns() {
      this.spikeCooldown = 0;
      this.ramCooldown = 0;
      this.diveCooldown = 0;
      this.summonCooldown = 0;
   }

   public void m_5634_(float p_70691_1_) {
      this.m_21153_(this.m_21223_() + p_70691_1_);
   }

   public boolean canDive() {
      return !this.isRamGoal
         && this.diveCooldown <= 0
         && this.hasWings()
         && !this.getPhaseSwapping()
         && !this.isFlying()
         && this.f_19861_
         && !this.isDefensive();
   }

   public boolean canSpike() {
      return !this.isRamGoal
         && this.spikeCooldown <= 0
         && this.hasSpikes()
         && !this.getPhaseSwapping()
         && !this.isFlying()
         && this.f_19861_
         && this.m_5448_() != null;
   }

   public boolean canRam(boolean withWings) {
      return withWings != this.hasWings()
         ? false
         : !this.isRamGoal
            && this.ramCooldown <= 0
            && this.hasHorns()
            && !this.getPhaseSwapping()
            && !this.isFlying()
            && !this.isDefensive()
            && this.m_5448_() != null
            && this.m_5448_().m_20096_()
            && this.m_20096_();
   }

   public boolean canSummon() {
      return !this.isRamGoal
         && this.m_5448_() != null
         && this.summonCooldown <= 0
         && !this.isFlying()
         && !this.getPhaseSwapping()
         && !this.isDefensive()
         && this.f_19861_;
   }

   public boolean canAttack() {
      return !this.isRamGoal && this.m_5448_() != null && this.m_21223_() >= 1.0F && !this.getPhaseSwapping() && !this.isFlying() && !this.isDefensive();
   }

   public boolean canRage() {
      return !this.getPhaseSwapping() && !this.isRamGoal;
   }

   public void getRandomUpgrade() {
      ArrayList<Integer> upgrades = new ArrayList<>();
      if (!this.hasWings()) {
         upgrades.add(0);
      }

      if (!this.hasSpikes()) {
         upgrades.add(1);
      }

      if (!this.hasHorns()) {
         upgrades.add(2);
      }

      if (!upgrades.isEmpty()) {
         int upgrade = upgrades.get(this.f_19796_.m_188503_(upgrades.size()));
         switch (upgrade) {
            case 0:
               this.setWings(true);
               break;
            case 1:
               this.setSpikes(true);
               break;
            case 2:
               this.setHorns(true);
         }
      }
   }

   public void m_7023_(Vec3 pTravelVector) {
      if (this.m_6142_() && this.m_20069_() && this.wantsToSwim()) {
         this.m_19920_(0.01F, pTravelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9));
      } else {
         super.m_7023_(pTravelVector);
      }
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source != DamageSource.f_19314_
         && source != DamageSource.f_19325_
         && source != DamageSource.f_19310_
         && source != DamageSource.f_19308_
         && source != DamageSource.f_19312_) {
         if (source.f_19326_.equals("cold")) {
            amount /= 2.0F;
         }

         if (this.getPhaseSwapping()) {
            return false;
         } else {
            Entity entity = source.m_7639_();
            if (entity instanceof LivingEntity entity1 && !entity.equals(this)) {
               if (this.isDefensive()
                  && !source.f_19326_.equals("thorns")
                  && !source.m_19376_()
                  && BlockUtil.distanceFrom(entity.f_19825_, this.f_19825_) <= 3.0) {
                  entity.m_6469_(DamageSource.m_19335_(this), 6.0F);
               }

               if (entity1 instanceof WildenStalker
                  || entity1 instanceof WildenGuardian
                  || entity instanceof WildenHunter
                  || entity instanceof ISummon && ((ISummon)entity).m_21805_() != null && ((ISummon)entity).m_21805_().equals(this.m_20148_())
                  || entity1 instanceof SummonWolf && ((SummonWolf)entity1).isWildenSummon) {
                  return false;
               }
            }

            if (this.isDefensive()) {
               return false;
            } else {
               float threshold = this.m_21233_() / 4.0F;
               float nextMin = this.m_21233_() - threshold * (float)(1 + this.getPhase());
               boolean res = super.m_6469_(source, amount);
               if (!this.f_19853_.f_46443_ && this.m_21223_() <= nextMin && this.getPhase() < 3) {
                  this.setPhaseSwapping(true);
                  this.setPhase(this.getPhase() + 1);
                  this.m_21573_().m_26573_();
                  this.m_21153_(nextMin);
                  this.f_20890_ = false;
                  this.setFlying(false);
                  this.setDefensiveMode(false);
                  this.isRamGoal = false;
               }

               return res;
            }
         }
      } else {
         return false;
      }
   }

   public boolean m_21224_() {
      return this.getPhase() >= 3 && this.m_21223_() <= 0.0F;
   }

   protected void m_8024_() {
      super.m_8024_();
      this.bossEvent.m_142711_(this.m_21223_() / this.m_21233_());
   }

   protected boolean m_7341_(Entity p_184228_1_) {
      return false;
   }

   public void m_6043_() {
      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
         this.m_142687_(RemovalReason.DISCARDED);
      } else {
         this.f_20891_ = 0;
      }
   }

   public void m_6457_(ServerPlayer p_184178_1_) {
      super.m_6457_(p_184178_1_);
      this.bossEvent.m_6543_(p_184178_1_);
   }

   public void m_6452_(ServerPlayer p_184203_1_) {
      super.m_6452_(p_184203_1_);
      this.bossEvent.m_6539_(p_184203_1_);
   }

   public boolean m_6072_() {
      return false;
   }

   public boolean m_7301_(MobEffectInstance instance) {
      MobEffect effect = instance.m_19544_();
      if (instance.m_19544_() instanceof SnareEffect) {
         return false;
      } else {
         if (effect == MobEffects.f_19597_) {
            instance = new MobEffectInstance(instance.m_19544_(), 1, 0);
         }

         if (effect == ModPotions.GRAVITY_EFFECT.get()) {
            instance = new MobEffectInstance(instance.m_19544_(), Math.min(instance.m_19557_(), 100), 0);
         }

         return super.m_7301_(instance);
      }
   }

   public int getCooldownModifier() {
      return 300 / (this.getPhase() + 1);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HAS_HORNS, false);
      this.f_19804_.m_135372_(HAS_SPIKES, false);
      this.f_19804_.m_135372_(HAS_WINGS, false);
      this.f_19804_.m_135372_(PHASE, 1);
      this.f_19804_.m_135372_(DEFENSIVE_MODE, false);
      this.f_19804_.m_135372_(PHASE_SWAPPING, false);
      this.f_19804_.m_135372_(IS_FLYING, false);
      this.f_19804_.m_135372_(IS_HOWLING, false);
      this.f_19804_.m_135372_(IS_DIVING, false);
      this.f_19804_.m_135372_(IS_RAMMING, false);
      this.f_19804_.m_135372_(RAM_PREP, false);
   }

   public boolean isFlying() {
      return (Boolean)this.f_19804_.m_135370_(IS_FLYING);
   }

   public void setFlying(boolean flying) {
      this.f_19804_.m_135381_(IS_FLYING, flying);
   }

   public boolean isHowling() {
      return (Boolean)this.f_19804_.m_135370_(IS_HOWLING) || this.getPhaseSwapping();
   }

   public void setHowling(boolean howling) {
      this.f_19804_.m_135381_(IS_HOWLING, howling);
   }

   public boolean hasHorns() {
      return (Boolean)this.f_19804_.m_135370_(HAS_HORNS);
   }

   public void setHorns(boolean hasHorns) {
      this.f_19804_.m_135381_(HAS_HORNS, hasHorns);
   }

   public boolean hasSpikes() {
      return (Boolean)this.f_19804_.m_135370_(HAS_SPIKES);
   }

   public void setSpikes(boolean hasSpikes) {
      this.f_19804_.m_135381_(HAS_SPIKES, hasSpikes);
   }

   public boolean hasWings() {
      return (Boolean)this.f_19804_.m_135370_(HAS_WINGS);
   }

   public void setWings(boolean hasWings) {
      this.f_19804_.m_135381_(HAS_WINGS, hasWings);
   }

   public boolean isDefensive() {
      return (Boolean)this.f_19804_.m_135370_(DEFENSIVE_MODE);
   }

   public void setDefensiveMode(boolean defensiveMode) {
      this.f_19804_.m_135381_(DEFENSIVE_MODE, defensiveMode);
   }

   public int getPhase() {
      return (Integer)this.f_19804_.m_135370_(PHASE);
   }

   public void setPhase(int phase) {
      this.f_19804_.m_135381_(PHASE, phase);
   }

   public boolean getPhaseSwapping() {
      return (Boolean)this.f_19804_.m_135370_(PHASE_SWAPPING);
   }

   public void setPhaseSwapping(boolean swapping) {
      this.f_19804_.m_135381_(PHASE_SWAPPING, swapping);
   }

   public void setDiving(boolean diving) {
      this.f_19804_.m_135381_(IS_DIVING, diving);
   }

   public boolean isDiving() {
      return (Boolean)this.f_19804_.m_135370_(IS_DIVING);
   }

   public boolean isRamming() {
      return (Boolean)this.f_19804_.m_135370_(IS_RAMMING);
   }

   public void setRamming(boolean ramming) {
      this.f_19804_.m_135381_(IS_RAMMING, ramming);
   }

   public boolean isRamPrep() {
      return (Boolean)this.f_19804_.m_135370_(RAM_PREP);
   }

   public void setRamPrep(boolean ramPrep) {
      this.f_19804_.m_135381_(RAM_PREP, ramPrep);
   }

   public void m_20258_(CompoundTag tag) {
      super.m_20258_(tag);
      this.setHorns(tag.m_128471_("horns"));
      this.setSpikes(tag.m_128471_("spikes"));
      this.setWings(tag.m_128471_("wings"));
      this.setPhase(tag.m_128451_("phase"));
      this.setDefensiveMode(tag.m_128471_("defensive"));
      this.setPhaseSwapping(tag.m_128471_("swapping"));
      this.summonCooldown = tag.m_128451_("summonCooldown");
      this.diveCooldown = tag.m_128451_("diveCooldown");
      this.spikeCooldown = tag.m_128451_("spikeCooldown");
      this.ramCooldown = tag.m_128451_("ramCooldown");
      this.rageTimer = tag.m_128451_("rage");
      this.swapTicks = tag.m_128451_("swapTicks");
   }

   public boolean m_20223_(CompoundTag tag) {
      tag.m_128379_("spikes", this.hasSpikes());
      tag.m_128379_("horns", this.hasHorns());
      tag.m_128379_("wings", this.hasWings());
      tag.m_128405_("phase", this.getPhase());
      tag.m_128379_("defensive", this.isDefensive());
      tag.m_128405_("summonCooldown", this.summonCooldown);
      tag.m_128405_("diveCooldown", this.diveCooldown);
      tag.m_128405_("spikeCooldown", this.spikeCooldown);
      tag.m_128405_("ramCooldown", this.ramCooldown);
      tag.m_128379_("swapping", this.getPhaseSwapping());
      tag.m_128405_("rage", this.rageTimer);
      tag.m_128405_("swapTicks", this.swapTicks);
      return super.m_20223_(tag);
   }

   protected void m_7840_(double p_184231_1_, boolean p_184231_3_, BlockState p_184231_4_, BlockPos p_184231_5_) {
      super.m_7840_(p_184231_1_, p_184231_3_, p_184231_4_, p_184231_5_);
      if (this.hasWings()) {
         this.f_19789_ = 0.0F;
      }

      this.f_19789_ = Math.min(this.f_19789_, 10.0F);
   }

   public boolean wantsToSwim() {
      LivingEntity livingentity = this.m_5448_();
      return livingentity != null && livingentity.m_20069_();
   }

   public PathNavigation m_21573_() {
      return (PathNavigation)(this.isFlying() ? this.flyingNavigator : super.m_21573_());
   }

   public static void faceBlock(BlockPos block, LivingEntity citizen) {
      double xDifference = (double)(block.m_123341_() - citizen.m_20183_().m_123341_());
      double zDifference = (double)(block.m_123343_() - citizen.m_20183_().m_123343_());
      double yDifference = (double)((float)block.m_123342_() - ((float)citizen.m_20183_().m_123342_() + citizen.m_20192_()));
      double squareDifference = Math.sqrt(xDifference * xDifference + zDifference * zDifference);
      double intendedRotationYaw = Math.atan2(zDifference, xDifference) * 180.0 / Math.PI - 90.0;
      double intendedRotationPitch = -(Math.atan2(yDifference, squareDifference) * 180.0 / Math.PI);
      citizen.f_19857_ = (float)(updateRotation((double)citizen.m_146908_(), intendedRotationYaw, 360.0) % 360.0);
      citizen.f_19858_ = (float)(updateRotation((double)citizen.m_146909_(), intendedRotationPitch, 360.0) % 360.0);
   }

   public static double updateRotation(double currentRotation, double intendedRotation, double maxIncrement) {
      double wrappedAngle = Mth.m_14175_(intendedRotation - currentRotation);
      if (wrappedAngle > maxIncrement) {
         wrappedAngle = maxIncrement;
      }

      if (wrappedAngle < -maxIncrement) {
         wrappedAngle = -maxIncrement;
      }

      return currentRotation + wrappedAngle;
   }

   protected float m_6108_() {
      return this.m_6069_() ? super.m_6108_() : 0.8F;
   }

   public boolean m_6084_() {
      return !this.m_213877_();
   }

   public static Builder getModdedAttributes() {
      return Mob.m_21552_()
         .m_22268_(Attributes.f_22276_, 1000.0)
         .m_22268_(Attributes.f_22279_, 0.33)
         .m_22268_(Attributes.f_22278_, 0.6F)
         .m_22268_(Attributes.f_22282_, 1.0)
         .m_22268_(Attributes.f_22281_, 8.0)
         .m_22268_(Attributes.f_22284_, 6.0)
         .m_22268_(Attributes.f_22277_, 100.0)
         .m_22268_(Attributes.f_22280_, 0.4F);
   }

   public static enum Animations {
      ATTACK,
      HOWL,
      CHARGE,
      FLYING,
      DIVE_BOMB;
   }

   public static class ChimeraMoveController extends MoveControl {
      private final boolean hoversInPlace;
      private final WildenChimera chimera;

      public ChimeraMoveController(WildenChimera chimera, boolean hoversInPlace) {
         super(chimera);
         this.hoversInPlace = hoversInPlace;
         this.chimera = chimera;
      }

      public void m_8126_() {
         WildenChimera chimera = (WildenChimera)this.f_24974_;
         if (chimera.isFlying()) {
            if (chimera.diving) {
               this.diveTick();
            } else {
               this.flyTick();
            }
         } else if (this.chimera.wantsToSwim() && this.chimera.m_20069_()) {
            this.swimTick();
         } else {
            super.m_8126_();
         }
      }

      public void swimTick() {
         LivingEntity livingentity = this.chimera.m_5448_();
         if (livingentity != null && livingentity.m_20186_() > this.chimera.m_20186_()) {
            this.chimera.m_20256_(this.chimera.m_20184_().m_82520_(0.0, 0.02, 0.0));
         }

         if (this.f_24981_ == Operation.MOVE_TO && !this.chimera.m_21573_().m_26571_()) {
            double d0 = this.f_24975_ - this.chimera.m_20185_();
            double d1 = this.f_24976_ - this.chimera.m_20186_();
            double d2 = this.f_24977_ - this.chimera.m_20189_();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 /= d3;
            float f = (float)(Mth.m_14136_(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
            this.chimera.m_146922_(this.m_24991_(this.chimera.m_146908_(), f, 90.0F));
            this.chimera.f_20883_ = this.chimera.m_146908_();
            float f1 = (float)(this.f_24978_ * this.chimera.m_21133_(Attributes.f_22279_));
            float f2 = Mth.m_14179_(0.25F, this.chimera.m_6113_(), f1);
            this.chimera.m_7910_(f2);
            this.chimera.m_20256_(this.chimera.m_20184_().m_82520_((double)f2 * d0 * 0.08, (double)f2 * d1 * 0.13, (double)f2 * d2 * 0.08));
         } else {
            this.chimera.m_7910_(0.0F);
         }
      }

      public void flyTick() {
         if (this.f_24981_ == Operation.MOVE_TO) {
            this.f_24981_ = Operation.WAIT;
            this.f_24974_.m_20242_(true);
            double d0 = this.f_24975_ - this.f_24974_.m_20185_();
            double d1 = this.f_24976_ - this.f_24974_.m_20186_();
            double d2 = this.f_24977_ - this.f_24974_.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < 2.5000003E-7F) {
               this.f_24974_.m_21567_(0.0F);
               this.f_24974_.m_21564_(0.0F);
               return;
            }

            float f1;
            if (this.f_24974_.m_20096_()) {
               f1 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_));
            } else {
               f1 = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22280_));
            }

            this.f_24974_.m_7910_(f1);
            this.f_24974_.m_21567_(d1 > 0.0 ? f1 : -f1);
         } else {
            if (!this.hoversInPlace) {
               this.f_24974_.m_20242_(false);
            }

            this.f_24974_.m_21567_(0.0F);
            this.f_24974_.m_21564_(0.0F);
         }
      }

      public void diveTick() {
         WildenChimera mob = (WildenChimera)this.f_24974_;
         double posX = mob.m_20185_();
         double posY = mob.m_20186_();
         double posZ = mob.m_20189_();
         double motionX = mob.m_20184_().f_82479_;
         double motionY = mob.m_20184_().f_82480_;
         double motionZ = mob.m_20184_().f_82481_;
         BlockPos dest = new BlockPos(mob.orbitOffset);
         double speedMod = 1.3;
         if (dest.m_123341_() != 0 || dest.m_123342_() != 0 || dest.m_123343_() != 0) {
            double targetX = (double)dest.m_123341_() + 0.5;
            double targetY = (double)dest.m_123342_() + 0.5;
            double targetZ = (double)dest.m_123343_() + 0.5;
            Vec3 targetVector = new Vec3(targetX - posX, targetY - posY, targetZ - posZ);
            double length = targetVector.m_82553_();
            targetVector = targetVector.m_82490_(0.3 / length);
            double weight = 0.0;
            if (length <= 3.0) {
               weight = 0.9 * ((3.0 - length) / 3.0);
            }

            motionX = (0.9 - weight) * motionX + (speedMod + weight) * targetVector.f_82479_;
            motionY = (0.9 - weight) * motionY + (speedMod + weight) * targetVector.f_82480_;
            motionZ = (0.9 - weight) * motionZ + (speedMod + weight) * targetVector.f_82481_;
         }

         mob.m_20334_(motionX, motionY, motionZ);
         WildenChimera.faceBlock(new BlockPos(mob.orbitOffset), mob);
      }
   }

   private static class ChimeraMusic extends AbstractTickableSoundInstance {
      private final WildenChimera chimera;

      private ChimeraMusic(WildenChimera chimera) {
         super((SoundEvent)SoundRegistry.WILD_HUNT.get(), SoundSource.RECORDS, SoundInstance.m_235150_());
         this.chimera = chimera;
         this.f_119575_ = chimera.m_20185_();
         this.f_119576_ = chimera.m_20186_();
         this.f_119577_ = chimera.m_20189_();
         this.f_119578_ = true;
      }

      public static void play(WildenChimera chimera) {
         Minecraft.m_91087_().m_91106_().m_120367_(new WildenChimera.ChimeraMusic(chimera));
      }

      public void m_7788_() {
         if (!this.chimera.m_6084_()) {
            this.m_119609_();
         } else {
            this.f_119575_ = this.chimera.m_20185_();
            this.f_119576_ = this.chimera.m_20186_();
            this.f_119577_ = this.chimera.m_20189_();
         }
      }
   }
}
