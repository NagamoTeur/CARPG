package com.hollingsworth.arsnouveau.api.recipe;

import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.block.WixieCauldron;
import com.hollingsworth.arsnouveau.common.block.tile.WixieCauldronTile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CraftingManager {
   public ItemStack outputStack;
   public List<ItemStack> neededItems;
   public List<ItemStack> remainingItems;
   public boolean craftCompleted;

   public CraftingManager() {
      this.outputStack = ItemStack.f_41583_;
      this.neededItems = new ArrayList<>();
      this.remainingItems = new ArrayList<>();
   }

   public CraftingManager(ItemStack outputStack, List<ItemStack> neededItems) {
      this.remainingItems = this.getContainerItems(neededItems);
      this.outputStack = outputStack;
      this.neededItems = neededItems;
   }

   List<ItemStack> getContainerItems(List<ItemStack> items) {
      List<ItemStack> remaining = new ArrayList<>();

      for (ItemStack item : items) {
         if (item.hasCraftingRemainingItem()) {
            remaining.add(item.getCraftingRemainingItem());
         }
      }

      return remaining;
   }

   public ItemStack getNextItem() {
      return !this.neededItems.isEmpty() ? this.neededItems.get(0) : ItemStack.f_41583_;
   }

   public boolean giveItem(Item i) {
      if (this.canBeCompleted()) {
         return false;
      } else {
         ItemStack stackToRemove = ItemStack.f_41583_;

         for (ItemStack stack : this.neededItems) {
            if (stack.m_41720_() == i) {
               stackToRemove = stack;
               break;
            }
         }

         return this.neededItems.remove(stackToRemove);
      }
   }

   public boolean canBeCompleted() {
      return this.neededItems.isEmpty();
   }

   public boolean isCraftCompleted() {
      return this.craftCompleted;
   }

   public void completeCraft(WixieCauldronTile tile) {
      Level level = tile.m_58904_();
      BlockPos worldPosition = tile.m_58899_();
      if (!this.outputStack.m_41619_()) {
         level.m_7967_(
            new ItemEntity(
               level,
               (double)worldPosition.m_123341_(),
               (double)worldPosition.m_123342_() + 1.0,
               (double)worldPosition.m_123343_(),
               this.outputStack.m_41777_()
            )
         );
      }

      for (ItemStack i : this.remainingItems) {
         if (!i.m_41619_()) {
            level.m_7967_(
               new ItemEntity(
                  level, (double)worldPosition.m_123341_(), (double)worldPosition.m_123342_() + 1.0, (double)worldPosition.m_123343_(), i.m_41777_()
               )
            );
         }
      }

      tile.hasSource = false;
      level.m_46597_(worldPosition, (BlockState)level.m_8055_(worldPosition).m_61124_(WixieCauldron.FILLED, false));
      level.m_5594_(null, worldPosition, SoundEvents.f_12049_, SoundSource.BLOCKS, 0.15F, 0.6F);
      this.craftCompleted = true;
   }

   public void write(CompoundTag tag) {
      CompoundTag stack = new CompoundTag();
      this.outputStack.m_41739_(stack);
      tag.m_128365_("output_stack", stack);
      NBTUtil.writeItems(tag, "progress", this.neededItems);
      NBTUtil.writeItems(tag, "refund", this.remainingItems);
      tag.m_128379_("completed", this.craftCompleted);
   }

   public void read(CompoundTag tag) {
      this.outputStack = ItemStack.m_41712_(tag.m_128469_("output_stack"));
      this.neededItems = NBTUtil.readItems(tag, "progress");
      this.remainingItems = NBTUtil.readItems(tag, "refund");
   }

   public static CraftingManager fromTag(CompoundTag tag) {
      CraftingManager craftingManager = (CraftingManager)(tag.m_128471_("isPotion") ? new PotionCraftingManager() : new CraftingManager());
      craftingManager.read(tag);
      return craftingManager;
   }
}
