package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.handlers.EnigmaticUpdateHandler;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUpdateNotification {
   public static void encode(PacketUpdateNotification msg, FriendlyByteBuf buf) {
   }

   public static PacketUpdateNotification decode(FriendlyByteBuf buf) {
      return new PacketUpdateNotification();
   }

   public static void handle(PacketUpdateNotification msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         LocalPlayer player = Minecraft.m_91087_().f_91074_;
         EnigmaticUpdateHandler.handleShowup(player);
      });
      ctx.get().setPacketHandled(true);
   }
}
