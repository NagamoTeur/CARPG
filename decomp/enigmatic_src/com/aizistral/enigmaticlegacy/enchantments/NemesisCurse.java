package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class NemesisCurse extends Enchantment {
   public NemesisCurse(EquipmentSlot... slots) {
      super(Rarity.RARE, EnchantmentCategory.WEAPON, slots);
   }

   public int m_6183_(int enchantmentLevel) {
      return 25;
   }

   public int m_6175_(int enchantmentLevel) {
      return 50;
   }

   public int m_44702_() {
      return 1;
   }

   public int m_6586_() {
      return 1;
   }

   public boolean m_6081_(ItemStack stack) {
      return OmniconfigHandler.isItemEnabled(this) && (super.m_6081_(stack) || Enchantments.f_44977_.m_6081_(stack));
   }

   public boolean m_6591_() {
      return true;
   }

   public boolean m_6589_() {
      return true;
   }

   public boolean isAllowedOnBooks() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   public boolean m_6592_() {
      return OmniconfigHandler.isItemEnabled(this);
   }

   protected boolean m_5975_(Enchantment ench) {
      return super.m_5975_(ench);
   }
}
