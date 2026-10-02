package shadows.apotheosis.adventure.client;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.net.RadialStateChangeMessage;

public class AdventureKeys {
   public static final KeyMapping TOGGLE_RADIAL = new KeyMapping(
      "key.apotheosis.toggle_radial_mining", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Type.KEYSYM, 79, "key.categories.apotheosis"
   );

   @SubscribeEvent
   public static void registerKeys(RegisterKeyMappingsEvent e) {
      e.register(TOGGLE_RADIAL);
   }

   @SubscribeEvent
   public static void handleKeys(ClientTickEvent e) {
      if (e.phase != Phase.START) {
         if (Minecraft.m_91087_().f_91074_ != null) {
            while (TOGGLE_RADIAL.m_90859_() && TOGGLE_RADIAL.isConflictContextAndModifierActive()) {
               if (Minecraft.m_91087_().f_91080_ == null) {
                  Apotheosis.CHANNEL.sendToServer(new RadialStateChangeMessage());
               }
            }
         }
      }
   }
}
