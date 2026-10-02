package com.bobmowzie.mowziesmobs.client.model.tools.geckolib;

import com.bobmowzie.mowziesmobs.server.entity.IAnimationTickable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.controller.AnimationController.IAnimationPredicate;
import software.bernie.geckolib3.core.easing.EasingType;

public class MowzieAnimationController<T extends IAnimatable & IAnimationTickable> extends AnimationController<T> {
   private double tickOffset = 0.0;
   private double timingOffset;

   public MowzieAnimationController(T animatable, String name, float transitionLengthTicks, IAnimationPredicate<T> animationPredicate, double timingOffset) {
      super(animatable, name, transitionLengthTicks, animationPredicate);
      this.timingOffset = timingOffset;
   }

   public MowzieAnimationController(
      T animatable, String name, float transitionLengthTicks, EasingType easingType, IAnimationPredicate<T> animationPredicate, double timingOffset
   ) {
      super(animatable, name, transitionLengthTicks, easingType, animationPredicate);
      this.timingOffset = timingOffset;
   }

   public void playAnimation(T animatable, AnimationBuilder animationBuilder) {
      this.markNeedsReload();
      this.setAnimation(animationBuilder);
      this.currentAnimation = (Animation)this.animationQueue.poll();
      this.isJustStarting = true;
      this.adjustTick((double)animatable.tickTimer());
      this.transitionLengthTicks = 0.0;
   }

   protected double adjustTick(double tick) {
      if (this.shouldResetTick) {
         if (this.getAnimationState() == AnimationState.Transitioning) {
            this.tickOffset = tick;
         } else if (this.getAnimationState() == AnimationState.Running) {
            this.tickOffset = this.tickOffset + this.transitionLengthTicks;
         }

         this.shouldResetTick = false;
      }

      double adjustedTick = Math.max(tick - this.tickOffset, 0.0) + this.timingOffset;
      if (this.currentAnimation != null && this.currentAnimation.loop.isRepeatingAfterEnd()) {
         adjustedTick %= this.currentAnimation.animationLength;
      }

      if (adjustedTick == this.timingOffset) {
         this.isJustStarting = true;
      }

      return adjustedTick;
   }
}
