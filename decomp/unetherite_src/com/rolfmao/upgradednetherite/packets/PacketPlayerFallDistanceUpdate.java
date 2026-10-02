package com.rolfmao.upgradednetherite.packets;

import com.rolfmao.upgradednetherite.handlers.PlayerFallDistanceUpdateHandler;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketPlayerFallDistanceUpdate {
   private final UUID player;
   private final Float fallDistance;

   public PacketPlayerFallDistanceUpdate(UUID player, Float fallDistance) {
      this.player = player;
      this.fallDistance = fallDistance;
   }

   public static void encode(PacketPlayerFallDistanceUpdate msg, FriendlyByteBuf buf) {
      buf.m_130077_(msg.player);
      buf.writeFloat(msg.fallDistance);
   }

   public static PacketPlayerFallDistanceUpdate decode(FriendlyByteBuf buf) {
      UUID player = buf.m_130259_();
      Float fallDistance = buf.readFloat();
      return new PacketPlayerFallDistanceUpdate(player, fallDistance);
   }

   public static void handle(PacketPlayerFallDistanceUpdate msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         Float fallDistance = msg.fallDistance;
         PlayerFallDistanceUpdateHandler.handlePlayerFallDistanceUpdate(player, fallDistance);
      });
      ctx.get().setPacketHandled(true);
   }
}
