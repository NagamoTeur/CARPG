package shadows.apotheosis.ench.anvil;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

public class ApothAnvilItem extends BlockItem {
   public ApothAnvilItem(Block block) {
      super(block, new Properties().m_41491_(CreativeModeTab.f_40750_));
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return stack.m_41613_() == 1 && (enchantment == Enchantments.f_44986_ || super.canApplyAtEnchantingTable(stack, enchantment));
   }

   public String getCreatorModId(ItemStack itemStack) {
      return "apotheosis";
   }

   public boolean m_8120_(ItemStack stack) {
      return stack.m_41613_() == 1;
   }

   public int getEnchantmentValue(ItemStack stack) {
      return 50;
   }
}
