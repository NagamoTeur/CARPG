package software.bernie.ars_nouveau.geckolib3.core.builder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.BoneAnimation;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.EventKeyFrame;
import software.bernie.ars_nouveau.geckolib3.core.keyframe.ParticleEventKeyFrame;

public class Animation {
   public String animationName;
   public double animationLength = -1.0;
   public ILoopType loop = ILoopType.EDefaultLoopTypes.LOOP;
   public List<BoneAnimation> boneAnimations;
   public List<EventKeyFrame<String>> soundKeyFrames = new ObjectArrayList();
   public List<ParticleEventKeyFrame> particleKeyFrames = new ObjectArrayList();
   public List<EventKeyFrame<String>> customInstructionKeyframes = new ObjectArrayList();
}
