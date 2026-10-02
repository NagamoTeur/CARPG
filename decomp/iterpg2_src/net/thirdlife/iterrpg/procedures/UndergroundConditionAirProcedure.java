package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;

public class UndergroundConditionAirProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      boolean check = false;
      return y <= 48.0 && !(world.m_8055_(new BlockPos(x, y, z)).m_60734_() instanceof LiquidBlock);
   }
}
