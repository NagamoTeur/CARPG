package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.event.ReactiveEvents;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketReactiveSpell {
   public PacketReactiveSpell() {
   }

   public PacketReactiveSpell(FriendlyByteBuf buf) {
   }

   public void toBytes(FriendlyByteBuf buf) {
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer serverPlayerEntity = ctx.get().getSender();
         if (serverPlayerEntity != null) {
            ItemStack stack = serverPlayerEntity.m_21205_();
            ReactiveEvents.castSpell(ctx.get().getSender(), stack);
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
