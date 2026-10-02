package shadows.apotheosis.ench.asm;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.enchantment.Enchantment;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.EnchModule;

public class EnchHooks {
   public static int getMaxLevel(Enchantment ench) {
      return !Apotheosis.enableEnch ? ench.m_6586_() : EnchModule.getEnchInfo(ench).getMaxLevel();
   }

   public static int getMaxLootLevel(Enchantment ench) {
      return !Apotheosis.enableEnch ? ench.m_6586_() : EnchModule.getEnchInfo(ench).getMaxLootLevel();
   }

   public static boolean isTreasureOnly(Enchantment ench) {
      return !Apotheosis.enableEnch ? ench.m_6591_() : EnchModule.getEnchInfo(ench).isTreasure();
   }

   public static boolean isDiscoverable(Enchantment ench) {
      return !Apotheosis.enableEnch ? ench.m_6592_() : EnchModule.getEnchInfo(ench).isDiscoverable();
   }

   public static boolean isLootable(Enchantment ench) {
      return !Apotheosis.enableEnch ? ench.m_6592_() : EnchModule.getEnchInfo(ench).isLootable();
   }

   public static boolean isTradeable(Enchantment ench) {
      return !Apotheosis.enableEnch ? ench.m_6594_() : EnchModule.getEnchInfo(ench).isTradeable();
   }

   public static int getTicksCaughtDelay(FishingHook bobber) {
      int lowBound = Math.max(1, 100 - bobber.f_37097_ * 10);
      int highBound = Math.max(lowBound, 600 - bobber.f_37097_ * 60);
      return Mth.m_216271_(bobber.f_19796_, lowBound, highBound);
   }
}
