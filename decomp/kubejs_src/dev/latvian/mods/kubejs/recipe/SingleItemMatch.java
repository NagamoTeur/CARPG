package dev.latvian.mods.kubejs.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public record SingleItemMatch(ItemStack stack) implements ItemMatch {
   @Override
   public boolean contains(ItemStack item) {
      return this.stack.m_41720_() == item.m_41720_();
   }

   @Override
   public boolean contains(Ingredient in) {
      return in.test(this.stack);
   }

   @Override
   public boolean contains(ItemLike itemLike) {
      return this.stack.m_41720_() == itemLike.m_5456_();
   }

   @Override
   public String toString() {
      return this.stack.m_41720_().kjs$getId();
   }
}
