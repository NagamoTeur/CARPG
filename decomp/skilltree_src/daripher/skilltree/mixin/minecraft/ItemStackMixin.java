package daripher.skilltree.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.skill.bonus.item.ItemDurabilityBonus;
import java.util.List;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({ItemStack.class})
public abstract class ItemStackMixin implements IForgeItemStack {
   @ModifyReturnValue(
      method = {"getMaxDamage"},
      at = {@At("RETURN")}
   )
   private int applyDurabilityModifiers(int original) {
      List<ItemDurabilityBonus> durabilityBonuses = ItemHelper.getDurabilityBonuses((ItemStack)this);
      original = (int)((float)original + getDurabilityBonus(durabilityBonuses, Operation.ADDITION));
      original = (int)((float)original * getDurabilityBonus(durabilityBonuses, Operation.MULTIPLY_BASE));
      return (int)((float)original * getDurabilityBonus(durabilityBonuses, Operation.MULTIPLY_TOTAL));
   }

   private static float getDurabilityBonus(List<ItemDurabilityBonus> durabilityBonuses, Operation operation) {
      float bonus = operation == Operation.ADDITION ? 0.0F : 1.0F;
      return bonus + durabilityBonuses.stream().filter(b -> b.getOperation() == operation).map(ItemDurabilityBonus::getAmount).reduce(Float::sum).orElse(0.0F);
   }
}
