package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.ModelPartMatrix;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.ArmorItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController.ModelFetcher;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.GeoUtils;
import software.bernie.geckolib3.util.RenderUtils;

public class MowzieGeoArmorRenderer<T extends ArmorItem & IAnimatable> extends GeoArmorRenderer<T> implements IGeoRenderer<T>, ModelFetcher<T> {
   public boolean usingCustomPlayerAnimations = false;

   public MowzieGeoArmorRenderer(AnimatedGeoModel<T> modelProvider) {
      super(modelProvider);
   }

   public void m_7695_(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      super.m_7695_(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      this.usingCustomPlayerAnimations = false;
   }

   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      poseStack.m_85836_();
      if (this.usingCustomPlayerAnimations && bone instanceof MowzieGeoBone && ((MowzieGeoBone)bone).isForceMatrixTransform()) {
         Pose last = poseStack.m_85850_();
         last.m_85861_().m_27624_();
         last.m_85864_().m_8180_();
         Matrix4f matrix4f = bone.getWorldSpaceXform();
         last.m_85861_().m_27644_(matrix4f);
         last.m_85864_().m_8178_(bone.getWorldSpaceNormal());
         poseStack.m_85845_(new Quaternion(0.0F, 0.0F, 180.0F, true));
         poseStack.m_85837_(0.0, -1.5, 0.0);
      } else {
         RenderUtils.prepMatrixForBone(poseStack, bone);
      }

      this.renderCubesOfBone(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      this.renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
      poseStack.m_85849_();
   }

   public void copyFrom(IBone geoBone, ModelPart modelRendererIn) {
      if (this.usingCustomPlayerAnimations && modelRendererIn instanceof ModelPartMatrix && geoBone instanceof MowzieGeoBone thisBone) {
         ModelPartMatrix other = (ModelPartMatrix)modelRendererIn;
         thisBone.setWorldSpaceNormal(other.getWorldNormal());
         thisBone.setWorldSpaceXform(other.getWorldXform());
         thisBone.setForceMatrixTransform(true);
      } else {
         GeoUtils.copyRotations(modelRendererIn, geoBone);
         geoBone.setPositionX(this.f_102808_.f_104200_);
         geoBone.setPositionY(-this.f_102808_.f_104201_);
         geoBone.setPositionZ(this.f_102808_.f_104202_);
      }
   }

   protected void fitToBiped() {
      if (this.headBone != null) {
         IBone headBone = this.getGeoModelProvider().getBone(this.headBone);
         this.copyFrom(headBone, this.f_102808_);
      }

      if (this.bodyBone != null) {
         IBone bodyBone = this.getGeoModelProvider().getBone(this.bodyBone);
         this.copyFrom(bodyBone, this.f_102810_);
      }

      if (this.rightArmBone != null) {
         IBone rightArmBone = this.getGeoModelProvider().getBone(this.rightArmBone);
         this.copyFrom(rightArmBone, this.f_102811_);
      }

      if (this.leftArmBone != null) {
         IBone leftArmBone = this.getGeoModelProvider().getBone(this.leftArmBone);
         this.copyFrom(leftArmBone, this.f_102812_);
      }

      if (this.rightLegBone != null) {
         IBone rightLegBone = this.getGeoModelProvider().getBone(this.rightLegBone);
         this.copyFrom(rightLegBone, this.f_102813_);
         if (this.rightBootBone != null) {
            IBone rightBootBone = this.getGeoModelProvider().getBone(this.rightBootBone);
            this.copyFrom(rightBootBone, this.f_102813_);
         }
      }

      if (this.leftLegBone != null) {
         IBone leftLegBone = this.getGeoModelProvider().getBone(this.leftLegBone);
         this.copyFrom(leftLegBone, this.f_102814_);
         if (this.leftBootBone != null) {
            IBone leftBootBone = this.getGeoModelProvider().getBone(this.leftBootBone);
            this.copyFrom(leftBootBone, this.f_102814_);
         }
      }
   }
}
