package com.min01.archaeology.init;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.IArmPoseTransformer;

public class ArchaeologyArmPose implements IArmPoseTransformer {
   public static ArmPose BRUSH;

   public void applyTransform(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
      if (arm == HumanoidArm.LEFT) {
         model.f_102812_.f_104203_ = model.f_102812_.f_104203_ * 0.5F - (float) (Math.PI / 5);
         model.f_102812_.f_104204_ = 0.0F;
      } else if (arm == HumanoidArm.RIGHT) {
         model.f_102811_.f_104203_ = model.f_102811_.f_104203_ * 0.5F - (float) (Math.PI / 5);
         model.f_102811_.f_104204_ = 0.0F;
      }
   }
}
