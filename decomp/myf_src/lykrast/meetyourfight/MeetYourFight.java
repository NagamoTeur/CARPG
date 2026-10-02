package lykrast.meetyourfight;

import lykrast.meetyourfight.registry.ModEntities;
import lykrast.meetyourfight.registry.ModItems;
import lykrast.meetyourfight.registry.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("meetyourfight")
public class MeetYourFight {
   public static final String MODID = "meetyourfight";
   public static final Logger LOG = LogManager.getLogger();

   public MeetYourFight() {
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      ModItems.REG.register(bus);
      ModEntities.REG.register(bus);
      ModSounds.REG.register(bus);
   }

   public static ResourceLocation rl(String name) {
      return new ResourceLocation("meetyourfight", name);
   }

   public static boolean loadedGunsWithoutRoses() {
      return ModList.get().isLoaded("gunswithoutroses");
   }
}
