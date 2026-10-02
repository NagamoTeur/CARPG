package com.aizistral.enigmaticlegacy.enchantments;

import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class CeaselessEnchantment extends Enchantment {
   public static Omniconfig.BooleanParameter allowNoArrow;

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("CeaselessEnchantment");
      allowNoArrow = builder.comment(
            "Whether or not crossbows with Ceaseless should be able to shoot basic arrows even if there are none in player's inventory."
         )
         .getBoolean("AllowNoArrow", true);
      builder.popPrefix();
   }

   public CeaselessEnchantment(EquipmentSlot... slots) {
      super(Rarity.RARE, EnchantmentCategory.CROSSBOW, slots);
   }

   public int m_44702_() {
      return 1;
   }

   public int m_6586_() {
      return 1;
   }

   protected boolean m_5975_(Enchantment ench) {
      return super.m_5975_(ench);
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
