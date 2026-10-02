package immersive_armors.armorEffects;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FireInflictingArmorEffect extends ArmorEffect {
   private final int length;

   public FireInflictingArmorEffect(int length) {
      this.length = length;
   }

   @Override
   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      if (this.isPrimaryArmor(armor, entity) && source.m_7639_() != null && !source.m_7639_().m_5825_()) {
         source.m_7639_().m_7311_(source.m_7639_().m_20094_() + this.length * this.getSetCount(armor, entity));
         entity.f_19853_.m_6269_(null, entity, SoundEvents.f_11702_, entity.m_5720_(), 1.0F, entity.m_217043_().m_188501_() * 0.7F + 0.3F);
      }

      return amount;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      tooltip.add(Component.m_237110_("armorEffect.fireInflicting", new Object[]{this.length}).m_130940_(ChatFormatting.RED));
   }

   @Override
   public void equippedTick(ItemStack stack, Level world, LivingEntity entity, int slot) {
      if (world.f_46443_
         && Minecraft.m_91087_().f_91074_ == entity
         && !Minecraft.m_91087_().f_91066_.m_92176_().m_90612_()
         && entity.m_217043_().m_188503_(15) == 0) {
         world.m_7106_(ParticleTypes.f_123744_, entity.m_20208_(0.5), entity.m_20187_(), entity.m_20262_(0.5), 0.0, 0.0, 0.0);
      }
   }
}
