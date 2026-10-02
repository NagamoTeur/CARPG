package dev.latvian.mods.kubejs.item;

import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class ItemStackKey {
   public static ItemStackKey EMPTY = new ItemStackKey(Items.f_41852_, null);
   private final Item item;
   private final CompoundTag tag;
   private int hashCode = 0;

   public static ItemStackKey of(ItemStack stack) {
      if (stack.m_41619_()) {
         return EMPTY;
      } else {
         return stack.m_41783_() == null ? stack.m_41720_().kjs$getTypeItemStackKey() : new ItemStackKey(stack);
      }
   }

   public ItemStackKey(Item item, @Nullable CompoundTag tag) {
      this.item = item;
      this.tag = tag;
   }

   private ItemStackKey(ItemStack is) {
      this(is.m_41720_(), is.m_41783_());
   }

   @Override
   public int hashCode() {
      if (this.hashCode == 0) {
         this.hashCode = this.item == Items.f_41852_ ? 0 : (this.tag == null ? this.item.hashCode() : this.item.hashCode() * 31 + this.tag.hashCode());
         if (this.hashCode == 0) {
            this.hashCode = 1;
         }
      }

      return this.hashCode;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof ItemStackKey k) ? false : this.item == k.item && this.hashCode() == k.hashCode() && Objects.equals(this.tag, k.tag);
   }
}
