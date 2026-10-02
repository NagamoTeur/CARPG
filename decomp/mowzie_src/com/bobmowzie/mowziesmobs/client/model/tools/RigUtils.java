package com.bobmowzie.mowziesmobs.client.model.tools;

import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.processor.IBone;

public class RigUtils {
   public static Vec3 lerp(Vec3 v, Vec3 u, float alpha) {
      return new Vec3(
         (double)Mth.m_14179_(alpha, (float)v.m_7096_(), (float)u.m_7096_()),
         (double)Mth.m_14179_(alpha, (float)v.m_7098_(), (float)u.m_7098_()),
         (double)Mth.m_14179_(alpha, (float)v.m_7094_(), (float)u.m_7094_())
      );
   }

   public static Vec3 lerpAngles(Vec3 v, Vec3 u, float alpha) {
      return new Vec3(
         Math.toRadians((double)Mth.m_14189_(alpha, (float)Math.toDegrees(v.m_7096_()), (float)Math.toDegrees(u.m_7096_()))),
         Math.toRadians((double)Mth.m_14189_(alpha, (float)Math.toDegrees(v.m_7098_()), (float)Math.toDegrees(u.m_7098_()))),
         Math.toRadians((double)Mth.m_14189_(alpha, (float)Math.toDegrees(v.m_7094_()), (float)Math.toDegrees(u.m_7094_())))
      );
   }

   public static Vec3 blendAngles(Vec3 v, Vec3 u, float alpha) {
      return new Vec3(
         Math.toRadians(Mth.m_14175_(Math.toDegrees(v.m_7096_()) * (double)alpha + Math.toDegrees(u.m_7096_()))),
         Math.toRadians(Mth.m_14175_(Math.toDegrees(v.m_7098_()) * (double)alpha + Math.toDegrees(u.m_7098_()))),
         Math.toRadians(Mth.m_14175_(Math.toDegrees(v.m_7094_()) * (double)alpha + Math.toDegrees(u.m_7094_())))
      );
   }

   public static Quaternion matrixToQuaternion(Matrix3f matrix) {
      double tr = (double)(matrix.f_8134_ + matrix.f_8138_ + matrix.f_8142_);
      double qw = 0.0;
      double qx = 0.0;
      double qy = 0.0;
      double qz = 0.0;
      if (tr > 0.0) {
         double S = Math.sqrt(tr + 1.0) * 2.0;
         qw = 0.25 * S;
         qx = (double)(matrix.f_8141_ - matrix.f_8139_) / S;
         qy = (double)(matrix.f_8136_ - matrix.f_8140_) / S;
         qz = (double)(matrix.f_8137_ - matrix.f_8135_) / S;
      } else if (matrix.f_8134_ > matrix.f_8138_ & matrix.f_8134_ > matrix.f_8142_) {
         double S = Math.sqrt(1.0 + (double)matrix.f_8134_ - (double)matrix.f_8138_ - (double)matrix.f_8142_) * 2.0;
         qw = (double)(matrix.f_8141_ - matrix.f_8139_) / S;
         qx = 0.25 * S;
         qy = (double)(matrix.f_8135_ + matrix.f_8137_) / S;
         qz = (double)(matrix.f_8136_ + matrix.f_8140_) / S;
      } else if (matrix.f_8138_ > matrix.f_8142_) {
         double S = Math.sqrt(1.0 + (double)matrix.f_8138_ - (double)matrix.f_8134_ - (double)matrix.f_8142_) * 2.0;
         qw = (double)(matrix.f_8136_ - matrix.f_8140_) / S;
         qx = (double)(matrix.f_8135_ + matrix.f_8137_) / S;
         qy = 0.25 * S;
         qz = (double)(matrix.f_8139_ + matrix.f_8141_) / S;
      } else {
         double S = Math.sqrt(1.0 + (double)matrix.f_8142_ - (double)matrix.f_8134_ - (double)matrix.f_8138_) * 2.0;
         qw = (double)(matrix.f_8137_ - matrix.f_8135_) / S;
         qx = (double)(matrix.f_8136_ + matrix.f_8140_) / S;
         qy = (double)(matrix.f_8139_ + matrix.f_8141_) / S;
         qz = 0.25 * S;
      }

      return new Quaternion((float)qw, (float)qx, (float)qy, (float)qz);
   }

   public static void removeMatrixRotation(Matrix4f matrix) {
      matrix.f_27603_ = 1.0F;
      matrix.f_27608_ = 1.0F;
      matrix.f_27613_ = 1.0F;
      matrix.f_27604_ = 0.0F;
      matrix.f_27605_ = 0.0F;
      matrix.f_27607_ = 0.0F;
      matrix.f_27609_ = 0.0F;
      matrix.f_27611_ = 0.0F;
      matrix.f_27612_ = 0.0F;
   }

