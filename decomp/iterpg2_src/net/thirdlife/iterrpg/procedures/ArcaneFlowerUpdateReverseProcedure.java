package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ArcaneFlowerUpdateReverseProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      BlockState stage0 = Blocks.f_50016_.m_49966_();
      BlockState stage1 = Blocks.f_50016_.m_49966_();
      BlockState stage2 = Blocks.f_50016_.m_49966_();
      if (!world.m_5776_()) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockEntity _blockEntity = world.m_7702_(_bp);
         BlockState _bs = world.m_8055_(_bp);
         if (_blockEntity != null) {
            _blockEntity.getPersistentData().m_128347_("GrowthTime", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z), "GrowthTime") - 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bp, _bs, _bs, 3);
         }
      }
   }
}
