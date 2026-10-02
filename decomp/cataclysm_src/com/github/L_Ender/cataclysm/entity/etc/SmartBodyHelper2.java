package com.github.L_Ender.cataclysm.entity.etc;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class SmartBodyHelper2 extends BodyRotationControl {
   private final Mob mob;
   private static final float MAX_ROTATE = 75.0F;
   private int rotationTickCounter;
   private static final int HISTORY_SIZE = 10;
   private float prevRenderYawHead;
   private final double[] histPosX = new double[10];
   private final double[] histPosZ = new double[10];

   public SmartBodyHelper2(Mob mob) {
      super(mob);
      this.mob = mob;
   }

   public void m_8121_() {
      for (int i = this.histPosX.length - 1; i > 0; i--) {
         this.histPosX[i] = this.histPosX[i - 1];
         this.histPosZ[i] = this.histPosZ[i - 1];
      }

      if (this.hasMoved()) {
         this.mob.f_20883_ = this.mob.m_146908_();
         this.func_220664_c();
         this.prevRenderYawHead = this.mob.f_20885_;
         this.rotationTickCounter = 0;
      } else if (this.noMobPassengers()) {
         if (Math.abs(this.mob.f_20885_ - this.prevRenderYawHead) > 15.0F) {
            this.rotationTickCounter = 0;
            this.prevRenderYawHead = this.mob.f_20885_;
            this.func_220663_b();
         } else {
            float limit = 75.0F;
            this.rotationTickCounter++;
            int speed = 10;
            if (this.rotationTickCounter > 10) {
               limit = Math.max(1.0F - (float)(this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
            }

            this.mob.f_20883_ = approach(this.mob.f_20885_, this.mob.f_20883_, limit);
         }
      }
   }

   private void func_220663_b() {
      this.mob.f_20883_ = Mth.m_14094_(this.mob.f_20883_, this.mob.f_20885_, (float)this.mob.m_8085_());
   }

   private void func_220664_c() {
      this.mob.f_20885_ = Mth.m_14094_(this.mob.f_20885_, this.mob.f_20883_, (float)this.mob.m_8085_());
   }

   private boolean noMobPassengers() {
      return this.mob.m_20197_().isEmpty() || !(this.mob.m_20197_().get(0) instanceof Mob);
   }

   private boolean hasMoved() {
      double d0 = this.mob.m_20185_() - this.mob.f_19854_;
      double d1 = this.mob.m_20189_() - this.mob.f_19856_;
      return d0 * d0 + d1 * d1 > 2.5000003E-7F;
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
