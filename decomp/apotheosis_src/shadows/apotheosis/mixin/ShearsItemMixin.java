package shadows.apotheosis.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import shadows.apotheosis.Apotheosis;

@Mixin({ShearsItem.class})
public class ShearsItemMixin extends Item {
   public ShearsItemMixin(Properties pProperties) {
      super(pProperties);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment ench) {
      return !Apotheosis.enableEnch
         ? super.canApplyAtEnchantingTable(stack, ench)
         : super.canApplyAtEnchantingTable(stack, ench) || ench == Enchantments.f_44986_ || ench == Enchantments.f_44984_ || ench == Enchantments.f_44987_;
   }

   public int m_6473_() {
      return Apotheosis.enableEnch ? 15 : 0;
   }

   public String getCreatorModId(ItemStack itemStack) {
      return Apotheosis.enableEnch && this == Items.f_42574_ ? "apotheosis" : super.getCreatorModId(itemStack);
   }
}
