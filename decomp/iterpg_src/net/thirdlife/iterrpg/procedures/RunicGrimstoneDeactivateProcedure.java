package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class RunicGrimstoneDeactivateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double distance = 0.0;
      boolean flag = false;
      boolean wallmeet = false;
      boolean up = false;
      boolean down = false;
      if ((
            world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
               || world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
               || world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
               || world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
               || world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
               || world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
         )
         && !world.m_5776_()) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockEntity _blockEntity = world.m_7702_(_bp);
         BlockState _bs = world.m_8055_(_bp);
         if (_blockEntity != null) {
            _blockEntity.getPersistentData().m_128347_("demoncharge", 64.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bp, _bs, _bs, 3);
         }
      }

      if (world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x + 1.0, y, z), "demoncharge") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") && !world.m_5776_()) {
         BlockPos _bpx = new BlockPos(x, y, z);
         BlockEntity _blockEntityx = world.m_7702_(_bpx);
         BlockState _bsx = world.m_8055_(_bpx);
         if (_blockEntityx != null) {
            _blockEntityx.getPersistentData().m_128347_("demoncharge", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x + 1.0, y, z), "demoncharge") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpx, _bsx, _bsx, 3);
         }
      }

      if (world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x - 1.0, y, z), "demoncharge") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") && !world.m_5776_()) {
         BlockPos _bpxx = new BlockPos(x, y, z);
         BlockEntity _blockEntityxx = world.m_7702_(_bpxx);
         BlockState _bsxx = world.m_8055_(_bpxx);
         if (_blockEntityxx != null) {
            _blockEntityxx.getPersistentData().m_128347_("demoncharge", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x - 1.0, y, z), "demoncharge") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpxx, _bsxx, _bsxx, 3);
         }
      }

      if (world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z + 1.0), "demoncharge") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") && !world.m_5776_()) {
         BlockPos _bpxxx = new BlockPos(x, y, z);
         BlockEntity _blockEntityxxx = world.m_7702_(_bpxxx);
         BlockState _bsxxx = world.m_8055_(_bpxxx);
         if (_blockEntityxxx != null) {
            _blockEntityxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z + 1.0), "demoncharge") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpxxx, _bsxxx, _bsxxx, 3);
         }
      }

      if (world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z - 1.0), "demoncharge") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") && !world.m_5776_()) {
         BlockPos _bpxxxx = new BlockPos(x, y, z);
         BlockEntity _blockEntityxxxx = world.m_7702_(_bpxxxx);
         BlockState _bsxxxx = world.m_8055_(_bpxxxx);
         if (_blockEntityxxxx != null) {
            _blockEntityxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z - 1.0), "demoncharge") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpxxxx, _bsxxxx, _bsxxxx, 3);
         }
      }

      if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y + 1.0, z), "demoncharge") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") && !world.m_5776_()) {
         BlockPos _bpxxxxx = new BlockPos(x, y, z);
         BlockEntity _blockEntityxxxxx = world.m_7702_(_bpxxxxx);
         BlockState _bsxxxxx = world.m_8055_(_bpxxxxx);
         if (_blockEntityxxxxx != null) {
            _blockEntityxxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y + 1.0, z), "demoncharge") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpxxxxx, _bsxxxxx, _bsxxxxx, 3);
         }
      }

      if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y - 1.0, z), "demoncharge") > (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") && !world.m_5776_()) {
         BlockPos _bpxxxxxx = new BlockPos(x, y, z);
         BlockEntity _blockEntityxxxxxx = world.m_7702_(_bpxxxxxx);
         BlockState _bsxxxxxx = world.m_8055_(_bpxxxxxx);
         if (_blockEntityxxxxxx != null) {
            _blockEntityxxxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y - 1.0, z), "demoncharge") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpxxxxxx, _bsxxxxxx, _bsxxxxxx, 3);
         }
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") > 1.0) {
         if (!world.m_5776_()) {
            BlockPos _bpxxxxxxx = new BlockPos(x, y, z);
            BlockEntity _blockEntityxxxxxxx = world.m_7702_(_bpxxxxxxx);
            BlockState _bsxxxxxxx = world.m_8055_(_bpxxxxxxx);
            if (_blockEntityxxxxxxx != null) {
               _blockEntityxxxxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bpxxxxxxx, _bsxxxxxxx, _bsxxxxxxx, 3);
            }
         }
      } else {
         world.m_7731_(new BlockPos(x, y, z), ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE.get()).m_49966_(), 3);
      }
   }
}
