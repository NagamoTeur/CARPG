package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class GoblinDungeonPrepareProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.GOBLIN_DUNGEON_GEN.get()).m_49966_(), 3);
      world.m_186460_(new BlockPos(x, y, z), world.m_8055_(new BlockPos(x, y, z)).m_60734_(), 20);
   }
}
