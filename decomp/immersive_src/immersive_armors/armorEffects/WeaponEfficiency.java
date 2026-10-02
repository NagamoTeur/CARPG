package immersive_armors.armorEffects;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WeaponEfficiency extends ArmorEffect {
   private final float damage;
   private final Class weapon;
   private final String weaponName;

   public WeaponEfficiency(float damage, Class weapon, String weaponName) {
      this.damage = damage;
      this.weapon = weapon;
      this.weaponName = weaponName;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
      super.appendTooltip(stack, world, tooltip, context);
      MutableComponent weaponText = Component.m_237115_("armorEffect.weaponEfficiency." + this.weaponName);
      tooltip.add(Component.m_237110_("armorEffect.weaponEfficiency", new Object[]{(int)(this.damage * 100.0F), weaponText}).m_130940_(ChatFormatting.GOLD));
   }

   @Override
   public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
      if (!source.m_19360_() && source.m_7639_() instanceof LivingEntity attacker && this.isPrimaryArmor(armor, attacker)) {
         boolean hasAxe = Stream.of(attacker.m_6844_(EquipmentSlot.MAINHAND), attacker.m_6844_(EquipmentSlot.OFFHAND))
            .filter(Objects::nonNull)
            .anyMatch(v -> this.weapon.isInstance(v.m_41720_()));
         if (hasAxe) {
            amount *= 1.0F + (float)this.getSetCount(armor, attacker) * this.damage;
         }
      }

      return amount;
   }
}
