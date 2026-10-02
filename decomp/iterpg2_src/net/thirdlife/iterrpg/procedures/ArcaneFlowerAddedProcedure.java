package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ArcaneFlowerAddedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      if (!world.m_5776_()) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockEntity _blockEntity = world.m_7702_(_bp);
         BlockState _bs = world.m_8055_(_bp);
         if (_blockEntity != null) {
            _blockEntity.getPersistentData().m_128347_("GrowthTime", 0.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bp, _bs, _bs, 3);
         }
      }

      int _value = 0;
      BlockPos _pos = new BlockPos(x, y, z);
      BlockState _bsx = world.m_8055_(_pos);
      if (_bsx.m_60734_().m_49965_().m_61081_("age") instanceof IntegerProperty _integerProp && _integerProp.m_6908_().contains(_value)) {
         world.m_7731_(_pos, (BlockState)_bsx.m_61124_(_integerProp, _value), 3);
      }
   }
}
