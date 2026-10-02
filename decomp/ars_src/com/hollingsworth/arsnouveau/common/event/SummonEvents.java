package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.api.event.SummonEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class SummonEvents {
   @SubscribeEvent
   public static void summonedEvent(SummonEvent event) {
   }

   @SubscribeEvent
   public static void summonDeathEvent(SummonEvent.Death event) {
   }
}
