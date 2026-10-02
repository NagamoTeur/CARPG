package shadows.apotheosis.adventure.loot;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent.Unload;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.server.ServerLifecycleHooks;
import shadows.apotheosis.adventure.AdventureModule;

@EventBusSubscriber(
   modid = "apotheosis",
   bus = Bus.FORGE
)
class ShieldBreakerTest implements Predicate<ItemStack> {
   private static Map<Level, ShieldBreakerTest.Zombies> zombieCache = new IdentityHashMap<>();

   public boolean test(ItemStack t) {
      try {
         MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
         Level level = null;
         if (server != null) {
            level = server.m_129880_(Level.f_46428_);
         } else if (FMLEnvironment.dist.isClient()) {
            level = ShieldBreakerTest.Client.getLevel();
         }

         if (level != null) {
            ShieldBreakerTest.Zombies zombies = zombieCache.computeIfAbsent(level, ShieldBreakerTest.Zombies::new);
            return t.canDisableShield(zombies.target.m_21206_(), zombies.target, zombies.attacker);
         } else {
            return t.canDisableShield(Items.f_42740_.m_7968_(), null, null);
         }
      } catch (Exception var5) {
         AdventureModule.LOGGER.error("Failed to execute ShieldBreakerTest", var5);
         return false;
      }
   }

   @SubscribeEvent
   public static void unload(Unload e) {
      zombieCache.remove(e.getLevel());
   }

   private static class Client {
      static Level getLevel() {
         return Minecraft.m_91087_().f_91073_;
      }
   }

   private static record Zombies(Zombie attacker, Zombie target) {
      public Zombies(Level level) {
         this(new Zombie(level), new Zombie(level));
         this.target.m_21008_(InteractionHand.OFF_HAND, new ItemStack(Items.f_42740_));
      }
   }
}
