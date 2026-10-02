package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class StateButton extends ANButton {
   public ResourceLocation texture;
   public int tile;
   public int state;
   public int texX = 0;
   public int texY = 0;
   public int imageWidth;
   public int imageHeight;

   public StateButton(int x, int y, int width, int height, int imageWidth, int imageHeight, int tile, ResourceLocation texture, OnPress pressable) {
      super(x, y, width, height, null, pressable);
      this.tile = tile;
      this.texture = texture;
      this.imageWidth = imageWidth;
      this.imageHeight = imageHeight;
   }

   public void m_6303_(PoseStack st, int mouseX, int mouseY, float pt) {
      if (this.f_93624_) {
         int x = this.getX();
         int y = this.getY();
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, this.texture);
         this.f_93622_ = mouseX >= x && mouseY >= y && mouseX < x + this.f_93618_ && mouseY < y + this.f_93619_;
         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         RenderSystem.m_69408_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         m_93133_(
            st,
            x,
            y,
            (float)(this.texX + this.state * this.f_93618_),
            (float)(this.texY + this.tile * this.f_93619_),
            this.f_93618_,
            this.f_93619_,
            this.imageWidth,
            this.imageHeight
         );
      }
   }
}
