package com.hollingsworth.arsnouveau.common.block.tile;

import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public class TransientCustomContainer extends CraftingContainer {
   public TransientCustomContainer(AbstractContainerMenu pMenu, int pWidth, int pHeight) {
      this(pMenu, pWidth, pHeight, NonNullList.m_122780_(pWidth * pHeight, ItemStack.f_41583_));
   }

   public TransientCustomContainer(AbstractContainerMenu pMenu, int pWidth, int pHeight, NonNullList<ItemStack> pItems) {
      super(pMenu, pWidth, pHeight);
      this.f_39320_ = pItems;
   }

   public ItemStack removeItemNoUpdate(int pSlot, int pAmount) {
      return ContainerHelper.m_18969_(this.f_39320_, pSlot, pAmount);
   }

   public void setItemNoUpdate(int pSlot, ItemStack pStack) {
      this.f_39320_.set(pSlot, pStack);
   }
}
