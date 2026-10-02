package com.aqutheseal.celestisynth.common.recipe.celestialcrafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

public interface CelestialCraftingRecipe extends Recipe<CraftingContainer> {
   @NotNull
   default ItemStack assemble(@NotNull CraftingContainer container) {
      return this.m_8043_().m_41777_();
   }

   @NotNull
   default NonNullList<Ingredient> m_7527_() {
      return NonNullList.m_122779_();
   }
}
