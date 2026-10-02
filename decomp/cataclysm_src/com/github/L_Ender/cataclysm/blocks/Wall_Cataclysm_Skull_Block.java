package com.github.L_Ender.cataclysm.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Wall_Cataclysm_Skull_Block extends Abstract_Cataclysm_Skull_Block {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.m_49796_(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         Direction.SOUTH,
         Block.m_49796_(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         Direction.EAST,
         Block.m_49796_(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         Direction.WEST,
         Block.m_49796_(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );

   public Wall_Cataclysm_Skull_Block(Cataclysm_Skull_Block.Type p_58101_, Properties p_58102_) {
      super(p_58101_, p_58102_);
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH));
   }

   public String m_7705_() {
      return this.m_5456_().m_5524_();
   }

   public VoxelShape m_5940_(BlockState p_58114_, BlockGetter p_58115_, BlockPos p_58116_, CollisionContext p_58117_) {
      return AABBS.get(p_58114_.m_61143_(FACING));
   }

   public BlockState m_5573_(BlockPlaceContext p_58104_) {
      BlockState blockstate = this.m_49966_();
      BlockGetter blockgetter = p_58104_.m_43725_();
      BlockPos blockpos = p_58104_.m_8083_();
      Direction[] adirection = p_58104_.m_6232_();

      for (Direction direction : adirection) {
         if (direction.m_122434_().m_122479_()) {
            Direction direction1 = direction.m_122424_();
            blockstate = (BlockState)blockstate.m_61124_(FACING, direction1);
            if (!blockgetter.m_8055_(blockpos.m_121945_(direction)).m_60629_(p_58104_)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState m_6843_(BlockState p_58109_, Rotation p_58110_) {
      return (BlockState)p_58109_.m_61124_(FACING, p_58110_.m_55954_((Direction)p_58109_.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState p_58106_, Mirror p_58107_) {
      return p_58106_.m_60717_(p_58107_.m_54846_((Direction)p_58106_.m_61143_(FACING)));
   }

   protected void m_7926_(Builder<Block, BlockState> p_58112_) {
      p_58112_.m_61104_(new Property[]{FACING});
   }
}
