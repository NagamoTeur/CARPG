package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;

public class ArcaneFlowerBlockCheckProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z) {
      return world.m_8055_(new BlockPos(x, y - 1.0, z)).m_204336_(BlockTags.create(new ResourceLocation("iter_rpg:arcane_flower_soil")));
   }
}
