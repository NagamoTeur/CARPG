package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class StorageSettingsButton extends StateButton {
   public StorageSettingsButton(int x, int y, int width, int height, int imageWidth, int imageHeight, int tile, ResourceLocation texture, OnPress pressable) {
      super(x, y, width, height, imageWidth, imageHeight, tile, texture, pressable);
   }

   public void m_6305_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
      super.m_6305_(pPoseStack, pMouseX, pMouseY, pPartialTick);
   }

   @Override
   public void m_6303_(PoseStack st, int mouseX, int mouseY, float pt) {
      if (this.f_93624_) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/storage_tab1.png"));
         m_93133_(st, this.f_93620_, this.f_93621_, 0.0F, 0.0F, 22, 13, 22, 13);
      }

      super.m_6303_(st, mouseX, mouseY, pt);
   }
}
