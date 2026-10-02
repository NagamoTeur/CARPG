package net.cisco.init;

import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class CiscoModModCuriosRenderers {
   @SubscribeEvent
   public static void registerLayers(RegisterLayerDefinitions evt) {
   }

   @SubscribeEvent
   public static void clientSetup(FMLClientSetupEvent evt) {
   }
}
