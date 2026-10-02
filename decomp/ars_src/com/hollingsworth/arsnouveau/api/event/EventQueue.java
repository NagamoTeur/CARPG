package com.hollingsworth.arsnouveau.api.event;

import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class EventQueue {
   List<ITimedEvent> events = new ArrayList<>();
   private static EventQueue serverQueue;
   private static EventQueue clientQueue;

   public void tick(TickEvent tickEvent) {
      if (this.events != null && !this.events.isEmpty()) {
         List<ITimedEvent> stale = new ArrayList<>();

         for (int i = 0; i < this.events.size(); i++) {
            ITimedEvent event = this.events.get(i);
            if (event.isExpired()) {
               stale.add(event);
            } else {
               event.tickEvent(tickEvent);
            }
         }

         this.events.removeAll(stale);
      }
   }

   public void addEvent(ITimedEvent event) {
      if (this.events == null) {
         this.events = new ArrayList<>();
      }

      this.events.add(event);
   }

   public static EventQueue getServerInstance() {
      if (serverQueue == null) {
         serverQueue = new EventQueue();
      }

      return serverQueue;
   }

   public static EventQueue getClientQueue() {
      if (clientQueue == null) {
         clientQueue = new EventQueue();
      }

      return clientQueue;
   }

   public void clear() {
      this.events = null;
   }

   private EventQueue() {
   }

   @SubscribeEvent
   public static void serverTick(ServerTickEvent e) {
      if (e.phase == Phase.END) {
         getServerInstance().tick(e);
      }
   }

   @SubscribeEvent
   public static void clientTickEvent(ClientTickEvent e) {
      if (e.phase == Phase.END) {
         getClientQueue().tick(e);
      }
   }
}
