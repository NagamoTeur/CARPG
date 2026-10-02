package com.hollingsworth.arsnouveau.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class ManaRegenEnchantment extends Enchantment {
   protected ManaRegenEnchantment() {
      super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[]{EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.HEAD, EquipmentSlot.LEGS});
   }

   public int m_6183_(int enchantmentLevel) {
      return 1 + 11 * (enchantmentLevel - 1);
   }

   public int m_6586_() {
      return 3;
   }
}
