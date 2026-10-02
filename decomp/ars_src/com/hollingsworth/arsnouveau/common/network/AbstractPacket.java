package com.hollingsworth.arsnouveau.common.network;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public abstract class AbstractPacket {
   public AbstractPacket(FriendlyByteBuf buf) {
   }

   public AbstractPacket() {
   }

   public abstract void toBytes(FriendlyByteBuf var1);

   public abstract void handle(Supplier<Context> var1);
}
