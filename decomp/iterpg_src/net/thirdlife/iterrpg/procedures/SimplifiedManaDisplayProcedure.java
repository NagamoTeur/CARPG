package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class SimplifiedManaDisplayProcedure {
   public static String execute(Entity entity) {
      return entity == null
         ? ""
         : Math.round(
               ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .Mana
            )
            + "/"
            + Math.round(
               ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                     .orElse(new IterRpgModVariables.PlayerVariables()))
                  .MaxMana
            );
   }
}
