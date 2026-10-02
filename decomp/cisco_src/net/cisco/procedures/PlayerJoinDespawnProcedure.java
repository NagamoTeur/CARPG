package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class PlayerJoinDespawnProcedure {
   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      execute(event, event.getEntity().f_19853_);
   }

   public static void execute(LevelAccessor world) {
      execute(null, world);
   }

   private static void execute(@Nullable Event event, LevelAccessor world) {
      CiscoModModVariables.MapVariables.get(world).IsBossAliveAndInBattle = false;
      CiscoModModVariables.MapVariables.get(world).syncData(world);
      CiscoModModVariables.MapVariables.get(world).FellKingLives = false;
      CiscoModModVariables.MapVariables.get(world).syncData(world);
      CiscoModModVariables.MapVariables.get(world).DescendedCiscoLives = false;
      CiscoModModVariables.MapVariables.get(world).syncData(world);
   }
}
