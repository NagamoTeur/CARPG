package com.hollingsworth.arsnouveau.client.gui.utils;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.client.gui.Color;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RenderUtils {
   private static final RenderType TRANSLUCENT = RenderType.m_110473_(TextureAtlas.f_118259_);

   public static void drawSpellPart(AbstractSpellPart objectToBeDrawn, PoseStack poseStack, int positionX, int positionY, int size, boolean renderTransparent) {
      drawItemAsIcon(objectToBeDrawn.glyphItem, poseStack, positionX, positionY, size, renderTransparent);
   }

   public static void drawItemAsIcon(Item providedItem, PoseStack poseStack, int positionX, int positionY, int size, boolean renderTransparent) {
      drawItemAsIcon(new ItemStack(providedItem), poseStack, positionX, positionY, size, renderTransparent);
   }

   public static void drawItemAsIcon(ItemStack itemStack, PoseStack poseStack, int positionX, int positionY, int size, boolean renderTransparent) {
      ItemRenderer itemRenderer = Minecraft.m_91087_().m_91291_();
      BakedModel itemBakedModel = itemRenderer.m_174264_(itemStack, null, null, 0);
      Minecraft.m_91087_().m_91097_().m_118506_(TextureAtlas.f_118259_).m_117960_(false, false);
      RenderSystem.m_157456_(0, TextureAtlas.f_118259_);
      RenderSystem.m_69478_();
      RenderSystem.m_69408_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      poseStack.m_85836_();
      poseStack.m_85837_((double)positionX, (double)positionY, 100.0);
      poseStack.m_85837_(8.0, 8.0, 0.0);
      poseStack.m_85841_(1.0F, -1.0F, 1.0F);
      poseStack.m_85841_((float)size, (float)size, (float)size);
      RenderSystem.m_157182_();
      BufferSource multibuffersource$buffersource = Minecraft.m_91087_().m_91269_().m_110104_();
      boolean flag = !itemBakedModel.m_7547_();
      if (flag) {
         Lighting.m_84930_();
      }

      if (renderTransparent) {
         itemRenderer.m_115143_(
            itemStack,
            TransformType.GUI,
            false,
            poseStack,
            transparentBuffer(multibuffersource$buffersource),
            15728880,
            OverlayTexture.f_118083_,
            itemBakedModel
         );
      } else {
         itemRenderer.m_115143_(
            itemStack, TransformType.GUI, false, poseStack, multibuffersource$buffersource, 15728880, OverlayTexture.f_118083_, itemBakedModel
         );
      }

      multibuffersource$buffersource.m_109911_();
      if (flag) {
         Lighting.m_84931_();
      }

      if (renderTransparent) {
         RenderSystem.m_69458_(true);
         RenderSystem.m_69416_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
      }

      poseStack.m_85849_();
      RenderSystem.m_157182_();
      if (renderTransparent) {
         RenderSystem.m_69461_();
         RenderSystem.m_69453_();
      }
   }

   private static MultiBufferSource transparentBuffer(MultiBufferSource buffer) {
      return renderType -> new TintedVertexConsumer(buffer.m_6299_(TRANSLUCENT), 1.0F, 1.0F, 1.0F, 0.25F);
   }

   public static void drawTextureFromResourceLocation(
      ResourceLocation providedResourceLocation, PoseStack stack, int x, int y, int size, boolean renderTransparent
   ) {
      RenderSystem.m_69478_();
      if (renderTransparent) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 0.5F);
      } else {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      }

      RenderSystem.m_157456_(0, providedResourceLocation);
      GuiComponent.m_93133_(stack, x, y, 0.0F, 0.0F, size, size, size, size);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69461_();
   }

   public static void colorBlit(
      PoseStack mStack, int x, int y, int uOffset, int vOffset, int width, int height, int textureWidth, int textureHeight, Color color
   ) {
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      Matrix4f matrix = mStack.m_85850_().m_85861_();
      int maxX = x + width;
      int maxY = y + height;
      float minU = (float)uOffset / (float)textureWidth;
      float minV = (float)vOffset / (float)textureHeight;
      float maxU = minU + (float)width / (float)textureWidth;
      float maxV = minV + (float)height / (float)textureHeight;
      int r = color.getRed();
      int g = color.getGreen();
      int b = color.getBlue();
      int alpha = color.getAlpha();
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_85982_(matrix, (float)x, (float)maxY, 0.0F).m_7421_(minU, maxV).m_6122_(r, g, b, alpha).m_5752_();
      bufferbuilder.m_85982_(matrix, (float)maxX, (float)maxY, 0.0F).m_7421_(maxU, maxV).m_6122_(r, g, b, alpha).m_5752_();
      bufferbuilder.m_85982_(matrix, (float)maxX, (float)y, 0.0F).m_7421_(maxU, minV).m_6122_(r, g, b, alpha).m_5752_();
      bufferbuilder.m_85982_(matrix, (float)x, (float)y, 0.0F).m_7421_(minU, minV).m_6122_(r, g, b, alpha).m_5752_();
      BufferUploader.m_231202_(bufferbuilder.m_231175_());
      RenderSystem.m_69461_();
   }
}
