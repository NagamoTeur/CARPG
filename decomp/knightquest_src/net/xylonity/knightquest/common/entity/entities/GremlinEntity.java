package net.xylonity.knightquest.common.entity.entities;

import java.util.Objects;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.xylonity.knightquest.common.entity.entities.ai.NearestAttackableTargetGoal;
import net.xylonity.knightquest.common.entity.entities.ai.RandomLookAroundGoal;
import net.xylonity.knightquest.config.values.KQConfigValues;
import net.xylonity.knightquest.registry.KnightQuestEntities;
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

public class GremlinEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private static final EntityDataAccessor<Boolean> IS_PASSIVE = SynchedEntityData.m_135353_(GremlinEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> SHOULD_TAKE_COIN = SynchedEntityData.m_135353_(GremlinEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> GOLD_VARIATION = SynchedEntityData.m_135353_(GremlinEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> PHASE = SynchedEntityData.m_135353_(GremlinEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> ATTACK = SynchedEntityData.m_135353_(GremlinEntity.class, EntityDataSerializers.f_135035_);
   private int tickCounterShield = 0;
   private int tickCounter = 0;
   private boolean isHalfHealth;

   public GremlinEntity(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
      if (!this.f_19853_.m_5776_()) {
         boolean attack1 = new Random().nextBoolean();
         this.f_19804_.m_135381_(ATTACK, attack1);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(IS_PASSIVE, false);
      this.f_19804_.m_135372_(SHOULD_TAKE_COIN, false);
      this.f_19804_.m_135372_(GOLD_VARIATION, 0);
      this.f_19804_.m_135372_(PHASE, 1);
      this.f_19804_.m_135372_(ATTACK, false);
   }

   public boolean getIsPassive() {
      return (Boolean)this.f_19804_.m_135370_(IS_PASSIVE);
   }

   public boolean getShouldTakeCoin() {
      return (Boolean)this.f_19804_.m_135370_(SHOULD_TAKE_COIN);
   }

   public int getGoldVariation() {
      return (Integer)this.f_19804_.m_135370_(GOLD_VARIATION);
   }

   public int getPhase() {
      return (Integer)this.f_19804_.m_135370_(PHASE);
   }

   public boolean getAttack() {
      return (Boolean)this.f_19804_.m_135370_(ATTACK);
   }

   public void setIsPassive(boolean isPassive) {
      this.f_19804_.m_135381_(IS_PASSIVE, isPassive);
   }

   public void setShouldTakeCoin(boolean shouldTakeCoin) {
      this.f_19804_.m_135381_(SHOULD_TAKE_COIN, shouldTakeCoin);
   }

   public void setGoldVariation(int goldVariation) {
      this.f_19804_.m_135381_(GOLD_VARIATION, goldVariation);
   }

   public void setPhase(int phase) {
      this.f_19804_.m_135381_(PHASE, phase);
   }

   public void setAttack(boolean attack) {
      this.f_19804_.m_135381_(ATTACK, attack);
   }

   public static AttributeSupplier setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 35.0)
         .m_22268_(Attributes.f_22281_, 5.5)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.63F)
         .m_22268_(Attributes.f_22277_, 35.0)
         .m_22265_();
   }

   private void updateAttributes() {
      Objects.requireNonNull(this.m_21051_(Attributes.f_22281_))
         .m_22100_(this.m_21133_(Attributes.f_22281_) * (double)KQConfigValues.MULTIPLIER_GREMLIN_ATTACK_DAMAGE);
      Objects.requireNonNull(this.m_21051_(Attributes.f_22279_))
         .m_22100_(this.m_21133_(Attributes.f_22279_) * (double)KQConfigValues.MULTIPLIER_GREMLIN_MOVEMENT_SPEED);
      Objects.requireNonNull(this.m_21051_(Attributes.f_22283_))
         .m_22100_(this.m_21133_(Attributes.f_22283_) * (double)KQConfigValues.MULTIPLIER_GREMLIN_ATTACK_SPEED);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 0.5, true));
      this.f_21345_.m_25352_(2, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(3, new TemptGoal(this, 0.5, Ingredient.m_43929_(new ItemLike[]{Items.f_42405_}), false));
      this.f_21345_.m_25352_(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public boolean m_6469_(@NotNull DamageSource pSource, float pAmount) {
      return this.tickCounter != 0 ? false : super.m_6469_(pSource, pAmount);
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
      animationData.addAnimationController(new AnimationController(this, "attackcontroller", 0.0F, this::attackPredicate));
   }

   private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
      if (this.f_20911_ && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
         event.getController().markNeedsReload();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", EDefaultLoopTypes.PLAY_ONCE));
         this.f_20911_ = false;
      }

      return PlayState.CONTINUE;
   }

   private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
      if (this.tickCounter != 0) {
         event.getController().markNeedsReload();
         String goldVariation = this.getGoldVariation() == 0 ? "gold" : "gold2";
         if (this.tickCounter == 1) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(goldVariation, EDefaultLoopTypes.PLAY_ONCE));
         }
      } else if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   public void m_8119_() {
      super.m_8119_();
      if ((double)this.m_21223_() < (double)this.m_21233_() * 0.5) {
         if (!this.isHalfHealth) {
            this.f_19853_
               .m_7106_((ParticleOptions)KnightQuestParticles.GREMLIN_PARTICLE.get(), this.m_20185_(), this.m_20186_() - 0.48, this.m_20189_(), 2.0, 0.0, 0.0);
            this.f_19853_.m_5594_(null, this.m_20183_(), SoundEvents.f_11868_, SoundSource.HOSTILE, 1.0F, 1.0F);
            this.isHalfHealth = true;
            if (this.getAttack()) {
               this.spawnShield();
            } else {
               this.updateAttributes();
            }

            this.setPhase(2);
         }

         if (this.getAttack()) {
            this.tickCounterShield++;
            if (this.tickCounterShield % 22 == 0 && this.tickCounterShield / 25 <= 1) {
               this.spawnShield();
            }
         }
      }

      if (this.getIsPassive()) {
         this.tickCounter++;
         if (this.tickCounter == 1) {
            this.setShouldTakeCoin(true);
         } else if (this.tickCounter == 3) {
            this.setShouldTakeCoin(false);
         } else if (this.tickCounter == 80) {
            this.setIsPassive(false);
            this.setShouldTakeCoin(false);
            this.tickCounter = 0;
         }
      }
   }

   private void spawnShield() {
      GhastlingEntity entity = (GhastlingEntity)((EntityType)KnightQuestEntities.SHIELD.get()).m_20615_(this.f_19853_);
      if (entity != null) {
         entity.m_20035_(this.m_20097_(), 1.0F, 0.0F);
         this.f_19853_.m_7967_(entity);
      }
   }

   @NotNull
   protected InteractionResult m_6071_(@NotNull Player pPlayer, @NotNull InteractionHand pHand) {
      if (KQConfigValues.CAN_TAKE_GOLD_GREMLIN) {
         ItemStack itemstack = pPlayer.m_21120_(pHand);
         Item desiredItem = Items.f_42417_;
         Item item = itemstack.m_41720_();
         if (item.equals(desiredItem)) {
            this.m_6710_(null);
            this.m_6274_().m_21936_(MemoryModuleType.f_26372_);
            this.m_6274_().m_21936_(MemoryModuleType.f_26368_);
            this.m_6703_(null);
            this.m_6598_(null);
            this.setGoldVariation(this.m_217043_().m_216339_(0, 2));
            this.setIsPassive(true);
         }
      }

      return super.m_6071_(pPlayer, pHand);
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   @NotNull
   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12501_;
   }

   protected SoundEvent m_7975_(@NotNull DamageSource pDamageSource) {
      return SoundEvents.f_12502_;
   }

   protected void m_7355_(@NotNull BlockPos pPos, @NotNull BlockState pState) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }
}
