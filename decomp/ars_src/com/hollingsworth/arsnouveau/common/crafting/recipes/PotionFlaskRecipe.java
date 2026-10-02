package com.hollingsworth.arsnouveau.common.crafting.recipes;

import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.common.items.PotionFlask;
import com.hollingsworth.arsnouveau.setup.RecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;

public class PotionFlaskRecipe extends ShapelessRecipe {
   public PotionFlaskRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
      super(idIn, groupIn, recipeOutputIn, recipeItemsIn);
   }

   public ItemStack m_5874_(CraftingContainer inv) {
      ItemStack output = super.m_5874_(inv);
      if (output.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         ItemStack flaskPotionStack = ItemStack.f_41583_;
         ItemStack potionStack = ItemStack.f_41583_;

         for (int i = 0; i < inv.m_6643_(); i++) {
            ItemStack stack = inv.m_8020_(i);
            if (stack.m_41720_() instanceof PotionFlask flask) {
               flaskPotionStack = stack.m_41777_();
               if (flask.isMax(stack)) {
                  return ItemStack.f_41583_;
               }
            }

            if (stack.m_41720_() instanceof PotionItem) {
               potionStack = stack;
            }
         }

         if (!flaskPotionStack.m_41619_() && !potionStack.m_41619_()) {
            PotionFlask.FlaskData flaskData = new PotionFlask.FlaskData(flaskPotionStack);
            PotionData potionData = new PotionData(potionStack);
            if (flaskData.getCount() <= 0) {
               flaskData.setPotion(potionData);
               flaskData.setCount(1);
               return flaskPotionStack.m_41777_();
            } else if (flaskData.getPotion().areSameEffects(potionData)) {
               flaskData.setCount(flaskData.getCount() + 1);
               return flaskPotionStack.m_41777_();
            } else {
               return ItemStack.f_41583_;
            }
         } else {
            return ItemStack.f_41583_;
         }
      }
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122780_(inv.m_6643_(), ItemStack.f_41583_);

      for (int i = 0; i < nonnulllist.size(); i++) {
         ItemStack item = inv.m_8020_(i);
         if (item.hasCraftingRemainingItem()) {
            nonnulllist.set(i, item.getCraftingRemainingItem());
         } else if (item.m_41720_() instanceof PotionItem) {
            nonnulllist.set(i, new ItemStack(Items.f_42590_));
         }
      }

      return nonnulllist;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)RecipeRegistry.POTION_FLASK_RECIPE.get();
   }

   public static class Serializer implements RecipeSerializer<PotionFlaskRecipe> {
      public PotionFlaskRecipe fromJson(ResourceLocation recipeID, JsonObject json) {
         String group = GsonHelper.m_13851_(json, "group", "");
         NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(json);
         ItemStack result = CraftingHelper.getItemStack(GsonHelper.m_13930_(json, "result"), true);
         return new PotionFlaskRecipe(recipeID, group, result, ingredients);
      }

      public PotionFlaskRecipe fromNetwork(ResourceLocation recipeID, FriendlyByteBuf buffer) {
         String group = buffer.m_130136_(32767);
         int numIngredients = buffer.m_130242_();
         NonNullList<Ingredient> ingredients = NonNullList.m_122780_(numIngredients, Ingredient.f_43901_);

         for (int j = 0; j < ingredients.size(); j++) {
            ingredients.set(j, Ingredient.m_43940_(buffer));
         }

         ItemStack result = buffer.m_130267_();
         return new PotionFlaskRecipe(recipeID, group, result, ingredients);
      }

      public void toNetwork(FriendlyByteBuf buffer, PotionFlaskRecipe recipe) {
         buffer.m_130070_(recipe.m_6076_());
         buffer.m_130130_(recipe.m_7527_().size());

         for (Ingredient ingredient : recipe.m_7527_()) {
            ingredient.m_43923_(buffer);
         }

         buffer.m_130055_(recipe.m_8043_());
      }
   }
}
