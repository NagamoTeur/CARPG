package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.MirrorWeaveTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MirrorWeave extends ModBlock implements EntityBlock {
   public static final Property<Integer> LIGHT_LEVEL = IntegerProperty.m_61631_("level", 0, 15);

   public MirrorWeave(Properties properties) {
      super(properties.m_60953_(b -> (Integer)b.m_61143_(LIGHT_LEVEL)));
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (!pLevel.f_46443_ && pHand == InteractionHand.MAIN_HAND) {
         MirrorWeaveTile tile = (MirrorWeaveTile)pLevel.m_7702_(pPos);
         if (tile != null) {
            ItemStack stack = pPlayer.m_21120_(pHand);
            if (stack.m_41720_() instanceof BlockItem blockItem && !(blockItem.m_40614_() instanceof EntityBlock)) {
               if (tile.mimicState.m_60713_(blockItem.m_40614_())) {
                  return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
               }

               tile.nextState = blockItem.m_40614_().m_5573_(new BlockPlaceContext(pLevel, pPlayer, pHand, stack, pHit));
               this.setMimicState(pLevel, pPos, !pPlayer.m_6144_());
               return InteractionResult.SUCCESS;
            }
         }

         return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   public void setMimicState(Level level, BlockPos pos, boolean updateNeighbors) {
      MirrorWeaveTile tile = (MirrorWeaveTile)level.m_7702_(pos);
      if (tile != null && tile.mimicState != null && tile.nextState != null && !tile.nextState.equals(tile.mimicState)) {
         BlockState previousState = tile.mimicState;
         tile.mimicState = tile.nextState;
         level.m_46597_(pos, (BlockState)tile.m_58900_().m_61124_(LIGHT_LEVEL, tile.mimicState.getLightEmission(level, pos)));
         tile.updateBlock();
         int ticks = 1;
         if (updateNeighbors) {
            for (Direction d : Direction.values()) {
               BlockPos offset = pos.m_121945_(d);
               BlockEntity var13 = level.m_7702_(offset);
               if (var13 instanceof MirrorWeaveTile) {
                  MirrorWeaveTile neighbor = (MirrorWeaveTile)var13;
                  if (neighbor.mimicState == previousState) {
                     neighbor.nextState = tile.mimicState;
                     level.m_186460_(offset, neighbor.m_58900_().m_60734_(), ticks++);
                  }
               }
            }
         }
      }
   }

   public void m_213897_(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
      MirrorWeaveTile tile = (MirrorWeaveTile)pLevel.m_7702_(pPos);
      if (tile != null) {
         this.setMimicState(pLevel, pPos, true);
      }
   }

   public VoxelShape m_6079_(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      if (pLevel.m_7702_(pPos) instanceof MirrorWeaveTile tile && tile.mimicState.m_60734_() != this) {
         return tile.mimicState.m_60820_(pLevel, pPos);
      }

      return super.m_6079_(pState, pLevel, pPos);
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      if (pLevel.m_7702_(pPos) instanceof MirrorWeaveTile tile && tile.mimicState.m_60734_() != this) {
         return tile.mimicState.m_60808_(pLevel, pPos);
      }

      return super.m_5940_(pState, pLevel, pPos, pContext);
   }

   public VoxelShape m_5939_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      if (pLevel.m_7702_(pPos) instanceof MirrorWeaveTile tile && tile.mimicState.m_60734_() != this) {
         return tile.mimicState.m_60742_(pLevel, pPos, pContext);
      }

      return super.m_5939_(pState, pLevel, pPos, pContext);
   }

   public boolean m_180643_(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      if (pLevel.m_7702_(pPos) instanceof MirrorWeaveTile tile && tile.mimicState.m_60734_() != this) {
         return tile.mimicState != null && tile.mimicState.m_60838_(pLevel, pPos);
      }

      return super.m_180643_(pState, pLevel, pPos);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new MirrorWeaveTile(pPos, pState);
   }

   public RenderShape m_7514_(BlockState pState) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{LIGHT_LEVEL});
   }
}
