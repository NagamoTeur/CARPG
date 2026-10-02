package shadows.apotheosis.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.ench.enchantments.masterwork.EndlessQuiverEnchant;

@Mixin({ArrowItem.class})
public class ArrowItemMixin {
   @Inject(
      method = {"isInfinite"},
      at = {@At("RETURN")},
      remap = false,
      cancellable = true
   )
   public void apoth_isInfinite(ItemStack stack, ItemStack bow, Player player, CallbackInfoReturnable<Boolean> ci) {
      if (!ci.getReturnValueZ() && Apotheosis.enableEnch) {
         ci.setReturnValue(((EndlessQuiverEnchant)Apoth.Enchantments.ENDLESS_QUIVER.get()).isTrulyInfinite(stack, bow, player));
      }
   }
}
