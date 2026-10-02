package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;

public class GoblinCampsConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return !(world.m_8055_(new BlockPos(x, y, z)).m_60734_() instanceof LiquidBlock)
         && !(world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() instanceof LiquidBlock)
         && !(world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() instanceof LiquidBlock)
         && GoblinCampsConfigConditionProcedure.execute();
   }
}
