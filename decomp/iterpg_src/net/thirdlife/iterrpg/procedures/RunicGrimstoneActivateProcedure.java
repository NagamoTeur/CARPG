package net.thirdlife.iterrpg.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

public class RunicGrimstoneActivateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z) {
      double distance = 0.0;
      boolean flag = false;
      boolean wallmeet = false;
      boolean up = false;
      boolean down = false;
      if (world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
         || world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
         || world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
         || world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
         || world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()
         || world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.MAGMANUM_BLOCK.get()) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator _level = _bso.m_61148_().entrySet().iterator();

         while (_level.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)_level.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var26) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntity = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntity != null) {
               _blockEntity.getPersistentData().m_128347_("demoncharge", 64.0);
            }

            if (world instanceof Level _levelx) {
               _levelx.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }

      if (world.m_8055_(new BlockPos(x + 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x + 1.0, y, z), "demoncharge") > 1.0) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator var67 = _bso.m_61148_().entrySet().iterator();

         while (var67.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var67.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var25) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntityx = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntityx != null) {
               _blockEntityx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x + 1.0, y, z), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }

      if (world.m_8055_(new BlockPos(x - 1.0, y, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x - 1.0, y, z), "demoncharge") > 1.0) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator var69 = _bso.m_61148_().entrySet().iterator();

         while (var69.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var69.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var24) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntityxx = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntityxx != null) {
               _blockEntityxx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x - 1.0, y, z), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }

      if (world.m_8055_(new BlockPos(x, y, z + 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z + 1.0), "demoncharge") > 1.0) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator var71 = _bso.m_61148_().entrySet().iterator();

         while (var71.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var71.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var23) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntityxxx = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntityxxx != null) {
               _blockEntityxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z + 1.0), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }

      if (world.m_8055_(new BlockPos(x, y, z - 1.0)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y, z - 1.0), "demoncharge") > 1.0) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator var73 = _bso.m_61148_().entrySet().iterator();

         while (var73.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var73.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var22) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntityxxxx = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntityxxxx != null) {
               _blockEntityxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y, z - 1.0), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }

      if (world.m_8055_(new BlockPos(x, y + 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y + 1.0, z), "demoncharge") > 1.0) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator var75 = _bso.m_61148_().entrySet().iterator();

         while (var75.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var75.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var21) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntityxxxxx = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntityxxxxx != null) {
               _blockEntityxxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y + 1.0, z), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }

      if (world.m_8055_(new BlockPos(x, y - 1.0, z)).m_60734_() == IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get() && (new Object() {
         public double getValue(LevelAccessor world, BlockPos pos, String tag) {
            BlockEntity blockEntity = world.m_7702_(pos);
            return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
         }
      }).getValue(world, new BlockPos(x, y - 1.0, z), "demoncharge") > 1.0) {
         BlockPos _bp = new BlockPos(x, y, z);
         BlockState _bs = ((Block)IterRpgModBlocks.RUNIC_GRIMSTONE_ACTIVE.get()).m_49966_();
         BlockState _bso = world.m_8055_(_bp);
         UnmodifiableIterator var77 = _bso.m_61148_().entrySet().iterator();

         while (var77.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var77.next();
            Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
            if (_property != null && _bs.m_61143_(_property) != null) {
               try {
                  _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
               } catch (Exception var20) {
               }
            }
         }

         world.m_7731_(_bp, _bs, 3);
         if (!world.m_5776_()) {
            _bp = new BlockPos(x, y, z);
            BlockEntity _blockEntityxxxxxx = world.m_7702_(_bp);
            _bso = world.m_8055_(_bp);
            if (_blockEntityxxxxxx != null) {
               _blockEntityxxxxxx.getPersistentData().m_128347_("demoncharge", (new Object() {
                  public double getValue(LevelAccessor world, BlockPos pos, String tag) {
                     BlockEntity blockEntity = world.m_7702_(pos);
                     return blockEntity != null ? blockEntity.getPersistentData().m_128459_(tag) : -1.0;
                  }
               }).getValue(world, new BlockPos(x, y - 1.0, z), "demoncharge") - 1.0);
            }

            if (world instanceof Level _level) {
               _level.m_7260_(_bp, _bso, _bso, 3);
            }
         }
      }
   }
}
