package com.aizistral.enigmaticlegacy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;

public class GUIUtils {
   public static final int DEFAULT_BACKGROUND_COLOR = -267386864;
   public static final int DEFAULT_BORDER_COLOR_START = 1347420415;
   public static final int DEFAULT_BORDER_COLOR_END = 1344798847;

   public static void drawGradientRect(Matrix4f mat, int zLevel, int left, int top, int right, int bottom, int startColor, int endColor) {
      float startAlpha = (float)(startColor >> 24 & 0xFF) / 255.0F;
      float startRed = (float)(startColor >> 16 & 0xFF) / 255.0F;
      float startGreen = (float)(startColor >> 8 & 0xFF) / 255.0F;
      float startBlue = (float)(startColor & 0xFF) / 255.0F;
      float endAlpha = (float)(endColor >> 24 & 0xFF) / 255.0F;
      float endRed = (float)(endColor >> 16 & 0xFF) / 255.0F;
      float endGreen = (float)(endColor >> 8 & 0xFF) / 255.0F;
      float endBlue = (float)(endColor & 0xFF) / 255.0F;
      RenderSystem.m_69482_();
      RenderSystem.m_69472_();
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      Tesselator tessellator = Tesselator.m_85913_();
      BufferBuilder buffer = tessellator.m_85915_();
      buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85815_);
      buffer.m_85982_(mat, (float)right, (float)top, (float)zLevel).m_85950_(startRed, startGreen, startBlue, startAlpha).m_5752_();
      buffer.m_85982_(mat, (float)left, (float)top, (float)zLevel).m_85950_(startRed, startGreen, startBlue, startAlpha).m_5752_();
      buffer.m_85982_(mat, (float)left, (float)bottom, (float)zLevel).m_85950_(endRed, endGreen, endBlue, endAlpha).m_5752_();
      buffer.m_85982_(mat, (float)right, (float)bottom, (float)zLevel).m_85950_(endRed, endGreen, endBlue, endAlpha).m_5752_();
      tessellator.m_85914_();
      RenderSystem.m_69461_();
      RenderSystem.m_69493_();
   }
}
