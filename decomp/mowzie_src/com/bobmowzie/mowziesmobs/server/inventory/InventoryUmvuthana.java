package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class InventoryUmvuthana implements Container {
   private final EntityUmvuthanaMinion umvuthana;
   private final List<ItemStack> slots = NonNullList.m_122780_(2, ItemStack.f_41583_);
   private Trade trade;

   public InventoryUmvuthana(EntityUmvuthanaMinion umvuthana) {
      this.umvuthana = umvuthana;
   }

   public int m_6643_() {
      return this.slots.size();
   }

   public ItemStack m_8020_(int index) {
      return this.slots.get(index);
   }

   public ItemStack m_7407_(int index, int count) {
      if (index == 1 && this.slots.get(index) != ItemStack.f_41583_) {
         return ContainerHelper.m_18969_(this.slots, index, this.slots.get(index).m_41613_());
      } else {
         ItemStack stack = ContainerHelper.m_18969_(this.slots, index, count);
         if (stack != ItemStack.f_41583_ && this.doUpdateForSlotChange(index)) {
            this.reset();
         }

         return stack;
      }
   }

   public ItemStack m_8016_(int index) {
      return ContainerHelper.m_18966_(this.slots, index);
   }

   public void m_6836_(int index, ItemStack stack) {
      this.slots.set(index, stack);
      if (stack != ItemStack.f_41583_ && stack.m_41613_() > this.m_6893_()) {
         stack.m_41764_(this.m_6893_());
      }

      if (this.doUpdateForSlotChange(index)) {
         this.reset();
      }
   }

   private boolean doUpdateForSlotChange(int slot) {
      return slot == 0;
   }

   public int m_6893_() {
      return 64;
   }

   public void m_6596_() {
      this.reset();
   }

   public boolean m_6542_(Player player) {
      return this.umvuthana.getCustomer() == player;
   }

   public void m_5856_(Player player) {
   }

   public void m_5785_(Player player) {
   }

   public boolean m_7013_(int index, ItemStack stack) {
      return true;
   }

   public void m_6211_() {
      this.slots.clear();
   }

   public void reset() {
      this.trade = null;
      ItemStack input = this.slots.get(0);
      if (input == ItemStack.f_41583_) {
         this.m_6836_(1, ItemStack.f_41583_);
      } else if (this.umvuthana.isOfferingTrade()) {
         Trade trade = this.umvuthana.getOfferingTrade();
         ItemStack tradeInput = trade.getInput();
         if (areItemsEqual(input, tradeInput) && input.m_41613_() >= tradeInput.m_41613_()) {
            this.trade = trade;
            this.m_6836_(1, trade.getOutput());
         } else {
            this.m_6836_(1, ItemStack.f_41583_);
         }
      }
   }

   public boolean m_7983_() {
      for (ItemStack stack : this.slots) {
         if (!stack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   private static boolean areItemsEqual(ItemStack s1, ItemStack s2) {
      return ItemStack.m_41746_(s1, s2) && (!s2.m_41782_() || s1.m_41782_() && NbtUtils.m_129235_(s2.m_41783_(), s1.m_41783_(), false));
   }
}
