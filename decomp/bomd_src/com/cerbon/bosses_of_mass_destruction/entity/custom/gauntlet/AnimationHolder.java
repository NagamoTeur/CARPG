package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.util.BaseEntity;
import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityEventHandler;
import com.cerbon.bosses_of_mass_destruction.entity.util.animation.AnimationPredicate;
import java.util.Map;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;

public class AnimationHolder implements IEntityEventHandler {
   private final BaseEntity entity;
   private final Map<Byte, AnimationHolder.Animation> animationStatusFlags;
   private final Byte stopAttackByte;
   private final int transition;
   private AnimationHolder.Animation nextAnimation;
   private boolean doIdleAnimation = true;
   private final AnimationPredicate<BaseEntity> attack = new AnimationPredicate<>(
      animationState -> {
         AnimationHolder.Animation animationData = this.nextAnimation;
         this.nextAnimation = null;
         if (animationData != null) {
            animationState.getController().markNeedsReload();
            animationState.getController()
               .setAnimation(new AnimationBuilder().addAnimation(animationData.animationName, false).addAnimation(animationData.idleAnimationName, true));
         }

         if (this.doIdleAnimation) {
            animationState.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
         }

         return PlayState.CONTINUE;
      }
   );

   public AnimationHolder(BaseEntity entity, Map<Byte, AnimationHolder.Animation> animationStatusFlags, Byte stopAttackByte, int transition) {
      this.entity = entity;
      this.animationStatusFlags = animationStatusFlags;
      this.stopAttackByte = stopAttackByte;
      this.transition = transition;
   }

   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController(this.entity, "attack", (float)this.transition, this.attack));
   }

   @Override
   public void handleEntityEvent(byte status) {
      if (this.animationStatusFlags.containsKey(status)) {
         this.doIdleAnimation = false;
         this.nextAnimation = this.animationStatusFlags.get(status);
      }

      if (status == this.stopAttackByte) {
         this.doIdleAnimation = true;
      }
   }

   public static record Animation(String animationName, String idleAnimationName) {
   }
}