   public static void removeMatrixTranslation(Matrix4f matrix) {
      matrix.f_27606_ = 0.0F;
      matrix.f_27610_ = 0.0F;
      matrix.f_27614_ = 0.0F;
   }

   public static Quaternion betweenVectors(Vec3 u, Vec3 v) {
      Vec3 a = u.m_82537_(v);
      float w = (float)(Math.sqrt(u.m_82556_() * v.m_82556_()) + u.m_82526_(v));
      Quaternion q = new Quaternion((float)a.m_7096_(), -((float)a.m_7098_()), -((float)a.m_7094_()), w);
      q.m_80160_();
      return q;
   }

   public static Vector3f translationFromMatrix(Matrix4f matrix4f) {
      return new Vector3f(matrix4f.f_27606_, matrix4f.f_27610_, matrix4f.f_27614_);
   }

   public static Vector3f eulerAnglesZYXFromMatrix(Matrix4f matrix4f) {
      float thetaZ;
      float thetaY;
      float thetaX;
      if (matrix4f.f_27611_ < 1.0F) {
         if (matrix4f.f_27611_ > -1.0F) {
            thetaY = (float)Math.asin((double)(-matrix4f.f_27611_));
            thetaZ = (float)Math.atan2((double)matrix4f.f_27607_, (double)matrix4f.f_27603_);
            thetaX = (float)Math.atan2((double)matrix4f.f_27612_, (double)matrix4f.f_27613_);
         } else {
            thetaY = (float) (Math.PI / 2);
            thetaZ = -((float)Math.atan2((double)(-matrix4f.f_27609_), (double)matrix4f.f_27608_));
            thetaX = 0.0F;
         }
      } else {
         thetaY = (float) (-Math.PI / 2);
         thetaZ = (float)Math.atan2((double)(-matrix4f.f_27609_), (double)matrix4f.f_27608_);
         thetaX = 0.0F;
      }

      return new Vector3f(thetaX, thetaY, thetaZ);
   }

   public static Vector3f eulerAnglesXYZFromMatrix(Matrix4f matrix4f) {
      float thetaZ;
      float thetaY;
      float thetaX;
      if (matrix4f.f_27611_ < 1.0F) {
         if (matrix4f.f_27611_ > -1.0F) {
            thetaY = (float)Math.asin((double)matrix4f.f_27605_);
            thetaX = (float)Math.atan2((double)(-matrix4f.f_27609_), (double)matrix4f.f_27613_);
            thetaZ = (float)Math.atan2((double)(-matrix4f.f_27604_), (double)matrix4f.f_27603_);
         } else {
            thetaY = (float) (-Math.PI / 2);
            thetaX = -((float)Math.atan2((double)matrix4f.f_27607_, (double)matrix4f.f_27608_));
            thetaZ = 0.0F;
         }
      } else {
         thetaY = (float) (Math.PI / 2);
         thetaX = (float)Math.atan2((double)matrix4f.f_27607_, (double)matrix4f.f_27608_);
         thetaZ = 0.0F;
      }

      return new Vector3f(thetaX, thetaY, thetaZ);
   }

   public static class BlendShape3D {
      private final RigUtils.BlendShape3DEntry[] entries;

      public BlendShape3D(RigUtils.BlendShape3DEntry[] entries) {
         this.entries = entries;
      }

      public void evaluate(IBone bone, Vec3 dir) {
         this.evaluate(bone, dir, false);
      }

      private double[] getWeights(Vec3 dir) {
         double[] weights = new double[this.entries.length];
         double[] dotProducts = new double[this.entries.length];
         double totalDotProduct = 0.0;

         for (int i = 0; i < this.entries.length; i++) {
            RigUtils.BlendShape3DEntry entry = this.entries[i];
            double dot = 1.0 - entry.getWeight(dir);
            if (!(dot > 0.0)) {
               weights[i] = 1.0;
               return weights;
            }

            totalDotProduct += 1.0 / dot;
            dotProducts[i] = dot;
         }

         for (int i = 0; i < this.entries.length; i++) {
            double dot_prod = totalDotProduct * dotProducts[i];
            if (dot_prod > 0.0) {
               weights[i] = 1.0 / dot_prod;
            } else {
               weights[i] = 0.0;
            }
         }

         return weights;
      }

