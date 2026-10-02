package com.github.L_Ender.cataclysm.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ModGroup {
   public static final CreativeModeTab ITEM = new CreativeModeTab("cataclysm.item") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)ModItems.THE_INCINERATOR.get());
      }
   };
   public static final CreativeModeTab BLOCK = new CreativeModeTab("cataclysm.block") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)ModItems.VOID_STONE.get());
      }
   };
}
