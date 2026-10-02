package io.redspace.ironsspellbooks.block.pedestal;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PedestalBlock extends BaseEntityBlock {
   public static final VoxelShape SHAPE_COLUMN = Block.m_49796_(3.0, 4.0, 3.0, 13.0, 12.0, 13.0);
   public static final VoxelShape SHAPE_BOTTOM = Block.m_49796_(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);
   public static final VoxelShape SHAPE_TOP = Block.m_49796_(0.0, 12.0, 0.0, 16.0, 16.0, 16.0);
   public static final VoxelShape SHAPE = Shapes.m_83124_(SHAPE_BOTTOM, new VoxelShape[]{SHAPE_TOP, SHAPE_COLUMN});
   public static final List<BlockPos> BOOKSHELF_OFFSETS = BlockPos.m_121886_(-3, -1, -3, 3, 1, 3)
      .filter(blockPos -> Math.abs(blockPos.m_123341_()) == 3 || Math.abs(blockPos.m_123343_()) == 3)
      .<BlockPos>map(BlockPos::m_7949_)
      .toList();

   public PedestalBlock() {
      super(Properties.m_60926_(Blocks.f_50729_).m_60955_());
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return SHAPE;
   }

   public InteractionResult m_6227_(BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
      if (!pLevel.m_5776_() && pLevel.m_7702_(pos) instanceof PedestalTile pedestalTile) {
         ItemStack currentPedestalItem = pedestalTile.getHeldItem();
         ItemStack handItem = player.m_21120_(hand);
         ItemStack playerItem = currentPedestalItem.m_41777_();
         if (!handItem.m_41619_() && handItem.m_41613_() != 1) {
            this.dropItem(playerItem, player);
         } else {
            player.m_21008_(hand, playerItem);
         }

         pedestalTile.setHeldItem(ItemStack.f_41583_);
         currentPedestalItem = handItem.m_41777_();
         if (!currentPedestalItem.m_41619_()) {
            currentPedestalItem.m_41764_(1);
            pedestalTile.setHeldItem(currentPedestalItem);
            handItem.m_41774_(1);
         }

         pLevel.m_7260_(pos, state, state, 2);
      }

      return InteractionResult.m_19078_(pLevel.m_5776_());
   }

   public void m_214162_(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
      super.m_214162_(pState, pLevel, pPos, pRandom);

      for (BlockPos blockpos : BOOKSHELF_OFFSETS) {
         if (pRandom.m_188503_(16) == 0 && pLevel.m_8055_(pPos.m_121955_(blockpos)).m_204336_(net.minecraftforge.common.Tags.Blocks.BOOKSHELVES)) {
            pLevel.m_7106_(
               ParticleTypes.f_123809_,
               (double)pPos.m_123341_() + 0.5,
               (double)pPos.m_123342_() + 2.0,
               (double)pPos.m_123343_() + 0.5,
               (double)((float)blockpos.m_123341_() + pRandom.m_188501_()) - 0.5,
               (double)((float)blockpos.m_123342_() - pRandom.m_188501_() - 1.0F),
               (double)((float)blockpos.m_123343_() + pRandom.m_188501_()) - 0.5
            );
         }
      }
   }

   private void dropItem(ItemStack itemstack, Player owner) {
      if (owner instanceof ServerPlayer serverplayer) {
         ItemEntity itementity = serverplayer.m_36176_(itemstack, false);
         if (itementity != null) {
            itementity.m_32061_();
            itementity.m_32047_(serverplayer.m_20148_());
         }
      }
   }

   public void m_6810_(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
      if (pState.m_60734_() != pNewState.m_60734_()) {
         BlockEntity blockEntity = pLevel.m_7702_(pPos);
         if (blockEntity instanceof PedestalTile) {
            ((PedestalTile)blockEntity).drops();
         }
      }

      super.m_6810_(pState, pLevel, pPos, pNewState, pIsMoving);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new PedestalTile(pos, state);
   }

   public RenderShape m_7514_(BlockState blockState) {
      return RenderShape.MODEL;
   }
}
