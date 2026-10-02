package com.hollingsworth.arsnouveau.common.mixin.camera;

import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import com.hollingsworth.arsnouveau.common.util.CameraUtil;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(
   value = {PlayerList.class},
   priority = 1100
)
public class PlayerListMixin {
   @Inject(
      method = {"broadcast"},
      at = {@At(
         value = "INVOKE_ASSIGN",
         target = "Lnet/minecraft/server/level/ServerPlayer;getZ()D"
      )},
      locals = LocalCapture.CAPTURE_FAILSOFT,
      cancellable = true
   )
   private void broadcastToCameras(
      Player except,
      double x,
      double y,
      double z,
      double radius,
      ResourceKey<Level> dimension,
      Packet<?> packet,
      CallbackInfo callback,
      int iteration,
      ServerPlayer player
   ) {
      if (CameraUtil.isPlayerMountedOnCamera(player)) {
         ScryerCamera camera = (ScryerCamera)player.m_8954_();
         double dX = x - camera.m_20185_();
         double dY = y - camera.m_20186_();
         double dZ = z - camera.m_20189_();
         if (dX * dX + dY * dY + dZ * dZ < radius * radius) {
            player.f_8906_.m_9829_(packet);
         }

         callback.cancel();
      }
   }
}
