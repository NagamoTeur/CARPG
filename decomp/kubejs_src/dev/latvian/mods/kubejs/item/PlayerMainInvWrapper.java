package dev.latvian.mods.kubejs.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerMainInvWrapper extends RangedWrapper {
   private final Inventory inventoryPlayer;

   public PlayerMainInvWrapper(Inventory inv) {
      super(inv, 0, inv.f_35974_.size());
      this.inventoryPlayer = inv;
   }

   @NotNull
   @Override
   public ItemStack kjs$insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
      ItemStack rest = super.kjs$insertItem(slot, stack, simulate);
      if (rest.m_41613_() != stack.m_41613_()) {
         ItemStack inSlot = this.kjs$getStackInSlot(slot);
         if (!inSlot.m_41619_()) {
            if (this.getInventoryPlayer().f_35978_.f_19853_.f_46443_) {
               inSlot.m_41754_(5);
            } else if (this.getInventoryPlayer().f_35978_ instanceof ServerPlayer) {
               this.getInventoryPlayer().f_35978_.f_36096_.m_38946_();
            }
         }
      }

      return rest;
   }

   public Inventory getInventoryPlayer() {
      return this.inventoryPlayer;
   }
}
