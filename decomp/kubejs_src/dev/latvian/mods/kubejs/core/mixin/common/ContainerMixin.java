package dev.latvian.mods.kubejs.core.mixin.common;

import dev.latvian.mods.kubejs.core.InventoryKJS;
import dev.latvian.mods.kubejs.item.ItemHandlerUtils;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({Container.class})
public interface ContainerMixin extends InventoryKJS {
   default Container kjs$self() {
      return (Container)this;
   }

   @Override
   default boolean kjs$isMutable() {
      return true;
   }

   @Override
   default int kjs$getSlots() {
      return this.kjs$self().m_6643_();
   }

   @NotNull
   @Override
   default ItemStack kjs$getStackInSlot(int slot) {
      return this.kjs$self().m_8020_(slot);
   }

   @NotNull
   @Override
   default ItemStack kjs$insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
      if (stack.m_41619_()) {
         return ItemStack.f_41583_;
      } else {
         ItemStack stackInSlot = this.kjs$self().m_8020_(slot);
         if (!stackInSlot.m_41619_()) {
            if (stackInSlot.m_41613_() >= Math.min(stackInSlot.m_41741_(), this.kjs$getSlotLimit(slot))) {
               return stack;
            } else if (!ItemHandlerUtils.canItemStacksStack(stack, stackInSlot)) {
               return stack;
            } else if (!this.kjs$self().m_7013_(slot, stack)) {
               return stack;
            } else {
               int m = Math.min(stack.m_41741_(), this.kjs$getSlotLimit(slot)) - stackInSlot.m_41613_();
               if (stack.m_41613_() <= m) {
                  if (!simulate) {
                     ItemStack copy = stack.m_41777_();
                     copy.m_41769_(stackInSlot.m_41613_());
                     this.kjs$self().m_6836_(slot, copy);
                     this.kjs$self().m_6596_();
                  }

                  return ItemStack.f_41583_;
               } else {
                  stack = stack.m_41777_();
                  if (!simulate) {
                     ItemStack copy = stack.m_41620_(m);
                     copy.m_41769_(stackInSlot.m_41613_());
                     this.kjs$self().m_6836_(slot, copy);
                     this.kjs$self().m_6596_();
                  } else {
                     stack.m_41774_(m);
                  }

                  return stack;
               }
            }
         } else if (!this.kjs$self().m_7013_(slot, stack)) {
            return stack;
         } else {
            int m = Math.min(stack.m_41741_(), this.kjs$getSlotLimit(slot));
            if (m < stack.m_41613_()) {
               stack = stack.m_41777_();
               if (!simulate) {
                  this.kjs$self().m_6836_(slot, stack.m_41620_(m));
                  this.kjs$self().m_6596_();
                  return stack;
               } else {
                  stack.m_41774_(m);
                  return stack;
               }
            } else {
               if (!simulate) {
                  this.kjs$self().m_6836_(slot, stack);
                  this.kjs$self().m_6596_();
               }

               return ItemStack.f_41583_;
            }
         }
      }
   }

   @NotNull
   @Override
   default ItemStack kjs$extractItem(int slot, int amount, boolean simulate) {
      if (amount == 0) {
         return ItemStack.f_41583_;
      } else {
         ItemStack stackInSlot = this.kjs$self().m_8020_(slot);
         if (stackInSlot.m_41619_()) {
            return ItemStack.f_41583_;
         } else if (simulate) {
            if (stackInSlot.m_41613_() < amount) {
               return stackInSlot.m_41777_();
            } else {
               ItemStack copy = stackInSlot.m_41777_();
               copy.m_41764_(amount);
               return copy;
            }
         } else {
            int m = Math.min(stackInSlot.m_41613_(), amount);
            ItemStack decrStackSize = this.kjs$self().m_7407_(slot, m);
            this.kjs$self().m_6596_();
            return decrStackSize;
         }
      }
   }

   @Override
   default void kjs$setStackInSlot(int slot, @NotNull ItemStack stack) {
      this.kjs$self().m_6836_(slot, stack);
   }

   @Override
   default int kjs$getSlotLimit(int slot) {
      return this.kjs$self().m_6893_();
   }

   @Override
   default boolean kjs$isItemValid(int slot, @NotNull ItemStack stack) {
      return this.kjs$self().m_7013_(slot, stack);
   }

   @Override
   default int kjs$getWidth() {
      if (this.kjs$self() instanceof ChestBlockEntity) {
         return 9;
      } else {
         return this.kjs$self() instanceof CraftingContainer crafter ? crafter.m_39347_() : this.kjs$getSlots();
      }
   }

   @Override
   default int kjs$getHeight() {
      if (this.kjs$self() instanceof ChestBlockEntity) {
         return this.kjs$getSlots() / 9;
      } else {
         return this.kjs$self() instanceof CraftingContainer crafter ? crafter.m_39346_() : 1;
      }
   }

   @Override
   default void kjs$clear() {
      this.kjs$self().m_6211_();
   }

   @Override
   default void kjs$setChanged() {
      this.kjs$self().m_6596_();
      if (this.kjs$self() instanceof Inventory inv) {
         inv.f_35978_.kjs$sendInventoryUpdate();
      }
   }

   @Nullable
   @Override
   default BlockContainerJS kjs$getBlock(Level level) {
      return this.kjs$self() instanceof BlockEntity be ? level.kjs$getBlock(be) : null;
   }
}
