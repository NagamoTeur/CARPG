package net.cisco.procedures;

import net.cisco.init.CiscoModModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class OwlTowerOnStructureInstanceGeneratedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      world.m_7731_(new BlockPos(x, y, z), ((Block)CiscoModModBlocks.STRUCUTEBLOCKFIX.get()).m_49966_(), 3);
      world.m_186460_(new BlockPos(x, y, z), world.m_8055_(new BlockPos(x, y, z)).m_60734_(), 20);
   }
}
