package com.github.L_Ender.cataclysm.message;

import com.github.L_Ender.cataclysm.client.sound.BossMusicPlayer;
import com.github.L_Ender.cataclysm.entity.etc.Animation_Monsters;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class MessageMusic {
   private final int entityID;
   private final boolean play;

   public MessageMusic(int entityID, boolean play) {
      this.entityID = entityID;
      this.play = play;
   }

   public static MessageMusic read(FriendlyByteBuf buf) {
      return new MessageMusic(buf.readInt(), buf.readBoolean());
   }

   public static void write(MessageMusic message, FriendlyByteBuf buf) {
      buf.writeInt(message.entityID);
      buf.writeBoolean(message.play);
   }

   public static class Handler {
      public static boolean onMessage(MessageMusic message, Supplier<Context> ctx) {
         ctx.get().enqueueWork(() -> {
            if (Minecraft.m_91087_().f_91073_.m_6815_(message.entityID) instanceof Animation_Monsters am) {
               if (message.play) {
                  BossMusicPlayer.playBossMusic(am);
               } else {
                  BossMusicPlayer.stopBossMusic(am);
               }
            }
         });
         ctx.get().setPacketHandled(true);
         return true;
      }
   }
}
