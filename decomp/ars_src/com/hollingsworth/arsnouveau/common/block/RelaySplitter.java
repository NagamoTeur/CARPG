package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.RelaySplitterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RelaySplitter extends Relay {
   public RelaySplitter() {
      super(defaultProperties().m_60953_(state -> 8).m_60955_());
   }

   @Override
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new RelaySplitterTile(pos, state);
   }
}
