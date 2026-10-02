package com.cerbon.bosses_of_mass_destruction.api.maelstrom.static_utilities;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.math.ReferencedAxisRotator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MathUtils {
   public static boolean withinDistance(Vec3 pos1, Vec3 pos2, double distance) {
      if (distance < 0.0) {
         throw new IllegalArgumentException("Distance cannot be negative");
      } else {
         return pos1.m_82557_(pos2) < Math.pow(distance, 2.0);
      }
   }

   public static boolean movingTowards(Vec3 center, Vec3 pos, Vec3 direction) {
      Vec3 directionTo = unNormedDirection(pos, center);
      return direction.m_82526_(directionTo) > 0.0;
   }

   public static Vec3 unNormedDirection(Vec3 source, Vec3 target) {
      return target.m_82546_(source);
   }

   public static void lineCallback(Vec3 start, Vec3 end, int points, BiConsumer<Vec3, Integer> callback) {
      Vec3 dir = end.m_82546_(start).m_82490_(1.0 / (double)(points - 1));
      Vec3 pos = start;

      for (int i = 0; i < points; i++) {
         callback.accept(pos, i);
         pos = pos.m_82549_(dir);
      }
   }

   public static void circleCallback(double radius, int points, Vec3 axis, Consumer<Vec3> callback) {
      double degrees = (Math.PI * 2) / (double)points;
      double axisYaw = directionToYaw(axis);
      ReferencedAxisRotator rotator = new ReferencedAxisRotator(VecUtils.yAxis, axis);

      for (int i = 0; i < points; i++) {
         double radians = (double)i * degrees;
         Vec3 offset = VecUtils.rotateVector(new Vec3(Math.sin(radians), 0.0, Math.cos(radians)).m_82490_(radius), VecUtils.yAxis, -axisYaw);
         Vec3 rotated = rotator.rotate(offset);
         callback.accept(rotated);
      }
   }

   public static Collection<Vec3> circlePoints(double radius, int points, Vec3 axis) {
      Collection<Vec3> vectors = new ArrayList<>();
      circleCallback(radius, points, axis, vectors::add);
      return vectors;
   }

   public static boolean willAABBFit(AABB aabb, Vec3 movement, Predicate<AABB> collision) {
      AtomicBoolean collided = new AtomicBoolean(false);
      int points = (int)Math.ceil(movement.m_82553_() / aabb.m_82309_());
      lineCallback(Vec3.f_82478_, movement, points, (vec3, integer) -> {
         if (collision.test(aabb.m_82383_(vec3))) {
            collided.set(true);
         }
      });
      return !collided.get();
   }

   public static float directionToPitch(Vec3 direction) {
      double x = direction.f_82479_;
      double z = direction.f_82481_;
      double y = direction.f_82480_;
      double h = Math.sqrt(x * x + z * z);
      return (float)Math.toDegrees(-Mth.m_14136_(y, h));
   }

   public static double directionToYaw(Vec3 direction) {
      double x = direction.m_7096_();
      double z = direction.m_7094_();
      return Math.toDegrees(Mth.m_14136_(z, x));
   }

   public static Vec3 lerpVec(float partialTicks, Vec3 vec1, Vec3 vec2) {
      double x = Mth.m_14139_((double)partialTicks, vec1.f_82479_, vec2.f_82479_);
      double y = Mth.m_14139_((double)partialTicks, vec1.f_82480_, vec2.f_82480_);
      double z = Mth.m_14139_((double)partialTicks, vec1.f_82481_, vec2.f_82481_);
      return new Vec3(x, y, z);
   }

   public static Vec3 axisOffset(Vec3 direction, Vec3 offset) {
      Vec3 forward = direction.m_82541_();
      Vec3 side = forward.m_82537_(VecUtils.yAxis).m_82541_();
      Vec3 up = side.m_82537_(forward).m_82541_();
      return forward.m_82490_(offset.f_82479_).m_82549_(side.m_82490_(offset.f_82481_)).m_82549_(up.m_82490_(offset.f_82480_));
   }

   public static boolean facingSameDirection(Vec3 direction1, Vec3 direction2) {
      return direction1.m_82526_(direction2) > 0.0;
   }

   public static int consecutiveSum(int firstNumber, int lastNumber) {
      return (lastNumber - firstNumber + 1) * (firstNumber + lastNumber) / 2;
   }

   public static float roundedStep(float n, List<Float> steps, boolean floor) {
      List<Float> sortableSteps = new ArrayList<>(steps);
      if (floor) {
         sortableSteps.sort(Collections.reverseOrder());

         for (Float step : sortableSteps) {
            if (step <= n) {
               return step;
            }
         }

         return sortableSteps.get(0);
      } else {
         Collections.sort(sortableSteps);

         for (Float stepx : sortableSteps) {
            if (stepx > n) {
               return stepx;
            }
         }

         return sortableSteps.get(sortableSteps.size() - 1);
      }
   }

   public static List<Vec3> buildBlockCircle(double radius) {
      int intRadius = (int)radius;
      double radiusSq = radius * radius;
      List<Vec3> points = new ArrayList<>();

      for (int x = -intRadius; x <= intRadius; x++) {
         for (int z = -intRadius; z <= intRadius; z++) {
            Vec3 pos = new Vec3((double)x, 0.0, (double)z);
            if (pos.m_82556_() <= radiusSq) {
               points.add(pos);
            }
         }
      }

      return points;
   }

   public static List<BlockPos> getBlocksInLine(BlockPos startPos, BlockPos endPos) {
      int x1 = startPos.m_123341_();
      int y1 = startPos.m_123342_();
      int z1 = startPos.m_123343_();
      int x2 = endPos.m_123341_();
      int y2 = endPos.m_123342_();
      int z2 = endPos.m_123343_();
      List<BlockPos> points = new ArrayList<>();
      points.add(startPos);
      int dx = Math.abs(x2 - x1);
      int dy = Math.abs(y2 - y1);
      int dz = Math.abs(z2 - z1);
      int xs = x2 > x1 ? 1 : -1;
      int ys = y2 > y1 ? 1 : -1;
      int zs = z2 > z1 ? 1 : -1;
      if (dx >= dy && dx >= dz) {
         int p1 = 2 * dy - dx;
         int p2 = 2 * dz - dx;

         while (x1 != x2) {
            x1 += xs;
            if (p1 >= 0) {
               y1 += ys;
               p1 -= 2 * dx;
            }

            if (p2 >= 0) {
               z1 += zs;
               p2 -= 2 * dx;
            }

            p1 += 2 * dy;
            p2 += 2 * dz;
            points.add(new BlockPos(x1, y1, z1));
         }
      } else if (dy >= dx && dy >= dz) {
         int p1 = 2 * dx - dy;
         int p2 = 2 * dz - dy;

         while (y1 != y2) {
            y1 += ys;
            if (p1 >= 0) {
               x1 += xs;
               p1 -= 2 * dy;
            }

            if (p2 >= 0) {
               z1 += zs;
               p2 -= 2 * dy;
            }

            p1 += 2 * dx;
            p2 += 2 * dz;
            points.add(new BlockPos(x1, y1, z1));
         }
      } else {
         int p1 = 2 * dy - dz;
         int p2 = 2 * dx - dz;

         while (z1 != z2) {
            z1 += zs;
            if (p1 >= 0) {
               y1 += ys;
               p1 -= 2 * dz;
            }

            if (p2 >= 0) {
               x1 += xs;
               p2 -= 2 * dz;
            }

            p1 += 2 * dy;
            p2 += 2 * dx;
            points.add(new BlockPos(x1, y1, z1));
         }
      }

      return points;
   }

   public static float ratioLerp(float time, float ratio, float maxAge, float partialTicks) {
      assert ratio <= 1.0F;

      assert ratio >= 0.0F;

      assert maxAge > 0.0F;

      float currentTime = Mth.m_14036_((time + partialTicks) / maxAge, 0.0F, 1.0F);
      return Math.max(0.0F, currentTime - ratio) / (1.0F - ratio);
   }
}
