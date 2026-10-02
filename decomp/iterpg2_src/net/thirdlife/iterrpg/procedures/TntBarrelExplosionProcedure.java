package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;

public class TntBarrelExplosionProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 3);
      if (world instanceof Level _level && !_level.m_5776_()) {
         _level.m_46511_(null, x + 0.5, y + 0.5, z + 0.5, 5.0F, BlockInteraction.BREAK);
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123777_, x + 0.5, y + 0.5, z + 0.5, 64, 1.25, 1.25, 1.25, Mth.m_216263_(RandomSource.m_216327_(), 0.1, 0.25));
      }

      if (world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123744_, x + 0.5, y + 0.5, z + 0.5, 64, 1.25, 1.25, 1.25, Mth.m_216263_(RandomSource.m_216327_(), 0.1, 0.25));
      }
   }
}
