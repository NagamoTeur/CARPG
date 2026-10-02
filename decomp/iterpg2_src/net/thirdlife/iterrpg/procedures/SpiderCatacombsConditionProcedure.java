package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;

public class SpiderCatacombsConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      boolean check = false;
      return SpiderCatacombsConfigConditionProcedure.execute()
         && y <= 48.0
         && world.m_8055_(new BlockPos(x, y, z)).m_60815_()
         && !(world.m_8055_(new BlockPos(x, y, z)).m_60734_() instanceof LiquidBlock);
   }
}
