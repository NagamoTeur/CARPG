package com.rolfmao.upgradednetherite_items.handlers;

import com.rolfmao.upgradednetherite_items.UpgradedNetherite_ItemsMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;

public class PacketHandler {
   public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
      UpgradedNetherite_ItemsMod.packetInstance.sendTo(message, player.f_8906_.m_6198_(), NetworkDirection.PLAY_TO_CLIENT);
   }

   public static <MSG> void sendToAllTracking(MSG message, ServerPlayer entity) {
      UpgradedNetherite_ItemsMod.packetInstance.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
   }
}
