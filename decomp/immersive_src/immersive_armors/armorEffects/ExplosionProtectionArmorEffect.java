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

public class ExplosionProtectionArmorEffect extends ArmorEffect {
   private final float strength;

   public ExplosionProtectionArmorEffect(float strength) {
      this.strength = strength;
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      return source.m_19372_() ? amount * (1.0F - this.strength) : amount;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.explosionResistance", new Object[]{(int)(this.strength * 100.0F)}).m_130940_(ChatFormatting.DARK_GREEN));
   }
}
