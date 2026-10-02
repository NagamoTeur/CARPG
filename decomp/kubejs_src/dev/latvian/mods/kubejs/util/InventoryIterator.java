package dev.latvian.mods.kubejs.util;

import dev.latvian.mods.kubejs.core.InventoryKJS;
import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.world.item.ItemStack;

public class InventoryIterator implements Iterator<ItemStack> {
   private final InventoryKJS inventory;
   private int cursor;

   public InventoryIterator(InventoryKJS inventory) {
      this.inventory = inventory;
   }

   @Override
   public boolean hasNext() {
      return this.cursor < this.inventory.kjs$getSlots();
   }

   public ItemStack next() {
      if (this.cursor >= this.inventory.kjs$getSlots()) {
         throw new NoSuchElementException();
      } else {
         return this.inventory.kjs$getStackInSlot(this.cursor++);
      }
   }
}
