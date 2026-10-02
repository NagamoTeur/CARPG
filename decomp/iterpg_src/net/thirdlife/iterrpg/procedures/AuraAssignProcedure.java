package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class AuraAssignProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double offset = 0.0;
         entity.getPersistentData().m_128347_("AuraDelay", (double)Mth.m_216271_(RandomSource.m_216327_(), 12, 20));
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123746_, x, y + 0.25, z, 12, 0.25, 0.2, 0.25, 0.05);
         }
      }
   }
}
