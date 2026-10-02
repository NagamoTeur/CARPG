package com.aizistral.enigmaticlegacy.packets.clients;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;
import vazkii.patchouli.client.base.PersistentData;

public class PacketPatchouliForce {
   public static void encode(PacketPatchouliForce msg, FriendlyByteBuf buf) {
   }

   public static PacketPatchouliForce decode(FriendlyByteBuf buf) {
      return new PacketPatchouliForce();
   }

   public static void handle(PacketPatchouliForce msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> PersistentData.data.bookGuiScale = 4);
      ctx.get().setPacketHandled(true);
   }
}
