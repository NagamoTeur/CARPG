package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.RepositoryTile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RepositoryBlock extends ModBlock implements EntityBlock {
   public RepositoryBlock() {
      super(ModBlock.defaultProperties().m_60955_());
   }

   public void m_6402_(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
      if (pLevel.m_7702_(pPos) instanceof RepositoryTile tile) {
         if (pStack.m_41788_()) {
            tile.m_58638_(pStack.m_41786_());
         }

         tile.configuration = pLevel.f_46441_.m_188503_(RepositoryTile.CONFIGURATIONS.length);
         tile.updateBlock();
      }
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         if (pLevel.m_7702_(pPos) instanceof RepositoryTile tile) {
            pPlayer.m_5893_(tile);
            pPlayer.m_36220_(Stats.f_12968_);
            PiglinAi.m_34873_(pPlayer, true);
         }

         return InteractionResult.CONSUME;
      }
   }

   public void m_6810_(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
      if (!pState.m_60713_(pNewState.m_60734_())) {
         if (pLevel.m_7702_(pPos) instanceof Container container) {
            Containers.m_19002_(pLevel, pPos, container);
            pLevel.m_46717_(pPos, this);
         }

         super.m_6810_(pState, pLevel, pPos, pNewState, pIsMoving);
      }
   }

   public RenderShape m_7514_(BlockState pState) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @org.jetbrains.annotations.Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new RepositoryTile(pPos, pState);
   }

   public boolean m_7278_(BlockState pState) {
      return true;
   }

   public int m_6782_(BlockState pBlockState, Level pLevel, BlockPos pPos) {
      return AbstractContainerMenu.m_38918_(pLevel.m_7702_(pPos));
   }
}
