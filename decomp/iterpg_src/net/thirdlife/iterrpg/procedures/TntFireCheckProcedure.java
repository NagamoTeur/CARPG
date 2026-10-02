package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TntFireCheckProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if ((
            world.m_8055_(
                        new BlockPos(
                           x + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           y + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           z + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1)
                        )
                     )
                     .m_60734_()
                  == Blocks.f_50084_
               || world.m_8055_(
                        new BlockPos(
                           x + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           y + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           z + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1)
                        )
                     )
                     .m_60734_()
                  == Blocks.f_50083_
         )
         && !world.m_5776_()) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockEntity _blockEntity = world.m_7702_(_bp);
         BlockState _bs = world.m_8055_(_bp);
         if (_blockEntity != null) {
            _blockEntity.getPersistentData().m_128347_("ignite", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z), "ignite") + 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bp, _bs, _bs, 3);
         }
      }

      if ((
            world.m_8055_(
                        new BlockPos(
                           x + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           y + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           z + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1)
                        )
                     )
                     .m_60734_()
                  == Blocks.f_49990_
               || world.m_8055_(
                        new BlockPos(
                           x + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           y + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           z + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1)
                        )
                     )
                     .m_60734_()
                  == Blocks.f_49990_
               || world.m_8055_(
                        new BlockPos(
                           x + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           y + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           z + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1)
                        )
                     )
                     .m_60734_()
                  == Blocks.f_50057_
               || world.m_8055_(
                        new BlockPos(
                           x + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           y + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1),
                           z + (double)Mth.m_216271_(RandomSource.m_216327_(), -1, 1)
                        )
                     )
                     .m_60734_()
                  == Blocks.f_50126_
         )
         && !world.m_5776_()) {
         BlockPos _bpx = new BlockPos(x, y, z);
         BlockEntity _blockEntityx = world.m_7702_(_bpx);
         BlockState _bsx = world.m_8055_(_bpx);
         if (_blockEntityx != null) {
            _blockEntityx.getPersistentData().m_128347_("ignite", 0.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpx, _bsx, _bsx, 3);
         }
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") > 0.0 && Mth.m_216263_(RandomSource.m_216327_(), 2.0, 32.0) < (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") && !world.m_5776_()) {
         BlockPos _bpxx = new BlockPos(x, y, z);
         BlockEntity _blockEntityxx = world.m_7702_(_bpxx);
         BlockState _bsxx = world.m_8055_(_bpxx);
         if (_blockEntityxx != null) {
            _blockEntityxx.getPersistentData().m_128347_("ignite", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z), "ignite") + 0.25);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpxx, _bsxx, _bsxx, 3);
         }
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") >= 2.0 && world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123762_, x + 0.5, y + 1.05, z + 0.5, Mth.m_216271_(RandomSource.m_216327_(), 1, 2), 0.15, 0.15, 0.15, 0.015);
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") >= 4.0 && world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123755_, x + 0.5, y + 1.05, z + 0.5, Mth.m_216271_(RandomSource.m_216327_(), 1, 2), 0.15, 0.15, 0.15, 0.015);
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") >= 8.0 && world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123777_, x + 0.5, y + 1.05, z + 0.5, Mth.m_216271_(RandomSource.m_216327_(), 1, 2), 0.15, 0.15, 0.15, 0.015);
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") >= 12.0 && world instanceof ServerLevel _level) {
         _level.m_8767_(ParticleTypes.f_123744_, x + 0.5, y + 1.05, z + 0.5, Mth.m_216271_(RandomSource.m_216327_(), 1, 2), 0.25, 0.15, 0.25, 0.015);
      }

      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "ignite") >= 16.0) {
         TntBarrelExplosionProcedure.execute(world, x, y, z);
      }
   }
}
