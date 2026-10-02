package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.items.Insignia;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemStack.class})
public class MixinItemStack {
   @Inject(
      method = {"getHoverName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetHoverName(CallbackInfoReturnable<Component> info) {
      ItemStack stack = (ItemStack)this;
      if (stack.m_41720_() instanceof Insignia) {
         info.setReturnValue(stack.m_41720_().m_7626_(stack));
      }
   }

   @Inject(
      method = {"hasCustomHoverName"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onHasCustomHoverName(CallbackInfoReturnable<Boolean> info) {
      ItemStack stack = (ItemStack)this;
      if (stack.m_41720_() instanceof Insignia) {
         info.setReturnValue(false);
      }
   }
}
