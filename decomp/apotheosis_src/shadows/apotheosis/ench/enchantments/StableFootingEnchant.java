package shadows.apotheosis.ench.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class StableFootingEnchant extends Enchantment {
   public StableFootingEnchant() {
      super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
   }

   public int m_6183_(int level) {
      return 40;
   }

   public int m_6175_(int level) {
      return 200;
   }

   public void breakSpeed(BreakSpeed e) {
      Player p = e.getEntity();
      if (!p.m_20096_() && EnchantmentHelper.m_44836_(this, p) > 0 && e.getOriginalSpeed() < e.getNewSpeed() * 5.0F) {
         e.setNewSpeed(e.getNewSpeed() * 5.0F);
      }
   }
}
