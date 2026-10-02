package com.rolfmao.upgradednetherite.items;

import com.rolfmao.upgradednetherite.UpgradedNetheriteMod;
import com.rolfmao.upgradednetherite.init.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;

public class UpgradedNetheriteEssenceItemBase extends Item {
   public UpgradedNetheriteEssenceItemBase() {
      super(new Properties().m_41491_(UpgradedNetheriteMod.TAB).m_41487_(16).m_41497_(Rarity.RARE));
   }

   public void m_6787_(CreativeModeTab tab, NonNullList<ItemStack> list) {
      if (this.m_220152_(tab)) {
         ItemStack itemStack = new ItemStack(this);
         if (itemStack.m_41720_() == ModItems.GOLD_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.FIRE_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.ENDER_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.WATER_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.WITHER_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.POISON_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.PHANTOM_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.FEATHER_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.CORRUPT_ESSENCE.get()) {
            list.add(itemStack);
         } else if (itemStack.m_41720_() == ModItems.ECHO_ESSENCE.get()) {
            list.add(itemStack);
         } else if (tab == CreativeModeTab.f_40754_) {
            list.add(itemStack);
         }
      }
   }
}
