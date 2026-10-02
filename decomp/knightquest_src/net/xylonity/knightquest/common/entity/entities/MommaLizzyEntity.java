package net.xylonity.knightquest.common.entity.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

public class MommaLizzyEntity extends Animal implements IAnimatable {
   private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
   private boolean condicion = false;
   private int a = 1;

   public MommaLizzyEntity(EntityType<? extends Animal> entityType, Level world) {
      super(entityType, world);
   }

   public static AttributeSupplier setAttributes() {
      return Animal.m_21552_()
         .m_22268_(Attributes.f_22276_, 10.0)
         .m_22268_(Attributes.f_22281_, 0.5)
         .m_22268_(Attributes.f_22283_, 1.0)
         .m_22268_(Attributes.f_22279_, 0.6F)
         .m_22265_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new AvoidEntityGoal(this, Player.class, 80.0F, 0.75, 0.75));
      this.f_21345_.m_25352_(2, new RandomSwimmingGoal(this, 0.6, 1));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(4, new AvoidEntityGoal(this, Monster.class, 80.0F, 0.6F, 0.6F));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Villager.class, 8.0F));
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, "controller", 0.0F, this::predicate));
   }

   private boolean isPlayerNearby() {
      return this.m_9236_()
         .m_45976_(Player.class, this.m_20191_().m_82400_(2.0))
         .stream()
         .anyMatch(player -> !player.m_6144_() && player.m_20280_(this) <= 4.0);
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

   @Nullable
   protected SoundEvent m_7975_(DamageSource pDamageSource) {
      return SoundEvents.f_12536_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_144062_;
   }

   protected void m_7355_(BlockPos pPos, BlockState pState) {
      this.m_5496_(SoundEvents.f_12624_, 0.15F, 1.0F);
   }

   @Nullable
   public AgeableMob m_142606_(ServerLevel serverLevel, AgeableMob ageableMob) {
      return null;
   }
}
