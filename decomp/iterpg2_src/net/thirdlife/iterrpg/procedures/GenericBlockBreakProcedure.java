package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

public class GenericBlockBreakProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.m_46961_(new BlockPos(x, y, z), false);
   }
}
