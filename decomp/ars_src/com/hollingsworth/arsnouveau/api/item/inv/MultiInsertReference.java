package com.hollingsworth.arsnouveau.api.item.inv;

import java.util.List;
import net.minecraft.world.item.ItemStack;

public class MultiInsertReference extends MultiSlotReference<SlotReference> {
   private ItemStack remainder;

   public MultiInsertReference(ItemStack remainder, List<SlotReference> slots) {
      super(slots);
      this.remainder = remainder;
   }

   public ItemStack getRemainder() {
      return this.remainder;
   }
}
