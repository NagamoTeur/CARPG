package com.aqutheseal.celestisynth;

import com.aqutheseal.celestisynth.common.events.CSRecipeBookSetupEvents;
import com.aqutheseal.celestisynth.manager.CSModManager;
import com.mojang.logging.LogUtils;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod("celestisynth")
public class Celestisynth {
   public static final String MODID = "celestisynth";
   public static final Logger LOGGER = LogUtils.getLogger();

   public Celestisynth() {
      GeckoLib.initialize();
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
      CSRecipeBookSetupEvents.staticInit();
      modEventBus.addListener(CSRecipeBookSetupEvents::registerEvent);
      if (modEventBus != null && forgeEventBus != null) {
         CSModManager.registerAll(modEventBus, forgeEventBus);
      }
   }

   public static ResourceLocation prefix(String path) {
      return new ResourceLocation("celestisynth", path.toLowerCase(Locale.ROOT));
   }
}
