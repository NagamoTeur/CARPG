package shadows.apotheosis.ench.enchantments;

import com.google.common.base.Predicates;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class InertEnchantment extends Enchantment {
   public static final EnchantmentCategory NULL = EnchantmentCategory.create("apotheosis.null", Predicates.alwaysFalse());

   public InertEnchantment() {
      super(Rarity.VERY_RARE, NULL, new EquipmentSlot[0]);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack) {
      return false;
   }

   public boolean m_6592_() {
      return false;
   }

   public boolean isAllowedOnBooks() {
      return false;
   }

   public boolean m_6594_() {
      return false;
   }

   public boolean m_6591_() {
      return true;
   }
}
