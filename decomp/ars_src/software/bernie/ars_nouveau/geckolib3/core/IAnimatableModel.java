package software.bernie.ars_nouveau.geckolib3.core;

import software.bernie.ars_nouveau.geckolib3.core.builder.Animation;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.processor.AnimationProcessor;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;

public interface IAnimatableModel<E> {
   default double getCurrentTick() {
      return (double)(System.nanoTime() / 1000000L) / 50.0;
   }

   default void setCustomAnimations(E animatable, int instanceId) {
      this.setCustomAnimations(animatable, instanceId, null);
   }

   default void setCustomAnimations(E animatable, int instanceId, AnimationEvent animationEvent) {
   }

   AnimationProcessor getAnimationProcessor();

   Animation getAnimation(String var1, IAnimatable var2);

   default IBone getBone(String boneName) {
      IBone bone = this.getAnimationProcessor().getBone(boneName);
      if (bone == null) {
         throw new RuntimeException("Could not find bone: " + boneName);
      } else {
         return bone;
      }
   }

   void setMolangQueries(IAnimatable var1, double var2);

   @Deprecated(
      forRemoval = true
   )
   default void setLivingAnimations(E animatable, Integer instanceId) {
      this.setCustomAnimations(animatable, instanceId);
   }

   @Deprecated(
      forRemoval = true
   )
   default void setLivingAnimations(E animatable, Integer instanceId, AnimationEvent animationEvent) {
      this.setCustomAnimations(animatable, instanceId, animationEvent);
   }
}
