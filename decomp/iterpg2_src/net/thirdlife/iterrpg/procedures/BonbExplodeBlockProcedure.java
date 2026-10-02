package net.thirdlife.iterrpg.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;

public class BonbExplodeBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         double damage = 0.0;
         double distance = 0.0;
         boolean hit = false;
         boolean particle = false;
         BonbExplodeProcedure.execute(world, x + 0.5, y + 0.5, z + 0.5, immediatesourceentity);
      }
   }
}
