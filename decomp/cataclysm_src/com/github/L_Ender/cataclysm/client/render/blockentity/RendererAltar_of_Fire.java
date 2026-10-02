package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.AltarOfFire_Block_Entity;
import com.github.L_Ender.cataclysm.client.model.block.Altar_of_Fire_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RendererAltar_of_Fire<T extends AltarOfFire_Block_Entity> implements BlockEntityRenderer<T> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/altar_of_fire/altar_of_fire.png");
   private static final ResourceLocation[] TEXTURE_FIRE_PROGRESS = new ResourceLocation[8];
   public static final ResourceLocation FLAME_STRIKE = new ResourceLocation("cataclysm", "textures/entity/flame_strike_sigil.png");
   private static final Altar_of_Fire_Model MODEL = new Altar_of_Fire_Model();

   public RendererAltar_of_Fire(Context rendererDispatcherIn) {
      for (int i = 0; i < 8; i++) {
         TEXTURE_FIRE_PROGRESS[i] = new ResourceLocation("cataclysm", "textures/block/altar_of_fire/altarfire_" + i + ".png");
      }
   }

   public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      float f2 = (float)tileEntityIn.tickCount + partialTicks;
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.5, 1.5, 0.5);
      matrixStackIn.m_85841_(1.0F, -1.0F, -1.0F);
      MODEL.animate(tileEntityIn, partialTicks);
      MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110458_(TEXTURE)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      MODEL.m_7695_(
         matrixStackIn,
         bufferIn.m_6299_(CMRenderTypes.getGlowingEffect(this.getIdleTexture((int)(f2 * 0.5F % 7.0F)))),
         210,
         OverlayTexture.f_118083_,
         1.0F,
         1.0F,
         1.0F,
         1.0F
      );
      matrixStackIn.m_85849_();
      this.renderItem(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
      this.renderSigil(tileEntityIn, partialTicks, matrixStackIn, bufferIn);
   }

   private ResourceLocation getIdleTexture(int age) {
      return TEXTURE_FIRE_PROGRESS[Mth.m_14045_(age, 0, 7)];
   }

   public void renderItem(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn) {
      ItemStack stack = tileEntityIn.m_8020_(0);
      float f2 = (float)tileEntityIn.tickCount + partialTicks;
      if (!stack.m_41619_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5, 1.0, 0.5);
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(f2));
         BakedModel ibakedmodel = Minecraft.m_91087_().m_91291_().m_174264_(stack, tileEntityIn.m_58904_(), (LivingEntity)null, 0);
         boolean flag = ibakedmodel.m_7539_();
         if (!flag) {
            matrixStackIn.m_85837_(0.0, 0.0, 0.0);
         }

         Minecraft.m_91087_()
            .m_91291_()
            .m_115143_(stack, TransformType.GROUND, false, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.f_118083_, ibakedmodel);
         matrixStackIn.m_85849_();
      }
   }

   public void renderSigil(T tileEntityIn, float delta, PoseStack matrixStackIn, MultiBufferSource bufferIn) {
      if (tileEntityIn.summoningthis) {
         float f2 = (float)tileEntityIn.tickCount + delta;
         float f3 = (float)Mth.m_14045_(tileEntityIn.summoningticks, 0, 25);
         matrixStackIn.m_85836_();
         VertexConsumer ivertexbuilder = bufferIn.m_6299_(CMRenderTypes.getGlowingEffect(FLAME_STRIKE));
         matrixStackIn.m_85837_(0.5, 0.001, 0.5);
         matrixStackIn.m_85841_(f3 * 0.1F, f3 * 0.1F, f3 * 0.1F);
         matrixStackIn.m_85845_(Vector3f.f_122225_.m_122240_(90.0F + f2));
         Pose lvt_19_1_ = matrixStackIn.m_85850_();
         Matrix4f lvt_20_1_ = lvt_19_1_.m_85861_();
         Matrix3f lvt_21_1_ = lvt_19_1_.m_85864_();
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, -1, 0.0F, 0.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, -1, 0, 1, 0.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, 1, 1.0F, 1.0F, 1, 0, 1, 240);
         this.drawVertex(lvt_20_1_, lvt_21_1_, ivertexbuilder, 1, 0, -1, 1.0F, 0.0F, 1, 0, 1, 240);
         matrixStackIn.m_85849_();
      }
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
