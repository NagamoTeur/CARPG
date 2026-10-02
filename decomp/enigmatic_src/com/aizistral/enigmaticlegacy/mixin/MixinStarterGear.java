package com.aizistral.enigmaticlegacy.mixin;

import com.aizistral.enigmaticlegacy.handlers.EnigmaticEventHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(
   targets = {"com.brandon3055.csg.ModEventHandler"},
   remap = false
)
public class MixinStarterGear {
   @Inject(
      method = {"playerLogin"},
      at = {@At("RETURN")},
      cancellable = false,
      require = 1
   )
   public void onCSGHandling(PlayerLoggedInEvent event, CallbackInfo info) {
      if (event.getEntity() instanceof ServerPlayer player) {
         EnigmaticEventHandler.grantStarterGear(player);
      }
   }
}
