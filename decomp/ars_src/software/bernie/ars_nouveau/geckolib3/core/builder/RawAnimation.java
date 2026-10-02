package software.bernie.ars_nouveau.geckolib3.core.builder;

import java.util.Objects;

public class RawAnimation {
   public String animationName;
   public ILoopType loopType;

   public RawAnimation(String animationName, ILoopType loop) {
      this.animationName = animationName;
      this.loopType = loop;
   }

   @Deprecated(
      forRemoval = true
   )
   public RawAnimation(String animationName, boolean loop) {
      this(animationName, loop ? ILoopType.EDefaultLoopTypes.LOOP : ILoopType.EDefaultLoopTypes.PLAY_ONCE);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else {
         return !(obj instanceof RawAnimation animation) ? false : animation.loopType == this.loopType && animation.animationName.equals(this.animationName);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.animationName, this.loopType);
   }
}
