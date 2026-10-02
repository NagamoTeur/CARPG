package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.entity.MowzieGeckoEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class GeckoItemlayer<T extends MowzieGeckoEntity> extends GeoLayerRenderer<T> {
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   private MowzieGeckoEntity entity;
   private String boneName;
   private ItemStack renderedItem;

   public GeckoItemlayer(IGeoRenderer<T> entityRendererIn, String boneName, ItemStack renderedItem) {
      super(entityRendererIn);
      this.boneName = boneName;
      this.renderedItem = renderedItem;
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entity,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.entity = entity;
      GeoModel model = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelResource(entity));
      this.renderRecursively(entity, (GeoBone)model.topLevelBones.get(0), poseStack, bufferIn, packedLightIn, OverlayTexture.f_118083_);
   }

   public void renderRecursively(MowzieGeckoEntity entity, GeoBone bone, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
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
      if (bone.getName().equals(this.boneName) && !bone.isHidden()) {
         poseStack.m_85841_(1.5F, 1.5F, 1.5F);
         Minecraft.m_91087_()
            .m_91291_()
            .m_174269_(this.renderedItem, TransformType.THIRD_PERSON_RIGHT_HAND, packedLight, packedOverlay, poseStack, buffer, entity.m_19879_());
      }

      if (bone.isTrackingXform()) {
         Matrix4f poseState = poseStack.m_85850_().m_85861_().m_27658_();
         Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);
         bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
         localMatrix.m_27648_(new Vector3f(this.getRenderOffset(this.entity, 1.0F)));
         bone.setLocalSpaceXform(localMatrix);
         Matrix4f worldState = localMatrix.m_27658_();
         worldState.m_27648_(new Vector3f(this.entity.m_20182_()));
         bone.setWorldSpaceXform(worldState);
      }

      RenderUtils.translateAwayFromPivotPoint(poseStack, bone);
      if (!bone.isHidden) {
         for (GeoBone childBone : bone.childBones) {
            this.renderRecursively(entity, childBone, poseStack, buffer, packedLight, packedOverlay);
         }
      }

      poseStack.m_85849_();
   }

   public Vec3 getRenderOffset(MowzieGeckoEntity p_114483_, float p_114484_) {
      return Vec3.f_82478_;
   }
}
