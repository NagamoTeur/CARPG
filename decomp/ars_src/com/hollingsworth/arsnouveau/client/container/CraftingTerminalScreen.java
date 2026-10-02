package com.hollingsworth.arsnouveau.client.container;

import com.hollingsworth.arsnouveau.client.gui.buttons.GuiImageButton;
import com.hollingsworth.arsnouveau.setup.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.recipebook.GhostRecipe;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.gui.screens.recipebook.GhostRecipe.GhostIngredient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CraftingTerminalScreen extends AbstractStorageTerminalScreen<CraftingTerminalMenu> implements RecipeUpdateListener {
   private static final ResourceLocation gui = new ResourceLocation("ars_nouveau", "textures/gui/crafting_terminal.png");
   private static final ResourceLocation gui_expanded = new ResourceLocation("ars_nouveau", "textures/gui/crafting_terminal_expanded.png");
   private final RecipeBookComponent recipeBookGui = new RecipeBookComponent();
   private boolean widthTooNarrow;
   private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("ars_nouveau", "textures/gui/recipe_book.png");
   private static final ResourceLocation CLEAR_CRAFT_TEXTURE = new ResourceLocation("ars_nouveau", "textures/gui/craft_clear.png");
   private static final ResourceLocation EXPAND_TEXTURE = new ResourceLocation("ars_nouveau", "textures/gui/expand_inventory.png");
   private static final ResourceLocation COLLAPSE_TEXTURE = new ResourceLocation("ars_nouveau", "textures/gui/collapse_inventory.png");
   private EditBox recipeBookSearch;
   private GhostRecipe ghostRecipe;
   public GuiImageButton btnClr;
   public GuiImageButton btnRecipeBook;
   public GuiImageButton btnExpand;
   public GuiImageButton btnCollapse;

   public CraftingTerminalScreen(CraftingTerminalMenu screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);

      try {
         this.recipeBookGui.f_100285_ = (CraftingTerminalMenu)this.m_6262_().new TerminalRecipeItemHelper();
         this.ghostRecipe = this.recipeBookGui.f_100269_;
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }
   }

   @Override
   public ResourceLocation getGui() {
      return this.expanded ? gui_expanded : gui;
   }

   @Override
   protected void onUpdateSearch(String text) {
      if (IAutoFillTerminal.hasSync() || this.searchType == 1) {
         if (this.recipeBookSearch != null) {
            this.recipeBookSearch.m_94144_(text);
         }

         this.recipeBookGui.m_100387_();
      }
   }

   @Override
   protected void m_7856_() {
      this.f_97726_ = 202;
      this.f_97727_ = 248;
      this.rowCount = 3;
      super.m_7856_();
      this.widthTooNarrow = this.f_96543_ < 379;
      this.recipeBookGui.m_100309_(this.f_96543_, this.f_96544_ + 30, this.f_96541_, this.widthTooNarrow, (RecipeBookMenu)this.f_97732_);
      this.f_97735_ = this.recipeBookGui.m_181401_(this.f_96543_, this.f_97726_);
      this.m_142416_(this.recipeBookGui);
      this.m_94718_(this.recipeBookGui);
      int recipeButtonY = this.f_96544_ / 2 - 34;
      int collapseButtonY = this.f_96544_ / 2 + 23;
      this.btnClr = new GuiImageButton(this.f_97735_ + 86, recipeButtonY, 0, 0, 9, 9, 9, 9, CLEAR_CRAFT_TEXTURE, b -> this.clearGrid());
      this.btnExpand = new GuiImageButton(this.f_97735_ + 86, recipeButtonY - 12, 0, 0, 14, 3, 14, 3, EXPAND_TEXTURE, b -> this.expandScreen());
      this.btnCollapse = new GuiImageButton(this.f_97735_ + 86, collapseButtonY, 0, 0, 14, 3, 14, 3, COLLAPSE_TEXTURE, b -> this.collapseScreen());
      this.btnCollapse.f_93624_ = this.expanded;
      this.btnExpand.f_93624_ = !this.expanded;
      this.m_142416_(this.btnClr);
      this.m_142416_(this.btnCollapse);
      this.m_142416_(this.btnExpand);
      this.btnRecipeBook = (GuiImageButton)this.m_142416_(
         new GuiImageButton(this.f_97735_ + 98, recipeButtonY, 0, 0, 9, 9, 9, 9, RECIPE_BUTTON_TEXTURE, thisButton -> {
            this.recipeBookGui.m_181404_();
            this.recipeBookSearch = this.recipeBookGui.f_100281_;
            this.recipeBookGui.m_100384_();
            this.f_97735_ = this.recipeBookGui.m_181401_(this.f_96543_, this.f_97726_);
            ((GuiImageButton)thisButton).setPosition(this.f_97735_ + 98, recipeButtonY);
            super.searchField.m_94214_(this.f_97735_ + 115);
            this.btnClr.setX(this.f_97735_ + 86);
            this.buttonSortingType.setX(this.f_97735_ - 18);
            this.buttonDirection.setX(this.f_97735_ - 18);
            this.buttonSearchType.setX(this.f_97735_ - 18);
            this.btnCollapse.setX(this.f_97735_ + 86);
            this.btnExpand.setX(this.f_97735_ + 86);
         })
      );
      if (this.recipeBookGui.m_100385_()) {
         this.buttonSortingType.setX(this.f_97735_ - 18);
         this.buttonDirection.setX(this.f_97735_ - 18);
         this.buttonSearchType.setX(this.f_97735_ - 18);
         super.searchField.m_94214_(this.f_97735_ + 115);
         this.recipeBookSearch = this.recipeBookGui.f_100281_;
         this.btnCollapse.setX(this.f_97735_ + 86);
         this.btnExpand.setX(this.f_97735_ + 86);
      }

      this.btnRecipeBook.f_93624_ = (Boolean)Config.SHOW_RECIPE_BOOK.get();
      this.onPacket();
   }

   @Override
   protected void onPacket() {
      super.onPacket();
      SortSettings s = ((CraftingTerminalMenu)this.f_97732_).terminalData;
      if (s != null) {
         this.btnCollapse.f_93624_ = this.expanded;
         this.btnExpand.f_93624_ = !this.expanded;
         this.btnClr.f_93624_ = !this.expanded;
         this.btnRecipeBook.f_93624_ = !this.expanded && (Boolean)Config.SHOW_RECIPE_BOOK.get();
      }
   }

   public void collapseScreen() {
      this.rowCount = 3;
      this.expanded = false;
      this.sendUpdate();
   }

   public void expandScreen() {
      this.rowCount = 7;
      this.expanded = true;
      if (this.recipeBookGui.m_100385_()) {
         this.btnRecipeBook.m_5691_();
      }

      this.sendUpdate();
   }

   @Override
   public void m_181908_() {
      super.m_181908_();
      this.recipeBookGui.m_100386_();
   }

   @Override
   public void m_6305_(PoseStack st, int mouseX, int mouseY, float partialTicks) {
      this.m_7333_(st);
      if (this.recipeBookGui.m_100385_() && this.widthTooNarrow) {
         this.m_7286_(st, partialTicks, mouseX, mouseY);
         this.recipeBookGui.m_6305_(st, mouseX, mouseY, partialTicks);
      } else {
         this.recipeBookGui.m_6305_(st, mouseX, mouseY, partialTicks);
         super.m_6305_(st, mouseX, mouseY, partialTicks);
         this.recipeBookGui.m_6545_(st, this.f_97735_, this.f_97736_, true, partialTicks);
      }

      this.m_7025_(st, mouseX, mouseY);
      this.recipeBookGui.m_100361_(st, this.f_97735_, this.f_97736_, mouseX, mouseY);
      this.m_94718_(this.recipeBookGui);
   }

   protected boolean m_6774_(int x, int y, int width, int height, double mouseX, double mouseY) {
      return (!this.widthTooNarrow || !this.recipeBookGui.m_100385_()) && super.m_6774_(x, y, width, height, mouseX, mouseY);
   }

   @Override
   public boolean m_6375_(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      return this.recipeBookGui.m_6375_(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)
         ? true
         : this.widthTooNarrow && this.recipeBookGui.m_100385_() || super.m_6375_(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
   }

   protected boolean m_7467_(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
      boolean flag = mouseX < (double)guiLeftIn
         || mouseY < (double)guiTopIn
         || mouseX >= (double)(guiLeftIn + this.f_97726_)
         || mouseY >= (double)(guiTopIn + this.f_97727_);
      return this.recipeBookGui.m_100297_(mouseX, mouseY, this.f_97735_, this.f_97736_, this.f_97726_, this.f_97727_, mouseButton) && flag;
   }

   protected void m_6597_(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.m_6597_(slotIn, slotId, mouseButton, type);
      this.recipeBookGui.m_6904_(slotIn);
   }

   public void m_6916_() {
      this.recipeBookGui.m_100387_();
   }

   public RecipeBookComponent m_5564_() {
      return this.recipeBookGui;
   }

   private void clearGrid() {
      this.f_96541_.f_91072_.m_105208_(((CraftingTerminalMenu)this.f_97732_).f_38840_, 0);
   }

   @Override
   public boolean m_7933_(int code, int p_231046_2_, int p_231046_3_) {
      if (code == 83 && this.f_97734_ != null) {
         ItemStack itemstack = null;

         for (int i = 0; i < this.ghostRecipe.m_100158_(); i++) {
            GhostIngredient ghostrecipe$ghostingredient = this.ghostRecipe.m_100141_(i);
            int j = ghostrecipe$ghostingredient.m_100169_();
            int k = ghostrecipe$ghostingredient.m_100170_();
            if (j == this.f_97734_.f_40220_ && k == this.f_97734_.f_40221_) {
               itemstack = ghostrecipe$ghostingredient.m_100171_();
            }
         }

         if (itemstack != null) {
            super.searchField.m_94144_(itemstack.m_41786_().getString());
            super.searchField.m_94178_(false);
            return true;
         }
      }

      return super.m_7933_(code, p_231046_2_, p_231046_3_);
   }
}
