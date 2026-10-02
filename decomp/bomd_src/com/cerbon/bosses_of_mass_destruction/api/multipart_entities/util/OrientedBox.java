package com.cerbon.bosses_of_mass_destruction.api.multipart_entities.util;

import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class OrientedBox {
   private final Vec3 center;
   private final Vec3 halfExtents;
   private final QuaternionD rotation;
   private AABB extents;
   private Matrix3d matrix;
   private Matrix3d inverse;
   private Vec3[] vertices;
   private Vec3[] basis;

   public OrientedBox(AABB box) {
      this.center = box.m_82399_();
      this.halfExtents = new Vec3(box.m_82362_() / 2.0, box.m_82376_() / 2.0, box.m_82385_() / 2.0);
      this.rotation = QuaternionD.IDENTITY;
   }

   public OrientedBox(Vec3 center, Vec3 halfExtents, QuaternionD rotation) {
      this.center = center;
      this.halfExtents = halfExtents;
      this.rotation = rotation;
   }

   public OrientedBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, QuaternionD rotation) {
      this.center = new Vec3((minX + maxX) / 2.0, (minY + maxY) / 2.0, (minZ + maxZ) / 2.0);
      this.halfExtents = new Vec3((maxX - minX) / 2.0, (maxY - minY) / 2.0, (maxZ - minZ) / 2.0);
      this.rotation = rotation;
   }

   private OrientedBox(Vec3 center, Vec3 halfExtents, QuaternionD rotation, Matrix3d matrix, Matrix3d inverse, Vec3[] basis) {
      this.center = center;
      this.halfExtents = halfExtents;
      this.rotation = rotation;
      this.matrix = matrix;
      this.inverse = inverse;
      this.basis = basis;
   }

   public Matrix3d getMatrix() {
      if (this.matrix == null) {
         this.matrix = new Matrix3d(this.rotation);
      }

      return this.matrix;
   }

   public Matrix3d getInverse() {
      if (this.inverse == null) {
         this.inverse = this.getMatrix().invert();
      }

      return this.inverse;
   }

   public AABB getExtents() {
      if (this.extents == null) {
         this.extents = new AABB(this.halfExtents.m_82542_(-1.0, -1.0, -1.0), this.halfExtents);
      }

      return this.extents;
   }

   public Vec3[] getBasis() {
      if (this.basis == null) {
         this.basis = this.matrix.getBasis();
      }

      return this.basis;
   }

   public OrientedBox rotate(QuaternionD quaternion) {
      return QuaternionD.IDENTITY.equals(quaternion) ? this : new OrientedBox(this.center, this.halfExtents, this.rotation.hamiltonProduct(quaternion));
   }

   public OrientedBox translate(double x, double y, double z) {
      if (x == 0.0 && y == 0.0 && z == 0.0) {
         return this;
      } else {
         Matrix3d matrix = this.getMatrix();
         double transX = matrix.transformX(x, y, z);
         double transY = matrix.transformY(x, y, z);
         double transZ = matrix.transformZ(x, y, z);
         return new OrientedBox(this.center.m_82520_(transX, transY, transZ), this.halfExtents, this.rotation, matrix, this.inverse, this.basis);
      }
   }

   public OrientedBox transform(double x, double y, double z, double pivotX, double pivotY, double pivotZ, QuaternionD quaternion) {
      Vec3 vec = this.getMatrix().transform(x - pivotX, y - pivotY, z - pivotZ);
      boolean bl = quaternion.equals(QuaternionD.IDENTITY);
      return new OrientedBox(
            this.center.m_82549_(vec),
            this.halfExtents,
            this.rotation.hamiltonProduct(quaternion),
            bl ? this.matrix : null,
            bl ? this.inverse : null,
            bl ? this.basis : null
         )
         .translate(pivotX, pivotY, pivotZ);
   }

   public QuaternionD getRotation() {
      return this.rotation;
   }

   public Vec3 getCenter() {
      return this.center;
   }

   public Vec3 getHalfExtents() {
      return this.halfExtents;
   }

   public OrientedBox offset(double x, double y, double z) {
      return new OrientedBox(this.center.m_82520_(x, y, z), this.halfExtents, this.rotation, this.matrix, this.inverse, this.basis);
   }

   private void computeVertices() {
      AABB box = this.getExtents();
      Vec3[] vertices = getVertices(box);
      this.vertices = new Vec3[8];
      Matrix3d matrix = this.getMatrix();

      for (int i = 0; i < vertices.length; i++) {
         this.vertices[i] = matrix.transform(vertices[i]).m_82549_(this.center);
      }
   }

   public static Vec3[] getVertices(AABB box) {
      Vec3[] vertices = new Vec3[8];
      int index = 0;
      AxisDirection[] axisDirections = AxisDirection.values();

      for (AxisDirection x : axisDirections) {
         for (AxisDirection y : axisDirections) {
            for (AxisDirection z : axisDirections) {
               vertices[index++] = new Vec3(getPoint(box, x, Axis.X), getPoint(box, y, Axis.Y), getPoint(box, z, Axis.Z));
            }
         }
      }

      return vertices;
   }

   private static double getPoint(AABB box, AxisDirection direction, Axis axis) {
      return direction == AxisDirection.NEGATIVE ? box.m_82340_(axis) : box.m_82374_(axis);
   }

   public boolean intersects(AABB other) {
      return this.intersects(getVertices(other));
   }

   public boolean intersects(Vec3[] otherVertices) {
      if (this.vertices == null) {
         this.computeVertices();
      }

      Vec3[] vertices1 = this.vertices;
      Vec3[] normals1 = this.getBasis();

      for (Vec3 normal : normals1) {
         if (!sat(normal, vertices1, otherVertices)) {
            return false;
         }
      }

      Vec3[] normals2 = Matrix3d.IDENTITY_BASIS;

      for (Vec3 normalx : normals2) {
         if (!sat(normalx, vertices1, otherVertices)) {
            return false;
         }
      }

      for (int i = 0; i < normals1.length; i++) {
         for (int j = i; j < normals2.length; j++) {
            Vec3 normalxx = cross(normals1[i], normals2[j]);
            if (!sat(normalxx, vertices1, otherVertices)) {
               return false;
            }
         }
      }

      return true;
   }

   private static Vec3 cross(Vec3 first, Vec3 second) {
      return new Vec3(
         first.f_82480_ * second.f_82481_ - first.f_82481_ * second.f_82480_,
         first.f_82481_ * second.f_82479_ - first.f_82479_ * second.f_82481_,
         first.f_82479_ * second.f_82480_ - first.f_82480_ * second.f_82479_
      );
   }

   private static boolean sat(Vec3 normal, Vec3[] vertices1, Vec3[] vertices2) {
      double min1 = Double.MAX_VALUE;
      double max1 = -Double.MAX_VALUE;

      for (Vec3 d : vertices1) {
         double v = d.m_82526_(normal);
         min1 = Math.min(min1, v);
         max1 = Math.max(max1, v);
      }

      double min2 = Double.MAX_VALUE;
      double max2 = -Double.MAX_VALUE;

      for (Vec3 vec3d : vertices2) {
         double v = vec3d.m_82526_(normal);
         min2 = Math.min(min2, v);
         max2 = Math.max(max2, v);
      }

      return min1 <= min2 && min2 <= max1 || min2 <= min1 && min1 <= max2;
   }

   public double raycast(Vec3 start, Vec3 end) {
      Matrix3d inverse = this.getInverse();
      Vec3 d = inverse.transform(start.f_82479_ - this.center.f_82479_, start.f_82480_ - this.center.f_82480_, start.f_82481_ - this.center.f_82481_);
      Vec3 e = inverse.transform(end.f_82479_ - this.center.f_82479_, end.f_82480_ - this.center.f_82480_, end.f_82481_ - this.center.f_82481_);
      return this.raycast0(d, e);
   }

   private double raycast0(Vec3 start, Vec3 end) {
      double d = end.f_82479_ - start.f_82479_;
      double e = end.f_82480_ - start.f_82480_;
      double f = end.f_82481_ - start.f_82481_;
      double[] t = new double[]{1.0};
      Direction direction = traceCollisionSide(this.getExtents(), start, t, d, e, f);
      return direction != null ? t[0] : -1.0;
   }

   @Nullable
   private static Direction traceCollisionSide(AABB box, Vec3 intersectingVector, double[] traceDistanceResult, double xDelta, double yDelta, double zDelta) {
      Direction approachDirection = null;
      if (xDelta > 1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            xDelta,
            yDelta,
            zDelta,
            box.f_82288_,
            box.f_82289_,
            box.f_82292_,
            box.f_82290_,
            box.f_82293_,
            Direction.WEST,
            intersectingVector.f_82479_,
            intersectingVector.f_82480_,
            intersectingVector.f_82481_
         );
      } else if (xDelta < -1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            xDelta,
            yDelta,
            zDelta,
            box.f_82291_,
            box.f_82289_,
            box.f_82292_,
            box.f_82290_,
            box.f_82293_,
            Direction.EAST,
            intersectingVector.f_82479_,
            intersectingVector.f_82480_,
            intersectingVector.f_82481_
         );
      }

      if (yDelta > 1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            yDelta,
            zDelta,
            xDelta,
            box.f_82289_,
            box.f_82290_,
            box.f_82293_,
            box.f_82288_,
            box.f_82291_,
            Direction.DOWN,
            intersectingVector.f_82480_,
            intersectingVector.f_82481_,
            intersectingVector.f_82479_
         );
      } else if (yDelta < -1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            yDelta,
            zDelta,
            xDelta,
            box.f_82292_,
            box.f_82290_,
            box.f_82293_,
            box.f_82288_,
            box.f_82291_,
            Direction.UP,
            intersectingVector.f_82480_,
            intersectingVector.f_82481_,
            intersectingVector.f_82479_
         );
      }

      if (zDelta > 1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            zDelta,
            xDelta,
            yDelta,
            box.f_82290_,
            box.f_82288_,
            box.f_82291_,
            box.f_82289_,
            box.f_82292_,
            Direction.NORTH,
            intersectingVector.f_82481_,
            intersectingVector.f_82479_,
            intersectingVector.f_82480_
         );
      } else if (zDelta < -1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            zDelta,
            xDelta,
            yDelta,
            box.f_82293_,
            box.f_82288_,
            box.f_82291_,
            box.f_82289_,
            box.f_82292_,
            Direction.SOUTH,
            intersectingVector.f_82481_,
            intersectingVector.f_82479_,
            intersectingVector.f_82480_
         );
      }

      return approachDirection;
   }

   @Nullable
   private static Direction traceCollisionSide(
      double[] traceDistanceResult,
      Direction approachDirection,
      double xDelta,
      double yDelta,
      double zDelta,
      double begin,
      double minX,
      double maxX,
      double minZ,
      double maxZ,
      Direction resultDirection,
      double startX,
      double startY,
      double startZ
   ) {
      double d = (begin - startX) / xDelta;
      double e = startY + d * yDelta;
      double f = startZ + d * zDelta;
      if (0.0 < d && d < traceDistanceResult[0] && minX - 1.0E-7 < e && e < maxX + 1.0E-7 && minZ - 1.0E-7 < f && f < maxZ + 1.0E-7) {
         traceDistanceResult[0] = d;
         return resultDirection;
      } else {
         return approachDirection;
      }
   }

   public boolean contains(double x, double y, double z) {
      x -= this.center.f_82479_;
      y -= this.center.f_82480_;
      z -= this.center.f_82481_;
      double transX = this.getMatrix().transformX(x, y, z);
      double transY = this.getMatrix().transformY(x, y, z);
      double transZ = this.getMatrix().transformZ(x, y, z);
      return this.getExtents().m_82393_(transX, transY, transZ);
   }

   public double getMax(Axis axis) {
      Matrix3d matrix = this.getMatrix();

      return switch (axis) {
         case X -> Math.max(matrix.m00, Math.max(matrix.m01, matrix.m02)) * this.halfExtents.f_82479_ + this.center.f_82479_;
         case Y -> Math.max(matrix.m10, Math.max(matrix.m11, matrix.m12)) * this.halfExtents.f_82480_ + this.center.f_82480_;
         case Z -> Math.max(matrix.m20, Math.max(matrix.m21, matrix.m22)) * this.halfExtents.f_82481_ + this.center.f_82481_;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public double getMin(Axis axis) {
      Matrix3d matrix = this.getMatrix();

      return switch (axis) {
         case X -> Math.min(matrix.m00, Math.min(matrix.m01, matrix.m02)) * this.halfExtents.f_82479_ + this.center.f_82479_;
         case Y -> Math.min(matrix.m10, Math.min(matrix.m11, matrix.m12)) * this.halfExtents.f_82480_ + this.center.f_82480_;
         case Z -> Math.min(matrix.m20, Math.min(matrix.m21, matrix.m22)) * this.halfExtents.f_82481_ + this.center.f_82481_;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public OrientedBox expand(double x, double y, double z) {
      return x == 0.0 && y == 0.0 && z == 0.0
         ? this
         : new OrientedBox(this.center, this.halfExtents.m_82520_(x / 2.0, y / 2.0, z / 2.0), this.rotation, this.matrix, this.inverse, this.basis);
   }
}
