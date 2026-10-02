package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;

public class BookUpgradeRecipe extends ShapelessRecipe {
   private BookUpgradeRecipe(ResourceLocation id, String group, ItemStack recipeOutput, NonNullList<Ingredient> ingredients) {
      super(id, group, recipeOutput, ingredients);
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      ItemStack output = super.m_5874_(inv);
      if (!output.m_41619_()) {
         for (int i = 0; i < inv.m_6643_(); i++) {
            ItemStack ingredient = inv.m_8020_(i);
            if (!ingredient.m_41619_() && ingredient.m_41720_() instanceof SpellBook) {
               output.m_41751_(ingredient.m_41783_());
            }
         }
      }

      return output;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.BOOK_UPGRADE_RECIPE.get();
   }

   public static class Serializer implements RecipeSerializer<BookUpgradeRecipe> {
      public BookUpgradeRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
         String group = GsonHelper.m_13851_(json, "group", "");
         NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(json);
         ItemStack result = CraftingHelper.getItemStack(GsonHelper.m_13930_(json, "result"), true);
         return new BookUpgradeRecipe(recipeID, group, result, ingredients);
      }

      public BookUpgradeRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buffer) {
         String group = buffer.m_130136_(32767);
         int numIngredients = buffer.m_130242_();
         NonNullList<Ingredient> ingredients = NonNullList.m_122780_(numIngredients, Ingredient.f_43901_);

         for (int j = 0; j < ingredients.size(); j++) {
            ingredients.set(j, Ingredient.m_43940_(buffer));
         }

         ItemStack result = buffer.m_130267_();
         return new BookUpgradeRecipe(recipeID, group, result, ingredients);
      }

      public void toNetwork(FriendlyByteBuf buffer, BookUpgradeRecipe recipe) {
         buffer.m_130070_(recipe.m_6076_());
         buffer.m_130130_(recipe.m_7527_().size());

         for (Ingredient ingredient : recipe.m_7527_()) {
            ingredient.m_43923_(buffer);
         }

         buffer.m_130055_(recipe.m_8043_());
      }
   }
}
