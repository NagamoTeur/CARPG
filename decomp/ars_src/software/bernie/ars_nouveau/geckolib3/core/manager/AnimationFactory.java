package software.bernie.ars_nouveau.geckolib3.core.manager;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;

public class AnimationFactory {
   protected final IAnimatable animatable;
   private final Int2ObjectOpenHashMap<AnimationData> animationDataMap = new Int2ObjectOpenHashMap();

   @Deprecated(
      forRemoval = true
   )
   public AnimationFactory(IAnimatable animatable) {
      this.animatable = animatable;
   }

   public AnimationData getOrCreateAnimationData(int uniqueID) {
      if (!this.animationDataMap.containsKey(uniqueID)) {
         AnimationData data = new AnimationData();
         this.animatable.registerControllers(data);
         this.animationDataMap.put(uniqueID, data);
      }

      return (AnimationData)this.animationDataMap.get(uniqueID);
   }

   @Deprecated(
      forRemoval = true
   )
   public AnimationData getOrCreateAnimationData(Integer uniqueID) {
      return this.getOrCreateAnimationData(uniqueID.intValue());
   }
}
