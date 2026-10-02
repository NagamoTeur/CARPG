package com.bobmowzie.mowziesmobs.server.entity;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmartBodyHelper extends BodyRotationControl {
   private static final float MAX_ROTATE = 75.0F;
   private static final int HISTORY_SIZE = 10;
   private final Mob entity;
   private int rotateTime;
   private float targetYawHead;
   private final double[] histPosX = new double[10];
   private final double[] histPosZ = new double[10];

   public SmartBodyHelper(Mob entity) {
      super(entity);
      this.entity = entity;
   }

   public void m_8121_() {
      for (int i = this.histPosX.length - 1; i > 0; i--) {
         this.histPosX[i] = this.histPosX[i - 1];
         this.histPosZ[i] = this.histPosZ[i - 1];
      }

      this.histPosX[0] = this.entity.m_20185_();
      this.histPosZ[0] = this.entity.m_20189_();
      double dx = this.delta(this.histPosX);
      double dz = this.delta(this.histPosZ);
      double distSq = dx * dx + dz * dz;
      if (distSq > 2.5E-7) {
         boolean isStrafing = false;
         if (this.entity instanceof MowzieEntity) {
            isStrafing = ((MowzieEntity)this.entity).isStrafing();
         }

         if (!isStrafing) {
            double moveAngle = (double)((float)Mth.m_14136_(dz, dx) * (180.0F / (float)Math.PI) - 90.0F);
            this.entity.f_20883_ = (float)((double)this.entity.f_20883_ + Mth.m_14175_(moveAngle - (double)this.entity.f_20883_) * 0.6F);
            this.targetYawHead = this.entity.f_20885_;
            this.rotateTime = 0;
         } else {
            super.m_8121_();
         }
      } else if (this.entity.m_20197_().isEmpty() || !(this.entity.m_20197_().get(0) instanceof Mob)) {
         float limit = 75.0F;
         if (Math.abs(this.entity.f_20885_ - this.targetYawHead) > 15.0F) {
            this.rotateTime = 0;
            this.targetYawHead = this.entity.f_20885_;
         } else {
            this.rotateTime++;
            int speed = 10;
            if (this.rotateTime > 10) {
               limit = Math.max(1.0F - (float)(this.rotateTime - 10) / 10.0F, 0.0F) * 75.0F;
            }
         }

         this.entity.f_20883_ = approach(this.entity.f_20885_, this.entity.f_20883_, limit);
      }
   }

   private double delta(double[] arr) {
      return this.mean(arr, 0) - this.mean(arr, 5);
   }

   private double mean(double[] arr, int start) {
      double mean = 0.0;

      for (int i = 0; i < 5; i++) {
         mean += arr[i + start];
      }

      return mean / (double)arr.length;
   }

   public static float approach(float target, float current, float limit) {
      float delta = Mth.m_14177_(current - target);
      if (delta < -limit) {
         delta = -limit;
      } else if (delta >= limit) {
         delta = limit;
      }

      return target + delta * 0.55F;
   }
}
