package com.cerbon.bosses_of_mass_destruction.event;

import com.cerbon.bosses_of_mass_destruction.block.BMDBlockEntities;
import com.cerbon.bosses_of_mass_destruction.config.BMDConfig;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.packet.BMDPacketHandler;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler.ConfigScreenFactory;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

public class BMDEvents {
   @EventBusSubscriber(
      modid = "bosses_of_mass_destruction",
      bus = Bus.MOD,
      value = {Dist.CLIENT}
   )
   public static class ClientEvents {
      @SubscribeEvent
      protected static void onClientSetup(FMLClientSetupEvent event) {
         BMDEntities.initClient();
         BMDItems.initClient();
         BMDBlockEntities.initClient();
         ModLoadingContext.get()
            .registerExtensionPoint(
               ConfigScreenFactory.class, () -> new ConfigScreenFactory((client, parent) -> (Screen)AutoConfig.getConfigScreen(BMDConfig.class, parent).get())
            );
      }

      @SubscribeEvent
      protected static void registerParticleProviders(@NotNull RegisterParticleProvidersEvent event) {
         BMDParticles.initClient(event);
      }
   }

   @EventBusSubscriber(
      modid = "bosses_of_mass_destruction",
      bus = Bus.MOD
   )
   public static class CommonEvents {
      @SubscribeEvent
      protected static void onCommonSetup(FMLCommonSetupEvent event) {
         event.enqueueWork(BMDPacketHandler::register);
      }

      @SubscribeEvent
      public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
         BMDEntities.createAttributes(event);
      }
   }
}
