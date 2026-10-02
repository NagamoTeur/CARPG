package com.cerbon.bosses_of_mass_destruction.entity.util.animation;

import java.util.function.Function;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController.IAnimationPredicate;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class AnimationPredicate<T extends IAnimatable> implements IAnimationPredicate<T> {
   private final Function<AnimationEvent<?>, PlayState> predicate;

   public AnimationPredicate(Function<AnimationEvent<?>, PlayState> predicate) {
      this.predicate = predicate;
   }

   public PlayState test(AnimationEvent<T> animationEvent) {
      return this.predicate.apply(animationEvent);
   }
}
