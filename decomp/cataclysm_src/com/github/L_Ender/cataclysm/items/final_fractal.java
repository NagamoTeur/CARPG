package com.github.L_Ender.cataclysm.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;

public class final_fractal extends SwordItem {
   public final_fractal(ModItemTier toolMaterial, Properties props) {
      super(toolMaterial, 3, -2.4F, props);
   }

   public boolean m_6832_(ItemStack itemStack, ItemStack itemStackMaterial) {
      return false;
   }

   public void setDamage(ItemStack stack, int damage) {
      super.setDamage(stack, 0);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment.f_44672_ != EnchantmentCategory.BREAKABLE && enchantment.f_44672_ == EnchantmentCategory.WEAPON;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.cataclysm.final_fractal.desc").m_130940_(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.m_237115_("item.cataclysm.wip.desc").m_130940_(ChatFormatting.DARK_GREEN));
   }
}
