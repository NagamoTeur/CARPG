package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Poison_Dart_Entity;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Poison_Dart_Renderer extends EntityRenderer<Poison_Dart_Entity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/entity/poison_dart.png");

   public Poison_Dart_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public ResourceLocation getTextureLocation(Poison_Dart_Entity entity) {
      return TEXTURE;
   }

   public void render(Poison_Dart_Entity p_113839_, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_) {
      p_113842_.m_85836_();
      p_113842_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(p_113841_, p_113839_.f_19859_, p_113839_.m_146908_()) - 90.0F));
      p_113842_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(p_113841_, p_113839_.f_19860_, p_113839_.m_146909_())));
      int i = 0;
      float f = 0.0F;
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = 0.15625F;
      float f4 = 0.0F;
      float f5 = 0.15625F;
      float f6 = 0.15625F;
      float f7 = 0.3125F;
      float f8 = 0.05625F;
      float f9 = (float)p_113839_.f_36706_ - p_113841_;
      if (f9 > 0.0F) {
         float f10 = -Mth.m_14031_(f9 * 3.0F) * f9;
         p_113842_.m_85845_(Vector3f.f_122227_.m_122240_(f10));
      }

      p_113842_.m_85845_(Vector3f.f_122223_.m_122240_(45.0F));
      p_113842_.m_85841_(0.05625F, 0.05625F, 0.05625F);
      p_113842_.m_85837_(-1.0, 0.0, 0.0);
      VertexConsumer vertexconsumer = p_113843_.m_6299_(RenderType.m_110452_(this.getTextureLocation(p_113839_)));
      Pose posestack$pose = p_113842_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, p_113844_);
      this.vertex(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, p_113844_);

      for (int j = 0; j < 4; j++) {
         p_113842_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, p_113844_);
         this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, p_113844_);
         this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, p_113844_);
         this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, p_113844_);
      }

      p_113842_.m_85849_();
      super.m_7392_(p_113839_, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
   }

   public void vertex(
      Matrix4f p_254392_,
      Matrix3f p_254011_,
      VertexConsumer p_253902_,
      int p_254058_,
      int p_254338_,
      int p_254196_,
      float p_254003_,
      float p_254165_,
      int p_253982_,
      int p_254037_,
      int p_254038_,
      int p_254271_
   ) {
      p_253902_.m_85982_(p_254392_, (float)p_254058_, (float)p_254338_, (float)p_254196_)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(p_254003_, p_254165_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(p_254271_)
         .m_85977_(p_254011_, (float)p_253982_, (float)p_254038_, (float)p_254037_)
         .m_5752_();
   }
}
