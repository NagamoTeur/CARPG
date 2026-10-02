package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
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

public class Laser_Beam_Renderer extends EntityRenderer<Laser_Beam_Entity> {
   private static final ResourceLocation TEXTURE_RED = new ResourceLocation("cataclysm", "textures/entity/harbinger/laser_beam.png");
   private static final RenderType RENDER_TYPE_RED = RenderType.m_110488_(TEXTURE_RED);

   public Laser_Beam_Renderer(Context mgr) {
      super(mgr);
   }

   public void render(Laser_Beam_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(45.0F));
      matrixStackIn.m_85841_(0.05625F, 0.05625F, 0.05625F);
      matrixStackIn.m_85837_(0.0, 0.0, 0.0);
      VertexConsumer vertexconsumer = bufferIn.m_6299_(RENDER_TYPE_RED);
      Pose posestack$pose = matrixStackIn.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

      for (int j = 0; j < 4; j++) {
         matrixStackIn.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
         this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
         this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
         this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
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
      float p_113832_,
      float p_113833_,
      int p_113834_,
      int p_113835_,
      int p_113836_,
      int p_113837_
   ) {
      p_113828_.m_85982_(p_113826_, (float)p_113829_, (float)p_113830_, (float)p_113831_)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(p_113832_, p_113833_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(p_113837_)
         .m_85977_(p_113827_, (float)p_113834_, (float)p_113836_, (float)p_113835_)
         .m_5752_();
   }

   public ResourceLocation getTextureLocation(Laser_Beam_Entity entity) {
      return TEXTURE_RED;
   }
}
