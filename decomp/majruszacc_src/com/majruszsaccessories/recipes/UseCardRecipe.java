package com.majruszsaccessories.recipes;

import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

public class UseCardRecipe extends CustomRecipe {
   public static Supplier<RecipeSerializer<?>> create() {
      return () -> new SimpleRecipeSerializer(UseCardRecipe::new);
   }

   public UseCardRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(CraftingContainer container, Level level) {
      RecipeData data = RecipeData.build(container);
      return data.getAccessoriesSize() == 1 && data.getBoostersSize() == 0 && data.getCardsSize() == 1;
   }

   public ItemStack assemble(CraftingContainer container) {
      RecipeData data = RecipeData.build(container);
      AccessoryHolder holder = data.getAccessory(0).copy();
      data.getCard(0).apply(holder);
      return holder.getItemStack();
   }

   public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
      RecipeData data = RecipeData.build(container);
      NonNullList<ItemStack> remainingItems = NonNullList.m_122780_(container.m_6643_(), ItemStack.f_41583_);
      List<ItemStack> itemStacks = data.getCard(0).getCraftingRemainder(data.getAccessory(0));

      for (int idx = 0; idx < itemStacks.size(); idx++) {
         remainingItems.set(idx, itemStacks.get(idx));
      }

      return remainingItems;
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)MajruszsAccessories.USE_CARD_RECIPE.get();
   }
}
