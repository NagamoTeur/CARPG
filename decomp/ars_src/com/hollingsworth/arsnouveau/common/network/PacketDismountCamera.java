package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.entity.ScryerCamera;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketDismountCamera {
   public static void encode(PacketDismountCamera message, FriendlyByteBuf buf) {
   }

   public static PacketDismountCamera decode(FriendlyByteBuf buf) {
      return new PacketDismountCamera();
   }

   public static void onMessage(PacketDismountCamera message, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player.m_8954_() instanceof ScryerCamera cam) {
            cam.stopViewing(player);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
