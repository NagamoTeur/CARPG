package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

public class VaseSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      boolean check = false;
      if (world.m_46859_(new BlockPos(x, y, z)) && world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60815_() && y <= 40.0) {
         check = true;
      } else {
         check = false;
      }

      return check;
   }
}
