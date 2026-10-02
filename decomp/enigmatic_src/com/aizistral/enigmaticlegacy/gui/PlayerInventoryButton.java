package com.aizistral.enigmaticlegacy.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nonnull;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.CreativeModeTab;
import top.theillusivec4.curios.client.gui.CuriosScreen;

public abstract class PlayerInventoryButton extends ImageButton {
   protected final AbstractContainerScreen<?> parentGui;
   protected final ResourceLocation f_94223_;
   protected int f_94224_ = 0;
   protected int f_94225_ = 0;
   protected int f_94227_ = 0;
   protected int f_94228_ = 0;
   protected int f_94226_ = 0;
   protected boolean isRecipeBookVisible = false;

   public PlayerInventoryButton(
      AbstractContainerScreen<?> gui,
      int xIn,
      int yIn,
      int widthIn,
      int heightIn,
      int xTexStartIn,
      int yTexStartIn,
      int yDiffTextIn,
      ResourceLocation resourceLocationIn,
      OnPress onPressIn
   ) {
      super(xIn, yIn, widthIn, heightIn, xTexStartIn, yTexStartIn, yDiffTextIn, resourceLocationIn, 256, 256, onPressIn);
      this.parentGui = gui;
      this.f_94223_ = resourceLocationIn;
      this.f_94224_ = xTexStartIn;
      this.f_94225_ = yTexStartIn;
      this.f_94227_ = 256;
      this.f_94228_ = 256;
      this.f_94226_ = yDiffTextIn;
   }

   public void m_6303_(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
      this.f_93623_ = true;
      if (this.parentGui instanceof InventoryScreen || this.parentGui instanceof CuriosScreen) {
         boolean lastVisible = this.isRecipeBookVisible;
         if (this.parentGui instanceof InventoryScreen) {
            this.isRecipeBookVisible = ((InventoryScreen)this.parentGui).m_5564_().m_100385_();
         } else if (this.parentGui instanceof CuriosScreen) {
            this.isRecipeBookVisible = ((CuriosScreen)this.parentGui).m_5564_().m_100385_();
         }

         if (lastVisible != this.isRecipeBookVisible) {
            Tuple<Integer, Integer> offsets = this.getOffsets(false);
            this.m_94278_(this.parentGui.getGuiLeft() + (Integer)offsets.m_14418_(), this.parentGui.getGuiTop() + (Integer)offsets.m_14419_());
         }
      } else if (this.parentGui instanceof CreativeModeInventoryScreen gui) {
         boolean isInventoryTab = gui.m_98628_() == CreativeModeTab.f_40761_.m_40775_();
         if (!isInventoryTab) {
            this.f_93623_ = false;
            return;
         }
      }

      if (this.beforeRender(poseStack, mouseX, mouseY, partialTicks)) {
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, this.f_94223_);
         int i = this.f_94225_;
         if (this.m_198029_()) {
            i += this.f_94226_;
         }

         RenderSystem.m_69482_();
         m_93133_(poseStack, this.f_93620_, this.f_93621_, (float)this.f_94224_, (float)i, this.f_93618_, this.f_93619_, this.f_94227_, this.f_94228_);
         if (this.f_93622_) {
            this.m_7428_(poseStack, mouseX, mouseY);
         }
      }
   }

   protected abstract boolean beforeRender(PoseStack var1, int var2, int var3, float var4);

   public abstract Tuple<Integer, Integer> getOffsets(boolean var1);
}
