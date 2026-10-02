package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketGetPersistentData {
   public CompoundTag tag;

   public PacketGetPersistentData(FriendlyByteBuf buf) {
      this.tag = buf.m_130260_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130079_(this.tag);
   }

   public PacketGetPersistentData(CompoundTag tag) {
      this.tag = tag;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> ClientInfo.persistentData = this.tag);
      ctx.get().setPacketHandled(true);
   }
}
