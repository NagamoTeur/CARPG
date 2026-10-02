package software.bernie.ars_nouveau.shadowed.eliotlash.mclib.utils;

public class MathHelper {
   public static float wrapDegrees(float value) {
      value %= 360.0F;
      if (value >= 180.0F) {
         value -= 360.0F;
      }

      if (value < -180.0F) {
         value += 360.0F;
      }

      return value;
   }

   public static double wrapDegrees(double value) {
      value %= 360.0;
      if (value >= 180.0) {
         value -= 360.0;
      }

      if (value < -180.0) {
         value += 360.0;
      }

      return value;
   }

   public static int wrapDegrees(int angle) {
      angle %= 360;
      if (angle >= 180) {
         angle -= 360;
      }

      if (angle < -180) {
         angle += 360;
      }

      return angle;
   }
}
