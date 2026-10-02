package com.majruszsaccessories.recipes;

import com.majruszlibrary.events.base.Events;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.events.OnBoosterCompatibilityGet;
import com.majruszsaccessories.items.BoosterItem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

public class BoostAccessoryRecipe extends CustomRecipe {
   public static Supplier<RecipeSerializer<?>> create() {
      return () -> new SimpleRecipeSerializer(BoostAccessoryRecipe::new);
   }

   public BoostAccessoryRecipe(ResourceLocation id) {
      super(id);
   }

   public boolean matches(CraftingContainer container, Level level) {
      RecipeData data = RecipeData.build(container);
      if (data.getAccessoriesSize() != 1) {
         return false;
      } else {
         AccessoryHolder holder = data.getAccessory(0);
         return data.getCardsSize() == 0
            && data.getBoostersSize() > 0
            && data.getBoostersSize() <= holder.getBoosterSlotsLeft()
            && areCompatible(data.boosters(), holder.getBoosters());
      }
   }

   public ItemStack assemble(CraftingContainer container) {
      RecipeData data = RecipeData.build(container);
      AccessoryHolder holder = data.getAccessory(0).copy();
      data.boosters().forEach(holder::addBooster);
      return holder.getItemStack();
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer<?>)MajruszsAccessories.BOOST_ACCESSORY_RECIPE.get();
   }

   private static boolean areCompatible(List<BoosterItem> a, List<BoosterItem> b) {
      List<BoosterItem> items = new ArrayList<>(a);
      items.addAll(b);

      for (int i = 0; i < items.size(); i++) {
         for (int j = i + 1; j < items.size(); j++) {
            if (((OnBoosterCompatibilityGet)Events.dispatch(new OnBoosterCompatibilityGet(items.get(i), items.get(j)))).areIncompatible()) {
               return false;
            }
         }
      }

      return true;
   }
}
