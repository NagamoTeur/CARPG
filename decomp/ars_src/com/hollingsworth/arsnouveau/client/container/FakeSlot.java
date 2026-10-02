package com.hollingsworth.arsnouveau.client.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

class FakeSlot extends Slot {
   private static final Container DUMMY = new SimpleContainer(1);

   public FakeSlot() {
      super(DUMMY, 0, Integer.MIN_VALUE, Integer.MIN_VALUE);
   }

   public boolean m_150651_(Player p_150652_) {
      return false;
   }

   public void m_5852_(ItemStack p_40240_) {
   }

   public ItemStack m_6201_(int p_40227_) {
      return ItemStack.f_41583_;
   }
}
