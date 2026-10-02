package com.aizistral.enigmaticlegacy.objects;

import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

public class Vector3 {
   public static final Vector3 ZERO = new Vector3(0.0, 0.0, 0.0);
   public static final Vector3 ONE = new Vector3(1.0, 1.0, 1.0);
   public static final Vector3 CENTER = new Vector3(0.5, 0.5, 0.5);
   public final double x;
   public final double y;
   public final double z;

   public Vector3(double d, double d1, double d2) {
      this.x = d;
      this.y = d1;
      this.z = d2;
   }

   public Vector3(Vec3 vec) {
      this(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public static Vector3 fromBlockPos(BlockPos pos) {
      return new Vector3((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_());
   }

   public static Vector3 fromEntity(Entity e) {
      return new Vector3(e.m_20185_(), e.m_20186_(), e.m_20189_());
   }

   public static Vector3 fromEntityCenter(Entity e) {
      return new Vector3(e.m_20185_(), e.m_20186_() - e.m_6049_() + (double)(e.m_20206_() / 2.0F), e.m_20189_());
   }

   public static Vector3 fromTileEntity(BlockEntity e) {
      return fromBlockPos(e.m_58899_());
   }

   public static Vector3 fromTileEntityCenter(BlockEntity e) {
      return fromTileEntity(e).add(0.5);
   }

   public double dotProduct(Vector3 vec) {
      double d = vec.x * this.x + vec.y * this.y + vec.z * this.z;
      if (d > 1.0 && d < 1.00001) {
         d = 1.0;
      } else if (d < -1.0 && d > -1.00001) {
         d = -1.0;
      }

      return d;
   }

   public double dotProduct(double d, double d1, double d2) {
      return d * this.x + d1 * this.y + d2 * this.z;
   }

   public Vector3 crossProduct(Vector3 vec) {
      double d = this.y * vec.z - this.z * vec.y;
      double d1 = this.z * vec.x - this.x * vec.z;
      double d2 = this.x * vec.y - this.y * vec.x;
      return new Vector3(d, d1, d2);
   }

   public Vector3 add(double d, double d1, double d2) {
      return new Vector3(this.x + d, this.y + d1, this.z + d2);
   }

   public Vector3 add(Vector3 vec) {
      return this.add(vec.x, vec.y, vec.z);
   }

   public Vector3 add(double d) {
      return this.add(d, d, d);
   }

   public Vector3 subtract(Vector3 vec) {
      return new Vector3(this.x - vec.x, this.y - vec.y, this.z - vec.z);
   }

   public Vector3 multiply(double d) {
      return this.multiply(d, d, d);
   }

   public Vector3 multiply(Vector3 f) {
      return this.multiply(f.x, f.y, f.z);
   }

   public Vector3 multiply(double fx, double fy, double fz) {
      return new Vector3(this.x * fx, this.y * fy, this.z * fz);
   }

   public double mag() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   public double magSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public Vector3 normalize() {
      double d = this.mag();
      return d != 0.0 ? this.multiply(1.0 / d) : this;
   }

   public Vector3 normalize(double normalizeFactor) {
      double d = this.mag();
      return d != 0.0 ? this.multiply(normalizeFactor / d) : this;
   }

   @Override
   public String toString() {
      MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
      return "Vector3(" + new BigDecimal(this.x, cont) + ", " + new BigDecimal(this.y, cont) + ", " + new BigDecimal(this.z, cont) + ")";
   }

   public Vector3 perpendicular() {
      return this.z == 0.0 ? this.zCrossProduct() : this.xCrossProduct();
   }

   public Vector3 xCrossProduct() {
      double d = this.z;
      double d1 = -this.y;
      return new Vector3(0.0, d, d1);
   }

   public Vector3 zCrossProduct() {
      double d = this.y;
      double d1 = -this.x;
      return new Vector3(d, d1, 0.0);
   }

   public Vector3 yCrossProduct() {
      double d = -this.z;
      double d1 = this.x;
      return new Vector3(d, 0.0, d1);
   }

   public Vec3 toVec3D() {
      return new Vec3(this.x, this.y, this.z);
   }

   public double angle(Vector3 vec) {
      return Math.acos(this.normalize().dotProduct(vec.normalize()));
   }

   public boolean isInside(AABB aabb) {
      return this.x >= aabb.f_82288_
         && this.y >= aabb.f_82292_
         && this.z >= aabb.f_82290_
         && this.x < aabb.f_82291_
         && this.y < aabb.f_82292_
         && this.z < aabb.f_82293_;
   }

   public boolean isZero() {
      return this.x == 0.0 && this.y == 0.0 && this.z == 0.0;
   }

   public boolean isAxial() {
      return this.x == 0.0 ? this.y == 0.0 || this.z == 0.0 : this.y == 0.0 && this.z == 0.0;
   }

   public Vector3f vector3f() {
      return new Vector3f((float)this.x, (float)this.y, (float)this.z);
   }

   public Vector4f vector4f() {
      return new Vector4f((float)this.x, (float)this.y, (float)this.z, 1.0F);
   }

   @OnlyIn(Dist.CLIENT)
   public void glVertex() {
      GL11.glVertex3d(this.x, this.y, this.z);
   }

   public Vector3 negate() {
      return new Vector3(-this.x, -this.y, -this.z);
   }

   public double scalarProject(Vector3 b) {
      double l = b.mag();
      return l == 0.0 ? 0.0 : this.dotProduct(b) / l;
   }

   public Vector3 project(Vector3 b) {
      double l = b.magSquared();
      if (l == 0.0) {
         return ZERO;
      } else {
         double m = this.dotProduct(b) / l;
         return b.multiply(m);
      }
   }

   public Vector3 rotate(double angle, Vector3 axis) {
      return Quat.aroundAxis(axis.normalize(), angle).rotate(this);
   }

   @Override
   public boolean equals(Object o) {
      return !(o instanceof Vector3 v) ? false : this.x == v.x && this.y == v.y && this.z == v.z;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.x, this.y, this.z);
   }
}
