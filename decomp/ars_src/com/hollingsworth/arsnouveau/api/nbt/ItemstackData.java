package com.hollingsworth.arsnouveau.api.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public abstract class ItemstackData extends AbstractData {
   public ItemStack stack;

   public ItemstackData(ItemStack stack) {
      super(stack.m_41784_());
      this.stack = stack;
   }

   public void writeItem() {
      CompoundTag tag = new CompoundTag();
      this.writeToNBT(tag);
      this.stack.m_41784_().m_128365_(this.getTagString(), tag);
   }

   public CompoundTag getItemTag(ItemStack stack) {
      return stack.m_41784_().m_128469_(this.getTagString());
   }

   public abstract String getTagString();
}
