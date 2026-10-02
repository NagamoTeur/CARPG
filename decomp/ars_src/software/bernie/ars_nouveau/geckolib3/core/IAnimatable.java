package software.bernie.ars_nouveau.geckolib3.core;

import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;

public interface IAnimatable {
   void registerControllers(AnimationData var1);

   AnimationFactory getFactory();
}
