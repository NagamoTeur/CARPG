package com.aqutheseal.celestisynth.common.recipe.celestialcrafting;

import com.aqutheseal.celestisynth.common.registry.CSRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class CelestialShapedRecipe extends ShapedRecipe implements CelestialCraftingRecipe {
   public CelestialShapedRecipe(ResourceLocation pId, String pGroup, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult) {
      super(pId, pGroup, pWidth, pHeight, pRecipeItems, pResult);
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)CSRecipeTypes.SHAPED_CELESTIAL_CRAFTING.get();
   }

   public RecipeType<CelestialCraftingRecipe> m_6671_() {
      return (RecipeType<CelestialCraftingRecipe>)CSRecipeTypes.CELESTIAL_CRAFTING_TYPE.get();
   }

   public static class Serializer implements RecipeSerializer<CelestialShapedRecipe> {
      public CelestialShapedRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
         ShapedRecipe fromJson = (ShapedRecipe)f_44076_.m_6729_(pRecipeId, pJson);
         return new CelestialShapedRecipe(pRecipeId, fromJson.m_6076_(), fromJson.m_44220_(), fromJson.m_44221_(), fromJson.m_7527_(), fromJson.m_8043_());
      }

      public CelestialShapedRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
         ShapedRecipe fromNetwork = (ShapedRecipe)f_44076_.m_8005_(pRecipeId, pBuffer);
         return new CelestialShapedRecipe(
            pRecipeId, fromNetwork.m_6076_(), fromNetwork.m_44220_(), fromNetwork.m_44221_(), fromNetwork.m_7527_(), fromNetwork.m_8043_()
         );
      }

      public void toNetwork(FriendlyByteBuf pBuffer, CelestialShapedRecipe pRecipe) {
         f_44076_.m_6178_(pBuffer, pRecipe);
      }
   }
}
