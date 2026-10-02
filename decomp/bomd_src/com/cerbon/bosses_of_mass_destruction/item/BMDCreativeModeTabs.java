package com.cerbon.bosses_of_mass_destruction.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class BMDCreativeModeTabs {
   public static final CreativeModeTab BOSSES_OF_MASS_DESTRUCTION = new CreativeModeTab("bosses_of_mass_destruction") {
      @NotNull
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)BMDItems.BLAZING_EYE.get());
      }
   };
}
