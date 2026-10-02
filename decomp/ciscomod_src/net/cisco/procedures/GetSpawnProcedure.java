package net.cisco.procedures;

import javax.annotation.Nullable;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class GetSpawnProcedure {
   @SubscribeEvent
   public static void onPlayerInBed(PlayerSleepInBedEvent event) {
      execute(event, (double)event.getPos().m_123341_(), (double)event.getPos().m_123342_(), (double)event.getPos().m_123343_(), event.getEntity());
   }

   public static void execute(double x, double y, double z, Entity entity) {
      execute(null, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, double x, double y, double z, Entity entity) {
      if (entity != null) {
         entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.PosX = x;
            capability.syncPlayerVariables(entity);
         });
         entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.PosY = y;
            capability.syncPlayerVariables(entity);
         });
         entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.PosZ = z;
            capability.syncPlayerVariables(entity);
         });
      }
   }
}
