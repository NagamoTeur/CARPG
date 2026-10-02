package com.bobmowzie.mowziesmobs.client.render.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimatedGeoModel;
import com.bobmowzie.mowziesmobs.client.render.MowzieRenderUtils;
import com.bobmowzie.mowziesmobs.server.entity.IAnimationTickable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public abstract class MowzieGeoEntityRenderer<T extends LivingEntity & IAnimatable & IAnimationTickable> extends GeoEntityRenderer<T> {
   private Matrix4f renderEarlyMat = new Matrix4f();

   protected MowzieGeoEntityRenderer(Context renderManager, AnimatedGeoModel<T> modelProvider) {
      super(renderManager, modelProvider);
   }

   public RenderType getRenderType(
      T animatable,
      float partialTicks,
      PoseStack stack,
      MultiBufferSource renderTypeBuffer,
      VertexConsumer vertexBuilder,
      int packedLightIn,
      ResourceLocation textureLocation
   ) {
      return RenderType.m_110458_(textureLocation);
   }

   public void renderEarly(
      T animatable,
      PoseStack stackIn,
      float ticks,
      MultiBufferSource renderTypeBuffer,
      VertexConsumer vertexBuilder,
      int packedLightIn,
      int packedOverlayIn,
      float red,
      float green,
      float blue,
      float partialTicks
   ) {
      super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
      this.renderEarlyMat = stackIn.m_85850_().m_85861_().m_27658_();
   }

   public MowzieAnimatedGeoModel<T> getMowzieAnimatedGeoModel() {
      return (MowzieAnimatedGeoModel<T>)super.getGeoModelProvider();
   }

   protected float getDeathMaxRotation(T animatable) {
      return 0.0F;
   }

   public void renderRecursively(
      GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha
   ) {
      poseStack.m_85836_();
      RenderUtils.translateMatrixToBone(poseStack, bone);
      RenderUtils.translateToPivotPoint(poseStack, bone);
      boolean rotOverride = bone.rotMat != null;
      if (rotOverride) {
         poseStack.m_85850_().m_85861_().m_27644_(bone.rotMat);
         poseStack.m_85850_().m_85864_().m_8178_(new Matrix3f(bone.rotMat));
      } else {
         RenderUtils.rotateMatrixAroundBone(poseStack, bone);
      }

      RenderUtils.scaleMatrixForBone(poseStack, bone);
      if (bone.isTrackingXform()) {
         Matrix4f poseState = poseStack.m_85850_().m_85861_().m_27658_();
         Matrix4f localMatrix = MowzieRenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         bone.setModelSpaceXform(MowzieRenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(this.m_7860_(this.animatable, 1.0F)));
         bone.setLocalSpaceXform(localMatrix);
         Matrix4f worldState = localMatrix.m_27658_();
         worldState.m_27648_(new Vector3f(this.animatable.m_20182_()));
         bone.setWorldSpaceXform(worldState);
      }

      RenderUtils.translateAwayFromPivotPoint(poseStack, bone);
      if (!bone.isHidden) {
         if (!bone.cubesAreHidden()) {
            for (GeoCube geoCube : bone.childCubes) {
               poseStack.m_85836_();
               this.renderCube(geoCube, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
               poseStack.m_85849_();
            }
         }

         for (GeoBone childBone : bone.childBones) {
            this.renderRecursively(childBone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
         }
      }

      poseStack.m_85849_();
   }
}
