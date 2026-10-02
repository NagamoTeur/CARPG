package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ArcaneConductionResetProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double _setval = 0.0;
         entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.ManaRegenMultiplier = _setval;
            capability.syncPlayerVariables(entity);
         });
      }
   }
}
