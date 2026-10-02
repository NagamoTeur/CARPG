package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;

public class EternalBindingCurse extends Enchantment {
   private final List<String> incompatibleKeywords = new ArrayList<>();

   public EternalBindingCurse(EquipmentSlot... slots) {
      super(Rarity.RARE, EnchantmentCategory.WEARABLE, slots);
      this.incompatibleKeywords.add("soulbound");
      this.incompatibleKeywords.add("soulbinding");
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
      return OmniconfigHandler.isItemEnabled(this)
         && !stack.m_150930_(EnigmaticItems.CURSED_RING)
         && !stack.m_150930_(EnigmaticItems.ESCAPE_SCROLL)
         && !stack.m_150930_(EnigmaticItems.ENIGMATIC_AMULET)
         && !stack.m_150930_(EnigmaticItems.DESOLATION_RING)
         && super.m_6081_(stack);
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
      return false;
   }

   protected boolean m_5975_(Enchantment ench) {
      if (this.incompatibleKeywords
         .stream()
         .anyMatch(keyword -> StringUtils.containsIgnoreCase(keyword, ForgeRegistries.ENCHANTMENTS.getKey(ench).m_135815_()))) {
         return false;
      } else {
         return ench != Enchantments.f_44975_ && ench != Enchantments.f_44963_ ? super.m_5975_(ench) : false;
      }
   }
}
