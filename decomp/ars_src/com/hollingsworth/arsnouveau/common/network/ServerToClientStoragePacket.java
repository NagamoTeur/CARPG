package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.container.AbstractStorageTerminalScreen;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent.Context;

public class ServerToClientStoragePacket {
   public CompoundTag tag;

   public ServerToClientStoragePacket(CompoundTag tag) {
      this.tag = tag;
   }

   public ServerToClientStoragePacket(FriendlyByteBuf pb) {
      this.tag = pb.m_130261_();
   }

   public void toBytes(FriendlyByteBuf pb) {
      pb.m_130079_(this.tag);
   }

   public static class Handler {
      public static boolean onMessage(ServerToClientStoragePacket message, Supplier<Context> ctx) {
         if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
               if (Minecraft.m_91087_().f_91080_ instanceof AbstractStorageTerminalScreen<?> terminalScreen) {
                  terminalScreen.receive(message.tag);
               }
            });
         }

         ctx.get().setPacketHandled(true);
         return true;
      }
   }
}
