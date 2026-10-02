package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.entity.Lily;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketUnsummonLily {
   public PacketUnsummonLily() {
   }

   public PacketUnsummonLily(FriendlyByteBuf buf) {
   }

   public void toBytes(FriendlyByteBuf buf) {
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ctx.get().getSender() != null) {
            ServerPlayer serverPlayer = ctx.get().getSender();
            ServerLevel level = serverPlayer.m_9236_();
            UUID lilyUuid = (UUID)Lily.ownerLilyMap.get(ctx.get().getSender().m_20148_());
            if (lilyUuid != null) {
               Lily lily = (Lily)level.m_8791_(lilyUuid);
               if (lily != null) {
                  lily.m_142687_(RemovalReason.DISCARDED);
               }
            }
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
