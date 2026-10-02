package com.bobmowzie.mowziesmobs.client.model.tools.dynamics;

import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class DynamicChain {
   private Vec3[] p;
   private Vec3[] v;
   private Vec3[] a;
   private Vec3[] F;
   private float[] m;
   private float[] d;
   private Vec3[] T;
   private Vec3[] r;
   private Vec3[] rv;
   private Vec3[] ra;
   private final Entity entity;
   private Vec3 prevP;
   private Vec3 prevV;
   private Vec3[] pOrig;
   private int prevUpdateTick;

   public DynamicChain(Entity entity) {
      this.entity = entity;
      this.p = new Vec3[0];
      this.v = new Vec3[0];
      this.a = new Vec3[0];
      this.F = new Vec3[0];
      this.m = new float[0];
      this.d = new float[0];
      this.T = new Vec3[0];
      this.ra = new Vec3[0];
      this.rv = new Vec3[0];
      this.r = new Vec3[0];
      this.pOrig = new Vec3[0];
      this.prevUpdateTick = -1;
   }

   public void updateBendConstraint(float gravityAmount, float stiffness, float stiffnessFalloff, float damping, int numUpdates, boolean useFloor) {
      Vec3[] prevPos = new Vec3[this.p.length];
      Vec3[] prevVel = new Vec3[this.v.length];

      for (int i = 0; i < this.p.length; i++) {
         prevPos[i] = this.p[i];
         prevVel[i] = this.v[i];
      }

      for (int j = 0; j < numUpdates; j++) {
         for (int i = 0; i < this.p.length - 1; i++) {
            if (i == 0) {
               Vec3 root = this.pOrig[i];
               this.p[i] = root;
               this.v[i] = this.p[i].m_82546_(prevPos[i]);
               this.a[i] = this.v[i].m_82546_(prevVel[i]);
            }

            Vec3 target = angleBetween(this.pOrig[i], this.pOrig[i + 1]);
            this.r[i] = angleBetween(this.p[i], this.p[i + 1]);
            this.T[i] = wrapAngles(this.r[i].m_82546_(target)).m_82490_((double)(-stiffness) / Math.pow((double)(i + 1), (double)stiffnessFalloff));
            double down = Math.PI / 2;
            Vec3 gravityVec = wrapAngles(
               new Vec3(0.0, (double)(gravityAmount * this.d[i + 1] * this.m[i + 1]) * Math.sin(down - this.r[i].f_82480_ + down), 0.0)
            );
            Vec3 floorVec = new Vec3(0.0, (double)(1.0F * this.d[i + 1] * this.m[i + 1]) * Math.sin((Math.PI / 2) - this.r[i].f_82480_ + (Math.PI / 2)), 0.0);
            if (useFloor && this.entity.m_20096_() && this.p[i + 1].f_82480_ < this.entity.m_20186_()) {
               this.T[i] = this.T[i].m_82546_(floorVec);
            }

            this.T[i] = wrapAngles(this.T[i].m_82549_(gravityVec));
            this.ra[i] = this.T[i].m_82490_((double)(1.0F / (this.m[i + 1] * this.d[i + 1] * this.d[i + 1])));
            this.rv[i] = this.rv[i].m_82549_(this.ra[i].m_82490_((double)(1.0F / (float)numUpdates))).m_82490_((double)damping);
            this.rv[i] = wrapAngles(this.rv[i]);
            this.r[i] = this.r[i].m_82549_(this.rv[i].m_82490_((double)(1.0F / (float)numUpdates)));
            this.r[i] = wrapAngles(this.r[i]);
            this.p[i + 1] = fromPitchYaw((float)(this.r[i].f_82480_ - (Math.PI / 2)), (float)(this.r[i].f_82479_ - (Math.PI / 2)))
               .m_82490_((double)this.d[i + 1])
               .m_82549_(this.p[i]);
            this.v[i + 1] = this.p[i + 1].m_82546_(prevPos[i + 1]);
            this.a[i + 1] = this.v[i + 1].m_82546_(prevVel[i + 1]);
         }
      }
   }

   public void updateSpringConstraint(
      float gravityAmount, float dampAmount, float stiffness, float maxForce, boolean doAttract, float attractFalloff, int numUpdates
   ) {
      for (int j = 0; j < numUpdates; j++) {
         for (int i = 0; i < this.p.length; i++) {
            this.a[i] = this.F[i].m_82490_((double)(1.0F / this.m[i]));
            this.v[i] = this.v[i].m_82549_(this.a[i].m_82490_((double)(1.0F / (float)numUpdates)));
            this.p[i] = this.p[i].m_82549_(this.v[i].m_82490_((double)(1.0F / (float)numUpdates)));
            Vec3 disp;
            if (i == 0) {
               Vec3 root = this.pOrig[i];
               disp = this.p[i].m_82546_(root);
            } else {
               disp = this.p[i].m_82546_(this.p[i - 1]);
            }

            disp = disp.m_82541_().m_82490_(disp.m_82553_() - (double)this.d[i]);
            Vec3 damp = this.v[i].m_82490_((double)dampAmount);
            Vec3 gravity = new Vec3(0.0, (double)(-gravityAmount), 0.0);
            Vec3 attract = this.pOrig[0].m_82546_(this.p[i]);
            this.F[i] = disp.m_82490_((double)(-stiffness) * disp.m_82553_()).m_82549_(gravity.m_82490_((double)this.m[i])).m_82546_(damp);
            if (i == 0 || doAttract) {
               this.F[i] = this.F[i].m_82549_(attract.m_82490_((double)(1.0F / (1.0F + (float)(i * i) * attractFalloff))));
            }

            if (this.F[i].m_82553_() > (double)maxForce) {
               this.F[i].m_82541_().m_82490_((double)maxForce);
            }
         }
      }
   }

   public void setChain(AdvancedModelRenderer[] chainOrig, AdvancedModelRenderer[] chainDynamic) {
      this.p = new Vec3[chainOrig.length];
      this.v = new Vec3[chainOrig.length];
      this.a = new Vec3[chainOrig.length];
      this.F = new Vec3[chainOrig.length];
      this.m = new float[chainOrig.length];
      this.d = new float[chainOrig.length];
      this.T = new Vec3[chainOrig.length];
      this.r = new Vec3[chainOrig.length];
      this.rv = new Vec3[chainOrig.length];
      this.ra = new Vec3[chainOrig.length];
      this.pOrig = new Vec3[chainOrig.length];

      for (int i = 0; i < chainOrig.length; i++) {
         this.pOrig[i] = chainOrig[i].getWorldPos(this.entity, 0.0F);
         this.p[i] = this.pOrig[i];
         this.v[i] = new Vec3(0.0, 0.0, 0.0);
         this.a[i] = new Vec3(0.0, 0.0, 0.0);
         this.F[i] = new Vec3(0.0, 0.0, 0.0);
         this.T[i] = new Vec3(0.0, 0.0, 0.0);
         this.r[i] = new Vec3(0.0, 0.0, 0.0);
         this.rv[i] = new Vec3(0.0, 0.0, 0.0);
         this.ra[i] = new Vec3(0.0, 0.0, 0.0);
         this.m[i] = 0.5F + 0.5F / (float)(i + 1);
         if (i > 0) {
            this.d[i] = (float)this.p[i].m_82554_(this.p[i - 1]);
         } else {
            this.d[i] = 1.0F;
         }

         chainOrig[i].setIsHidden(true);
      }

      for (int i = 0; i < chainOrig.length - 1; i++) {
         this.r[i] = angleBetween(this.p[i], this.p[i + 1]);
      }

      this.prevP = this.p[0];
      this.prevV = this.v[0];

      for (int i = 0; i < chainOrig.length; i++) {
         if (chainDynamic[i] == null) {
            chainDynamic[i] = new AdvancedModelRenderer(chainOrig[i]);
         }
      }
   }

   public void updateChain(
      float delta,
      AdvancedModelRenderer[] chainOrig,
      AdvancedModelRenderer[] chainDynamic,
      float gravityAmount,
      float stiffness,
      float stiffnessFalloff,
      float damping,
      int numUpdates,
      boolean useFloor
   ) {
      if (this.p.length != chainOrig.length || Double.isNaN(this.p[1].f_82479_)) {
         this.setChain(chainOrig, chainDynamic);
      }

      if (this.prevUpdateTick != this.entity.f_19797_) {
         for (int i = 0; i < chainOrig.length; i++) {
            this.pOrig[i] = chainOrig[i].getWorldPos(this.entity, delta);
         }

         this.updateBendConstraint(gravityAmount, stiffness, stiffnessFalloff, damping, numUpdates, useFloor);
         this.prevUpdateTick = this.entity.f_19797_;
      }

      if (chainDynamic != null) {
         if (Minecraft.m_91087_().m_91104_()) {
            delta = 0.5F;
         }

         for (int i = chainDynamic.length - 1; i >= 0; i--) {
            if (chainDynamic[i] == null) {
               return;
            }

            Vec3 renderPos = this.p[i].m_82549_(this.v[i].m_82490_((double)delta)).m_82549_(this.a[i].m_82490_(0.5 * (double)delta * (double)delta));
            chainDynamic[i].setWorldPos(this.entity, renderPos, delta);
            if (i < chainDynamic.length - 1) {
               Vec3 p1 = new Vec3((double)chainDynamic[i].rotationPointX, (double)chainDynamic[i].rotationPointY, (double)chainDynamic[i].rotationPointZ);
               Vec3 p2 = new Vec3(
                  (double)chainDynamic[i + 1].rotationPointX, (double)chainDynamic[i + 1].rotationPointY, (double)chainDynamic[i + 1].rotationPointZ
               );
               Vec3 diff = p2.m_82546_(p1);
               float yaw = (float)Math.atan2(diff.f_82479_, diff.f_82481_);
               float pitch = -((float)Math.asin(diff.f_82480_ / diff.m_82553_()));
               chainDynamic[i].rotateAngleY = chainDynamic[i].defaultRotationY + yaw;
               chainDynamic[i].rotateAngleX = chainDynamic[i].defaultRotationZ + pitch;
               chainDynamic[i].rotateAngleZ = (float)this.r[i].f_82481_;
               Vec3 diffRotated = diff.m_82524_(yaw);
               diffRotated = diffRotated.m_82496_(pitch);
            }
         }
      }
   }

   public void render(
      PoseStack matrixStackIn,
      VertexConsumer bufferIn,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float alpha,
      AdvancedModelRenderer[] dynModelRenderers
   ) {
      if (dynModelRenderers != null) {
         for (int i = 0; i < dynModelRenderers.length - 1; i++) {
            if (dynModelRenderers[i] == null) {
               return;
            }

            dynModelRenderers[i].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
         }
      }
   }

   private static Vec3 fromPitchYaw(float pitch, float yaw) {
      float f = Mth.m_14089_(-yaw - (float) Math.PI);
      float f1 = Mth.m_14031_(-yaw - (float) Math.PI);
      float f2 = -Mth.m_14089_(-pitch);
      float f3 = Mth.m_14031_(-pitch);
      return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   private static Vec3 angleBetween(Vec3 p1, Vec3 p2) {
      float dz = (float)(p2.f_82481_ - p1.f_82481_);
      float dx = (float)(p2.f_82479_ - p1.f_82479_);
      float dy = (float)(p2.f_82480_ - p1.f_82480_);
      float yaw = (float)Mth.m_14136_((double)dz, (double)dx);
      float pitch = (float)Mth.m_14136_(Math.sqrt((double)(dz * dz + dx * dx)), (double)dy);
      return wrapAngles(new Vec3((double)yaw, (double)pitch, 0.0));
   }

   public static Vec3 toPitchYaw(Vec3 vector) {
      double f3 = vector.f_82480_;
      double pitch = -Math.asin(f3);
      double f2 = -Math.cos(pitch);
      double f1 = vector.f_82479_ / f2;
      double yaw = -Math.asin(f1) + (Math.PI / 2);
      return wrapAngles(new Vec3(yaw, pitch, 0.0));
   }

   private static Vec3 toEuler(Vec3 axis, double angle) {
      double s = Math.sin(angle);
      double c = Math.cos(angle);
      double t = 1.0 - c;
      double yaw = 0.0;
      double pitch = 0.0;
      double roll = 0.0;
      double x = axis.f_82479_;
      double y = axis.f_82480_;
      double z = axis.f_82481_;
      if (x * y * t + z * s > 0.998) {
         yaw = 2.0 * Math.atan2(x * Math.sin(angle / 2.0), Math.cos(angle / 2.0));
         pitch = Math.PI / 2;
         roll = 0.0;
      } else if (x * y * t + z * s < -0.998) {
         yaw = -2.0 * Math.atan2(x * Math.sin(angle / 2.0), Math.cos(angle / 2.0));
         pitch = -Math.PI / 2;
         roll = 0.0;
      } else {
         yaw = Math.atan2(y * s - x * z * t, 1.0 - (y * y + z * z) * t);
         pitch = Math.asin(x * y * t + z * s);
         roll = Math.atan2(x * s - y * z * t, 1.0 - (x * x + z * z) * t);
      }

      return new Vec3(yaw, pitch, roll);
   }

   private static Vec3 wrapAngles(Vec3 r) {
      double x = r.f_82479_;
      double y = r.f_82480_;
      double z = r.f_82481_;

      while (x > Math.PI) {
         x -= Math.PI * 2;
      }

      while (x < -Math.PI) {
         x += Math.PI * 2;
      }

      while (y > Math.PI) {
         y -= Math.PI * 2;
      }

      while (y < -Math.PI) {
         y += Math.PI * 2;
      }

      while (z > Math.PI) {
         z -= Math.PI * 2;
      }

      while (z < -Math.PI) {
         z += Math.PI * 2;
      }

      return new Vec3(x, y, z);
   }

   private static Vec3 multiply(Vec3 u, Vec3 v, boolean preserveDir) {
      return preserveDir
         ? new Vec3(u.f_82479_ * Math.abs(v.f_82479_), u.f_82480_ * Math.abs(v.f_82480_), u.f_82481_ * Math.abs(v.f_82481_))
         : new Vec3(u.f_82479_ * v.f_82479_, u.f_82480_ * v.f_82480_, u.f_82481_ * v.f_82481_);
   }
}
