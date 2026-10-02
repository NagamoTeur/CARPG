package com.aizistral.enigmaticlegacy.handlers;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.packets.clients.PacketUpdateNotification;
import com.aizistral.omniconfig.Configuration;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import java.net.URL;
import java.util.Scanner;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;

public class EnigmaticUpdateHandler {
   public static Omniconfig.BooleanParameter notificationsEnabled;
   private static String currentVersion = "2.26.5 Release";
   private static String newestVersion;
   public static MutableComponent updateStatus = null;
   public static boolean show = false;
   static boolean worked = false;

   @SubscribeConfig(
      receiveClient = true
   )
   public static void onConfig(OmniconfigWrapper builder) {
      if (builder.config.getSidedType() == Configuration.SidedConfigType.CLIENT) {
         notificationsEnabled = builder.comment("Whether or not Enigmatic Legacy should show notification in chat when new mod update is available.")
            .getBoolean("UpdateHandlerEnabled", true);
      }
   }

   @SubscribeEvent
   public void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         EnigmaticLegacy.packetInstance.send(PacketDistributor.PLAYER.with(() -> player), new PacketUpdateNotification());
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static void handleShowup(LocalPlayer player) {
      if (show) {
         if (notificationsEnabled.getValue()) {
            player.m_213846_(updateStatus);
         }

         show = false;
      }
   }

   public static void init() {
      getNewestVersion();
      if (newestVersion != null) {
         if (newestVersion.equalsIgnoreCase(currentVersion)) {
            show = false;
         } else if (!newestVersion.equalsIgnoreCase(currentVersion)) {
            show = true;
            MutableComponent newVerArg = Component.m_237113_(newestVersion);
            newVerArg.m_130940_(ChatFormatting.GOLD);
            updateStatus = Component.m_237110_("status.enigmaticlegacy.outdated", new Object[]{newVerArg});
            updateStatus.m_130940_(ChatFormatting.DARK_PURPLE);
         }
      } else {
         show = true;
         updateStatus = Component.m_237115_("status.enigmaticlegacy.noconnection");
         updateStatus.m_130940_(ChatFormatting.RED);
      }
   }

   private static void getNewestVersion() {
      try {
         URL url = new URL("https://raw.githubusercontent.com/Extegral/Enigmatic-Legacy/1.19.X/version.txt");
         Scanner s = new Scanner(url.openStream());
         newestVersion = s.nextLine();
         s.close();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }
}
