package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class StorageTabButton extends StateButton {
   public boolean isSelected;
   public String highlightText;
   public boolean isAll = false;

   public StorageTabButton(int x, int y, int width, int height, int imageWidth, int imageHeight, int tile, ResourceLocation texture, OnPress pressable) {
      super(x, y, width, height, imageWidth, imageHeight, tile, texture, pressable);
   }

   public StorageTabButton(
      int x, int y, int width, int height, int imageWidth, int imageHeight, int state, int tile, ResourceLocation texture, OnPress pressable
   ) {
      super(x, y, width, height, imageWidth, imageHeight, tile, texture, pressable);
      this.state = state;
   }

   @Override
   public void m_6303_(PoseStack st, int mouseX, int mouseY, float pt) {
      if (this.f_93624_) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, new ResourceLocation("ars_nouveau", "textures/gui/storage_tab2" + (this.isSelected ? "_selected" : "") + ".png"));
         m_93133_(st, this.f_93620_, this.f_93621_, 0.0F, 0.0F, 18, 13, 18, 13);
      }

      super.m_6303_(st, mouseX, mouseY, pt);
   }
}
