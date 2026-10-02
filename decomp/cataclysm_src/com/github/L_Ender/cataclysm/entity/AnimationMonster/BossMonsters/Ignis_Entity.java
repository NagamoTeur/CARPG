package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAniamtionGoal3;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal1;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAnimationGoal2;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackMoveGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.PredictiveChargeAttackAnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.world.data.CMWorldData;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;

public class Ignis_Entity extends LLibrary_Boss_Monster implements IHoldEntity {
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.m_5446_(), BossBarColor.YELLOW, false, 2);
   public static final Animation SWING_ATTACK = Animation.create(55);
   public static final Animation SWING_ATTACK_SOUL = Animation.create(46);
   public static final Animation SWING_ATTACK_BERSERK = Animation.create(37);
   public static final Animation HORIZONTAL_SWING_ATTACK = Animation.create(68);
   public static final Animation HORIZONTAL_SWING_ATTACK_SOUL = Animation.create(58);
   public static final Animation SHIELD_SMASH_ATTACK = Animation.create(70);
   public static final Animation PHASE_2 = Animation.create(68);
   public static final Animation POKE_ATTACK = Animation.create(65);
   public static final Animation POKE_ATTACK2 = Animation.create(56);
   public static final Animation POKE_ATTACK3 = Animation.create(50);
   public static final Animation POKED_ATTACK = Animation.create(65);
   public static final Animation PHASE_3 = Animation.create(120);
   public static final Animation MAGIC_ATTACK = Animation.create(69);
   public static final Animation SMASH_IN_AIR = Animation.create(105);
   public static final Animation SMASH = Animation.create(47);
   public static final Animation BODY_CHECK_ATTACK1 = Animation.create(62);
   public static final Animation BODY_CHECK_ATTACK2 = Animation.create(62);
   public static final Animation BODY_CHECK_ATTACK3 = Animation.create(62);
   public static final Animation BODY_CHECK_ATTACK4 = Animation.create(62);
   public static final Animation BODY_CHECK_ATTACK_SOUL1 = Animation.create(45);
   public static final Animation BODY_CHECK_ATTACK_SOUL2 = Animation.create(45);
   public static final Animation BODY_CHECK_ATTACK_SOUL3 = Animation.create(45);
   public static final Animation BODY_CHECK_ATTACK_SOUL4 = Animation.create(45);
   public static final Animation IGNIS_DEATH = Animation.create(124);
   public static final Animation COUNTER = Animation.create(61);
   public static final Animation STRIKE = Animation.create(62);
   public static final Animation COMBO1 = Animation.create(102);
   public static final Animation COMBO2 = Animation.create(131);
   public static final Animation BREAK_THE_SHIELD = Animation.create(87);
   public static final Animation SWING_UPPERCUT = Animation.create(65);
   public static final Animation SWING_UPPERSLASH = Animation.create(54);
   public static final Animation SPIN_ATTACK = Animation.create(38);
   public static final Animation EARTH_SHUDDERS_ATTACK = Animation.create(138);
   public static final Animation HORIZONTAL_SMALL_SWING_ATTACK = Animation.create(44);
   public static final Animation HORIZONTAL_SMALL_SWING_ALT_ATTACK2 = Animation.create(38);
   public static final Animation REINFORCED_SMASH_IN_AIR = Animation.create(162);
   public static final Animation REINFORCED_SMASH = Animation.create(115);
   public static final Animation REINFORCED_SMASH_IN_AIR_SOUL = Animation.create(162);
   public static final Animation REINFORCED_SMASH_SOUL = Animation.create(115);
   public static final Animation SHIELD_BREAK_COUNTER = Animation.create(53);
   public static final Animation SHIELD_BREAK_STRIKE = Animation.create(64);
   public static final Animation ULTIMATE_ATTACK = Animation.create(114);
   public static final int NATURE_HEAL_COOLDOWN = 200;
   public static final int AIR_SMASH_COOLDOWN = 240;
   public static final int BODY_CHECK_COOLDOWN = 200;
   public static final int POKE_COOLDOWN = 240;
   public static final int CONTER_STRIKE_COOLDOWN = 360;
   public static final int EARTH_SHUDDERS_COOLDOWN = 800;
   public static final int SWORD_DANCE_COOLDOWN = 600;
   public static final int HORIZONTAL_SMALL_SWING_COOLDOWN = 100;
   public static final int HORIZONTAL_SWING_COOLDOWN = 160;
   public static final int MAGIC_COOLDOWN = 300;
   public static final int REINFORCED_SMASH_COOLDOWN = 1800;
   public static final int ULTIMATE_COOLDOWN = 1200;
   private static final EntityDataAccessor<Boolean> IS_BLOCKING = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_SHIELD_BREAK = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> SHIELD_DURABILITY = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> IS_SHIELD = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> SHOW_SHIELD = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_SWORD = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> BOSS_PHASE = SynchedEntityData.m_135353_(Ignis_Entity.class, EntityDataSerializers.f_135028_);
   private Vec3 prevBladePos = new Vec3(0.0, 0.0, 0.0);
   private int air_smash_cooldown = 0;
   private int body_check_cooldown = 0;
   private int poke_cooldown = 0;
   private int counter_strike_cooldown = 0;
   private int horizontal_small_swing_cooldown = 0;
   private int horizontal_swing_cooldown = 0;
   private int magic_cooldown = 0;
   private int earth_shudders_cooldown = 0;
   private int sword_dance_cooldown = 0;
   private int reinforced_smash_cooldown = 1800;
   private int ultimate_cooldown = 0;
   private boolean Combo = false;
   private int CanSpin = 0;
   private int timeWithoutTarget;
   private int destroyBlocksTick;
   public float blockingProgress;
   public float swordProgress;
   public float prevblockingProgress;
   public float prevswordProgress;

   public Ignis_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 500;
      this.m_21441_(BlockPathTypes.UNPASSABLE_RAIL, 0.0F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.LAVA, 8.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0F);
      if (world.f_46443_) {
         this.socketPosArray = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      setConfigattribute(this, CMConfig.IgnisHealthMultiplier, CMConfig.IgnisDamageMultiplier);
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   public float getStepHeight() {
      return 2.5F;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         NO_ANIMATION,
         SWING_ATTACK,
         SWING_ATTACK_SOUL,
         SWING_ATTACK_BERSERK,
         SWING_UPPERCUT,
         SWING_UPPERSLASH,
         SPIN_ATTACK,
         HORIZONTAL_SWING_ATTACK,
         HORIZONTAL_SWING_ATTACK_SOUL,
         POKE_ATTACK,
         POKE_ATTACK2,
         POKE_ATTACK3,
         POKED_ATTACK,
         MAGIC_ATTACK,
         PHASE_3,
         SHIELD_SMASH_ATTACK,
         PHASE_2,
         BODY_CHECK_ATTACK4,
         BODY_CHECK_ATTACK3,
         BODY_CHECK_ATTACK2,
         BODY_CHECK_ATTACK1,
         BODY_CHECK_ATTACK_SOUL1,
         BODY_CHECK_ATTACK_SOUL2,
         BODY_CHECK_ATTACK_SOUL3,
         BODY_CHECK_ATTACK_SOUL4,
         SMASH,
         COUNTER,
         STRIKE,
         SMASH_IN_AIR,
         BREAK_THE_SHIELD,
         COMBO1,
         COMBO2,
         EARTH_SHUDDERS_ATTACK,
         HORIZONTAL_SMALL_SWING_ATTACK,
         HORIZONTAL_SMALL_SWING_ALT_ATTACK2,
         REINFORCED_SMASH_IN_AIR,
         REINFORCED_SMASH,
         REINFORCED_SMASH_IN_AIR_SOUL,
         REINFORCED_SMASH_SOUL,
         SHIELD_BREAK_COUNTER,
         SHIELD_BREAK_STRIKE,
         ULTIMATE_ATTACK,
         IGNIS_DEATH
      };
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new AttackMoveGoal(this, true, 1.0));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Hornzontal_SwingGoal(this, HORIZONTAL_SWING_ATTACK, 31, 51, 20, 36));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Hornzontal_SwingGoal(this, HORIZONTAL_SWING_ATTACK_SOUL, 27, 47, 16, 31));
      this.f_21345_.m_25352_(1, new Ignis_Entity.PokeGoal(this, POKE_ATTACK, 39, 59, 34, 41, 34, 40));
      this.f_21345_.m_25352_(1, new Ignis_Entity.PokeGoal(this, POKE_ATTACK2, 33, 53, 28, 35, 28, 34));
      this.f_21345_.m_25352_(1, new Ignis_Entity.PokeGoal(this, POKE_ATTACK3, 29, 49, 24, 31, 24, 30));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Combo1(this, COMBO1));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Combo2(this, COMBO2, 34, 12.0F, 27, 0.3F, 0.3F));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, PHASE_3, 34, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, SWING_UPPERSLASH, 23, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, BREAK_THE_SHIELD, 35, false));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, COMBO1, 10, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, MAGIC_ATTACK, 49, true));
      this.f_21345_
         .m_25352_(
            1,
            new AttackAnimationGoal1<Ignis_Entity>(this, ULTIMATE_ATTACK, 72, true) {
               public void m_8056_() {
                  super.m_8056_();
                  float f1 = (float)Math.cos(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
                  float f2 = (float)Math.sin(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
                  float f0 = (float)Mth.m_14136_((double)f1, (double)f2);
                  Ignis_Entity.this.spawnFlameStrike(
                     Ignis_Entity.this.m_20185_(),
                     Ignis_Entity.this.m_20189_(),
                     Ignis_Entity.this.m_20186_(),
                     Ignis_Entity.this.m_20186_(),
                     f0,
                     30,
                     68,
                     0,
                     5.0F,
                     true
                  );
               }
            }
         );
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, COUNTER, 55, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, SHIELD_BREAK_COUNTER, 60, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, STRIKE, 34, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal1<>(this, SHIELD_BREAK_STRIKE, 34, true));
      this.f_21345_.m_25352_(1, new AttackAnimationGoal2<>(this, PHASE_2, 34, 54));
      this.f_21345_.m_25352_(1, new AttackAniamtionGoal3<>(this, SMASH));
      this.f_21345_.m_25352_(1, new AttackAniamtionGoal3<>(this, REINFORCED_SMASH_SOUL));
      this.f_21345_.m_25352_(1, new AttackAniamtionGoal3<>(this, REINFORCED_SMASH));
      this.f_21345_.m_25352_(1, new PredictiveChargeAttackAnimationGoal<>(this, SWING_UPPERCUT, 34, 50, 12.0F, 27, 0.3F, 0.3F));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Shield_Smash(this, SHIELD_SMASH_ATTACK));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Poked(this, POKED_ATTACK));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Air_Smash(this, SMASH_IN_AIR));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Swing_Attack_Goal(this, SWING_ATTACK, 24, 30));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Swing_Attack_Goal(this, SWING_ATTACK_SOUL, 18, 24));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Swing_Attack_Goal(this, SWING_ATTACK_BERSERK, 17, 23));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Hornzontal_Small_SwingGoal(this, 19, 13, 12, 21));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Body_Check_Attack(this));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Earth_Shudders(this, EARTH_SHUDDERS_ATTACK));
      this.f_21345_.m_25352_(1, new Ignis_Entity.Reinforced_Air_Smash(this));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0, 80));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity = source.m_7640_();
      LivingEntity target = this.m_5448_();
      double range = this.calculateRange(source);
      if (entity != null && !this.m_21525_() && (this.blockingProgress == 10.0F || this.swordProgress == 10.0F)) {
         if (target != null
            && target.m_6084_()
            && this.getAnimation() == NO_ANIMATION
            && this.m_217043_().m_188501_() * 100.0F < 12.0F
            && this.counter_strike_cooldown <= 0
            && range < 225.0) {
            this.counter_strike_cooldown = 360;
            Animation counter = this.getIsShieldBreak() ? SHIELD_BREAK_COUNTER : COUNTER;
            this.setAnimation(counter);
         }

         if (this.getAnimation() == COUNTER && this.getAnimationTick() > 16 && this.getAnimationTick() <= 46) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, STRIKE);
            this.m_5496_(SoundEvents.f_11704_, 0.5F, 0.4F + this.m_217043_().m_188501_() * 0.1F);
            if (!source.m_19378_()) {
               return false;
            }
         }

         if (this.getAnimation() == SHIELD_BREAK_COUNTER && this.getAnimationTick() > 8 && this.getAnimationTick() <= 38) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, SHIELD_BREAK_STRIKE);
            this.m_5496_(SoundEvents.f_11704_, 0.5F, 0.4F + this.m_217043_().m_188501_() * 0.1F);
            if (!source.m_19378_()) {
               return false;
            }
         }
      }

      if (source.m_7640_() instanceof Ignis_Abyss_Fireball_Entity) {
         if (source.m_7639_() instanceof Ignis_Entity) {
            return false;
         }

         if (source.m_19360_() && this.getShieldDurability() < 3) {
            this.m_5496_((SoundEvent)ModSounds.IGNIS_ARMOR_BREAK.get(), 1.0F, 0.8F);
            if (!this.f_19853_.f_46443_) {
               this.setShieldDurability(this.getShieldDurability() + 1);
            }
         }
      }

      if (range > CMConfig.IgnisLongRangelimit * CMConfig.IgnisLongRangelimit && !source.m_19378_()) {
         return false;
      } else {
         if ((
               this.getAnimation() == ULTIMATE_ATTACK
                  || this.getBossPhase() == 1 && this.m_21223_() <= this.m_21233_() * 1.0F / 3.0F
                  || this.getBossPhase() == 0 && this.m_21223_() <= this.m_21233_() * 2.0F / 3.0F
            )
            && !source.m_19378_()) {
            damage = (float)((double)damage * 0.5);
         }

         if ((this.getAnimation() == PHASE_3 || this.getAnimation() == PHASE_2 || this.getAnimation() == STRIKE) && !source.m_19378_()) {
            return false;
         } else if (source.m_7640_() instanceof Ignis_Fireball_Entity) {
            return false;
         } else if (damage > 0.0F && this.canBlockDamageSource(source)) {
            this.m_7909_(damage);
            if (!source.m_19360_() && entity instanceof LivingEntity) {
               this.m_6728_((LivingEntity)entity);
            }

            this.m_5496_(SoundEvents.f_11704_, 0.5F, 0.4F + this.m_217043_().m_188501_() * 0.1F);
            return false;
         } else {
            if (this.destroyBlocksTick <= 0) {
               this.destroyBlocksTick = 20;
            }

            Ignis_Entity.Crackiness irongolem$crackiness = this.getCrackiness();
            boolean attack = super.m_6469_(source, damage);
            if (attack && this.getCrackiness() != irongolem$crackiness) {
               this.m_5496_((SoundEvent)ModSounds.IGNIS_ARMOR_BREAK.get(), 1.0F, 0.8F);
            }

            return attack;
         }
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.IgnisDamageCap;
   }

   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
      Entity entity = damageSourceIn.m_7640_();
      boolean flag = false;
      if (entity instanceof Ignis_Abyss_Fireball_Entity && !(damageSourceIn.m_7639_() instanceof Ignis_Entity)) {
         flag = true;
      }

      if (!damageSourceIn.m_19376_() && !flag && this.getIsShield()) {
         Vec3 vector3d2 = damageSourceIn.m_7270_();
         if (vector3d2 != null) {
            Vec3 vector3d = this.m_20252_(1.0F);
            Vec3 vector3d1 = vector3d2.m_82505_(this.m_20182_()).m_82541_();
            vector3d1 = new Vec3(vector3d1.f_82479_, 0.0, vector3d1.f_82481_);
            return vector3d1.m_82526_(vector3d) < 0.0;
         }
      }

      return false;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_BLOCKING, false);
      this.f_19804_.m_135372_(IS_SHIELD, false);
      this.f_19804_.m_135372_(IS_SHIELD_BREAK, false);
      this.f_19804_.m_135372_(SHIELD_DURABILITY, 0);
      this.f_19804_.m_135372_(IS_SWORD, false);
      this.f_19804_.m_135372_(SHOW_SHIELD, true);
      this.f_19804_.m_135372_(BOSS_PHASE, 0);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("BossPhase", this.getBossPhase());
      compound.m_128379_("Is_Shield_Break", this.getIsShieldBreak());
      compound.m_128405_("Shield_Durability", this.getShieldDurability());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setBossPhase(compound.m_128451_("BossPhase"));
      this.setIsShieldBreak(compound.m_128471_("Is_Shield_Break"));
      this.setShieldDurability(compound.m_128451_("Shield_Durability"));
      if (this.m_8077_()) {
         this.bossInfo.m_6456_(this.m_5446_());
      }
   }

   public void setIsBlocking(boolean isBlocking) {
      if (isBlocking && this.getIsSword()) {
         this.setIsSword(false);
      }

      this.f_19804_.m_135381_(IS_BLOCKING, isBlocking);
   }

   public boolean getIsBlocking() {
      return (Boolean)this.f_19804_.m_135370_(IS_BLOCKING);
   }

   public void setIsShield(boolean isShield) {
      this.f_19804_.m_135381_(IS_SHIELD, isShield);
   }

   public boolean getIsShield() {
      return (Boolean)this.f_19804_.m_135370_(IS_SHIELD);
   }

   public void setIsSword(boolean isSword) {
      if (isSword && this.getIsBlocking()) {
         this.setIsBlocking(false);
      }

      this.f_19804_.m_135381_(IS_SWORD, isSword);
   }

   public boolean getIsSword() {
      return (Boolean)this.f_19804_.m_135370_(IS_SWORD);
   }

   public void setIsShieldBreak(boolean isShieldBreak) {
      if (isShieldBreak) {
         if (this.getIsBlocking()) {
            this.setIsBlocking(false);
            this.setIsSword(true);
         }

         this.setShieldDurability(3);
         this.setShowShield(false);
      }

      this.f_19804_.m_135381_(IS_SHIELD_BREAK, isShieldBreak);
   }

   public boolean getIsShieldBreak() {
      return (Boolean)this.f_19804_.m_135370_(IS_SHIELD_BREAK);
   }

   public void setShieldDurability(int ShieldDurability) {
      this.f_19804_.m_135381_(SHIELD_DURABILITY, ShieldDurability);
   }

   public int getShieldDurability() {
      return (Integer)this.f_19804_.m_135370_(SHIELD_DURABILITY);
   }

   public void setShowShield(boolean showShield) {
      this.f_19804_.m_135381_(SHOW_SHIELD, showShield);
   }

   public boolean getShowShield() {
      return (Boolean)this.f_19804_.m_135370_(SHOW_SHIELD);
   }

   public void setBossPhase(int bossPhase) {
      this.f_19804_.m_135381_(BOSS_PHASE, bossPhase);
   }

   public int getBossPhase() {
      return (Integer)this.f_19804_.m_135370_(BOSS_PHASE);
   }

   public Ignis_Entity.Crackiness getCrackiness() {
      return Ignis_Entity.Crackiness.byFraction(this.m_21223_() / this.m_21233_());
   }

   public static Builder ignis() {
      return m_33035_()
         .m_22268_(Attributes.f_22277_, 50.0)
         .m_22268_(Attributes.f_22279_, 0.33F)
         .m_22268_(Attributes.f_22281_, 14.0)
         .m_22268_(Attributes.f_22276_, 450.0)
         .m_22268_(Attributes.f_22284_, 10.0)
         .m_22268_(Attributes.f_22278_, 1.0);
   }

   public float m_213856_() {
      return 1.0F;
   }

   protected int m_7302_(int air) {
      return air;
   }

   private void floatStrider() {
      if (this.m_20077_()) {
         CollisionContext lvt_1_1_ = CollisionContext.m_82750_(this);
         if (lvt_1_1_.m_6513_(LiquidBlock.f_54690_, this.m_20183_().m_7495_(), true)
            && !this.f_19853_.m_6425_(this.m_20183_().m_7494_()).m_205070_(FluidTags.f_13132_)) {
            this.m_6853_(true);
         } else {
            this.m_20256_(this.m_20184_().m_82490_(0.5).m_82520_(0.0, (double)this.f_19796_.m_188501_() * 0.5, 0.0));
         }
      }
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_20256_(itementity.m_20184_().m_82542_(0.0, 1.5, 0.0));
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   protected SoundEvent m_7515_() {
      return (SoundEvent)ModSounds.IGNIS_AMBIENT.get();
   }

   private static Animation getRandomPoke(RandomSource rand) {
      switch (rand.m_188503_(3)) {
         case 0:
            return POKE_ATTACK;
         case 1:
            return POKE_ATTACK2;
         case 2:
            return POKE_ATTACK3;
         default:
            return POKE_ATTACK;
      }
   }

   private static Animation getRandomReinforced(RandomSource rand) {
      switch (rand.m_188503_(2)) {
         case 0:
            return REINFORCED_SMASH_IN_AIR;
         case 1:
            return REINFORCED_SMASH_IN_AIR_SOUL;
         default:
            return REINFORCED_SMASH_IN_AIR;
      }
   }

   public boolean m_203441_(FluidState p_204067_) {
      return p_204067_.m_205070_(FluidTags.f_13132_);
   }

   @Override
   public void m_8119_() {
      this.bossInfo.m_142711_(this.m_21223_() / this.m_21233_());
      this.prevblockingProgress = this.blockingProgress;
      this.prevswordProgress = this.swordProgress;
      this.floatStrider();
      if (this.getIsBlocking() && this.blockingProgress < 10.0F) {
         this.blockingProgress++;
      }

      if (!this.getIsBlocking() && this.blockingProgress > 0.0F) {
         this.blockingProgress--;
      }

      if (this.getIsSword() && this.swordProgress < 10.0F) {
         this.swordProgress++;
      }

      if (!this.getIsSword() && this.swordProgress > 0.0F) {
         this.swordProgress--;
      }

      if (!this.m_20197_().isEmpty() && ((Entity)this.m_20197_().get(0)).m_6144_() && this.getAnimation() == POKED_ATTACK) {
         ((Entity)this.m_20197_().get(0)).m_20260_(false);
      }

      LivingEntity target = this.m_5448_();
      this.SwingParticles();
      if (this.f_19853_.f_46443_) {
         if (this.f_19796_.m_188503_(24) == 0 && !this.m_20067_()) {
            this.f_19853_
               .m_7785_(
                  this.m_20185_() + 0.5,
                  this.m_20186_() + 0.5,
                  this.m_20189_() + 0.5,
                  SoundEvents.f_11702_,
                  this.m_5720_(),
                  1.0F + this.f_19796_.m_188501_(),
                  this.f_19796_.m_188501_() * 0.7F + 0.3F,
                  false
               );
         }

         if (this.getBossPhase() > 1) {
            int i = this.getCrackiness() == Ignis_Entity.Crackiness.NONE
               ? 5
               : (this.getCrackiness() == Ignis_Entity.Crackiness.LOW ? 4 : (this.getCrackiness() == Ignis_Entity.Crackiness.MEDIUM ? 3 : 2));
            if (this.f_19796_.m_188503_(i) == 0) {
               this.f_19853_.m_7106_((ParticleOptions)ModParticle.SOUL_LAVA.get(), this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), 0.0, 0.0, 0.0);
            }
         } else {
            for (int i = 0; i < 2; i++) {
               this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20208_(0.5), this.m_20187_(), this.m_20262_(0.5), 0.0, 0.0, 0.0);
            }
         }
      } else {
         if (this.timeWithoutTarget > 0) {
            this.timeWithoutTarget--;
         }

         if (target != null) {
            this.timeWithoutTarget = 200;
            if (this.getIsShieldBreak()) {
               this.setIsSword(true);
            } else {
               this.setIsBlocking(true);
            }
         }

         if (this.getAnimation() == NO_ANIMATION && this.timeWithoutTarget <= 0) {
            if (!this.m_21525_() && CMConfig.IgnisNatureHealing > 0.0 && this.f_19797_ % 20 == 0) {
               this.m_5634_((float)CMConfig.IgnisNatureHealing);
            }

            if (this.getIsBlocking() || this.getIsSword() && target == null) {
               this.setIsSword(false);
               this.setIsBlocking(false);
            }
         }

         if (this.getBossPhase() > 0) {
            this.bossInfo.m_6451_(BossBarColor.BLUE);
            this.bossInfo.setRenderType(3);
         }

         if (this.getBossPhase() > 1) {
            this.bossInfo.m_7003_(true);
            if (this.getAnimation() != PHASE_3) {
               this.setIsShieldBreak(true);
            }
         }

         if (this.getIsBlocking() && this.blockingProgress == 10.0F) {
            if (this.getAnimation() == NO_ANIMATION) {
               this.setIsShield(true);
            } else if (this.getAnimation() == COUNTER) {
               this.setIsShield(true);
            } else if (this.getAnimation() == STRIKE) {
               this.setIsShield(false);
            } else if (this.getAnimation() == POKED_ATTACK) {
               this.setIsShield(false);
            } else if (this.getAnimation() == BREAK_THE_SHIELD) {
               this.setIsShield(false);
            } else if (this.getAnimation() == HORIZONTAL_SWING_ATTACK) {
               this.setIsShield(this.getAnimationTick() > 31);
            } else if (this.getAnimation() == HORIZONTAL_SWING_ATTACK_SOUL) {
               this.setIsShield(this.getAnimationTick() > 27);
            } else if (this.getAnimation() == BODY_CHECK_ATTACK1
               || this.getAnimation() == BODY_CHECK_ATTACK2
               || this.getAnimation() == BODY_CHECK_ATTACK3
               || this.getAnimation() == BODY_CHECK_ATTACK4) {
               this.setIsShield(this.getAnimationTick() < 25);
            } else if (this.getAnimation() == BODY_CHECK_ATTACK_SOUL1
               || this.getAnimation() == BODY_CHECK_ATTACK_SOUL2
               || this.getAnimation() == BODY_CHECK_ATTACK_SOUL3
               || this.getAnimation() == BODY_CHECK_ATTACK_SOUL4) {
               this.setIsShield(this.getAnimationTick() < 21);
            } else if (this.getAnimation() == POKE_ATTACK) {
               this.setIsShield(this.getAnimationTick() < 39);
            } else if (this.getAnimation() == POKE_ATTACK2) {
               this.setIsShield(this.getAnimationTick() < 34);
            } else if (this.getAnimation() == POKE_ATTACK3) {
               this.setIsShield(this.getAnimationTick() < 29);
            } else if (this.getAnimation() == SWING_ATTACK) {
               this.setIsShield(this.getAnimationTick() < 24);
            } else if (this.getAnimation() == SWING_ATTACK_SOUL) {
               this.setIsShield(this.getAnimationTick() < 18);
            } else if (this.getAnimation() == SWING_ATTACK_BERSERK) {
               this.setIsShield(this.getAnimationTick() < 15);
            } else if (this.getAnimation() == SWING_UPPERSLASH) {
               this.setIsShield(this.getAnimationTick() > 27);
            } else if (this.getAnimation() == MAGIC_ATTACK) {
               this.setIsShield(this.getAnimationTick() > 34 && this.getAnimationTick() < 46);
            } else if (this.getAnimation() == HORIZONTAL_SMALL_SWING_ATTACK) {
               this.setIsShield(false);
            } else if (this.getAnimation() == HORIZONTAL_SMALL_SWING_ALT_ATTACK2) {
               this.setIsShield(false);
            } else if (this.getAnimation() == EARTH_SHUDDERS_ATTACK) {
               this.setIsShield(false);
            } else if (this.getAnimation() != REINFORCED_SMASH_SOUL && this.getAnimation() != REINFORCED_SMASH) {
               if (this.getAnimation() == REINFORCED_SMASH_IN_AIR_SOUL || this.getAnimation() == REINFORCED_SMASH_IN_AIR) {
                  this.setIsShield(false);
               }
            } else {
               this.setIsShield(this.getAnimationTick() > 6 && this.getAnimationTick() < 19);
            }
         } else {
            this.setIsShield(false);
         }
      }

      if (this.body_check_cooldown > 0) {
         this.body_check_cooldown--;
      }

      if (this.air_smash_cooldown > 0) {
         this.air_smash_cooldown--;
      }

      if (this.counter_strike_cooldown > 0) {
         this.counter_strike_cooldown--;
      }

      if (this.poke_cooldown > 0) {
         this.poke_cooldown--;
      }

      if (this.earth_shudders_cooldown > 0) {
         this.earth_shudders_cooldown--;
      }

      if (this.horizontal_small_swing_cooldown > 0) {
         this.horizontal_small_swing_cooldown--;
      }

      if (this.horizontal_swing_cooldown > 0) {
         this.horizontal_swing_cooldown--;
      }

      if (this.magic_cooldown > 0) {
         this.magic_cooldown--;
      }

      if (this.reinforced_smash_cooldown > 0) {
         this.reinforced_smash_cooldown--;
      }

      if (this.sword_dance_cooldown > 0) {
         this.sword_dance_cooldown--;
      }

      if (this.ultimate_cooldown > 0) {
         this.ultimate_cooldown--;
      }

      this.repelEntities(1.4F, 4.0F, 1.4F, 1.4F);
      Animation animation = getRandomPoke(this.f_19796_);
      if (this.m_6084_()) {
         if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.getShieldDurability() > 2 && !this.getIsShieldBreak()) {
            this.setAnimation(BREAK_THE_SHIELD);
         } else if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.m_21223_() <= this.m_21233_() * 2.0F / 3.0F && this.getBossPhase() < 1) {
            this.setAnimation(PHASE_2);
         } else if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.m_21223_() <= this.m_21233_() * 1.0F / 3.0F && this.getBossPhase() < 2) {
            this.setAnimation(PHASE_3);
         } else if (target != null && target.m_6084_()) {
            if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
               && !this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.m_20280_(target) <= 225.0
               && this.ultimate_cooldown <= 0
               && this.getBossPhase() > 1) {
               this.ultimate_cooldown = 1200;
               this.setAnimation(ULTIMATE_ATTACK);
            } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
               && !this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.m_20280_(target) <= 400.0
               && this.reinforced_smash_cooldown <= 0) {
               this.reinforced_smash_cooldown = 1800;
               Animation ranimation = getRandomReinforced(this.f_19796_);
               this.setAnimation(ranimation);
            } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
               && !this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.m_20280_(target) >= 225.0
               && this.m_20280_(target) <= 1024.0
               && target.m_20096_()
               && this.air_smash_cooldown <= 0) {
               this.air_smash_cooldown = 240;
               this.setAnimation(SMASH_IN_AIR);
            } else if ((
                  this.blockingProgress != 10.0F && this.swordProgress != 10.0F
                     || this.m_21525_()
                     || this.getAnimation() != NO_ANIMATION
                     || !(this.m_20280_(target) >= 81.0)
                     || !(this.m_20280_(target) <= 625.0)
                     || this.magic_cooldown > 0
                     || !(this.m_217043_().m_188501_() * 100.0F < 1.0F)
               )
               && (
                  this.m_21525_()
                     || this.getAnimation() != NO_ANIMATION
                     || !(this.m_217043_().m_188501_() * 100.0F < 10.0F)
                     || !(this.m_20186_() + 5.0 <= target.m_20186_())
                     || this.magic_cooldown > 0
               )) {
               if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 12.0F
                  && this.m_20270_(target) > 5.0F
                  && this.poke_cooldown <= 0
                  && this.m_217043_().m_188501_() * 100.0F < 4.0F) {
                  this.poke_cooldown = 240;
                  this.setAnimation(animation);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 12.0F
                  && this.m_217043_().m_188501_() * 100.0F < 15.0F
                  && this.poke_cooldown <= 0
                  && target.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
                  this.poke_cooldown = 240;
                  this.setAnimation(animation);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 6.5F
                  && this.m_217043_().m_188501_() * 100.0F < 4.0F) {
                  Animation animation2 = this.getBossPhase() > 0 ? HORIZONTAL_SWING_ATTACK_SOUL : HORIZONTAL_SWING_ATTACK;
                  this.horizontal_swing_cooldown = 160;
                  this.setAnimation(animation2);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 4.25F
                  && this.m_217043_().m_188501_() * 100.0F < 8.0F) {
                  Animation animation3 = this.getBossPhase() > 1 ? SWING_ATTACK_BERSERK : (this.getBossPhase() > 0 ? SWING_ATTACK_SOUL : SWING_ATTACK);
                  this.setAnimation(animation3);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 5.25F
                  && this.m_217043_().m_188501_() * 100.0F < 5.0F
                  && this.getIsShieldBreak()
                  && this.sword_dance_cooldown <= 0) {
                  this.sword_dance_cooldown = this.getBossPhase() > 1 ? 600 : (this.getBossPhase() > 0 ? 720 : 840);
                  this.setAnimation(COMBO1);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 3.0F
                  && this.m_217043_().m_188501_() * 100.0F < 20.0F
                  && !this.getIsShieldBreak()) {
                  this.setAnimation(SHIELD_SMASH_ATTACK);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 5.0F
                  && this.m_217043_().m_188501_() * 100.0F < 0.7F
                  && this.counter_strike_cooldown <= 0
                  && !target.m_21023_((MobEffect)ModEffect.EFFECTSTUN.get())) {
                  this.counter_strike_cooldown = 360;
                  Animation counter = this.getIsShieldBreak() ? SHIELD_BREAK_COUNTER : COUNTER;
                  this.setAnimation(counter);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) > 4.5F
                  && this.m_20270_(target) < 11.0F
                  && this.earth_shudders_cooldown <= 0
                  && this.m_217043_().m_188501_() * 100.0F < 1.0F
                  && this.m_20186_() >= target.m_20186_() - 2.5
                  && this.m_20186_() <= target.m_20186_() + 2.5) {
                  this.earth_shudders_cooldown = 800;
                  this.setAnimation(EARTH_SHUDDERS_ATTACK);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 5.5F
                  && this.m_217043_().m_188501_() * 100.0F < 15.0F
                  && this.horizontal_small_swing_cooldown <= 0) {
                  this.horizontal_small_swing_cooldown = 100;
                  this.setAnimation(HORIZONTAL_SMALL_SWING_ATTACK);
               } else if ((this.blockingProgress == 10.0F || this.swordProgress == 10.0F)
                  && !this.m_21525_()
                  && this.getAnimation() == NO_ANIMATION
                  && this.m_20270_(target) < 3.0F
                  && this.m_217043_().m_188501_() * 100.0F < 10.0F
                  && this.body_check_cooldown <= 0) {
                  this.body_check_cooldown = 200;
                  Animation animation5 = this.getBossPhase() > 0 ? BODY_CHECK_ATTACK_SOUL1 : BODY_CHECK_ATTACK1;
                  this.setAnimation(animation5);
               }
            } else {
               this.magic_cooldown = 300;
               this.setAnimation(MAGIC_ATTACK);
            }
         }
      }

      this.blockbreak();
      super.m_8119_();
   }

   public void m_8107_() {
      super.m_8107_();
      int brand = this.getBossPhase() > 0 ? 240 : 200;
      if (this.getAnimation() == SWING_ATTACK && this.getAnimationTick() == 24) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(6.5F, 6.0F, 70.0F, 1.0F, 0.05F, 80, 2, brand, 5, false, 0.0F);
      }

      if (this.getAnimation() == HORIZONTAL_SWING_ATTACK && this.getAnimationTick() == 31) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(5.25F, 6.0F, 210.0F, 1.0F, 0.06F, 120, 3, brand, 5, false, 0.0F);
      }

      if (this.getAnimation() == SWING_ATTACK_SOUL && this.getAnimationTick() == 18) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(6.5F, 6.0F, 70.0F, 1.0F, 0.05F, 80, 2, brand, 5, false, 0.0F);
      }

      if (this.getAnimation() == SWING_ATTACK_BERSERK && this.getAnimationTick() == 17) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(6.5F, 6.0F, 70.0F, 1.0F, 0.05F, 80, 2, brand, 7, false, 0.0F);
      }

      if (this.getAnimation() == HORIZONTAL_SWING_ATTACK_SOUL && this.getAnimationTick() == 27) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(5.25F, 6.0F, 210.0F, 1.0F, 0.06F, 120, 3, brand, 5, false, 0.0F);
      }

      if (this.getAnimation() == HORIZONTAL_SMALL_SWING_ATTACK && this.getAnimationTick() == 19) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(5.25F, 6.0F, 120.0F, 0.4F, 0.03F, 0, 2, brand, 3, true, 0.0F);
      }

      if (this.getAnimation() == HORIZONTAL_SMALL_SWING_ALT_ATTACK2 && this.getAnimationTick() == 13) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(5.25F, 6.0F, 120.0F, 0.4F, 0.03F, 40, 2, brand, 3, false, 0.0F);
      }

      if (this.getAnimation() == SPIN_ATTACK && this.getAnimationTick() == 14) {
         this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
         this.AreaAttack(6.5F, 6.0F, 310.0F, 1.0F, 0.06F, 120, 3, brand, 5, false, 0.3F);
      }

      if (this.getAnimation() == BREAK_THE_SHIELD) {
         if (this.getAnimationTick() == 25) {
            this.setShowShield(false);
            this.ShieldExplode(-2.75F, 1.5F, 2.0F);
         }

         if (this.getAnimationTick() == 79) {
            this.setIsShieldBreak(true);
         }

         if (this.getAnimationTick() == 55) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.15F, 0, 50);
            List<LivingEntity> entities = this.getEntityLivingBaseNearby(12.0, 12.0, 12.0, 12.0);
            this.m_5496_((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 0.8F);

            for (LivingEntity inRange : entities) {
               if ((!(inRange instanceof Player) || !((Player)inRange).m_150110_().f_35934_) && !this.m_7307_(inRange)) {
                  inRange.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), 60));
               }
            }
         }
      }

      if (this.getAnimation() == PHASE_2) {
         if (this.getAnimationTick() == 29) {
            this.m_5496_((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() > 29 && this.getAnimationTick() < 39) {
            this.Sphereparticle(2.0F, 0.0F, 5.0F);
            this.Phase_Transition(14, 0.4F, 0.03F, 5, 240);
         }

         if (this.getAnimationTick() == 34) {
            this.setBossPhase(1);
         }
      }

      if (this.getAnimation() == PHASE_3) {
         if (this.getAnimationTick() == 58) {
            this.setBossPhase(2);
            this.setShowShield(false);
            if (!this.getIsShieldBreak()) {
               this.ShieldExplode(2.0F, 0.575F, 2.0F);
            }

            this.m_5496_((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.m_5496_((SoundEvent)ModSounds.SWORD_STOMP.get(), 1.0F, 0.75F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.15F, 0, 10);
            this.ShieldSmashparticle(0.5F, 1.0F, -0.15F);
         }

         if (this.getAnimationTick() > 58 && this.getAnimationTick() < 68) {
            this.Sphereparticle(0.5F, 1.0F, 6.0F);
            this.Phase_Transition(27, 0.6F, 0.05F, 5, 240);
         }
      }

      if (this.getAnimation() == SHIELD_SMASH_ATTACK) {
         if (this.getAnimationTick() == 34) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.AreaAttack(4.85F, 2.5F, 45.0F, 1.5F, 0.15F, 200, 0, 0, 5, false, 0.0F);
            this.ShieldSmashparticle(1.3F, 2.75F, -0.1F);
         }

         int i = 34;

         for (int j = 4; i <= 40; j++) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 1.5F, 2.75F, false, 0, 1.0F, 0.02F, 0.1F);
            }

            i += 3;
         }
      }

      if (this.getAnimation() == SMASH) {
         float vec = this.getIsShieldBreak() ? 1.8F : 1.5F;
         float radius = this.getIsShieldBreak() ? 0.8F : 1.3F;
         float math = this.getIsShieldBreak() ? 0.3F : 0.0F;
         if (this.getAnimationTick() == 5) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.AreaAttack(4.85F, 2.5F, 45.0F, 1.5F, 0.15F, 200, 0, 0, 5, false, 0.0F);
            this.ShieldSmashparticle(radius, vec, math);
         }

         int i = 5;

         for (int j = 3; i <= 14; j++) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 1.5F, vec, false, 0, 1.0F, 0.02F, 0.1F);
            }

            i += 3;
         }
      }

      float vec = this.getIsShieldBreak() ? 1.5F : 3.0F;
      if (this.getAnimation() == REINFORCED_SMASH) {
         if (this.getAnimationTick() == 5) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);

            for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(1.25))) {
               if (!this.m_7307_(entity) && !(entity instanceof Ignis_Entity) && entity != this) {
                  DamageSource damagesource = DamageSource.m_19370_(this);
                  entity.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * 1.5 + (double)(entity.m_21233_() * 0.15F)));
                  if (entity.m_21275_(damagesource) && entity instanceof Player player) {
                     this.disableShield(player, 200);
                  }
               }
            }

            this.ShieldSmashparticle(1.3F, vec, 0.0F);
         }

         int i = 5;

         for (int j = 3; i <= 19; j++) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 2.5F, 2.0F, false, 80, 1.1F, 0.06F, 0.075F);
            }

            i += 2;
         }

         if (this.getAnimationTick() == 46) {
            this.m_5496_((SoundEvent)ModSounds.SWORD_STOMP.get(), 1.0F, 0.75F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.15F, 0, 20);
            this.ShieldSmashparticle(0.5F, 2.0F, 0.6F);
            this.AreaAttack(4.85F, 2.5F, 45.0F, 1.5F, 0.15F, 200, 0, 0, 5, false, 0.0F);
            switch (this.f_19796_.m_188503_(3)) {
               case 0:
                  this.shootAbyssFireball(new Vec3(2.0, 3.0, 0.0), 54);
                  this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 41);
                  this.shootFireball(new Vec3(0.0, 3.0, 0.0), 28);
                  break;
               case 1:
                  this.shootFireball(new Vec3(2.0, 3.0, 0.0), 28);
                  this.shootAbyssFireball(new Vec3(-2.0, 3.0, 0.0), 54);
                  this.shootFireball(new Vec3(0.0, 3.0, 0.0), 41);
                  break;
               case 2:
                  this.shootFireball(new Vec3(2.0, 3.0, 0.0), 28);
                  this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 41);
                  this.shootAbyssFireball(new Vec3(0.0, 3.0, 0.0), 54);
            }
         }

         if (this.getAnimationTick() > 46 && this.getAnimationTick() < 56) {
            this.Sphereparticle(0.75F, 2.0F, 6.0F);
         }

         i = 46;

         for (int j = 16; i <= 57; j--) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 2.5F, 2.0F, false, 80, 1.1F, 0.06F, 0.075F);
            }

            i++;
         }
      }

      if (this.getAnimation() == REINFORCED_SMASH_SOUL) {
         if (this.getAnimationTick() == 5) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);

            for (LivingEntity entityx : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(1.25))) {
               if (!this.m_7307_(entityx) && !(entityx instanceof Ignis_Entity) && entityx != this) {
                  DamageSource damagesource = DamageSource.m_19370_(this);
                  boolean flag = entityx.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * 1.5 + (double)(entityx.m_21233_() * 0.15F)));
                  if (entityx.m_21275_(damagesource) && entityx instanceof Player player) {
                     this.disableShield(player, 200);
                  }
               }
            }

            this.ShieldSmashparticle(1.3F, vec, 0.0F);
         }

         int i = 5;

         for (int j = 16; i <= 25; j--) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 2.5F, vec, true, 80, 1.1F, 0.06F, 0.075F);
            }

            i += 2;
         }

         if (this.getAnimationTick() == 46) {
            this.m_5496_((SoundEvent)ModSounds.SWORD_STOMP.get(), 1.0F, 0.75F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.15F, 0, 20);
            this.ShieldSmashparticle(0.5F, 2.0F, 0.6F);
            this.AreaAttack(4.85F, 2.5F, 45.0F, 1.5F, 0.15F, 200, 0, 0, 5, false, 0.0F);
            switch (this.f_19796_.m_188503_(3)) {
               case 0:
                  this.shootAbyssFireball(new Vec3(2.0, 3.0, 0.0), 54);
                  this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 41);
                  this.shootFireball(new Vec3(0.0, 3.0, 0.0), 28);
                  break;
               case 1:
                  this.shootFireball(new Vec3(2.0, 3.0, 0.0), 28);
                  this.shootAbyssFireball(new Vec3(-2.0, 3.0, 0.0), 54);
                  this.shootFireball(new Vec3(0.0, 3.0, 0.0), 41);
                  break;
               case 2:
                  this.shootFireball(new Vec3(2.0, 3.0, 0.0), 28);
                  this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 41);
                  this.shootAbyssFireball(new Vec3(0.0, 3.0, 0.0), 54);
            }
         }

         if (this.getAnimationTick() > 46 && this.getAnimationTick() < 56) {
            this.Sphereparticle(0.75F, 2.0F, 6.0F);
         }

         for (int l = 48; l <= 55; l++) {
            if (this.getAnimationTick() == l) {
               int d = l - 45;
               this.ShieldSmashDamage(2.0F, d, 2.5F, 2.0F, false, 80, 1.1F, 0.06F, 0.075F);
            }
         }
      }

      if (this.getAnimation() == REINFORCED_SMASH_IN_AIR) {
         if (this.getAnimationTick() == 23) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(4.5F, 8.0F, 100.0F, 1.0F, 0.1F, 120, 3, brand, 5, false, 0.65F);
         }

         if (this.getAnimationTick() == 53) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.5F, 6.0F, 70.0F, 1.1F, 0.1F, 120, 2, brand, 5, false, 0.0F);
         }

         if (this.getAnimationTick() > 74 && this.getAnimationTick() < 81) {
            this.PatternParticle(1.5F, 0.0F, 1.0F, false);
         }

         if (this.getAnimationTick() == 21) {
            this.bladeFireball(2.0F, -1.5F, 5.0F, 30);
         }

         if (this.getAnimationTick() == 23) {
            this.bladeFireball(3.0F, 0.0F, 4.0F, 28);
         }

         if (this.getAnimationTick() == 25) {
            this.bladeFireball(2.0F, 1.5F, 3.0F, 26);
         }
      }

      if (this.getAnimation() == REINFORCED_SMASH_IN_AIR_SOUL) {
         if (this.getAnimationTick() == 23) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(4.5F, 8.0F, 100.0F, 1.0F, 0.1F, 120, 3, brand, 5, false, 0.65F);
         }

         if (this.getAnimationTick() == 53) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.5F, 6.0F, 70.0F, 1.1F, 0.1F, 120, 2, brand, 5, false, 0.0F);
         }

         if (this.getAnimationTick() == 21) {
            this.bladeFireball(2.0F, -1.5F, 5.0F, 30);
         }

         if (this.getAnimationTick() == 23) {
            this.bladeFireball(3.0F, 0.0F, 4.0F, 28);
         }

         if (this.getAnimationTick() == 25) {
            this.bladeFireball(2.0F, 1.5F, 3.0F, 26);
         }

         if (this.getAnimationTick() > 74 && this.getAnimationTick() < 81) {
            this.PatternParticle(1.5F, 0.0F, 1.0F, true);
         }
      }

      if (this.getAnimation() == STRIKE) {
         if (this.getAnimationTick() == 31) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(5.25F, 6.0F, 120.0F, 1.25F, 0.1F, 120, 5, brand, 7, false, 0.0F);
         }

         if (this.getAnimationTick() > 31 && this.getAnimationTick() < 35) {
            this.StrikeParticle(0.75F, 5, 0.0F);
         }

         int i = 36;

         for (int j = 4; i <= 48; j += 2) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(0.75F, j, 2.5F, 0.0F, true, 240, 1.1F, 0.12F, 0.1F);
               this.ShieldSmashDamage(0.75F, j + 1, 2.5F, 0.0F, true, 240, 1.1F, 0.12F, 0.1F);
               this.earthquakesound((float)((j + j + 1) / 2));
            }

            i += 2;
         }
      }

      if (this.getAnimation() == SHIELD_BREAK_STRIKE) {
         if (this.getAnimationTick() == 15) {
            this.m_5496_((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 0.8F);
         }

         if (this.getAnimationTick() == 17) {
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.15F, 0, 50);

            for (LivingEntity inRangex : this.getEntityLivingBaseNearby(12.0, 12.0, 12.0, 12.0)) {
               if ((!(inRangex instanceof Player) || !((Player)inRangex).m_150110_().f_35934_) && !this.m_7307_(inRangex)) {
                  inRangex.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), 60));
               }
            }
         }

         if (this.getAnimationTick() == 44) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.0F, 6.0F, 310.0F, 1.25F, 0.1F, 120, 5, brand, 7, false, 0.3F);
         }

         int i = 49;

         for (int j = 4; i <= 61; j += 2) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(0.75F, j, 2.5F, 0.0F, true, 240, 1.1F, 0.12F, 0.1F);
               this.ShieldSmashDamage(0.75F, j + 1, 2.5F, 0.0F, true, 240, 1.1F, 0.12F, 0.1F);
               this.earthquakesound((float)((j + j + 1) / 2));
            }

            i += 2;
         }

         if (this.getAnimationTick() > 44 && this.getAnimationTick() < 48) {
            this.StrikeParticle(0.75F, 5, 0.0F);
         }
      }

      if (this.getAnimation() == POKE_ATTACK) {
         if (this.getAnimationTick() == 37) {
            this.m_5496_((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 0.75F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() == 39) {
            this.Poke(7.0F, 70.0F, 160);
         }
      }

      if (this.getAnimation() == POKE_ATTACK2) {
         if (this.getAnimationTick() == 32) {
            this.m_5496_((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 0.75F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() == 34) {
            this.Poke(7.0F, 65.0F, 160);
         }
      }

      if (this.getAnimation() == POKE_ATTACK3) {
         if (this.getAnimationTick() == 27) {
            this.m_5496_((SoundEvent)ModSounds.IGNIS_POKE.get(), 1.0F, 0.75F + this.m_217043_().m_188501_() * 0.1F);
         }

         if (this.getAnimationTick() == 29) {
            this.Poke(7.0F, 60.0F, 160);
         }
      }

      if ((
            this.getAnimation() == BODY_CHECK_ATTACK1
               || this.getAnimation() == BODY_CHECK_ATTACK2
               || this.getAnimation() == BODY_CHECK_ATTACK3
               || this.getAnimation() == BODY_CHECK_ATTACK4
         )
         && this.getAnimationTick() == 25) {
         this.BodyCheckAttack(3.0F, 6.0F, 120.0F, 0.8F, 0.03F, 80, 80, 0.2F);
      }

      if ((
            this.getAnimation() == BODY_CHECK_ATTACK_SOUL1
               || this.getAnimation() == BODY_CHECK_ATTACK_SOUL2
               || this.getAnimation() == BODY_CHECK_ATTACK_SOUL3
               || this.getAnimation() == BODY_CHECK_ATTACK_SOUL4
         )
         && this.getAnimationTick() == 21) {
         this.BodyCheckAttack(3.0F, 6.0F, 120.0F, 0.9F, 0.03F, 100, 100, 0.2F);
      }

      if (this.getAnimation() == SWING_UPPERCUT && this.getAnimationTick() == 32) {
         this.BodyCheckAttack(4.5F, 8.0F, 120.0F, 1.0F, 0.03F, 60, 70, 0.8);
      }

      if (this.getAnimation() == SWING_UPPERSLASH) {
         if (this.getAnimationTick() == 24) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(4.5F, 8.0F, 100.0F, 1.0F, 0.05F, 120, 3, brand, 5, false, 0.65F);
         }

         for (int lx = 26; lx <= 30; lx += 2) {
            if (this.getAnimationTick() == lx) {
               int d = lx - 23;
               int d2 = lx - 22;
               float ds = (float)((d + d2) / 2);
               this.ShieldSmashDamage(0.4F, d, 2.5F, 0.0F, false, 80, 1.0F, 0.03F, 0.1F);
               this.earthquakesound(ds);
               this.ShieldSmashDamage(0.4F, d2, 2.5F, 0.0F, false, 80, 1.0F, 0.03F, 0.1F);
            }
         }
      }

      if (this.getAnimation() == EARTH_SHUDDERS_ATTACK) {
         if (this.getAnimationTick() == 32) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.AreaAttack(4.0F, 6.0F, 80.0F, 1.2F, 0.08F, 120, 5, brand, 5, false, 0.0F);
            this.ShieldSmashparticle(0.75F, 2.3F, -0.65F);

            for (int lxx = 7; lxx >= 4; lxx--) {
               this.ShieldSmashDamage(2.0F, lxx, 3.0F, 2.3F, false, 80, 1.0F, 0.05F, 0.05F);
            }
         }

         if (this.getAnimationTick() == 73) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.AreaAttack(4.0F, 6.0F, 80.0F, 1.2F, 0.08F, 120, 5, brand, 5, false, 0.0F);
            this.ShieldSmashparticle(0.75F, 1.85F, -0.6F);
         }

         int i = 73;

         for (int j = 16; i <= 85; j -= 2) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 3.0F, 2.3F, true, 80, 1.0F, 0.08F, 0.05F);
               this.ShieldSmashDamage(2.0F, j - 1, 3.0F, 2.3F, true, 80, 1.0F, 0.08F, 0.05F);
            }

            i += 3;
         }

         if (this.getAnimationTick() == 117) {
            this.m_5496_(SoundEvents.f_12513_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 20.0F, 0.3F, 0, 20);
            this.ShieldSmashparticle(0.75F, 2.3F, -0.65F);
            this.AreaAttack(4.0F, 6.0F, 80.0F, 1.2F, 0.08F, 120, 5, brand, 5, false, 0.0F);
         }

         i = 117;

         for (int j = 3; i <= 135; j += 2) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(2.0F, j, 3.0F, 2.3F, false, 80, 1.0F, 0.08F, 0.05F);
               this.ShieldSmashDamage(2.0F, j + 1, 3.0F, 2.3F, false, 80, 1.0F, 0.08F, 0.05F);
            }

            i += 3;
         }
      }

      if (this.getAnimation() == MAGIC_ATTACK && this.getAnimationTick() == 5) {
         this.f_19853_
            .m_7785_(
               this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11868_, this.m_5720_(), 5.0F, 1.4F + this.m_217043_().m_188501_() * 0.1F, false
            );
         switch (this.f_19796_.m_188503_(5)) {
            case 0:
               this.shootAbyssFireball(new Vec3(-5.0, 3.0, 0.0), 109);
               this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 45);
               this.shootFireball(new Vec3(0.0, 3.0, 0.0), 61);
               this.shootFireball(new Vec3(2.0, 3.0, 0.0), 77);
               this.shootFireball(new Vec3(5.0, 3.0, 0.0), 93);
               break;
            case 1:
               this.shootFireball(new Vec3(-5.0, 3.0, 0.0), 45);
               this.shootAbyssFireball(new Vec3(-2.0, 3.0, 0.0), 109);
               this.shootFireball(new Vec3(0.0, 3.0, 0.0), 61);
               this.shootFireball(new Vec3(2.0, 3.0, 0.0), 77);
               this.shootFireball(new Vec3(5.0, 3.0, 0.0), 93);
               break;
            case 2:
               this.shootFireball(new Vec3(-5.0, 3.0, 0.0), 45);
               this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 61);
               this.shootAbyssFireball(new Vec3(0.0, 3.0, 0.0), 109);
               this.shootFireball(new Vec3(2.0, 3.0, 0.0), 77);
               this.shootFireball(new Vec3(5.0, 3.0, 0.0), 93);
               break;
            case 3:
               this.shootFireball(new Vec3(-5.0, 3.0, 0.0), 45);
               this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 61);
               this.shootFireball(new Vec3(0.0, 3.0, 0.0), 77);
               this.shootAbyssFireball(new Vec3(2.0, 3.0, 0.0), 109);
               this.shootFireball(new Vec3(5.0, 3.0, 0.0), 93);
               break;
            case 4:
               this.shootFireball(new Vec3(-5.0, 3.0, 0.0), 45);
               this.shootFireball(new Vec3(-2.0, 3.0, 0.0), 61);
               this.shootFireball(new Vec3(0.0, 3.0, 0.0), 77);
               this.shootFireball(new Vec3(2.0, 3.0, 0.0), 93);
               this.shootAbyssFireball(new Vec3(5.0, 3.0, 0.0), 109);
         }
      }

      if (this.getAnimation() == ULTIMATE_ATTACK) {
         float f1 = (float)Math.cos(Math.toRadians((double)(this.m_146908_() + 90.0F)));
         float f2 = (float)Math.sin(Math.toRadians((double)(this.m_146908_() + 90.0F)));
         float f0 = (float)Mth.m_14136_((double)f1, (double)f2);
         if (this.getAnimationTick() == 74) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.5F + this.m_217043_().m_188501_() * 0.1F);
            ScreenShake_Entity.ScreenShake(this.f_19853_, this.m_20182_(), 30.0F, 0.3F, 0, 20);
            LivingEntity target = this.m_5448_();
            this.AreaAttack(6.0F, 6.0F, 45.0F, 2.0F, 0.25F, 300, 5, brand, 7, false, 0.0F);
            if (target != null) {
               double d0 = Math.min(target.m_20186_(), this.m_20186_());
               double d1 = Math.max(target.m_20186_(), this.m_20186_()) + 1.0;

               for (int lxx = 0; lxx < 8; lxx++) {
                  double d2 = 4.25 * (double)(lxx + 2);
                  int j2 = (int)(1.5F * (float)lxx);
                  this.spawnFlameStrike(this.m_20185_() + (double)f1 * d2, this.m_20189_() + (double)f2 * d2, d0, d1, f0, 60, j2, j2, 2.0F, false);
               }
            } else {
               for (int lxx = 0; lxx < 8; lxx++) {
                  double d2 = 4.25 * (double)(lxx + 2);
                  int j2 = (int)(1.5F * (float)lxx);
                  this.spawnFlameStrike(
                     this.m_20185_() + (double)f1 * d2, this.m_20189_() + (double)f2 * d2, this.m_20186_(), this.m_20186_(), f0, 60, j2, j2, 2.0F, false
                  );
               }
            }

            for (int lxx = 4; lxx < 38; lxx++) {
               this.UltimateAttack(lxx, 3.0F, 1.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -1.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 2.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -2.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 3.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -3.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 4.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -4.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 5.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -5.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 6.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -6.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 7.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -7.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 8.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -8.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, 9.5F, 150, 1.5F, 0.15F, 1.0F);
               this.UltimateAttack(lxx, 3.0F, -9.5F, 150, 1.5F, 0.15F, 1.0F);
            }

            this.earthquakesound(10.0F);
         }
      }

      if (this.getAnimation() == COMBO1) {
         if (this.getAnimationTick() == 19) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.5F, 6.0F, 90.0F, 0.75F, 0.05F, 60, 2, brand, 5, false, 0.0F);
         }

         if (this.getAnimationTick() == 38) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.5F, 6.0F, 120.0F, 0.75F, 0.05F, 60, 2, brand, 5, false, 0.0F);
         }

         if (this.getAnimationTick() == 61) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.0F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.5F, 6.0F, 70.0F, 0.75F, 0.05F, 60, 2, brand, 5, false, 0.0F);
         }

         if (this.getAnimationTick() == 76) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(6.5F, 6.0F, 310.0F, 0.75F, 0.05F, 60, 3, brand, 5, false, 0.3F);
         }
      }

      if (this.getAnimation() == COMBO2) {
         if (this.getAnimationTick() == 32) {
            this.BodyCheckAttack(4.5F, 8.0F, 120.0F, 1.0F, 0.03F, 60, 40, 0.8);
         }

         if (this.getAnimationTick() == 59) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(5.25F, 6.0F, 120.0F, 0.4F, 0.03F, 0, 2, brand, 3, false, 0.0F);
         }

         if (this.getAnimationTick() == 74) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 1.25F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(5.25F, 6.0F, 120.0F, 0.4F, 0.03F, 50, 2, brand, 3, false, 0.0F);
         }

         if (this.getAnimationTick() == 108) {
            this.m_5496_((SoundEvent)ModSounds.STRONGSWING.get(), 1.0F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
            this.AreaAttack(5.25F, 6.0F, 225.0F, 1.25F, 0.1F, 150, 5, brand, 7, false, 0.0F);
         }

         if (this.getAnimationTick() > 108 && this.getAnimationTick() < 112) {
            this.StrikeParticle(1.25F, 5, 0.0F);
         }

         int i = 108;

         for (int j = 3; i <= 116; j += 2) {
            if (this.getAnimationTick() == i) {
               this.ShieldSmashDamage(1.25F, j, 2.5F, 0.0F, true, 240, 1.1F, 0.12F, 0.1F);
               this.ShieldSmashDamage(1.25F, j + 1, 2.5F, 0.0F, true, 240, 1.1F, 0.12F, 0.1F);
               this.earthquakesound((float)((j + j + 1) / 2));
            }

            i += 2;
         }
      }
   }

   private void blockbreak() {
      if (!this.m_21525_() && !this.f_19853_.f_46443_ && this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0 && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            boolean flag = false;
            AABB aabb = this.m_20191_().m_82400_(0.2);

            for (BlockPos blockpos : BlockPos.m_121976_(
               Mth.m_14107_(aabb.f_82288_),
               Mth.m_14107_(this.m_20186_()),
               Mth.m_14107_(aabb.f_82290_),
               Mth.m_14107_(aabb.f_82291_),
               Mth.m_14107_(aabb.f_82292_),
               Mth.m_14107_(aabb.f_82293_)
            )) {
               BlockState blockstate = this.f_19853_.m_8055_(blockpos);
               if (!blockstate.m_60795_()
                  && blockstate.canEntityDestroy(this.f_19853_, blockpos, this)
                  && !blockstate.m_204336_(ModTag.IGNIS_IMMUNE)
                  && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                  flag = this.f_19853_.m_46953_(blockpos, true, this) || flag;
               }
            }

            if (flag) {
               this.f_19853_.m_5898_((Player)null, 1022, this.m_20183_(), 0);
            }
         }
      }
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return IGNIS_DEATH;
   }

   private void AreaAttack(
      float range,
      float height,
      float arc,
      float damage,
      float hpdamage,
      int shieldbreakticks,
      int firetime,
      int brandticks,
      int heal,
      boolean combo,
      float airborne
   ) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)height, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
         );
         if ((
               entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !(entityHit instanceof Ignis_Entity)) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(
               damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage + (double)(entityHit.m_21233_() * hpdamage))
            );
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               entityHit.m_20254_(firetime);
               if (brandticks > 0) {
                  MobEffectInstance effectinstance1 = entityHit.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
                  int i = 1;
                  if (effectinstance1 != null) {
                     i += effectinstance1.m_19564_();
                     entityHit.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
                  } else {
                     i--;
                  }

                  i = Mth.m_14045_(i, 0, 4);
                  MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), brandticks, i, false, true, true);
                  entityHit.m_7292_(effectinstance);
                  this.m_5634_((float)heal * (float)CMConfig.IgnisHealingMultiplier * (float)(i + 1));
               }

               if (combo && !this.Combo) {
                  this.Combo = true;
                  this.CanSpin++;
               }

               if (airborne > 0.0F) {
                  entityHit.m_20256_(entityHit.m_20184_().m_82520_(0.0, (double)airborne, 0.0));
               }
            }
         }
      }
   }

   private void BodyCheckAttack(float range, float height, float arc, float damage, float hpdamage, int shieldbreakticks, int slowticks, double airborne) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)height, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         float entityHitDistance = (float)Math.sqrt(
            (entityHit.m_20189_() - this.m_20189_()) * (entityHit.m_20189_() - this.m_20189_())
               + (entityHit.m_20185_() - this.m_20185_()) * (entityHit.m_20185_() - this.m_20185_())
         );
         if ((
               entityHitDistance <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !this.m_7307_(entityHit)
            && !(entityHit instanceof Ignis_Entity)) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(
               damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage + (double)(entityHit.m_21233_() * hpdamage))
            );
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag) {
               this.m_5496_(SoundEvents.f_11668_, 1.5F, 0.8F + this.m_217043_().m_188501_() * 0.1F);
               double d0 = entityHit.m_20185_() - this.m_20185_();
               double d1 = entityHit.m_20189_() - this.m_20189_();
               double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
               entityHit.m_5997_(d0 / d2 * 2.5, airborne, d1 / d2 * 2.5);
               if (slowticks > 0) {
                  entityHit.m_7292_(new MobEffectInstance((MobEffect)ModEffect.EFFECTSTUN.get(), slowticks));
               }
            }
         }
      }
   }

   private void Poke(float range, float arc, int shieldbreakticks) {
      for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)range, (double)range, (double)range, (double)range)) {
         float entityHitAngle = (float)(
            (Math.atan2(entityHit.m_20189_() - this.m_20189_(), entityHit.m_20185_() - this.m_20185_()) * (180.0 / Math.PI) - 90.0) % 360.0
         );
         float entityAttackingAngle = this.f_20883_ % 360.0F;
         if (entityHitAngle < 0.0F) {
            entityHitAngle += 360.0F;
         }

         if (entityAttackingAngle < 0.0F) {
            entityAttackingAngle += 360.0F;
         }

         float entityRelativeAngle = entityHitAngle - entityAttackingAngle;
         if ((
               this.m_20270_(entityHit) <= range && entityRelativeAngle <= arc / 2.0F && entityRelativeAngle >= -arc / 2.0F
                  || entityRelativeAngle >= 360.0F - arc / 2.0F
                  || entityRelativeAngle <= -360.0F + arc / 2.0F
            )
            && !this.m_7307_(entityHit)
            && !(entityHit instanceof Ignis_Entity)) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entityHit.m_6469_(damagesource, (float)this.m_21133_(Attributes.f_22281_) + entityHit.m_21233_() * 0.1F);
            if (entityHit.m_21275_(damagesource) && entityHit instanceof Player player && shieldbreakticks > 0) {
               this.disableShield(player, shieldbreakticks);
            }

            if (flag && !entityHit.m_6095_().m_204039_(ModTag.IGNIS_CANT_POKE) && entityHit.m_6084_() && this.m_20197_().isEmpty()) {
               if (entityHit.m_6144_()) {
                  entityHit.m_20260_(false);
               }

               if (!this.f_19853_.f_46443_) {
                  entityHit.m_7998_(this, true);
               }

               AnimationHandler.INSTANCE.sendAnimationMessage(this, POKED_ATTACK);
            }
         }
      }
   }

   public void m_7332_(Entity p_20312_) {
      this.m_19956_(p_20312_, Entity::m_6034_);
   }

   public void m_19956_(Entity passenger, MoveFunction moveFunc) {
      if (this.m_20363_(passenger)) {
         int tick = 5;
         if (this.getAnimation() == POKED_ATTACK) {
            tick = this.getAnimationTick();
            if (this.getAnimationTick() == 46) {
               passenger.m_8127_();
            }
         }

         this.f_20885_ = this.f_20883_;
         float radius = 4.0F;
         float angle = (float) (Math.PI / 180.0) * this.f_20883_;
         double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         double extraY = tick < 10 ? 0.0 : (double)(0.2F * (float)Mth.m_14045_(tick - 10, 0, 15));
         moveFunc.m_20372_(passenger, this.m_20185_() + extraX, this.m_20186_() + extraY + 1.2F, this.m_20189_() + extraZ);
         if ((tick - 10) % 4 == 0 && passenger instanceof LivingEntity living) {
            boolean flag = living.m_6469_(DamageSource.m_19370_(this), 4.0F + living.m_21233_() * 0.02F);
            if (flag) {
               MobEffectInstance effectinstance1 = living.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               int i = 1;
               if (effectinstance1 != null) {
                  i += effectinstance1.m_19564_();
                  living.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
               } else {
                  i--;
               }

               i = Mth.m_14045_(i, 0, 4);
               MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), 240, i, false, true, true);
               living.m_7292_(effectinstance);
               this.m_5634_(2.0F * (float)CMConfig.IgnisHealingMultiplier * (float)(i + 1));
            }
         }
      }
   }

   public boolean shouldRiderSit() {
      return false;
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   private void Flameswing() {
      Vec3 bladePos = this.socketPosArray[0];
      int Density = 4;
      float Randomness = 0.5F;
      double length = this.prevBladePos.m_82546_(bladePos).m_82553_();
      int numClouds = (int)Math.floor(2.0 * length);

      for (int i = 0; i < numClouds; i++) {
         double x = this.prevBladePos.f_82479_ + (double)i * (bladePos.f_82479_ - this.prevBladePos.f_82479_) / (double)numClouds;
         double y = this.prevBladePos.f_82480_ + (double)i * (bladePos.f_82480_ - this.prevBladePos.f_82480_) / (double)numClouds;
         double z = this.prevBladePos.f_82481_ + (double)i * (bladePos.f_82481_ - this.prevBladePos.f_82481_) / (double)numClouds;

         for (int j = 0; j < Density; j++) {
            float xOffset = Randomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
            float yOffset = Randomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
            float zOffset = Randomness * (2.0F * this.f_19796_.m_188501_() - 1.0F);
            ParticleOptions type = this.getBossPhase() > 0 ? ParticleTypes.f_123745_ : ParticleTypes.f_123744_;
            this.f_19853_.m_7106_(type, x + (double)xOffset, y + (double)yOffset, z + (double)zOffset, 0.0, 0.0, 0.0);
         }
      }
   }

   private void SwingParticles() {
      if (this.f_19853_.f_46443_) {
         Vec3 bladePos = this.socketPosArray[0];
         if (this.getAnimation() == HORIZONTAL_SWING_ATTACK && this.getAnimationTick() > 27 && this.getAnimationTick() < 33) {
            this.Flameswing();
         }

         if (this.getAnimation() == SWING_ATTACK && this.getAnimationTick() > 15 && this.getAnimationTick() < 27) {
            this.Flameswing();
         }

         if (this.getAnimation() == SWING_ATTACK_BERSERK && this.getAnimationTick() > 12 && this.getAnimationTick() < 17) {
            this.Flameswing();
         }

         if (this.getAnimation() == HORIZONTAL_SWING_ATTACK_SOUL && this.getAnimationTick() > 24 && this.getAnimationTick() < 28) {
            this.Flameswing();
         }

         if (this.getAnimation() == SWING_ATTACK_SOUL && this.getAnimationTick() > 16 && this.getAnimationTick() < 19) {
            this.Flameswing();
         }

         if (this.getAnimation() == PHASE_3 && this.getAnimationTick() > 96 && this.getAnimationTick() < 100) {
            this.Flameswing();
         }

         if (this.getAnimation() == STRIKE && this.getAnimationTick() > 28 && this.getAnimationTick() < 33) {
            this.Flameswing();
         }

         if (this.getAnimation() == SWING_UPPERSLASH && this.getAnimationTick() > 23 && this.getAnimationTick() < 28) {
            this.Flameswing();
         }

         if (this.getAnimation() == HORIZONTAL_SMALL_SWING_ATTACK && this.getAnimationTick() > 7) {
            this.Flameswing();
         }

         if (this.getAnimation() == HORIZONTAL_SMALL_SWING_ALT_ATTACK2 && this.getAnimationTick() > 3) {
            this.Flameswing();
         }

         if (this.getAnimation() == SPIN_ATTACK && this.getAnimationTick() > 10 && this.getAnimationTick() < 18) {
            this.Flameswing();
         }

         if ((this.getAnimation() == REINFORCED_SMASH_IN_AIR || this.getAnimation() == REINFORCED_SMASH_IN_AIR_SOUL)
            && this.getAnimationTick() > 19
            && this.getAnimationTick() < 58) {
            this.Flameswing();
         }

         if (this.getAnimation() == SHIELD_BREAK_STRIKE && this.getAnimationTick() > 37 && this.getAnimationTick() < 49) {
            this.Flameswing();
         }

         if (this.getAnimation() == ULTIMATE_ATTACK && this.getAnimationTick() > 71 && this.getAnimationTick() < 74) {
            this.Flameswing();
         }

         if (this.getAnimation() == COMBO1
            && (
               this.getAnimationTick() > 16 && this.getAnimationTick() < 21
                  || this.getAnimationTick() > 36 && this.getAnimationTick() < 40
                  || this.getAnimationTick() > 60 && this.getAnimationTick() < 78
            )) {
            this.Flameswing();
         }

         if (this.getAnimation() == COMBO2
            && (
               this.getAnimationTick() > 59 && this.getAnimationTick() < 62
                  || this.getAnimationTick() > 74 && this.getAnimationTick() < 77
                  || this.getAnimationTick() > 107 && this.getAnimationTick() < 114
            )) {
            this.Flameswing();
         }

         this.prevBladePos = bladePos;
      }
   }

   private void ShieldSmashparticle(float radius, float vec, float math) {
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
            double motionX = this.m_217043_().m_188583_() * 0.07;
            double motionY = this.m_217043_().m_188583_() * 0.07;
            double motionZ = this.m_217043_().m_188583_() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
            double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
            double extraY = 0.3F;
            double extraZ = (double)(radius * Mth.m_14089_(angle));
            int hitX = Mth.m_14107_(this.m_20185_() + (double)vec * vecX + extraX);
            int hitY = Mth.m_14107_(this.m_20186_());
            int hitZ = Mth.m_14107_(this.m_20189_() + (double)vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.f_19853_.m_8055_(hit.m_7495_());
            if (block.m_60799_() != RenderShape.INVISIBLE) {
               this.f_19853_
                  .m_7106_(
                     new BlockParticleOption(ParticleTypes.f_123794_, block),
                     this.m_20185_() + (double)vec * vecX + extraX + (double)(f * math),
                     this.m_20186_() + extraY,
                     this.m_20189_() + (double)vec * vecZ + extraZ + (double)(f1 * math),
                     motionX,
                     motionY,
                     motionZ
                  );
            }
         }

         this.f_19853_
            .m_7106_(
               new RingParticle.RingData(0.0F, (float) (Math.PI / 2), 25, 1.0F, 1.0F, 1.0F, 1.0F, 25.0F, false, RingParticle.EnumRingBehavior.GROW),
               this.m_20185_() + (double)vec * vecX + (double)(f * math),
               this.m_20186_() + 0.3F,
               this.m_20189_() + (double)vec * vecZ + (double)(f1 * math),
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void ShieldExplode(float radius, float math, float y) {
      if (!this.f_19853_.f_46443_) {
         float angle = (float) (Math.PI / 180.0) * this.f_20883_;
         float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
         float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
         double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         this.f_19853_
            .m_46511_(
               this,
               this.m_20185_() + extraX + (double)(f * math),
               this.m_20186_() + (double)y,
               this.m_20189_() + extraZ + (double)(f1 * math),
               2.0F,
               BlockInteraction.NONE
            );
      }
   }

   private void ShieldSmashDamage(
      float spreadarc, int distance, float mxy, float vec, boolean grab, int shieldbreakticks, float damage, float hpdamage, float airborne
   ) {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      int hitY = Mth.m_14107_(this.m_20191_().f_82289_ - 0.5);
      double spread = Math.PI * (double)spreadarc;
      int arcLen = Mth.m_14165_((double)distance * spread);
      double minY = this.m_20186_() - 1.0;
      double maxY = this.m_20186_() + (double)mxy;

      for (int i = 0; i < arcLen; i++) {
         double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = this.m_20185_() + vx * (double)distance + (double)vec * Math.cos((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         double pz = this.m_20189_() + vz * (double)distance + (double)vec * Math.sin((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         float factor = 1.0F - (float)distance / 12.0F;
         int hitX = Mth.m_14107_(px);
         int hitZ = Mth.m_14107_(pz);
         BlockPos pos = new BlockPos(hitX, hitY, hitZ);
         BlockState block = this.f_19853_.m_8055_(pos);
         int maxDepth = 256;

         for (int depthCount = 0; depthCount < maxDepth && block.m_60799_() != RenderShape.MODEL; depthCount++) {
            pos = pos.m_7495_();
            block = this.f_19853_.m_8055_(pos);
         }

         if (block.m_60799_() != RenderShape.MODEL) {
            block = Blocks.f_50016_.m_49966_();
         }

         Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
            this.f_19853_, (double)hitX + 0.5, (double)hitY + 1.0, (double)hitZ + 0.5, block, 10
         );
         fallingBlockEntity.m_5997_(0.0, 0.2 + this.m_217043_().m_188583_() * 0.15, 0.0);
         this.f_19853_.m_7967_(fallingBlockEntity);
         if (!this.f_19853_.f_46443_ && block.m_204336_(ModTag.IGNIS_CAN_DESTROY_CRACKED_BLOCK)) {
            if (CMConfig.IgnisBlockBreaking) {
               this.f_19853_.m_46953_(pos, false, this);
            } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
               this.f_19853_.m_46953_(pos, false, this);
            }
         }

         AABB selection = new AABB(px - 0.5, minY, pz - 0.5, px + 0.5, maxY, pz + 0.5);

         for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
            if (!this.m_7307_(entity) && !(entity instanceof Ignis_Entity) && entity != this) {
               DamageSource damagesource = DamageSource.m_19370_(this);
               boolean flag = entity.m_6469_(
                  damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage + (double)(entity.m_21233_() * hpdamage))
               );
               if (entity.m_21275_(damagesource) && entity instanceof Player) {
                  Player player = (Player)entity;
                  if (shieldbreakticks > 0) {
                     this.disableShield(player, shieldbreakticks);
                  }
               }

               if (flag) {
                  if (grab) {
                     double magnitude = -4.0;
                     double x = vx * (double)(1.0F - factor) * magnitude;
                     double y = 0.0;
                     if (entity.m_20096_()) {
                        y += 0.15;
                     }

                     double z = vz * (double)(1.0F - factor) * magnitude;
                     entity.m_20256_(entity.m_20184_().m_82520_(x, y, z));
                  } else {
                     entity.m_20256_(entity.m_20184_().m_82520_(0.0, (double)(airborne * (float)distance) + this.f_19853_.f_46441_.m_188500_() * 0.15, 0.0));
                  }
               }
            }
         }
      }
   }

   private void UltimateAttack(int distance, float mxy, float math, int shieldbreakticks, float damage, float hpdamage, float airborne) {
      int hitY = Mth.m_14107_(this.m_20191_().f_82289_ - 0.5);
      double minY = this.m_20186_() - 2.0;
      double maxY = this.m_20186_() + (double)mxy;
      float angle = (float) (Math.PI / 180.0) * this.f_20883_;
      float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
      double extraX = (double)((float)distance * Mth.m_14031_((float)(Math.PI + (double)angle)));
      double extraZ = (double)((float)distance * Mth.m_14089_(angle));
      double px = this.m_20185_() + extraX + (double)(f * math);
      double pz = this.m_20189_() + extraZ + (double)(f1 * math);
      int hitX = Mth.m_14107_(px);
      int hitZ = Mth.m_14107_(pz);
      BlockPos pos = new BlockPos(hitX, hitY, hitZ);
      BlockState block = this.f_19853_.m_8055_(pos);
      int maxDepth = 30;

      for (int depthCount = 0; depthCount < maxDepth && block.m_60799_() != RenderShape.MODEL; depthCount++) {
         pos = pos.m_7495_();
         block = this.f_19853_.m_8055_(pos);
      }

      if (block.m_60799_() != RenderShape.MODEL) {
         block = Blocks.f_50016_.m_49966_();
      }

      Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
         this.f_19853_, (double)hitX + 0.5, (double)hitY + 1.0, (double)hitZ + 0.5, block, 10
      );
      fallingBlockEntity.m_5997_(0.0, 0.2 + this.m_217043_().m_188583_() * 0.15, 0.0);
      this.f_19853_.m_7967_(fallingBlockEntity);
      if (!this.f_19853_.f_46443_ && block.m_204336_(ModTag.IGNIS_CAN_DESTROY_CRACKED_BLOCK)) {
         if (CMConfig.IgnisBlockBreaking) {
            this.f_19853_.m_46953_(pos, false, this);
         } else if (ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            this.f_19853_.m_46953_(pos, false, this);
         }
      }

      AABB selection = new AABB(px - 0.5, minY, pz - 0.5, px + 0.5, maxY, pz + 0.5);

      for (LivingEntity entity : this.f_19853_.m_45976_(LivingEntity.class, selection)) {
         if (!this.m_7307_(entity) && !(entity instanceof Ignis_Entity) && entity != this) {
            DamageSource damagesource = DamageSource.m_19370_(this);
            boolean flag = entity.m_6469_(damagesource, (float)(this.m_21133_(Attributes.f_22281_) * (double)damage + (double)(entity.m_21233_() * hpdamage)));
            if (entity.m_21275_(damagesource) && entity instanceof Player) {
               Player player = (Player)entity;
               if (shieldbreakticks > 0) {
                  this.disableShield(player, shieldbreakticks);
               }
            }

            if (flag) {
               entity.m_20256_(entity.m_20184_().m_82520_(0.0, (double)airborne + this.f_19853_.f_46441_.m_188500_() * 0.15, 0.0));
            }
         }
      }
   }

   private void earthquakesound(float distance) {
      double theta = (double)this.f_20883_ * (Math.PI / 180.0);
      double vecX = Math.cos(++theta);
      double vecZ = Math.sin(theta);
      this.f_19853_
         .m_7785_(
            this.m_20185_() + (double)distance * vecX,
            this.m_20186_(),
            this.m_20189_() + (double)distance * vecZ,
            SoundEvents.f_12513_,
            this.m_5720_(),
            1.5F,
            0.8F + this.m_217043_().m_188501_() * 0.1F,
            false
         );
   }

   private void StrikeParticle(float spreadarc, int distance, float vec) {
      double perpFacing = (double)this.f_20883_ * (Math.PI / 180.0);
      double facingAngle = perpFacing + (Math.PI / 2);
      double spread = Math.PI * (double)spreadarc;
      int arcLen = Mth.m_14165_((double)distance * spread);

      for (int i = 0; i < arcLen; i++) {
         double theta = ((double)i / ((double)arcLen - 1.0) - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double vy = (double)Mth.m_14116_((float)(vx * (double)distance * vx * (double)distance + vz * (double)distance * vz * (double)distance));
         double px = this.m_20185_() + vx * (double)distance + (double)vec * Math.cos((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         double pz = this.m_20189_() + vz * (double)distance + (double)vec * Math.sin((double)(this.f_20883_ + 90.0F) * Math.PI / 180.0);
         if (this.f_19853_.f_46443_ && this.f_19797_ % 2 == 0) {
            for (int i1 = 0; i1 < 80 + this.f_19796_.m_188503_(12); i1++) {
               double motionX = 0.2 * Mth.m_14139_(1.0, vx * (double)distance + 3.0, vx * (double)distance);
               double motionY = 0.2 * Mth.m_14139_(1.5, vy * 0.1, vy * 0.1);
               double motionZ = 0.2 * Mth.m_14139_(1.0, vz * (double)distance + 3.0, vz * (double)distance);
               double spreads = 10.0 + this.m_217043_().m_188500_() * 2.5;
               double velocity = 0.5 + this.m_217043_().m_188500_() * 0.15;
               motionX += this.m_217043_().m_188583_() * 0.0075F * spreads;
               motionZ += this.m_217043_().m_188583_() * 0.0075F * spreads;
               motionX *= velocity;
               motionZ *= velocity;
               ParticleOptions type = this.getBossPhase() > 0 ? ParticleTypes.f_123745_ : ParticleTypes.f_123744_;
               this.f_19853_.m_7106_(type, px, this.m_20186_() + 1.3F, pz, motionX, motionY, motionZ);
            }
         }
      }
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_IGNIS) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (living != null) {
         CMWorldData worldData = CMWorldData.get(this.f_19853_, Level.f_46429_);
         if (worldData != null) {
            boolean prev = worldData.isIgnisDefeatedOnce();
            if (!prev) {
               worldData.setIgnisDefeatedOnce(true);
               if (this.f_19853_ instanceof ServerLevel serverLevel) {
                  serverLevel.m_8795_(EntitySelector.f_20408_)
                     .forEach(
                        serverPlayer -> serverPlayer.m_5661_(Component.m_237115_("entity.cataclysm.ignis.defeat_message").m_130940_(ChatFormatting.GOLD), true)
                     );
               }
            }
         }
      }
   }

   private void Sphereparticle(float height, float vec, float size) {
      if (this.f_19853_.f_46443_ && this.f_19797_ % 2 == 0) {
         double d0 = this.m_20185_();
         double d1 = this.m_20186_() + (double)height;
         double d2 = this.m_20189_();
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (float i = -size; i <= size; i++) {
            for (float j = -size; j <= size; j++) {
               for (float k = -size; k <= size; k++) {
                  double d3 = (double)j + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d4 = (double)i + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d5 = (double)k + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.f_19796_.m_188583_() * 0.05;
                  ParticleOptions type = this.getBossPhase() > 0 ? ParticleTypes.f_123745_ : ParticleTypes.f_123744_;
                  this.f_19853_.m_7106_(type, d0 + (double)vec * vecX, d1, d2 + (double)vec * vecZ, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2.0F - 1.0F;
                  }
               }
            }
         }
      }
   }

   private void PatternParticle(float height, float vec, float size, boolean blue) {
      if (this.f_19853_.f_46443_ && this.f_19797_ % 2 == 0) {
         double d0 = this.m_20185_();
         double d1 = this.m_20186_() + (double)height;
         double d2 = this.m_20189_();
         double theta = (double)this.f_20883_ * (Math.PI / 180.0);
         double vecX = Math.cos(++theta);
         double vecZ = Math.sin(theta);

         for (float i = -size; i <= size; i++) {
            for (float j = -size; j <= size; j++) {
               for (float k = -size; k <= size; k++) {
                  double d3 = (double)j + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d4 = (double)i + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d5 = (double)k + (this.f_19796_.m_188500_() - this.f_19796_.m_188500_()) * 0.5;
                  double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.f_19796_.m_188583_() * 0.05;
                  ParticleOptions type = blue ? ParticleTypes.f_123745_ : ParticleTypes.f_123744_;
                  this.f_19853_.m_7106_(type, d0 + (double)vec * vecX, d1, d2 + (double)vec * vecZ, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2.0F - 1.0F;
                  }
               }
            }
         }
      }
   }

   private void Phase_Transition(int dist, float damage, float hpdamage, int firetime, int brandticks) {
      if (this.getAnimationTick() % 2 == 0) {
         int distance = this.getAnimationTick() / 2 - dist;

         for (LivingEntity entityHit : this.getEntityLivingBaseNearby((double)distance, (double)distance, (double)distance, (double)distance)) {
            if (!this.m_7307_(entityHit) && !(entityHit instanceof Ignis_Entity) && entityHit != this) {
               boolean flag = entityHit.m_6469_(
                  DamageSource.m_19367_(this, this), (float)(this.m_21133_(Attributes.f_22281_) * (double)damage + (double)(entityHit.m_21233_() * hpdamage))
               );
               if (flag) {
                  entityHit.m_20254_(firetime);
                  if (brandticks > 0) {
                     MobEffectInstance effectinstance1 = entityHit.m_21124_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
                     int i = 1;
                     if (effectinstance1 != null) {
                        i += effectinstance1.m_19564_();
                        entityHit.m_6234_((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get());
                     } else {
                        i--;
                     }

                     i = Mth.m_14045_(i, 0, 4);
                     MobEffectInstance effectinstance = new MobEffectInstance((MobEffect)ModEffect.EFFECTBLAZING_BRAND.get(), brandticks, i, false, true, true);
                     entityHit.m_7292_(effectinstance);
                  }
               }
            }
         }
      }
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public SpawnGroupData m_6518_(
      ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType p_29680_, @Nullable SpawnGroupData p_29681_, @Nullable CompoundTag p_29682_
   ) {
      return super.m_6518_(p_29678_, p_29679_, p_29680_, p_29681_, p_29682_);
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.IGNIS_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.IGNIS_DEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.IGNIS_MUSIC.get();
   }

   protected BodyRotationControl m_7560_() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation m_6037_(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossInfo.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossInfo.m_6539_(player);
   }

   private boolean shouldFollowUp(float Range) {
      LivingEntity target = this.m_5448_();
      if (target != null && target.m_6084_()) {
         Vec3 targetMoveVec = target.m_20184_();
         Vec3 betweenEntitiesVec = this.m_20182_().m_82546_(target.m_20182_());
         boolean targetComingCloser = targetMoveVec.m_82526_(betweenEntitiesVec) > 0.0;
         return this.m_20270_(target) < Range || this.m_20270_(target) < 5.0F + Range && targetComingCloser;
      } else {
         return false;
      }
   }

   private void shootAbyssFireball(Vec3 shotAt, int timer) {
      shotAt = shotAt.m_82524_(-this.m_146908_() * (float) (Math.PI / 180.0));
      Ignis_Abyss_Fireball_Entity shot = new Ignis_Abyss_Fireball_Entity(this.f_19853_, this);
      shot.m_6034_(
         this.m_20185_() - (double)(this.m_20205_() + 1.0F) * 0.15 * (double)Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0)),
         this.m_20186_() + 1.0,
         this.m_20189_() + (double)(this.m_20205_() + 1.0F) * 0.15 * (double)Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0))
      );
      double d0 = shotAt.f_82479_;
      double d1 = shotAt.f_82480_;
      double d2 = shotAt.f_82481_;
      float f = Mth.m_14116_((float)(d0 * d0 + d2 * d2)) * 0.35F;
      shot.m_6686_(d0, d1 + (double)f, d2, 0.25F, 3.0F);
      shot.setUp(timer);
      this.f_19853_.m_7967_(shot);
   }

   private void shootFireball(Vec3 shotAt, int timer) {
      shotAt = shotAt.m_82524_(-this.m_146908_() * (float) (Math.PI / 180.0));
      Ignis_Fireball_Entity shot = new Ignis_Fireball_Entity(this.f_19853_, this);
      shot.m_6034_(
         this.m_20185_() - (double)(this.m_20205_() + 1.0F) * 0.15 * (double)Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0)),
         this.m_20186_() + 1.0,
         this.m_20189_() + (double)(this.m_20205_() + 1.0F) * 0.15 * (double)Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0))
      );
      double d0 = shotAt.f_82479_;
      double d1 = shotAt.f_82480_;
      double d2 = shotAt.f_82481_;
      float f = Mth.m_14116_((float)(d0 * d0 + d2 * d2)) * 0.35F;
      shot.m_6686_(d0, d1 + (double)f, d2, 0.25F, 3.0F);
      shot.setUp(timer);
      if (this.getBossPhase() > 0) {
         shot.setSoul(true);
      }

      this.f_19853_.m_7967_(shot);
   }

   private void bladeFireball(float radius, float math, float Y, int timer) {
      Ignis_Fireball_Entity shot = new Ignis_Fireball_Entity(this.f_19853_, this);
      float angle = (float) (Math.PI / 180.0) * this.f_20883_;
      float f = Mth.m_14089_(this.m_146908_() * (float) (Math.PI / 180.0));
      float f1 = Mth.m_14031_(this.m_146908_() * (float) (Math.PI / 180.0));
      double extraX = (double)(radius * Mth.m_14031_((float)(Math.PI + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      shot.m_6034_(this.m_20185_() + (double)(f * math) + extraX, this.m_20186_() + (double)Y, this.m_20189_() + (double)(f1 * math) + extraZ);
      shot.setUp(timer);
      if (this.getBossPhase() > 0) {
         shot.setSoul(true);
      }

      this.f_19853_.m_7967_(shot);
   }

   private void spawnFlameStrike(double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, float radius, boolean soul) {
      BlockPos blockpos = new BlockPos(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.m_7495_();
         BlockState blockstate = this.f_19853_.m_8055_(blockpos1);
         if (blockstate.m_60783_(this.f_19853_, blockpos1, Direction.UP)) {
            if (!this.f_19853_.m_46859_(blockpos)) {
               BlockState blockstate1 = this.f_19853_.m_8055_(blockpos);
               VoxelShape voxelshape = blockstate1.m_60812_(this.f_19853_, blockpos);
               if (!voxelshape.m_83281_()) {
                  d0 = voxelshape.m_83297_(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.m_7495_();
      } while (blockpos.m_123342_() >= Mth.m_14107_(minY) - 1);

      if (flag) {
         this.f_19853_
            .m_7967_(
               new Flame_Strike_Entity(
                  this.f_19853_, x, (double)blockpos.m_123342_() + d0, z, rotation, duration, wait, delay, radius, soul ? 8.0F : 6.0F, 6.0F, soul, this
               )
            );
      }
   }

   class Air_Smash extends SimpleAnimationGoal<Ignis_Entity> {
      public Air_Smash(Ignis_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if (target != null) {
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         }

         if (Ignis_Entity.this.getAnimationTick() == 19) {
            if (target != null) {
               Ignis_Entity.this.m_20334_(
                  (target.m_20185_() - Ignis_Entity.this.m_20185_()) * 0.15, 1.3, (target.m_20189_() - Ignis_Entity.this.m_20189_()) * 0.15
               );
            } else {
               Ignis_Entity.this.m_20334_(0.0, 1.4, 0.0);
            }
         }

         if (Ignis_Entity.this.getAnimationTick() > 19 && Ignis_Entity.this.m_20096_()) {
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.SMASH);
         }
      }
   }

   class Body_Check_Attack extends AnimationGoal<Ignis_Entity> {
      public Body_Check_Attack(Ignis_Entity entity) {
         super(entity);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ignis_Entity.BODY_CHECK_ATTACK1
            || animation == Ignis_Entity.BODY_CHECK_ATTACK2
            || animation == Ignis_Entity.BODY_CHECK_ATTACK3
            || animation == Ignis_Entity.BODY_CHECK_ATTACK4
            || animation == Ignis_Entity.BODY_CHECK_ATTACK_SOUL1
            || animation == Ignis_Entity.BODY_CHECK_ATTACK_SOUL2
            || animation == Ignis_Entity.BODY_CHECK_ATTACK_SOUL3
            || animation == Ignis_Entity.BODY_CHECK_ATTACK_SOUL4;
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK_SOUL1
            || Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK_SOUL2
            || Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK_SOUL3
            || Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK_SOUL4) {
            if (Ignis_Entity.this.getAnimationTick() < 21 && target != null) {
               Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_20883_);
            } else {
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
            }

            if (Ignis_Entity.this.getAnimationTick() == 16 && target != null) {
               Ignis_Entity.this.m_20334_(
                  (target.m_20185_() - Ignis_Entity.this.m_20185_()) * 0.4F, 0.0, (target.m_20189_() - Ignis_Entity.this.m_20189_()) * 0.4F
               );
            }
         }

         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK1
            || Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK2
            || Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK3
            || Ignis_Entity.this.getAnimation() == Ignis_Entity.BODY_CHECK_ATTACK4) {
            if (Ignis_Entity.this.getAnimationTick() < 25 && target != null) {
               Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_20883_);
            } else {
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
            }

            if (Ignis_Entity.this.getAnimationTick() == 20 && target != null) {
               Ignis_Entity.this.m_20334_(
                  (target.m_20185_() - Ignis_Entity.this.m_20185_()) * 0.25, 0.0, (target.m_20189_() - Ignis_Entity.this.m_20189_()) * 0.25
               );
            }
         }
      }
   }

   class Combo1 extends SimpleAnimationGoal<Ignis_Entity> {
      public Combo1(Ignis_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         float f1 = (float)Math.cos(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         float f2 = (float)Math.sin(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if ((Ignis_Entity.this.getAnimationTick() >= 20 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 62 || Ignis_Entity.this.getAnimationTick() <= 44 || target == null)) {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         } else {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         }

         if (Ignis_Entity.this.getAnimationTick() == 15 || Ignis_Entity.this.getAnimationTick() == 36 || Ignis_Entity.this.getAnimationTick() == 71) {
            if (target != null) {
               float r = Ignis_Entity.this.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 5.0F);
               Ignis_Entity.this.m_5997_((double)f1 * 0.35 * (double)r, 0.0, (double)f2 * 0.35 * (double)r);
            } else {
               Ignis_Entity.this.m_5997_((double)f1, 0.0, (double)f2);
            }
         }

         if (Ignis_Entity.this.getAnimationTick() == 84 && target != null) {
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.COMBO2);
         }
      }
   }

   class Combo2 extends SimpleAnimationGoal<Ignis_Entity> {
      private final int look1;
      private final float sensing;
      private final int charge;
      private final float motionx;
      private final float motionz;
      public double prevX;
      public double prevZ;
      private int newX;
      private int newZ;

      public Combo2(Ignis_Entity entity, Animation animation, int look1, float sensing, int charge, float motionx, float motionz) {
         super(entity, animation);
         this.look1 = look1;
         this.sensing = sensing;
         this.charge = charge;
         this.motionx = motionx;
         this.motionz = motionz;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8056_() {
         super.m_8056_();
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if (target != null) {
            this.prevX = target.m_20185_();
            this.prevZ = target.m_20189_();
         }
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if ((Ignis_Entity.this.getAnimationTick() >= this.look1 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 59 || Ignis_Entity.this.getAnimationTick() <= 43 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 74 || Ignis_Entity.this.getAnimationTick() <= 61 || target == null)) {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         } else {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_20883_);
         }

         if (Ignis_Entity.this.getAnimationTick() == this.charge - 1 && target != null) {
            double x = target.m_20185_();
            double z = target.m_20189_();
            double vx = (x - this.prevX) / (double)this.charge;
            double vz = (z - this.prevZ) / (double)this.charge;
            this.newX = Mth.m_14107_(x + vx * (double)this.sensing);
            this.newZ = Mth.m_14107_(z + vz * (double)this.sensing);
         }

         if (Ignis_Entity.this.getAnimationTick() == this.charge && target != null) {
            Ignis_Entity.this.m_20334_(
               ((double)this.newX - Ignis_Entity.this.m_20185_()) * (double)this.motionx,
               0.0,
               ((double)this.newZ - Ignis_Entity.this.m_20189_()) * (double)this.motionz
            );
         }

         float f1 = (float)Math.cos(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         float f2 = (float)Math.sin(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         if (Ignis_Entity.this.getAnimationTick() == 55 || Ignis_Entity.this.getAnimationTick() == 70) {
            if (target != null) {
               if (Ignis_Entity.this.m_20270_(target) > 3.5F) {
                  Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
               }
            } else {
               Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
            }
         }
      }
   }

   public static enum Crackiness {
      NONE(1.0F),
      LOW(0.3F),
      MEDIUM(0.2F),
      HIGH(0.1F);

      private static final List<Ignis_Entity.Crackiness> BY_DAMAGE = Stream.of(values())
         .sorted(Comparator.comparingDouble(p_28904_ -> (double)p_28904_.fraction))
         .collect(ImmutableList.toImmutableList());
      private final float fraction;

      private Crackiness(float p_28900_) {
         this.fraction = p_28900_;
      }

      public static Ignis_Entity.Crackiness byFraction(float p_28902_) {
         for (Ignis_Entity.Crackiness ignis$crackiness : BY_DAMAGE) {
            if (p_28902_ < ignis$crackiness.fraction) {
               return ignis$crackiness;
            }
         }

         return NONE;
      }
   }

   class Earth_Shudders extends SimpleAnimationGoal<Ignis_Entity> {
      public Earth_Shudders(Ignis_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         Ignis_Entity.this.m_20334_(0.0, Ignis_Entity.this.m_20184_().f_82480_, 0.0);
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if ((Ignis_Entity.this.getAnimationTick() >= 31 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 73 || Ignis_Entity.this.getAnimationTick() <= 45 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 117 || Ignis_Entity.this.getAnimationTick() <= 89 || target == null)) {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         } else {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         }
      }
   }

   class Hornzontal_Small_SwingGoal extends AnimationGoal<Ignis_Entity> {
      private final int look1;
      private final int look2;
      private final int look3;
      private final int follow_through_tick;

      public Hornzontal_Small_SwingGoal(Ignis_Entity entity, int look1, int look2, int look3, int follow_through_tick) {
         super(entity);
         this.look1 = look1;
         this.look2 = look2;
         this.look3 = look3;
         this.follow_through_tick = follow_through_tick;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ignis_Entity.HORIZONTAL_SMALL_SWING_ALT_ATTACK2
            || animation == Ignis_Entity.HORIZONTAL_SMALL_SWING_ATTACK
            || animation == Ignis_Entity.SPIN_ATTACK;
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         float f1 = (float)Math.cos(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         float f2 = (float)Math.sin(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.HORIZONTAL_SMALL_SWING_ATTACK) {
            if (Ignis_Entity.this.getAnimationTick() < this.look1 && target != null) {
               Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
            } else {
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
            }

            if (Ignis_Entity.this.getAnimationTick() == 14) {
               if (target != null) {
                  if (Ignis_Entity.this.m_20270_(target) > 3.5F) {
                     Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
                  }
               } else {
                  Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
               }
            }

            if (Ignis_Entity.this.Combo && Ignis_Entity.this.getAnimationTick() == this.follow_through_tick) {
               Ignis_Entity.this.Combo = false;
               AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.HORIZONTAL_SMALL_SWING_ALT_ATTACK2);
            }
         }

         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.HORIZONTAL_SMALL_SWING_ALT_ATTACK2) {
            if (Ignis_Entity.this.getAnimationTick() < this.look2 && target != null) {
               Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
            } else {
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
            }

            if (Ignis_Entity.this.getAnimationTick() == 10) {
               if (target != null) {
                  if (Ignis_Entity.this.m_20270_(target) > 3.5F) {
                     Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
                  }
               } else {
                  Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
               }
            }

            if (Ignis_Entity.this.getAnimationTick() == this.follow_through_tick && Ignis_Entity.this.CanSpin >= 2) {
               Ignis_Entity.this.CanSpin = 0;
               AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.SPIN_ATTACK);
            }
         }

         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.SPIN_ATTACK) {
            if (Ignis_Entity.this.getAnimationTick() < this.look3 && target != null) {
               Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
               Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
            } else {
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
            }

            if (Ignis_Entity.this.getAnimationTick() == 10) {
               Ignis_Entity.this.m_5997_((double)f1 * 1.5, 0.0, (double)f2 * 1.5);
            }
         }
      }
   }

   class Hornzontal_SwingGoal extends SimpleAnimationGoal<Ignis_Entity> {
      private final int look1;
      private final int look2;
      private final int charge;
      private final int bodycheck;

      public Hornzontal_SwingGoal(Ignis_Entity entity, Animation animation, int look1, int look2, int charge, int bodycheck) {
         super(entity, animation);
         this.look1 = look1;
         this.look2 = look2;
         this.charge = charge;
         this.bodycheck = bodycheck;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if ((Ignis_Entity.this.getAnimationTick() >= this.look1 || target == null) && (Ignis_Entity.this.getAnimationTick() <= this.look2 || target == null)) {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         } else {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         }

         if (Ignis_Entity.this.getAnimationTick() == this.charge) {
            float f1 = (float)Math.cos(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
            float f2 = (float)Math.sin(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
            if (target != null) {
               float r = Ignis_Entity.this.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 10.0F);
               Ignis_Entity.this.m_5997_((double)f1 * 0.3 * (double)r, 0.0, (double)f2 * 0.3 * (double)r);
            }
         }

         if (Ignis_Entity.this.getAnimationTick() == this.bodycheck
            && Ignis_Entity.this.shouldFollowUp(3.5F)
            && Ignis_Entity.this.f_19796_.m_188503_(3) == 0
            && Ignis_Entity.this.body_check_cooldown <= 0) {
            Ignis_Entity.this.body_check_cooldown = 200;
            Animation bodycheck = Ignis_Entity.this.getBossPhase() > 0 ? Ignis_Entity.BODY_CHECK_ATTACK_SOUL2 : Ignis_Entity.BODY_CHECK_ATTACK2;
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, bodycheck);
         }
      }
   }

   class PokeGoal extends SimpleAnimationGoal<Ignis_Entity> {
      private final int look1;
      private final int look2;
      private final int charge;
      private final int bodycheck;
      private final int motion1;
      private final int motion2;

      public PokeGoal(Ignis_Entity entity, Animation animation, int look1, int look2, int charge, int bodycheck, int motion1, int motion2) {
         super(entity, animation);
         this.look1 = look1;
         this.look2 = look2;
         this.charge = charge;
         this.bodycheck = bodycheck;
         this.motion1 = motion1;
         this.motion2 = motion2;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         float f1 = (float)Math.cos(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         float f2 = (float)Math.sin(Math.toRadians((double)(Ignis_Entity.this.m_146908_() + 90.0F)));
         if ((Ignis_Entity.this.getAnimationTick() >= this.look1 || target == null) && (Ignis_Entity.this.getAnimationTick() <= this.look2 || target == null)) {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         } else {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_20883_);
         }

         if (Ignis_Entity.this.getAnimationTick() == this.charge) {
            if (target != null) {
               float r = Ignis_Entity.this.m_20270_(target);
               r = Mth.m_14036_(r, 0.0F, 15.0F);
               Ignis_Entity.this.m_5997_((double)f1 * 0.3 * (double)r, 0.0, (double)f2 * 0.3 * (double)r);
            } else {
               Ignis_Entity.this.m_5997_((double)f1, 0.0, (double)f2);
            }
         }

         if (Ignis_Entity.this.getAnimationTick() == this.bodycheck
            && Ignis_Entity.this.shouldFollowUp(3.0F)
            && Ignis_Entity.this.f_19796_.m_188503_(2) == 0
            && Ignis_Entity.this.body_check_cooldown <= 0) {
            Ignis_Entity.this.body_check_cooldown = 200;
            Animation bodycheck = Ignis_Entity.this.getBossPhase() > 0 ? Ignis_Entity.BODY_CHECK_ATTACK_SOUL4 : Ignis_Entity.BODY_CHECK_ATTACK4;
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, bodycheck);
         }

         if (Ignis_Entity.this.getAnimationTick() < this.motion1 || Ignis_Entity.this.getAnimationTick() > this.motion2) {
            Ignis_Entity.this.m_20334_(0.0, Ignis_Entity.this.m_20184_().f_82480_, 0.0);
         }
      }
   }

   class Poked extends SimpleAnimationGoal<Ignis_Entity> {
      public Poked(Ignis_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if (target != null) {
            Ignis_Entity.this.m_21563_().m_24960_(target, 20.0F, 20.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         }

         Ignis_Entity.this.m_20334_(0.0, Ignis_Entity.this.m_20184_().f_82480_, 0.0);
      }
   }

   class Reinforced_Air_Smash extends AnimationGoal<Ignis_Entity> {
      public Reinforced_Air_Smash(Ignis_Entity entity) {
         super(entity);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == Ignis_Entity.REINFORCED_SMASH_IN_AIR_SOUL || animation == Ignis_Entity.REINFORCED_SMASH_IN_AIR;
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if ((Ignis_Entity.this.getAnimationTick() >= 25 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 55 || Ignis_Entity.this.getAnimationTick() <= 36 || target == null)
            && (Ignis_Entity.this.getAnimationTick() >= 85 || Ignis_Entity.this.getAnimationTick() <= 66 || target == null)) {
            if (Ignis_Entity.this.getAnimationTick() >= 83 && target != null) {
               Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
            } else {
               Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
            }
         } else {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         }

         if (Ignis_Entity.this.getAnimationTick() == 84) {
            if (target != null) {
               Ignis_Entity.this.m_20334_(
                  (target.m_20185_() - Ignis_Entity.this.m_20185_()) * 0.15, 1.3, (target.m_20189_() - Ignis_Entity.this.m_20189_()) * 0.15
               );
            } else {
               Ignis_Entity.this.m_20334_(0.0, 1.8, 0.0);
            }
         }

         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.REINFORCED_SMASH_IN_AIR
            && Ignis_Entity.this.getAnimationTick() > 84
            && Ignis_Entity.this.m_20096_()) {
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.REINFORCED_SMASH);
         }

         if (Ignis_Entity.this.getAnimation() == Ignis_Entity.REINFORCED_SMASH_IN_AIR_SOUL
            && Ignis_Entity.this.getAnimationTick() > 84
            && Ignis_Entity.this.m_20096_()) {
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.REINFORCED_SMASH_SOUL);
         }
      }
   }

   class Shield_Smash extends SimpleAnimationGoal<Ignis_Entity> {
      public Shield_Smash(Ignis_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if (Ignis_Entity.this.getAnimationTick() < 34 && target != null) {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         } else {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         }

         Ignis_Entity.this.m_20334_(0.0, Ignis_Entity.this.m_20184_().f_82480_, 0.0);
         if (Ignis_Entity.this.getAnimationTick() == 45
            && Ignis_Entity.this.shouldFollowUp(4.0F)
            && Ignis_Entity.this.f_19796_.m_188503_(3) == 0
            && Ignis_Entity.this.body_check_cooldown <= 0) {
            Ignis_Entity.this.body_check_cooldown = 200;
            Animation bodycheck = Ignis_Entity.this.getBossPhase() > 0 ? Ignis_Entity.BODY_CHECK_ATTACK_SOUL3 : Ignis_Entity.BODY_CHECK_ATTACK3;
            AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, bodycheck);
         }
      }
   }

   class Swing_Attack_Goal extends SimpleAnimationGoal<Ignis_Entity> {
      private final int look1;
      private final int follow_through_tick;

      public Swing_Attack_Goal(Ignis_Entity entity, Animation animation, int look1, int follow_through_tick) {
         super(entity, animation);
         this.look1 = look1;
         this.follow_through_tick = follow_through_tick;
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public void m_8037_() {
         LivingEntity target = Ignis_Entity.this.m_5448_();
         if (Ignis_Entity.this.getAnimationTick() < this.look1 && target != null) {
            Ignis_Entity.this.m_21563_().m_24960_(target, 30.0F, 30.0F);
            Ignis_Entity.this.m_21391_(target, 30.0F, 30.0F);
         } else {
            Ignis_Entity.this.m_146922_(Ignis_Entity.this.f_19859_);
         }

         if (Ignis_Entity.this.getAnimationTick() == this.follow_through_tick && Ignis_Entity.this.f_19796_.m_188503_(2) == 0 && target != null) {
            if (Ignis_Entity.this.m_20270_(target) <= 6.0F) {
               AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.SWING_UPPERSLASH);
            } else {
               AnimationHandler.INSTANCE.sendAnimationMessage(Ignis_Entity.this, Ignis_Entity.SWING_UPPERCUT);
            }
         }

         Ignis_Entity.this.m_20334_(0.0, Ignis_Entity.this.m_20184_().f_82480_, 0.0);
      }
   }
}
