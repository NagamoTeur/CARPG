package immersive_armors.armorEffects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ArrowBlockArmorEffect extends ArmorEffect {
   private final float chance;

   public ArrowBlockArmorEffect(float chance) {
      this.chance = chance;
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      if (source.m_19360_()
         && this.isPrimaryArmor(armor, entity)
         && entity.f_19853_.f_46441_.m_188501_() < (float)this.getSetCount(armor, entity) * this.chance) {
         entity.f_19853_.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_12346_, entity.m_5720_(), 0.5F, 1.25F);
         return 0.0F;
      } else {
         return amount;
      }
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.arrowBlock", new Object[]{(int)(this.chance * 100.0F)}).m_130940_(ChatFormatting.GOLD));
   }
}
