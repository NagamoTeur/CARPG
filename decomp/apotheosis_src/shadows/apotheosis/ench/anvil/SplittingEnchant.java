package shadows.apotheosis.ench.anvil;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import shadows.apotheosis.ench.EnchModule;

public class SplittingEnchant extends Enchantment {
   public SplittingEnchant() {
      super(Rarity.RARE, EnchModule.ANVIL, new EquipmentSlot[0]);
   }

   public int m_6183_(int enchantmentLevel) {
      return 20;
   }

   public int m_6175_(int enchantmentLevel) {
      return 200;
   }

   public int m_6586_() {
      return 1;
   }
}
