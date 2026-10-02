package shadows.apotheosis.mixin;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.apotheosis.core.attributeslib.AttributesLib;
import shadows.apotheosis.core.attributeslib.api.IFormattableAttribute;

@Mixin({PotionUtils.class})
public class PotionUtilsMixin {
   @Redirect(
      method = {"addPotionTooltip(Lnet/minecraft/world/item/ItemStack;Ljava/util/List;F)V"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/List;isEmpty()Z",
         ordinal = 1
      ),
      require = 1
   )
   private static boolean attributeslib_potionTooltips(
      List<Pair<Attribute, AttributeModifier>> list, ItemStack stack, List<Component> tooltips, float durationFactor
   ) {
      if (!list.isEmpty()) {
         tooltips.add(CommonComponents.f_237098_);
         tooltips.add(Component.m_237115_("potion.whenDrank").m_130940_(ChatFormatting.DARK_PURPLE));

         for (Pair<Attribute, AttributeModifier> pair : list) {
            tooltips.add(IFormattableAttribute.toComponent((Attribute)pair.getFirst(), (AttributeModifier)pair.getSecond(), AttributesLib.getTooltipFlag()));
         }
      }

      return true;
   }
}
