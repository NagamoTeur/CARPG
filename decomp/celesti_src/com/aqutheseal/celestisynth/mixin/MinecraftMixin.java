package com.aqutheseal.celestisynth.mixin;

import com.aqutheseal.celestisynth.api.item.CSWeapon;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Minecraft.class})
public class MinecraftMixin {
   @Shadow
   @Nullable
   public LocalPlayer f_91074_;

   private MinecraftMixin() {
      throw new IllegalAccessError("Attempted to instantiate a Mixin Class!");
   }

   @Inject(
      method = {"handleKeybinds"},
      at = {@At(
         value = "FIELD",
         target = "Lnet/minecraft/world/entity/player/Inventory;selected:I",
         shift = Shift.BEFORE
      )},
      cancellable = true
   )
   private void celestisynth$handleKeybinds(CallbackInfo info) {
      if (this.f_91074_.m_150109_().m_36056_().m_41720_() instanceof CSWeapon
         && this.f_91074_.m_150109_().m_36056_().m_41737_("csController") != null
         && this.f_91074_.m_150109_().m_36056_().m_41737_("csController").m_128471_("cs.hasAnimationBegun")) {
         info.cancel();
      }
   }
}
