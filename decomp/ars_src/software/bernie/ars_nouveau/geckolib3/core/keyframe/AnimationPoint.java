package software.bernie.ars_nouveau.geckolib3.core.keyframe;

public class AnimationPoint {
   public final double currentTick;
   public final double animationEndTick;
   public final double animationStartValue;
   public final double animationEndValue;
   public final KeyFrame keyframe;

   public AnimationPoint(KeyFrame keyframe, double tick, double animationEndTick, double animationStartValue, double animationEndValue) {
      this.keyframe = keyframe;
      this.currentTick = tick;
      this.animationEndTick = animationEndTick;
      this.animationStartValue = animationStartValue;
      this.animationEndValue = animationEndValue;
   }

   @Override
   public String toString() {
      return "Tick: "
         + this.currentTick
         + " | End Tick: "
         + this.animationEndTick
         + " | Start Value: "
         + this.animationStartValue
         + " | End Value: "
         + this.animationEndValue;
   }
}
