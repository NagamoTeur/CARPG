package com.aizistral.enigmaticlegacy.packets.server;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketAnvilField {
   private String field;

   public PacketAnvilField(String field) {
      this.field = field;
   }

   public static void encode(PacketAnvilField msg, FriendlyByteBuf buf) {
      buf.m_130070_(msg.field);
   }

   public static PacketAnvilField decode(FriendlyByteBuf buf) {
      return new PacketAnvilField(buf.m_130136_(128));
   }

   public static void handle(PacketAnvilField msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer playerServ = ctx.get().getSender();
      });
      ctx.get().setPacketHandled(true);
   }
}
