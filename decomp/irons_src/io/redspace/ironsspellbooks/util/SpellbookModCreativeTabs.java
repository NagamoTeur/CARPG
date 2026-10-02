package io.redspace.ironsspellbooks.util;

import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class SpellbookModCreativeTabs {
   public static final CreativeModeTab SPELL_MATERIALS_TAB = new CreativeModeTab("spell_materials_tab") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)ItemRegistry.DIVINE_PEARL.get());
      }
   };
   public static final CreativeModeTab SPELL_EQUIPMENT_TAB = new CreativeModeTab("spell_equipment_tab") {
      public ItemStack m_6976_() {
         return new ItemStack((ItemLike)ItemRegistry.IRON_SPELL_BOOK.get());
      }
   };
}
