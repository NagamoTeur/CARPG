package com.rolfmao.upgradednetherite.handlers;

import com.rolfmao.upgradednetherite.UpgradedNetheriteMod;
import com.rolfmao.upgradednetherite.packets.PacketPlayerFallDistanceUpdate;
import java.util.UUID;
import net.minecraft.server.level.ServerPlayer;

public class PlayerFallDistanceUpdateHandler {
   public static void PlayerFallDistanceUpdate(UUID player, Float fallDistance) {
      UpgradedNetheriteMod.packetInstance.sendToServer(new PacketPlayerFallDistanceUpdate(player, fallDistance));
   }

   public static void handlePlayerFallDistanceUpdate(ServerPlayer player, Float fallDistance) {
      player.f_19789_ = fallDistance;
   }
}
