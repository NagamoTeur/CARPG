package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.CosmicHeart;
import com.aizistral.enigmaticlegacy.registries.EnigmaticRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class BlessedShapedRecipe extends ShapedRecipe {
   public BlessedShapedRecipe(
      ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn
   ) {
      super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
   }

   public RecipeSerializer<?> m_7707_() {
      return EnigmaticRecipes.BLESSED_SHAPED;
   }

   public boolean m_5818_(CraftingContainer inv, Level worldIn) {
      boolean isAllBlessed = true;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (checkedItemStack.m_41720_() instanceof CosmicHeart && !ItemNBTHelper.getBoolean(checkedItemStack, "isBelieverBlessed", false)) {
            isAllBlessed = false;
         }
      }

      return isAllBlessed && super.m_5818_(inv, worldIn);
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      return super.m_5874_(inv);
   }

   public static class Serializer implements RecipeSerializer<BlessedShapedRecipe> {
      private NonNullList<Ingredient> handleBlessed(NonNullList<Ingredient> ingredientList) {
         for (Ingredient ing : ingredientList) {
            for (ItemStack stack : ing.m_43908_()) {
               if (stack.m_41720_() instanceof CosmicHeart) {
                  ItemNBTHelper.setBoolean(stack, "isBelieverBlessed", true);
               }
            }
         }

         return ingredientList;
      }

      public BlessedShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         ShapedRecipe recipe = (ShapedRecipe)f_44076_.m_6729_(recipeId, json);
         return new BlessedShapedRecipe(
            recipe.m_6423_(), recipe.m_6076_(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), this.handleBlessed(recipe.m_7527_()), recipe.m_8043_()
         );
      }

      public BlessedShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         ShapedRecipe recipe = (ShapedRecipe)f_44076_.m_8005_(recipeId, buffer);
         return new BlessedShapedRecipe(
            recipe.m_6423_(), recipe.m_6076_(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), this.handleBlessed(recipe.m_7527_()), recipe.m_8043_()
         );
      }

      public void toNetwork(FriendlyByteBuf buffer, BlessedShapedRecipe recipe) {
         f_44076_.m_6178_(buffer, recipe);
      }
   }
}
