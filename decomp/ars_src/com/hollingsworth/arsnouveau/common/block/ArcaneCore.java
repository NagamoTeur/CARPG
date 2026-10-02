package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.ArcaneCoreTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ArcaneCore extends ModBlock implements EntityBlock {
   public ArcaneCore() {
      super(defaultProperties().m_60955_().m_60953_(state -> 15));
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new ArcaneCoreTile(pos, state);
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }
}
