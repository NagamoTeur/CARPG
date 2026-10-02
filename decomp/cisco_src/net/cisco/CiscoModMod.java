package net.cisco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.cisco.init.CiscoModModBlocks;
import net.cisco.init.CiscoModModEntities;
import net.cisco.init.CiscoModModFeatures;
import net.cisco.init.CiscoModModItems;
import net.cisco.init.CiscoModModMobEffects;
import net.cisco.init.CiscoModModParticleTypes;
import net.cisco.init.CiscoModModSounds;
import net.cisco.init.CiscoModModTabs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod("cisco_mod")
public class CiscoModMod {
   public static final Logger LOGGER = LogManager.getLogger(CiscoModMod.class);
   public static final String MODID = "cisco_mod";
   private static final String PROTOCOL_VERSION = "1";
   public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(
      new ResourceLocation("cisco_mod", "cisco_mod"), () -> "1", "1"::equals, "1"::equals
   );
   private static int messageID = 0;
   private static final Collection<SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

   public CiscoModMod() {
      MinecraftForge.EVENT_BUS.register(this);
      CiscoModModTabs.load();
      IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
      CiscoModModSounds.REGISTRY.register(bus);
      CiscoModModBlocks.REGISTRY.register(bus);
      CiscoModModItems.REGISTRY.register(bus);
      CiscoModModEntities.REGISTRY.register(bus);
      CiscoModModFeatures.REGISTRY.register(bus);
      CiscoModModMobEffects.REGISTRY.register(bus);
      CiscoModModParticleTypes.REGISTRY.register(bus);
      GeckoLib.initialize();
   }

   public static <T> void addNetworkMessage(
      Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<Context>> messageConsumer
   ) {
      PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
      messageID++;
   }

   public static void queueServerWork(int tick, Runnable action) {
      workQueue.add(new SimpleEntry<>(action, tick));
   }

   @SubscribeEvent
   public void tick(ServerTickEvent event) {
      if (event.phase == Phase.END) {
         List<SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
         workQueue.forEach(work -> {
            work.setValue(work.getValue() - 1);
            if (work.getValue() == 0) {
               actions.add((SimpleEntry<Runnable, Integer>)work);
            }
         });
         actions.forEach(e -> e.getKey().run());
         workQueue.removeAll(actions);
      }
   }
}
