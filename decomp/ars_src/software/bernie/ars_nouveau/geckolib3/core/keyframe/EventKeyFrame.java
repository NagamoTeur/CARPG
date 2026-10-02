package software.bernie.ars_nouveau.geckolib3.core.keyframe;

public class EventKeyFrame<T> {
   private final T eventData;
   private final double startTick;

   public EventKeyFrame(double startTick, T eventData) {
      this.startTick = startTick;
      this.eventData = eventData;
   }

   public T getEventData() {
      return this.eventData;
   }

   public double getStartTick() {
      return this.startTick;
   }
}
