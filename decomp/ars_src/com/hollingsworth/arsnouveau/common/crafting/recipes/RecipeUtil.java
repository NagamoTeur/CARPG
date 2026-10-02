package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.core.NonNullList;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;

public class RecipeUtil {
   public static NonNullList<Ingredient> parseShapeless(JsonObject json) {
      NonNullList<Ingredient> ingredients = NonNullList.m_122779_();

      for (JsonElement element : GsonHelper.m_13933_(json, "ingredients")) {
         ingredients.add(CraftingHelper.getIngredient(element));
      }

      if (ingredients.isEmpty()) {
         throw new JsonParseException("No ingredients for shapeless recipe");
      } else {
         return ingredients;
      }
   }
}
