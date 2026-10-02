package com.bobmowzie.mowziesmobs.server.inventory;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public final class ContainerUmvuthiTrade extends ContainerTradeBase {
   private final EntityUmvuthi barako;
   private final InventoryUmvuthi inventoryUmvuthi;

   public ContainerUmvuthiTrade(int id, Inventory playerInventory) {
      this(id, (EntityUmvuthi)MowziesMobs.PROXY.getReferencedMob(), playerInventory);
   }

   public ContainerUmvuthiTrade(int id, EntityUmvuthi barako, Inventory playerInv) {
      this(id, barako, new InventoryUmvuthi(barako), playerInv);
   }

   public ContainerUmvuthiTrade(int id, EntityUmvuthi barako, InventoryUmvuthi inventory, Inventory playerInv) {
      super((MenuType<?>)ContainerHandler.CONTAINER_UMVUTHI_TRADE.get(), id, barako, inventory, playerInv);
      this.barako = barako;
      this.inventoryUmvuthi = inventory;
   }

   @Override
   protected void addCustomSlots(Inventory playerInv) {
      EntityUmvuthi barako = (EntityUmvuthi)this.getTradingMob();
      InventoryUmvuthi inventoryUmvuthi = (InventoryUmvuthi)this.inventory;
      if (barako != null && !barako.hasTradedWith(playerInv.f_35978_)) {
         this.m_38897_(new Slot(inventoryUmvuthi, 0, 69, 54));
      }
   }

   @Override
   public void m_6877_(Player player) {
      super.m_6877_(player);
      if (this.barako != null) {
         this.barako.setCustomer(null);
      }
   }

   public EntityUmvuthi getUmvuthi() {
      return this.barako;
   }

   public InventoryUmvuthi getInventoryUmvuthi() {
      return this.inventoryUmvuthi;
   }
}
