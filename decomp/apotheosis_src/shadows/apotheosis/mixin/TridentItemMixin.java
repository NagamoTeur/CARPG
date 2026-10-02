package shadows.apotheosis.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import shadows.apotheosis.Apotheosis;

@Mixin({TridentItem.class})
public abstract class TridentItemMixin extends Item {
   public TridentItemMixin(Properties pProperties) {
      super(pProperties);
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment ench) {
      return !Apotheosis.enableEnch
         ? super.canApplyAtEnchantingTable(stack, ench)
         : super.canApplyAtEnchantingTable(stack, ench) || ench == Enchantments.f_44977_ || ench == Enchantments.f_44982_ || ench == Enchantments.f_44961_;
   }

   public String getCreatorModId(ItemStack itemStack) {
      return Apotheosis.enableEnch ? "apotheosis" : "minecraft";
   }
}
