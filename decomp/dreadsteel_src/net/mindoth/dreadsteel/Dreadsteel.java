package net.mindoth.dreadsteel;

import net.mindoth.dreadsteel.config.DreadsteelCommonConfig;
import net.mindoth.dreadsteel.message.MessageSwingArm;
import net.mindoth.dreadsteel.registries.DreadsteelEntities;
import net.mindoth.dreadsteel.registries.DreadsteelItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod("dreadsteel")
public class Dreadsteel {
   public static final String MOD_ID = "dreadsteel";
   public static final SimpleChannel NETWORK_WRAPPER;
   private static final String PROTOCOL_VERSION = Integer.toString(1);
   private static int packetsRegistered = 0;

   public Dreadsteel() {
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      if (FMLEnvironment.dist == Dist.CLIENT) {
         DreadsteelClient.registerHandlers();
      }

      this.addRegistries(modEventBus);
      modEventBus.addListener(this::setup);
      ModLoadingContext.get().registerConfig(Type.COMMON, DreadsteelCommonConfig.SPEC, "dreadsteel-common.toml");
   }

   private void addRegistries(IEventBus modEventBus) {
      DreadsteelItems.ITEMS.register(modEventBus);
      DreadsteelEntities.ENTITIES.register(modEventBus);
   }

   public static <MSG> void sendMSGToServer(MSG message) {
      NETWORK_WRAPPER.sendToServer(message);
   }

   private void setup(FMLCommonSetupEvent event) {
      NETWORK_WRAPPER.registerMessage(
         packetsRegistered++, MessageSwingArm.class, MessageSwingArm::encode, MessageSwingArm::decode, MessageSwingArm.Handler::handle
      );
   }

   static {
      ChannelBuilder channel = ChannelBuilder.named(new ResourceLocation("dreadsteel", "main_channel"));
      String version = PROTOCOL_VERSION;
      channel = channel.clientAcceptedVersions(version::equals);
      version = PROTOCOL_VERSION;
      NETWORK_WRAPPER = channel.serverAcceptedVersions(version::equals).networkProtocolVersion(() -> Dreadsteel.PROTOCOL_VERSION).simpleChannel();
   }
}
