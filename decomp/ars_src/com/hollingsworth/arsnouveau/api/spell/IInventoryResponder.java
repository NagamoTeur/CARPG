package com.hollingsworth.arsnouveau.api.spell;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

@Deprecated
public interface IInventoryResponder {
   @NotNull
   default List<IItemHandler> getInventory() {
      return new ArrayList<>();
   }

   @NotNull
   default ItemStack getItem(ItemStack stack) {
      return this.getItem((Predicate<ItemStack>)(i -> i.m_41656_(stack)));
   }

   @NotNull
   default ItemStack getItem(Predicate<ItemStack> predicate) {
      for (IItemHandler i : this.getInventory()) {
         for (int slots = 0; slots < i.getSlots(); slots++) {
            if (predicate.test(i.getStackInSlot(slots))) {
               return i.getStackInSlot(slots);
            }
         }
      }

      return ItemStack.f_41583_;
   }

   @NotNull
   default ItemStack extractItem(Predicate<ItemStack> predicate, int count) {
      for (IItemHandler i : this.getInventory()) {
         for (int slots = 0; slots < i.getSlots(); slots++) {
            if (predicate.test(i.getStackInSlot(slots))) {
               return i.extractItem(slots, count, false);
            }
         }
      }

      return ItemStack.f_41583_;
   }
}
