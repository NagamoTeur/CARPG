package immersive_armors.armorEffects;

import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WitherArmorEffect extends ArmorEffect {
   private final float immunity;
   private final int wither;

   public WitherArmorEffect(float immunity, int wither) {
      this.immunity = immunity;
      this.wither = wither;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.wither", new Object[]{this.wither}).m_130940_(ChatFormatting.GRAY));
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      if (this.isPrimaryArmor(armor, entity) && source.m_7639_() instanceof LivingEntity attacker && !attacker.m_5825_()) {
         attacker.m_7292_(new MobEffectInstance(MobEffects.f_19615_, this.wither * this.getSetCount(armor, entity)));
      }

      return Objects.equals(source.f_19326_, "wither") ? amount * (1.0F - this.immunity) : amount;
   }
}
