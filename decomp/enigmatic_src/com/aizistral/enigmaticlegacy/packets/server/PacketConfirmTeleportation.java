package com.aizistral.enigmaticlegacy.packets.server;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.items.EyeOfNebula;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketConfirmTeleportation {
   private boolean pressed;

   public PacketConfirmTeleportation(boolean pressed) {
      this.pressed = pressed;
   }

   public static void encode(PacketConfirmTeleportation msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.pressed);
   }

   public static PacketConfirmTeleportation decode(FriendlyByteBuf buf) {
      return new PacketConfirmTeleportation(buf.readBoolean());
   }

   public static void handle(PacketConfirmTeleportation msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer playerServ = ctx.get().getSender();
         int counter = 0;

         while (counter <= 32 && !SuperpositionHandler.validTeleportRandomly(playerServ, playerServ.f_19853_, (int)EyeOfNebula.dodgeRange.getValue())) {
            counter++;
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
