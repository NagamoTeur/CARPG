package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import shadows.apotheosis.ench.EnchModule;

public class TemptingEnchant extends Enchantment {
   public TemptingEnchant() {
      super(Rarity.UNCOMMON, EnchModule.HOE, new EquipmentSlot[0]);
   }

   public int m_6183_(int enchantmentLevel) {
      return 0;
   }

   public int m_6175_(int enchantmentLevel) {
      return 200;
   }

   public boolean shouldFollow(LivingEntity target) {
      ItemStack stack = target.m_21205_();
      if (stack.getEnchantmentLevel(this) > 0) {
         return true;
      } else {
         stack = target.m_21206_();
         return stack.getEnchantmentLevel(this) > 0;
      }
   }
}
