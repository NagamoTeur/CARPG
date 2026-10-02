package shadows.apotheosis.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import shadows.apotheosis.ench.EnchModule;

@Mixin(
   value = {Enchantment.class},
   priority = 1500
)
public class EnchantmentMixin {
   @Inject(
      method = {"getFullname"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void apoth_modifyEnchColorForAboveMaxLevel(int level, CallbackInfoReturnable<Component> cir) {
      Enchantment ench = (Enchantment)this;
      if (!ench.m_6589_() && level > ench.m_6586_() && cir.getReturnValue() instanceof MutableComponent mc) {
         cir.setReturnValue(mc.m_130938_(s -> s.m_131148_(EnchModule.Colors.LIGHT_BLUE_FLASH)));
      }
   }
}
