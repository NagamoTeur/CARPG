package com.bobmowzie.mowziesmobs.client.render.entity.layer;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import java.util.Optional;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class UmvuthiSunLayer extends GeoLayerRenderer<EntityUmvuthi> {
   protected Matrix4f dispatchedMat = new Matrix4f();
   protected Matrix4f renderEarlyMat = new Matrix4f();
   private final Vec3 v1 = new Vec3(-2.0, 1.0, -1.0);
   private final Vec3 v2 = new Vec3(0.0, 1.0, -1.0);
   private final Vec3 v3 = new Vec3(-1.0, 0.0, 1.0);
   private final Vec3 v4 = new Vec3(-1.0, 0.0, -3.0);
   private final Vec3 v5 = new Vec3(-3.0, -1.0, 0.0);
   private final Vec3 v6 = new Vec3(-3.0, -1.0, -2.0);
   private final Vec3 v7 = new Vec3(1.0, -1.0, 0.0);
   private final Vec3 v8 = new Vec3(1.0, -1.0, -2.0);
   private final Vec3 v9 = new Vec3(0.0, -3.0, -1.0);
   private final Vec3 v10 = new Vec3(-2.0, -3.0, -1.0);
   private final Vec3 v11 = new Vec3(-1.0, -2.0, 1.0);
   private final Vec3 v12 = new Vec3(-1.0, -2.0, -3.0);
   private final Vec3[] POS = new Vec3[]{
      this.v1,
      this.v2,
      this.v3,
      this.v1,
      this.v1,
      this.v2,
      this.v4,
      this.v1,
      this.v1,
      this.v5,
      this.v6,
      this.v1,
      this.v2,
      this.v7,
      this.v8,
      this.v2,
      this.v2,
      this.v8,
      this.v4,
      this.v2,
      this.v1,
      this.v4,
      this.v6,
      this.v1,
      this.v1,
      this.v5,
      this.v3,
      this.v1,
      this.v2,
      this.v3,
      this.v7,
      this.v2,
      this.v9,
      this.v7,
      this.v8,
      this.v9,
      this.v5,
      this.v6,
      this.v10,
      this.v5,
      this.v9,
      this.v10,
      this.v11,
      this.v9,
      this.v9,
      this.v10,
      this.v12,
      this.v9,
      this.v4,
      this.v6,
      this.v12,
      this.v4,
      this.v4,
      this.v8,
      this.v12,
      this.v4,
      this.v3,
      this.v5,
      this.v11,
      this.v3,
      this.v3,
      this.v7,
      this.v11,
      this.v3,
      this.v5,
      this.v10,
      this.v11,
      this.v5,
      this.v7,
      this.v9,
      this.v11,
      this.v7,
      this.v8,
      this.v9,
      this.v12,
      this.v8,
      this.v6,
      this.v10,
      this.v12,
      this.v6
   };

   public UmvuthiSunLayer(IGeoRenderer<EntityUmvuthi> entityRendererIn) {
      super(entityRendererIn);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource bufferIn,
      int packedLightIn,
      EntityUmvuthi entityLivingBaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entityLivingBaseIn.shouldRenderSun()) {
         poseStack.m_85836_();
         GeoModel model = this.entityRenderer.getGeoModelProvider().getModel(this.entityRenderer.getGeoModelProvider().getModelResource(entityLivingBaseIn));
         String boneName = "sun_render";
         Optional<GeoBone> bone = model.getBone(boneName);
         if (bone.isPresent() && !bone.get().isHidden()) {
            Matrix4f boneMatrix = bone.get().getModelSpaceXform();
            poseStack.m_166854_(boneMatrix);
            poseStack.m_85837_(0.06, 0.0, -0.0);
            poseStack.m_85841_(0.06F, 0.06F, 0.06F);
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110454_(new ResourceLocation("mowziesmobs", "textures/effects/sun_effect.png"), true));
            Pose matrixstack$entry = poseStack.m_85850_();
            Matrix4f matrix4f = matrixstack$entry.m_85861_();
            Matrix3f matrix3f = matrixstack$entry.m_85864_();
            float scaleMult = 1.0F;
            if (entityLivingBaseIn.getActiveAbilityType() == EntityUmvuthi.SUPERNOVA_ABILITY && entityLivingBaseIn.getActiveAbility().getTicksInUse() > 90) {
               scaleMult = ((float)entityLivingBaseIn.getActiveAbility().getTicksInUse() + partialTicks - 90.0F) / 10.0F;
               scaleMult = Mth.m_14036_(scaleMult, 0.0F, 1.0F);
            }

            this.drawSun(matrix4f, matrix3f, ivertexbuilder, (float)entityLivingBaseIn.f_19797_ + partialTicks, scaleMult);
         }

         poseStack.m_85849_();
      }
   }

   private void drawSun(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer builder, float time, float scaleMultiplier) {
      float scale = (0.9F + (float)Math.sin((double)(time * 4.0F)) * 0.07F) * scaleMultiplier;

      for (int i = 0; i < 4; i++) {
         for (Vec3 vec : this.POS) {
            vec = vec.m_82542_((double)(1.0F + scale * (float)i), (double)(1.0F + scale * (float)i), (double)(1.0F + scale * (float)i));
            builder.m_85982_(matrix4f, (float)vec.f_82479_ + scale * (float)i, (float)vec.f_82480_ + scale * (float)i, (float)vec.f_82481_ + scale * (float)i)
               .m_85950_(1.0F, 1.0F, 0.4F, 0.2F)
               .m_7421_(0.0F, 0.5F)
               .m_86008_(OverlayTexture.f_118083_)
               .m_85969_(15728880)
               .m_85977_(matrix3f, 1.0F, 1.0F, 1.0F)
               .m_5752_();
         }
      }

      for (Vec3 vec : this.POS) {
         builder.m_85982_(
               matrix4f,
               (float)vec.f_82479_ * 1.2F * scaleMultiplier,
               (float)vec.f_82480_ * 1.2F * scaleMultiplier,
               (float)vec.f_82481_ * 1.2F * scaleMultiplier
            )
            .m_85950_(1.0F, 1.0F, 1.0F, 1.0F)
            .m_7421_(0.0F, 0.5F)
            .m_86008_(OverlayTexture.f_118083_)
            .m_85969_(15728880)
            .m_85977_(matrix3f, 1.0F, 1.0F, 1.0F)
            .m_5752_();
      }
   }
}
