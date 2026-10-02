package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class SineWaveAnimationUtils {
   public static void addSineWaveMotionToModelPart(
      ModelPart modelPart,
      float amount,
      float speed,
      float tick,
      float min,
      float max,
      float offset,
      float speedMultiplier,
      float amountMultiplier,
      SineWaveMotionTypes motionType
   ) {
      float rotationMotion = Mth.m_14036_(
         Mth.m_14089_(tick * speedMultiplier * speed + degToRad(offset)) * (float) (Math.PI / 180.0) * degToRad(amount), degToRad(min), degToRad(max)
      );
      float motion = Mth.m_14036_(Mth.m_14089_(tick * speedMultiplier * speed + offset) * amount, min, max);
      if (motionType == SineWaveMotionTypes.POSITION_Y) {
         motion = -motion;
      }

      affectModelPartBasedOnMotionType(modelPart, rotationMotion * amountMultiplier, motion * amountMultiplier, motionType);
   }

   public static void adjustPositionOfModelPart(ModelPart modelPart, float amount, float amountMultiplier, SineWaveMotionTypes motionType) {
      float newPositionAmount = amount;
      if (motionType == SineWaveMotionTypes.POSITION_Y) {
         newPositionAmount = -amount;
      }

      affectModelPartBasedOnMotionType(modelPart, degToRad(amount) * amountMultiplier, newPositionAmount * amountMultiplier, motionType);
   }

   public static float getSineWaveKeyframe(float amount, float speed, float tick, float min, float max, float offset) {
      return Mth.m_14036_(Mth.m_14089_(tick * 1.0F * speed + offset) * amount, min, max);
   }

   public static float getConstantMotionKeyframe(float speed, float tick) {
      return tick * speed;
   }

   public static void affectModelPartBasedOnMotionType(ModelPart modelPart, float rotationMotion, float motion, SineWaveMotionTypes motionType) {
      switch (motionType) {
         case ROTATION_X:
            modelPart.f_104203_ += rotationMotion;
            break;
         case ROTATION_Y:
            modelPart.f_104204_ += rotationMotion;
            break;
         case ROTATION_Z:
            modelPart.f_104205_ += rotationMotion;
            break;
         case POSITION_X:
            modelPart.f_104200_ += motion;
            break;
         case POSITION_Y:
            modelPart.f_104201_ += motion;
            break;
         case POSITION_Z:
            modelPart.f_104202_ += motion;
            break;
         case SCALE_X:
            modelPart.f_233553_ += motion;
            break;
         case SCALE_Y:
            modelPart.f_233554_ += motion;
            break;
         case SCALE_Z:
            modelPart.f_233555_ += motion;
      }
   }

   public static float tickAmountMultiplierChange(float value, boolean shouldPlay, float transitionSpeed) {
      float newValue = value;
      if (shouldPlay) {
         if (value < 1.0F) {
            if (value + transitionSpeed > 1.0F) {
               newValue = 1.0F;
            } else {
               newValue = value + transitionSpeed;
            }
         }
      } else if (value > 0.0F) {
         if (value - transitionSpeed < 0.0F) {
            newValue = 0.0F;
         } else {
            newValue = value - transitionSpeed;
         }
      }

      return newValue;
   }

   public static float getTick(int tick, boolean convertToRadians) {
      return convertToRadians ? degToRad((float)tick + Minecraft.m_91087_().m_91296_()) / 20.0F : ((float)tick + Minecraft.m_91087_().m_91296_()) / 20.0F;
   }

   private static float degToRad(float deg) {
      return deg * (float) (Math.PI / 180.0);
   }
}
