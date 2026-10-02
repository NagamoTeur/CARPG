package com.hollingsworth.arsnouveau.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;

public class DirectionalModBlock extends ModBlock {
   public DirectionalModBlock(Properties properties) {
      super(properties);
      this.m_49959_(this.m_49966_());
   }

   public DirectionalModBlock() {
      this(defaultProperties());
   }

   public BlockState m_6843_(BlockState pState, Rotation pRot) {
      return (BlockState)pState.m_61124_(HorizontalDirectionalBlock.f_54117_, pRot.m_55954_((Direction)pState.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }

   @Deprecated
   public BlockState m_6943_(BlockState pState, Mirror pMirror) {
      return pState.m_60717_(pMirror.m_54846_((Direction)pState.m_61143_(HorizontalDirectionalBlock.f_54117_)));
   }

   public BlockState m_5573_(BlockPlaceContext pContext) {
      return (BlockState)this.m_49966_().m_61124_(HorizontalDirectionalBlock.f_54117_, pContext.m_8125_());
   }

   protected void m_7926_(Builder<Block, BlockState> pBuilder) {
      super.m_7926_(pBuilder);
      pBuilder.m_61104_(new Property[]{HorizontalDirectionalBlock.f_54117_});
   }
}
