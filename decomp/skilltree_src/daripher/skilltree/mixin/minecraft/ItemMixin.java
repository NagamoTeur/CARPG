package daripher.skilltree.mixin.minecraft;

import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.skill.bonus.item.FoodEffectBonus;
import daripher.skilltree.skill.bonus.item.FoodSaturationBonus;
import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Item.class})
public abstract class ItemMixin implements IForgeItem {
   @Redirect(
      method = {"getBarWidth"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/Item;getMaxDamage(Lnet/minecraft/world/item/ItemStack;)I"
      )
   )
   private int applyBonusDurability(Item item, ItemStack stack) {
      return stack.m_41776_();
   }

   public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
      FoodProperties properties = super.getFoodProperties(stack, entity);
      if (properties == null) {
         return null;
      } else {
         float restorationMultiplier = 1.0F;
         restorationMultiplier += ItemHelper.getItemBonuses(stack, FoodSaturationBonus.class)
            .stream()
            .map(FoodSaturationBonus::getMultiplier)
            .reduce(Float::sum)
            .orElse(0.0F);
         List<MobEffectInstance> bonusEffects = ItemHelper.getItemBonuses(stack, FoodEffectBonus.class)
            .stream()
            .map(FoodEffectBonus::getEffectInstance)
            .<MobEffectInstance>map(MobEffectInstance::new)
            .toList();
         if (restorationMultiplier == 1.0F && bonusEffects.isEmpty()) {
            return properties;
         } else {
            Builder newProperties = new Builder();
            if (properties.m_38747_()) {
               newProperties.m_38765_();
            }

            if (properties.m_38748_()) {
               newProperties.m_38766_();
            }

            if (properties.m_38746_()) {
               newProperties.m_38757_();
            }

            properties.m_38749_().forEach(pair -> newProperties.effect(pair::getFirst, (Float)pair.getSecond()));
            bonusEffects.forEach(effect -> newProperties.effect(() -> effect, 1.0F));
            newProperties.m_38760_((int)((float)properties.m_38744_() * restorationMultiplier));
            newProperties.m_38758_(properties.m_38745_());
            return newProperties.m_38767_();
         }
      }
   }
}
