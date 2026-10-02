package daripher.skilltree.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({AnvilMenu.class})
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
   public AnvilMenuMixin() {
      super(null, 0, null, null);
   }

   @ModifyExpressionValue(
      method = {"createResult"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"
      )},
      require = 0
   )
   private int uncapEnchantmentLevel(int original) {
      ItemStack base = this.f_39769_.m_8020_(0);
      if (base.m_41720_() == Items.f_42690_) {
         return original;
      } else {
         ItemStack addition = this.f_39769_.m_8020_(1);
         return base.getAllEnchantments().isEmpty() && addition.m_41720_() == Items.f_42690_ ? Integer.MAX_VALUE : original;
      }
   }
}
