package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ArcanePedestal extends ModBlock implements EntityBlock, SimpleWaterloggedBlock {
   public static final VoxelShape shape = Stream.of(
         Block.m_49796_(2.0, 11.0, 2.0, 14.0, 13.0, 14.0),
         Block.m_49796_(5.0, 0.0, 5.0, 11.0, 3.0, 11.0),
         Block.m_49796_(5.0, 8.0, 5.0, 11.0, 11.0, 11.0),
         Block.m_49796_(6.0, 3.0, 6.0, 10.0, 8.0, 10.0),
         Stream.of(Block.m_49796_(7.0, 8.0, 1.0, 9.0, 11.0, 5.0), Block.m_49796_(7.0, 3.0, 3.0, 9.0, 8.0, 5.0), Block.m_49796_(7.0, 0.0, 1.0, 9.0, 3.0, 5.0))
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get(),
         Stream.of(Block.m_49796_(1.0, 8.0, 7.0, 5.0, 11.0, 9.0), Block.m_49796_(3.0, 3.0, 7.0, 5.0, 8.0, 9.0), Block.m_49796_(1.0, 0.0, 7.0, 5.0, 3.0, 9.0))
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get(),
         Stream.of(
               Block.m_49796_(7.0, 8.0, 11.0, 9.0, 11.0, 15.0), Block.m_49796_(7.0, 3.0, 11.0, 9.0, 8.0, 13.0), Block.m_49796_(7.0, 0.0, 11.0, 9.0, 3.0, 15.0)
            )
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get(),
         Stream.of(
               Block.m_49796_(11.0, 8.0, 7.0, 15.0, 11.0, 9.0), Block.m_49796_(11.0, 3.0, 7.0, 13.0, 8.0, 9.0), Block.m_49796_(11.0, 0.0, 7.0, 15.0, 3.0, 9.0)
            )
            .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
            .get()
      )
      .reduce((v1, v2) -> Shapes.m_83113_(v1, v2, BooleanOp.f_82695_))
      .get();

   public ArcanePedestal() {
      super(ModBlock.defaultProperties().m_60955_());
      this.m_49959_((BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, false));
   }

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (handIn != InteractionHand.MAIN_HAND) {
         return InteractionResult.PASS;
      } else {
         if (!world.f_46443_ && world.m_7702_(pos) instanceof ArcanePedestalTile tile) {
            if (tile.getStack() != null && player.m_21120_(handIn).m_41619_()) {
               ItemEntity item = new ItemEntity(world, player.m_20185_(), player.m_20186_(), player.m_20189_(), tile.getStack());
               world.m_7967_(item);
               tile.setStack(ItemStack.f_41583_);
            } else if (!player.m_150109_().m_36056_().m_41619_()) {
               if (tile.getStack() != null) {
                  ItemEntity item = new ItemEntity(world, player.m_20185_(), player.m_20186_(), player.m_20189_(), tile.getStack());
                  world.m_7967_(item);
               }

               tile.setStack(player.m_150109_().m_7407_(player.m_150109_().f_35977_, 1));
            }

            world.m_7260_(pos, state, state, 2);
         }

         return InteractionResult.SUCCESS;
      }
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
      if (worldIn.m_7702_(pos) instanceof ArcanePedestalTile tile && tile.getStack() != null) {
         worldIn.m_7967_(new ItemEntity(worldIn, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), tile.getStack()));
      }
   }

   public VoxelShape m_5940_(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
      return shape;
   }

   public BlockEntity m_142194_(BlockPos pos, BlockState state) {
      return new ArcanePedestalTile(pos, state);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{BlockStateProperties.f_61362_});
   }

   public FluidState m_5888_(BlockState state) {
      return state.m_61143_(BlockStateProperties.f_61362_) ? Fluids.f_76193_.m_76068_(false) : Fluids.f_76191_.m_76145_();
   }

   @NotNull
   public BlockState m_5573_(BlockPlaceContext context) {
      FluidState fluidState = context.m_43725_().m_6425_(context.m_8083_());
      return (BlockState)this.m_49966_().m_61124_(BlockStateProperties.f_61362_, fluidState.m_76152_() == Fluids.f_76193_);
   }

   public BlockState m_7417_(BlockState stateIn, Direction side, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)stateIn.m_61143_(BlockStateProperties.f_61362_)) {
         worldIn.m_186469_(currentPos, Fluids.f_76193_, Fluids.f_76193_.m_6718_(worldIn));
      }

      return stateIn;
   }

   public boolean m_7278_(BlockState state) {
      return true;
   }

   public int m_6782_(BlockState blockState, Level worldIn, BlockPos pos) {
      ArcanePedestalTile tile = (ArcanePedestalTile)worldIn.m_7702_(pos);
      return tile != null && !tile.getStack().m_41619_() ? 15 : 0;
   }

   public void m_6861_(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
      super.m_6861_(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
      if (!pLevel.f_46443_ && pLevel.m_7702_(pPos) instanceof ArcanePedestalTile tile && tile.hasSignal != pLevel.m_46753_(pPos)) {
         tile.hasSignal = !tile.hasSignal;
         tile.updateBlock();
      }
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
