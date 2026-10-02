package immersive_armors.forge;

import immersive_armors.forge.cobalt.registration.RegistrationImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   modid = "immersive_armors",
   value = {Dist.CLIENT},
   bus = Bus.MOD
)
public final class ClientForge {
   @SubscribeEvent
   public static void setup(FMLClientSetupEvent event) {
      RegistrationImpl.bootstrap();
   }
}
