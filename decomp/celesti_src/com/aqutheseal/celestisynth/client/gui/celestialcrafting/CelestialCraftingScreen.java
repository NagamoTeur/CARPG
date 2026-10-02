package com.aqutheseal.celestisynth.client.gui.celestialcrafting;

import com.aqutheseal.celestisynth.Celestisynth;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;

public class CelestialCraftingScreen extends AbstractContainerScreen<CelestialCraftingMenu> implements RecipeUpdateListener {
   private static final ResourceLocation CRAFTING_TABLE_LOCATION = Celestisynth.prefix("textures/gui/celestial_crafting_table.png");
   private static final ResourceLocation RECIPE_BUTTON_LOCATION = Celestisynth.prefix("textures/gui/celestial_recipe_button.png");
   private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
   private boolean widthTooNarrow;

   public CelestialCraftingScreen(CelestialCraftingMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
      super(pMenu, pPlayerInventory, pTitle);
   }

   protected void m_7856_() {
      super.m_7856_();
      this.widthTooNarrow = this.f_96543_ < 379;
      this.recipeBookComponent.m_100309_(this.f_96543_, this.f_96544_, this.f_96541_, this.widthTooNarrow, (RecipeBookMenu)this.f_97732_);
      this.f_97735_ = this.recipeBookComponent.m_181401_(this.f_96543_, this.f_97726_);
      this.m_142416_(new ImageButton(this.f_97735_ + 5, this.f_96544_ / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_LOCATION, targetButton -> {
         this.recipeBookComponent.m_100384_();
         this.f_97735_ = this.recipeBookComponent.m_181401_(this.f_96543_, this.f_97726_);
         ((ImageButton)targetButton).m_94278_(this.f_97735_ + 5, this.f_96544_ / 2 - 49);
      }));
      this.m_7787_(this.recipeBookComponent);
      this.m_94718_(this.recipeBookComponent);
      this.f_97728_ = 29;
   }

   public void m_181908_() {
      super.m_181908_();
      this.recipeBookComponent.m_100386_();
   }

   public void m_6305_(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
      this.m_7333_(pPoseStack);
      if (this.recipeBookComponent.m_100385_() && this.widthTooNarrow) {
         this.m_7286_(pPoseStack, pPartialTick, pMouseX, pMouseY);
         this.recipeBookComponent.m_6305_(pPoseStack, pMouseX, pMouseY, pPartialTick);
      } else {
         this.recipeBookComponent.m_6305_(pPoseStack, pMouseX, pMouseY, pPartialTick);
         super.m_6305_(pPoseStack, pMouseX, pMouseY, pPartialTick);
         this.recipeBookComponent.m_6545_(pPoseStack, this.f_97735_, this.f_97736_, true, pPartialTick);
      }

      this.m_7025_(pPoseStack, pMouseX, pMouseY);
      this.recipeBookComponent.m_100361_(pPoseStack, this.f_97735_, this.f_97736_, pMouseX, pMouseY);
   }

   protected void m_7286_(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, CRAFTING_TABLE_LOCATION);
      this.m_93228_(pPoseStack, super.f_97735_, super.f_97736_, 0, 0, this.f_97726_, this.f_97727_);
      int topLeftX = super.f_97735_ + super.f_97728_ - 6;
      int topLeftY = super.f_97736_ + super.f_97729_ - 4;
      int stringWidth = super.f_96547_.m_92852_(super.f_96539_);
      int topRightX = topLeftX + 9 + stringWidth;
      super.m_93228_(pPoseStack, topLeftX, topLeftY, 176, 0, 3, 14);
      super.m_93228_(pPoseStack, topLeftX + 3, topLeftY, 0, 205, stringWidth + 6, 14);
      super.m_93228_(pPoseStack, topRightX, topLeftY, 182, 0, 3, 14);
   }

   protected boolean m_6774_(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
      return (!this.widthTooNarrow || !this.recipeBookComponent.m_100385_()) && super.m_6774_(pX, pY, pWidth, pHeight, pMouseX, pMouseY);
   }

   public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
      if (this.recipeBookComponent.m_6375_(pMouseX, pMouseY, pButton)) {
         this.m_7522_(this.recipeBookComponent);
         return true;
      } else {
         return this.widthTooNarrow && this.recipeBookComponent.m_100385_() || super.m_6375_(pMouseX, pMouseY, pButton);
      }
   }

   protected boolean m_7467_(double pMouseX, double pMouseY, int pGuiLeft, int pGuiTop, int pMouseButton) {
      boolean mouseOutOfBounds = pMouseX < (double)pGuiLeft
         || pMouseY < (double)pGuiTop
         || pMouseX >= (double)(pGuiLeft + this.f_97726_)
         || pMouseY >= (double)(pGuiTop + this.f_97727_);
      return this.recipeBookComponent.m_100297_(pMouseX, pMouseY, this.f_97735_, this.f_97736_, this.f_97726_, this.f_97727_, pMouseButton) && mouseOutOfBounds;
   }

   protected void m_6597_(Slot pSlot, int pSlotId, int pMouseButton, ClickType pType) {
      super.m_6597_(pSlot, pSlotId, pMouseButton, pType);
      this.recipeBookComponent.m_6904_(pSlot);
   }

   public void m_6916_() {
      this.recipeBookComponent.m_100387_();
   }

   protected void m_7027_(PoseStack pPoseStack, int pMouseX, int pMouseY) {
      this.f_96547_.m_92889_(pPoseStack, this.f_96539_, (float)this.f_97728_, (float)this.f_97729_, 0);
   }

   public void m_7861_() {
      this.recipeBookComponent.m_100373_();
      super.m_7861_();
   }

   public RecipeBookComponent m_5564_() {
      return this.recipeBookComponent;
   }
}
