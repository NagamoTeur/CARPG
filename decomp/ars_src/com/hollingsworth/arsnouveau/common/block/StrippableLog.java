package com.hollingsworth.arsnouveau.common.block;

import java.util.function.Supplier;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

public class StrippableLog extends RotatedPillarBlock {
   Supplier<Block> strippedState;

   public StrippableLog(Properties properties, Supplier<Block> stateSupplier) {
      super(properties);
      this.strippedState = stateSupplier;
   }

   @Nullable
   public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
      return toolAction == ToolActions.AXE_STRIP
         ? (BlockState)this.strippedState.get().m_49966_().m_61124_(RotatedPillarBlock.f_55923_, (Axis)state.m_61143_(RotatedPillarBlock.f_55923_))
         : null;
   }
}
