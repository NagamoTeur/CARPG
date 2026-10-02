package shadows.apotheosis.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.placebo.util.EnchantmentUtils;

@Mixin({AnvilMenu.class})
public class AnvilMenuMixin {
   @ModifyConstant(
      method = {"createResult()V"},
      constant = {@Constant(
         intValue = 40
      )}
   )
   public int apoth_removeLevelCap(int old) {
      return Integer.MAX_VALUE;
   }

   @Redirect(
      method = {"onTake"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V"
      )
   )
   public void apoth_chargeOptimalLevels(Player player, int level) {
      EnchantmentUtils.chargeExperience(player, EnchantmentUtils.getTotalExperienceForLevel(-level));
   }
}
