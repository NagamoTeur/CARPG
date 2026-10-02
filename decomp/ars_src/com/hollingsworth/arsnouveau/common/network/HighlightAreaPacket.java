package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class HighlightAreaPacket {
   public List<ColorPos> colorPos;
   public int ticks;

   public HighlightAreaPacket(List<ColorPos> colorPos, int ticks) {
      this.colorPos = colorPos;
      this.ticks = ticks;
   }

   public static HighlightAreaPacket decode(FriendlyByteBuf buf) {
      HighlightAreaPacket packet = new HighlightAreaPacket(new ArrayList<>(), 0);
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         packet.colorPos.add(ColorPos.fromTag(buf.m_130260_()));
      }

      packet.ticks = buf.readInt();
      return packet;
   }

   public static void encode(HighlightAreaPacket msg, FriendlyByteBuf buf) {
      buf.writeInt(msg.colorPos.size());

      for (ColorPos pos : msg.colorPos) {
         buf.m_130079_(pos.toTag());
      }

      buf.writeInt(msg.ticks);
   }

   public static class Handler {
      public static void handle(final HighlightAreaPacket m, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  ClientInfo.highlightPosition(m.colorPos, m.ticks);
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
