package com.hollingsworth.arsnouveau.client.events;

import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau"
)
public class ClientForgeHandler {
   @SubscribeEvent
   public static void overlayEvent(Pre event) {
      if (Minecraft.m_91087_().f_91080_ instanceof GuiRadialMenu && event.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type()) {
         event.setCanceled(true);
      }
   }

   public static Component localize(String key, Object... params) {
      for (int i = 0; i < params.length; i++) {
         Object parameter = params[i];
         if (parameter instanceof Component) {
            Component component = (Component)parameter;
            if (component.m_214077_() instanceof TranslatableContents translatableContents) {
               params[i] = localize(translatableContents.m_237508_(), translatableContents.m_237523_());
            }
         }
      }

      return Component.m_237110_(key, params);
   }
}
