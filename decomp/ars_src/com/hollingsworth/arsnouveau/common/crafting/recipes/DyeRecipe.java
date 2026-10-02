package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.common.crafting.CraftingHelper;

public class DyeRecipe extends ShapelessRecipe {
   public DyeRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
      super(idIn, groupIn, recipeOutputIn, recipeItemsIn);
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      ItemStack output = super.m_5874_(inv);
      if (!output.m_41619_()) {
         for (int i = 0; i < inv.m_6643_(); i++) {
            ItemStack ingredient = inv.m_8020_(i);
            if (!ingredient.m_41619_() && ingredient.m_41720_() instanceof IDyeable) {
               output.m_41751_(ingredient.m_41784_().m_6426_());
            }
         }

         for (int ix = 0; ix < inv.m_6643_(); ix++) {
            ItemStack ingredient = inv.m_8020_(ix);
            DyeColor color = DyeColor.getColor(ingredient);
            if (!ingredient.m_41619_() && color != null && output.m_41720_() instanceof IDyeable dyeable) {
               dyeable.onDye(output, color);
            }
         }
      }

      return output;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.DYE_RECIPE.get();
   }

   public static JsonElement asRecipe(Item item) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", "ars_nouveau:dye");
      JsonArray ingredients = new JsonArray();
      JsonObject dyeObject = new JsonObject();
      dyeObject.addProperty("tag", Items.DYES.f_203868_().toString());
      ingredients.add(dyeObject);
      JsonObject input = new JsonObject();
      input.addProperty("item", RegistryHelper.getRegistryName(item).toString());
      ingredients.add(input);
      jsonobject.add("ingredients", ingredients);
      JsonObject itemObject = new JsonObject();
      itemObject.addProperty("item", RegistryHelper.getRegistryName(item).toString());
      jsonobject.add("result", itemObject);
      return jsonobject;
   }

   public static class Serializer implements RecipeSerializer<DyeRecipe> {
      public DyeRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
         String group = GsonHelper.m_13851_(json, "group", "");
         NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(json);
         ItemStack result = CraftingHelper.getItemStack(GsonHelper.m_13930_(json, "result"), true);
         return new DyeRecipe(recipeID, group, result, ingredients);
      }

      public DyeRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buffer) {
         String group = buffer.m_130136_(32767);
         int numIngredients = buffer.m_130242_();
         NonNullList<Ingredient> ingredients = NonNullList.m_122780_(numIngredients, Ingredient.f_43901_);

         for (int j = 0; j < ingredients.size(); j++) {
            ingredients.set(j, Ingredient.m_43940_(buffer));
         }

         ItemStack result = buffer.m_130267_();
         return new DyeRecipe(recipeID, group, result, ingredients);
      }

      public void toNetwork(FriendlyByteBuf buffer, DyeRecipe recipe) {
         buffer.m_130070_(recipe.m_6076_());
         buffer.m_130130_(recipe.m_7527_().size());

         for (Ingredient ingredient : recipe.m_7527_()) {
            ingredient.m_43923_(buffer);
         }

         buffer.m_130055_(recipe.m_8043_());
      }
   }
}