      private double[] getWeightsGradientBand(Vec3 dir) {
         double[] weights = new double[this.entries.length];
         double[] sqrdDistances = new double[this.entries.length];
         double[] angularDistances = new double[this.entries.length];
         double totalSqrdDistance = 0.0;
         double totalAngularDistance = 0.0;

         for (int i = 0; i < this.entries.length; i++) {
            RigUtils.BlendShape3DEntry entry = this.entries[i];
            double sqrdDistance = dir.m_82546_(entry.direction).m_82526_(dir.m_82546_(entry.direction));
            if (!(sqrdDistance > 0.0)) {
               weights[i] = 1.0;
               return weights;
            }

            double angularDistance = -(Mth.m_14008_(dir.m_82526_(entry.direction), -1.0, 1.0) - 1.0) * 0.5;
            totalSqrdDistance += 1.0 / sqrdDistance;
            if (angularDistance > 0.0) {
               totalAngularDistance += 1.0 / angularDistance;
            }

            sqrdDistances[i] = sqrdDistance;
            angularDistances[i] = angularDistance;
         }

         for (int i = 0; i < this.entries.length; i++) {
            double sqrdDistancex = totalSqrdDistance * sqrdDistances[i];
            double angularDistance = totalAngularDistance * angularDistances[i];
            if (sqrdDistancex > 0.0 && angularDistance > 0.0) {
               weights[i] = 1.0 / sqrdDistancex * 0.5 + 1.0 / angularDistance * 0.5;
            } else if (sqrdDistancex > 0.0) {
               weights[i] = 1.0 / sqrdDistancex * 0.5 + 0.5;
            } else {
               weights[i] = 0.0;
            }
         }

         return weights;
      }

      public void evaluate(IBone bone, Vec3 d, boolean mirrorX) {
         Vec3 dir = mirrorX ? d.m_82542_(-1.0, 1.0, 1.0) : d;
         dir = dir.m_82541_();
         double[] weights = this.getWeights(dir);
         RigUtils.BoneTransform transform = new RigUtils.BoneTransform(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

         for (int i = 0; i < this.entries.length; i++) {
            RigUtils.BlendShape3DEntry entry = this.entries[i];
            transform = entry.blend(transform, (float)Mth.m_14008_(weights[i], 0.0, 1.0));
         }

         transform.apply(bone, mirrorX);
      }
   }

   public static class BlendShape3DEntry {
      private RigUtils.BoneTransform transform;
      private Vec3 direction;
      private float power;

      public BlendShape3DEntry(RigUtils.BoneTransform transform, Vec3 direction, float power) {
         this.transform = transform;
         this.direction = direction.m_82541_();
         this.power = power;
      }

      public double getWeight(Vec3 dir) {
         double dot = dir.m_82541_().m_82526_(this.direction.m_82541_());
         dot = Math.max(dot, 0.0);
         return Math.pow(dot, 0.01 * (double)this.power);
      }

      public RigUtils.BoneTransform blend(RigUtils.BoneTransform other, float alpha) {
         return this.transform.blend(other, alpha);
      }
   }

   public static class BoneTransform {
      private final Vec3 translation;
      private final Vec3 rotation;
      private final Vec3 scale;

      public BoneTransform(double tx, double ty, double tz, double rx, double ry, double rz, double sx, double sy, double sz) {
         this.translation = new Vec3(tx, ty, tz);
         this.rotation = new Vec3(rx, ry, rz);
         this.scale = new Vec3(sx, sy, sz);
      }

      public BoneTransform(Vec3 t, Vec3 r, Vec3 s) {
         this.translation = t;
         this.rotation = r;
         this.scale = s;
      }

      public RigUtils.BoneTransform blend(RigUtils.BoneTransform other, float alpha) {
         return new RigUtils.BoneTransform(
            this.translation.m_82490_((double)alpha).m_82549_(other.translation),
            RigUtils.blendAngles(this.rotation, other.rotation, alpha),
            this.scale.m_82490_((double)alpha).m_82549_(other.scale)
         );
      }

      public void apply(IBone bone) {
         this.apply(bone, false);
      }

      public void apply(IBone bone, boolean mirrorX) {
         float mirror = mirrorX ? -1.0F : 1.0F;
         bone.setPositionX(bone.getPositionX() + mirror * (float)this.translation.m_7096_());
         bone.setPositionY(bone.getPositionY() + (float)this.translation.m_7098_());
         bone.setPositionZ(bone.getPositionZ() + (float)this.translation.m_7094_());
         bone.setRotationX(bone.getRotationX() + (float)this.rotation.m_7096_());
         bone.setRotationY(bone.getRotationY() + mirror * (float)this.rotation.m_7098_());
         bone.setRotationZ(bone.getRotationZ() + mirror * (float)this.rotation.m_7094_());
         bone.setScaleX(bone.getScaleX() * (float)this.scale.m_7096_());
         bone.setScaleY(bone.getScaleY() * (float)this.scale.m_7098_());
         bone.setScaleZ(bone.getScaleZ() * (float)this.scale.m_7094_());
      }
   }
}
