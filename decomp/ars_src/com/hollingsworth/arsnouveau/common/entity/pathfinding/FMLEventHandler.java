package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FMLEventHandler {
   @SubscribeEvent
   public static void onServerStopped(ServerStoppingEvent event) {
      Pathfinding.shutdown();
   }
}
