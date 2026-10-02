package com.majruszsaccessories.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.config.Config;
import com.majruszsaccessories.items.AccessoryItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class AccessoryRecipe extends CustomRecipe {
   final AccessoryItem result;
   final List<AccessoryItem> ingredients;

   public static Supplier<RecipeSerializer<?>> create() {
      return AccessoryRecipe.Serializer::new;
   }

   public AccessoryRecipe(ResourceLocation id, AccessoryItem result, List<AccessoryItem> ingredients) {
      super(id);
      this.result = result;
      this.ingredients = ingredients;
   }

   public boolean matches(CraftingContainer container, Level level) {
      RecipeData data = RecipeData.build(container);
      return data.getCardsSize() == 0
         && data.getBoostersSize() == 0
         && data.getAccessoriesSize() == this.ingredients.size()
         && this.ingredients.stream().allMatch(data::hasAccessory);
   }

   public ItemStack assemble(CraftingContainer container) {
      RecipeData data = RecipeData.build(container);
      float average = data.getAverageBonus();
      float std = data.getStandardDeviation();
      float minBonus = (Float)Config.Efficiency.RANGE.clamp(average - std);
      float maxBonus = (Float)Config.Efficiency.RANGE.clamp(average + std);
      return AccessoryHolder.create(this.result).setBonus(Range.of(minBonus, maxBonus)).getItemStack();
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)MajruszsAccessories.ACCESSORY_RECIPE.get();
   }

   public static class Serializer implements RecipeSerializer<AccessoryRecipe> {
      public AccessoryRecipe fromJson(ResourceLocation id, JsonObject object) {
         AccessoryItem result = (AccessoryItem)GsonHelper.m_13909_(object, "result");
         List<AccessoryItem> ingredients = serializeIngredients(GsonHelper.m_13933_(object, "ingredients"));
         if (ingredients.isEmpty()) {
            throw new JsonParseException("No ingredients for accessory recipe");
         } else {
            return new AccessoryRecipe(id, result, ingredients);
         }
      }

      public AccessoryRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
         int size = buffer.m_130242_();
         List<AccessoryItem> ingredients = new ArrayList<>();

         for (int idx = 0; idx < size; idx++) {
            ingredients.add((AccessoryItem)buffer.m_130267_().m_41720_());
         }

         AccessoryItem result = (AccessoryItem)buffer.m_130267_().m_41720_();
         return new AccessoryRecipe(id, result, ingredients);
      }

      public void toNetwork(FriendlyByteBuf buffer, AccessoryRecipe recipe) {
         buffer.m_130130_(recipe.ingredients.size());
         recipe.ingredients.forEach(ingredient -> buffer.m_130055_(new ItemStack(ingredient)));
         buffer.m_130055_(new ItemStack(recipe.result));
      }

      private static List<AccessoryItem> serializeIngredients(JsonArray array) {
         List<AccessoryItem> ingredients = new ArrayList<>();

         for (int i = 0; i < array.size(); i++) {
            ingredients.add((AccessoryItem)GsonHelper.m_13874_(array.get(i), "item"));
         }

         return ingredients;
      }
   }
}
