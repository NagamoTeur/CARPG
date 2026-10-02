package software.bernie.ars_nouveau.geckolib3.core.manager;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;

public class SingletonAnimationFactory extends AnimationFactory {
   private final Int2ObjectOpenHashMap<AnimationData> animationDataMap = new Int2ObjectOpenHashMap();

   public SingletonAnimationFactory(IAnimatable animatable) {
      super(animatable);
   }

   @Override
   public AnimationData getOrCreateAnimationData(int uniqueID) {
      if (!this.animationDataMap.containsKey(uniqueID)) {
         AnimationData data = new AnimationData();
         this.animatable.registerControllers(data);
         this.animationDataMap.put(uniqueID, data);
      }

      return (AnimationData)this.animationDataMap.get(uniqueID);
   }
}
