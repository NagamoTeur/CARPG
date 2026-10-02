package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public abstract class ContainerTradeBase extends AbstractContainerMenu {
   protected final MowzieEntity tradingMob;
   protected final Container inventory;
   protected final Player player;
   private int numCustomSlots;

   public ContainerTradeBase(MenuType<?> menuType, int id, MowzieEntity tradingMob, Container inventory, Inventory playerInv) {
      super(menuType, id);
      this.tradingMob = tradingMob;
      this.inventory = inventory;
      this.player = playerInv.f_35978_;
      this.addCustomSlots(playerInv);
      this.numCustomSlots = this.f_38839_.size();

      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 9; col++) {
            this.m_38897_(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
         }
      }

      for (int col = 0; col < 9; col++) {
         this.m_38897_(new Slot(playerInv, col, 8 + col * 18, 142));
      }
   }

   protected void addCustomSlots(Inventory playerInv) {
   }

   public boolean m_6875_(Player player) {
      return this.tradingMob != null && this.inventory.m_6542_(player) && this.tradingMob.m_6084_() && this.tradingMob.m_20270_(player) < 8.0F;
   }

   public ItemStack m_7648_(Player player, int index) {
      ItemStack stack = ItemStack.f_41583_;
      int playerHotbarStart = this.numCustomSlots + 27;
      int playerInventoryEnd = this.numCustomSlots + 36;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack contained = slot.m_7993_();
         stack = contained.m_41777_();
         if (index == 1) {
            if (!this.m_38903_(contained, this.numCustomSlots, playerInventoryEnd, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(contained, stack);
         } else if (index != 0) {
            if (index >= this.numCustomSlots && index < playerHotbarStart) {
               if (!this.m_38903_(contained, playerHotbarStart, playerInventoryEnd, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (index >= playerHotbarStart && index < playerInventoryEnd && !this.m_38903_(contained, this.numCustomSlots, playerHotbarStart, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(contained, this.numCustomSlots, playerInventoryEnd, false)) {
            return ItemStack.f_41583_;
         }

         if (contained.m_41613_() == 0) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (contained.m_41613_() == stack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(player, contained);
      }

      return stack;
   }

   public void m_6877_(Player player) {
      super.m_6877_(player);
      this.returnItems();
   }

   public void returnItems() {
      if (!this.player.f_19853_.f_46443_) {
         ItemStack stack = this.inventory.m_8016_(0);
         if (stack != ItemStack.f_41583_) {
            ItemHandlerHelper.giveItemToPlayer(this.player, stack);
         }
      }
   }

   public MowzieEntity getTradingMob() {
      return this.tradingMob;
   }

   public Container getInventory() {
      return this.inventory;
   }
}
