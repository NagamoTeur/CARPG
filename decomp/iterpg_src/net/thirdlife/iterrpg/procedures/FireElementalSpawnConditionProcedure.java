package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.level.LevelAccessor;

public class FireElementalSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double count = 0.0;
      return ElementalsSpawnConditionProcedure.execute(world, x, y, z);
   }
}
