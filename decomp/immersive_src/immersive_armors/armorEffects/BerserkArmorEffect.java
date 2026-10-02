package immersive_armors.armorEffects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BerserkArmorEffect extends ArmorEffect {
   private final float berserk;

   public BerserkArmorEffect(float berserk) {
      this.berserk = berserk;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.berserk", new Object[]{(int)(this.berserk * 100.0F)}).m_130940_(ChatFormatting.RED));
   }

   @Override
   public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
      if (source.m_7639_() instanceof LivingEntity attacker && this.isPrimaryArmor(armor, attacker)) {
         float healthFactor = attacker.m_21223_() / attacker.m_21233_();
         amount = (float)((double)amount * (1.0 + (double)((float)this.getSetCount(armor, attacker) * this.berserk) * (1.0 - (double)healthFactor)));
      }

      return amount;
   }
}
