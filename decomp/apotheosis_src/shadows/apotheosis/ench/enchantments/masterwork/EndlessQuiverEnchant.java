package shadows.apotheosis.ench.enchantments.masterwork;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.Enchantment.Rarity;

public class EndlessQuiverEnchant extends Enchantment {
   public EndlessQuiverEnchant() {
      super(Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
   }

   public int m_6586_() {
      return 1;
   }

   public int m_6183_(int enchantmentLevel) {
      return 60;
   }

   public int m_6175_(int enchantmentLevel) {
      return 200;
   }

   public Component m_44700_(int level) {
      return ((MutableComponent)super.m_44700_(level)).m_130940_(ChatFormatting.DARK_GREEN);
   }

   protected boolean m_5975_(Enchantment ench) {
      return super.m_5975_(ench) && ench != Enchantments.f_44952_;
   }

   public boolean isTrulyInfinite(ItemStack stack, ItemStack bow, Player player) {
      return bow.getEnchantmentLevel(this) > 0 && stack.m_41720_() instanceof ArrowItem;
   }
}
