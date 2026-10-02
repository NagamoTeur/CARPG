package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PhantomChainExistProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double distance = 0.0;
      boolean flag = false;
      boolean wallmeet = false;
      boolean up = false;
      boolean down = false;
      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "demoncharge") > 1.0) {
         if (!world.m_5776_()) {
            BlockPos _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntity = world.m_7702_(_bp);
            BlockState _bs = world.m_8055_(_bp);
            if (_blockEntity != null) {
               _blockEntity.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bs, _bs, 3);
            }
         }
      } else {
         BlockPos _pos = new BlockPos(x, y, z);
         Block.m_49892_(world.m_8055_(_pos), world, new BlockPos(x, -128.0, z), null);
         world.m_46961_(_pos, false);
      }
   }
}
