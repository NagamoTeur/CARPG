package com.hollingsworth.arsnouveau.common.items.itemscrolls;

import com.hollingsworth.arsnouveau.common.items.ItemScroll;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public class MimicItemScroll extends ItemScroll {
   public MimicItemScroll() {
   }

   public MimicItemScroll(Properties properties) {
      super(properties);
   }

   @Override
   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
      return InteractionResultHolder.m_19098_(pPlayer.m_21120_(pUsedHand));
   }

   @Override
   public ItemScroll.SortPref getSortPref(ItemStack stackToStore, ItemStack scrollStack, IItemHandler inventory) {
      for (int i = 0; i < inventory.getSlots(); i++) {
         ItemStack inventoryStack = inventory.getStackInSlot(i);
         if (!inventoryStack.m_41619_() && inventory.getStackInSlot(i).m_41726_(stackToStore)) {
            return ItemScroll.SortPref.HIGHEST;
         }
      }

      return ItemScroll.SortPref.INVALID;
   }
}
