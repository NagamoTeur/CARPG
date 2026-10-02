package com.hollingsworth.arsnouveau.api.recipe;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ShapedHelper {
   int recipeWidth;
   int recipeHeight;
   NonNullList<Ingredient> recipeItems;
   public List<List<Ingredient>> possibleRecipes;

   public ShapedHelper(ShapedRecipe recipe) {
      this.recipeHeight = recipe.getRecipeHeight();
      this.recipeWidth = recipe.getRecipeWidth();
      this.recipeItems = recipe.m_7527_();
      this.possibleRecipes = this.getPossibleRecipes();
   }

   public List<List<Ingredient>> getPossibleRecipes() {
      List<List<Ingredient>> ingredients = new ArrayList<>();

      for (int i = 0; i <= 3 - this.recipeWidth; i++) {
         for (int j = 0; j <= 3 - this.recipeHeight; j++) {
            if (!this.checkMatch(i, j, true).isEmpty()) {
               ingredients.add(this.checkMatch(i, j, true));
            }

            if (!this.checkMatch(i, j, false).isEmpty()) {
               ingredients.add(this.checkMatch(i, j, false));
            }
         }
      }

      return ingredients;
   }

   private List<Ingredient> checkMatch(int width, int height, boolean p_77573_4_) {
      List<Ingredient> ingredientList = new ArrayList<>();

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            int k = i - width;
            int l = j - height;
            Ingredient ingredient = Ingredient.f_43901_;
            if (k >= 0 && l >= 0 && k < this.recipeWidth && l < this.recipeHeight) {
               if (p_77573_4_) {
                  ingredient = (Ingredient)this.recipeItems.get(this.recipeWidth - k - 1 + l * this.recipeWidth);
               } else {
                  ingredient = (Ingredient)this.recipeItems.get(k + l * this.recipeWidth);
               }
            }

            if (ingredient.m_43908_().length != 0) {
               ingredientList.add(ingredient);
            }
         }
      }

      return ingredientList;
   }
}
