package software.bernie.ars_nouveau.geckolib3.core.manager;

import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;

public class InstancedAnimationFactory extends AnimationFactory {
   private AnimationData animationData;

   public InstancedAnimationFactory(IAnimatable animatable) {
      super(animatable);
   }

   @Override
   public AnimationData getOrCreateAnimationData(int uniqueID) {
      if (this.animationData == null) {
         this.animationData = new AnimationData();
         this.animatable.registerControllers(this.animationData);
      }

      return this.animationData;
   }
}
