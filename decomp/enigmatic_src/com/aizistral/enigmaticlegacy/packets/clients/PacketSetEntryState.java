package com.aizistral.enigmaticlegacy.packets.clients;

import com.aizistral.enigmaticlegacy.helpers.PatchouliHelper;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSetEntryState {
   private boolean isRead;
   private String entryId;

   public PacketSetEntryState(boolean read, String id) {
      this.isRead = read;
      this.entryId = id;
   }

   public static void encode(PacketSetEntryState msg, FriendlyByteBuf buf) {
      buf.writeBoolean(msg.isRead);
      buf.m_130070_(msg.entryId);
   }

   public static PacketSetEntryState decode(FriendlyByteBuf buf) {
      return new PacketSetEntryState(buf.readBoolean(), buf.m_130277_());
   }

   public static void handle(PacketSetEntryState msg, Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (msg.isRead) {
            PatchouliHelper.markEntryRead(new ResourceLocation(msg.entryId));
         } else {
            PatchouliHelper.markEntryUnread(new ResourceLocation(msg.entryId));
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
