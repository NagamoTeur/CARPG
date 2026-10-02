package com.aqutheseal.celestisynth.common.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CSCreativeTabs {
   public static final CreativeModeTab CELESTISYNTH = new CreativeModeTab("celestisynth.celestisynth_tab") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)CSItems.SOLARIS.get());
      }
   };
}
