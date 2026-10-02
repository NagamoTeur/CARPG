package net.cisco.procedures;

import net.cisco.network.CiscoModModVariables;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class LegionnaireKingsguardOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (!CiscoModModVariables.MapVariables.get(world).FellKingLives && !entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
