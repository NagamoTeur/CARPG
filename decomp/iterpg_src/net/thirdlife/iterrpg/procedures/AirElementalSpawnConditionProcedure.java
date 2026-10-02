package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;

public class AirElementalSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double count = 0.0;
      return y >= 100.0
         && (
            world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("windswept_hills"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("windswept_hills"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("snowy_taiga"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("taiga"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("jagged_peaks"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("frozen_peaks"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("stony_peaks"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("meadow"))
         )
         && ElementalsSpawnConditionProcedure.execute(world, x, y, z);
   }
}
