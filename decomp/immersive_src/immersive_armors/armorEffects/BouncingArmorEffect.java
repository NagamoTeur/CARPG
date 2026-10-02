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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BouncingArmorEffect extends ArmorEffect {
   private final float strength;

   public BouncingArmorEffect(float strength) {
      this.strength = strength;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.bounceback", new Object[]{(int)(this.strength * 100.0F)}).m_130940_(ChatFormatting.GREEN));
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      Entity attacker = source.m_7639_();
      if (attacker != null && !source.m_19360_()) {
         Vec3 direction = attacker.m_20182_().m_82546_(entity.m_20182_()).m_82541_().m_82490_((double)this.strength);
         Vec3 velocity = attacker.m_20184_();
         attacker.m_20256_(velocity.m_82549_(direction));
      }

      return amount;
   }
}
