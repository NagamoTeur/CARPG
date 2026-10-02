package com.hollingsworth.arsnouveau.client.container;

import com.hollingsworth.arsnouveau.common.block.tile.StorageLecternTile;

public class SlotStorage {
   public int xDisplayPosition;
   public int yDisplayPosition;
   private final int slotIndex;
   public final StorageLecternTile inventory;
   public StoredItemStack stack;

   public SlotStorage(StorageLecternTile inventory, int slotIndex, int xPosition, int yPosition) {
      this.xDisplayPosition = xPosition;
      this.yDisplayPosition = yPosition;
      this.slotIndex = slotIndex;
      this.inventory = inventory;
   }
}
