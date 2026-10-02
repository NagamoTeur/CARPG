package software.bernie.ars_nouveau.geckolib3.core.keyframe;

import software.bernie.ars_nouveau.shadowed.eliotlash.mclib.math.IValue;

public class BoneAnimation {
   public final String boneName;
   public VectorKeyFrameList<KeyFrame<IValue>> rotationKeyFrames;
   public VectorKeyFrameList<KeyFrame<IValue>> positionKeyFrames;
   public VectorKeyFrameList<KeyFrame<IValue>> scaleKeyFrames;

   public BoneAnimation(String boneName) {
      this.boneName = boneName;
   }
}
