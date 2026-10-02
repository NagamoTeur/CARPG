package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters;

import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AttackAniamtionGoal3;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Howitzer_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Wither_Missile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

public class The_Harbinger_Entity extends LLibrary_Boss_Monster implements RangedAttackMob, PowerableMob {
   public static final Animation DEATHLASER_ANIMATION = Animation.create(124);
   public static final Animation CHARGE_ANIMATION = Animation.create(45);
   public static final Animation DEATH_ANIMATION = Animation.create(144);
   public static final Animation LAUNCH_ANIAMATION = Animation.create(59);
   public static final Animation MISSILE_FIRE_ANIAMATION = Animation.create(118);
   public static final Animation MISSILE_FIRE_FAST_ANIAMATION = Animation.create(96);
   public static final Animation STUN_ANIAMATION = Animation.create(105);
   public static final int SKILL_COOLDOWN = 240;
   private static final EntityDataAccessor<Integer> FIRST_HEAD_TARGET = SynchedEntityData.m_135353_(The_Harbinger_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> SECOND_HEAD_TARGET = SynchedEntityData.m_135353_(
      The_Harbinger_Entity.class, EntityDataSerializers.f_135028_
   );
   private static final EntityDataAccessor<Integer> THIRD_HEAD_TARGET = SynchedEntityData.m_135353_(The_Harbinger_Entity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> OVERLOAD = SynchedEntityData.m_135353_(The_Harbinger_Entity.class, EntityDataSerializers.f_135028_);
   private static final List<EntityDataAccessor<Integer>> HEAD_TARGETS = ImmutableList.of(FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET);
   private static final EntityDataAccessor<Boolean> LASER_MODE = SynchedEntityData.m_135353_(The_Harbinger_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> ISCHARGE = SynchedEntityData.m_135353_(The_Harbinger_Entity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> IS_ACT = SynchedEntityData.m_135353_(The_Harbinger_Entity.class, EntityDataSerializers.f_135035_);
   public static final int MODE_CHANGE_COOLDOWN = 300;
   private final float[] xRotHeads = new float[2];
   private final float[] yRotHeads = new float[2];
   private final float[] xRotOHeads = new float[2];
   private final float[] yRotOHeads = new float[2];
   private final int[] nextHeadUpdate = new int[2];
   private final int[] idleHeadUpdates = new int[2];
   public float Laser_Mode_Progress;
   public float prev_Laser_Mode_Progress;
   public float deactivateProgress;
   public float prevdeactivateProgress;
   private int destroyBlocksTick;
   private int blockBreakCounter;
   private final CMBossInfoServer bossEvent = new CMBossInfoServer(this.m_5446_(), BossBarColor.RED, true, 4);
   private int mode_change_cooldown = 0;
   private int skill_cooldown = 160;
   private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = p_31504_ -> p_31504_.m_5789_()
         && !p_31504_.m_6095_().m_204039_(ModTag.TEAM_THE_HARBINGER);
   private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.m_148352_().m_26883_(20.0).m_26888_(LIVING_ENTITY_SELECTOR);

   public The_Harbinger_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.f_21364_ = 300;
      this.f_21342_ = new FlyingMoveControl(this, 10, false);
      setConfigattribute(this, CMConfig.HarbingerHealthMultiplier, CMConfig.HarbingerDamageMultiplier);
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      return 0;
   }

   public boolean m_6673_(DamageSource p_20122_) {
      return super.m_6673_(p_20122_) || p_20122_.m_146707_();
   }

   protected PathNavigation m_6037_(Level p_186262_) {
      FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_186262_);
      flyingpathnavigation.m_26440_(false);
      flyingpathnavigation.m_7008_(true);
      flyingpathnavigation.m_26443_(true);
      return flyingpathnavigation;
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         NO_ANIMATION,
         DEATHLASER_ANIMATION,
         CHARGE_ANIMATION,
         DEATH_ANIMATION,
         LAUNCH_ANIAMATION,
         MISSILE_FIRE_ANIAMATION,
         STUN_ANIAMATION,
         MISSILE_FIRE_FAST_ANIAMATION
      };
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new The_Harbinger_Entity.AwakenGoal());
      this.f_21345_.m_25352_(1, new The_Harbinger_Entity.DeathLaserGoal(this, DEATHLASER_ANIMATION));
      this.f_21345_.m_25352_(1, new The_Harbinger_Entity.ChargeGoal(this, CHARGE_ANIMATION));
      this.f_21345_.m_25352_(1, new The_Harbinger_Entity.LaunchGoal(this, LAUNCH_ANIAMATION));
      this.f_21345_.m_25352_(1, new The_Harbinger_Entity.MissileLaunchGoal(this, MISSILE_FIRE_ANIAMATION));
      this.f_21345_.m_25352_(1, new The_Harbinger_Entity.MissileLaunchGoal2(this, MISSILE_FIRE_FAST_ANIAMATION));
      this.f_21345_.m_25352_(1, new AttackAniamtionGoal3<>(this, STUN_ANIAMATION));
      this.f_21345_.m_25352_(2, new RangedAttackGoal(this, 1.0, 40, 20.0F));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
   }

   public static Builder harbinger() {
      return Monster.m_33035_()
         .m_22268_(Attributes.f_22276_, 390.0)
         .m_22268_(Attributes.f_22279_, 0.6F)
         .m_22268_(Attributes.f_22281_, 9.0)
         .m_22268_(Attributes.f_22280_, 0.6F)
         .m_22268_(Attributes.f_22277_, 40.0)
         .m_22268_(Attributes.f_22278_, 0.5)
         .m_22268_(Attributes.f_22284_, 12.0);
   }

   protected int m_7302_(int air) {
      return air;
   }

   public boolean m_142535_(float p_148711_, float p_148712_, DamageSource p_148713_) {
      return false;
   }

