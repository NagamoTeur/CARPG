package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.RelayCollectorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RelayCollectorBlock extends Relay {
   @Override
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new RelayCollectorTile(pos, state);
   }
}
