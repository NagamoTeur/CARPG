package immersive_armors.forge;

import immersive_armors.ClientMain;
import immersive_armors.cobalt.network.NetworkHandler;
import immersive_armors.network.s2c.SettingsMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "immersive_armors"
)
public class ForgeBusEvents {
   public static boolean firstLoad = true;

   @SubscribeEvent
   public static void onClientStart(ClientTickEvent event) {
      if (firstLoad) {
         ClientMain.postLoad();
         firstLoad = false;
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedInEvent(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         NetworkHandler.sendToPlayer(new SettingsMessage(), player);
      }
   }
}
