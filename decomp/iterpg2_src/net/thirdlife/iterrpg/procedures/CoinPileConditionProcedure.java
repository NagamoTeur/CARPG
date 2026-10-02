package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;

public class CoinPileConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60783_(world, new BlockPos(x, y - 1.0, z), Direction.UP);
   }
}
