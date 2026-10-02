package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.SourceJar;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SourceJarTile extends AbstractSourceMachine implements ITooltipProvider, ITickable {
   public SourceJarTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.SOURCE_JAR_TILE, pos, state);
   }

   public SourceJarTile(BlockEntityType<? extends SourceJarTile> tileTileEntityType, BlockPos pos, BlockState state) {
      super(tileTileEntityType, pos, state);
   }

   @Override
   public int getMaxSource() {
      return 10000;
   }

   @Override
   public boolean updateBlock() {
      super.updateBlock();
      BlockState state = this.f_58857_.m_8055_(this.f_58858_);
      int fillState = 0;
      if (this.getSource() > 0 && this.getSource() < 1000) {
         fillState = 1;
      } else if (this.getSource() != 0) {
         fillState = this.getSource() / 1000 + 1;
      }

      if (state.m_61138_(SourceJar.fill)) {
         this.f_58857_.m_7731_(this.f_58858_, (BlockState)state.m_61124_(SourceJar.fill, fillState), 3);
      }

      return true;
   }

   @Override
   public int getTransferRate() {
      return this.getMaxSource();
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      tooltip.add(Component.m_237110_("ars_nouveau.source_jar.fullness", new Object[]{this.getSource() * 100 / this.getMaxSource()}));
   }
}
