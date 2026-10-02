package software.bernie.ars_nouveau.geckolib3.network;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;
import software.bernie.ars_nouveau.geckolib3.GeckoLib;
import software.bernie.ars_nouveau.geckolib3.network.messages.SyncAnimationMsg;

public class GeckoLibNetwork {
   private static final Map<String, Supplier<ISyncable>> SYNCABLES = new Object2ObjectOpenHashMap();
   private static final String PROTOCOL_VERSION = "0";
   private static final SimpleChannel CHANNEL = fetchGeckoLibChannel("main");

   private static SimpleChannel fetchGeckoLibChannel(String name) {
      try {
         ResourceLocation key = new ResourceLocation("geckolib3", name);
         Method findTarget = NetworkRegistry.class.getDeclaredMethod("findTarget", ResourceLocation.class);
         findTarget.setAccessible(true);
         return ((Optional)findTarget.invoke(null, key))
            .<SimpleChannel>map(SimpleChannel::new)
            .orElseGet(() -> NetworkRegistry.newSimpleChannel(key, () -> "0", "0"::equals, "0"::equals));
      } catch (Throwable var3) {
         throw new RuntimeException("Failed to fetch GeckoLib network channel", var3);
      }
   }

   public static void initialize() {
      int id = -1;
      SyncAnimationMsg.register(CHANNEL, ++id);
   }

   public static void syncAnimation(PacketTarget target, ISyncable syncable, int id, int state) {
      if (!target.getDirection().getOriginationSide().isServer()) {
         throw new IllegalArgumentException("Only the server can request animation syncs!");
      } else {
         String key = syncable.getSyncKey();
         if (!SYNCABLES.containsKey(key)) {
            throw new IllegalArgumentException("Syncable not registered for " + key);
         } else {
            CHANNEL.send(target, new SyncAnimationMsg(key, id, state));
         }
      }
   }

   public static ISyncable getSyncable(String key) {
      Supplier<ISyncable> delegate = SYNCABLES.get(key);
      return delegate == null ? null : delegate.get();
   }

   public static void registerSyncable(ISyncable entry) {
      String key = entry.getSyncKey();
      if (SYNCABLES.putIfAbsent(key, () -> entry) != null) {
         throw new IllegalArgumentException("Syncable already registered for " + key);
      } else {
         GeckoLib.LOGGER.debug("Registered syncable for " + key);
      }
   }
}
