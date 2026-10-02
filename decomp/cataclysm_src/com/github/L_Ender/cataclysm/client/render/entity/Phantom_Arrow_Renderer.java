package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Arrow_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Phantom_Arrow_Renderer extends EntityRenderer<Phantom_Arrow_Entity> {
   private static final ResourceLocation TEXTURE_RED = new ResourceLocation("cataclysm", "textures/entity/maledictus/phantom_arrow.png");
   private static final RenderType RENDER_TYPE_RED = CMRenderTypes.getGhost(TEXTURE_RED);

   public Phantom_Arrow_Renderer(Context mgr) {
      super(mgr);
   }

   public void render(
      Phantom_Arrow_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn
   ) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      float f1 = 0.15625F;
      float f2 = 0.3125F;
      float f9 = (float)entityIn.f_36706_ - partialTicks;
      if (f9 > 0.0F) {
         float f10 = -Mth.m_14031_(f9 * 3.0F) * f9;
         matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(f10));
      }

      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(45.0F));
      matrixStackIn.m_85841_(0.075F, 0.075F, 0.075F);
      matrixStackIn.m_85837_(-4.0, 0.0, 0.0);
      VertexConsumer vertexconsumer = bufferIn.m_6299_(RENDER_TYPE_RED);
      Pose posestack$pose = matrixStackIn.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      float hide = (float)entityIn.getTransparency() / 200.0F;
      float alpha = 1.0F - hide;
      int light = (int)(255.0F * Mth.m_14036_(alpha, 0.0F, 1.0F));
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, light, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, light, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, light, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, light, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, light, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, light, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, light, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, light, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

      for (int j = 0; j < 4; j++) {
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, light, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
         this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, light, 0.65F, 0.0F, 0, 1, 0, packedLightIn);
         this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, light, 0.65F, 0.15625F, 0, 1, 0, packedLightIn);
         this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, light, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
      }

      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public void vertex(
      Matrix4f p_113826_,
      Matrix3f p_113827_,
      VertexConsumer p_113828_,
      int p_113829_,
      int p_113830_,
      int p_113831_,
      int light,
      float p_113832_,
      float p_113833_,
      int p_113834_,
      int p_113835_,
      int p_113836_,
      int p_113837_
   ) {
      p_113828_.m_85982_(p_113826_, (float)p_113829_, (float)p_113830_, (float)p_113831_)
         .m_6122_(255, 255, 255, light)
         .m_7421_(p_113832_, p_113833_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(p_113837_)
         .m_85977_(p_113827_, (float)p_113834_, (float)p_113836_, (float)p_113835_)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(Phantom_Arrow_Entity entity) {
      return TEXTURE_RED;
   }
}
