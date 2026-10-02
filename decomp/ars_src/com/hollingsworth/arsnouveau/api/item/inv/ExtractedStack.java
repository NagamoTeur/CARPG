package com.hollingsworth.arsnouveau.api.item.inv;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class ExtractedStack extends SlotReference {
   public ItemStack stack;

   protected ExtractedStack(ItemStack stack, IItemHandler handler, int slot) {
      super(handler, slot);
      this.stack = stack;
   }

   public static ExtractedStack from(IItemHandler handler, int slot, int amount) {
      return new ExtractedStack(handler.extractItem(slot, amount, false), handler, slot);
   }

   public static ExtractedStack from(SlotReference slotReference, int amount) {
      return from(slotReference.getHandler(), slotReference.getSlot(), amount);
   }

   public static ExtractedStack empty() {
      return new ExtractedStack(ItemStack.f_41583_, null, -1);
   }

   public void replaceAndReturnOrDrop(ItemStack stack, Level level, BlockPos pos) {
      this.stack = stack;
      this.stack = this.returnStack();
      if (!this.stack.m_41619_()) {
         level.m_7967_(new ItemEntity(level, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, this.stack.m_41777_()));
         this.stack.m_41764_(0);
      }
   }

   public ItemStack returnStack() {
      if (this.isEmpty()) {
         return ItemStack.f_41583_;
      } else {
         ItemStack remainder = this.handler.insertItem(this.slot, this.stack, false);
         return ItemHandlerHelper.insertItemStacked(this.handler, remainder, false);
      }
   }

   public void returnOrDrop(Level level, BlockPos pos) {
      if (!this.isEmpty()) {
         ItemStack remainder = this.returnStack();
         if (!remainder.m_41619_()) {
            level.m_7967_(new ItemEntity(level, (double)pos.m_123341_() + 0.5, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.5, remainder.m_41777_()));
            this.stack = ItemStack.f_41583_;
         }
      }
   }

   @Override
   public boolean isEmpty() {
      return super.isEmpty() || this.stack.m_41619_();
   }

   public ItemStack getStack() {
      return this.stack;
   }
}
