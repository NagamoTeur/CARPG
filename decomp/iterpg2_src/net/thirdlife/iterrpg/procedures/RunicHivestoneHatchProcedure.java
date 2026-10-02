package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class RunicHivestoneHatchProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double xpos = 0.0;
      double ypos = 0.0;
      double zpos = 0.0;

      for (int index0 = 0; index0 < 64; index0++) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123809_, x + 0.5, y + 0.5, z + 0.5, 1, 0.25, 0.25, 0.25, 0.0);
         }

         xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -6, 6);
         ypos = (double)Mth.m_216271_(RandomSource.m_216327_(), -3, 3);
         zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -6, 6);
         if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60734_() == IterRpgModBlocks.SPIDER_EGG.get()) {
            SpiderEggMagicHatchProcedure.execute(world, x + xpos, y + ypos, z + zpos);
         }
      }
   }
}
