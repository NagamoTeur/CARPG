package com.hollingsworth.arsnouveau.common.camera;

import com.hollingsworth.arsnouveau.common.util.ClientCameraUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau",
   value = {Dist.CLIENT}
)
public class ClientCameraEvents {
   @SubscribeEvent
   public static void renderHandEvent(RenderHandEvent event) {
      if (ClientCameraUtil.isPlayerMountedOnCamera()) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent
   public static void onClickInput(InteractionKeyMappingTriggered event) {
      if (ClientCameraUtil.isPlayerMountedOnCamera()) {
         event.setCanceled(true);
         event.setSwingHand(false);
      }
   }

   @SubscribeEvent
   @OnlyIn(Dist.CLIENT)
   public static void onGuiOpen(Pre event) {
      VanillaGuiOverlay[] overlays = new VanillaGuiOverlay[]{VanillaGuiOverlay.JUMP_BAR, VanillaGuiOverlay.EXPERIENCE_BAR, VanillaGuiOverlay.POTION_ICONS};
      if (ClientCameraUtil.isPlayerMountedOnCamera()) {
         for (VanillaGuiOverlay overlay : overlays) {
            if (event.getOverlay() == overlay.type()) {
               event.setCanceled(true);
            }
         }
      }
   }
}
