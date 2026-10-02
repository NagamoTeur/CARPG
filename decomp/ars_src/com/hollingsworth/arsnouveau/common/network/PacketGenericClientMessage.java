package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.items.curios.JumpingRing;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketGenericClientMessage {
   PacketGenericClientMessage.Action action;

   public PacketGenericClientMessage(PacketGenericClientMessage.Action action) {
      this.action = action;
   }

   public PacketGenericClientMessage(FriendlyByteBuf buf) {
      this.action = PacketGenericClientMessage.Action.valueOf(buf.m_130277_());
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130070_(this.action.name());
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player != null && this.action == PacketGenericClientMessage.Action.JUMP_RING) {
            JumpingRing.doJump(player);
         }
      });
      ctx.get().setPacketHandled(true);
   }

   public static enum Action {
      JUMP_RING;
   }
}
