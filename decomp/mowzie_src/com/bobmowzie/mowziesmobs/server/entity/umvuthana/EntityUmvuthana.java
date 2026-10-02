package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleRibbon;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonComponent;
import com.bobmowzie.mowziesmobs.client.particle.util.RibbonParticleData;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.BlockAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.DieAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.MeleeAttackAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIAvoidEntity;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.ItemUmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.base.Supplier;
import java.util.EnumSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.easing.EasingType;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public abstract class EntityUmvuthana extends MowzieGeckoEntity {
   public static final AbilityType<EntityUmvuthana, DieAbility<EntityUmvuthana>> DIE_ABILITY = new AbilityType(
      "umvuthana_die", (type, entity) -> new DieAbility<EntityUmvuthana>(type, entity, "die", 70) {
            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.getTicksInUse() == 1) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_HURT.get(), this.getUser().m_6121_(), this.getUser().m_6100_());
               }

               if (this.getTicksInUse() == 15) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_RETRACT.get(), this.getUser().m_6121_(), 1.0F);
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaHurtAbility> HURT_ABILITY = new AbilityType(
      "umvuthana_hurt", EntityUmvuthana.UmvuthanaHurtAbility::new
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaAttackAbility> ATTACK_ABILITY = new AbilityType(
      "umvuthana_attack", EntityUmvuthana.UmvuthanaAttackAbility::new
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> ALERT_ABILITY = new AbilityType(
      "umvuthana_alert", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, "alert", 15, true) {
            int soundFrame;

            @Override
            public void start() {
               super.start();
               this.soundFrame = this.rand.nextInt(7);
            }

            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.soundFrame == this.getTicksInUse()) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_ALERT.get(), this.getUser().m_6121_(), this.getUser().m_6100_());
               }

               if (this.getUser().m_5448_() != null) {
                  this.getUser().m_21391_(this.getUser().m_5448_(), 30.0F, 30.0F);
                  this.getUser().m_21563_().m_24960_(this.getUser().m_5448_(), 30.0F, 30.0F);
               }
            }

            @Override
            public void end() {
               super.end();
               if ((double)this.rand.nextFloat() < 0.2) {
                  this.getUser().sendAbilityMessage(EntityUmvuthana.ROAR_ABILITY);
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> ROAR_ABILITY = new AbilityType(
      "umvuthana_roar", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, "roar", 35, true) {
            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.getTicksInUse() == 2) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_ROAR.get(), this.getUser().m_6121_() + 0.5F, this.getUser().m_6100_());
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> ACTIVATE_ABILITY = new AbilityType(
      "umvuthana_activate", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, "emerge", 21) {
            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.getTicksInUse() == 5) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_EMERGE.get(), 1.0F, 1.0F);
               }

               if (this.getTicksInUse() == 10) {
                  this.getUser().active = true;
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthana, SimpleAnimationAbility<EntityUmvuthana>> DEACTIVATE_ABILITY = new AbilityType(
      "umvuthana_deactivate", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthana>(type, entity, "retract", 11) {
            @Override
            public void end() {
               super.end();
               this.getUser().m_146870_();
               ItemUmvuthanaMask mask = EntityUmvuthana.getMaskFromType(this.getUser().getMaskType());
               if (!this.getUser().f_19853_.f_46443_) {
                  ItemEntity itemEntity = this.getUser().m_5552_(this.getUser().getDeactivatedMask(mask), 1.5F);
                  if (itemEntity != null) {
                     ItemStack item = itemEntity.m_32055_();
                     item.m_41721_((int)Math.ceil((double)((1.0F - this.getUser().getHealthRatio()) * (float)item.m_41776_())));
                     item.m_41714_(this.getUser().m_7770_());
                  }
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthana, BlockAbility<EntityUmvuthana>> BLOCK_ABILITY = new AbilityType(
      "umvuthana_block", (type, entity) -> new BlockAbility<>(type, entity, "block", 10)
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaBlockCounterAbility> BLOCK_COUNTER_ABILITY = new AbilityType(
      "umvuthana_block_counter", EntityUmvuthana.UmvuthanaBlockCounterAbility::new
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaTeleportAbility> TELEPORT_ABILITY = new AbilityType(
      "umvuthana_teleport", EntityUmvuthana.UmvuthanaTeleportAbility::new
   );
   public static final AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaHealAbility> HEAL_ABILITY = new AbilityType(
      "umvuthana_heal", EntityUmvuthana.UmvuthanaHealAbility::new
   );
   private static final EntityDataAccessor<Boolean> DANCING = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> MASK = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> WEAPON = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> ACTIVE = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Float> HEALPOSX = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HEALPOSY = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Float> HEALPOSZ = SynchedEntityData.m_135353_(EntityUmvuthana.class, EntityDataSerializers.f_135029_);
   private boolean circleDirection = true;
   protected int circleTick = 0;
   protected boolean attacking = false;
   private int ticksWithoutTarget;
   public int timeUntilDeath = -1;
   private int blockCount = 0;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] staffPos;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] headPos;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] barakoPos;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] myPos;
   protected Vec3 teleportDestination;
   private static final byte FOOTSTEP_ID = 69;
   private int footstepCounter = 0;
   int maskTimingOffset = this.f_19796_.m_216339_(0, 150);
   protected AnimationController<MowzieGeckoEntity> maskController = new MowzieAnimationController<>(
      this, "mask_controller", 1.0F, this::predicateMask, (double)this.maskTimingOffset
   );
   protected MowzieAnimationController<MowzieGeckoEntity> walkRunController = new MowzieAnimationController<>(
      this, "walk_run_controller", 4.0F, EasingType.EaseInOutQuad, this::predicateWalkRun, 0.0
   );
   private float prevMaskRot = 0.0F;
   private boolean rattling = false;

   public EntityUmvuthana(EntityType<? extends EntityUmvuthana> type, Level world) {
      super(type, world);
      this.setMask(MaskType.from(Mth.m_216271_(this.f_19796_, 1, 4)));
      this.f_19793_ = 1.0F;
      this.circleTick = this.circleTick + this.f_19796_.m_188503_(200);
      this.frame = this.frame + this.f_19796_.m_188503_(50);
      this.f_21364_ = 6;
      this.active = false;
      if (world.f_46443_) {
         this.staffPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.barakoPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.myPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.headPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }
   }

   protected void m_8099_() {
      super.m_8099_();
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, -8.0F);
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new UseAbilityAI<>(this, ACTIVATE_ABILITY));
      this.f_21345_.m_25352_(0, new UseAbilityAI<>(this, DEACTIVATE_ABILITY));
      this.f_21345_.m_25352_(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.f_21345_.m_25352_(3, new EntityAIAvoidEntity<>(this, EntitySunstrike.class, EntitySunstrike::isStriking, 3.0F, 0.7F));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, ATTACK_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, ALERT_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, ROAR_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, BLOCK_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, BLOCK_COUNTER_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, TELEPORT_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, HEAL_ABILITY, false));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 0.4));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, EntityUmvuthana.class, 8.0F));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, EntityUmvuthi.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new EntityUmvuthana.CircleAttackGoal(this, 6.5F));
      this.registerTargetGoals();
   }

   protected void registerTargetGoals() {
   }

   protected void registerHuntingTargetGoals() {
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Animal.class, 200, true, false, target -> {
         float volume = target.m_20205_() * target.m_20205_() * target.m_20206_();
         return (target.m_21051_(Attributes.f_22281_) == null || target.m_21133_(Attributes.f_22281_) < 3.0) && (double)volume > 0.1 && volume < 6.0F;
      }));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Zombie.class, 0, true, false, e -> !(e instanceof ZombifiedPiglin)));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, 0, true, false, null));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Zoglin.class, 0, true, false, null));
      this.f_21346_.m_25352_(6, new AvoidEntityGoal(this, Creeper.class, 6.0F, 1.0, 1.2));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, Player.class, 0, true, true, target -> {
         if (target instanceof Player) {
            if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
               return false;
            } else {
               ItemStack headArmorStack = (ItemStack)((Player)target).m_150109_().f_35975_.get(3);
               return !(headArmorStack.m_41720_() instanceof UmvuthanaMask);
            }
         } else {
            return true;
         }
      }));
   }

   @Override
   public void registerControllers(AnimationData data) {
      super.registerControllers(data);
      data.addAnimationController(this.maskController);
      data.addAnimationController(this.walkRunController);
   }

   protected <E extends IAnimatable> PlayState predicateMask(AnimationEvent<E> event) {
      if (this.m_6084_() && this.active && this.getActiveAbilityType() != HEAL_ABILITY) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("mask_twitch", EDefaultLoopTypes.LOOP));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   protected <E extends IAnimatable> PlayState predicateWalkRun(AnimationEvent<E> event) {
      float threshold = 0.9F;
      Animation currentAnim = event.getController().getCurrentAnimation();
      if (currentAnim != null && currentAnim.animationName.equals("run_switch")) {
         threshold = 0.7F;
      }

      if (event.getLimbSwingAmount() > threshold && !this.isStrafing()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("run_switch", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk_switch", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   @Override
   protected <E extends IAnimatable> void loopingAnimations(AnimationEvent<E> event) {
      if (this.active) {
         if (this.m_5912_()) {
            event.getController().transitionLengthTicks = 4.0;
            if (event.isMoving()) {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("walk_aggressive", EDefaultLoopTypes.LOOP));
            } else {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_aggressive", EDefaultLoopTypes.LOOP));
            }
         } else {
            event.getController().transitionLengthTicks = 4.0;
            if (event.isMoving()) {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("walk_neutral", EDefaultLoopTypes.LOOP));
            } else {
               event.getController().setAnimation(new AnimationBuilder().addAnimation("idle_neutral", EDefaultLoopTypes.LOOP));
            }
         }
      } else {
         event.getController().transitionLengthTicks = 0.0;
         if (!this.m_20096_() && !this.m_20077_() && !this.m_20069_()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("tumble", EDefaultLoopTypes.LOOP));
         } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("inactive", EDefaultLoopTypes.LOOP));
         }
      }
   }

   protected BodyRotationControl m_7560_() {
      return super.m_7560_();
   }

   @Override
   public AbilityType getDeathAbility() {
      return DIE_ABILITY;
   }

   @Override
   public AbilityType getHurtAbility() {
      return HURT_ABILITY;
   }

   protected SoundEvent m_7515_() {
      if (this.getActiveAbilityType() == DEACTIVATE_ABILITY) {
         return null;
      } else {
         int i = Mth.m_216271_(this.f_19796_, 0, MMSounds.ENTITY_UMVUTHANA_IDLE.size());
         return i < MMSounds.ENTITY_UMVUTHANA_IDLE.size() ? (SoundEvent)((Supplier)MMSounds.ENTITY_UMVUTHANA_IDLE.get(i)).get() : null;
      }
   }

   protected SoundEvent m_7975_(DamageSource source) {
      return this.active ? (SoundEvent)MMSounds.ENTITY_UMVUTHANA_HURT.get() : null;
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().m_22268_(Attributes.f_22281_, 3.0).m_22268_(Attributes.f_22276_, 8.0);
   }

   @Override
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData, CompoundTag compound) {
      if (this.canHoldVaryingWeapons()) {
         this.setWeapon(this.f_19796_.m_188503_(3) == 0 ? 1 : 0);
      }

      if (reason == MobSpawnType.COMMAND
         && !(this instanceof EntityUmvuthanaRaptor)
         && !(this instanceof EntityUmvuthanaCrane)
         && !(this instanceof EntityUmvuthanaCraneToPlayer)) {
         this.setMask(MaskType.from(Mth.m_216271_(this.f_19796_, 1, 4)));
      }

      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   public int getAnimationTick() {
      return 0;
   }

   protected boolean canHoldVaryingWeapons() {
      return true;
   }

   protected Vec3 updateCirclingPosition(float radius, float speed) {
      LivingEntity target = this.m_5448_();
      if (target != null) {
         if (this.f_19796_.m_188503_(200) == 0) {
            this.circleDirection = !this.circleDirection;
         }

         if (this.circleDirection) {
            this.circleTick++;
         } else {
            this.circleTick--;
         }

         return this.circleEntityPosition(target, radius, speed, true, this.circleTick, 0.0F);
      } else {
         return null;
      }
   }

   @Override
   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.m_5776_()
         && this.f_20919_ < 20
         && this.active
         && (
            this.getActiveAbilityType() != TELEPORT_ABILITY
               || this.getActiveAbility().getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
         )) {
         if (this.tickTimer() % 10 == 1) {
            AdvancedParticleBase.spawnParticle(
               this.f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.GLOW.get(),
               this.m_20185_(),
               this.m_20186_(),
               this.m_20189_(),
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               1.0,
               1.0,
               0.3,
               0.4,
               1.0,
               9.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PinLocation(this.headPos),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.oscillate(9.0F, 10.0F, 12), false
                  )
               }
            );
         }

         if (this.headPos != null && this.headPos.length > 0 && this.headPos[0] != null && this.f_19796_.m_188501_() < 0.3F) {
            int amount = this.f_19796_.m_188503_(2) + 1;

            while (amount-- > 0) {
               float theta = this.f_19796_.m_188501_() * (float) (Math.PI * 2);
               float r = this.f_19796_.m_188501_() * 0.4F;
               float x = r * Mth.m_14089_(theta);
               float z = r * Mth.m_14031_(theta);
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.headPos[0].m_7096_() + (double)x,
                     this.headPos[0].m_7098_() + 0.1,
                     this.headPos[0].m_7094_() + (double)z,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.m_5448_() != null && (this.m_5448_().m_213877_() || this.m_5448_().m_21224_())) {
         this.m_6710_(null);
      }

      if (this.getActiveAbilityType() != BLOCK_ABILITY && this.blockCount > 0 && this.f_19797_ % 10 == 0) {
         this.blockCount--;
      }

      if (!this.f_19853_.f_46443_ && this.active && !this.getActive()) {
         this.setActive(true);
      }

      this.active = this.getActive();
      if (this.active) {
         if (this.getActiveAbility() != null) {
            this.m_21573_().m_26573_();
            this.f_20885_ = this.f_20883_ = this.m_146908_();
         }

         if (this.m_5448_() != null && this.ticksWithoutTarget > 3) {
            this.sendAbilityMessage(ALERT_ABILITY);
         }

         if (this.m_5448_() == null) {
            this.ticksWithoutTarget++;
         } else {
            this.ticksWithoutTarget = 0;
         }

         if (this.timeUntilDeath > 0) {
            this.timeUntilDeath--;
         } else if (this.timeUntilDeath == 0) {
            this.m_6469_(DamageSource.m_19367_(this, null), this.m_21223_() + 1.0F);
         }
      } else {
         this.m_21573_().m_26573_();
         this.m_146922_(this.f_19859_);
         this.f_20883_ = this.m_146908_();
         if ((this.f_19861_ || this.m_20069_() || this.m_20077_()) && this.getActiveAbility() == null) {
            this.sendAbilityMessage(ACTIVATE_ABILITY);
         }
      }
   }

   public void updateRattleSound(float maskRot) {
      if (!this.rattling) {
         if ((double)Math.abs(maskRot - this.prevMaskRot) > 0.06) {
            this.f_19853_
               .m_7785_(
                  this.m_20185_(),
                  this.m_20186_(),
                  this.m_20189_(),
                  (SoundEvent)MMSounds.ENTITY_UMVUTHANA_RATTLE.get(),
                  SoundSource.HOSTILE,
                  0.03F,
                  this.m_6100_(),
                  false
               );
         }
      } else if ((double)Math.abs(maskRot - this.prevMaskRot) < 1.0E-8) {
         this.rattling = false;
      }

      this.prevMaskRot = maskRot;
   }

   protected float m_6059_() {
      if (this.m_20096_()) {
         this.f_19853_.m_7605_(this, (byte)69);
      }

      return super.m_6059_();
   }

   @Override
   public void m_7822_(byte id) {
      if (id == 69) {
         this.footstepCounter++;
         float rotation = (float)Math.toRadians((double)(this.f_20883_ + 180.0F));
         Vec3 offset = new Vec3(0.0, 0.0, this.footstepCounter % 2 == 0 ? 0.3 : -0.3).m_82524_(rotation);
         AdvancedParticleBase.spawnParticle(
            this.f_19853_,
            (ParticleType<AdvancedParticleData>)ParticleHandler.STRIX_FOOTPRINT.get(),
            this.m_20185_() + offset.m_7096_(),
            this.m_20186_() + 0.01,
            this.m_20189_() + offset.m_7094_(),
            0.0,
            0.0,
            0.0,
            false,
            (double)rotation,
            Math.PI / 2,
            0.0,
            0.0,
            1.0,
            1.0,
            0.95,
            0.1,
            1.0,
            1.0,
            200.0,
            true,
            false,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.RED,
                  new ParticleComponent.KeyTrack(new float[]{0.995F, 0.05F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.GREEN,
                  new ParticleComponent.KeyTrack(new float[]{0.95F, 0.05F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.BLUE,
                  new ParticleComponent.KeyTrack(new float[]{0.1F, 0.05F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                  new ParticleComponent.KeyTrack(new float[]{1.0F, 0.8F}, new float[]{0.0F, 0.3F}),
                  false
               ),
               new ParticleComponent() {
                  @Override
                  public void postUpdate(AdvancedParticleBase particle) {
                     super.postUpdate(particle);
                     if (particle.getAge() < 80.0F && EntityUmvuthana.this.f_19796_.m_188501_() < 0.3F) {
                        int amount = 1;

                        while (amount-- > 0) {
                           float theta = EntityUmvuthana.this.f_19796_.m_188501_() * (float) (Math.PI * 2);
                           float r = EntityUmvuthana.this.f_19796_.m_188501_() * 0.2F;
                           float x = r * Mth.m_14089_(theta);
                           float z = r * Mth.m_14031_(theta);
                           EntityUmvuthana.this.f_19853_
                              .m_7106_(
                                 ParticleTypes.f_123762_,
                                 particle.getPosX() + (double)x,
                                 particle.getPosY() + 0.05,
                                 particle.getPosZ() + (double)z,
                                 0.0,
                                 0.0,
                                 0.0
                              );
                        }
                     }
                  }
               }
            }
         );
      } else {
         super.m_7822_(id);
      }
   }

   public static ItemUmvuthanaMask getMaskFromType(MaskType maskType) {
      ItemUmvuthanaMask mask = ItemHandler.UMVUTHANA_MASK_FURY;
      switch (maskType) {
         case BLISS:
            mask = ItemHandler.UMVUTHANA_MASK_BLISS;
            break;
         case FEAR:
            mask = ItemHandler.UMVUTHANA_MASK_FEAR;
            break;
         case FURY:
            mask = ItemHandler.UMVUTHANA_MASK_FURY;
            break;
         case MISERY:
            mask = ItemHandler.UMVUTHANA_MASK_MISERY;
            break;
         case RAGE:
            mask = ItemHandler.UMVUTHANA_MASK_RAGE;
            break;
         case FAITH:
            mask = ItemHandler.UMVUTHANA_MASK_FAITH;
      }

      return mask;
   }

   protected ItemStack getDeactivatedMask(ItemUmvuthanaMask mask) {
      return new ItemStack(mask);
   }

   protected SoundEvent m_5592_() {
      this.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_DIE.get(), 1.0F, 0.95F + this.f_19796_.m_188501_() * 0.1F);
      return null;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(DANCING, false);
      this.m_20088_().m_135372_(MASK, 0);
      this.m_20088_().m_135372_(WEAPON, 0);
      this.m_20088_().m_135372_(ACTIVE, true);
      this.m_20088_().m_135372_(HEALPOSX, 0.0F);
      this.m_20088_().m_135372_(HEALPOSY, 0.0F);
      this.m_20088_().m_135372_(HEALPOSZ, 0.0F);
   }

   public boolean getDancing() {
      return (Boolean)this.m_20088_().m_135370_(DANCING);
   }

   public void setDancing(boolean dancing) {
      this.m_20088_().m_135381_(DANCING, dancing);
   }

   public MaskType getMaskType() {
      return MaskType.from((Integer)this.m_20088_().m_135370_(MASK));
   }

   public void setMask(MaskType type) {
      this.m_20088_().m_135381_(MASK, type.ordinal());
      this.m_8061_(EquipmentSlot.HEAD, getMaskFromType(type).m_7968_());
      this.m_21409_(EquipmentSlot.HEAD, 0.0F);
   }

   public int getWeapon() {
      return (Integer)this.m_20088_().m_135370_(WEAPON);
   }

   public void setWeapon(int type) {
      this.m_20088_().m_135381_(WEAPON, type);
   }

   public boolean getActive() {
      return (Boolean)this.m_20088_().m_135370_(ACTIVE);
   }

   public void setActive(boolean active) {
      this.m_20088_().m_135381_(ACTIVE, active);
   }

   public Vec3 getHealPos() {
      return new Vec3(
         (double)((Float)this.m_20088_().m_135370_(HEALPOSX)).floatValue(),
         (double)((Float)this.m_20088_().m_135370_(HEALPOSY)).floatValue(),
         (double)((Float)this.m_20088_().m_135370_(HEALPOSZ)).floatValue()
      );
   }

   public void setHealPos(Vec3 vec) {
      this.m_20088_().m_135381_(HEALPOSX, (float)vec.f_82479_);
      this.m_20088_().m_135381_(HEALPOSY, (float)vec.f_82480_);
      this.m_20088_().m_135381_(HEALPOSZ, (float)vec.f_82481_);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("mask", this.getMaskType().ordinal());
      compound.m_128405_("weapon", this.getWeapon());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setMask(MaskType.from(compound.m_128451_("mask")));
      this.setWeapon(compound.m_128451_("weapon"));
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (this.getActiveAbilityType() == DEACTIVATE_ABILITY) {
         return false;
      } else {
         Entity entity = source.m_7639_();
         if (source == DamageSource.f_19309_) {
            return false;
         } else {
            boolean angleFlag = true;
            if (entity != null) {
               int arc = 220;
               Vec3 entityPos = entity.m_20182_();
               float entityHitAngle = (float)(
                  (Math.atan2(entityPos.m_7094_() - this.m_20189_(), entityPos.m_7096_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
               );
               float entityAttackingAngle = this.f_20883_ % 360.0F;
               if (entityHitAngle < 0.0F) {
                  entityHitAngle += 360.0F;
               }

               if (entityAttackingAngle < 0.0F) {
                  entityAttackingAngle += 360.0F;
               }

               float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
               angleFlag = (double)entityRelativeAngle <= (double)arc / 2.0 && (double)entityRelativeAngle >= (double)(-arc) / 2.0
                  || (double)entityRelativeAngle >= 360.0 - (double)arc / 2.0
                  || (double)entityRelativeAngle <= (double)(-arc) + 45.0;
            }

            if (angleFlag
               && this.getMaskType().canBlock
               && entity instanceof LivingEntity
               && (this.getActiveAbility() == null || this.getActiveAbilityType() == HURT_ABILITY || this.getActiveAbilityType() == BLOCK_ABILITY)
               && !source.m_19376_()) {
               this.blockingEntity = (LivingEntity)entity;
               this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
               if (this.blockingEntity == this.m_5448_()
                  && (double)this.f_19796_.m_188501_() < Mth.m_14008_((double)this.blockCount / 5.0, 0.0, 1.0)
                  && this.m_20270_(this.blockingEntity) < 4.0F) {
                  AbilityHandler.INSTANCE.sendAbilityMessage(this, BLOCK_COUNTER_ABILITY);
                  this.blockCount = 0;
               } else {
                  AbilityHandler.INSTANCE.sendAbilityMessage(this, BLOCK_ABILITY);
                  this.blockCount++;
               }

               return false;
            } else {
               return super.m_6469_(source, damage);
            }
         }
      }
   }

   protected ResourceLocation m_7582_() {
      switch (this.getMaskType()) {
         case BLISS:
            return LootTableHandler.UMVUTHANA_BLISS;
         case FEAR:
            return LootTableHandler.UMVUTHANA_FEAR;
         case FURY:
            return LootTableHandler.UMVUTHANA_FURY;
         case MISERY:
            return LootTableHandler.UMVUTHANA_MISERY;
         case RAGE:
            return LootTableHandler.UMVUTHANA_RAGE;
         case FAITH:
            return LootTableHandler.UMVUTHANA_FAITH;
         default:
            return LootTableHandler.UMVUTHANA_FURY;
      }
   }

   public boolean m_6087_() {
      return this.active;
   }

   public boolean m_142535_(float distance, float damageMultipler, DamageSource source) {
      return this.active ? super.m_142535_(distance, damageMultipler, source) : false;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.UMVUTHANA.combatConfig;
   }

   public boolean isUmvuthiDevoted() {
      return true;
   }

   public int randomizeWeapon() {
      return this.f_19796_.m_188503_(3) == 0 ? 1 : 0;
   }

   public boolean canHeal(LivingEntity entity) {
      return false;
   }

   protected void sunBlockTarget() {
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{
         DIE_ABILITY,
         HURT_ABILITY,
         ATTACK_ABILITY,
         ALERT_ABILITY,
         ROAR_ABILITY,
         ACTIVATE_ABILITY,
         DEACTIVATE_ABILITY,
         BLOCK_ABILITY,
         BLOCK_COUNTER_ABILITY,
         TELEPORT_ABILITY,
         HEAL_ABILITY
      };
   }

   protected static class CircleAttackGoal extends Goal {
      private final EntityUmvuthana mob;
      private final float attackRadius;
      private int strafingLeftRightMul;
      private int strafingFrontBackMul;
      private boolean chasing = false;
      protected boolean attacking = false;
      private int timeSinceAttack = 0;

      public CircleAttackGoal(EntityUmvuthana mob, float attackRadius) {
         this.mob = mob;
         this.attackRadius = attackRadius;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean m_8036_() {
         return this.mob.m_5448_() != null;
      }

      public boolean m_8045_() {
         return this.m_8036_() || !this.mob.m_21573_().m_26571_();
      }

      public void m_8056_() {
         super.m_8056_();
         this.mob.m_21561_(true);
         this.timeSinceAttack = this.mob.f_19796_.m_188503_(80);
      }

      public void m_8041_() {
         super.m_8041_();
         this.mob.m_21561_(false);
         this.mob.setStrafing(false);
         this.mob.m_21566_().m_24988_(0.0F, 0.0F);
         this.attacking = false;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         LivingEntity target = this.mob.m_5448_();
         if (target != null) {
            if (this.timeSinceAttack < 80) {
               this.timeSinceAttack++;
            }

            double distToTarget = (double)this.mob.m_20270_(target);
            float frontBackDistBuffer = 2.0F;
            float leftRightDistBuffer = 1.5F;
            if (this.chasing && distToTarget <= (double)this.attackRadius) {
               this.chasing = false;
            }

            if (!this.chasing && distToTarget >= (double)(this.attackRadius + frontBackDistBuffer)) {
               this.chasing = true;
            }

            if (this.chasing) {
               this.mob.m_21573_().m_5624_(target, 0.6);
               this.mob.m_21563_().m_24960_(target, 30.0F, 30.0F);
               this.mob.setStrafing(false);
               this.mob.m_21566_().m_24988_(0.0F, 0.0F);
            } else {
               if (!this.attacking && this.mob.getActiveAbility() == null) {
                  this.mob.m_21573_().m_26573_();
                  float strafeSpeed = 0.5F;
                  Vec3 circlePos = this.mob.updateCirclingPosition(this.attackRadius, strafeSpeed - 0.2F);
                  double distToCirclePos = this.mob.m_20182_().m_82554_(circlePos);
                  if (distToCirclePos <= (double)leftRightDistBuffer) {
                     this.mob.setStrafing(true);
                     if (distToTarget > (double)this.attackRadius + 0.5) {
                        this.strafingFrontBackMul = 1;
                     } else if (distToTarget < (double)this.attackRadius - 0.5) {
                        this.strafingFrontBackMul = -1;
                     } else {
                        this.strafingFrontBackMul = 0;
                     }

                     Vec3 toTarget = target.m_20182_().m_82546_(this.mob.m_20182_()).m_82542_(1.0, 0.0, 1.0).m_82541_();
                     Vec3 toCirclePos = circlePos.m_82546_(this.mob.m_20182_()).m_82542_(1.0, 0.0, 1.0).m_82541_();
                     Vec3 cross = toTarget.m_82537_(toCirclePos);
                     if (cross.f_82480_ > 0.0) {
                        this.strafingLeftRightMul = 1;
                     } else if (cross.f_82480_ < 0.0) {
                        this.strafingLeftRightMul = -1;
                     } else {
                        this.strafingLeftRightMul = 0;
                     }

                     float distScale = (float)Math.min(Math.pow(distToCirclePos * 1.0 / (double)leftRightDistBuffer, 0.7), 1.0);
                     this.mob.m_21566_().m_24988_((float)this.strafingFrontBackMul * strafeSpeed, (float)this.strafingLeftRightMul * strafeSpeed * distScale);
                     this.mob.m_21391_(target, 30.0F, 30.0F);
                     if ((double)this.mob.f_19796_.m_188501_() < 0.002) {
                        this.mob.sendAbilityMessage(EntityUmvuthana.ROAR_ABILITY);
                     }
                  } else {
                     this.mob.setStrafing(false);
                     this.mob.m_21566_().m_24988_(0.0F, 0.0F);
                     this.mob.m_21573_().m_26519_(circlePos.f_82479_, circlePos.f_82480_, circlePos.f_82481_, 0.53);
                     this.mob.m_21563_().m_24960_(target, 30.0F, 30.0F);
                  }
               } else {
                  this.mob.m_21566_().m_24988_(0.0F, 0.0F);
                  this.mob.setStrafing(false);
               }

               if (this.mob.f_19796_.m_188503_(80) == 0 && this.timeSinceAttack >= 80 && this.mob.m_21574_().m_148306_(target)) {
                  this.attacking = true;
               }

               if (this.attacking && this.mob.getActiveAbility() == null) {
                  this.mob.m_21573_().m_5624_(target, 0.5);
                  if (distToTarget <= 3.75 && this.mob.m_21574_().m_148306_(target)) {
                     this.attacking = false;
                     this.timeSinceAttack = 0;
                     AbilityHandler.INSTANCE.sendAbilityMessage(this.mob, EntityUmvuthana.ATTACK_ABILITY);
                  }
               }
            }
         }
      }
   }

   private static class UmvuthanaAttackAbility extends MeleeAttackAbility<EntityUmvuthana> {
      public UmvuthanaAttackAbility(AbilityType<EntityUmvuthana, ? extends MeleeAttackAbility<EntityUmvuthana>> abilityType, EntityUmvuthana user) {
         super(abilityType, user, new String[]{"attack_slash_left", "attack_slash_right"}, null, null, 1.0F, 3.0F, 1.0F, 13, 9, true);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 5) {
            this.getUser().m_20256_(this.getUser().m_20184_().m_82549_(this.getUser().m_20156_().m_82541_().m_82490_(0.5)));
         }

         if (this.getTicksInUse() == 1) {
            int i = this.rand.nextInt(MMSounds.ENTITY_UMVUTHANA_ATTACK.size());
            this.getUser().m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_UMVUTHANA_ATTACK.get(i)).get(), 1.0F, this.rand.nextFloat(0.9F, 1.1F));
         }
      }
   }

   private static class UmvuthanaBlockCounterAbility extends MeleeAttackAbility<EntityUmvuthana> {
      public UmvuthanaBlockCounterAbility(AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaBlockCounterAbility> abilityType, EntityUmvuthana user) {
         super(abilityType, user, new String[]{"block_counter"}, null, null, 3.0F, 2.2F, 1.2F, 7, 11, false);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().m_20331_(true);
      }

      @Override
      public void end() {
         super.end();
         this.getUser().m_20331_(false);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 5) {
            float distToTarget = 1.0F;
            if (this.getUser().m_5448_() != null) {
               distToTarget = Mth.m_14036_(this.getUser().m_20270_(this.getUser().m_5448_()) / 2.0F - 1.0F, 0.0F, 1.0F);
            }

            this.getUser().m_20256_(this.getUser().m_20184_().m_82549_(this.getUser().m_20156_().m_82541_().m_82490_(1.6 * (double)distToTarget)));
         }

         if (this.getTicksInUse() == 0) {
            this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_ATTACK_BIG.get(), 1.0F, this.rand.nextFloat(0.9F, 1.1F));
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return super.canCancelActiveAbility()
            || this.getUser().getActiveAbility() instanceof BlockAbility
            || this.getUser().getActiveAbility() instanceof HurtAbility;
      }
   }

   private static class UmvuthanaHealAbility extends Ability<EntityUmvuthana> {
      public UmvuthanaHealAbility(AbilityType<EntityUmvuthana, ? extends Ability> abilityType, EntityUmvuthana user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 16)
            }
         );
      }

      @Override
      protected void beginSection(AbilitySection section) {
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.playAnimation("heal_start", false);
         } else if (section.sectionType != AbilitySection.AbilitySectionType.ACTIVE) {
            this.playAnimation("heal_end", false);
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getUser().m_5448_() != null) {
            this.getUser().m_21563_().m_24960_(this.getUser().m_5448_(), (float)this.getUser().m_8085_(), (float)this.getUser().m_8132_());
            this.getUser().m_21391_(this.getUser().m_5448_(), (float)this.getUser().m_8085_(), (float)this.getUser().m_8132_());
         }

         if (this.getTicksInUse() == 6) {
            this.getUser().m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_UMVUTHANA_HEAL_START.get(this.rand.nextInt(3))).get(), 4.0F, 1.0F);
            MowziesMobs.PROXY.playSunblockSound(this.getUser());
         }

         if (this.getTicksInUse() >= 6) {
            EffectHandler.addOrCombineEffect(this.getUser(), MobEffects.f_19619_, 5, 0, false, false);
         }

         if (this.getUser().f_19853_.f_46443_ && this.getTicksInUse() == 5 && this.getUser().headPos != null && this.getUser().headPos.length >= 1) {
            this.getUser().headPos[0] = this.getUser().m_20182_().m_82520_(0.0, (double)this.getUser().m_20192_(), 0.0);
         }

         if (this.getTicksInUse() == 12) {
            this.playAnimation("heal_loop", true);
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.spawnHealParticles();
            this.getUser().sunBlockTarget();
            if (!this.getLevel().m_5776_() && this.getUser().m_5448_() == null) {
               AbilityHandler.INSTANCE.sendJumpToSectionMessage(this.getUser(), this.getAbilityType(), 2);
            }
         }
      }

      public void spawnHealParticles() {
         if (this.getUser().m_5448_() != null) {
            this.getUser().setHealPos(this.getUser().m_5448_().m_20182_().m_82549_(new Vec3(0.0, (double)(this.getUser().m_5448_().m_20206_() / 2.0F), 0.0)));
         }

         if (this.getUser().f_19853_.f_46443_ && this.getUser().barakoPos != null) {
            this.getUser().barakoPos[0] = this.getUser().getHealPos();
            if (this.getUser().headPos != null && this.getUser().headPos[0] != null) {
               double dist = Math.max(this.getUser().barakoPos[0].m_82554_(this.getUser().headPos[0]), 0.01);
               double radius = 0.5;
               double yaw = (double)(this.rand.nextFloat() * 2.0F) * Math.PI;
               double pitch = (double)(this.rand.nextFloat() * 2.0F) * Math.PI;
               double ox = radius * Math.sin(yaw) * Math.sin(pitch);
               double oy = radius * Math.cos(pitch);
               double oz = radius * Math.cos(yaw) * Math.sin(pitch);
               if (this.getTicksInUse() % 5 == 0) {
                  AdvancedParticleBase.spawnParticle(
                     this.getUser().f_19853_,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.ARROW_HEAD.get(),
                     this.getUser().headPos[0].m_7096_(),
                     this.getUser().headPos[0].m_7098_(),
                     this.getUser().headPos[0].m_7094_(),
                     0.0,
                     0.0,
                     0.0,
                     false,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     3.5,
                     0.95,
                     0.9,
                     0.35,
                     0.75,
                     1.0,
                     Math.min(2.0 * dist, 60.0),
                     true,
                     false,
                     new ParticleComponent[]{
                        new ParticleComponent.Attractor(this.getUser().barakoPos, 0.5F, 0.2F, ParticleComponent.Attractor.EnumAttractorBehavior.LINEAR),
                        new RibbonComponent(
                           (ParticleType<? extends RibbonParticleData>)ParticleHandler.RIBBON_FLAT.get(),
                           10,
                           0.0,
                           0.0,
                           0.0,
                           0.12F,
                           0.95,
                           0.9,
                           0.35,
                           0.75,
                           true,
                           true,
                           new ParticleComponent[]{
                              new RibbonComponent.PropertyOverLength(
                                 RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F)
                              )
                           }
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_X,
                           new ParticleComponent.Oscillator(0.0F, (float)ox, (float)(1.0 * dist), 2.5F),
                           true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y,
                           new ParticleComponent.Oscillator(0.0F, (float)oy, (float)(1.0 * dist), 2.5F),
                           true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z,
                           new ParticleComponent.Oscillator(0.0F, (float)oz, (float)(1.0 * dist), 2.5F),
                           true
                        ),
                        new ParticleComponent.FaceMotion(),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 0.0F, 1.0F}, new float[]{0.0F, 0.05F, 0.06F}),
                           false
                        )
                     }
                  );
               }

               if (this.getTicksInUse() % 5 == 0) {
                  AdvancedParticleBase.spawnParticle(
                     this.getUser().f_19853_,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
                     this.getUser().headPos[0].m_7096_(),
                     this.getUser().headPos[0].m_7098_(),
                     this.getUser().headPos[0].m_7094_(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     1.5,
                     1.0,
                     0.8745098F,
                     0.25882354F,
                     1.0,
                     1.0,
                     15.0,
                     true,
                     false,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 10.0F), false
                        )
                     }
                  );
               }

               int spawnFreq = 5;
               if (this.getTicksInUse() % spawnFreq == 0) {
                  ParticleRibbon.spawnRibbon(
                     this.getUser().f_19853_,
                     (ParticleType<? extends RibbonParticleData>)ParticleHandler.RIBBON_SQUIGGLE.get(),
                     (int)(0.5 * dist),
                     this.getUser().headPos[0].m_7096_(),
                     this.getUser().headPos[0].m_7098_(),
                     this.getUser().headPos[0].m_7094_(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.5,
                     0.95,
                     0.9,
                     0.35,
                     0.75,
                     1.0,
                     (double)spawnFreq,
                     true,
                     new ParticleComponent[]{
                        new RibbonComponent.BeamPinning(this.getUser().headPos, this.getUser().barakoPos), new RibbonComponent.PanTexture(0.0F, 1.0F)
                     }
                  );
               }
            }
         }
      }

      @Override
      public boolean damageInterrupts() {
         return false;
      }
   }

   private static class UmvuthanaHurtAbility extends HurtAbility<EntityUmvuthana> {
      public UmvuthanaHurtAbility(AbilityType<EntityUmvuthana, EntityUmvuthana.UmvuthanaHurtAbility> abilityType, EntityUmvuthana user) {
         super(abilityType, user, "", 12);
      }

      @Override
      public void start() {
         super.start();
         if (this.getUser().m_5912_()) {
            if (this.getUser().f_19796_.m_188499_()) {
               this.playAnimation("hurt_right_aggressive", false);
            } else {
               this.playAnimation("hurt_left_aggressive", false);
            }
         } else if (this.getUser().f_19796_.m_188499_()) {
            this.playAnimation("hurt_right_neutral", false);
         } else {
            this.playAnimation("hurt_left_neutral", false);
         }
      }
   }

   private static class UmvuthanaTeleportAbility extends Ability<EntityUmvuthana> {
      private Vec3 teleportStart;
      private static int ACTIVE_DURATION = 7;

      public UmvuthanaTeleportAbility(AbilityType<EntityUmvuthana, ? extends Ability> abilityType, EntityUmvuthana user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 7),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, ACTIVE_DURATION),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 13)
            }
         );
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.playAnimation("teleport_start", false);
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.teleportStart = this.getUser().m_20182_();
            this.playAnimation("teleport_loop", true);
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            this.playAnimation("teleport_end", false);
         }
      }

      @Override
      protected void endSection(AbilitySection section) {
         super.endSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.getUser().teleportDestination != null) {
            this.getUser()
               .m_6021_(
                  this.getUser().teleportDestination.m_7096_(), this.getUser().teleportDestination.m_7098_(), this.getUser().teleportDestination.m_7094_()
               );
            this.getUser().m_20334_(0.0, 0.0, 0.0);
            this.getUser().m_21573_().m_26573_();
         }
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 2) {
            this.getUser().m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_UMVUTHANA_TELEPORT.get(this.rand.nextInt(3))).get(), 3.0F, 1.0F);
         }

         if (this.getTicksInUse() == 16) {
            this.getUser().m_5496_((SoundEvent)((Supplier)MMSounds.ENTITY_UMVUTHANA_TELEPORT.get(this.rand.nextInt(3))).get(), 3.0F, 1.2F);
         }

         if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE
            && this.teleportStart != null
            && this.getUser().teleportDestination != null) {
            float t = (float)this.getTicksInSection() / (float)ACTIVE_DURATION;
            t = (float)(0.5 - 0.5 * Math.cos((double)t * Math.PI));
            Vec3 newPos = this.teleportStart.m_82549_(this.getUser().teleportDestination.m_82546_(this.teleportStart).m_82490_((double)t));
            this.getUser().m_6021_(newPos.m_7096_(), newPos.m_7098_(), newPos.m_7094_());
            this.getUser().m_21573_().m_26573_();
         }

         if (this.getUser().m_5448_() != null) {
            this.getUser().m_21563_().m_24960_(this.getUser().m_5448_(), 30.0F, 30.0F);
         }

         if (this.getUser().f_19853_.f_46443_) {
            this.getUser().myPos[0] = this.getUser().m_20182_().m_82520_(0.0, 1.2F, 0.0);
            if (this.getTicksInUse() == 5) {
               ParticleComponent.KeyTrack keyTrack1 = ParticleComponent.KeyTrack.oscillate(0.0F, 2.0F, 24);
               ParticleComponent.KeyTrack keyTrack2 = new ParticleComponent.KeyTrack(new float[]{0.0F, 18.0F, 18.0F, 0.0F}, new float[]{0.0F, 0.2F, 0.8F, 1.0F});
               AdvancedParticleBase.spawnParticle(
                  this.getUser().f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.SUN.get(),
                  this.getUser().m_20185_(),
                  this.getUser().m_20186_(),
                  this.getUser().m_20189_(),
                  0.0,
                  0.0,
                  0.0,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  1.0,
                  1.0,
                  1.0,
                  1.0,
                  1.0,
                  15.0,
                  true,
                  false,
                  new ParticleComponent[]{
                     new ParticleComponent.PinLocation(this.getUser().myPos),
                     new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, keyTrack2, false),
                     new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, keyTrack1, true),
                     new RibbonComponent(
                        (ParticleType<? extends RibbonParticleData>)ParticleHandler.RIBBON_FLAT.get(),
                        10,
                        0.0,
                        0.0,
                        0.0,
                        0.12F,
                        0.95,
                        0.9,
                        0.35,
                        0.75,
                        true,
                        true,
                        new ParticleComponent[]{
                           new RibbonComponent.PropertyOverLength(
                              RibbonComponent.PropertyOverLength.EnumRibbonProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F)
                           )
                        }
                     )
                  }
               );
            }

            this.getUser().myPos[0] = this.getUser().m_20182_().m_82520_(0.0, 1.2F, 0.0);
            if (this.getTicksInUse() == 4 || this.getTicksInUse() == 18) {
               int num = 5;

               for (int i = 0; i < num * num; i++) {
                  Vec3 v = new Vec3((0.3 + 0.15 * (double)this.rand.nextFloat()) * 0.8, 0.0, 0.0);
                  float increment = (float) (Math.PI * 2) / (float)num;
                  v = v.m_82524_(increment * this.rand.nextFloat() + increment * ((float)i / (float)num));
                  v = v.m_82535_(increment * this.rand.nextFloat() + increment * (float)(i % num));
                  AdvancedParticleBase.spawnParticle(
                     this.getUser().f_19853_,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
                     this.getUser().myPos[0].m_7096_(),
                     this.getUser().myPos[0].m_7098_(),
                     this.getUser().myPos[0].m_7094_(),
                     v.m_7096_(),
                     v.m_7098_(),
                     v.m_7094_(),
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     4.0,
                     0.98,
                     0.94,
                     0.39,
                     1.0,
                     0.8,
                     (double)(6.0F + this.rand.nextFloat() * 4.0F),
                     true,
                     false,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{4.0F, 0.0F}, new float[]{0.8F, 1.0F}),
                           false
                        )
                     }
                  );
               }
            }
         }
      }

      @Override
      public boolean canCancelActiveAbility() {
         return this.getUser().getActiveAbility() instanceof EntityUmvuthana.UmvuthanaHealAbility;
      }
   }
}
