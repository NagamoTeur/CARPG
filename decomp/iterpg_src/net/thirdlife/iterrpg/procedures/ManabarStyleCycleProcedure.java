package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ManabarStyleCycleProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .ManabarStyle
            == 1.0) {
            double _setval = 0.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ManabarStyle = _setval;
               capability.syncPlayerVariables(entity);
            });
         } else {
            double _setval = 1.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.ManabarStyle = _setval;
               capability.syncPlayerVariables(entity);
            });
         }
      }
   }
}
