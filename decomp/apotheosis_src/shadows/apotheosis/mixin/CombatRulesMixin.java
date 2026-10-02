package shadows.apotheosis.mixin;

import net.minecraft.world.damagesource.CombatRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import shadows.apotheosis.adventure.AdventureModule;
import shadows.apotheosis.core.attributeslib.asm.ALCombatRules;

@Mixin({CombatRules.class})
public class CombatRulesMixin {
   @Overwrite
   public static float m_19269_(float damage, float protPoints) {
      return damage * ALCombatRules.getProtDamageReduction(protPoints);
   }

   @Overwrite
   public static float m_19272_(float damage, float armor, float toughness) {
      AdventureModule.LOGGER.trace("Invocation of CombatRules#getDamageAfterAbsorb is bypassing armor pen.");
      return damage * ALCombatRules.getArmorDamageReduction(damage, armor);
   }
}
