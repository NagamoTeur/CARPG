package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;

public class EarthElementalSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double count = 0.0;
      return (
            world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("lush_caves"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("jungle"))
         )
         && y <= 60.0
         && ElementalsSpawnConditionProcedure.execute(world, x, y, z);
   }
}
