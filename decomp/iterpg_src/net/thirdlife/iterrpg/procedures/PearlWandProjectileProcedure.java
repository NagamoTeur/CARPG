package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class PearlWandProjectileProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123772_, x, y + 0.2, z, 1, 0.0, 0.0, 0.0, 0.05);
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123769_, x, y + 0.2, z, 1, 0.0, 0.0, 0.0, 0.05);
      }
   }
}
