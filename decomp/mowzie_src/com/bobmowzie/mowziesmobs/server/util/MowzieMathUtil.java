package com.bobmowzie.mowziesmobs.server.util;

import net.minecraft.util.Mth;

public class MowzieMathUtil {
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
}
