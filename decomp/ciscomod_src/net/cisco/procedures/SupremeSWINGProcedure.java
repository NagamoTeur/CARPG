package net.cisco.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;

public class SupremeSWINGProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123746_, x, y, z, 70, 1.0, 1.0, 1.0, 0.8);
      }
   }
}
