package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.api.event.ChimeraSummonEvent;
import com.hollingsworth.arsnouveau.api.event.ITimedEvent;
import com.hollingsworth.arsnouveau.common.event.timed.EruptionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketTimedEvent {
   CompoundTag tag;
   public static Map<String, Function<CompoundTag, Void>> methodMap = new HashMap<>();

   public PacketTimedEvent(FriendlyByteBuf buf) {
      this.tag = buf.m_130260_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130079_(this.tag);
   }

   public PacketTimedEvent(CompoundTag tag) {
      this.tag = tag;
   }

   public PacketTimedEvent(ITimedEvent event) {
      this.tag = new CompoundTag();
      event.serialize(this.tag);
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         if (!methodMap.containsKey(this.tag.m_128461_("id"))) {
            throw new IllegalStateException("No event found for ID or ID missing");
         } else {
            methodMap.get(this.tag.m_128461_("id")).apply(this.tag);
         }
      });
      ctx.get().setPacketHandled(true);
   }

   static {
      methodMap.put("chimera", nbt -> ChimeraSummonEvent.get(nbt).onPacketHandled());
      methodMap.put("eruption", nbt -> EruptionEvent.get(nbt).onPacketHandled());
   }
}
