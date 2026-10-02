package net.thirdlife.iterrpg.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class ArcaneFlowerGrowProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      BlockState stage0 = Blocks.f_50016_.m_49966_();
      BlockState stage1 = Blocks.f_50016_.m_49966_();
      BlockState stage2 = Blocks.f_50016_.m_49966_();
      if ((new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z), "GrowthTime") >= 12.0) {
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

         if ((blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip3 ? (Integer)blockstate.m_61143_(_getip3) : -1) >= 2) {
            BlockPos _bpx = new BlockPos(x, y, z);
            BlockState _bsx = ((Block)IterRpgModBlocks.ARCANE_FLOWER.get()).m_49966_();
            BlockState _bso = world.m_8055_(_bpx);
            UnmodifiableIterator _integerProp = _bso.m_61148_().entrySet().iterator();

            while (_integerProp.hasNext()) {
               Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)_integerProp.next();
               Property _property = _bsx.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
               if (_property != null && _bsx.m_61143_(_property) != null) {
                  try {
                     _bsx = (BlockState)_bsx.m_61124_(_property, entry.getValue());
                  } catch (Exception var19) {
                  }
               }
            }

            world.m_7731_(_bpx, _bsx, 3);
         } else {
            int _value = (blockstate.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _getip6 ? (Integer)blockstate.m_61143_(_getip6) : -1)
               + 1;
            BlockPos _pos = new BlockPos(x, y, z);
            BlockState _bsx = world.m_8055_(_pos);
            if (_bsx.m_60734_().m_49965_().m_61081_("stage") instanceof IntegerProperty _integerProp && _integerProp.m_6908_().contains(_value)) {
               world.m_7731_(_pos, (BlockState)_bsx.m_61124_(_integerProp, _value), 3);
            }
         }
      } else if (!world.m_5776_()) {
         BlockPos _bpx = new BlockPos(x, y, z);
         BlockEntity _blockEntityx = world.m_7702_(_bpx);
         BlockState _bsx = world.m_8055_(_bpx);
         if (_blockEntityx != null) {
            _blockEntityx.getPersistentData().m_128347_("GrowthTime", (new Object() {
               public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                  BlockEntity blockEntity = world.m_7702_(pos);
                  return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
               }
            }).getValue(world, new BlockPos(x, y, z), "GrowthTime") + 1.0);
         }

         if (world instanceof Level _level) {
            _level.m_7260_(_bpx, _bsx, _bsx, 3);
         }
      }
   }
}
