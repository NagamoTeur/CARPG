package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class FireballHitProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity) {
      if (entity != null && immediatesourceentity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123756_, x, y, z, 6, 0.0, 0.0, 0.0, 0.05);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123744_, x, y, z, 6, 0.0, 0.0, 0.0, 0.05);
         }

         entity.m_20254_(8);
         if (!immediatesourceentity.f_19853_.m_5776_()) {
            immediatesourceentity.m_146870_();
         }
      }
   }
}
