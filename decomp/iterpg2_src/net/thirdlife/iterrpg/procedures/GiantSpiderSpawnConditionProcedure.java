package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class GiantSpiderSpawnConditionProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_
         && world.m_46803_(new BlockPos(x, y, z)) <= 7
         && (
            y <= 16.0
               || world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("forge:giant_spider_biomes")))
         );
   }
}
