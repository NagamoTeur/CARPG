package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ShowSimplifiedManabarConditionProcedure {
   public static boolean execute(Entity entity) {
      return entity == null
         ? false
         : ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .MageStatus
            && ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .ManabarStyle
               == 1.0;
   }
}
