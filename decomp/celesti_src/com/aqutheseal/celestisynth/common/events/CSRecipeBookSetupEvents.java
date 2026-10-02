package com.aqutheseal.celestisynth.common.events;

import com.aqutheseal.celestisynth.common.registry.CSItems;
import com.aqutheseal.celestisynth.common.registry.CSRecipeTypes;
import java.util.List;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;

public class CSRecipeBookSetupEvents {
   public static final RecipeBookType CELESTIAL_CRAFTING = RecipeBookType.create("celestial_crafting");

   public static void registerEvent(RegisterRecipeBookCategoriesEvent event) {
      RecipeBookCategories celestialCraftingSearch = RecipeBookCategories.create(
         "celestial_crafting", new ItemStack[]{new ItemStack((ItemLike)CSItems.CELESTIAL_CORE.get())}
      );
      RecipeBookCategories celestialWeapons = RecipeBookCategories.create(
         "celestial_weapons", new ItemStack[]{new ItemStack((ItemLike)CSItems.SUPERNAL_NETHERITE_INGOT.get())}
      );
      List<RecipeBookCategories> celestialList = List.of(celestialWeapons, celestialCraftingSearch);
      event.registerBookCategories(CELESTIAL_CRAFTING, celestialList);
      event.registerRecipeCategoryFinder((RecipeType)CSRecipeTypes.CELESTIAL_CRAFTING_TYPE.get(), recipe -> celestialWeapons);
      event.registerAggregateCategory(celestialCraftingSearch, celestialList);
   }

   public static void staticInit() {
   }
}
