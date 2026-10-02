package com.github.L_Ender.cataclysm.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class FacingPillarBlock extends Block {
   public static final BooleanProperty TOP = BooleanProperty.m_61465_("top");
   public static final DirectionProperty FACING = BlockStateProperties.f_61372_;

   public FacingPillarBlock(Properties p_49046_) {
      super(p_49046_);
      this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_(TOP, true)).m_61124_(FACING, Direction.UP));
   }

   public BlockState m_7417_(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos1) {
      BlockState pillar = super.m_7417_(state, direction, state1, levelAccessor, blockPos, blockPos1);
      if (levelAccessor.m_8055_(blockPos.m_121945_((Direction)state.m_61143_(FACING))).m_60734_() instanceof FacingPillarBlock) {
         pillar = (BlockState)pillar.m_61124_(TOP, false);
      } else {
         pillar = (BlockState)pillar.m_61124_(TOP, true);
      }

      return pillar;
   }

   public BlockState m_6843_(BlockState p_49085_, Rotation p_49086_) {
      return (BlockState)p_49085_.m_61124_(FACING, p_49086_.m_55954_((Direction)p_49085_.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState p_49082_, Mirror p_49083_) {
      return p_49082_.m_60717_(p_49083_.m_54846_((Direction)p_49082_.m_61143_(FACING)));
   }

   protected void m_7926_(Builder<Block, BlockState> p_49088_) {
      p_49088_.m_61104_(new Property[]{TOP, FACING});
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      LevelAccessor levelaccessor = context.m_43725_();
      BlockPos blockpos = context.m_8083_();
      BlockState above = levelaccessor.m_8055_(blockpos.m_121945_(context.m_7820_().m_122424_()));
      return (BlockState)((BlockState)this.m_49966_().m_61124_(TOP, !(above.m_60734_() instanceof FacingPillarBlock)))
         .m_61124_(FACING, context.m_7820_().m_122424_());
   }
}
