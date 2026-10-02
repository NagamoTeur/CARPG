package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.common.block.tile.ScryerCrystalTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ScryerCrystal extends TickableModBlock {
   public static final DirectionProperty FACING = DirectionProperty.m_156003_("facing");
   public static final BooleanProperty BEING_VIEWED = BooleanProperty.m_61465_("being_viewed");
   public static VoxelShape SOUTH = m_49796_(5.0, 5.0, 0.0, 11.0, 11.0, 1.0);
   public static VoxelShape NORTH = m_49796_(5.0, 5.0, 15.0, 11.0, 11.0, 16.0);
   public static VoxelShape EAST = m_49796_(0.0, 5.0, 5.0, 1.0, 11.0, 11.0);
   public static VoxelShape WEST = m_49796_(15.0, 5.0, 5.0, 16.0, 11.0, 11.0);
   public static VoxelShape UP = m_49796_(5.0, 0.0, 5.0, 11.0, 1.0, 11.0);
   public static VoxelShape DOWN = m_49796_(5.0, 15.0, 5.0, 11.0, 16.0, 11.0);

   public ScryerCrystal(Properties properties) {
      super(properties);
      this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(FACING, Direction.NORTH)).m_61124_(BEING_VIEWED, false));
   }

   public ScryerCrystal() {
      this(defaultProperties().m_60955_());
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING, BEING_VIEWED});
   }

   public VoxelShape m_5939_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
      return Shapes.m_83040_();
   }

   public VoxelShape m_5940_(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
      if (pState.m_61143_(FACING) == Direction.SOUTH) {
         return SOUTH;
      } else if (pState.m_61143_(FACING) == Direction.NORTH) {
         return NORTH;
      } else if (pState.m_61143_(FACING) == Direction.EAST) {
         return EAST;
      } else if (pState.m_61143_(FACING) == Direction.WEST) {
         return WEST;
      } else if (pState.m_61143_(FACING) == Direction.UP) {
         return UP;
      } else {
         return pState.m_61143_(FACING) == Direction.DOWN ? DOWN : EAST;
      }
   }

   public static Position getDispensePosition(BlockSource coords, Direction direction) {
      double negOffset = -0.49;
      double d0 = coords.m_7096_() + negOffset * (double)direction.m_122429_();
      double d1 = coords.m_7098_() + negOffset * (double)direction.m_122430_();
      double d2 = coords.m_7094_() + negOffset * (double)direction.m_122431_();
      return new PositionImpl(d0, d1, d2);
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new ScryerCrystalTile(pPos, pState);
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pLevel.m_7702_(pPos) instanceof ScryerCrystalTile scryerCrystalTile && pPlayer.m_21120_(pHand).m_41619_() & pHand == InteractionHand.MAIN_HAND) {
         scryerCrystalTile.mountCamera(pLevel, pPos, pPlayer);
      }

      return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
   }

   public BlockState m_6843_(BlockState state, Rotation rot) {
      return (BlockState)state.m_61124_(FACING, rot.m_55954_((Direction)state.m_61143_(FACING)));
   }

   public BlockState m_6943_(BlockState state, Mirror mirrorIn) {
      return state.m_60717_(mirrorIn.m_54846_((Direction)state.m_61143_(FACING)));
   }

   public boolean m_7278_(BlockState state) {
      return true;
   }

   public int m_6782_(BlockState blockState, Level worldIn, BlockPos pos) {
      return worldIn.m_7702_(pos) instanceof ScryerCrystalTile scryerCrystalTile ? scryerCrystalTile.playersViewing : 0;
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext pContext) {
      return (BlockState)this.m_49966_().m_61124_(FACING, pContext.m_43719_());
   }

   public RenderShape m_7514_(BlockState p_149645_1_) {
      return RenderShape.MODEL;
   }
}
