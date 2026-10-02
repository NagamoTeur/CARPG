package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.objects.TransientPlayerData;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncTransientData {
   private TransientPlayerData playerData;

   public PacketSyncTransientData(TransientPlayerData data) {
      this.playerData = data;
   }

   public static void encode(PacketSyncTransientData msg, FriendlyByteBuf buf) {
      TransientPlayerData.encode(msg.playerData, buf);
   }

   public static PacketSyncTransientData decode(FriendlyByteBuf buf) {
      TransientPlayerData data = TransientPlayerData.decode(buf);
      return new PacketSyncTransientData(data);
   }

   public static void handle(PacketSyncTransientData msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (msg.playerData != null && msg.playerData.getPlayer().f_19853_.f_46443_) {
            TransientPlayerData.set(msg.playerData.getPlayer(), msg.playerData);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
