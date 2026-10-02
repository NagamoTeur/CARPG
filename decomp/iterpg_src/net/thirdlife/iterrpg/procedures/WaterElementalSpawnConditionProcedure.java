package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class WaterElementalSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      double count = 0.0;
      return (
            world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("minecraft:is_ocean"))) && y >= 16.0
               || (
                     Blocks.f_50034_.m_49966_().m_60710_(world, new BlockPos(x, y, z))
                        || Blocks.f_50037_.m_49966_().m_60710_(world, new BlockPos(x, y, z))
                        || Blocks.f_50130_.m_49966_().m_60710_(world, new BlockPos(x, y, z))
                        || Blocks.f_50128_.m_49966_().m_60710_(world, new BlockPos(x, y, z))
                  )
                  && world.m_46861_(new BlockPos(x, y, z))
                  && world.m_6106_().m_6533_()
                  && Math.random() >= 0.75
         )
         && (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_
         && ElementalsSpawnConditionProcedure.execute(world, x, y, z);
   }
}
