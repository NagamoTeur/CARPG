package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketEnchantingGUI {
   public static void encode(PacketEnchantingGUI msg, FriendlyByteBuf buf) {
   }

   public static PacketEnchantingGUI decode(FriendlyByteBuf buf) {
      return new PacketEnchantingGUI();
   }

   public static void handle(PacketEnchantingGUI msg, Supplier<Context> ctx) {
      ctx.get()
         .enqueueWork(() -> SuperpositionHandler.grantAdvancement(ctx.get().getSender(), new ResourceLocation("enigmaticlegacy", "book/enchantments/generic")));
      ctx.get().setPacketHandled(true);
   }
}
