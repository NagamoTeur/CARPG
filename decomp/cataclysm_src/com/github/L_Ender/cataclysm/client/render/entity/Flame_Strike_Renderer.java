package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Flame_Strike_Renderer extends EntityRenderer<Flame_Strike_Entity> {
   public static final ResourceLocation FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/flame_strike_sigil.png");
   public static final ResourceLocation SOUL_FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/soul_flame_strike_sigil.png");

   public Flame_Strike_Renderer(Context mgr) {
      super(mgr);
   }

   public ResourceLocation getTextureLocation(Flame_Strike_Entity entity) {
      return entity.isSoul() ? SOUL_FLAME_STRIKE : FLAME_STRIKE;
   }

   public void render(Flame_Strike_Entity flameStrike, float entityYaw, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      float f2 = (float)flameStrike.f_19797_ + delta;
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(CMRenderTypes.getGlowingEffect(this.getTextureLocation(flameStrike)));
      matrixStackIn.m_85841_(flameStrike.getRadius(), flameStrike.getRadius(), flameStrike.getRadius());
      matrixStackIn.m_85837_(0.0, 0.001, 0.0);
      if (flameStrike.isSoul()) {
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f2));
      } else {
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F - flameStrike.m_146908_() + f2));
      }

      Pose lvt_19_1_ = matrixStackIn.m_85850_();
      Matrix4f lvt_20_1_ = lvt_19_1_.m_85861_();
      Matrix3f lvt_21_1_ = lvt_19_1_.m_85864_();
      if (flameStrike.isSee()) {
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
      }

      matrixStackIn.m_85849_();
      super.m_7392_(flameStrike, entityYaw, delta, matrixStackIn, bufferIn, packedLightIn);
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
}
