package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class TwiffleShareProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double xpos = 0.0;
      double zpos = 0.0;
      double ypos = 0.0;
      xpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
      zpos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
      ypos = (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1);
      if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60734_() == IterRpgModBlocks.TWIFFLE_BLOCK.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "moisture") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x + xpos, y + ypos, z + zpos), "moisture")) {
         if (!world.m_5776_()) {
            BlockPos _bp = new BlockPos(x + xpos, y + ypos, z + zpos);
            BlockEntity _blockEntity = world.m_7702_(_bp);
            BlockState _bs = world.m_8055_(_bp);
            if (_blockEntity != null) {
               _blockEntity.getPersistentData().m_128347_("moisture", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x + xpos, y + ypos, z + zpos), "moisture") + (double)Math.round((new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "moisture") / 2.0));
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
               _blockEntityx.getPersistentData().m_128347_("moisture", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "moisture") - (double)Math.round((new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "moisture") / 2.0));
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bpx, _bsx, _bsx, 3);
            }
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123804_, x + 0.5, y + 0.5, z + 0.5, 16, 0.4, 0.4, 0.4, 0.025);
         }
      }

      if (world.m_8055_(new BlockPos(x + xpos, y + ypos, z + zpos)).m_60734_() == IterRpgModBlocks.TWIFFLE_BLOCK.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "nutrients") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x + xpos, y + ypos, z + zpos), "nutrients")) {
         if (!world.m_5776_()) {
            BlockPos _bpxx = new BlockPos(x + xpos, y + ypos, z + zpos);
            BlockEntity _blockEntityxx = world.m_7702_(_bpxx);
            BlockState _bsxx = world.m_8055_(_bpxx);
            if (_blockEntityxx != null) {
               _blockEntityxx.getPersistentData().m_128347_("nutrients", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x + xpos, y + ypos, z + zpos), "nutrients") + (double)Math.round((new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "nutrients") / 2.0));
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bpxx, _bsxx, _bsxx, 3);
            }
         }

         if (!world.m_5776_()) {
            BlockPos _bpxxx = new BlockPos(x, y, z);
            BlockEntity _blockEntityxxx = world.m_7702_(_bpxxx);
            BlockState _bsxxx = world.m_8055_(_bpxxx);
            if (_blockEntityxxx != null) {
               _blockEntityxxx.getPersistentData().m_128347_("nutrients", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "nutrients") - (double)Math.round((new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "nutrients") / 2.0));
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bpxxx, _bsxxx, _bsxxx, 3);
            }
         }

         if (world instanceof ServerLevel _level) {
            _level.m_8767_(ParticleTypes.f_123780_, x + 0.5, y + 0.5, z + 0.5, 16, 0.4, 0.4, 0.4, 0.025);
         }
      }
   }
}
