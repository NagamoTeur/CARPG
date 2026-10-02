package com.hollingsworth.arsnouveau.common.mixin.elytra;

import com.hollingsworth.arsnouveau.common.spell.effect.EffectGlide;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shadowed.llamalad7.mixinextras.injector.ModifyExpressionValue;

@Mixin({LocalPlayer.class})
public class ClientElytraMixin {
   @ModifyExpressionValue(
      method = {"aiStep"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;canElytraFly(Lnet/minecraft/world/entity/LivingEntity;)Z",
         remap = false
      )}
   )
   public boolean elytraOverride(boolean original) {
      return original || EffectGlide.canGlide((LivingEntity)this);
   }
}
