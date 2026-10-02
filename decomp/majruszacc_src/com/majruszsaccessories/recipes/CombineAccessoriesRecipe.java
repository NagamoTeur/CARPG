package com.majruszsaccessories.recipes;

import com.majruszlibrary.math.Range;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.config.Config;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

public class CombineAccessoriesRecipe extends CustomRecipe {
   public static Supplier<RecipeSerializer<?>> create() {
      return () -> new SimpleRecipeSerializer(CombineAccessoriesRecipe::new);
   }

   public CombineAccessoriesRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(CraftingContainer container, Level level) {
      RecipeData data = RecipeData.build(container);
      return data.getAccessoriesSize() > 1
         && data.hasIdenticalItemTypes()
         && data.getMaxBonus() < (Float)Config.Efficiency.RANGE.to
         && data.getBoostersSize() == 0
         && data.getCardsSize() == 0;
   }

   public ItemStack assemble(CraftingContainer container) {
      RecipeData data = RecipeData.build(container);
      float craftingMaxBonus = data.getMaxBonus();
      float minBonus = craftingMaxBonus - 0.02F * (float)(data.getAccessoriesSize() - 1);
      float maxBonus = craftingMaxBonus + 0.07F * (float)(data.getAccessoriesSize() - 1);
      return AccessoryHolder.create(data.getAccessory(0).getItem()).setBonus(Range.of(minBonus, maxBonus)).getItemStack();
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)MajruszsAccessories.COMBINE_ACCESSORIES_RECIPE.get();
   }
}
