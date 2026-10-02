package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class AirElementalPoofProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (Mth.m_216271_(RandomSource.m_216327_(), 1, 2) == 1) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123759_, x, y + (double)(entity.m_20206_() / 2.0F), z, 1, 0.2, 0.2, 0.2, 0.0);
            }
         } else if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_175830_, x, y + (double)(entity.m_20206_() / 2.0F), z, 1, 0.2, 0.2, 0.2, 0.01);
         }
      }
   }
}
