package com.aizistral.enigmaticlegacy.objects;

import java.util.Arrays;
import java.util.Formatter;
import java.util.Locale;

public final class Quat {
   public static final Quat IDENTITY = new Quat(0.0, 0.0, 0.0, 1.0);
   public final double x;
   public final double y;
   public final double z;
   public final double s;
   private final int hashCode;

   public Quat(double d, double d1, double d2, double d3) {
      this.x = d1;
      this.y = d2;
      this.z = d3;
      this.s = d;
      this.hashCode = Arrays.hashCode(new double[]{d, d1, d2, d3});
   }

   public static Quat aroundAxis(double ax, double ay, double az, double angle) {
      angle *= 0.5;
      double d4 = Math.sin(angle);
      return new Quat(Math.cos(angle), ax * d4, ay * d4, az * d4);
   }

   public Quat multiply(Quat quat) {
      double d = this.s * quat.s - this.x * quat.x - this.y * quat.y - this.z * quat.z;
      double d1 = this.s * quat.x + this.x * quat.s - this.y * quat.z + this.z * quat.y;
      double d2 = this.s * quat.y + this.x * quat.z + this.y * quat.s - this.z * quat.x;
      double d3 = this.s * quat.z - this.x * quat.y + this.y * quat.x + this.z * quat.s;
      return new Quat(d1, d2, d3, d);
   }

   public Quat rightMultiply(Quat quat) {
      double d = this.s * quat.s - this.x * quat.x - this.y * quat.y - this.z * quat.z;
      double d1 = this.s * quat.x + this.x * quat.s + this.y * quat.z - this.z * quat.y;
      double d2 = this.s * quat.y - this.x * quat.z + this.y * quat.s + this.z * quat.x;
      double d3 = this.s * quat.z + this.x * quat.y - this.y * quat.x + this.z * quat.s;
      return new Quat(d1, d2, d3, d);
   }

   public double mag() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.s * this.s);
   }

   public Quat normalize() {
      double d = this.mag();
      if (d == 0.0) {
         return this;
      } else {
         d = 1.0 / d;
         return new Quat(this.x * d, this.y * d, this.z * d, this.s * d);
      }
   }

   public Vector3 rotate(Vector3 vec) {
      double d = -this.x * vec.x - this.y * vec.y - this.z * vec.z;
      double d1 = this.s * vec.x + this.y * vec.z - this.z * vec.y;
      double d2 = this.s * vec.y - this.x * vec.z + this.z * vec.x;
      double d3 = this.s * vec.z + this.x * vec.y - this.y * vec.x;
      double vx = d1 * this.s - d * this.x - d2 * this.z + d3 * this.y;
      double vy = d2 * this.s - d * this.y + d1 * this.z - d3 * this.x;
      double vz = d3 * this.s - d * this.z - d1 * this.y + d2 * this.x;
      return new Vector3(vx, vy, vz);
   }

   @Override
   public int hashCode() {
      return this.hashCode;
   }

   @Override
   public boolean equals(Object o) {
      return o instanceof Quat && ((Quat)o).x == this.x && ((Quat)o).y == this.y && ((Quat)o).z == this.z && ((Quat)o).s == this.s;
   }

   @Override
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      Formatter formatter = new Formatter(stringbuilder, Locale.US);
      formatter.format("Quaternion:%n");
      formatter.format("  < %f %f %f %f >%n", this.s, this.x, this.y, this.z);
      formatter.close();
      return stringbuilder.toString();
   }

   public static Quat aroundAxis(Vector3 axis, double angle) {
      return aroundAxis(axis.x, axis.y, axis.z, angle);
   }
}
