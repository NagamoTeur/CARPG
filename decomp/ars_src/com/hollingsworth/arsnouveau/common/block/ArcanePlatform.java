package com.hollingsworth.arsnouveau.common.block;

import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArcanePlatform extends ArcanePedestal {
   public static final VoxelShape UP = Stream.of(
         Block.m_49796_(2.0, 3.0, 2.0, 14.0, 5.0, 14.0),
         Block.m_49796_(5.0, 0.0, 5.0, 11.0, 3.0, 11.0),
         Block.m_49796_(7.0, 0.0, 1.0, 9.0, 3.0, 5.0),
         Block.m_49796_(1.0, 0.0, 7.0, 5.0, 3.0, 9.0),
         Block.m_49796_(7.0, 0.0, 11.0, 9.0, 3.0, 15.0),
         Block.m_49796_(11.0, 0.0, 7.0, 15.0, 3.0, 9.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final VoxelShape DOWN = Stream.of(
         Block.m_49796_(2.0, 11.0, 2.0, 14.0, 13.0, 14.0),
         Block.m_49796_(5.0, 13.0, 5.0, 11.0, 16.0, 11.0),
         Block.m_49796_(7.0, 13.0, 1.0, 9.0, 16.0, 5.0),
         Block.m_49796_(11.0, 13.0, 7.0, 15.0, 16.0, 9.0),
         Block.m_49796_(7.0, 13.0, 11.0, 9.0, 16.0, 15.0),
         Block.m_49796_(1.0, 13.0, 7.0, 5.0, 16.0, 9.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final VoxelShape EAST = Stream.of(
         Block.m_49796_(3.0, 2.0, 2.0, 5.0, 14.0, 14.0),
         Block.m_49796_(0.0, 5.0, 5.0, 3.0, 11.0, 11.0),
         Block.m_49796_(0.0, 7.0, 11.0, 3.0, 9.0, 15.0),
         Block.m_49796_(0.0, 1.0, 7.0, 3.0, 5.0, 9.0),
         Block.m_49796_(0.0, 7.0, 1.0, 3.0, 9.0, 5.0),
         Block.m_49796_(0.0, 11.0, 7.0, 3.0, 15.0, 9.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final VoxelShape WEST = Stream.of(
         Block.m_49796_(11.0, 2.0, 2.0, 13.0, 14.0, 14.0),
         Block.m_49796_(13.0, 5.0, 5.0, 16.0, 11.0, 11.0),
         Block.m_49796_(13.0, 7.0, 1.0, 16.0, 9.0, 5.0),
         Block.m_49796_(13.0, 1.0, 7.0, 16.0, 5.0, 9.0),
         Block.m_49796_(13.0, 7.0, 11.0, 16.0, 9.0, 15.0),
         Block.m_49796_(13.0, 11.0, 7.0, 16.0, 15.0, 9.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final VoxelShape NORTH = Stream.of(
         Block.m_49796_(2.0, 2.0, 11.0, 14.0, 14.0, 13.0),
         Block.m_49796_(5.0, 5.0, 13.0, 11.0, 11.0, 16.0),
         Block.m_49796_(11.0, 7.0, 13.0, 15.0, 9.0, 16.0),
         Block.m_49796_(7.0, 1.0, 13.0, 9.0, 5.0, 16.0),
         Block.m_49796_(1.0, 7.0, 13.0, 5.0, 9.0, 16.0),
         Block.m_49796_(7.0, 11.0, 13.0, 9.0, 15.0, 16.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final VoxelShape SOUTH = Stream.of(
         Block.m_49796_(2.0, 2.0, 3.0, 14.0, 14.0, 5.0),
         Block.m_49796_(5.0, 5.0, 0.0, 11.0, 11.0, 3.0),
         Block.m_49796_(1.0, 7.0, 0.0, 5.0, 9.0, 3.0),
         Block.m_49796_(7.0, 1.0, 0.0, 9.0, 5.0, 3.0),
         Block.m_49796_(11.0, 7.0, 0.0, 15.0, 9.0, 3.0),
         Block.m_49796_(7.0, 11.0, 0.0, 9.0, 15.0, 3.0)
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();

   public ArcanePlatform() {
      this.m_49959_(
         (BlockState)((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, false)).m_61124_(BlockStateProperties.f_61372_, Direction.NORTH)
      );
   }

   @Nullable
   @Override
   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61372_, context.m_43719_());
   }

   @Override
   protected void m_7926_(Builder<Block, BlockState> builder) {
      super.m_7926_(builder);
      builder.m_61104_(new Property[]{BlockStateProperties.f_61372_});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(BlockStateProperties.f_61372_, rot.m_55954_((Direction)state.m_61143_(BlockStateProperties.f_61372_)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(BlockStateProperties.f_61372_)));
   }

   @Override
   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      Direction facing = (Direction)state.m_61143_(BlockStateProperties.f_61372_);
      switch (facing) {
         case UP:
            return UP;
         case DOWN:
            return DOWN;
         case EAST:
            return EAST;
         case WEST:
            return WEST;
         case NORTH:
            return NORTH;
         case SOUTH:
            return SOUTH;
         default:
            return UP;
      }
   }
}
