package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class ArcaneParticleProjectileProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (world instanceof ServerLevel _level) {
         _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.ARCANE_PARTICLE.get(), x, y + 0.2, z, 1, 0.0, 0.0, 0.0, 0.001);
      }
   }
}
