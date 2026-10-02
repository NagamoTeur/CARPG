package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;

public class SpiderlingSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return (
            world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("lush_caves"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("jungle"))
               || world.m_204166_(new BlockPos(x, y, z)).m_203373_(new ResourceLocation("dripstone_caves"))
         )
         && world.m_46803_(new BlockPos(x, y, z)) <= 7;
   }
}
