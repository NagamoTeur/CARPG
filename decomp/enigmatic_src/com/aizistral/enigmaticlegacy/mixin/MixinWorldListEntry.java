package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList.WorldListEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WorldListEntry.class})
public class MixinWorldListEntry {
   @Shadow
   @Final
   LevelSummary f_101695_;
   private Component cachedInfo;

   @Redirect(
      method = {"render"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/level/storage/LevelSummary;getInfo()Lnet/minecraft/network/chat/Component;"
      )
   )
   private Component getAltInfo(LevelSummary summary) {
      if (this.cachedInfo == null) {
         this.cachedInfo = SuperpositionHandler.getAltInfo(summary);
      }

      return this.cachedInfo;
   }

   @Inject(
      method = {"joinWorld"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onJoinWorld(CallbackInfo info) {
      if (SuperpositionHandler.isWorldFractured(SuperpositionHandler.getSaveFolder(this.f_101695_))) {
         EnigmaticLegacy.PROXY.displayPermadeathScreen();
         info.cancel();
      }
   }
}
