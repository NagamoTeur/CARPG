package com.min01.archaeology.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public interface ContainerSingleItem extends Container {
   ItemStack getTheItem();

   default ItemStack splitTheItem(int amount) {
      return this.getTheItem().m_41620_(amount);
   }

   void setTheItem(ItemStack var1);

   default ItemStack removeTheItem() {
      return this.splitTheItem(this.m_6893_());
   }

   default int m_6643_() {
      return 1;
   }

   default boolean m_7983_() {
      return this.getTheItem().m_41619_();
   }

   default void m_6211_() {
      this.removeTheItem();
   }

   default ItemStack m_8016_(int i) {
      return this.m_7407_(i, this.m_6893_());
   }

   default ItemStack m_8020_(int i) {
      return i == 0 ? this.getTheItem() : ItemStack.f_41583_;
   }

   default ItemStack m_7407_(int slot, int amount) {
      return slot != 0 ? ItemStack.f_41583_ : this.splitTheItem(amount);
   }

   default void m_6836_(int slot, @NotNull ItemStack stack) {
      if (slot == 0) {
         this.setTheItem(stack);
      }
   }

   public interface BlockContainerSingleItem extends ContainerSingleItem {
      BlockEntity getContainerBlockEntity();

      default boolean m_6542_(@NotNull Player player) {
         BlockEntity blockEntity = this.getContainerBlockEntity();
         if (blockEntity.m_58904_() == null) {
            return false;
         } else {
            return blockEntity.m_58904_().m_7702_(blockEntity.m_58899_()) != blockEntity ? false : player.canInteractWith(blockEntity.m_58899_(), 4.0);
         }
      }
   }
}
