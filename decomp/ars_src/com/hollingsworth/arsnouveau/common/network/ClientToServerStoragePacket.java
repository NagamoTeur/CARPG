package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.container.StorageTerminalMenu;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;

public class ClientToServerStoragePacket {
   public CompoundTag tag;

   public ClientToServerStoragePacket(CompoundTag tag) {
      this.tag = tag;
   }

   public ClientToServerStoragePacket(FriendlyByteBuf pb) {
      this.tag = pb.m_130261_();
   }

   public void toBytes(FriendlyByteBuf pb) {
      pb.m_130079_(this.tag);
   }

   public static class Handler {
      public static boolean onMessage(ClientToServerStoragePacket message, Supplier<Context> ctx) {
         if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            ctx.get().enqueueWork(() -> {
               ServerPlayer sender = ctx.get().getSender();
               if (sender.f_36096_ instanceof StorageTerminalMenu terminalScreen) {
                  terminalScreen.receive(message.tag);
               }
            });
         }

         ctx.get().setPacketHandled(true);
         return true;
      }
   }
}
