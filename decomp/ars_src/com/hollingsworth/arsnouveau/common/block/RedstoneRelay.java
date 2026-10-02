package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.RedstoneRelayTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

public class RedstoneRelay extends TickableModBlock implements EntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
   public static final IntegerProperty POWER = BlockStateProperties.f_61426_;

   public RedstoneRelay() {
      this(defaultProperties().m_60955_());
   }

   public RedstoneRelay(Properties p_49795_) {
      super(p_49795_);
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(POWER, 0));
   }

   public int m_6378_(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
      Direction facing = (Direction)pBlockState.m_61143_(FACING);
      return pSide != facing.m_122424_() && pBlockAccess.m_7702_(pPos) instanceof RedstoneRelayTile redstoneRelayTile ? redstoneRelayTile.getOutputPower() : 0;
   }

   public int m_6376_(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
      return this.m_6378_(pState, pLevel, pPos, pDirection);
   }

   public boolean m_7899_(BlockState pState) {
      return true;
   }

   public void m_6861_(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
      super.m_6861_(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
      this.updatePower(pLevel, pPos, pState);
   }

   public void updatePower(Level pLevel, BlockPos pPos, BlockState pState) {
      int power = 0;
      Direction direction = (Direction)pState.m_61143_(FACING);
      power = pLevel.m_46681_(pPos.m_121945_((Direction)pState.m_61143_(FACING)), direction);
      if (pLevel.m_7702_(pPos) instanceof RedstoneRelayTile redstoneRelayTile) {
         redstoneRelayTile.setLocalPower(power);
      }
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public void m_6810_(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
      if (!pState.m_60713_(pNewState.m_60734_())) {
         if (pLevel.f_46443_) {
            super.m_6810_(pState, pLevel, pPos, pNewState, pIsMoving);
         } else if (pLevel.m_7702_(pPos) instanceof RedstoneRelayTile thisTile) {
            BlockPos var12 = pPos.m_7949_();

            for (BlockPos pos : thisTile.powering) {
               if (pLevel.m_7702_(pos) instanceof RedstoneRelayTile redstoneRelayTile) {
                  redstoneRelayTile.onParentRemoved(var12);
               }
            }

            super.m_6810_(pState, pLevel, pPos, pNewState, pIsMoving);
         }
      }
   }

   public void m_6807_(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
      super.m_6807_(pState, pLevel, pPos, pOldState, pIsMoving);
      this.updatePower(pLevel, pPos, pState);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new RedstoneRelayTile(pPos, pState);
   }

   public BlockState m_5573_(BlockPlaceContext pContext) {
      return (BlockState)this.m_49966_().m_61124_(FACING, pContext.m_8125_().m_122424_());
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, POWER});
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(HorizontalDirectionalBlock.f_54117_, rot.m_55954_((Direction)state.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
