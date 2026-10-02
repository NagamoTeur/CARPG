package net.thirdlife.iterrpg.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ToggleSetBonusProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         boolean _setval = !((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
               .orElse(new IterRpgModVariables.PlayerVariables()))
            .SetBonusToggle;
         entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.SetBonusToggle = _setval;
            capability.syncPlayerVariables(entity);
         });
         if (entity instanceof Player _player && !_player.f_19853_.m_5776_()) {
            _player.m_5661_(
               Component.m_237113_(
                  "Armor set bonus: "
                     + ((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                           .orElse(new IterRpgModVariables.PlayerVariables()))
                        .SetBonusToggle
               ),
               false
            );
         }
      }
   }
}
