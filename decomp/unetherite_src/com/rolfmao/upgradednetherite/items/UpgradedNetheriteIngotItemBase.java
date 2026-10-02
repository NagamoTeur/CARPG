package com.rolfmao.upgradednetherite.items;

import com.rolfmao.upgradednetherite.UpgradedNetheriteMod;
import com.rolfmao.upgradednetherite.init.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;

public class UpgradedNetheriteIngotItemBase extends Item {
   public UpgradedNetheriteIngotItemBase() {
      super(new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41497_(Rarity.RARE).m_41486_());
   }

   public boolean isPiglinCurrency(ItemStack stack) {
      return ItemStack.m_41758_(stack, new ItemStack((ItemLike)ModItems.GOLD_UPGRADED_NETHERITE_INGOT.get()));
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (itemStack.m_41720_() == ModItems.GOLD_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.FIRE_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.ENDER_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.WATER_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.WITHER_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.POISON_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.PHANTOM_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.FEATHER_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.CORRUPT_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.ECHO_UPGRADED_NETHERITE_INGOT.get()) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }
}
