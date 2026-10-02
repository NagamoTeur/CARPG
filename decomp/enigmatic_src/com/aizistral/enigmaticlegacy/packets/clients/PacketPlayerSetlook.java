package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketPlayerSetlook {
   private double x;
   private double y;
   private double z;

   public PacketPlayerSetlook(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public static void encode(PacketPlayerSetlook msg, FriendlyByteBuf buf) {
      buf.writeDouble(msg.x);
      buf.writeDouble(msg.y);
      buf.writeDouble(msg.z);
   }

   public static PacketPlayerSetlook decode(FriendlyByteBuf buf) {
      return new PacketPlayerSetlook(buf.readDouble(), buf.readDouble(), buf.readDouble());
   }

   public static void handle(PacketPlayerSetlook msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         LocalPlayer player = Minecraft.m_91087_().f_91074_;
         SuperpositionHandler.lookAt(msg.x, msg.y, msg.z, player);
      });
      ctx.get().setPacketHandled(true);
   }
}
