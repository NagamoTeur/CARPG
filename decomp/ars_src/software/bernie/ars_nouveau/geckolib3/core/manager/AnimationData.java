package software.bernie.ars_nouveau.geckolib3.core.manager;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;
import software.bernie.ars_nouveau.geckolib3.core.snapshot.BoneSnapshot;

public class AnimationData {
   private Map<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection;
   private Map<String, AnimationController> animationControllers = new Object2ObjectOpenHashMap();
   public double tick;
   public boolean isFirstTick = true;
   private double resetTickLength = 1.0;
   public double startTick = -1.0;
   public Object ticker;
   public boolean shouldPlayWhilePaused = false;

   public AnimationData() {
      this.boneSnapshotCollection = new Object2ObjectOpenHashMap();
   }

   public AnimationController addAnimationController(AnimationController value) {
      return this.animationControllers.put(value.getName(), value);
   }

   public Map<String, Pair<IBone, BoneSnapshot>> getBoneSnapshotCollection() {
      return this.boneSnapshotCollection;
   }

   public void setBoneSnapshotCollection(HashMap<String, Pair<IBone, BoneSnapshot>> boneSnapshotCollection) {
      this.boneSnapshotCollection = boneSnapshotCollection;
   }

   public void clearSnapshotCache() {
      this.boneSnapshotCollection = new HashMap<>();
   }

   public double getResetSpeed() {
      return this.resetTickLength;
   }

   public void setResetSpeedInTicks(double resetTickLength) {
      this.resetTickLength = resetTickLength < 0.0 ? 0.0 : resetTickLength;
   }

   public Map<String, AnimationController> getAnimationControllers() {
      return this.animationControllers;
   }
}
