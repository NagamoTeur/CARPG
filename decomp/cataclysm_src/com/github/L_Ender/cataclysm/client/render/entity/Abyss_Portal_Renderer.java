package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Portal_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Abyss_Portal_Renderer extends EntityRenderer<Abyss_Portal_Entity> {
   private static final ResourceLocation TEXTURE_0 = new ResourceLocation("cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_0.png");
   private static final ResourceLocation TEXTURE_1 = new ResourceLocation("cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_1.png");
   private static final ResourceLocation TEXTURE_2 = new ResourceLocation("cataclysm", "textures/entity/leviathan/portal/abyss_portal_idle_2.png");
   private static final ResourceLocation[] TEXTURE_PROGRESS = new ResourceLocation[8];

   public Abyss_Portal_Renderer(Context renderManagerIn) {
      super(renderManagerIn);

      for (int i = 0; i < 8; i++) {
         TEXTURE_PROGRESS[i] = new ResourceLocation("cataclysm", "textures/entity/leviathan/portal/abyss_portal_grow_" + i + ".png");
      }
   }

   public void render(Abyss_Portal_Entity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0, 0.01F, 0.0);
      matrixStackIn.m_85841_(4.0F, 4.0F, 4.0F);
      this.renderPortal(entityIn, matrixStackIn, bufferIn, false);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private void renderPortal(Abyss_Portal_Entity entityIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, boolean shattered) {
      ResourceLocation tex;
      if (entityIn.getLifespan() < 20) {
         tex = this.getGrowingTexture((int)((float)entityIn.getLifespan() * 0.5F % 20.0F));
      } else if (entityIn.f_19797_ < 20) {
         tex = this.getGrowingTexture((int)((float)entityIn.f_19797_ * 0.5F % 20.0F));
      } else {
         tex = this.getIdleTexture(entityIn.f_19797_ % 9);
      }

      VertexConsumer ivertexbuilder = bufferIn.m_6299_(CMRenderTypes.getfullBright(tex));
      this.renderArc(matrixStackIn, ivertexbuilder);
   }

   private void renderArc(PoseStack matrixStackIn, VertexConsumer ivertexbuilder) {
      matrixStackIn.m_85836_();
      Pose lvt_19_1_ = matrixStackIn.m_85850_();
      Matrix4f lvt_20_1_ = lvt_19_1_.m_85861_();
      Matrix3f lvt_21_1_ = lvt_19_1_.m_85864_();
      this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
      this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
      matrixStackIn.m_85849_();
   }

   public ResourceLocation getTextureLocation(Abyss_Portal_Entity entity) {
      return TEXTURE_0;
   }

   public void drawVertex(
      Matrix4f p_229039_1_,
      Matrix3f p_229039_2_,
      VertexConsumer p_229039_3_,
      int p_229039_4_,
      int p_229039_5_,
      int p_229039_6_,
      float p_229039_7_,
      float p_229039_8_,
      int p_229039_9_,
      int p_229039_10_,
      int p_229039_11_,
      int p_229039_12_
   ) {
      p_229039_3_.m_85982_(p_229039_1_, (float)p_229039_4_, (float)p_229039_5_, (float)p_229039_6_)
         .m_6122_(255, 255, 255, 255)
         .m_7421_(p_229039_7_, p_229039_8_)
         .m_86008_(OverlayTexture.f_118083_)
         .m_85969_(p_229039_12_)
         .m_85977_(p_229039_2_, (float)p_229039_9_, (float)p_229039_11_, (float)p_229039_10_)
         .m_5752_();
   }

   public ResourceLocation getIdleTexture(int age) {
      if (age < 3) {
         return TEXTURE_0;
      } else if (age < 6) {
         return TEXTURE_1;
      } else {
         return age < 10 ? TEXTURE_2 : TEXTURE_0;
      }
   }

   public ResourceLocation getGrowingTexture(int age) {
      return TEXTURE_PROGRESS[Mth.m_14045_(age, 0, 7)];
   }
}
