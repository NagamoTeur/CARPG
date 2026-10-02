package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class SorrowSpirePrepareProcedure {
   public static void execute(LevelAccessor world, double x, double z) {
      world.m_7731_(new BlockPos(x, 35.0, z), ((Block)IterRpgModBlocks.SORROW_SPIRE_GEN_BLOCK.get()).m_49966_(), 3);
      world.m_186460_(new BlockPos(x, 35.0, z), world.m_8055_(new BlockPos(x, 35.0, z)).m_60734_(), 20);
   }
}
