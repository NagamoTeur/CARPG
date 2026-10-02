package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.api.capabilities.IPlaytimeCounter;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncPlayTime {
   private UUID playerID;
   private long timeWithCurses;
   private long timeWithoutCurses;

   public PacketSyncPlayTime(UUID playerID, long timeWithCurses, long timeWithoutCurses) {
      this.playerID = playerID;
      this.timeWithCurses = timeWithCurses;
      this.timeWithoutCurses = timeWithoutCurses;
   }

   public static void encode(PacketSyncPlayTime msg, FriendlyByteBuf buf) {
      buf.m_130077_(msg.playerID);
      buf.writeLong(msg.timeWithCurses);
      buf.writeLong(msg.timeWithoutCurses);
   }

   public static PacketSyncPlayTime decode(FriendlyByteBuf buf) {
      return new PacketSyncPlayTime(buf.m_130259_(), buf.readLong(), buf.readLong());
   }

   public static void handle(PacketSyncPlayTime msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> handle(msg.playerID, msg.timeWithCurses, msg.timeWithoutCurses));
      ctx.get().setPacketHandled(true);
   }

   @OnlyIn(Dist.CLIENT)
   private static void handle(UUID playerID, long timeWithCurses, long timeWithoutCurses) {
      Minecraft.m_91087_().f_91073_.m_6907_().stream().filter(player -> player.m_20148_().equals(playerID)).findAny().ifPresent(player -> {
         IPlaytimeCounter counter = IPlaytimeCounter.get(player);
         counter.setTimeWithCurses(timeWithCurses);
         counter.setTimeWithoutCurses(timeWithoutCurses);
      });
   }
}
