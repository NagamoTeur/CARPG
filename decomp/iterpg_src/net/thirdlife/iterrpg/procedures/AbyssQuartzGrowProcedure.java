package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class AbyssQuartzGrowProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double direction = 0.0;
      Direction direct = Direction.NORTH;
      if (Math.random() >= 0.92
         && (
            world.m_8055_(new BlockPos(x + 1.0, y + 0.0, z + 0.0)).m_60734_() == Blocks.f_50752_
               || world.m_8055_(new BlockPos(x - 1.0, y + 0.0, z + 0.0)).m_60734_() == Blocks.f_50752_
               || world.m_8055_(new BlockPos(x + 0.0, y + 0.0, z + 1.0)).m_60734_() == Blocks.f_50752_
               || world.m_8055_(new BlockPos(x + 0.0, y + 0.0, z - 1.0)).m_60734_() == Blocks.f_50752_
               || world.m_8055_(new BlockPos(x + 0.0, y + 1.0, z + 0.0)).m_60734_() == Blocks.f_50752_
               || world.m_8055_(new BlockPos(x + 0.0, y - 1.0, z + 0.0)).m_60734_() == Blocks.f_50752_
         )
         && (world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_
         && y <= -58.0) {
         direct = Direction.m_235672_(RandomSource.m_216327_());
         if (world.m_46859_(new BlockPos(x + (double)direct.m_122429_(), y + (double)direct.m_122430_(), z + (double)direct.m_122431_()))) {
            world.m_7731_(
               new BlockPos(x + (double)direct.m_122429_(), y + (double)direct.m_122430_(), z + (double)direct.m_122431_()),
               ((Block)IterRpgModBlocks.ABYSS_QUARTZ.get()).m_49966_(),
               3
            );
            BlockPos _pos = new BlockPos(x + (double)direct.m_122429_(), y + (double)direct.m_122430_(), z + (double)direct.m_122431_());
            BlockState _bs = world.m_8055_(_pos);
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp && _dp.m_6908_().contains(direct)) {
               world.m_7731_(_pos, (BlockState)_bs.m_61124_(_dp, direct), 3);
               return;
            }

            if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ap && _ap.m_6908_().contains(direct.m_122434_())) {
               world.m_7731_(_pos, (BlockState)_bs.m_61124_(_ap, direct.m_122434_()), 3);
            }
         }
      }
   }
}
