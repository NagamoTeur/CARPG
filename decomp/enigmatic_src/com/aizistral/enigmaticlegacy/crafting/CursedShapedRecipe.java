package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.api.items.ITaintable;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
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

public class CursedShapedRecipe extends ShapedRecipe {
   public CursedShapedRecipe(
      ResourceLocation idIn, String groupIn, int recipeWidthIn, int recipeHeightIn, NonNullList<Ingredient> recipeItemsIn, ItemStack recipeOutputIn
   ) {
      super(idIn, groupIn, recipeWidthIn, recipeHeightIn, recipeItemsIn, recipeOutputIn);
   }

   public RecipeSerializer<?> m_7707_() {
      return EnigmaticRecipes.CURSED_SHAPED;
   }

   public boolean m_5818_(CraftingContainer inv, Level worldIn) {
      boolean isAllTainted = true;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack checkedItemStack = inv.m_8020_(i);
         if (checkedItemStack.m_41720_() instanceof ITaintable && !((ITaintable)checkedItemStack.m_41720_()).isTainted(checkedItemStack)) {
            isAllTainted = false;
         }
      }

      return super.m_5818_(inv, worldIn) && isAllTainted;
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      return super.m_5874_(inv);
   }

   public static class Serializer implements RecipeSerializer<CursedShapedRecipe> {
      private NonNullList<Ingredient> handleTainted(NonNullList<Ingredient> ingredientList) {
         for (Ingredient ing : ingredientList) {
            for (ItemStack stack : ing.m_43908_()) {
               if (stack.m_41720_() instanceof ITaintable) {
                  ItemNBTHelper.setBoolean(stack, "isTainted", true);
               }
            }
         }

         return ingredientList;
      }

      public CursedShapedRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         ShapedRecipe recipe = (ShapedRecipe)f_44076_.m_6729_(recipeId, json);
         return new CursedShapedRecipe(
            recipe.m_6423_(), recipe.m_6076_(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), this.handleTainted(recipe.m_7527_()), recipe.m_8043_()
         );
      }

      public CursedShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         ShapedRecipe recipe = (ShapedRecipe)f_44076_.m_8005_(recipeId, buffer);
         return new CursedShapedRecipe(
            recipe.m_6423_(), recipe.m_6076_(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), this.handleTainted(recipe.m_7527_()), recipe.m_8043_()
         );
      }

      public void toNetwork(FriendlyByteBuf buffer, CursedShapedRecipe recipe) {
         f_44076_.m_6178_(buffer, recipe);
      }
   }
}
