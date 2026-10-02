package shadows.apotheosis.ench.enchantments.masterwork;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;
import shadows.apotheosis.ench.EnchModule;

public class GrowthSerumEnchant extends Enchantment {
   public GrowthSerumEnchant() {
      super(Rarity.VERY_RARE, EnchModule.SHEARS, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
   }

   public int m_6183_(int pLevel) {
      return 55;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_GREEN);
   }

   public void unshear(Sheep sheep, ItemStack shears) {
      if (shears.getEnchantmentLevel(this) > 0 && sheep.f_19796_.m_188499_()) {
         sheep.m_29878_(false);
      }
   }
}
