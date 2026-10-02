package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.entity.Lily;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSummonLily {
   public PacketSummonLily() {
   }

   public PacketSummonLily(FriendlyByteBuf buf) {
   }

   public void toBytes(FriendlyByteBuf buf) {
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (ctx.get().getSender() != null) {
            ServerPlayer player = ctx.get().getSender();
            Lily lily = new Lily(player.f_19853_);
            lily.m_6034_(player.m_20185_(), player.m_20186_(), player.m_20189_());
            lily.m_21816_(player.m_20148_());
            player.f_19853_.m_7967_(lily);
            Lily.ownerLilyMap.put(player.m_20148_(), lily.m_20148_());
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
