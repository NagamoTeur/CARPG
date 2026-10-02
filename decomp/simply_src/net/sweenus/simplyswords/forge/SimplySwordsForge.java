package net.sweenus.simplyswords.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sweenus.simplyswords.SimplySwords;
import net.sweenus.simplyswords.forge.compat.GobberCompat;
import net.sweenus.simplyswords.forge.events.SimplySwordsClientEvents;

@Mod("simplyswords")
public class SimplySwordsForge {
   public SimplySwordsForge() {
      EventBuses.registerModEventBus("simplyswords", FMLJavaModLoadingContext.get().getModEventBus());
      SimplySwords.init();
      MinecraftForge.EVENT_BUS.register(new SimplySwordsClientEvents());
      if (ModList.get().isLoaded("gobber2")) {
         GobberCompat.registerModItems();
         GobberCompat.GOBBER_ITEM.register(FMLJavaModLoadingContext.get().getModEventBus());
      }
   }
}
