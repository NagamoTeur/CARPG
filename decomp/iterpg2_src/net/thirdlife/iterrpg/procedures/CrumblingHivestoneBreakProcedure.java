package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class CrumblingHivestoneBreakProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double xpos = 0.0;
         double zpos = 0.0;
         if (entity instanceof Player && !entity.m_6144_()) {
            world.m_46961_(new BlockPos(x, y, z), false);

            for (int index0 = 0; index0 < 8; index0++) {
               xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
               zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
               if (world.m_8055_(new BlockPos(x + xpos, y, z + zpos)).m_60734_() == IterRpgModBlocks.CRUMBLING_HIVESTONE.get()) {
                  world.m_46961_(new BlockPos(x + xpos, y, z + zpos), false);
                  if (world instanceof Level _level) {
                     _level.m_46672_(new BlockPos(x + xpos, y, z + zpos), _level.m_8055_(new BlockPos(x + xpos, y, z + zpos)).m_60734_());
                  }
               }
            }

            if (world instanceof Level _level) {
               _level.m_46672_(new BlockPos(x, y, z), _level.m_8055_(new BlockPos(x, y, z)).m_60734_());
            }
         }
      }
   }
}
