package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.FalseWeaveTile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FalseWeave extends MirrorWeave {
   public FalseWeave(Properties properties) {
      super(properties);
   }

   @Override
   public VoxelShape m_5939_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return Shapes.m_83040_();
   }

   @Nullable
   @Override
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new FalseWeaveTile(pPos, pState);
   }
}
