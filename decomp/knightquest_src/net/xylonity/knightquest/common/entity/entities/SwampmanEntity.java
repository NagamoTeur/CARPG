package net.xylonity.knightquest.common.entity.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.xylonity.knightquest.common.entity.entities.ai.RangedAttackGoal;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestParticles;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SwampmanEntity extends Monster implements IAnimatable, RangedAttackMob {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.m_135353_(SwampmanEntity.class, EntityDataSerializers.f_135028_);
   private boolean isHalfHealth;

   public SwampmanEntity(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
      this.initEquipment();
   }

   protected int m_7305_(int pCurrentAir) {
      return this.m_6062_();
   }

   private void initEquipment() {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42411_));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(PHASE, 1);
   }

   public int getPhase() {
      return (Integer)this.f_19804_.m_135370_(PHASE);
   }

   public void setPhase(int phase) {
      this.f_19804_.m_135381_(PHASE, phase);
   }

   public static AttributeSupplier setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 50.0)
         .m_22268_(Attributes.f_22281_, 8.0)
         .m_22268_(Attributes.f_22283_, 0.8F)
         .m_22268_(Attributes.f_22279_, 0.5)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new RangedAttackGoal(this, 0.7, 10, 15.0F));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 0.6, true));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public void m_8119_() {
      super.m_8119_();
      if ((double)this.m_21223_() < (double)this.m_21233_() * 0.5 && !this.isHalfHealth && KQConfigValues.CAN_CHANGE_PHASE_SWAMPMAN) {
         this.f_19853_
            .m_7106_((ParticleOptions)KnightQuestParticles.BLUEBLASTWAVE.get(), this.m_20185_(), this.m_20186_() - 0.48, this.m_20189_(), 2.0, 0.0, 0.0);
         this.f_19853_.m_5594_(null, this.m_20183_(), SoundEvents.f_11868_, SoundSource.HOSTILE, 1.0F, 1.0F);
         this.isHalfHealth = true;
         this.setPhase(2);
      }

      if (this.getPhase() == 2 && this.f_19797_ % 20 == 0) {
         this.m_5634_(KQConfigValues.PHASE_2_HEALING_SWAMPMAN);
      }
   }

   public int m_6062_() {
      return 1000;
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
      animationData.addAnimationController(new AnimationController(this, "attackcontroller", 0.0F, this::attackPredicate));
   }

   private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
      if (this.m_6117_() && this.m_21205_().m_41720_() instanceof ProjectileWeaponItem) {
         event.getController().markNeedsReload();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("bow_attack", EDefaultLoopTypes.PLAY_ONCE));
      } else if (this.f_20911_ && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
         event.getController().markNeedsReload();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", EDefaultLoopTypes.PLAY_ONCE));
         this.f_20911_ = false;
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

   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11703_;
   }

   protected SoundEvent m_7975_(@NotNull DamageSource pDamageSource) {
      return SoundEvents.f_11704_;
   }

   public void m_6504_(LivingEntity livingEntity, float v) {
      SwampmanAxeEntity swampmanAxeEntity = new SwampmanAxeEntity(this.f_19853_, this);
      double d0 = livingEntity.m_20185_() - this.m_20185_();
      double d1 = livingEntity.m_20227_(0.34) - swampmanAxeEntity.m_20186_();
      double d2 = livingEntity.m_20189_() - this.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      swampmanAxeEntity.m_6686_(d0, d1 + d3 * 0.2F, d2, 1.3F, (float)(14 - this.f_19853_.m_46791_().m_19028_() * 4));
      this.m_5496_(SoundEvents.f_12382_, 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
      if (KQConfigValues.POISON_PHASE_2_SWAMPMAN) {
         swampmanAxeEntity.addEffect(new MobEffectInstance(MobEffects.f_19614_, 100, 0, true, true, true));
      }

      this.f_19853_.m_7967_(swampmanAxeEntity);
   }
}
