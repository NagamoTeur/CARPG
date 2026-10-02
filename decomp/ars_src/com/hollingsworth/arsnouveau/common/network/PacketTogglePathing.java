package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.entity.pathfinding.pathjobs.AbstractPathJob;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketTogglePathing {
   public PacketTogglePathing(FriendlyByteBuf buf) {
   }

   public void toBytes(FriendlyByteBuf buf) {
   }

   public PacketTogglePathing() {
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> AbstractPathJob.DEBUG_DRAW = !AbstractPathJob.DEBUG_DRAW);
      ctx.get().setPacketHandled(true);
   }
}