   public ItemEntity m_19983_(ItemStack stack) {
      ItemEntity itementity = this.m_5552_(stack, 0.0F);
      if (itementity != null) {
         itementity.m_146915_(true);
         itementity.m_32064_();
      }

      return itementity;
   }

   @Override
   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(LASER_MODE, false);
      this.f_19804_.m_135372_(ISCHARGE, false);
      this.f_19804_.m_135372_(IS_ACT, true);
      this.f_19804_.m_135372_(FIRST_HEAD_TARGET, 0);
      this.f_19804_.m_135372_(SECOND_HEAD_TARGET, 0);
      this.f_19804_.m_135372_(THIRD_HEAD_TARGET, 0);
      this.f_19804_.m_135372_(OVERLOAD, 0);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Is_Act", this.getIsAct());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setIsAct(compound.m_128471_("Is_Act"));
      if (this.m_8077_()) {
         this.bossEvent.m_6456_(this.m_5446_());
      }
   }

   @Override
   public boolean m_6469_(DamageSource source, float damage) {
      Entity entity1 = source.m_7639_();
      if (entity1 instanceof The_Harbinger_Entity) {
         return false;
      } else {
         for (int i = 0; i < this.idleHeadUpdates.length; i++) {
            this.idleHeadUpdates[i] = this.idleHeadUpdates[i] + 3;
         }

         double range = this.calculateRange(source);
         if (range > CMConfig.HarbingerLongRangelimit * CMConfig.HarbingerLongRangelimit && !source.m_19378_()) {
            return false;
         } else {
            if (this.destroyBlocksTick <= 0) {
               this.destroyBlocksTick = 20;
            }

            if (this.getAnimation() != STUN_ANIAMATION && this.getAnimation() != DEATHLASER_ANIMATION && source == CMDamageTypes.EMP) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, STUN_ANIAMATION);
            }

            if (this.m_7090_()) {
               Entity entity = source.m_7640_();
               if (entity instanceof AbstractArrow) {
                  return false;
               }
            }

            return this.deactivateProgress > 0.0F && !source.m_19378_() ? false : super.m_6469_(source, damage);
         }
      }
   }

   @Override
   public float DamageCap() {
      return (float)CMConfig.HarbingerDamageCap;
   }

   public boolean m_142066_() {
      return this.getIsAct() && super.m_142066_();
   }

   public void m_6457_(ServerPlayer player) {
      super.m_6457_(player);
      this.bossEvent.m_6543_(player);
   }

   public void m_6452_(ServerPlayer player) {
      super.m_6452_(player);
      this.bossEvent.m_6539_(player);
   }

   public boolean m_7307_(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.m_7307_(entityIn)) {
         return true;
      } else {
         return !entityIn.m_6095_().m_204039_(ModTag.TEAM_THE_HARBINGER) ? false : this.m_5647_() == null && entityIn.m_5647_() == null;
      }
   }

   public void m_8107_() {
      Vec3 vec3 = this.m_20184_().m_82542_(1.0, 0.6, 1.0);
      this.bossEvent.m_142711_(this.m_21223_() / this.m_21233_());
      this.prev_Laser_Mode_Progress = this.Laser_Mode_Progress;
      if (this.getIsLaserMode() && this.Laser_Mode_Progress < 30.0F) {
         this.Laser_Mode_Progress++;
      }

      if (!this.getIsLaserMode() && this.Laser_Mode_Progress > 0.0F) {
         this.Laser_Mode_Progress--;
      }

      this.prevdeactivateProgress = this.deactivateProgress;
      if (!this.getIsAct() && this.deactivateProgress < 40.0F) {
         this.deactivateProgress = 40.0F;
      }

      if (this.getIsAct() && this.deactivateProgress > 0.0F) {
         this.deactivateProgress--;
      }

      if (this.getIsAct() && this.skill_cooldown > 0) {
         this.skill_cooldown--;
      }

      Entity entity = this.f_19853_.m_6815_(this.getAlternativeTarget(0));
      if (!this.f_19853_.f_46443_
         && this.getAlternativeTarget(0) > 0
         && this.m_6084_()
         && !this.getIsCharge()
         && this.getAnimation() != STUN_ANIAMATION
         && entity != null) {
         double d0 = vec3.f_82480_;
         double l0 = this.getAnimation() != MISSILE_FIRE_FAST_ANIAMATION
               && this.getAnimation() != MISSILE_FIRE_ANIAMATION
               && this.getAnimation() != LAUNCH_ANIAMATION
            ? 2.25
            : 1.0;
         if (this.m_20186_() < entity.m_20186_() + l0) {
            d0 = Math.max(0.0, d0);
            d0 += 0.3 - d0 * 0.6F;
         }

         vec3 = new Vec3(vec3.f_82479_, d0, vec3.f_82481_);
         Vec3 vec31 = new Vec3(entity.m_20185_() - this.m_20185_(), 0.0, entity.m_20189_() - this.m_20189_());
         if (vec31.m_165925_() > 25.0
            && (this.getAnimation() != DEATHLASER_ANIMATION || this.getAnimationTick() <= 13)
            && this.getAnimation() != MISSILE_FIRE_ANIAMATION
            && this.getAnimation() != MISSILE_FIRE_FAST_ANIAMATION) {
            Vec3 vec32 = vec31.m_82541_();
            vec3 = vec3.m_82520_(vec32.f_82479_ * 0.3 - vec3.f_82479_ * 0.6, 0.0, vec32.f_82481_ * 0.3 - vec3.f_82481_ * 0.6);
         }
      }

      LivingEntity target = this.m_5448_();
      if (this.getIsAct()
         && this.m_6084_()
         && this.deactivateProgress == 0.0F
         && target != null
         && target.m_6084_()
         && this.skill_cooldown <= 0
         && (this.Laser_Mode_Progress == 30.0F || this.Laser_Mode_Progress == 0.0F)) {
         if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.getOverload() >= 3 && this.m_217043_().m_188501_() * 100.0F < 3.0F) {
            this.skill_cooldown = 240;
            this.setAnimation(DEATHLASER_ANIMATION);
         } else if (this.m_21525_()
            || this.getAnimation() != NO_ANIMATION
            || this.getOverload() >= 3
            || !(this.m_20280_(target) < 64.0)
            || (!(this.m_217043_().m_188501_() * 100.0F < 4.0F) || !this.m_142582_(target))
               && (!(this.m_217043_().m_188501_() * 100.0F < 20.0F) || this.m_142582_(target))) {
            if (!this.m_21525_()
               && this.getAnimation() == NO_ANIMATION
               && this.getOverload() < 3
               && this.m_217043_().m_188501_() * 100.0F < 3.0F
               && this.Laser_Mode_Progress == 0.0F) {
               this.skill_cooldown = 240;
               this.setAnimation(LAUNCH_ANIAMATION);
            } else if (!this.m_21525_() && this.getAnimation() == NO_ANIMATION && this.getOverload() < 3 && this.m_217043_().m_188501_() * 100.0F < 1.5F) {
               this.skill_cooldown = 240;
               Animation missile = this.m_7090_() ? MISSILE_FIRE_FAST_ANIAMATION : MISSILE_FIRE_ANIAMATION;
               this.setAnimation(missile);
            }
         } else {
            this.skill_cooldown = 240;
            this.setAnimation(CHARGE_ANIMATION);
         }
      }

      this.m_20256_(vec3);
      if (vec3.m_165925_() > 0.05) {
         this.m_146922_((float)Mth.m_14136_(vec3.f_82481_, vec3.f_82479_) * (180.0F / (float)Math.PI) - 90.0F);
      }

      super.m_8107_();

      for (int i = 0; i < 2; i++) {
         this.yRotOHeads[i] = this.yRotHeads[i];
         this.xRotOHeads[i] = this.xRotHeads[i];
      }

      if (this.getAnimation() != CHARGE_ANIMATION && this.getIsCharge()) {
         this.setIsCharge(false);
      }

      if (this.getAnimation() == STUN_ANIAMATION && this.getAnimationTick() == 15) {
         this.f_19853_
            .m_6269_(
               (Player)null, this, (SoundEvent)ModSounds.HARBINGER_STUN.get(), SoundSource.HOSTILE, 4.0F, this.f_19853_.f_46441_.m_188501_() * 0.2F + 1.0F
            );
      }

      if (this.getAnimation() == DEATHLASER_ANIMATION && this.getAnimationTick() == 33) {
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.DEATH_LASER.get(), SoundSource.HOSTILE, 4.0F, 0.75F);
      }

      if (this.getAnimation() == CHARGE_ANIMATION && this.getAnimationTick() == 24) {
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.HARBINGER_CHARGE.get(), SoundSource.HOSTILE, 4.0F, 0.65F);
      }

      if ((this.getAnimation() == MISSILE_FIRE_ANIAMATION || this.getAnimation() == MISSILE_FIRE_FAST_ANIAMATION) && this.getAnimationTick() == 24) {
         this.f_19853_.m_6269_((Player)null, this, (SoundEvent)ModSounds.HARBINGER_PREPARE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
      }

      for (int j = 0; j < 2; j++) {
         int k = this.getAlternativeTarget(j + 1);
         Entity entity1 = null;
         if (k > 0) {
            entity1 = this.f_19853_.m_6815_(k);
         }

         if (entity1 != null) {
            double d9 = this.getHeadX(j + 1);
            double d1 = this.getHeadY(j + 1);
            double d3 = this.getHeadZ(j + 1);
            double d4 = entity1.m_20185_() - d9;
            double d5 = entity1.m_20188_() - d1;
            double d6 = entity1.m_20189_() - d3;
            double d7 = Math.sqrt(d4 * d4 + d6 * d6);
            float f = (float)(Mth.m_14136_(d6, d4) * 180.0F / (float)Math.PI) - 90.0F;
            float f1 = (float)(-(Mth.m_14136_(d5, d7) * 180.0F / (float)Math.PI));
            this.xRotHeads[j] = this.m_31442_(this.xRotHeads[j], f1, 40.0F);
            this.yRotHeads[j] = this.m_31442_(this.yRotHeads[j], f, 10.0F);
         } else {
            this.yRotHeads[j] = this.m_31442_(this.yRotHeads[j], this.f_20883_, 10.0F);
         }
      }

      if (this.f_19853_.f_46443_ && this.getIsAct()) {
         double d0x = (double)(this.f_19796_.m_188501_() - 0.5F) + this.m_20184_().f_82479_;
         double d1 = (double)(this.f_19796_.m_188501_() - 0.5F) + this.m_20184_().f_82480_;
         double d2 = (double)(this.f_19796_.m_188501_() - 0.5F) + this.m_20184_().f_82481_;
         double dist = (double)(1.0F + this.f_19796_.m_188501_() * 0.2F);
         double d3 = d0x * dist;
         double d4 = d1 * dist;
         double d5 = d2 * dist;
         this.f_19853_.m_7106_(new LightningParticle.OrbData(255, 51, 0), this.m_20185_() + d0x, this.m_20186_() + 2.0, this.m_20189_() + d2, d3, d4, d5);
         if (entity != null && this.getAnimation() != MISSILE_FIRE_ANIAMATION) {
            float f = Mth.m_14089_(this.f_20883_ * (float) (Math.PI / 180.0));
            float f1 = Mth.m_14031_(this.f_20883_ * (float) (Math.PI / 180.0));
            double theta = (double)this.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            double vecZ = Math.sin(theta);
            double vec = -1.75;
            double math = 1.35;

            for (int i1 = 0; i1 < 10; i1++) {
               float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
               double extraX = (double)(0.2F * Mth.m_14031_((float)(Math.PI + (double)angle)));
               double extraY = 2.75;
               double extraZ = (double)(0.2F * Mth.m_14089_(angle));
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123744_,
                     this.m_20185_() + vec * vecX + extraX + (double)f * math,
                     this.m_20186_() + extraY,
                     this.m_20189_() + vec * vecZ + extraZ + (double)f1 * math,
                     0.0,
                     -0.07,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123744_,
                     this.m_20185_() + vec * vecX + extraX + (double)f * -math,
                     this.m_20186_() + extraY,
                     this.m_20189_() + vec * vecZ + extraZ + (double)f1 * -math,
                     0.0,
                     -0.07,
                     0.0
                  );
            }

            for (int i1 = 0; i1 < 5; i1++) {
               float angle = (float) (Math.PI / 180.0) * this.f_20883_ + (float)i1;
               double extraX = (double)(0.2F * Mth.m_14031_((float)(Math.PI + (double)angle)));
               double extraY = 2.75;
               double extraZ = (double)(0.2F * Mth.m_14089_(angle));
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.m_20185_() + vec * vecX + extraX + (double)f * math,
                     this.m_20186_() + extraY,
                     this.m_20189_() + vec * vecZ + extraZ + (double)f1 * math,
                     0.0,
                     -0.07,
                     0.0
                  );
               this.f_19853_
                  .m_7106_(
                     ParticleTypes.f_123762_,
                     this.m_20185_() + vec * vecX + extraX + (double)f * -math,
                     this.m_20186_() + extraY,
                     this.m_20189_() + vec * vecZ + extraZ + (double)f1 * -math,
                     0.0,
                     -0.07,
                     0.0
                  );
            }
         }

         if (this.getAnimation() == STUN_ANIAMATION) {
            for (int i = 0; i < 2; i++) {
               this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20208_(1.5), this.m_20187_(), this.m_20262_(1.5), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   private void blockbreak() {
      if (this.getIsCharge()) {
         if (!this.f_19853_.f_46443_ && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            boolean flag = false;
            AABB aabb = this.m_20191_().m_82377_(1.5, 0.2, 1.5);

            for (BlockPos blockpos : BlockPos.m_121976_(
               Mth.m_14107_(aabb.f_82288_),
               Mth.m_14107_(aabb.f_82289_),
               Mth.m_14107_(aabb.f_82290_),
               Mth.m_14107_(aabb.f_82291_),
               Mth.m_14107_(aabb.f_82292_),
               Mth.m_14107_(aabb.f_82293_)
            )) {
               BlockState blockstate = this.f_19853_.m_8055_(blockpos);
               if (!blockstate.m_60795_()
                  && blockstate.canEntityDestroy(this.f_19853_, blockpos, this)
                  && !blockstate.m_204336_(ModTag.HARBINGER_IMMUNE)
                  && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                  if (this.f_19796_.m_188503_(3) == 0 && !blockstate.m_155947_()) {
                     Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                        this.f_19853_,
                        (double)blockpos.m_123341_() + 0.5,
                        (double)blockpos.m_123342_() + 0.5,
                        (double)blockpos.m_123343_() + 0.5,
                        blockstate,
                        20
                     );
                     flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
                     fallingBlockEntity.m_20256_(
                        fallingBlockEntity.m_20184_()
                           .m_82549_(
                              this.m_20182_()
                                 .m_82546_(fallingBlockEntity.m_20182_())
                                 .m_82542_(
                                    (-1.2 + this.f_19796_.m_188500_()) / 3.0,
                                    0.2 + this.m_217043_().m_188583_() * 0.15,
                                    (-1.2 + this.f_19796_.m_188500_()) / 3.0
                                 )
                           )
                     );
                     this.f_19853_.m_7967_(fallingBlockEntity);
                  } else {
                     flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
                  }
               }
            }

            if (flag) {
               this.f_19853_.m_5898_((Player)null, 1022, this.m_20183_(), 0);
            }
         }

         if (this.f_19797_ % 4 == 0) {
            for (LivingEntity Lentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_20191_().m_82400_(1.5))) {
               if (!this.m_7307_(Lentity) && !(Lentity instanceof The_Harbinger_Entity) && Lentity != this) {
                  boolean flag = Lentity.m_6469_(
                     DamageSource.m_19370_(this),
                     (float)(
                        (double)((float)this.m_21133_(Attributes.f_22281_) + (float)this.f_19796_.m_188503_(5))
                           + Math.min(this.m_21133_(Attributes.f_22281_), (double)Lentity.m_21233_() * CMConfig.HarbingerChargeHpDamage)
                     )
                  );
                  if (flag && Lentity.m_20096_()) {
                     double d0 = Lentity.m_20185_() - this.m_20185_();
                     double d1 = Lentity.m_20189_() - this.m_20189_();
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                     float f = 1.5F;
                     Lentity.m_5997_(d0 / d2 * (double)f, 0.5, d1 / d2 * (double)f);
                  }
               }
            }
         }
      }
   }

   private void destoryblock2() {
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      } else {
         boolean flag = false;
         if (this.getAnimation() == NO_ANIMATION
            && !this.getIsCharge()
            && !this.f_19853_.f_46443_
            && this.blockBreakCounter == 0
            && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            AABB aabb = this.m_20191_().m_82400_(0.2);

            for (BlockPos pos : BlockPos.m_121976_(
               Mth.m_14107_(aabb.f_82288_),
               Mth.m_14107_(aabb.f_82289_),
               Mth.m_14107_(aabb.f_82290_),
               Mth.m_14107_(aabb.f_82291_),
               Mth.m_14107_(aabb.f_82292_),
               Mth.m_14107_(aabb.f_82293_)
            )) {
               BlockState blockstate = this.f_19853_.m_8055_(pos);
               if (!blockstate.m_60795_() && blockstate.canEntityDestroy(this.f_19853_, pos, this) && !blockstate.m_204336_(ModTag.HARBINGER_IMMUNE)) {
                  if (this.f_19796_.m_188503_(5) == 0 && !blockstate.m_155947_()) {
                     Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                        this.f_19853_, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5, blockstate, 20
                     );
                     flag = this.f_19853_.m_46953_(pos, false, this) || flag;
                     fallingBlockEntity.m_20256_(
                        fallingBlockEntity.m_20184_()
                           .m_82549_(
                              this.m_20182_()
                                 .m_82546_(fallingBlockEntity.m_20182_())
                                 .m_82542_(
                                    (-1.2 + this.f_19796_.m_188500_()) / 3.0,
                                    0.2 + this.m_217043_().m_188583_() * 0.15,
                                    (-1.2 + this.f_19796_.m_188500_()) / 3.0
                                 )
                           )
                     );
                     this.f_19853_.m_7967_(fallingBlockEntity);
                  } else {
                     flag = this.f_19853_.m_46953_(pos, false, this) || flag;
                     this.m_20256_(this.m_20184_().m_82542_(0.6F, 1.0, 0.6F));
                  }
               }
            }
         }

         if (flag) {
            this.blockBreakCounter = 20;
            this.f_19853_.m_5898_((Player)null, 1022, this.m_20183_(), 0);
         }
      }
   }

   private void destroyBlock() {
      if (this.getAnimation() != STUN_ANIAMATION && !this.f_19853_.f_46443_ && this.destroyBlocksTick > 0) {
         this.destroyBlocksTick--;
         if (this.destroyBlocksTick == 0 && ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
            int j1 = Mth.m_14107_(this.m_20186_());
            int i2 = Mth.m_14107_(this.m_20185_());
            int j2 = Mth.m_14107_(this.m_20189_());
            boolean flag = false;

            for (int j = -1; j <= 1; j++) {
               for (int k2 = -1; k2 <= 1; k2++) {
                  for (int k = 0; k <= 3; k++) {
                     int l2 = i2 + j;
                     int l = j1 + k;
                     int i1 = j2 + k2;
                     BlockPos blockpos = new BlockPos(l2, l, i1);
                     BlockState blockstate = this.f_19853_.m_8055_(blockpos);
                     if (!blockstate.m_60795_()
                        && blockstate.canEntityDestroy(this.f_19853_, blockpos, this)
                        && !blockstate.m_204336_(ModTag.HARBINGER_IMMUNE)
                        && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                        if (this.f_19796_.m_188503_(5) == 0 && !blockstate.m_155947_()) {
                           Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                              this.f_19853_,
                              (double)blockpos.m_123341_() + 0.5,
                              (double)blockpos.m_123342_() + 0.5,
                              (double)blockpos.m_123343_() + 0.5,
                              blockstate,
                              20
                           );
                           flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
                           fallingBlockEntity.m_20256_(
                              fallingBlockEntity.m_20184_()
                                 .m_82549_(
                                    this.m_20182_()
                                       .m_82546_(fallingBlockEntity.m_20182_())
                                       .m_82542_(
                                          (-1.2 + this.f_19796_.m_188500_()) / 3.0,
                                          0.2 + this.m_217043_().m_188583_() * 0.15,
                                          (-1.2 + this.f_19796_.m_188500_()) / 3.0
                                       )
                                 )
                           );
                           this.f_19853_.m_7967_(fallingBlockEntity);
                        } else {
                           flag = this.f_19853_.m_46953_(blockpos, false, this) || flag;
                        }
                     }
                  }
               }
            }

            if (flag) {
               this.f_19853_.m_5898_((Player)null, 1022, this.m_20183_(), 0);
            }
         }
      }
   }

   public InteractionResult m_6071_(Player player, InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      Item item = itemstack.m_41720_();
      if (item == Items.f_42686_ && !this.getIsAct()) {
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         this.setIsAct(true);
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6071_(player, hand);
      }
   }

   protected void m_8024_() {
      if (this.getIsAct()) {
         if (this.deactivateProgress > 0.0F) {
            float k1 = this.deactivateProgress - 1.0F;
            if (k1 <= 0.0F && !this.m_20067_()) {
               this.f_19853_.m_6798_(1023, this.m_20183_(), 0);
            }
         } else {
            super.m_8024_();

            for (int i = 1; i < 3; i++) {
               if (this.f_19797_ >= this.nextHeadUpdate[i - 1]) {
                  this.nextHeadUpdate[i - 1] = this.f_19797_ + 10 + this.f_19796_.m_188503_(10);
                  int l1 = this.getAlternativeTarget(i);
                  if (l1 > 0) {
                     LivingEntity livingentity = (LivingEntity)this.f_19853_.m_6815_(l1);
                     if (livingentity != null
                        && this.m_6779_(livingentity)
                        && !(this.m_20280_(livingentity) > 1600.0)
                        && this.m_142582_(livingentity)
                        && (this.Laser_Mode_Progress == 30.0F || this.Laser_Mode_Progress == 0.0F)
                        && this.getAnimation() == NO_ANIMATION) {
                        this.performRangedAttack(i + 1, livingentity);
                        int f = this.getIsLaserMode() ? 15 + this.f_19796_.m_188503_(5) : 30 + this.f_19796_.m_188503_(20);
                        this.nextHeadUpdate[i - 1] = this.f_19797_ + f;
                        this.idleHeadUpdates[i - 1] = 0;
                     } else {
                        this.setAlternativeTarget(i, 0);
                     }
                  } else {
                     List<LivingEntity> list = this.f_19853_
                        .m_45971_(LivingEntity.class, TARGETING_CONDITIONS, this, this.m_20191_().m_82377_(20.0, 8.0, 20.0));
                     if (!list.isEmpty()) {
                        LivingEntity livingentity1 = list.get(this.f_19796_.m_188503_(list.size()));
                        this.setAlternativeTarget(i, livingentity1.m_19879_());
                     }
                  }
               }
            }

            this.blockbreak();
            this.destroyBlock();
            if (this.m_5448_() != null) {
               this.destoryblock2();
            }

            if (this.mode_change_cooldown < 300) {
               this.mode_change_cooldown++;
            } else if (this.getAnimation() == NO_ANIMATION) {
               this.setIsLaserMode(!this.getIsLaserMode());
               this.m_5496_((SoundEvent)ModSounds.HARBINGER_MODE_CHANGE.get(), 3.0F, 1.0F);
               this.mode_change_cooldown = this.f_19796_.m_188503_(50);
            }

            if (this.m_5448_() != null) {
               this.setAlternativeTarget(0, this.m_5448_().m_19879_());
            } else {
               this.setAlternativeTarget(0, 0);
            }

            if (this.f_19797_ % 20 == 0) {
               this.m_5634_((float)CMConfig.HarbingerHealingMultiplier);
            }
         }
      } else if (this.f_19797_ % 20 == 0) {
         this.m_5634_(50.0F * (float)CMConfig.HarbingerHealthMultiplier);
      }
   }

   protected SoundEvent m_7515_() {
      return this.getIsAct() ? (SoundEvent)ModSounds.HARBINGER_IDLE.get() : super.m_7515_();
   }

   protected SoundEvent m_7975_(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.HARBINGER_HURT.get();
   }

   protected SoundEvent m_5592_() {
      return (SoundEvent)ModSounds.HARBINGER_HURT.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.HARBINGER_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && this.getIsAct();
   }

   @Override
   protected void onDeathAIUpdate() {
      super.onDeathAIUpdate();
      this.m_6478_(MoverType.SELF, new Vec3(0.0, 0.15F, 0.0));
      this.m_146922_(this.f_19859_);
      this.f_20883_ = this.m_146908_();
      this.f_20885_ = this.m_146908_();
      if (this.f_20919_ == 123 && !this.f_19853_.f_46443_) {
         BlockInteraction explosion$blockinteraction = ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)
            ? BlockInteraction.DESTROY
            : BlockInteraction.NONE;
         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20188_(), this.m_20189_(), 7.0F, false, explosion$blockinteraction);
      }
   }

   private double getHeadX(int head) {
      if (head <= 0) {
         return this.m_20185_();
      } else {
         float f = (this.f_20883_ + (float)(180 * (head - 1))) * (float) (Math.PI / 180.0);
         float f1 = Mth.m_14089_(f);
         double f2 = this.getIsLaserMode() ? 1.65 : 1.5;
         return this.m_20185_() + (double)f1 * f2;
      }
   }

   private double getHeadY(int head) {
      return head <= 0 ? this.m_20186_() + 3.0 : this.m_20186_() + 2.6;
   }

   private double getHeadZ(int head) {
      if (head <= 0) {
         return this.m_20189_();
      } else {
         float f = (this.f_20883_ + (float)(180 * (head - 1))) * (float) (Math.PI / 180.0);
         float f1 = Mth.m_14031_(f);
         double f2 = this.getIsLaserMode() ? 1.65 : 1.5;
         return this.m_20189_() + (double)f1 * f2;
      }
   }

   private float m_31442_(float p_31443_, float p_31444_, float p_31445_) {
      float f = Mth.m_14177_(p_31444_ - p_31443_);
      if (f > p_31445_) {
         f = p_31445_;
      }

      if (f < -p_31445_) {
         f = -p_31445_;
      }

      return p_31443_ + f;
   }

   private void performRangedAttack(int head, LivingEntity target) {
      this.performRangedAttack(head, target.m_20185_(), target.m_20186_() + (double)target.m_20192_() * 0.5, target.m_20189_());
   }

   private void performRangedAttack(int head, double targetX, double targetY, double targetZ) {
      double d0 = this.getHeadX(head);
      double d1 = this.getHeadY(head);
      double d2 = this.getHeadZ(head);
      double d3 = targetX - d0;
      double d4 = targetY - d1;
      double d5 = targetZ - d2;
      if (this.getIsLaserMode()) {
         if (!this.m_20067_()) {
            this.m_5496_((SoundEvent)ModSounds.HARBINGER_LASER.get(), 1.0F, 1.0F);
         }

         Laser_Beam_Entity laserBeam = new Laser_Beam_Entity(this.f_19853_, this);
         laserBeam.m_6686_(d3, d4, d5, 1.0F, 1.0F);
         laserBeam.setDamage((float)CMConfig.HarbingerLaserdamage);
         laserBeam.m_20343_(d0, d1, d2);
         this.f_19853_.m_7967_(laserBeam);
      } else {
         if (!this.m_20067_()) {
            this.m_5496_((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 0.8F);
         }

         Wither_Missile_Entity witherskull = new Wither_Missile_Entity(this, d3, d4, d5, this.f_19853_, (float)CMConfig.HarbingerWitherMissiledamage);
         witherskull.m_20343_(d0, d1, d2);
         this.f_19853_.m_7967_(witherskull);
      }
   }

   public void m_6504_(LivingEntity p_31468_, float p_31469_) {
      this.performRangedAttack(0, p_31468_);
   }

   @Override
   protected void repelEntities(float x, float y, float z, float radius) {
      super.repelEntities(x, y, z, radius);
   }

   public float getHeadYRot(int head) {
      return this.yRotHeads[head];
   }

   public float getHeadXRot(int head) {
      return this.xRotHeads[head];
   }

   public int getAlternativeTarget(int head) {
      return (Integer)this.f_19804_.m_135370_(HEAD_TARGETS.get(head));
   }

   public void setAlternativeTarget(int targetOffset, int newId) {
      this.f_19804_.m_135381_(HEAD_TARGETS.get(targetOffset), newId);
   }

   public void setIsLaserMode(boolean isLaserMode) {
      this.f_19804_.m_135381_(LASER_MODE, isLaserMode);
   }

   public boolean getIsLaserMode() {
      return (Boolean)this.f_19804_.m_135370_(LASER_MODE);
   }

   public void setIsAct(boolean isAct) {
      this.f_19804_.m_135381_(IS_ACT, isAct);
      this.bossEvent.m_8321_(isAct);
   }

   public boolean getIsAct() {
      return (Boolean)this.f_19804_.m_135370_(IS_ACT);
   }

   public void setIsCharge(boolean isCharge) {
      this.f_19804_.m_135381_(ISCHARGE, isCharge);
   }

   public boolean getIsCharge() {
      return (Boolean)this.f_19804_.m_135370_(ISCHARGE);
   }

   public void setOverload(int Overload) {
      this.f_19804_.m_135381_(OVERLOAD, Overload);
   }

   public int getOverload() {
      return (Integer)this.f_19804_.m_135370_(OVERLOAD);
   }

   public boolean m_7090_() {
      return this.m_21223_() <= this.m_21233_() / 2.0F;
   }

   @Override
   public boolean canBePushedByEntity(Entity entity) {
      return false;
   }

   public boolean m_6072_() {
      return false;
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return DEATH_ANIMATION;
   }

   @Override
   public boolean m_7301_(MobEffectInstance p_31495_) {
      return p_31495_.m_19544_() != MobEffects.f_19597_
         && p_31495_.m_19544_() != MobEffects.f_19614_
         && p_31495_.m_19544_() != MobEffects.f_19615_
         && p_31495_.m_19544_() != MobEffects.f_19613_
         && p_31495_.m_19544_() != MobEffects.f_19620_
         && super.m_7301_(p_31495_);
   }

   public BossBarColor bossBarColor() {
      return BossBarColor.PURPLE;
   }

   class AwakenGoal extends Goal {
      public AwakenGoal() {
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean m_8036_() {
         return The_Harbinger_Entity.this.deactivateProgress > 0.0F;
      }

      public boolean m_183429_() {
         return true;
      }

      public void m_8037_() {
         The_Harbinger_Entity.this.m_20334_(0.0, The_Harbinger_Entity.this.m_20184_().f_82480_, 0.0);
      }
   }

   static class ChargeGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public ChargeGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         super.m_8056_();
         this.entity.setOverload(this.entity.getOverload() + 1);
         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.HARBINGER_CHARGE_PREPARE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getAnimationTick() == 24) {
            this.entity.setIsCharge(true);
         }

         if (this.entity.getAnimationTick() == 36) {
            this.entity.setIsCharge(false);
         }

         if (target != null) {
            if (this.entity.getAnimationTick() < 24) {
               this.entity.m_21391_(target, 30.0F, 30.0F);
               this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            }

            if (this.entity.getAnimationTick() == 24) {
               Vec3 rot = target.m_20182_().m_82492_(0.0, 2.0, 0.0).m_82549_(this.entity.m_20182_().m_82542_(-1.0, -1.0, -1.0)).m_82541_();
               this.entity.m_20256_(rot.m_82542_(4.0, 5.0, 4.0));
            }

            if (this.entity.getAnimationTick() == 45) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Harbinger_Entity.CHARGE_ANIMATION);
            }
         }
      }

      public void m_8041_() {
         super.m_8041_();
         LivingEntity target = this.entity.m_5448_();
         if (this.entity.getOverload() < 3 && this.entity.m_7090_() && target != null && this.entity.f_19796_.m_188503_(2) == 0) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Harbinger_Entity.CHARGE_ANIMATION);
         }
      }
   }

   static class DeathLaserGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public DeathLaserGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         super.m_8056_();
         this.entity.f_19853_.m_6269_((Player)null, this.entity, (SoundEvent)ModSounds.HARBINGER_DEATHLASER_PREPARE.get(), SoundSource.HOSTILE, 8.0F, 1.2F);
         this.entity.setOverload(0);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         this.entity.m_20334_(0.0, this.entity.m_20184_().f_82480_, 0.0);
         if (this.entity.getAnimationTick() == 18 && !this.entity.f_19853_.f_46443_) {
            Death_Laser_Beam_Entity DeathBeam = new Death_Laser_Beam_Entity(
               (EntityType<? extends Death_Laser_Beam_Entity>)ModEntities.DEATH_LASER_BEAM.get(),
               this.entity.f_19853_,
               this.entity,
               this.entity.m_20185_(),
               this.entity.m_20186_() + 2.9,
               this.entity.m_20189_(),
               (float)((double)(this.entity.f_20885_ + 90.0F) * Math.PI / 180.0),
               (float)((double)(-this.entity.m_146909_()) * Math.PI / 180.0),
               60,
               (float)CMConfig.DeathLaserdamage,
               (float)CMConfig.DeathLaserHpdamage
            );
            if (this.entity.m_7090_()) {
               DeathBeam.setFire(true);
            }

            this.entity.f_19853_.m_7967_(DeathBeam);
         }

         if (this.entity.getAnimationTick() >= 35 && target != null) {
            this.entity.m_21563_().m_24950_(target.m_20185_(), target.m_20186_() + (double)(target.m_20206_() / 2.0F), target.m_20189_(), 6.0F, 90.0F);
            this.entity.m_21391_(target, 30.0F, 30.0F);
         }
      }
   }

   static class LaunchGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public LaunchGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         super.m_8056_();
         this.entity.setOverload(this.entity.getOverload() + 1);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            if (this.entity.getAnimationTick() == 13) {
               this.launch(2, target);
            }

            if (this.entity.getAnimationTick() == 19) {
               this.launch(1, target);
            }
         }
      }

      private void launch(int head, LivingEntity p_31459_) {
         this.launch(head, p_31459_.m_20185_(), p_31459_.m_20186_() + (double)p_31459_.m_20192_() * 0.5, p_31459_.m_20189_());
      }

      private void launch(int head, double p_31450_, double p_31451_, double p_31452_) {
         if (!this.entity.m_20067_()) {
            this.entity.m_5496_((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 1.0F);
         }

         double d0 = this.entity.getHeadX(head);
         double d1 = this.entity.getHeadY(head);
         double d2 = this.entity.getHeadZ(head);
         double d3 = p_31450_ - d0;
         double d4 = p_31451_ - d1;
         double d5 = p_31452_ - d2;
         double d6 = (double)Mth.m_14116_((float)(d3 * d3 + d5 * d5));
         int b = this.entity.m_7090_() ? 7 : 5;

         for (int i = 0; i < b; i++) {
            Wither_Howitzer_Entity lava = new Wither_Howitzer_Entity(
               (EntityType<Wither_Howitzer_Entity>)ModEntities.WITHER_HOWITZER.get(), this.entity.f_19853_, this.entity
            );
            lava.m_20343_(d0, d1, d2);
            lava.setRadius(3.0F);
            lava.m_6686_(d3, d4 + d6 * 6.0, d5, 0.6F, 60.0F);
            this.entity.f_19853_.m_7967_(lava);
         }
      }
   }

   static class MissileLaunchGoal extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public MissileLaunchGoal(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         super.m_8056_();
         this.entity.setOverload(this.entity.getOverload() + 1);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 80 || this.entity.getAnimationTick() == 84 || this.entity.getAnimationTick() == 88) {
               this.mlaunch(2, target);
            }

            if (this.entity.getAnimationTick() == 98 || this.entity.getAnimationTick() == 102 || this.entity.getAnimationTick() == 106) {
               this.mlaunch(1, target);
            }
         }
      }

      private void mlaunch(int head, LivingEntity target) {
         if (!this.entity.m_20067_()) {
            this.entity.m_5496_((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 1.0F);
         }

         double d0 = this.getLauncherX(head);
         double d1 = this.getLauncherY(head);
         double d2 = this.getLauncherZ(head);
         Wither_Homing_Missile_Entity laserBeam = new Wither_Homing_Missile_Entity(this.entity.f_19853_, this.entity, target);
         laserBeam.m_20343_(d0, d1, d2);
         this.entity.f_19853_.m_7967_(laserBeam);
      }

      private double getLauncherX(int head) {
         if (head <= 0) {
            return this.entity.m_20185_();
         } else {
            double theta = (double)this.entity.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            float f = (this.entity.f_20883_ + (float)(180 * (head - 1))) * (float) (Math.PI / 180.0);
            float f1 = Mth.m_14089_(f);
            return this.entity.m_20185_() + (double)f1 * 1.25 + vecX * 1.35;
         }
      }

      private double getLauncherY(int head) {
         return head <= 0 ? this.entity.m_20186_() + 3.0 : this.entity.m_20186_() + 3.8;
      }

      private double getLauncherZ(int head) {
         if (head <= 0) {
            return this.entity.m_20189_();
         } else {
            double theta = (double)this.entity.f_20883_ * (Math.PI / 180.0);
            double vecZ = Math.sin(++theta);
            float f = (this.entity.f_20883_ + (float)(180 * (head - 1))) * (float) (Math.PI / 180.0);
            float f1 = Mth.m_14031_(f);
            return this.entity.m_20189_() + (double)f1 * 1.25 + vecZ * 1.35;
         }
      }
   }

   static class MissileLaunchGoal2 extends SimpleAnimationGoal<The_Harbinger_Entity> {
      public MissileLaunchGoal2(The_Harbinger_Entity entity, Animation animation) {
         super(entity, animation);
         this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void m_8056_() {
         super.m_8056_();
         this.entity.setOverload(this.entity.getOverload() + 1);
      }

      public void m_8037_() {
         LivingEntity target = this.entity.m_5448_();
         if (target != null) {
            this.entity.m_21391_(target, 30.0F, 30.0F);
            this.entity.m_21563_().m_24960_(target, 30.0F, 30.0F);
            if (this.entity.getAnimationTick() == 71 || this.entity.getAnimationTick() == 75 || this.entity.getAnimationTick() == 79) {
               this.mlaunch(2, target);
               this.mlaunch(1, target);
            }
         }
      }

      private void mlaunch(int head, LivingEntity target) {
         if (!this.entity.m_20067_()) {
            this.entity.m_5496_((SoundEvent)ModSounds.ROCKET_LAUNCH.get(), 1.0F, 1.0F);
         }

         double d0 = this.getLauncherX(head);
         double d1 = this.getLauncherY(head);
         double d2 = this.getLauncherZ(head);
         Wither_Homing_Missile_Entity laserBeam = new Wither_Homing_Missile_Entity(this.entity.f_19853_, this.entity, target);
         laserBeam.m_20343_(d0, d1, d2);
         this.entity.f_19853_.m_7967_(laserBeam);
      }

      private double getLauncherX(int head) {
         if (head <= 0) {
            return this.entity.m_20185_();
         } else {
            double theta = (double)this.entity.f_20883_ * (Math.PI / 180.0);
            double vecX = Math.cos(++theta);
            float f = (this.entity.f_20883_ + (float)(180 * (head - 1))) * (float) (Math.PI / 180.0);
            float f1 = Mth.m_14089_(f);
            return this.entity.m_20185_() + (double)f1 * 1.25 + vecX * 1.35;
         }
      }

      private double getLauncherY(int head) {
         return head <= 0 ? this.entity.m_20186_() + 3.0 : this.entity.m_20186_() + 3.8;
      }

      private double getLauncherZ(int head) {
         if (head <= 0) {
            return this.entity.m_20189_();
         } else {
            double theta = (double)this.entity.f_20883_ * (Math.PI / 180.0);
            double vecZ = Math.sin(++theta);
            float f = (this.entity.f_20883_ + (float)(180 * (head - 1))) * (float) (Math.PI / 180.0);
            float f1 = Mth.m_14031_(f);
            return this.entity.m_20189_() + (double)f1 * 1.25 + vecZ * 1.35;
         }
      }
   }
}
