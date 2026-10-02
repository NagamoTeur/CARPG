package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SelectableButton extends GuiImageButton {
   public ResourceLocation secondImage;
   public boolean isSelected;

   public SelectableButton(
      int x,
      int y,
      int u,
      int v,
      int w,
      int h,
      int image_width,
      int image_height,
      ResourceLocation resource_image,
      ResourceLocation secondImage,
      OnPress onPress
   ) {
      super(x, y, u, v, w, h, image_width, image_height, resource_image.m_135815_(), onPress);
      this.secondImage = secondImage;
   }

   @Override
   public void m_6305_(PoseStack ms, int parX, int parY, float partialTicks) {
      if (this.f_93624_) {
         if (this.parent != null
            && this.parent.isMouseInRelativeRange(parX, parY, this.f_93620_, this.f_93621_, this.f_93618_, this.f_93619_)
            && this.toolTip != null
            && !this.toolTip.toString().isEmpty()) {
            List<Component> tip = new ArrayList<>();
            tip.add(this.toolTip);
            this.parent.tooltip = tip;
         }

         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         ResourceLocation renderImage = this.isSelected ? this.secondImage : this.image;
         GuiSpellBook.drawFromTexture(
            renderImage, this.f_93620_, this.f_93621_, this.u, this.v, this.f_93618_, this.f_93619_, this.image_width, this.image_height, ms
         );
      }
   }
}
