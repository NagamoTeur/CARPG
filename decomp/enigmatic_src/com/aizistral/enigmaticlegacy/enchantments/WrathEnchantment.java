package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.TridentImpalerEnchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class WrathEnchantment extends Enchantment {
   public WrathEnchantment(EquipmentSlot... slots) {
      super(Rarity.RARE, EnchantmentCategory.TRIDENT, slots);
   }

   public int m_6183_(int enchantmentLevel) {
      return 1 + (enchantmentLevel - 1) * 8;
   }

   public int m_6175_(int enchantmentLevel) {
      return this.m_6183_(enchantmentLevel) + 20;
   }

   public int m_6586_() {
      return 5;
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return this.m_6081_(stack) && super.canApplyAtEnchantingTable(stack);
   }

   public boolean m_5975_(Enchantment ench) {
      return !(ench instanceof DamageEnchantment)
         && !(ench instanceof TridentImpalerEnchantment)
         && !(ench instanceof TorrentEnchantment)
         && super.m_5975_(ench);
   }

   public boolean isAllowedOnBooks() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   public boolean m_6592_() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   public boolean m_6081_(ItemStack stack) {
      return OmniconfigHandler.isItemEnabled(this) && stack.canApplyAtEnchantingTable(this);
   }

   public float bonusDamageByCreature(LivingEntity attacker, LivingEntity living, int level) {
      return 0.0F;
   }

   public float m_7335_(int level, MobType creatureType) {
      return (float)level * 1.25F;
   }
}
