package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.level.LevelAccessor;

public class DemonbloodBurstBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      DemonbloodBurstProcedure.execute(world, x + 0.5, y + 0.5, z + 0.5);
   }
}
