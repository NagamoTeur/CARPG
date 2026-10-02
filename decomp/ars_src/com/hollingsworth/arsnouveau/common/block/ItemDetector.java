package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.ItemDetectorTile;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ItemDetector extends TickableModBlock {
   public static final VoxelShape shape = Shapes.m_83113_(
      Block.m_49796_(3.0, 0.0, 3.0, 13.0, 2.0, 13.0), Block.m_49796_(4.0, 2.0, 4.0, 12.0, 14.0, 12.0), BooleanOp.f_82695_
   );

   public ItemDetector(Properties properties) {
      super(properties);
   }

   public ItemDetector() {
      super(defaultProperties().m_60955_());
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new ItemDetectorTile(pPos, pState);
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      ItemStack stack = player.m_21120_(handIn);
      if (handIn == InteractionHand.MAIN_HAND) {
         if (worldIn.f_46443_) {
            return InteractionResult.SUCCESS;
         }

         if (stack.m_41720_() instanceof DominionWand || !(worldIn.m_7702_(pos) instanceof ItemDetectorTile itemDetector)) {
            return super.m_6227_(state, worldIn, pos, player, handIn, hit);
         }

         if (stack.m_41619_()) {
            itemDetector.addCount(player.m_6144_() ? 8 : 1);
         } else if (!stack.m_41619_()) {
            itemDetector.setFilterStack(stack.m_41777_());
         }
      }

      return InteractionResult.SUCCESS;
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   public void m_6256_(BlockState state, Level level, BlockPos pos, Player player) {
      if (!level.f_46443_ && level.m_7702_(pos) instanceof ItemDetectorTile tile) {
         tile.addCount(player.m_6144_() ? -8 : -1);
      }
   }

   public int m_6378_(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
      if (pBlockAccess.m_7702_(pPos) instanceof ItemDetectorTile detectorTile) {
         return detectorTile.getPoweredState() ? 15 : 0;
      } else {
         return 0;
      }
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
