package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketPermadeath {
   public static void encode(PacketPermadeath msg, FriendlyByteBuf buf) {
   }

   public static PacketPermadeath decode(FriendlyByteBuf buf) {
      return new PacketPermadeath();
   }

   public static void handle(PacketPermadeath msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> EnigmaticLegacy.PROXY.displayPermadeathScreen());
      ctx.get().setPacketHandled(true);
   }
}
