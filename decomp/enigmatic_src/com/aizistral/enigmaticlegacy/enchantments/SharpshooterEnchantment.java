package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class SharpshooterEnchantment extends Enchantment {
   public SharpshooterEnchantment(EquipmentSlot... slots) {
      super(Rarity.RARE, EnchantmentCategory.CROSSBOW, slots);
   }

   public int m_44702_() {
      return 1;
   }

   public int m_6586_() {
      return 5;
   }

   protected boolean m_5975_(Enchantment ench) {
      return ench != Enchantments.f_44959_ && ench != Enchantments.f_44961_ && super.m_5975_(ench);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return this.m_6081_(stack) && stack.m_41720_() instanceof CrossbowItem;
   }

   public boolean m_6591_() {
      return false;
   }

   public boolean m_6589_() {
      return false;
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
}
