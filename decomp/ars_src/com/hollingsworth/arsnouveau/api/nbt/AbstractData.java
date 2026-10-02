package com.hollingsworth.arsnouveau.api.nbt;

import net.minecraft.nbt.CompoundTag;

public abstract class AbstractData {
   private CompoundTag initTag;

   public AbstractData(CompoundTag tag) {
      this.initTag = tag;
   }

   public CompoundTag getInitTag() {
      return this.initTag;
   }

   public abstract void writeToNBT(CompoundTag var1);
}
