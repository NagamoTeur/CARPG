package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class SoulsReleaseProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      if (sourceentity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123746_, x, y + (double)(sourceentity.m_20206_() / 2.0F), z, 15, 0.1, 0.1, 0.1, 0.025);
         }
      }
   }
}
