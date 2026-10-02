package com.hollingsworth.arsnouveau.common.crafting.recipes;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public interface IDyeable {
   default void onDye(ItemStack stack, DyeColor dyeColor) {
      stack.m_41784_().m_128405_("color", dyeColor.m_41060_());
   }

   default int getDyeColor(ItemStack stack) {
      return !stack.m_41782_() ? -1 : stack.m_41783_().m_128451_("color");
   }
}
