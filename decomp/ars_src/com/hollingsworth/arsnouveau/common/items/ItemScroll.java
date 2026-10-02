package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.RegistryHelper;
import com.hollingsworth.arsnouveau.api.item.IScribeable;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;

public abstract class ItemScroll extends ModItem implements IScribeable {
   @Deprecated(
      forRemoval = true
   )
   public static String ITEM_PREFIX = "item_";

   public ItemScroll() {
   }

   public ItemScroll(Properties properties) {
      super(properties);
   }

   public abstract ItemScroll.SortPref getSortPref(ItemStack var1, ItemStack var2, IItemHandler var3);

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
      if (pUsedHand == InteractionHand.MAIN_HAND && !pLevel.f_46443_) {
         ItemStack thisStack = pPlayer.m_21120_(pUsedHand);
         ItemStack otherStack = pPlayer.m_21120_(InteractionHand.OFF_HAND);
         if (!otherStack.m_41619_()) {
            this.onScribe(pLevel, pPlayer.m_20183_(), pPlayer, InteractionHand.OFF_HAND, thisStack);
            return InteractionResultHolder.m_19090_(thisStack);
         }
      }

      return super.m_7203_(pLevel, pPlayer, pUsedHand);
   }

   @Override
   public boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack thisStack) {
      ItemStack stackToWrite = player.m_21120_(handIn);
      ItemScroll.ItemScrollData scrollData = new ItemScroll.ItemScrollData(thisStack);
      return scrollData.writeWithFeedback(player, stackToWrite);
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (stack.m_41782_()) {
         ItemScroll.ItemScrollData scrollData = new ItemScroll.ItemScrollData(stack);

         for (ItemStack s : scrollData.items) {
            tooltip2.add(s.m_41786_());
         }
      }
   }

   public static class ItemScrollData extends ItemstackData {
      private List<ItemStack> items = new ArrayList<>();

      public ItemScrollData(ItemStack stack) {
         super(stack);
         CompoundTag tag = this.getItemTag(stack);
         if (tag != null && !tag.m_128456_()) {
            for (String s : tag.m_128431_()) {
               if (s.contains(ItemScroll.ITEM_PREFIX)) {
                  this.items.add(ItemStack.m_41712_(tag.m_128469_(s)));
               }
            }
         }
      }

      public boolean writeWithFeedback(Player player, ItemStack stackToWrite) {
         if (stackToWrite.m_41619_()) {
            return false;
         } else if (this.containsStack(stackToWrite)) {
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.scribe.item_removed"));
            return this.remove(stackToWrite);
         } else if (this.add(stackToWrite)) {
            PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.scribe.item_added"));
            return true;
         } else {
            return false;
         }
      }

      public boolean containsStack(ItemStack stack) {
         return this.items.stream().anyMatch(s -> s.m_41656_(stack));
      }

      public boolean remove(ItemStack stack) {
         boolean didRemove = this.items.removeIf(s -> s.m_41656_(stack));
         this.writeItem();
         return didRemove;
      }

      public boolean add(ItemStack stack) {
         boolean added = this.items.add(stack.m_41777_());
         this.writeItem();
         return added;
      }

      public List<ItemStack> getItems() {
         return this.items;
      }

      public String getItemKey(ItemStack stack) {
         return ItemScroll.ITEM_PREFIX + RegistryHelper.getRegistryName(stack.m_41720_()).toString();
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         for (ItemStack s : this.items) {
            CompoundTag itemTag = new CompoundTag();
            s.m_41739_(itemTag);
            tag.m_128365_(this.getItemKey(s), itemTag);
         }
      }

      @Override
      public String getTagString() {
         return "an_scrollData";
      }
   }

   public static enum SortPref {
      INVALID,
      LOW,
      HIGH,
      HIGHEST;
   }
}
