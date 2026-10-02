package net.xylonity.knightquest.common.entity.entities;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class RatmanEntity extends Skeleton implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private final Level serverWorld;
   private boolean summoned = false;
   private int counter = 0;
   private int arrowRotation = 50;
   private static final EntityDataAccessor<Boolean> ATTACK1 = SynchedEntityData.m_135353_(RatmanEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> VARIATION = SynchedEntityData.m_135353_(RatmanEntity.class, EntityDataSerializers.f_135028_);

   public RatmanEntity(EntityType<? extends Skeleton> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.serverWorld = pLevel;
      if (!this.serverWorld.m_5776_()) {
         boolean attack1 = new Random().nextBoolean();
         this.f_19804_.m_135381_(ATTACK1, attack1);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(ATTACK1, false);
      this.f_19804_.m_135372_(VARIATION, 1);
   }

   public void m_7380_(CompoundTag pCompound) {
      super.m_7380_(pCompound);
      pCompound.m_128379_("attack1", this.getAttack1());
      pCompound.m_128405_("Variant", this.getVariation());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setAttack1(compound.m_128471_("attack1"));
      this.f_19804_.m_135381_(VARIATION, compound.m_128451_("Variant"));
   }

   public boolean getAttack1() {
      return (Boolean)this.f_19804_.m_135370_(ATTACK1);
   }

   public int getVariation() {
      return (Integer)this.f_19804_.m_135370_(VARIATION);
   }

   public void setAttack1(boolean attack1) {
      this.f_19804_.m_135381_(ATTACK1, attack1);
   }

   public void setVariation(int variation) {
      this.f_19804_.m_135381_(VARIATION, variation);
   }

   public static AttributeSupplier setAttributes() {
      return Skeleton.m_21552_()
         .m_22268_(Attributes.f_22276_, 22.0)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.5)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(2, new RangedBowAttackGoal(this, 0.7, 10, 15.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.f_30122_));
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
      animationData.addAnimationController(new AnimationController(this, "attackcontroller", 0.0F, this::attackPredicate));
   }

   public boolean m_5886_(@NotNull ProjectileWeaponItem pProjectileWeapon) {
      return pProjectileWeapon == Items.f_42717_;
   }

   private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
      if (this.m_6117_()) {
         event.getController().markNeedsReload();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", EDefaultLoopTypes.PLAY_ONCE));
      }

      return PlayState.CONTINUE;
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   @NotNull
   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11946_;
   }

   protected SoundEvent m_7975_(@NotNull DamageSource pDamageSource) {
      return SoundEvents.f_11948_;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11944_;
   }

   protected void m_7355_(@NotNull BlockPos pPos, @NotNull BlockState pState) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.getAttack1()) {
         if (!this.summoned && (double)this.m_21223_() < (double)this.m_21233_() * 0.5) {
            if (this.counter < 10 && this.counter % 2 == 0) {
               for (int i = 0; i < 2; i++) {
                  this.shootArrow(this.arrowRotation += 36);
               }
            }

            this.counter++;
            if (this.counter >= 10) {
               this.summoned = true;
            }
         }
      } else if (!this.summoned && (double)this.m_21223_() < (double)this.m_21233_() * 0.5) {
         this.smokeBomb();
         this.summoned = true;
      }
   }

   private void shootArrow(int angle) {
      double arrowX = Math.cos(Math.toRadians((double)angle));
      double arrowY = this.m_20188_();
      double arrowZ = Math.sin(Math.toRadians((double)angle));
      Arrow arrow = new Arrow(this.serverWorld, this);
      arrow.m_6034_(this.m_20185_() + arrowX, arrowY - 1.5, this.m_20189_() + arrowZ);
      arrow.m_36793_(false);
      double velX = Math.cos(Math.toRadians((double)angle));
      double velZ = Math.sin(Math.toRadians((double)angle));
      arrow.m_20334_(velX, 0.3, velZ);
      switch (this.getVariation()) {
         case 1:
            arrow.m_36870_(new MobEffectInstance(MobEffects.f_19614_, 100, 0));
            break;
         case 2:
            arrow.m_36870_(new MobEffectInstance(MobEffects.f_19613_, 100, 0));
            break;
         case 3:
            arrow.m_36870_(new MobEffectInstance(MobEffects.f_19615_, 100, 0));
            break;
         default:
            arrow.m_20254_(10);
      }

      this.serverWorld.m_5594_(null, this.m_20183_(), SoundEvents.f_11798_, SoundSource.HOSTILE, 0.75F, 1.0F);
      this.serverWorld.m_7967_(arrow);
   }

   private void smokeBomb() {
      double radius = 2.5;
      this.serverWorld.m_5594_(null, this.m_20183_(), SoundEvents.f_12436_, SoundSource.HOSTILE, 1.0F, 1.0F);

      for (int i = 0; i < 450; i++) {
         double u = this.f_19796_.m_188500_();
         double v = this.f_19796_.m_188500_();
         double theta = (Math.PI * 2) * u;
         double phi = Math.acos(2.0 * v - 1.0);
         double scale = Math.cbrt(this.f_19796_.m_188500_());
         double x = this.m_20185_() + radius * scale * Math.sin(phi) * Math.cos(theta);
         double y = this.m_20186_() + radius * scale * Math.sin(phi) * Math.sin(theta);
         double z = this.m_20189_() + radius * scale * Math.cos(phi);
         this.serverWorld.m_7106_(ParticleTypes.f_123777_, x, y, z, 0.0, 0.0, 0.0);
      }

      this.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 100, 1));
   }

   @Nullable
   public SpawnGroupData m_6518_(
      @NotNull ServerLevelAccessor pLevel,
      @NotNull DifficultyInstance pDifficulty,
      @NotNull MobSpawnType pReason,
      @Nullable SpawnGroupData pSpawnData,
      @Nullable CompoundTag pDataTag
   ) {
      this.setVariation(this.m_217043_().m_216332_(1, 4));
      return super.m_6518_(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
   }
}
