package net.xylonity.knightquest.common.entity.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

public class BadPatchEntity extends Monster implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public BadPatchEntity(EntityType<? extends Monster> entityType, Level world) {
      super(entityType, world);
   }

   public static AttributeSupplier setAttributes() {
      return Monster.m_21552_()
         .m_22268_(Attributes.f_22276_, 7.0)
         .m_22268_(Attributes.f_22281_, 4.0)
         .m_22268_(Attributes.f_22283_, 0.2F)
         .m_22268_(Attributes.f_22279_, 0.5)
         .m_22268_(Attributes.f_22277_, 35.0)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new MeleeAttackGoal(this, 0.5, true));
      this.f_21345_.m_25352_(2, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(3, new TemptGoal(this, 0.5, Ingredient.m_43929_(new ItemLike[]{Items.f_42405_}), false));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, true));
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
      if (event.isMoving()) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", EDefaultLoopTypes.LOOP));
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", EDefaultLoopTypes.LOOP));
      }

      if (this.f_20890_) {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("death", EDefaultLoopTypes.LOOP));
      }

      return PlayState.CONTINUE;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_144067_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11812_;
   }

   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_215763_;
   }

   protected void m_7355_(BlockPos pPos, BlockState pState) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }
}
