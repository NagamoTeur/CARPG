package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.ModelPartMatrix;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelPlayerAnimated<T extends LivingEntity> extends PlayerModel<T> {
   private final List<ModelPart> parts;

   public ModelPlayerAnimated(ModelPart root, boolean smallArmsIn) {
      super(root, smallArmsIn);
      ModelPartMatrix bodyMatrix = new ModelPartMatrix(this.f_102810_, false);
      ModelPartMatrix headMatrix = new ModelPartMatrix(this.f_102808_, false);
      ModelPartMatrix rightArmMatrix = new ModelPartMatrix(this.f_102811_, false);
      ModelPartMatrix leftArmMatrix = new ModelPartMatrix(this.f_102812_, false);
      ModelPartMatrix rightLegMatrix = new ModelPartMatrix(this.f_102813_, false);
      ModelPartMatrix leftLegMatrix = new ModelPartMatrix(this.f_102814_, false);
      ModelPartMatrix hatMatrix = new ModelPartMatrix(this.f_102809_, false);
      ModelPartMatrix jacketMatrix = new ModelPartMatrix(this.f_103378_, false);
      ModelPartMatrix leftSleeveMatrix = new ModelPartMatrix(this.f_103374_, false);
      ModelPartMatrix rightSleeveMatrix = new ModelPartMatrix(this.f_103375_, false);
      ModelPartMatrix leftPantsMatrix = new ModelPartMatrix(this.f_103376_, false);
      ModelPartMatrix rightPantsMatrix = new ModelPartMatrix(this.f_103377_, false);
      ModelPartMatrix earMatrix = new ModelPartMatrix(this.f_103379_, false);
      Map<ModelPart, ModelPart> origToNew = new HashMap<>();
      origToNew.put(this.f_102810_, bodyMatrix);
      origToNew.put(this.f_102808_, headMatrix);
      origToNew.put(this.f_102811_, rightArmMatrix);
      origToNew.put(this.f_102812_, leftArmMatrix);
      origToNew.put(this.f_102813_, rightLegMatrix);
      origToNew.put(this.f_102814_, leftLegMatrix);
      origToNew.put(this.f_102809_, hatMatrix);
      origToNew.put(this.f_103378_, jacketMatrix);
      origToNew.put(this.f_103374_, leftSleeveMatrix);
      origToNew.put(this.f_103375_, rightSleeveMatrix);
      origToNew.put(this.f_103376_, leftPantsMatrix);
      origToNew.put(this.f_103377_, rightPantsMatrix);
      origToNew.put(this.f_103379_, earMatrix);
      this.f_102810_ = bodyMatrix;
      this.f_102808_ = headMatrix;
      this.f_102811_ = rightArmMatrix;
      this.f_102812_ = leftArmMatrix;
      this.f_102813_ = rightLegMatrix;
      this.f_102814_ = leftLegMatrix;
      this.f_102809_ = hatMatrix;
      this.f_103378_ = jacketMatrix;
      this.f_103374_ = leftSleeveMatrix;
      this.f_103375_ = rightSleeveMatrix;
      this.f_103376_ = leftPantsMatrix;
      this.f_103377_ = rightPantsMatrix;
      this.f_103379_ = earMatrix;
      List<ModelPart> originalList = root.m_171331_().filter(p_170824_ -> !p_170824_.m_171326_()).collect(ImmutableList.toImmutableList());
      this.parts = new ArrayList<>();

      for (ModelPart origPart : originalList) {
         ModelPart newPart = origToNew.get(origPart);
         if (newPart != null) {
            this.parts.add(newPart);
         }
      }
   }

   public void m_6973_(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.f_103376_.m_104315_(this.f_102814_);
      this.f_103377_.m_104315_(this.f_102813_);
      this.f_103374_.m_104315_(this.f_102812_);
      this.f_103375_.m_104315_(this.f_102811_);
      this.f_103378_.m_104315_(this.f_102810_);
      this.f_102809_.m_104315_(this.f_102808_);
      this.f_103379_.m_104315_(this.f_102808_);
   }

   public ModelPart m_233438_(RandomSource randomIn) {
      return this.parts.get(randomIn.m_188503_(this.parts.size()));
   }

   public void m_102872_(HumanoidModel<T> modelIn) {
      if (!(modelIn.f_102810_ instanceof ModelPartMatrix)) {
         modelIn.f_102808_ = new ModelPartMatrix(modelIn.f_102808_);
         modelIn.f_102809_ = new ModelPartMatrix(modelIn.f_102809_);
         modelIn.f_102810_ = new ModelPartMatrix(modelIn.f_102810_);
         modelIn.f_102812_ = new ModelPartMatrix(modelIn.f_102812_);
         modelIn.f_102811_ = new ModelPartMatrix(modelIn.f_102811_);
         modelIn.f_102814_ = new ModelPartMatrix(modelIn.f_102814_);
         modelIn.f_102813_ = new ModelPartMatrix(modelIn.f_102813_);
      }

      ModelBipedAnimated.setUseMatrixMode(modelIn, true);
      super.m_102872_(modelIn);
   }

   public static void setUseMatrixMode(PlayerModel<? extends LivingEntity> bipedModel, boolean useMatrixMode) {
      if (bipedModel.f_102809_ instanceof ModelPartMatrix) {
         ((ModelPartMatrix)bipedModel.f_103378_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_103376_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_103377_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_103375_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_103374_).setUseMatrixMode(useMatrixMode);
         ((ModelPartMatrix)bipedModel.f_103379_).setUseMatrixMode(useMatrixMode);
      }

      ModelBipedAnimated.setUseMatrixMode(bipedModel, useMatrixMode);
   }
}
