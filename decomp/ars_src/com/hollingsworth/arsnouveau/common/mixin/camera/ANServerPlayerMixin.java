package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import shadowed.llamalad7.mixinextras.injector.WrapWithCondition;

@Mixin(
   value = {ServerPlayer.class},
   priority = 1000
)
public class ANServerPlayerMixin {
   @WrapWithCondition(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/level/ServerPlayer;absMoveTo(DDDFF)V"
      )}
   )
   private boolean shouldMove(ServerPlayer player, double x, double y, double z, float yaw, float pitch) {
      return !CameraUtil.isPlayerMountedOnCamera(player);
   }
}
