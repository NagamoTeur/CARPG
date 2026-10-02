package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.setup.Config;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketJoinedServer {
   public boolean isSupporter;

   public PacketJoinedServer(boolean isSupporter) {
      this.isSupporter = isSupporter;
   }

   public PacketJoinedServer(FriendlyByteBuf buf) {
      this.isSupporter = buf.readBoolean();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.writeBoolean(this.isSupporter);
   }

   public static class Handler {
      public static void handle(final PacketJoinedServer message, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  ClientInfo.isSupporter = message.isSupporter;
                  if ((Boolean)Config.SHOW_SUPPORTER_MESSAGE.get()) {
                     Minecraft.m_91087_().f_91074_.m_213846_(Component.m_237115_("ars_nouveau.rewards.enabled"));
                     Config.SHOW_SUPPORTER_MESSAGE.set(false);
                     Config.SHOW_SUPPORTER_MESSAGE.save();
                  }
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
