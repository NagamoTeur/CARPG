package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

@EventBusSubscriber
public class ArmorCooldownProcedure {
   @SubscribeEvent
   public static void onPlayerTick(PlayerTickEvent event) {
      if (event.phase == Phase.END) {
         execute(event, event.player);
      }
   }

   public static void execute(Entity entity) {
      execute(null, entity);
   }

   private static void execute(@Nullable Event event, Entity entity) {
      if (entity != null) {
         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .ElementalArmorCooldown
            > 0.0) {
            double _setval = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ElementalArmorCooldown
               - 1.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ElementalArmorCooldown = _setval;
               capability.syncPlayerVariables(entity);
            });
         }

         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .ElementalArmorPassiveCooldown
            > 0.0) {
            double _setval = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ElementalArmorPassiveCooldown
               - 1.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ElementalArmorPassiveCooldown = _setval;
               capability.syncPlayerVariables(entity);
            });
         }
      }
   }
}
