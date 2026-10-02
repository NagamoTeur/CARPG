package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import java.util.Optional;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class UmvuthanaSunLayer extends GeoLayerRenderer<EntityUmvuthana> {
   protected final EntityRenderDispatcher entityRenderDispatcher;

   public UmvuthanaSunLayer(IGeoRenderer<EntityUmvuthana> entityRendererIn, Context context) {
      super(entityRendererIn);
      this.entityRenderDispatcher = context.m_174022_();
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferIn,
      int packedLightIn,
      EntityUmvuthana entityLivingBaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entityLivingBaseIn.f_20919_ < 27
         && entityLivingBaseIn.active
         && (
            entityLivingBaseIn.getActiveAbilityType() != EntityUmvuthana.TELEPORT_ABILITY
               || entityLivingBaseIn.getActiveAbility().getCurrentSection().sectionType == AbilitySection.AbilitySectionType.RECOVERY
         )) {
         poseStack.m_85836_();
         GeoModel model = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelResource(entityLivingBaseIn));
         String boneName = "head";
         Optional<GeoBone> bone = model.getBone(boneName);
         if (bone.isPresent() && !bone.get().isHidden()) {
            Matrix4f boneMatrix = bone.get().getModelSpaceXform();
            poseStack.m_166854_(boneMatrix);
            Pose matrixstack$entry = poseStack.m_85850_();
            Matrix4f matrix4f = matrixstack$entry.m_85861_();
            Vector4f vecTranslation = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
            vecTranslation.m_123607_(matrix4f);
            PoseStack newPoseStack = new PoseStack();
            newPoseStack.m_85837_((double)vecTranslation.m_123601_(), (double)vecTranslation.m_123615_(), (double)vecTranslation.m_123616_());
            Vector4f vecScale = new Vector4f(1.0F, 0.0F, 0.0F, 1.0F);
            vecScale.m_123607_(matrix4f);
            float scale = (float)new Vec3(
                  (double)(vecScale.m_123601_() - vecTranslation.m_123601_()),
                  (double)(vecScale.m_123615_() - vecTranslation.m_123615_()),
                  (double)(vecScale.m_123616_() - vecTranslation.m_123616_())
               )
               .m_82553_();
            newPoseStack.m_85841_(scale, scale, scale);
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(
               RenderType.m_110454_(new ResourceLocation("mowziesmobs", "textures/particle/sun_no_glow.png"), true)
            );
            Pose matrixstack$entry2 = newPoseStack.m_85850_();
            Matrix4f matrix4f2 = matrixstack$entry2.m_85861_();
            Matrix3f matrix3f = matrixstack$entry.m_85864_();
            this.drawSun(matrix4f2, matrix3f, ivertexbuilder, packedLightIn, (float)entityLivingBaseIn.f_19797_ + partialTicks);
         }

         poseStack.m_85849_();
      }
   }

   private void drawSun(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer builder, int packedLightIn, float time) {
      float sunRadius = 1.2F + (float)Math.sin((double)(time * 4.0F)) * 0.085F;
      this.drawVertex(matrix4f, matrix3f, builder, -sunRadius, -sunRadius, 0.0F, 0.0F, 0.0F, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, -sunRadius, sunRadius, 0.0F, 0.0F, 1.0F, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, sunRadius, sunRadius, 0.0F, 1.0F, 1.0F, 1.0F, packedLightIn);
      this.drawVertex(matrix4f, matrix3f, builder, sunRadius, -sunRadius, 0.0F, 1.0F, 0.0F, 1.0F, packedLightIn);
   }

   public void drawVertex(
      Matrix4f matrix,
      Matrix3f normals,
      VertexConsumer vertexBuilder,
      float offsetX,
      float offsetY,
      float offsetZ,
      float textureX,
      float textureY,
      float alpha,
      int packedLightIn
   ) {
      vertexBuilder.m_85982_(matrix, offsetX, offsetY, offsetZ)
         .m_85950_(1.0F, 1.0F, 1.0F, 1.0F)
         .m_7421_(textureX, textureY)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(15728880)
         .m_85977_(normals, 1.0F, 1.0F, 1.0F)
         .m_5752_();
   }
}
