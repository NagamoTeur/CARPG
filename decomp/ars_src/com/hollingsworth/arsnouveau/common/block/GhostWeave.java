package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.GhostWeaveTile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GhostWeave extends MirrorWeave {
   public GhostWeave(Properties properties) {
      super(properties);
   }

   @Nullable
   @Override
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new GhostWeaveTile(pPos, pState);
   }
}
