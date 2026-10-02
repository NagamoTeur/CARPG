package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class WalkOnCloudProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (Mth.m_216271_(RandomSource.m_216327_(), 0, 5) == 2 && world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123759_, entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 1, 0.1, 0.05, 0.1, 0.025);
         }
      }
   }
}
