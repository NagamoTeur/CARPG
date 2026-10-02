package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketToggleMagnetEffects {
   public static void encode(PacketToggleMagnetEffects msg, FriendlyByteBuf buf) {
   }

   public static PacketToggleMagnetEffects decode(FriendlyByteBuf buf) {
      return new PacketToggleMagnetEffects();
   }

   public static void handle(PacketToggleMagnetEffects msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer playerServ = ctx.get().getSender();
         TransientPlayerData data = TransientPlayerData.get(playerServ);
         data.setDisabledMagnetRingEffects(!data.getDisabledMagnetRingEffects());
         data.syncToAllClients();
      });
      ctx.get().setPacketHandled(true);
   }
}
