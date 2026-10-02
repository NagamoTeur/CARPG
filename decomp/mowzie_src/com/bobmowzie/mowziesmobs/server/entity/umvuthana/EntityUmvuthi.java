package com.bobmowzie.mowziesmobs.server.entity.umvuthana;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.DieAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.mob.HurtAbility;
import com.bobmowzie.mowziesmobs.server.ability.abilities.player.SimpleAnimationAbility;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.ai.LookAtTargetGoal;
import com.bobmowzie.mowziesmobs.server.ai.NearestAttackableTargetPredicateGoal;
import com.bobmowzie.mowziesmobs.server.ai.UmvuthanaHurtByTargetAI;
import com.bobmowzie.mowziesmobs.server.ai.UseAbilityAI;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.bobmowzie.mowziesmobs.server.inventory.ContainerUmvuthiTrade;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.item.UmvuthanaMask;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class EntityUmvuthi extends MowzieGeckoEntity implements LeaderSunstrikeImmune, Enemy {
   public static final AbilityType<EntityUmvuthi, DieAbility<EntityUmvuthi>> DIE_ABILITY = new AbilityType(
      "umvuthi_die", (type, entity) -> new DieAbility<EntityUmvuthi>(type, entity, "death", 115) {
            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.getTicksInUse() == 1) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_HURT.get(), this.getUser().m_6121_(), this.getUser().m_6100_());
               }

               if (this.getTicksInUse() == 14) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_DIE.get(), this.getUser().m_6121_(), 1.0F);
               }

               if (this.getTicksInUse() == 80) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.MISC_METAL_IMPACT.get(), this.getUser().m_6121_(), 1.0F);
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthi, HurtAbility<EntityUmvuthi>> HURT_ABILITY = new AbilityType(
      "umvuthi_hurt", (type, entity) -> new HurtAbility<>(type, entity, "hurt", 13)
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> BELLY_ABILITY = new AbilityType(
      "umvuthi_belly", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthi>(type, entity, "belly_drum", 40, true) {
            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.getTicksInUse() == 9 || this.getTicksInUse() == 29) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_BELLY.get(), 3.0F, 1.0F);
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> TALK_ABILITY = new AbilityType<>(
      "umvuthi_talk", (type, entity) -> new SimpleAnimationAbility(type, entity, "talk", 23, true)
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> ROAR_ABILITY = new AbilityType(
      "umvuthi_roar", (type, entity) -> new SimpleAnimationAbility<EntityUmvuthi>(type, entity, "roar", 70, false) {
            @Override
            public void tickUsing() {
               super.tickUsing();
               if (this.getTicksInUse() == 2) {
                  this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_ROAR.get(), 3.0F, 1.0F);
               }
            }
         }
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SunstrikeAbility> SUNSTRIKE_ABILITY = new AbilityType(
      "umvuthi_sunstrike", EntityUmvuthi.SunstrikeAbility::new
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SolarFlareAbility> SOLAR_FLARE_ABILITY = new AbilityType(
      "umvuthi_flare", EntityUmvuthi.SolarFlareAbility::new
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SpawnFollowersAbility> SPAWN_ABILITY = new AbilityType(
      "umvuthi_spawn", (type, entity) -> new EntityUmvuthi.SpawnFollowersAbility(type, entity, false)
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SpawnFollowersAbility> SPAWN_SUNBLOCKERS_ABILITY = new AbilityType(
      "umvuthi_spawn_healers", (type, entity) -> new EntityUmvuthi.SpawnFollowersAbility(type, entity, true)
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SolarBeamAbility> SOLAR_BEAM_ABILITY = new AbilityType(
      "umvuthi_solar_beam", EntityUmvuthi.SolarBeamAbility::new
   );
   public static final AbilityType<EntityUmvuthi, SimpleAnimationAbility<EntityUmvuthi>> BLESS_ABILITY = new AbilityType(
      "umvuthi_bless",
      (type, entity) -> new SimpleAnimationAbility<EntityUmvuthi>(type, entity, "bless", 84) {
            @Override
            public boolean canCancelActiveAbility() {
               return this.getUser().getActiveAbilityType() == EntityUmvuthi.ROAR_ABILITY
                  || this.getUser().getActiveAbilityType() == EntityUmvuthi.TALK_ABILITY
                  || this.getUser().getActiveAbilityType() == EntityUmvuthi.BELLY_ABILITY;
            }
         }
   );
   public static final AbilityType<EntityUmvuthi, EntityUmvuthi.SupernovaAbility> SUPERNOVA_ABILITY = new AbilityType(
      "umvuthi_supernova", EntityUmvuthi.SupernovaAbility::new
   );
   protected AnimationController<MowzieGeckoEntity> maskController = new MowzieAnimationController<>(this, "mask_controller", 1.0F, this::predicateMask, 0.0);
   protected AnimationController<MowzieGeckoEntity> blinkController = new MowzieAnimationController<>(this, "blink_controller", 1.0F, this::predicateBlink, 0.0);
   private static final int MAX_HEALTH = 150;
   private static final int SUNSTRIKE_PAUSE_MAX = 50;
   private static final int SUNSTRIKE_PAUSE_MIN = 30;
   private static final int LASER_PAUSE = 230;
   private static final int SUPERNOVA_PAUSE = 230;
   private static final int UMVUTHANA_PAUSE = 200;
   private static final int ROAR_PAUSE = 300;
   private static final int HEAL_PAUSE = 75;
   private static final int HEALTH_LOST_BETWEEN_SUNBLOCKERS = 45;
   private static final EntityDataAccessor<Integer> DIRECTION = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> DIALOGUE = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<ItemStack> DESIRES = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135033_);
   private static final EntityDataAccessor<CompoundTag> TRADED_PLAYERS = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135042_);
   private static final EntityDataAccessor<Float> HEALTH_LOST = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Optional<UUID>> MISBEHAVED_PLAYER = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135041_);
   private static final EntityDataAccessor<Boolean> IS_TRADING = SynchedEntityData.m_135353_(EntityUmvuthi.class, EntityDataSerializers.f_135035_);
   public ControlledAnimation legsUp = new ControlledAnimation(15);
   public ControlledAnimation angryEyebrow = new ControlledAnimation(5);
   private Player customer;
   public int umvuthanaSpawnCount = 0;
   private int direction = 0;
   private boolean blocksByFeet = true;
   private int timeUntilSunstrike = 0;
   private int timeUntilLaser = 0;
   private int timeUntilUmvuthana = 0;
   private int timeUntilRoar = 0;
   private int timeUntilSupernova = 0;
   private int timeUntilHeal = 0;
   public Player blessingPlayer;
   private UmvuthanaHurtByTargetAI hurtByTargetAI;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] betweenHandPos;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] headPos;
   @OnlyIn(Dist.CLIENT)
   public Vec3[] blessingPlayerPos;
   private static final TargetingConditions GIVE_ACHIEVEMENT_PRED = TargetingConditions.m_148352_().m_26893_();
   private float prevMaskRot = 0.0F;
   private boolean rattling = false;

   public EntityUmvuthi(EntityType<? extends EntityUmvuthi> type, Level world) {
      super(type, world);
      if (this.getDirectionData() == 0) {
         this.setDirection(this.f_19796_.m_188503_(4) + 1);
      }

      this.f_21364_ = 45;
      if (world.f_46443_) {
         this.headPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.betweenHandPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
         this.blessingPlayerPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      this.active = true;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.hurtByTargetAI = new UmvuthanaHurtByTargetAI(this);
      this.f_21346_.m_25352_(3, this.hurtByTargetAI);
      this.f_21346_
         .m_25352_(
            4,
            new NearestAttackableTargetPredicateGoal<Player>(
               this, Player.class, 0, false, true, TargetingConditions.m_148352_().m_26883_(this.m_21133_(Attributes.f_22277_)).m_26888_(target -> {
                  if (target instanceof Player) {
                     if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
                        return false;
                     } else {
                        ItemStack headArmorStack = (ItemStack)((Player)target).m_150109_().f_35975_.get(3);
                        return !(headArmorStack.m_41720_() instanceof UmvuthanaMask) || target == this.getMisbehavedPlayer();
                     }
                  } else {
                     return true;
                  }
               }).m_148355_()
            ) {
               public void m_8041_() {
                  super.m_8041_();
                  EntityUmvuthi.this.setMisbehavedPlayerId(null);
               }
            }
         );
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, IronGolem.class, 0, false, false, null));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, Zombie.class, 0, false, false, e -> !(e instanceof ZombifiedPiglin)));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, AbstractSkeleton.class, 0, false, false, null));
      this.f_21345_.m_25352_(1, new UseAbilityAI<>(this, DIE_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, HURT_ABILITY, false));
      this.f_21345_.m_25352_(6, new UseAbilityAI<>(this, BELLY_ABILITY, false));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, SUNSTRIKE_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, SOLAR_FLARE_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, SOLAR_BEAM_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, SUPERNOVA_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, SPAWN_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, SPAWN_SUNBLOCKERS_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, BLESS_ABILITY));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, TALK_ABILITY, false));
      this.f_21345_.m_25352_(2, new UseAbilityAI<>(this, ROAR_ABILITY, true));
      this.f_21345_.m_25352_(5, new LookAtTargetGoal(this, 24.0F));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, EntityUmvuthana.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
   }

   protected float m_6431_(Pose poseIn, EntityDimensions sizeIn) {
      return super.m_6431_(poseIn, sizeIn);
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .m_22268_(Attributes.f_22281_, 2.0)
         .m_22268_(Attributes.f_22276_, 150.0)
         .m_22268_(Attributes.f_22278_, 1.0)
         .m_22268_(Attributes.f_22277_, 40.0);
   }

   @Override
   public void registerControllers(AnimationData data) {
      super.registerControllers(data);
      data.addAnimationController(this.maskController);
      data.addAnimationController(this.blinkController);
   }

   protected <E extends IAnimatable> PlayState predicateMask(AnimationEvent<E> event) {
      if (this.m_6084_()
         && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY
         && this.getActiveAbilityType() != SUPERNOVA_ABILITY
         && this.getActiveAbilityType() != SPAWN_ABILITY
         && this.getActiveAbilityType() != SPAWN_SUNBLOCKERS_ABILITY) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("mask_twitch", EDefaultLoopTypes.LOOP));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   protected <E extends IAnimatable> PlayState predicateBlink(AnimationEvent<E> event) {
      if (this.m_6084_() && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("blink", EDefaultLoopTypes.LOOP));
         return PlayState.CONTINUE;
      } else {
         return PlayState.STOP;
      }
   }

   @Override
   protected <E extends IAnimatable> void loopingAnimations(AnimationEvent<E> event) {
      event.getController().transitionLengthTicks = 4.0;
      super.loopingAnimations(event);
   }

   public boolean m_8023_() {
      return true;
   }

   protected SoundEvent m_7515_() {
      if (this.getActiveAbility() == null) {
         this.sendAbilityMessage(TALK_ABILITY);
         return (SoundEvent)MMSounds.ENTITY_UMVUTHI_IDLE.get();
      } else {
         return null;
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
                  0.04F,
                  this.m_6100_() * 0.75F,
                  false
               );
         }
      } else if ((double)Math.abs(maskRot - this.prevMaskRot) < 1.0E-8) {
         this.rattling = false;
      }

      this.prevMaskRot = maskRot;
   }

   protected SoundEvent m_7975_(DamageSource source) {
      return (SoundEvent)MMSounds.ENTITY_UMVUTHI_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return null;
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public boolean shouldRenderSun() {
      return this.f_20919_ < 85
         && (this.getActiveAbilityType() != SUPERNOVA_ABILITY || this.getActiveAbility().getTicksInUse() <= 5 || this.getActiveAbility().getTicksInUse() > 90);
   }

   @Override
   public void m_8119_() {
      this.legsUp.updatePrevTimer();
      this.angryEyebrow.updatePrevTimer();
      this.m_20334_(0.0, this.m_20184_().f_82480_, 0.0);
      super.m_8119_();
      if (this.f_19797_ == 1) {
         this.direction = this.getDirectionData();
      }

      if (this.getActiveAbilityType() != SOLAR_FLARE_ABILITY || this.getActiveAbility().getTicksInUse() < 12 || this.getActiveAbility().getTicksInUse() > 14) {
         this.repelEntities(1.2F, 1.2F, 1.2F, 1.2F);
      }

      this.m_146922_((float)((this.direction - 1) * 90));
      this.f_20883_ = this.m_146908_();
      if (this.f_19853_.m_5776_() && this.shouldRenderSun() && this.headPos != null && this.headPos.length > 0 && this.headPos[0] != null) {
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
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.oscillate(12.5F, 13.5F, 12), false
                  )
               }
            );
         }

         if (this.f_19796_.m_188501_() < 0.3F) {
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

      if (!this.f_19853_.f_46443_
         && this.getHealthLost() >= 45.0F
         && this.getActiveAbility() == null
         && !this.m_21525_()
         && this.getEntitiesNearby(EntityUmvuthanaCrane.class, 40.0).size() < 3) {
         this.sendAbilityMessage(SPAWN_SUNBLOCKERS_ABILITY);
         this.setHealthLost(0.0F);
      }

      if (this.m_5448_() != null) {
         LivingEntity target = this.m_5448_();
         this.setAngry(true);
         float entityHitAngle = (float)(
            (Math.atan2(target.m_20189_() - this.m_20189_(), target.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.m_146908_() % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = Math.abs(entityHitAngle - entityAttackingAngle);
         Vec3 betweenEntitiesVec = this.m_20182_().m_82546_(target.m_20182_());
         boolean targetComingCloser = target.m_20184_().m_82526_(betweenEntitiesVec) > 0.0 && target.m_20184_().m_82556_() > 0.015;
         if (this.getActiveAbility() == null
            && !this.m_21525_()
            && this.f_19796_.m_188503_(80) == 0
            && ((double)this.targetDistance > 5.5 || this.m_21023_((MobEffect)EffectHandler.SUNBLOCK.get()))
            && this.timeUntilUmvuthana <= 0
            && this.getEntitiesNearby(EntityUmvuthana.class, 50.0).size() < 4) {
            this.sendAbilityMessage(SPAWN_ABILITY);
            this.timeUntilUmvuthana = 200;
         } else if (this.getActiveAbility() == null
            && !this.m_21525_()
            && (double)this.getHealthRatio() <= 0.6
            && this.timeUntilLaser <= 0
            && (entityRelativeAngle < 60.0F || entityRelativeAngle > 300.0F)
            && this.m_21574_().m_148306_(target)
            && (double)this.targetDistance < 30.0) {
            this.sendAbilityMessage(SOLAR_BEAM_ABILITY);
            this.timeUntilLaser = 230;
         } else if (this.getActiveAbility() == null
            && !this.m_21525_()
            && (double)this.getHealthRatio() <= 0.6
            && !this.m_21023_((MobEffect)EffectHandler.SUNBLOCK.get())
            && this.timeUntilSupernova <= 0
            && (double)this.targetDistance <= 10.5) {
            this.sendAbilityMessage(SUPERNOVA_ABILITY);
            this.timeUntilSupernova = 230;
         } else if (this.getActiveAbility() != null
            || this.m_21525_()
            || (!(this.targetDistance <= 6.0F) || !targetComingCloser) && !(this.targetDistance < 4.0F)) {
            if (this.getActiveAbility() == null && !this.m_21525_() && this.timeUntilSunstrike <= 0) {
               this.sendAbilityMessage(SUNSTRIKE_ABILITY);
               this.timeUntilSunstrike = this.getTimeUntilSunstrike();
            }
         } else {
            this.sendAbilityMessage(SOLAR_FLARE_ABILITY);
         }

         if (this.hurtByTargetAI != null && !this.hurtByTargetAI.m_8045_()) {
            this.hurtByTargetAI.m_8041_();
         }
      } else if (!this.f_19853_.f_46443_) {
         this.setAngry(false);
      }

      if (this.f_19797_ % 20 == 0) {
         this.blocksByFeet = this.checkBlocksByFeet();
      }

      if (this.blocksByFeet) {
         this.legsUp.increaseTimer();
      } else {
         this.legsUp.decreaseTimer();
      }

      if (this.getAngry()) {
         this.angryEyebrow.increaseTimer();
      } else {
         this.angryEyebrow.decreaseTimer();
      }

      if (this.getActiveAbility() == null && !this.m_21525_() && this.m_5448_() == null && this.f_19796_.m_188503_(200) == 0) {
         this.sendAbilityMessage(BELLY_ABILITY);
      }

      if (this.getActiveAbility() == null && !this.m_21525_() && this.m_5448_() == null && this.timeUntilRoar <= 0 && this.f_19796_.m_188503_(300) == 0) {
         this.sendAbilityMessage(ROAR_ABILITY);
         this.timeUntilRoar = 300;
      }

      if (this.getActiveAbilityType() == SOLAR_FLARE_ABILITY) {
         this.f_20885_ = this.m_146908_();
         if (this.getActiveAbility().getTicksInUse() == 10) {
            if (this.f_19853_.f_46443_) {
               this.spawnExplosionParticles(30);
            }

            this.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_ATTACK.get(), 1.7F, 0.9F);
         }

         if (this.getActiveAbility().getTicksInUse() <= 6 && this.f_19853_.f_46443_) {
            int particleCount = 8;

            while (--particleCount != 0) {
               double radius = 2.0;
               double yaw = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
               double pitch = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
               double ox = radius * Math.sin(yaw) * Math.sin(pitch);
               double oy = radius * Math.cos(pitch);
               double oz = radius * Math.cos(yaw) * Math.sin(pitch);
               float offsetX = (float)(-0.3 * Math.sin((double)this.m_146908_() * Math.PI / 180.0));
               float offsetZ = (float)(-0.3 * Math.cos((double)this.m_146908_() * Math.PI / 180.0));
               float offsetY = 1.0F;
               this.f_19853_
                  .m_7106_(
                     new ParticleOrb.OrbData((float)this.m_20185_() + offsetX, (float)this.m_20186_() + offsetY, (float)this.m_20189_() + offsetZ, 6.0F),
                     this.m_20185_() + ox + (double)offsetX,
                     this.m_20186_() + (double)offsetY + oy,
                     this.m_20189_() + oz + (double)offsetZ,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }
      }

      if (this.getActiveAbilityType() == BLESS_ABILITY) {
         this.f_20885_ = this.m_146908_();
         if (this.getActiveAbility().getTicksInUse() == 1) {
            this.blessingPlayer = this.getCustomer();
         }

         if (this.f_19853_.f_46443_ && this.blessingPlayer != null) {
            this.blessingPlayerPos[0] = this.blessingPlayer.m_20182_().m_82549_(new Vec3(0.0, (double)(this.blessingPlayer.m_20206_() / 2.0F), 0.0));
            if (this.getActiveAbility().getTicksInUse() > 5 && this.getActiveAbility().getTicksInUse() < 40) {
               int particleCount = 2;

               while (--particleCount != 0) {
                  double radius = 0.7F;
                  double yaw = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
                  double pitch = (double)(this.f_19796_.m_188501_() * 2.0F) * Math.PI;
                  double ox = radius * Math.sin(yaw) * Math.sin(pitch);
                  double oy = radius * Math.cos(pitch);
                  double oz = radius * Math.cos(yaw) * Math.sin(pitch);
                  AdvancedParticleBase.spawnParticle(
                     this.f_19853_,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.ORB2.get(),
                     this.m_20185_() + ox,
                     this.m_20186_() + 0.8F + oy,
                     this.m_20189_() + oz,
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     20.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.Attractor(this.blessingPlayerPos, 0.5F, 0.2F, ParticleComponent.Attractor.EnumAttractorBehavior.LINEAR),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_X, new ParticleComponent.Oscillator(0.0F, (float)ox, 6.0F, 2.5F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, new ParticleComponent.Oscillator(0.0F, (float)oy, 6.0F, 2.5F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Z, new ParticleComponent.Oscillator(0.0F, (float)oz, 6.0F, 2.5F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 1.0F}, new float[]{0.0F, 0.8F}),
                           false
                        )
                     }
                  );
               }
            }

            if (this.getActiveAbility().getTicksInUse() % 15 == 0) {
               AdvancedParticleBase.spawnParticle(
                  this.f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
                  this.m_20185_(),
                  this.m_20186_() + 0.8F,
                  this.m_20189_(),
                  0.0,
                  0.0,
                  0.0,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  3.5,
                  1.0,
                  0.8745098F,
                  0.25882354F,
                  1.0,
                  1.0,
                  15.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(5.0F, 35.0F), false
                     )
                  }
               );
            }
         }
      }

      if (this.f_19797_ % 40 == 0) {
         for (Player player : this.getPlayersNearby(15.0, 15.0, 15.0, 15.0)) {
            ItemStack headArmorStack = (ItemStack)player.m_150109_().f_35975_.get(3);
            if (this.m_5448_() != player
               && this.m_21040_(player, GIVE_ACHIEVEMENT_PRED)
               && headArmorStack.m_41720_() instanceof UmvuthanaMask
               && player instanceof ServerPlayer) {
               AdvancementHandler.SNEAK_VILLAGE_TRIGGER.trigger((ServerPlayer)player);
            }
         }
      }

      if (!this.f_19853_.f_46443_
         && this.m_5448_() == null
         && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY
         && this.getActiveAbilityType() != SUPERNOVA_ABILITY) {
         this.timeUntilHeal--;
         if ((Boolean)ConfigHandler.COMMON.MOBS.UMVUTHI.healsOutOfBattle.get() && this.timeUntilHeal <= 0) {
            this.m_5634_(0.3F);
         }

         if (this.m_21223_() == this.m_21233_()) {
            this.setHealthLost(0.0F);
         }
      } else {
         this.timeUntilHeal = 75;
      }

      if (this.timeUntilSunstrike > 0) {
         this.timeUntilSunstrike--;
      }

      if (this.timeUntilLaser > 0 && this.getActiveAbilityType() != SUPERNOVA_ABILITY) {
         this.timeUntilLaser--;
      }

      if (this.timeUntilUmvuthana > 0) {
         this.timeUntilUmvuthana--;
      }

      if (this.timeUntilSupernova > 0 && this.getActiveAbilityType() != SOLAR_BEAM_ABILITY) {
         this.timeUntilSupernova--;
      }

      if (this.timeUntilRoar > 0) {
         this.timeUntilRoar--;
      }
   }

   @Override
   public AbilityType getHurtAbility() {
      return HURT_ABILITY;
   }

   @Override
   public AbilityType getDeathAbility() {
      return DIE_ABILITY;
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      if (source == DamageSource.f_19309_) {
         return false;
      } else if (this.m_21023_((MobEffect)EffectHandler.SUNBLOCK.get()) && !source.m_19378_()) {
         if (source.m_7640_() != null) {
            this.m_5496_((SoundEvent)MMSounds.ENTITY_WROUGHT_UNDAMAGED.get(), 0.4F, 2.0F);
         }

         return false;
      } else {
         this.timeUntilHeal = 75;
         float prevHealth = this.m_21223_();
         boolean superResult = super.m_6469_(source, damage);
         if (superResult) {
            float diffHealth = prevHealth - this.m_21223_();
            this.setHealthLost(this.getHealthLost() + diffHealth);
         }

         return superResult;
      }
   }

   private boolean checkBlocksByFeet() {
      BlockState blockLeft;
      BlockState blockRight;
      if (this.direction == 1) {
         BlockPos posLeft = new BlockPos(Mth.m_14107_(this.m_20185_()) + 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) + 1);
         BlockPos posRight = new BlockPos(Mth.m_14107_(this.m_20185_()) - 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) + 1);
         blockLeft = this.f_19853_.m_8055_(posLeft);
         blockRight = this.f_19853_.m_8055_(posRight);
      } else if (this.direction == 2) {
         BlockPos posLeft = new BlockPos(Mth.m_14107_(this.m_20185_()) - 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) + 1);
         BlockPos posRight = new BlockPos(Mth.m_14107_(this.m_20185_()) - 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) - 1);
         blockLeft = this.f_19853_.m_8055_(posLeft);
         blockRight = this.f_19853_.m_8055_(posRight);
      } else if (this.direction == 3) {
         BlockPos posLeft = new BlockPos(Mth.m_14107_(this.m_20185_()) - 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) - 1);
         BlockPos posRight = new BlockPos(Mth.m_14107_(this.m_20185_()) + 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) - 1);
         blockLeft = this.f_19853_.m_8055_(posLeft);
         blockRight = this.f_19853_.m_8055_(posRight);
      } else {
         if (this.direction != 4) {
            return false;
         }

         BlockPos posLeft = new BlockPos(Mth.m_14107_(this.m_20185_()) + 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) - 1);
         BlockPos posRight = new BlockPos(Mth.m_14107_(this.m_20185_()) + 1, Math.round((float)(this.m_20186_() - 1.0)), Mth.m_14107_(this.m_20189_()) + 1);
         blockLeft = this.f_19853_.m_8055_(posLeft);
         blockRight = this.f_19853_.m_8055_(posRight);
      }

      return blockLeft.m_60767_().m_76334_() || blockRight.m_60767_().m_76334_();
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.25F;
         float yaw = (float)i * ((float) (Math.PI * 2) / (float)amount);
         float vy = this.f_19796_.m_188501_() * 0.1F - 0.05F;
         float vx = 0.25F * Mth.m_14089_(yaw);
         float vz = 0.25F * Mth.m_14031_(yaw);
         this.f_19853_.m_7106_(ParticleTypes.f_123744_, this.m_20185_(), this.m_20186_() + 1.0, this.m_20189_(), (double)vx, (double)vy, (double)vz);
      }
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(DIRECTION, 0);
      this.m_20088_().m_135372_(DIALOGUE, 0);
      this.m_20088_().m_135372_(ANGRY, false);
      Item tradeItem = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation((String)ConfigHandler.COMMON.MOBS.UMVUTHI.whichItem.get()));
      this.m_20088_().m_135372_(DESIRES, new ItemStack(tradeItem, (Integer)ConfigHandler.COMMON.MOBS.UMVUTHI.howMany.get()));
      this.m_20088_().m_135372_(TRADED_PLAYERS, new CompoundTag());
      this.m_20088_().m_135372_(HEALTH_LOST, 0.0F);
      this.m_20088_().m_135372_(MISBEHAVED_PLAYER, Optional.empty());
      this.m_20088_().m_135372_(IS_TRADING, false);
   }

   public int getDirectionData() {
      return (Integer)this.m_20088_().m_135370_(DIRECTION);
   }

   public void setDirection(int direction) {
      this.m_20088_().m_135381_(DIRECTION, direction);
   }

   public int getWhichDialogue() {
      return (Integer)this.m_20088_().m_135370_(DIALOGUE);
   }

   public void setWhichDialogue(int dialogue) {
      this.m_20088_().m_135381_(DIALOGUE, dialogue);
   }

   public boolean getAngry() {
      return (Boolean)this.m_20088_().m_135370_(ANGRY);
   }

   public void setAngry(boolean angry) {
      this.m_20088_().m_135381_(ANGRY, angry);
   }

   public void setDesires(ItemStack stack) {
      this.m_20088_().m_135381_(DESIRES, stack);
   }

   public ItemStack getDesires() {
      return (ItemStack)this.m_20088_().m_135370_(DESIRES);
   }

   public void setTradedPlayersCompound(ListTag players) {
      CompoundTag compound = new CompoundTag();
      compound.m_128365_("players", players);
      this.m_20088_().m_135381_(TRADED_PLAYERS, compound);
   }

   public Set<UUID> getTradedPlayers() {
      Set<UUID> tradedPlayers = new HashSet<>();
      CompoundTag compound = (CompoundTag)this.m_20088_().m_135370_(TRADED_PLAYERS);

      for (Tag player : compound.m_128437_("players", 11)) {
         tradedPlayers.add(NbtUtils.m_129233_(player));
      }

      return tradedPlayers;
   }

   public float getHealthLost() {
      return (Float)this.m_20088_().m_135370_(HEALTH_LOST);
   }

   public void setHealthLost(float amount) {
      this.m_20088_().m_135381_(HEALTH_LOST, amount);
   }

   public boolean doesItemSatisfyDesire(ItemStack stack) {
      return canPayFor(stack, this.getDesires());
   }

   public boolean fulfillDesire(Slot input) {
      ItemStack desires = this.getDesires();
      if (canPayFor(input.m_7993_(), desires)) {
         input.m_6201_(desires.m_41613_());
         return true;
      } else {
         return false;
      }
   }

   public boolean hasTradedWith(Player player) {
      return this.getTradedPlayers().contains(UUIDUtil.m_235875_(player.m_36316_()));
   }

   public void rememberTrade(Player player) {
      UUID uuid = UUIDUtil.m_235875_(player.m_36316_());
      CompoundTag compound = (CompoundTag)this.m_20088_().m_135370_(TRADED_PLAYERS);
      ListTag players = compound.m_128437_("players", 11);
      players.add(NbtUtils.m_129226_(uuid));
      compound.m_128365_("players", players);
      this.m_20088_().m_135381_(TRADED_PLAYERS, compound);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("direction", this.getDirectionData());
      CompoundTag compoundTradedPlayers = (CompoundTag)this.m_20088_().m_135370_(TRADED_PLAYERS);
      ListTag players = compoundTradedPlayers.m_128437_("players", 11);
      compound.m_128365_("players", players);
      compound.m_128405_("HomePosX", this.m_21534_().m_123341_());
      compound.m_128405_("HomePosY", this.m_21534_().m_123342_());
      compound.m_128405_("HomePosZ", this.m_21534_().m_123343_());
      compound.m_128350_("healthLost", this.getHealthLost());
      if (this.getMisbehavedPlayerId() != null) {
         compound.m_128362_("MisbehavedPlayer", this.getMisbehavedPlayerId());
      }
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setDirection(compound.m_128451_("direction"));
      ListTag players = compound.m_128437_("players", 11);
      this.setTradedPlayersCompound(players);
      int i = compound.m_128451_("HomePosX");
      int j = compound.m_128451_("HomePosY");
      int k = compound.m_128451_("HomePosZ");
      this.m_21446_(new BlockPos(i, j, k), -1);
      this.setHealthLost((float)compound.m_128451_("healthLost"));
      UUID uuid;
      if (compound.m_128403_("MisbehavedPlayer")) {
         uuid = compound.m_128342_("MisbehavedPlayer");
      } else {
         String s = compound.m_128461_("MisbehavedPlayer");
         uuid = OldUsersConverter.m_11083_(this.m_20194_(), s);
      }

      if (uuid != null) {
         try {
            this.setMisbehavedPlayerId(uuid);
         } catch (Throwable var8) {
         }
      }
   }

   @Nullable
   public UUID getMisbehavedPlayerId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(MISBEHAVED_PLAYER)).orElse((UUID)null);
   }

   public void setMisbehavedPlayerId(@Nullable UUID p_184754_1_) {
      this.f_19804_.m_135381_(MISBEHAVED_PLAYER, Optional.ofNullable(p_184754_1_));
   }

   @Nullable
   public LivingEntity getMisbehavedPlayer() {
      try {
         UUID uuid = this.getMisbehavedPlayerId();
         return uuid == null ? null : this.f_19853_.m_46003_(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   protected void m_7355_(BlockPos pos, BlockState blockState) {
   }

   private int getTimeUntilSunstrike() {
      float damageRatio = 1.0F - this.getHealthRatio();
      if ((double)damageRatio > 0.6) {
         damageRatio = 0.6F;
      }

      return (int)(50.0F - damageRatio / 0.6F * 20.0F);
   }

   @Override
   public AbilityType<?, ?>[] getAbilities() {
      return new AbilityType[]{
         DIE_ABILITY,
         HURT_ABILITY,
         BELLY_ABILITY,
         TALK_ABILITY,
         SUNSTRIKE_ABILITY,
         SOLAR_FLARE_ABILITY,
         SPAWN_ABILITY,
         SPAWN_SUNBLOCKERS_ABILITY,
         SOLAR_BEAM_ABILITY,
         BLESS_ABILITY,
         SUPERNOVA_ABILITY,
         ROAR_ABILITY
      };
   }

   @Override
   public void m_6667_(DamageSource cause) {
      super.m_6667_(cause);

      for (EntityUmvuthana entityUmvuthana : this.getEntitiesNearby(EntityUmvuthana.class, 30.0, 20.0, 30.0, 30.0)) {
         if (entityUmvuthana.isUmvuthiDevoted()) {
            if (entityUmvuthana instanceof EntityUmvuthanaCrane) {
               ((EntityUmvuthanaCrane)entityUmvuthana).hasTriedOrSucceededTeleport = true;
            }

            entityUmvuthana.timeUntilDeath = this.f_19796_.m_188503_(20);
         }
      }

      super.m_6667_(cause);
   }

   public void setTrading(boolean trading) {
      this.f_19804_.m_135381_(IS_TRADING, trading);
   }

   public boolean isTrading() {
      return (Boolean)this.f_19804_.m_135370_(IS_TRADING);
   }

   public Player getCustomer() {
      return this.customer;
   }

   public void setCustomer(Player customer) {
      this.setTrading(customer != null);
      this.customer = customer;
   }

   public void openGUI(Player playerEntity) {
      this.setCustomer(playerEntity);
      MowziesMobs.PROXY.setReferencedMob(this);
      if (!this.f_19853_.f_46443_ && this.m_5448_() == null && this.m_6084_()) {
         playerEntity.m_5893_(new MenuProvider() {
            public AbstractContainerMenu m_7208_(int id, Inventory playerInventory, Player player) {
               return new ContainerUmvuthiTrade(id, EntityUmvuthi.this, playerInventory);
            }

            public Component m_5446_() {
               return EntityUmvuthi.this.m_5446_();
            }
         });
      }
   }

   protected InteractionResult m_6071_(Player player, InteractionHand hand) {
      if (this.canTradeWith(player) && this.m_5448_() == null && this.m_6084_()) {
         this.openGUI(player);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public boolean canTradeWith(Player player) {
      if (!this.isTrading() && !(this.m_21223_() <= 0.0F)) {
         ItemStack headStack = (ItemStack)player.m_150109_().f_35975_.get(3);
         return headStack.m_41720_() instanceof UmvuthanaMask;
      } else {
         return false;
      }
   }

   private static boolean canPayFor(ItemStack stack, ItemStack worth) {
      return stack.m_41720_() == worth.m_41720_() && stack.m_41613_() >= worth.m_41613_();
   }

   @Override
   public boolean hasBossBar() {
      return (Boolean)ConfigHandler.COMMON.MOBS.UMVUTHI.hasBossBar.get();
   }

   @Override
   protected BossBarColor bossBarColor() {
      return BossBarColor.YELLOW;
   }

   protected ResourceLocation m_7582_() {
      return LootTableHandler.UMVUTHI;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig;
   }

   @Override
   public SpawnGroupData m_6518_(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData livingData, CompoundTag compound) {
      if (reason == MobSpawnType.SPAWN_EGG) {
         List<Player> players = this.getPlayersNearby(5.0, 5.0, 5.0, 5.0);
         if (!players.isEmpty()) {
            Player closestPlayer = players.get(0);
            float closestPlayerDist = 6.0F;

            for (Player player : players) {
               if (player.m_21205_().m_41720_() == ItemHandler.UMVUTHI_SPAWN_EGG || player.m_21205_().m_41720_() == ItemHandler.UMVUTHI_SPAWN_EGG) {
                  float thisDist = this.m_20270_(player);
                  if (thisDist < closestPlayerDist) {
                     closestPlayer = player;
                     closestPlayerDist = thisDist;
                  }
               }
            }

            float angle = (float)this.getAngleBetweenEntities(this, closestPlayer) + 225.0F;
            int direction = (int)(angle / 90.0F) % 4 + 1;
            this.setDirection(direction);
         }
      }

      if (reason != MobSpawnType.STRUCTURE) {
         this.m_21446_(this.m_20183_(), -1);
      }

      return super.m_6518_(world, difficulty, reason, livingData, compound);
   }

   public boolean m_142535_(float p_147187_, float p_147188_, DamageSource p_147189_) {
      return false;
   }

   public boolean m_6063_() {
      return false;
   }

   public void m_5997_(double x, double y, double z) {
      super.m_5997_(0.0, y, 0.0);
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)MMSounds.MUSIC_UMVUTHI_THEME.get();
   }

   @Override
   public boolean resetHealthOnPlayerRespawn() {
      return (Boolean)ConfigHandler.COMMON.MOBS.UMVUTHI.resetHealthWhenRespawn.get();
   }

   public static class SolarBeamAbility extends Ability<EntityUmvuthi> {
      protected LivingEntity entityTarget;
      private EntitySolarBeam solarBeam;

      public SolarBeamAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 22),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 68),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 10)
            }
         );
      }

      @Override
      public void start() {
         super.start();
         this.entityTarget = this.getUser().m_5448_();
         this.playAnimation("solar_beam", false);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         float radius1 = 0.8F;
         EntityUmvuthi entity = this.getUser();
         if (this.getTicksInUse() == 4 && !entity.f_19853_.f_46443_) {
            this.solarBeam = new EntitySolarBeam(
               (EntityType<? extends EntitySolarBeam>)EntityHandler.SOLAR_BEAM.get(),
               this.getUser().f_19853_,
               entity,
               entity.m_20185_() + (double)radius1 * Math.sin((double)(-entity.m_146908_()) * Math.PI / 180.0),
               entity.m_20186_() + 1.4,
               entity.m_20189_() + (double)radius1 * Math.cos((double)(-entity.m_146908_()) * Math.PI / 180.0),
               (float)((double)(entity.f_20885_ + 90.0F) * Math.PI / 180.0),
               (float)((double)(-entity.m_146909_()) * Math.PI / 180.0),
               55
            );
            entity.f_19853_.m_7967_(this.solarBeam);
         }

         if (this.getTicksInUse() >= 22 && this.entityTarget != null) {
            entity.m_21563_()
               .m_24950_(
                  this.entityTarget.m_20185_(),
                  this.entityTarget.m_20186_() + (double)(this.entityTarget.m_20206_() / 2.0F),
                  this.entityTarget.m_20189_(),
                  2.0F,
                  90.0F
               );
         }
      }
   }

   public static class SolarFlareAbility extends Ability<EntityUmvuthi> {
      public static AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 12),
         new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 18)
      };

      public SolarFlareAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(abilityType, user, SECTION_TRACK);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_BURST.get(), 1.7F, 1.5F);
         this.playAnimation("flare", false);
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            EntityUmvuthi entity = this.getUser();
            float radius = 4.0F;

            for (LivingEntity aHit : entity.getEntityLivingBaseNearby((double)radius, (double)(2.0F * radius), (double)radius, (double)radius)) {
               if (!(aHit instanceof LeaderSunstrikeImmune)) {
                  entity.doHurtTarget(aHit, 1.0F, 3.0F);
                  if (!aHit.m_20147_() && (!(aHit instanceof Player) || !((Player)aHit).m_150110_().f_35934_)) {
                     double knockback = 3.0;
                     double angle = entity.getAngleBetweenEntities(entity, aHit);
                     double x = knockback * Math.cos(Math.toRadians(angle - 90.0));
                     double z = knockback * Math.sin(Math.toRadians(angle - 90.0));
                     aHit.m_20334_(x, 0.3, z);
                     if (aHit instanceof ServerPlayer) {
                        ((ServerPlayer)aHit).f_8906_.m_9829_(new ClientboundSetEntityMotionPacket(aHit));
                     }
                  }
               }
            }
         }
      }
   }

   public static class SpawnFollowersAbility extends Ability<EntityUmvuthi> {
      private boolean spawnSunblockers;

      public SpawnFollowersAbility(AbilityType abilityType, EntityUmvuthi user, boolean spawnSunblockers) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 11)
            }
         );
         this.spawnSunblockers = spawnSunblockers;
      }

      @Override
      public void start() {
         super.start();
         this.getUser().umvuthanaSpawnCount++;
         this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_INHALE.get(), 1.2F, 0.5F);
         this.playAnimation("spawn_strix", false);
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         EntityUmvuthi entity = this.getUser();
         if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_INHALE.get(), 1.2F, 0.5F);
            this.playAnimation("spawn_strix", false);
         }

         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getUser().f_19853_.m_5776_()) {
            entity.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_BELLY.get(), 1.5F, 1.0F);
            entity.m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHANA_BLOWDART.get(), 1.5F, 0.5F);
            double angle = (double)entity.f_20885_;
            if (angle < 0.0) {
               angle += 360.0;
            }

            if (angle - (double)entity.m_146908_() > 70.0) {
               angle = (double)(70.0F + entity.m_146908_());
            } else if (angle - (double)entity.m_146908_() < -70.0) {
               angle = (double)(-70.0F + entity.m_146908_());
            }

            EntityUmvuthanaMinion umvuthana;
            if (this.spawnSunblockers) {
               umvuthana = new EntityUmvuthanaCrane((EntityType<? extends EntityUmvuthanaMinion>)EntityHandler.UMVUTHANA_CRANE.get(), entity.f_19853_);
               ((EntityUmvuthanaCrane)umvuthana).hasTriedOrSucceededTeleport = false;
            } else {
               umvuthana = new EntityUmvuthanaMinion((EntityType<? extends EntityUmvuthanaMinion>)EntityHandler.UMVUTHANA_MINION.get(), entity.f_19853_);
            }

            umvuthana.m_19890_(
               entity.m_20185_() + 2.0 * Math.sin(-angle * (Math.PI / 180.0)),
               entity.m_20186_() + 2.5,
               entity.m_20189_() + 2.0 * Math.cos(-angle * (Math.PI / 180.0)),
               entity.f_20885_,
               0.0F
            );
            umvuthana.setActive(false);
            umvuthana.active = false;
            umvuthana.m_6518_((ServerLevelAccessor)entity.m_20193_(), entity.f_19853_.m_6436_(umvuthana.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            umvuthana.m_21446_(entity.m_21534_(), 25);
            if (entity.m_5647_() instanceof PlayerTeam) {
               umvuthana.f_19853_.m_6188_().m_6546_(umvuthana.m_6302_(), (PlayerTeam)entity.m_5647_());
            }

            entity.f_19853_.m_7967_(umvuthana);
            umvuthana.m_20334_(0.7 * Math.sin(-angle * (Math.PI / 180.0)), 0.5, 0.7 * Math.cos(-angle * (Math.PI / 180.0)));
            if (!this.spawnSunblockers) {
               umvuthana.m_6710_(entity.m_5448_());
               if (entity.m_5448_() instanceof Player) {
                  umvuthana.setMisbehavedPlayerId(entity.m_5448_().m_20148_());
               }
            }
         }
      }

      @Override
      protected void endSection(AbilitySection section) {
         super.endSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY
            && this.getUser().targetDistance <= 6.0F
            && this.getUser().m_5448_() != null
            && !this.spawnSunblockers) {
            this.interrupt();
         }
      }
   }

   public static class SunstrikeAbility extends Ability<EntityUmvuthi> {
      private static int STARTUP_DURATION = 9;
      protected LivingEntity entityTarget;
      public double prevX;
      public double prevZ;
      private int newX;
      private int newZ;
      private int y;

      public SunstrikeAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(
            abilityType,
            user,
            new AbilitySection[]{
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, STARTUP_DURATION),
               new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
               new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 12)
            }
         );
      }

      @Override
      public void start() {
         super.start();
         this.entityTarget = this.getUser().m_5448_();
         if (this.entityTarget != null) {
            this.prevX = this.entityTarget.m_20185_();
            this.prevZ = this.entityTarget.m_20189_();
         }

         this.playAnimation("sun_strike", false);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (!this.getUser().f_19853_.m_5776_()) {
            if (this.entityTarget == null) {
               return;
            }

            if (this.getTicksInUse() == STARTUP_DURATION - 2) {
               double x = this.entityTarget.m_20185_();
               this.y = Mth.m_14107_(this.entityTarget.m_20186_() - 1.0);
               double z = this.entityTarget.m_20189_();
               double vx = (x - this.prevX) / (double)STARTUP_DURATION;
               double vz = (z - this.prevZ) / (double)STARTUP_DURATION;
               int t = 38;
               this.newX = Mth.m_14107_(x + vx * (double)t);
               this.newZ = Mth.m_14107_(z + vz * (double)t);
               double dx = (double)this.newX - this.getUser().m_20185_();
               double dz = (double)this.newZ - this.getUser().m_20189_();
               double dist2ToUmvuthi = dx * dx + dz * dz;
               if (dist2ToUmvuthi < 3.0) {
                  this.newX = Mth.m_14107_(this.entityTarget.m_20185_());
                  this.newZ = Mth.m_14107_(this.entityTarget.m_20189_());
               }

               for (int i = 0; i < 5 && !this.getUser().f_19853_.m_46861_(new BlockPos(this.newX, this.y, this.newZ)); i++) {
                  this.y++;
               }
            }

            if (this.getTicksInUse() < STARTUP_DURATION - 2) {
               this.getUser().m_21563_().m_24960_(this.entityTarget, 30.0F, 30.0F);
            }

            if (this.getTicksInUse() >= STARTUP_DURATION - 2) {
               this.getUser().m_21563_().m_24950_((double)this.newX, (double)((float)this.y + this.entityTarget.m_20192_()), (double)this.newZ, 50.0F, 50.0F);
            }
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (!this.getUser().f_19853_.m_5776_() && section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_ATTACK.get(), 1.4F, 1.0F);
            EntitySunstrike sunstrike = new EntitySunstrike(
               (EntityType<? extends EntitySunstrike>)EntityHandler.SUNSTRIKE.get(), this.getUser().f_19853_, this.getUser(), this.newX, this.y, this.newZ
            );
            sunstrike.onSummon();
            this.getUser().f_19853_.m_7967_(sunstrike);
         }
      }
   }

   public static class SupernovaAbility extends Ability<EntityUmvuthi> {
      public static AbilitySection[] SECTION_TRACK = new AbilitySection[]{
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 44),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 40),
         new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 16)
      };
      private static final ParticleComponent.KeyTrack superNovaKeyTrack1 = new ParticleComponent.KeyTrack(
         new float[]{0.0F, 25.0F, 32.0F, 0.0F}, new float[]{0.0F, 0.6F, 0.85F, 1.0F}
      );
      private static final ParticleComponent.KeyTrack superNovaKeyTrack2 = ParticleComponent.KeyTrack.oscillate(0.0F, 7.0F, 24);

      public SupernovaAbility(AbilityType abilityType, EntityUmvuthi user) {
         super(abilityType, user, SECTION_TRACK);
      }

      @Override
      public void start() {
         super.start();
         this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_SUPERNOVA_START.get(), 3.0F, 1.0F);
         this.playAnimation("supernova", false);
      }

      @Override
      public void tickUsing() {
         super.tickUsing();
         if (this.getTicksInUse() == 30) {
            this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_SUPERNOVA_BLACKHOLE.get(), 2.0F, 1.2F);
         }

         if (this.getTicksInUse() < 30) {
            for (LivingEntity inRange : this.getUser().getEntityLivingBaseNearby(16.0, 16.0, 16.0, 16.0)) {
               if (!(inRange instanceof LeaderSunstrikeImmune) && (!(inRange instanceof Player) || !((Player)inRange).m_150110_().f_35934_)) {
                  Vec3 diff = inRange.m_20182_().m_82546_(this.getUser().m_20182_().m_82520_(0.0, 3.0, 0.0));
                  diff = diff.m_82541_().m_82490_(0.03);
                  inRange.m_20256_(inRange.m_20184_().m_82546_(diff));
                  if (inRange.m_20186_() < this.getUser().m_20186_() + 3.0) {
                     inRange.m_20256_(inRange.m_20184_().m_82520_(0.0, 0.075, 0.0));
                  }
               }
            }
         }

         if (this.getTicksInUse() == 40) {
            this.getUser().m_5496_((SoundEvent)MMSounds.ENTITY_UMVUTHI_ROAR.get(), 3.0F, 1.0F);
         }

         if (this.getLevel().f_46443_) {
            superNovaEffects(this, this.getUser().betweenHandPos, this.getLevel());
         }
      }

      @Override
      protected void beginSection(AbilitySection section) {
         super.beginSection(section);
         if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE && !this.getUser().f_19853_.f_46443_) {
            Vec3 offset = new Vec3(1.1F, 0.0, 0.0);
            offset = offset.m_82524_((float)Math.toRadians((double)(-this.getUser().m_146908_() - 90.0F)));
            EntitySuperNova superNova = new EntitySuperNova(
               (EntityType<? extends EntitySuperNova>)EntityHandler.SUPER_NOVA.get(),
               this.getUser().f_19853_,
               this.getUser(),
               this.getUser().m_20185_() + offset.f_82479_,
               this.getUser().m_20186_() + 0.05,
               this.getUser().m_20189_() + offset.f_82481_
            );
            this.getUser().f_19853_.m_7967_(superNova);
         }
      }

      @OnlyIn(Dist.CLIENT)
      public static void superNovaEffects(Ability activeAbility, Vec3[] pinLocation, Level level) {
         Player clientPlayer = Minecraft.m_91087_().f_91074_;
         if (clientPlayer != null) {
            double distToCaster = activeAbility.getUser().m_20182_().m_82557_(clientPlayer.m_20182_());
            if (distToCaster < 1000.0) {
               Minecraft.m_91087_().f_91063_.f_109068_ += 0.06F;
               if (Minecraft.m_91087_().f_91063_.f_109068_ > 1.0F) {
                  Minecraft.m_91087_().f_91063_.f_109068_ = 1.0F;
               }
            }

            if (pinLocation != null && pinLocation.length != 0 && pinLocation[0] != null) {
               int ticksInUse = activeAbility.getTicksInUse();
               LivingEntity user = activeAbility.getUser();
               RandomSource random = user.m_217043_();
               if (ticksInUse == 1) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.SUN.get(),
                     user.m_20185_(),
                     user.m_20186_(),
                     user.m_20189_(),
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
                     33.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, superNovaKeyTrack1, false),
                        new ParticleComponent.PropertyControl(ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, superNovaKeyTrack2, true)
                     }
                  );
               }

               if (ticksInUse == 33) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.SUN_NOVA.get(),
                     user.m_20185_(),
                     user.m_20186_(),
                     user.m_20189_(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     20.0,
                     1.0,
                     1.0,
                     1.0,
                     0.0,
                     1.0,
                     13.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{11.0F, 7.0F, 5.5F, 1.0F, 30.0F}, new float[]{0.0F, 0.15F, 0.8F, 0.89F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 1.0F, 1.0F, 0.0F}, new float[]{0.0F, 0.15F, 0.89F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.PARTICLE_ANGLE, ParticleComponent.KeyTrack.startAndEnd(0.0F, -6.0F), false
                        )
                     }
                  );
               }

               if (ticksInUse == 32) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.FLARE.get(),
                     user.m_20185_(),
                     user.m_20186_(),
                     user.m_20189_(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     0.7,
                     1.0,
                     3.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, ParticleComponent.constant(-0.15F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 22.0F, 0.0F}, new float[]{0.0F, 0.2F, 1.0F}),
                           false
                        )
                     }
                  );
               }

               if (ticksInUse > 30 && ticksInUse < 41) {
                  for (int i = 0; i < 6; i++) {
                     float phaseOffset = random.m_188501_();
                     double value = random.m_188500_() * 0.3 + 0.05;
                     AdvancedParticleBase.spawnParticle(
                        level,
                        (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
                        pinLocation[0].f_82479_,
                        pinLocation[0].f_82480_,
                        pinLocation[0].f_82481_,
                        0.0,
                        0.0,
                        0.0,
                        true,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        5.0,
                        value,
                        value,
                        value,
                        1.0,
                        1.0,
                        6.0,
                        false,
                        true,
                        new ParticleComponent[]{
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                              new ParticleComponent.KeyTrack(new float[]{0.0F, 3.0F}, new float[]{0.0F, 0.2F}),
                              false
                           ),
                           new ParticleComponent.Orbit(
                              pinLocation,
                              ParticleComponent.KeyTrack.startAndEnd(0.0F + phaseOffset, -0.4F + phaseOffset),
                              ParticleComponent.KeyTrack.startAndEnd(0.5F + random.m_188501_(), 0.0F),
                              ParticleComponent.constant(0.0F),
                              ParticleComponent.constant(0.0F),
                              ParticleComponent.constant(0.0F),
                              true
                           )
                        }
                     );
                  }
               }

               if (ticksInUse > 1 && ticksInUse < 27) {
                  for (int i = 0; i < 6; i++) {
                     Vec3 particlePos = new Vec3((double)(random.m_188501_() * 5.0F), 0.0, 0.0);
                     particlePos = particlePos.m_82524_((float)((double)(random.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82496_((float)((double)(random.m_188501_() * 2.0F) * Math.PI));
                     particlePos = particlePos.m_82549_(pinLocation[0]);
                     double value = random.m_188500_() * 0.5 + 0.1;
                     AdvancedParticleBase.spawnParticle(
                        level,
                        (ParticleType<AdvancedParticleData>)ParticleHandler.PIXEL.get(),
                        particlePos.f_82479_,
                        particlePos.f_82480_,
                        particlePos.f_82481_,
                        0.0,
                        0.0,
                        0.0,
                        true,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        5.0,
                        value,
                        value,
                        value,
                        1.0,
                        1.0,
                        7.0,
                        false,
                        true,
                        new ParticleComponent[]{
                           new ParticleComponent.Attractor(pinLocation, 1.1F, 1.0F, ParticleComponent.Attractor.EnumAttractorBehavior.EXPONENTIAL),
                           new ParticleComponent.PropertyControl(
                              ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                              new ParticleComponent.KeyTrack(new float[]{0.0F, 3.5F}, new float[]{0.0F, 0.2F}),
                              false
                           )
                        }
                     );
                  }
               }

               float timeFrac = Math.min((float)ticksInUse / 20.0F, 1.0F);
               if (ticksInUse > 1 && ticksInUse < 25 && ticksInUse % (int)(4.0F * (1.0F - timeFrac) + 1.0F) == 0) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.RING_SPARKS.get(),
                     user.m_20185_(),
                     user.m_20186_(),
                     user.m_20189_(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     (double)(random.m_188501_() * (float) Math.PI * 2.0F),
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     (double)(6.0F + random.m_188501_() * 3.0F),
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           ParticleComponent.KeyTrack.startAndEnd(10.0F + 20.0F * timeFrac * timeFrac + 10.0F * random.m_188501_() * timeFrac, 0.0F),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.0F, 0.7F), false
                        )
                     }
                  );
               }

               if (ticksInUse == 14) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.FLARE.get(),
                     user.m_20185_(),
                     user.m_20186_(),
                     user.m_20189_(),
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     18.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.POS_Y, ParticleComponent.constant(-0.1F), true
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(new float[]{0.0F, 35.0F, 0.0F}, new float[]{0.0F, 0.8F, 1.0F}),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, new ParticleComponent.Oscillator(-5.0F, 5.0F, 42.0F, 0.0F), true
                        )
                     }
                  );
               }

               if (ticksInUse == 32) {
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.BURST_IN.get(),
                     user.m_20185_(),
                     user.m_20186_(),
                     user.m_20189_(),
                     0.0,
                     0.0,
                     0.0,
                     false,
                     0.0,
                     Math.PI / 2,
                     0.0,
                     0.0,
                     5.0,
                     0.0,
                     0.0,
                     0.0,
                     1.0,
                     1.0,
                     10.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PinLocation(pinLocation),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(25.0F, 0.0F), false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.0F, 1.0F), false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, new ParticleComponent.Oscillator(-2.0F, 2.0F, 42.0F, 0.0F), true
                        )
                     }
                  );
               }

               if (ticksInUse == 44) {
                  float scale = 85.0F;
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.RING_BIG.get(),
                     pinLocation[0].f_82479_,
                     pinLocation[0].f_82480_,
                     pinLocation[0].f_82481_,
                     0.0,
                     0.0,
                     0.0,
                     false,
                     0.0,
                     Math.PI / 2,
                     0.0,
                     0.0,
                     5.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     1.0,
                     40.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(
                              new float[]{0.0F * scale, 0.59F * scale, 0.87F * scale, 0.974F * scale, 0.998F * scale, scale},
                              new float[]{0.0F, 0.2F, 0.4F, 0.6F, 0.8F, 1.0F}
                           ),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                        )
                     }
                  );
                  scale = 120.0F;
                  AdvancedParticleBase.spawnParticle(
                     level,
                     (ParticleType<AdvancedParticleData>)ParticleHandler.GLOW.get(),
                     pinLocation[0].f_82479_,
                     pinLocation[0].f_82480_,
                     pinLocation[0].f_82481_,
                     0.0,
                     0.0,
                     0.0,
                     true,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     5.0,
                     0.95,
                     0.9,
                     0.35,
                     1.0,
                     1.0,
                     40.0,
                     true,
                     true,
                     new ParticleComponent[]{
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                           new ParticleComponent.KeyTrack(
                              new float[]{0.0F * scale, 0.59F * scale, 0.87F * scale, 0.974F * scale, 0.998F * scale, scale},
                              new float[]{0.0F, 0.2F, 0.4F, 0.6F, 0.8F, 1.0F}
                           ),
                           false
                        ),
                        new ParticleComponent.PropertyControl(
                           ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(1.0F, 0.0F), false
                        )
                     }
                  );
               }
            }
         }
      }
   }
}
