package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class ContainerUmvuthanaTrade extends ContainerTradeBase {
   private final EntityUmvuthanaMinion umvuthanaMinion;
   private final InventoryUmvuthana inventoryUmvuthana;

   public ContainerUmvuthanaTrade(int id, Inventory playerInventory) {
      this(id, (EntityUmvuthanaMinion)MowziesMobs.PROXY.getReferencedMob(), playerInventory);
   }

   public ContainerUmvuthanaTrade(int id, EntityUmvuthanaMinion barakoaya, Inventory playerInv) {
      this(id, barakoaya, new InventoryUmvuthana(barakoaya), playerInv);
   }

   public ContainerUmvuthanaTrade(int id, EntityUmvuthanaMinion umvuthanaMinion, InventoryUmvuthana inventory, Inventory playerInv) {
      super((MenuType<?>)ContainerHandler.CONTAINER_UMVUTHANA_TRADE.get(), id, umvuthanaMinion, inventory, playerInv);
      this.inventoryUmvuthana = inventory;
      this.umvuthanaMinion = umvuthanaMinion;
   }

   @Override
   protected void addCustomSlots(Inventory playerInv) {
      this.m_38897_(new Slot(this.getInventory(), 0, 80, 54));
      this.m_38897_(new ContainerUmvuthanaTrade.SlotResult(this.getInventory(), 1, 133, 54));
   }

   public void m_6199_(Container inv) {
      this.inventoryUmvuthana.reset();
      super.m_6199_(inv);
   }

   @Override
   public void m_6877_(Player player) {
      super.m_6877_(player);
      if (this.umvuthanaMinion != null) {
         this.umvuthanaMinion.setCustomer(null);
      }
   }

   public EntityUmvuthanaMinion getUmvuthana() {
      return this.umvuthanaMinion;
   }

   public InventoryUmvuthana getInventoryUmvuthana() {
      return this.inventoryUmvuthana;
   }

   private class SlotResult extends Slot {
      private int removeCount;

      public SlotResult(Container inventory, int index, int x, int y) {
         super(inventory, index, x, y);
      }

      public boolean m_5857_(ItemStack stack) {
         return false;
      }

      public ItemStack m_6201_(int amount) {
         if (this.m_6657_()) {
            this.removeCount = this.removeCount + Math.min(amount, this.m_7993_().m_41613_());
         }

         return super.m_6201_(amount);
      }

      protected void m_7169_(ItemStack stack, int amount) {
         this.removeCount += amount;
         super.m_7169_(stack, amount);
      }

      protected void m_5845_(ItemStack stack) {
         stack.m_41678_(ContainerUmvuthanaTrade.this.umvuthanaMinion.f_19853_, ContainerUmvuthanaTrade.this.player, this.removeCount);
         this.removeCount = 0;
      }

      public ItemStack m_150647_(int p_150648_, int p_150649_, Player p_150650_) {
         return super.m_150647_(p_150648_, p_150649_, p_150650_);
      }

      public void m_142406_(Player player, ItemStack stack) {
         this.m_5845_(stack);
         if (ContainerUmvuthanaTrade.this.umvuthanaMinion != null && ContainerUmvuthanaTrade.this.umvuthanaMinion.isOfferingTrade()) {
            Trade trade = ContainerUmvuthanaTrade.this.umvuthanaMinion.getOfferingTrade();
            ItemStack input = this.f_40218_.m_8020_(0);
            ItemStack tradeInput = trade.getInput();
            if (input.m_41720_() == tradeInput.m_41720_() && input.m_41613_() >= tradeInput.m_41613_()) {
               input.m_41774_(tradeInput.m_41613_());
               if (input.m_41613_() <= 0) {
                  input = ItemStack.f_41583_;
               }

               this.f_40218_.m_6836_(0, input);
            }
         }
      }
   }
}
