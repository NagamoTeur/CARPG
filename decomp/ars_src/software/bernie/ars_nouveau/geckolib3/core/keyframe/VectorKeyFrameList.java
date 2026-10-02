package software.bernie.ars_nouveau.geckolib3.core.keyframe;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;

public class VectorKeyFrameList<T extends KeyFrame> {
   public List<T> xKeyFrames;
   public List<T> yKeyFrames;
   public List<T> zKeyFrames;

   public VectorKeyFrameList(List<T> XKeyFrames, List<T> YKeyFrames, List<T> ZKeyFrames) {
      this.xKeyFrames = XKeyFrames;
      this.yKeyFrames = YKeyFrames;
      this.zKeyFrames = ZKeyFrames;
   }

   public VectorKeyFrameList() {
      this.xKeyFrames = new ObjectArrayList();
      this.yKeyFrames = new ObjectArrayList();
      this.zKeyFrames = new ObjectArrayList();
   }

   public double getLastKeyframeTime() {
      double xTime = 0.0;

      for (T frame : this.xKeyFrames) {
         xTime += frame.getLength();
      }

      double yTime = 0.0;

      for (T frame : this.yKeyFrames) {
         yTime += frame.getLength();
      }

      double zTime = 0.0;

      for (T frame : this.zKeyFrames) {
         zTime += frame.getLength();
      }

      return Math.max(xTime, Math.max(yTime, zTime));
   }
}
