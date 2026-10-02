package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ManaTemplateProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         double distance = 0.0;
         double damage = 0.0;
         double directionx = 0.0;
         double directionz = 0.0;
         BlockState directiony = Blocks.f_50016_.m_49966_();
         boolean hit = false;
         boolean particle = false;
         if (((IterRpgModVariables.PlayerVariables)entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new IterRpgModVariables.PlayerVariables()))
               .MagicCooldown
            < 10.0) {
            double _setval = 10.0;
            entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.MagicCooldown = _setval;
               capability.syncPlayerVariables(entity);
            });
         }
      }
   }
}
