package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.thirdlife.iterrpg.init.IterRpgModMobEffects;
import net.thirdlife.iterrpg.network.IterRpgModVariables;

public class ArcaneConductionFunctionProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         int var10000;
         label15: {
            if (entity instanceof LivingEntity _livEnt && _livEnt.m_21023_((MobEffect)IterRpgModMobEffects.ARCANE_CONDUCTION.get())) {
               var10000 = _livEnt.m_21124_((MobEffect)IterRpgModMobEffects.ARCANE_CONDUCTION.get()).m_19564_();
               break label15;
            }

            var10000 = 0;
         }

         double _setval = (double)(var10000 * 50 / 100) + 0.5;
         entity.getCapability(IterRpgModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
            capability.ManaRegenMultiplier = _setval;
            capability.syncPlayerVariables(entity);
         });
      }
   }
}
