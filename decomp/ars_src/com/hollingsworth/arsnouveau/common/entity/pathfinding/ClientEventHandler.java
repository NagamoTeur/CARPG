package com.hollingsworth.arsnouveau.common.entity.pathfinding;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.hollingsworth.arsnouveau.common.light.LightManager;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void renderWorldLastEvent(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRIPWIRE_BLOCKS) {
         ClientInfo.partialTicks = event.getPartialTick();
         LightManager.updateAll(event.getLevelRenderer());
      }
   }

   @SubscribeEvent
   public static void clientPlayerLogin(LoggingIn e) {
      if (e.getPlayer() != null && (Boolean)Config.INFORM_LIGHTS.get()) {
         Player entity = e.getPlayer();
         PortUtil.sendMessage(entity, Component.m_237115_("ars_nouveau.light_message").m_130940_(ChatFormatting.GOLD));
         Config.INFORM_LIGHTS.set(false);
         Config.INFORM_LIGHTS.save();
      }
   }
}
