package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class FireballParticleProjectileProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123744_, x, y + 0.2, z, 1, 0.0, 0.0, 0.0, 0.001);
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123762_, x, y + 0.2, z, 1, 0.0, 0.0, 0.0, 0.001);
         }

         if (entity.m_20069_()) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(ParticleTypes.f_123755_, x, y + 0.2, z, 4, 0.0, 0.0, 0.0, 0.001);
            }
         }
      }
   }
}
