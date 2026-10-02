package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;

public class GoblinSpawnProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return y >= 50.0
         && world.m_204166_(new BlockPos(x, y, z)).m_203656_(TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("iter_rpg:goblin_biomes")));
   }
}
