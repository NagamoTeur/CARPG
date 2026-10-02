package com.hollingsworth.arsnouveau.common.network;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSyncTag {
   public CompoundTag tag;
   public int entityId;

   public PacketSyncTag(CompoundTag tag, int entityId) {
      this.tag = tag;
      this.entityId = entityId;
   }

   public static PacketSyncTag decode(FriendlyByteBuf buf) {
      return new PacketSyncTag(buf.m_130260_(), buf.readInt());
   }

   public static void encode(PacketSyncTag msg, FriendlyByteBuf buf) {
      buf.m_130079_(msg.tag);
      buf.writeInt(msg.entityId);
   }

   public static class Handler {
      public static void handle(final PacketSyncTag m, Supplier<Context> ctx) {
         if (ctx.get().getDirection().getReceptionSide().isServer()) {
            ctx.get().setPacketHandled(true);
         } else {
            ctx.get().enqueueWork(new Runnable() {
               @Override
               public void run() {
                  Minecraft mc = Minecraft.m_91087_();
                  ClientLevel world = mc.f_91073_;
                  if (world.m_6815_(m.entityId) instanceof ITagSyncable tagSyncable) {
                     tagSyncable.onTagSync(m.tag);
                  }
               }
            });
            ctx.get().setPacketHandled(true);
         }
      }
   }
}
