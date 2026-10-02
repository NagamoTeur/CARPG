package com.hollingsworth.arsnouveau.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class ReactiveEnchantment extends Enchantment {
   protected ReactiveEnchantment() {
      super(
         Rarity.VERY_RARE, EnchantmentCategory.WEARABLE, new EquipmentSlot[]{EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS}
      );
   }

   public int m_6183_(int enchantmentLevel) {
      return 0;
   }

   public int m_6175_(int enchantmentLevel) {
      return 0;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_6081_(ItemStack stack) {
      return true;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return false;
   }

   public boolean isAllowedOnBooks() {
      return false;
   }
}
