package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.block.tile.AlterationTile;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class AlterationTable extends TableBlock {
   public static VoxelShape SOUTH_OTHER = Shapes.m_83124_(
      Block.m_49796_(3.4, 0.0, 1.0, 7.333333, 1.0, 17.0),
      new VoxelShape[]{Block.m_49796_(7.333333, 0.0, 1.0, 12.666667, 4.5, 17.0), Block.m_49796_(12.666667, 4.0, 1.0, 14.0, 10.0, 17.0)}
   );
   public static VoxelShape NORTH_OTHER = Shapes.m_83124_(
      Block.m_49796_(8.666667, 0.0, -1.0, 12.6, 1.0, 15.0),
      new VoxelShape[]{Block.m_49796_(3.333333, 0.0, -1.0, 8.666667, 4.5, 15.0), Block.m_49796_(2.0, 4.0, -1.0, 3.333333, 10.0, 15.0)}
   );
   public static VoxelShape EAST_OTHER = Shapes.m_83124_(
      Block.m_49796_(1.0, 0.0, 8.666667, 17.0, 1.0, 12.6),
      new VoxelShape[]{Block.m_49796_(1.0, 0.0, 3.333333, 17.0, 4.5, 8.666667), Block.m_49796_(1.0, 4.0, 2.0, 17.0, 10.0, 3.333333)}
   );
   public static VoxelShape WEST_OTHER = Shapes.m_83124_(
      Block.m_49796_(-1.0, 0.0, 3.4, 15.0, 1.0, 7.333333),
      new VoxelShape[]{Block.m_49796_(-1.0, 0.0, 7.333333, 15.0, 4.5, 12.666667), Block.m_49796_(-1.0, 4.0, 12.666667, 15.0, 10.0, 14.0)}
   );

   public InteractionResult m_6227_(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      if (!world.f_46443_ && handIn == InteractionHand.MAIN_HAND && world.m_7702_(pos) instanceof AlterationTile tile) {
         ItemStack var11 = player.m_21205_();
         if (tile.isMasterTile()) {
            IPerkHolder<ItemStack> holder = PerkUtil.getPerkHolder(var11);
            if (holder instanceof ArmorPerkHolder) {
               if (tile.armorStack.m_41619_()) {
                  tile.setArmorStack(var11, player);
                  return InteractionResult.SUCCESS;
               }
            } else if (var11.m_41619_() && !tile.armorStack.m_41619_()) {
               tile.removeArmorStack(player);
               return InteractionResult.SUCCESS;
            }
         } else if (state.m_61143_(PART) == ThreePartBlock.OTHER) {
            this.m_6227_(world.m_8055_(pos.m_7495_()), world, pos.m_7495_(), player, handIn, hit);
         } else {
            AlterationTile var10 = tile.getLogicTile();
            if (var10 == null) {
               return InteractionResult.SUCCESS;
            }

            if (var11.m_41619_()) {
               var10.removePerk(player);
               return InteractionResult.SUCCESS;
            }

            if (!(var11.m_41720_() instanceof PerkItem)) {
               PortUtil.sendMessage(player, Component.m_237115_("ars_nouveau.perk.not_perk"));
               return InteractionResult.SUCCESS;
            }

            var10.addPerkStack(var11, player);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.SUCCESS;
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new AlterationTile(pPos, pState);
   }

   public void m_5707_(Level worldIn, BlockPos pos, BlockState state, Player player) {
      super.m_5707_(worldIn, pos, state, player);
      if (worldIn.m_7702_(pos) instanceof AlterationTile tile) {
         tile.dropItems();
      }
   }

   @Override
   public VoxelShape m_5940_(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      ThreePartBlock partBlock = (ThreePartBlock)state.m_61143_(PART);
      if (partBlock != ThreePartBlock.OTHER) {
         return super.m_5940_(state, getter, pos, context);
      } else {
         Direction direction = (Direction)state.m_61143_(FACING);
         if (direction == Direction.SOUTH) {
            return SOUTH_OTHER;
         } else if (direction == Direction.NORTH) {
            return NORTH_OTHER;
         } else if (direction == Direction.EAST) {
            return EAST_OTHER;
         } else {
            return direction == Direction.WEST ? WEST_OTHER : super.m_5940_(state, getter, pos, context);
         }
      }
   }

   public VoxelShape m_5939_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      ThreePartBlock partBlock = (ThreePartBlock)pState.m_61143_(PART);
      return partBlock != ThreePartBlock.OTHER ? super.m_5939_(pState, pLevel, pPos, pContext) : Shapes.m_83040_();
   }

   @Override
   public BlockState tearDown(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
      if (!world.m_5776_() && world.m_7702_(pos) instanceof AlterationTile tile) {
         tile.dropItems();
      }

      return Blocks.f_50016_.m_49966_();
   }

   @Override
   public void m_6402_(Level world, BlockPos pos, BlockState state, @javax.annotation.Nullable LivingEntity entity, ItemStack stack) {
      if (!world.f_46443_) {
         BlockPos blockpos = pos.m_121945_((Direction)state.m_61143_(FACING));
         world.m_7731_(blockpos, (BlockState)state.m_61124_(PART, ThreePartBlock.HEAD), 3);
         BlockPos lecternPos = pos.m_121945_(Direction.UP);
         world.m_7731_(lecternPos, (BlockState)state.m_61124_(PART, ThreePartBlock.OTHER), 3);
         world.m_6289_(pos, Blocks.f_50016_);
         state.m_60701_(world, pos, 3);
      }
   }

   @Override
   public BlockState m_7417_(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
      List<Direction> connectedDirs = this.getConnectedDirections(state);
      if (connectedDirs.contains(direction)) {
         for (Direction dir : connectedDirs) {
            if (world.m_8055_(pos.m_121945_(dir)).m_60734_() != this) {
               return this.tearDown(state, dir, state2, world, pos, pos2);
            }
         }
      }

      return super.m_7417_(state, direction, state2, world, pos, pos2);
   }

   public List<Direction> getConnectedDirections(BlockState state) {
      Direction direction = (Direction)state.m_61143_(FACING);

      return switch ((ThreePartBlock)state.m_61143_(PART)) {
         case HEAD -> List.of(direction.m_122424_());
         case FOOT -> List.of(direction, Direction.UP);
         case OTHER -> List.of(Direction.DOWN);
         default -> List.of();
      };
   }

   @Nullable
   @Override
   public BlockState m_5573_(BlockPlaceContext context) {
      Direction direction = context.m_8125_();
      BlockPos blockpos = context.m_8083_();
      BlockPos blockpos1 = blockpos.m_121945_(direction);
      BlockState horizontalState = context.m_43725_().m_8055_(blockpos1);
      BlockState aboveState = context.m_43725_().m_8055_(blockpos.m_7494_());
      return horizontalState.m_60629_(context) && aboveState.m_60629_(context) ? (BlockState)this.m_49966_().m_61124_(FACING, direction) : null;
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
