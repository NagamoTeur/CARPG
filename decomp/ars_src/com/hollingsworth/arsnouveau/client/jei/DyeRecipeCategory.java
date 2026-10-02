package com.hollingsworth.arsnouveau.client.jei;

import com.hollingsworth.arsnouveau.common.crafting.recipes.DyeRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.IDyeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class DyeRecipeCategory implements ICraftingCategoryExtension {
   private final DyeRecipe recipe;

   public DyeRecipeCategory(DyeRecipe recipe) {
      this.recipe = recipe;
   }

   public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
      List<List<ItemStack>> inputs = this.recipe.m_7527_().stream().map(ingredient -> List.of(ingredient.m_43908_())).toList();
      ItemStack resultItem = this.recipe.m_8043_();
      List<ItemStack> results = new ArrayList<>();
      if (resultItem.m_41720_() instanceof IDyeable toDye) {
         List<ItemStack> focus = focuses.getItemStackFocuses(RecipeIngredientRole.INPUT)
            .map(f -> (ItemStack)f.getTypedValue().getIngredient())
            .filter(f -> f.m_41720_() instanceof DyeItem)
            .toList();

         for (DyeColor color : focus.isEmpty()
            ? Arrays.stream(((Ingredient)this.recipe.m_7527_().get(0)).m_43908_()).map(DyeColor::getColor).toList()
            : focus.stream().map(DyeColor::getColor).toList()) {
            ItemStack copy = resultItem.m_41777_();
            toDye.onDye(copy, color);
            results.add(copy);
         }
      }

      craftingGridHelper.createAndSetOutputs(builder, results);
      craftingGridHelper.createAndSetInputs(builder, inputs, 0, 0);
   }
}
