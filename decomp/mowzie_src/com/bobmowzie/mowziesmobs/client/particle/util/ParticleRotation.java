package com.bobmowzie.mowziesmobs.client.particle.util;

import net.minecraft.world.phys.Vec3;

public abstract class ParticleRotation {
   public void setPrevValues() {
   }

   public static class EulerAngles extends ParticleRotation {
      public float yaw;
      public float pitch;
      public float roll;
      public float prevYaw;
      public float prevPitch;
      public float prevRoll;

      public EulerAngles(float yaw, float pitch, float roll) {
         this.yaw = this.prevYaw = yaw;
         this.pitch = this.prevPitch = pitch;
         this.roll = this.prevRoll = roll;
      }

      @Override
      public void setPrevValues() {
         this.prevYaw = this.yaw;
         this.prevPitch = this.pitch;
         this.prevRoll = this.roll;
      }
   }

   public static class FaceCamera extends ParticleRotation {
      public float faceCameraAngle;
      public float prevFaceCameraAngle;

      public FaceCamera(float faceCameraAngle) {
         this.faceCameraAngle = faceCameraAngle;
      }

      @Override
      public void setPrevValues() {
         this.prevFaceCameraAngle = this.faceCameraAngle;
      }
   }

   public static class OrientVector extends ParticleRotation {
      public Vec3 orientation;
      public Vec3 prevOrientation;

      public OrientVector(Vec3 orientation) {
         this.orientation = this.prevOrientation = orientation;
      }

      @Override
      public void setPrevValues() {
         this.prevOrientation = this.orientation;
      }
   }
}
