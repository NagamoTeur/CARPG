package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.gui.containers.LoreInscriberContainer;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketInkwellField {
   private String field;

   public PacketInkwellField(String field) {
      this.field = field;
   }

   public static void encode(PacketInkwellField msg, FriendlyByteBuf buf) {
      buf.m_130070_(msg.field);
   }

   public static PacketInkwellField decode(FriendlyByteBuf buf) {
      return new PacketInkwellField(buf.m_130136_(128));
   }

   public static void handle(PacketInkwellField msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer playerServ = ctx.get().getSender();
         if (playerServ.f_36096_ instanceof LoreInscriberContainer container) {
            container.updateItemName(msg.field);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
