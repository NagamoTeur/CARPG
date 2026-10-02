package com.github.L_Ender.cataclysm.util;

import com.mojang.math.Quaternion;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class CMMathUtil {
   public static float approachSmooth(float current, float previous, float desired, float desiredSpeed, float deltaSpeed) {
      float prevSpeed = current - previous;
      desiredSpeed = Mth.m_14154_(desiredSpeed);
      desiredSpeed = current < desired ? desiredSpeed : -desiredSpeed;
      float speed = Mth.m_14121_(prevSpeed, desiredSpeed, deltaSpeed);
      float speedApproachReduction = (float)(
         1.0 - Math.pow((double)Mth.m_14036_(-Mth.m_14154_(current - desired) / Mth.m_14154_(2.0F * desiredSpeed / deltaSpeed) + 1.0F, 0.0F, 1.0F), 4.0)
      );
      speed *= speedApproachReduction;
      return current < desired ? Mth.m_14036_(current + speed, current, desired) : Mth.m_14036_(current + speed, desired, current);
   }

   public static float approachDegreesSmooth(float current, float previous, float desired, float desiredSpeed, float deltaSpeed) {
      float desiredDifference = Mth.m_14118_(current, desired);
      float previousDifference = Mth.m_14118_(current, previous);
      return approachSmooth(current, current + previousDifference, current + desiredDifference, desiredSpeed, deltaSpeed);
   }

   public static Quaternion quatFromRotationXYZ(float x, float y, float z, boolean degrees) {
      return new Quaternion(x, y, z, degrees);
   }

   public static Vec3 getOffsetPos(Entity entity, double offsetX, double offsetY, double offsetZ, float rotationX, float rotationY) {
      Vec3 Vec3 = new Vec3(offsetZ, offsetY, offsetX)
         .m_82535_(rotationX * (float) (Math.PI / 180.0))
         .m_82524_(-rotationY * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
      return entity.m_20182_().m_82520_(Vec3.f_82479_, Vec3.f_82480_, Vec3.f_82481_);
   }

   public static Vec3 getOffsetMotion(Entity entity, double offsetX, double offsetY, double offsetZ, float rotationX, float rotationY) {
      return new Vec3(offsetZ, offsetY, offsetX)
         .m_82535_(rotationX * (float) (Math.PI / 180.0))
         .m_82524_(-rotationY * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
   }

   public static float smin(float a, float b, float k) {
      float h = Math.max(k - Math.abs(a - b), 0.0F) / k;
      return Math.min(a, b) - h * h * k * 0.25F;
   }

   public static float cullAnimationTick(int tick, float amplitude, float partialTick, int startOffset, int endAt) {
      float i = Mth.m_14036_((float)tick + partialTick - (float)startOffset, 0.0F, (float)endAt);
      float f = (float)Math.sin((double)(i / (float)endAt) * Math.PI) * amplitude;
      return smin(f, 1.0F, 0.1F);
   }
}
