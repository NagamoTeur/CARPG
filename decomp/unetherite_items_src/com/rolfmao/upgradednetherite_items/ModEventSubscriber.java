package com.rolfmao.upgradednetherite_items;

import com.rolfmao.upgradednetherite_items.config.ConfigHelper;
import com.rolfmao.upgradednetherite_items.config.ConfigHolder;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@EventBusSubscriber(
   modid = "upgradednetherite_items",
   bus = Bus.MOD
)
public class ModEventSubscriber {
   @SubscribeEvent
   public static void onModConfigEvent(ModConfigEvent event) {
      ModConfig config = event.getConfig();
      if (config.getSpec() == ConfigHolder.CLIENT_SPEC) {
         ConfigHelper.bakeClient(config);
      } else if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
         ConfigHelper.bakeServer(config);
      }
   }
}
