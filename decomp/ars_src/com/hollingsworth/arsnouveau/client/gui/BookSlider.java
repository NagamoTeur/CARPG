package com.hollingsworth.arsnouveau.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class BookSlider extends ForgeSlider {
   public BookSlider(
      int x,
      int y,
      int width,
      int height,
      Component prefix,
      Component suffix,
      double minValue,
      double maxValue,
      double currentValue,
      double stepSize,
      int precision,
      boolean drawString
   ) {
      super(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, stepSize, precision, drawString);
   }

   public BookSlider(
      int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, boolean drawString
   ) {
      super(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, drawString);
   }

   protected void m_7906_(PoseStack pPoseStack, Minecraft pMinecraft, int pMouseX, int pMouseY) {
      RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/sound_bar_knob.png"));
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      m_93133_(pPoseStack, this.f_93620_ + (int)(this.f_93577_ * (double)(this.f_93618_ - 8)), this.f_93621_, 0.0F, 0.0F, 8, 20, 8, 20);
   }

   protected void m_5697_() {
   }

   public void m_6303_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
      Minecraft minecraft = Minecraft.m_91087_();
      Font font = minecraft.f_91062_;
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/sound_bar.png"));
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, this.f_93625_);
      int i = this.m_7202_(this.m_198029_());
      RenderSystem.m_69478_();
      RenderSystem.m_69453_();
      RenderSystem.m_69482_();
      m_93133_(pPoseStack, this.f_93620_, this.f_93621_, 0.0F, 0.0F, 100, 20, this.f_93618_, this.f_93619_);
      this.m_7906_(pPoseStack, minecraft, pMouseX, pMouseY);
      int j = 10526880;
      font.m_92889_(
         pPoseStack,
         this.m_6035_(),
         (float)this.f_93620_ + (float)this.f_93618_ / 4.0F,
         (float)this.f_93621_ + (float)(this.f_93619_ - 32) / 2.0F,
         j | Mth.m_14167_(this.f_93625_ * 255.0F) << 24
      );
   }

   public void m_6305_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
      super.m_6305_(pPoseStack, pMouseX, pMouseY, pPartialTick);
   }
}
