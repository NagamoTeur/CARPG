package immersive_armors.armorEffects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SpikesArmorEffect extends ArmorEffect {
   private final int strength;

   public SpikesArmorEffect(int strength) {
      this.strength = strength;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.spikes", new Object[]{this.strength}).m_130940_(ChatFormatting.RED));
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      if (this.isPrimaryArmor(armor, entity) && !source.m_19360_()) {
         Entity attacker = source.m_7639_();
         if (attacker != null) {
            attacker.m_6469_(DamageSource.m_19335_(entity), (float)(this.strength * this.getSetCount(armor)));
         }
      }

      return amount;
   }
}
