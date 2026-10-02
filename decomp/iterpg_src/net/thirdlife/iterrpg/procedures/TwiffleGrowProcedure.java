package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class TwiffleGrowProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double xpos = 0.0;
      double zpos = 0.0;
      double ypos = 0.0;
      xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
      ypos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
      zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
      if ((new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "moisture") > 32.0
         && (new Object() {
            public double getValue(LevelAccessor world, BlockPos pos, String tag) {
               BlockEntity blockEntity = world.m_7702_(pos);
               return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
            }
         }).getValue(world, new BlockPos(x, y, z), "nutrients") > 32.0
         && world.m_46859_(new BlockPos(x + xpos, y + ypos, z + zpos))
         && ((Block)IterRpgModBlocks.TWIFFLE.get()).m_49966_().m_60710_(world, new BlockPos(x + xpos, y + ypos, z + zpos))) {
         if (!world.m_5776_()) {
            BlockPos _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntity = world.m_7702_(_bp);
            BlockState _bs = world.m_8055_(_bp);
            if (_blockEntity != null) {
               _blockEntity.getPersistentData().m_128347_("moisture", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "moisture") - 32.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bs, _bs, 3);
            }
         }

         if (!world.m_5776_()) {
            BlockPos _bpx = new BlockPos(x, y, z);
            BlockEntity _blockEntityx = world.m_7702_(_bpx);
            BlockState _bsx = world.m_8055_(_bpx);
            if (_blockEntityx != null) {
               _blockEntityx.getPersistentData().m_128347_("nutrients", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "nutrients") - 32.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bpx, _bsx, _bsx, 3);
            }
         }

         world.m_7731_(new BlockPos(x + xpos, y + ypos, z + zpos), ((Block)IterRpgModBlocks.TWIFFLE.get()).m_49966_(), 3);
      }
   }
}
