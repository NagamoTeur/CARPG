package software.bernie.ars_nouveau.geckolib3.util;

import net.minecraft.client.model.geom.ModelPart;
import software.bernie.ars_nouveau.geckolib3.core.processor.IBone;

public class GeoUtils {
   public static void copyRotations(ModelPart from, IBone to) {
      to.setRotationX(-from.f_104203_);
      to.setRotationY(-from.f_104204_);
      to.setRotationZ(from.f_104205_);
   }
}
