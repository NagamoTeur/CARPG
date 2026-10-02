package com.hollingsworth.arsnouveau.api.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class SingleRecipe {
   public List<Ingredient> recipeIngredients;
   public ItemStack outputStack;
   public Recipe iRecipe;

   public SingleRecipe(List<Ingredient> recipeIngredients, ItemStack outputStack, Recipe iRecipe) {
      this.recipeIngredients = recipeIngredients;
      this.outputStack = outputStack;
      this.iRecipe = iRecipe;
   }

   @Deprecated
   public List<ItemStack> canCraftFromInventory(Map<Item, Integer> inventory) {
      Map<Item, Integer> map = new HashMap<>(inventory);
      List<ItemStack> items = new ArrayList<>();

      for (Ingredient i : this.recipeIngredients) {
         boolean foundStack = false;

         for (ItemStack stack : i.m_43908_()) {
            if (inventory.containsKey(stack.m_41720_()) && map.get(stack.m_41720_()) > 0) {
               map.put(stack.m_41720_(), map.get(stack.m_41720_()) - 1);
               foundStack = true;
               items.add(stack.m_41777_());
               break;
            }
         }

         if (!foundStack) {
            return null;
         }
      }

      return items;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         SingleRecipe recipe1 = (SingleRecipe)o;
         return Objects.equals(this.recipeIngredients, recipe1.recipeIngredients) && Objects.equals(this.outputStack, recipe1.outputStack);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.recipeIngredients, this.outputStack);
   }
}
