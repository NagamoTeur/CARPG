package daripher.skilltree.mixin.apotheosis;

import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.EnchantmentBonus;

@Mixin(
   value = {EnchantmentBonus.class},
   remap = false
)
public class EnchantmentBonusMixin {
   @Redirect(
      method = {"getSocketBonusTooltip"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
      )
   )
   private Object amplifyGemPowerVisually(Map<?, ?> values, Object rarity, ItemStack gem) {
      return this.getGemPower(values, rarity, gem);
   }

   @Redirect(
      method = {"getEnchantmentLevels"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
      )
   )
   private Object amplifyGemPower(Map<?, ?> values, Object rarity, ItemStack gem) {
      return this.getGemPower(values, rarity, gem);
   }

   private int getGemPower(Map<?, ?> values, Object rarity, ItemStack gem) {
      int level = (Integer)values.get(rarity);
      CompoundTag tag = gem.m_41784_();
      return !tag.m_128441_("gem_power") ? level : (int)((float)level * tag.m_128457_("gem_power"));
   }
}
