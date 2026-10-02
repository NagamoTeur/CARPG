package com.hollingsworth.arsnouveau.api.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.event.TickEvent;

public interface ITimedEvent {
   default void tickEvent(TickEvent event) {
      this.tick(event.side.isServer());
   }

   void tick(boolean var1);

   boolean isExpired();

   default CompoundTag serialize(CompoundTag tag) {
      if (this.getID().isEmpty()) {
         throw new IllegalStateException("Serialize without ID");
      } else {
         tag.m_128359_("id", this.getID());
         return tag;
      }
   }

   default Void onPacketHandled() {
      EventQueue.getClientQueue().addEvent(this);
      return null;
   }

   default String getID() {
      return "";
   }
}
