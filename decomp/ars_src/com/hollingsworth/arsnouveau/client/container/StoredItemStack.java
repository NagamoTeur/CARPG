package com.hollingsworth.arsnouveau.client.container;

import java.util.Comparator;
import java.util.function.Function;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class StoredItemStack {
   private ItemStack stack;
   private long count;
   private static final String ITEM_COUNT_NAME = "c";
   private static final String ITEMSTACK_NAME = "s";
   private int hash;

   public StoredItemStack(ItemStack stack, long count) {
      this.stack = stack;
      this.count = count;
   }

   public StoredItemStack(ItemStack stack) {
      this.stack = stack.m_41777_();
      this.stack.m_41764_(1);
      this.count = (long)stack.m_41613_();
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public long getQuantity() {
      return this.count;
   }

   public ItemStack getActualStack() {
      ItemStack s = this.stack.m_41777_();
      s.m_41764_((int)this.count);
      return s;
   }

   public CompoundTag writeToNBT(CompoundTag tag) {
      tag.m_128356_("c", this.getQuantity());
      tag.m_128365_("s", this.stack.m_41739_(new CompoundTag()));
      tag.m_128469_("s").m_128473_("Count");
      return tag;
   }

   public CompoundTag writeToNBT(CompoundTag tag, long q) {
      tag.m_128356_("c", q);
      tag.m_128365_("s", this.stack.m_41739_(new CompoundTag()));
      tag.m_128469_("s").m_128473_("Count");
      return tag;
   }

   public static StoredItemStack readFromNBT(CompoundTag tag) {
      ItemStack cheat = ItemStack.m_41712_(tag);
      tag.m_128469_("s").m_128344_("Count", (byte)1);
      StoredItemStack stack = new StoredItemStack(
         !cheat.m_41619_() ? cheat : ItemStack.m_41712_(tag.m_128469_("s")), !cheat.m_41619_() ? (long)cheat.m_41613_() : tag.m_128454_("c")
      );
      return !stack.stack.m_41619_() ? stack : null;
   }

   @Override
   public int hashCode() {
      if (this.hash == 0) {
         int prime = 31;
         int result = 1;
         result = 31 * result + (this.stack == null ? 0 : this.stack.m_41720_().hashCode());
         result = 31 * result + (this.stack != null && this.stack.m_41782_() ? this.stack.m_41783_().hashCode() : 0);
         this.hash = result;
         return result;
      } else {
         return this.hash;
      }
   }

   public String getDisplayName() {
      return this.stack.m_41786_().getString();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         StoredItemStack other = (StoredItemStack)obj;
         return this.stack == null ? other.stack == null : ItemStack.m_41746_(this.stack, other.stack) && ItemStack.m_41658_(this.stack, other.stack);
      }
   }

   public boolean equals(StoredItemStack other) {
      if (this == other) {
         return true;
      } else if (other == null) {
         return false;
      } else if (this.count != other.count) {
         return false;
      } else {
         return this.stack == null ? other.stack == null : ItemStack.m_41746_(this.stack, other.stack) && ItemStack.m_41658_(this.stack, other.stack);
      }
   }

   public void grow(long c) {
      this.count += c;
   }

   public void setCount(long count) {
      this.count = count;
   }

   public int getMaxStackSize() {
      return this.stack.m_41741_();
   }

   public static class ComparatorAmount implements StoredItemStack.IStoredItemStackComparator {
      public boolean reversed;

      public ComparatorAmount(boolean reversed) {
         this.reversed = reversed;
      }

      public int compare(StoredItemStack in1, StoredItemStack in2) {
         int c = in2.getQuantity() > in1.getQuantity()
            ? 1
            : (in1.getQuantity() == in2.getQuantity() ? in1.getStack().m_41786_().getString().compareTo(in2.getStack().m_41786_().getString()) : -1);
         return this.reversed ? -c : c;
      }

      @Override
      public boolean isReversed() {
         return this.reversed;
      }

      @Override
      public int type() {
         return 0;
      }

      @Override
      public void setReversed(boolean rev) {
         this.reversed = rev;
      }
   }

   public static class ComparatorName implements StoredItemStack.IStoredItemStackComparator {
      public boolean reversed;

      public ComparatorName(boolean reversed) {
         this.reversed = reversed;
      }

      public int compare(StoredItemStack in1, StoredItemStack in2) {
         int c = in1.getDisplayName().compareTo(in2.getDisplayName());
         return this.reversed ? -c : c;
      }

      @Override
      public boolean isReversed() {
         return this.reversed;
      }

      @Override
      public int type() {
         return 1;
      }

      @Override
      public void setReversed(boolean rev) {
         this.reversed = rev;
      }
   }

   public interface IStoredItemStackComparator extends Comparator<StoredItemStack> {
      boolean isReversed();

      void setReversed(boolean var1);

      int type();
   }

   public static enum SortingTypes {
      AMOUNT(StoredItemStack.ComparatorAmount::new),
      NAME(StoredItemStack.ComparatorName::new);

      public static final StoredItemStack.SortingTypes[] VALUES = values();
      private final Function<Boolean, StoredItemStack.IStoredItemStackComparator> factory;

      private SortingTypes(Function<Boolean, StoredItemStack.IStoredItemStackComparator> factory) {
         this.factory = factory;
      }

      public StoredItemStack.IStoredItemStackComparator create(boolean rev) {
         return this.factory.apply(rev);
      }
   }
}
