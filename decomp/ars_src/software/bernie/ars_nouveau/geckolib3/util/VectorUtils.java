package software.bernie.ars_nouveau.geckolib3.util;

import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

public class VectorUtils {
   public static Vec3 fromArray(double[] array) {
      Validate.validIndex(ArrayUtils.toObject(array), 2);
      return new Vec3(array[0], array[1], array[2]);
   }

   public static Vector3f fromArray(float[] array) {
      Validate.validIndex(ArrayUtils.toObject(array), 2);
      return new Vector3f(array[0], array[1], array[2]);
   }

   public static Vector3f convertDoubleToFloat(Vec3 vector) {
      return new Vector3f((float)vector.f_82479_, (float)vector.f_82480_, (float)vector.f_82481_);
   }

   public static Vec3 convertFloatToDouble(Vector3f vector) {
      return new Vec3((double)vector.m_122239_(), (double)vector.m_122260_(), (double)vector.m_122269_());
   }
}
