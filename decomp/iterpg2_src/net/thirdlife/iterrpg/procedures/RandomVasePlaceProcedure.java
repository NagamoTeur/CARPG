package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class RandomVasePlaceProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double decide = 0.0;
      double chance = 0.0;
      chance = Mth.m_216263_(RandomSource.m_216327_(), 1.0, 12.0);
      if (chance <= 12.0 && chance >= 9.0) {
         world.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 3);
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.BIG_VASE.get()).m_49966_(), 3);
      } else if (chance <= 9.0 && chance >= 5.0) {
         world.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 3);
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.VASE.get()).m_49966_(), 3);
      } else {
         world.m_7731_(new BlockPos(x, y, z), Blocks.f_50016_.m_49966_(), 3);
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.SMALL_VASE.get()).m_49966_(), 3);
      }
   }
}
