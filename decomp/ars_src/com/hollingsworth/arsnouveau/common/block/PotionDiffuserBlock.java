package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.PotionDiffuserTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

public class PotionDiffuserBlock extends TickableModBlock {
   public PotionDiffuserBlock() {
      super(defaultProperties().m_60955_());
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new PotionDiffuserTile(pPos, pState);
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
