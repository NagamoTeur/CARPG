package com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class VecUtils {
   public static final Vec3 xAxis = new Vec3(1.0, 0.0, 0.0);
   public static final Vec3 yAxis = new Vec3(0.0, 1.0, 0.0);
   public static final Vec3 zAxis = new Vec3(0.0, 0.0, 1.0);
   public static final Vec3 unit = new Vec3(1.0, 1.0, 1.0);

   public static Vec3 yOffset(Vec3 vec, double i) {
      return vec.m_82520_(0.0, i, 0.0);
   }

   public static Vec3 planeProject(Vec3 vec, Vec3 planeVector) {
      return vec.m_82546_(planeVector.m_82542_(vec.m_82526_(planeVector), vec.m_82526_(planeVector), vec.m_82526_(planeVector)));
   }

   public static Vec3 asVec3(BlockPos blockPos) {
      return new Vec3((double)blockPos.m_123341_(), (double)blockPos.m_123342_(), (double)blockPos.m_123343_());
   }

   public static Vec3 negateServer(Vec3 vec) {
      return vec.m_82542_(-1.0, -1.0, -1.0);
   }

   public static Vec3 coerceAtLeast(Vec3 vec1, Vec3 vec2) {
      return new Vec3(Math.max(vec1.m_7096_(), vec2.m_7096_()), Math.max(vec1.m_7098_(), vec2.m_7098_()), Math.max(vec1.m_7094_(), vec2.m_7094_()));
   }

   public static Vec3 coerceAtMost(Vec3 vec1, Vec3 vec2) {
      return new Vec3(Math.min(vec1.m_7096_(), vec2.m_7096_()), Math.min(vec1.m_7098_(), vec2.m_7098_()), Math.min(vec1.m_7094_(), vec2.m_7094_()));
   }

   public static double unsignedAngle(Vec3 vec, Vec3 b) {
      double dot = vec.m_82526_(b);
      double lengths = vec.m_82553_() * b.m_82553_();
      if (lengths == 0.0) {
         return 0.0;
      } else {
         double cos = Math.max(-1.0, Math.min(1.0, dot / lengths));
         return Math.toDegrees(Math.acos(cos));
      }
   }

   public static Vec3 rotateVector(Vec3 vec, Vec3 axis, double degrees) {
      double theta = Math.toRadians(degrees);
      Vec3 normedAxis = axis.m_82541_();
      double x = vec.m_7096_();
      double y = vec.m_7098_();
      double z = vec.m_7094_();
      double u = normedAxis.m_7096_();
      double v = normedAxis.m_7098_();
      double w = normedAxis.m_7094_();
      double v1 = u * x + v * y + w * z;
      double xPrime = u * v1 * (1.0 - Math.cos(theta)) + x * Math.cos(theta) + (-w * y + v * z) * Math.sin(theta);
      double yPrime = v * v1 * (1.0 - Math.cos(theta)) + y * Math.cos(theta) + (w * x - u * z) * Math.sin(theta);
      double zPrime = w * v1 * (1.0 - Math.cos(theta)) + z * Math.cos(theta) + (-v * x + u * y) * Math.sin(theta);
      return new Vec3(xPrime, yPrime, zPrime);
   }
}
