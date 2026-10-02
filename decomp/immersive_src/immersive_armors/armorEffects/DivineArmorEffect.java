package immersive_armors.armorEffects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DivineArmorEffect extends ArmorEffect {
   private final long cooldown;

   public DivineArmorEffect(long cooldown) {
      this.cooldown = cooldown;
   }

   private boolean isCharged(long time, ItemStack armor) {
      CompoundTag tag = armor.m_41784_();
      return (!tag.m_128441_("last_divine") || tag.m_128454_("last_divine") + this.cooldown < time) && this.getSetCount(armor) == 4;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237115_("item.immersive_armors.divine.description").m_130940_(ChatFormatting.GRAY));
      int count = this.getSetCount(stack);
      if (count == 4) {
         if (world != null && this.isCharged(world.m_46467_(), stack)) {
            tooltip.add(Component.m_237115_("armorEffect.charged").m_130940_(ChatFormatting.AQUA));
         }
      } else {
         tooltip.add(Component.m_237110_("immersive_armors.incomplete", new Object[]{count, 4}));
      }
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      if (this.isPrimaryArmor(armor, entity)) {
         long time = entity.f_19853_.m_46467_();
         boolean charged = this.getMatchingEquippedArmor(entity, armor).anyMatch(a -> this.isCharged(time, a));
         if (charged) {
            entity.f_19853_.m_6263_(null, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), SoundEvents.f_11668_, entity.m_5720_(), 0.5F, 1.25F);
            this.getMatchingEquippedArmor(entity, armor).forEach(a -> a.m_41784_().m_128356_("last_divine", time));
            return 0.0F;
         }
      }

      return amount;
   }
}
