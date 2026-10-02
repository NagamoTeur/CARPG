package net.thirdlife.iterrpg.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class VoidQuartzBlockCheckProcedure {
   public static boolean execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
      return world.m_8055_(new BlockPos(x - (double)(new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate).m_122429_(), y - (double)(new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate).m_122430_(), z - (double)(new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate).m_122431_())).m_60783_(world, new BlockPos(x - (double)(new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate).m_122429_(), y - (double)(new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate).m_122430_(), z - (double)(new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate).m_122431_()), (new Object() {
         public Direction getDirection(BlockState _bs) {
            if (_bs.m_60734_().m_49965_().m_61081_("facing") instanceof DirectionProperty _dp) {
               return (Direction)_bs.m_61143_(_dp);
            } else {
               if (_bs.m_60734_().m_49965_().m_61081_("axis") instanceof EnumProperty _ep && _ep.m_6908_().toArray()[0] instanceof Axis) {
                  return Direction.m_122387_((Axis)_bs.m_61143_(_ep), AxisDirection.POSITIVE);
               }

               return Direction.NORTH;
            }
         }
      }).getDirection(blockstate));
   }
}
