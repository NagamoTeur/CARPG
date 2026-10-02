package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.registries.EnigmaticRecipes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessNoReturnRecipe extends ShapelessRecipe {
   private final String group;
   private final ItemStack recipeOutput;
   private final NonNullList<Ingredient> recipeItems;

   public ShapelessNoReturnRecipe(ResourceLocation id, String group, ItemStack output, NonNullList<Ingredient> inputs) {
      super(id, group, output, inputs);
      this.group = group;
      this.recipeOutput = output;
      this.recipeItems = inputs;
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
      return NonNullList.m_122780_(inv.m_6643_(), ItemStack.f_41583_);
   }

   public RecipeSerializer<?> m_7707_() {
      return EnigmaticRecipes.SHAPELESS_NO_RETURN;
   }

   public static class Serialize implements RecipeSerializer<ShapelessNoReturnRecipe> {
      public ShapelessNoReturnRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         ShapelessRecipe recipe = (ShapelessRecipe)f_44077_.m_6729_(recipeId, json);
         return new ShapelessNoReturnRecipe(recipe.m_6423_(), recipe.m_6076_(), recipe.m_8043_(), recipe.m_7527_());
      }

      public ShapelessNoReturnRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         ShapelessRecipe recipe = (ShapelessRecipe)f_44077_.m_8005_(recipeId, buffer);
         return new ShapelessNoReturnRecipe(recipe.m_6423_(), recipe.m_6076_(), recipe.m_8043_(), recipe.m_7527_());
      }

      public void toNetwork(FriendlyByteBuf buffer, ShapelessNoReturnRecipe recipe) {
         f_44077_.m_6178_(buffer, recipe);
      }
   }
}
