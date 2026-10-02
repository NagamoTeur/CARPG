package com.github.L_Ender.cataclysm.jei;

import com.github.L_Ender.cataclysm.crafting.AltarOfAmethystRecipe;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class AltarOfAmethystRecipeCategory implements IRecipeCategory<AltarOfAmethystRecipe> {
   private final IDrawable background = new AltarOfAmethystDrawable();
   private final IDrawable icon;

   public AltarOfAmethystRecipeCategory(IGuiHelper guiHelper) {
      this.icon = guiHelper.createDrawableItemStack(new ItemStack((ItemLike)ModBlocks.ALTAR_OF_AMETHYST.get()));
   }

   public RecipeType<AltarOfAmethystRecipe> getRecipeType() {
      return LEnderCataclysmJEIPlugin.ALTAR_OF_AMETHYST_RECIPE_RECIPE_TYPE;
   }

   public Component getTitle() {
      return ((Block)ModBlocks.ALTAR_OF_AMETHYST.get())
         .m_49954_()
         .m_7220_(Component.m_237113_(" "))
         .m_7220_(Component.m_237115_("cataclysm.gui.altar_of_amethyst_blessing"));
   }

   public IDrawable getBackground() {
      return this.background;
   }

   public IDrawable getIcon() {
      return this.icon;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, AltarOfAmethystRecipe recipe, IFocusGroup focuses) {
      builder.addSlot(RecipeIngredientRole.INPUT, 21, 23).addIngredients(recipe.getbaseIngredient());
      builder.addSlot(RecipeIngredientRole.OUTPUT, 94, 23).addItemStack(recipe.getResult());
   }

   public boolean isHandled(AltarOfAmethystRecipe recipe) {
      return true;
   }
}
