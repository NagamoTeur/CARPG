package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.RelayDepositTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RelayDepositBlock extends Relay {
   public RelayDepositBlock(String registryName) {
   }

   public RelayDepositBlock() {
   }

   @Override
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new RelayDepositTile(pos, state);
   }
}
