package com.hollingsworth.arsnouveau.client.gui.buttons;

import com.hollingsworth.arsnouveau.client.gui.book.BaseBook;
import com.hollingsworth.arsnouveau.client.gui.book.GuiSpellBook;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiImageButton extends ANButton {
   public ResourceLocation image;
   public String resourceIcon;
   public int u;
   public int v;
   public int image_width;
   public int image_height;
   public BaseBook parent;
   public Component toolTip;
   public boolean soundDisabled = false;

   public GuiImageButton(int x, int y, int u, int v, int w, int h, int image_width, int image_height, String resource_image, OnPress onPress) {
      this(x, y, u, v, w, h, image_width, image_height, new ResourceLocation("ars_nouveau", resource_image), onPress);
   }

   public GuiImageButton(int x, int y, int u, int v, int w, int h, int image_width, int image_height, ResourceLocation image, OnPress onPress) {
      super(x, y, w, h, Component.m_237113_(""), onPress);
      this.f_93620_ = x;
      this.f_93621_ = y;
      this.resourceIcon = image.m_135815_();
      this.u = u;
      this.v = v;
      this.image_height = image_height;
      this.image_width = image_width;
      this.image = image;
   }

   public GuiImageButton withTooltip(BaseBook parent, Component toolTip) {
      this.parent = parent;
      this.toolTip = toolTip;
      return this;
   }

   protected void m_7906_(PoseStack p_230441_1_, Minecraft p_230441_2_, int p_230441_3_, int p_230441_4_) {
   }

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
         GuiSpellBook.drawFromTexture(
            this.image, this.f_93620_, this.f_93621_, this.u, this.v, this.f_93618_, this.f_93619_, this.image_width, this.image_height, ms
         );
      }
   }

   public void m_7435_(SoundManager pHandler) {
      if (!this.soundDisabled) {
         super.m_7435_(pHandler);
      }
   }

   @Override
   public void setPosition(int pX, int pY) {
      this.f_93620_ = pX;
      this.f_93621_ = pY;
   }
}
