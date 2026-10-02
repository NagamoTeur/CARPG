package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.registries.EnigmaticEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({CrossbowItem.class})
public class MixinCrossbowItem {
   @Inject(
      method = {"getArrow"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void onGetArrow(Level level, LivingEntity shooter, ItemStack crossbowStack, ItemStack ammoStack, CallbackInfoReturnable<AbstractArrow> info) {
      AbstractArrow arrow = (AbstractArrow)info.getReturnValue();
      int sharpshooter = crossbowStack.getEnchantmentLevel(EnigmaticEnchantments.SHARPSHOOTER);
      arrow.m_36781_(arrow.m_36789_() + 0.8 * (double)sharpshooter);
      if (crossbowStack.getEnchantmentLevel(EnigmaticEnchantments.CEASELESS) > 0) {
         arrow.f_36705_ = Pickup.CREATIVE_ONLY;
      }
   }
}
