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
public class ManaCalculateProcedure {
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
         boolean _setval = true;
         entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.MageStatus = _setval;
            capability.syncPlayerVariables(entity);
         });
         double _setvalx = 0.12;
         entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.ItemManaRegen = _setval;
            capability.syncPlayerVariables(entity);
         });
         double _setvalxx = 32.0;
         entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.ItemManaCapacity = _setval;
            capability.syncPlayerVariables(entity);
         });
         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
               .orElse(new IterRpgModVariables.PlayerVariables()))
            .MageStatus) {
            double _setvalxxx = (
                  ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ItemManaRegen
                     + ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .BaseManaRegen
               )
               * (
                  1.0
                     + ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ManaRegenMultiplier
               );
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ManaRegen = _setval;
               capability.syncPlayerVariables(entity);
            });
            double _setvalxxxx = (
                  ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ItemManaCapacity
                     + ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .BaseManaCapacity
               )
               * (
                  1.0
                     + ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ManaCapacityMultiplier
               );
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.MaxMana = _setval;
               capability.syncPlayerVariables(entity);
            });
            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MagicCooldown
               > 0.0) {
               double _setvalxxxxx = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .MagicCooldown
                  - 1.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.MagicCooldown = _setval;
                  capability.syncPlayerVariables(entity);
               });
               double _setvalxxxxxx = 0.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ManaAcceleration = _setval;
                  capability.syncPlayerVariables(entity);
               });
            } else if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ManaAcceleration
               < 50.0) {
               double _setvalxxxxx = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .ManaAcceleration
                  + 1.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ManaAcceleration = _setval;
                  capability.syncPlayerVariables(entity);
               });
            }

            if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
               <= ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MaxMana) {
               if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .MaxMana
                     - ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .Mana
                  < ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ManaAcceleration
                     * 2.0
                     * ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ManaRegen
                     / 100.0) {
                  double _setvalxxxxx = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .MaxMana;
                  entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                     capability.Mana = _setval;
                     capability.syncPlayerVariables(entity);
                  });
               } else {
                  _setval = false;
                  entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                     capability.ManaOverload = _setval;
                     capability.syncPlayerVariables(entity);
                  });
                  double _setvalxxxxx = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .Mana
                     + ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                              .orElse(new IterRpgModVariables.PlayerVariables()))
                           .ManaAcceleration
                        * 5.0
                        * ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                              .orElse(new IterRpgModVariables.PlayerVariables()))
                           .ManaRegen
                        / 100.0;
                  entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                     capability.Mana = _setval;
                     capability.syncPlayerVariables(entity);
                  });
               }
            } else {
               _setval = true;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.ManaOverload = _setval;
                  capability.syncPlayerVariables(entity);
               });
               double _setvalxxxxx = ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                        .orElse(new IterRpgModVariables.PlayerVariables()))
                     .Mana
                  - ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .ManaRegen
                     * 11.0;
               entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.Mana = _setval;
                  capability.syncPlayerVariables(entity);
               });
            }
         } else {
            double _setvalxxxxx = 0.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.Mana = _setval;
               capability.syncPlayerVariables(entity);
            });
            double _setvalxxxxxx = 0.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ManaRegen = _setval;
               capability.syncPlayerVariables(entity);
            });
            double _setvalxxxxxxx = 0.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.MaxMana = _setval;
               capability.syncPlayerVariables(entity);
            });
         }
      }
   }
}
