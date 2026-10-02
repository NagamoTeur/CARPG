package immersive_armors.armorEffects;

import immersive_armors.item.ExtendedArmorItem;
import immersive_armors.item.ExtendedArmorMaterial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class ArmorEffect {
   private final List<EquipmentSlot> armorEquipmentSlots = Arrays.asList(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

   public float applyArmorToDamage(LivingEntity entity, DamageSource source, float amount, ItemStack armor) {
      return amount;
   }

   public float applyArmorToAttack(LivingEntity target, DamageSource source, float amount, ItemStack armor) {
      return amount;
   }

   protected Stream<ItemStack> getMatchingEquippedArmor(LivingEntity entity, ExtendedArmorMaterial material) {
      return this.armorEquipmentSlots
         .stream()
         .<ItemStack>map(entity::m_6844_)
         .filter(Objects::nonNull)
         .filter(stack -> stack.m_41720_() instanceof ExtendedArmorItem && ((ExtendedArmorItem)stack.m_41720_()).getMaterial() == material);
   }

   protected Stream<ItemStack> getMatchingEquippedArmor(LivingEntity entity, ItemStack stack) {
      return this.getMatchingEquippedArmor(entity, ((ExtendedArmorItem)stack.m_41720_()).getMaterial());
   }

   protected boolean isPrimaryArmor(ItemStack stack, LivingEntity entity) {
      return stack == this.getMatchingEquippedArmor(entity, stack).findFirst().orElse(null);
   }

   protected int getSetCount(ItemStack stack, LivingEntity entity) {
      return (int)this.getMatchingEquippedArmor(entity, stack).count();
   }

   protected int getSetCount(ItemStack stack) {
      return stack.m_41784_().m_128451_("set_bonus");
   }

   public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
   }

   public void equippedTick(ItemStack stack, Level world, LivingEntity entity, int slot) {
      if (world.m_46467_() % 20L == 0L) {
         stack.m_41784_().m_128405_("set_bonus", this.getSetCount(stack, entity));
      }
   }

   public void receiveCommand(ItemStack armor, Level world, LivingEntity entity, int slot, String command) {
   }
}
