package com.rolfmao.upgradednetherite.packets;

import com.rolfmao.upgradednetherite.handlers.EntityFallDistanceUpdateHandler;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketEntityFallDistanceUpdate {
   private final Integer entityId;
   private final Float fallDistance;

   public PacketEntityFallDistanceUpdate(Integer entityId, Float fallDistance) {
      this.entityId = entityId;
      this.fallDistance = fallDistance;
   }

   public static void encode(PacketEntityFallDistanceUpdate msg, FriendlyByteBuf buf) {
      buf.writeInt(msg.entityId);
      buf.writeFloat(msg.fallDistance);
   }

   public static PacketEntityFallDistanceUpdate decode(FriendlyByteBuf buf) {
      Integer entityId = buf.readInt();
      Float fallDistance = buf.readFloat();
      return new PacketEntityFallDistanceUpdate(entityId, fallDistance);
   }

   public static void handle(PacketEntityFallDistanceUpdate msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         Entity entity = player.f_19853_.m_6815_(msg.entityId);
         Float fallDistance = msg.fallDistance;
         EntityFallDistanceUpdateHandler.handleEntityFallDistanceUpdate(entity, fallDistance);
      });
      ctx.get().setPacketHandled(true);
   }
}
