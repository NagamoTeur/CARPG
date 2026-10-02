package com.hollingsworth.arsnouveau.common.items.itemscrolls;

import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.items.IItemHandler;

public class DenyItemScroll extends ItemScroll {
   public DenyItemScroll() {
   }

   public DenyItemScroll(Properties properties) {
      super(properties);
   }

   @Override
   public ItemScroll.SortPref getSortPref(ItemStack stackToStore, ItemStack scrollStack, IItemHandler inventory) {
      ItemScroll.ItemScrollData data = new ItemScroll.ItemScrollData(scrollStack);
      return !data.containsStack(stackToStore) ? ItemScroll.SortPref.HIGH : ItemScroll.SortPref.INVALID;
   }
}
