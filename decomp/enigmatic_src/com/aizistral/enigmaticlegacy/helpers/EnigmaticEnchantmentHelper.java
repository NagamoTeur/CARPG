package com.aizistral.enigmaticlegacy.helpers;

import com.aizistral.enigmaticlegacy.registries.EnigmaticEnchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class EnigmaticEnchantmentHelper {
   public static boolean hasCustomCrossbowEnchantments(ItemStack crossbowStack) {
      return EnchantmentHelper.m_44843_(EnigmaticEnchantments.CEASELESS, crossbowStack) > 0
         || EnchantmentHelper.m_44843_(EnigmaticEnchantments.SHARPSHOOTER, crossbowStack) > 0;
   }

   public static boolean hasSharpshooterEnchantment(ItemStack stack) {
      return getSharpshooterLevel(stack) > 0;
   }

   public static boolean hasCeaselessEnchantment(ItemStack stack) {
      return getCeaselessLevel(stack) > 0;
   }

   public static boolean hasNemesisCurseEnchantment(ItemStack stack) {
      return getNemesisCurseLevel(stack) > 0;
   }

   public static int getSharpshooterLevel(ItemStack stack) {
      return EnchantmentHelper.m_44843_(EnigmaticEnchantments.SHARPSHOOTER, stack);
   }

   public static int getCeaselessLevel(ItemStack stack) {
      return EnchantmentHelper.m_44843_(EnigmaticEnchantments.CEASELESS, stack);
   }

   public static int getNemesisCurseLevel(ItemStack stack) {
      return EnchantmentHelper.m_44843_(EnigmaticEnchantments.NEMESIS, stack);
   }
}
