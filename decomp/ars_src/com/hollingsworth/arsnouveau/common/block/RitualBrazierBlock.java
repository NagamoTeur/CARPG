package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.common.block.tile.RitualBrazierTile;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RitualBrazierBlock extends TickableModBlock {
   public static VoxelShape shape = Stream.of(
         Block.m_49796_(3.0, 12.0, 3.0, 13.0, 15.0, 13.0),
         Block.m_49796_(6.0, 0.0, 6.0, 10.0, 12.0, 10.0),
         Stream.of(
               Block.m_49796_(2.0, 14.0, 2.0, 11.0, 16.0, 5.0),
               Block.m_49796_(7.0, 11.0, 1.0, 9.0, 15.0, 6.0),
               Block.m_49796_(7.0, 0.0, 0.0, 9.0, 5.0, 4.0),
               Block.m_49796_(7.0, 0.0, 4.0, 9.0, 11.0, 6.0)
            )
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get(),
         Stream.of(
               Block.m_49796_(2.0, 14.0, 5.0, 5.0, 16.0, 14.0),
               Block.m_49796_(1.0, 11.0, 7.0, 6.0, 15.0, 9.0),
               Block.m_49796_(0.0, 0.0, 7.0, 4.0, 5.0, 9.0),
               Block.m_49796_(4.0, 0.0, 7.0, 6.0, 11.0, 9.0)
            )
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get(),
         Stream.of(
               Block.m_49796_(5.0, 14.0, 11.0, 14.0, 16.0, 14.0),
               Block.m_49796_(7.0, 11.0, 10.0, 9.0, 15.0, 15.0),
               Block.m_49796_(7.0, 0.0, 12.0, 9.0, 5.0, 16.0),
               Block.m_49796_(7.0, 0.0, 10.0, 9.0, 11.0, 12.0)
            )
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get(),
         Stream.of(
               Block.m_49796_(11.0, 14.0, 2.0, 14.0, 16.0, 11.0),
               Block.m_49796_(10.0, 11.0, 7.0, 15.0, 15.0, 9.0),
               Block.m_49796_(12.0, 0.0, 7.0, 16.0, 5.0, 9.0),
               Block.m_49796_(10.0, 0.0, 7.0, 12.0, 11.0, 9.0)
            )
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get()
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();
   public static final Property<Boolean> LIT = BooleanProperty.m_61465_("lit");

   public RitualBrazierBlock() {
      super(defaultProperties().m_60955_().m_60953_(b -> b.m_61143_(LIT) ? 15 : 0));
      this.m_49959_((BlockState)this.m_49966_().m_61124_(LIT, false));
   }

   public InteractionResult m_6227_(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (worldIn.m_7702_(pos) instanceof RitualBrazierTile tile && handIn == InteractionHand.MAIN_HAND) {
         ItemStack heldStack = player.m_21205_();
         if (heldStack.m_41619_() && tile.ritual != null && !tile.isRitualDone()) {
            tile.startRitual();
         }

         if (!heldStack.m_41619_()) {
            tile.tryBurnStack(heldStack);
         }

         return super.m_6227_(state, worldIn, pos, player, handIn, hit);
      }

      return super.m_6227_(state, worldIn, pos, player, handIn, hit);
   }

   public PushReaction m_5537_(BlockState p_149656_1_) {
      return PushReaction.BLOCK;
   }

   public void m_6861_(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
      super.m_6861_(state, world, pos, blockIn, fromPos, isMoving);
      if (!world.m_5776_() && world.m_7702_(pos) instanceof RitualBrazierTile tile) {
         tile.isOff = world.m_46753_(pos);
         if (world.m_46753_(pos) && tile.ritual != null && tile.canRitualStart()) {
            tile.startRitual();
         }

         BlockUtil.safelyUpdateState(world, pos);
      }
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
      if (worldIn.m_7702_(pos) instanceof RitualBrazierTile tile && tile.ritual != null && !tile.ritual.isRunning() && !tile.ritual.isDone()) {
         worldIn.m_7967_(
            new ItemEntity(
               worldIn,
               (double)pos.m_123341_(),
               (double)pos.m_123342_(),
               (double)pos.m_123343_(),
               new ItemStack((ItemLike)ArsNouveauAPI.getInstance().getRitualItemMap().get(tile.ritual.getRegistryName()))
            )
         );
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{LIT});
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new RitualBrazierTile(pos, state);
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      return shape;
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
