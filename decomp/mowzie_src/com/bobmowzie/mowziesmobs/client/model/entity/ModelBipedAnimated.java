package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.ModelPartMatrix;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class ModelBipedAnimated<T extends LivingEntity> extends HumanoidModel<T> {
   public ModelBipedAnimated(ModelPart root) {
      super(root);
      this.f_102810_ = new ModelPartMatrix(this.f_102810_);
      this.f_102808_ = new ModelPartMatrix(this.f_102808_);
      this.f_102809_ = new ModelPartMatrix(this.f_102809_);
      this.f_102811_ = new ModelPartMatrix(this.f_102811_);
      this.f_102812_ = new ModelPartMatrix(this.f_102812_);
      this.f_102813_ = new ModelPartMatrix(this.f_102813_);
      this.f_102814_ = new ModelPartMatrix(this.f_102814_);
   }

   public static void copyFromGeckoModel(HumanoidModel<?> bipedModel, ModelGeckoPlayerThirdPerson geckoModel) {
      if (bipedModel.f_102810_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102810_).setWorldXform(geckoModel.bipedBody().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102810_).setWorldNormal(geckoModel.bipedBody().getWorldSpaceNormal());
      }

      if (bipedModel.f_102808_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102808_).setWorldXform(geckoModel.bipedHead().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102808_).setWorldNormal(geckoModel.bipedHead().getWorldSpaceNormal());
      }

      if (bipedModel.f_102809_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102809_).setWorldXform(geckoModel.bipedHead().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102809_).setWorldNormal(geckoModel.bipedHead().getWorldSpaceNormal());
      }

      if (bipedModel.f_102814_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102814_).setWorldXform(geckoModel.bipedLeftLeg().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102814_).setWorldNormal(geckoModel.bipedLeftLeg().getWorldSpaceNormal());
      }

      if (bipedModel.f_102813_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102813_).setWorldXform(geckoModel.bipedRightLeg().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102813_).setWorldNormal(geckoModel.bipedRightLeg().getWorldSpaceNormal());
      }

      if (bipedModel.f_102811_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102811_).setWorldXform(geckoModel.bipedRightArm().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102811_).setWorldNormal(geckoModel.bipedRightArm().getWorldSpaceNormal());
      }

      if (bipedModel.f_102812_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102812_).setWorldXform(geckoModel.bipedLeftArm().getWorldSpaceXform());
         ((ModelPartMatrix)bipedModel.f_102812_).setWorldNormal(geckoModel.bipedLeftArm().getWorldSpaceNormal());
      }
   }

   public static void setUseMatrixMode(HumanoidModel<? extends LivingEntity> bipedModel, boolean useMatrixMode) {
      if (bipedModel.f_102810_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_102810_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_102808_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_102809_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_102814_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_102813_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_102811_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_102812_).setUseMatrixMode(useMatrixMode);
      }
   }
}
