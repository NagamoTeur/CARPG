package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double gobamount = 0.0;
      double hobgob = 0.0;
      double gob = 0.0;
      double gobwar = 0.0;
      double gobpoints = 0.0;
      double decide = 0.0;
      double xc = 0.0;
      double zc = 0.0;
      if (!world.m_5776_()) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockEntity _blockEntity = world.m_7702_(_bp);
         BlockState _bs = world.m_8055_(_bp);
         if (_blockEntity != null) {
            _blockEntity.getPersistentData().m_128347_("respawnTime", 14000.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bp, _bs, _bs, 3);
         }
      }
   }
}
