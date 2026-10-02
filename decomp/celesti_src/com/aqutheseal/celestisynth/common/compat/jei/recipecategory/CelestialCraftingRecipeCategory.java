package com.aqutheseal.celestisynth.common.compat.jei.recipecategory;

import com.aqutheseal.celestisynth.common.compat.jei.CSCompatJEI;
import com.aqutheseal.celestisynth.common.recipe.celestialcrafting.CelestialCraftingRecipe;
import com.aqutheseal.celestisynth.common.registry.CSBlocks;
import java.util.List;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class CelestialCraftingRecipeCategory implements IRecipeCategory<CelestialCraftingRecipe> {
   public static final int WIDTH = 116;
   public static final int HEIGHT = 54;
   @NotNull
   private final Component localizedName = Component.m_237115_(((Block)CSBlocks.CELESTIAL_CRAFTING_TABLE.get()).m_7705_());
   @NotNull
   private final IDrawable background;
   @NotNull
   private final IDrawable icon;

   public CelestialCraftingRecipeCategory(@NotNull IGuiHelper guiHelper) {
      this.background = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 60, 116, 54);
      this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)CSBlocks.CELESTIAL_CRAFTING_TABLE.get()));
   }

   @NotNull
   public IDrawable getBackground() {
      return this.background;
   }

   @NotNull
   public IDrawable getIcon() {
      return this.icon;
   }

   @NotNull
   public RecipeType<CelestialCraftingRecipe> getRecipeType() {
      return CSCompatJEI.CELESTIAL_CRAFTING;
   }

   @NotNull
   public Component getTitle() {
      return this.localizedName;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, CelestialCraftingRecipe recipe, IFocusGroup focuses) {
      ItemStack resultItem = recipe.m_8043_();
      int width = this.getWidth();
      int height = this.getHeight();
      IRecipeSlotBuilder outputSlot = builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 19);
      outputSlot.addIngredients(VanillaTypes.ITEM_STACK, List.of(resultItem));

      for (int y = 0; y < 3; y++) {
         for (int x = 0; x < 3; x++) {
            IRecipeSlotBuilder inputSlots = builder.addSlot(RecipeIngredientRole.INPUT, x * 18 + 1, y * 18 + 1);
            inputSlots.addIngredients((Ingredient)recipe.m_7527_().get(y * 3 + x));
         }
      }
   }
}
