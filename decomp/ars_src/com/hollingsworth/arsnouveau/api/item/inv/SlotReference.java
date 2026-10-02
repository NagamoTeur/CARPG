package com.hollingsworth.arsnouveau.api.item.inv;

import javax.annotation.Nullable;
import net.minecraftforge.items.IItemHandler;

public class SlotReference {
   protected IItemHandler handler;
   protected int slot;

   public SlotReference(IItemHandler handler, int slot) {
      this.handler = handler;
      this.slot = slot;
   }

   public static SlotReference empty() {
      return new SlotReference(null, -1);
   }

   public boolean isEmpty() {
      return this.handler == null || this.slot < 0;
   }

   @Nullable
   public IItemHandler getHandler() {
      return this.handler;
   }

   public int getSlot() {
      return this.slot;
   }
}
